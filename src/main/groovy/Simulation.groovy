package net.vveitas.offernet

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
import java.util.UUID;

class Simulation extends UntypedAbstractActor {
	OfferNet on;
	Logger logger;
	List agentList;

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
      logger.info("received Method message: {}",message.getMethodString())
      switch (message) {
        default: 
          def args = message.args
          def reply = this."$message.name"(*args)
          getSender().tell(reply,getSelf());
          break;
      }
    } else if (message instanceof GraphNode ) {
        logger.info("received GraphNode message: {}",message.getMethodString())
        switch (message) {
          default:
            this.knowsAgent(message);
            break;
        }
    } else if (message instanceof String) {
      logger.info("Agent {} received message {}",this.id(), message)
    } else { 
      getSender().tell("Cannot process the message",getSelf()) 
    }
  }


	private Simulation() {

		def start = System.currentTimeMillis();
		def config = new ConfigSlurper().parse(new File('configs/log4j-properties.groovy').toURL())
		PropertyConfigurator.configure(config.toProperties())
		logger = LoggerFactory.getLogger('Simulation.class');

		on = new OfferNet();
		on.flushVertices();
		logger.warn("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)

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
    def actorRef = getContext().actorOf(Agent.props(on.session,agentId),agentId);
    return actorRef
  }

  private Vertex getAgentVertexId(ActorRef actorRef) {
    return actorRef.tell(id());
  }

    private List createAgentNetwork(int numberOfAgents) {
      def start = System.currentTimeMillis()
      List agentsList = new ArrayList()
      def firstAgent = Props.create(Agent.class, on.session)

      agentsList.add(system.actorOf(Agent.props(on.session),"agent1"))

      while (agentsList.size() < numberOfAgents) {
          def random = new Random();
          def i = random.nextInt(agentsList.size())
          Object agent1 = agentsList[i]
          Object agent2 = system.actorOf(Agent.props(on.session),"agent1");
          agent1.tell(new Method("knowsAgent",[agent2id]),getRef())
          agentsList.add(agent2)
      }
      logger.info("Created a network of "+numberOfAgents+ " Agents")
      logger.warn("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)        
      return agentsList;
  }

  /* done until here */

	private Integer connectIfSimilarForAllAgents(List agentList, Integer similarityThreshold, Integer maxReachDistance) {

		def start = System.currentTimeMillis();
		logger.warn("Searching and connecting similar items of all agents in the graph:")
		def newConnectionsCreated = 0;
		agentList.each {agent ->
			 newConnectionsCreated += agent.searchAndConnect(similarityThreshold,maxReachDistance);
		}
		logger.warn("Created {} new 'similarity' links between items in the graph",newConnectionsCreated)
		logger.warn("Method {} took {} seconds to complete", Utils.getCurrentMethodName(), (System.currentTimeMillis()-start)/1000)

		return newConnectionsCreated
	}

	private void testNetworkSmall() {
	    def start = System.currentTimeMillis();
	    def chain = ["0010","0110","0000","1110"]
	    //def chain = Utils.createChain(4)
	    logger.info("Created chain {}", chain)

   	    def agent1 = new Agent(on.session);
	    def agent2 = new Agent(on.session);
	    def agent3 = new Agent(on.session);
   	    def agent4 = new Agent(on.session);
   	    logger.info("Created agents: {}",[agent1.id(),agent2.id(),agent3.id(),agent4.id()])


	    def work1 = agent1.ownsWork(chain[0],chain[1]);
	    logger.info("agent {} owns work {}", agent1,work1)
	    def work2 = agent2.ownsWork(chain[1],chain[2]);
	    logger.info("agent {} owns work {}", agent2,work2)
	    def work3 = agent3.ownsWork(chain[2],chain[3]);
	    logger.info("agent {} owns work {}", agent3,work3)
	    logger.info("Created works: {}",[work1.getId(),work2.getId(),work3.getId()])

	    agent1.knowsAgent(agent2);
	    logger.info("agent {} knows agent {}",agent1,agent2)
	    agent2.knowsAgent(agent3);
	    logger.info("agent {} knows agent {}",agent2,agent3)
	    agent3.knowsAgent(agent4);
	    logger.info("agent {} knows agent {}",agent3,agent4)

	    agentList = [agent1,agent2,agent3,agent4]
    }


	private void testNetworkSmallWithCycle() {
	    def start = System.currentTimeMillis();
	    def chain = ["0010","0110","0000","1110"]
	    //def chain = Utils.createChain(4)
	    logger.info("Created chain {}", chain)

   	    def agent1 = new Agent(on.session);
	    def agent2 = new Agent(on.session);
	    def agent3 = new Agent(on.session);
   	    def agent4 = new Agent(on.session);
   	    logger.info("Created agents: {}",[agent1.id(),agent2.id(),agent3.id(),agent4.id()])


	    def work1 = agent1.ownsWork(chain[0],chain[1]);
	    logger.info("agent {} owns work {}", agent1,work1)
	    def work2 = agent2.ownsWork(chain[1],chain[2]);
	    logger.info("agent {} owns work {}", agent2,work2)
	    def work3 = agent3.ownsWork(chain[2],chain[3]);
	    logger.info("agent {} owns work {}", agent3,work3)
	    def work4 = agent4.ownsWork(chain[3],chain[0]);
	    logger.info("Created works: {}",[work1.getId(),work2.getId(),work3.getId(),work4.getId()])

	    agent1.knowsAgent(agent2);
	    logger.info("agent {} knows agent {}",agent1,agent2)
	    agent2.knowsAgent(agent3);
	    logger.info("agent {} knows agent {}",agent2,agent3)
	    agent3.knowsAgent(agent4);
	    logger.info("agent {} knows agent {}",agent3,agent4)

	    agentList = [agent1,agent2,agent3,agent4]
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
            logger.warn("Agent {}",x)
            y = scanner.nextInt();
            logger.warn("knows agent {}",y)
            adj[x - 1].add(y - 1);
            adj[y - 1].add(x - 1);
        }
        logger.warn("Imported adjacency list: {}",adj)

        def agentsList = [];
        numberOfAgents.times {
        	agentsList.add( on.createAgent() );
        }
        logger.warn("Created {} agents list: {}", agentsList.size(), agentsList)

        def edges = 0;
        for (int i = 0; i<adj.size(); i++) {
        	def agent1 = agentsList[i]
        	adj[i].each { i2 ->
        		def agent2 = agentsList[i2]
        		on.knowsAgent(agent1, agent2)	
        		edges +=1
        	}
        }
        logger.warn("Created {} edges in the network",edges)
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
}
