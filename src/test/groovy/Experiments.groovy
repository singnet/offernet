package io.singularitynet.offernet

import org.apache.log4j.PropertyConfigurator
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static org.junit.Assert.*
import org.junit.Test;
import org.junit.Ignore;
import org.junit.BeforeClass;
import org.junit.AfterClass;

import kamon.Kamon;
import kamon.prometheus.PrometheusReporter;
//import kamon.jaeger.JaegerReporter;
import kamon.zipkin.ZipkinReporter;

import akka.actor.Props
import akka.actor.ActorSystem;
import akka.actor.ActorRef;

import java.text.SimpleDateFormat;
import akka.pattern.Patterns;
import scala.concurrent.Future;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;
import akka.util.Timeout;

import com.datastax.driver.dse.graph.Vertex;
import com.datastax.driver.dse.graph.Edge;

import groovy.json.JsonSlurper;

import akka.testkit.TestActorRef
import akka.testkit.JavaTestKit;



public class Experiments {
	static ActorSystem system = ActorSystem.create("SimulationTests");
	static private Logger logger;

	@BeforeClass
	static void initLogging() {
	    def config = new ConfigSlurper().parse(new File('configs/log4j-properties.groovy').toURL())
        PropertyConfigurator.configure(config.toProperties())
   	    logger = LoggerFactory.getLogger('Experiments.class');

	}

	@Test
	void compareDecentralizedAndCentralizedSearch() {

		/*
		* Constructing the parameter matrix:
		* both centralized and decentralized search will be run on every 
		* permutation of these parameters;
		*/

		String experimentId = 'EXP'+(new SimpleDateFormat("MM-dd-hh-mm").format(new Date())) +"-"+ Utils.generateRandomString(6);
	
		def agentNumbers = [2000] // number of agents in the network
		def chainLengths = [5] // the length of the chain to drop into the network;
		def randomWorksNumberMultipliers = [1] // number of random works (outside chain) to drop into the network;
		def maxDistances = [2] // the maximum number of hops when doing decentralized similarity search;
		def similaritySearchThresholds = [0.99] // consider only items that are this similar when searching for path;

		logger.warn('method={} : experimentId={} : agentNumbers={} : chainLengths={} : randomWorksNumberMultipliers={} : maxDistances={} : similaritySearchThresholds={}', 
      		Utils.getCurrentMethodName(), 
      		experimentId,
      		agentNumbers,
      		chainLengths,
      		randomWorksNumberMultipliers,
      		maxDistances,
      		similaritySearchThresholds,
      	)

		agentNumbers.each { agentNumber ->
			chainLengths.each { chainLength ->
				randomWorksNumberMultipliers.each { randomWorksNumberMultiplier ->
					def randomWorksNumber = agentNumber*randomWorksNumberMultiplier;
					maxDistances.each { maxDistance ->
						similaritySearchThresholds.each { similaritySearchThreshold ->
							// This is actual experiment code; 
							// The choice is to construct the whole structure anew for each run
							// It is not strictly necessary, yet may be good for equalizing performance

							Global.parameters.similaritySearchThreshold = similaritySearchThreshold

					       	// 1: create simulation object
							def sim = TestActorRef.create(system, Simulation.props()).underlyingActor();
							assertNotNull(sim);
							sim.on.flushVertices();

							logger.info('experimentId={} : simulationId={} : agentNumber={} : chainLength={} : randomWorksNumberMultiplier={} : maxDistance={} : similaritySearchThreshold={}',
								experimentId,
								Global.parameters.simulationId,
					     		agentNumber,
					      		chainLength,
					      		randomWorksNumberMultiplier,
					      		maxDistance,
					      		similaritySearchThreshold
					      	)								 
			
							// 2: create agent network
							def agentList = sim.createAgentNetwork(agentNumber);

							// 3: add random works to the agents in the network
							sim.addRandomWorksToAgents(randomWorksNumber)

							// 4: create chain and assign its items to random agents
							def chains = [Utils.createChain(chainLength)]
							def chain = chains[0]
							def chainedWorksJson = sim.addChainToNetwork(chain, true)  // add chain to network and return json structure...

							// 5: create taskAgent -- the one which closes the chain in the network
							def taskAgent = createTaskAgentInTheNetwork(sim,chain)

							// 6: running decentralizedCycleSearch
								// 6.1: connecting all similar items in decentralized way:
								sim.centralizedSimilaritySearchAndConnect()
								//sim.decentralizedSimilaritySearchAndConnect(maxDistance)

							//	Thread.sleep(3000)

								// 6.1: looking for a cycle:
								sim.decentralizedCycleSearch(taskAgent, chainedWorksJson)

							// 7: removing all similarity connections
							// So that centralized search could be run in full on the same network
							//sim.on.removeEdges("similarity")

							// 8: running centralizedCycleSearch
								//8.1: connecting all similar items in centralized way:
								//sim.centralizedSimilaritySearchAndConnect()
								//8.2.1: looking for a cycle Naive:
								sim.allCyclesCentralized(similaritySearchThreshold, chainedWorksJson, 1)
								//8.2.2: looking for a cycle depthFirstSerch:
								sim.allCyclesCentralized(similaritySearchThreshold, chainedWorksJson, 2)
						}
					}
				}
			}
		}
	}

	ActorRef createTaskAgentInTheNetwork(Simulation sim, List chain) {
      // create agent that has a work which closes the chain into the cycle
      // this agent will have the last item in he chain as demand
      // and the first item in the chain as offer
      def vertexIdList = new ArrayList(sim.vertexIdToActorRefTable.keySet())
      def taskAgent = sim.createAgent()
      def randomAgent = vertexIdList[new Random().nextInt(vertexIdList.size())]
      Method msg = new Method("knowsAgent", new ArrayList(){{add(randomAgent)}});
      Timeout timeout = new Timeout(Duration.create(5, "seconds"));
      Future<Object> future = Patterns.ask(taskAgent, msg, timeout);
      def knowsEdge = (Edge) Await.result(future, timeout.duration());
      assertNotNull(knowsEdge);

      msg = new Method("ownsWork", new ArrayList(){{add(chain[-1]);add(chain[0])}});
      timeout = new Timeout(Duration.create(5, "seconds"));
      future = Patterns.ask(taskAgent, msg, timeout);
      Vertex taskWork = (Vertex) Await.result(future, timeout.duration());
      assertNotNull(taskWork);

      return taskAgent
    }


}