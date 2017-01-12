import org.apache.tinkerpop.gremlin.structure.Direction;

public class Process  {
	private Client client; 
	private Vertex process;

	private Process(Client client) {
		this.client= client;

        Map params = new HashMap();
        params.put("process", "process");
        this.process = client.submit("g.V().addV(process)",params).one().getVertex()
        this.process.addDemand();
        this.process.addOffer();
	}

	private Process(Object id, Client client) {
		this.id = id;
		this.client =client;
	}

	// getters & setters

    public static id() {
    	return this.id;
    }

    public static Item addDemand() {
    	return addItem(new Item(client), "demands");
    }

    public static Item addDemand(Item item) {
    	return addItem(item, "demands");
    }
	
    public static Item getDemands() {
    	Map params = new HashMap();
    	return getItems("demands");
    }

    public static Item addDemand() {
    	return addItem(new Item(client), "demands");
    }

    public static Item addDemand(Item item) {
    	return addItem(item, "demands");
    }
	
    public static List getDemands() {
        return getItems("demands");
    }

    public static Item addOffer() {
    	return addItem(new Item(client), "offers");
    }

    public static Item addOffer(Item item) {
    	return addItem(item, "offers");
    }
	
    public static List getOffers() {
        return getItems("offers");
    }

    public static Item addItem(Item item, String label) {
    	Map params = new HashMap();
        params.put("this", this.process.id());
        params.put("label", label);
        params.put("item",item.id());
     	def item = client.submit("g.v(this).addE(process).to(g.v(item))",params).one().getVertex();
     	logger.info("Added {} {} to process {}",label, item.toString(),this.process.toString());
     	return demand;
    }

    public static List getItems(String label) {
    	Map params = new HashMap();
        params.put("this", this.process.id());
        params.put("label", label);
        params.put("direction", Direction.OUT);
        def items = Utils.toList(client.submit("g.v(this).vertices(direction,label)",params).iterator());
        logger.info("Retrieved {} list {} from process {}", label, items.toString(),this.process.toString());
        return items;
    }


}	