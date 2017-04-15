package edu.csu2017sp314.DTR14.tripco.Model;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ShortestRouteCalculatorTest{
	
	private LocationList locList;

	@Test
	public void testConstructor() {
		locList = new LocationList();
		new ShortestRouteCalculator(locList, 0, false);
	}

	@Test
	public void testFindBestNNTour() {
		locList = new LocationList();
		locList.addLocation(new Location("A", "20", "20", "1", "Hello", ""));
		locList.addLocation(new Location("B", "30", "31", "2", "", ""));
		locList.addLocation(new Location("C", "20", "30", "3","", ""));
		
		ShortestRouteCalculator src = new ShortestRouteCalculator(locList, 0, false);
		src.findBestNearestNeighbor(false, false);
		int[][] tests = src.getFinalRoute();
		
		assertTrue("wrong route", tests[0][0] == 0);
		assertTrue("wrong route", tests[1][0] == 2);
		assertTrue("wrong route", tests[2][0] == 1);
		assertTrue("wrong route", tests[3][0] == 0);
	}
	
	@Test
	public void testGetFinalRoute() {
		locList = new LocationList();
		locList.addLocation(new Location("A", "20", "20", "1","Hello", ""));
		locList.addLocation(new Location("B", "30", "31", "2","", ""));
		locList.addLocation(new Location("C", "20", "30", "3","", ""));

		ShortestRouteCalculator src = new ShortestRouteCalculator(locList, 0, false);
		src.findBestNearestNeighbor(false, false);
		int[][] tests = src.getFinalRoute();
		assertTrue(tests.length == 4);
		assertTrue(tests[0].length == 2);
	}

	@Test
	public void testGetFinalDis() {
		locList = new LocationList();
		locList.addLocation(new Location("A", "20", "20", "1","Hello", ""));
		locList.addLocation(new Location("B", "30", "31", "2","", ""));
		locList.addLocation(new Location("C", "20", "30", "3", "", ""));

		ShortestRouteCalculator src = new ShortestRouteCalculator(locList, 0, false);
		src.findBestNearestNeighbor(false, false);
		int[][] tests = src.getFinalRoute();
		assertTrue((int)Math.ceil(tests[tests.length - 1][tests[0].length - 1]) == src.getFinalDis());
	}

	@Test
	public void testFindBest2Opt() {
		locList = new LocationList();
		locList.addLocation(new Location("A", "10", "10", "1","Hello", ""));
		locList.addLocation(new Location("B", "20.1", "20", "1","", ""));
		locList.addLocation(new Location("C", "19.9", "20", "1","", ""));
		locList.addLocation(new Location("D", "30", "30", "1","", ""));
		ShortestRouteCalculator src = new ShortestRouteCalculator(locList, 0, false);
		src.findBestNearestNeighbor(false, false);
		int[][] test1 = src.getFinalRoute();
		ShortestRouteCalculator src2 = new ShortestRouteCalculator(locList, 0, false);
		src2.findBestNearestNeighbor(true, false);
		
		int[][] test2 = src2.getFinalRoute();

		assertTrue(test1[2][1] != test2[2][1]);

	}
	
	@Test
	public void testFind2OptSwapDistance() {
		locList = new LocationList();
		locList.addLocation(new Location("A", "39.1177", "-106.4453"));
		locList.addLocation(new Location("B", "39.1875", "-106.4756"));
		locList.addLocation(new Location("C", "38.9243","-106.3208"));
		locList.addLocation(new Location("D", "37.5774", "-105.4857"));
		locList.addLocation(new Location("E", "39.0294", "-106.4729"));
		ShortestRouteCalculator src = new ShortestRouteCalculator(locList, 0, false);
		
		src.findBestNearestNeighbor(false, false);
		
		src.copyRoute(src.final_route, src.test_route);
		double dis = src.find2OptSwapDistance(1, 3);
		double correctDis = 0;
		
		correctDis += src.dis_matrix[0][4];
		correctDis += src.dis_matrix[4][1];
		correctDis += src.dis_matrix[1][2];
		correctDis += src.dis_matrix[2][3];
		correctDis += src.dis_matrix[3][0];

		assertTrue(dis - correctDis < 0.01 && dis - correctDis > -0.01);
	}
	
	@Test
	public void testDo2OptSwap() {
		locList = new LocationList();
		locList.addLocation(new Location("A", "39.1177", "-106.4453"));
		locList.addLocation(new Location("B", "39.1875", "-106.4756"));
		locList.addLocation(new Location("C", "38.9243","-106.3208"));
		locList.addLocation(new Location("D", "37.5774", "-105.4857"));
		locList.addLocation(new Location("E", "39.0294", "-106.4729"));
		ShortestRouteCalculator src = new ShortestRouteCalculator(locList, 0, false);
		
		src.opt_route = new int[6][2];
		src.test_route = new int[6][2];
		
		for (int i = 0; i < 5; i++) {
			src.test_route[i][0] = i;
		}
		src.test_route[5][0] = 0;
		
		src.do2OptSwap(1, 5);
		assertTrue(src.opt_route[0][0] == 0);
		assertTrue(src.opt_route[1][0] == 4);
		assertTrue(src.opt_route[2][0] == 3);
		assertTrue(src.opt_route[3][0] == 2);
		assertTrue(src.opt_route[4][0] == 1);
		assertTrue(src.opt_route[5][0] == 0);
	}
	
	@Test
	public void testRebuildDistances() {
		locList = new LocationList();
		locList.addLocation(new Location("A", "39.1177", "-106.4453"));
		locList.addLocation(new Location("B", "39.1875", "-106.4756"));
		locList.addLocation(new Location("C", "38.9243","-106.3208"));
		locList.addLocation(new Location("D", "37.5774", "-105.4857"));
		locList.addLocation(new Location("E", "39.0294", "-106.4729"));
		ShortestRouteCalculator src = new ShortestRouteCalculator(locList, 0, false);
		
		src.opt_route = new int[6][2];
		
		for (int i = 0; i < 5; i++) {
			src.opt_route[i][0] = i;
		}
		src.opt_route[5][0] = 0;

	}
	
	@Test
	public void testWorldWrapping() {
		locList = new LocationList();
		locList.addLocation(new Location("A", "39.1177", "-180"));
		locList.addLocation(new Location("B", "39.1177", "180"));
		ShortestRouteCalculator src = new ShortestRouteCalculator(locList, 0, false);
		
		double dis = src.getDistance(0, 1);
		assertTrue(dis < 0.0001 && dis > -0.0001);
	}
}

