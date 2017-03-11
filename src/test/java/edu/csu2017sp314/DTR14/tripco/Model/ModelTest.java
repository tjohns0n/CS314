package edu.csu2017sp314.DTR14.tripco.Model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import static org.junit.Assert.*;

import org.junit.*;

public class ModelTest{

	@Test
	public void testConstructor() {
		Model model = new Model("test.csv");
	}

	@Test
	public void testPlanTrip() {
		Model model = new Model("test.csv");
		assertTrue("wrong trip", model.planTrip(false, false));
	}

	@Test
	public void testReteriveTrip() throws IOException {
            try (BufferedWriter b = new BufferedWriter(new FileWriter("test.csv"))) {
                b.write("name,id,Elevation,Estimated Prominence,latitude,longitude,Quadrangle,Range\n");
                b.write("Mount Elbert,1,14433,9093,39.1177,-106.4453,Mount Elbert,Sawatch\n");
                b.write("Mount Massive,2,14421,1961,39.1875,-106.4756,Mount Massive,Sawatch\n");
                b.write("Mount Harvard,3,14420,2360,38.9243,-106.3208,Mount Harvard,Sawatch\n");
                b.write("Blanca Peak,4,14345,5325,37.5774,-105.4857,Blanca Peak,Sangre de Cristo\n");
                b.close();
            }
		Model model = new Model("test.csv");
		model.planTrip(false, false);
		String tests[][] = model.reteriveTrip();
		assertTrue("wrong trip", tests[0][0].equals("0,Mount Elbert,39.1177,-106.4453"));
		assertTrue("wrong trip", tests[1][1].equals("2,14421,1961,Mount Massive,Sawatch"));
		assertTrue("wrong trip", tests[2][2].equals("id,Elevation,Estimated Prominence,Quadrangle,Range"));
	}
}