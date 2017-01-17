@Grab(group='org.apache.tinkerpop', module='gremlin-driver', version='3.0.1-incubating')
@Grab(group='log4j', module='log4j', version='1.2.17')

import org.apache.log4j.PropertyConfigurator
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import Work;

public class Agent  {
	private Object id;
	private Client client; 
    private Logger logger;


	public Agent(Client client) {

        def config = new ConfigSlurper().parse(new File('configs/log4j-properties.groovy').toURL())
        PropertyConfigurator.configure(config.toProperties())
        logger = LoggerFactory.getLogger('OfferNet.class');

		this.client= client;

        Map params = new HashMap();
        params.put("labelValue", "agent");

		this.id = client.submit("g.addV(label,labelValue).id()",params).all().get().first().object;
        logger.warn("Created a new Agent with id {}", this.id);
	}

	public Agent(Object id, Client client) {
		this.id = id;
		this.client =client;
	}

	private Object knowsAgent(Agent agent) {
        logger.warn("Creating knows edge from agent {} to agent {}", this.id, agent.id)
        Map params = new HashMap();
        params.put("agent1", this.id);
        params.put("agent2",agent.id());
        params.put('edgeLabel','knows');

        def edgeId = client.submit("g.withSideEffect('a',g.V(agent2)).V(agent1).addOutE(edgeLabel,'a').id()",params).all().get().first().object;
        logger.info("Added knows edge {} to the network", edgeId);
        return edgeId;
    }

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

    private id() {
    	return this.id;
    }
}