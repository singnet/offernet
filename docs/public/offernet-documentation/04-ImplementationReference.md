# Implementation and Configuration Reference

Here we will document implementation decisions on the software level (including configuration) regarding [Open problems / features to consider] discussed in [Conceptual framework].

## Description of items of exchange (implementation)
Jump to conceptual discussion of [Description of items of exchange].

As of now, items of exchange are represented as binary vectors of fixed length. The idea is each bit represents a feature of the item -- 1 if item has this feature,  0 -- if not. The length of vectors (i.e. number of features) can be set in the [global configuration file](https://github.com/kabirkbr/offernet/blob/master/configs/offernet.conf). It seems that binary vectors are the best middle-way between fixed types and maps. We can easily simulate fixed types by defining a vocabulary and attributing binary string to every type -- then set similarity threshold to 1, which means that the system will consider similar only vectors that are identical (if cosine  similarity is used -- see [Similarity measure (implementation)] below).

## Similarity measure (implementation)
Jump to conceptual discussion of [Similarity measure]. 

Currently there are two options for calculating similarity -- modified hamming distance and cosine similarity. The latter is default, the former is easy to adopt, but is not included as configuration option as for now -- some change of code (and tests) would be needed to switch between two:

* **Modified Hamming distance** (hidden) just calculates the number of how many bits in the same position are equal to 1 in both vectors. Which means that it calculates how many same features the two items have in common. See code of function: [veitasSimilarity(CharSequence left, CharSequence right)](https://github.com/kabirkbr/offernet/blob/master/src/main/groovy/Utils.groovy#L70). The assumption here is that there is a global mapping of features (i.e. each position in a vector represents the same feature which are the same globally). This is a strong assumption which will probably not hold in anything close to real circumstances. That is the main reason why cosine similarity is default measure. 

* **Cosine similarity** (default). The immediate advantage is that when similarity measure is calculated as cosine between vectors, it does not depend on the vector length -- so decisions about similarity measure and representation of items are a bit more decoupled. Therefore, all kinds of system parameters, like similarityThreshold does not need to be adjusted when vector length changes (i.e. when items get explained by more features). See code of function: [cosineSimilarity(String left, String right)](https://github.com/kabirkbr/offernet/blob/master/src/main/groovy/Utils.groovy#L190). It is also more forward looking to further advancement of the framework towards conceptual description.

