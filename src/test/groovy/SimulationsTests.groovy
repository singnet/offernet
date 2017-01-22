package net.vveitas.offernet

import org.apache.log4j.PropertyConfigurator
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static org.junit.Assert.*

import org.junit.Test;
import org.junit.Ignore;
import org.junit.BeforeClass;
import org.junit.AfterClass;

public class SimulationsTests {

		@Test
		void createOfferNetworkTest() {

			Simulations.createAgentNetwork(10,25,[3,2])

			// test if nework was created correctly
		}
}
