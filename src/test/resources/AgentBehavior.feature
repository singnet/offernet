Feature: Agent behaviour

Agents can interact with the graph structure of OfferNet by issuing semantically constrained graph traversals which query or mutate the graph as needed. These define the API of the OfferNet graph.

Scenario: an agent can introduce a new agent to the network by 'befriending' it
	The only way for the OfferNet to grow in a decentralized manner (i.e. without central controlling repository) is for new agents to get introduced to the network via old agent's recommendation.

	Given Agent "AgentOne" is created on the OfferNet
	And Agent "AgentTwo" does not exist on the OfferNet
	When Agent "AgentOne" befriends Agent "AgentTwo"
	Then Agent "AgentTwo" exists on the OfferNet
	Then there is "knows" link between "AgentOne" and "AgentTwo"
	And there is no "knows" link between "AgentTwo" and "AgentOne"

Scenario: an agent can 'befriend' another agent existing in the network
	OfferNet is a social network of Agents. Each agent has an ability to initiate creation of 'knows' link to another agent existing in the network. So if two unconnected agents exist on the network and one of them knows the identity of the other, then the first one can initiate creation of 'knows' link to another in the underlying graph which should result in the appropriate relation between them. Note that the 'knows' relation is not reciprocal.

	Given Agent "AgentOne" is created on the OfferNet
	And Agent "AgentTwo" is created on the OfferNet
	And there is no "knows" link between "AgentOne" and "AgentTwo"
	And there is no "knows" link between "AgentTwo" and "AgentOne"
	When Agent "AgentOne" befriends Agent "AgentTwo"
	Then there is "knows" link between "AgentOne" and "AgentTwo"
	And there is no "knows" link between "AgentTwo" and "AgentOne"

Scenario: an agent can post an {offer-demand} pair to the OfferNet
	Agents in an Offer Network engage in exchange by publishing an offer-demand pairs, or 'work's. See explanation of the Offer Network structure in https://kabirkbr.github.io/offernet/offernet-docs-bookdown/book/computational-framework.html#data-structures-and-objects.

	Given Agent "AgentOne" is created on the OfferNet
	When Agent "AgentOne" posts a pair of offer "offer-1" and demand of "demand-1"
	Then Agent "AgentOne" owns a work which "demands" item "demand-1"
	And Agent "AgentOne" owns a work which "offers" item "offer-1"

Scenario Outline: an agent can access all posted offers and demands by traversing its social network
	In a distributed system where there is no global observer which has the registry of all agents and their items of offer and demand. In this case, the only way for an agent to access items of other agents is to traverse its social network (to a given depth) and ask all friends of friends to send information about their items. Since the OfferNet is fully connected (and hopefully is a small world network) the whole network can be scanned this way, depending on availability of time and resource constrains of an agent which initiates the search. The expected number of items that an agent has to find in its social network is calculated as follows: (<numberOfAgents> -1) * <pairsPerAgent> * 2 (as every pair holds two items: one demand and one offer..).

	Given there exists an sequentially connected line of '<numberOfAgents>' agents
	And all agents have posted '<pairsPerAgent>' offer-demand pair each
	When first agent initiates search of '<searchDepth>' depth
	Then agent finds '<numberOfFoundItems>' demands and offers in its social network

	Examples:
	| numberOfAgents | pairsPerAgent | searchDepth | numberOfFoundItems |
	| 4 |  1 | 3 | 6 |
	| 20 |  2 | 19 | 76 |


#Scenario: an agent can connect similar offers and demands of its social network
#	Empty scenario