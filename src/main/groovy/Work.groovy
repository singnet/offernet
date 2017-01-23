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


public class Work  {
    private Vertex vertex;
    private DseSession session;
    private Logger logger;

	private Work(DseSession session) {

        def config = new ConfigSlurper().parse(new File('configs/log4j-properties.groovy').toURL())
        PropertyConfigurator.configure(config.toProperties())
        logger = LoggerFactory.getLogger('Work.class');
        this.session= session;

        Map params = new HashMap();
        params.put("labelValue", "work");

        GraphResultSet rs = session.executeGraph(new SimpleGraphStatement("g.addV(label, labelValue)", params));
        logger.warn("Executed statement: {}", Utils.getStatement(rs));
        logger.warn("Execution warnings from the server: {}", Utils.getWarnings(rs));
        this.vertex = rs.one().asVertex();

        logger.warn("Created a new {} with id {}", vertex.getLabel(), vertex.getId());

        this.addDemand();
        this.addOffer();
	}

  private Work(Vertex vertex,DseSession session) {

    def config = new ConfigSlurper().parse(new File('configs/log4j-properties.groovy').toURL())
    PropertyConfigurator.configure(config.toProperties())
    logger = LoggerFactory.getLogger('Work.class');
    this.session= session;
    this.vertex=vertex
    logger.warn("Created a new {} from known vertex {}", vertex.getLabel(), vertex.getId());
    
  }
    public id() {
    	return this.vertex.getId();
    }

    public Item addDemand() {
    	return addItem(new Item(this.session), 'demands');
    }

    public Item addDemand(Item item) {
    	return addItem(item, 'demands');
    }

    public List getDemands() {
    	return getItems('demands');
    }

    public Item addOffer() {
    	return addItem(new Item(session), 'offers');
    }

    public Item addOffer(Item item) {
    	return addItem(item, 'offers');
    }

    public List getOffers() {
        return getItems("offers");
    }

    public Item addItem(Item item, String labelName) {
    	Map params = new HashMap();
        params.put("thisVertex", this.id());
        params.put("edgeLabel", (String) labelName);
        params.put("targetVertex",item.id());

        logger.info("Adding {}:{} to work:{}", params.edgeLabel, params.targetVertex, params.thisVertex);

        SimpleGraphStatement s = new SimpleGraphStatement(
                "def v1 = g.V(thisVertex).next()\n" +
                "def v2 = g.V(targetVertex).next()\n" +
                "v1.addEdge(edgeLabel, v2)", params);

        GraphResultSet rs = session.executeGraph(s);
        def edge = rs.one().asEdge();
        logger.info("Added {} edge {} to the network", labelName, edge);
     	return item;
    }

    public List<Vertex> getItems(String labelName) {
    	  Map params = new HashMap();
        params.put("thisVertex", this.id());
        params.put("edgeLabel", labelName);

        SimpleGraphStatement s = new SimpleGraphStatement("g.V(thisVertex).out(edgeLabel)",params);
        GraphResultSet rs = session.executeGraph(s);
        logger.warn("Executed statement: {}", Utils.getStatement(rs));
        logger.warn("Execution warnings from the server: {}", Utils.getWarnings(rs));

        List<Vertex> items = rs.all().collect {it.asVertex()};
        logger.info("Retrieved {} list {} from process {}", labelName, items.toString(),this.id());
        return items;
    }


}
