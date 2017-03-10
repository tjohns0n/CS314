//DTR-14
//TripCo.java
//main method for TripCo project
//Parses arguments and sends them to presenter
package edu.csu2017sp314.DTR14.tripco;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;

import edu.csu2017sp314.DTR14.tripco.Presenter.*;

public class TripCo{
	//List of input .csv files
	static ArrayList<File> files;
	//Optional argument booleans
	static boolean ID;
	static boolean mileage;
	static boolean name;
	//Std usage message
	private static void usage(){
		System.out.println("TripCo is a trip planning program that creates the shortest trip from a given list of locations");
		System.out.println("TripCo takes an (absolute if not in directory diretly above src) file path to a .csv file of longitude and lattitude coordinates to construct a trip from");
		System.out.println(".csv Location files should have the first line as a template line with labels for subsequent lines' data");
		System.out.println("Optional arguments:");
		System.out.println("	-i : shows the ID of the locations on the map");
		System.out.println("	-m : Display mileage of legs on map");
		System.out.println("	-n : shows the names of the locations on the map");
		System.out.println("EX: TripCo -mn list.csv");
	}
	
	public TripCo(){
		ID=false;
		mileage=false;
		name=false;
		files = new ArrayList<File>();
	}
	
	//To string method
	@Override
	public String toString(){
		return "TripCo is an interactive Colorado trip planning application";
	}
	
	public static void main(String[] args){
		//Need at least one input file
		if(args.length < 1){
			usage();
			return;
		}
		ID=false;
		name=false;
		mileage=false;
		files = new ArrayList<File>();
		//Parse args
		for(int h=0;h<args.length;h++){
			String arg = args[h];
			if(arg.length()>=5){
				if(arg.substring(arg.length()-4, arg.length()).equalsIgnoreCase(".csv")){
					files.add(new File(arg));
					continue;
				}
			}
			//Switch for optional flags, so order don't matter
			if(arg.charAt(0)=='-'){
				if(arg.length()>1){
					int ind = 1;
					while(ind<arg.length()){
						char next = arg.charAt(ind);
						switch(next){
							case 'i': ID = true; break;
							case 'm': mileage= true; break;
							case 'n': name = true; break;
							default:{
								System.out.println("Argument: '" +arg+"' not a recognized argument");
								System.out.println("Argument: '" +arg+"' will be ignored");
								break;
							}
						}
						ind++;
					}
				}
				else{
					System.out.println("Argument: '" +arg+"' not a recognized argument");
					System.out.println("Argument: '" +arg+"' will be ignored");
				}
			}
			else{
				System.out.println("Argument: '" +arg+"' not a recognized argument");
				System.out.println("Argument: '" +arg+"' will be ignored");
			}
		}
		if(ID && name){
			System.out.println("Cannot display both ID's and names of locations");
			System.out.println("Pick one options -i or -n (default)");
			usage();
			return;
		}
		boolean[] opt = {ID, mileage, name};
		//System.out.println(Arrays.toString(opt));
		//Instantiate Presenter, put in running loop to check for needed updates
		Presenter present = new Presenter(files, opt);
		try {
			present.run();
		} catch (URISyntaxException e) {
			e.printStackTrace();
			System.out.println("We tried to get the webpage to launch without the server");
			System.out.println("A bandaid yes, but we tried, and it looks like it didn't work");
			System.out.println("But the XML and svg files should be in the directory with the proper names/data");
		}		
		
		/* Open js webpage with proper port set
		 * Send XML
		 * Presenter.sendFileToClient(out.get(0))
		 * Send SVG
		 * Presenter.sendFileToClient(out.get(1))
		 * Loop for rest/interactions
		 */
	}
}
