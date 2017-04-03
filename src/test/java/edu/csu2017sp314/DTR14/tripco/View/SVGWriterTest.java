package edu.csu2017sp314.DTR14.tripco.View;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

public class SVGWriterTest {
	
	@Test
	public void testSVGWriterConstructor2() {
		ColoradoSVGWriter s = new ColoradoSVGWriter();
		String expected = "<?xml version=\"1.0\"?>\n<svg width=\"1066.6073\" height=\"783.0824\" xmlns:svg=\"http://www.w3.org/2000/svg\" xmlns=\"http://www.w3.org/2000/svg\">";
		assertTrue((s.header.get(0) + "\n" + s.header.get(1)).equals(expected));
	}
	
	
	@Test
	public void testMapPoints() {
		ColoradoSVGWriter s = new ColoradoSVGWriter();
		int[] test = s.mapPoints(-109, 41);
		// -109.0, 41.0 (CO top left corner) should map to 37, 37 (top left of CO on svg)
		assertTrue(test[0] == 37 && test[1] == 37);
		
		test = s.mapPoints(-102, 37);
		// -102, 37 (CO bottom right corner) should map to 992 + 37, 709 + 37 (bottom right corner of CO + offset of top left)
		assertTrue(test[0] == (993 + 37) && test[1] == (709 + 37));
		
		WorldMapWriter w = new WorldMapWriter();
		test = w.mapPoints(-109, -41);
		assertTrue(test[0] == (71 * 4) && test[1] == 131 * 4);
		test = w.mapPoints(109, 41);
		assertTrue(test[0] == 289 * 4 && test[1] == 196);
		test = w.mapPoints(0, 0);
		assertTrue(test[0] == 720 && test[1] == 360);
		test = w.mapPoints(-180, 90);
		assertTrue(test[0] == 0 && test[1] == 0);
		test = w.mapPoints(180, -90);
		assertTrue(test[0] == 1440 && test[1] == 720);
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
			ColoradoSVGWriter s = new ColoradoSVGWriter("test.svg");
			s.addLine(new double[]{0, 0, 10, 10}, "#999999", 3, false);
			s.addLine(new double[]{0.0, 0.0, 10.0, 10.0}, "#999999", 3, false);
			String expected = s.content.get(0);
			assertTrue(expected.equals("<line x1=\"0.0\" y1=\"0.0\" x2=\"10.0\" y2=\"10.0\" stroke=\"#999999\" stroke-width=\"3\" />"));
			expected = s.content.get(1);
			assertTrue(expected.equals("<line x1=\"0.0\" y1=\"0.0\" x2=\"10.0\" y2=\"10.0\" stroke=\"#999999\" stroke-width=\"3\" />"));
		}
		catch (IOException e) {
			System.out.println("Failed to create file test.svg");
		}
	}
	
	@Test
	public void testAddTitle() {
		ColoradoSVGWriter s = new ColoradoSVGWriter();
		s.addTitle("Test Title", "testid");
		assertTrue(s.footer.get(0).contains("font-size=\"24"));
		assertTrue(s.footer.get(0).contains(">Test Title<"));
		assertTrue(s.footer.get(0).contains("id=\"testid"));
		WorldMapWriter w = new WorldMapWriter();
		w.addTitle("Test", "test");
		assertTrue(w.content.get(0).contains("font-size=\"24"));
		assertTrue(w.content.get(0).contains("x=\"720"));
		assertTrue(w.content.get(0).contains("y=\"30"));
	}
	
	@Test
	public void testAddFooter() {
		ColoradoSVGWriter s = new ColoradoSVGWriter();
		s.addFooter("Test Footer", "testid");
		assertTrue(s.footer.get(1).contains("font-size=\"24"));
		assertTrue(s.footer.get(1).contains(">Test Footer<"));
		assertTrue(s.footer.get(1).contains("id=\"testid"));	
		WorldMapWriter w = new WorldMapWriter();
		w.addFooter("Test", "test");
		assertTrue(w.content.get(0).contains("font-size=\"24"));
		assertTrue(w.content.get(0).contains("x=\"720"));
		assertTrue(w.content.get(0).contains("y=\"700"));
	}

	@Test
	public void testAddLineLabel() {
		ColoradoSVGWriter s = new ColoradoSVGWriter();
		// Label should be halfway between the two points:
		int[] test = s.mapPoints(-105.5, 39);
		s.addLineLabel("Test Label", "testlabel", new double[]{-109, 41, -102, 37});
		assertTrue(s.content.get(0).contains("x=\"" + test[0]));
		assertTrue(s.content.get(0).contains("y=\"" + test[1]));
	}
	
	@Test
	public void testAddLabel() {
		ColoradoSVGWriter s = new ColoradoSVGWriter();
		s.addLabel("Test Label", "test id", new double[] {-109, 41});
		int[] test = s.mapPoints(-109, 41);
		assertTrue(s.content.get(0).contains("x=\"" + test[0]));
		assertTrue(s.content.get(0).contains("y=\"" + test[1]));
		assertTrue(s.content.get(0).contains(">Test Label<"));
	}
	
	@Test
	public void testWriteSVG() {
		ColoradoSVGWriter s = new ColoradoSVGWriter();
		ArrayList<String> testData = s.writeSVG("test0.svg");
		System.out.println(testData);
		assertTrue(testData.get(0).equals("<?xml version=\"1.0\"?>"));
		assertTrue(testData.get(1).contains("<svg"));
		assertTrue(testData.get(2).equals("</svg>"));
		String loc = System.getProperty("user.dir");
		if (loc.contains("src")) {
			loc += "/main/resources/";
		} else {
			loc += "/src/main/resources/";
		}
		
		File f = new File(loc+"test0.svg");
		f.delete();
	}
	
	@Test
	public void testNewGroup() {
		ColoradoSVGWriter s = new ColoradoSVGWriter();
		s.newGroup("Test group");
		System.out.println(s.content);
		assertTrue(s.content.get(0).equals("<g id=\"Test group\">"));
		assertTrue(s.content.get(1).equals("<title>Test group</title>"));
	}

	@Test
	public void testEndGroup() {
		ColoradoSVGWriter s = new ColoradoSVGWriter();
		s.endGroup();
		assertTrue(s.content.get(0).equals("</g>"));
	}

}
