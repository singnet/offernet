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
		void createChainTest() {
			List chain = Utils.createChain(5)
			assertEquals(5,chain.size());
		}

		@Test
		void calculateSimilarityTest() {
//			String value1 = "000000"
//			String value2 = "111111"
			String value1 = "0111011001011100"
			String value2 = "0111111010101000"
			def d1 = Utils.calculateSimilarity(value1,value2);
			assertNotNull(d1)
			def agent = TestActorRef.create(system, Agent.props(on.session, UUID.randomUUID().toString())).underlyingActor();
            def work = agent.ownsWork(value1,value2);
            def item1 = agent.getWorksItems(work,"demands")[0]
            def item2 = agent.getWorksItems(work,"offers")[0]
			assertNotNull(item1)
			assertNotNull(item2)
			def d2 = Utils.calculateSimilarity(item1,item2);
			assertNotNull(d2)
			def delta = 0.00001;
			assertEquals(d1,d2,delta);
			assertEquals(0.6666666666666666,d1,delta); // if the similarity calculation is 'cosine'
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
}