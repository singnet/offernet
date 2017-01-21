package net.vveitas.offernet

class CreateOfferNetwork {

	static List main(args) {

		OfferNet on = new OfferNet();
		on.flushVertices();

		on.createAgentNetwork(10)
		on.addRandomWorksToAgents(20)
		def chain1 = on.addChainToNetwork(Utils.createChain(3))
		def chain2 = on.addChainToNetwork(Utils.createChain(2))

		// run search algorithm and check if chains are discovered

		on.close();
		on=null;

		return [se1,se2]

	}

}
