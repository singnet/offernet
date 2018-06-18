# Implementation

## Plans, goals & phases 

High level goals of the programme are described below. These goals are further (micro)managed via GitHub project management in singnet/offernet repository -- see [here](https://github.com/singnet/offernet/projects) or links related to each item. All issues/tasks are broken into 6 projects:

1. [Computational framework](https://github.com/singnet/offernet/projects/3);
1. [Conceptual framework](https://github.com/singnet/offernet/projects/2);
1. [Simulation modelling](https://github.com/singnet/offernet/projects/1);
1. [Analysis engine](https://github.com/singnet/offernet/projects/4);
1. [Documentation](https://github.com/singnet/offernet/projects/5);
1. [Integration with SingularityNET prototype](https://github.com/singnet/offernet/projects/6);

### Goals

(in no particular order)

* Medium-term: enable Offer Networks as an alternative to token-based exchange on SingularityNET….  Or rather, as a superclass of token-based exchange, since one type of offer that can be made is “to pay to X a certain number N of tokens of type T”;
* Implementation-wise / medium term: formulate offers and demands in a predicate-logic type format, compilable into executable smart contract form -- [issue](https://github.com/singnet/offernet/issues/18);
* Continuous: Integration with SingularityNET prototype (Python, Solidity, Ethereum, etc.) -- [project](https://github.com/singnet/offernet/projects/6);
* Short term: find an actual AI task to simulate for demonstration, communication purposes, conceptual coherence, etc -- for boosting a fast prototyping process [issue](https://github.com/singnet/offernet/issues/19).
* Long-term: build an economic exchange network that would perform ‘better’ than purely monetary based exchanges (better in terms of global value created, customer satisfaction, etc.) -- [issue](https://github.com/singnet/offernet/issues/16);
* Continuous: build and perform a fast prototyping / modelling / experimenting pipeline that feeds back to the conceptual development -- [project](https://github.com/singnet/offernet/projects/1);
* Short-term: design the architecture which allows to combine -- [project](https://github.com/singnet/offernet/projects/1):
	* rich/expensive small-scale experiments, which let us explore and understand in-depth application implementation strategies, but aren't actually more efficient than centralized approaches at the scale on which they're being run;
	* more simplistic scalable simulations, aimed at demonstrating/exploring the efficiency advantage achievable via decentralized methods at large scale

### Phases

* **(short-term)**: Exploratory work implementing Offer Networks atop SingularityNET and running preliminary experiments and simulations aimed at gaining knowledge and intuition. It may be beneficial to first construct experiments and simulations of isolated open problems / features to consider (search, memory, etc.) and get insights about them individually before integrating everything into one system. For this we may need to work out a more fine-grained schedule of goals / phases -- milestones [one](https://github.com/singnet/offernet/milestone/2) and [two](https://github.com/singnet/offernet/milestone/5).

* **(medium-term)**: Get a concrete sense of what sorts of AI-related exchanges are going to have the property that Offer Network type exchange works better for them than token based exchange -- [issue](https://github.com/singnet/offernet/issues/16);

* **(long-term)**: Design and implement a scalable version of Offer Networks within SingularityNET, for handling an appropriate subset of AI interactions -- [project](https://github.com/singnet/offernet/projects/6);

## Simulation modelling

The whole rationale of building the framework is to test conceptual ideas and hypotheses via simulation modelling. The way we are doing it roughly follows the following sequence: 

(1) formulate a research question / hypothesis;
(2) design an experiment / simulation that could answer it;
(3) implement in software (which usually involves pushing the capabilities of the framework further);
(4) run, analyse, formulate & publish results;
(5) go back to 1. and start all over again...

This is again managed via GibHub -- see the [Simulation modelling project](https://github.com/singnet/offernet/projects/1) on singnet/offenet repo. Below are  descriptions of already conceived experiments. Hopefully more will be added as we move forward, but we are not going to plan for more than 6-12 months into the future...

### Compare decentralized and centralized search

* Github issue: [#15](https://github.com/singnet/offernet/issues/15);
* Due: milestone [v0.1.0](https://github.com/singnet/offernet/milestone/2);

The goal of Offer Networks could be operationalized as: *search and execute globally optimal set of exchanges that would maximally satisfy outstanding offers/demands by maximum number of agents* -- see also [@goertzel_matching_2015]. This goal can be achieved by centralized (global) or decentralized (local) search and matching of offer-demand pairs. Goal of this experiment is to compare these two modes and get insights about their relative advantages, disadvantages and trade-offs.

#### Definitions

* **Centralized search** is a search on the global state of the network. In other words, a process which is performing centralized search has full and perfect knowledge of all agents and their outstanding offers and demands. Such process then can pull all this information into a single data structure and calculate a set of exchanges that would optimize stated preferences of agents at the moment.

	* Advantages: global optimality -- the solution found is knowingly optimal;
	* Disadvantage #1: depending on the size of the network may need a lot of memory in order to represent the whole network available for single process;
	* Disadvantage #2: searching for optimal solution is computationally expensive and (may) lead to combinatorial explosion(s);
	* Disadvantage #3 (derived from #2): exchange preferences (and preferences in general) have time value -- agents in general would not be willing to wait for too long until their offers/demands are met; which means that globally optimal solution which cannot be reached *fast enough* may not be optimal at all... 

* **Decentralized search** (or sometimes called 'local' search) is a search performed by an agent which does not have a privileged access /full information about the global state of the network and other agents. Decentralized process tries to pull as much locally available information as needed in order to achieve "good enough" satisfiability of individual preferences of an agent performing search /matching. 

	* Advantage #1: agents can potentially achieve better individual satisfaction of their preferences (since they do not need to calculate all possibilities leading to optimal solution);
	* Advantage #2: the individual process is potentially faster (due to above);
	* Disadvantage #1: global optimality is not guaranteed;
	* Disadvantage #2: competition between agents without global arbiter may lead to inefficiencies;

#### Research question

In what context (in terms of size of network, rate of change, connectivity of agents, etc.) decentralized search provides better results than centralized (and vice versa) in terms of number of matches found per unit of time. 

#### Set-up

* Option #1: Benchmark centralized and decentralized algorithms on the same pre-constructed data structures of different sizes and complexities (offer network with known number of simple / complex matches); Calculate the trade-off between memory and time needed in both cases;

* Option #2: Set-up a dynamic network, where agents constantly add/withdraw offers and demands; benchmark centralized and decentralized algorithms on such network;

In theory both options should be equivalent, but the second option is a more realistic (and somewhat more difficult to implement) simulation of the Offer Networks dynamics which potentially could lead to more insights. Anyway we start with the first option. 

### Design and demonstrate learning capabilities of the network

* Github issue: [#17](https://github.com/singnet/offernet/issues/17);
* Due: milestone [v0.2.0](https://github.com/singnet/offernet/milestone/5);

Preliminary research question: Building on [Compare decentralized and centralized search] experiment see if network learning / collaboration between agents would help decentralized search / matching approach globally optimal solution.

### Complex barter transactions on top of monetary economy

* Github issue: [#1](https://github.com/singnet/offernet/issues/16);
* Due: December 31, 2018 (milestone [v0.3.0](https://github.com/singnet/offernet/milestone/4));

Design: TBD

## Implementation and Configuration Reference

Here we will document implementation decisions on the software level (including configuration) regarding [Open problems / features to consider] discussed in [Conceptual framework].

### Description of items of exchange (implementation)
Jump to conceptual discussion of [Description of items of exchange].

As of now, items of exchange are represented as binary vectors of fixed length. The idea is each bit represents a feature of the item -- 1 if item has this feature,  0 -- if not. The length of vectors (i.e. number of features) can be set in the [global configuration file](https://github.com/kabirkbr/offernet/blob/master/configs/offernet.conf). It seems that binary vectors are the best middle-way between fixed types and maps. We can easily simulate fixed types by defining a vocabulary and attributing binary string to every type -- then set similarity threshold to 1, which means that the system will consider similar only vectors that are identical (if cosine  similarity is used -- see [Similarity measure (implementation)] below).

### Similarity measure (implementation)
Jump to conceptual discussion of [Similarity measure]. 

Currently there are two options for calculating similarity -- modified hamming distance and cosine similarity. The latter is default, the former is easy to adopt, but is not included as configuration option as for now -- some change of code (and tests) would be needed to switch between two:

* **Modified Hamming distance** (hidden) just calculates the number of how many bits in the same position are equal to 1 in both vectors. Which means that it calculates how many same features the two items have in common. See code of function: [veitasSimilarity(CharSequence left, CharSequence right)](https://github.com/kabirkbr/offernet/blob/master/src/main/groovy/Utils.groovy#L70). The assumption here is that there is a global mapping of features (i.e. each position in a vector represents the same feature which are the same globally). This is a strong assumption which will probably not hold in anything close to real circumstances. That is the main reason why cosine similarity is default measure. 

* **Cosine similarity** (default). The immediate advantage is that when similarity measure is calculated as cosine between vectors, it does not depend on the vector length -- so decisions about similarity measure and representation of items are a bit more decoupled. Therefore, all kinds of system parameters, like similarityThreshold does not need to be adjusted when vector length changes (i.e. when items get explained by more features). See code of function: [cosineSimilarity(String left, String right)](https://github.com/kabirkbr/offernet/blob/master/src/main/groovy/Utils.groovy#L190). It is also more forward looking to further advancement of the framework towards conceptual description.
