package edu.csu2017sp314.DTR14.tripco.Presenter;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.Arrays;

import org.junit.Assert;

import edu.csu2017sp314.DTR14.tripco.Presenter.XMLReader;

public class XMLReaderTest {
	
	private XMLReader xmlr;
	
	@Before
	public void initiate(){
		xmlr = new XMLReader();
	}
	
	@Test
	public void TestConStructor() throws FileNotFoundException{
		assertTrue(xmlr.readSelectFile("pom.xml").length == 0);
		String[] arr = xmlr.readSelectFile(this.getClass().getClassLoader().getResource("test.xml").toString().substring(5));
		assertTrue(arr.length == 4);
		assertTrue(arr[0].equals("2"));
		assertTrue(arr[1].equals("4"));
		assertTrue(arr[2].equals("6"));
		assertTrue(arr[3].equals("9"));
	}

}