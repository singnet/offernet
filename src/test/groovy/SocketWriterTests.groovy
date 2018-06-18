/*
Copyright (c) 2018 SingularityNET

Distributed under the MIT software license, see LICENSE file
*/

package io.singularitynet.offernet

import org.apache.log4j.PropertyConfigurator
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.junit.Test;
import org.junit.BeforeClass;
import akka.testkit.TestActorRef
import akka.testkit.JavaTestKit;
import akka.actor.ActorSystem;
import akka.actor.ActorRef;
import akka.actor.UntypedAbstractActor;
import groovy.json.JsonSlurper

import akka.actor.Props;
import akka.japi.Creator;

import static org.junit.Assert.*

public class SocketWriterTests {
	static private OfferNet on = new OfferNet().flushVertices();
    static private Logger logger;
    static ActorSystem system = ActorSystem.create("EventTests");

	@BeforeClass
	static void initLogging() {
	    def config = new ConfigSlurper().parse(new File('configs/log4j-properties.groovy').toURL())
		PropertyConfigurator.configure(config.toProperties())
		logger = LoggerFactory.getLogger('EventTests.class');
	}

	@Test
	void createSocketWriterTest() {
		ActorRef socketServer = system.actorOf(DummySocketServer.props(),"DummySocketServer");
		Thread.sleep(1000)
    	def socketWriter = system.actorOf(SocketWriter.props(),"SocketWriter");
		def msg = new Method("startServer",[])
		socketWriter.tell(msg,ActorRef.noSender());
		Thread.sleep(1000);
    	assertNotNull(socketWriter);
    	logger.info("created a new SocketWriter actor {}", socketWriter);
	}


	@Test
	void createAgentEventTest() {
		ActorRef socketServer = system.actorOf(DummySocketServer.props(),"DummySocketServer");
		def msg = new Method("startServer",[])
		socketServer.tell(msg,ActorRef.noSender());
		Thread.sleep(1000)
		String agentUUID = UUID.randomUUID().toString()
		def agent = TestActorRef.create(system, Agent.props(on.session, agentUUID),agentUUID).underlyingActor();
		def socketWriterRef = system.actorOf(SocketWriter.props(),"SocketWriter");
		def eventProperties = [eventName: "newAgent",agentId: agent.id(),vertexId: agent.vertexId().toString()]
		def event = Utils.createEvent(eventProperties)
		assertNotNull(event)
		assertTrue(event instanceof String)
		logger.info("Created event object {}", event);
	}

	@Test
	void writeSocketTest() {
    	def socketWriter = system.actorOf(SocketWriter.props(),"SocketWriter");
    	/*
		def msg = new Method("startServer",[])
		socketWriter.tell(msg,ActorRef.noSender());
		Thread.sleep(1000);
    	assertNotNull(socketWriter);
    	logger.info("created a new SocketWriter actor {}", socketWriter);
		*/
		def socketClient = system.actorOf(DummySocketServer.props(),"SocketClient");
		def msg = new Method("startServer",[])
		socketClient.tell(msg,ActorRef.noSender());
		Thread.sleep(1000);
    	assertNotNull(socketClient);
	
		String agentUUID = UUID.randomUUID().toString()
		def agent = TestActorRef.create(system, Agent.props(on.session, agentUUID),agentUUID).underlyingActor();
		def eventProperties = [eventName: "newAgent",agentId: agent.id(),vertexId: agent.vertexId().toString()]
		def event = Utils.createEvent(eventProperties)
		assertNotNull(event)
		assertTrue(event instanceof String)
		logger.info("Created event object {}", event);

		for (int i=0;i<20;i++) {
			msg = new Method("writeSocket",[event])
			socketWriter.tell(msg,ActorRef.noSender());
			Thread.sleep(200);
			logger.info("sent a message for writing socket no {}",i)
		}
	}

	@Test
	void writeSocketTestToWebPage() {
		def sim = TestActorRef.create(system, Simulation.props()).underlyingActor();
		def socketWriter = system.actorOf(SocketWriter.props(),"SocketWriter");
		def msg = new Method("startServer",[])
		socketWriter.tell(msg,ActorRef.noSender());
		Thread.sleep(1000);
    	assertNotNull(socketWriter);
    	logger.info("created a new SocketWriter actor {}", socketWriter);

    	//sim.on.openVisualizationWindow();
    	//Thread.sleep(1000);

		String agentUUID = UUID.randomUUID().toString()
		def agent = TestActorRef.create(system, Agent.props(on.session, agentUUID),agentUUID).underlyingActor();
		def eventProperties = [eventName: "newAgent",agentId: agent.id(),vertexId: agent.vertexId().toString()]
		def event = Utils.createEvent(eventProperties)
		assertNotNull(event)
		assertTrue(event instanceof String)
		logger.info("Created event object {}", event);

		msg = new Method("writeSocket",[event])
		socketWriter.tell(msg,ActorRef.noSender());


	}
}
