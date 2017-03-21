package edu.csu2017sp314.DTR14.tripco.Model;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.*;

public class CSVReaderTest{
	String fileName = this.getClass().getClassLoader().getResource("test.csv").toString().substring(5);
	private static LocationList loclist;
	private static CSVReader csvr;
	@Test
	public void testConstructor() {
		CSVReader cvsr = new CSVReader(fileName, loclist);
	}

	@Test
	public void testInitiate(){
		//System.out.println(System.getProperty("user.dir"));
		// Test without subselection:
		loclist = new LocationList();
		csvr = new CSVReader(fileName, loclist);
		csvr.initiate(new String[0]);
		assertTrue(loclist.get(0).getName().equals("Mount Elbert"));
		assertTrue(loclist.get(0).getLatitude() == 39.1177);
		assertTrue(loclist.get(0).getLongitude() == -106.4453);

		// Test with subselection:
		String[] selection = new String[2];
		selection[0] = "2";
		selection[1] = "4";
		loclist = new LocationList();
		csvr = new CSVReader(fileName, loclist);
		csvr.initiate(selection);
		assertTrue(loclist.getsize() == 2);
		assertTrue(loclist.get(0).getName().equals("Mount Massive"));
		assertTrue(loclist.get(1).getName().equals("Blanca Peak"));
	}
	
}