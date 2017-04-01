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
	private boolean[] opts;

    @Before
    public void initObjects() {
        files = new ArrayList<File>();
        files.add(new File("Colorado14ers.csv"));
        tc = new TripCo();
        opts = new boolean[]{false, true, false, false, true, true};
    }

	@Test
	public void testConstructor(){
        // test empty constructor, no side effect
        new TripCo();
        
        // test full constructor
        new TripCo("test.xml", null, opts, files);
		new TripCo(null, "coloradoMap.svg", opts, files);
		new TripCo(null, null, opts, files);
	}
        
    @Test
	public void testAddFile(){
    	// test correct file
        Assert.assertTrue(tc.addFile(new File("ColoradoCountySeats.csv")));
        // test wrong file - not csv
        Assert.assertFalse(tc.addFile(new File("coloradoMap.svg")));
        // test wrong file - not exist
        Assert.assertFalse(tc.addFile(new File("coloradoMap.csv")));
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
	        System.out.println("Throwing anFileNotFoundException" );
	        anFileNotFoundException.getStackTrace();
	    } catch (Exception anException){
	    	System.out.println("Throwing anException" );
	    	anException.getStackTrace();
        } 
        
	}
}