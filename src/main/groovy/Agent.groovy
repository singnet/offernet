//@Grab(group='com.datastax.cassandra', module='dse-driver', version='1.1.1')
//@Grab(group='log4j', module='log4j', version='1.2.17')

package net.vveitas.offernet

import com.datastax.driver.dse.DseCluster;
import com.datastax.driver.dse.DseSession;

import com.datastax.driver.dse.graph.GraphStatement;
import com.datastax.driver.dse.graph.SimpleGraphStatement;
import com.datastax.driver.dse.graph.GraphResultSet
import com.datastax.driver.dse.graph.GraphOptions

import com.datastax.driver.dse.graph.Vertex
import com.datastax.driver.dse.graph.Edge

import org.apache.log4j.PropertyConfigurator
import org.slf4j.Logger
import org.slf4j.LoggerFactory

public class Agent  {
    private Vertex vertex;
	  private DseSession session;
    private Logger logger;


	public Agent(DseSession session) {
        def start = System.currentTimeMillis();
        def config = new ConfigSlurper().parse(new File('configs/log4j-properties.groovy').toURL())
        PropertyConfigurator.configure(config.toProperties())
        logger = LoggerFactory.getLogger('Agent.class');

		    this.session= session;

        Map params = new HashMap();
        params.put("labelValue", "agent");

        GraphResultSet rs = session.executeGraph(new SimpleGraphStatement("g.addV(label, labelValue)", params));
        this.vertex = rs.one().asVertex();

        logger.warn("Created a new {} with id {}", vertex.getLabel(), vertex.getId());
        logger.warn("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)
	}

  public Agent(Object vertexId, DseSession session) {
      def start = System.currentTimeMillis();
      def config = new ConfigSlurper().parse(new File('configs/log4j-properties.groovy').toURL())
      PropertyConfigurator.configure(config.toProperties())
      logger = LoggerFactory.getLogger('OfferNet.class');

      this.session= session;

      Map params = new HashMap();
      params.put("vertexId",vertexId);

      GraphResultSet rs = session.executeGraph(new SimpleGraphStatement("g.V(vertexId)", params));
      this.vertex = rs.one().asVertex();

      logger.warn("Instantiated an {} with existing vertex id {}", vertex.getLabel(), vertex.getId());
      logger.warn("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)
  }

  /*
  * returns an id of an Agent vertex
  */
  private id() {
    return vertex.getId();
  }

  /*
  * Creates 'knows' edge between current agent and provided agent; returns that edge;
  */
	private Edge knowsAgent(Agent agent) {

        def start = System.currentTimeMillis();
        Map params = new HashMap();
        params.put("agent1", vertex.getId());
        params.put("agent2",agent.vertex.getId());
        params.put('edgeLabel','knows');

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

    private Object ownsWork(Vertex work) {
        def start = System.currentTimeMillis();
        Map params = new HashMap();
        params.put("agent", this.id());
        params.put("work",work.getId());
        params.put("edgeLabel","owns");

        logger.warn("Creating owns edge from agent {} to work {}", params.agent, params.process)

        SimpleGraphStatement s = new SimpleGraphStatement(
                "def v1 = g.V(agent).next()\n" +
                "def v2 = g.V(work).next()\n" +
                "v1.addEdge(edgeLabel, v2)", params)

        GraphResultSet rs = session.executeGraph(s);
        def edge = rs.one().asEdge();
        logger.info("Added {} edge {} to the network", params.edgeLabel, edge);
        logger.warn("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)

        return edge;
    }


    private Vertex ownsWork() {
        return this.ownsWork(Utils.generateBinaryString(Parameters.parameters.binaryStringLength),Utils.generateBinaryString(Parameters.parameters.binaryStringLength));
    }

    /*
    * Creates new work for an agent and returns it as Vertex
    */
    private Vertex ownsWork(String demandValue, String offerValue) {

        def start = System.currentTimeMillis();

        Map params = new HashMap();
        params.put("labelValue", "work");
        params.put("agent", this.id());
        params.put("edgeLabel","owns");

        logger.warn("Creating new work for agent {}", params.agent)

        SimpleGraphStatement s = new SimpleGraphStatement(
                "def v1 = g.V(agent).next()\n" +
                "def v2 = g.addV(label, labelValue).next()\n" +
                "v1.addEdge(edgeLabel, v2)\n" +
                "v2", params)

        GraphResultSet rs = session.executeGraph(s);
        logger.warn("Executed statement: {}", Utils.getStatement(rs));
        logger.warn("Execution warnings from the server: {}", Utils.getWarnings(rs));
        Vertex work = rs.one().asVertex();

        logger.warn("Created a new {} with id {}", work.getLabel(), work.getId());
        logger.warn("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)

        this.addItemToWork("demands",work,demandValue);
        this.addItemToWork("offers",work,offerValue);
        return work;
    }

    public Vertex addItemToWork(String labelName, Vertex work, Vertex item) {
        def start = System.currentTimeMillis();
        Map params = new HashMap();
        params.put("thisVertex", work.getId());
        params.put("edgeLabel", (String) labelName);
        params.put("targetVertex",item.getId());

        logger.info("Adding {}:{} to work:{}", params.edgeLabel, params.targetVertex, params.thisVertex);

        SimpleGraphStatement s = new SimpleGraphStatement(
                "def v1 = g.V(thisVertex).next()\n" +
                "def v2 = g.V(targetVertex).next()\n" +
                "v1.addEdge(edgeLabel, v2)", params);

        GraphResultSet rs = session.executeGraph(s);
        def edge = rs.one().asEdge();
        logger.info("Added {} edge {} to the network", labelName, edge);
        logger.warn("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)

      return item;
    }

    public Vertex addItemToWork(String labelName, Vertex work) {
        return this.addItemToWork(labelName, work, Utils.generateBinaryString(Parameters.parameters.binaryStringLength));
    }


    public Vertex addItemToWork(String labelName, Vertex work, String value) {
        def start = System.currentTimeMillis();
        Map params = new HashMap();
        params.put("labelValue","item");
        params.put("thisVertex", work.getId());
        params.put("edgeLabel", (String) labelName);
        params.put("propertyKey", "value");
        params.put("propertyValue", value);

        logger.info("Adding {}:{} to work:{}", params.edgeLabel, value, params.thisVertex);

        SimpleGraphStatement s = new SimpleGraphStatement(
                "def v1 = g.V(thisVertex).next()\n" +
                "def v2 = g.addV(label, labelValue).property(propertyKey ,propertyValue).next()\n" +
                "v1.addEdge(edgeLabel, v2)\n"+
                "v2", params);

        GraphResultSet rs = session.executeGraph(s);
        def item = rs.one().asVertex();
        logger.info("Added item {}:{} to the network", labelName,item);
        logger.warn("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)

      return item;
    }

    /*
    * returns all works of the current agent;
    */
    private List<Vertex> getWorks() {

        def start = System.currentTimeMillis();
        Map params = new HashMap();
        params.put("agent", this.id());
        params.put("edgeLabel","owns");

        logger.warn("Getting all works owned by agent {}",params.agent);

        SimpleGraphStatement s = new SimpleGraphStatement("g.V(agent).out(edgeLabel)",params);

        GraphResultSet rs = session.executeGraph(s);
        List<Vertex> works = rs.all().collect {it.asVertex()};
        logger.info("Retrieved works list {} from agent {}", works.toString(),this.id());
        logger.warn("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)

        return works;
    }

    /*
    * returns all items of the works of the current agent;
    */
    private List<Vertex> allItems() {

      def start = System.currentTimeMillis();
      Map params = new HashMap();
      params.put("agentLabelName", this.id());

      logger.warn("Getting all items of agent {}", this.id())

      SimpleGraphStatement s = new SimpleGraphStatement(
            "g.V(agentLabelName).outE('owns').inV().outE().inV().has(label,'item')", params)

      GraphResultSet rs = session.executeGraph(s);
      List items = rs.all().collect {it.asVertex() };
      logger.info("Returned {} items of agent {}", items.size(), this.id());
      logger.warn("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)
      
      return items;
    }

    public List<Vertex> getWorksItems(Vertex work, String labelName) {
      logger.warn("Retrieving {} from work {}",labelName,work);
      def start = System.currentTimeMillis();

      Map params = new HashMap();
      params.put("thisVertex", work.getId());
      params.put("edgeLabel", labelName);

      SimpleGraphStatement s = new SimpleGraphStatement("g.V(thisVertex).out(edgeLabel)",params);
      GraphResultSet rs = session.executeGraph(s);
      logger.warn("Executed statement: {}", Utils.getStatement(rs));
      logger.warn("Execution warnings from the server: {}", Utils.getWarnings(rs));

      List<Vertex> items = rs.all().collect {it.asVertex()};
      logger.info("Retrieved {} list {} from process {}", labelName, items.toString(),work.getId());
      logger.warn("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)

      return items;
    }

    private Integer searchAndConnect(Integer similarityThreshold, Integer maxReachDistance) {
      def start = System.currentTimeMillis();
      logger.warn('Search and connect all items of agent {} with its known agents at similarity {}', this.id(), maxReachDistance)
      def totalConnectionsCreated = 0;
      this.allItems().each {item ->
        def itemsOfKnownAgents = this.itemsOfKnownAgents(maxReachDistance);
        def similarityEdges = this.connectAllSimilar(item,itemsOfKnownAgents,similarityThreshold);
        logger.warn("Found and connected {} similar items to the item {}",similarityEdges.size(),item.getId())
        totalConnectionsCreated=totalConnectionsCreated+similarityEdges.size();
      }
      logger.warn("Created {} new similarity connections for agent {}", totalConnectionsCreated, this.id())
      logger.warn("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)
      return totalConnectionsCreated;
    }

    private List<Vertex> itemsOfKnownAgents(Integer maxReachDistance) {
      def start = System.currentTimeMillis()
      Map params = new HashMap();
      params.put("thisAgent", this.id());
      params.put("repeats", maxReachDistance);

      logger.warn("Getting a list of all connected items of agent {} with loop {}", this.id(), maxReachDistance)

      SimpleGraphStatement s = new SimpleGraphStatement(
            "g.V(thisAgent).as('s').repeat("+
              "both('knows').has(label,'agent')).times(repeats).emit().dedup().as('t')"+
              ".where('t',neq('s')).out('owns').out()",params);

      GraphResultSet rs = session.executeGraph(s);
      List items = rs.all().collect {it.asVertex() };
      logger.info("Returned {} items with maxReachDistance {} from item {}", items.size(), maxReachDistance, this.id());
      logger.info("Method {} complete time: {} seconds", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)
      return items;
    }

    private List connectAllSimilar(Vertex item, List<Vertex> itemsOfKnownAgents, Integer similarityThreshold) {
        def start = System.currentTimeMillis()
        def similarityEdges = [];
        itemsOfKnownAgents.each {knownItem ->
            def edge = this.connectIfSimilar(item,knownItem,similarityThreshold)
            if (edge != null) {similarityEdges.add(edge)}
        }
        logger.info("Added {} similarity Edges to graph", similarityEdges.size());
        logger.info("Method {} complete time: {} seconds", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)
        return similarityEdges;
    }

    private Edge connectIfSimilar(Vertex item, Vertex knownItem, Integer similarityThreshold) {
      def start = System.currentTimeMillis()
      def similarityEdge = null;
      if (this.existsSimilarity(item,knownItem) == -1) {
        def similarity = Utils.calculateSimilarity(item,knownItem);
        logger.warn("The similarity between items {} and {} is {}", item.getId(),knownItem.getId(),similarity);
        if (similarity >= similarityThreshold) {
            logger.warn("similarity {}  >= similarityThreshold {}, therefore connecting", similarity, similarityThreshold)
            similarityEdge = this.reciprocalDistanceLink(item,knownItem,similarity)
        } else {
           logger.warn("similarity {} < similarityThreshold {}, therefore not connecting", similarity, similarityThreshold)
        }
      }
      logger.info("Method {} complete time: {} seconds", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)
      return similarityEdge;
  }

  private Integer existsSimilarity(Vertex item, Vertex anotherItem) {
    def start = System.currentTimeMillis();
    logger.info("Checking if explicit similarity link exists between from {} to {}",item.getId(),anotherItem.getId())
    List similarityList = []
    this.similarityEdges(item).each { outEdge ->
        if (outEdge.getInV() == anotherItem.getId()) {
          similarityList.add(outEdge);
          logger.info("Found similarity link {}",outEdge)
        }
    }
    def similarity = similarityList.isEmpty()!= true ? Utils.edgePropertyValueAsInteger(similarityList[0],'similarity') : -1;
    logger.info("Retrieved similarity value {} between item {} and {}",similarity,item.getId(),anotherItem.getId())
    logger.warn("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)
    return similarity;
  }

  private List<Edge> similarityEdges(Vertex item) {
    def start = System.currentTimeMillis()
    Map params = new HashMap();
    params.put("thisItem", item.getId());

    SimpleGraphStatement s = new SimpleGraphStatement(
          "g.V(thisItem).outE('similarity')", params)

    GraphResultSet rs = session.executeGraph(s);
    List similarityEdges = rs.all().collect {it.asEdge()};
    logger.info("Found {} items with explicit similarity from item {}",similarityEdges.size(),this.id());
    logger.warn("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)

    return similarityEdges;

  }


  private Edge reciprocalDistanceLink(Vertex item, Vertex knownItem, Integer similarity) {
     // every similarity edge created also triggers the creation of reciprocal edge with same parameters
     this.connect(knownItem,item,similarity);
     return this.connect(item,knownItem, similarity);
  }

  private Object connect(Vertex item, Vertex knownItem, Integer similarity) {
    def start = System.currentTimeMillis();
    Map params = new HashMap();
    params.put("item1", item.getId());
    params.put("item2",knownItem.getId());
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


}
