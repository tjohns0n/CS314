package edu.csu2017sp314.DTR14.tripco.Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CSVReader{

	// args: *.csv 			
	// # csv filename 
	private String csvFileName;
	
	// args: buffer reader 	
	// # read buffer from a file and return a line string
	private BufferedReader br;
	
	// args: location list 	
	// # have a reference of location list
	// # send line string to location list function for further process
	private LocationList loclist;
	
	// args: cvs splitchar 	
	// # a char that used for spliting information in cvs format
	private final String cvsSplitChar = ",";


	// Constructor 
	// args: csvFileName / args: LocationList
	// # accpet filename and send string to location list
	// Enhancement: -- location list can be a local args
	protected CSVReader(String csvFileName, LocationList loclist){
		this.csvFileName= csvFileName;
		this.loclist 	= loclist;
	}

	protected void initiate(String[] selection){
		csvHandler(selection);
	}

	// csvHandler - private function
	// # deal with csv file and extract valid info from each line
	private void csvHandler(String[] selection){
		try{
			// open a buffer reader and check the first line
			br = new BufferedReader(new FileReader(csvFileName));
			String line = "";
			if ((line = br.readLine()) == null){
				System.out.println("Empty File!");
				return;
			}
			//line = line.replaceAll("\\s+", "");
			// use the first line as a template to extract valid info
			String title[] = line.split(cvsSplitChar);
			// handle each line
			// auto-add the location to the list
			while((line = br.readLine()) != null){
				//line = line.replaceAll("\\s+", "");
				loclist.lineHandler(line, title, selection);
			} 
		}catch (FileNotFoundException e) {
	        e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	}

}
