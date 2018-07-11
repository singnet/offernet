package io.singularitynet.offernet

import org.apache.log4j.PropertyConfigurator
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.datastax.driver.dse.graph.Vertex;
import com.datastax.driver.dse.DseSession;
import com.datastax.driver.dse.graph.GraphNode;

import akka.actor.UntypedAbstractActor;
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


class Simulation extends UntypedAbstractActor {
	public OfferNet on;
	Logger logger;
	List agentList;
  public Hashtable<String,ActorRef> vertexIdToActorRefTable;
  public Hashtable<ActorRef,String> actorRefToVertexIdTable;
  public Hashtable<String,ActorRef> agentIdToActorRefTable;

  public static Props props() {
    return Props.create(new Creator<Simulation>() {
      @Override
      public Simulation create() throws Exception {
        return new Simulation();
      }
    });
  }

  public void onReceive(Object message) throws Exception {
    if (message instanceof Method) {
      logger.info("{}: {} : received message: {} of {}",
        Utils.getCurrentMethodName(),
        Parameters.parameters.simulationId,
        message,
        message.getClass())
      switch (message) {
        default: 
          def args = message.args
          def reply = this."$message.name"(*args)
          getSender().tell(reply,getSelf());
          break;
      }
    }
  }


	private Simulation() {

		def start = System.currentTimeMillis();
		def config = new ConfigSlurper().parse(new File('configs/log4j-properties.groovy').toURL())
		PropertyConfigurator.configure(config.toProperties())
		logger = LoggerFactory.getLogger('Simulation.class');

		on = new OfferNet();
		on.flushVertices();
		logger.debug("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)

    vertexIdToActorRefTable = new Hashtable<String,ActorRef>();
    actorRefToVertexIdTable = new Hashtable<ActorRef,String>();
    agentIdToActorRefTable = new Hashtable<String,ActorRef>();

    // create a unique number for each simulation which will be included into log entries
    String simulationId = 'SIM'+(new SimpleDateFormat("MM-dd-hh-mm").format(new Date())) +"-"+ Utils.generateRandomString(6); 
    // write simulation Id to global variables in order to be able to access from everywhere
    Parameters.parameters.simulationId = simulationId;

    logger.info('{} : {} : wallTime_ms={} msec.', 
      Utils.getCurrentMethodName(), 
      Parameters.parameters.simulationId,
      (System.currentTimeMillis()-start))
	}

	/*
	* TODO:
	* With agents the logic of simulation changes considerably. What needs to bee done:
	- OfferNetwork class should hold a map with agents / vertex labels and ways to query them;
	- Simulation is an Actor, OfferNet is a normal class (encapsulated within Simulation);
	- Simulations will be run by passing messages for running methods
	*/

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
    return actorRef
  }

  /**
  * gets agent vertexId via asynchronous blocking message
  * not perfect: have to change to non -blocking message with probably future.onComplete...
  */
  private Object getAgentVertexId(ActorRef actorRef) {
    Timeout timeout = new Timeout(Duration.create(5, "seconds"));
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
    logger.info('{} : {} : [numberOfAgents={}] : wallTime_ms={} sec.', 
      Utils.getCurrentMethodName(), 
      Parameters.parameters.simulationId,
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
    logger.debug("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)        
    return agentsList;
  }

  /**
  * This method sends a single message to every agent in a system in a row instructing it to start searchAndConnect method on their behalf
  * Used for testing purposes only. It is a semi-decentralized: decentralized because each agent gets a message and starts routines asynchronously. Centralized because a global list of agents is used. Complete decentralization could be achieved by randomly choosing agents in the network that start the discovery process.
  */
	private void connectIfSimilarForAllAgents(List agentList, Object similarityThreshold, Integer maxReachDistance) throws Throwable{

		def start = System.currentTimeMillis();
		logger.debug("Searching and connecting similar items of all agents in the graph:")
		def newConnectionsCreated = 0;
		agentList.each {agentRef ->
        def args = [similarityThreshold,maxReachDistance];
        Method msg = new Method("searchAndConnect", args);
        agentRef.tell(msg,getSelf());
		}

    logger.debug('{} : {} : agent count in the list={}; similarityThreshold={}; maxReachDistance={} : wallTime_ms={} sec.', 
      Utils.getCurrentMethodName(), 
      Parameters.parameters.simulationId,
      agentList.size(),
      similarityThreshold,
      maxReachDistance,
      (System.currentTimeMillis()-start)/1000)
	}


  private void createAgentNetworkFromNetworkXDataFile(String fileName) throws Throwable {
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
        	agentsList.add( on.createAgent() );
        }
        logger.debug("Created {} agents list: {}", agentsList.size(), agentsList)

        def edges = 0;
        for (int i = 0; i<adj.size(); i++) {
        	def agent1 = agentsList[i]
        	adj[i].each { i2 ->
        		def agent2 = agentsList[i2]
        		on.knowsAgent(agent1, agent2)	
        		edges +=1
        	}
        }
        logger.debug("Created {} edges in the network",edges)
    }

    private void createAgentNetworkConnectedStars(Vertex center, Integer radius, Integer branchingFactor) {
    	if (radius > 0 ) {
    		branchingFactor.times {
    			def spike = on.createAgent()	
    			on.knowsAgent(center,spike)
    			createAgentNetworkConnectedStars(spike,radius -1,branchingFactor);
    		}
    	}
    }

    private void addRandomWorksToAgents(int numberOfWorks) {
        def start=System.currentTimeMillis();
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
        logger.debug("Added "+numberOfWorks+" of random processes to the network")
        logger.debug("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)
    }

    public List createAgentNetwork(Integer numberOfAgents, Integer numberOfRandomWorks, ArrayList chains) {
      def start = System.currentTimeMillis();
      agentList = this.createAgentNetwork(numberOfAgents)
      //this.addRandomWorksToAgents(numberOfRandomWorks)
      chains.each {chain ->
        this.addChainToNetwork(chain)
      }
      logger.debug("Created agentNetwork with {} agents, {} randomWorks and {} chains",numberOfAgents,numberOfRandomWorks,chains.size())
      logger.debug("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)

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
    so temporarily it is a kind of 'override'
    */
    public Object addChainToNetwork(List chain, boolean json) {
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
        if (Parameters.parameters.reportMode) {
            String experimentDir = System.getProperty("user.dir")+"/"+Parameters.parameters.experimentDataDir + "/"+Parameters.parameters.experimentId
            new File(experimentDir).mkdirs();
            String chainFilePath = experimentDir + "/"+Utils.generateRandomString(3)+"-chain.json"
            new File(chainFilePath).write(JsonOutput.toJson(chainedWorks))
            logger.debug('Wrote chain to file  {}', chainFilePath)
        }

        return chainedWorks;
    }

    /**
    * Simply checks if an agent with a given Id already exist on the offernet graph..
    **/

    public boolean agentExists(String agentId) {
      def vertices = this.on.getVertices('agentId',agentId);
      return !vertices.isEmpty();
    }

    public Set allCyclesCentralized(Object similarityThreshold, int version) {
      switch(version) {
        case 1:
          this.naiveCentralizedCycleSearch(similarityThreshold)
          break
        case 2:
          this.depthFirstCycleSearch(similarityThreshold)
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
    public Set naiveCentralizedCycleSearch(Object similaritySearchThreshold) {
      def start = System.currentTimeMillis()
      int totalPaths = 0;
      def uniquePaths = [] as Set;
      def agentPaths;
      def actorRefList = new ArrayList(this.actorRefToVertexIdTable.keySet())
      actorRefList.each{ agent -> 
        agentPaths = [];
        logger.debug("Getting all works of an agent {}", agent)
        Method msg = new Method("getWorks", new ArrayList());
        Timeout timeout = new Timeout(Duration.create(5, "seconds"));
        Future<Object> future = Patterns.ask(agent, msg, timeout);
        List works = (List<Vertex>) Await.result(future, timeout.duration());
        logger.debug("Retrieved {} works of agent {}", works.size(), agent)
        works.each { work ->
          logger.debug("Running decentralized PathSearch from work's {} perspective", work)
          msg = new Method("cycleSearch", new ArrayList(){{add(work);add(similaritySearchThreshold)}});
          timeout = new Timeout(Duration.create(120, "seconds"));
          future = Patterns.ask(agent, msg, timeout);
          List path = (List<GraphNode>) Await.result(future, timeout.duration());
          logger.debug("Found path {} from work {}",path,work)
          if (path.size()!=0) {agentPaths.add(path);totalPaths += 1;}
        }
        logger.debug("Found {} paths from agent {} perspective", agentPaths.size(), agent)
          uniquePaths.addAll(agentPaths)
      }
      
      logger.info('{} : {} : [similaritySearchThreshold={}; total_found_cycles_count={}; unique_found_cycles_count={}] : wallTime_ms={} msec.', 
        Utils.getCurrentMethodName(), 
        Parameters.parameters.simulationId,
        similaritySearchThreshold,
        totalPaths,
        uniquePaths.size(),
        (System.currentTimeMillis()-start))


      return uniquePaths;
    }

    /**
    * Depth first centralized search is just a depth first search which works pretty much
    * like the naive one, but checks visited agents and works prior to processing them
    */
    public Set depthFirstCycleSearch(Object similaritySearchThreshold) {
      def start = System.currentTimeMillis()
      def totalPaths = 0;
      def uniquePaths = [] as Set;
      def visitedWorks = [] as Set;
      def allWorks = this.on.getVertices('work')
      logger.debug('allWorks are {}',allWorks)
      def agentPaths;
      def actorRefList = new ArrayList(this.actorRefToVertexIdTable.keySet())
      actorRefList.find{ agent -> 
        agentPaths = [];
        logger.debug("Getting all works of an agent {}", agent)
        Method msg = new Method("getWorks", new ArrayList());
        Timeout timeout = new Timeout(Duration.create(5, "seconds"));
        Future<Object> future = Patterns.ask(agent, msg, timeout);
        List works = (List<Vertex>) Await.result(future, timeout.duration());
        logger.debug("Retrieved {} works of agent {}", works.size(), agent)
        works.each { work ->
          if (!visitedWorks.contains(work)) {
            logger.debug("Running decentralized PathSearch from work's {} perspective", work)
            msg = new Method("cycleSearch", new ArrayList(){{add(work);add(similaritySearchThreshold)}});
            timeout = new Timeout(Duration.create(120, "seconds"));
            future = Patterns.ask(agent, msg, timeout);
            List path = (List<GraphNode>) Await.result(future, timeout.duration());
            logger.debug("Found path {} from work {}",path,work)
            if (path.size()!=0) {agentPaths.add(path);totalPaths +=1;}
            def verticesOfThePath = getTypeVerticesBelongingToSubgraph(path,['work']);
            def worksVisitedBySearch = verticesOfThePath['work']
            visitedWorks.addAll(worksVisitedBySearch)
            logger.debug('saving worksVisitedBySearch: {}',worksVisitedBySearch)
          }
        }
        logger.debug("Found {} paths from agent {} perspective", agentPaths.size(), agent)
        uniquePaths.addAll(agentPaths)
        logger.debug('visitedWorks are {} of size()',visitedWorks.size())
        logger.debug('allWorks are {} of size()',allWorks.size())

        if (visitedWorks.size() ==allWorks.size()) {
          logger.debug('Traversal already touched all works in the graph: aborting')
          return true
        } // break
        else {return false}
      }

      logger.info('{} : {} : similaritySearchThreshold={}; total_found_cycles_count={}; unique_found_cycles_count={}] : wallTime_ms={} msec.', 
        Utils.getCurrentMethodName(), 
        Parameters.parameters.simulationId,
        similaritySearchThreshold,
        totalPaths,
        uniquePaths.size(),
        (System.currentTimeMillis()-start))

      return uniquePaths;
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
        logger.warn('{} : {} : wallTime_ms={} msec.', 
          Utils.getCurrentMethodName(), 
          Parameters.parameters.simulationId,
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
        logger.warn('{} : {} : wallTime_ms={} msec.', 
          Utils.getCurrentMethodName(), 
          Parameters.parameters.simulationId,
          (System.currentTimeMillis()-start))

        return visitedVertices
    }
}
