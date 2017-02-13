import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import Presenter.Presenter;


public class TripCo{
	//List of input .csv files
	static ArrayList<File> files;
	//List of optional flags
	static ArrayList<String> opt;
	//List of Trips
	//ArrayList<Trip> trips;
	//Vanilla constructor just for files
	static ArrayList<File> out;
	//Parse passed list of option flags and applies them
	private static void parseOpt(){
		if(!opt.isEmpty() && opt!= null){
			for(int i=0; i<opt.size();i++){
				String next=opt.get(i);
				//Switch for grabbing and applying option flags
				//Often will be just assigning a value to certain static variables (debug, multi-trip booleans, etc.)
				switch(next){
					case "":
					default: break;
				}
			}
		}
		
	}
	private static void usage(){
		System.out.println("TripCo is a trip planning program that creates the shortest trip from a given list of locations");
		System.out.println("TripCo Currently only takes (a) .csv file(s) of longitude and lattitude coordinates to construct a trip from");
		System.out.println(".csv Location files should have the first line as a template line with labels for subsequent lines' data");
		System.out.println("EX: TripCo list.csv");
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
			}System.out.println();
			//Switch for args, so order don't matter
			//If an arg matches an option add it to opt so parseOpt can do it's job
			//Else the arg is more vital to operation and should have it's own parser (like file one above)
			//Or the info needed for the arg can be processed here
			switch(arg){
				case "":
				default: break;
			}
		}
		parseOpt();
		//Instantiate and call Model to process input
		//Input file reading and processing loop
		for(int i=0;i<files.size();i++){
			//read = new FileReader(files.get(i));
			//ArrayList<Location> locs = read.ReadFile();
			//tripper = new Model(locs);
			//trips.add(tripper.NearestNeighbor);
		}
		//Handle choosing which file, probably an option for separate trips or all as one
		/* View part of flow
		 * Output file writing loop
		for(int j=0;j<tripper.size();i++){
			write = new XMLWriter(tripper.get(i));
			//Option handler (if) for output name specified
			String outName = "Out"+j;
			out.add(write.writeOut(outName+".xml"));
			mapper = new SVGWriter(tripper.get(i));
			out.add(mapper.draw(outName+".svg"));
		}
		
		*/
		
		//Instantiate Presenter, put in running loop to check for needed updates
		Presenter present = new Presenter();
		try {
			present.initiate();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Grab port number to feed to js
		int port = present.getPort();
		/* Open js webpage with proper port set
		 * Send XML
		 * Presenter.sendFileToClient(out.get(0))
		 * Send SVG
		 * Presenter.sendFileToClient(out.get(1))
		 * Loop for rest/interactions
		 */
	}

}
