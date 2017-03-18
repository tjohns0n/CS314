package edu.csu2017sp314.DTR14.tripco.View;

import org.junit.Test;
import static org.junit.Assert.*;

public class SelectionWriterTest {
	
	@Test
	public void testConstructor(){
		Selection s = new Selection("testSelec.xml");
		s.setBackSVG("here.svg");
		SelectionWriter sw = new SelectionWriter(s);
		assertTrue(sw.select.getBackSVGName().equals("here.svg"));
		assertTrue(sw.select.getSubset().length==0);
		boolean[] optn = sw.select.getOptions();
		assertTrue(optn[0]==false);
		assertTrue(optn[1]==false);
		assertTrue(optn[2]==false);
		boolean[] opt = sw.select.getOpts();
		assertTrue(opt[0]==false);
		assertTrue(opt[1]==false);
	}

}
