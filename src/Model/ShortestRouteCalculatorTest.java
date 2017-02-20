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
		ShortestRouteCalculator src = new ShortestRouteCalculator(locList, 0);
		src.initiate();
	}

}