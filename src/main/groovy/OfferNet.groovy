//@Grab(group='com.datastax.cassandra', module='dse-driver', version='1.1.1')
//@Grab(group='log4j', module='log4j', version='1.2.17')

package net.vveitas.offernet

import com.datastax.driver.dse.DseCluster;
import com.datastax.driver.dse.DseSession;

import com.datastax.driver.dse.graph.GraphStatement;
import com.datastax.driver.dse.graph.SimpleGraphStatement;
import com.datastax.driver.dse.graph.GraphResultSet
import com.datastax.driver.dse.graph.GraphOptions

import org.apache.log4j.PropertyConfigurator
import org.slf4j.Logger
import org.slf4j.LoggerFactory

public class OfferNet implements AutoCloseable {

    private DseCluster cluster;
    private DseSession session; // creating one 'main' client and allowing to create more with the method
    private Logger logger;

    public void ass() {
    Runtime.getRuntime().addShutdownHook(new Thread() {
        public void run() {
          sesion.close();
          cluster.close();
        }
      });
    }

    private OfferNet() {

        //loading log4j properties
        def config = new ConfigSlurper().parse(new File('configs/log4j-properties.groovy').toURL())
        PropertyConfigurator.configure(config.toProperties())
        logger = LoggerFactory.getLogger('OfferNet.class');

        try {
            cluster = DseCluster.builder().addContactPoint("192.168.1.6").build();
            cluster.connect().executeGraph("system.graph('offernet').ifNotExists().create()");

            cluster = DseCluster.builder()
                .addContactPoint("192.168.1.6")
                .withGraphOptions(new GraphOptions().setGraphName("offernet"))
                .build();
            session = cluster.connect();

            logger.info("Created OfferNet instance with session {}", session);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws Exception {
        String clusterName = cluster.toString()
        String sessionName = session.toString()
        this.session.close();
        logger.info("Closed session {}",sessionName);
        this.cluster.close();
        logger.info("Closed cluster {}",clusterName);
    }

    public List createAgentNetwork(int numberOfAgents) {
        List agentsList = new ArrayList()
        agentsList.add(new Agent(this.session))

        while (agentsList.size() < numberOfAgents) {
            def random = new Random();
            def i = random.nextInt(agentsList.size())
            Object Agent1 = agentsList[i]
            Object Agent2 = new Agent(this.session)
            Agent1.knowsAgent(Agent2)
            agentsList.add(Agent2)
        }
        logger.info("Created a network of "+numberOfAgents+ " Agents")
        return agentsList;
    }

    public void flushVertices(String labelName) {
      Map params = new HashMap();
      params.put("labelName", labelName);

      SimpleGraphStatement s = new SimpleGraphStatement("g.V().has(label,labelName).drop()",params);
      GraphResultSet rs = session.executeGraph(s);
      logger.warn("Executed statement: {}", Utils.getStatement(rs));
      logger.warn("Execution warnings from the server: {}", Utils.getWarnings(rs));
      logger.info("Dropped vertexes with label {} from OfferNet", labelName);
    }

    public Object flushVertices() {
      SimpleGraphStatement s = new SimpleGraphStatement("g.V().drop()");
      GraphResultSet rs = session.executeGraph(s);
      logger.warn("Executed statement: {}", Utils.getStatement(rs));
      logger.warn("Execution warnings from the server: {}", Utils.getWarnings(rs));
      logger.info("Dropped all vertexes from OfferNet");
      return this;
    }

    public List getIds(String labelName) {
      Map params = new HashMap();
      params.put("labelName", labelName);

      SimpleGraphStatement s = new SimpleGraphStatement("g.V().has(label,labelName).id()",params);
      GraphResultSet rs = session.executeGraph(s);
      logger.warn("Executed statement: {}", Utils.getStatement(rs));
      logger.warn("Execution warnings from the server: {}", Utils.getWarnings(rs));

      List<Object> agentIds = rs.all();
      logger.info("Retrieved list of {} agentIds from OfferNet", agentIds.size());
      return agentIds;
    }

    public addRandomWorksToAgents(int numberOfWorks) {
        List agentIds = this.getIds('agent');
        numberOfWorks.times {
            def random = new Random();
            def i = random.nextInt(agentIds.size())
            def agent = new Agent(agentIds[i],this.session);
            def ownsWork = agent.ownsWork();
            logger.info("Added ownsWork link {} to agent {}", ownsWork, agent.id());
        }
        logger.info("Added "+numberOfWorks+" of random processes to the network")
    }

    def Object addChainToNetwork(List chain) {
        def dataItemsWithDesignedSimilarities = new ArrayList()
        def chainedWorks = []
        for (int x=0;x<chain.size()-1;x++) {
            def random = new Random();
            def agentIds = this.getIds('agent');
            def i = random.nextInt(agentIds.size())
            def agent = new Agent(agentIds[i],this.session)
            def work = new Work(this.session);
            chainedWorks.add(work.id())
            def demand = work.addDemand(new Item(chain[x],this.session))
            def offer = work.addOffer(new Item(chain[x+1],this.session))
            agent.ownsWork(work);
            //print a list with items which have designed similarities in the network
            switch (x) {
                case 0:
                    // for the first item in chain we add only offer
                    logger.info("First item with designed similarity {}:{}", offer,offer.getValue())
                    break
                case chain.size()-2:
                    //for the last item in chain we add only demand
                    logger.info("Last item with designed similarity {}:{}", demand,demand.getValue())
                    break
                default:
                    //otherwise we add both, because it has similarity both ways
                    logger.info("Item with designed similarity {}:{}", demand,demand.getValue())
                    logger.info("Item with designed similarity {}:{}", offer,offer.getValue())
                    break
            }
        }
        logger.info("Added a chain to the network {}",chainedWorks)
        return chainedWorks;
    }

    /* 
    * Note that this function is for testing only - it calculates the perfect similarities between items
    */ 
    public List allConnectedSimilarPairs() {
        logger.warn("Centralized search of all demand-offer pairs with perfect similarities in the network");

        SimpleGraphStatement s = new SimpleGraphStatement(
                "g.V().match("+
                "__.as('g').has(label,'work').as('w').out('offers').as('o').properties('value').value().as('b')"+
                ",__.as('o').out('similarity').as('d'),__.as('d').properties('value').value().as('b')"+
                ",__.as('d').in('demands').as('w2')"+
                ").select('b','o','d')");

        GraphResultSet rs = session.executeGraph(s);
        List pairs = rs.all();
        logger.info("Found {} demand-offer pairs existing in the network", pairs.size());

        return pairs;

    }

    // this query mysteriously does not work -- looks like something is wrong with the type of 'v', as sometimes it works and sometimes not.

    public List allSimilarPairs() {
        logger.warn("Centralized search of connected demand-offer pairs with perfect similarities in the network");

        SimpleGraphStatement s = new SimpleGraphStatement(
                "g.V().match("+
                "__.as('g').has(label,'work').out('offers').as('o').properties('value').value().as('v')"+
                ",__.as('g').has(label,'work').out('demands').as('d').properties('value').value().as('v')"+
                ").select('o','d','v')");

        GraphResultSet rs = session.executeGraph(s);
        List pairs = rs.all();
        logger.info("Found {} demand-offer pairs existing in the network", pairs.size());

        return pairs;

    }

    public List allSimilarityEdges() {
        logger.warn("Returning all similarity links");

        SimpleGraphStatement s = new SimpleGraphStatement(
                "g.E().has(label,'similarity')");

        GraphResultSet rs = session.executeGraph(s);
        List edges = rs.all();
        logger.info("Found {} similarity Edges existing in the network", edges.size());

        return edges;
    }


}
