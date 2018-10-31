import networkx as nx
import osmnx as ox
import sys
import datetime

G = nx.read_graphml(sys.argv[1])
nodes = list(G.nodes)
print ('Number of all nodes: ', G.number_of_nodes())
start = datetime.datetime.now()
G = nx.to_undirected(G)
d = str(nx.diameter(G))
print ('Diameter of the whole graph: ',d)
print ('Time of calculation: ')
print(datetime.datetime.now() - start)

G = nx.read_graphml(sys.argv[1])
nodes = list(G.nodes)
i=0
while i< len(nodes):
	node = nodes[i]
#	print ('Node ',i,'has id ',node)
	if 'label=agent' not in node:
		G.remove_node(node)
	i = i+1

print ('Number of agent nodes: ', G.number_of_nodes())
# try to calculate all shortest paths and then see if can get diameter from there.
start = datetime.datetime.now()
G = nx.to_undirected(G)
d = str(nx.diameter(G))
print ('Diameter of agent-> knows-> agent graph: ',d)
print ('Time of calculation: ')
print(datetime.datetime.now() - start)
#basic_stats = ox.basic_stats(G)
#print(basic_stats)

