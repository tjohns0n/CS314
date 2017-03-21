package edu.csu2017sp314.DTR14.tripco.Presenter;

import org.junit.Before;
import org.junit.Test;

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
	
	@SuppressWarnings("deprecation")
	@Test
	public void TestConStructor() throws FileNotFoundException{
		Assert.assertEquals(new String[0], xmlr.readSelectFile("pom.xml", files));
		Assert.assertEquals(new String[]{"2","4","6","9"}, xmlr.readSelectFile("test.xml", files));
		Assert.assertEquals("ColoradoCountySeats.csv", files.get(0).getName());
	}

}