//loading log4j properties
def config = new ConfigSlurper().parse(new File('configs/log4j-createOfferNetwork.groovy').toURL())
PropertyConfigurator.configure(config.toProperties())
logger = LoggerFactory.getLogger('createOfferNetwork.groovy');

Service service = Service.getInstance();

List framedAgentsList


def createChain(int lenght) {
    List chain = []
    lenght.times {
        chain.add(Utils.generateBinaryString(16))
    }
    logger.info("Created chain: "+chain.toString())
    logger.info("Closing process (not added): { demand: {}, offer: {} }",chain.last(),chain.first())
    return chain
}

def createAgentNetwork(int numberOfAgents) {
    framedAgentsList = new ArrayList()
    framedAgentsList.add(Globals.network.g.addVertex(null,FramedAgent.class))
    def random = new Random();

    while (framedAgentsList.size() < numberOfAgents) {
        def i = random.nextInt(framedAgentsList.size())
        FramedAgent framedAgent1 = framedAgentsList[i]
        FramedAgent framedAgent2 = Globals.network.g.addVertex(null,FramedAgent.class)
        framedAgent1.addKnowsAgent(framedAgent2)
        framedAgentsList.add(framedAgent2)
        Globals.network.commit()
    }
    logger.info("Created a network of "+numberOfAgents+ " agents;")
    logger.info('With parameters: ')
    Parameters.parameters.each {k,v -> logger.info(k + ": " + v)}
}

def addRandomProcess(FramedAgent agent) {
    def process = agent.addOwnsProcess()
    Utils.getOwnsEdge(agent, process).setProperty("weight", Parameters.parameters.initialOwnsWeight)
    process.setType("work")
    Utils.getDemandsEdge(process, process.addDemand()).setProperty("weight", Parameters.parameters.initialDemandsWeight)
    Utils.getOffersEdge(process, process.addOffer()).setProperty("weight", Parameters.parameters.initialOffersWeight)

	def demandDataItem = process.asVertex().outE('label','demands').inV.next()
	def offerDataItem = process.asVertex().outE('label','offers').inV.next()
	
	logger.info("Added a random process {} with {}:{} demand and {}:{} offer", \
		process,demandDataItem.toString(),demandDataItem.value,offerDataItem.toString(),offerDataItem.value)
    Globals.network.commit()
	return process
}

def addNonRandomProcess(FramedAgent agent, String demandDataItem, String offerDataItem) {
    def process = agent.addOwnsProcess()
    Utils.getOwnsEdge(agent, process).setProperty("weight", Parameters.parameters.initialOwnsWeight)
    process.setType("work")
    def demand = process.addDemand()
    demand.setValue(demandDataItem)
    Utils.getDemandsEdge(process, demand).setProperty("weight", Parameters.parameters.initialDemandsWeight)

    def offer = process.addOffer()
    offer.setValue(offerDataItem)
    Utils.getOffersEdge(process, offer).setProperty("weight", Parameters.parameters.initialOffersWeight)

    logger.info("Added a non-random process {} with {}:{} demand and {}:{} offer", \
		process,demand.toString(),demand.value,offer.toString(),offer.value)
    Globals.network.commit()
	return process
}

def addRandomProcessesToAgents(numberOfProcesses) {
    def random = new Random();
    numberOfProcesses.times {
        def i = random.nextInt(framedAgentsList.size())
        addRandomProcess(framedAgentsList[i])
    }
    logger.info("Added "+numberOfProcesses+" of random processes to the network")
}

def addChainToNetwork(List chain) {
    def random = new Random();
	def dataItemsWithDesignedSimilarities = new ArrayList()
    for (x=0;x<chain.size()-1;x++) {
        def i = random.nextInt(framedAgentsList.size())
        def process = addNonRandomProcess(framedAgentsList[i],chain[x],chain[x+1])
		def demand = process.getDemands()[0]
		def offer = process.getOffers()[0]
		//construct a list with items which have designed similarities in the network
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

createAgentNetwork(10)
addRandomProcessesToAgents(20)
def chain1 = createChain(3)
addChainToNetwork(chain1)
def chain2 = createChain(4)
addChainToNetwork(chain2)
