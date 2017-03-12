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
import java.nio.ByteBuffer;

import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/Server")
public class Server {
    private static File uploadedFile = null;
    private static String fileName = null;
    private static FileOutputStream fos = null;
    private final static String filePath="./";

    @OnOpen
    public void open(Session session, EndpointConfig conf) {
        System.out.println("Server: DTR-14 Server open successfully");
    }

    @OnMessage
    public void processUpload(Session session, ByteBuffer msg) {   

        while(msg.hasRemaining()) {         
            try {
                fos.write(msg.get());
            } catch (IOException e) {               
                e.printStackTrace();
            }
        }

    }
    
    @OnMessage
    public void message(Session session, String msg) {
        System.out.println("Server: " + msg);
        
        if(msg.equals("end")) {
            try {
                fos.flush();
                fos.close();                
            } catch (IOException e) {       
                e.printStackTrace();
            }
        } else if(msg.equals("Starting Plan Your Trip")) {
            startPlanTrip();
        } else {
            
            String parts[] = msg.split("\\s+");
            fileName = parts[0];
            uploadedFile = new File(fileName);
            try {
                fos = new FileOutputStream(uploadedFile);
            } catch (FileNotFoundException e) {     
                e.printStackTrace();
            }
        }
    }

    @OnClose
    public void close(Session session, CloseReason reason) {
        System.out.println("Server: socket closed, "+ reason.getReasonPhrase());
    }

    @OnError
    public void error(Session session, Throwable t) {
        t.printStackTrace();

    }
    
    public void startPlanTrip(){
        
        //TODO:
        
        // I need para from website as listed
//      static String _xml;			//.xml
//	static String _csv;			//.csv
//	static String _svg;			//.svg
//	static boolean _gui;		//-g
//	static boolean _id;			//-i
//	static boolean _mileage;	//-m
//	static boolean _name;		//-n
//	static boolean _2opt;		//-2
//	static boolean _3opt;		//-3
//      static ArrayList<File> files;

        TripCo co = new TripCo();
        //co.initiate();
    }
}