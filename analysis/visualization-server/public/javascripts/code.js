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
        selector: 'node[label="agent"]',
        style: {
          'background-color': '#ffce66'
        }
      },
      {
        selector: 'node[label="work"]',
        style: {
          'background-color': '#7be452'
        }
      },
      {
        selector: 'node[label="item"]',
        style: {
          'background-color': ' #e60000'
        }
      },
      {
        selector: 'edge[label="knows"]',
        style: {
          'width': 2,
          'line-color': '#ffce66'
        }
      },
      {
        selector: 'edge[label="owns"]',
        style: {
          'width': 2,
          'line-color': '#7be452'
        }
      },
      {
        selector: 'edge[label="demands"]',
        style: {
          'width': 2,
          'line-color': '#b30000'
        }
      },
      {
        selector: 'edge[label="offers"]',
        style: {
          'width': 2,
          'line-color': '#663300'
        }
      }
    ],

    elements: {
      nodes: [],
      edges: []
      }

  });
