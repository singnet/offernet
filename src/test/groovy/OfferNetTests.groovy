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
	    static ActorSystem system = ActorSystem.create("OfferNetTests");

		@BeforeClass
		static void initLogging() {
		    def config = new ConfigSlurper().parse(new File('configs/log4j-properties.groovy').toURL())
    		PropertyConfigurator.configure(config.toProperties())
    		logger = LoggerFactory.getLogger('OfferNetTests.class');
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
			TestActorRef.create(system, Agent.props(on.session, UUID.randomUUID().toString())).underlyingActor();
			TestActorRef.create(system, Agent.props(on.session, UUID.randomUUID().toString())).underlyingActor();
			assertNotEquals(0,on1.getIds('agent').size())
			on1.flushVertices("agent");
			assertEquals(0,on1.getIds('agent').size())
		}

		@Test
		void flushVerticesTest() {
			def on1 = new OfferNet();
			assertNotNull(on1);
			def agent = TestActorRef.create(system, Agent.props(on.session, UUID.randomUUID().toString())).underlyingActor();
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
		void allWorkItemEdgesTest() {
			def sim = TestActorRef.create(system, Simulation.props()).underlyingActor();
			def size = 4
			def chains = [Utils.createChain(size)]
			logger.info("Created chains: {}",chains)

			sim.createAgentNetwork(size,0,chains);
			Thread.sleep(1000)

			def demandEdges = on.allWorkItemEdges("demands");
			def offerEdges = on.allWorkItemEdges("offers")

	        logger.info("demandEdges {} of class {}",demandEdges,demandEdges.getClass())

			assertEquals(size-1,demandEdges.size())
			assertEquals(size-1,offerEdges.size())
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

		@Ignore
		@Test
		void openVisualizationWindowTest() {
			def sim = TestActorRef.create(system, Simulation.props()).underlyingActor();
			sim.on.openVisualizationWindow();
		}
}