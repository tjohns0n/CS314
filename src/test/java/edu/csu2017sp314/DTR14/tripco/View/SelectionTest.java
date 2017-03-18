package edu.csu2017sp314.DTR14.tripco.View;

import org.junit.Test;
import static org.junit.Assert.*;



public class SelectionTest {
	
	@Test
	public void testConstuctor(){
		Selection s = new Selection("testSelect.xml");
		assertTrue(s.getBackSVGName().equals(""));
		assertTrue(s.getSubset().length==0);
		boolean[] optn = s.getOptions();
		assertTrue(optn[0]==false);
		assertTrue(optn[1]==false);
		assertTrue(optn[2]==false);
		boolean[] opt = s.getOpts();
		assertTrue(opt[0]==false);
		assertTrue(opt[1]==false);
	}
	
	@Test
	public void testSetBackSVG(){
		Selection s = new Selection("testSelect.xml");
		s.setBackSVG("test");
		assertTrue(s.getBackSVGName().equalsIgnoreCase("test"));
	}
	
	@Test
	public void testSetTitle(){
		Selection s = new Selection("testSelect.xml");
		s.setTitle("here");
		assertTrue(s.getTitle().equals("here"));
	}
	
	@Test
	public void testSetSelectName(){
		Selection s = new Selection("testSelect.xml");
		s.setSelectName("there");
		assertTrue(s.getFilename().equals("there"));
	}
	
	@Test
	public void testSetOpt(){
		Selection s = new Selection("testSelect.xml");
		boolean[] t = new boolean[] {true, false};
		s.setOpt(t);
		assertTrue(s.getOpts()[0]==t[0]);
		assertTrue(s.getOpts()[1]==t[1]);
	}
	
	@Test
	public void testSet2Opt(){
		Selection s = new Selection("testSelect.xml");
		s.set2Opt(true);
		assertTrue(s.getOpts()[0]);
	}
	
	@Test
	public void testSet3Opt(){
		Selection s = new Selection("testSelect.xml");
		s.set3Opt(false);
		assertFalse(s.getOpts()[1]);
	}
	
	@Test
	public void testSetOptions(){
		Selection s = new Selection("testSelect.xml");
		boolean[] t = new boolean[] {true, false, true};
		s.setOptions(t);
		assertTrue(s.getOptions()[0]==t[0]);
		assertTrue(s.getOptions()[1]==t[1]);
		assertTrue(s.getOptions()[2]==t[2]);
	}
	
	@Test
	public void testSetIDOption(){
		Selection s = new Selection("testSelect.xml");
		s.setIDOption(true);
		assertTrue(s.getOptions()[0]);
	}
	
	@Test
	public void testSetMileageOption(){
		Selection s = new Selection("testSelect.xml");
		s.setMileageOption(true);
		assertTrue(s.getOptions()[1]);	
	}
	
	@Test
	public void testSetNameOption(){
		Selection s = new Selection("testSelect.xml");
		s.setNameOption(true);
		assertTrue(s.getOptions()[2]);
	}
	
	@Test
	public void testSetSubset(){
		Selection s = new Selection("testSelect.xml");
		String[] t = new String[] {"here", "there", "Everywhere"};
		s.setSubset(t);
		String[] ta = s.getSubset();
		assertTrue(ta[0].equals("here"));
		assertTrue(ta[1].equals("there"));
		assertTrue(ta[2].equals("Everywhere"));
	}
	
	@Test
	public void testGetFilename(){
		Selection s = new Selection("testSelect.xml");
		s.setSelectName("blah");;
		assertTrue(s.getFilename().equals("blah"));
	}
	
	@Test
	public void testGetTitle(){
		Selection s = new Selection("testSelect.xml");
		s.setTitle("bleh");
		assertTrue(s.getTitle().equals("bleh"));
	}
	
	@Test
	public void testGetBackSVGName(){
		Selection s = new Selection("testSelect.xml");
		s.setBackSVG("this.svg");
		assertTrue(s.getBackSVGName().equals("this.svg"));
	}
	
	@Test
	public void testGetOptions(){
		Selection s = new Selection("testSelect.xml");
		boolean[] t = new boolean[] {true, true, false};
		s.setOptions(t);
		boolean[] ta = s.getOptions();
		assertTrue(ta[0]==t[0]);
		assertTrue(ta[1]==t[1]);
		assertTrue(ta[2]==t[2]);
	}
	
	@Test
	public void testGetOpts(){
		Selection s = new Selection("testSelect.xml");
		boolean[] t = new boolean[] {true, false};
		s.setOpt(t);
		boolean[] ta = s.getOpts();
		assertTrue(ta[0]==t[0]);
		assertTrue(ta[1]==t[1]);
	}
	
	@Test
	public void testGetSubset(){
		Selection s = new Selection("testSelect.xml");
		String[] t = new String[] {"blah", "bleh", "blih"};
		s.setSubset(t);
		String[] ta = s.getSubset();
		assertTrue(ta[0].equals(t[0]));
		assertTrue(ta[1].equals(t[1]));
		assertTrue(ta[2].equals(t[2]));
	}
}
