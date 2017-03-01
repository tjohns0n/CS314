package Model;

import static org.junit.Assert.*;

import org.junit.*;

public class LocationListTest{

	private LocationList loclist;

	@Test
	public void testConstructor() {
		LocationList loc = new LocationList();
	}

	
	@Test
	public void testLineHandler() {
		loclist = new LocationList();
		String[] title = {"name", "latitude", "longitude", "zipcode"};
		loclist.lineHandler("ft collins, 2391.2, 9312.3, 80525" , title);
		assertTrue("wrong Handler" , loclist.get(0).getName().equals("ft collins"));
		assertTrue("wrong Handler" , loclist.get(0).getLatitude() == 2391.2);
		assertTrue("wrong Handler" , loclist.get(0).getLongitude() == 9312.3);
		assertTrue("wrong Handler" , loclist.get(0).getExtras().equals("80525"));
		assertTrue("wrong Handler" , loclist.get(0).getTemplate().equals("zipcode"));
	}
	
	
	@Test
	public void testGetsize() {
		
		// change the constance before you test the getsize()!
		loclist = new LocationList();
		loclist.addLocation(new Location("A", "1", "2", "A B", "id"));
		System.out.println(loclist.getsize());
		assertTrue("wrong size", loclist.getsize() == 1);
	}

	@Test
	public void testGet() {
		loclist = new LocationList();
		loclist.addLocation(new Location("A", "1", "2", "A B C D E F", "id,lat,lon"));
		assertTrue("wrong get", loclist.get(0).getName().equals("A"));
		assertTrue("wrong get", loclist.get(0).getLatitude() == 1);
		assertTrue("wrong get", loclist.get(0).getLongitude() == 2);
		assertTrue("wrong get", loclist.get(0).getExtras().equals("A B C D E F"));
		assertTrue("wrong get", loclist.get(0).getTemplate().equals("id,lat,lon"));
	}

}
