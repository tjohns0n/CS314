//DTR-14
//TripCo.java
//main method for TripCo project
//Parses arguments and sends them to presenter
import java.io.File;
import java.util.ArrayList;

import Presenter.*;

public class TripCo{
	//List of input .csv files
	static ArrayList<File> files;
	//Optional argument booleans
	static boolean ID;
	static boolean mileage;
	static boolean name;
	
	private static void usage(){
		System.out.println("TripCo is a trip planning program that creates the shortest trip from a given list of locations");
		System.out.println("TripCo takes a .csv file of longitude and lattitude coordinates to construct a trip from");
		System.out.println(".csv Location files should have the first line as a template line with labels for subsequent lines' data");
		System.out.println("Optional arguments:");
		System.out.println("	-i : shows the ID of the locations on the map");
		System.out.println("	-m : Display mileage of legs on map");
		System.out.println("	-n : shows the names of the locations on the map (default implied)");
		System.out.println("EX: TripCo -mn list.csv");
	}
	
	public static void main(String[] args){
		//Need at least one input file
		if(args.length < 1){
			usage();
			return;
		}
		for(int h=0;h<args.length;h++){
			String arg = args[h];
			if(arg.substring(arg.length()-4, arg.length()).equals(".csv")){
				files.add(new File(arg));
				continue;
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
		if(!name && !ID){
			name=true;
		}
		boolean[] opt = {ID, mileage, name};
		//Instantiate Presenter, put in running loop to check for needed updates
		Presenter present = new Presenter(files, opt);
		present.run();		
		//Grab port number to feed to js
		int port = present.getServPort();
		//TODO
		//Init webpage here or in presenter?
		
		
		//String jspage = js/file/path
		//String url = "localhost:"+Integer.toString(port)+":"+jspage;
		/* Open js webpage with proper port set
		 * Send XML
		 * Presenter.sendFileToClient(out.get(0))
		 * Send SVG
		 * Presenter.sendFileToClient(out.get(1))
		 * Loop for rest/interactions
		 */
	}

}
