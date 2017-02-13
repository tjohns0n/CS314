package View;

import static org.junit.Assert.*;

import org.junit.Test;

public class XMLElementTest {

	@Test
	public void testXMLVersion() {
		XMLElement.XMLVersion v = new XMLElement.XMLVersion();
		assertTrue("XMLVersion footer not correct", v.start.equals("<?xml version=\"1.0\"?>"));
		assertTrue("XMLElement getStart() method faulty", v.start.equals(v.getStart()));
		assertTrue("XMLVersion footer not blank", v.end.equals(""));
		assertTrue("XMLElement getEnd() method faulty", v.end.equals(v.getEnd()));
	}

}
