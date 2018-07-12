package io.singularitynet.offernet

import com.datastax.driver.dse.DseCluster;
import com.datastax.driver.dse.DseSession;

import com.datastax.driver.dse.graph.GraphStatement;
import com.datastax.driver.dse.graph.GraphNode;
import com.datastax.driver.dse.graph.SimpleGraphStatement;
import com.datastax.driver.dse.graph.GraphResultSet
import com.datastax.driver.dse.graph.GraphOptions

import com.datastax.driver.dse.graph.Vertex
import com.datastax.driver.dse.graph.Edge

import org.apache.log4j.PropertyConfigurator
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import akka.actor.UntypedAbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.Creator;
import groovy.json.JsonSlurper;

//import java.util.UUID;

public class Agent extends UntypedAbstractActor {
    private Vertex vertex;
	  private DseSession session;
    private Logger logger;

  static Props props(DseSession session, String agentId) {
    return Props.create(new Creator<Agent>() {
      @Override
      public Agent create() throws Exception {
        return new Agent(session,agentId);
      }
    });
  }

  public void onReceive(Object message) throws Exception {
    logger.debug("{} : {} : agent {} received message: {} of {}",
      Utils.getCurrentMethodName(),
      Parameters.parameters.simulationId,
      this.id(),
      message,
      message.getClass())
  
    if (message instanceof Method) {
      switch (message) {
        default: 
          def args = message.args
          def reply = this."$message.name"(*args)
          getSender().tell(reply,getSelf());
          break;
      }
    }
  }

  /**
  * Agent constructor returning a new agent by creating a vertex in the graph
  * if a vertex with the given UUID exists - connect this vertex to the newly created actor
  * UUID is shared between graph identifier (agentId) and actor identifier (path)
  * @param session the DSE graph session for communication with the graph
  * @return Agent class instance;
  * @author kabir@singularitynet.io
  */

	public Agent(DseSession session, String agentId) {

        def start = System.currentTimeMillis();
        def config = new ConfigSlurper().parse(new File('configs/log4j-properties.groovy').toURL())
        PropertyConfigurator.configure(config.toProperties())
        logger = LoggerFactory.getLogger('Agent.class');

        this.session= session;

        Map params = new HashMap();
        params.put("labelValue", "agent");
        params.put("agentId",agentId);
        params.put("agentIdLabel","agentId")

        GraphResultSet rs = session.executeGraph(new SimpleGraphStatement(
          "if (g.V().has(agentIdLabel,agentId).toList().size() == 0)\n"+ 
            "g.addV(label, labelValue).property(agentIdLabel,agentId)\n"+
          "else\n"+
            "g.V().has(agentIdLabel,agentId)", params));
        this.vertex = rs.one().asVertex();

        if (Parameters.parameters.visualizationEngine) {
          this.emitNewVertexEvent(this.vertex)
        }

        logger.info('{} : {} : [agentId={}] : wallTime_ms={} msec.', 
          Utils.getCurrentMethodName(), 
          Parameters.parameters.simulationId,
          agentId,
          (System.currentTimeMillis()-start))
	}

  private emitNewVertexEvent(Vertex vertex) {
      Map vertexProperties = [id:vertex.getId(),label:vertex.getLabel()]
      def event = Utils.createEvent("newVertex",vertexProperties);
      def socketWriter = getContext().actorSelection("/user/SocketWriter");
      socketWriter.tell(new Method("writeSocket",[event]),ActorRef.noSender());
  }

  private emitNewEdgeEvent(Edge edge) {
      Map edgeProperties = [id:edge.getId(),inV:edge.getInV(),outV: edge.getOutV(),label:edge.getLabel().toString()]
      logger.trace("edge properties are: {}",edgeProperties.toString());
      def event = Utils.createEvent("newEdge",edgeProperties);
      def socketWriter = getContext().actorSelection("/user/SocketWriter");
      socketWriter.tell(new Method("writeSocket",[event]),ActorRef.noSender());
  }

  /**
  * returns the agentId property on the vertex, which is the unique id (is also the actor name in akka system)
  * need to rename into something more intuitive -- agentId
  */
  private String id() {
    return vertex.getProperty("agentId").getValue().asString();
  }

  /**
  * returns the agentId property on the vertex, which is the unique id in the graph
  */
  private Object vertexId() {
    return vertex.getId();
  }

  /*
  * Creates 'knows' edge between current agent and provided agent; returns that edge;
  * Takes vertex Id as parameter (depreciated?)
  * Rewrite this method to take agentId as a parameter -- may need refactoring
  */
	private Edge knowsAgent(Object id) {

        def start = System.currentTimeMillis();
        Map params = new HashMap();
        params.put("agent1", vertex.getId());
        params.put("agent2",id);
        params.put('edgeLabel','knows');

        logger.trace("Creating knows edge from agent {} to agent {}", params.agent1, params.agent2)


        SimpleGraphStatement s = new SimpleGraphStatement(
                "def v1 = g.V(agent1).next()\n" +
                "def v2 = g.V(agent2).next()\n" +
                "v1.addEdge(edgeLabel, v2)", params)

        GraphResultSet rs = session.executeGraph(s);
        def edge = rs.one().asEdge();
        
        if (Parameters.parameters.visualizationEngine) {
          emitNewEdgeEvent(edge);
        }
 
        logger.info('{} : {} : [agent={}; knows_agent={}] : wallTime_ms={} msec.', 
          Utils.getCurrentMethodName(), 
          Parameters.parameters.simulationId,
          this.id(),
          id,
          (System.currentTimeMillis()-start))

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
        params.put("agent", this.vertexId());
        params.put("edgeLabel","owns");

        logger.trace("Creating new work for agent {}", params.agent)

        SimpleGraphStatement s = new SimpleGraphStatement(
                "def v1 = g.V(agent).next()\n" +
                "def v2 = g.addV(label, labelValue).next()\n" +
                "def edge = v1.addEdge(edgeLabel, v2)\n" +
                "[edge,v2]", params)

        GraphResultSet rs = session.executeGraph(s);
        logger.trace("Executed statement: {}", Utils.getStatement(rs));
        logger.trace("Execution warnings from the server: {}", Utils.getWarnings(rs));
        ArrayList result = (ArrayList) rs.all();
        Edge edge = result[0].asEdge();
        logger.trace("Added {} edge {} to the network", params.edgeLabel, edge);
        Vertex work = result[1].asVertex();

        if (Parameters.parameters.visualizationEngine) {
          emitNewVertexEvent(work);
          Thread.sleep(100)
          emitNewEdgeEvent(edge);
        }

        logger.info('{} : {} : [agent={};  edge={}; demand={}; offer={}] : wallTime_ms={} msec.', 
          Utils.getCurrentMethodName(), 
          Parameters.parameters.simulationId,
          this.id(),
          edge.getId(),
          demandValue,
          offerValue,
          (System.currentTimeMillis()-start))

        this.addItemToWork("demands",work,demandValue);
        this.addItemToWork("offers",work,offerValue);
        return work;
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

        logger.trace("Adding {}:{} to work:{}", params.edgeLabel, value, params.thisVertex);

        SimpleGraphStatement s = new SimpleGraphStatement(
                "def v1 = g.V(thisVertex).next()\n" +
                "def v2 = g.addV(label, labelValue).property(propertyKey ,propertyValue).next()\n" +
                "def edge = v1.addEdge(edgeLabel, v2)\n"+
                "[edge,v2]", params);

        GraphResultSet rs = session.executeGraph(s);
        ArrayList result = (ArrayList) rs.all();
        Edge edge = result[0].asEdge();
        logger.trace("Added {} edge {} to the network", params.edgeLabel, edge);
        Vertex item = result[1].asVertex();

        if (Parameters.parameters.visualizationEngine) {
          emitNewVertexEvent(item);
          Thread.sleep(100)
          emitNewEdgeEvent(edge);
        }

        logger.info('{} : {} : [agent={}; work={}; label={}; value={}; edge={}] : wallTime_ms={} msec.', 
          Utils.getCurrentMethodName(), 
          Parameters.parameters.simulationId,
          this.id(),
          work.getId(),
          labelName,
          value,
          edge.getId(),
          (System.currentTimeMillis()-start))

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

        SimpleGraphStatement s = new SimpleGraphStatement("g.V().has('agentId',agent).out(edgeLabel)",params);

        GraphResultSet rs = session.executeGraph(s);
        List<Vertex> works = rs.all().collect {it.asVertex()};

        logger.info('{} : {} : [agentId={}; works_count={}] : wallTime_ms={} msec.', 
          Utils.getCurrentMethodName(), 
          Parameters.parameters.simulationId,
          this.id(),
          works.size(),
          (System.currentTimeMillis()-start))

        return works;
    }

    /*
    * returns all items of the works of the current agent;
    */
    private List<Vertex> allItems() {

      def start = System.currentTimeMillis();
      Map params = new HashMap();
      params.put("agentId", this.id());
      params.put("agentIdLabel", "agentId");

      SimpleGraphStatement s = new SimpleGraphStatement(
            "g.V().has(agentIdLabel,agentId).outE('owns').inV().outE().inV().has(label,'item')", params)

      GraphResultSet rs = session.executeGraph(s);
      List items = rs.all().collect {it.asVertex() };

      logger.info('{} : {} : [agentId={}; items_count={}] : wallTime_ms={} msec.', 
          Utils.getCurrentMethodName(), 
          Parameters.parameters.simulationId,
          this.id(),
          items.size(),
          (System.currentTimeMillis()-start))
      
      return items;
    }

    public List<Vertex> getWorksItems(Vertex work, String labelName) {
      logger.trace("Retrieving {} from work {}",labelName,work);
      def start = System.currentTimeMillis();

      Map params = new HashMap();
      params.put("thisVertex", work.getId());
      params.put("edgeLabel", labelName);

      SimpleGraphStatement s = new SimpleGraphStatement("g.V(thisVertex).out(edgeLabel)",params);
      GraphResultSet rs = session.executeGraph(s);
      logger.trace("Executed statement: {}", Utils.getStatement(rs));
      logger.trace("Execution warnings from the server: {}", Utils.getWarnings(rs));

      List<Vertex> items = rs.all().collect {it.asVertex()};

      logger.info('{} : {} : [agentId={}; work={}; label={}; items_count={}] : wallTime_ms={} msec.', 
          Utils.getCurrentMethodName(), 
          Parameters.parameters.simulationId,
          this.id(),
          work.getId(),
          labelName,
          items.size(),
          (System.currentTimeMillis()-start))

      return items;
    }


    private Integer searchAndConnect(Object similarityThreshold, Integer maxReachDistance) {
      def start = System.currentTimeMillis();
      logger.trace('Search and connect all items of agent {} with its known agents at similarity {}', this.id(), maxReachDistance)
      def totalConnectionsCreated = 0;
      this.allItems().each {item ->
        def itemsOfKnownAgents = this.itemsOfKnownAgents(maxReachDistance);
        def similarityEdges = this.connectAllSimilar(item,itemsOfKnownAgents,similarityThreshold);
        totalConnectionsCreated=totalConnectionsCreated+similarityEdges.size();
      }

      logger.info('{} : {} : [agentId={}; similarityThreshold={}; maxReachDistance={}; totalConnectionsCreated={}] : wallTime_ms={} msec.', 
        Utils.getCurrentMethodName(), 
        Parameters.parameters.simulationId,
        this.id(),
        similarityThreshold,
        maxReachDistance,
        totalConnectionsCreated,
        (System.currentTimeMillis()-start))

      return totalConnectionsCreated;
    }

    /* 
    * maybe search and connect can be done more efficient by allowing to connect not only items similar to the one
    * from which a search has bee initiated, but also all other along the way
    * yet this requires to check if items of the same agent are getting connected
    * on the other hand it is not clear whether that would be beneficial or not in the long term
    * (yet it prevents current tests from passing..) 
    */
    private Integer searchAndConnect2(Object similarityThreshold, Integer maxReachDistance) {
      def start = System.currentTimeMillis();
      logger.trace('Search and connect items with similarity {} and distance {} from the perspective of agent {}', similarityThreshold, maxReachDistance, this.id())
      def totalConnectionsCreated = 0;
      def itemsToProcess = this.itemsOfKnownAgents(maxReachDistance);
      itemsToProcess.addAll(this.allItems());
      for (def i = 0; i<itemsToProcess.size();i++) {
        def item = itemsToProcess.get(i)
        def similarityEdges = this.connectAllSimilar(item,itemsToProcess.drop(i+1),similarityThreshold);
        logger.trace("Found and connected {} similar items to the item {}",similarityEdges.size(),item.getId())
        totalConnectionsCreated=totalConnectionsCreated+similarityEdges.size();
      }
      logger.trace("Created {} new similarity connections for agent {}", totalConnectionsCreated, this.id())
      logger.trace("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start))
      return totalConnectionsCreated;
    }


    private List<Vertex> itemsOfKnownAgents(Integer maxReachDistance) {
      def start = System.currentTimeMillis()
      Map params = new HashMap();
      params.put("thisAgentId", this.id());
      params.put("agentIdLabel","agentId")
      params.put("repeats", maxReachDistance);

      SimpleGraphStatement s = new SimpleGraphStatement(
            "g.V().has(agentIdLabel,thisAgentId).as('s').repeat("+
              "both('knows').has(label,'agent').dedup()).times(repeats).emit().as('t')"+
              ".where('t',neq('s')).out('owns').out()",params);

      GraphResultSet rs = session.executeGraph(s);
      logger.trace("Executed statement: {}",Utils.getStatement(rs,params));
      logger.trace("Execution warnings from the server: {}", Utils.getWarnings(rs));
      List items = rs.all().collect {it.asVertex() };
      
      logger.info('{} : {} : [agentId={}; maxReachDistance={}; items_count={}] : wallTime_ms={} msec.', 
        Utils.getCurrentMethodName(), 
        Parameters.parameters.simulationId,
        this.id(),
        maxReachDistance,
        items.size(),
        (System.currentTimeMillis()-start))

      return items;
    }

    private List connectAllSimilar(Vertex item, List<Vertex> itemsOfKnownAgents, Double similarityThreshold) {
        def start = System.currentTimeMillis()
        def similarityEdges = [];
        itemsOfKnownAgents.each {knownItem ->
            def edge = this.connectIfSimilar(item,knownItem,similarityThreshold)
            if (edge != null) {similarityEdges.add(edge)}
        }

        logger.info('{} : {} : [agentId={}; item={}; known items count={}; similarityThreshold={}; added_similarity_edges count={}] : wallTime_ms={} msec.', 
          Utils.getCurrentMethodName(), 
          Parameters.parameters.simulationId,
          this.id(),
          item.getId(),
          itemsOfKnownAgents.size(),
          similarityThreshold,
          similarityEdges.size(),
          (System.currentTimeMillis()-start))

        return similarityEdges;
    }

    private Edge connectIfSimilar(Vertex item, Vertex knownItem, Double similarityThreshold) {
      def start = System.currentTimeMillis()
      def similarityEdge = null;
      if (this.existsSimilarity(item,knownItem) == -1) {
        def similarity = Utils.calculateSimilarity(item,knownItem);
        logger.trace("The similarity between items {} and {} is {}", item.getId(),knownItem.getId(),similarity);
        if (similarity >= similarityThreshold) {
            logger.debug("similarity {}  >= similarityThreshold {}, therefore connecting", similarity, similarityThreshold)
            similarityEdge = this.reciprocalDistanceLink(item,knownItem,similarity)
        } else {
           logger.trace("similarity {} < similarityThreshold {}, therefore not connecting", similarity, similarityThreshold)
        }
      }

      logger.info('{} : {} : [agentId={}; item={}; knownItem={}; similarityThreshold={}; added_similarity_edge={}] : wallTime_ms={} msec.', 
          Utils.getCurrentMethodName(), 
          Parameters.parameters.simulationId,
          this.id(),
          item.getId(),
          knownItem.getId(),
          similarityThreshold,
          similarityEdge ? similarityEdge.getId(): null,
          (System.currentTimeMillis()-start))

      return similarityEdge;
  }

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

    logger.info('{} : {} : [agentId={}; item={}; anotherItem={}; similarity_value={}] : wallTime_ms={} msec.', 
        Utils.getCurrentMethodName(), 
        Parameters.parameters.simulationId,
        this.id(),
        item.getId(),
        anotherItem.getId(),
        similarity,
        (System.currentTimeMillis()-start))

    return similarity;
  }

  private List<Edge> getAllOutEdges(String labelName) {
    def start = System.currentTimeMillis()
    Map params = new HashMap();
    params.put("labelName", labelName);
    params.put("thisItem", this.vertexId());

    SimpleGraphStatement s = new SimpleGraphStatement(
      "g.V(thisItem).outE(labelName)", params)

    GraphResultSet rs = session.executeGraph(s);
    List outEdges = rs.all().collect {it.asEdge()};

    logger.info('{} : {} : [agentId={}; label={}; outEdges_count={}] : wallTime_ms={} msec.', 
      Utils.getCurrentMethodName(), 
      Parameters.parameters.simulationId,
      this.id(),
      labelName,
      outEdges.size(),
      (System.currentTimeMillis()-start))

    return outEdges;
  }

  private List<Edge> similarityEdges(Vertex item) {
    def start = System.currentTimeMillis()
    Map params = new HashMap();
    params.put("thisItem", item.getId());

    SimpleGraphStatement s = new SimpleGraphStatement(
          "g.V(thisItem).outE('similarity')", params)

    GraphResultSet rs = session.executeGraph(s);
    List similarityEdges = rs.all().collect {it.asEdge()};

    logger.info('{} : {} : [agentId={}; item={}; similarity_edges_count={}] : wallTime_ms={} msec.', 
      Utils.getCurrentMethodName(), 
      Parameters.parameters.simulationId,
      this.id(),
      item.getId(),
      similarityEdges.size(),
      (System.currentTimeMillis()-start))

    return similarityEdges;
  }


  private Edge reciprocalDistanceLink(Vertex item, Vertex knownItem, Object similarity) {
     // every similarity edge created also triggers the creation of reciprocal edge with same parameters
     //this.connect(knownItem,item,similarity);
     return this.connect(item,knownItem, similarity);
  }

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

    logger.info('{} : {} : [agentId={}; item={}; knownItem={}; similarity={}; similarityEdge={}] : wallTime_ms={} msec.', 
      Utils.getCurrentMethodName(), 
      Parameters.parameters.simulationId,
      this.id(),
      item.getId(),
      knownItem.getId(),
      similarity,
      similarityEdge.getId(),
      (System.currentTimeMillis()-start))

    return similarityEdge;

  }

  /**
  * starting from each work of the current agent traverses the graph via similarity links and looks for {offer,demand} matches and hopefully paths 
  * params: cutoffValue stops the search if this depth is reached; similarityConstraint - all exceeding this value is considered as similar (so allows to connect items which are not EXACTLY similar) -- ideally this should be customizable for every item individually;
  */
  private List<GraphNode> pathSearch(Vertex work, Integer cutoffValue, Object similarityConstraint) {
      def start = System.currentTimeMillis()
      def thisWorkString = Utils.formatVertexLabel(work.getId());
      logger.trace("thisWorkString = {} of {}",thisWorkString, thisWorkString.getClass())
      Map params = new HashMap();
      params.put("thisWork", thisWorkString);
      params.put("cutoffValue", cutoffValue);
      params.put("similarityConstraint", similarityConstraint);

      logger.debug("Searching for a path starting from work {}, cutoffValue {}, similarityConstraint {}", work.getId(), cutoffValue, similarityConstraint)

      String query="""
         g.V(thisWork).as('source').repeat(
                 __.outE('offers').subgraph('subGraph').inV().bothE('similarity').has('similarity',gte(similarityConstraint)).subgraph('subGraph')            // (2)
                .otherV().inE('demands').subgraph('subGraph').outV().dedup()).times(cutoffValue).cap('subGraph').next().traversal().E()
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

      logger.info('{} : {} : [agentId={}; work={}; cutoffValue={}; similarityConstraint={}; paths_count={}] : wallTime_ms={} msec.', 
        Utils.getCurrentMethodName(), 
        Parameters.parameters.simulationId,
        this.id(),
        work.getId(),
        cutoffValue,
        similarityConstraint,
        result.size(),
        (System.currentTimeMillis()-start))

      return result;
  }

  private List<GraphNode> cycleSearch(Vertex work, Object similarityConstraint) {
      def start = System.currentTimeMillis()
      Map params = new HashMap();
      logger.info('cycleSearch: Work is : {}', work)
      logger.info('cycleSearch: Work id is: {}', work.getId())
      logger.info('cycleSearch: formatted label is: {}', Utils.formatVertexLabel(work.getId()))
      params.put("thisWork", work.getId());
      params.put("similarityConstraint", similarityConstraint);

      logger.trace("Searching for a cycle starting from work {}, cutoffValue {}, similarityConstraint {}", work.getId(), similarityConstraint)

      SimpleGraphStatement s = new SimpleGraphStatement(
                "g.V(thisWork).as('source').until(eq('work')).repeat("+
                 "__.outE('offers').subgraph('subGraph').inV().bothE('similarity').has('similarity',gte(similarityConstraint)).subgraph('subGraph')"+            // (2)
                ".otherV().inE('demands').subgraph('subGraph').outV().dedup()).cap('subGraph').next().traversal().E()", params)

      GraphResultSet rs = session.executeGraph(s);
      logger.trace("Executed statement: {}",Utils.getStatement(rs,params));
      logger.trace("With parameters: {}", params);
      def result = rs.all()
      logger.trace("Graph results are exhausted {}", rs.isExhausted())
      logger.trace("Received result {}",result)

      logger.info('{} : {} : [agentId={}; work={}; similarityConstraint={}; cycles_count={}] : wallTime_ms={} msec.', 
        Utils.getCurrentMethodName(), 
        Parameters.parameters.simulationId,
        this.id(),
        work.getId(),
        similarityConstraint,
        result.size(),
        (System.currentTimeMillis()-start))

      return result;
  }

}
