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
import com.datastax.driver.dse.DseCluster;
import com.datastax.driver.dse.DseSession;

import com.datastax.dse.graph.api.DseGraph;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource
import org.apache.tinkerpop.gremlin.structure.Vertex

public class SimulationTests {

		@Ignore // for now -- takes too much time
		@Test
		void cycleSearchTest() {
			def sim = new Simulation()
			assertNotNull(sim);
			
			def chains = [Utils.createChain(3),Utils.createChain(2)]
			def agentList = this.createAgentNetwork(10,20,chains);
			sim.connectIfSimilarForAllAgents(agentList,Parameters.parameters.similarityThreshold,2);

			agentList.each { agent ->
				def works = agent.get
			}

			//search Cycles (not implemented yet)

			// test if nework was created correctly
		}

		@Ignore // this test fails therefore I do not use fluent API for now (does not give much advantage over DSEGraph API)
		@Test
		void fluentAPITest() {
			DseCluster dseCluster = DseCluster.builder().addContactPoint("192.168.1.6").build();
			DseSession dseSession = dseCluster.connect();

			GraphTraversalSource g = DseGraph.traversal(dseSession, new GraphOptions().setGraphName("offernet"));

			// Now you can use the Traversal source and use it **as if** it was working against a local graph, and with the usual TinkerPop API. All the network is done transparently.
			Vertex agent = g.V().hasLabel("agent").next();
			println agent
			Vertex demand = agent.outE('owns').inV().outE('demands').inV().hasLabel('item')
			println demand
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
