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
	// offsets once SVG is padded
	int xOffset = 50;
	int yOffset = 50;
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

		// Init string
    	try {
			// Create buffered reader to read an SVG
			BufferedReader readSVG = new BufferedReader(new FileReader(filename));
			String line;
			
			// Read in a line from the SVG and trim the whitespace (may remove trimming later)
			line = readSVG.readLine().trim();
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
				if (line.contains("<svg")) {
					svg = true;
				}
				// if in svg tag
				if (svg) {
					// split on quotes to isolate values of attributes
					String[] strings = line.split("\"");
					// loop through the svg tag
					for (int i = 0; i < strings.length; i++) {
						// extract the width from the svg
						if (strings[i].contains("width")) {
							width = Double.parseDouble(strings[i + 1]);
						}
						// extract the height from the svg
						if (strings[i].contains("height")) {
							height = Double.parseDouble(strings[i + 1]);
						}
						// if the svg tag is over, stop searching for height and width
						if (strings[i].contains(">")) {
							svg = false;
						}
					}
				}
				// add each element of the SVG to the start of the write queue
				originalContent.add(line);
				// read the next line
				line = readSVG.readLine().trim();
			}
			// if end of document reached without seeing "</svg>, svg is invalid"
			if (line == null) {
				readSVG.close();
				throw new IOException();
				
			} else {
				// add </svg> to the end of the write queue
				footer.add("</svg>");
			}
			// close the buffered writer
			readSVG.close();

		} catch (IOException e) {
			System.out.println("SVG not formatted properly");
		}
    	this.filename = filename;
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
	 * addLine - add a line to the SVG write queue
	 * args:
	 * x1, y1, x2, y2 - the x and y coordinates of each point 
	 * color - the color of the line (accepts SVG color names or hex values with #)
	 * width - the width of the line 
	 */
    public void addLine(int x1, int y1, int x2, int y2, String color, int width) {
		// Construct attribute list for the line
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
    	attributes.add(color);
    	attributes.add("stroke-width");
    	attributes.add(Integer.toString(width));

		// Create the line and add it to the write queue 
    	XMLElement line = new XMLElement("line", attributes);
    	content.add(line.getStart());
    }		/*

	/*
	 * addLine - add a line to the SVG write queue
	 * args:
	 * x1, y1, x2, y2 - the x and y coordinates of each point 
	 * color - the color of the line (accepts SVG color names or hex values with #)
	 * width - the width of the line 
	 */
	public void addLine(double x1, double y1, double x2, double y2, String color, int width) {
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
		XMLElement svg = new XMLElement("svg", "width=\"" + (width + 2 * xOffset) + 
			"\" height=\"" + (height + 2 * yOffset) + "\" " +
			"xmlns:svg=\"http://www.w3.org/2000/svg\" xmlns=\"http://www.w3.org/2000/svg\"");
		// Place the new SVG tag after the xml header but before the original SVG
		header.add(1, svg.getStart());
		
		// Put the original SVG in a new group and transform that group to be moved by the padding offset
		XMLElement g = new XMLElement("g", "transform=\"translate(" + xOffset + ", " + yOffset + ")\"");
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
	 */
	private XMLElement addText(String text, int x, int y, int size, String id, boolean center) {
		ArrayList<String> attributes = new ArrayList<String>();
		attributes.add("x");
		attributes.add(Integer.toString(x));
		attributes.add("y");
		attributes.add(Integer.toString(y));
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
		XMLElement txt = addText(text, (int)(width + 2 * xOffset)/ 2, yOffset - 10, 24, id, true);
		header.add(2, txt.getStart() + text + txt.getEnd());
	}

	/*
	 * writeSVG - output the SVG to a file
	*/
    public void writeSVG(String filename) {
    	try {
			// New BufferedWriter with filename of original input file
    		BufferedWriter write = new BufferedWriter(new FileWriter(filename));
			// Write the contents of the original SVG, as well as whatever header elements added
			for (String s: header) {
				write.write(s + "\n");
			}

			// Write the old SVG if it exists
			if (originalContent != null) {
				for (String s: originalContent) {
					write.write(s + "\n");
				}
			}
    		
			// Write the newly added SVG content
    		for (String s : content) {
    			write.write(s);
    			write.write("\n");
    		}

			// Close the content of the original SVG
    		for (String s: footer) {
    			write.write(s);
    			write.write("\n");
    		}
    		write.close();
    		
    	} catch (IOException e) {
    		
    	}
    }
}