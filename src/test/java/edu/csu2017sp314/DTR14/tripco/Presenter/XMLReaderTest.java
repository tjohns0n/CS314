package edu.csu2017sp314.DTR14.tripco.Presenter;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.Assert;

import edu.csu2017sp314.DTR14.tripco.Presenter.XMLReader;

public class XMLReaderTest {
	
	private XMLReader xmlr;
	private ArrayList<File> files;
	
	@Before
	public void initiate(){
		xmlr = new XMLReader();
		files = new ArrayList <File>();
	}
	
	// TODO: fix files
	@Test
	public void TestConStructor() throws FileNotFoundException{
		String[] file = new String[2];
		file[0] = this.getClass().getClassLoader().getResource("testBadXML.xml").toString().substring(5);
		file[1] = this.getClass().getClassLoader().getResource("test.xml").toString().substring(5);
		String testArr[] = xmlr.readSelectFile(file[0], files);
		assertTrue(testArr.length == 0);
		String indexArr[] = {"2", "5"};
		testArr = xmlr.readSelectFile(file[1], files);
		assertTrue(testArr.length == indexArr.length);
		assertTrue(testArr[0].equals(indexArr[0]));
		assertTrue(testArr[1].equals(indexArr[1]));
		assertTrue("test.csv".equals(files.get(0).getName()));
	}

}