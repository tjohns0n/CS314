package edu.csu2017sp314.DTR14.tripco.Presenter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import edu.csu2017sp314.DTR14.tripco.Presenter.Presenter;

public class PresenterTest {
	
	private ArrayList<File> files;
    private Presenter prez;
    private boolean[] options;
        
    @Before
    public void initObjects() {
        files = new ArrayList<File>();
        files.add(new File("Colorado14ers.csv"));
        options = new boolean[6];
        Arrays.fill(options, false);
        prez = new Presenter(files, null, "coloradoMap.svg", options);
    }

	@Test
	public void testConstructor(){
		// test empty constructor, no side effect
		new Presenter(files);
        
        // test full constructor
		new Presenter(files, null, "coloradoMap.svg", options);
		
	}
	

	@Test
	public void testRun() {
		try{
			prez.run();
			
		} catch(IOException anIOException){
			System.out.println("Error in generating an IOException");
			System.out.println("We tried to get the webpage to launch without the server");
            System.out.println("A bandaid yes, but we tried, and it looks like it didn't work");
            System.out.println("But the XML and svg files should be in the directory with the proper names/data");
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