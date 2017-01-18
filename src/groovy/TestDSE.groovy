@Grab(group='com.datastax.cassandra', module='dse-driver', version='1.1.1')
@Grab(group='org.apache.tinkerpop', module='gremlin-driver', version='3.2.3')

import com.datastax.driver.dse.DseGraph;
import com.datastax.driver.dse.DseCluster;
import com.datastax.driver.dse.DseSession;

import com.datastax.driver.dse.graph.GraphStatement;
import com.datastax.driver.dse.graph.SimpleGraphStatement;
import com.datastax.driver.dse.graph.GraphResultSet
import com.datastax.driver.dse.graph.GraphOptions

import org.apache.tinkerpop.gremlin.structure.*;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;

DseCluster dseCluster = null;
DseSession dseSession = null;
try {
	dseCluster = DseCluster.builder().addContactPoint("192.168.1.6").build();
	dseCluster.connect().executeGraph("system.graph('demo').ifNotExists().create()");

	dseCluster = DseCluster.builder()
        .addContactPoint("192.168.1.6")
        .withGraphOptions(new GraphOptions().setGraphName("demo"))
        .build();
    dseSession = dseCluster.connect();
    g = DseGraph.traversal(dseSession);

	Map params = [:]
	params.put("vertexLabel","testVertex");
	params.put("name","john");
	params.put("age","43");

	SimpleGraphStatement s1 = new SimpleGraphStatement("g.addV(label, vertexLabel).property('name',name).property('age',age)", params).setGraphName("demo");
	println s1.getQueryString();
	dseSession.executeGraph(s1);

	GraphStatement s2 = new SimpleGraphStatement("g.V()").setGraphName("demo");
	GraphResultSet rs = dseSession.executeGraph(s2);
	System.out.println(rs.one().asVertex());

	GraphOptions graphOptions = dseCluster.getConfiguration().getGraphOptions();
	graphOptions.setGraphName("demo");

	Vertex john = g.V().has('name','john').next();
	VertexProperty<Integer> ageProperty = john.properties("age");
	int age = ageProperty.value();
	println john.toString();
	println age

} finally {	 
	if (dseCluster != null) dseCluster.close();
}


