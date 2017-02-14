package Model;

public class Model{

	// args: *.csv 			#csv filename 
	private CSVReader cvsr;
	// args: location list 	#have a reference of location list
	//						#a reference of location list used for external interface
	private LocationList loclist;

	// Constructor 
	// args: csvFileName
	// #build a new location list which store the information from csv file
	// #build a new CSVReader which handle the file and auto add location to the list
	// #calculate a shortestneighborroute
	// Improvement: ShortestRouteCalculator is in process and need improvement
	public Model(String csvFileName){
		loclist = new LocationList();
		cvsr 	= new CSVReader(csvFileName, loclist);
		loclist.showLocList();
		ShortestRouteCalculator src = new ShortestRouteCalculator(loclist);
	}

	// getLocList - External interface function
	// #return args:locList
	public LocationList getLocList(){
		return locList;
	}

	public static void main(String args[]){
		String filename = args[0];
		Model cvsr = new Model(filename);
	}
}