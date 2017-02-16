package View;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

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
			s.addLine(0, 0, 10, 10, "#999999", 3);
			s.addLine(0.0, 0.0, 10.0, 10.0, "#999999", 3);
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
	public void testWriteSVG() {

	}

	@Test
	public void testPadSVG() {
		SVGWriter s = new SVGWriter("coloradoMap.svg");
		s.padSVG();
		s.addTitle("Colorado", "state");
		s.writeSVG("coloradoMapCopy.svg");
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
