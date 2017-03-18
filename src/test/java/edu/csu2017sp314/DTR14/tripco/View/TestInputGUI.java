package edu.csu2017sp314.DTR14.tripco.View;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestInputGUI {
	
	@Test
	public void testConstructor(){
		InputGUI ig = new InputGUI();
		assertTrue(ig.vw==null);
		View vw = new View();
		vw.rootName="blah";
		assertTrue(vw.ig.vw.rootName.equals("blah"));
		
		
	}
	
}
