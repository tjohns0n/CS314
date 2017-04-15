//DTR-14
//Presenter.java
//Takes ArrayList of files and boolean array for options
package edu.csu2017sp314.DTR14.tripco.Presenter;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.application.Application;
import edu.csu2017sp314.DTR14.tripco.Model.Model;
import edu.csu2017sp314.DTR14.tripco.View.ItineraryLeg;
import edu.csu2017sp314.DTR14.tripco.View.View;

public class Presenter {

    //List of input .csv files
    private ArrayList<File> files;
    //Optional argument booleans

    // boolean[] opt = {_id, _mileage, _name, _2opt, _3opt, _gui, _unit};
    private static boolean[] options;
    private static Model model = new Model();
    private static View view = new View();
    private static String _svg;
    private static String _xml;
    private static String name;
    private String[] subSet;
    private String unit;

    public Presenter(ArrayList<File> files, String _xml, String _svg, boolean[] options) {
        this.files = files;
        Presenter.options = options;
        Presenter._svg = _svg;
        Presenter._xml = _xml;
        if(Presenter.options[6])
        	this.unit="Km";
        else
        	this.unit = "Miles";
    }

    public Presenter(ArrayList<File> files) {
        this.files = files;
        options = new boolean[7];
        Arrays.fill(options, false);
    }
    
    public Presenter() {
		// TODO Auto-generated constructor stub
	}

    public Message sendMessage(Message msg){
    	return model.sendMessage(msg);
    }
    
    public void run() throws IOException {
    	this.run(true);
    }
    
    public void run(boolean flag) throws IOException {
    	model = new Model(this, options[6]);
    	xmlHandler();
        viewHandler(modelHandler());
        
        if(flag) webPageViewer();
    }

    private void xmlHandler() throws IOException{
        StringBuilder csvFileName = new StringBuilder();
        if(_xml == null || _xml.equals("null") || _xml.equals("")) subSet = new String[0];
        else subSet = new XMLReader().readSelectFile(_xml, csvFileName);
        if (options[5] == true) {
            // if GUI
            Application.launch(View.class, new String[0]);
            files.add(new File(name+".csv"));
            subSet = new String[0];
        }
        else{
        	model.sendToModel(new Message(subSet, "M-DB-TRIP0"));
        }
        if(csvFileName.length() != 0) files.add(new File(csvFileName.toString()));
    }

    private String[][] modelHandler(){
        model.planTrip(options[3], options[4], subSet);
        return model.reteriveTrip();
    }
    
    
    private void viewHandler(String[][] s){
    	String[][] route = s;
        String[] total = route[route.length - 1][0].split(",");
        int totalMileage = Integer.parseInt(total[0]);
        boolean[] label = {options[0], options[1]};
        view = new View(files.get(0).getName(), _svg, totalMileage, label, options[6], route.length);
        viewItin(route);
        viewWriter(route);
    }
    
    private void viewWriter(String[][] route){
    	
    	for (int i = 0; i < route.length - 1; i++) {
            //Essentials string splitarg0
            //[0]=Accumulated Dist, [1]=name, [2]=lat, [3]=long [4]=id;
            String[] essentials1 = route[i][0].split(",");
            String[] essentials2 = route[i + 1][0].split(",");
            //Leg Distance calculated by difference between accumulated miles
            //First locations should have "0" as accumulated distance
            //System.out.println(Arrays.toString(essentials1));
            int mile = Integer.parseInt(essentials2[0]) - Integer.parseInt(essentials1[0]);

            double[] coordinates = {Double.parseDouble(essentials1[3]), Double.parseDouble(essentials1[2]), 
            		Double.parseDouble(essentials2[3]), Double.parseDouble(essentials2[2])};
            
            view.addLeg(coordinates, essentials1[1], essentials1[4],
            		essentials2[1], essentials2[4],  mile);
            
        }
        //Write the files
        view.writeFiles();
    }
    
    
    //Write itinerary with new detailed legs
    private void viewItin(String[][] route){
    	String[] ids = new String[route.length];
    	for(int i=0;i<route.length;i++){
    		String[] info = route[i][0].split(",");
    		ids[i] = info[4];
    	}
    	Message m = model.sendMessage(new Message(ids, "M-DB-ITIN"));
    	for(int j=0;j<m.content.length-1;j++){
    		 String[] essentials1 = route[j][0].split(",");
             String[] essentials2 = route[j + 1][0].split(",");
             int mile = Integer.parseInt(essentials2[0]) - Integer.parseInt(essentials1[0]);
             ItineraryLeg itinLeg = new ItineraryLeg(m.content[j].split(","), m.content[j+1].split(","),
            		 mile, j+1, unit);
             view.addItinLeg(itinLeg);
    	}
    }
    
    private void webPageViewer(){
    	URI webpage = null;
        String dir = System.getProperty("user.dir") + "/main/resources/View.html";

        System.out.println("Ready to show the webpage = " + dir);
        File webFile = new File(dir);
        webpage = webFile.toURI();
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
    
    private void printlines(){
      //System.out.println("csv File = " + files.get(0).getName());
      System.out.println("svg File = " + _svg);
      System.out.println("xml File = " + _xml);
      System.out.println("Filename = " + name);
      System.out.println("_i = " + options[0]);
      System.out.println("_m = " + options[1]);
      System.out.println("_n = " + options[2]);
      System.out.println("_2 = " + options[3]);
      System.out.println("_3 = " + options[4]);
      for(int i = 0; i < subSet.length; i++)
          System.out.println("subSet = " + subSet[i]);
    }
  
	public static void setOptions(boolean[] options) {
		Presenter.options = options;
	}

	public static void set_svg(String _svg) {
		if(Presenter._svg == null)
			Presenter._svg = _svg;
	}

	public static void set_xml(String _xml) {
		if(Presenter._xml == null)
			Presenter._xml = _xml;
	}
	
	public static void setName(String name) {
		if(Presenter.name == null || 
				Presenter.name.equals("temp"));
			Presenter.name = name;
	}

}