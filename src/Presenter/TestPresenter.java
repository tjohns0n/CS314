package Presenter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

public class TestPresenter {
	
	private static Presenter pres; 

	
	//Tests given port number, can fail just because port isn't open
	@Test
	public static void testPort(){
		Assert.assertEquals(9999, pres.getServPort());
	}

	
	
	public static void main(String[] args) {
		File testfile = new File(args[0]);
		ArrayList<File>  fi = new ArrayList<File>();
		fi.add(testfile);
		pres = new Presenter(fi, 9999);
		testPort();
		
	}
}
