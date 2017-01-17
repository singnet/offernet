@Grab(group='org.apache.tinkerpop', module='gremlin-driver', version='3.0.1-incubating')

import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.structure.Direction;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import Item;

public class Work  {
	private Client client; 
	private Vertex process;

	private Work(Client client) {
		this.client= client;

        Map params = new HashMap();
        params.put("process", "process");
        this.process = client.submit("g.V().addV(process)",params).one().getVertex()
        this.process.addDemand();
        this.process.addOffer();
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
    	return addItem(new Item(client), "demands");
    }

    public Item addDemand(Item item) {
    	return addItem(item, "demands");
    }
	
    public Item getDemands() {
    	Map params = new HashMap();
    	return getItems("demands");
    }

    public Item addOffer() {
    	return addItem(new Item(client), "offers");
    }

    public Item addOffer(Item item) {
    	return addItem(item, "offers");
    }
	
    public List getOffers() {
        return getItems("offers");
    }

    public Item addItem(Item item, String label) {
    	Map params = new HashMap();
        params.put("this", this.process.id());
        params.put("label", label);
        params.put("item",item.id());
     	def itemVertex = client.submit("g.v(this).addE(process).to(g.v(item))",params).one().getVertex();
     	logger.info("Added {} {} to process {}",label, itemVertex.toString(),this.process.toString());
     	return itemVertex;
    }

    public List getItems(String label) {
    	Map params = new HashMap();
        params.put("this", this.process.id());
        params.put("label", label);
        params.put("direction", Direction.OUT);
        def items = Utils.toList(client.submit("g.v(this).vertices(direction,label)",params).iterator());
        logger.info("Retrieved {} list {} from process {}", label, items.toString(),this.process.toString());
        return items;
    }


}	