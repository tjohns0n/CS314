package Model;

import static org.junit.Assert.*;

import org.junit.*;

public class TripTest{

	private final static LocationList locList = new LocationList();;

	private final static String fileName = "Colorado14ers.csv";

	private final static CSVReader csvr = new CSVReader(fileName, locList);

	private final static ShortestRouteCalculator src = new ShortestRouteCalculator(locList, 0);

	@Test
	public void testConstructor() {
		Trip trip =  new Trip(locList, src.getFinalRoute());
	}

	@Test
	public void testCreateTrip() {
		csvr.initiate();
		src.initiate();
		Trip trip = new Trip(locList, src.getFinalRoute());
		String tests[][] = trip.createTrip();
		assertTrue("wrong trip", tests[0][0].equals("0,Mount Elbert,39.1177,-106.4453"));
		assertTrue("wrong trip", tests[1][1].equals("2,14421,1961,Mount Massive,Sawatch"));
		assertTrue("wrong trip", tests[2][2].equals("id,Elevation,Estimated Prominence,Quadrangle,Range"));
	}
}