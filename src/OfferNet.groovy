/**
* OfferNet class describes the general structure and global operations on it needed for the simulation
**/

public class OfferNet {

    private Cluster cluster;
    private Client client; // creating one 'main' client and allowing to create more with the method
    private List agentsList;

    /**
     * Create Service as a singleton given the simplicity of App.
     */
    private static final Service INSTANCE = new Service();

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

    public static Client createClient() throws Exception {
        return cluster.connect();
    }

    public static Client closeClient(Client client) throws Exception {
        return client.close();
    }

    @Override
    public void close() throws Exception {
        client.close();
        cluster.close();
    }


    public static List createChain(int length) {
        List chain = []
        lenght.times {
            chain.add(generateBinaryString(16))
        }
        logger.info("Created chain of length with: { end: {}, start: {} }",chain.last(),chain.first())
        return chain
    }

    public static createAgentNetwork(int numberOfAgents) {
        agentsList = new ArrayList()
        agentsList.add(new Agent(this.client))

        while (agentsList.size() < numberOfAgents) {
            def random = new Random();
            def i = random.nextInt(agentsList.size())
            Object agent1 = agentsList[i]
            Object agent2 = new Agent(this.client)
            agent1.knowsAgent(agent2)
            agentsList.add(agent2)
        }
        logger.info("Created a network of "+numberOfAgents+ " agents")
    }


    public static addRandomProcessesToAgents(numberOfProcesses) {
        numberOfProcesses.times {
            def i = random.nextInt(agentsList.size())
            agentsList[i].ownsProcess();
        }
        logger.info("Added "+numberOfProcesses+" of random processes to the network")
    }

    def addChainToNetwork(List chain) {
        def dataItemsWithDesignedSimilarities = new ArrayList()
        for (x=0;x<chain.size()-1;x++) {
            def random = new Random();
            def i = random.nextInt(agentsList.size())
            def process = agentsList[i].ownsProcess(new Process().addDemand(new Item(chain[x])).addOffer(new Item(chain[x+1]));
            def demand = process.getDemands()[0]
            def offer = process.getOffers()[0]
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