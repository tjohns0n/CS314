/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//Requires Java EE
//Should be run within NetBeans
package TripCo;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author jimx
 */

//Corrosponds to Server endpoint name in Server.js for clients
@ServerEndpoint("/cs314")
public class WebSocket {
    //Default root directory for Server End point
    //Working directory for everything called within executable classes
    private final static String workdir = WebSocket.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    //Resource files root directory
    //SVG/XML filepath start here
    //Equal to /resources folder
    private final static String fileRoot= workdir.substring(0, workdir.indexOf("WEB-INF/"));

    private final static Query query = new Query();;
    
    //Primary Message Handling method. 
    //default stub is string, changed to void for our example
    @OnMessage
    public void onMessage(Session session, String message) {
        System.out.println("[ServerSide] message : " + message);
        //Make message JSON
        JsonReader reader = Json.createReader(new StringReader(message));
        JsonObject json = reader.readObject();//Recieved Message JSON
        //Grab key of message
        String selectionKey = removeQuotes(json.get("Key").toString());
        
        // Message Redirection
        switch (selectionKey){
            
            /* Case <--Init-->
             * This will be called when Webpage First setting up
             * Input Json {Key = 'Init', Value = ''}
             * Output Json {Key = 'Init', Type = '(types)', Contient = '(continents)', Country = '(countries)'}
             */
            case "Init":
                initWebPage(session);
                break;

            /* Case <--Search-->
             * This will be called when Webpage Search specific content
             * Input Json {Key = 'Search', Value = 'content'}
             * Output Json {Key = 'Search', Identifier = 'idts', Name = 'AirportNames'}
             */ 
            case "Search":
                searchQuery(session, json);
                break;

            /* Case <--ReadXML-->  NOTE:THIS IS IN MY TODO LIST
             * This will be called when User upload XML File
             * Input Json {Key = 'ReadXML', Value = 'TBD'}
             * Output Json {Key = 'ReadXML'}
             */ 
            case "ReadXML":
                break;

            /* Case <--PlanTrip-->
             * This will be called when Webpage Search specific content
             * Input Json {Key = 'PlanTrip', Name = 'name', Identifier = 'idts', Option = 'opts(true/false)'}
             * boolean [1] = _id, [2] = _mileage, [3] = _name [4] = _km
             * Output Json {Key = 'PlanTrip', XML = 'xmlPath', SVG = 'svgPath'}
             * When Plantrip is Done, an JSON obj will be sent to JSX
             * JSX should load the file according to the path.
             */ 
            case "PlanTrip":
                planTrip(session, json);
                break;
        }
    }
    
    private JsonObject buildJSON(String key, String value){
        JsonObject json = Json.createObjectBuilder()
           .add("Key", key)
           .add("Value", value).build();
        return json;
    }

    //Send response message
    private void sendBack(Session session, JsonObject json){
        RemoteEndpoint.Basic remote = session.getBasicRemote();//Get Session remote end 
        try{
            System.out.println(json.toString());
            remote.sendText(json.toString());            
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    // refer to the comments in switch syntax
    private void initWebPage(Session session){
        String[] answer = query.initWebPage();
        JsonObject jso = Json.createObjectBuilder()
            .add("Key", "Init")
            .add("Type", answer[0])
            .add("Contient", answer[1])
            .add("Country", answer[2]).build();
        sendBack(session, jso);
    }

    // refer to the comments in switch syntax
    private void searchQuery(Session session, JsonObject json){
        String content = removeQuotes(json.get("Value").toString());
        String[] answer = query.searchQuery(content);
        JsonObject jso = Json.createObjectBuilder()
            .add("Key", "Init")
            .add("Identifier", answer[0])
            .add("Name", answer[1]).build();
        sendBack(session, jso);
    }

    // refer to the comments in switch syntax
    private void planTrip(Session session, JsonObject json){
        String name = removeQuotes(json.get("Name").toString());
        String[] idts = removeQuotes(json.get("Identifier").toString()).split(",");
        String[] options = removeQuotes(json.get("Option").toString()).split(",");
        boolean[] opts = new boolean[options.length];
        for(int i = 0; i < options.length; i++)
            opts[i] = options[i].equals("true") ? true : false;
        // new TripCo(name, opts, idts);
        sendBack(session, json);
    }


    //Removes quotes from strings for JSON handling
    private String removeQuotes(String string){
        return string.replaceAll("\"", "");
    }

    //Default method called when connection open
    @OnOpen
    public void onOpen(Session session, EndpointConfig conf) {
        new File(fileRoot + session.getId()).mkdir();
        System.out.println("[ServerSide] message : " + fileRoot);//Print statement sanity check
        System.out.println("[ServerSide] " + session.getId() + " open successfully");
    }
    
    //Default method called on error
    @OnError
    public void onError(Session session, Throwable t) {
        System.out.println("[ServerSide] " + session.getId() + " gets error");
        t.printStackTrace();
    }

    //Default method called when session closed, record keeping done here
    @OnClose
    public void onClose(Session session, CloseReason reason) {
        System.out.println("[ServerSide] " + session.getId() + " socket closed");
        System.out.println("[ServerSide] " + reason.getReasonPhrase());
    }
    
}