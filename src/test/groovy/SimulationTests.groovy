package net.vveitas.offernet

import org.apache.log4j.PropertyConfigurator
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static org.junit.Assert.*

import org.junit.Test;
import org.junit.Ignore;
import org.junit.BeforeClass;
import org.junit.AfterClass;

import com.datastax.driver.dse.graph.GraphStatement;
import com.datastax.driver.dse.graph.SimpleGraphStatement;
import com.datastax.driver.dse.graph.GraphResultSet
import com.datastax.driver.dse.graph.GraphOptions
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

public class SimulationTests {

		static private Logger logger;

		@BeforeClass
		static void initLogging() {
		    def config = new ConfigSlurper().parse(new File('configs/log4j-properties.groovy').toURL())
	        PropertyConfigurator.configure(config.toProperties())
    	    logger = LoggerFactory.getLogger('OfferNet.class');
		}

		//@Ignore // for now -- takes too much time
		@Test
		void cycleSearchTest() {

			def sim = new Simulation()
			assertNotNull(sim);
			
			def chains = [Utils.createChain(3)]
			logger.warn("Created chain to add to the network: {}", chains[0])

			def agentList = sim.createAgentNetwork(5,0,chains);
			logger.warn("added agent network with agents: {}", agentList)			
			def similarityThreshold = Parameters.parameters.similarityThreshold
			def maxDistance = 2;
			def connections = sim.connectIfSimilarForAllAgents(agentList,similarityThreshold,maxDistance);
			logger.warn("Created {} similarity connections of all agents with similarity {} and maxDistance {}", similarityThreshold, maxDistance)

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
			def sim = new Simulation()
			assertNotNull(sim);
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
 	       	assertEquals("similarityLinks is not correct",2,allSimilarityLinks);
 	       	assertEquals("similarityLinks is not equal to allConectedSimilarPairs",connectedPairs.size(),allSimilarityLinks)
 	       	
 	       	def cutoffValue = 2;
 	       	def uniquePaths = [] as Set;
 	       	sim.agentList.subList(0,2).each{ agent -> 
 	       		def path = new Work(agent.getWorks()[0],sim.on.session).pathSearch(cutoffValue,similarityThreshold)
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
 	       		Utils.convertToDotNotation(path,"Path","resources/path"+index+".dot");
 	       	}
		}

		@Test
		void pathSearchManualCentralizedTest() {
			def sim = new Simulation()
			assertNotNull(sim);
			sim.testNetworkSmall();

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
 	       		Utils.convertToDotNotation(path,"Path","resources/path"+index+".dot");
 	       	}

		}


		@Test
		void decentralizedPathSimulationTest() {

			def sim = new Simulation()
			assertNotNull(sim);
			
			def chainLength = 6
			def chains = [Utils.createChain(chainLength)]
			logger.warn("Created chain to add to the network: {}", chains[0])

			def agentList = sim.createAgentNetwork(chainLength+2,0,chains);
			logger.warn("added agent network with agents: {}", agentList)			

			logger.warn("Running decentralized similarity search")
			def start = System.currentTimeMillis();
			def similarityThreshold = Parameters.parameters.binaryStringLength
			def maxDistance = 6;
			def similarityConnectionsDecentralized = sim.connectIfSimilarForAllAgents(agentList,similarityThreshold,maxDistance);
			logger.warn("Created {} similarity connections of all agents with similarity {} and maxDistance {}", similarityThreshold, maxDistance);
			
	       	def cutoffValue = 7;
 	       	def uniquePaths = [] as Set;
 	       	agentList.each{ agent -> 
 	       		def path = new Work(agent.getWorks()[0],sim.on.session).pathSearch(cutoffValue,similarityThreshold)
 	       		logger.info("Found {} paths from agent {}",path.size(),agent.id())
 	       		uniquePaths.add(path)
 	       	}
 	       	logger.warn("Found uniquePaths: {}", uniquePaths.size())
           	logger.warn("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)

           	def index = 0;

           	String dirname = new SimpleDateFormat("MMddhhmmss").format(new Date());
           	def methodName = Utils.getCurrentMethodName()
           	//new File("resources/"+methodName+dirname).mkdir();
 	       	uniquePaths.each {path -> 
 	       		index +=1;
 	       		Utils.convertToDotNotation(path,"Path","resources/path"+index+".dot");
 	       	}

		}

		@Test
		void centralizedPathSimulationTest() {
			
			def sim = new Simulation()
			assertNotNull(sim);
			
			def chainLength = 6
			def chains = [Utils.createChain(chainLength)]
			logger.warn("Created chain to add to the network: {}", chains[0])

			def agentList = sim.createAgentNetwork(chainLength+2,0,chains);
			logger.warn("added agent network with agents: {}", agentList)			

			logger.warn("Running centralized similarity search")
			def start = System.currentTimeMillis();
			def similarityThreshold = Parameters.parameters.binaryStringLength
			def maxDistance = 3;

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
 	       		Utils.convertToDotNotation(path,"Path","resources/path"+index+".dot");
 	       	}
		}

		@Test
		void compareCentralizedAndDecentralizedSimilaritySearchTest() {
			def sim = new Simulation()
			assertNotNull(sim);
			
			def chainLength = 5
			def chains = [Utils.createChain(chainLength)]
			logger.warn("Created chain to add to the network: {}", chains[0])

			def agentList = sim.createAgentNetwork(chainLength+2,0,chains);
			logger.warn("added agent network with agents: {}", agentList)			

			logger.warn("Running decentralized similarity search")
			def timeStart = System.currentTimeMillis();
			def similarityThreshold = Parameters.parameters.binaryStringLength
			def maxDistance = 3;
			def similarityConnectionsDecentralized = sim.connectIfSimilarForAllAgents(agentList,similarityThreshold,maxDistance);
			def timeDecentralized = System.currentTimeMillis() - timeStart;
			logger.warn("Created {} similarity connections of all agents with similarity {} and maxDistance {}", similarityThreshold, maxDistance);
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

			DseCluster dseCluster = DseCluster.builder().addContactPoint("192.168.1.6").build();
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
}
