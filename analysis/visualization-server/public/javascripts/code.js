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
          'content': 'data(display)',
          'text-wrap' : 'wrap'
        }
      },
      {
        selector: 'edge',
        style: {
          'content': 'data(display)',
          'text-wrap' : 'wrap',
          'curve-style': 'bezier',
          'target-arrow-shape': 'triangle'
        }
      },
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
          'line-color': '#ffce66',
        }
      },
      {
        selector: 'edge[label="owns"]',
        style: {
          'width': 2,
          'line-color': '#7be452',
        }
      },
      {
        selector: 'edge[label="demands"]',
        style: {
          'width': 2,
          'line-color': '#b30000',
        }
      },
      {
        selector: 'edge[label="offers"]',
        style: {
          'width': 2,
          'line-color': '#663300',
        }
      },
      {
        selector: 'edge[label="similarity"]',
        style: {
          'width': 2,
        }
      }

    ],

    elements: {
      nodes: [],
      edges: []
      }

  });


cy.on('tap', 'node', function(e){

  // display node properties on top
  // by this id
  var nodeid = e.target.id();
  console.log('nodeId is: ',nodeid)
  toggleProperties(nodeid);

});

cy.on('tap', 'edge', function(e){

  // display edge properties on the bottom
  // by this id
  var edgeid = e.target.id();
  console.log(edgeid)
  toggleProperties(edgeid);

});
