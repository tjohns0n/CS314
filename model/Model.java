

public class Model{
	private 	CSVReader 		cvsr;
	private 	LocationList 	loclist;

	public Model(String csvFileName){
		loclist = new LocationList();
		cvsr 	= new CSVReader(csvFileName, loclist);
		loclist.showLocList();
		ShortestRouteCalculator src = new ShortestRouteCalculator(loclist);
	}

	public static void main(String args[]){
		String filename = args[0];
		Model cvsr = new Model(filename);
	}
}