package edu.csu2017sp314.DTR14.tripco.Presenter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PresenterTest {
	// TODO add tests
	private Presenter pres; 

        
	@Test
	public void testFiles() throws IOException, URISyntaxException{
            ArrayList<File> f = new ArrayList<File>();

            f.add(new File(this.getClass().getClassLoader().getResource("test.csv").toURI()));
            pres = new Presenter(f,9999);
            System.out.println(pres.inFiles.get(0).getAbsolutePath());
	}
	
	@Test
	public void testRun() throws IOException, URISyntaxException{
            ArrayList<File> f = new ArrayList<File>();
		f.add(new File(this.getClass().getClassLoader().getResource("test.csv").toURI()));
		pres = new Presenter(f,9999);
		try{
			pres.run();
			Assert.assertTrue(true);
		}catch (URISyntaxException e){
			e.printStackTrace();
			System.out.println("URI failure, unable to get working path and this unable launch webpage");
			Assert.fail();
		}catch (UnsupportedOperationException o) {
			System.out.println("Cannot open web browser");
		}
	}
        
	
	//TODO
	//LOOK HERE
	
	//Not sure why, but when running through Junit in eclipse it will fail
	//To initalize
	//But when running the main below (which just calls all the methods Junit would)
	//It runs fine and nothings fails
	//I'm not sure, and you may not care I understand that, 
	//But just wanted to put it out there
	
	
	
	/*public static void main(String[] args){
		PresenterTest tp = new PresenterTest();
		tp.beforeAll();
		tp.testFiles();
		//tp.testPort();
		tp.testRun();
		System.out.println("here");
		return;
	}*/
	
}
