package Model;

import static org.junit.Assert.*;

import org.junit.*;

public class CSVReaderTest{

	@Test
	public void testConstructor() {
		
		LocationList loclist = new LocationList();

		CSVReader csvr = new CSVReader("Colorado14ers.csv", loclist);
		assertTrue(loclist.get(0).getName().equals("Mount Elbert"));
		assertTrue(loclist.get(1).getLatitude().equals("39.1875"));
		assertTrue(loclist.get(2).getLongitude().equals("-106.3208"));
	}
	
}