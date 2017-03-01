
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
	private double[][] dis_matrix;

	// args: startIndex
	// # refer to the city where it decide to start
	private int startIndex;

	// args: startIndex
	// # store the final_disance
	private double final_dis;

	// Constructor 
	// args: LocationList / args: startIndex
	// # LocationList is the information where the shortestroute algorithm built from
	// # startIndex is the index where the city in the LocationList
	protected ShortestRouteCalculator(LocationList locList, int startIndex){
		this.locList = locList;
		this.startIndex = startIndex;
		// Set final distance to Int.MAX for finding minimum distance later
		final_dis = Double.MAX_VALUE;
		// Init arrays
		dis_matrix = new double[locList.getsize()][locList.getsize()];
		final_route = new int[locList.getsize() + 1][2];
		test_route = new int[locList.getsize() + 1][2];
		// Populate a distance table of locations
		calculateDistanceTable();
	}

	// Initiation
	// # initiate args and initiate functions
	protected void findBestNearestNeighbor(){
		// temp var for each NN total distance
		double test_dis;
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
		return (int)Math.ceil(final_dis);
	}

	// showResult - private function
	protected void showResult(){
		for(int i = 0; i < locList.getsize(); i++){
			for(int j = 0 ; j < locList.getsize(); j++)
				System.out.printf("%d ", (int)dis_matrix[i][j]);
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
		double final_dis = 0;

		// to set the startIndex as the first city
		int i = startIndex;
		vis[startIndex] = true;
		test_route[0][0] = startIndex;
		test_route[0][1] = 0;
		int cnt = 1;

		// to travel n - 1 cities
		// each time pick the nearest neighbor
		for(int step = 0; step < locList.getsize() - 1; step++){
			double mins = Double.MAX_VALUE;
			for(int j = 0; j < locList.getsize(); j++){
				if(vis[j] == true) continue;
				if(mins > dis_matrix[i][j]){
					mins = dis_matrix[i][j];
					test_route[cnt][0] = j;
				}
			}
			vis[test_route[cnt][0]] = true;
			final_dis += dis_matrix[i][test_route[cnt][0]];
			test_route[cnt][1] = (int)final_dis;
			i = test_route[cnt++][0];
		}
		test_route[cnt][0] = startIndex;
		final_dis += dis_matrix[test_route[cnt-1][0]][startIndex];
		test_route[cnt][1] = (int)Math.ceil(final_dis);
		//System.out.println(final_dis);
		return (int)Math.ceil(final_dis);
	}

	/*
	 * findBest2Opt - call run2Opt until 2-opt doesn't find a better route
	 */
	public void findBest2Opt() {
		while(run2Opt()) {
			continue;
		}
	}
	
	/*
	 * run2Opt - perform a single 2-opt sweep
	 * returns true if a better route is found, false otherwise
	 */
	protected boolean run2Opt() {
		// Return false if a better route isn't found
		boolean update = false;
		// loop through all values of the calculated nn route except the first and last
		// first location of the trip MUST be the same as the last location, so they should never be swapped
		for (int i = 1; i < final_route.length - 2; i++) {
			// compare every element i to every other element j to see if a swap improves the total distance
			for (int j = i + 1; j < final_route.length - 1; j++) {
				// swap i and j in the route
				int[][] new_route = swap(i, j);
				// get original and new route distances
				int new_dist = new_route[new_route.length - 1][1];
				int old_dist = final_route[new_route.length - 1][1];
				// if there is an improvement in mileage
				if (new_dist < old_dist) {
					// return true so another 2-opt sweep is performed
					update = true;
					// replace the original nn route with the new one
					copyRoute(new_route, final_route);
					// update the final distance
					final_dis = new_dist;
				}
			}
		}
		return update;
	}
	
	/*
	 * swap - swap two elements for 2-opt 
	 * a 2-opt swap is different from simply changing two positions
	 * instead, it swaps the two positions and then reverses the order of every
	 * element in between. This eliminates cross-overs in the route
	 * args:
	 * start - the first element of the swap 
	 * end - the second element of the swap (inclusive)
	 */
	private int[][] swap(int start, int end) {
		// create a new route the same length as the current route
		int[][] new_route = new int[final_route.length][final_route[0].length];
		// copy the original route up to the start value
		for (int i = 0; i < start; i++) {
			new_route[i][0] = final_route[i][0];
			new_route[i][1] = final_route[i][1];
		}
		
		// copy the elements between swap and end (inclusive) backwards
		// also calculates the new cumulative distances 
		int offset = 0;
		int dist = 0;
		for (int i = start; i <= end; i++) {
			new_route[i][0] = final_route[end + offset][0];
			offset--;
			dist = (int)Math.ceil(dis_matrix[new_route[i - 1][0]][new_route[i][0]]);
			new_route[i][1] = new_route[i - 1][1] + dist;
		}
		// copy the remainder of the route, updating the cumulative distance
		for (int i = end + 1; i < new_route.length; i++) {
			new_route[i][0] = final_route[i][0];
			dist = (int)Math.ceil(dis_matrix[new_route[i - 1][0]][new_route[i][0]]);
			new_route[i][1] = new_route[i - 1][1] + dist;
		}
		// return the new route so run2Opt can see if this route is better
		return new_route;
	}

	/*
	 * copyRoute - copy an entire route 
	 * args:
	 * newRoute - reference to the route you wish to copy 
	 * oldRoute - reference to the route to be overwritten
	 * TODO: Use System.arraycopy to do a faster copy
	 */
	private void copyRoute(int[][] newRoute, int[][] oldRoute) {
		for (int i = 0; i < oldRoute.length; i++) {
			oldRoute[i][0] = newRoute[i][0];
			oldRoute[i][1] = newRoute[i][1];
		}
	}

	/*
	 * TODO: run3Opt - perform 3-opt on a nn tour
	 */
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
	private double distanceCalculator(Location l1, Location l2){

		final int R = 3959; // Radius of the earth

		Double latDistance = Math.toRadians(l1.getLatitude() - l2.getLatitude());
	    Double lonDistance = Math.toRadians(l1.getLongitude() - l2.getLongitude());

	    Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	            + Math.cos(Math.toRadians(l1.getLatitude())) * Math.cos(Math.toRadians(l2.getLatitude()))
	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	    Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    double distance = R * c;


	    return distance;
	}
}