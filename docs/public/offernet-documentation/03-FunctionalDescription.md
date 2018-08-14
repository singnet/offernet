# Functional Description

Functional description of the software is automatically generated from behavioural test suite (latest build report available is [here](../cucumber-html-reports/overview-features.html)). Every behaviour described below is already implemented and working. Note, therefore, that the aim of this section is to describe already **implemented** functionality, contrary to planned functionality, described elsewhere -- e.g. [Conceptual Framework]. Therefore #2, the contents of this section gets augmented every time the new behaviour is implemented and tested -- so evolves in parallel with the [codebase](https://github.com/singnet/offernet).

## Agent behaviour

Agents can interact with the graph structure of OfferNet by issuing semantically constrained graph traversals which query or mutate the graph as needed. These define the API of the OfferNet graph.

### Scenario: an agent can introduce a new agent to the network by 'befriending' it
The only way for the OfferNet to grow in a decentralized manner (i.e. without central controlling repository) is for new agents to get introduced to the network via old agent's recommendation.

* Given : Agent "AgentOne" is created on the OfferNet

* And : Agent "AgentTwo" does not exist on the OfferNet

* When : Agent "AgentOne" befriends Agent "AgentTwo"

* Then : Agent "AgentTwo" exists on the OfferNet

* Then : there is "knows" link between "AgentOne" and "AgentTwo"

* And : there is no "knows" link between "AgentTwo" and "AgentOne"

### Scenario: an agent can 'befriend' another agent existing in the network
OfferNet is a social network of Agents. Each agent has an ability to initiate creation of 'knows' link to another agent existing in the network. So if two unconnected agents exist on the network and one of them knows the identity of the other, then the first one can initiate creation of 'knows' link to another in the underlying graph which should result in the appropriate relation between them. Note that the 'knows' relation is not reciprocal.

* Given : Agent "AgentOne" is created on the OfferNet

* And : Agent "AgentTwo" is created on the OfferNet

* And : there is no "knows" link between "AgentOne" and "AgentTwo"

* And : there is no "knows" link between "AgentTwo" and "AgentOne"

* When : Agent "AgentOne" befriends Agent "AgentTwo"

* Then : there is "knows" link between "AgentOne" and "AgentTwo"

* And : there is no "knows" link between "AgentTwo" and "AgentOne"

### Scenario: an agent can post an {offer-demand} pair to the OfferNet
Agents in an Offer Network engage in exchange by publishing an offer-demand pairs, or 'work's. See explanation of the Offer Network structure in [Data structures and objects].

* Given : Agent "AgentOne" is created on the OfferNet

* When : Agent "AgentOne" posts a pair of offer "0.15" and demand of "0.57"

* Then : Agent "AgentOne" owns a work which "demands" item "0.57"

* And : Agent "AgentOne" owns a work which "offers" item "0.15"

### Scenario Outline: an agent can access all posted offers and demands by traversing its social network
In a distributed system where there is no global observer which has the registry of all agents and their items of offer and demand. In this case, the only way for an agent to access items of other agents is to traverse its social network (to a given depth) and ask all friends of friends to send information about their items. Since the OfferNet is fully connected (and hopefully is a small world network) the whole network can be scanned this way, depending on availability of time and resource constrains of an agent which initiates the search. The expected number of items that an agent has to find in its social network is calculated as follows: $(numberOfAgents -1) * pairsPerAgent * 2$ (as every pair holds two items: one demand and one offer..).

#### Example 1

* Given : there exists an sequentially connected line of '4' agents

* And : all agents have posted '1' offer-demand pair each

* When : first agent initiates search of '3' depth

* Then : agent finds '6' demands and offers in its social network

#### Example 2

* Given : there exists an sequentially connected line of '20' agents

* And : all agents have posted '2' offer-demand pair each

* When : first agent initiates search of '19' depth

* Then : agent finds '76' demands and offers in its social network

### Scenario Outline: an agent can connect similar offers and demands of its social network
After an agent finds offer and demand items posted by its social network which are similar to its own items, it can connect those items with weighted $similarity$ relations (edges in the graph). Weight of the relation represents the 'strength' of similarity. Definition and computing algorithm of a $similarity$ relation can be chosen by choosing configuration parameters of the OfferNet system during initialization -- allowing to experiment with different approaches. This mechanism addresses these [Open problems / features to consider] of the framework: [Representation / theory of value], [Description of items of exchange], [Similarity measure];

#### Example 1

* Given : a connected network of '4' agents

* Given : that '3' similar item pairs exist in the network

* When : any agent finds similar items by executing search of '3' depth

* Then : there are '3' similarity relations in the network

#### Example 2

* Given : a connected network of '20' agents

* Given : that '5' similar item pairs exist in the network

* When : any agent finds similar items by executing search of '19' depth

* Then : there are '5' similarity relations in the network
