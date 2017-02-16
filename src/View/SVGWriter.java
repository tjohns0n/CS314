/*
SVGWriter.java
Draws a map of a trip
*/

package View;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SVGWriter {

    // Width and height of the SVG.
    double width;
    double height;
    // The SVG content to be written before whatever elements the user adds
    String header;
    // Usually just </svg>
    String footer;
    // What's been added to the SVG:
    ArrayList<String> content;
    // Name of file to write
    String filename;

    /*
     * SVGWriter constructor 1:
     * Read in an existing SVG so that additional elements can be added on
     */
    public SVGWriter(String filename) {
    	content = new ArrayList<String>();
    	header = "";
    	try {
			BufferedReader readSVG = new BufferedReader(new FileReader(filename));
			String line;
			
			line = readSVG.readLine().trim();
			boolean svg = false;
			while ((line != null) && !(line.equals("</svg>"))) {
				if (line.contains("<svg")) {
					svg = true;
				}
				if (svg) {
					String[] strings = line.split("\"");
					for (int i = 0; i < strings.length; i++) {
						if (strings[i].contains("width")) {
							width = Double.parseDouble(strings[i + 1]);
						}
						if (strings[i].contains("height")) {
							height = Double.parseDouble(strings[i + 1]);
						}
						if (strings[i].contains(">")) {
							svg = false;
						}
					}
				}
				header += line + "\n";
				line = readSVG.readLine().trim();
			}
			if (line == null) {
				readSVG.close();
				throw new IOException();
				
			} else {
				footer = "</svg>";
			}
			readSVG.close();

		} catch (IOException e) {
			System.out.println("SVG not formatted properly");
		}
    	this.filename = filename;
    }
    
    public void addLine(int x1, int y1, int x2, int y2) {
    	ArrayList<String> attributes = new ArrayList<String>();
    	attributes.add("x1");
    	attributes.add(Integer.toString(x1));
    	attributes.add("y1");
    	attributes.add(Integer.toString(y1));
    	attributes.add("x2");
    	attributes.add(Integer.toString(x2));
    	attributes.add("y2");
    	attributes.add(Integer.toString(y2));
    	attributes.add("stroke");
    	attributes.add("#999999");
    	attributes.add("stroke-width");
    	attributes.add("3");
    	XMLElement line = new XMLElement("line", attributes);
    	content.add(line.getStart());
    }
    
    public void writeSVG() {
    	try {
    		BufferedWriter write = new BufferedWriter(new FileWriter(filename));
    		write.write(header + "\n");
    		for (String s : content) {
    			System.out.println("eh");
    			write.write(s);
    			write.write("\n");
    		}
    		write.write(footer);
    		write.close();
    		
    	} catch (IOException e) {
    		
    	}
    }
}