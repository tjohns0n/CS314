package edu.csu2017sp314.DTR14.tripco.Model;

import static org.junit.Assert.*;

import org.junit.*;

public class TripTest{

	private LocationList locList = new LocationList();;

	//private final static String fileName = "Colorado14ers.csv";

	//private final static CSVReader csvr = new CSVReader(fileName, locList);

	private ShortestRouteCalculator src;// = new ShortestRouteCalculator(locList, 0);

	@Test
	public void testConstructor() {
		src = new ShortestRouteCalculator(locList, 0);
		Trip trip =  new Trip(locList, src.getFinalRoute());
	}

	@Test
	public void testCreateTrip() {
		Location l1 = new Location("Mount Elbert", "39.1177", "-106.4453", "Hello", "");
		Location l2 = new Location("Mount Cool", "39", "-106", "Cool", "");
		locList.addLocation(l1);
		locList.addLocation(l2);
		src = new ShortestRouteCalculator(locList, 0);
		src.findBestNearestNeighbor(false, false);
		Trip trip = new Trip(locList, src.getFinalRoute());
		String tests[][] = trip.createTrip();
		assertTrue("wrong trip", tests[0][0].equals("0,Mount Elbert,39.1177,-106.4453"));
	}
}