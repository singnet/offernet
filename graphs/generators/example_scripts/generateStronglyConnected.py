#!/usr/bin/env python

import networkx as nx
import sys

print ('Arguments: numberOfVertexes,outputFileName')
print ('Generating cycle graph graph: ')
print ('Vertexes: ' + sys.argv[1])
# print ('Edges: ' + sys.argv[2])
print ('To file: ' + sys.argv[2])
G = nx.cycle_graph(int(sys.argv[1]), create_using=nx.DiGraph())
print ('Turns out to have: '+str(nx.number_of_edges(G))+' edges')
print ('And '+str(nx.number_strongly_connected_components(G))+' strongly connected components')
sccno = nx.number_strongly_connected_components(G)
print ('I.e.: ')
for c in nx.strongly_connected_components(G):
    print str(c)
f = open(sys.argv[2], 'w')
f.write(str(nx.number_of_nodes(G)))
f.write(" ")
f.write(str(nx.number_of_edges(G)))
f.write("\n")
for edge in nx.edges(G):
    f.write(str(edge[0]+1))
    f.write(" ")
    f.write(str(edge[1]+1))
    f.write("\n")
# for week2/acyclic result of the acyclicity (1-thereis a cycle; 0-no cycle;2-unknown)
f.write(str(sccno))
f.close()
