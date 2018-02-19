#!/usr/local/bin/node
var portfinder = require('/usr/local/lib/node_modules/portfinder');
var connect = require('/usr/local/lib/node_modules/connect');
var serveStatic = require('/usr/local/lib/node_modules/serve-static');

portfinder.getPort(function (err,port) {
    if (err) {
	console.log ("Could not connect ");
    } else {
	connect().use(serveStatic(__dirname)).listen(port);
	console.log("Node Server is listening on port " + port);
    }});
