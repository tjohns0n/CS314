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

    /*
    View constructor
    args:
    rootName - The name of the CSV file, with or without the .csv extension
    mileage - Whether or not the SVG should have labels showing the mileages of each leg of a trip
    names - Whether or not the SVG should have labels showing the names of each location of a trip
    ids - Whether or not the SVG should have labels showing the id assigned to each location of a trip
    */
    public View(String rootName, boolean mileage, boolean names, boolean ids) {
        // Store the root of the CSV file name
        if (rootName.substring(rootName.length()-4, rootName.length()).equals(".csv")) {
            this.rootName = rootName.substring(0, rootName.length() - 4);
        } else {
            this.rootName = rootName;
        }
        this.mileage = mileage;
        this.names = names;
        this.ids = ids;
    }

    public String getRootName() {
    	return rootName;
    }
    
    /*
    writeSVG: Create an SVGWriter to output an SVG displaying a Trip.
    args:
    trip (unimplemented) - the trip to be drawn to the SVG
    */
    public void writeSVG(/*Trip trip*/) {
        
    }

    /*
    writeXML: Create an XMLWriter to output an XML displaying a Trip.
    args:
    trip (unimplemented) - the trip to be detailed by the XML 
    */
    public void writeXML(/*Trip trip*/) {
        
    }

    /*
    === Likely to be removed ===
    display: output text to the console
    args:
    text - The text to be printed to the console
    */
    public void display(String text) {
        System.out.println(text);
    }
    
    public static void main(String args) {
    	View v = new View("hello", false, false, false);
    	System.out.println(v.getRootName());
    }
}