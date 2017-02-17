//DTR-14
//Presenter.java
//Takes ArrayList of files and boolean array for options
package Presenter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import Model.Model;
import Model.LocationList;
import View.View;



public class Presenter implements Runnable{
	//ArrayList of files
	ArrayList<File> inFiles;
	
	//Optional booleans
	boolean ID;
	boolean name;
	boolean mileage;
	int port;
	
	//Server, not initially instantiated
	Server serv;
	
	public Presenter(ArrayList<File> files, boolean[] options){
		inFiles=files;
		port = 0;
		for(int i=0; i<options.length;i++){
			if(i==0)
				ID=options[i];
			if(i==1)
				name=options[i];
			if(i==2)
				mileage = options[i];
		}
	}
	public Presenter(ArrayList<File> files){
		inFiles=files;
		ID=false;
		name=true;
		mileage=false;
		port = 0;
	}
	public Presenter(ArrayList<File> files, int Port){
		inFiles=files;
		ID=false;
		name=true;
		mileage=false;
		port = Port;
	}
	@Override
	public void run() {
		//Instantiate and call Model to process input
		Model mod = new Model(inFiles.get(0).getName());
		
		//Input file reading and processing loop
		/* TODO
		 * Loop for multiple file handling, for later
		for(int i=0;i<files.size();i++){
			
		}
		*/
		
		
		LocationList ll = mod.getLocList();
		//TODO
		//Mem mgmt?
		//mod.close();
		View vw = new View(inFiles.get(0).getName(), mileage, name, ID);
		
		
		for(int i=0; i<ll.getsize();i++){
			//TODO
			//Send view necessary info for drawing SVG / writing XML 
			
			
			
		}
		//Place holders
		vw.writeSVG();
		vw.writeXML();
		
		String xml = vw.getRootName()+".xml";
		String svg = vw.getRootName()+".svg";
		File xmlf = new File(xml);
		File svgf = new File(svg);
		if(port == 0)
			serv = new Server();
		else
			serv = new Server(port);
		try {
			serv.initiate();
		} catch (IOException e) {
			System.out.println("Server could not intiate on port: "+serv.getPort());
			e.printStackTrace();
		}
		try {
			serv.sendFileToClient(xmlf);
		} catch (IOException e) {
			System.out.println("Server could not send file to webpage on port: "+serv.getPort());
			e.printStackTrace();
		}
		try {
			serv.sendFileToClient(svgf);
		} catch (IOException e) {
			System.out.println("Server could not send file to webpage on port: "+serv.getPort());
			e.printStackTrace();
		}
		//TODO
		//Init webpage here or in TripCo?
		
		//String jspage = /js/full/file/path
		//String url = "localhost:"+Integer.toString(port)+jspage;
		/* Open js webpage with proper port set
		 * Send XML
		 * Presenter.sendFileToClient(out.get(0))
		 * Send SVG
		 * Presenter.sendFileToClient(out.get(1))
		 * Loop for rest/interactions
		 */
		//Rest loop goes here
		//Loop for serv to listen
		//Parse the rest and tell serv what to do, may need model/view to do work too
	}
	
	public int getServPort(){
		return serv.getPort();
	}
	
}
