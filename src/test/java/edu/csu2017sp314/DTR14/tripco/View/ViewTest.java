package edu.csu2017sp314.DTR14.tripco.View;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.csu2017sp314.DTR14.tripco.Presenter.Presenter;

public class ViewTest {
	
	private View view;
	private String test;
	
	@BeforeClass
	public static void createTestFile() throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter("test.svg"));
		bw.write("<?xml version=\"1.0\" ?>\n<svg\nwidth=\"10\"\nheight=\"10\">\n");
		bw.write("<svg width=\"1066.6073\" height=\"783.0824\" xmlns:svg=\"http://www.w3.org/2000/svg\" xmlns=\"http://www.w3.org/2000/svg\">");
		bw.write("</svg>");
		bw.close();
	}
	
	@Test
	public void testConstructor() {
		View v1 = new View("hello", "test.svg", 300, false, false, false);
		assertTrue("rootName not read correctly", v1.getRootName().equals("hello"));

		View v2 = new View("hello.csv", "test.svg", 300, true, true, true);
		assertTrue(".csv extension not properly removed from rootName", v2.getRootName().equals("hello"));

	}
	
	@Test
	public void testGetRootName() {
		View v1 = new View("helloWorld.csv", "test.svg", 300, false, false, false);
		assertTrue("getRootName() method not returning correct root name", v1.getRootName().equals("helloWorld"));
	}


	@Test
	public void testAddLeg() {
		view = new View("helloWorld.csv", "test.svg", 300, true, false, false);
		test = view.addLeg(new double[]{40, -108, 39, -107}, "Not Denver", "id1", "Not CO Springs", "id2", 50);
		assertTrue(test.equals("m"));
		view = new View("helloWorld.csv", "test.svg", 300, true, true, true);
		test = view.addLeg(new double[]{40, -108, 39, -107}, "Denver", "id2", "CO Springs", "id3", 50);
		assertTrue(test.equals("mni"));
	}
	
	@AfterClass() 
	public static void deleteTestFile() {
		File f = new File("test.svg");
		f.delete();
	}

}
