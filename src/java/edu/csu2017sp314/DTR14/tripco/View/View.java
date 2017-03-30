/*
View.java
Creates and displays viewable files and pages from data provided by the Presenter 
*/

package edu.csu2017sp314.DTR14.tripco.View;

public class View {

    // Name of the CSV input file, sans the .csv extension 
    private String filename;
    private String fileroot;
    // Optionally display labels on the SVG
    private boolean mileage;
    private boolean names;
    private boolean ids;
    // ItineraryWriter and SVGWriter:
    private ItineraryWriter itinWrite;
    private SVGWriter svgWrite;

    //Empty constructor, for javafx Application compliance
    public View(){

    }

    /*
    View Command Line constructor
    args:
    rootName - The name of the CSV file, with or without the .csv extension
    SVGFile - The name of the input SVGFile (should be "coloradoMap.svg" for spring 1)
    mileage - Whether or not the SVG should have labels showing the mileages of each leg of a trip
    totalMileage - the total mileage of the trip
    names - Whether or not the SVG should have labels showing the names of each location of a trip
    ids - Whether or not the SVG should have labels showing the id assigned to each location of a trip
    */
    
    public View(String path, String SVGFile, int totalMileage, boolean mileage, boolean names, boolean ids){
    	itinWrite = new ItineraryWriter();
    	if(SVGFile == null || SVGFile.equals("null") || SVGFile.equals(""))
    		svgWrite = new SVGWriter(1067, 783);
    	else
    		svgWrite = new SVGWriter(SVGFile);
        // For now, automatically pad the SVG with whitespace
        //svgWrite.padSVG();
        // Store the root of the CSV file name
        filename = path.substring(path.lastIndexOf('-')+1, path.lastIndexOf('.'));
        System.out.println("filename = " + filename);
        fileroot = path.substring(0, path.lastIndexOf('.'));
        System.out.println("fileroot = " + fileroot);
        // For now, automatically title and foot the SVG 
        svgWrite.addTitle("Colorado - " + filename, "maptitle");
        svgWrite.addFooter(totalMileage + " miles", "mapfooter");

        this.mileage = mileage;
        this.names = names;
        this.ids = ids;
    }
    
    public String getRootName() {
    	return fileroot;
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
    public String addLeg(double startLocationLat, double startLocationLong, String startLocationName, String startLocationID,
                        double endLocationLat, double endLocationLong, String endLocationName, String endLocationID, int mileage) {
    	int legCount = 0;
    	String testString = "";
    	svgWrite.newGroup("leg" + ++legCount);
        svgWrite.addLine(startLocationLong, startLocationLat, endLocationLong, endLocationLat, "blue", 3, true);
        itinWrite.addLeg(startLocationName, endLocationName, mileage);

        if (this.mileage) {
        	testString += "m";
            svgWrite.addLineLabel(Integer.toString(mileage), "leg" + Integer.toString(legCount), 
                                    startLocationLong, startLocationLat, endLocationLong, endLocationLat);
        }

        if (this.names) {
        	testString += "n";
            svgWrite.addLabel(startLocationName, "loc" + legCount, startLocationLong, startLocationLat);
        }

        if (this.ids) {
        	testString += "i";
            svgWrite.addLabel(startLocationID, "loc" + legCount, startLocationLong, startLocationLat);
        }
        svgWrite.endGroup();
        return testString;
    }

    /*
     * Write the SVG and XML files once all the legs have been added
     */
    public void writeFiles() {
        System.out.println("Writing stuff = " + fileroot + ".svg");
        System.out.println("Writing stuff = " + fileroot + ".xml");
        svgWrite.writeSVG(fileroot + ".svg");
        itinWrite.writeXML(fileroot + ".xml");
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
	
}