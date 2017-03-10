package edu.csu2017sp314.DTR14.tripco.View;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

public class SVGWriterTest {

	@Test
	public void testSVGWriterConstructor1() {
		
		try {
			/*
			 * Create a simple SVG
			 */
			String expected = "<?xml version=\"1.0\" ?>\n<svg\nwidth=\"10\"\nheight=\"10\">\n";
			BufferedWriter b = new BufferedWriter(new FileWriter("test.svg"));
			b.write("<?xml version=\"1.0\" ?>\n\t<svg \n\t\twidth=\"10\" \n\t\theight=\"10\">\n\t</svg>");
			b.close();
			/*
			 * Open the SVG using the SVGWriter class
			 */
			SVGWriter s = new SVGWriter("test.svg");
			// Check the header
			String cat = s.header.get(0);
			for (String string : s.originalContent) {
				cat += string + "\n";
			}
			assertTrue(cat.equals(expected));
			// Check the height and width
			assertTrue(s.width == 10);
			assertTrue(s.height == 10);
		}
		catch (IOException e) {
			System.out.println("Failed to create file test.svg");
		}
	}
	
	@Test
	public void testSVGWriterConstructor2() {
		SVGWriter s = new SVGWriter(100, 100);
		String expected = "<?xml version=\"1.0\"?>\n<svg width=\"100\" height=\"100\" xmlns:svg=\"http://www.w3.org/2000/svg\" xmlns=\"http://www.w3.org/2000/svg\">";
		assertTrue((s.header.get(0) + "\n" + s.header.get(1)).equals(expected));
	}
	
	
	@Test
	public void testMapPoints() {
		SVGWriter s = new SVGWriter(100, 100);
		int[] test = s.mapPoints(-109, 41);
		// -109.0, 41.0 (CO top left corner) should map to 37, 37 (top left of CO on svg)
		assertTrue(test[0] == 37 && test[1] == 37);
		
		test = s.mapPoints(-102, 37);
		// -102, 37 (CO bottom right corner) should map to 992 + 37, 709 + 37 (bottom right corner of CO + offset of top left)
		assertTrue(test[0] == (992 + 37) && test[1] == (709 + 37));
	}
	
	@Test
	public void testAddLine() {
		try {
			/*
			 * Create a simple SVG
			 */
			BufferedWriter b = new BufferedWriter(new FileWriter("test.svg"));
			b.write("<?xml version=\"1.0\" ?>\n\t<svg \n\t\twidth=\"10\" \n\t\theight=\"10\">\n\t</svg>");
			b.close();
			/*
			 * Open the SVG using the SVGWriter class
			 */
			SVGWriter s = new SVGWriter("test.svg");
			s.addLine(0, 0, 10, 10, "#999999", 3, false);
			s.addLine(0.0, 0.0, 10.0, 10.0, "#999999", 3, false);
			String expected = s.content.get(0);
			assertTrue(expected.equals("<line x1=\"0\" y1=\"0\" x2=\"10\" y2=\"10\" stroke=\"#999999\" stroke-width=\"3\" />"));
			expected = s.content.get(1);
			assertTrue(expected.equals("<line x1=\"0\" y1=\"0\" x2=\"10\" y2=\"10\" stroke=\"#999999\" stroke-width=\"3\" />"));
		}
		catch (IOException e) {
			System.out.println("Failed to create file test.svg");
		}
	}
	
	@Test
	public void testAddTitle() {
		SVGWriter s = new SVGWriter(100, 100);
		s.addTitle("Test Title", "testid");
		assertTrue(s.header.get(2).contains("font-size=\"24"));
		assertTrue(s.header.get(2).contains(">Test Title<"));
		assertTrue(s.header.get(2).contains("id=\"testid"));
	}
	
	@Test
	public void testAddFooter() {
		SVGWriter s = new SVGWriter(100, 100);
		s.addFooter("Test Footer", "testid");
		assertTrue(s.header.get(2).contains("font-size=\"24"));
		assertTrue(s.header.get(2).contains(">Test Footer<"));
		assertTrue(s.header.get(2).contains("id=\"testid"));		
	}

	@Test
	public void testAddLineLabel() {
		SVGWriter s = new SVGWriter(100, 100);
		// Label should be halfway between the two points:
		int[] test = s.mapPoints(-105.5, 39);
		s.addLineLabel("Test Label", "testlabel", -109, 41, -102, 37);
		assertTrue(s.content.get(0).contains("x=\"" + test[0]));
		assertTrue(s.content.get(0).contains("y=\"" + test[1]));
	}
	
	@Test
	public void testAddLabel() {
		SVGWriter s = new SVGWriter(100, 100);
		s.addLabel("Test Label", "test id", -109, 41);
		int[] test = s.mapPoints(-109, 41);
		assertTrue(s.content.get(0).contains("x=\"" + test[0]));
		assertTrue(s.content.get(0).contains("y=\"" + test[1]));
		assertTrue(s.content.get(0).contains(">Test Label<"));
	}
	
	@Test
	public void testWriteSVG() {
		SVGWriter s = new SVGWriter(100, 100);
		ArrayList<String> testData = s.writeSVG("test.svg");
		System.out.println(testData);
		assertTrue(testData.get(0).equals("<?xml version=\"1.0\"?>"));
		assertTrue(testData.get(1).contains("<svg"));
		assertTrue(testData.get(2).equals("</svg>"));
	}

	@Test
	public void testPadSVG() {
		SVGWriter svg = new SVGWriter(100, 100);
		svg.xOffset = 50;
		svg.yOffset = 50;
		svg.padSVG();
		// Assert there is a new SVG header with width 2 * xOffset + original width:
		assertTrue(svg.header.get(1).contains("width=\"200"));
		// Assert there is a new SVG header with height 2 * yOffset + original height:
		assertTrue(svg.header.get(1).contains("height=\"200"));
		// Assert the original SVG has been translated 50 down and right:
		assertTrue(svg.header.get(2).contains("translate(50, 50)"));
	}
	
	
	@Test
	public void testNewGroup() {
		SVGWriter s = new SVGWriter(100, 100);
		s.newGroup("Test group");
		assertTrue(s.content.get(0).equals("<g >"));
		assertTrue(s.content.get(1).equals("<title >Test group</title>"));
	}

	@Test
	public void testEndGroup() {
		SVGWriter s = new SVGWriter(100, 100);
		s.endGroup();
		assertTrue(s.content.get(0).equals("</g>"));
	}

}
