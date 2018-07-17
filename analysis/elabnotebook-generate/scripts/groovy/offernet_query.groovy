@Grab(group='com.datastax.dse', module='dse-java-driver-graph', version='1.6.7')

//package io.singularitynet.offernet

import com.datastax.driver.dse.DseCluster;
import com.datastax.driver.dse.DseSession;

import com.datastax.driver.dse.graph.GraphStatement;
import com.datastax.driver.dse.graph.GraphNode;
import com.datastax.driver.dse.graph.SimpleGraphStatement;
import com.datastax.driver.dse.graph.GraphResultSet
import com.datastax.driver.dse.graph.GraphOptions

import com.datastax.driver.dse.graph.Vertex
import com.datastax.driver.dse.graph.Edge

import java.nio.file.Files

import groovy.json.JsonSlurper
import groovy.json.JsonOutput

// init time
def start = System.currentTimeMillis();

// capture parameters
def argNo = args.size();
String name = args.size() > 0 ? args[0] : ''
String simulationId = args.size() > 1 ? args[1] : ''
Map params = args.size() > 2 ? new JsonSlurper().parseText(args[2]) : [:] 

println "Running query:"
println name
println simulationId
println params.toString()

String scriptDir = new File(getClass().protectionDomain.codeSource.location.path).getParentFile().getAbsolutePath()
String queryFilePath = scriptDir+"/../gremlin/"+name+".gremlin"
new File(scriptDir+"/../../tmp/"+simulationId).mkdir()
String outFilePath = scriptDir+"/../../tmp/"+simulationId+"/"+name+".json"
String query = new File(queryFilePath).text

DseCluster cluster = null 
try {
  // init connection to the database
  cluster = DseCluster.builder().addContactPoint("dse-server.host").build();
  cluster.connect().executeGraph("system.graph('offernet').ifNotExists().create()");

  cluster = DseCluster.builder()
      .addContactPoint("dse-server.host")
      .withGraphOptions(new GraphOptions().setGraphName("offernet"))
      .build();
  def session = cluster.connect();

  //issue query
  SimpleGraphStatement s = new SimpleGraphStatement(query,params);
  GraphResultSet rs = session.executeGraph(s);
  def results = rs.one();

  new File(outFilePath).write(results.toString())

  println "...Done"
  println "got results: "+results
  println "Results written to file "+ outFilePath
  println "Time of analysis (min): "+(System.currentTimeMillis() - start)/60000

} finally {
  cluster.close()
  System.exit(0)
}
