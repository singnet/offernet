package net.vveitas.offernet

class Simulations {

	static List createAgentNetwork(Integer numberOfAgents, Integer numberOfRandomWorks, ArrayList chains) {

		OfferNet on = new OfferNet();
		on.flushVertices();

		on.createAgentNetwork(10)
		on.addRandomWorksToAgents(20)
		def se = [];
		chains.each {chainLength ->
			se.add(on.addChainToNetwork(Utils.createChain(chainLength)))
		}
		// run search algorithm and check if chains are discovered

		on.close();
		on=null;

		return se;

	}

}
