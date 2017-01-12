import json

class GraphSONTransformer(object):

    def __init__(self, path):
        self.path  = path

        # Stubs for lazy loaded properties
        self._data   = None
        self._idxmap = None

    @property
    def graph(self):
        """
        Returns the graph object of the loaded data from the path.
        """
        if not self._data:
            with open(self.path, 'r') as data:
                self._data = json.load(data)
        return self._data['graph']

    @property
    def idxmap(self):
        """
        Returns a positional index based on the index of the node.
        """
        if not self._idxmap:
            self._idxmap = {node['_id']: idx for idx, node in enumerate(self.graph['vertices'])}
        return self._idxmap

    @property
    def nodes(self):
        """
        Gets all the nodes in order and returns a list.
        """

        CHARACTER  = 1
        GROUP      = 2
        PLACE      = 3
        TECHNOLOGY = 4
        GENERIC    = 5

        typemap = {
            "thing": GENERIC,
            "sith lord": CHARACTER,
            "princess": CHARACTER,
            "jedi": CHARACTER,
            "droid": CHARACTER,
            "smuggler": CHARACTER,
            "bounty hunter": CHARACTER,
            "crime lord": CHARACTER,
            "pilot": CHARACTER,
            "imperial": CHARACTER,
            "star fighter": TECHNOLOGY,
            "freighter": TECHNOLOGY,
            "group": GROUP,
            "location": PLACE,
            "planet": PLACE,
            "moon": PLACE,
        }

        def vertex_iter(graph, typemap):
            for vertex in graph['vertices']:
                v     = vertex.copy()