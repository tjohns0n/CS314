/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//Declare Websocket to talk to server
var webSocket;
//Build URI for client side, based off of their window (OS dependent)
var loc = window.location, new_uri;
if (loc.protocol === "https:") {
    new_uri = "wss:";
} else {
    new_uri = "ws:";
}
new_uri += "//" + document.location.host + document.location.pathname;
new_uri += "cs314";//Server end point reference, corrosponds to java server endpoint
//Establish connection from client URI
webSocket = new WebSocket(new_uri);
//Defualt On Open function, empty for now but can add funtionality
webSocket.onopen = function () {
    writeResponse('try to connect');
    // Check onOpen Here
};

//Default on message function
//Where message recieved from server and handled
webSocket.onmessage = function (event) {
    var message = event.data;//Grab message string
    var obj = JSON.parse(message);//Turn message string into JSON
    switch (obj.Key)//Switch on JSON key
    {
        
    }
};

//Default function called when session closed
webSocket.onclose = function () {
    // Check onClose Here
};
//Default function called when error occurs (i.e 404)
webSocket.onerror = function () {
    // Check onError Here
};


function writeResponse(text){
    var messages = document.getElementById("content");
    messages.innerHTML += "<br/>" + text;
}

webSocket.binaryType = "arraybuffer";

function sendFile() {
        
    var file = document.getElementById('filename').files[0];
    if (!file.name.includes("xml")) {
        
        // Not Acceptable FileName
        // TODO?

    } else{
        
        var reader = new FileReader();
        var rawData = new ArrayBuffer(); 
        
        reader.onload = function(e) {
            data = e.target.result;
            var obj = new Object();
            obj.Key = 'ReadXML';
            obj.FileName  = file.name;
            var jsonString= JSON.stringify(obj);
            webSocket.send(jsonString);
            webSocket.send(rawData);
            writeResponse("trying to send");
        };
        reader.readAsArrayBuffer(file);
    }
}