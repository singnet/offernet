package net.vveitas.offernet

class CreateOfferNetwork {

	static main(args) {

		OfferNet on = OfferNet.getInstance();

		on.createAgentNetwork(10)
		on.addRandomWorksToAgents(20)
		on.addChainToNetwork(on.createChain(3))
		on.addChainToNetwork(on.createChain(2))

		on.close();

	}

}
