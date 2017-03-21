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

	@Test
	public void testConstructor() {
		Model model = new Model(filename);
	}

	@Test
	public void testPlanTrip() {
		String loc = System.getProperty("user.dir");
		System.out.println(loc);
		//loc+="/main/resources/";
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
}