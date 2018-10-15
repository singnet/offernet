import networkx as nx
import osmnx as ox
import sys

G = nx.read_graphml(sys.argv[1])
nodes = list(G.nodes)
print ('Number of all nodes: ', G.number_of_nodes())
i=0
while i< len(nodes):
	node = nodes[i]
#	print ('Node ',i,'has id ',node)
	if 'label=agent' not in node:
		G.remove_node(node)
	i = i+1

print ('Number of agent nodes: ', G.number_of_nodes())
# try to calculate all shortest paths and then see if can get diameter from there.
all = nx.all_shortest_paths(G)
#print ('Diameter of agent-> knows-> agent graph: ',d)
#basic_stats = ox.basic_stats(G)
#print(basic_stats)

