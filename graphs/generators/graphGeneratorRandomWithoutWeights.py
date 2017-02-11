#!/usr/bin/env python
import networkx as nx
import sys

print ('Arguments: numberOfVertexes, numberOfEdges, outputFileName')
print ('Generating graph: ')
print ('Vertexes: ' + sys.argv[1])
print ('Edges: ' + sys.argv[2])
print ('To file: ' + sys.argv[3])
G = nx.gnm_random_graph(int(sys.argv[1]), int(sys.argv[2]))
f = open(sys.argv[3], 'w')
f.write(str(sys.argv[1]))
f.write(" ")
f.write(str(sys.argv[2]))
f.write("\n")

# writing down the graph
for edge in nx.edges(G):
    f.write(str(edge[0]+1))
    f.write(" ")
    f.write(str(edge[1]+1))
    f.write("\n")

# writing down the source and target
f.write("1")
f.write(" ")
f.write(str(sys.argv[1]))
f.write("\n")
# for week2/acyclic result of the acyclicity (1-thereis a cycle; 0-no cycle;2-unknown)
f.write("unknown")
f.close()
