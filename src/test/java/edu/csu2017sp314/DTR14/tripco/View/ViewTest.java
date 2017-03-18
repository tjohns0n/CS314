package edu.csu2017sp314.DTR14.tripco.View;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.io.File;


import org.junit.Test;

import edu.csu2017sp314.DTR14.tripco.Presenter.Presenter;

public class ViewTest {
	
	
	@Test
	public void testConstructor() {
		Presenter prez = new Presenter(new ArrayList<File>());
		View v1 = new View(prez, "hello", "coloradoMap.svg", 300, false, false, false);
		assertTrue("rootName not read correctly", v1.rootName.equals("hello"));
		assertTrue("v1 flags do not match expected", v1.mileage == false && v1.ids == false && v1.names == false);
		
		View v2 = new View(prez, "hello.csv", "coloradoMap.svg", 300, true, true, true);
		assertTrue(".csv extension not properly removed from rootName", v2.rootName.equals("hello"));
		assertTrue("v2 flags do not match expected", v2.mileage == true && v2.ids == true && v2.names == true);
		assertTrue(v2.mileage == true);
		assertTrue(v2.names == true);
		assertTrue(v2.ids == true);
		View v3 = new View(prez);
		assertTrue("rootname not empty", v3.rootName.equals(""));
	}
	
	@Test
	public void testGetRootName() {
		Presenter prez = new Presenter(new ArrayList<File>());
		View v1 = new View(prez, "helloWorld.csv", "coloradoMap.svg", 300, false, false, false);
		assertTrue("getRootName() method not returning correct root name", v1.getRootName().equals("helloWorld"));
	}
	
	@Test
	public void testDisplay() {
		Presenter prez = new Presenter(new ArrayList<File>());
		View v1 = new View(prez, "helloWorld.csv", "coloradoMap.svg", 300, false, false, false);
		assertTrue(v1.display("hello").equals("hello"));
	}
	
	// writeFiles() calls methods called in other classes
	// 
	
	@Test
	public void testAddLeg() {
		Presenter prez = new Presenter(new ArrayList<File>());
		View v = new View(prez, "helloWorld.csv", "coloradoMap.svg", 300, true, false, false);
		String test = v.addLeg(40, -108, "Not Denver", "id1", 39, -107, "Not CO Springs", "id2", 50);
		assertTrue(v.legCount == 1);
		assertTrue(test.equals("m"));
	}
	//Test getters for Selection
	@Test
	public void testGetCSV(){
		View v = new View();
		v.ig.select = new Selection("testSelect.xml");
		assertTrue(v.getCSV()==null);
	}
	@Test
	public void testGetCSVName(){
		View v = new View();
		v.ig.select = new Selection("testSelect.xml");
		assertTrue(v.getCSV()==null);
	}
	@Test
	public void testGetSelectFileName(){
		View v = new View();
		v.ig.select = new Selection("testSelect.xml");
		assertTrue(v.getSelectFilename().equalsIgnoreCase("testSelect.xml"));
	}
	@Test
	public void testGetBackSVGName(){
		View v = new View();
		v.ig.select = new Selection("testSelect.xml");
		v.ig.select.setBackSVG("test");
		assertTrue(v.getBackSVGName().equalsIgnoreCase("test"));
	}
	@Test
	public void testGetOptions(){
		View v = new View();
		v.ig.select = new Selection("testSelect.xml");
		boolean[] t = new boolean[] {true, false, true};
		v.ig.select.setOptions(t);
		boolean [] ta = v.getOptions();
		assertTrue(t[0]==ta[0]);
		assertTrue(t[1]==ta[1]);
		assertTrue(t[2]==ta[2]);
	}
	@Test
	public void testGetOpts(){
		View v = new View();
		v.ig.select = new Selection("testSelect.xml");
		boolean[] t = new boolean[] {true, false};
		v.ig.select.setOpt(t);
		boolean [] ta = v.getOpts();
		assertTrue(t[0]==ta[0]);
		assertTrue(t[1]==ta[1]);
	}
	@Test
	public void testGetSubset(){
		View v = new View();
		v.ig.select = new Selection("testSelect.xml");
		String[] t = new String[] {"1", "3"};
		v.ig.select.setSubset(t);
		String [] ta = v.getSubset();
		assertTrue(t[0].equalsIgnoreCase(ta[0]));
		assertTrue(t[1].equalsIgnoreCase(ta[1]));
	}
	

}
