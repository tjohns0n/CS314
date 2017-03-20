/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.csu2017sp314.DTR14.tripco;

/**
 *
 * @author jimx
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;

import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.websocket.RemoteEndpoint;

@ServerEndpoint("/Server")
public class WebServer {
    private static File uploadedFile = null;
    private static String fileName = null;
    private static FileOutputStream fos = null;
    private final static String filePath="./../../../../../TripCoOnline/src/main/webapp/webTemFile/";
    private final static HashMap<Session, String> maps = new HashMap<Session, String>();
    
    @OnOpen
    public void open(Session session, EndpointConfig conf) {
        new File(filePath + session.getId()).mkdir();
        System.out.println("Server: DTR-14 Server open successfully");
    }
    
    @OnMessage
    public void processUpload(ByteBuffer msg, Session session) { 
        while(msg.hasRemaining()) {         
            try {
                fos.write(msg.get());
            } catch (IOException e) {               
                e.printStackTrace();
            }
        }
        try {
            fos.flush();
            fos.close();                
        } catch (IOException e) {       
            e.printStackTrace();
        }
    }
    
    private void processFile(Session session, String fileName) {   
        System.out.println("Server: " + fileName);
        if (fileName.contains("csv")) maps.put(session, fileName.substring(0, fileName.indexOf('.')));
        uploadedFile = new File(filePath + session.getId() + "/" + fileName);
        try {
            fos = new FileOutputStream(uploadedFile);
        } catch (FileNotFoundException e) {     
            e.printStackTrace();
        }
        sendBackInformation(session, fileName + " sent successfully!");
        
    }
        
    @OnMessage
    public void message(Session session, String msg) {
        System.out.println("msg = " + msg);
        JsonReader reader = Json.createReader(new StringReader(msg));
        JsonObject jso = reader.readObject();
        String selectionKey = jso.get("Key").toString();
        selectionKey = selectionKey.replaceAll("^\"|\"$", "");
        switch (selectionKey){
            case "File":
                processFile(session, jso.get("FileName").toString().replaceAll("^\"|\"$", ""));
                break;
            case "TripCo":
                startPlanTrip(session, jso.get("_n").toString(), jso.get("_m").toString(), 
                        jso.get("_i").toString(), jso.get("_2").toString(), jso.get("_3").toString());
                break;
            case "ListFile":
                listFile(session);
                break;
            case "DeleteFile":
                deleteFile(session, jso.get("Value").toString().replaceAll("^\"|\"$", ""));
                break;
        }
        
    }

    @OnClose
    public void close(Session session, CloseReason reason) {
        File filedir = new File(filePath + session.getId());
        String[] files = filedir.list();
        for(String s: files){
            File currentFile = new File(filedir.getPath(),s);
            if (s.contains("csv")) maps.remove(session);
            currentFile.delete();
        }
        filedir.delete();
        
        System.out.println("Server: socket closed, "+ reason.getReasonPhrase());
    }

    @OnError
    public void error(Session session, Throwable t) {
        t.printStackTrace();
    }
    
    private void listFile(Session session){
        File filedir = new File(filePath + session.getId());
        String[] files = filedir.list();
        String fnames = "";
        for(String s: files){   
            fnames += s + "  ";
        }
        if(fnames.equals("")) fnames += "No Files In Storage";
        sendBackInformation(session, fnames);
    }
    
    private void deleteFile(Session session, String ext){
        File filedir = new File(filePath + session.getId());
        String[] files = filedir.list();
        for(String s: files){   
            if(s.toUpperCase().contains(ext)){
                File currentFile = new File(filedir.getPath(),s);
                if (s.contains("csv")) maps.remove(session);
                currentFile.delete();
            }
        }
        sendBackInformation(session, "Delete " + ext + "s successfully!");
    }
    
    private String selectFile(Session session, String ext){
        File filedir = new File(filePath + session.getId());
        String[] files = filedir.list();
        for(String s: files){   
            if(s.toUpperCase().contains(ext))
                return filedir.getPath();
        }
        return null;
    }
    private void sendBackInformation(Session session, String msg){
        JsonObject json = Json.createObjectBuilder()
               .add("Key", "Message")
               .add("Value", msg).build();
        RemoteEndpoint.Basic remote = session.getBasicRemote();
        try{
            remote.sendText(json.toString());
            System.out.println(json.toString());
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    private boolean checkValid(Session session){
        File filedir = new File(filePath + session.getId());
        String[] files = filedir.list();
        int[] couts = {1,1,1};
        for(String s: files){   
            if(s.toUpperCase().contains("CSV")) couts[0]--;
            if(s.toUpperCase().contains("SVG")) couts[1]--;
            if(s.toUpperCase().contains("XML")) couts[2]--;
        }
        return (couts[0] == 0 ? (couts[1] >= 0 ? (couts[2] >= 0 ? true : false) : true) : false);
    }
    
    private void startPlanTrip(Session session, String _n, String _m, String _i, String _2, String _3){
        
        //TODO:
        String _xml = selectFile(session, "XML");
        String _csv = selectFile(session, "CSV");
        String _svg = selectFile(session, "SVG");
        boolean[] opts = {false, _i.equals("true"), _m.equals("true"), _n.equals("true"),
                            _2.equals("true"), _3.equals("true")};
        if (_csv == null){
            sendBackInformation(session, "Needs CSV file, please check again");
            return;
        } 
        if (checkValid(session) == false){
            sendBackInformation(session, "No more than one same type file, please check again");
            return;
        }
        
//      static ArrayList<File> files;
        //co.initiate();
        
        JsonObject json = Json.createObjectBuilder()
               .add("Key", "Result")
               .add("Root", session.getId())
               .add("Name", (String)maps.get(session)).build();
        RemoteEndpoint.Basic remote = session.getBasicRemote();
        try{
            remote.sendText(json.toString());
            System.out.println(json.toString());
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
   
    private JsonObject jsonFromString(String jsonObjectStr) {
        JsonObject object;
        try (JsonReader jsonReader = Json.createReader(new StringReader(jsonObjectStr))) {
            object = jsonReader.readObject();
        }
        return object;
    }
}