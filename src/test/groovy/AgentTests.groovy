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

import java.util.UUID;

public class AgentTests {
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
	void idStaticTest() {
		def agent1 = TestActorRef.create(system, Agent.props(on.session)).underlyingActor();
		assertNotNull(agent1.id())
		logger.info("id of the actor via static interface is {}",agentId)
	}

	@Test
	void idMessageTest() {
		new JavaTestKit(system) {{
			def agentRef = system.actorOf(Agent.props(on.session),"agent1");
			agentRef.tell(new Method("id",[]),getRef())
        	def agentId = receiveN(1)
			assertNotNull(agentId)
			logger.info("id of the actor via message is {}",agentId)
		}}
	}

	@Test
	void connectTest() {
		def agent1 = TestActorRef.create(system, Agent.props(on.session)).underlyingActor();
		def agent2 = TestActorRef.create(system, Agent.props(on.session)).underlyingActor();
		def work1 = agent1.ownsWork();
		def work2 = agent2.ownsWork();
		def item1 = agent1.addItemToWork("demands",work1)
		def item2 = agent2.addItemToWork("demands",work1)

		def similarity = Utils.calculateSimilarity(item1,item2);
		def similarityEdge = agent1.connect(item1,item2, similarity);
		assertNotNull(similarityEdge);
	}

	@Test
	void connectAllSimilarTest() {
		on.flushVertices();
		def agent1 = TestActorRef.create(system, Agent.props(on.session)).underlyingActor();
		def agent2 = TestActorRef.create(system, Agent.props(on.session)).underlyingActor();
		def work1 = agent1.ownsWork('1111','1110');
		def work2 = agent2.ownsWork('1100','1000');
		def start = agent1.addItemToWork("demands",work2,'0000')

		def knownItemsList = on.getVertices('item')
		List similarityEdges = agent1.connectAllSimilar(start, knownItemsList,2)
		assertEquals(3,similarityEdges.size())
	}

	@Test
	void reciprocalDistanceLinkTest() {
		def agent1 = TestActorRef.create(system, Agent.props(on.session)).underlyingActor();
		def agent2 = TestActorRef.create(system, Agent.props(on.session)).underlyingActor();
		def work1 = agent1.ownsWork();
		def work2 = agent2.ownsWork();
		def item1 = agent1.addItemToWork("demands",work1)
		def item2 = agent2.addItemToWork("demands",work1)

		def similarity = Utils.calculateSimilarity(item1,item2);
		def similarityEdge = agent1.connect(item1, item2, similarity);
		assertNotNull(similarityEdge);

		def d1 =agent1.existsSimilarity(item1,item2);
		assertNotNull(d1)
		def d2 = agent2.existsSimilarity(item2,item1);
		assertNotNull(d2)
		assertEquals(d1,d2)
	}

	@Test
	void existsSimilarityTest() {
		def agent1 = TestActorRef.create(system, Agent.props(on.session)).underlyingActor();
		def agent2 = TestActorRef.create(system, Agent.props(on.session)).underlyingActor();
		def work1 = agent1.ownsWork();
		def work2 = agent2.ownsWork();
		def item1 = agent1.addItemToWork("demands",work1)
		def item2 = agent2.addItemToWork("demands",work1)

		def similarity = Utils.calculateSimilarity(item1,item2);
		def similarityEdge = agent1.connect(item1, item2, similarity);
		assertNotNull(similarityEdge);

		def d2 = agent2.existsSimilarity(item1,item2);
		assertNotNull(d2)

	}

	@Test
	void connectIfSimilarTest() {
		def agent1 = TestActorRef.create(system, Agent.props(on.session)).underlyingActor();
		def agent2 = TestActorRef.create(system, Agent.props(on.session)).underlyingActor();
		def work1 = agent1.ownsWork();
		def work2 = agent2.ownsWork();
		def item1 = agent1.addItemToWork("demands",work1)
		def item2 = agent2.addItemToWork("demands",work1)

		def similarity = Utils.calculateSimilarity(item1,item2);
		def connectedEdge = agent1.connectIfSimilar(item1, item2, similarity-1);
		assertNotNull(connectedEdge);
		assertEquals(similarity,Utils.edgePropertyValueAsInteger(connectedEdge,'value'))

		def item3 = agent1.addItemToWork("offers",work1)
		def item4 = agent2.addItemToWork("offers",work1)

		similarity = Utils.calculateSimilarity(item3,item4);

		connectedEdge = agent2.connectIfSimilar(item3, item4, similarity+1);
		assertNull(connectedEdge);
	}

	@Test
	void itemsOfKnownAgentsTest() {
		def agent1 = TestActorRef.create(system, Agent.props(on.session)).underlyingActor();
		def agent2 = TestActorRef.create(system, Agent.props(on.session)).underlyingActor();
		def agent3 = TestActorRef.create(system, Agent.props(on.session)).underlyingActor();
		def agent4 = TestActorRef.create(system, Agent.props(on.session)).underlyingActor();

		agent1.knowsAgent(agent2.vertex.getId());
		agent2.knowsAgent(agent3.vertex.getId());
		agent3.knowsAgent(agent4.vertex.getId());

		def work1 = agent1.ownsWork();
		agent2.ownsWork()
		agent3.ownsWork()
		agent4.ownsWork()

		List items = agent1.itemsOfKnownAgents(2)
		assertNotNull(items)
		assertEquals(4,items.size())
		items.each{ item ->
			assertEquals('item',item.getLabel())
		}

	}

	@Test
	void createAgentNewVertexTest() {
		String agentId = UUID.randomUUID().toString();
    	def agent1 = TestActorRef.create(system, Agent.props(on.session,agentId)).underlyingActor();
    	assertNotNull(agent1);
    	def agentIdFromVertex = agent1.id()
    	logger.info("Original agent id {} is of type {}",agentId, agentId.getClass().getSimpleName())
    	logger.info("Agent id extracted from vertex {} is of type {}",agentIdFromVertex, agentIdFromVertex.getClass().getSimpleName())
    	assertEquals(agentId,agentIdFromVertex)
		}

	@Test
	void createAgentExistingVertexTest() {
		def agent1 = TestActorRef.create(system, Agent.props(on.session)).underlyingActor();
		assertNotNull(agent1);
		def id1 = agent1.id();

		def agent2 = TestActorRef.create(system, Agent.props(on.session)).underlyingActor();
		assertNotNull(agent2);
		assertEquals(id1,agent2.id());
	}


	@Test
	void agentKnowsAgentTest() {
        def agent1 = TestActorRef.create(system, Agent.props(on.session)).underlyingActor();
        assertNotNull(agent1);
        def agent2 = TestActorRef.create(system, Agent.props(on.session)).underlyingActor();
        assertNotNull(agent2);
        def edge = agent1.knowsAgent(agent2.vertex,getId());
        assertNotNull(edge);
	}

	@Test
	void agentKnowsAgentViaMessageTest() {
		new JavaTestKit(system) {{
        	def agent1Ref = system.actorOf(Agent.props(on.session),"agent1");
        	assertNotNull(agent1Ref);
        	def agent2Ref = system.actorOf(Agent.props(on.session),"agent2");
        	assertNotNull(agent2Ref);
        	agent2Ref.tell(new Method("id",[]),getRef())
        	def agent2id = receiveN(1)
        	agent1Ref.tell(new Method("knowsAgent",[agent2id]),getRef())
        	def edge = receiveN(1)
        	assertNotNull(edge);
        }}
	}

	@Test
	void agentOwnsNewWorkTest() {
			def agent1 = TestActorRef.create(system, Agent.props(on.session)).underlyingActor();
			assertNotNull(agent1);

			def work = agent1.ownsWork();
			assertNotNull(work);
	}

	@Test
	void agentOwnsKnownWorkTest() {
			def agent1 = TestActorRef.create(system, Agent.props(on.session)).underlyingActor();
			assertNotNull(agent1);

			def work = agent1.ownsWork("00011","00001")
			assertNotNull(work);

			def demand = agent1.getWorksItems(work,"demands")[0];
			assertNotNull(demand)
			def offer = agent1.getWorksItems(work,"offers")[0];
			assertNotNull(offer)

			assertEquals("00011",demand.getProperty("value").getValue().asString())
			assertEquals("00001",offer.getProperty("value").getValue().asString())
	}

	@Test
	void allItemsTest() {
			def agent = TestActorRef.create(system, Agent.props(on.session)).underlyingActor();
			assertNotNull(agent);

			def work = agent.ownsWork();
			assertNotNull(work);

			agent.addItemToWork("demands",work);
			agent.addItemToWork("offers",work);

			assertEquals(4,agent.allItems().size())
	}

	@Test
	void searchAndConnectTest() {
		on.flushVertices("agent");
		def agent1 = TestActorRef.create(system, Agent.props(on.session)).underlyingActor();
		agent1.ownsWork('111110','000000');
		def agent2 = TestActorRef.create(system, Agent.props(on.session)).underlyingActor();
		agent2.ownsWork('111100','110000');
		def agent3 = TestActorRef.create(system, Agent.props(on.session)).underlyingActor();
		agent3.ownsWork('100000','111100');
		def agent4 = TestActorRef.create(system, Agent.props(on.session)).underlyingActor();
		agent4.ownsWork('111110','000000');

		agent1.knowsAgent(agent2.vertex.getId());
		agent2.knowsAgent(agent3.vertex.getId());
		agent3.knowsAgent(agent4.vertex.getId());

		/*
		The resulting graph has 4 agents, 4 works, 8 items and 6 reciprocal connections (12 links in total)
		*/
		assertEquals(3,agent1.searchAndConnect(5,2)) // this traverses part of the graph
		assertEquals(3,agent1.searchAndConnect(4,3)) // traverses the whole graph, and finds the rest of connections with similarity gte(4)

	}

	@Test
	void getWorksTest() {
		def agent = TestActorRef.create(system, Agent.props(on.session)).underlyingActor();
		def work1 = agent.ownsWork();
		def work2 = agent.ownsWork();
		def work3 = agent.ownsWork();

		List worksVertexList = agent.getWorks();
		assertEquals(3,worksVertexList.size());
		assertTrue(worksVertexList.contains(work1));
		assertTrue(worksVertexList.contains(work2));
		assertTrue(worksVertexList.contains(work3));

	}

	@Test
	void addNewOfferTest() {
		def agent = TestActorRef.create(system, Agent.props(on.session)).underlyingActor();
		def work = agent.ownsWork()
		def offer = agent.addItemToWork("offers",work)
		assertNotNull(offer);
	}

	@Test
	void addKnownOfferTest() {
		def agent = TestActorRef.create(system, Agent.props(on.session)).underlyingActor();
		def work = agent.ownsWork()
		def offer = agent.addItemToWork("offers",work,"00000")
		assertEquals("00000",offer.getProperty("value").getValue().asString());
	}

	@Test
	void addNewDemandTest() {
		def agent = TestActorRef.create(system, Agent.props(on.session)).underlyingActor();
		def work = agent.ownsWork()
		def offer = agent.addItemToWork("demands",work)
		assertNotNull(offer);
	}

	@Test
	void addKnownDemandTest() {
		def agent = TestActorRef.create(system, Agent.props(on.session)).underlyingActor();
		def work = agent.ownsWork()
		def offer = agent.addItemToWork("demands",work,"00011")
		assertEquals("00011",offer.getProperty("value").getValue().asString());
	}

	@Test
	void getWorkItemsTest() {
		 def agent = TestActorRef.create(system, Agent.props(on.session)).underlyingActor();
		 def work = agent.ownsWork();
		 def item1 = agent.addItemToWork("demands",work);
		 def item2 = agent.addItemToWork("demands",work);

		 def demands = agent.getWorksItems(work,"demands");
		 assertEquals(3,demands.size()); // tree because one is created by default in Work constructor

		 def offers = agent.getWorksItems(work,"offers");
		 assertEquals(1,offers.size());

	}

}
