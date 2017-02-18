package Model;

import static org.junit.Assert.*;

import org.junit.*;

public class CSVReaderTest{

	private static LocationList loclist;

	private final String fileName = "Colorado14ers.csv";

	private static CSVReader csvr;
	@Test
	public void testConstructor() {

	}

	@Test
	public void testInitiate(){
		loclist = new LocationList();
		csvr = new CSVReader(fileName, loclist);
		csvr.initiate();
		assertTrue(loclist.get(0).getName().equals("Mount Elbert"));
		assertTrue(loclist.get(1).getLatitude() == 39.1875);
		assertTrue(loclist.get(2).getLongitude() == -106.3208);
	}
	
}