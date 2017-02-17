package Model;

public class Trip{

	// args: locList 		
	// # an array list store a list of locations
	private LocationList locList;

	// args: route matrix - 2D
	// # the first column store the route
	// # the second column store the accumulated dis
	private int[][] route;

	public Trip(LocationList locList, int[][] route){
		this.locList = locList;
		this.route = route;
	}

	protected String[] createTrip(){
		String[] strings = new String[route.length];
		int cnt = 0;
		for(int i = 0; i < route.length; i++){
			String news = "";
			news += locList.get(route[i][0]).getName() + ",";
			news += Double.toString(locList.get(route[i][0]).getLatitude()) + ",";
			news += Double.toString(locList.get(route[i][0]).getLongitude()) + ",";
			news += Integer.toString(route[i][1]);
			strings[cnt++] = news;
		}
		for(int i = 0; i < strings.length; i++){
			System.out.println(strings[i].toString());
		}
		return strings;
	}

}