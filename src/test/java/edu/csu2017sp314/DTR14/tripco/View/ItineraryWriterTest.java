package edu.csu2017sp314.DTR14.tripco.View;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.Test;

public class ItineraryWriterTest {

	@Test
	public void testConstructor() {
		ItineraryWriter w = new ItineraryWriter();
		
		assertTrue(w.header.get(0).equals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"));
		assertTrue(w.header.get(2).equals("<trip>"));
		assertTrue(w.footer.get(1).equals("</trip>"));
	}

	
	@Test
	public void testNewLeg() {
		ItineraryWriter w = new ItineraryWriter();
		
		ArrayList<String> testData = w.addLeg("here", "there", 3);
		
		assertTrue(testData.get(1).equals("<leg>"));
		assertTrue(testData.get(3).equals("<sequence>"));
		assertTrue(testData.get(4).equals("1"));
		assertTrue(testData.get(5).equals("</sequence>"));
		
		assertTrue(testData.get(7).equals("<start>"));
		assertTrue(testData.get(8).equals("here"));
		assertTrue(testData.get(9).equals("</start>"));		
		
		assertTrue(testData.get(11).equals("<finish>"));
		assertTrue(testData.get(12).equals("there"));
		assertTrue(testData.get(13).equals("</finish>"));
		
		assertTrue(testData.get(15).equals("<mileage>"));
		assertTrue(testData.get(16).equals("3"));
		assertTrue(testData.get(17).equals("</mileage>"));
		assertTrue(testData.get(19).equals("</leg>"));
	}
	
	@Test
	public void testWriteXML() {
		ItineraryWriter w = new ItineraryWriter();
		
		ArrayList<String> testData = w.writeXML("test0.xml");
		
		assertTrue(testData.contains("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"));
		assertTrue(testData.contains("<trip>"));
		assertTrue(testData.contains("</trip>"));
		String loc = System.getProperty("user.dir");
		if (loc.contains("src")) {
			loc += "/main/resources/";
		} else {
			loc += "/src/main/resources/";
		}
		
		File f = new File(loc+"test0.xml");
		f.delete();
	}
}
