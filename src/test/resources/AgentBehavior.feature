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

Scenario: an agent can create a work (offer-demand) pair
	Agents in an Offer Network engage in exchange by publishing an offer-demand pair, or 'work'. See explanation of the Offer Network structure in https://kabirkbr.github.io/offernet/offernet-docs-bookdown/book/computational-framework.html#data-structures-and-objects.

	Given Agent "AgentOne" is created on the OfferNet
	When Agent "AgentOne" publishes a pair of offer "offer-1" and demand of "demand-1"
	Then Agent "AgentOne" owns a work which "demands" item "demand-1"
	And Agent "AgentOne" owns a work which "offers" item "offer-1"