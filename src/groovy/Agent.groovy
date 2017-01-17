@Grab(group='com.datastax.cassandra', module='dse-driver', version='1.1.1')
@Grab(group='com.datastax.cassandra', module='java-dse-graph', version='1.0.0-beta1')
@Grab(group='log4j', module='log4j', version='1.2.17')

import com.datastax.driver.dse.DseCluster;
import com.datastax.driver.dse.DseSession;

import com.datastax.driver.dse.graph.GraphStatement;
import com.datastax.driver.dse.graph.SimpleGraphStatement;
import com.datastax.driver.dse.graph.GraphResultSet
import com.datastax.driver.dse.graph.GraphOptions

import com.datastax.driver.dse.graph.Vertex
import com.datastax.driver.dse.graph.Edge

import org.apache.log4j.PropertyConfigurator
import org.slf4j.Logger
import org.slf4j.LoggerFactory

//import Work;

public class Agent  {
    private Vertex vertex;
	private DseSession session; 
    private Logger logger;


	public Agent(DseSession session) {

        def config = new ConfigSlurper().parse(new File('configs/log4j-properties.groovy').toURL())
        PropertyConfigurator.configure(config.toProperties())
        logger = LoggerFactory.getLogger('OfferNet.class');

		this.session= session;

        Map params = new HashMap();
        params.put("labelValue", "agent");

        GraphResultSet rs = session.executeGraph(new SimpleGraphStatement("g.addV(label, labelValue)", params));
        this.vertex = rs.one().asVertex();

        logger.warn("Created a new {} with id {}", vertex.getLabel(), vertex.getId());
	}

	public Agent(Vertex vertex, DseSession session) {
		this.vertex = vertex;
		this.session =session;
	}

	private Object knowsAgent(Agent agent) {
        Map params = new HashMap();
        params.put("agent1", vertex.getId());
        params.put("agent2",agent.vertex.getId());
        params.put('edgeLabel','knows');

        logger.warn("Creating knows edge from agent {} to agent {}", params.agent1, params.agent2)


        SimpleGraphStatement s = new SimpleGraphStatement(
                "def v1 = g.V(agent1).next()\n" +
                "def v2 = g.V(agent2).next()\n" +
                "v1.addEdge(edgeLabel, v2)", params)

        GraphResultSet rs = session.executeGraph(s);
        def edge = rs.one().asEdge();
        logger.info("Added knows edge {} to the network", edge);
        return edge;
    }

    /*
    private Object ownsWork() {
    	return ownsWork(new Work(this.client));
    }

	private Object ownsWork(Work process) {
        logger.warn("Creating owns edge from agent {} to work {}", this.id, process.id)

        Map params = new HashMap();
        params.put("agent", this.id);
        params.put("process",process.id());
        params.put("edgeLabel","owns");

        def edgeId = client.submit("g.withSideEffect('a',g.V(process)).V(agent).addOutE(edgeLabel,'a').id()",params).all().get().first().object;

        logger.info("Added owns edge {} to the network", edgeId.toString());
        return edgeId;
    }
    */
    private id() {
    	return vertex.getId();
    }

}