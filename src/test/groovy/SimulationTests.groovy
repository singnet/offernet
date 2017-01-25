package net.vveitas.offernet

import org.apache.log4j.PropertyConfigurator
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static org.junit.Assert.*

import org.junit.Test;
import org.junit.Ignore;
import org.junit.BeforeClass;
import org.junit.AfterClass;

public class SimulationTests {

		@Test
		void cycleSearchTest() {
			def sim = new Simulation()
			assertNotNull(sim);
			sim.one();
			// test if nework was created correctly
		}
}
