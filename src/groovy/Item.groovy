@Grab(group='org.apache.tinkerpop', module='gremlin-driver', version='3.0.1-incubating')

import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.structure.Direction;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import Utils;

public class Item  {
	private Object id;
	private Client client; 

	private Item(Client client) {
		this.client= client;

        Map params = new HashMap();
        params.put("labelValue", "item");
        params.put("propertyKey", "value");
        params.put("propertyValue", Utils.generateBinaryString(16));
		this.id = client.submit("g.V().addV(label, labelValue, propertyKey ,propertyValue).id()",params).one().object;

	}

	private Item(Object id, Client client) {
		this.id = id;
		this.client =client;
	}

    public id() {
    	return this.id;
    }
}