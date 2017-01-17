@Grab(group='org.apache.tinkerpop', module='gremlin-driver', version='3.0.1-incubating')
@Grab(group='log4j', module='log4j', version='1.2.17')

import org.apache.log4j.PropertyConfigurator
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import OfferNet;

import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.structure.Direction;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;


class Tests {
	private static OfferNet on;
    private static Logger logger;

	static main(args) {
		def config = new ConfigSlurper().parse(new File('configs/log4j-properties.groovy').toURL())
        PropertyConfigurator.configure(config.toProperties())
        logger = LoggerFactory.getLogger('OfferNet.class');
        on = OfferNet.getInstance();
		getResultAsObject();
		on.close();
	}

	static getResultAsObject() {
        Map params = new HashMap();
        params.put("labelValue", "test");
        params.put("key1", "param1");
        params.put("value1", "param1");
        params.put("key2", "param2");
        params.put("value2", "param2");

		def result = on.client.submit("g.addV(label,labelValue,key1,value1,key2,value2)",params).all().get().first();
        logger.warn("Created a new test vertex with object {}", result);
	}

}
