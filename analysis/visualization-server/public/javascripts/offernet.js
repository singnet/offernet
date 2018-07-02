const serverName = '127.0.0.1';
const serverPort = '7777';
const timeout = 1000;

function listenToEvents(serverName,serverPort) {
	const socket = io("http://"+serverName+":"+serverPort);	
	socket.on('newVertex', (data) => {
		newVertex(data[0]);
	});
	socket.on('newEdge', (data) => {
		newEdge(data[0]);
	});
}


listenToEvents(serverName,serverPort);

function layoutUpdate() {
	runLayoutCola(cy);
//	setTimeout(layoutUpdate,timeout)
}

function newVertex(vertex) {
	var vertexData = {group: "nodes", 
					  data: vertex };
	console.log("adding node: "+ JSON.stringify(vertex))
	cy.add(vertexData);
}

function newEdge(edge) {
	var edgeData = {group: "edges", 
		data: edge };
	console.log("adding edge: "+ JSON.stringify(edge))
	cy.add(edgeData);
}

layoutUpdate()

function toggleProperties(id) {
	var ele = cy.getElementById(id)
	var display = ele.data().display
	if (display == null || display == "") {
		var description = ele.data().description
		console.log('description of element '+id+' is '+JSON.stringify(description))
		console.log('JSON of element '+id+' is '+JSON.stringify(ele.json()))
		ele.data('display',description)
	} else {
		ele.data('display',"")
	}
}
