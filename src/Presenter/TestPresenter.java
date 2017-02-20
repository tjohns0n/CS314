package Presenter;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestPresenter {
	
	private Presenter pres; 
	
	@BeforeClass
	private void beforeAll(){
		ArrayList<File> f = new ArrayList<File>();
		String pwd = System.getProperty("user.dir");
		System.out.println(pwd);
		String testFile = pwd+"/src/Presenter/Testfile.csv";
		f.add(new File(testFile));
		pres = new Presenter(f,9999);
	}
	@Test
	public void testFiles(){
		System.out.println(pres.inFiles.get(0).getAbsolutePath());
	}
	
	@Test
	public void testRun(){
		try{
			pres.run();
			Assert.assertTrue(true);
		}catch (URISyntaxException e){
			e.printStackTrace();
			System.out.println("URI failure, unable to get working path and this unable launch webpage");
			Assert.fail();
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
	
	
	
	public static void main(String[] args){
		TestPresenter tp = new TestPresenter();
		tp.beforeAll();
		tp.testFiles();
		//tp.testPort();
		tp.testRun();
		System.out.println("here");
		return;
	}
	
}
