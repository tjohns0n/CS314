package Model;

import static org.junit.Assert.*;

import org.junit.*;

public class LocationTest{

	@Test
	public void testConstructor1() {
		
		Location loc = new Location("name1", "134.23", "-424.32", "info");

		assertTrue("name not read correctly", loc.getName().equals("name1"));
		assertTrue("latitude not read correctly", loc.getLatitude() == 134.23);
		assertTrue("longitude not read correctly", loc.getLongitude() == -424.32);
		assertTrue("info not read correctly", loc.getInfo().equals("info"));
	}

	@Test
	public void testConstructor2() {
		
		Location loc = new Location("name2", "-424.32", "134.23");

		assertTrue("name not read correctly", loc.getName().equals("name2"));
		assertTrue("latitude not read correctly", loc.getLatitude() == -424.32);
		assertTrue("longitude not read correctly", loc.getLongitude() == 134.23);
		assertTrue("info not read correctly", loc.getInfo().equals(""));
	}

	@Test
	public void testGetName() {
		
		Location loc = new Location("name1", "134.23", "-424.32", "info");

		assertTrue("name not read correctly", loc.getName().equals("name1"));
	}

	@Test
	public void testGetLatitude() {
		
		Location loc = new Location("name1", "134.23", "-424.32", "info");

		assertTrue("name not read correctly", loc.getLatitude() == 134.23);
	}

	@Test
	public void testGetLongitude() {
		
		Location loc = new Location("name1", "134.23", "-424.32", "info");

		assertTrue("name not read correctly", loc.getLongitude() == -424.32);
	}

	@Test
	public void testGetInfo() {
		
		Location loc = new Location("name1", "134.23", "-424.32", "info");

		assertTrue("name not read correctly", loc.getInfo().equals("info"));
	}


}