//SelectionWriter.java
//Writes Selection of input to XML

package edu.csu2017sp314.DTR14.tripco.View;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SelectionWriter {
	//Selection to be written
	public Selection select;
	//Header for <XML> and <selection>
	private ArrayList<String> header;
	//Holds selection elements
	private ArrayList<String> body;
    // Contains </selection>
    private ArrayList<String> footer;
    // The number of legs of the trip
	
	public SelectionWriter(Selection Select){
		select=Select;
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
        writeBody();
	}
	//Writes body of Selection XML
	private void writeBody(){
		//Add title
		XMLElement title = new XMLElement("title", "");
		body.add("\t");
		body.add(title.getStart());
		body.add(select.getTitle());
		body.add(title.getEnd());
		body.add("\n");
		//Add filename
		XMLElement filename = new XMLElement("filename", "");
		body.add("\t");
		body.add(filename.getStart());
		body.add(select.getCSVName());
		body.add(filename.getEnd());
		body.add("\n");
		if(select.getSubset().length>1){
			//Add subset of destinations
			XMLElement dest = new XMLElement("destinations", "");
			XMLElement id = new XMLElement("id", "");
			String[] sub = select.getSubset();
			body.add("\t");
			body.add(dest.getStart());
			body.add("\n");
			//Add individual ids of subset
			for(int i=0;i<sub.length;i++){
				body.add("\t");
				body.add("\t");
				body.add(id.getStart());
				body.add(sub[i]);
				body.add(id.getEnd());
				body.add("\n");
			}
			//Add closing destinations tag
			body.add("\t");
			body.add(dest.getEnd());
			body.add("\n");
		}
	}
    protected boolean writeXML(String filename) {
    	//Return value, assume bad return
    	boolean r = false;
    	// Add all elements together
        ArrayList<String> data = new ArrayList<String>();
     	data.addAll(header);
     	data.addAll(body);
     	data.addAll(footer);
     	
      	try {
            //New writer with filename of selection
      		BufferedWriter write = new BufferedWriter(new FileWriter(select.getFilename()));
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
