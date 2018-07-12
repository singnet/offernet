/*
Copyright (c) 2018 SingularityNET

Distributed under the MIT software license, see LICENSE file
*/

package io.singularitynet.offernet

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

public class MethodTests {
	static private OfferNet on = new OfferNet().flushVertices();
    static private Logger logger;
    static ActorSystem system = ActorSystem.create("MethodTests");

	@BeforeClass
	static void initLogging() {
	    //def config = new ConfigSlurper().parse(new File('configs/log4j-properties.groovy').toURL())
		//PropertyConfigurator.configure(config.toProperties())
		logger = LoggerFactory.getLogger('MethodTests.class');
	}

	@Test
	void sendMethod() {
		new JavaTestKit(system) {{
			String agentUUID = UUID.randomUUID().toString()
			def agentRef = system.actorOf(Agent.props(on.session, agentUUID),agentUUID);
			String method = "id"
			def args = [];
			agentRef.tell(new Method(method,args),getRef())
			logger.trace("Sent method {} with arguments {} to actor {}", method, args, agentRef)
        	def agentId = receiveN(1)
			assertNotNull(agentId)
			logger.trace("Received message {} of type {}", agentId, agentId.getClass().getSimpleName())
			assertEquals(agentId[0], agentUUID)
			logger.trace("Received {} of the actor via message is {}",method, agentId)
		}}
	}

}