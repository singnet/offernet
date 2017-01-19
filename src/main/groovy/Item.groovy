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

        def config = new ConfigSlurper().parse(new File('configs/log4j-properties.groovy').toURL())
        PropertyConfigurator.configure(config.toProperties())
        logger = LoggerFactory.getLogger('Item.class');
		    this.session= session;

        Map params = new HashMap();
        params.put("labelValue", "item");
        params.put("propertyKey", "value");
        params.put("propertyValue", Utils.generateBinaryString(16));

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
}
