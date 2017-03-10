package edu.csu2017sp314.DTR14.tripco.Model;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.*;

public class CSVReaderTest{

	private static LocationList loclist;

	private final String fileName = "test.csv";

	private static CSVReader csvr;
	@Test
	public void testConstructor() {
		try {
			BufferedWriter b = new BufferedWriter(new FileWriter(fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CSVReader cvsr = new CSVReader(fileName, loclist);
	}

	@Test
	public void testInitiate(){
		try {
			BufferedWriter b = new BufferedWriter(new FileWriter(fileName));
			b.write("name,id,Elevation,Estimated Prominence,latitude,longitude,Quadrangle,Range\n");
			b.write("Mount Elbert,1,14433,9093,39.1177,-106.4453,Mount Elbert, Sawatch");
			b.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		loclist = new LocationList();
		csvr = new CSVReader(fileName, loclist);
		csvr.initiate();
		assertTrue(loclist.get(0).getName().equals("Mount Elbert"));
		assertTrue(loclist.get(0).getLatitude() == 39.1177);
		assertTrue(loclist.get(0).getLongitude() == -106.4453);
	}
	
}