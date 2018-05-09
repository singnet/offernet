package io.singularitynet.offernet;

import io.singularitynet.offernet.OfferNet;
import io.singularitynet.offernet.Simulation;

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

import com.datastax.driver.dse.graph.Edge;

public class AgentBehaviorStepDefs {
	private Simulation sim;
	private Scenario thisScenario;
	private ActorSystem system;


    @Before
    public void before(Scenario scenario) throws Throwable {
    	this.thisScenario = scenario;
    	this.system = ActorSystem.create();
    	this.sim = (Simulation) TestActorRef.create(this.system, Simulation.props()).underlyingActor();
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

}
