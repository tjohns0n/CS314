
// ATTENTION: this class is still in process

package edu.csu2017sp314.DTR14.tripco.Model;

import java.util.Arrays;

public class ShortestRouteCalculator{

	// args: location list 	
	// # have a reference of location list
	private LocationList locList;
	
	// args: final_route matrix - 2D
	// # the first column store the route
	// # the second column store the accumulated dis
	protected int[][] final_route;
	
	// temp version of route
	// assigned to final_route if better than current
	protected int[][] test_route;

	// store single 3-opt routes
	protected int[][] opt_route;

	// args: dis_matrix matrix - 2D
	// # store the calculated distance between each city
	protected double[][] dis_matrix;

	// args: startIndex
	// # refer to the city where it decide to start
	private int startIndex;

	// args: startIndex
	// # store the final_disance
	private double final_dis;
	private double testDistance;
	
	private boolean do2opt, do3opt;


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
	protected void findBestNearestNeighbor(boolean do2opt, boolean do3opt){
		this.do2opt = do2opt;
		this.do3opt = do3opt;
		// temp var for each NN total distance
		double test_dis;
		// start NN at each location
		for (int i = 0; i < locList.getsize(); i++) {
			if(this.startIndex != -1 && i != startIndex) continue;
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
		if (do2opt) {
			final_dis = findBestOpt(do3opt, final_dis);
		}
		return (int)Math.ceil(final_dis);
	}

	/*
	 * findBest2Opt - call run2Opt until 2-opt doesn't find a better route
	 * args:
	 * do3Opt - do 3-opt in addition to 2-opt
	 */
	public double findBestOpt(boolean do3Opt, double originalDistance) {
		// if 3-opt is selected, interleave 2-opt and 3-opt
		// run until no better route is found
		if (do3Opt) {
			// initialize opt_route, which is used by 3-opt code
			opt_route = new int[final_route.length][final_route[0].length];
			while(run2Opt() || run3Opt()) {
				if (testDistance < originalDistance) {
					originalDistance = testDistance;
				}
			}
		// if 3-opt is not selected, only run 2-opt 
		// run until no better route is found
		} else {
			while(run2Opt()) {
				if (testDistance < originalDistance) {
					originalDistance = testDistance;
				}
			}
		}

		return originalDistance;
	}
	
	/*
	 * run2Opt - perform a single 2-opt sweep
	 * returns true if a better route is found, false otherwise
	 */
	protected boolean run2Opt() {
		// 2D loop through route
		// i: first element in swap (inclusive)
		for (int i = 1; i < test_route.length - 1; i++) {
			// j: last element affected by swap (i.e. j - 1 is last element moved in a swap)
			for (int j = i + 2; j < test_route.length; j++) {
				int originalDistance = test_route[test_route.length - 1][test_route[0].length - 1];
				double swapDistance = find2OptSwapDistance(i, j);
				// if the 2-opt gives a shorter distance:
				if ((int)Math.ceil(swapDistance) < originalDistance) {
					// reinit opt_route
					opt_route = new int[final_route.length][final_route[0].length];
					// reverse i to j - 1
					do2OptSwap(i, j);
					// copy this new trip
					copyRoute(opt_route, test_route);
					// return and tell 2-opt to keep running
					testDistance = test_route[test_route.length - 1][1];
					return true;
				}
			}
		}
		return false;
	}
	
	protected double find2OptSwapDistance(int i, int j) {
		// Start with distance of last element not affected by swap
		double distance = test_route[i - 1][1];
		// Add in distances of swapped elements:
			// Add in i - 1 to j - 1 distance
		int loc1 = test_route[i - 1][0];
		int loc2 = test_route[j - 1][0];

		distance += dis_matrix[loc1][loc2];
			// Add in j - 1 to j - 2 to j - 3 to ... i distances
		for (int x = i; x < j - 1; x++) { 
			loc1 = test_route[x][0];
			loc2 = test_route[x + 1][0];

			distance += dis_matrix[loc1][loc2];
		}
		// Add in distances of elements after swap:
			// Add in i to j distance
		loc1 = test_route[i][0];
		loc2 = test_route[j][0];

		distance += dis_matrix[loc1][loc2];
			// Add in j to end of trip distances
		for (int x = j; x < test_route.length - 1; x++) {
			loc1 = test_route[x][0];
			loc2 = test_route[x + 1][0];

			distance += dis_matrix[loc1][loc2];
		}
	
		return distance;
	}
	
	protected void do2OptSwap(int i, int j) {
		// Copy start of route to i - 1
		for (int x = 0; x < i; x++) {
			opt_route[x][0] = test_route[x][0];
		}
		
		// Copy j - 1 to i locations (in reverse order)
		int marker = j - 1;
		int index = i;
		while (marker >= i) {
			opt_route[index][0] = test_route[marker][0];
			marker--;
			index++;
		}
		
		// Copy j to end of trip
		for (int x = j; x < test_route.length; x++) {
			opt_route[x][0] = test_route[x][0];
		}
		
		// Recalculate distances
		rebuildDistances();
	}
	
	protected void rebuildDistances() {
		// Set initial distance to 0
		opt_route[0][1] = 0;
		int loc1;
		int loc2;
		// Loop through trip
		for (int i = 0; i < opt_route.length - 2; i++) {
			// find i and i + 1 in the trip
			loc1 = opt_route[i][0];
			loc2 = opt_route[i + 1][0];
			// calculate the distance between them and add it in to distance
			opt_route[i + 1][1] = opt_route[i][1] + (int)dis_matrix[loc1][loc2];
		}
		// Math.ceil final distance to be consistent with nn's distance calculations
		loc1 = opt_route[opt_route.length - 2][0];
		loc2 = opt_route[opt_route.length - 1][0];
		opt_route[opt_route.length - 1][1] = opt_route[opt_route.length - 2][1] + (int)Math.ceil(dis_matrix[loc1][loc2]); 
	}

	/*
	 * copyRoute - copy an entire route 
	 * args:
	 * newRoute - reference to the route you wish to copy 
	 * oldRoute - reference to the route to be overwritten
	 * TODO: Use System.arraycopy to do a faster copy
	 */
	protected void copyRoute(int[][] newRoute, int[][] oldRoute) {
		for (int i = 0; i < oldRoute.length; i++) {
			oldRoute[i][0] = newRoute[i][0];
			oldRoute[i][1] = newRoute[i][1];
		}
	}

	/*
	 * TODO: run3Opt - perform 3-opt on a nn tour
	 */
	private boolean run3Opt() {
		return false;
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