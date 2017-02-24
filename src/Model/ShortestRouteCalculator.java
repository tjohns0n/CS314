
// ATTENTION: this class is still in process

package Model;

import java.util.Arrays;

public class ShortestRouteCalculator{

	// args: location list 	
	// # have a reference of location list
	private LocationList locList;
	
	// args: final_route matrix - 2D
	// # the first column store the route
	// # the second column store the accumulated dis
	private	int[][] final_route;
	
	// temp version of route
	// assigned to final_route if better than current
	private int[][] test_route;

	// args: dis_matrix matrix - 2D
	// # store the calculated distance between each city
	private int[][] dis_matrix;

	// args: startIndex
	// # refer to the city where it decide to start
	private int startIndex;

	// args: startIndex
	// # store the final_disance
	private int final_dis;

	// Whether or not to run 2-opt and 3-opt
	private boolean opt2;
	private boolean opt3;

	// Constructor 
	// args: LocationList / args: startIndex
	// # LocationList is the information where the shortestroute algorithm built from
	// # startIndex is the index where the city in the LocationList
	protected ShortestRouteCalculator(LocationList locList, int startIndex){
		this.locList = locList;
		this.startIndex = startIndex;
		// Set final distance to Int.MAX for finding minimum distance later
		final_dis = Integer.MAX_VALUE;
		// Init arrays
		dis_matrix = new int[locList.getsize()][locList.getsize()];
		final_route = new int[locList.getsize() + 1][2];
		test_route = new int[locList.getsize() + 1][2];
		// Populate a distance table of locations
		calculateDistanceTable();
	}

	// Initiation
	// # initiate args and initiate functions
	protected void findBestNearestNeighbor(){
		// temp var for each NN total distance
		int test_dis;
		// start NN at each location
		for (int i = 0; i < locList.getsize(); i++) {
			test_dis = nearestNeighbor(i);
			// Replace final_route with test_route if test_route better
			// TODO: Optimize so copying only takes place once 
			if (test_dis < final_dis) {
				final_dis = test_dis;
				for (int j = 0; j < final_route.length; j++) {
					for (int k = 0; k < final_route[0].length; k++) {
						final_route[j][k] = test_route[j][k];
					}
				}
			}
			
		}
		//showResult();
	}

	// getFinalRoute - External interface function
	// #return args:final_route
	protected int[][] getFinalRoute(){
		return final_route;
	}

	protected void printDisMatrix() {
		System.out.println(locList);
		for (int i = 0; i < dis_matrix.length; i++) {
			System.out.println(Arrays.toString(dis_matrix[i]));
		}
	}

	// getFinalDis - External interface function
	// #return args:final_dis
	protected int getFinalDis(){
		return final_dis;
	}

	// showResult - private function
	private void showResult(){
		for(int i = 0; i < locList.getsize(); i++){
			for(int j = 0 ; j < locList.getsize(); j++)
				System.out.printf("%10d", dis_matrix[i][j]);
			System.out.println();
		}
		for(int i = 0; i < final_route.length; i++)
			System.out.print(final_route[i][0] + (i < final_route.length-1 ? " -> " : ""));
		System.out.println();
		for(int i = 0; i < final_route.length; i++)
			System.out.print(final_route[i][1] + (i < final_route.length-1 ? " -> " : ""));
		System.out.println();
		for(int i = 0; i < final_route.length; i++)
			System.out.print(locList.get(final_route[i][0]).getName() + (i < final_route.length-1 ? " -> " : ""));
		System.out.println("\n" + final_dis);
	}

	// calculator - private function
	// # calculate the shortest path from all the cities in LocationList
	// startIndex - where the NN tour should start
	// Enhancement -- may calculate a specific range of cities
	private int nearestNeighbor(int startIndex){ 
		//boolean vis, check if city was visited
		boolean[] vis = new boolean[locList.getsize()];
		for(int i = 0; i < locList.getsize(); i++) vis[i] = false;
		int final_dis = 0;

		// to set the startIndex as the first city
		int i = startIndex;
		vis[startIndex] = true;
		test_route[0][0] = startIndex;
		test_route[0][1] = 0;
		int cnt = 1;

		// to travel n - 1 cities
		// each time pick the nearest neighbor
		for(int step = 0; step < locList.getsize() - 1; step++){
			int mins = Integer.MAX_VALUE;
			for(int j = 0; j < locList.getsize(); j++){
				if(vis[j] == true) continue;
				if(mins > dis_matrix[i][j]){
					mins = dis_matrix[i][j];
					test_route[cnt][0] = j;
				}
			}
			vis[test_route[cnt][0]] = true;
			final_dis += dis_matrix[i][test_route[cnt][0]];
			test_route[cnt][1] = final_dis;
			i = test_route[cnt++][0];
		}
		test_route[cnt][0] = startIndex;
		final_dis += dis_matrix[test_route[cnt-1][0]][startIndex];
		test_route[cnt][1] = final_dis;
		//System.out.println(final_dis);
		return final_dis;
	}

	protected void run2Opt() {

	}

	protected void run3Opt() {
		
	}

	// calculateDistance - private function
	// # calculate the distrance matrix
	private void calculateDistanceTable(){
		for(int i = 0; i < locList.getsize(); i++)
			for(int j = i; j < locList.getsize(); j++)
				dis_matrix[i][j] = dis_matrix[j][i] = distanceCalculator(locList.get(i), locList.get(j));
	}

	// distanceCalculator - private function
	// # calculate the distance between two locations
	// Prerequirements -- assume that the latitude and longitude has no chars
	private int distanceCalculator(Location l1, Location l2){

		final int R = 3959; // Radius of the earth

		Double latDistance = Math.toRadians(l1.getLatitude() - l2.getLatitude());
	    Double lonDistance = Math.toRadians(l1.getLongitude() - l2.getLongitude());

	    Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	            + Math.cos(Math.toRadians(l1.getLatitude())) * Math.cos(Math.toRadians(l2.getLatitude()))
	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	    Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    double distance = R * c;


	    return (int)Math.ceil(distance);
	}
}