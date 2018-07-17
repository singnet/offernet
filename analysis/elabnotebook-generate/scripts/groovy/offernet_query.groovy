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

import java.nio.file.Files


// init time
def start = System.currentTimeMillis();

// init connection to the database
def cluster = DseCluster.builder().addContactPoint("dse-server.host").build();
cluster.connect().executeGraph("system.graph('offernet').ifNotExists().create()");

cluster = DseCluster.builder()
    .addContactPoint("dse-server.host")
    .withGraphOptions(new GraphOptions().setGraphName("offernet"))
    .build();
def session = cluster.connect();

//issue queries

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


start = System.currentTimeMillis()
println "running analysis degrees.groovy"
workdir = args[0]
experimentNo = args[1]
experimentDir = workdir +"/eln/"+experimentNo;
println "experimentDir is: " + experimentDir

print "Opening graph database.."
g = TitanFactory.open(experimentDir+"/titan")
println "...Done"

//get degrees
print "Getting degree data from the graph database..."
degreesFile = new FileWriter(experimentDir+'/degrees.dat')
degreesFile<<"agent,outDegree,outWeightSum,inDegree,inWeightSum\n"
g.V('type','agent')\
	.sideEffect{degreesFile<<it.toString() +","}\
	.sideEffect{degreesFile<<it.outE('knows').inV.has('type','agent').count().toString()+","}\
	.sideEffect{degreesFile<<it.outE('knows').inV.has('type','agent').back(2).weight.sum().toString()+","}\
	.sideEffect{degreesFile<<it.inE('knows').inV.has('type','agent').count().toString()+","}\
	.sideEffect{degreesFile<<it.inE('knows').inV.has('type','agent').back(2).weight.sum().toString()+"\n"}\
	.iterate()

degreesFile.close()

println "...Done"
println "Time of analysis (min): "+(System.currentTimeMillis() - start)/60000

