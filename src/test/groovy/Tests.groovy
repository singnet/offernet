package net.vveitas.offernet

import org.apache.log4j.PropertyConfigurator
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static org.junit.Assert.*

import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;

public class Tests {
		private OfferNet on = new OfferNet().flushVertices();

		/*
		* Item.groovy
		*/

		@Test
		void createItemTest() {
				def item = new Item(on.session);
				assertNotNull(item)
				println "createdItem: "+item;
		}

		@Test
		void getItemIdTest() {
			def id = new Item(on.session).id();
			assertNotNull(id);
			println "got Item.id: "+id;
		}

		@Test
		void getItemPropertiesTest() {
			def properties = new Item(on.session).getProperties();
			assertNotNull(properties);
			println "got item properties: "+properties;
		}

		@Test
		void getItemValueTest() {
			def value = new Item(on.session).getValue();
			assertNotNull(value);
			println "got item value: "+value;
		}


		@Test
		void connectTest() {
			def item1 = new Item(on.session);
			def item2 = new Item (on.session);
			def distance = Utils.calculateDistance(item1,item2);
			def similarityEdge = item1.connect(item1, distance);
			assertNotNull(similarityEdge);
		}

		@Test
		void reciprocalDistanceLinkTest() {
			def item1 = new Item(on.session);
			def item2 = new Item (on.session);
			def distance = Utils.calculateDistance(item1,item2);
			def similarityEdge = item1.connect(item1, distance);
			assertNotNull(similarityEdge);

			def d1 =item1.existsDistance(item2);
			assertNotNull(d1)
			def d2 = item2.existsDistance(item1);
			assertNotNull(d2)
			assertEquals(d1,d2)

		}


		@Test
		void existsDistanceTest() {
			def item1 = new Item(on.session);
			def item2 = new Item (on.session);
			def d1 = Utils.calculateDistance(item1,item2);
			def similarityEdge = item1.connect(item2, d1);
			assertNotNull(similarityEdge);

			def d2 = item1.existsDistance(item2);
			assertNotNull(d2)
			assertEquals(d1,d2)
		}

		@Test
		void connectIfSimilarTest() {
			def item1 = new Item(on.session);
			def item2 = new Item (on.session);
			def distance = Utils.calculateDistance(item1,item2);
			def connectedEdge = item1.connect(item1, distance-1);
			assertNotNull(similarityEdge);
			assertEquals(distance-1,similarityEdge.getProperty('value'))

			connectedEdge = item1.connect(item1, distance+1);
			assertNull(connectedEdge);
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

				def work = new Work(on.session);
				assertNotNull(work);

				def edge = agent1.ownsWork(work);
				assertNotNull(edge);
		}

		@Test
		void allItemsTest() {
				def agent=new Agent(on.session)
				assertNotNull(agent);

				def work = agent.ownsWork();
				assertNotNull(work);

				work.addDemand();
				work.addOffer();

				assertEquals(4,agent.allItems().size())
		}

		/*
		* Work.groovy
		*/

		@Test
    void createWorkTest() {
        def work = new Work(on.session);
        assertNotNull(work);
    }

		@Test
		void getWorkIdTest() {
			def id = new Work(on.session).id();
			assertNotNull(id);
			println "got Work.id: "+id;
		}

		@Test
		void addNewOfferTest() {
			def work = new Work(on.session)
			def offer = work.addOffer();
			assertNotNull(offer);
		}

		@Test
		void addKnownOfferTest() {
			def work = new Work(on.session)
			def offer = new Item(on.session);
			assertEquals(offer,work.addOffer(offer));
		}

		@Test
		void addNewDemandTest() {
			def work = new Work(on.session)
			def demand = work.addDemand();
			assertNotNull(demand);
		}

		@Test
		void addKnownDemandTest() {
			def work = new Work(on.session)
			def demand = new Item(on.session);
			assertEquals(demand,work.addDemand(demand));
		}

		@Test
		void getWorkItemsTest() {
			 def work = new Work(on.session);
			 def item1 = new Item(on.session);
			 work.addItem(item1, "demands");

			 def item2 = new Item(on.session);
			 work.addItem(item2, "demands");

			 def demands = work.getItems("demands");
			 assertEquals(3,demands.size()); // tree because one is created by default in Work constructor

			 def offers = work.getItems("offers");
			 assertEquals(1,offers.size());

		}

		@Test
		void getDemandsTest() {
			def work = new Work(on.session);
			work.addDemand();
			work.addOffer();

			def demands = work.getDemands()
			assertEquals(2,demands.size());
		}

		@Test
		void getOffersTest() {
			def work = new Work(on.session);
			work.addDemand();
			work.addOffer();

			def offers = work.getOffers()
			assertEquals(2,offers.size());
		}

		/*
		*	OfferNet.class
		*/

		@Test
		void createOfferNetworkTest() {
			def on = new OfferNet()
			assertNotNull(on);
			assertFalse(on.session.isClosed());
			assertFalse(on.cluster.isClosed());
		}

		@Test
		void closeOfferNetworkTest() {
			def on = new OfferNet()
			assertNotNull(on);
			on.close();
			assertTrue(on.session.isClosed());
			assertTrue(on.cluster.isClosed());
		}

		@Test
		void flushAgentsTest() {
			def on = new OfferNet()
			assertNotNull(on);
			on.flushVertices("agent");
			assertEquals(0,on.getIds('agent').size())
		}

		@Test
		void flushVerticesTest() {
			def on = new OfferNet();
			assertNotNull(on);
			on.flushVertices();
			assertEquals(0,on.getIds('item').size())
		}

		@Test
		void getIdsTest() {
			def on = new OfferNet()
			on.flushVertices('agent');
			on.createAgentNetwork(10)

			def agentIds = on.getIds('agent');
			assertNotNull(agentIds);
			assertEquals(10,agentIds.size());
		}


		@Test
		void createAgentNetworkTest() {
			def on = new OfferNet()
			on.createAgentNetwork(10)
		}

		@Test
		void addRandomWorksToAgentsTest() {
			def on = new OfferNet()
			on.flushVertices();

			on.createAgentNetwork(10)
			on.addRandomWorksToAgents(10)
			assertEquals(20,on.getIds("item").size()); // creates two items (demand and offer) when creating a ramdom work;
		}

		@Test
		void createChainToNetworkTest() {

		}

		/*
		*	Utils.class
		*/

		@Test
		void generateBinaryStringTest() {
				String string = Utils.generateBinaryString(16);
				assertEquals(16,string.length());
				assertTrue(string.toSet().sort().join() == '01' | string.toSet().sort().join() == '10');
		}

		@Test
		void createChainTest() {
			List chain = Utils.createChain(5)
			assertEquals(5,chain.size());
		}

		@Test
		void calculateDistanceTest() {
			def value1 = '000000'
			def value2 = '000111'
			def d1 = Utils.hammingDistance(value1,value2);
			assertNotNull(d1)
			def item1 = new Item(value1,on.session);
			assertNotNull(item1)
			def item2 = new Item(value2,on.session);
			assertNotNull(item2)
			def d2 = Utils.calculateDistance(item1,item2);
			assertNotNull(d2)
			assertEquals(d1,d2);

		}

}
