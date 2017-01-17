@Grab(group='com.datastax.cassandra', module='dse-driver', version='1.1.1')
@Grab(group='log4j', module='log4j', version='1.2.17')

import org.apache.log4j.PropertyConfigurator
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import OfferNet;
import Agent;

import static org.junit.Assert.assertNotNull


class Tests {
	private static OfferNet on;
    private static Logger logger;

	static main(args) {
		def config = new ConfigSlurper().parse(new File('configs/log4j-properties.groovy').toURL())
        PropertyConfigurator.configure(config.toProperties())
        logger = LoggerFactory.getLogger('OfferNet.class');
        on = OfferNet.getInstance();
        def agent1 = new Agent(on.session);
        assertNotNull(agent1);
        def agent2 = new Agent(on.session);
        assertNotNull(agent2);
        def edge = agent1.knowsAgent(agent2);
        assertNotNull(edge);
		on.close();
	}

}
