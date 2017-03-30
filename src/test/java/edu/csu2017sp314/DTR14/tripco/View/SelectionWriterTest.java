package edu.csu2017sp314.DTR14.tripco.View;

import org.junit.Test;
import static org.junit.Assert.*;

public class SelectionWriterTest {
	
	@Test
	public void testConstructor(){
		SelectionWriter sw = new SelectionWriter(new String[]{"1","2","3","4"}, "test.xml", "test.svg");
		assertFalse(sw.header.isEmpty());
		assertFalse(sw.footer.isEmpty());
		assertFalse(sw.body.isEmpty());
	}

}
