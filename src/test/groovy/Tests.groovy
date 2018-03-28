package net.vveitas.offernet

import org.apache.log4j.PropertyConfigurator
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static org.junit.Assert.*

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

public class Tests {
		static private OfferNet on = new OfferNet().flushVertices();
	    static private Logger logger;
	    static ActorSystem system = ActorSystem.create();

		@BeforeClass
		static void initLogging() {
		    def config = new ConfigSlurper().parse(new File('configs/log4j-properties.groovy').toURL())
    		PropertyConfigurator.configure(config.toProperties())
    		logger = LoggerFactory.getLogger('Tests.class');
		}

		/*
		* Agent.groovy
		*/

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
        	def agent1 = TestActorRef.create(system, Agent.props(on.session)).underlyingActor();
        	assertNotNull(agent1);
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

		/*
		*	OfferNet.class
		*/

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
			def on1 = new OfferNet()
			on1.flushVertices('agent');
			on1.createAgentNetwork(10)
			def agentIds = on1.getIds('agent');
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
			def sim = new Simulation();

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
			def sim = new Simulation();
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
			def sim = new Simulation();
			def agent = sim.on.createAgent();
			assertNotNull(agent);
		}

		@Test 
		void createEdgeTest() {
			def sim = new Simulation();
			def agent1 = sim.on.createAgent();
			assertNotNull(agent1)
			def agent2 = sim.on.createAgent();
			assertNotNull(agent2)
			def edge = sim.on.knowsAgent(agent1,agent2);
			assertNotNull(edge)
		}

		/*
		*	Utils.class
		*/

		@Test
		void generateBinaryStringTest() {
				String string = Utils.generateBinaryString(Parameters.parameters.binaryStringLength);
				assertEquals(16,string.length());
				assertTrue(string.toSet().sort().join() == '01' | string.toSet().sort().join() == '10');
		}

		@Test
		void createChainTest() {
			List chain = Utils.createChain(5)
			assertEquals(5,chain.size());
		}

		@Test
		void calculateSimilarityTest() {
			String value1 = "000000"
			String value2 = "000111"
			def d1 = Utils.veitasSimilarity(value1,value2);
			assertNotNull(d1)
			def agent = TestActorRef.create(system, Agent.props(on.session)).underlyingActor();
            def work = agent.ownsWork(value1,value2);
            def item1 = agent.getWorksItems(work,"demands")[0]
            def item2 = agent.getWorksItems(work,"offers")[0]
			assertNotNull(item1)
			assertNotNull(item2)
			def d2 = Utils.calculateSimilarity(item1,item2);
			assertNotNull(d2)
			assertEquals(d1,d2);
			assertEquals(3,d1);
		}

		/*
		*	Parameters.class
		*/

		@Test
		void parametersTest() {
			assertEquals(16,Parameters.parameters.binaryStringLength);
			assertEquals(8,Parameters.parameters.similarityThreshold);
		}

}
