#!/usr/bin/env python

import networkx as nx
import sys

G = nx.DiGraph()
file = open(str(sys.argv[1]))
print "number of vertexes and edges "+ file.readline()
for line in file:
    edge = line.split()
    if len(edge) == 2:
        G.add_edge(int(edge[0]),int(edge[1]))
    else:
        print 'the result is: ' + str(edge[0])
print "Edges: "+str(nx.number_of_edges(G))
print "Nodes: "+str(nx.number_of_nodes(G))
sccno = nx.number_strongly_connected_components(G)
print "Number of strongly connected coponents: "+str(sccno)
print ('I.e.: ')
for c in nx.strongly_connected_components(G):
    print str(c)
