public class Agent  {
	private Object id;
	private Client client; 

	private Agent(Client client) {
		this.client= client;
		this.id = clRient.submit("g.V().addV('agent')").one().getVertex().id();
	}

	private Agent(Object id, Client client) {
		this.id = id;
		this.client =client;
	}

	public static Object knowsAgent(Agent agent) {
        Map params = new HashMap();
        params.put("agent1", this.id);
        params.put("agent2",agent.id());
        params.put("label","knows");

        def edge = client.submit("g.v(agent1).addE(label).to(g.v(agent2))",params).one().getEdge();
        logger.info("Added knows edge {} to the network", edge.toString());
        return edge.id();
    }

    public static Edge ownsProcess() {
    	return ownsProcess(new Process(this.client));
    }

	public static Edge ownsProcess(Process process) {
        Map params = new HashMap();
        params.put("agent", this.id);
        params.put("process",process.id());
        params.put("label","owns");

        def edge = client.submit("g.v(agent).addE(label).to(g.v(process))",params).one().getEdge();
        logger.info("Added owns edge {} to the network", edge.toString());
        return edge;
    }



    public static id() {
    	return this.id;
    }
}