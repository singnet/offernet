@Grab(group='org.apache.tinkerpop', module='gremlin-driver', version='3.0.1-incubating')
@Grab(group='log4j', module='log4j', version='1.2.17')

import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.driver.Cluster;

import org.apache.log4j.PropertyConfigurator
import org.slf4j.Logger
import org.slf4j.LoggerFactory


public class OfferNet implements AutoCloseable {

    private Cluster cluster;
    private Client client; // creating one 'main' client and allowing to create more with the method
    private List AgentsList;
    private Logger logger;

    /**
     * Create Service as a singleton given the simplicity of App.
     */
    private static final OfferNet INSTANCE = new OfferNet();

    private OfferNet() {
        //loading log4j properties
        def config = new ConfigSlurper().parse(new File('configs/log4j-properties.groovy').toURL())
        PropertyConfigurator.configure(config.toProperties())
        logger = LoggerFactory.getLogger('OfferNet.class');

        try {
            cluster = Cluster.build(new File("configs/driver-settings.yaml")).create();
            client = cluster.connect();
            logger.info("Created OfferNet instance with client {}", client);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static OfferNet getInstance() {
        return INSTANCE;
    }

    @Override
    public void close() throws Exception {
        client.close();
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
        AgentsList = new ArrayList()
        AgentsList.add(new Agent(this.client))

        while (AgentsList.size() < numberOfAgents) {
            def random = new Random();
            def i = random.nextInt(AgentsList.size())
            Object Agent1 = AgentsList[i]
            Object Agent2 = new Agent(this.client)
            Agent1.knowsAgent(Agent2)
            AgentsList.add(Agent2)
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