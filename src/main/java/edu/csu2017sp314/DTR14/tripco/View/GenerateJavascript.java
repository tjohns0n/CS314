package edu.csu2017sp314.DTR14.tripco.View;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GenerateJavascript {
    ArrayList<String> lines;
    String filename;

    /*
     * GenerateJavascript constructor
     * args:
     * rootname - the root name of the XML and SVG files
     *            should not have any file extension
     */
    public GenerateJavascript(String rootname) {
        filename = rootname;
        lines = new ArrayList<String>();

        lines.add("function nameSVG() {");
        lines.add("\treturn '" + "../../src/main/resources/" + filename + ".svg';");
        lines.add("}");

        lines.add("function nameXML() {");
        lines.add("\treturn '" + "../../src/main/resources/" + filename + ".xml';");
        lines.add("}");

        try {
        	String dir = System.getProperty("user.dir");
        	BufferedWriter write = new BufferedWriter(new FileWriter("View.js"));
        	for (String s : lines) {
        		write.write(s + "\n");
        	}
        	write.close();
        } catch (IOException e) {
        	System.out.println("Could not open file");
        }
    }
    
    public static void main(String[] args) {
    	GenerateJavascript j = new GenerateJavascript("hello");
    }
}