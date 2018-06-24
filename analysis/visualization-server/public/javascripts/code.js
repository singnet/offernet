var cy = window.cy = cytoscape({
    container: document.getElementById('cy'),

    boxSelectionEnabled: false,
    autounselectify: true,
    layout: {
      name: 'cola',
      fit: true,
      animate: true,
      refresh: 2,
      maxSimulationTime: 1000
    },

    style: [
      {
        selector: 'node',
        style: {
          'background-color': '#ad1a66'
        }
      },

      {
        selector: 'edge',
        style: {
          'width': 3,
          'line-color': '#ad1a66'
        }
      }
    ],

    elements: {
      nodes: [],
      edges: []
      }

  });
