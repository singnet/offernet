#!/usr/bin/env python
# source: http://stackoverflow.com/questions/13543069/how-to-create-random-single-source-random-acyclic-directed-graphs-with-negative

import networkx as nx
import sys
import random

print ('Arguments: numberOfVertexes,outputFileName')
print ('Generating random Powerlaw tree: ')
print ('Vertexes: ' + sys.argv[1])
# print ('Edges: ' + sys.argv[2])
print ('To file: ' + sys.argv[2])


G=nx.gnp_random_graph(int(sys.argv[1]),0.1,directed=True)
DAG = nx.DiGraph([(u,v,{'weight':random.randint(-10,10)}) for (u,v) in G.edges() if u<v])
print ('Turns out to have: '+str(nx.number_of_edges(DAG))+' edges')
print ('Check if DAG: '+str(nx.is_directed_acyclic_graph(DAG)))
order = nx.topological_sort(DAG, reverse=True)
print ('Topological order: '+str(order))
f = open(sys.argv[2], 'w')
f.write(str(nx.number_of_nodes(DAG)))
f.write(" ")
f.write(str(nx.number_of_edges(DAG)))
f.write("\n")
print ('Edges: '+str(nx.edges(DAG)))
for edge in nx.edges(DAG):
    f.write(str(edge[0]+1))
    f.write(" ")
    f.write(str(edge[1]+1))
    f.write("\n")

##  this returns reverse post-order ordering. In oder to make it non-reverse post-order:
for vertex in reversed(order):
    f.write(str(vertex+1))
    f.write(str(" "))
f.write("\n")
f.close()
