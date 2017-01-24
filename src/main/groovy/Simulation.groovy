package net.vveitas.offernet

class Simulation {
	OfferNet on;

	private Simulation() {
		on = new OfferNet();
		on.flushVertices();
	}

	public one() {
		def chains = [Utils.createChain(3),Utils.createChain(2)]
		def agentList = on.createAgentNetwork(10,20,chains);
		on.connectIfSimilarForAllAgents(agentList,8,3);
		//search Cycles (not implemented yet)

	}

	private List createAgentNetwork(Integer numberOfAgents, Integer numberOfRandomWorks, ArrayList chains) {

		def agentList = on.createAgentNetwork(numberOfAgents)
		on.addRandomWorksToAgents(numberOfRandomWorks)
		def se = [];
		chains.each {chainLength ->
			se.add(on.addChainToNetwork(Utils.createChain(chainLength)))
		}

		return se;

	}

	private void connectIfSimilarForAllAgents(List agentList, Integer similarityThreshold, Integer maxDistance) {
		logger.warn("Searching and connecting similar items of all agents in the graph:")
		def newConnectionsCreated = 0;
		agentList.each {agent ->
			 newConnectionsCreated += agent.searchAndConnect(similarityThreshold,maxDistance);
		}
		logger.warn("Created {} new 'distnace' links between items in the grahp",newConnectionsCreated)
	}




}
