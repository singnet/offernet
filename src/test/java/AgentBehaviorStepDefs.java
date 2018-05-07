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
    
	@Given("^Agent \"(.*?)\" exists on the OfferNet$")
	public void agentExistsOnTheOfferNet(String agentId) throws Throwable {
	   	ActorRef actorRef = this.sim.createAgentWithId(agentId);
	    assertNotNull(actorRef);
	    actorRef.tell(akka.actor.PoisonPill.getInstance(), ActorRef.noSender());
	}

	@Given("^there is no \"(.*?)\" link between \"(.*?)\" and \"(.*?)\"$")
	public void thereIsNoLinkBetweenAnd(String linkName, String agentOne, String agentTwo) throws Throwable {
	    ActorRef actorOneRef = this.sim.createAgentWithId(agentOne);
	    ActorRef actorTwoRef = this.sim.createAgentWithId(agentTwo);
	    Method msg = new Method("getAllOutEdges", new ArrayList(){{add("knows");}});
	    Timeout timeout = new Timeout(Duration.create(5, "seconds"));
	   	Future<Object> future = Patterns.ask(actorOneRef, msg, timeout);
  		List edges = (List<Edge>) Await.result(future, timeout.duration());
  		assertTrue(edges.size() == 0);
  		actorOneRef.tell(akka.actor.PoisonPill.getInstance(), ActorRef.noSender());
  		actorTwoRef.tell(akka.actor.PoisonPill.getInstance(), ActorRef.noSender());
	}

	@When("^Agent \"(.*?)\" befriends Agent \"(.*?)\"$")
	public void agentBefriendsAgent(String arg1, String arg2) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Then("^there is \"(.*?)\" link between \"(.*?)\" and \"(.*?)\"$")
	public void thereIsLinkBetweenAnd(String arg1, String arg2, String arg3) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

}
