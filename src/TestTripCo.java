import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestTripCo {
	
	
	private static TripCo trip = new TripCo();
	
	@Test
	public static void testToString(){
		Assert.assertEquals("TripCo is an interactive Colorado trip planning application", trip.toString());
	}

	public static void main(String[] args) {
		testToString();
	}
}