
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
