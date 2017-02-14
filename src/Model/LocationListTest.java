package Model;

import static org.junit.Assert.*;

import org.junit.*;

public class LocationListTest{

	@Test
	public void testConstructor() {

		LocationList loclist = new LocationList();
	}


	@Test
	public void testGetsize() {

		LocationList loclist = new LocationList();

		CSVReader csvr = new CSVReader("Colorado14ers.csv", loclist);

		assertTrue("wrong size", loclist.getsize() == 53);
	}

	@Test
	public void testGet() {
		
		LocationList loclist = new LocationList();

		CSVReader csvr = new CSVReader("Colorado14ers.csv", loclist);

		assertTrue("wrong get", loclist.get(28).getName().equals("Mount Democrat"));
		assertTrue("wrong get", loclist.get(38).getName().equals("Missouri Mountain"));
	}

	@Test
	public void testLineHandler() {
		
	}

}
