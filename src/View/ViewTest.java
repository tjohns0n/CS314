package View;

import static org.junit.Assert.*;
import org.junit.Test;

public class ViewTest {
	
	
	@Test
	public void testConstructor() {
		View v1 = new View("hello", "coloradoMap.svg", 300, false, false, false);
		assertTrue("rootName not read correctly", v1.rootName.equals("hello"));
		assertTrue("v1 flags do not match expected", v1.mileage == false && v1.ids == false && v1.names == false);
		
		View v2 = new View("hello.csv", "coloradoMap.svg", 300, true, true, true);
		assertTrue(".csv extension not properly removed from rootName", v2.rootName.equals("hello"));
		assertTrue("v2 flags do not match expected", v2.mileage == true && v2.ids == true && v2.names == true);
		assertTrue(v2.mileage == true);
		assertTrue(v2.names == true);
		assertTrue(v2.ids == true);
	}
	
	@Test
	public void testGetRootName() {
		View v1 = new View("helloWorld.csv", "coloradoMap.svg", 300, false, false, false);
		assertTrue("getRootName() method not returning correct root name", v1.getRootName().equals("helloWorld"));
	}
	
	@Test
	public void testDisplay() {
		View v1 = new View("helloWorld.csv", "coloradoMap.svg", 300, false, false, false);
		assertTrue(v1.display("hello").equals("hello"));
	}
	
	// writeFiles() calls methods called in other classes
	// 
	
	@Test
	public void testAddLeg() {
		View v = new View("helloWorld.csv", "coloradoMap.svg", 300, true, false, false);
		String test = v.addLeg(40, -108, "Not Denver", "id1", 39, -107, "Not CO Springs", "id2", 50);
		assertTrue(v.legCount == 1);
		assertTrue(test.equals("m"));
	}

}
