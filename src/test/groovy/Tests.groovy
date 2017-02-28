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

public class Tests {
		static private OfferNet on = new OfferNet().flushVertices();
	    static private Logger logger;

		@BeforeClass
		static void initLogging() {
		    def config = new ConfigSlurper().parse(new File('configs/log4j-properties.groovy').toURL())
    		PropertyConfigurator.configure(config.toProperties())
    		logger = LoggerFactory.getLogger('Tests.class');
		}

		/*
		* Item.groovy
		*/

		@Test
		void connectTest() {
			def start = System.currentTimeMillis()
			def item1 = new Item(on.session);
			def item2 = new Item (on.session);
			def similarity = Utils.calculateSimilarity(item1,item2);
			def similarityEdge = item1.connect(item1, similarity);
			assertNotNull(similarityEdge);
			logger.warn("Finished test {} in {} seconds","connectTest",(System.currentTimeMillis() - start)/1000)	
		}

		@Test
		void reciprocalDistanceLinkTest() {
			def item1 = new Item(on.session);
			def item2 = new Item (on.session);
			def similarity = Utils.calculateSimilarity(item1,item2);
			def similarityEdge = item1.connect(item1, similarity);
			assertNotNull(similarityEdge);

			def d1 =item1.existsSimilarity(item2);
			assertNotNull(d1)
			def d2 = item2.existsSimilarity(item1);
			assertNotNull(d2)
			assertEquals(d1,d2)

		}


		@Test
		void existsSimilarityTest() {
			def item1 = new Item(on.session);
			def item2 = new Item (on.session);
			def d1 = Utils.calculateSimilarity(item1,item2);
			def similarityEdge = item1.connect(item2, d1);
			assertNotNull(similarityEdge);

			def d2 = item1.existsSimilarity(item2);
			assertNotNull(d2)
			assertEquals(d1,d2)
		}

		@Test
		void connectIfSimilarTest() {
			def item1 = new Item(on.session);
			def item2 = new Item (on.session);
			def similarity = Utils.calculateSimilarity(item1,item2);
			def connectedEdge = item1.connectIfSimilar(item2, similarity-1);
			assertNotNull(connectedEdge);
			assertEquals(similarity,Utils.edgePropertyValueAsInteger(connectedEdge,'value'))

			def item3 = new Item(on.session);
			def item4 = new Item (on.session);
			similarity = Utils.calculateSimilarity(item3,item4);

			connectedEdge = item3.connectIfSimilar(item4, similarity+1);
			assertNull(connectedEdge);

		}

		@Test
		void connectAllSimilarTest() {
			def item = new Item('1111',on.session);
			List knownItemsList = []
			knownItemsList.add(new Item ('1110',on.session));
			knownItemsList.add(new Item ('1100',on.session));
			knownItemsList.add(new Item ('1000',on.session));
			knownItemsList.add(new Item ('0000',on.session));
			List similarityEdges = item.connectAllSimilar(knownItemsList.collect{it.vertex},2)
			assertEquals(2,similarityEdges.size())
		}

		@Test
		void itemsOfKnownAgentsTest() {
			def agent1 = new Agent(on.session)
			def agent2 = new Agent(on.session)
			def agent3 = new Agent(on.session)
			def agent4 = new Agent(on.session)

			agent1.knowsAgent(agent2);
			agent2.knowsAgent(agent3);
			agent3.knowsAgent(agent4);

			def work1 = agent1.ownsWork();
			agent2.ownsWork()
			agent3.ownsWork()
			agent4.ownsWork()

			def demand = new Item(work1.getDemands()[0],on.session);
			List items = demand.itemsOfKnownAgents(2)
			assertNotNull(items)
			assertEquals(4,items.size())
			items.each{ item ->
				assertEquals('item',item.getLabel())
			}

		}

		/*
		* Agent.groovy
		*/

		@Test
    	void createAgentNewVertexTest() {
        	def agent1 = new Agent(on.session);
        	assertNotNull(agent1);
   		}

		@Test
		void createAgentExistingVertexTest() {
			def agent1 = new Agent(on.session);
			assertNotNull(agent1);
			def id1 = agent1.id();

			def agent2 = new Agent(id1,on.session);
			assertNotNull(agent2);
			assertEquals(id1,agent2.id());
		}


		@Test
		void agentKnowsAgentTest() {
	        def agent1 = new Agent(on.session);
	        assertNotNull(agent1);
	        def agent2 = new Agent(on.session);
	        assertNotNull(agent2);
	        def edge = agent1.knowsAgent(agent2);
	        assertNotNull(edge);
    	}

		@Test
		void agentOwnsNewWorkTest() {
				def agent1 = new Agent(on.session);
				assertNotNull(agent1);

				def work = agent1.ownsWork();
				assertNotNull(work);
		}

		@Test
		void agentOwnsKnownWorkTest() {
				def agent1 = new Agent(on.session);
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
				def agent=new Agent(on.session)
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
			def agent1 = new Agent(on.session);
			agent1.ownsWork('111110','000000');
			def agent2 = new Agent(on.session);
			agent2.ownsWork('111100','110000');
			def agent3 = new Agent(on.session);
			agent3.ownsWork('100000','111100');
			def agent4 = new Agent(on.session);
			agent4.ownsWork('111110','000000');

			agent1.knowsAgent(agent2);
			agent2.knowsAgent(agent3);
			agent3.knowsAgent(agent4);

			/*
			The resulting graph has 4 agents, 4 works, 8 items and 6 reciprocal connections (12 links in total)
			*/
			assertEquals(3,agent1.searchAndConnect(5,2)) // this traverses part of the graph
			assertEquals(3,agent1.searchAndConnect(4,3)) // traverses the whole graph, and finds the rest of connections with similarity gte(4)

		}

		@Test
		void getWorksTest() {
			def agent = new Agent(on.session);
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
			def agent=new Agent(on.session)
			def work = agent.ownsWork()
			def offer = agent.addItemToWork("offers",work)
			assertNotNull(offer);
		}

		@Test
		void addKnownOfferTest() {
			def agent=new Agent(on.session)
			def work = agent.ownsWork()
			def offer = agent.addItemToWork("offers",work,"00000")
			assertEquals("00000",offer.getProperty("value").getValue().asString());
		}

		@Test
		void addNewDemandTest() {
			def agent=new Agent(on.session)
			def work = agent.ownsWork()
			def offer = agent.addItemToWork("demands",work)
			assertNotNull(offer);
		}

		@Test
		void addKnownDemandTest() {
			def agent=new Agent(on.session)
			def work = agent.ownsWork()
			def offer = agent.addItemToWork("demands",work,"00011")
			assertEquals("00011",offer.getProperty("value").getValue().asString());
		}

		@Test
		void getWorkItemsTest() {
			 def agent=new Agent(on.session)
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
			new Agent(on1.session)
			new Agent(on1.session)
			assertEquals(2,on1.getIds('agent').size())
			on1.flushVertices("agent");
			assertEquals(0,on1.getIds('agent').size())
		}

		@Test
		void flushVerticesTest() {
			def on1 = new OfferNet();
			assertNotNull(on1);
			def agent=new Agent(on1.session)
			def work = agent.ownsWork();
			def item1 = agent.addItemToWork("offers",work);
			def item2 = agent.addItemToWork("demands",work);
			assertEquals(1,on1.getIds('agent').size())
			assertEquals(1,on1.getIds('work').size())
			assertEquals(4,on1.getIds('item').size())
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
			def agent = new Agent(on.session)
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
