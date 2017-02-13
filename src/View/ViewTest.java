package View;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ViewTest {
	
	@Test
	public void testConstructor() {
		View v1 = new View("hello", false, false, false);
		assertTrue("rootName not read correctly", v1.rootName.equals("hello"));
		assertTrue("v1 flags do not match expected", v1.mileage == false && v1.ids == false && v1.names == false);
		
		View v2 = new View("hello.csv", true, true, true);
		assertTrue(".csv extension not properly removed from rootName", v2.rootName.equals("hello"));
		assertTrue("v2 flags do not match expected", v2.mileage == true && v2.ids == true && v2.names == true);
	}
	
	@Test
	public void testGetRootName() {
		View v1 = new View("helloWorld.csv", false, false, false);
		assertTrue("getRootName() method not returning correct root name", v1.getRootName().equals("helloWorld"));
	}

}
