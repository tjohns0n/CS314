package edu.csu2017sp314.DTR14.tripco.Presenter;

import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;

import org.junit.Assert;

import edu.csu2017sp314.DTR14.tripco.Presenter.XMLReader;

public class XMLReaderTest {
	
	private XMLReader xmlr;
	
	@Before
	public void initiate(){
		xmlr = new XMLReader();
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void TestConStructor() throws FileNotFoundException{
		Assert.assertEquals(new String[0], xmlr.readSelectFile("pom.xml"));
		Assert.assertEquals(new String[]{"2","4","6","9"}, xmlr.readSelectFile("test.xml"));
	}

}