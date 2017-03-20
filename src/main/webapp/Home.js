/* 
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
        var webSocket;
        var messages = document.getElementById("messages");
        
        // make uri dymatic suitbable to all versions
        var loc = window.location, new_uri;
        if (loc.protocol === "https:") {
            new_uri = "wss:";
        } else {
            new_uri = "ws:";
        }
        new_uri += "//" + document.location.host + document.location.pathname;
        new_uri += "Server";
        writeResponse(new_uri + ' ');
        // end of uri dymatic process
        
        webSocket = new WebSocket(new_uri);
        
        webSocket.binaryType = "arraybuffer";
        
        webSocket.onopen = function() {
            writeResponse("Welcome to DTR-14 GUI !");
        };

        webSocket.onmessage = function(event) {
            
            var msg = event.data;
            var obj = JSON.parse(msg);
            switch(obj.Key)
            {
                case "Message":
                    writeResponse(obj.Value);
                    break;
                case "Result":
                    showResult(obj.Root, obj.Name);
                    break;
            }
            
        };

        webSocket.onclose = function() {
            writeResponse("Connection is closed...");
        };
        webSocket.onerror = function(e) {
            writeResponse(e.msg);
        };
    

    function sendFile() {
        
        var file = document.getElementById('filename').files[0];
        if (!file.name.includes("svg")
                && !file.name.includes("csv")
                    && !file.name.includes("xml")) {
            writeResponse(file.name + ' not ACCETABLE!');
        } else{
            
            var reader = new FileReader();
            var rawData = new ArrayBuffer();            

            reader.loadend = function() {

            };  
            reader.onload = function(e) {
                rawData = e.target.result;
                var obj = new Object();
                obj.Key = 'File';
                obj.FileName  = file.name;
                obj.Value = rawData;
                var jsonString= JSON.stringify(obj);
                webSocket.send(jsonString);
                webSocket.send(rawData);
            };
            reader.readAsArrayBuffer(file);
        }
    }


    function startPlanMyTrip(){
        var name = new Boolean(document.getElementById("nCheck").checked);
        var milage = new Boolean(document.getElementById("mCheck").checked);
        var info = new Boolean(document.getElementById("infoCheck").checked);
        var _2opt = new Boolean(document.getElementById("_2optCheck").checked);
        var _3opt = new Boolean(document.getElementById("_3optCheck").checked);

        var obj = new Object();
        obj.Key = 'TripCo';
        obj._n = name;
        obj._m = milage;
        obj._i = info;
        obj._2 = _2opt;
        obj._3 = _3opt;
        var jsonString= JSON.stringify(obj);
        webSocket.send(jsonString);
        writeResponse("It's still not available yet");
    }
    
    function ListFile(){
        var obj = new Object();
        obj.Key = "ListFile";
        var jsonString = JSON.stringify(obj);
        webSocket.send(jsonString);
    }
    
    function deleteSVG(){
        deleteFile("SVG");
    }
    
    function deleteCSV(){
        deleteFile("CSV");
    }
    
    function deleteXML(){
        deleteFile("XML");
    }
    
    function deleteFile(ext){
        var obj = new Object();
        obj.Key = "DeleteFile";
        obj.Value = ext;
        var jsonString= JSON.stringify(obj);
        webSocket.send(jsonString);
    }
    
    function writeResponse(text){
        messages.innerHTML += "<br/>" + text;
    }
    
    function showResult(Root, Name){
        window.open("http://localhost:8080/TripCoOnline/View.html?" + Root + "&" + Name);
    }
     