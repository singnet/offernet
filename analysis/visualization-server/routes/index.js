var express = require('express');
var router = express.Router();
var jsonfile = require('jsonfile');
const javaPort = 7776; // port for socket connection between java app and nodejs;
const wssPort = 7778; // port for websocket server
const socketIOPort = 7777; // port for socket communication between nodejs and web frontend;

const io = require('socket.io')();

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Offernet graph visualization'});
});

module.exports = router;

/* Socket.io server for sending events to the web page for visualization*/
function webServer() {
	io.origins(['http://localhost:3000','127.0.0.1:7777']);
	io.listen(socketIOPort);

	io.on('connect', (socket) => {
		console.log("Web fronted connected to port "+socketIOPort);
	});
}

function sendToFrontend(event) {
	io.emit(event.eventType,event.data);
}


/* Plain socket server for listening to events from groovy application */
function javaServer() {

	var javaServer = require('net').createServer();
	var WebSocketServer = require('ws').Server
	 , wss = new WebSocketServer({port: wssPort});

	javaServer.on('listening', function () {
	   console.log('javaServer is listening on ' + javaPort);
	});

	javaServer.on('error', function (e) {
	    console.log('Server error: ' + e.code);
	});

	javaServer.on('close', function () {
	   console.log('Server closed');
	});

	javaServer.on('connection', function (javaSocket) {
		var clientAddress = javaSocket.address().address + ':' + javaSocket.address().port;
		console.log('Java ' + clientAddress + ' connected');

		var dataListenner = function (data) {
			var json = JSON.parse(data.toString());
//			console.log("javaServer received data:")
//			console.log(json);
	    	sendToFrontend(json);
		}	

		javaSocket.on('data', dataListenner);

		javaSocket.on('close', function() {
	        console.log('Java ' + clientAddress + ' disconnected');
	 	});
	});

	javaServer.listen(javaPort);
}

function getClass(obj) {
  if (typeof obj === "undefined")
    return "undefined";
  if (obj === null)
    return "null";
  return Object.prototype.toString.call(obj)
    .match(/^\[object\s(.*)\]$/)[1];
}

webServer();
javaServer();