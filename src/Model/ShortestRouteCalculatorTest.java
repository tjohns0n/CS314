package Model;

import static org.junit.Assert.*;

import org.junit.*;

public class ShortestRouteCalculatorTest{
	
	private final static LocationList loclist = new LocationList();;

	private final static String fileName = "Colorado14ers.csv";

	private final static CSVReader csvr = new CSVReader(fileName, loclist);;

	@Test
	public void testConstructor() {
		
	}

	@Test
	public void testInitiate() {
	}

}