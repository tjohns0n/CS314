/*
SVGWriter.java
Draws a map of a trip
*/

package edu.csu2017sp314.DTR14.tripco.View;

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
	// offsets once SVG is padded
	int xOffset = 37;
	int yOffset = 37;
    // The SVG content to be written before whatever elements the user adds
    ArrayList<String> header;
	ArrayList<String> originalContent;
    // Usually just </svg>
    ArrayList<String> footer;
    // What's been added to the SVG:
    ArrayList<String> content;
    // Name of file to write
    String filename;

    /*
     * SVGWriter constructor 1:
     * Read in an existing SVG so that additional elements can be added on
     */
    public SVGWriter(String filename) {
		// Init arraylists
    	header = new ArrayList<String>();
    	content = new ArrayList<String>();
    	footer = new ArrayList<String>();
		originalContent = new ArrayList<String>();
    	this.filename = filename;
    	readFile(filename);
    }
    
    private void readFile(String filename){
    	// Init string	
    	try {
			BufferedReader readSVG = new BufferedReader(new FileReader(filename));
			
			// Read in a line from the SVG and trim the whitespace (may remove trimming later)
			String line = readSVG.readLine().trim();
			// svg: true if the <svg> element is currently being read
			boolean svg = false;
			// Read from the SVG until the file ends or the SVG element ends
			while ((line != null) && !(line.equals("</svg>"))) {
				// Add the XML header to the start of the write queue
				if (line.contains("<?xml") && !svg) {
					header.add(line + "\n");
					line = readSVG.readLine().trim();
					continue;
				}
				// mark svg to true now that svg tag has started
				if (line.contains("<svg"))
					svg = true;
				// if in svg tag
				if (svg) {
					// split on quotes to isolate values of attributes
					String[] strings = line.split("\"");
					// loop through the svg tag
					for (int i = 0; i < strings.length; i++) {
						// extract the width from the svg
						if (strings[i].contains("width"))
							width = Double.parseDouble(strings[i + 1]);
						// extract the height from the svg
						if (strings[i].contains("height")) 
							height = Double.parseDouble(strings[i + 1]);
						// if the svg tag is over, stop searching for height and width
						if (strings[i].contains(">")) 
							svg = false;
					}
				}
				// add each element of the SVG to the start of the write queue
				originalContent.add(line);
				// read the next line
				line = readSVG.readLine().trim();
			}

			// if end of document reached without seeing "</svg>, svg is invalid"
			// else add </svg> to the end of the write queue
			if (!line.contains("svg") ) throw new IOException();
			else footer.add("</svg>");
				
			// close the buffered writer
			readSVG.close();

		} catch (IOException e) {
			System.out.println("SVG not formatted properly");
		}
    }
    
	public SVGWriter(int width, int height) {
		header = new ArrayList<String>();
		footer = new ArrayList<String>();
		content = new ArrayList<String>();

		this.width = (double)width;
		this.height = (double)height;

		XMLElement xml = new XMLElement("xml", "version=\"1.0\"");
		
		XMLElement svg = new XMLElement("svg", "width=\"" + width + 
										"\" height=\"" + height + 
										"\" xmlns:svg=\"http://www.w3.org/2000/svg\" " + 
										"xmlns=\"http://www.w3.org/2000/svg\"");
		header.add(xml.getStart());
		header.add(svg.getStart());
		footer.add(svg.getEnd());
	}
    
	/*
	 * mapPoints - take points from one coordinate system and put them in that of the SVG
	 * Currently hardcoded to work with the colorado map
	 * args:
	 * x - the x coordinate of the point to map
	 * y - the y coordinate of the point to map
	 *
	 * TODO: 
	 * - change so that any coordinate system can be mapped
	 */
	public int[] mapPoints(double x, double y) {
		int[] mapping = new int[2];
		x += 109;//Left corner
		y -= 41;
		x *= ((992.6073) / 7);//Horizontal scalling
		y *= ((709.0824) / -4);//Vertical scalling
		x += 37;
		y += 37;
		mapping[0] = (int)x;
		mapping[1] = (int)y;
		return mapping;
	}
	
	/*
	 * addLine - add a line to the SVG write queue
	 * args:
	 * x1, y1, x2, y2 - the x and y coordinates of each point 
	 * color - the color of the line (accepts SVG color names or hex values with #)
	 * width - the width of the line 
	 * map - whether or not the points need to be mapped to the SVG coordinates
	 */
	
    public void addLine(int x1, int y1, int x2, int y2, String color, int width, boolean map) {
		addLine((double)x1, (double)y1, (double)x2, (double)y2, color, width, map);
    }

	
	public void addLine(double x1, double y1, double x2, double y2, String color, int width, boolean map) {
		if (map) {
			int[] point1 = mapPoints((double)x1, (double)y1);
			x1 = point1[0];
			y1 = point1[1];

			int[] point2 = mapPoints((double)x2, (double)y2);
			x2 = point2[0];
			y2 = point2[1];
		}

		ArrayList<String> attributes = new ArrayList<String>();
    	attributes.add("x1");
    	attributes.add(Integer.toString((int)x1));
    	attributes.add("y1");
    	attributes.add(Integer.toString((int)y1));
    	attributes.add("x2");
    	attributes.add(Integer.toString((int)x2));
    	attributes.add("y2");
    	attributes.add(Integer.toString((int)y2));
    	attributes.add("stroke");
    	attributes.add(color);
    	attributes.add("stroke-width");
    	attributes.add(Integer.toString(width));
    	XMLElement line = new XMLElement("line", attributes);
    	content.add(line.getStart());
	}
    
	/*
	 * padSVG - add whitespace padding to the edges of an SVG. Uses xOffset and yOffset to pad.
	 * Centers the original SVG in a new, larger SVG file. 
	 * TODO:
	 * Allow for custom padding rather than 50 pixels on each side
	*/
	public void padSVG() {
		// Create a new SVG with height = original height + total horizontal paddding, width = orig width + total vertical padding
		XMLElement svg = new XMLElement("svg", "width=\"" + (width) + 
			"\" height=\"" + (height) + "\" " +
			"xmlns:svg=\"http://www.w3.org/2000/svg\" xmlns=\"http://www.w3.org/2000/svg\"");
		// Place the new SVG tag after the xml header but before the original SVG
		header.add(1, svg.getStart());
		
		// Put the original SVG in a new group and transform that group to be moved by the padding offset
		XMLElement g = new XMLElement("g", "");
		header.add(2, g.getStart());
		// Close the group
		footer.add(0, g.getEnd());
		// Close the new SVG
		footer.add(0, svg.getEnd());
	}

	/*
	 * newGroup - Add a <g> tag to the SVG for grouping
	 * args:
	 * groupTitle - the name of the group, placed in the <title> section
	 */
	public void newGroup(String groupTitle) {
		XMLElement g = new XMLElement("g", "");
		XMLElement title = new XMLElement("title", "");
		content.add(g.getStart());
		content.add(title.getStart() + groupTitle + title.getEnd());
	}

	/*
	 * endGroup - close a group by adding the </g> tag to the content queue
	 */
	public void endGroup() {
		content.add(new XMLElement("g", "").getEnd());
	}

	/*
	 * === PRIVATE METHOD ===
	 * To add text to your SVG, use a method like addTitle, addLabel, or addFooter (not yet implemented)
	 *
	 * addText - return text to add to an SVG
	 * args:
	 * text - the text to display
	 * x - the x position of the text
	 * y - the y position of the text
	 * size - the font size
	 * id - the XML id of the text element
	 * center - center text on x, y if true
	 * map - whether or not the points need to be mapped to the SVG coordinates
	 */
	private XMLElement addText(String text, double x, double y, int size, String id, boolean center, boolean map) {
		if (map) {
			int[] point = mapPoints(x, y);
			x = (double)point[0];
			y = (double)point[1];
		}

		ArrayList<String> attributes = new ArrayList<String>();
		attributes.add("x");
		attributes.add(Integer.toString((int)x));
		attributes.add("y");
		attributes.add(Integer.toString((int)y));
		attributes.add("font-size");
		attributes.add(Integer.toString(size));
		attributes.add("id");
		attributes.add(id);
		attributes.add("font-family");
		attributes.add("Sans-serif");
		if (center) {
			attributes.add("text-anchor");
			attributes.add("middle");
		}
		return new XMLElement("text", attributes);
	}

	/*
	 * addTitle - add a title to an SVG. The title will be centered in the upper padding of the SVG
	 * text - the title text
	 * id - the SVG id of the text element
	 */
	public void addTitle(String text, String id) {
		// Create text element centered on the horizontal axis, 4/5 of the way down the padding on the vertical axis
		XMLElement txt = addText(text, (width + 2 * xOffset)/ 2, yOffset * 4 / 5, 24, id, true, false);
		// Add to the header so it's not transformed with the original SVG:
		footer.add(0, txt.getStart() + text + txt.getEnd());
	}

	public void addFooter(String text, String id) {
		XMLElement txt = addText(text, (width + 2 * xOffset) / 2, -yOffset + (3 * yOffset / 5) + height, 24, id, true, false);
		footer.add(1, txt.getStart() + text + txt.getEnd());
	}

	/*
	 * addLineLabel - add a text label in the middle of two x,y coordinates
	 * text - the text of the label
	 * id - the id attribute of the text (e.g. "leg1")
	 * x1, y1, x2, y2 - the coordinates of a line 
	 */
	public void addLineLabel(String text, String id, double x1, double y1, double x2, double y2) {
		XMLElement txt = addText(text, x1 + (x2 - x1) / 2, y1 + (y2 - y1) / 2, 16, id, false, true);
		content.add(txt.getStart() + text + txt.getEnd());
	}

	public void addLabel(String text, String id, double x, double y) {
		XMLElement txt = addText(text, x, y, 16, id, false, true);
		content.add(txt.getStart() + text + txt.getEnd());
	}

	/*
	 * writeSVG - output the SVG to a file
	 * Returns an ArrayList of all the contents for testing 
	 */
    public ArrayList<String> writeSVG(String filename) {
    	ArrayList<String> testData = new ArrayList<String>();
    	testData.addAll(header);
    	if (originalContent != null)
    		testData.addAll(originalContent);
    	testData.addAll(content);
    	testData.addAll(footer);
		String loc = System.getProperty("user.dir");
		if (loc.contains("src")) {
			loc += "/main/resources/";
		} else {
			loc += "/src/main/resources/";
		}
		
    	try {
			// New BufferedWriter with filename of original input file
    		BufferedWriter write = new BufferedWriter(new FileWriter(loc + filename));
			// Write the contents of the original SVG, as well as whatever header elements added
			for (String s: header) 
				write.write(s + "\n");

			// Write the old SVG if it exists
			if (originalContent != null)
				for (String s: originalContent)
					write.write(s + "\n");
    		
			// Write the newly added SVG content
    		for (String s : content)
    			write.write(s + "\n");

			// Close the content of the original SVG
    		for (String s: footer) 
    			write.write(s + "\n");
    		
    		write.close();
    		
    	} catch (IOException e) {
    		
    	}
    	return testData;
    }
    
    public static void main(String[] args) {
		SVGWriter s = new SVGWriter("coloradoMap.svg");
		s.padSVG();
		s.addTitle("Colorado", "state");
		s.addFooter("infinite miles", "footer");
		// 37. 37, 1029, 746
		s.addLine(-105.5, 39, -102, 41, "#999999", 3, true);
		s.addLineLabel("test", "test", -105.5, 39, -102, 41);
		s.addLabel("test label", "id3", -105.5, 39);
		//s.addLine(s.mapPoints(-102,41)[0], s.mapPoints(-102, 41)[1], s.mapPoints(-109, 37)[0], s.mapPoints(-109, 37)[1], "black", 3);
		//s.addLine(37, 37, 1029, 746, "black", 3);
		s.writeSVG("coloradoMapCopy.svg");
    }
    
}