package edu.csu2017sp314.DTR14.tripco;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import org.junit.Assert;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

import edu.csu2017sp314.DTR14.tripco.TripCo;

public class TripCoTest {
	
	private ArrayList<File> files;
    private TripCo tc;
        
    @Before
    public void initObjects() {
        files = new ArrayList<File>();
        files.add(new File("Colorado14ers.csv"));
        tc = new TripCo();
    }

	@Test
	public void testConstructor(){
        // test empty constructor, no side effect
        new TripCo();
        
        // test full constructor
        new TripCo(null, "coloradoMap.svg",
                            false, true, false, false, true, true, files);
	}
        
        @Test
	public void testAddFile(){
        Assert.assertTrue(tc.addFile(new File("ColoradoCountySeats.csv")));
	}
	
	@Test
	public void testToString(){
		Assert.assertEquals("TripCo is an interactive Colorado trip planning application", tc.toString());
	}  
    
    @Test
	public void testInitiate(){
        try {
	        tc.initiate();
	        fail("Expected an FileNotFoundException to be thrown");
	        fail("Expected an Exception to be thrown");
	    } catch (FileNotFoundException anFileNotFoundException) {
	        
	    } catch (Exception anException){
            
        } 
        
	}


	//public static void main(String[] args) {
	//	testToString();
	//	System.out.println("here");
//		return;
//	}
}