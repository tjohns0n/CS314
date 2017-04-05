/*
ColoradoSVGWriter.java
Draws a trip to a Colorado map SVG 
Legacy class, use WorldSVGWriter from now on
*/

package edu.csu2017sp314.DTR14.tripco.View;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ColoradoSVGWriter extends SVGWriter {
	// offsets once SVG is padded
	int xOffset = 37;
	int yOffset = 37;

    /*
     * SVGWriter constructor 1:
     * Read in an existing SVG so that additional elements can be added on
     */
    public ColoradoSVGWriter(String filename) {
		super(filename);
		width = 1066.6073;
		height = 783.0824;
		initXml();
    }
    
    /*
	 * SVGWriter constructor 2:
	 * Write a blank SVG 
	 */
	public ColoradoSVGWriter() {
		super();
		width = 1066.6073;
		height = 783.0824;
		initXml();
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
		mapping[0] = (int)Math.round(x);
		mapping[1] = (int)Math.round(y);
		return mapping;
	}
    
	/*
	 * addTitle - add a title text to the SVG 
	 * args:
	 * text - the title text 
	 * id - the id of the text element
	 */
	public void addTitle(String text, String id) {
		// Create text element centered on the horizontal axis, 
		// 		4/5 of the way down the padding on the vertical axis
		double[] coordinates = {(width + 2 * xOffset)/ 2, yOffset * 4 / 5}};
		XMLElement txt = addText(text, coordinates, 24, id, true, false);
		// Add to the header so it's not transformed with the original SVG:
		footer.add(0, txt.getStart() + text + txt.getEnd());
	}

	/*
	 * addFooter - add a footer to the SVG 
	 * args:
	 * text - the footer text 
	 * id - the id of the text element 
	 */
	public void addFooter(String text, String id) {
		double[] coordinates = {(width + 2 * xOffset) / 2, -yOffset + (3 * yOffset / 5) + height};
		XMLElement txt = addText(text, coordinates, 24, id, true, false);
		footer.add(1, txt.getStart() + text + txt.getEnd());
	}

    public static void main(String[] args) {
		ColoradoSVGWriter s = new ColoradoSVGWriter("coloradoMap.svg");
		s.addTitle("Colorado", "state");
		s.addFooter("infinite miles", "footer");
		// 37. 37, 1029, 746
		s.addLine(new double[]{-105.5, 39, -102, 41}, "#999999", 3, true);
		s.addLineLabel("test", "test", new double[] {-105.5, 39, -102, 41});
		s.addLabel("test label", "id3", new double[] {-105.5, 39});
		s.writeSVG("coloradoMapCopy.svg");
    }
}