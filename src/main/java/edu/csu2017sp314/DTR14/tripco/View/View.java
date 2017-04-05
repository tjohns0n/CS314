/*
View.java
Creates and displays viewable files and pages from data provided by the Presenter 
*/

package edu.csu2017sp314.DTR14.tripco.View;

import javafx.application.Application;
import javafx.stage.Stage;

public class View extends Application {

    // Name of the CSV input file, sans the .csv extension 
	private String title;
    // Optionally display labels on the SVG
    private boolean distances;
    private boolean names;
    private int legCount;
    private boolean ids;
    private boolean miles;
    
    private int totalDistance;
    
    // ItineraryWriter and SVGWriter:
    private ItineraryWriter itinWrite;
    private ColoradoSVGWriter svgWrite;

    //Empty constructor, for javafx Application compliance
    public View(){

    }

    /*
    View Command Line constructor
    args:
    title - The title chosen 
    SVGFile - The name of the input SVGFile (should be "coloradoMap.svg" for spring 1)
    totalDistance - the total distance of the trip
	labels - whether to display distances, names, and ids on the map or not
    */
    
    public View(String title, String SVGFile, int totalDistance, boolean[] labels, boolean miles){
    	legCount = 0;
    	itinWrite = new ItineraryWriter();
    	if(SVGFile == null || SVGFile.equals("null") || SVGFile.equals(""))
    		svgWrite = new ColoradoSVGWriter();
    	else
    		svgWrite = new ColoradoSVGWriter(SVGFile);
       
        this.title = title;
        this.totalDistance = totalDistance;
        
        setTitle();
        setFooter();
        
        this.ids = labels[0];
        this.distances = labels[1];
        this.names = labels[2];
    }
    
    private void setTitle() {
    	String substring = title.substring(title.length() - 4).toLowerCase();
    	if (substring.equals(".csv")) {
    		title = title.substring(0, title.length() - 4);
    	}
    	
    	svgWrite.addTitle(title, "maptitle");
    }
    
    private void setFooter() {
    	if (miles) {
        	svgWrite.addFooter(totalDistance + " miles", "mapfooter");
        } else {
        	svgWrite.addFooter(totalDistance + " kilometers", "mapfooter");
        }
    }
    
    //Util methods
    public void run(){
    	Application.launch(View.class, new String[0]);
    }
    
    @Override
	public void start(Stage primaryStage) throws Exception {
    	new FileChooserGUI().start(primaryStage);
	}

    public String getRootName() {
    	return title;
    }
    
    //Called by Input gui, sets options from gui, starts svgwriter, wakes up Presenter thread
    
    /*
     * Add a line to the SVG and a leg to the XML. Automatically converts from geographic coordinates.
     * If any of the command line flags are true, labels will automatically be generated as well
     * args:
     * startLocationLat, startLocationLong - lat and long of the first location of the leg 
     * startLocationName, startLocationID, name and Id of the first location of the leg 
     * endLocationLat, endLocationLong - lat and long of the second location of the leg
     * endLocationName, endLocationID, name and Id of the second location of the leg
     * mileage - the mileage between the two locations
     */
    public String addLeg(double[] coordinates, String startLocationName, String startLocationID,
    						String endLocationName, String endLocationID, int mileage) {
    	String testString = "";
    	svgWrite.newGroup("leg" + Integer.toString(++legCount));
        svgWrite.addLine(coordinates, "blue", 3, true);
        itinWrite.addLeg(startLocationName, endLocationName, mileage);

        if (this.distances) {
        	testString += "m";
            svgWrite.addLineLabel(Integer.toString(mileage), "mileage" 
            		+ Integer.toString(legCount), coordinates);
        }

        if (this.names) {
        	testString += "n";
            svgWrite.addLabel(startLocationName, "loc" + legCount, coordinates);
        }

        if (this.ids) {
        	testString += "i";
            svgWrite.addLabel(startLocationID, "loc" + legCount, coordinates);
        }
        svgWrite.endGroup();
        return testString;
    }

    /*
     * Write the SVG and XML files once all the legs have been added
     */
    public void writeFiles() {
        svgWrite.writeSVG(title + ".svg");
        itinWrite.writeXML(title + ".xml");
        new GenerateJavascript(getRootName());
    }

    /*
    === Likely to be removed ===
    display: output text to the console
    args:
    text - The text to be printed to the console
    */
    
    public String display(String text) {
        System.out.println(text);
        return text;
    }
    
    public static void main(String[] args) throws Exception {
    	View vw = new View();
    	vw.run();
//    	View v = new View("hello.csv", "coloradoMap.svg", 300, false, false, true);
//    	v.addLeg(40, -108, "Not Denver", "id1", 39, -107, "Not CO Springs", "id2", 50);
//    	v.addLeg(39, -107, "Not CO Springs", "id2", 40.5, -108, "Not Fort Collins", "id3", 60);
//    	v.addLeg(40.5, -108, "Not Fort Collins", "id3", 40, -108, "Not Denver", "id1", 100);
//    	v.writeFiles();
    }


	
}