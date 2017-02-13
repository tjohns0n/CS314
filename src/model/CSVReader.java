

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CSVReader{

	private 		String 				csvFileName;
	private 		BufferedReader 		br;
	private 		LocationList		loclist;
	private final 	String 				cvsSplitChar = ",";


	// Constructor with csvFileName and loclist
	// csvFileName 
	// loclist 
	public CSVReader(String csvFileName, LocationList loclist){
		this.csvFileName= csvFileName;
		this.loclist 	= loclist;
		csvHandler();
	}

	//deal with csv file and extract valid info from each line
	private void csvHandler(){
		try{
			// open a buffer reader and check the first line
			br = new BufferedReader(new FileReader(csvFileName));
			String line = "";
			if ((line = br.readLine()) == null){
				System.out.println("Empty File!");
				return;
			}
			// use the first line as a template to extract valid info
			String title[] = line.split(cvsSplitChar);
			
			// handle each line
			// auto-add the location to the list
			while((line = br.readLine()) != null){
				loclist.lineHandler(line, title);
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

