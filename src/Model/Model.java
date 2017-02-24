package Model;

public class Model{

	// args: *.csv 			
	// # csv filename 
	private CSVReader cvsr;
	
	// args: location list 	
	// # have a reference of location list
	// # a reference of location list used for external interface
	private LocationList locList;

	private ShortestRouteCalculator src;

	// Constructor 
	// args: csvFileName
	// # build a new location list which store the information from csv file
	// # build a new CSVReader which handle the file and auto add location to the list
	// # calculate a shortestneighborroute
	// Improvement: ShortestRouteCalculator is in process and need improvement
	public Model(String csvFileName){
		locList = new LocationList();
		cvsr 	= new CSVReader(csvFileName, locList);
	}

	private void initiate(){
		cvsr.initiate();
		src = new ShortestRouteCalculator(locList, 0);
		src.initiate();
	}

	public void planTrip(){
		initiate();
	}

	public String[][] reteriveTrip(){
		Trip trip = new Trip(locList, src.getFinalRoute());
		return trip.createTrip();
	}

	public static void main(String args[]){
		String filename = args[0];
		Model model = new Model(filename);
		model.initiate();
		model.reteriveTrip();
		
	}
}