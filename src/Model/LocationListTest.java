package Model;

import static org.junit.Assert.*;

import org.junit.*;

public class LocationListTest{

	private final static LocationList loclist = new LocationList();;

	private final static String fileName = "Colorado14ers.csv";

	private final static CSVReader csvr = new CSVReader(fileName, loclist);;

	@Test
	public void testConstructor() {
	
	}


	@Test
	public void testGetsize() {
		csvr.initiate();
		// change the constance before you test the getsize()!
		assertTrue("wrong size", loclist.getsize() == 10);
	}

	@Test
	public void testGet() {
		csvr.initiate();
		assertTrue("wrong get", loclist.get(5).getName().equals("Uncompahgre Peak"));
		assertTrue("wrong get", loclist.get(6).getLatitude() == 37.9667);
		assertTrue("wrong get", loclist.get(7).getLongitude() == -106.1115);
		assertTrue("wrong get", loclist.get(5).getExtras().equals("6,14309,4249,Uncompahgre Peak,San Juan"));
		assertTrue("wrong get", loclist.get(9).getTemplate().equals("id,Elevation,Estimated Prominence,Quadrangle,Range"));
	}

	@Test
	public void testLineHandler() {
		testGet();
	}

}
