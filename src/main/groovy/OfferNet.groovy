package io.singularitynet.offernet

import com.datastax.driver.dse.DseCluster;
import com.datastax.driver.dse.DseSession;

import com.datastax.driver.dse.graph.GraphStatement;
import com.datastax.driver.dse.graph.SimpleGraphStatement;
import com.datastax.driver.dse.graph.GraphResultSet
import com.datastax.driver.dse.graph.GraphOptions
import com.datastax.driver.dse.auth.DsePlainTextAuthProvider;
import com.datastax.driver.core.PoolingOptions;
import com.datastax.driver.core.HostDistance;

import com.datastax.driver.dse.graph.Edge
import com.datastax.driver.dse.graph.Vertex

import org.apache.log4j.PropertyConfigurator
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import akka.actor.ActorSystem;
import akka.actor.ActorRef;

import groovy.json.JsonBuilder

import kamon.Kamon;
import kamon.prometheus.PrometheusReporter;
//import kamon.jaeger.JaegerReporter;
import kamon.zipkin.ZipkinReporter;


public class OfferNet implements AutoCloseable {

    private DseCluster cluster;
    private DseSession session; // creating one 'main' client and allowing to create more with the method
    private Logger logger;
    static ActorSystem system;
    ActorRef socketWriter;
    ActorRef analyst;

    public void ass() {
    Runtime.getRuntime().addShutdownHook(new Thread() {
        public void run() {
          session.close();
          cluster.close();
        }
      });
    }

    private OfferNet() {
        def start = System.currentTimeMillis()
        //loading log4j properties
        //def config = new ConfigSlurper().parse(new File('configs/log4j-properties.groovy').toURL())
        //PropertyConfigurator.configure(config.toProperties())
        logger = LoggerFactory.getLogger('OfferNet.class');
        system =  ActorSystem.create("OfferNet");
        try {

            cluster = DseCluster.builder()
              .addContactPoint("dse-server.host")
              .withAuthProvider(new DsePlainTextAuthProvider(Global.parameters.cassandraUsername.trim(), Global.parameters.cassandraPassword.trim()))
              .build();
            if (! Global.parameters.persistence) {
              cluster.connect().executeGraph("system.graph('offernet').drop()");  
            }
            cluster.connect().executeGraph("system.graph('offernet').ifNotExists().create()");

            PoolingOptions poolingOptions = new PoolingOptions();
            poolingOptions
              .setCoreConnectionsPerHost(HostDistance.LOCAL,  4)
              .setMaxConnectionsPerHost( HostDistance.LOCAL, 10)
              .setCoreConnectionsPerHost(HostDistance.REMOTE, 2)
              .setMaxConnectionsPerHost( HostDistance.REMOTE, 4);

            poolingOptions
              .setMaxRequestsPerConnection(HostDistance.LOCAL, 32768)
              .setMaxRequestsPerConnection(HostDistance.REMOTE, 2000);

            cluster = DseCluster.builder()
                .addContactPoint("dse-server.host")
                .withAuthProvider(new DsePlainTextAuthProvider(Global.parameters.cassandraUsername.trim(), Global.parameters.cassandraPassword.trim()))
                .withPoolingOptions(poolingOptions)
                .withGraphOptions(new GraphOptions().setGraphName("offernet"))
                .build();
            session = cluster.connect();

            if (! Global.parameters.developmentMode) {
              String propertyKeys = """
                schema.propertyKey("value").Double().single().ifNotExists().create();
                schema.propertyKey("agentId").Text().single().ifNotExists().create();
                schema.propertyKey("itemId").Text().single().ifNotExists().create();
                schema.propertyKey("workId").Text().single().ifNotExists().create();
                schema.propertyKey("similarity").Double().single().ifNotExists().create();
                schema.propertyKey("type").Text().single().ifNotExists().create();
              """

              SimpleGraphStatement createPropertyKeys = new SimpleGraphStatement(propertyKeys);
              session.executeGraph(createPropertyKeys)

              String vertexLabels = """
                schema.vertexLabel("agent").properties("agentId").ifNotExists().create();
                schema.vertexLabel("work").properties("workId").ifNotExists().create();
                schema.vertexLabel("item").properties("itemId").ifNotExists().create();
                schema.vertexLabel("item").properties("value").ifNotExists().create();
                schema.vertexLabel("agent").properties("type").ifNotExists().create();
                schema.vertexLabel("work").properties("type").ifNotExists().create();
                schema.vertexLabel("item").properties("type").ifNotExists().create();
              """

              SimpleGraphStatement createVertexLabels = new SimpleGraphStatement(vertexLabels);
              session.executeGraph(createVertexLabels)

              String edgeLabels = """
                schema.edgeLabel("offers").multiple().ifNotExists().create();
                schema.edgeLabel("demands").multiple().ifNotExists().create();
                schema.edgeLabel("owns").multiple().ifNotExists().create();
                schema.edgeLabel("knows").multiple().ifNotExists().create();
                schema.edgeLabel("similarity").multiple().properties("similarity").ifNotExists().create();
                schema.edgeLabel("offers").connection("work", "item").ifNotExists().create();
                schema.edgeLabel("demands").connection("work", "item").ifNotExists().create();
                schema.edgeLabel("owns").connection("agent", "work").ifNotExists().create();
                schema.edgeLabel("knows").connection("agent", "agent").ifNotExists().create();
                schema.edgeLabel("similarity").connection("item", "item").ifNotExists().create();
              """

              SimpleGraphStatement createEdgeLabels = new SimpleGraphStatement(edgeLabels);
              session.executeGraph(createEdgeLabels)

              String vertexIndexes = """
                schema.vertexLabel('agent').index('byAgentId').materialized().by('agentId').ifNotExists().add()
                schema.vertexLabel('item').index('byValue').materialized().by('value').ifNotExists().add()
                schema.vertexLabel('agent').index('byType').materialized().by('type').ifNotExists().add()
                schema.vertexLabel('work').index('byType').materialized().by('type').ifNotExists().add()
                schema.vertexLabel('item').index('byType').materialized().by('type').ifNotExists().add()
              """

              SimpleGraphStatement createVertexIndexes = new SimpleGraphStatement(vertexIndexes);
              session.executeGraph(createVertexIndexes)

              String edgeIndexes = """
                schema.vertexLabel('item').index('bySimilarity').bothE('similarity').by(similarity').ifNotExists().add()
              """

              SimpleGraphStatement createEdgeIndexes = new SimpleGraphStatement(vertexIndexes);
              session.executeGraph(createEdgeIndexes)


            } else if (Global.parameters.developmentMode) {
              SimpleGraphStatement developmentMode = new SimpleGraphStatement("schema.config().option('graph.schema_mode').set('Development')")
              session.executeGraph(developmentMode)
            }

            logger.debug("Created OfferNet instance with session {}", session);
            logger.debug("Method {} took {} seconds to complete", '<init>', (System.currentTimeMillis()-start)/1000)

            if (Global.parameters.visualizationEngine) {
              this.socketWriter = createSocketWriter();
            }

            this.analyst = createAnalyst();

          if (Global.parameters.debugMode) {
            Kamon.addReporter(new PrometheusReporter());
            //Kamon.addReporter(new JaegerReporter());
            Kamon.addReporter(new ZipkinReporter())
            // wait until Kamon initializes -- 
          }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        logger.info('method={} : simulationId={} : wallTime_ms={} msec.', 
          '<init>', 
          Global.parameters.simulationId,
          (System.currentTimeMillis()-start))
    }

    private Object createSocketWriter() {
      def socketWriter = system.actorOf(SocketWriter.props(),"SocketWriter");
      logger.debug("created a new SocketWriter actor {}", socketWriter);
      socketWriter.tell(new Method("createSocket",[]),ActorRef.noSender());
      return socketWriter;
    }

    private Object createAnalyst() {
      def analyst = system.actorOf(Analyst.props(this.session),"Analyst");
      logger.debug("created a new Analyst actor {}", analyst);
      return analyst;
    }

    private analyze(String message) {
      ArrayList parameters = ['agent','knows', 'both', message]
      Method msg = new Method('degreeDistribution', parameters)
      analyst.tell(msg,ActorRef.noSender());

      parameters = ['agent','owns','both', message]
      msg = new Method('degreeDistribution', parameters)
      analyst.tell(msg,ActorRef.noSender());

      parameters = ['item','similarity','out', message]
      msg = new Method('degreeDistribution', parameters)
      analyst.tell(msg,ActorRef.noSender());

      parameters = ['work','demands','both', message]
      msg = new Method('degreeDistribution', parameters)
      analyst.tell(msg,ActorRef.noSender());

      parameters = ['work','offers','both', message]
      msg = new Method('degreeDistribution', parameters)
      analyst.tell(msg,ActorRef.noSender());
      
      analyst.tell(new Method('allEdgesByLabel', [message]),ActorRef.noSender());
      analyst.tell(new Method('allVerticesByLabel', [message]),ActorRef.noSender());
      analyst.tell(new Method('similarityEdgesByWeight', [message]),ActorRef.noSender());
    }

    private void openVisualizationWindow() {
      String visualizationPagePath = Global.parameters.visualizationPagePath;
      String path = System.getProperty("user.dir")+"/"+visualizationPagePath;
      def builder = new ProcessBuilder( "firefox", path)
      builder.start()
      logger.debug("Opened browser with visualization page: {}", path)
    }

    @Override
    public void close() throws Exception {
        String clusterName = cluster.toString()
        String sessionName = session.toString()
        this.session.close();
        logger.debug("Closed session {}",sessionName);
        this.cluster.close();
        logger.debug("Closed cluster {}",clusterName);
    }

    public void flushVertices(String labelName) {
      Map params = new HashMap();
      params.put("labelName", labelName);

      SimpleGraphStatement s = new SimpleGraphStatement("g.V().has('type',labelName).drop()",params);
      GraphResultSet rs = session.executeGraph(s);
      logger.debug("Executed statement: {}", Utils.getStatement(rs));
      logger.debug("Execution warnings from the server: {}", Utils.getWarnings(rs));
      logger.debug("Dropped vertexes with label {} from OfferNet", labelName);
    }

    public Object flushVertices() {
      String query = "g.V().has('type',within(['work','agent','item'])).drop()" // delete all one by one -- may give errors if exceeds resource limits...
      SimpleGraphStatement s = new SimpleGraphStatement(query);
      GraphResultSet rs = session.executeGraph(s);
      logger.debug("Executed statement: {}", Utils.getStatement(rs));
      logger.debug("Execution warnings from the server: {}", Utils.getWarnings(rs));
      logger.debug("Dropped all vertexes from OfferNet");
      return this;
    }

    public List getIds(String labelName) {
      def start = System.currentTimeMillis()
      Map params = new HashMap();
      params.put("propertyValue1", labelName);
      params.put("propertyKey1", "type");

      SimpleGraphStatement s = new SimpleGraphStatement("g.V().has(propertyKey1,propertyValue1).id()",params);
      GraphResultSet rs = session.executeGraph(s);
      logger.debug("Executed statement: {}", Utils.getStatement(rs));
      logger.debug("Execution warnings from the server: {}", Utils.getWarnings(rs));

      List<Object> agentIds = rs.all();
      logger.debug("Retrieved list of {} vertices with label '{}' from OfferNet", agentIds.size(),labelName);
      logger.debug("Method {} took {} seconds to complete", 'getIds', (System.currentTimeMillis()-start)/1000)
      return agentIds;
    }

    private Object getVertex(String id) {
      Map params = new HashMap();
      params.put("idName", id);

      SimpleGraphStatement s = new SimpleGraphStatement("g.V().has(id,idName)",params);
      GraphResultSet rs = session.executeGraph(s);
      logger.debug("Executed statement: {}", Utils.getStatement(rs));
      logger.debug("Execution warnings from the server: {}", Utils.getWarnings(rs));

      Vertex vertex = rs.one().asVertex();
      Object jsonVertex = Utils.vertexToJson(vertex)
      logger.debug("Converted to json representation {}", jsonVertex)
      return jsonVertex;
    }


    private List getVertices(String labelName) {
      def start = System.currentTimeMillis()
      Map params = new HashMap();
      params.put("propertyKey1", "type");
      params.put("propertyValue1", labelName);

      SimpleGraphStatement s = new SimpleGraphStatement("g.V().has(propertyKey1,propertyValue1)",params);
      GraphResultSet rs = session.executeGraph(s);
      logger.debug("Executed statement: {}", Utils.getStatement(rs));
      logger.debug("Execution warnings from the server: {}", Utils.getWarnings(rs));

      List<Object> vertices = rs.all().collect{it.asVertex()};
      logger.debug("Retrieved list of {} vertices from OfferNet",  vertices.size());
      logger.debug("Method {} took {} seconds to complete", 'getVertices', (System.currentTimeMillis()-start)/1000)
      return vertices;
    }

    private List getVertices(String vertexType, String propertyName, String propertyValue) {
      def start = System.currentTimeMillis()
      Map params = new HashMap();
      params.put("propertyKey1", "type");
      params.put("propertyValue1", vertexType);
      params.put("propertyKey2", propertyName);
      params.put("propertyValue2", propertyValue);

      SimpleGraphStatement s = new SimpleGraphStatement("g.V().has(propertyKey1,propertyValue1).has(propertyKey2,propertyValue2)",params);
      GraphResultSet rs = session.executeGraph(s);
      logger.debug("Executed statement: {}", Utils.getStatement(rs));
      logger.debug("Execution warnings from the server: {}", Utils.getWarnings(rs));

      List<Object> vertices = rs.all().collect{it.asVertex()};
      logger.debug("Retrieved list of {} vertices from OfferNet", vertices.size());
      logger.debug("Method {} took {} seconds to complete", 'getVertices', (System.currentTimeMillis()-start)/1000)
      return vertices;
    }

    public List getEdges(String vertexLabel, String edgeLabel) {
      def start = System.currentTimeMillis()
      Map params = new HashMap();
      params.put("propertyKey1", "type");
      params.put("propertyValue1", vertexLabel);
      params.put("edgeLabel", edgeLabel);


      SimpleGraphStatement s = new SimpleGraphStatement("g.V().has(propertyKey1,propertyValue1).bothE(edgeLabel).dedup()",params);
      GraphResultSet rs = session.executeGraph(s);
      logger.debug("Executed statement: {}", Utils.getStatement(rs));
      logger.debug("Execution warnings from the server: {}", Utils.getWarnings(rs));

      List<Object> agentIds = rs.all().collect{it.asEdge()};
      logger.debug("Retrieved list of {} edges from OfferNet", agentIds.size());
      logger.debug("Method {} took {} seconds to complete", 'getEdges', (System.currentTimeMillis()-start)/1000)
      return agentIds;
    }

    private void createAgentNetworkWithChains(String[] args){
        def numberOfAgents = args[1];
        def numberOfRandomWorks = args[2];
        def numberOfChains = args[3];
        def lenghtOfChain = args[4];
        List chains = [];
        numberOfChains.times {
          chains.add(Utils.createChain(lenghtOfChain));
        }
        createAgentNetwork(numberOfAgents,numberOfRandomWorks,chains);
    }

    /* 
    * gets all similarity edges and connected items and connected works by iterating through vertices
    * in a centralized manner
    */ 
    public List allSimilarityEdgesRich(Integer similarityThreshold, int version = 1) {
        logger.debug("Centralized search of all demand-offer pairs with perfect similarities in the network");
        def start = System.currentTimeMillis();
        Map params = new HashMap();
        params.put("similarityThreshold", similarityThreshold);
        params.put("propertyKey1", "type");
        params.put("propertyValue1", "work");

        String query = """
                g.V().match(
                  __.as('g').has(propertyKey1,propertyValue1).as('w').out('offers').as('o').properties('value').value().as('b')
                ,__.as('o').outE('similarity').as('s').properties('similarity').value().is(gte(similarityThreshold))
                ,__.as('s').inV().as('d')
                ,__.as('d').properties('value').value().as('b')
                ,__.as('d').in('demands').as('w2')
                ).select('b','o','d')
              """

        SimpleGraphStatement s = new SimpleGraphStatement(query,params);
        GraphResultSet rs = session.executeGraph(s);
        List edges = rs.all();
        logger.debug("Found {} similarity edges in the network", edges.size());
        logger.debug("Method {} took {} seconds to complete", 'allSimilarityEdgesRich', (System.currentTimeMillis()-start)/1000)

        return edges;

    }

    /* 
    * gets all similarity edges and connected items and connected works by iterating through edges
    * in a centralized manner; version 1 theoretically should be more efficient
    */ 
    public List allSimilarityEdgesRich(Object similarityThreshold, int version = 2) {
      logger.debug("Pulling all similarity edges and rich structure around them from the graph");
      def start = System.currentTimeMillis();
      Map params = new HashMap();
      params.put("similarityConstraint", similarityThreshold);
      params.put("propertyKey1", "type");
      params.put("propertyValue1", "similarity");


      String query = """
            g.E().has(propertyKey1,propertyValue1).as('s-edge').properties('similarity').value().is(similarityConstraint).as('s-value').match(
                __.as('s-edge').inV().bothE('demands').as('demands')
                ,__.as('demands').otherV().as('work1')
                ,__.as('s-edge').outV().bothE('offers').as('offers')
                ,__.as('offers').otherV().as('work2')
                ,__.as('work2').where(neq('work1')))
            .select(values)
            """
        SimpleGraphStatement s = new SimpleGraphStatement(query,params);
        GraphResultSet rs = session.executeGraph(s);
        List edges = rs.all();
        logger.debug("Found {} demand-offer pairs existing in the network", edges.size());
        logger.debug("Method {} took {} seconds to complete", 'allSimilarityEdgesRich', (System.currentTimeMillis()-start)/1000)

        return edges;


    }

    /**
    * Taking all similarity edges and calculating paths in a centralized way 
    * ensuring completeness (i.e. all paths in the network will be found)
    */
    public List allPathsCentralized(List similarityEdges, int version=1){
      logger.debug("Centralized search of all paths in the network, version 1");
      // not implemented
    }

    /**
    * Almost equivalent to the algorithm of agent, but iterates all agents within the graph -- so not using
    * the fact that some vertices are better positioned and therefore can find the path faster;
    */
    public List allPathsCentralized(Object similarityThreshold, int version=3, Integer cutoffValue) {
      logger.debug("Centralized search of all paths in the network, version 3");
      def start = System.currentTimeMillis()
      Map params = new HashMap();
      params.put("cutoffValue", cutoffValue);
      params.put("similarityConstraint", similarityThreshold);
      params.put("propertyKey1", "type");
      params.put("propertyValue1", "work");      

      String query="""
         g.V().has(propertyKey1,propertyValue1).as('source').repeat(
                 __.outE('offers').subgraph('subGraph').inV().bothE('similarity').has('similarity',gte(0.99)).subgraph('subGraph')            // (2)
                .otherV().inE('demands').subgraph('subGraph').outV().dedup()).times(10).emit().cap('subGraph').next().traversal().E()
                .store('allsubGraphs').select('allsubGraphs')      
      """
      /*
      This query gets a list of edges which form a found path
      In order to visualize it showing vertex properties, the list of edges has to be furher procesed
      */
      SimpleGraphStatement s = new SimpleGraphStatement(query,params);
      GraphResultSet rs = session.executeGraph(s);
      logger.debug("Executed statement: {}",Utils.getStatement(rs,params));
      logger.debug("With parameters: {}", params);
      def result = rs.all()
      logger.debug("Received result {}",result)

      logger.info('method={} : simulationId={} : version={} ; similarityThreshold={} ; cutoffValue={} ; paths_count={} : wallTime_ms={} msec.', 
        'allPathsCentralized.v1', 
        Global.parameters.simulationId,
        version,
        similarityThreshold,
        cutoffValue,
        result.size(),
        (System.currentTimeMillis()-start))

      return result;
    }

    /**
    * Getting all paths via match query directly from the graph, without pulling all data to java and
    * connecting paths there
    * the problem is that in order to specify a pattern to match, a path length should be specified (in a quite ugly way within a query), 
    * so at the end there is no completeness guarantee of this method...
    * yet it is left as an example -- could be used for benchmarking performance when all work is done on graph back-end side
    */
    public List allPathsCentralized(Object similarityThreshold, int version=2) {
        logger.debug("Centralized search of all paths in the network, version 2: maxPathLength=3");
        def start = System.currentTimeMillis();
        Map params = new HashMap();
        params.put("similarityConstraint", similarityThreshold);
        params.put("propertyKey1", "type");
        params.put("propertyValue1", "work");          
      
        String query="""
          g.V().has(propertyKey1,propertyValue1).
          match(__.as('w1').out('demands').as('d1')
                ,__.as('d1').bothE('similarity').as('s1').otherV().as('o1')
                ,__.as('o1').in('offers').as('w2')
                ,__.as('w1').where(neq('w2'))
                ,__.as('s1').properties('similarity').value().is(gte(0.99))
                
                ,__.as('w2').out('demands').as('d2')
                ,__.as('d2').bothE('similarity').as('s2').otherV().as('o2')
                ,__.as('o2').in('offers').as('w3')
                ,__.as('w2').where(neq('w3'))
                ,__.as('s2').properties('similarity').value().is(gte(0.99))          

                ,__.as('w3').out('demands').as('d3')
                ,__.as('d3').bothE('similarity').as('s3').otherV().as('o3')
                ,__.as('o3').in('offers').as('w4')
                ,__.as('w3').where(neq('w4'))
                ,__.as('s3').properties('similarity').value().is(gte(0.99))          
                
                )
          .select(values)
          """

        SimpleGraphStatement s = new SimpleGraphStatement(query,params);
        GraphResultSet rs = session.executeGraph(s);
        List paths = rs.all()
        logger.debug("Found {} paths",paths);

        logger.info('method={} : simulationId={} : version={} ; similarityThreshold={} ; paths_count={} : wallTime_ms={} msec.', 
          'allPathsCentralized.v2', 
          Global.parameters.simulationId,
          version,
          similarityThreshold,
          paths.size(),
          (System.currentTimeMillis()-start))

        return paths
    }

    public List allWorkItemEdges(String itemName) {
        logger.debug("Centralized search of work vertexes in the graph");
        def start = System.currentTimeMillis();

        Map params = new HashMap();
        params.put("propertyKey1", "type");
        params.put("propertyValue1", "work"); 
        params.put("itemName",itemName);
        params.put("propertyName","value");

        SimpleGraphStatement s = new SimpleGraphStatement(
              "g.V().has(propertyKey1,propertyValue1).outE(itemName).as('d').inV().properties(propertyName).as('v').select('d','v')"
              ,params)

        GraphResultSet rs = session.executeGraph(s);
        List edges = rs.all();
        logger.debug("Found {} allWorkItemEdges of {} existing in the network", edges.size(), itemName);

        logger.info('method={} : simulationId={} : version={} ; label={} ; edges count={} : wallTime_ms={} msec.', 
          'allWorkItemEdges', 
          Global.parameters.simulationId,
          itemName,
          edges.size(),
          (System.currentTimeMillis()-start))

        return edges

    }

    public List allSimilarityEdges() {

        def start = System.currentTimeMillis();
        Map params = new HashMap();
        params.put("propertyKey1", "type");
        params.put("propertyValue1", "similarity");        

        logger.debug("Returning all similarity links");

        SimpleGraphStatement s = new SimpleGraphStatement(
                "g.E().has(propertyKey1,propertyValue1)",params);

        GraphResultSet rs = session.executeGraph(s);
        List edges = rs.all();
        logger.debug("Found {} similarity Edges existing in the network", edges.size());
        logger.debug("Method {} took {} seconds to complete", 'allSimilarityEdges', (System.currentTimeMillis()-start)/1000)

        return edges;
    }

    public void removeEdges(String edgeLabel) {
        Map params = new HashMap();
        params.put("labelName", edgeLabel);

        logger.debug("Removing all {}} links", edgeLabel);

        SimpleGraphStatement s = new SimpleGraphStatement(
                "g.E().has(label,labelName).drop()",params);

        GraphResultSet rs = session.executeGraph(s);
        logger.debug("Removed all {} edges from the graph", edgeLabel);
    }

    private Vertex createVertex(Map params) {
        def start = System.currentTimeMillis();

        GraphResultSet rs = session.executeGraph(new SimpleGraphStatement("g.addV(labelValue)", params));
        def vertex = rs.one().asVertex();

        logger.debug("Created a new {} with id {}", vertex.getLabel(), vertex.getId());
        logger.debug("Method {} took {} seconds to complete", 'removeEdges', (System.currentTimeMillis()-start)/1000)
        return vertex;
    }

    public Edge knowsAgent(Vertex agent1, Vertex agent2) {
        Map params = new HashMap();
        params.put("agent1", agent1.getId());
        params.put("agent2",agent2.getId());
        params.put('edgeLabel','knows');
        return createEdge(params)
    }

    public Edge createEdge(Map params) {
        def start = System.currentTimeMillis();
        logger.debug("Creating knows edge from agent {} to agent {}", params.agent1, params.agent2)

        SimpleGraphStatement s = new SimpleGraphStatement(
                "def v1 = g.V(agent1).next()\n" +
                "def v2 = g.V(agent2).next()\n" +
                "v1.addEdge(edgeLabel, v2)", params)

        GraphResultSet rs = session.executeGraph(s);
        def edge = rs.one().asEdge();

        logger.info('method={} : simulationId={} : parameters={} ; edge={} : wallTime_ms={} msec.', 
          'createEdge', 
          Global.parameters.simulationId,
          params,
          edge.getId(),
          (System.currentTimeMillis()-start))

        return edge;
    }

    private Integer searchAndConnect(Object similarityThreshold) {
      def start = System.currentTimeMillis();
      Map params = new HashMap();
      params.put("similarityThreshold", similarityThreshold);


      String query = """      
                g.V().has('type','agent')
                    .match(
                        __.as('agent1').out('owns').as('work1').out('offers').as('offer'),
                        __.as('agent1').both('knows').as('agent2'),
                        __.as('agent2').out('owns').as('work2'),
                        __.as('work2').out('demands').as('demand'),
                        __.as('agent1').where(neq('agent2')),
                        __.as('work1').where(neq('work2'))
                    )
                .select('offer').values('itemId').as('offerId')
                .select('offer').values('value').as('offerValue')
                .select('demand').values('value').as('demandValue')
                .math('1-abs(demandValue - offerValue)').as('similarity').where(is(gte(similarityThreshold)))
                .choose(select('demand').bothE('similarity').otherV().values('itemId').where(eq('offerId')),
                          __.select('somilarity'),
                          __.select('offer').union(
                              __.addE('similarity').to('demand').property('similarity',select('similarity')).as('sedge'),
                                addE('similarity').from('demand').property('similarity',select('similarity'))
                            )
                        )
                .select('sedge').count().next()

      """            

      SimpleGraphStatement s = new SimpleGraphStatement(query,params);

      GraphResultSet rs = session.executeGraph(s);
      logger.debug("Executed statement: {}",Utils.getStatement(rs,params));
      logger.debug("Execution warnings from the server: {}", Utils.getWarnings(rs));
      int connectionsCreated = rs.one().asInt();

      logger.info('method={} : simulationId={} : similarityThreshold={} : connectionsCreated={} : wallTime_ms={} msec.', 
        'searchAndConnect', 
        Global.parameters.simulationId,
        similarityThreshold,
        connectionsCreated,
        (System.currentTimeMillis()-start))

      return connectionsCreated

    }
}
