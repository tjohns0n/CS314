//DTR-14
//TripCo.java
//main method for TripCo project
//Parses arguments and sends them to presenter
package edu.csu2017sp314.DTR14.tripco;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import edu.csu2017sp314.DTR14.tripco.Presenter.Presenter;

public class TripCo{
	//List of input .csv files
	private ArrayList<File> files;
	//Optional argument booleans
	private String _xml;			//.xml
	private String _svg;			//.svg
	private boolean _gui;		//-g
	private boolean _id;		//-i
	private boolean _mileage;	//-m
	private boolean _name;		//-n
	private boolean _2opt;		//-2
	private boolean _3opt;		//-3
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
		_xml = "";
		_svg = "";
		_gui = false;
		_id = false;
		_mileage = false;
		_name = false;
		_2opt = false;
		_3opt = false;
		files = new ArrayList<File>();
	}
	
	//	boolean 
	//	[1] = _gui, [2] = _id, [3] = _mileage, 
	//	[4] = _name, [5] =  _2opt, [6] = boolean _3opt,
	public TripCo(String _xml, String _svg, boolean[] opts, ArrayList<File> files){
		this._xml = _xml;
		this._svg = _svg;
		this._gui = opts[0];
		this._id = opts[1];
		this._mileage = opts[2];
		this._name = opts[3];
		this._2opt = opts[4];
		this._3opt = opts[5];
		this.files = files;
	}

	//To string method
	@Override
	public String toString(){
		return "TripCo is an interactive Colorado trip planning application";
	}

	public synchronized boolean addFile(File file){
		if(file.exists() && file.getName().contains("csv")) return files.add(file);
		else return false;
	}

	public void initiate() throws FileNotFoundException, Exception{
		boolean[] opt = {_id, _mileage, _name, _2opt, _3opt, _gui};
		//Instantiate Presenter, put in running loop to check for needed updates
		Presenter present = new Presenter(files, _xml, _svg, opt);
        
		present.run();
		
		/* Open js webpage with proper port set
		 * Send XML
		 * Presenter.sendFileToClient(out.get(0))
		 * Send SVG
		 * Presenter.sendFileToClient(out.get(1))
		 * Loop for rest/interactions
		 */
	}
	
	public static void main(String[] args) throws FileNotFoundException, InterruptedException, Exception{

		String _xml = "";
		String _svg = "";
		boolean[] opts = new boolean[7];
		ArrayList<File> files = new ArrayList<File>();

		//Need at least one input file
		if(args.length < 1){
			usage(); return;
		}
		//Parse args
		for(int h = 0; h < args.length; h++){
			
			String arg = args[h];
			
			if(arg.length()>=5){
				if(arg.substring(arg.length()-4, arg.length()).equalsIgnoreCase(".csv"))
					files.add(new File(arg));
				else if(arg.substring(arg.length()-4, arg.length()).equalsIgnoreCase(".xml"))
					_xml += arg;
				else if(arg.substring(arg.length()-4, arg.length()).equalsIgnoreCase(".svg"))
					_svg += arg;
			} else if(arg.length() == 2 && arg.charAt(0) == '-'){
				//Switch for optional flags, so order don't matter
				switch(arg.charAt(1)){
					case 'g': opts[0] = true; break;
					case 'i': opts[1] = true; break;
					case 'm': opts[2] = true; break;
					case 'n': opts[3] = true; break;
					case '2': opts[4] = true; break;
					case '3': opts[5] = true; break;
					case 'k': opts[6] = true; break;
					default:{
						System.out.println("Argument: '" +arg+"' not a recognized argument");
						System.out.println("Argument: '" +arg+"' will be ignored");
						break;
					}
				}
			}else {
				System.out.println("Argument: '" +arg+"' not a recognized argument");
				System.out.println("Argument: '" +arg+"' will be ignored");
			}
		}
		if(opts[1] && opts[3]){
			System.out.println("Cannot display both ID's and names of locations");
			System.out.println("Pick one options -i or -n (default)");
			usage();
			return;
		}
		
		TripCo tc = new TripCo(_xml, _svg, opts, files);
		tc.initiate();
	}
}