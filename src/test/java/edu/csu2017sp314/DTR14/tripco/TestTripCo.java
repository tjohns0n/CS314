package edu.csu2017sp314.DTR14.tripco;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestTripCo {
	
	
	@Test
	public static void testToString(){
		TripCo trip = new TripCo();
		Assert.assertEquals("TripCo is an interactive Colorado trip planning application", trip.toString());
	}

	public static void main(String[] args) {
		testToString();
		System.out.println("here");
		return;
	}
}