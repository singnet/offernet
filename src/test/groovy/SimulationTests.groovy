package net.vveitas.offernet

import org.apache.log4j.PropertyConfigurator
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static org.junit.Assert.*

import org.junit.Test;
import org.junit.Ignore;
import org.junit.BeforeClass;
import org.junit.AfterClass;

import com.datastax.driver.dse.graph.GraphStatement;
import com.datastax.driver.dse.graph.SimpleGraphStatement;
import com.datastax.driver.dse.graph.GraphResultSet
import com.datastax.driver.dse.graph.GraphOptions

public class SimulationTests {

		@Test
		void cycleSearchTest() {
			def sim = new Simulation()
			assertNotNull(sim);
			sim.one();

			// test if nework was created correctly
		}

		private Integer numsimilarityEdgesNotLessSimilar(Integer similarityConstraint) {
			Map params = new HashMap();
			params.put("similarityConstraint", similarityConstraint);

			SimpleGraphStatement s = new SimpleGraphStatement(
						"g.E().has(label,'similarity').has('similarity',gte(similarityConstraint).count()",params);
			GraphResultSet rs = session.executeGraph(s);

			return rs.one().object;
		}
}
