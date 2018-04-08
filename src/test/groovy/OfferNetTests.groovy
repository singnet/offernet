package net.vveitas.offernet

import org.apache.log4j.PropertyConfigurator
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static org.junit.Assert.*
import static org.hamcrest.CoreMatchers.instanceOf;

import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;

import org.apache.log4j.PropertyConfigurator
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import akka.actor.Props
import akka.actor.ActorSystem;
import akka.actor.ActorRef;

import akka.testkit.TestActorRef
import akka.testkit.JavaTestKit;

public class OfferNetTests {
		static private OfferNet on = new OfferNet().flushVertices();
	    static private Logger logger;
	    static ActorSystem system = ActorSystem.create();

		@BeforeClass
		static void initLogging() {
		    def config = new ConfigSlurper().parse(new File('configs/log4j-properties.groovy').toURL())
    		PropertyConfigurator.configure(config.toProperties())
    		logger = LoggerFactory.getLogger('Tests.class');
		}

		@Test
		void createOfferNetworkTest() {
			def on1 = new OfferNet()
			assertNotNull(on1);
			assertFalse(on1.session.isClosed());
			assertFalse(on1.cluster.isClosed());
		}

		@Test
		void closeOfferNetworkTest() {
			def on1 = new OfferNet()
			assertNotNull(on1);
			on1.close();
			assertTrue(on1.session.isClosed());
			assertTrue(on1.cluster.isClosed());
		}

		@Test
		void flushAgentsTest() {
			def on1 = new OfferNet()
			assertNotNull(on1);
			TestActorRef.create(system, Agent.props(on.session)).underlyingActor();
			TestActorRef.create(system, Agent.props(on.session)).underlyingActor();
			assertNotEquals(0,on1.getIds('agent').size())
			on1.flushVertices("agent");
			assertEquals(0,on1.getIds('agent').size())
		}

		@Test
		void flushVerticesTest() {
			def on1 = new OfferNet();
			assertNotNull(on1);
			def agent = TestActorRef.create(system, Agent.props(on.session)).underlyingActor();
			def work = agent.ownsWork();
			def item1 = agent.addItemToWork("offers",work);
			def item2 = agent.addItemToWork("demands",work);
			assertNotEquals(0,on1.getIds('agent').size())
			assertNotEquals(0,on1.getIds('work').size())
			assertNotEquals(0,on1.getIds('item').size())
			on1.flushVertices();
			assertEquals(0,on1.getIds('agent').size())
			assertEquals(0,on1.getIds('work').size())
			assertEquals(0,on1.getIds('item').size())
		}

		@Test
		void getIdsTest() {
			def sim = TestActorRef.create(system, Simulation.props()).underlyingActor();
			sim.on.flushVertices('agent');
			sim.createAgentNetwork(10)
			def agentIds = sim.on.getIds('agent');
			assertNotNull(agentIds);
			assertEquals(10,agentIds.size());
		}

		@Test
		void addRandomWorksToAgentsTest() {
			def on1 = new OfferNet()
			on1.flushVertices();

			on1.createAgentNetwork(10)
			on1.addRandomWorksToAgents(10)
			assertEquals(20,on.getIds("item").size()); // creates two items (demand and offer) when creating a random work;
		}

		@Test
		void allWorkItemEdgesTest() {
			def sim = TestActorRef.create(system, Simulation.props()).underlyingActor();

			def chains = [Utils.createChain(4)]
			logger.info("Created chains: {}",chains)

			sim.createAgentNetwork(4,0,chains);

			def demandEdges = on.allWorkItemEdges("demands");
			def offerEdges = on.allWorkItemEdges("offers")

	        logger.info("demandEdges {} of class {}",demandEdges,demandEdges.getClass())

			assertEquals(7,demandEdges.size())
			assertEquals(7,offerEdges.size())
		}

		@Test
		void connectMatchingPairsTest() {
			def sim = TestActorRef.create(system, Simulation.props()).underlyingActor();
			def chainLength = 4

			def chains = [Utils.createChain(chainLength)]
			logger.info("Created chains: {}",chains)

			sim.createAgentNetwork(chainLength,0,chains);

			def demandEdges = on.allWorkItemEdges("demands");
			def offerEdges = on.allWorkItemEdges("offers")

	        logger.info("demandEdges {} of class {}",demandEdges,demandEdges.getClass())

			def matchingOfferDemandPairs = Utils.getMatchingOfferDemandPairs(offerEdges,demandEdges)
			logger.warn("Offer-Demand pairs found: {}",matchingOfferDemandPairs)

			def connectedPairsCount = sim.on.connectMatchingPairs(matchingOfferDemandPairs);
			assertEquals((chainLength - 2).toInteger(),connectedPairsCount)
		}

		@Test
		void createAgentTest() {
			def sim = TestActorRef.create(system, Simulation.props()).underlyingActor();
			def agent = sim.on.createAgent();
			assertNotNull(agent);
		}

		@Test 
		void createEdgeTest() {
			def sim = TestActorRef.create(system, Simulation.props()).underlyingActor();
			def agent1 = sim.on.createAgent();
			assertNotNull(agent1)
			def agent2 = sim.on.createAgent();
			assertNotNull(agent2)
			def edge = sim.on.knowsAgent(agent1,agent2);
			assertNotNull(edge)
		}
}