package net.vveitas.offernet

import org.apache.log4j.PropertyConfigurator
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static org.junit.Assert.*

import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;

public class SimulationsTests {

		@Test
		void createOfferNetworkTest() {

			OfferNet on = new OfferNet();
			on.flushVertices();

			on.createAgentNetwork(10)
			on.addRandomWorksToAgents(20)
			def se1 = on.addChainToNetwork(Utils.createChain(3))
			def se2 = on.addChainToNetwork(Utils.createChain(2))

			// run search for similarities;
			// assert that what is found is the same to what is put in;

			on.close();
			on=null;

}
