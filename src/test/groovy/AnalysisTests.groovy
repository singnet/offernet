/*
Copyright (c) 2018 SingularityNET

Distributed under the MIT software license, see LICENSE file
*/

package io.singularitynet.offernet

import static org.junit.Assert.*
import static org.hamcrest.CoreMatchers.instanceOf;

import org.junit.Test;
import org.junit.Ignore;
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

import kamon.Kamon;
import kamon.prometheus.PrometheusReporter;
import kamon.jaeger.JaegerReporter;

import java.util.UUID;

public class AnalysisTests {
	static private OfferNet on = new OfferNet().flushVertices();


	// this tests lasts forever -- for testing message tracing servers which take time to initialize
	@Ignore
	@Test
	void sendMessages() {
		ArrayList allActors = new ArrayList();
		def actor1 = on.system.actorOf(Agent.props(on.session, UUID.randomUUID().toString()),UUID.randomUUID().toString());
		allActors.add(actor1)
		def actor2 = on.system.actorOf(Agent.props(on.session, UUID.randomUUID().toString()),UUID.randomUUID().toString());
		allActors.add(actor2)
		def actor3 = on.system.actorOf(Agent.props(on.session, UUID.randomUUID().toString()),UUID.randomUUID().toString());
		allActors.add(actor3)
	    Random random = new Random();


      while(true) {
        ActorRef randomActor = (ActorRef) allActors.get(random.nextInt(allActors.size()));
   		randomActor.tell(new Method("id",[]),ActorRef.noSender())
        Kamon.counter("telling.actor").increment();
        Thread.sleep(100);
      }
	}
}

