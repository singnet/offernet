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
//import kamon.jaeger.JaegerReporter;
import kamon.zipkin.ZipkinReporter;

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
			logger.debug("Created agent with actorRef {} and vertexId {}", actorRef, vertexId);
		}

		@Test
		void createAgentWithTickersTest() {
			def simRef = TestActorRef.create(system, Simulation.props(),'SimulationActor')
			def sim = simRef.underlyingActor()
			//ActorRef simRef = system.actorOf(Simulation.props("SimulationActor"),'SimulationActor');
			logger.debug('created SimulationActor with ActorRef {}',simRef)
			List tickers = []
			tickers.add(['ownsWork',[],200])
			// each agent initiates search and connect process per some time
			tickers.add(['searchAndConnect',[0.99,10],200])
			// each agent initiates cycle search per some time
			tickers.add(['cycleSearchRandom',[1,20],200])

			simRef.tell(new Method("createAgentWithTickers", new ArrayList(){{add(tickers)}}),simRef)
			simRef.tell(new Method("createAgentWithTickers", new ArrayList(){{add(tickers)}}),simRef)
			simRef.tell(new Method("createAgentWithTickers", new ArrayList(){{add(tickers)}}),simRef)
			simRef.tell(new Method("createAgentWithTickers", new ArrayList(){{add(tickers)}}),simRef)

			def agentNumber = sim.on.getVertices('agent').size();
			assert agentNumber == 4
		}

		@Test
		void addChainToNetworkWithTaskAgentTest() {
			def simRef = TestActorRef.create(system, Simulation.props(),'SimulationActor')
			def sim = simRef.underlyingActor();
			logger.debug('created SimulationActor with ActorRef {}',simRef)
			List tickers = []
			tickers.add(['ownsWork',[],2000])
			// each agent initiates search and connect process per some time
			tickers.add(['searchAndConnect',[0.99,10],1000])
			// each agent initiates cycle search per some time
			tickers.add(['cycleSearchRandom',[1,20],2000])

			10.times {
				simRef.tell(new Method("createAgentWithTickers", new ArrayList(){{add(tickers)}}),simRef)
			}

			Thread.sleep(900)

			assert sim.on.getVertices('agent').size() == 10
		    Method msg = new Method("allEdgesByLabel", ["allEdgesByLabel", Global.parameters.simulationId]);
		    Timeout timeout = new Timeout(Duration.create(10, "seconds"));
		   	Future<Object> future = Patterns.ask(sim.on.analyst, msg, timeout);
		   	String allEdgesByLabelString = Await.result(future, timeout.duration());
		   	def jsonSlurper = new JsonSlurper();
		   	def allEdgesByLabel = jsonSlurper.parseText(allEdgesByLabelString)
		   	assert allEdgesByLabel.get("knows") == 9
		   	assert allEdgesByLabel.size() == 1

			tickers = []
			// each agent creates some random work per some time
			tickers.add(['searchAndConnect',[0.99,10],500])
			// each agent initiates cycle search per some time
			tickers.add(['cycleSearch',[1,20],1000])

			def params = [[5,6], tickers]
	    	simRef.tell(new Method("addChainToNetworkWithTaskAgent",params),simRef)

	    	Thread.sleep(2000)
			assert sim.on.getVertices('agent').size() == 11
			msg = new Method("allEdgesByLabel", ["allEdgesByLabel", Global.parameters.simulationId]);
		    timeout = new Timeout(Duration.create(10, "seconds"));
		   	future = Patterns.ask(sim.on.analyst, msg, timeout);
		   	allEdgesByLabelString = Await.result(future, timeout.duration());
		   	allEdgesByLabel = jsonSlurper.parseText(allEdgesByLabelString)
		   	assert allEdgesByLabel.get("knows") == 10
		   	assert allEdgesByLabel.size() > 1
		   	assert allEdgesByLabel.get("similarity") > 0
		   	assert allEdgesByLabel.get("owns") > 0
		   	assert allEdgesByLabel.get("demands") > 0
		   	assert allEdgesByLabel.get("offers") > 0
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
			logger.debug("Got agents {} vertexId {} via message {}", agent1, vertexIdViaObject, vertexIdViaMessage)
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
			def knowsEdgeNumber = sim.on.getEdges('agent','knows').size();
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
			logger.debug("Created chain to add to the network: {}", chain)

			def agentList = sim.createAgentNetwork(size)
			assertEquals(agentList.size(), size)
			Global.parameters.reportMode=false
		    def chainedWorks = sim.addChainToNetwork(chains[0], true)  // add chain to network and return json structure...
      		//assertEquals(chainedWorks.size(),chainLength)
			def agentNumber = sim.on.getVertices('agent').size();
			assertEquals(agentNumber, size);
			def knowsEdgeNumber = sim.on.getEdges('agent','knows').size();
			assertEquals(knowsEdgeNumber, size-1);
			def itemNumber = sim.on.getVertices('item').size();
			
		}

		@Test
		void createAgentNetworkWithChainAndTestActorTest() {
			def sim = TestActorRef.create(system, Simulation.props()).underlyingActor();
			assertNotNull(sim);
			sim.on.flushVertices();
			int size = 20

			def agentList = sim.createAgentNetwork(size)
			assertEquals(agentList.size(), size)
			Global.parameters.reportMode=false

		    def chainedWorks = sim.addChainToNetworkWithTaskAgent([[5,10],[]])  // add chain to network and return json structure...
      		Thread.sleep(20000)

      		def agentNumber = sim.on.getVertices('agent').size();
			assertEquals(agentNumber, size+1);
			def knowsEdgeNumber = sim.on.getEdges('agent','knows').size();
			assertEquals(knowsEdgeNumber, size);
			def itemNumber = sim.on.getVertices('item').size();
			
		}


		@Test
		void createAgentNetworkWithChainAndRandomWorks() {
			
			def sim = TestActorRef.create(system, Simulation.props()).underlyingActor();
			assertNotNull(sim);
			sim.on.flushVertices();
			int size = 30

			def chainLength = 4
			def chains = [Utils.createChain(chainLength)]
			def chain = chains[0]

			def agentList = sim.createAgentNetwork(size)
			assertEquals(agentList.size(), size)
			Global.parameters.reportMode=false
		    def chainedWorks = sim.addChainToNetwork(chains[0], true)  // add chain to network and return json structure...
		    def randomWorksNumber = size * 2
		    sim.addRandomWorksToAgents(randomWorksNumber) 
      		//assertEquals(chainedWorks.size(),chainLength)
			def agentNumber = sim.on.getVertices('agent').size();
			assertEquals(agentNumber, size);
			def knowsEdgeNumber = sim.on.getEdges('agent','knows').size();
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
			def knowsEdgeNumber = sim.on.getEdges('agent','knows').size();
			assertEquals(knowsEdgeNumber, size-1);
		}


		//	@Ignore // for now -- takes too much time
		@Test
		void cycleSearchTest() {
			def sim = TestActorRef.create(system, Simulation.props()).underlyingActor();
			assertNotNull(sim);
			sim.on.flushVertices();
			
			def chains = [Utils.createChain(3)]
			logger.debug("Created chain to add to the network: {}", chains[0])

			def agentList = sim.createAgentNetwork(5,0,chains);
			logger.debug("added agent network with agents: {}", agentList)			
			def similarityThreshold = Global.parameters.similarityThreshold
			def maxDistance = 2;
			def connections = sim.connectIfSimilarForAllAgents(agentList,similarityThreshold,maxDistance);
			Thread.sleep(1000);
			logger.debug("Created {} similarity connections of all agents with similarity {} and maxDistance {}", connections, similarityThreshold, maxDistance)

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
			logger.debug("added agent network with agents: {}", agentList)			
			sim.addRandomWorksToAgents(randomWorksNumber)

			// create chain and assign its items to random agents
			def chains = [Utils.createChain(chainLength)]
			def chain = chains[0]
			logger.debug("Created chain to add to the network: {}", chain)
			
			def chainedWorksJson = sim.addChainToNetwork(chain, true)  // add chain to network and return json structure...

      		// Connect similar items in the network (similarity > than similarityThreshold in parameters)
			logger.debug("Running decentralized similarity search and connect")
			def start = System.currentTimeMillis();
			def similarityConnectThreshold = Global.parameters.similarityThreshold
			
			def similarityConnectionsDecentralized = sim.connectIfSimilarForAllAgents(agentList,similarityConnectThreshold,maxDistance);
			logger.debug("Created {} similarity connections of all agents with similarity {} and maxDistance {}", similarityConnectThreshold, maxDistance);
			logger.debug("Method {} took {} seconds to complete", 'pathContainsChainTest', (System.currentTimeMillis()-start)/1000)
			
			// Search for path -- results should include the chain that was previously created
			logger.debug("Running decentralized PathSearch")
			start = System.currentTimeMillis();	
			
 	       	def uniquePaths = [] as Set;
 	       	def agentPaths;
 	       	agentList.each{ agent -> 
 	       		agentPaths = [];
 	       		logger.debug("Getting all works of an agent {}", agent)
			    Method msg = new Method("getWorks", new ArrayList());
			    Timeout timeout = new Timeout(Duration.create(5, "seconds"));
			   	Future<Object> future = Patterns.ask(agent, msg, timeout);
		  		List works = (List<Vertex>) Await.result(future, timeout.duration());
		  		assertNotNull(works);
		  		logger.debug("Retrieved {} works of agent {}", works.size(), agent)
		  		works.each { work ->
	 	       		logger.debug("Running decentralized PathSearch from work's {} perspective", work)
				    msg = new Method("pathSearch", new ArrayList(){{add(work);add(cutoffValue);add(similaritySearchThreshold)}});
				    timeout = new Timeout(Duration.create(120, "seconds"));
				   	future = Patterns.ask(agent, msg, timeout);
			  		List path = (List<GraphNode>) Await.result(future, timeout.duration());
		  			assertNotNull(path);
	 	       		logger.debug("Found path {} from work {}",path,work)
	 	       		if (path.size()!=0) {agentPaths.add(path)}
	 	       	}
	 	       	logger.debug("Found {} paths from agent {} perspective", agentPaths.size(), agent)
	 	       	uniquePaths.addAll(agentPaths)
 	       	}
	      	def jsonSlurper = new JsonSlurper()
    	  	def uniquePathsJson = jsonSlurper.parseText(uniquePaths.toString());

 	       	logger.debug("Found {} uniquePaths: {}", uniquePathsJson.size(), uniquePaths)
           	logger.debug("Method {} took {} seconds to complete", 'pathContainsChainTest', (System.currentTimeMillis()-start)/1000)

           	def allPaths = sim.getVerticesBelongingToSubgraphs(uniquePathsJson)

           	// all paths found should contain the previously created chain (one or more)
      		def pathsContainingChain = 0;
      		allPaths.each { uniquePathJson ->
      			def pathId = Utils.generateRandomString(6)
      			boolean containsChain =  Utils.pathContainsChain(uniquePathJson, chainedWorksJson)
      			int contains = containsChain ? 1 : 0;
      			pathsContainingChain = pathsContainingChain + contains
      		}
      		logger.debug("Found {} paths containing the chain", pathsContainingChain)
      		assertTrue(pathsContainingChain > 0);

           	// creating chain which will now be checked -- so the unique path should not contain it...
 			def chainsNoAdd = [Utils.createChain(chainLength)]
			def chainNoAdd = chainsNoAdd[0]
			logger.debug("Created chain NOT to add to the network: {}", chainNoAdd)
			def chainedNoAddWorksJson = sim.addChainToNetwork(chainNoAdd, true) // this is a bit stupid, but have to get the correct format...

          	// but they should NOT contain the chain that was not added...
      		def pathsContainingChainNoAdd = 0;
      		allPaths.each { uniquePathJson ->
      			def pathId = Utils.generateRandomString(6)+"ChainNoAdd"
      			boolean containsChainNoAdd =  Utils.pathContainsChain(uniquePathJson, chainedNoAddWorksJson)
      			int contains = containsChainNoAdd ? 1 : 0;
      			pathsContainingChainNoAdd = pathsContainingChainNoAdd + contains
      		}
      		logger.debug("Found {} paths containing the chainNoAdd", pathsContainingChainNoAdd)
      		assertFalse(pathsContainingChainNoAdd > 0);


		}		

		void generateCYFileForEachPath(Object uniquePaths) {
 	       	uniquePaths.each {path -> 
 	       		Utils.convertToCYNotation(path,"allPaths");
 	       		//logger.debug("Wrote file to {}",pathName);
 	       	}
		}

		@Test
		void decentralizedPathSearchTest() {
			/* run test with parameters: */
			def agentNumber = 6 // number of agents in the network
			def chainLength = agentNumber -2 // the length of the chain to drop into the network;
			def randomWorksNumber = 4 // number of random works (outside chain) to drop into the network;
			def maxDistance = 4; // the maximum number of hops when doing decentralized similarity search;
			def similaritySearchThreshold = 1 // consider only items that are this similar when searching for path;
	       	def cutoffValue = 4; // maximum number of hops when doing path search;

	       	Global.parameters.similaritySearchThreshold = similaritySearchThreshold

	       	// create simulation object
			def sim = TestActorRef.create(system, Simulation.props()).underlyingActor();
			assertNotNull(sim);
			sim.on.flushVertices();
			
			// create agent network and put some random works into it
			def agentList = sim.createAgentNetwork(agentNumber);
			logger.debug("added agent network with agents: {}", agentList)			
			sim.addRandomWorksToAgents(randomWorksNumber)

			// create chain and assign its items to random agents
			def chains = [Utils.createChain(chainLength)]
			def chain = chains[0]
			logger.debug("Created chain to add to the network: {}", chain)
			
			def chainedWorksJson = sim.addChainToNetwork(chain, true)  // add chain to network and return json structure...

			// create agent that has a work which closes the chain into the cycle
			// this agent will have the last item in he chain as demand
			// and the first item in the chain as offer
			
			def vertexIdList = new ArrayList(sim.vertexIdToActorRefTable.keySet())
			def taskAgent = sim.createAgent()
			logger.debug('Created an agent {} for performing the cycle search',taskAgent)
			def randomAgent = vertexIdList[new Random().nextInt(vertexIdList.size())]
			Method msg = new Method("knowsAgent", new ArrayList(){{add(randomAgent)}});
			Timeout timeout = new Timeout(Duration.create(5, "seconds"));
			Future<Object> future = Patterns.ask(taskAgent, msg, timeout);
		  	def knowsEdge = (Edge) Await.result(future, timeout.duration());
		  	assertNotNull(knowsEdge);
		  	logger.debug('agent {} knows agent {}', taskAgent, randomAgent)			

			msg = new Method("ownsWork", new ArrayList(){{add(chain[-1]);add(chain[0])}});
			timeout = new Timeout(Duration.create(5, "seconds"));
			future = Patterns.ask(taskAgent, msg, timeout);
		  	Vertex taskWork = (Vertex) Await.result(future, timeout.duration());
		  	assertNotNull(taskWork);
		  	logger.debug('Added work {} to agent {}', taskWork, taskAgent)			

		  	sim.decentralizedSimilaritySearchAndConnect(maxDistance)
			Thread.sleep(1000)
			 // test fails without above line: 
			 //it seems that connectIfSimilarForAllAgents takes a lot of time
			 // need to debug
 	       	int foundPathsCount = sim.decentralizedPathSearch(taskAgent, maxDistance, chainedWorksJson);
      		logger.debug("Found {} paths containing the chain", foundPathsCount)
      		assertTrue(foundPathsCount > 0);
		}

		// this test does not check return values since individualCycleSearch is asynchronous and does not return value
		@Ignore
		@Test
		void individualCycleSearchTest() {
			/* run test with parameters: */
			def agentNumber = 50 // number of agents in the network
			def chainLength = 10 // the length of the chain to drop into the network;
			def randomWorksNumber = 50*2 // number of random works (outside chain) to drop into the network;
			def maxDistance = 50; // the maximum number of hops when doing decentralized similarity search;
			def similaritySearchThreshold = 1 // consider only items that are this similar when searching for path;

	       	Global.parameters.similaritySearchThreshold = similaritySearchThreshold
	       	Global.parameters.persistence = false

	       	// create simulation object
			def sim = TestActorRef.create(system, Simulation.props()).underlyingActor();
			assertNotNull(sim);
			
			// create agent network and put some random works into it
			def agentList = sim.createAgentNetwork(agentNumber);
			logger.debug("added agent network with agents: {}", agentList)			
			sim.addRandomWorksToAgents(randomWorksNumber)

			// create chain and assign its items to random agents
			def chains = [Utils.createChain(chainLength)]
			def chain = chains[0]
			logger.debug("Created chain to add to the network: {}", chain)
			
			def chainedWorksJson = sim.addChainToNetwork(chain, true)  // add chain to network and return json structure...

			// create agent that has a work which closes the chain into the cycle
			// this agent will have the last item in he chain as demand
			// and the first item in the chain as offer
			
			def vertexIdList = new ArrayList(sim.vertexIdToActorRefTable.keySet())
			def taskAgent = sim.createAgent()
			logger.debug('Created an agent {} for performing the cycle search',taskAgent)
			def randomAgent = vertexIdList[new Random().nextInt(vertexIdList.size())]
			Method msg = new Method("knowsAgent", new ArrayList(){{add(randomAgent)}});
			Timeout timeout = new Timeout(Duration.create(5, "seconds"));
			Future<Object> future = Patterns.ask(taskAgent, msg, timeout);
		  	def knowsEdge = (Edge) Await.result(future, timeout.duration());
		  	assertNotNull(knowsEdge);
		  	logger.debug('agent {} knows agent {}', taskAgent, randomAgent)			

			msg = new Method("ownsWork", new ArrayList(){{add(chain[-1]);add(chain[0])}});
			timeout = new Timeout(Duration.create(5, "seconds"));
			future = Patterns.ask(taskAgent, msg, timeout);
		  	Vertex taskWork = (Vertex) Await.result(future, timeout.duration());
		  	assertNotNull(taskWork);
		  	logger.debug('Added work {} to agent {}', taskWork, taskAgent)			

		  	sim.decentralizedSimilaritySearchAndConnect(maxDistance)
		  	//sim.centralizedSimilaritySearchAndConnect();
			Thread.sleep(3000)
			 // test fails without above line: 
			 //it seems that connectIfSimilarForAllAgents takes a lot of time
			 // need to debug
 	       	sim.individualCycleSearch(taskAgent, chainedWorksJson);
 	       	Thread.sleep(5000)
      		//logger.debug("Found {} paths containing the chain", foundCyclesCount)
      		//assert foundCyclesCount > 0;
      		
		}

		// this test does not check return values since individualCycleSearch is asynchronous and does not return value
		@Ignore
		@Test
		void individualCycleSearchWithMaxReachDistanceTest() {
			/* run test with parameters: */
			def agentNumber = 50 // number of agents in the network
			def chainLength = 10 // the length of the chain to drop into the network;
			def randomWorksNumber = 50*2 // number of random works (outside chain) to drop into the network;
			def maxDistance = 50; // the maximum number of hops when doing decentralized similarity search;
			def similaritySearchThreshold = 1 // consider only items that are this similar when searching for path;
			def maxReachDistance = chainLength +1

	       	Global.parameters.similaritySearchThreshold = similaritySearchThreshold
	       	Global.parameters.persistence = false

	       	// create simulation object
			def sim = TestActorRef.create(system, Simulation.props()).underlyingActor();
			assertNotNull(sim);
			
			// create agent network and put some random works into it
			def agentList = sim.createAgentNetwork(agentNumber);
			logger.debug("added agent network with agents: {}", agentList)			
			sim.addRandomWorksToAgents(randomWorksNumber)

			// create chain and assign its items to random agents
			def chains = [Utils.createChain(chainLength)]
			def chain = chains[0]
			logger.debug("Created chain to add to the network: {}", chain)
			
			def chainedWorksJson = sim.addChainToNetwork(chain, true)  // add chain to network and return json structure...

			// create agent that has a work which closes the chain into the cycle
			// this agent will have the last item in he chain as demand
			// and the first item in the chain as offer
			
			def vertexIdList = new ArrayList(sim.vertexIdToActorRefTable.keySet())
			def taskAgent = sim.createAgent()
			logger.debug('Created an agent {} for performing the cycle search',taskAgent)
			def randomAgent = vertexIdList[new Random().nextInt(vertexIdList.size())]
			Method msg = new Method("knowsAgent", new ArrayList(){{add(randomAgent)}});
			Timeout timeout = new Timeout(Duration.create(5, "seconds"));
			Future<Object> future = Patterns.ask(taskAgent, msg, timeout);
		  	def knowsEdge = (Edge) Await.result(future, timeout.duration());
		  	assertNotNull(knowsEdge);
		  	logger.debug('agent {} knows agent {}', taskAgent, randomAgent)			

			msg = new Method("ownsWork", new ArrayList(){{add(chain[-1]);add(chain[0])}});
			timeout = new Timeout(Duration.create(5, "seconds"));
			future = Patterns.ask(taskAgent, msg, timeout);
		  	Vertex taskWork = (Vertex) Await.result(future, timeout.duration());
		  	assertNotNull(taskWork);
		  	logger.debug('Added work {} to agent {}', taskWork, taskAgent)			

		  	sim.decentralizedSimilaritySearchAndConnect(maxDistance)
		  	//sim.centralizedSimilaritySearchAndConnect();
			Thread.sleep(3000)
			 // test fails without above line: 
			 //it seems that connectIfSimilarForAllAgents takes a lot of time
			 // need to debug
 	       	sim.individualCycleSearch(taskAgent, chainedWorksJson, maxReachDistance);
 	       	Thread.sleep(5000)
      		//logger.debug("Found {} paths containing the chain", foundCyclesCount)
      		//assert foundCyclesCount > 0;
		}


		// this test does not check return values since individualCycleSearch is asynchronous and does not return value
		@Ignore
		@Test
		void individualCycleSearchWithTaskAgentTest() {
			/* run test with parameters: */
			def agentNumber = 50 // number of agents in the network
			def chainLengths = [5,10] // the length of the chain to drop into the network;
			def randomWorksNumber = 50*2 // number of random works (outside chain) to drop into the network;
			def maxDistance = 50; // the maximum number of hops when doing decentralized similarity search;
			def similaritySearchThreshold = 1 // consider only items that are this similar when searching for path;
			def maxReachDistances = [5,20] // select max cycle search distance within these bounds

	       	Global.parameters.similaritySearchThreshold = similaritySearchThreshold
	       	Global.parameters.persistence = false

	       	// create simulation object
			def sim = TestActorRef.create(system, Simulation.props()).underlyingActor();
			assertNotNull(sim);
			
			// create agent network and put some random works into it
			def agentList = sim.createAgentNetwork(agentNumber);
			logger.debug("added agent network with agents: {}", agentList)			
			sim.addRandomWorksToAgents(randomWorksNumber)

			def chainedWorksJson = sim.addChainToNetworkWithTaskAgent([[5,10],[]]) 
			Thread.sleep(5000)
			// create agent that has a work which closes the chain into the cycle
			// this agent will have the last item in he chain as demand
			// and the first item in the chain as offer
			
		  	sim.decentralizedSimilaritySearchAndConnect(maxDistance)
		  	//sim.centralizedSimilaritySearchAndConnect();
			Thread.sleep(3000)
			 // test fails without above line: 
			 //it seems that connectIfSimilarForAllAgents takes a lot of time
			 // need to debug
 	       	sim.individualCycleSearchTargeted(maxReachDistances);
 	       	Thread.sleep(5000)
      		//logger.debug("Found {} paths containing the chain", foundCyclesCount)
      		//assert foundCyclesCount > 0;
		}

		// this test does not check return values since individualCycleSearch is asynchronous and does not return value
		@Ignore
		@Test
		void individualRandomCycleSearchTest() {
			/* run test with parameters: */
			def agentNumber = 50 // number of agents in the network
			def chainLengths = [5,10] // the length of the chain to drop into the network;
			def randomWorksNumber = 50*2 // number of random works (outside chain) to drop into the network;
			def maxDistance = 50; // the maximum number of hops when doing decentralized similarity search;
			def similaritySearchThreshold = 1 // consider only items that are this similar when searching for path;
			def maxReachDistances = [5,20] // select max cycle search distance within these bounds

	       	Global.parameters.similaritySearchThreshold = similaritySearchThreshold
	       	Global.parameters.persistence = false

	       	// create simulation object
			def sim = TestActorRef.create(system, Simulation.props()).underlyingActor();
			assertNotNull(sim);
			
			// create agent network and put some random works into it
			def agentList = sim.createAgentNetwork(agentNumber);
			logger.debug("added agent network with agents: {}", agentList)			
			sim.addRandomWorksToAgents(randomWorksNumber)

			def chainedWorksJson = sim.addChainToNetworkWithTaskAgent([[5,10],[]]) 

			// create agent that has a work which closes the chain into the cycle
			// this agent will have the last item in he chain as demand
			// and the first item in the chain as offer
			
		  	sim.decentralizedSimilaritySearchAndConnect(maxDistance)
		  	//sim.centralizedSimilaritySearchAndConnect();
			Thread.sleep(3000)
			 // test fails without above line: 
			 //it seems that connectIfSimilarForAllAgents takes a lot of time
			 // need to debug
 	       	sim.individualCycleSearchRandom(maxReachDistances);
 	       	Thread.sleep(5000)
      		//logger.debug("Found {} paths containing the chain", foundCyclesCount)
      		//assert foundCyclesCount > 0;
		}


		@Test
		void centralizedCycleSearchTestNaive() {
			centralizedCycleSearchTest(1)
		}

		void centralizedCycleSearchTest(int searchVersion) {
			Global.parameters.simulationTimeout = 500
			/* run test with parameters: */
			def agentNumber = 50 // number of agents in the network
			def chainLength = 20 // the length of the chain to drop into the network;
			def randomWorksNumber = 4 // number of random works (outside chain) to drop into the network;
			def maxDistance = 4; // the maximum number of hops when doing decentralized similarity search;
			def similaritySearchThreshold = 1 // consider only items that are this similar when searching for path;
	       	def cutoffValue = 4; // maximum number of hops when doing path search;

	       	Global.parameters.similaritySearchThreshold = similaritySearchThreshold
	       	// create simulation object
			def sim = TestActorRef.create(system, Simulation.props()).underlyingActor();
			assertNotNull(sim);
			sim.on.flushVertices();
			
			// create agent network and put some random works into it
			def agentList = sim.createAgentNetwork(agentNumber);
			logger.debug("added agent network with agents: {}", agentList)			
			sim.addRandomWorksToAgents(randomWorksNumber)

			// create chain and assign its items to random agents
			def chains = [Utils.createChain(chainLength)]
			def chain = chains[0]
			logger.debug("Created chain to add to the network: {}", chain)
			
			def chainedWorksJson = sim.addChainToNetwork(chain, true)  // add chain to network and return json structure...

			// create agent that has a work which closes the chain into the cycle
			// this agent will have the last item in he chain as demand
			// and the first item in the chain as offer
			
			def vertexIdList = new ArrayList(sim.vertexIdToActorRefTable.keySet())
			def taskAgent = sim.createAgent()
			logger.debug('Created an agent {} for performing the cycle search',taskAgent)
			def randomAgent = vertexIdList[new Random().nextInt(vertexIdList.size())]
			Method msg = new Method("knowsAgent", new ArrayList(){{add(randomAgent)}});
			Timeout timeout = new Timeout(Duration.create(5, "seconds"));
			Future<Object> future = Patterns.ask(taskAgent, msg, timeout);
		  	def knowsEdge = (Edge) Await.result(future, timeout.duration());
		  	assertNotNull(knowsEdge);
		  	logger.debug('agent {} knows agent {}', taskAgent, randomAgent)			

			msg = new Method("ownsWork", new ArrayList(){{add(chain[-1]);add(chain[0])}});
			timeout = new Timeout(Duration.create(5, "seconds"));
			future = Patterns.ask(taskAgent, msg, timeout);
		  	Vertex taskWork = (Vertex) Await.result(future, timeout.duration());
		  	assertNotNull(taskWork);
		  	logger.debug('Added work {} to agent {}', taskWork, taskAgent)			

		  	
		  	def t = System.currentTimeMillis()
			sim.on.connectAllSimilarCentralized(); def method = 'connectAllSimilarCentralized'
			//sim.decentralizedSimilaritySearchAndConnect(20); def method = 'decentralizedSimilaritySearchAndConnect(10)'
			//sim.centralizedSimilaritySearchAndConnect(); def method = 'centralizedSimilaritySearchAndConnect'			
			logger.debug('{} method of centralizedCycleSearch took {} ms for {} agents', method, System.currentTimeMillis()-t, agentNumber)						
			Thread.sleep(1000)
			 // test fails without above line: 
			 //it seems that connectIfSimilarForAllAgents takes a lot of time
			 // need to debug

			 //running a centralized cycle search
			
			def foundCyclesCount = sim.allCyclesCentralized(chainedWorksJson,searchVersion)
     		logger.debug("Found {} cycles containing the chain", foundCyclesCount)
      		//assert foundCyclesCount > 0;
      		/*
      		This test cannot finish with assertions by design, since cycles that are found are sent via akka actor 
      		to simulation actor to process -- with current design of tests there is no way to get this message
      		into here. In order to do that, all actors have to be initialized as TestActors..
      		*/

		}		

		@Test
		void createAgentNetworkPathGraphTest() {
			def sim = TestActorRef.create(system, Simulation.props()).underlyingActor();
			assertNotNull(sim);
			sim.on.flushVertices();

			sim.createAgentNetworkFromNetworkXDataFile("graphs/data/pathGraph50.dat");
			assertEquals(50,sim.on.getVertices('agent').size());
			assertEquals(49*2,sim.on.getEdges('agent','knows').size());
		}

		@Test
		void createAgentNetworkCycleGraphTest() {
			def sim = TestActorRef.create(system, Simulation.props()).underlyingActor();
			assertNotNull(sim);
			sim.on.flushVertices();

			sim.createAgentNetworkFromNetworkXDataFile("graphs/data/cycleGraph50.dat");
			assertEquals(50,sim.on.getVertices('agent').size());
			assertEquals(50*2,sim.on.getEdges('agent','knows').size());
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

			sim.createAgentNetworkConnectedStars(sim.createAgent(),3,3);
			assertEquals(sim.on.getVertices("agent").size(),connectedStarsNumber(3,3))
		}

		private Integer connectedStarsNumber(Integer radius, Integer branchingFactor) {
			def number = 0;
			for (int i = 0; i<=radius; i++) {
				number = number + branchingFactor ** i;
			}
			logger.debug("Calculated connectedStars number={} for radius={}, branchingFactor={}",number,radius,branchingFactor)
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

		@Test
		void createPeriodicTimerTest() {
			def simTestRef = TestActorRef.create(system, Simulation.props())
			def sim = simTestRef.underlyingActor();
			sim.createAgentNetwork(20)
			String methodName = "addRandomWorksToAgents"
			List params = [1]
			int period = 500

			sim.createPeriodicTimer(methodName,params,period)
			Thread.sleep(5000)
			simTestRef.suspend()
			assert sim.vertexIdToActorRefTable.size() != 0
			simTestRef.stop()
		}

}
