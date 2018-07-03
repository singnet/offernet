package io.singularitynet.offernet

import org.apache.log4j.PropertyConfigurator
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static org.junit.Assert.*
import static org.hamcrest.CoreMatchers.instanceOf;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.BeforeClass;
import org.junit.AfterClass;

import com.datastax.driver.dse.graph.GraphStatement;
import com.datastax.driver.dse.graph.SimpleGraphStatement;
import com.datastax.driver.dse.graph.GraphResultSet
import com.datastax.driver.dse.graph.GraphOptions
import com.datastax.driver.dse.graph.GraphNode
import com.datastax.driver.dse.DseCluster;
import com.datastax.driver.dse.DseSession;

import com.datastax.dse.graph.api.DseGraph;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource
import org.apache.tinkerpop.gremlin.structure.Direction
import org.apache.tinkerpop.gremlin.structure.Vertex

import org.apache.log4j.PropertyConfigurator
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.text.SimpleDateFormat;

import akka.actor.Props
import akka.actor.ActorSystem;
import akka.actor.ActorRef;

import akka.testkit.TestActorRef
import akka.testkit.JavaTestKit;

import akka.pattern.Patterns;
import scala.concurrent.Future;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;
import akka.util.Timeout;

import kamon.Kamon;
import kamon.prometheus.PrometheusReporter;
import kamon.jaeger.JaegerReporter;

import groovy.json.JsonSlurper;
import org.json.JSONArray

public class SimulationTests {
		static ActorSystem system = ActorSystem.create("SimulationTests");
		static private Logger logger;

		@BeforeClass
		static void initLogging() {
		    def config = new ConfigSlurper().parse(new File('configs/log4j-properties.groovy').toURL())
	        PropertyConfigurator.configure(config.toProperties())
    	    logger = LoggerFactory.getLogger('SimulationTests.class');
    	    if (Parameters.parameters.visualizationEngine) {
    	    	Kamon.addReporter(new PrometheusReporter());
      			Kamon.addReporter(new JaegerReporter());
      		}
		}

		@Test
		void createSimulationTest() {
			def sim = TestActorRef.create(system, Simulation.props()).underlyingActor();
			assertNotNull(sim);
			sim.on.flushVertices();
			assertThat(sim, instanceOf(Simulation.class))
		}

		@Test
		void createAgentTest() {
			def sim = TestActorRef.create(system, Simulation.props()).underlyingActor();
			def actorRef = sim.createAgent();
			assertThat(actorRef, instanceOf(ActorRef.class));
			def vertexId = sim.actorRefToVertexIdTable.get(actorRef)
			assertNotNull(vertexId)
			logger.info("Created agent with actorRef {} and vertexId {}", actorRef, vertexId);
		}

		@Test
		void getAgentVertexIdTest() {
			def sim = TestActorRef.create(system, Simulation.props()).underlyingActor();
			assertNotNull(sim);
			sim.on.flushVertices();

			def agent1Ref = TestActorRef.create(system, Agent.props(sim.on.session, UUID.randomUUID().toString()));
			def agent1 = agent1Ref.underlyingActor();
			def vertexIdViaObject = agent1.vertexId()
			def vertexIdViaMessage = sim.getAgentVertexId(agent1Ref)
			logger.info("Got agents {} vertexId {} via message {}", agent1, vertexIdViaObject, vertexIdViaMessage)
			assertEquals(vertexIdViaObject, vertexIdViaMessage)
		}
		
		@Test
		void createAgentNetworkTest() {
			def sim = TestActorRef.create(system, Simulation.props()).underlyingActor();
			assertNotNull(sim);
			sim.on.flushVertices();
			int size = 20
			def agentList = sim.createAgentNetwork(size)
			assertEquals(agentList.size(), size)
			def agentNumber = sim.on.getVertices('agent').size();
			assertEquals(agentNumber, size);
			def knowsEdgeNumber = sim.on.getEdges('knows').size();
			assertEquals(knowsEdgeNumber, size-1);
		}

		@Test
		void createAgentNetworkWithChainTest() {
			def sim = TestActorRef.create(system, Simulation.props()).underlyingActor();
			assertNotNull(sim);
			sim.on.flushVertices();
			int size = 20

			def chainLength = 4
			def chains = [Utils.createChain(chainLength)]
			def chain = chains[0]
			logger.warn("Created chain to add to the network: {}", chain)

			def agentList = sim.createAgentNetwork(size)
			assertEquals(agentList.size(), size)

		    def chainedWorks = sim.addChainToNetwork(chains[0], true)  // add chain to network and return json structure...
      		//assertEquals(chainedWorks.size(),chainLength)
			def agentNumber = sim.on.getVertices('agent').size();
			assertEquals(agentNumber, size);
			def knowsEdgeNumber = sim.on.getEdges('knows').size();
			assertEquals(knowsEdgeNumber, size-1);
			def itemNumber = sim.on.getVertices('item').size();
			
		}


		@Test
		void createAgentLineTest() {
			def sim = TestActorRef.create(system, Simulation.props()).underlyingActor();
			assertNotNull(sim);
			sim.on.flushVertices();
			int size = 20
			def agentList = sim.createAgentLine(size)
			assertEquals(agentList.size(), size)
			def agentNumber = sim.on.getVertices('agent').size();
			assertEquals(agentNumber, size);
			def knowsEdgeNumber = sim.on.getEdges('knows').size();
			assertEquals(knowsEdgeNumber, size-1);
		}


		//	@Ignore // for now -- takes too much time
		@Test
		void cycleSearchTest() {
			def sim = TestActorRef.create(system, Simulation.props()).underlyingActor();
			assertNotNull(sim);
			sim.on.flushVertices();
			
			def chains = [Utils.createChain(3)]
			logger.warn("Created chain to add to the network: {}", chains[0])

			def agentList = sim.createAgentNetwork(5,0,chains);
			logger.warn("added agent network with agents: {}", agentList)			
			def similarityThreshold = Parameters.parameters.similarityThreshold
			def maxDistance = 2;
			def connections = sim.connectIfSimilarForAllAgents(agentList,similarityThreshold,maxDistance);
			Thread.sleep(1000);
			logger.warn("Created {} similarity connections of all agents with similarity {} and maxDistance {}", connections, similarityThreshold, maxDistance)

			/*

			def cutoffValue = 2;
			def similarityConstraint = Parameters.parameters.binaryStringLength;
			def paths = []
			agentList.each { agent ->
				List works = agent.getWorks();
				works.collect{ new Work(it,sim.on.session) }.each {work -> 
					paths.add(work.cycleSearch(cutoffValue,similarityConstraint))
				}
			}
			*/
		}

		@Test
		void pathSearchManualDecentralizedTest() {
			def sim = TestActorRef.create(system, Simulation.props()).underlyingActor();
			assertNotNull(sim);
			sim.on.flushVertices();
			sim.testNetworkSmallWithCycle();

			def start = System.currentTimeMillis();

    	    def similarityThreshold = 4;
    	    def maxDistance = 3;
    	    logger.info("made {} connections on agent {} with similarityThreshold {} and maxDistance {}",sim.agentList[0].searchAndConnect(similarityThreshold,maxDistance),sim.agentList[0].id(),similarityThreshold, maxDistance)
    	    logger.info("made {} connections on agent {} with similarityThreshold {} and maxDistance {}",sim.agentList[1].searchAndConnect(similarityThreshold,maxDistance),sim.agentList[1].id(),similarityThreshold, maxDistance)
    	    logger.info("made {} connections on agent {} with similarityThreshold {} and maxDistance {}",sim.agentList[2].searchAndConnect(similarityThreshold,maxDistance),sim.agentList[2].id(),similarityThreshold, maxDistance)
 	       	logger.info("made {} connections on agent {} with similarityThreshold {} and maxDistance {}",sim.agentList[3].searchAndConnect(similarityThreshold,maxDistance),sim.agentList[3].id(),similarityThreshold, maxDistance)

 	       	def connectedPairs = sim.on.allConnectedSimilarPairsCentralized(4);
 	       	logger.info("Calculated allConnectedSimilarPairs: {}",connectedPairs.size())
 	       	def similarPairs = sim.on.allSimilarPairsCentralized();
 	       	logger.info("Calculated allSimilarityPairs (not necessarily connected) --  (query does not work): {}",similarPairs.size())
 	       	def similarityEdges = sim.on.allSimilarityEdges().size() / 2;
 	       	def allSimilarityLinks = similarityEdges.toInteger();
 	       	//assertEquals("similarityLinks is not correct",2,allSimilarityLinks);
 	       	//assertEquals("similarityLinks is not equal to allConectedSimilarPairs",connectedPairs.size(),allSimilarityLinks)
 	       	
 	       	def cutoffValue = 2;
 	       	def uniquePaths = [] as Set;
 	       	sim.agentList.subList(0,2).each{ agent -> 
 	       		def path = agent.pathSearch(agent.getWorks()[0],cutoffValue,similarityThreshold)
 	       		logger.info("Found {} paths from agent {}",path.size(),agent.id())
 	       		uniquePaths.add(path)
 	       	}
 	       	logger.warn("Found uniquePaths: {}", uniquePaths.size())
           	def methodName = Utils.getCurrentMethodName();
           	logger.warn("Method {} took {} seconds to complete", methodName, (System.currentTimeMillis()-start)/1000)

           	def index = 0;
           	String dirname = new SimpleDateFormat("MMddhhmmss").format(new Date());
           	//new File("resources/"+Utils.getCurrentMethodName()+dirname).mkdir();
 	       	uniquePaths.each {path -> 
 	       		index +=1;
 	       		Utils.convertToCyNotation(path,"Path","temp/path"+index+".dot");
 	       	}
		}

		@Ignore // does not look as something with better than centralizedPathSearch
		@Test
		void pathSearchManualCentralizedTest() {
			def sim = TestActorRef.create(system, Simulation.props()).underlyingActor();
			assertNotNull(sim);
			sim.on.flushVertices();
			createSmallTestNetwork(sim);

			def start = System.currentTimeMillis();

    	    def similarityThreshold = 4;
    	    def maxDistance = 2;
    	    logger.info("made {} connections on agent {} with similarityThreshold {} and maxDistance {}",sim.agentList[0].searchAndConnect(similarityThreshold,maxDistance),sim.agentList[0].id(),similarityThreshold, maxDistance)
    	    logger.info("made {} connections on agent {} with similarityThreshold {} and maxDistance {}",sim.agentList[1].searchAndConnect(similarityThreshold,maxDistance),sim.agentList[1].id(),similarityThreshold, maxDistance)
    	    logger.info("made {} connections on agent {} with similarityThreshold {} and maxDistance {}",sim.agentList[2].searchAndConnect(similarityThreshold,maxDistance),sim.agentList[2].id(),similarityThreshold, maxDistance)
 	       	logger.info("made {} connections on agent {} with similarityThreshold {} and maxDistance {}",sim.agentList[3].searchAndConnect(similarityThreshold,maxDistance),sim.agentList[3].id(),similarityThreshold, maxDistance)

			def demandEdges = sim.on.allWorkItemEdges("demands");
			def offerEdges = sim.on.allWorkItemEdges("offers")

			def matchingOfferDemandPairs = Utils.getMatchingOfferDemandPairs(offerEdges,demandEdges)
			def similarityConnectionsCentralized = sim.on.connectMatchingPairs(matchingOfferDemandPairs);

			logger.warn("Created {} similarity connections of all agents with similarity {} and maxDistance {}", similarityThreshold, maxDistance);
			
			def uniquePaths = [] as Set;
			def paths = sim.on.allPathsCentralized(similarityThreshold);
			uniquePaths.addAll(paths);

 	       	logger.warn("Found uniquePaths: {}", uniquePaths.size())
 	       	def methodName = Utils.getCurrentMethodName();
           	logger.warn("Method {} took {} seconds to complete", methodName, (System.currentTimeMillis()-start)/1000)

           	def index = 0;

           	String dirname = new SimpleDateFormat("MMddhhmmss").format(new Date());
           	//new File("resources/"+Utils.getCurrentMethodName()+dirname).mkdir();
 	       	uniquePaths.each {path -> 
 	       		index +=1;
 	       		Utils.convertToDotNotation(path,"Path","temp/path"+index+".dot");
 	       	}
		}

		@Test
		void decentralizedPathSimulationTest() {
			/* run test with parameters: */
			def agentNumber = 6 // number of agents in the network
			def chainLength = agentNumber -2 // the length of the chain to drop into the network;
			def randomWorksNumber = 4 // number of random works (outside chain) to drop into the network;
			def maxDistance = 4; // the maximum number of hops when doing decentralized similarity search;
			def similaritySearchThreshold = 0.99 // consider only items that are this similar when searching for path;
	       	def cutoffValue = 4; // maximum number of hops when doing path search;

	       	// create simulation object
			def sim = TestActorRef.create(system, Simulation.props()).underlyingActor();
			assertNotNull(sim);
			sim.on.flushVertices();
			
			// create agent network and put some random works into it
			def agentList = sim.createAgentNetwork(agentNumber);
			logger.warn("added agent network with agents: {}", agentList)			
			sim.addRandomWorksToAgents(randomWorksNumber)

			// create chain and assign its items to random agents
			def chains = [Utils.createChain(chainLength)]
			def chain = chains[0]
			logger.warn("Created chain to add to the network: {}", chain)
			
			def chainedWorksJson = sim.addChainToNetwork(chain, true)  // add chain to network and return json structure...

      		// Connect similar items in the network (similarity > than similarityThreshold in parameters)
			logger.warn("Running decentralized similarity search and connect")
			def start = System.currentTimeMillis();
			def similarityConnectThreshold = Parameters.parameters.similarityThreshold
			
			def similarityConnectionsDecentralized = sim.connectIfSimilarForAllAgents(agentList,similarityConnectThreshold,maxDistance);
			logger.warn("Created {} similarity connections of all agents with similarity {} and maxDistance {}", similarityConnectThreshold, maxDistance);
			logger.warn("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)
			
			// Search for path -- results should include the chain that was previously created
			logger.warn("Running decentralized PathSearch")
			start = System.currentTimeMillis();	
			
 	       	def uniquePaths = [] as Set;
 	       	def agentPaths;
 	       	agentList.each{ agent -> 
 	       		agentPaths = [];
 	       		logger.warn("Getting all works of an agent {}", agent)
			    Method msg = new Method("getWorks", new ArrayList());
			    Timeout timeout = new Timeout(Duration.create(5, "seconds"));
			   	Future<Object> future = Patterns.ask(agent, msg, timeout);
		  		List works = (List<Vertex>) Await.result(future, timeout.duration());
		  		assertNotNull(works);
		  		logger.warn("Retrieved {} works of agent {}", works.size(), agent)
		  		works.each { work ->
	 	       		logger.warn("Running decentralized PathSearch from work's {} perspective", work)
				    msg = new Method("pathSearch", new ArrayList(){{add(work);add(cutoffValue);add(similaritySearchThreshold)}});
				    timeout = new Timeout(Duration.create(120, "seconds"));
				   	future = Patterns.ask(agent, msg, timeout);
			  		List path = (List<GraphNode>) Await.result(future, timeout.duration());
		  			assertNotNull(path);
	 	       		logger.info("Found path {} from work {}",path,work)
	 	       		if (path.size()!=0) {agentPaths.add(path)}
	 	       	}
	 	       	logger.info("Found {} paths from agent {} perspective", agentPaths.size(), agent)
	 	       	uniquePaths.addAll(agentPaths)
 	       	}
	      	def jsonSlurper = new JsonSlurper()
    	  	def uniquePathsJson = jsonSlurper.parseText(uniquePaths.toString());

 	       	logger.warn("Found {} uniquePaths: {}", uniquePathsJson.size(), uniquePaths)
           	logger.warn("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)

           	def allPaths = getVerticesBelongingToSubgraph(uniquePathsJson, sim)
           	// if visualization is set on, produce the properly formated json of the path (and save on disk for later usage)
           	if (Parameters.parameters.reportMode) {
				generateCYFileForEachPath(allPaths)           	
      		}

      		// all paths found should contain the previously created chain
      		def pathsContainingChain = 0;
      		allPaths.each { uniquePathJson ->
      			boolean containsChain =  Utils.pathContainsChain(uniquePathJson, chainedWorksJson)
      			int contains = containsChain ? 1 : 0;
      			pathsContainingChain = pathsContainingChain + contains
      		}
      		logger.info("Found {} paths containing the chain", pathsContainingChain)
      		assertTrue(pathsContainingChain > 0);

		}

		@Test
		void pathContainsChainTest() {
			/* run test with parameters: */
			def agentNumber = 10 // number of agents in the network
			def chainLength = agentNumber -2 // the length of the chain to drop into the network;
			def randomWorksNumber = 10 // number of random works (outside chain) to drop into the network;
			def maxDistance = 6; // the maximum number of hops when doing decentralized similarity search;
			def similaritySearchThreshold = 0.99 // consider only items that are this similar when searching for path;
	       	def cutoffValue = 5; // maximum number of hops when doing path search;

	       	// create simulation object
			def sim = TestActorRef.create(system, Simulation.props()).underlyingActor();
			assertNotNull(sim);
			sim.on.flushVertices();
			
			// create agent network and put some random works into it
			def agentList = sim.createAgentNetwork(agentNumber);
			logger.warn("added agent network with agents: {}", agentList)			
			sim.addRandomWorksToAgents(randomWorksNumber)

			// create chain and assign its items to random agents
			def chains = [Utils.createChain(chainLength)]
			def chain = chains[0]
			logger.warn("Created chain to add to the network: {}", chain)
			
			def chainedWorksJson = sim.addChainToNetwork(chain, true)  // add chain to network and return json structure...

      		// Connect similar items in the network (similarity > than similarityThreshold in parameters)
			logger.warn("Running decentralized similarity search and connect")
			def start = System.currentTimeMillis();
			def similarityConnectThreshold = Parameters.parameters.similarityThreshold
			
			def similarityConnectionsDecentralized = sim.connectIfSimilarForAllAgents(agentList,similarityConnectThreshold,maxDistance);
			logger.warn("Created {} similarity connections of all agents with similarity {} and maxDistance {}", similarityConnectThreshold, maxDistance);
			logger.warn("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)
			
			// Search for path -- results should include the chain that was previously created
			logger.warn("Running decentralized PathSearch")
			start = System.currentTimeMillis();	
			
 	       	def uniquePaths = [] as Set;
 	       	def agentPaths;
 	       	agentList.each{ agent -> 
 	       		agentPaths = [];
 	       		logger.warn("Getting all works of an agent {}", agent)
			    Method msg = new Method("getWorks", new ArrayList());
			    Timeout timeout = new Timeout(Duration.create(5, "seconds"));
			   	Future<Object> future = Patterns.ask(agent, msg, timeout);
		  		List works = (List<Vertex>) Await.result(future, timeout.duration());
		  		assertNotNull(works);
		  		logger.warn("Retrieved {} works of agent {}", works.size(), agent)
		  		works.each { work ->
	 	       		logger.warn("Running decentralized PathSearch from work's {} perspective", work)
				    msg = new Method("pathSearch", new ArrayList(){{add(work);add(cutoffValue);add(similaritySearchThreshold)}});
				    timeout = new Timeout(Duration.create(120, "seconds"));
				   	future = Patterns.ask(agent, msg, timeout);
			  		List path = (List<GraphNode>) Await.result(future, timeout.duration());
		  			assertNotNull(path);
	 	       		logger.info("Found path {} from work {}",path,work)
	 	       		if (path.size()!=0) {agentPaths.add(path)}
	 	       	}
	 	       	logger.info("Found {} paths from agent {} perspective", agentPaths.size(), agent)
	 	       	uniquePaths.addAll(agentPaths)
 	       	}
	      	def jsonSlurper = new JsonSlurper()
    	  	def uniquePathsJson = jsonSlurper.parseText(uniquePaths.toString());

 	       	logger.warn("Found {} uniquePaths: {}", uniquePathsJson.size(), uniquePaths)
           	logger.warn("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)

           	def allPaths = getVerticesBelongingToSubgraph(uniquePathsJson, sim)

           	// all paths found should contain the previously created chain (one or more)
      		def pathsContainingChain = 0;
      		allPaths.each { uniquePathJson ->
      			boolean containsChain =  Utils.pathContainsChain(uniquePathJson, chainedWorksJson)
      			int contains = containsChain ? 1 : 0;
      			pathsContainingChain = pathsContainingChain + contains
      		}
      		logger.info("Found {} paths containing the chain", pathsContainingChain)
      		assertTrue(pathsContainingChain > 0);

           	// creating chain which will now be checked -- so the unique path should not contain it...
 			def chainsNoAdd = [Utils.createChain(chainLength)]
			def chainNoAdd = chainsNoAdd[0]
			logger.warn("Created chain NOT to add to the network: {}", chainNoAdd)
			def chainedNoAddWorksJson = sim.addChainToNetwork(chainNoAdd, true) // this is a bit stupid, but have to get the correct format...

          	// but they should NOT contain the chain that was not added...
      		def pathsContainingChainNoAdd = 0;
      		allPaths.each { uniquePathJson ->
      			boolean containsChainNoAdd =  Utils.pathContainsChain(uniquePathJson, chainedNoAddWorksJson)
      			int contains = containsChainNoAdd ? 1 : 0;
      			pathsContainingChainNoAdd = pathsContainingChainNoAdd + contains
      		}
      		logger.info("Found {} paths containing the chainNoAdd", pathsContainingChainNoAdd)
      		assertFalse(pathsContainingChainNoAdd > 0);


		}		

		List getVerticesBelongingToSubgraph(Object subgraphs,Simulation sim) {
			/*
			subgraph dontains only edges
			but we want to have both edges and vertices
			*/
			def uniquePaths = []
			//logger.info("subgraphs {} class is {}",subgraphs, subgraphs.getClass())
			def sbgsIterator = subgraphs.iterator()
			while (sbgsIterator.hasNext()) {
				def subgraph = sbgsIterator.next();
				logger.info("subgraph  is {}",subgraph)
				JSONArray uniquePath = new JSONArray()
				subgraph.each { edge ->
					JSONArray singleChain = new JSONArray()
					singleChain.put(edge)
					logger.info("Getting vertexes of the edge {}",edge)
					def inV = Utils.formatVertexLabel(edge.id.get('~in_vertex'))
					logger.info("Getting vertex {}",inV)
					def vertexIn = sim.on.getVertex(inV)
					logger.info('got vertexIn {}',vertexIn)
					def outV = Utils.formatVertexLabel(edge.id.get('~out_vertex'))
					logger.info("Getting vertex {}",outV)
					def vertexOut = sim.on.getVertex(outV)
					logger.info('got vertexOut {}',vertexOut)
					singleChain.put(vertexIn[0])
					singleChain.put(vertexOut[0])
					uniquePath.put(singleChain)
				}
				logger.info("formed a uniquePath with edges and vertices {}",uniquePath)
				uniquePaths.add(uniquePath)
			}
			logger.info("subgraph enriched by vertices: {}", uniquePaths)
			return uniquePaths

		}

		void generateCYFileForEachPath(Object uniquePaths) {
			String dirname = new SimpleDateFormat("MMddhhmmss").format(new Date());
           	def methodName = Utils.getCurrentMethodName()
           	new File("temp/"+methodName+dirname).mkdir();
			def index = 0;
 	       	uniquePaths.each {path -> 
 	       		index +=1;
 	       		def pathName = "temp/"+methodName+dirname+"/path"+index+".dot"
 	       		Utils.convertToCYNotation(path,pathName);
 	       		logger.info("Wrote file to {}",pathName);
 	       	}
		}

		@Test
		void centralizedPathSimulationTest() {
			/* run test with parameters: 
			when comparing with decentralized counterpart obviously has to run with the same parameters...
			*/
			def agentNumber = 6 // number of agents in the network
			def chainLength = agentNumber -2 // the length of the chain to drop into the network;
			def randomWorksNumber = 4 // number of random works (outside chain) to drop into the network;
			def similaritySearchThreshold = 0.99 // consider only items that are this similar when searching for path;
	       	def cutoffValue = 4; // maximum number of hops when doing path search;

	       	// create simulation object
			def sim = TestActorRef.create(system, Simulation.props()).underlyingActor();
			assertNotNull(sim);
			sim.on.flushVertices();
			
			// create agent network and put some random works into it
			def agentList = sim.createAgentNetwork(agentNumber);
			logger.warn("added agent network with agents: {}", agentList)			
			sim.addRandomWorksToAgents(randomWorksNumber)

			// create chain and assign its items to random agents
			def chains = [Utils.createChain(chainLength)]
			def chain = chains[0]
			logger.warn("Created chain to add to the network: {}", chain)
			
			def chainedWorksJson = sim.addChainToNetwork(chain, true)  // add chain to network and return json structure...

			// here things start to be different from decentralized version...
      		// Connect similar items in the network (similarity > than similarityThreshold in parameters)
      		// by simply getting ALL items in the network and checking mutual similarities
			logger.warn("Running centralized similarity search and connect")
			def start = System.currentTimeMillis();

			def allItems = sim.on.getVertices('item');
			def similarityConnectThreshold = Parameters.parameters.similarityThreshold
			
			def similarityConnectionsCentralized = sim.on.connectAllSimilarCentralized(allItems,similarityConnectThreshold);
			logger.warn("Created {} similarity connections of all agents with similarity {}", similarityConnectionsCentralized.size(),similarityConnectThreshold);
			logger.warn("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)
			
			// Search for path -- results should include the chain that was previously created
			logger.warn("Running centralized PathSearch")
			start = System.currentTimeMillis();	

			def uniquePaths = [] as Set;
			def paths = sim.on.allPathsCentralized(similaritySearchThreshold);
			uniquePaths.addAll(paths);

			def jsonSlurper = new JsonSlurper()
    	  	def uniquePathsJson = jsonSlurper.parseText(uniquePaths.toString());

 	       	logger.warn("Found {} uniquePaths: {}", uniquePathsJson.size(), uniquePaths)
           	logger.warn("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)

           	def allPaths = getVerticesBelongingToSubgraph(uniquePathsJson, sim)
           	// if visualization is set on, produce the properly formated json of the path (and save on disk for later usage)
           	if (Parameters.parameters.reportMode) {
				generateCYFileForEachPath(allPaths)           	
      		}

      		// all paths found should contain the previously created chain
      		def pathsContainingChain = 0;
      		allPaths.each { uniquePathJson ->
      			boolean containsChain =  Utils.pathContainsChain(uniquePathJson, chainedWorksJson)
      			int contains = containsChain ? 1 : 0;
      			pathsContainingChain = pathsContainingChain + contains
      		}
      		logger.info("Found {} paths containing the chain", pathsContainingChain)
      		assertTrue(pathsContainingChain > 0);

		}

		@Ignore
		/* old one -- delete after above starts to work */
		@Test
		void centralizedPathSimulationTestOld() {
			def sim = TestActorRef.create(system, Simulation.props()).underlyingActor();
			assertNotNull(sim);
			sim.on.flushVertices();
			
			def chainLength = 4
			def chains = [Utils.createChain(chainLength)]
			logger.warn("Created chain to add to the network: {}", chains[0])

			def agentList = sim.createAgentNetwork(chainLength+2,0,chains);
			logger.warn("added agent network with agents: {}", agentList)			

			logger.warn("Running centralized similarity search")
			def start = System.currentTimeMillis();
			def similarityThreshold = Parameters.parameters.binaryStringLength

			def demandEdges = sim.on.allWorkItemEdges("demands");
			def offerEdges = sim.on.allWorkItemEdges("offers")

			def matchingOfferDemandPairs = Utils.getMatchingOfferDemandPairs(offerEdges,demandEdges)
			def similarityConnectionsCentralized = sim.on.connectMatchingPairs(matchingOfferDemandPairs);

			logger.warn("Created {} similarity connections of all agents with similarity {}", similarityConnectionsCentralized, similarityThreshold);
			
			def uniquePaths = [] as Set;
			def paths = sim.on.allPathsCentralized(similarityThreshold);
			uniquePaths.addAll(paths);

 	       	logger.warn("Found uniquePaths: {}", uniquePaths.size())
 	       	def methodName = Utils.getCurrentMethodName();
           	logger.warn("Method {} took {} seconds to complete", methodName, (System.currentTimeMillis()-start)/1000)

           	def index = 0;

           	Thread.sleep(5000) // pause until everything gets done in the graph
           	String dirname = new SimpleDateFormat("MMddhhmmss").format(new Date());
           	//new File("resources/"+Utils.getCurrentMethodName()+dirname).mkdir();
 	       	uniquePaths.each {path -> 
 	       		index +=1;
 	       		Utils.convertToDotNotation(path,"Path","temp/path"+index+".dot");
 	       	}
		}
	

		@Test
		void compareCentralizedAndDecentralizedSimilaritySearchTest() {
			def sim = TestActorRef.create(system, Simulation.props()).underlyingActor();
			assertNotNull(sim);
			sim.on.flushVertices();
			
			def chainLength = 5
			def chains = [Utils.createChain(chainLength)]
			logger.warn("Created chain to add to the network: {}", chains[0])

			def agentList = sim.createAgentNetwork(chainLength+2,0,chains);
			logger.warn("added agent network with agents: {}", agentList)			

			logger.warn("Running decentralized similarity search")
			def timeStart = System.currentTimeMillis();
			def similarityThreshold = Parameters.parameters.binaryStringLength
			def maxDistance = 3;
			sim.connectIfSimilarForAllAgents(agentList,similarityThreshold,maxDistance);
			def timeDecentralized = System.currentTimeMillis() - timeStart;
			def similarityConnectionsDecentralized = sim.on.getEdges('similarity')
			logger.warn("Decentralized search time (sec): {}",timeDecentralized/1000)

			sim.on.removeEdges('similarity');

			logger.warn("Running centralized similarity search")
			timeStart = System.currentTimeMillis();
			def demandEdges = sim.on.allWorkItemEdges("demands");
			def offerEdges = sim.on.allWorkItemEdges("offers")

			def matchingOfferDemandPairs = Utils.getMatchingOfferDemandPairs(offerEdges,demandEdges)
			def similarityConnectionsCentralized = sim.on.connectMatchingPairs(matchingOfferDemandPairs);

			def timeCentralized = System.currentTimeMillis() - timeStart;
			logger.warn("Centralized search time (sec): {}",timeCentralized/1000)
			assertEquals(similarityConnectionsDecentralized,similarityConnectionsCentralized)

		}

		@Ignore // this test fails therefore I do not use fluent API for now (does not give much advantage over DSEGraph API)
		@Test
		void fluentAPITest() {
    	    def on = new OfferNet();
    	    on.flushVertices();

	   	    def agent1 = new Agent(on.session);
	   	    agent1.ownsWork();

	   	    on = null;

			DseCluster dseCluster = DseCluster.builder().addContactPoint("dse-server.host").build();
			DseSession dseSession = dseCluster.connect();

			GraphTraversalSource g = DseGraph.traversal(dseSession, new GraphOptions().setGraphName("offernet"));

			// Now you can use the Traversal source and use it **as if** it was working against a local graph, and with the usual TinkerPop API. All the network is done transparently.
			Vertex agent = g.V().hasLabel("agent").next();
			println agent
			Vertex work = agent.edges(Direction.OUT,'owns').next()
			println work
			
		}

		private Integer numsimilarityEdgesNotLessSimilar(Integer similarityConstraint) {
			Map params = new HashMap();
			params.put("similarityConstraint", similarityConstraint);

			SimpleGraphStatement s = new SimpleGraphStatement(
						"g.E().has(label,'similarity').has('similarity',gte(similarityConstraint).count()",params);
			GraphResultSet rs = session.executeGraph(s);

			return rs.one().object;
		}

		@Test
		void createAgentNetworkPathGraphTest() {
			def sim = TestActorRef.create(system, Simulation.props()).underlyingActor();
			assertNotNull(sim);
			sim.on.flushVertices();

			sim.createAgentNetworkFromNetworkXDataFile("graphs/data/pathGraph50.dat");
			assertEquals(50,sim.on.getVertices('agent').size());
			assertEquals(49*2,sim.on.getEdges('knows').size());
		}

		@Test
		void createAgentNetworkCycleGraphTest() {
			def sim = TestActorRef.create(system, Simulation.props()).underlyingActor();
			assertNotNull(sim);
			sim.on.flushVertices();

			sim.createAgentNetworkFromNetworkXDataFile("graphs/data/cycleGraph50.dat");
			assertEquals(50,sim.on.getVertices('agent').size());
			assertEquals(50*2,sim.on.getEdges('knows').size());
		}

		@Test
		void createAgentNetworkSmallWorldTest() {
			def sim = TestActorRef.create(system, Simulation.props()).underlyingActor();
			assertNotNull(sim);
			sim.on.flushVertices();

			sim.createAgentNetworkFromNetworkXDataFile("graphs/data/smallWorld50.dat");
			assertEquals(50,sim.on.getVertices('agent').size());
		}

		@Test
		void createAgentNetworkPreferentialAttachmentGraphTest() {
			def sim = TestActorRef.create(system, Simulation.props()).underlyingActor();
			assertNotNull(sim);
			sim.on.flushVertices();

			sim.createAgentNetworkFromNetworkXDataFile("graphs/data/preferentialAttachment50x3.dat");
			assertEquals(50,sim.on.getVertices('agent').size());
		}

		@Test
		void createAgentNetworkConnectedStarsTest() {
			def sim = TestActorRef.create(system, Simulation.props()).underlyingActor();
			assertNotNull(sim);
			sim.on.flushVertices();

			sim.createAgentNetworkConnectedStars(sim.on.createAgent(),3,3);
			assertEquals(sim.on.getVertices("agent").size(),connectedStarsNumber(3,3))
		}

		private Integer connectedStarsNumber(Integer radius, Integer branchingFactor) {
			def number = 0;
			for (int i = 0; i<=radius; i++) {
				number = number + branchingFactor ** i;
			}
			logger.info("Calculated connectedStars number={} for radius={}, branchingFactor={}",number,radius,branchingFactor)
			return number;
		}

		@Test
		void addRandomWorksToAgentsTest() {
			def sim = TestActorRef.create(system, Simulation.props()).underlyingActor();
			sim.on.flushVertices();

			sim.createAgentNetwork(20)
			def itemNo = sim.on.getIds("agent").size()
			assertEquals(20,itemNo); // creates two items (demand and offer) when creating a random work;			
			sim.addRandomWorksToAgents(20)
			// since the above is asynchronous and works via messages, we have to wait some time until it does the job...
			// it is a bit lousy as probably has to be implemented with futures...
			sleep(2000)
			itemNo = sim.on.getIds("item").size()
			assertEquals(40,itemNo); // creates two items (demand and offer) when creating a random work;
		}

}
