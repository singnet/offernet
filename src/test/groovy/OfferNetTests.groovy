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
		    //def config = new ConfigSlurper().parse(new File('configs/log4j-properties.groovy').toURL())
    		//PropertyConfigurator.configure(config.toProperties())
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
			logger.debug("Created chains: {}",chains)

			sim.createAgentNetwork(size,0,chains);
			Thread.sleep(1000)

			def demandEdges = on.allWorkItemEdges("demands");
			def offerEdges = on.allWorkItemEdges("offers")

	        logger.debug("demandEdges {} of class {}",demandEdges,demandEdges.getClass())

			assertEquals(size-1,demandEdges.size())
			assertEquals(size-1,offerEdges.size())
		}

		@Test
		void createAgentTest() {
			def sim = TestActorRef.create(system, Simulation.props()).underlyingActor();
			def agent = sim.createAgent();
			assertNotNull(agent);
		}

		@Test 
		void createEdgeTest() {
			def sim = TestActorRef.create(system, Simulation.props()).underlyingActor();
			def agent1Id = UUID.randomUUID().toString()
			def agent1 = TestActorRef.create(system, Agent.props(on.session,agent1Id),agent1Id).underlyingActor();
			assertNotNull(agent1)
			def agent2 = sim.createAgent();
			assertNotNull(agent2)
			def edge = agent1.knowsAgent(sim.getAgentVertexId(agent2));
			assertNotNull(edge)
		}

		@Ignore
		@Test
		void openVisualizationWindowTest() {
			def sim = TestActorRef.create(system, Simulation.props()).underlyingActor();
			sim.on.openVisualizationWindow();
		}

	@Test
	void connectAllSimilarTest() {
		on.flushVertices();
		def agent1 = TestActorRef.create(system, Agent.props(on.session, UUID.randomUUID().toString())).underlyingActor();
		def agent2 = TestActorRef.create(system, Agent.props(on.session, UUID.randomUUID().toString())).underlyingActor();
		agent1.knowsAgent(agent2.vertexId());
		def work1 = agent1.ownsWork(0.2,0.8);
		def work2 = agent2.ownsWork(0.5,0.8);
		def knownItemsList = on.getVertices('item')
		def start = agent1.addItemToWork("demands",work2,0.9)
		def allItems = on.getVertices('item')
		def tail = allItems.clone();
		def allSmilarityEdges=[]
		allItems.each { item ->
			 tail = tail.drop(1)
			 def similarityEdges= on.connectAllSimilar(item, tail,0.5d)
	         logger.debug('similaritySearchLoop: allItems size is {} ', allItems.size())
		     logger.debug('similaritySearchLoop:  currentItem {} ', item)
        	 logger.debug('similaritySearchLoop: tail {} ', tail.size())
			 logger.debug('found similarity edges {} for item {}', similarityEdges, item)
			 allSmilarityEdges.addAll(similarityEdges)
		}
		assert allSmilarityEdges.size() == 7 // founds more than from agent perspective because it does not check if an item belongs to the same work/agent;
	}

	@Test
	void connectAllSimilarCentralized() {
		on.flushVertices();
		def agent1 = TestActorRef.create(system, Agent.props(on.session, UUID.randomUUID().toString())).underlyingActor();
		def agent2 = TestActorRef.create(system, Agent.props(on.session, UUID.randomUUID().toString())).underlyingActor();
		agent1.knowsAgent(agent2.vertexId());
		def work1 = agent1.ownsWork(0.2,0.8);
		def work2 = agent2.ownsWork(0.5,0.8);
		def knownItemsList = on.getVertices('item')
		def start = agent1.addItemToWork("demands",work2,0.9)
		def allSmilarityEdges = on.connectAllSimilarCentralized()
		assert allSmilarityEdges.size() == 7 // founds more than from agent perspective because it does not check if an item belongs to the same work/agent;
	}

	@Test
	void connectIfSimilarTest() {
		def agent1 = TestActorRef.create(system, Agent.props(on.session, UUID.randomUUID().toString())).underlyingActor();
		def agent2 = TestActorRef.create(system, Agent.props(on.session, UUID.randomUUID().toString())).underlyingActor();
		def work1 = agent1.ownsWork();
		def work2 = agent2.ownsWork();
		def item1 = agent1.addItemToWork("demands",work1)
		def item2 = agent2.addItemToWork("demands",work1)

		def similarity = Utils.calculateSimilarity(item1,item2);
		def connectedEdge = on.connectIfSimilar(item1, item2, similarity);
		assertNotNull(connectedEdge);
		assertEquals(similarity,Utils.edgePropertyValue(connectedEdge,'value'),0.0001)

		def item3 = agent1.addItemToWork("offers",work1)
		def item4 = agent2.addItemToWork("offers",work1)

		similarity = Utils.calculateSimilarity(item3,item4);

		connectedEdge = on.connectIfSimilar(item3, item4, similarity*2);
		assertNull(connectedEdge);
	}

	@Test
	void existsSimilarityTest() {
		def agent1 = TestActorRef.create(system, Agent.props(on.session, UUID.randomUUID().toString())).underlyingActor();
		def agent2 = TestActorRef.create(system, Agent.props(on.session, UUID.randomUUID().toString())).underlyingActor();
		def work1 = agent1.ownsWork();
		def work2 = agent2.ownsWork();
		def item1 = agent1.addItemToWork("demands",work1)
		def item2 = agent2.addItemToWork("offers",work2)

		agent1.searchAndConnect(0.0,2)

		def d2 = on.existsSimilarity(item1,item2);
		assertNotNull(d2)

	}

	@Test
	void connectTest() {
		def agent1 = TestActorRef.create(system, Agent.props(on.session, UUID.randomUUID().toString())).underlyingActor();
		def agent2 = TestActorRef.create(system, Agent.props(on.session, UUID.randomUUID().toString())).underlyingActor();
		def work1 = agent1.ownsWork();
		def work2 = agent2.ownsWork();
		def item1 = agent1.addItemToWork("demands",work1)
		def item2 = agent2.addItemToWork("demands",work1)

		def similarity = Utils.calculateSimilarity(item1,item2);
		def similarityEdge = on.connect(item1,item2, similarity);
		assertNotNull(similarityEdge);
	}


}