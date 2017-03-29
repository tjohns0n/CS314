package edu.csu2017sp314.DTR14.tripco.Model;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.*;

public class CSVReaderTest{
	String fileName = "test.csv";
	private static LocationList loclist;
	private static CSVReader csvr;
	
	@BeforeClass
	public static void writeTestFile() {
		String testfile = "test.csv";
		try {
			BufferedWriter w = new BufferedWriter(new FileWriter(testfile));
			w.write("name,id,Elevation,Estimated Prominence," 
					+ "latitude,longitude,Quadrangle,Range" 
					+ "\nMount Elbert,1,14433,9093,39.1177,"
					+ "-106.4453,Mount Elbert,Sawatch"
					+ "\nMount Massive,2,14421,1961,39.1875,"
					+ "-106.4756,Mount Massive,Sawatch"
					+ "\nMount Harvard,3,14420,2360,38.9243,"
					+ "-106.3208,Mount Harvard,Sawatch"
					+ "\nBlanca Peak,4,14345,5325,37.5774,"
					+ "-105.4857,Blanca Peak,Sangre de Cristo)");
			w.close();
		} catch (IOException e) {
			System.err.println("Failed to open new file for writing - CSVReaderTest");
			e.printStackTrace();
		}
	}
	
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
	
	@AfterClass
	public static void removeTestFile() {
		File f = null;
		f = new File("test.csv");
		f.delete();
	}
	
}