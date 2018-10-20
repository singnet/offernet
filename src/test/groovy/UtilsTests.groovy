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

import groovy.json.JsonSlurper;

import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;


public class UtilsTests {
		static private OfferNet on = new OfferNet().flushVertices();
	    static private Logger logger;
	    static ActorSystem system = ActorSystem.create("UtilsTests");

		@BeforeClass
		static void initLogging() {
		    //def config = new ConfigSlurper().parse(new File('configs/log4j-properties.groovy').toURL())
    		//PropertyConfigurator.configure(config.toProperties())
    		logger = LoggerFactory.getLogger('Tests.class');
		}

		@Test
		void generateBinaryStringTest() {
				String string = Utils.generateBinaryString(Global.parameters.binaryStringLength);
				assertEquals(16,string.length());
				assertTrue(string.toSet().sort().join() == '01' | string.toSet().sort().join() == '10');
		}

		@Test
		void generateDoubleTest() {
			Double dbl = Utils.generateDouble();
			assert dbl.getClass().getSimpleName() =="Double"
		}

		@Test
		void createChainTest() {
			List chain = Utils.createChain(5)
			assertEquals(5,chain.size());
		}

		@Test
		void formatVertexLabelTest() {
			def agent = TestActorRef.create(system, Agent.props(on.session, UUID.randomUUID().toString())).underlyingActor();
			def agentId = agent.vertexId();
			def agentLabel = Utils.formatVertexLabel(agentId);
  		    def jsonSlurper = new JsonSlurper()
	   		def fieldNames = jsonSlurper.parseText(agentId.toString());
	   		def vertexLabel = fieldNames.get('~label')+':'+fieldNames.community_id+':'+fieldNames.member_id
	   		assertEquals(vertexLabel, agentLabel)
		}

		@Test
		void formatEdgeLabelTest() {
			def agent1 = TestActorRef.create(system, Agent.props(on.session, UUID.randomUUID().toString())).underlyingActor();
			def agent2 = TestActorRef.create(system, Agent.props(on.session, UUID.randomUUID().toString())).underlyingActor();
			def edge = agent1.knowsAgent(agent2.vertexId())
			def edgeId = edge.getId();
			def edgeLabel = Utils.formatEdgeLabel(edgeId);
  		    def jsonSlurper = new JsonSlurper()
	   		def fieldNames = jsonSlurper.parseText(edgeId.toString());
	   		def graphEdgeLabel = fieldNames.get('~local_id');
	   		assertTrue(edgeLabel == graphEdgeLabel)
		}

		@Test
		void kabirSimilarityTest() {
			def valueOne = 0.7
			def valueTwo = 0.1
			def kabirSimilarity = Utils.kabirSimilarity(valueOne, valueTwo)
			assert kabirSimilarity == 0.4
		}

}