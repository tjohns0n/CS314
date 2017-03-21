//DTR-14
//Presenter.java
//Takes ArrayList of files and boolean array for options
package edu.csu2017sp314.DTR14.tripco.Presenter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import edu.csu2017sp314.DTR14.tripco.Model.Model;

import edu.csu2017sp314.DTR14.tripco.View.GenerateJavascript;
import edu.csu2017sp314.DTR14.tripco.View.View;



public class Presenter{

	//List of input .csv files
	private static ArrayList<File> files;
	//Optional argument booleans

	// boolean[] opt = {_id, _mileage, _name, _2opt, _3opt, _gui};
	private static final int optionSize = 6;
	private static boolean[] options;

	//Server, not initially instantiated
	private static Model model;
	private static View view;
    private static String _svg;
    private static String _xml;
    private static String[] subSet;
        
	public Presenter( ArrayList<File> files, String _xml, String _svg, boolean[] options){
		this.files = files;
		this.options = options;
		this._svg = _svg;
		this._xml = _xml;
	}

	public Presenter(ArrayList<File> files){
		this.files = files;
		Arrays.fill(options, false);
	}

	public void run() throws URISyntaxException, InterruptedException, FileNotFoundException{
		if (options[5] == true){
			// if GUI
			view = new View(this);
			Thread vThread = new Thread(view);
			vThread.run();
			synchronized(this){
				this.wait();
			}
			this.files.add(view.getCSV());
			if(this._svg.equals("")) this._svg = view.getBackSVGName();
			if(this._xml.equals("")) this._xml = view.getSelectFilename();
			else view.readSelectionXML(new File(_xml));
			this.options = view.getOptions();
			this.options[4] = view.getOpts()[0]; this.options[5] = view.getOpts()[1];
			this.subSet = view.getSubset();
		} else if(!_xml.isEmpty()){
			view = new View(this);
			view.setSelection(view.readSelectionXML(new File(_xml)));
			this.subSet = view.getSubset();
			this.options = view.getOptions();
			this.options[4] = view.getOpts()[0]; this.options[5] = view.getOpts()[1];
			this.subSet = view.getSubset();
		}
		
		model = new Model(files.get(0).getAbsolutePath());
		model.planTrip(options[4], options[5], subSet);
		String[][] route = model.reteriveTrip();
		String[] total = route[route.length-1][0].split(",");
		int totalMileage = Integer.parseInt(total[0]);
		view.setTotal(totalMileage);
		//view = new View(this, files.get(0).getName(), _svg, totalMileage, options[1], options[2], options[0]);
		//Find if ID is an actual value or should just be value
		int idIndex = -1;
		boolean idPresent = false;
		String[] labels = route[0][2].split(",");
		//TODO
		//Add into larger loop to handle routes with multiple formats i.e. multiple extras/template formats
		for(int i = 0; i < labels.length;i ++){
			if(labels[i].equalsIgnoreCase("ID")){
				idPresent = true;
				idIndex = i;
				break;
			}
		}
		//Feed View info to draw lines of trip
		int idCount = 1;
		//TODO
		//Make more robust loop for added options
		for(int i = 0; i < route.length - 1; i++){
			//Essentials string splitarg0
			//[0]=Accumulated Dist, [1]=name, [2]=lat, [3]=long
			String[] essentials1 = route[i][0].split(",");
			String[] essentials2 = route[i+1][0].split(",");
			//Leg Distance calculated by difference between accumulated miles
			//First locations should have "0" as accumulated distance
			int mile = Integer.parseInt(essentials2[0]) - Integer.parseInt(essentials1[0]);
			if(idPresent){
				//Split to grab id
				String[] extras1 = route[i][1].split(",");
				String[] extras2 = route[i+1][1].split(",");				
				view.addLeg(Double.parseDouble(essentials1[2]), Double.parseDouble(essentials1[3]), essentials1[1], extras1[idIndex], 
						Double.parseDouble(essentials2[2]), Double.parseDouble(essentials2[3]), essentials2[1], extras2[idIndex], mile);
			}
			else{
				//Vanilla draw
				//ID is order they are entered
				view.addLeg(Double.parseDouble(essentials1[2]), Double.parseDouble(essentials1[3]), essentials1[1], Integer.toString(idCount),
						Double.parseDouble(essentials2[2]), Double.parseDouble(essentials2[3]),essentials2[1], Integer.toString(idCount+1), mile);
				idCount +=2;
			}
		}
		//Write the files
		view.writeFiles();
		//Bandaid fix for js since server still not working
		GenerateJavascript gjs = new GenerateJavascript(view.getRootName());
		
		//Get URI for webpage launch
		URI webpage = null;
                webpage = this.getClass().getClassLoader().getResource("View.html").toURI();
		
		//Launch webpage
		try {
			java.awt.Desktop.getDesktop().browse(webpage);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("We tried to get the webpage to launch without the server");
			System.out.println("A bandaid yes, but we tried, and it looks like it didn't work");
			System.out.println("But the XML and svg files should be in the directory with the proper names/data");
		}

	}
}