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

public class SimulationTests {

		private Logger logger;

		//@Ignore // for now -- takes too much time
		@Test
		void cycleSearchTest() {

		    def config = new ConfigSlurper().parse(new File('configs/log4j-properties.groovy').toURL())
	        PropertyConfigurator.configure(config.toProperties())
    	    logger = LoggerFactory.getLogger('OfferNet.class');

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
		void pathSearchManualTest() {
		    def config = new ConfigSlurper().parse(new File('configs/log4j-properties.groovy').toURL())
	        PropertyConfigurator.configure(config.toProperties())
    	    logger = LoggerFactory.getLogger('SimulationTests.class');

    	    def on = new OfferNet();
    	    on.flushVertices();


    	    def chain = ["0010","0110","0000","1110"]
    	    //def chain = Utils.createChain(4)
    	    logger.info("Created chain {}", chain)

    	    def work1 = new Work([new Item(chain[0],on.session)],[new Item(chain[1],on.session)],on.session);
    	    def work2 = new Work([new Item(chain[1],on.session)],[new Item(chain[2],on.session)],on.session);
    	    def work3 = new Work([new Item(chain[2],on.session)],[new Item(chain[3],on.session)],on.session);
    	    logger.info("Created two works: {}",[work1.id(),work2.id()],work3.id())

	   	    def agent1 = new Agent(on.session);
    	    def agent2 = new Agent(on.session);
    	    def agent3 = new Agent(on.session);
       	    def agent4 = new Agent(on.session);

    	    logger.info("Created three agents: {}",[agent1.id(),agent2.id(),agent3.id(),agent4.id()])

    	    agent1.knowsAgent(agent2);
    	    logger.info("agent {} knows agent {}",agent1,agent2)
    	    agent2.knowsAgent(agent3);
    	    logger.info("agent {} knows agent {}",agent2,agent3)
    	    agent3.knowsAgent(agent4);
    	    logger.info("agent {} knows agent {}",agent3,agent4)

    	    agent1.ownsWork(work1);
    	    logger.info("agent {} owns work {}", agent1,work1)
    	    agent2.ownsWork(work2);
    	    logger.info("agent {} owns work {}", agent2,work2)
       	    agent3.ownsWork(work3);
    	    logger.info("agent {} owns work {}", agent3,work3)

    	    def similarityThreshold = 4;
    	    def maxDistance = 2;
    	    logger.info("made {} connections on agent {} with similarityThreshold {} and maxDistance {}",agent1.searchAndConnect(similarityThreshold,maxDistance),agent1.id(),similarityThreshold, maxDistance)
    	    logger.info("made {} connections on agent {} with similarityThreshold {} and maxDistance {}",agent2.searchAndConnect(similarityThreshold,maxDistance),agent2.id(),similarityThreshold, maxDistance)
    	    logger.info("made {} connections on agent {} with similarityThreshold {} and maxDistance {}",agent3.searchAndConnect(similarityThreshold,maxDistance),agent3.id(),similarityThreshold, maxDistance)
 	       	logger.info("made {} connections on agent {} with similarityThreshold {} and maxDistance {}",agent4.searchAndConnect(similarityThreshold,maxDistance),agent4.id(),similarityThreshold, maxDistance)

 	       	def connectedPairs = on.allConnectedSimilarPairs();
 	       	logger.info("Calculated allConnectedSimilarPairs: {}",connectedPairs.size())
 	       	def similarPairs = on.allSimilarPairs();
 	       	logger.info("Calculated allSimilarityPairs (not necessarily connected) --  (query does not work): {}",similarPairs.size())
 	       	def similarityEdges = on.allSimilarityEdges().size() / 2;
 	       	def allSimilarityLinks = similarityEdges.toInteger();
 	       	assertEquals("similarityLinks is not correct",2,allSimilarityLinks);
 	       	assertEquals("similarityLinks is not equal to allConectedSimilarPairs",connectedPairs.size(),allSimilarityLinks)
 	       	
 	       	def cutoffValue = 2;
 	       	def uniquePaths = [] as Set;
 	       	[agent1,agent2,agent3].each{ agent -> 
 	       		def path = new Work(agent.getWorks()[0],on.session).pathSearch(cutoffValue,similarityThreshold)
 	       		logger.info("Found {} paths from agent {}",path.size(),agent1.id())
 	       		uniquePaths.add(path)
 	       	}
 	       	logger.warn("Found uniquePaths: {}", uniquePaths.size())

 	       	def index = 0;
 	       	uniquePaths.each {path -> 
 	       		index +=1;
 	       		Utils.convertToDotNotation(path,"Path","resources/path"+index+".dot");
 	       	}

		}

		@Test
		void compareCentralizedAndDecentralizedSimilaritySearchTest() {
		    def config = new ConfigSlurper().parse(new File('configs/log4j-properties.groovy').toURL())
	        PropertyConfigurator.configure(config.toProperties())
    	    logger = LoggerFactory.getLogger('OfferNet.class');

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
