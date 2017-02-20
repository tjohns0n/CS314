/*
View.java
Creates and displays viewable files and pages from data provided by the Presenter 
*/

package View;

public class View {

    // Name of the CSV input file, sans the .csv extension 
    String rootName;
    // Optionally display labels on the SVG
    boolean mileage, names, ids;
    // ItineraryWriter and SVGWriter:
    ItineraryWriter itinWrite;
    SVGWriter svgWrite;
    // Number of legs:
    int legCount;

    /*
    View constructor
    args:
    rootName - The name of the CSV file, with or without the .csv extension
    SVGFile - The name of the input SVGFile (should be "coloradoMap.svg" for spring 1)
    mileage - Whether or not the SVG should have labels showing the mileages of each leg of a trip
    totalMileage - the total mileage of the trip
    names - Whether or not the SVG should have labels showing the names of each location of a trip
    ids - Whether or not the SVG should have labels showing the id assigned to each location of a trip
    */
    public View(String rootName, String SVGFile, int totalMileage, boolean mileage, boolean names, boolean ids) {
        itinWrite = new ItineraryWriter();
        svgWrite = new SVGWriter(SVGFile);
        legCount = 0;

        // For now, automatically pad the SVG with whitespace
        svgWrite.padSVG();
        // Store the root of the CSV file name
        if (rootName.substring(rootName.length()-4, rootName.length()).equals(".csv")) {
            this.rootName = rootName.substring(0, rootName.length() - 4);
        } else {
            this.rootName = rootName;
        }

        // For now, automatically title and foot the SVG 
        svgWrite.addTitle("Colorado - " + this.rootName, "maptitle");
        svgWrite.addFooter(totalMileage + " miles", "mapfooter");

        this.mileage = mileage;
        this.names = names;
        this.ids = ids;
    }

    public String getRootName() {
    	return rootName;
    }
    
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
        svgWrite.writeSVG(rootName + ".svg");
        itinWrite.writeXML(rootName + ".xml");
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
    
    public static void main(String[] args) {
    	View v = new View("hello.csv", "coloradoMap.svg", 300, false, false, true);
    	v.addLeg(40, -108, "Not Denver", "id1", 39, -107, "Not CO Springs", "id2", 50);
    	v.addLeg(39, -107, "Not CO Springs", "id2", 40.5, -108, "Not Fort Collins", "id3", 60);
    	v.addLeg(40.5, -108, "Not Fort Collins", "id3", 40, -108, "Not Denver", "id1", 100);
    	v.writeFiles();
    }
}