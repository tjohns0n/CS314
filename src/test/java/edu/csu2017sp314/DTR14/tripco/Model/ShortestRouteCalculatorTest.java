package edu.csu2017sp314.DTR14.tripco.Model;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.*;

public class ShortestRouteCalculatorTest{
	
	private LocationList locList;

	@Test
	public void testConstructor() {
		locList = new LocationList();
		ShortestRouteCalculator src = new ShortestRouteCalculator(locList, 0);
	}

	@Test
	public void testFindBestNNTour() {
		locList = new LocationList();
		locList.addLocation(new Location("A", "20", "20", "Hello", ""));
		locList.addLocation(new Location("B", "30", "31", "", ""));
		locList.addLocation(new Location("C", "20", "30", "", ""));
		
		ShortestRouteCalculator src = new ShortestRouteCalculator(locList, 0);
		src.findBestNearestNeighbor();
		int[][] tests = src.getFinalRoute();
		
		assertTrue("wrong route", tests[0][0] == 0);
		assertTrue("wrong route", tests[1][0] == 2);
		assertTrue("wrong route", tests[2][0] == 1);
		assertTrue("wrong route", tests[3][0] == 0);
	}
	
	@Test
	public void testGetFinalRoute() {
		locList = new LocationList();
		locList.addLocation(new Location("A", "20", "20", "Hello", ""));
		locList.addLocation(new Location("B", "30", "31", "", ""));
		locList.addLocation(new Location("C", "20", "30", "", ""));	

		ShortestRouteCalculator src = new ShortestRouteCalculator(locList, 0);
		src.findBestNearestNeighbor();
		int[][] tests = src.getFinalRoute();
		assertTrue(tests.length == 4);
		assertTrue(tests[0].length == 2);
	}

	@Test
	public void testGetFinalDis() {
		locList = new LocationList();
		locList.addLocation(new Location("A", "20", "20", "Hello", ""));
		locList.addLocation(new Location("B", "30", "31", "", ""));
		locList.addLocation(new Location("C", "20", "30", "", ""));	

		ShortestRouteCalculator src = new ShortestRouteCalculator(locList, 0);
		src.findBestNearestNeighbor();
		int[][] tests = src.getFinalRoute();
		assertTrue((int)Math.ceil(tests[tests.length - 1][tests[0].length - 1]) == src.getFinalDis());
	}

	@Test
	public void testFindBest2Opt() {
		locList = new LocationList();
		locList.addLocation(new Location("A", "10", "10", "Hello", ""));
		locList.addLocation(new Location("B", "20.1", "20", "", ""));
		locList.addLocation(new Location("C", "19.9", "20", "", ""));	
		locList.addLocation(new Location("D", "30", "30", "", ""));
		ShortestRouteCalculator src = new ShortestRouteCalculator(locList, 0);
		src.findBestNearestNeighbor();
		int[][] test1 = src.getFinalRoute();
		ShortestRouteCalculator src2 = new ShortestRouteCalculator(locList, 0);
		src2.findBestNearestNeighbor();
		src2.findBestOpt(false);
		
		int[][] test2 = src2.getFinalRoute();

		assertTrue(test1[2][1] != test2[2][1]);

	}
}

