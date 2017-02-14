package Presenter;

import java.io.File;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;

public class TestPresenter {
	
	private static Presenter pres = new Presenter(9999);
	private static File testfile;
	
	//Run before any other tests to initialize presenter
	@Test
	public static void initTest(){
		try {
			pres.initiate();
		} catch (IOException e) {
			System.out.println();
			e.printStackTrace();
		}
	}
	
	//Tests given port number, can fail just because port isn't open
	@Test
	public static void testPort(){
		Assert.assertEquals(9999, pres.getPort());
	}
	
	//Tests closing method
	//Since void really just waiting if an exception is thrown or not
	@Test
	public static void testClose(){
		pres.closeAll();
	}
	
	//Tests sendFileToClient() with original byte by byte
	//Require test file to be given and set
	//If IOException thrown obvious fail
	@Test
	public static void testSend(){
		try {
			Assert.assertTrue(pres.sendFileToClient(testfile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		testfile = new File(args[0]);
		initTest();
		testPort();
		testSend();
		testClose();
		
	}
}
