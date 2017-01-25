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

import static org.junit.Assert.*

public class Item  {
    private Vertex vertex;
    private DseSession session;
    private Logger logger;

	private Item(DseSession session) {
    this(Utils.generateBinaryString(16),session)
	}

  private Item(Vertex vertex,DseSession session) {

    def config = new ConfigSlurper().parse(new File('configs/log4j-properties.groovy').toURL())
    PropertyConfigurator.configure(config.toProperties())
    logger = LoggerFactory.getLogger('Item.class');
    this.session= session;
    this.vertex=vertex
    logger.warn("Created a new {} from known vertex {}", vertex.getLabel(), vertex.getId());

  }

  private Item(String value, DseSession session) {
      def config = new ConfigSlurper().parse(new File('configs/log4j-properties.groovy').toURL())
      PropertyConfigurator.configure(config.toProperties())
      logger = LoggerFactory.getLogger('Item.class');
      this.session= session;

      Map params = new HashMap();
      params.put("labelValue", "item");
      params.put("propertyKey", "value");
      params.put("propertyValue", value);

      GraphResultSet rs = session.executeGraph(new SimpleGraphStatement("g.addV(label, labelValue).property(propertyKey ,propertyValue)", params));
      logger.warn("Executed statement: {}", Utils.getStatement(rs));
      logger.warn("Execution warnings from the server: {}", Utils.getWarnings(rs));
      this.vertex = rs.one().asVertex();

      logger.warn("Created a new {}", this.id().toString());
  }

  private id() {
    return this.vertex.getId();
  }

  private Map getProperties() {
    Map properties = [:]
    def vertexProperties = vertex.getProperties();
    vertexProperties.each { vp ->
      properties.put(vp.getName(),vp.getValue());
    }
    return properties;
  }

  private String getValue() {
    return this.vertex.getProperty("value").getValue();
  }

  private List itemsOfKnownAgents(Integer maxDistance) {
    def start = System.currentTimeMillis()
    Map params = new HashMap();
    params.put("thisItem", this.id());
    params.put("repeats", maxDistance);

    logger.warn("Getting a list of all connected items of item {} with loop {}", this.id(), maxDistance)

    SimpleGraphStatement s = new SimpleGraphStatement(
          "g.V(thisItem).in().in('owns').as('s').repeat("+
            "both('knows').has(label,'agent')).times(repeats).emit().dedup().as('t')"+
            ".where('t',neq('s')).out('owns').out()",params);

    GraphResultSet rs = session.executeGraph(s);
    List items = rs.all().collect {it.asVertex() };
    logger.info("Returned {} items with maxDistance {} from item {}", items.size(), maxDistance, this.id());
    logger.info("Method {} complete time: {} seconds", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)
    return items;
  }

  private List distanceEdges() {

    Map params = new HashMap();
    params.put("thisItem", this.id());

    SimpleGraphStatement s = new SimpleGraphStatement(
          "g.V(thisItem).outE('distance')", params)

    GraphResultSet rs = session.executeGraph(s);
    List distanceEdges = rs.all().collect {it.asEdge()};
    logger.info("Found {} items with explicit distance from item {}",distanceEdges.size(),this.id());
    return distanceEdges;

  }

  private Integer existsDistance(Item anotherItem) {
    logger.info("Checking if explicit distance link exists between from {} to {}",this.id(),anotherItem.id())
    List distanceList = []
    this.distanceEdges().each { outEdge ->
        if (outEdge.getInV() == anotherItem.id()) {
          distanceList.add(outEdge);
          logger.info("Found distance link {}",outEdge)
        }
    }
    assertTrue(distanceList.size()<2);
    def distance = distanceList.isEmpty()!= true ? Utils.edgePropertyValueAsInteger(distanceList[0],'distance') : -1;
    logger.info("Retrieved distance value {} between item {} and {}",distance,this.id(),anotherItem.id())
    return distance;
  }

  private Object reciprocalDistanceLink(Item knownItem, Integer distance) {
     // every distance edge created also triggers the creation of reciprocal edge with same parameters
     knownItem.connect(this,distance);
     return this.connect(knownItem, distance);
  }

  private Object connect(Item knownItem, Integer distance) {
    Map params = new HashMap();
    params.put("item1", this.id());
    params.put("item2",knownItem.id());
    params.put('edgeLabel','distance');
    params.put('valueKey','distance');
    params.put('valueName',distance);

    logger.warn("Creating distance edge from item {} to item {} with value {}", params.item1, params.item2, distance)

    SimpleGraphStatement s = new SimpleGraphStatement(
            "def v1 = g.V(item1).next()\n" +
            "def v2 = g.V(item2).next()\n" +
            "def e = v1.addEdge(edgeLabel, v2)\n"+
            "e.property(valueKey,valueName)\n"+
            "e", params);

    GraphResultSet rs = session.executeGraph(s);
    def similarityEdge = rs.one().asEdge();
    logger.info("Added distance edge {} to the network", similarityEdge);

    return similarityEdge;

  }

  private Object connectIfSimilar(Item knownItem, Integer similarityThreshold) {
      def start = System.currentTimeMillis()
      def similarityEdge = null;
      if (this.existsDistance(knownItem) == -1) {
        def distance = Utils.calculateDistance(this,knownItem);
        logger.warn("The distance between items {} and {} is {}", this.id(),knownItem.id(),distance);
        if (distance <= similarityThreshold) {
            logger.warn("distance {}  <= similarityThreshold {}, therefore connecting", distance, similarityThreshold)
            similarityEdge = this.reciprocalDistanceLink(knownItem,distance)
        } else {
           logger.warn("distance {}  > similarityThreshold {}, therefore not connecting", distance, similarityThreshold)
        }
      }
      logger.info("Method {} complete time: {} seconds", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)
      return similarityEdge;
  }

  private List connectAllSimilar(List itemsOfKnownAgents, Integer similarityThreshold) {
      def start = System.currentTimeMillis()
      def similarityEdges = [];
      itemsOfKnownAgents.collect{vertex -> new Item(vertex,this.session) }.each {knownItem ->
          def edge = this.connectIfSimilar(knownItem,similarityThreshold)
          if (edge != null) {similarityEdges.add(edge)}
      }
      logger.info("Added {} similarity Edges to graph", similarityEdges.size());
      logger.info("Method {} complete time: {} seconds", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)
      return similarityEdges;
  }

  private List connectAllSimilar(Integer maxDistance, Integer similarityThreshold) {
      def start = System.currentTimeMillis()
      def similarityEdges = [];
      this.itemsOfKnownAgents(maxDistance).collect{vertex -> new Item(vertex,this.session) }.each {knownItem ->
          def edge = this.connectIfSimilar(knownItem,similarityThreshold)
          if (edge != null) {similarityEdges.add(edge)}
      }
      logger.info("Added {} similarity Edges to graph", similarityEdges.size());
      logger.info("Method {} complete time: {} seconds", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)
      return similarityEdges;
  }

  /*
  * params: cutoffValue stops the search if this depth is reached with no cycle discovered; similarityConstraint - all exceeding this value is considered as similar (so allows to connect items which are not EXACTLY similar) -- ideally this should be customizable for every item individually;
  */
  private Object cycleSearch(Integer cutoffValue, Integer similarityConstraint) {
    Map params = new HashMap();
    params.put("thisItem", this.id());
    params.put("cutoffValue", maxDistance);
    params.put("similarityConstraint", similarityConstraint);

    logger.warn("Searching for the cycle starting from item {}, cutoffValue {}, similarityConstraint {}", this.id(), cutoffValue, similarityConstraint)

    SimpleGraphStatement s = new SimpleGraphStatement(
          "g.V().has(label,'item').range(0,1).bothE('distance').where(values('distance').is(gt(6)))"
          // finished here - does not work, because something is wrong with types -- try to run from here...
          // it seems that everything recorded to the graph is a String type, therefore comparison work correctly only up to 9
          // use the length of the item value 9 for now and ask the question to the group.
          ,params);

    GraphResultSet rs = session.executeGraph(s);
    List items = rs.all().collect {it.asVertex() };
    logger.info("Returned {} items with maxDistance {} from item {}", items.size(), maxDistance, this.id());

    return items;

  }



}
