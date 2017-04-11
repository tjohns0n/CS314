package edu.csu2017sp314.DTR14.tripco.Model;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.*;

public class QueryTest {
	
	@Test
	public void testConstructorINIT(){
		Query init = new Query(null);
		assertTrue(init.getMsg().code.equalsIgnoreCase("V-ST-INIT"));
		assertTrue(init.getMsg().content.length==5);
		assertTrue(init.getMsg().content[0].split(",").length==7);
		assertTrue(init.getMsg().content[1].split(",").length==7);
		assertTrue(init.getMsg().content[2].split(",").length==246);
	}
	
	@Test
	public void testConstructorITIN(){
		String[] sub = {"KDFW", "KDEN", "EGLL", "VHHH", "YSSY", "KSFO"};
		Query itin = new Query(sub, null);
		assertTrue(itin.getMsg().code.equalsIgnoreCase("V-ST-ITIN"));
		assertTrue(itin.getMsg().content.length==6);
		assertTrue(itin.getMsg().content[0].split(",").length==12);
	}
	
	@Test
	public void testConstructorSRCH(){
		Query srch = new Query("Dallas", null);
		assertTrue(srch.getMsg().code.equalsIgnoreCase("V-ST-SRCH"));
		assertTrue(srch.getMsg().content.length==6);
		assertTrue(srch.getMsg().content[4].split(",").length==42);
		assertTrue(srch.getMsg().content[5].split(",").length==42);
	}
	
	@Test
	public void testConstructor(){
		Query countries = new Query(null, "Continent-North America");
		assertTrue(countries.getMsg().content.length==5);
		assertTrue(countries.getMsg().content[2].split(",").length==41);
		assertTrue(countries.getMsg().code.equalsIgnoreCase("V-UP-CY"));
		Query regions = new Query(null, "Country-United States");
		assertTrue(regions.getMsg().content.length==5);
		assertTrue(regions.getMsg().content[3].split(",").length==52);
		assertTrue(regions.getMsg().code.equalsIgnoreCase("V-UP-RN"));
		Query ind = new Query(null, "Region-Rhode Island");
		assertTrue(ind.getMsg().content.length==5);
		assertTrue(ind.getMsg().content[4].split(",").length==35);
		assertTrue(ind.getMsg().code.equalsIgnoreCase("V-UP-IL"));
	}
}
