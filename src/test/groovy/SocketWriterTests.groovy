/*
Copyright (c) 2018 SingularityNET

Distributed under the MIT software license, see LICENSE file
*/

package io.singularitynet.offernet

import org.apache.log4j.PropertyConfigurator
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.junit.Test;
import org.junit.Ignore;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import akka.testkit.TestActorRef
import akka.testkit.JavaTestKit;
import akka.actor.ActorSystem;
import akka.actor.ActorRef;
import akka.actor.UntypedAbstractActor;
import groovy.json.JsonSlurper

import akka.actor.Props;
import akka.japi.Creator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.io.*;

import static org.junit.Assert.*

import org.json.JSONObject;

import java.lang.ProcessBuilder

public class SocketWriterTests {
	static private OfferNet on = new OfferNet().flushVertices();
    static private Logger logger;
    static ActorSystem system = ActorSystem.create("EventTests");
    static Process process;

	@BeforeClass
	static void initLogging() {
		if (Global.parameters.visualizationMode) {

		    //def config = new ConfigSlurper().parse(new File('configs/log4j-properties.groovy').toURL())
			//PropertyConfigurator.configure(config.toProperties())
			logger = LoggerFactory.getLogger('SocketWriterTests.class');
			
		  String visualizationServerPath = Global.parameters.visualizationServerPath;
	      String path = System.getProperty("user.dir")+"/"+visualizationServerPath;
	      ProcessBuilder builder = new ProcessBuilder("npm", "start")
	      builder.directory(new File(path))
	      process = builder.start()
	      logger.debug("Started npm sever", path)
  		}
	}

	@AfterClass
	static void stopNPM() {
		if (Global.parameters.visualizationMode) {
		  this.process.destroy()
		 }
	}

	@Test
	void createSocketWriterTest() {
		if (Global.parameters.visualizationMode) {
	    	def socketWriter = system.actorOf(SocketWriter.props(),"SocketWriter");
			def msg = new Method("startServer",[])
			socketWriter.tell(msg,ActorRef.noSender());
			Thread.sleep(1000);
	    	assertNotNull(socketWriter);
	    	logger.debug("created a new SocketWriter actor {}", socketWriter);
	    	socketWriter.tell(akka.actor.PoisonPill.getInstance(), ActorRef.noSender());
	    	Thread.sleep(100)
	    }
	}


	@Test
	void createAgentEventTest() {
		if (Global.parameters.visualizationMode) {
			String agentUUID = UUID.randomUUID().toString()
			def agent = TestActorRef.create(system, Agent.props(on.session, agentUUID),agentUUID).underlyingActor();
			def eventProperties = [eventName: "newAgent",agentId: agent.id(),vertexId: agent.vertexId().toString()]
			def event = Utils.createEvent("newAgent",eventProperties)
			assertNotNull(event)
			assertTrue(event instanceof String)
			logger.debug("Created event object {}", event);
		}
	}

	@Test
	void writeSocketTest() {
		if (Global.parameters.visualizationMode) {
	    	def socketWriter = system.actorOf(SocketWriter.props(),"SocketWriter");
	    	/*
			def msg = new Method("startServer",[])
			socketWriter.tell(msg,ActorRef.noSender());
			Thread.sleep(1000);
	    	assertNotNull(socketWriter);
	    	logger.debug("created a new SocketWriter actor {}", socketWriter);
			*/
			String agentUUID = UUID.randomUUID().toString()
			def agent = TestActorRef.create(system, Agent.props(on.session, agentUUID),agentUUID).underlyingActor();
			def eventProperties = [eventName: "newAgent",agentId: agent.id(),vertexId: agent.vertexId().toString()]
			def event = Utils.createEvent("newAgent",eventProperties)
			assertNotNull(event)
			assertTrue(event instanceof String)
			logger.debug("Created event object {}", event);

			/*
			while(true) {
				//do nothing
			}
			*/
			def msg;
			for (int i=0;i<10;i++) {
				msg = new Method("writeSocket",[event])
				socketWriter.tell(msg,ActorRef.noSender());
				Thread.sleep(100);
				logger.debug("sent a message for writing socket no {}",i)
			}

	    	socketWriter.tell(akka.actor.PoisonPill.getInstance(), ActorRef.noSender());
	    	Thread.sleep(100)
	    }

	}

	@Test
	void writeSocketTestToWebPage() {
		if (Global.parameters.visualizationMode) { 
			def sim = TestActorRef.create(system, Simulation.props()).underlyingActor();
			def socketWriter = system.actorOf(SocketWriter.props());
			socketWriter.tell(new Method("createSocket",[]),ActorRef.noSender());

			String agentUUID = UUID.randomUUID().toString()
			def agent = TestActorRef.create(system, Agent.props(on.session, agentUUID),agentUUID).underlyingActor();
			def eventProperties = [eventName: "newAgent",agentId: agent.id(),vertexId: agent.vertexId().toString()]
			def event = Utils.createEvent("newAgent",eventProperties)
			assertNotNull(event)
			assertTrue(event instanceof String)
			logger.debug("Created event object {}", event);

			for (int i=0;i<10;i++) {
				socketWriter.tell(new Method("writeSocket",[event]),ActorRef.noSender());
				Thread.sleep(200);
				logger.debug("sent a message for writing socket no {}",i)
			}

	    	socketWriter.tell(akka.actor.PoisonPill.getInstance(), ActorRef.noSender());
	    	Thread.sleep(100)
	    }
	}

//    @Ignore
	@Test
	void visualizationTest() {
		def sim = TestActorRef.create(system, Simulation.props()).underlyingActor();
		sim.on.flushVertices();
		sim.createAgentNetwork(100);
	}

}
