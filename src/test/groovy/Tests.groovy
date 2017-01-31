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
			def similarity = Utils.calculateSimilarity(item1,item2);
			def similarityEdge = item1.connect(item1, similarity);
			assertNotNull(similarityEdge);
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

		@Test
		void searchAndConnectTest() {
			on.flushVertices("agent");
			def agent1 = new Agent(on.session);
			agent1.ownsWork(new Work([new Item ('111110',on.session)],[new Item ('000000',on.session)],on.session));
			def agent2 = new Agent(on.session);
			agent2.ownsWork(new Work([new Item ('111100',on.session)],[new Item ('110000',on.session)],on.session));
			def agent3 = new Agent(on.session);
			agent3.ownsWork(new Work([new Item ('100000',on.session)],[new Item ('111100',on.session)],on.session));
			def agent4 = new Agent(on.session);
			agent4.ownsWork(new Work([new Item ('111110',on.session)],[new Item ('000000',on.session)],on.session));

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
			assertTrue(worksVertexList.contains(work1.vertex));
			assertTrue(worksVertexList.contains(work2.vertex));
			assertTrue(worksVertexList.contains(work3.vertex));

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
		void createWorkFromExistingVertexTest() {
			def work1 = new Work(on.session);
			assertNotNull(work1);
			def vertex1 = work1.vertex;
			assertNotNull(vertex1);

			def work2 = new Work(vertex1,on.session);
			assertNotNull(work2);
			assertEquals(work1.vertex,work2.vertex);

		}

	    @Test 
	    void createWorkWithKnownItemsTest() {
			def demand = new Item(on.session);
			def offer = new Item(on.session);

			def work = new Work([demand],[offer],on.session);

			assertEquals(work.getDemands()[0].getId(),demand.id());
			assertEquals(work.getOffers()[0].getId(),offer.id());
	    	
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
			def item1 = new Item(value1,on.session);
			assertNotNull(item1)
			def item2 = new Item(value2,on.session);
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
