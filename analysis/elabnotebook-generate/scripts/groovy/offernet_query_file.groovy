@Grab(group='com.datastax.dse', module='dse-java-driver-graph', version='1.6.7')

//package io.singularitynet.offernet

import com.datastax.driver.dse.DseCluster;
import com.datastax.driver.dse.DseSession;

import com.datastax.driver.dse.graph.GraphStatement;
import com.datastax.driver.dse.graph.GraphNode;
import com.datastax.driver.dse.graph.SimpleGraphStatement;
import com.datastax.driver.dse.graph.GraphResultSet
import com.datastax.driver.dse.graph.GraphOptions
import com.datastax.driver.dse.auth.DsePlainTextAuthProvider;

import com.datastax.driver.dse.graph.Vertex
import com.datastax.driver.dse.graph.Edge

import java.nio.file.Files

import groovy.json.JsonSlurper
import groovy.json.JsonOutput

import java.security.MessageDigest;

// init time
def start = System.currentTimeMillis();

// capture parameters
def argNo = args.size();
String name = args.size() > 0 ? args[0] : ''
String simulationId = args.size() > 1 ? args[1] : ''
//Map params = args.size() > 2 ? args[2] : [:] 
Map params = args.size() > 2 ? new JsonSlurper().parseText(args[2]) : [:] 

// calculate parameters hash for file naming
if (args.size() > 2 ) {
  MessageDigest digest = MessageDigest.getInstance("MD5") ;
  digest.update(args[2].bytes); 
  parHash = new BigInteger(1, digest.digest()).toString(16)
}

String scriptDir = new File(getClass().protectionDomain.codeSource.location.path).getParentFile().getAbsolutePath()
String queryFilePath = scriptDir+"/../gremlin/"+name+".gremlin"
String outFilePath = scriptDir+"/../../tmp/"+simulationId+".json"
String query = new File(queryFilePath).text

DseCluster cluster = null 

  // init connection to the database
  cluster = DseCluster.builder()
    .addContactPoint("dse-server.host")
    .withAuthProvider(new DsePlainTextAuthProvider("offernet_actor_system", "singoffcass69585"))
    .build();
  cluster.connect().executeGraph("system.graph('offernet').ifNotExists().create()");

  cluster = DseCluster.builder()
      .addContactPoint("dse-server.host")
      .withAuthProvider(new DsePlainTextAuthProvider("offernet_actor_system", "singoffcass69585"))
      .withGraphOptions(new GraphOptions().setGraphName("offernet"))
      .build();
  def session = cluster.connect();
  SimpleGraphStatement developmentMode = new SimpleGraphStatement("schema.config().option('graph.schema_mode').set('Development')")
  session.executeGraph(developmentMode)

  //issue query
  SimpleGraphStatement s = new SimpleGraphStatement(query,params);
  GraphResultSet rs = session.executeGraph(s);
  def results = rs.one().asMap();
  //def json = [ : ]

  def outFile = new File(outFilePath)
  def json = outFile.exists() ? new JsonSlurper().parseText(outFile.text) : [ : ]
  if (name.contains('degreeDistribution')) {
      name = name+params.labelValue+params.edgeLabel
//      if (! json.containsKey('degreeDistribution')) {json."$name" = [ ]}
//      json."$name".add([ params: params, data: results ]); 
  } //else {
    json."$name" = [ : ] 
    json."$name".params = params
    json."$name".data = results
//  }

  outFile.write(JsonOutput.toJson(json))

  cluster.close()
  System.exit(0)
