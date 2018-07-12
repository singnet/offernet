package io.singularitynet.offernet;

import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.And;
import cucumber.api.java.After;
import cucumber.api.java.Before;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import akka.testkit.TestActorRef;
import akka.actor.ActorSystem;
import akka.actor.ActorRef;
import akka.actor.Actor;
import akka.actor.PoisonPill;
import akka.util.Timeout;
import akka.pattern.Patterns;

import scala.concurrent.Future;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Enumeration;

import com.datastax.driver.dse.graph.Edge;
import com.datastax.driver.dse.graph.Vertex;

import java.util.Random;

public class AgentBehaviorStepDefs {
	private Simulation sim;
	private Scenario thisScenario;
	private ActorSystem system;
  private ArrayList<Object> exchangeBin;

    @Before
    public void before(Scenario scenario) throws Throwable {
    	this.thisScenario = scenario;
    	this.system = ActorSystem.create("AgentBehaviorStepDefs");
    	this.sim = (Simulation) TestActorRef.create(this.system, Simulation.props()).underlyingActor();
      this.exchangeBin = new ArrayList<Object>();
    }

    @After
    public void after(Scenario scenario) throws Throwable {
		// this.on.flushVertices();
    }

    /*
    * Scenario: an agent can introduce a new agent to the network by 'befriending' it
    */

	@Then("^Agent \"(.*?)\" exists on the OfferNet$")
	public void agentExistsOnTheOfferNet(String agentId) throws Throwable {
	   	boolean exists = this.sim.agentExists(agentId);
	    assertTrue(exists);
	}

	@And("^Agent \"(.*?)\" does not exist on the OfferNet$")
	public void agentDoesNotExistOnTheOfferNet(String agentId) throws Throwable {
	   	boolean exists = this.sim.agentExists(agentId);
	    assertFalse(exists);
	}


    /*
    * Scenario: an agent can 'befriend' another agent existing in the network
    */
    
	@Given("^Agent \"(.*?)\" is created on the OfferNet$")
	public void agentIsCreatedOnTheOfferNet(String agentId) throws Throwable {
	   	ActorRef actorRef = this.sim.createAgentWithId(agentId);
	    assertNotNull(actorRef);
	    actorRef.tell(akka.actor.PoisonPill.getInstance(), ActorRef.noSender());
	    Thread.sleep(1000);
	}

	@Given("^there is no \"(.*?)\" link between \"(.*?)\" and \"(.*?)\"$")
	public void thereIsNoLinkBetweenAnd(String linkName, String agentOne, String agentTwo) throws Throwable {
	    ActorRef actorOneRef = this.sim.createAgentWithId(agentOne);
	    ActorRef actorTwoRef = this.sim.createAgentWithId(agentTwo);
	    Method msg = new Method("getAllOutEdges", new ArrayList(){{add(linkName);}});
	    Timeout timeout = new Timeout(Duration.create(5, "seconds"));
	   	Future<Object> future = Patterns.ask(actorOneRef, msg, timeout);
  		List edges = (List<Edge>) Await.result(future, timeout.duration());
  		assertTrue(edges.size() == 0);
  		actorOneRef.tell(akka.actor.PoisonPill.getInstance(), ActorRef.noSender());
  		actorTwoRef.tell(akka.actor.PoisonPill.getInstance(), ActorRef.noSender());
  		Thread.sleep(1000);
	}

	@When("^Agent \"(.*?)\" befriends Agent \"(.*?)\"$")
	public void agentBefriendsAgent(String agentOne, String agentTwo) throws Throwable {
	    ActorRef actorOneRef = this.sim.createAgentWithId(agentOne);
	    ActorRef actorTwoRef = this.sim.createAgentWithId(agentTwo);

	    Method msg = new Method("vertexId", new ArrayList());
  	  Timeout timeout = new Timeout(Duration.create(5, "seconds"));
	   	Future<Object> future = Patterns.ask(actorOneRef, msg, timeout);
  		Object actorTwoVertexId = Await.result(future, timeout.duration());
  		assertNotNull(actorTwoVertexId);

	    msg = new Method("knowsAgent", new ArrayList(){{add(actorTwoVertexId);}});
	    timeout = new Timeout(Duration.create(5, "seconds"));
	   	future = Patterns.ask(actorOneRef, msg, timeout);
  		Edge knowsEdge = (Edge) Await.result(future, timeout.duration());
  		assertTrue(knowsEdge.getLabel().equals("knows"));
  		actorOneRef.tell(akka.actor.PoisonPill.getInstance(), ActorRef.noSender());
  		actorTwoRef.tell(akka.actor.PoisonPill.getInstance(), ActorRef.noSender());
  		Thread.sleep(1000);
	}

	@Then("^there is \"(.*?)\" link between \"(.*?)\" and \"(.*?)\"$")
	public void thereIsLinkBetweenAnd(String linkName, String agentOne, String agentTwo) throws Throwable {
	    ActorRef actorOneRef = this.sim.createAgentWithId(agentOne);
	    ActorRef actorTwoRef = this.sim.createAgentWithId(agentTwo);
	    Method msg = new Method("getAllOutEdges", new ArrayList(){{add(linkName);}});
	    Timeout timeout = new Timeout(Duration.create(5, "seconds"));
	   	Future<Object> future = Patterns.ask(actorOneRef, msg, timeout);
  		List edges = (List<Edge>) Await.result(future, timeout.duration());
  		assertTrue(edges.size() == 1);
  		assertTrue(((Edge) edges.get(0)).getLabel().equals(linkName));
  		actorOneRef.tell(akka.actor.PoisonPill.getInstance(), ActorRef.noSender());
  		actorTwoRef.tell(akka.actor.PoisonPill.getInstance(), ActorRef.noSender());
  		Thread.sleep(1000);
	}

    /*
    * Scenario: an agent can create a work (offer-demand) pair
    */

    @When("^Agent \"([^\"]*)\" posts a pair of offer \"([^\"]*)\" and demand of \"([^\"]*)\"$")
    public void agent_publishes_a_pair_of_offer_and_demand_of(String agentLabel, String offerValue, String demandValue) throws Throwable {
        ActorRef actorRef = this.sim.createAgentWithId(agentLabel);
        assertNotNull(actorRef);
	    Method msg = new Method("ownsWork", new ArrayList(){{add(demandValue);add(offerValue);}});
	    Timeout timeout = new Timeout(Duration.create(5, "seconds"));
	   	Future<Object> future = Patterns.ask(actorRef, msg, timeout);
  		Vertex work = (Vertex) Await.result(future, timeout.duration());
  		assertTrue(work.getLabel().equals("work"));
  		actorRef.tell(akka.actor.PoisonPill.getInstance(), ActorRef.noSender());
  		Thread.sleep(1000);
    }

    @Then("^Agent \"([^\"]*)\" owns a work which \"([^\"]*)\" item \"([^\"]*)\"$")
    public void agent_owns_a_work_which_item(String agentLabel, String ownsLinkType, String itemValue) throws Throwable {

    	// get the actor:
    	ActorRef actorRef = this.sim.createAgentWithId(agentLabel);
    	assertNotNull(actorRef);

	   	// get the work of an actor
	    Method msg = new Method("getWorks", new ArrayList());
	    Timeout timeout = new Timeout(Duration.create(5, "seconds"));
	   	Future<Object> future = Patterns.ask(actorRef, msg, timeout);
	   	List<Vertex> works = (List<Vertex>) Await.result(future, timeout.duration());
	   	assertTrue(works.size() == 1);
	   	Vertex work = works.get(0);
	   	assertTrue(work.getLabel().equals("work"));

	   	// get item and check its label
	    msg = new Method("getWorksItems", new ArrayList(){{add(work);add(ownsLinkType);}});
	    timeout = new Timeout(Duration.create(5, "seconds"));
	   	future = Patterns.ask(actorRef, msg, timeout);
	   	List<Vertex> items = (List<Vertex>) Await.result(future, timeout.duration());
	   	assertTrue(items.size() == 1);
	   	Vertex item = items.get(0);
	   	assertTrue(item.getLabel().equals("item"));
	   	assertTrue(item.getProperty("value").getValue().asString().equals(itemValue));

   		actorRef.tell(akka.actor.PoisonPill.getInstance(), ActorRef.noSender());
   		Thread.sleep(1000);
    }

    /*
    * Scenario Outline: an agent can access all posted offers and demands by traversing its social network
    */

    @Given("^there exists an sequentially connected line of '(.+)' agents$")
    public void there_exists_an_sequentially_connected_line_of_numberOfAgents_agents(Integer numberOfAgents) throws Throwable {
        List<Vertex> agentList = this.sim.createAgentLine(numberOfAgents);
        assertNotNull(agentList);
        assertTrue(agentList.size() == numberOfAgents);
    }

    @Given("^all agents have posted '(.+)' offer-demand pair each$")
    public void all_agents_have_posted_pairsPerAgent_offer_demand_pair_each(Integer pairsPerAgent) throws Throwable {
    	Method msg;
    	Timeout timeout;
    	Future<Object> future;
    	Vertex work;
    	Enumeration<ActorRef> actorRefs = this.sim.actorRefToVertexIdTable.keys();
    	int numberOfWorks = 0;
    	int numberOfActors = 0;
    	while (actorRefs.hasMoreElements()) {
    		ActorRef actorRef = actorRefs.nextElement();
    		for (int i = 0; i<pairsPerAgent; i++) {
    			msg = new Method("ownsWork", new ArrayList());
	    		timeout = new Timeout(Duration.create(5, "seconds"));
	   			future = Patterns.ask(actorRef, msg, timeout);
  				work = (Vertex) Await.result(future, timeout.duration());
  				assertEquals(work.getLabel(),"work");
  				numberOfWorks = numberOfWorks +1;
  			}
        numberOfActors = numberOfActors +1;
		}
		assertTrue(numberOfWorks == numberOfActors * pairsPerAgent);
    Thread.sleep(1000);
    }

    @When("^first agent initiates search of '(.+)' depth$")
    public void first_agent_initiates_search_of_searchDepth_depth(Integer searchDepth) throws Throwable {
      // get the actor-0 which is the first in line:
      ActorRef actorRef = this.sim.agentIdToActorRefTable.get("agent-0");
      assertNotNull(actorRef);
      
      // initiate the search and get results back
      Method msg = new Method("itemsOfKnownAgents", new ArrayList(){{add(searchDepth);}});
      Timeout timeout = new Timeout(Duration.create(5, "seconds"));
      Future<Object> future = Patterns.ask(actorRef, msg, timeout);
      List<Vertex> items = (List<Vertex>) Await.result(future, timeout.duration());
      assertNotNull(items);

      exchangeBin.add(items);
      assertEquals(exchangeBin.size(), 1);
      Thread.sleep(2000);
    }

    @Then("^agent finds '(.+)' demands and offers in its social network$")
    public void agent_finds_numberOfFoundItems_in_its_social_network(Integer numberOfFoundItems) throws Throwable {
        // get items collected in the previous step;
        ArrayList<Vertex> items = (ArrayList<Vertex>) this.exchangeBin.get(0);
        assertNotNull(items);
        assertEquals((long) numberOfFoundItems, (long) items.size());
    }

    /*
    * Scenario Outline: an agent can connect similar offers and demands of its social network
    */

    @Given("^a connected network of '(\\d+)' agents$")
    public void a_connected_network_of_agents(int numberOfAgents) throws Throwable {
      List<ActorRef> agentList = sim.createAgentNetwork(numberOfAgents);
      assertEquals(numberOfAgents, sim.vertexIdToActorRefTable.size());
    }

    @Given("^that '(\\d+)' similar item pairs exist in the network$")
    public void that_similar_items_exist_in_the_network(int numberOfSimilarItems) throws Throwable {
      int chainLength = numberOfSimilarItems +2;
      List chain = Utils.createChain(chainLength);
      List works = sim.addChainToNetwork(chain);
    }

    @When("^any agent finds similar items by executing search of '(\\d+)' depth$")
    public void any_agent_finds_similar_items_by_executing_search_of_depth(int searchDepth) throws Throwable {
        Object[] actorRefs = sim.actorRefToVertexIdTable.keySet().toArray();
        Integer allSimilarItems = 0;
        for (Object actor : actorRefs) {
            ActorRef agent = (ActorRef) actor;
            double similarityThreshold = 0.999d;  
            Method msg = new Method("searchAndConnect", new ArrayList(){{add(similarityThreshold); add(searchDepth);}});
            Timeout timeout = new Timeout(Duration.create(5, "seconds"));
            Future<Object> future = Patterns.ask(agent, msg, timeout);
            Integer items = (Integer) Await.result(future, timeout.duration());
            assertNotNull(items);
            allSimilarItems = allSimilarItems + items;
        }
    }

    @Then("^there are '(\\d+)' similarity relations in the network$")
    public void there_are_similarity_relations_in_the_network(int numberOfSimilarityRelations) throws Throwable {
        ArrayList<Edge> edges = ( ArrayList<Edge> ) sim.on.getEdges("similarity");
        assertEquals(numberOfSimilarityRelations, edges.size()/2);
    }


}




