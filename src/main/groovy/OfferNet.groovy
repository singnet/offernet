package io.singularitynet.offernet

import com.datastax.driver.dse.DseCluster;
import com.datastax.driver.dse.DseSession;

import com.datastax.driver.dse.graph.GraphStatement;
import com.datastax.driver.dse.graph.SimpleGraphStatement;
import com.datastax.driver.dse.graph.GraphResultSet
import com.datastax.driver.dse.graph.GraphOptions

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
            cluster = DseCluster.builder().addContactPoint("dse-server.host").build();
            cluster.connect().executeGraph("system.graph('offernet').ifNotExists().create()");
            
            cluster = DseCluster.builder()
                .addContactPoint("dse-server.host")
                .withGraphOptions(new GraphOptions().setGraphName("offernet"))
                .build();
            session = cluster.connect();

            session.executeGraph(new SimpleGraphStatement("schema.config().option('graph.schema_mode').set('Development')"))

            logger.trace("Created OfferNet instance with session {}", session);
            logger.trace("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)

            if (Global.parameters.visualizationEngine) {
              socketWriter = createSocketWriter();
//              Thread.sleep(2000);
//              openVisualizationWindow();
            }

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
          Utils.getCurrentMethodName(), 
          Global.parameters.simulationId,
          (System.currentTimeMillis()-start))
    }

    private Object createSocketWriter() {
      def socketWriter = system.actorOf(SocketWriter.props(),"SocketWriter");
      logger.trace("created a new SocketWriter actor {}", socketWriter);
      socketWriter.tell(new Method("createSocket",[]),ActorRef.noSender());
      return socketWriter;
    }

    private void openVisualizationWindow() {
      String visualizationPagePath = Global.parameters.visualizationPagePath;
      String path = System.getProperty("user.dir")+"/"+visualizationPagePath;
      def builder = new ProcessBuilder( "firefox", path)
      builder.start()
      logger.trace("Opened browser with visualization page: {}", path)
    }

    @Override
    public void close() throws Exception {
        String clusterName = cluster.toString()
        String sessionName = session.toString()
        this.session.close();
        logger.trace("Closed session {}",sessionName);
        this.cluster.close();
        logger.trace("Closed cluster {}",clusterName);
    }

    public void flushVertices(String labelName) {
      Map params = new HashMap();
      params.put("labelName", labelName);

      SimpleGraphStatement s = new SimpleGraphStatement("g.V().has(label,labelName).drop()",params);
      GraphResultSet rs = session.executeGraph(s);
      logger.trace("Executed statement: {}", Utils.getStatement(rs));
      logger.trace("Execution warnings from the server: {}", Utils.getWarnings(rs));
      logger.trace("Dropped vertexes with label {} from OfferNet", labelName);
    }

    public Object flushVertices() {
      SimpleGraphStatement s = new SimpleGraphStatement("g.V().drop()");
      GraphResultSet rs = session.executeGraph(s);
      logger.trace("Executed statement: {}", Utils.getStatement(rs));
      logger.trace("Execution warnings from the server: {}", Utils.getWarnings(rs));
      logger.trace("Dropped all vertexes from OfferNet");
      return this;
    }

    public List getIds(String labelName) {
      def start = System.currentTimeMillis()
      Map params = new HashMap();
      params.put("labelName", labelName);

      SimpleGraphStatement s = new SimpleGraphStatement("g.V().has(label,labelName).id()",params);
      GraphResultSet rs = session.executeGraph(s);
      logger.trace("Executed statement: {}", Utils.getStatement(rs));
      logger.trace("Execution warnings from the server: {}", Utils.getWarnings(rs));

      List<Object> agentIds = rs.all();
      logger.trace("Retrieved list of {} vertices with label '{}' from OfferNet", agentIds.size(),labelName);
      logger.trace("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)
      return agentIds;
    }

    private Object getVertex(String id) {
      Map params = new HashMap();
      params.put("idName", id);

      SimpleGraphStatement s = new SimpleGraphStatement("g.V().has(id,idName)",params);
      GraphResultSet rs = session.executeGraph(s);
      logger.trace("Executed statement: {}", Utils.getStatement(rs));
      logger.trace("Execution warnings from the server: {}", Utils.getWarnings(rs));

      Vertex vertex = rs.one().asVertex();
      Object jsonVertex = Utils.vertexToJson(vertex)
      logger.trace("Converted to json representation {}", jsonVertex)
      return jsonVertex;
    }


    private List getVertices(String labelName) {
      def start = System.currentTimeMillis()
      Map params = new HashMap();
      params.put("labelName", labelName);

      SimpleGraphStatement s = new SimpleGraphStatement("g.V().has(label,labelName)",params);
      GraphResultSet rs = session.executeGraph(s);
      logger.trace("Executed statement: {}", Utils.getStatement(rs));
      logger.trace("Execution warnings from the server: {}", Utils.getWarnings(rs));

      List<Object> vertices = rs.all().collect{it.asVertex()};
      logger.trace("Retrieved list of {} vertices from OfferNet",  vertices.size());
      logger.trace("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)
      return vertices;
    }

    private List getVertices(String propertyName, String propertyValue) {
      def start = System.currentTimeMillis()
      Map params = new HashMap();
      params.put("propertyName", propertyName);
      params.put("propertyValue", propertyValue);

      SimpleGraphStatement s = new SimpleGraphStatement("g.V().has(propertyName,propertyValue)",params);
      GraphResultSet rs = session.executeGraph(s);
      logger.trace("Executed statement: {}", Utils.getStatement(rs));
      logger.trace("Execution warnings from the server: {}", Utils.getWarnings(rs));

      List<Object> vertices = rs.all().collect{it.asVertex()};
      logger.trace("Retrieved list of {} vertices from OfferNet", vertices.size());
      logger.trace("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)
      return vertices;
    }

    public List getEdges(String labelName) {
      def start = System.currentTimeMillis()
      Map params = new HashMap();
      params.put("labelName", labelName);

      SimpleGraphStatement s = new SimpleGraphStatement("g.E().has(label,labelName)",params);
      GraphResultSet rs = session.executeGraph(s);
      logger.trace("Executed statement: {}", Utils.getStatement(rs));
      logger.trace("Execution warnings from the server: {}", Utils.getWarnings(rs));

      List<Object> agentIds = rs.all().collect{it.asEdge()};
      logger.trace("Retrieved list of {} edges from OfferNet", agentIds.size());
      logger.trace("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)
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
        logger.trace("Centralized search of all demand-offer pairs with perfect similarities in the network");
        def start = System.currentTimeMillis();
        Map params = new HashMap();
        params.put("similarityThreshold", similarityThreshold);

        String query = """
                g.V().match(
                  __.as('g').has(label,'work').as('w').out('offers').as('o').properties('value').value().as('b')
                ,__.as('o').outE('similarity').as('s').properties('similarity').value().is(gte(similarityThreshold))
                ,__.as('s').inV().as('d')
                ,__.as('d').properties('value').value().as('b')
                ,__.as('d').in('demands').as('w2')
                ).select('b','o','d')
              """

        SimpleGraphStatement s = new SimpleGraphStatement(query,params);
        GraphResultSet rs = session.executeGraph(s);
        List edges = rs.all();
        logger.trace("Found {} similarity edges in the network", edges.size());
        logger.trace("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)

        return edges;

    }

    /* 
    * gets all similarity edges and connected items and connected works by iterating through edges
    * in a centralized manner; version 1 theoretically should be more efficient
    */ 
    public List allSimilarityEdgesRich(Object similarityThreshold, int version = 2) {
      logger.trace("Pulling all similarity edges and rich structure around them from the graph");
      def start = System.currentTimeMillis();
      Map params = new HashMap();
      params.put("similarityConstraint", similarityThreshold);

      String query = """
            g.E().has(label,'similarity').as('s-edge').properties('similarity').value().is(similarityConstraint).as('s-value').match(
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
        logger.trace("Found {} demand-offer pairs existing in the network", edges.size());
        logger.trace("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)

        return edges;


    }

    /**
    * Taking all similarity edges and calculating paths in a centralized way 
    * ensuring completeness (i.e. all paths in the network will be found)
    */
    public List allPathsCentralized(List similarityEdges, int version=1){
      logger.trace("Centralized search of all paths in the network, version 1");
      // not implemented
    }

    /**
    * Almost equivalent to the algorithm of agent, but iterates all agents within the graph -- so not using
    * the fact that some vertices are better positioned and therefore can find the path faster;
    */
    public List allPathsCentralized(Object similarityThreshold, int version=3, Integer cutoffValue) {
      logger.trace("Centralized search of all paths in the network, version 3");
      def start = System.currentTimeMillis()
      Map params = new HashMap();
      params.put("cutoffValue", cutoffValue);
      params.put("similarityConstraint", similarityThreshold);

      String query="""
         g.V().has(label,'work').as('source').repeat(
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
      logger.trace("Executed statement: {}",Utils.getStatement(rs,params));
      logger.trace("With parameters: {}", params);
      def result = rs.all()
      logger.trace("Received result {}",result)

      logger.info('method={} : simulationId={} : version={} ; similarityThreshold={} ; cutoffValue={} ; paths_count={} : wallTime_ms={} msec.', 
        Utils.getCurrentMethodName(), 
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
        logger.trace("Centralized search of all paths in the network, version 2: maxPathLength=3");
        def start = System.currentTimeMillis();
        Map params = new HashMap();
        params.put("similarityConstraint", similarityThreshold);
      
        String query="""
          g.V().has(label,'work').
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
        logger.trace("Found {} paths",paths);

        logger.info('method={} : simulationId={} : version={} ; similarityThreshold={} ; paths_count={} : wallTime_ms={} msec.', 
          Utils.getCurrentMethodName(), 
          Global.parameters.simulationId,
          version,
          similarityThreshold,
          paths.size(),
          (System.currentTimeMillis()-start))

        return paths
    }

    public List allWorkItemEdges(String itemName) {
        logger.trace("Centralized search of work vertexes in the graph");
        def start = System.currentTimeMillis();

        Map params = new HashMap();
        params.put("labelName", "work");
        params.put("itemName",itemName);
        params.put("propertyName","value");

        SimpleGraphStatement s = new SimpleGraphStatement(
              "g.V().has(label,labelName).outE(itemName).as('d').inV().properties(propertyName).as('v').select('d','v')"
              ,params)

        GraphResultSet rs = session.executeGraph(s);
        List edges = rs.all();
        logger.trace("Found {} allWorkItemEdges of {} existing in the network", edges.size(), itemName);

        logger.info('method={} : simulationId={} : version={} ; label={} ; edges count={} : wallTime_ms={} msec.', 
          Utils.getCurrentMethodName(), 
          Global.parameters.simulationId,
          itemName,
          edges.size(),
          (System.currentTimeMillis()-start))

        return edges

    }

    public List allSimilarityEdges() {

        def start = System.currentTimeMillis();
        Map params = new HashMap();
        params.put("labelName", "similarity");

        logger.trace("Returning all similarity links");

        SimpleGraphStatement s = new SimpleGraphStatement(
                "g.E().has(label,labelName)",params);

        GraphResultSet rs = session.executeGraph(s);
        List edges = rs.all();
        logger.trace("Found {} similarity Edges existing in the network", edges.size());
        logger.trace("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)

        return edges;
    }

    public void removeEdges(String edgeLabel) {
        Map params = new HashMap();
        params.put("labelName", edgeLabel);

        logger.trace("Removing all {}} links", edgeLabel);

        SimpleGraphStatement s = new SimpleGraphStatement(
                "g.E().has(label,labelName).drop()",params);

        GraphResultSet rs = session.executeGraph(s);
        logger.trace("Removed all {} edges from the graph", edgeLabel);
    }

    public Vertex createAgent() {
        Map params = new HashMap();
        params.put("labelValue", "agent");
        return createVertex(params)
    }

    private Vertex createVertex(Map params) {
        def start = System.currentTimeMillis();

        GraphResultSet rs = session.executeGraph(new SimpleGraphStatement("g.addV(labelValue)", params));
        def vertex = rs.one().asVertex();

        logger.trace("Created a new {} with id {}", vertex.getLabel(), vertex.getId());
        logger.trace("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)
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
        logger.trace("Creating knows edge from agent {} to agent {}", params.agent1, params.agent2)

        SimpleGraphStatement s = new SimpleGraphStatement(
                "def v1 = g.V(agent1).next()\n" +
                "def v2 = g.V(agent2).next()\n" +
                "v1.addEdge(edgeLabel, v2)", params)

        GraphResultSet rs = session.executeGraph(s);
        def edge = rs.one().asEdge();

        logger.info('method={} : simulationId={} : parameters={} ; edge={} : wallTime_ms={} msec.', 
          Utils.getCurrentMethodName(), 
          Global.parameters.simulationId,
          params,
          edge.getId(),
          (System.currentTimeMillis()-start))

        return edge;
    }

    private List connectAllSimilarCentralized(List<Vertex> allItems, Double similarityThreshold) {
      def start = System.currentTimeMillis();
      def tail;
      def similarityEdges=[];
      def recursiveSimilaritySearch
      recursiveSimilaritySearch = {List<Vertex> items ->
        if (items.size > 0 ) {
          def item = items[0];
          tail = items.drop(1)
          logger.trace('tail of items is {}',tail)
          similarityEdges.addAll(connectAllSimilar(item,tail,similarityThreshold))
          tail = recursiveSimilaritySearch(tail);
        } else {tail = []}
        return tail;
      }
      logger.trace('running recursiveSimilaritySearch')
      recursiveSimilaritySearch(allItems);
      logger.trace('Added total {} similarity edges to the graph (centralized)',similarityEdges.size())

      logger.info('method={} : simulationId={} : items_count={} ; similarityThreshold={} ; similarity_edges_count={} : wallTime_ms={} msec.', 
          Utils.getCurrentMethodName(), 
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
          Utils.getCurrentMethodName(), 
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
        logger.trace("The similarity between items {} and {} is {}", item.getId(),knownItem.getId(),similarity);
        if (similarity >= similarityThreshold) {
            logger.trace("similarity {}  >= similarityThreshold {}, therefore connecting", similarity, similarityThreshold)
            similarityEdge = this.reciprocalDistanceLink(item,knownItem,similarity)
        } else {
           logger.trace("similarity {} < similarityThreshold {}, therefore not connecting", similarity, similarityThreshold)
        }
      }
      
      logger.info('method={} : simulationId={} : item={} ; knownItem={} ; similarityThreshold={} ; added_similarity_edge={} : wallTime_ms={} msec.', 
        Utils.getCurrentMethodName(), 
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
    logger.trace("Checking if explicit similarity link exists between from {} to {}",item.getId(),anotherItem.getId())
    List similarityList = []
    this.similarityEdges(item).each { outEdge ->
        if (outEdge.getInV() == anotherItem.getId()) {
          similarityList.add(outEdge);
          logger.trace("Found similarity link {}",outEdge)
        }
    }
    def similarity = similarityList.isEmpty()!= true ? Utils.edgePropertyValue(similarityList[0],'similarity') : -1;

    logger.info('method={} : simulationId={} : item={} ; anotherItem={} ; similarity_value={} : wallTime_ms={} msec.', 
      Utils.getCurrentMethodName(), 
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
      Utils.getCurrentMethodName(), 
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

    logger.trace("Creating similarity edge from item {} to item {} with value {}", params.item1, params.item2, similarity)

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
      Utils.getCurrentMethodName(), 
      Global.parameters.simulationId,
      item.getId(),
      knownItem.getId(),
      similarity,
      similarityEdge.getId(),
      (System.currentTimeMillis()-start))


    return similarityEdge;
  }



}
