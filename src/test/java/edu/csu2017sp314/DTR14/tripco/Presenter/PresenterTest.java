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
	public void testFiles() throws IOException{
            ArrayList<File> f = new ArrayList<File>();
            try (BufferedWriter b = new BufferedWriter(new FileWriter("test.csv"))) {
                b.write("name,id,Elevation,Estimated Prominence,latitude,longitude,Quadrangle,Range\n");
                b.write("Mount Elbert,1,14433,9093,39.1177,-106.4453,Mount Elbert,Sawatch\n");
                b.write("Mount Massive,2,14421,1961,39.1875,-106.4756,Mount Massive,Sawatch\n");
                b.write("Mount Harvard,3,14420,2360,38.9243,-106.3208,Mount Harvard,Sawatch\n");
                b.write("Blanca Peak,4,14345,5325,37.5774,-105.4857,Blanca Peak,Sangre de Cristo\n");
                b.close();
            }

            f.add(new File("test.csv"));
            pres = new Presenter(f,9999);
            System.out.println(pres.inFiles.get(0).getAbsolutePath());
	}
	
	@Test
	public void testRun() throws IOException{
            ArrayList<File> f = new ArrayList<File>();
            try (BufferedWriter b = new BufferedWriter(new FileWriter("test.csv"))) {
                b.write("name,id,Elevation,Estimated Prominence,latitude,longitude,Quadrangle,Range\n");
                b.write("Mount Elbert,1,14433,9093,39.1177,-106.4453,Mount Elbert,Sawatch\n");
                b.write("Mount Massive,2,14421,1961,39.1875,-106.4756,Mount Massive,Sawatch\n");
                b.write("Mount Harvard,3,14420,2360,38.9243,-106.3208,Mount Harvard,Sawatch\n");
                b.write("Blanca Peak,4,14345,5325,37.5774,-105.4857,Blanca Peak,Sangre de Cristo\n");
                b.close();
            }
		f.add(new File("test.csv"));
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
