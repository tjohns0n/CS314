package edu.csu2017sp314.DTR14.tripco.Model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import static org.junit.Assert.*;

import org.junit.*;

public class ModelTest{

	String filename = "test.csv";

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
			System.err.println("Failed to open new file for writing - ModelTest");
			e.printStackTrace();
		}
	}
	
	@Test
	public void testConstructor() {
		Model model = new Model(filename);
	}

	@Test
	public void testPlanTrip() {
		Model model = new Model(filename);
		assertTrue("wrong trip", model.planTrip(false, false, new String[0]));
	}

	@Test
	public void testReteriveTrip() throws IOException {

		Model model = new Model(filename);
		model.planTrip(false, false, new String[0]);
		String tests[][] = model.reteriveTrip();
		assertTrue("wrong trip", tests[0][0].equals("0,Mount Elbert,39.1177,-106.4453"));
		assertTrue("wrong trip", tests[1][1].equals("2,14421,1961,Mount Massive,Sawatch"));
		assertTrue("wrong trip", tests[2][2].equals("id,Elevation,Estimated Prominence,Quadrangle,Range"));
	}
	
	@AfterClass
	public static void removeTestFile() {
		File f = null;
		f = new File("test.csv");
		f.delete();
	}
}