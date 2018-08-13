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

import java.io.File;

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
            cluster.connect().executeGraph("system.graph('offernet').ifNotExists().create()");
            if (! Global.parameters.persistence) {
              cluster.connect().executeGraph("system.graph('offernet').drop()");  
              cluster.connect().executeGraph("system.graph('offernet').ifNotExists().create()");
            }
            

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

    private Object setEvaluationTimeout(String timeout) {
       String query = "schema.config().option('graph.traversal_sources.g.evaluation_timeout').set('$timeout')"
       cluster.connect().executeGraph(query);

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
      ArrayList parameters = ['agent','knows', 'both', message, Global.parameters.simulationId]
      Method msg = new Method('degreeDistribution', parameters)
      analyst.tell(msg,ActorRef.noSender());

      parameters = ['agent','owns','both', message, Global.parameters.simulationId]
      msg = new Method('degreeDistribution', parameters)
      analyst.tell(msg,ActorRef.noSender());

      parameters = ['item','similarity','out', message, Global.parameters.simulationId]
      msg = new Method('degreeDistribution', parameters)
      analyst.tell(msg,ActorRef.noSender());

      parameters = ['work','demands','both', message, Global.parameters.simulationId]
      msg = new Method('degreeDistribution', parameters)
      analyst.tell(msg,ActorRef.noSender());

      parameters = ['work','offers','both', message, Global.parameters.simulationId]
      msg = new Method('degreeDistribution', parameters)
      analyst.tell(msg,ActorRef.noSender());
      
      analyst.tell(new Method('allEdgesByLabel', [message, Global.parameters.simulationId]),ActorRef.noSender());
      analyst.tell(new Method('allVerticesByLabel', [message, Global.parameters.simulationId]),ActorRef.noSender());
      analyst.tell(new Method('similarityEdgesByWeight', [message, Global.parameters.simulationId]),ActorRef.noSender());
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

      SimpleGraphStatement s = new SimpleGraphStatement("g.V(idName)",params);
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
    public List allPathsCentralized2(Object similarityThreshold) {
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

    public void removeEdges(String vertexType, String edgeLabel) {
        Map params = new HashMap();
        params.put("edgeLabel", edgeLabel);
        params.put("vertexType", vertexType);

        logger.debug("Removing all {} links", edgeLabel);

        SimpleGraphStatement s = new SimpleGraphStatement(
                "g.V().has('type',vertexType).outE(edgeLabel).drop()",params);

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
          def itemsOne = g.V().has('type','item').values('itemId').toList() // gets all items in the graph by itemIds
          def itemsTwo = itemsOne.clone() // 
          def sedges = 0

          for (int i = 0; i<itemsOne.size(); i++) {
              def itemOneId  = itemsOne[i]
              itemsTwo = itemsTwo.drop(1)
              def agentOne = g.V().has('type','item').has('itemId',itemOneId).inE('offers','demands').as('edgeOne').outV().as('workOne').in('owns').as('agentOne').next()

              for (int z = 0; z<itemsTwo.size(); z++) {
                  def itemTwoId = itemsTwo[z]
                  def agentTwo = g.V().has('type','item').has('itemId',itemTwoId).inE('offers','demands').as('edgeOne').outV().as('workOne').in('owns').as('agentOne').next()
                  if (!agentOne.equals(agentTwo)) {
                      // connect if similar
                      def valueOne = g.V().has('type','item').has('itemId',itemOneId).values('value').next()
                      def valueTwo = g.V().has('type','item').has('itemId',itemTwoId).values('value').next()
                      Double similarity = 1 - Math.abs(valueOne - valueTwo)
                      if (similarity >= similarityThreshold) {
                          if (!g.V().has('type','item').has('itemId',itemOneId).bothE('similarity').otherV().values('itemId').where(is(eq(itemTwoId))).hasNext()) {
                              def sedge = g.V().has('type','item').has('itemId',itemOneId).addE('similarity').to(g.V().has('type','item').has('itemId',itemTwoId)).property('similarity',similarity).next()
                              g.V().has('type','item').has('itemId',itemOneId).addE('similarity').from(g.V().has('type','item').has('itemId',itemTwoId)).property('similarity',similarity).next()
                              sedges +=1
                          }
                      }
                  }
              }
          }
          sedges.size()
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

    // all following methods are for centralized search:

    private List connectAllSimilarCentralized() {
      def timeout = Global.parameters.simulationTimeout
      def similarityThreshold = Global.parameters.similarityThreshold
      def allItems = this.getVertices('item')
      def start = System.currentTimeMillis();
      def similarityEdges=[];
      def tail = allItems.clone();
      logger.debug('initiating similarity search from every agent in the network')
      for (int i =0; i<allItems.size();i++) {
        logger.debug('similaritySearchLoop: allItems size is {} ', allItems.size())
        def item = allItems[i];
        logger.debug('similaritySearchLoop:  currentItem {} ', item)
        tail = tail.drop(1);
        logger.debug('similaritySearchLoop: tail {} ', tail)
        similarityEdges.addAll(connectAllSimilar(item,tail,similarityThreshold));
        if ((System.currentTimeMillis() - start )>timeout*1000) {
          break;
        }
      }
      logger.debug('Added total {} similarity edges to the graph (centralized)',similarityEdges.size())

      logger.info('method={} : simulationId={} : items_count={} ; similarityThreshold={} ; similarity_edges_count={} : wallTime_ms={} msec.', 
          'connectAllSimilarCentralized', 
          Global.parameters.simulationId,
          allItems.size(),
          similarityThreshold,
          similarityEdges.size(),
          (System.currentTimeMillis()-start))

      return similarityEdges;
    }


    /**
    Exact copy of Agent.connectAllSimilar
    */
    private List connectAllSimilar(Vertex item, List<Vertex> itemsOfKnownAgents, Double similarityThreshold) {
        def start = System.currentTimeMillis()
        def similarityEdges = [];
        itemsOfKnownAgents.each {knownItem ->
            def edge = this.connectIfSimilar(item,knownItem,similarityThreshold)
            if (edge != null) {similarityEdges.add(edge)}
        }
        
        logger.info('method={} : simulationId={} : item={}; known_items_count={} ; similarityThreshold={} ; added_similarity_edges_count={} : wallTime_ms={} msec.', 
          'connectAllSimilar', 
          Global.parameters.simulationId,
          item.getId(),
          itemsOfKnownAgents.size(),
          similarityThreshold,
          similarityEdges.size(),
          (System.currentTimeMillis()-start))

        return similarityEdges;
    }

    /**
    Exact copy of Agent.connectIfSimilar
    */
    private Edge connectIfSimilar(Vertex item, Vertex knownItem, Double similarityThreshold) {
      def start = System.currentTimeMillis()
      def similarityEdge = null;
      if (this.existsSimilarity(item,knownItem) == -1) {
        def similarity = Utils.calculateSimilarity(item,knownItem);
        logger.debug("The similarity between items {} and {} is {}", item.getId(),knownItem.getId(),similarity);
        if (similarity >= similarityThreshold) {
            logger.debug("similarity {}  >= similarityThreshold {}, therefore connecting", similarity, similarityThreshold)
            similarityEdge = this.reciprocalDistanceLink(item,knownItem,similarity)
        } else {
           logger.debug("similarity {} < similarityThreshold {}, therefore not connecting", similarity, similarityThreshold)
        }
      }
      
      logger.info('method={} : simulationId={} : item={} ; knownItem={} ; similarityThreshold={} ; added_similarity_edge={} : wallTime_ms={} msec.', 
        'connectIfSimilar', 
        Global.parameters.simulationId,
        item.getId(),
        knownItem.getId(),
        similarityThreshold,
        similarityEdge ? similarityEdge.getId(): null,
        (System.currentTimeMillis()-start))

      return similarityEdge;
  }

  /**
  Exact copy of Agent.existsSimilarity
  */
  private Integer existsSimilarity(Vertex item, Vertex anotherItem) {
    def start = System.currentTimeMillis();
    logger.debug("Checking if explicit similarity link exists between from {} to {}",item.getId(),anotherItem.getId())
    List similarityList = []
    this.similarityEdges(item).each { outEdge ->
        if (outEdge.getInV() == anotherItem.getId()) {
          similarityList.add(outEdge);
          logger.debug("Found similarity link {}",outEdge)
        }
    }
    def similarity = similarityList.isEmpty()!= true ? Utils.edgePropertyValue(similarityList[0],'similarity') : -1;

    logger.info('method={} : simulationId={} : item={} ; anotherItem={} ; similarity_value={} : wallTime_ms={} msec.', 
      'existsSimilarity', 
      Global.parameters.simulationId,
      item.getId(),
      anotherItem.getId(),
      similarity,
      (System.currentTimeMillis()-start))


    return similarity;
  }

  /**
  Exact copy of Agent.similarityEdges
  */
  private List<Edge> similarityEdges(Vertex item) {
    def start = System.currentTimeMillis()
    Map params = new HashMap();
    params.put("thisItem", item.getId());

    SimpleGraphStatement s = new SimpleGraphStatement(
          "g.V(thisItem).outE('similarity')", params)

    GraphResultSet rs = session.executeGraph(s);
    List similarityEdges = rs.all().collect {it.asEdge()};

    logger.info('method={} : simulationId={} : item={} ; similarity_edges_count={} : wallTime_ms={} msec.', 
      'similarityEdges', 
      Global.parameters.simulationId,
      item.getId(),
      similarityEdges.size(),
      (System.currentTimeMillis()-start))

    return similarityEdges;

  }

  /**
  Exact copy of Agent.reciprocalDistanceLink
  */
  private Edge reciprocalDistanceLink(Vertex item, Vertex knownItem, Object similarity) {
     // every similarity edge created also triggers the creation of reciprocal edge with same parameters
     //this.connect(knownItem,item,similarity);
     return this.connect(item,knownItem, similarity);
  }

  /**
  Exact copy of Agent.connect
  */
  private Object connect(Vertex item, Vertex knownItem, Object similarity) {
    def start = System.currentTimeMillis();
    Map params = new HashMap();
    params.put("item1", item.getId());
    params.put("item2",knownItem.getId());
    params.put('edgeLabel','similarity');
    params.put('valueKey','similarity');
    params.put('valueName', (Double) similarity);

    logger.debug("Creating similarity edge from item {} to item {} with value {}", params.item1, params.item2, similarity)

    SimpleGraphStatement s = new SimpleGraphStatement(
            "def v1 = g.V(item1).next()\n" +
            "def v2 = g.V(item2).next()\n" +
            "def e = v1.addEdge(edgeLabel, v2)\n"+
            "e.property(valueKey,valueName)\n"+
            "def e2 = v2.addEdge(edgeLabel, v1)\n"+
            "e2.property(valueKey,valueName)\n"+
            "e", params);

    GraphResultSet rs = session.executeGraph(s);
    def similarityEdge = rs.one().asEdge();

    logger.info('method={} : simulationId={} : item={} ; knownItem={} ; similarity={} ; similarityEdge={} : wallTime_ms={} msec.', 
      'connect', 
      Global.parameters.simulationId,
      item.getId(),
      knownItem.getId(),
      similarity,
      similarityEdge.getId(),
      (System.currentTimeMillis()-start))


    return similarityEdge;
  }

  private boolean archive() {
    SimpleGraphStatement developmentModeOn = new SimpleGraphStatement("schema.config().option('graph.schema_mode').set('Development')")
    session.executeGraph(developmentModeOn)

    String outFileDir = System.getProperty("user.dir")+"/"+
              Global.parameters.experimentDataDir + "/" + 
              Global.parameters.experimentId + "/" +
              Global.parameters.simulationId
    File dir = new File(outFileDir)
    dir.mkdirs()
    dir.setWritable(true,false)
    def outputFilePath = outFileDir +"/graph.graphml" 

    Map params = new HashMap();
    params.put("outputFilePath", outputFilePath);

    SimpleGraphStatement s = new SimpleGraphStatement(
            "g.getGraph().io(IoCore.graphml()).writeGraph(outputFilePath)", params);

    session.executeGraph(s);

    SimpleGraphStatement developmentModeOff = new SimpleGraphStatement("schema.config().option('graph.schema_mode').set('Production')")
    session.executeGraph(developmentModeOff)

  }

}
