
function runLayoutCola(scope) {
	scope.layout(
		{
    		name: 'cola',
    		fit: true,
    		animate: true,
    		refresh: 2,
    		maxSimulationTime: 1000,
        alignment: function(node) {}
    		//radius: 30
  		}
  	).run();
}

function loadJson() {
  //console.log("loading JSON:")
  var path = $('#names-load-json').val()
  //console.log(path)
  var json = JSON.parse(path)
  console.log(json)
  for (i in json) {
    console.log("Adding cy element "+JSON.stringify(json[i]))
    cy.add(json[i])
  }
  layoutUpdate()
}