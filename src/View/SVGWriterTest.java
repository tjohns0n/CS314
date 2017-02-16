package View;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;

public class SVGWriterTest {

	//@Test
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
			assertTrue(s.header.equals(expected));
			// Check the height and width
			assertTrue(s.width == 10);
			assertTrue(s.height == 10);
		}
		catch (IOException e) {
			System.out.println("Failed to create file test.svg");
		}
	}
	
	//@Test
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
			s.addLine(0, 0, 10, 10);
			String expected = s.content.get(0);
			System.out.println(expected);
			assertTrue(expected.equals("<line x1=\"0\" y1=\"0\" x2=\"10\" y2=\"10\" />"));
		}
		catch (IOException e) {
			System.out.println("Failed to create file test.svg");
		}
	}
	
	@Test
	public void testWriteSVG() {

	}

}
