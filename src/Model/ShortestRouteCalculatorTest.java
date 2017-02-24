package Model;

import static org.junit.Assert.*;

import org.junit.*;

public class ShortestRouteCalculatorTest{
	
	private final static LocationList locList = new LocationList();;

	private final static String fileName = "Colorado14ers.csv";

	private final static CSVReader csvr = new CSVReader(fileName, locList);;

	@Test
	public void testConstructor() {
		ShortestRouteCalculator src = new ShortestRouteCalculator(locList, 0);
	}

	@Test
	public void testInitiate() {
		csvr.initiate();
		ShortestRouteCalculator src = new ShortestRouteCalculator(locList, 0);
		src.initiate();
		int[][] tests = src.getFinalRoute();
		assertTrue("wrong route", tests[0][1] == 0);
		assertTrue("wrong route", tests[1][1] == 5);
		assertTrue("wrong route", tests[5][0] == 17);
		assertTrue("wrong route", tests[10][1] == 40);
	}

}