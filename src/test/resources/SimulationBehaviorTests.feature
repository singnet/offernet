Feature: OfferNet processes

The structure of OfferNet is realized as a Property Graph where agents / offers and demands connect via edges and all is enriched with properties. Agents interact with the graph (query/mutate) by executing  semantically constrained graph traversal processes. The result of these processes depend on the context of operation, but we want to make sure that they are deterministic when the context is known and clearly defined. These processes can be centralized or decentralized.

<realize SimulationTests as scenarios here>