package Model;

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

	@Test
	public void testReteriveTrip() {
		Model model = new Model(fileName);
		model.planTrip();
		String tests[][] = model.reteriveTrip();
		assertTrue("wrong trip", tests[0][0].equals("0,MountElbert,39.1177,-106.4453"));
		assertTrue("wrong trip", tests[1][1].equals("2,14421,1961,MountMassive,Sawatch"));
		assertTrue("wrong trip", tests[2][2].equals("id,Elevation,Estimated Prominence,Quadrangle,Range"));
	}
}