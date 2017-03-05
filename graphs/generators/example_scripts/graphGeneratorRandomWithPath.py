#!/usr/bin/env python
import networkx as nx
import sys
import random

print ('Arguments: numberOfVertexes, numberOfEdges, outputFileName')
print ('Generating graph: ')
print ('Vertexes: ' + sys.argv[1])
print ('Edges: ' + sys.argv[2])
print ('To file: ' + sys.argv[3])
G = nx.gnm_random_graph(int(sys.argv[1]), int(sys.argv[2]))
G2 = nx.Graph()
for edge in nx.edges(G):
    source,target = edge
    G2.add_edge(source,target,cost=-random.randint(0,1000))
source = 1
target = int(sys.argv[1])
correctOutput = -1
if (nx.has_path(G2,source-1,target-1)):
    correctOutput = nx.shortest_path_length(G2,source=source-1,target=target-1,weight='cost')
f = open(sys.argv[3], 'w')
f.write(str(sys.argv[1]))
f.write(" ")
f.write(str(sys.argv[2]))
f.write("\n")

cost=nx.get_edge_attributes(G2,'cost')
# writing down the graph
for edge in nx.edges(G2):
    f.write(str(edge[0]+1))
    f.write(" ")
    f.write(str(edge[1]+1))
    f.write(" ")
    f.write(str(cost[edge]))
    f.write("\n")

# writing down the source and target
#f.write(str(source))
#f.write(" ")
#f.write(str(target))
#f.write("\n")
# for week4/dijkstra: -2 means unknown;
f.write(str(correctOutput))
f.close()
