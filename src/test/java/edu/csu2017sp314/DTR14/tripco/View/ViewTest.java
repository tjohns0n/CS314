package edu.csu2017sp314.DTR14.tripco.View;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.io.File;


import org.junit.Test;

import edu.csu2017sp314.DTR14.tripco.Presenter.Presenter;

public class ViewTest {

	private View view;
	private String test;
	
	@Test
	public void testConstructor() {
		View v1 = new View("hello", "coloradoMap.svg", 300, false, false, false);
		assertTrue("rootName not read correctly", v1.getRootName().equals("hello"));

		View v2 = new View("hello.csv", "coloradoMap.svg", 300, true, true, true);
		assertTrue(".csv extension not properly removed from rootName", v2.getRootName().equals("hello"));

	}
	
	@Test
	public void testGetRootName() {
		View v1 = new View("helloWorld.csv", "coloradoMap.svg", 300, false, false, false);
		assertTrue("getRootName() method not returning correct root name", v1.getRootName().equals("helloWorld"));
	}


	@Test
	public void testAddLeg() {
		view = new View("helloWorld.csv", "coloradoMap.svg", 300, true, false, false);
		test = view.addLeg(40, -108, "Not Denver", "id1", 39, -107, "Not CO Springs", "id2", 50);
		assertTrue(test.equals("m"));
		view = new View("helloWorld.csv", "coloradoMap.svg", 300, true, true, true);
		test = view.addLeg(40, -108, "Denver", "id2", 39, -107, "CO Springs", "id3", 50);
		assertTrue(test.equals("mni"));
	}
	

}
