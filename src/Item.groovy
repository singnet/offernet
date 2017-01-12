import Utils

public class Item  {
	private Vertex item;
	private Client client; 

	private Item(Client client) {
		this.client= client;

        Map params = new HashMap();
        params.put("value", Utils.generateBinaryString(16));
        params.put("type", "item");
		this.item = client.submit("g.V().addV(type).property('value',value)",params).one().getVertex();

	}

	private Item(Vertex item, Client client) {
		this.item = item;
		this.client =client;
	}

    public static id() {
    	return this.item.id();
    }
}