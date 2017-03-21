//SelectionWriter.java
//Writes Selection of input to XML

package edu.csu2017sp314.DTR14.tripco.View;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SelectionWriter {
	//Header for <XML> and <selection>
	protected ArrayList<String> header;
	//Holds selection elements
	protected ArrayList<String> body;
    // Contains </selection>
    protected ArrayList<String> footer;
    // The number of legs of the trip
	
	public SelectionWriter(String subSet, String xmlFile, String csvFile){
		header = new ArrayList<String>();
		body = new ArrayList<String>();
        footer = new ArrayList<String>();
        XMLElement xml = new XMLElement("xml", "version=\"1.0\" encoding=\"UTF-8\"");
        header.add(xml.getStart());
        header.add("\n");
        XMLElement slct = new XMLElement("selection", "");
        header.add(slct.getStart());
        header.add("\n");
        footer.add(slct.getEnd());
        footer.add("\n");
        writeBody(subSet, xmlFile, csvFile);
	}
	public SelectionWriter(String subSet, String xmlFile, String csvFile, boolean go){
		header = new ArrayList<String>();
		body = new ArrayList<String>();
        footer = new ArrayList<String>();
        XMLElement xml = new XMLElement("xml", "version=\"1.0\" encoding=\"UTF-8\"");
        header.add(xml.getStart());
        header.add("\n");
        XMLElement slct = new XMLElement("selection", "");
        header.add(slct.getStart());
        header.add("\n");
        footer.add(slct.getEnd());
        footer.add("\n");
        if(go)
        	writeBody(subSet, xmlFile, csvFile);
	}
	//Writes body of Selection XML
	private void writeBody(String subSet, String xmlFile, String csvFile){
		//Add title
		XMLElement title = new XMLElement("title", "");
		body.add("\t");
		body.add(title.getStart());
		body.add(xmlFile);
		body.add(title.getEnd());
		body.add("\n");
		//Add filename
		XMLElement filename = new XMLElement("filename", "");
		body.add("\t");
		body.add(filename.getStart());
		body.add(csvFile);
		body.add(filename.getEnd());
		body.add("\n");
		String[] subs = subSet.split(",");
		if(subs.length>1){
			//Add subset of destinations
			XMLElement dest = new XMLElement("destinations", "");
			XMLElement id = new XMLElement("id", "");
			body.add("\t");
			body.add(dest.getStart());
			body.add("\n");
			//Add individual ids of subset
			for(int i=0;i<subs.length;i++){
				body.add("\t");
				body.add("\t");
				body.add(id.getStart());
				body.add(subs[i]);
				body.add(id.getEnd());
				body.add("\n");
			}
			//Add closing destinations tag
			body.add("\t");
			body.add(dest.getEnd());
			body.add("\n");
		}
	}
	protected boolean writeXML(String name) {
    	//Return value, assume bad return
    	boolean r = false;
    	// Add all elements together
        ArrayList<String> data = new ArrayList<String>();
     	data.addAll(header);
     	data.addAll(body);
     	data.addAll(footer);
     	
      	try {
            //New writer with filename of selection
      		BufferedWriter write = new BufferedWriter(new FileWriter(name));
            //Write each element
            for(String s : data) {
                write.write(s);
            }
            //Close writer
            write.close();
            //We made it, return good
            r = true;
      	} catch (IOException e) {
      		//Failed to write XML, return bad
      		r = false;
      	}
        return r;
    }
}