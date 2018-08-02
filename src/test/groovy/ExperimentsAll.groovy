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
import akka.testkit.javadsl.TestKit;



public class ExperimentsAll {
	static ActorSystem system = ActorSystem.create("SimulationTests");
	static private Logger logger;
	private Simulation sim;
	Integer timeout = 3600//18000 // timeout of a simulation in seconds (5 hours );

	@BeforeClass
	static void initLogging() {
	    def config = new ConfigSlurper().parse(new File('configs/log4j-properties.groovy').toURL())
        PropertyConfigurator.configure(config.toProperties())
   	    logger = LoggerFactory.getLogger('Experiments.class');

	}

	@AfterClass
  	static void teardown() {
    	TestKit.shutdownActorSystem(system, true);
    	system = null;
  	}

	@Test
	void run() {

		/*
		* Constructing the parameter matrix:
		* both centralized and decentralized search will be run on every 
		* permutation of these parameters;
		*/

		String experimentId = 'EXP'+(new SimpleDateFormat("MM-dd-hh-mm").format(new Date())) +"-"+ Utils.generateRandomString(6);
	
		def agentNumbers = [100, 200, 500]//, 1000, 2000, 5000] // number of agents in the network
		def chainLengths = [10, 20] // the length of the chain to drop into the network (cannot be less than 3!)
		def randomWorksNumberMultipliers = [2] // number of random works (outside chain) to drop into the network;
		def maxDistances = [10, 30] // the maximum number of hops when doing decentralized similarity search;
		def similaritySearchThresholds = [1] // consider only items that are this similar when searching for path;
		 

		logger.warn('method={} : experimentId={} : agentNumbers={} : chainLengths={} : randomWorksNumberMultipliers={} : maxDistances={} : similaritySearchThresholds={}', 
      		'decentralizedVersion', 
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
							Global.parameters.simulationTimeout = this.timeout

					       	// 1: create simulation object
					       	String simulationIdGeneral = 'SIM'+(new SimpleDateFormat("MM-dd-hh-mm").format(new Date())) +"-"+ Utils.generateRandomString(6)

							sim = TestActorRef.create(system, Simulation.props(simulationIdGeneral+"--DV")).underlyingActor();
							Global.parameters.persistence=true
							assertNotNull(sim);
							sim.on.flushVertices();
							this.on.setEvaluationTimeout('PT2H') // setting timeout to max for cassandra...
			
							// 2: create agent network
							def agentList = sim.createAgentNetwork(agentNumber);

							// 3: add random works to the agents in the network
							sim.addRandomWorksToAgents(randomWorksNumber)

							// 4. add chain to the network
							def chains = [Utils.createChain(chainLength)]
							def chain = chains[0]
							def chainedWorksJson = sim.addChainToNetwork(chain, true)  // add chain to network and return json structure...

							// 5: create taskAgent -- the one which closes the chain in the network
							def taskAgent = createTaskAgentInTheNetwork(sim,chain)

							// 6: running decentralized version...

							logger.info('experimentId={} : simulationId={} : keyword={} : agentNumber={} : chainLength={} : randomWorksNumberMultiplier={} : maxDistance={} : similaritySearchThreshold={}',
								experimentId,
								Global.parameters.simulationId,
								'simulationParameters',
					     		agentNumber,
					      		chainLength,
					      		randomWorksNumberMultiplier,
					      		maxDistance,
					      		similaritySearchThreshold
					      	)								 

							runDecentralizedVersion(maxDistance, taskAgent, chainedWorksJson, experimentId);
							sim.on.analyze("Analysis after runDecentralizedVersion")

							ArrayList allAgentIds = sim.agentIdToActorRefTable.keySet().toArray()
							recreateActorSystem(allAgentIds,simulationIdGeneral+"--CV")							

							// 8: running centralized version...

							logger.info('experimentId={} : simulationId={} : keyword={} : agentNumber={} : chainLength={} : randomWorksNumberMultiplier={} : maxDistance={} : similaritySearchThreshold={}',
								experimentId,
								Global.parameters.simulationId,
								'simulationParameters',
					     		agentNumber,
					      		chainLength,
					      		randomWorksNumberMultiplier,
					      		maxDistance,
					      		similaritySearchThreshold
					      	)								 

							runCentralizedVersion(taskAgent, chainedWorksJson, experimentId);
							sim.on.analyze("Analysis after runCentralizedVersion")

						}
					}
				}
			}
		}
	}

	void recreateActorSystem(ArrayList agentIdList, String simulationId) {
		def on = sim.on;
		// recreating simulation object with the same graph for the centralizedSearch
		this.teardown();
		System.gc();
		Thread.sleep(1000)
		assertNull(system)
		system = ActorSystem.create("SimulationTests");
		sim = TestActorRef.create(system, Simulation.props(simulationId)).underlyingActor();
		assertNotNull(sim);
		this.on.setEvaluationTimeout('PT2H') // setting timeout to max for cassandra...
		sim.recreateAgents(agentIdList);
	}

	void runDecentralizedVersion(Object maxDistance, Object taskAgent, Object chainedWorksJson, Object experimentId) {
		def start = System.currentTimeMillis()
		sim.decentralizedSimilaritySearchAndConnect(maxDistance)

		// since the  decentralizedSimilaritySearchAndConnect is asynchronous and exists 
		// as soon as messages are sent to agents,
		// running cycle search immediately after does not guarantee that all agents did 
		// finished the searchAndConnect routines
		// therefore we mus loop on decentralized search until cycle is found...
		Global.parameters.terminate_all = false;
		def outOfTime = false
		Global.parameters.terminate_all = false; 
		while (!Global.parameters.terminate_all & !outOfTime) {	// the loop is needed in case messages are not delivered...
			def time = System.currentTimeMillis() - start
			if (time > timeout*1000) {
				outOfTime = true
				logger.info('experimentId={} : simulationId={} : timeout_ms={}',
					experimentId,
					Global.parameters.simulationId,
					time
		      	)
			} else {
				sim.individualCycleSearch(sim.agentIdToActorRefTable.get(taskAgent), chainedWorksJson) // (4) 
				Thread.sleep(1000)
			}
		}
	}

	void runCentralizedVersion(Object taskAgent, Object chainedWorksJson, Object experimentId) {
		/*
		There are a few versions to run this one:
		(1) centralizedSimilaritySearchAndConnect runs the cenralized search through one
		query in Gremlin. If the query run more than one hour, it timeouts... it is terribly inefficient as of current implementation.
		(2) connectAllSimilarCentralized() pulls all items from the graphs and sequentially checks all permutations -- awfully inefficient, but works.
		(3) sim.allCyclesCentralized(chainedWorksJson,1) loops through all agents and instructs them to run cycle search -- therefore guaranteed to find all cycles in general case.
		(4) since we know task agent, we can run the cycle search directly from it; it is more comparable to the decentralized search,
			albeit not generalizable... default.
		*/
		def start = System.currentTimeMillis()
		// sim.centralizedSimilaritySearchAndConnect()  //  (1)
		sim.on.connectAllSimilarCentralized() //  (2)
		//def cycles = sim.allCyclesCentralized(chainedWorksJson,1) // (3)
		def outOfTime = false
		Global.parameters.terminate_all = false; 
		while (!Global.parameters.terminate_all & !outOfTime) {	// the loop is needed in case messages are not delivered...
			def time = System.currentTimeMillis() - start
			if (time > timeout*1000) {
				outOfTime = true
				logger.info('experimentId={} : simulationId={} : timeout_ms={}',
					experimentId,
					Global.parameters.simulationId,
					time
		      	)
			} else {
				sim.individualCycleSearch(sim.agentIdToActorRefTable.get(taskAgent), chainedWorksJson) // (4) 
				Thread.sleep(1000)
			}
		}
	}


	String createTaskAgentInTheNetwork(Simulation sim, List chain) {
	  def start = System.currentTimeMillis()
	  def currentMethodName = "createTaskAgentInTheNetwork"
      // create agent that has a work which closes the chain into the cycle
      // this agent will have the last item in he chain as demand
      // and the first item in the chain as offer
      def vertexIdList = new ArrayList(sim.vertexIdToActorRefTable.keySet())
      ActorRef taskAgent = sim.createAgent()
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

	  logger.info('method={} : simulationId={} : wallTime_ms={}',
		currentMethodName,
		Global.parameters.simulationId,
 	    (System.currentTimeMillis()-start)
	  )	

      return sim.actorRefToAgentIdTable.get(taskAgent)
    }


}