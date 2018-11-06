package io.singularitynet.offernet

import org.apache.log4j.PropertyConfigurator
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.datastax.driver.dse.graph.Vertex;
import com.datastax.driver.dse.DseSession;
import com.datastax.driver.dse.graph.GraphNode;

import akka.actor.AbstractActorWithTimers;
import akka.actor.AbstractActor.Receive;
import akka.actor.Props;
import akka.japi.Creator;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import java.util.UUID;

import akka.dispatch.*;
import scala.concurrent.Future;
import scala.concurrent.Await;
import akka.util.Timeout;
import scala.concurrent.duration.Duration;
import akka.pattern.Patterns;
import groovy.json.JsonOutput;
import java.text.SimpleDateFormat;

import akka.pattern.Patterns;
import scala.concurrent.Future;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;
import akka.util.Timeout;


import groovy.json.JsonSlurper;
import org.json.JSONArray


class Simulation extends AbstractActorWithTimers {
	public OfferNet on;
	Logger logger;
	List agentList;
  public Hashtable<String,ActorRef> vertexIdToActorRefTable;
  public Hashtable<ActorRef,String> actorRefToVertexIdTable;
  public Hashtable<ActorRef,String> actorRefToAgentIdTable;
  public Hashtable<String,ActorRef> agentIdToActorRefTable;

  public static Props props() {
    return Props.create(new Creator<Simulation>() {
      @Override
      public Simulation create() throws Exception {
        return new Simulation();
      }
    });
  }

  public static Props props(String simulationId) {
    return Props.create(new Creator<Simulation>() {
      @Override
      public Simulation create() throws Exception {
        return new Simulation(simulationId);
      }
    });
  }

  @Override
  public Receive createReceive() {
    def runMethod = { message -> 
        logger.info("{}: {} : received message: {} of {}",
        'createReceive',
        Global.parameters.simulationId,
        message,
        message.getClass())

        def args = message.args
        def reply = this."$message.name"(*args)
        if (reply != null) { 
          getSender().tell(reply,getSelf());
        }
      }
    return receiveBuilder()
      .match(Method.class, runMethod).build();
  }

  private Simulation() {
    this('SIM'+(new SimpleDateFormat("MM-dd-hh-mm").format(new Date())) +"-"+ Utils.generateRandomString(6));
  }

	private Simulation(String simulationId) {

		def start = System.currentTimeMillis();
		//def config = new ConfigSlurper().parse(new File('configs/log4j-properties.groovy').toURL())
		//PropertyConfigurator.configure(config.toProperties())
		logger = LoggerFactory.getLogger('Simulation.class');

    on = new OfferNet();  
    
		logger.debug("Method {} took {} seconds to complete", '<init>', (System.currentTimeMillis()-start)/1000)

    vertexIdToActorRefTable = new Hashtable<String,ActorRef>();
    actorRefToVertexIdTable = new Hashtable<ActorRef,String>();
    agentIdToActorRefTable = new Hashtable<String,ActorRef>();
    actorRefToAgentIdTable = new Hashtable<String,ActorRef>();

    // write simulation Id to global variables in order to be able to access from everywhere
    Global.parameters.simulationId = simulationId;

    logger.info('method={} : simulationId={} : wallTime_ms={} msec.', 
      '<init>', 
      Global.parameters.simulationId,
      (System.currentTimeMillis()-start))

	}


  private ActorRef createAgent() {
    String agentId = UUID.randomUUID().toString();
    def actorRef = createAgentWithId(agentId);
    return actorRef
  }

  public ActorRef createAgentWithId(String agentId) {
    //def actorRef = getContext().actorOf(Agent.props(on.session,agentId),agentId);
    def actorRef = on.system.actorOf(Agent.props(on.session,agentId),agentId);
    def vertexId = this.getAgentVertexId(actorRef);
    vertexIdToActorRefTable.put(vertexId,actorRef);
    actorRefToVertexIdTable.put(actorRef,vertexId);
    agentIdToActorRefTable.put(agentId,actorRef);
    actorRefToAgentIdTable.put(actorRef,agentId);
    return actorRef
  }

  /**
  This method is used when the simulation object is recreated with the same underlying graph
  */
  public void recreateAgents(ArrayList agentIdList) {
    agentIdList.each { agentId -> 
      this.createAgentWithId(agentId)
    }
    logger.debug('recreated {} actors in simulation {}', agentIdList.size(), Global.parameters.simulationId)
  }

  /**
  * gets agent vertexId via asynchronous blocking message
  * not perfect: have to change to non -blocking message with probably future.onComplete...
  */
  private Object getAgentVertexId(ActorRef actorRef) {
    Timeout timeout = new Timeout(Duration.create(30, "seconds"));
    def msg = new Method("vertexId",[])
    Future<Object> future = Patterns.ask(actorRef, msg, timeout);
    def vertexId = Await.result(future, timeout.duration());
    logger.debug('Got actorRefs {} vertexId {} via blocking future', actorRef, vertexId)
    return vertexId;
  }

  public List createAgentNetwork(int numberOfAgents) {
    def start = System.currentTimeMillis()
    List agentsList = new ArrayList()

    agentsList.add(this.createAgent())

    while (agentsList.size() < numberOfAgents) {
        def random = new Random();
        def i = random.nextInt(agentsList.size())
        Object agent1 = agentsList[i]
        Object agent2 = this.createAgent();
        def agent2vertexId = this.getAgentVertexId(agent2)
        agent1.tell(new Method("knowsAgent",[agent2vertexId]),getSelf())
        agentsList.add(agent2)
      }
    logger.info('method={} : simulationId={} : numberOfAgents={} : wallTime_ms={} sec.', 
      'createAgentNetwork', 
      Global.parameters.simulationId,
      numberOfAgents,
      (System.currentTimeMillis()-start)/1000)
    return agentsList;
  }

  public List createAgentLine(int numberOfAgents) {
    def start = System.currentTimeMillis()
    List agentsList = new ArrayList()

    agentsList.add(this.createAgentWithId('agent-0'))

    for (int x = 1; x<numberOfAgents;x++) {
      Object agent1 = agentsList[x-1];
      Object agent2 = this.createAgentWithId('agent-'+x);
      def agent2vertexId = this.getAgentVertexId(agent2)
      agent1.tell(new Method("knowsAgent",[agent2vertexId]),getSelf())
      agentsList.add(agent2)      
    }
    logger.debug("Created a line of {} agents",numberOfAgents);
    logger.debug("Method {} took {} seconds to complete", 'createAgentLine', (System.currentTimeMillis()-start)/1000)        
    return agentsList;
  }

  /**
  * This method sends a single message to every agent in a system in a row instructing it to start searchAndConnect method on their behalf
  * Used for testing purposes only. It is a semi-decentralized: decentralized because each agent gets a message and starts routines asynchronously. The only that miss from making it centralized is the check that all items in the graph are visited -- this should not be very difficult to do.
  Centralized because a global list of agents is used. Complete decentralization could be achieved by randomly choosing agents in the network that start the discovery process.
  */
	private void connectIfSimilarForAllAgents(List agentList, Object similarityThreshold, Integer maxReachDistance) throws Throwable{
    def currentMethodName = 'connectIfSimilarForAllAgents'
		def start = System.currentTimeMillis();
		logger.debug("Searching and connecting similar items of all agents in the graph:")
		def newConnectionsCreated = 0;
		agentList.each {agentRef ->
        def args = [similarityThreshold,maxReachDistance];
        Method msg = new Method("searchAndConnect", args);
        agentRef.tell(msg,getSelf());
		}

    logger.info('method={} : simulationId={} : agentNumber={}; similarityThreshold={}; maxReachDistance={} : wallTime_ms={} sec.', 
      currentMethodName,
      Global.parameters.simulationId,
      agentList.size(),
      similarityThreshold,
      maxReachDistance,
      (System.currentTimeMillis()-start)/1000)
	}


  private boolean createAgentNetworkFromNetworkXDataFile(String fileName) throws Throwable {
        FileInputStream inStream = new FileInputStream(fileName);
        Scanner scanner = new Scanner(inStream);
        int numberOfAgents = scanner.nextInt();
        int numberOfEdges = scanner.nextInt();
        ArrayList<Integer>[] adj = (ArrayList<Integer>[])new ArrayList[numberOfAgents];
        for (int i = 0; i < numberOfAgents; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < numberOfEdges; i++) {
            int x, y;
            x = scanner.nextInt();
            logger.debug("Agent {}",x)
            y = scanner.nextInt();
            logger.debug("knows agent {}",y)
            adj[x - 1].add(y - 1);
            adj[y - 1].add(x - 1);
        }
        logger.debug("Imported adjacency list: {}",adj)

        def agentsList = [];
        numberOfAgents.times {
        	agentsList.add( this.createAgent() );
        }
        logger.debug("Created {} agents list: {}", agentsList.size(), agentsList)

        def edges = 0;
        for (int i = 0; i<adj.size(); i++) {
        	def agent1 = agentsList[i]
        	adj[i].each { i2 ->
        		def agent2 = agentsList[i2]
            def agent2vertexId = this.getAgentVertexId(agent2)
            agent1.tell(new Method("knowsAgent",[agent2vertexId]),getSelf())
            agentsList.add(agent2)      
        		edges +=1
        	}
        }
        logger.debug("Created {} edges in the network",edges)
        return true
    }

    private void createAgentNetworkConnectedStars(ActorRef center, Integer radius, Integer branchingFactor) {
    	if (radius > 0 ) {
    		branchingFactor.times {
    			def spike = this.createAgent()	
          def spikeVertexId = this.getAgentVertexId(spike)
          center.tell(new Method("knowsAgent",[spikeVertexId]),getSelf())
    			createAgentNetworkConnectedStars(spike,radius -1,branchingFactor);
    		}
    	}
    }

    private void addRandomWorksToAgents(int numberOfWorks) {
        def start=System.currentTimeMillis();
        def currentMethodName = "addRandomWorksToAgents"
        /* here should randomly select actor from the system instead of the next line*/
        ArrayList actorRefs = actorRefToVertexIdTable.keySet().toArray();
        logger.debug("ActorRefs array is of size {}: {}", actorRefs.size(), actorRefs)
        numberOfWorks.times {
            def random = new Random();
            def id = random.nextInt(actorRefs.size()-1)
            def actorRef = actorRefs[id]
            actorRef.tell(new Method("ownsWork",[]),getSelf());
            logger.debug("Added random work to actorRef {}", actorRef);
        }

        logger.info('method={} : simulationId={} : numberOfWorks={} : wallTime_ms={} msec.', 
          currentMethodName, 
          Global.parameters.simulationId,
          numberOfWorks,
          (System.currentTimeMillis()-start)
        )
         
    }

    private void startRandomWorkGenerator(int events) {
      // events is number of random works per agent per day
    }

    private void startChainGenerator(int events) {
      // events is number of chains per agent per day
    }

    private void startRandomCycleSearchGenerator(int events) {
      // events is number of randomCycleSearches per agent per day
    }

    private void startTargetedCycleSearchGenerator(int events) {
      // events is number of targetedCycleSearches per agent per day
    }

    public List createAgentNetwork(Integer numberOfAgents, Integer numberOfRandomWorks, ArrayList chains) {
      def start = System.currentTimeMillis();
      agentList = this.createAgentNetwork(numberOfAgents)
      //this.addRandomWorksToAgents(numberOfRandomWorks)
      chains.each {chain ->
        this.addChainToNetwork(chain)
      }
      logger.debug("Created agentNetwork with {} agents, {} randomWorks and {} chains",numberOfAgents,numberOfRandomWorks,chains.size())
      logger.debug("Method {} took {} seconds to complete", 'createAgentNetwork', (System.currentTimeMillis()-start)/1000)

      return agentList;
    }

    public List addChainToNetwork(List chain) {
        def start = System.currentTimeMillis();
        def dataItemsWithDesignedSimilarities = new ArrayList()
        def affectedActors = []
        def chainedWorks = []
        ArrayList actorRefs = actorRefToVertexIdTable.keySet().toArray();
        for (int x=0;x<chain.size()-1;x++) {
            boolean selected = false;
            while (!selected) {
              def random = new Random();
              def agentRef = actorRefs[random.nextInt(actorRefs.size())]
              Vertex work;
              if (! affectedActors.contains(agentRef)){
                  /*
                  * a chain is added to the network by sending messages to agents to add a work with 
                  * specified demand and offer items
                  * it has to return work in order to log it and then compare to search results
                  * therefore messages are sent via ask pattern
                  * and block until they get replies 
                  */
                  selected = true;
                  Timeout timeout = new Timeout(Duration.create(5, "seconds"));
                  String method = "ownsWork"
                  def args = [chain[x],chain[x+1]];
                  def msg = new Method(method,args)
                  Future<Object> future = Patterns.ask(agentRef, msg, timeout);
                  work = Await.result(future, timeout.duration());
                  chainedWorks.add([work, [demands: chain[x]], [offers:chain[x+1]]])
                  affectedActors.add(agentRef)
              }
            }
        }
        logger.debug('Added chain to the network: {}', chainedWorks)
        return chainedWorks;
    }

    /*
    This method sort of replaces the simple addChainToNetwork, but since it returns complex Object (json)
    rather than list it breaks quite a few test, so more complicated refactoring is needed...
    so temporarily it is a kind of an 'override'
    */
    public Object addChainToNetwork(List chain, boolean json) {
        def currentMethodName = "addChainToNetwork"
        def start = System.currentTimeMillis();
        def dataItemsWithDesignedSimilarities = new ArrayList()
        def affectedActors = []
        def chainedWorks = []
        ArrayList actorRefs = actorRefToVertexIdTable.keySet().toArray();
        for (int x=0;x<chain.size()-1;x++) {
            boolean selected = false;
            while (!selected) {
              def random = new Random();
              def agentRef = actorRefs[random.nextInt(actorRefs.size())]
              Vertex work;
              if (! affectedActors.contains(agentRef)){
                  /*
                  * a chain is added to the network by sending messages to agents to add a work with 
                  * specified demand and offer items
                  * it has to return work in order to log it and then compare to search results
                  * therefore messages are sent via ask pattern
                  * and block until they get replies 
                  */
                  selected = true;
                  Timeout timeout = new Timeout(Duration.create(5, "seconds"));
                  String method = "ownsWork"
                  def args = [chain[x],chain[x+1]];
                  def msg = new Method(method,args)
                  Future<Object> future = Patterns.ask(agentRef, msg, timeout);
                  work = Await.result(future, timeout.duration());
                  affectedActors.add(agentRef)

                  def singleChain = [:]  
                  def workLabel = Utils.formatVertexLabel(work.id)
                  singleChain.put("work", workLabel)

                  ['demands','offers'].each{ edgeLabel ->
                    timeout = new Timeout(Duration.create(5, "seconds"));
                    method = "getWorksItems"
                    args = [work,edgeLabel];
                    msg = new Method(method,args)
                    future = Patterns.ask(agentRef, msg, timeout);
                    List items = Await.result(future, timeout.duration());
                    def z = x
                    if (edgeLabel == "offers") { z = x+1 }
                    singleChain.put("$edgeLabel", [ item: Utils.formatVertexLabel(items[0].id), value: chain[z] ] )
                  }
                chainedWorks.add(singleChain)
              }
            }
        }
        def jsonChainedWorks = JsonOutput.toJson(chainedWorks)
        logger.debug('Added chain to the network (json): {}', jsonChainedWorks)
        if (Global.parameters.reportMode) {
            String experimentDir = System.getProperty("user.dir")+"/"+Global.parameters.experimentDataDir + "/"+Global.parameters.experimentId
            new File(experimentDir).mkdirs();
            String chainFilePath = experimentDir + "/"+Utils.generateRandomString(3)+"-chain.json"
            new File(chainFilePath).write(JsonOutput.toJson(chainedWorks))
            logger.debug('Wrote chain to file  {}', chainFilePath)
        }

        logger.info('method={} : simulationId={} : wallTime_ms={}',
          currentMethodName,
          Global.parameters.simulationId,
            'addChainToNetwork finished',
            (System.currentTimeMillis()-start)
        ) 
        return chainedWorks;
    }

    /**
    * Simply checks if an agent with a given Id already exist on the offernet graph..
    **/

    public boolean agentExists(String agentId) {
      def vertices = this.on.getVertices('agent','agentId',agentId);
      return !vertices.isEmpty();
    }

    public void allCyclesCentralized(List chain, int version) {
      def similaritySearchThreshold = Global.parameters.similaritySearchThreshold
      logger.debug('running all cycles centralized routine... similaritySearchThreshold={}, chain={}', similaritySearchThreshold, chain)
      def foundCyclesCount = 0
      switch(version) {
        case 1:
          this.naiveCentralizedCycleSearch(chain)
          break
        case 2:
          this.depthFirstCycleSearch(similaritySearchThreshold, chain)
          break
      }
    }

    /**
    * This is a naive version of finding all cycles in the graph:
    * it simply iterates over all agents and issues 'cycleSearch' method
    * so that they all find a cycle that they belong to
    * it is not efficient, since every vertex is traversed many times and more than 
    * one similar cycle is returned
    */
    public int naiveCentralizedCycleSearch(List chainedWorksJson) {
      def similaritySearchThreshold = Global.parameters.similaritySearchThreshold
      def start = System.currentTimeMillis()
      def currentMethodName = 'naiveCentralizedCycleSearch';
      int totalPaths = 0;
      def uniquePaths = [] as Set;
      def actorRefList = new ArrayList(this.actorRefToVertexIdTable.keySet())
      logger.debug('actorRefList in naiveCentralizeCycleSearch: {}', actorRefList)
      actorRefList.each{ agent -> 
        Timeout timeout = new Timeout(Duration.create(10, "seconds"));
        Method msg = new Method("cycleSearchSynchronous", new ArrayList(){{add(similaritySearchThreshold);add(chainedWorksJson)}});
        Future<Object> future = Patterns.ask(agent, msg, timeout);
        List<GraphNode> agentPaths = Await.result(future, timeout.duration());
        uniquePaths.addAll(agentPaths)
      }

      def uniquePathsList = []
      uniquePathsList.addAll(uniquePaths)
      int foundCycles = 0
      uniquePaths.each { uniquePath -> 
        foundCycles = foundCycles + this.checkFoundPaths(uniquePathsList,chainedWorksJson)
      }

      logger.info('method={} : simulationId={} : foundCycles={} : similaritySearchThreshold={} : chainedWorksJson={} : wallTime_ms={} msec.', 
        currentMethodName, 
        Global.parameters.simulationId,
        foundCycles,
        similaritySearchThreshold,
        chainedWorksJson,
        (System.currentTimeMillis()-start)
      )
      return foundCycles
    }

    List getVerticesBelongingToSubgraphs(Object subgraphs) {
      /*
      subgraph dontains only edges
      but we want to have both edges and vertices
      */
      def uniquePaths = []
      logger.debug("subgraphs {} class is {}",subgraphs, subgraphs.getClass())
      def sbgsIterator = subgraphs.iterator()
      while (sbgsIterator.hasNext()) {
        def subgraph = sbgsIterator.next()
        def uniquePath = this.getVerticesBelongingToSubgraph(subgraph)
        uniquePaths.add(uniquePath)
      }
      logger.debug("subgraph enriched by vertices: {}", uniquePaths)
      return uniquePaths

    }


    Object getVerticesBelongingToSubgraph(Object subgraph) {
        def start = System.currentTimeMillis()
        logger.debug("subgraph is {}",subgraph)
        JSONArray uniquePath = new JSONArray()
        subgraph.each { edge ->
          JSONArray singleChain = new JSONArray()
          singleChain.put(edge)
          logger.debug("Getting vertexes of the edge {}",edge)
          def inV = Utils.formatVertexLabel(edge.id.get('~in_vertex'))
          logger.debug("Getting vertex {}",inV)
          def vertexIn = this.on.getVertex(inV)
          logger.debug('got vertexIn {}',vertexIn)
          def outV = Utils.formatVertexLabel(edge.id.get('~out_vertex'))
          logger.debug("Getting vertex {}",outV)
          def vertexOut = this.on.getVertex(outV)
          logger.debug('got vertexOut {}',vertexOut)
          singleChain.put(vertexIn[0])
          singleChain.put(vertexOut[0])
          uniquePath.put(singleChain)
        }
        logger.debug("formed a uniquePath with edges and vertices {}",uniquePath)
        logger.debug('{} : {} : wallTime_ms={} msec.', 
          'getVerticesBelongingToSubgraph', 
          Global.parameters.simulationId,
          (System.currentTimeMillis()-start))

        return uniquePath
    }

    Object getTypeVerticesBelongingToSubgraph(Object subgraph, ArrayList types) {
        def start = System.currentTimeMillis()
        logger.debug("subgraph is {}",subgraph)
        def visitedVertices = [:]
        subgraph.each { edge ->
          JSONArray singleChain = new JSONArray()
          logger.debug("Getting vertexes of the edge {}",edge)
          def inV = Utils.formatVertexLabel(edge.id.get('~in_vertex'))
          logger.debug("Getting vertex {}",inV)
          def vertexIn = this.on.getVertex(inV)[0]
          logger.debug('got vertexIn {}',vertexIn)
          def outV = Utils.formatVertexLabel(edge.id.get('~out_vertex'))
          logger.debug("Getting vertex {}",outV)
          def vertexOut = this.on.getVertex(outV)[0]
          logger.debug('got vertexOut {}',vertexOut)
          if (types.contains(vertexIn.label)) {
            visitedVertices[vertexIn.label] = vertexIn;
          }
          if (types.contains(vertexOut.label)) {
            visitedVertices[vertexOut.label] = vertexOut;
          }
        }
        logger.debug("the path visited vertices {}",visitedVertices)
        logger.debug('{} : {} : wallTime_ms={} msec.', 
          'getVerticesBelongingToSubgraph', 
          Global.parameters.simulationId,
          (System.currentTimeMillis()-start))

        return visitedVertices
    }

    void decentralizedSimilaritySearchAndConnect(int maxDistance) {
      def start = System.currentTimeMillis();
      def currentMethodName = "decentralizedSimilaritySearchAndConnect"
      def similarityConnectThreshold = Global.parameters.similarityThreshold
      def agentList = new ArrayList(actorRefToVertexIdTable.keySet());
      
      this.connectIfSimilarForAllAgents(agentList,similarityConnectThreshold,maxDistance);

      logger.info('method={} : simulationId={} : agentNumber={}; maxDistance={} : wallTime_ms={} sec.', 
        currentMethodName,
        Global.parameters.simulationId,
        agentList.size(),
        maxDistance,
        (System.currentTimeMillis()-start)/1000)
    }

    void centralizedSimilaritySearchAndConnect() {
      logger.debug("Running centralized similarity search and connect")
      def start = System.currentTimeMillis();
      this.on.setEvaluationTimeout('PT2H') // setting timeout to max for cassandra...

      //def allItems = this.on.getVertices('item');
      def similarityConnectThreshold = Global.parameters.similarityThreshold
      
      def similarityConnectionsCentralized = this.on.searchAndConnect(similarityConnectThreshold);
      logger.debug("Created {} similarity connections of all agents with similarity {}", similarityConnectionsCentralized,similarityConnectThreshold);
      logger.debug("Method {} took {} seconds to complete", 'decentralizedSimilaritySearchAndConnect', (System.currentTimeMillis()-start)/1000)
    }

    void individualCycleSearch(ActorRef agent, List chain) {
        def start = System.currentTimeMillis();
        def uniqueCycles = [] as Set
        def similaritySearchThreshold = Global.parameters.similaritySearchThreshold 
        Method msg = new Method("cycleSearch", new ArrayList(){{add(similaritySearchThreshold);add(chain)}});
        agent.tell(msg, getSelf());
        def currentMethodName = 'individualCycleSearch'
        logger.info('method={} : simulationId={} : agentId={} : similaritySearchThreshold={} :  wallTime_ms={} msec.', 
            currentMethodName, 
            Global.parameters.simulationId,
            this.actorRefToAgentIdTable.get(agent),
            similaritySearchThreshold,
            (System.currentTimeMillis()-start)
        )
    }

    int checkFoundPaths(List cycle, List chain) {
          def start = System.currentTimeMillis();
          def currentMethodName = 'checkFoundPaths'
          def jsonSlurper = new JsonSlurper()
          def cycleJson = jsonSlurper.parseText(cycle.toString());
          def foundCyclesCount = 0;

          def richCycle = getVerticesBelongingToSubgraph(cycleJson)
          logger.debug("Got cycle enriched by vertices {}",richCycle)
          def keyword = "";
          if (richCycle.size()!=0) {
              def cycleContainsChain = Utils.pathContainsChain(richCycle, chain)
              if (cycleContainsChain) {
                foundCyclesCount += 1;
                keyword = "foundCycle";
                Global.parameters.terminate_all = true
              } else {
                keyword = "foundPath"
              }
          }
       
          logger.info('method={} : simulationId={} : agentId={} : keyword={} : cyGraph={} : wallTime_ms={} msec.', 
            currentMethodName, 
            Global.parameters.simulationId,
            this.actorRefToAgentIdTable.get(getSender()),
            keyword,
            Utils.convertToCYNotation(richCycle,keyword),
            (System.currentTimeMillis()-start)
          )
        return foundCyclesCount;
    }

    int decentralizedPathSearch(ActorRef agent, int maxDistance, List chain) {
        def currentMethodName = 'decentralizedCycleSearch';
        def start = System.currentTimeMillis();
        int foundPathsCount = 0;
        def similaritySearchThreshold = Global.parameters.similaritySearchThreshold 
        logger.debug("Getting all works of an agent {}", agent)
        Method msg = new Method("getWorks", new ArrayList());
        Timeout timeout = new Timeout(Duration.create(10, "seconds"));
        Future<Object> future = Patterns.ask(agent, msg, timeout);
        List works = (List<Vertex>) Await.result(future, timeout.duration());
        logger.debug("Retrieved {} works of agent {}", works.size(), agent)
        works.each { work ->
          logger.debug("Running decentralized PathSearch from work's {} perspective", work)
          msg = new Method("pathSearch", new ArrayList(){{add(work);add(maxDistance);add(similaritySearchThreshold)}});
          timeout = new Timeout(Duration.create(5, "seconds"));
          future = Patterns.ask(agent, msg, timeout);
          List cycle = (List<GraphNode>) Await.result(future, timeout.duration());
          logger.debug("Found path {} from work {}",cycle,work)
          
          def jsonSlurper = new JsonSlurper()
          def cycleJson = jsonSlurper.parseText(cycle.toString());

          def richCycle = getVerticesBelongingToSubgraph(cycleJson)
          logger.debug("Got cycle enriched by vertices {}",richCycle)
          def keyword = "";
          if (richCycle.size()!=0) {
              def cycleContainsChain = Utils.pathContainsChain(richCycle, chain)
              if (cycleContainsChain) {
                foundPathsCount += 1;
                keyword = "foundCycle"
              } else {keyword = "foundPath"}
          }
       
          logger.info('method={} : simulationId={} : agentId={} : keyword={} : cyGraph={} : wallTime_ms={} msec.', 
            currentMethodName, 
            Global.parameters.simulationId,
            this.actorRefToAgentIdTable.get(agent),
            keyword,
            Utils.convertToCYNotation(richCycle,keyword),
            (System.currentTimeMillis()-start)
          )
        }
        return foundPathsCount;
    }

}
