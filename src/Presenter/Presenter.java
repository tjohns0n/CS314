//DTR-14
//Presenter.java
//Takes ArrayList of files and boolean array for options
package Presenter;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import Model.Model;
import View.GenerateJavascript;
import View.View;



public class Presenter{
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
				mileage=options[i];
			if(i==2)
				name = options[i];
		}
	}
	public Presenter(ArrayList<File> files, boolean[] options, int Port){
		inFiles=files;
		port = Port;
		for(int i=0; i<options.length;i++){
			if(i==0)
				ID=options[i];
			if(i==1)
				mileage=options[i];
			if(i==2)
				name = options[i];
		}
	}
	public Presenter(ArrayList<File> files){
		inFiles=files;
		ID=false;
		name=false;
		mileage=false;
		port = 0;
	}
	public Presenter(ArrayList<File> files, int Port){
		inFiles=files;
		ID=false;
		name=false;
		mileage=false;
		port = Port;
	}
	public void run() throws URISyntaxException{
		//Instantiate and call Model to process input
		Model mod = new Model(inFiles.get(0).getAbsolutePath());
		mod.planTrip();
		//Input file reading and processing loop
		/* TODO
		 * Loop for multiple file handling, for later
		for(int i=0;i<files.size();i++){
			Model mode = new Model(inFiles.get(i).getName());
			mode.initiate();
			
		}
		*/
//		System.out.println("ID: "+ID);
//		System.out.println("mileage: "+mileage);
//		System.out.println("Name: "+name);
		String[][] route = mod.reteriveTrip();
		//TODO
		//Mem mgmt?
		//mod.close();
		String[] total = route[route.length-1][0].split(",");
		String miles = total[0];
		String pwd = System.getProperty("user.dir");
		int totalMileage = Integer.parseInt(miles);
		View vw = new View(inFiles.get(0).getName(),"coloradoMap.svg", totalMileage, mileage, name, ID);
		//Find if ID is an actual value or should just be value
		int idIndex=-1;
		boolean idPresent = false;
		String[] labels = route[0][2].split(",");
		//TODO
		//Add into larger loop to handle routes with multiple formats i.e. multiple extras/template formats
		for(int i=0; i<labels.length;i++){
			if(labels[i].equalsIgnoreCase("ID")){
				idPresent=true;
				idIndex=i;
				break;
			}
		}
		//Feed View info to draw lines of trip
		int idCount = 1;
		//TODO
		//Make more robust loop for added options
		for(int i=0; i<route.length-1;i++){
			//Essentials string splitarg0
			//[0]=Accumulated Dist, [1]=name, [2]=lat, [3]=long
			String[] essentials1 = route[i][0].split(",");
			String[] essentials2 = route[i+1][0].split(",");
			//Leg Distance calculated by difference between accumulated miles
			//First locations should have "0" as accumulated distance
			int mile = Integer.parseInt(essentials2[0]) -Integer.parseInt(essentials1[0]);
			if(idPresent){
				//Split to grab id
				String[] extras1 = route[i][1].split(",");
				String[] extras2 = route[i+1][1].split(",");				
				vw.addLeg(Double.parseDouble(essentials1[2]), Double.parseDouble(essentials1[3]), essentials1[1], extras1[idIndex], 
						Double.parseDouble(essentials2[2]), Double.parseDouble(essentials2[3]), essentials2[1], extras2[idIndex], mile);
			}
			else{
				//Vanilla draw
				//ID is order they are entered
				vw.addLeg(Double.parseDouble(essentials1[2]), Double.parseDouble(essentials1[3]), essentials1[1], Integer.toString(idCount),
						Double.parseDouble(essentials2[2]), Double.parseDouble(essentials2[3]),essentials2[1], Integer.toString(idCount+1), mile);
				idCount +=2;
			}
		}
		//Write the files
		vw.writeFiles();
		//Bandaid fix for js since server still not working
		GenerateJavascript gjs = new GenerateJavascript(vw.getRootName());
		//TODO
		// mem mgmt?
		// gjs.close()?
		
		//Get URI for webpage launch
		URI webpage = null;
		webpage = new URI("file://"+pwd+"/src/View/View.html");
		//Launch webpage
		try {
			java.awt.Desktop.getDesktop().browse(webpage);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("We tried to get the webpage to launch without the server");
			System.out.println("A bandaid yes, but we tried, and it looks like it didn't work");
			System.out.println("But the XML and svg files should be in the directory with the proper names/data");
		}
		if(port == 0)
			serv = new Server();
		else
			serv = new Server(port);
		//TODO
		//Figure out which websocket lib to use
		//So we can actually use the server
//		String xml = vw.getRootName()+".xml";
//		String svg = vw.getRootName()+".svg";
//		File xmlf = new File(xml);
//		File svgf = new File(svg);
//		try {
//			serv.initiate();
//		} catch (IOException e) {
//			System.out.println("Server could not intiate on port: "+serv.getPort());
//			e.printStackTrace();
//		}
//		try {
//			serv.sendFileToClient(xmlf);
//		} catch (IOException e) {
//			System.out.println("Server could not send file to webpage on port: "+serv.getPort());
//			e.printStackTrace();
//		}
//		try {
//			serv.sendFileToClient(svgf);
//		} catch (IOException e) {
//			System.out.println("Server could not send file to webpage on port: "+serv.getPort());
//			e.printStackTrace();
//		}
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
}
