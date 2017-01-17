@Grab(group='org.apache.tinkerpop', module='gremlin-driver', version='3.0.1-incubating')

import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.structure.Direction;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import Item;

import org.apache.log4j.PropertyConfigurator
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static org.junit.Assert.*


public class Work  {
	private Client client; 
	private Object id;
    private Logger logger

	private Work(Client client) {

        def config = new ConfigSlurper().parse(new File('configs/log4j-properties.groovy').toURL())
        PropertyConfigurator.configure(config.toProperties())
        logger = LoggerFactory.getLogger('Work.class');

		this.client= client;

        Map params = new HashMap();
        params.put("labelValue", "work");
        this.id = client.submit("g.addV(label,labelValue).id()", params).all().get().first().object;
        this.addDemand();
        this.addOffer();
	}

	private Work(Object id, Client client) {
		this.id = id;
		this.client =client;
	}

	// getters & setters

    public id() {
    	return this.id;
    }

    public Item addDemand() {
    	return addItem(new Item(client), 'demands');
    }

    public Item addDemand(Item item) {
    	return addItem(item, 'demands');
    }
	
    public Item getDemands() {
    	Map params = new HashMap();
    	return getItems('demands');
    }

    public Item addOffer() {
    	return addItem(new Item(client), 'offers');
    }

    public Item addOffer(Item item) {
    	return addItem(item, 'offers');
    }
	
    public List getOffers() {
        return getItems("offers");
    }

    public Item addItem(Item item, String labelName) {
    	Map params = new HashMap();
        params.put("thisVertex", this.id);
        params.put("edgeLabel", (String) labelName);
        params.put("targetVertex",item.id());
        logger.info("Executing query : g.withSideEffect('a',g.V({})).V({}).addOutE({},'a').id()", params.targetVertex, params.thisVertex, params.edgeLabel);
        def resultList = client.submit("g.withSideEffect('a',g.V(targetVertex)).V(thisVertex).addOutE(edgeLabel,'a').id()",params).all().get().stream().collect()

        logger.info("get resultList of size {}", resultList.size());
     	return item;
    }

    public List getItems(String labelName) {
    	Map params = new HashMap();
        params.put("thisVertex", this.process.id());
        params.put("edgeLabel", label);
        params.put("edgeDirection", Direction.OUT);
        def items = Utils.toList(client.submit("g.V(thisVertex).vertices(edgeDirection,edgeLabel)",params).iterator());
        logger.info("Retrieved {} list {} from process {}", edgeLabel, items.toString(),this.process.toString());
        return items;
    }


}	