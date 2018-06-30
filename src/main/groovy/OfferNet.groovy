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

public class OfferNet implements AutoCloseable {

    private DseCluster cluster;
    private DseSession session; // creating one 'main' client and allowing to create more with the method
    private Logger logger;
    static ActorSystem system;
    ActorRef socketWriter;

    public void ass() {
    Runtime.getRuntime().addShutdownHook(new Thread() {
        public void run() {
          sesion.close();
          cluster.close();
        }
      });
    }

    private OfferNet() {

        //loading log4j properties
        def config = new ConfigSlurper().parse(new File('configs/log4j-properties.groovy').toURL())
        PropertyConfigurator.configure(config.toProperties())
        logger = LoggerFactory.getLogger('OfferNet.class');
        system =  ActorSystem.create("OfferNet");

        try {
            def start = System.currentTimeMillis()
            cluster = DseCluster.builder().addContactPoint("dse-server.host").build();
            cluster.connect().executeGraph("system.graph('offernet').ifNotExists().create()");
            
            cluster = DseCluster.builder()
                .addContactPoint("dse-server.host")
                .withGraphOptions(new GraphOptions().setGraphName("offernet"))
                .build();
            session = cluster.connect();

            session.executeGraph(new SimpleGraphStatement("schema.config().option('graph.schema_mode').set('Development')"))

            logger.info("Created OfferNet instance with session {}", session);
            logger.warn("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)

            if (Parameters.parameters.visualizationEngine) {
              socketWriter = createSocketWriter();
//              Thread.sleep(2000);
//              openVisualizationWindow();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Object createSocketWriter() {
      def socketWriter = system.actorOf(SocketWriter.props(),"SocketWriter");
      logger.info("created a new SocketWriter actor {}", socketWriter);
      socketWriter.tell(new Method("createSocket",[]),ActorRef.noSender());
      return socketWriter;
    }

    private void openVisualizationWindow() {
      String visualizationPagePath = Parameters.parameters.visualizationPagePath;
      String path = System.getProperty("user.dir")+"/"+visualizationPagePath;
      def builder = new ProcessBuilder( "firefox", path)
      builder.start()
      logger.info("Opened browser with visualization page: {}", path)
    }

    @Override
    public void close() throws Exception {
        String clusterName = cluster.toString()
        String sessionName = session.toString()
        this.session.close();
        logger.info("Closed session {}",sessionName);
        this.cluster.close();
        logger.info("Closed cluster {}",clusterName);
    }

    public void flushVertices(String labelName) {
      Map params = new HashMap();
      params.put("labelName", labelName);

      SimpleGraphStatement s = new SimpleGraphStatement("g.V().has(label,labelName).drop()",params);
      GraphResultSet rs = session.executeGraph(s);
      logger.warn("Executed statement: {}", Utils.getStatement(rs));
      logger.warn("Execution warnings from the server: {}", Utils.getWarnings(rs));
      logger.info("Dropped vertexes with label {} from OfferNet", labelName);
    }

    public Object flushVertices() {
      SimpleGraphStatement s = new SimpleGraphStatement("g.V().drop()");
      GraphResultSet rs = session.executeGraph(s);
      logger.warn("Executed statement: {}", Utils.getStatement(rs));
      logger.warn("Execution warnings from the server: {}", Utils.getWarnings(rs));
      logger.info("Dropped all vertexes from OfferNet");
      return this;
    }

    public List getProperties(String labelName, String labelValue, String propertyName) {
      def start = System.currentTimeMillis()
      Map params = new HashMap();
      params.put("labelName", labelName);
      params.put("labelValue", labelValue);
      params.put("propertyName", propertyName);

      SimpleGraphStatement s = new SimpleGraphStatement("g.V().has(labelName,labelValue).properties(propertyName).value()",params);
      GraphResultSet rs = session.executeGraph(s);
      logger.warn("Executed statement: {}", Utils.getStatement(rs));
      logger.warn("Execution warnings from the server: {}", Utils.getWarnings(rs));

      List<Object> agentIds = rs.all();
      logger.info("Retrieved list of {} agentIds from OfferNet", agentIds.size());
      logger.warn("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)
      return agentIds;
    }

    public List getIds(String labelName) {
      def start = System.currentTimeMillis()
      Map params = new HashMap();
      params.put("labelName", labelName);

      SimpleGraphStatement s = new SimpleGraphStatement("g.V().has(label,labelName).id()",params);
      GraphResultSet rs = session.executeGraph(s);
      logger.warn("Executed statement: {}", Utils.getStatement(rs));
      logger.warn("Execution warnings from the server: {}", Utils.getWarnings(rs));

      List<Object> agentIds = rs.all();
      logger.info("Retrieved list of {} vertices with label '{}' from OfferNet", agentIds.size(),labelName);
      logger.warn("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)
      return agentIds;
    }

    private Object getVertex(String id) {
      Map params = new HashMap();
      params.put("idName", id);

      SimpleGraphStatement s = new SimpleGraphStatement("g.V().has(id,idName)",params);
      GraphResultSet rs = session.executeGraph(s);
      logger.warn("Executed statement: {}", Utils.getStatement(rs));
      logger.warn("Execution warnings from the server: {}", Utils.getWarnings(rs));

      Vertex vertex = rs.one().asVertex();
      Object jsonVertex = Utils.vertexToJson(vertex)
      logger.info("Converted to json representation {}", jsonVertex)
      return jsonVertex;
    }


    private List getVertices(String labelName) {
      def start = System.currentTimeMillis()
      Map params = new HashMap();
      params.put("labelName", labelName);

      SimpleGraphStatement s = new SimpleGraphStatement("g.V().has(label,labelName)",params);
      GraphResultSet rs = session.executeGraph(s);
      logger.warn("Executed statement: {}", Utils.getStatement(rs));
      logger.warn("Execution warnings from the server: {}", Utils.getWarnings(rs));

      List<Object> vertices = rs.all().collect{it.asVertex()};
      logger.info("Retrieved list of {} vertices from OfferNet",  vertices.size());
      logger.warn("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)
      return vertices;
    }

    private List getVertices(String propertyName, String propertyValue) {
      def start = System.currentTimeMillis()
      Map params = new HashMap();
      params.put("propertyName", propertyName);
      params.put("propertyValue", propertyValue);

      SimpleGraphStatement s = new SimpleGraphStatement("g.V().has(propertyName,propertyValue)",params);
      GraphResultSet rs = session.executeGraph(s);
      logger.warn("Executed statement: {}", Utils.getStatement(rs));
      logger.warn("Execution warnings from the server: {}", Utils.getWarnings(rs));

      List<Object> vertices = rs.all().collect{it.asVertex()};
      logger.info("Retrieved list of {} vertices from OfferNet", vertices.size());
      logger.warn("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)
      return vertices;
    }

    public List getEdges(String labelName) {
      def start = System.currentTimeMillis()
      Map params = new HashMap();
      params.put("labelName", labelName);

      SimpleGraphStatement s = new SimpleGraphStatement("g.E().has(label,labelName)",params);
      GraphResultSet rs = session.executeGraph(s);
      logger.warn("Executed statement: {}", Utils.getStatement(rs));
      logger.warn("Execution warnings from the server: {}", Utils.getWarnings(rs));

      List<Object> agentIds = rs.all().collect{it.asEdge()};
      logger.info("Retrieved list of {} edges from OfferNet", agentIds.size());
      logger.warn("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)
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
    * Note that this function is for testing only - it calculates the perfect similarities between items
    */ 
    public List allConnectedSimilarPairsCentralized(Integer similarityThreshold) {
        logger.warn("Centralized search of all demand-offer pairs with perfect similarities in the network");
        def start = System.currentTimeMillis();
        Map params = new HashMap();
        params.put("similarityThreshold", similarityThreshold);


        SimpleGraphStatement s = new SimpleGraphStatement(
                "g.V().match("+
                "__.as('g').has(label,'work').as('w').out('offers').as('o').properties('value').value().as('b')"+
                ",__.as('o').outE('similarity').as('s').properties('similarity').value().is(gte(similarityThreshold))"+
                ",__.as('s').inV().as('d')"+
                ",__.as('d').properties('value').value().as('b')"+
                ",__.as('d').in('demands').as('w2')"+
                ").select('b','o','d')",params);

        GraphResultSet rs = session.executeGraph(s);
        List pairs = rs.all();
        logger.info("Found {} demand-offer pairs existing in the network", pairs.size());
        logger.warn("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)

        return pairs;

    }

    public List allPathsCentralized(Integer similarityThreshold) {
        logger.warn("Centralized search of all paths in the network");
        def start = System.currentTimeMillis();
        Map params = new HashMap();
        params.put("similarityThreshold", similarityThreshold);

        SimpleGraphStatement s = new SimpleGraphStatement(
                "g.V().has(label,'work').as('source')"+
                ".until(outE('demands').inV().has(label,'item').bothE('similarity').has('similarity',gte(similarityThreshold)).count().is(0))"+
                ".repeat(__.outE('demands').inV().as('a').has(label,'item')"+
                  ".bothE('similarity').has('similarity',gte(similarityThreshold)).bothV().as('b').where('a',neq('b'))"+
                  ".inE('offers').outV().has(label,'work')).simplePath().path()"+
                  ".where(count(local).is(neq(1)))",params);

        GraphResultSet rs = session.executeGraph(s);
        List paths = rs.all().collect{it.asPath().getObjects()}
        logger.warn("Found {} paths",paths);
        logger.warn("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)

        return paths
    }

    // this query mysteriously does not work -- looks like something is wrong with the type of 'v', as sometimes it works and sometimes not.

    public List allSimilarPairsCentralized() {
        logger.warn("Centralized search of connected demand-offer pairs with perfect similarities in the network");
        def start = System.currentTimeMillis();

        SimpleGraphStatement s = new SimpleGraphStatement(
                "g.V().match("+
                "__.as('g').has(label,'work').out('offers').as('o').properties('value').value().as('v')"+
                ",__.as('g').has(label,'work').out('demands').as('d').properties('value').value().as('v')"+
                ").select('o','d','v')");

        GraphResultSet rs = session.executeGraph(s);
        List pairs = rs.all();
        logger.info("Found {} demand-offer pairs existing in the network", pairs.size());
        logger.warn("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)

        return pairs;
    }

    public List allWorkItemEdges(String itemName) {
        logger.warn("Centralized search of work vertexes in the graph");
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
        logger.info("Found {} allWorkItemEdges of {} existing in the network", edges.size(), itemName);
        logger.warn("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)        

        return edges

    }

    public List allSimilarityEdges() {

        def start = System.currentTimeMillis();
        Map params = new HashMap();
        params.put("labelName", "similarity");

        logger.warn("Returning all similarity links");

        SimpleGraphStatement s = new SimpleGraphStatement(
                "g.E().has(label,labelName)",params);

        GraphResultSet rs = session.executeGraph(s);
        List edges = rs.all();
        logger.info("Found {} similarity Edges existing in the network", edges.size());
        logger.warn("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)

        return edges;
    }

    public void removeEdges(String edgeLabel) {
        Map params = new HashMap();
        params.put("labelName", edgeLabel);

        logger.warn("Removing all similarity links");

        SimpleGraphStatement s = new SimpleGraphStatement(
                "g.E().has(label,labelName).drop()",params);

        GraphResultSet rs = session.executeGraph(s);
        logger.info("Removed all {} edges from the graph", edgeLabel);
    }


    public Integer connectMatchingPairs(Map matchingPairs) {
      def start = System.currentTimeMillis();
      def connected = 0;
      logger.info("Connecting all matching demand offer item pairs")
      matchingPairs.each { key,value ->
         def offers = value.get('offers');
         offers.each {offerEdge -> 
            offerEdge = offerEdge.asEdge()
            logger.info("Offer edge: {}", offerEdge)
            def item1 = offerEdge.getInV();
            String item1Label = "item:"+item1.community_id+":"+item1.member_id;
            logger.info("Offer item: {}", item1)
            def demands = value.get('demands');
            demands.each{demandEdge -> 
              demandEdge = demandEdge.asEdge()
              def item2 = demandEdge.getInV();
              String item2Label = "item:"+item2.community_id+":"+item2.member_id;
              logger.info("Demand item: {}", item2);
              def similarity = Utils.calculateSimilarity(key.asString(),key.asString())
              this.connectItems(item1Label,item2Label,similarity)
              connected += 1
            }
         }
      }
      logger.info("Connected {} item pairs.", connected)
      logger.warn("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)
      return connected
    }

    public Edge connectItems(String item1Label, String item2Label, Object similarity) {
      def start = System.currentTimeMillis();
      Map params = new HashMap();
      params.put("item1", item1Label);
      params.put("item2",item2Label);
      params.put('edgeLabel','similarity');
      params.put('valueKey','similarity');
      params.put('valueName',similarity);

      logger.warn("Creating similarity edge from item {} to item {} with value {}", params.item1, params.item2, similarity)

      SimpleGraphStatement s = new SimpleGraphStatement(
              "def v1 = g.V(item1).next()\n" +
              "def v2 = g.V(item2).next()\n" +
              "def e = v1.addEdge(edgeLabel, v2)\n"+
              "e.property(valueKey,valueName)\n"+
              "e", params);

      GraphResultSet rs = session.executeGraph(s);
      def similarityEdge = rs.one().asEdge();
      logger.info("Added similarity edge {} to the network", similarityEdge);
      logger.warn("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)

      return similarityEdge;
    }

    public Vertex createAgent() {
        Map params = new HashMap();
        params.put("labelValue", "agent");
        return createVertex(params)
    }

    private Vertex createVertex(Map params) {
        def start = System.currentTimeMillis();

        GraphResultSet rs = session.executeGraph(new SimpleGraphStatement("g.addV(label, labelValue)", params));
        def vertex = rs.one().asVertex();

        logger.warn("Created a new {} with id {}", vertex.getLabel(), vertex.getId());
        logger.warn("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)
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
        logger.warn("Creating knows edge from agent {} to agent {}", params.agent1, params.agent2)

        SimpleGraphStatement s = new SimpleGraphStatement(
                "def v1 = g.V(agent1).next()\n" +
                "def v2 = g.V(agent2).next()\n" +
                "v1.addEdge(edgeLabel, v2)", params)

        GraphResultSet rs = session.executeGraph(s);
        def edge = rs.one().asEdge();
        logger.info("Added knows edge {} to the network", edge);
        logger.warn("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)

        return edge;
    }

}
