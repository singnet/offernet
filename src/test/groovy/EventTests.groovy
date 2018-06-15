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

public class EventTests {
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
		def msg = new Method("startServer",[])
		socketServer.tell(msg,ActorRef.noSender());
		Thread.sleep(1000)
    	def socketWriter = TestActorRef.create(system, SocketWriter.props()).underlyingActor();
    	assertNotNull(socketWriter);
    	logger.info("created a new SocketWriter actor {}", socketWriter);
	}


	@Test
	void createAgentEventTest() {
		new JavaTestKit(system) {{
			String agentUUID = UUID.randomUUID().toString()
			def agentRef = system.actorOf(Agent.props(on.session, agentUUID),agentUUID);
			def socketWriterRef = system.actorOf(SocketWriter.props(on.session),"SocketWriter");
			Event event = new Event("newAgent",agentRef.underlyingActor())
			assertNotNull(event)
			assertTrue(event instanceof Event)
			String eventJson = event.toJson();
			assertNotNull(eventJson)
			assertTrue(eventJson instanceof String);
		}}
	}

	@Test
	void writeSocketTest() {
		new JavaTestKit(system) {{
			String agentUUID = UUID.randomUUID().toString()
			def agentRef = system.actorOf(Agent.props(on.session, agentUUID),agentUUID);
			def socketWriterRef = system.actorOf(SocketWriter.props(on.session),"SocketWriter");
			Event event = new Event("newAgent",agentRef.underlyingActor())
			socketWriterRef.tell(event, agentRef)
			logger.info("Sent method {} with arguments {} to actor {}", method, args, agentRef)
        	def eventJson = receiveN(1)
			assertNotNull(agentId)
			logger.info("Received message {} of type {}", agentId, agentId.getClass().getSimpleName())
			assertEquals(agentId[0], agentUUID)
			logger.info("Received {} of the actor via message is {}",method, agentId)
		}}
	}
}
