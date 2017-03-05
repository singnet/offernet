#!/usr/bin/env python
import networkx as nx
import sys

print ('Arguments: numberOfVertexes, numberOfEdges, outputFileName')
print ('Generating graph: ')
print ('Vertexes: ' + sys.argv[1])
print ('Edges: '+sys.argv[2] )
print ('To file: ' + sys.argv[3])
G = nx.barabasi_albert_graph(int(sys.argv[1]), int(sys.argv[2]))
f = open(sys.argv[3], 'w')
f.write(str(nx.number_of_nodes(G)))
f.write(" ")
f.write(str(nx.number_of_edges(G)))
f.write("\n")

# writing down the graph
for edge in nx.edges(G):
    f.write(str(edge[0]+1))
    f.write(" ")
    f.write(str(edge[1]+1))
    f.write("\n")

f.close()
