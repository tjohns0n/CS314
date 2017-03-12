package edu.csu2017sp314.DTR14.tripco.Model;

import static org.junit.Assert.*;

import org.junit.*;

public class ModelTest{

	private final static String fileName = "Colorado14ers.csv";

	@Test
	public void testConstructor() {
		Model model = new Model(fileName);
	}

	@Test
	public void testPlanTrip() {
		Model model = new Model(fileName);
		//assertTrue("wrong trip", model.planTrip());
	}

        /*
	@Test
	public void testReteriveTrip() {
		Model model = new Model(fileName);
		model.planTrip();
		String tests[][] = model.reteriveTrip();
		assertTrue("wrong trip", tests[0][0].equals("0,Mount Elbert,39.1177,-106.4453"));
		assertTrue("wrong trip", tests[1][1].equals("2,14421,1961,Mount Massive,Sawatch"));
		assertTrue("wrong trip", tests[2][2].equals("id,Elevation,Estimated Prominence,Quadrangle,Range"));
	}
*/
}