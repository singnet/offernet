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
        session.close();
        cluster.close();
    }

    public List createChain(int length) {
        List chain = []
        lenght.times {
            chain.add(generateBinaryString(16))
        }
        logger.info("Created chain of length with: { end: {}, start: {} }",chain.last(),chain.first())
        return chain
    }

    public createAgentNetwork(int numberOfAgents) {
        List agentsList = new ArrayList()
        agentsList.add(new Agent(this.client))

        while (AgentsList.size() < numberOfAgents) {
            def random = new Random();
            def i = random.nextInt(AgentsList.size())
            Object Agent1 = AgentsList[i]
            Object Agent2 = new Agent(this.client)
            Agent1.knowsAgent(Agent2)
            agentsList.add(Agent2)
        }
        logger.info("Created a network of "+numberOfAgents+ " Agents")
    }


    public addRandomWorksToAgents(int numberOfWorks) {
        numberOfWorks.times {
            def random = new Random();
            def i = random.nextInt(AgentsList.size())
            AgentsList[i].ownsWork();
        }
        logger.info("Added "+numberOfWorks+" of random processes to the network")
    }

    def addChainToNetwork(List chain) {
        def dataItemsWithDesignedSimilarities = new ArrayList()
        for (x=0;x<chain.size()-1;x++) {
            def random = new Random();
            def i = random.nextInt(AgentsList.size())
            def process = AgentsList[i].ownsWork(new Work().addDemand(new Item(chain[x])).addOffer(new Item(chain[x+1])));
            def demand = process.getDemands().first()
            def offer = process.getOffers().first()
            //print a list with items which have designed similarities in the network
            switch (x) {
                case 0:
                    // for the first item in chain we add only offer
                    logger.info("Item with designed similarity {}:{}", offer,offer.value)
                    break
                case chain.size()-2:
                    //for the last item in chain we add only demand
                    logger.info("Item with designed similarity {}:{}", demand,demand.value)
                    break
                default:
                    //otherwise we add both, because it has similarity both ways
                    logger.info("Item with designed similarity {}:{}", demand,demand.value)
                    logger.info("Item with designed similarity {}:{}", offer,offer.value)
                    break
            }
        }
        logger.info("Added a chain to the network")
    }


}
