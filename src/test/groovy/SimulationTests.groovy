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
import com.datastax.driver.dse.graph.Vertex;
import com.datastax.driver.dse.graph.Edge;
import com.datastax.dse.graph.api.DseGraph;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource
import org.apache.tinkerpop.gremlin.structure.Direction

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

import java.util.concurrent.TimeUnit;

public class SimulationTests {
		static ActorSystem system = ActorSystem.create("SimulationTests");
		static private Logger logger;

		@BeforeClass
		static void initLogging() {
		    def config = new ConfigSlurper().parse(new File('configs/log4j-properties.groovy').toURL())
	        PropertyConfigurator.configure(config.toProperties())
    	    logger = LoggerFactory.getLogger('SimulationTests.class');

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
			logger.trace("Created agent with actorRef {} and vertexId {}", actorRef, vertexId);
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
			logger.trace("Got agents {} vertexId {} via message {}", agent1, vertexIdViaObject, vertexIdViaMessage)
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
			logger.trace("Created chain to add to the network: {}", chain)

			def agentList = sim.createAgentNetwork(size)
			assertEquals(agentList.size(), size)
			Global.parameters.reportMode=false
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
			logger.trace("Created chain to add to the network: {}", chains[0])

			def agentList = sim.createAgentNetwork(5,0,chains);
			logger.trace("added agent network with agents: {}", agentList)			
			def similarityThreshold = Global.parameters.similarityThreshold
			def maxDistance = 2;
			def connections = sim.connectIfSimilarForAllAgents(agentList,similarityThreshold,maxDistance);
			Thread.sleep(1000);
			logger.trace("Created {} similarity connections of all agents with similarity {} and maxDistance {}", connections, similarityThreshold, maxDistance)

			/*

			def cutoffValue = 2;
			def similarityConstraint = Global.parameters.binaryStringLength;
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
		void decentralizedPathSearchTest() {
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
			logger.trace("added agent network with agents: {}", agentList)			
			sim.addRandomWorksToAgents(randomWorksNumber)

			// create chain and assign its items to random agents
			def chains = [Utils.createChain(chainLength)]
			def chain = chains[0]
			logger.trace("Created chain to add to the network: {}", chain)
			
			def chainedWorksJson = sim.addChainToNetwork(chain, true)  // add chain to network and return json structure...

      		// Connect similar items in the network (similarity > than similarityThreshold in parameters)
			logger.trace("Running decentralized similarity search and connect")
			def start = System.currentTimeMillis();
			def similarityConnectThreshold = Global.parameters.similarityThreshold
			
			def similarityConnectionsDecentralized = sim.connectIfSimilarForAllAgents(agentList,similarityConnectThreshold,maxDistance);
			logger.trace("Created {} similarity connections of all agents with similarity {} and maxDistance {}", similarityConnectThreshold, maxDistance);
			logger.trace("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)
			
			// Search for path -- results should include the chain that was previously created
			logger.trace("Running decentralized PathSearch")
			start = System.currentTimeMillis();	
			
 	       	def uniquePaths = [] as Set;
 	       	def agentPaths;
 	       	agentList.each{ agent -> 
 	       		agentPaths = [];
 	       		logger.trace("Getting all works of an agent {}", agent)
			    Method msg = new Method("getWorks", new ArrayList());
			    Timeout timeout = new Timeout(Duration.create(5, "seconds"));
			   	Future<Object> future = Patterns.ask(agent, msg, timeout);
		  		List works = (List<Vertex>) Await.result(future, timeout.duration());
		  		assertNotNull(works);
		  		logger.trace("Retrieved {} works of agent {}", works.size(), agent)
		  		works.each { work ->
	 	       		logger.trace("Running decentralized PathSearch from work's {} perspective", work)
				    msg = new Method("pathSearch", new ArrayList(){{add(work);add(cutoffValue);add(similaritySearchThreshold)}});
				    timeout = new Timeout(Duration.create(120, "seconds"));
				   	future = Patterns.ask(agent, msg, timeout);
			  		List path = (List<GraphNode>) Await.result(future, timeout.duration());
		  			assertNotNull(path);
	 	       		logger.trace("Found path {} from work {}",path,work)
	 	       		if (path.size()!=0) {agentPaths.add(path)}
	 	       	}
	 	       	logger.trace("Found {} paths from agent {} perspective", agentPaths.size(), agent)
	 	       	uniquePaths.addAll(agentPaths)
 	       	}
	      	def jsonSlurper = new JsonSlurper()
    	  	def uniquePathsJson = jsonSlurper.parseText(uniquePaths.toString());

 	       	logger.trace("Found {} uniquePaths: {}", uniquePathsJson.size(), uniquePaths)
           	logger.trace("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)

           	def allPaths = getVerticesBelongingToSubgraphs(uniquePathsJson, sim)

      		// all paths found should contain the previously created chain
      		def pathsContainingChain = 0;
      		allPaths.each { uniquePathJson ->
      			def pathId = Utils.generateRandomString(6)
      			boolean containsChain =  Utils.pathContainsChain(uniquePathJson, chainedWorksJson, pathId)
      			int contains = containsChain ? 1 : 0;
      			pathsContainingChain = pathsContainingChain + contains
      		}
      		logger.trace("Found {} paths containing the chain", pathsContainingChain)
      		assertTrue(pathsContainingChain > 0);

		}

		@Test
		void pathContainsChainTest() {
			/* run test with parameters: */
			def agentNumber = 6 // number of agents in the network
			def chainLength = agentNumber -2 // the length of the chain to drop into the network;
			def randomWorksNumber = 0 // number of random works (outside chain) to drop into the network;
			def maxDistance = 6; // the maximum number of hops when doing decentralized similarity search;
			def similaritySearchThreshold = 0.99 // consider only items that are this similar when searching for path;
	       	def cutoffValue = 5; // maximum number of hops when doing path search;

	       	// create simulation object
			def sim = TestActorRef.create(system, Simulation.props()).underlyingActor();
			assertNotNull(sim);
			sim.on.flushVertices();
			
			// create agent network and put some random works into it
			def agentList = sim.createAgentNetwork(agentNumber);
			logger.trace("added agent network with agents: {}", agentList)			
			sim.addRandomWorksToAgents(randomWorksNumber)

			// create chain and assign its items to random agents
			def chains = [Utils.createChain(chainLength)]
			def chain = chains[0]
			logger.trace("Created chain to add to the network: {}", chain)
			
			def chainedWorksJson = sim.addChainToNetwork(chain, true)  // add chain to network and return json structure...

      		// Connect similar items in the network (similarity > than similarityThreshold in parameters)
			logger.trace("Running decentralized similarity search and connect")
			def start = System.currentTimeMillis();
			def similarityConnectThreshold = Global.parameters.similarityThreshold
			
			def similarityConnectionsDecentralized = sim.connectIfSimilarForAllAgents(agentList,similarityConnectThreshold,maxDistance);
			logger.trace("Created {} similarity connections of all agents with similarity {} and maxDistance {}", similarityConnectThreshold, maxDistance);
			logger.trace("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)
			
			// Search for path -- results should include the chain that was previously created
			logger.trace("Running decentralized PathSearch")
			start = System.currentTimeMillis();	
			
 	       	def uniquePaths = [] as Set;
 	       	def agentPaths;
 	       	agentList.each{ agent -> 
 	       		agentPaths = [];
 	       		logger.trace("Getting all works of an agent {}", agent)
			    Method msg = new Method("getWorks", new ArrayList());
			    Timeout timeout = new Timeout(Duration.create(5, "seconds"));
			   	Future<Object> future = Patterns.ask(agent, msg, timeout);
		  		List works = (List<Vertex>) Await.result(future, timeout.duration());
		  		assertNotNull(works);
		  		logger.trace("Retrieved {} works of agent {}", works.size(), agent)
		  		works.each { work ->
	 	       		logger.trace("Running decentralized PathSearch from work's {} perspective", work)
				    msg = new Method("pathSearch", new ArrayList(){{add(work);add(cutoffValue);add(similaritySearchThreshold)}});
				    timeout = new Timeout(Duration.create(120, "seconds"));
				   	future = Patterns.ask(agent, msg, timeout);
			  		List path = (List<GraphNode>) Await.result(future, timeout.duration());
		  			assertNotNull(path);
	 	       		logger.trace("Found path {} from work {}",path,work)
	 	       		if (path.size()!=0) {agentPaths.add(path)}
	 	       	}
	 	       	logger.trace("Found {} paths from agent {} perspective", agentPaths.size(), agent)
	 	       	uniquePaths.addAll(agentPaths)
 	       	}
	      	def jsonSlurper = new JsonSlurper()
    	  	def uniquePathsJson = jsonSlurper.parseText(uniquePaths.toString());

 	       	logger.trace("Found {} uniquePaths: {}", uniquePathsJson.size(), uniquePaths)
           	logger.trace("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)

           	def allPaths = getVerticesBelongingToSubgraphs(uniquePathsJson, sim)

           	// all paths found should contain the previously created chain (one or more)
      		def pathsContainingChain = 0;
      		allPaths.each { uniquePathJson ->
      			def pathId = Utils.generateRandomString(6)
      			boolean containsChain =  Utils.pathContainsChain(uniquePathJson, chainedWorksJson, pathId)
      			int contains = containsChain ? 1 : 0;
      			pathsContainingChain = pathsContainingChain + contains
      		}
      		logger.trace("Found {} paths containing the chain", pathsContainingChain)
      		assertTrue(pathsContainingChain > 0);

           	// creating chain which will now be checked -- so the unique path should not contain it...
 			def chainsNoAdd = [Utils.createChain(chainLength)]
			def chainNoAdd = chainsNoAdd[0]
			logger.trace("Created chain NOT to add to the network: {}", chainNoAdd)
			def chainedNoAddWorksJson = sim.addChainToNetwork(chainNoAdd, true) // this is a bit stupid, but have to get the correct format...

          	// but they should NOT contain the chain that was not added...
      		def pathsContainingChainNoAdd = 0;
      		allPaths.each { uniquePathJson ->
      			def pathId = Utils.generateRandomString(6)+"ChainNoAdd"
      			boolean containsChainNoAdd =  Utils.pathContainsChain(uniquePathJson, chainedNoAddWorksJson, pathId)
      			int contains = containsChainNoAdd ? 1 : 0;
      			pathsContainingChainNoAdd = pathsContainingChainNoAdd + contains
      		}
      		logger.trace("Found {} paths containing the chainNoAdd", pathsContainingChainNoAdd)
      		assertFalse(pathsContainingChainNoAdd > 0);


		}		

		List getVerticesBelongingToSubgraphs(Object subgraphs,Simulation sim) {
			/*
			subgraph dontains only edges
			but we want to have both edges and vertices
			*/
			def uniquePaths = []
			logger.trace("subgraphs {} class is {}",subgraphs, subgraphs.getClass())
			def sbgsIterator = subgraphs.iterator()
			while (sbgsIterator.hasNext()) {
				def subgraph = sbgsIterator.next()
				def uniquePath = sim.getVerticesBelongingToSubgraph(subgraph)
				uniquePaths.add(uniquePath)
			}
			logger.trace("subgraph enriched by vertices: {}", uniquePaths)
			return uniquePaths

		}

		void generateCYFileForEachPath(Object uniquePaths) {
 	       	uniquePaths.each {path -> 
 	       		Utils.convertToCYNotation(path,"allPaths");
 	       		//logger.trace("Wrote file to {}",pathName);
 	       	}
		}

		@Test
		void decentralizedCycleSearchTest() {
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
			logger.trace("added agent network with agents: {}", agentList)			
			sim.addRandomWorksToAgents(randomWorksNumber)

			// create chain and assign its items to random agents
			def chains = [Utils.createChain(chainLength)]
			def chain = chains[0]
			logger.trace("Created chain to add to the network: {}", chain)
			
			def chainedWorksJson = sim.addChainToNetwork(chain, true)  // add chain to network and return json structure...

			// create agent that has a work which closes the chain into the cycle
			// this agent will have the last item in he chain as demand
			// and the first item in the chain as offer
			
			def vertexIdList = new ArrayList(sim.vertexIdToActorRefTable.keySet())
			def taskAgent = sim.createAgent()
			logger.trace('Created an agent {} for performing the cycle search',taskAgent)
			def randomAgent = vertexIdList[new Random().nextInt(vertexIdList.size())]
			Method msg = new Method("knowsAgent", new ArrayList(){{add(randomAgent)}});
			Timeout timeout = new Timeout(Duration.create(5, "seconds"));
			Future<Object> future = Patterns.ask(taskAgent, msg, timeout);
		  	def knowsEdge = (Edge) Await.result(future, timeout.duration());
		  	assertNotNull(knowsEdge);
		  	logger.trace('agent {} knows agent {}', taskAgent, randomAgent)			

			msg = new Method("ownsWork", new ArrayList(){{add(chain[-1]);add(chain[0])}});
			timeout = new Timeout(Duration.create(5, "seconds"));
			future = Patterns.ask(taskAgent, msg, timeout);
		  	Vertex taskWork = (Vertex) Await.result(future, timeout.duration());
		  	assertNotNull(taskWork);
		  	logger.trace('Added work {} to agent {}', taskWork, taskAgent)			

		  	sim.decentralizedSimilaritySearchAndConnect()
			Thread.sleep(1000)
			 // test fails without above line: 
			 //it seems that connectIfSimilarForAllAgents takes a lot of time
			 // need to debug


 	       	Set agentPaths= sim.decentralizedCycleSearch(taskAgent);

			def jsonSlurper = new JsonSlurper()
	   	  	def uniquePathsJson = jsonSlurper.parseText(uniquePaths.toString());
	   	  	logger.trace("Found path (json) {} from agent {}",uniquePathsJson,taskAgent)

           	logger.trace("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)

           	def allPaths = getVerticesBelongingToSubgraphs(uniquePathsJson, sim)

      		// all paths found should contain the previously created chain
      		def pathsContainingChain = 0;
      		allPaths.each { uniquePathJson ->
      			def pathId = Utils.generateRandomString(6)
      			boolean containsChain =  Utils.pathContainsChain(uniquePathJson, chainedWorksJson, pathId)
      			int contains = containsChain ? 1 : 0;
      			pathsContainingChain = pathsContainingChain + contains
      		}
      		logger.trace("Found {} paths containing the chain", pathsContainingChain)
      		assertTrue(pathsContainingChain > 0);

		}


		@Test
		void centralizedCycleSearchTestNaive() {
			centralizedCycleSearchTest(1)
		}

		@Test
		void centralizedCycleSearchTestDepthFirstSearch() {
			centralizedCycleSearchTest(2)
		}

		void centralizedCycleSearchTest(int searchVersion) {
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
			logger.trace("added agent network with agents: {}", agentList)			
			sim.addRandomWorksToAgents(randomWorksNumber)

			// create chain and assign its items to random agents
			def chains = [Utils.createChain(chainLength)]
			def chain = chains[0]
			logger.trace("Created chain to add to the network: {}", chain)
			
			def chainedWorksJson = sim.addChainToNetwork(chain, true)  // add chain to network and return json structure...

			// create agent that has a work which closes the chain into the cycle
			// this agent will have the last item in he chain as demand
			// and the first item in the chain as offer
			
			def vertexIdList = new ArrayList(sim.vertexIdToActorRefTable.keySet())
			def taskAgent = sim.createAgent()
			logger.trace('Created an agent {} for performing the cycle search',taskAgent)
			def randomAgent = vertexIdList[new Random().nextInt(vertexIdList.size())]
			Method msg = new Method("knowsAgent", new ArrayList(){{add(randomAgent)}});
			Timeout timeout = new Timeout(Duration.create(5, "seconds"));
			Future<Object> future = Patterns.ask(taskAgent, msg, timeout);
		  	def knowsEdge = (Edge) Await.result(future, timeout.duration());
		  	assertNotNull(knowsEdge);
		  	logger.trace('agent {} knows agent {}', taskAgent, randomAgent)			

			msg = new Method("ownsWork", new ArrayList(){{add(chain[-1]);add(chain[0])}});
			timeout = new Timeout(Duration.create(5, "seconds"));
			future = Patterns.ask(taskAgent, msg, timeout);
		  	Vertex taskWork = (Vertex) Await.result(future, timeout.duration());
		  	assertNotNull(taskWork);
		  	logger.trace('Added work {} to agent {}', taskWork, taskAgent)			

			sim.centralizedSimilaritySearchAndConnect();			
			Thread.sleep(1000)
			 // test fails without above line: 
			 //it seems that connectIfSimilarForAllAgents takes a lot of time
			 // need to debug

			 //running a centralized cycle search
			def uniquePaths = sim.allCyclesCentralized(similaritySearchThreshold,searchVersion)

			def jsonSlurper = new JsonSlurper()
	   	  	def uniquePathsJson = jsonSlurper.parseText(uniquePaths.toString());
	   	  	logger.trace("Found path (json) {} from agent {}",uniquePathsJson,taskAgent)

           	logger.trace("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)

           	def allPaths = getVerticesBelongingToSubgraphs(uniquePathsJson, sim)

      		// all paths found should contain the previously created chain
      		def pathsContainingChain = 0;
      		allPaths.each { uniquePathJson ->
      			def pathId = Utils.generateRandomString(6)
      			boolean containsChain =  Utils.pathContainsChain(uniquePathJson, chainedWorksJson, pathId)
      			int contains = containsChain ? 1 : 0;
      			pathsContainingChain = pathsContainingChain + contains
      		}
      		logger.trace("Found {} paths containing the chain", pathsContainingChain)
      		assertTrue(pathsContainingChain > 0);

		}		

		@Test
		void centralizedPathSearchTest() {
			/* run test with parameters: 
			when comparing with decentralized counterpart obviously has to run with the same Global...
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
			logger.trace("added agent network with agents: {}", agentList)			
			sim.addRandomWorksToAgents(randomWorksNumber)

			// create chain and assign its items to random agents
			def chains = [Utils.createChain(chainLength)]
			def chain = chains[0]
			logger.trace("Created chain to add to the network: {}", chain)
			
			def chainedWorksJson = sim.addChainToNetwork(chain, true)  // add chain to network and return json structure...

			// here things start to be different from decentralized version...
      		// Connect similar items in the network (similarity > than similarityThreshold in parameters)
      		// by simply getting ALL items in the network and checking mutual similarities
			logger.trace("Running centralized similarity search and connect")
			def start = System.currentTimeMillis();

			def allItems = sim.on.getVertices('item');
			def similarityConnectThreshold = Global.parameters.similarityThreshold
			
			def similarityConnectionsCentralized = sim.on.connectAllSimilarCentralized(allItems,similarityConnectThreshold);
			logger.trace("Created {} similarity connections of all agents with similarity {}", similarityConnectionsCentralized.size(),similarityConnectThreshold);
			logger.trace("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)
			
			// Search for path -- results should include the chain that was previously created
			logger.trace("Running centralized PathSearch")
			start = System.currentTimeMillis();	

			def uniquePaths = [] as Set;
			def paths = sim.on.allPathsCentralized(similaritySearchThreshold,3,4);
			uniquePaths.addAll(paths);

			def jsonSlurper = new JsonSlurper()
    	  	def uniquePathsJson = jsonSlurper.parseText(uniquePaths.toString());

 	       	logger.trace("Found {} uniquePaths: {}", uniquePathsJson.size(), uniquePaths)
           	logger.trace("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)

           	def allPaths = getVerticesBelongingToSubgraphs(uniquePathsJson, sim)

      		// all paths found should contain the previously created chain
      		def pathsContainingChain = 0;
      		allPaths.each { uniquePathJson ->
      			def pathId = Utils.generateRandomString(6)
      			boolean containsChain =  Utils.pathContainsChain(uniquePathJson, chainedWorksJson, pathId)
      			int contains = containsChain ? 1 : 0;
      			pathsContainingChain = pathsContainingChain + contains
      		}
      		logger.trace("Found {} paths containing the chain", pathsContainingChain)
      		assertTrue(pathsContainingChain > 0);
		}

		@Test
		void compareCentralizedAndDecentralizedSimilaritySearchTest() {
			def sim = TestActorRef.create(system, Simulation.props()).underlyingActor();
			assertNotNull(sim);
			sim.on.flushVertices();
			
			def chainLength = 5
			def chains = [Utils.createChain(chainLength)]
			logger.trace("Created chain to add to the network: {}", chains[0])

			def agentList = sim.createAgentNetwork(chainLength+2,0,chains);
			logger.trace("added agent network with agents: {}", agentList)			

			logger.trace("Running decentralized similarity search")
			def timeStart = System.currentTimeMillis();
			def similarityThreshold = Global.parameters.binaryStringLength
			def maxDistance = 3;
			sim.connectIfSimilarForAllAgents(agentList,similarityThreshold,maxDistance);
			def timeDecentralized = System.currentTimeMillis() - timeStart;
			def similarityConnectionsDecentralized = sim.on.getEdges('similarity')
			logger.trace("Decentralized search time (sec): {}",timeDecentralized/1000)

			sim.on.removeEdges('similarity');

			logger.trace("Running centralized similarity search")
			timeStart = System.currentTimeMillis();
			def demandEdges = sim.on.allWorkItemEdges("demands");
			def offerEdges = sim.on.allWorkItemEdges("offers")

			def matchingOfferDemandPairs = Utils.getMatchingOfferDemandPairs(offerEdges,demandEdges)
			def similarityConnectionsCentralized = sim.on.connectMatchingPairs(matchingOfferDemandPairs);

			def timeCentralized = System.currentTimeMillis() - timeStart;
			logger.trace("Centralized search time (sec): {}",timeCentralized/1000)
			assertEquals(similarityConnectionsDecentralized,similarityConnectionsCentralized)

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
			logger.trace("Calculated connectedStars number={} for radius={}, branchingFactor={}",number,radius,branchingFactor)
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
