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

public class Item  {
    private Vertex vertex;
    private DseSession session;
    private Logger logger;

	private Item(DseSession session) {
    this(Utils.generateBinaryString(16),session)
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

  private Object getValue() {
    return this.vertex.getProperty("value").getValue();
  }

  private List itemsOfKnownAgents(Integer maxDistance) {
    Map params = new HashMap();
    params.put("thisItem", this.id());
    params.put("repeats", repeats);

    logger.warn("Getting a list of all connected items of item {} with loop {}", this.id(), repeats)

    SimpleGraphStatement s = new SimpleGraphStatement(
          "g.V(thisItem).as('thisItem').in().has(label,'work').in('owns').repeat(both('knows').has(label,'agent')).times(repeats).dedup()", params)

    GraphResultSet rs = session.executeGraph(s);
    List items = rs.all().collect {it.asVertex() };
    logger.info("Returned {} items with maxDistance {} from item {}", items.size(), maxDistance, this.id());

    return items;
  }

  private Integer existsDistance(Item anotherItem) {
    Map params = new HashMap();
    params.put("thisItem", this.id());
    params.put("anotherItem", item.id());

    logger.warn("Retrieving esplicit (recorded to graph) distance link between items {} and {}", this.id(), anotherItem)

    SimpleGraphStatement s = new SimpleGraphStatement("g.V(thisItem).union("+
                              ".outE('similar').as('e1').where(_.as('e1').inV(),eq(g.V(anotherItem))),"+
                              ".inE('similar').as('e2').where(_.as('e2').outV(),eq(g.V(anotherItem)))).dedup()", params)

    GraphResultSet rs = session.executeGraph(s);
    def distance = -1
    List results = rs.all()
    if (! results.isEmpty()) {distanceEdge = rs.one().getProperty('value');}
    logger.info("Found explicit distance {} between {} and {}", distance, this.id(),anotherItem.id());

    return distance;

  }

  private Object connectSimilar(List itemsOfKnownAgents, Integer similarityThreshold) {
      itemsOfKnownAgents.each {knownItem ->
        if (this.existsDistance(knownItem) != -1) {
          def distance = Utils.calculateDistance(this.vertex,knownItem);
          logger.warn("The distance between items {} and {} is {}", this.vertex.id(),knownItem.id(),distance);
          if (distance !< similarityThreshold) {
              Map params = new HashMap();
              params.put("item1", this.id());
              params.put("item2",knownItem.id());
              params.put('edgeLabel','distance');
              params.put('valueKey','value');
              params.put('valueName',distance);

              logger.warn("Creating distance edge from item {} to item {} with value {}", params.item1, params.item2, param.value)


              SimpleGraphStatement s = new SimpleGraphStatement(
                      "def v1 = g.V(item1).next()\n" +
                      "def v2 = g.V(item2).next()\n" +
                      "v1.addEdge(edgeLabel, v2).property(valueKey,valueName)", params)

              GraphResultSet rs = session.executeGraph(s);
              def edge = rs.one().asEdge();
              logger.info("Added similar edge {} to the network", edge);
          }
      }
    }
    return edge;
  }



}
