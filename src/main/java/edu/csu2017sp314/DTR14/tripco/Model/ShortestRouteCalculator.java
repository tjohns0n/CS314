
// ATTENTION: this class is still in process

package edu.csu2017sp314.DTR14.tripco.Model;

import java.util.Arrays;

public class ShortestRouteCalculator{

	boolean debug = true;
	
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
	protected int[][] dis_matrix;

	// args: startIndex
	// # refer to the city where it decide to start
	private int startIndex;

	// args: startIndex
	// # store the final_disance
	private int final_dis;
	private int testDistance;
	
	private boolean do2opt, do3opt, KM;


	// Constructor 
	// args: LocationList / args: startIndex
	// # LocationList is the information where the shortestroute algorithm built from
	// # startIndex is the index where the city in the LocationList
	protected ShortestRouteCalculator(LocationList locList, int startIndex, boolean km){
		this.locList = locList;
		this.startIndex = startIndex;
		KM=km;
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
	protected void findBestNearestNeighbor(boolean do2opt, boolean do3opt){
		this.do2opt = do2opt;
		this.do3opt = do3opt;
		// temp var for each NN total distance
		int test_dis;
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
		return final_dis;
	}

	// showResult - private function
	protected void showResult(){
		for(int i = 0; i < locList.getsize(); i++){
			for(int j = 0 ; j < locList.getsize(); j++)
				System.out.printf("%d ", dis_matrix[i][j]);
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
			test_route[cnt][1] = final_dis;
			i = test_route[cnt++][0];
		}
		test_route[cnt][0] = startIndex;
		final_dis += dis_matrix[test_route[cnt-1][0]][startIndex];
		test_route[cnt][1] = final_dis;
		if (do2opt) {
			final_dis = findBestOpt(do3opt, final_dis);
		}
		System.out.println("return");
		return final_dis;
	}

	/*
	 * findBest2Opt - call run2Opt until 2-opt doesn't find a better route
	 * args:
	 * do3Opt - do 3-opt in addition to 2-opt
	 */
	public int findBestOpt(boolean do3Opt, int originalDistance) {
		// if 3-opt is selected, interleave 2-opt and 3-opt
		// run until no better route is found
		if (do3Opt) {
			// initialize opt_route, which is used by 3-opt code
			opt_route = new int[final_route.length][final_route[0].length];
			while(run2Opt()) {
				if (testDistance < originalDistance) {
					//System.out.println("orig: " + originalDistance + " test: " + testDistance);
					originalDistance = testDistance;
					for (int i = 0; i < test_route.length; i++) {
						//System.out.println("loc: " + test_route[i][0] + " dis: " + test_route[i][1]);
					}
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
				int swapDistance = find2OptSwapDistance(i, j);
				// if the 2-opt gives a shorter distance:
				if (swapDistance < originalDistance) {
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
	
	protected int find2OptSwapDistance(int i, int j) {
		// Start with distance of last element not affected by swap
		int distance = test_route[i - 1][1];
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
		rebuildDistances(opt_route);
	}
	
	protected void rebuildDistances(int[][] route) {
		// Set initial distance to 0
		route[0][1] = 0;
		int loc1;
		int loc2;
		// Loop through trip
		for (int i = 0; i < route.length - 2; i++) {
			// find i and i + 1 in the trip
			loc1 = route[i][0];
			loc2 = route[i + 1][0];
			// calculate the distance between them and add it in to distance
			route[i + 1][1] = route[i][1] + dis_matrix[loc1][loc2];
		}
		// Math.ceil final distance to be consistent with nn's distance calculations
		loc1 = route[route.length - 2][0];
		loc2 = route[route.length - 1][0];
		route[route.length - 1][1] = route[route.length - 2][1] + dis_matrix[loc1][loc2]; 
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
		for (int i = 0; i < final_route.length - 5; i++) {
			for (int j = i + 2; j < final_route.length - 3; j++) {
				for (int k = j + 2; k < final_route.length - 1; k++) {
					// if an improvement is found, return to findBestOpt and start 3-opt again
					if (find3OptImprovement(i, j, k)) {
						opt_route = new int[final_route.length][final_route[0].length];
						return true;
					}
				}
			}
		}
		return false;
	}

	
	protected boolean find3OptImprovement(int i, int j, int k) {
		// find which of the 4 possible 3-opt swaps is best, if any
		int swapType = findBest3OptSwap(i, j, k);
		// if no swap improves on current route, return to run3Opt and try new i,j,k combination
		if (swapType == 0) {
			return false;
		} else {
			// otherwise, do a 3-opt swap
			do3OptSwap(i, j, k, swapType);
			// rebuild the test_route mileage array
			rebuildDistances(test_route);
			// set distance for comparison later
			testDistance = test_route[test_route.length - 1][1];
			return true;
		}
	}
	
	// find the best of the four swaps
	// return 0 if no swap is better
	// return 1 if i -> j -> i + 1 -> k -> j + 1 -> k + 1
	// return 2 if i -> j + 1 -> k -> i + 1 -> j -> k + 1
	// return 3 if i -> j + 1 -> k -> j -> i + 1 -> k + 1
	// return 4 if i -> k -> j + 1 -> i + 1 -> j -> k + 1
	protected int findBest3OptSwap(int i, int j, int k) {
		int bestSwap = 0;
		int test;
		int compare = findCompare(i, k + 1);
		
		if ((test = checkPossibility1(i, j, k)) < compare) {
			System.out.println("com: " + compare + "test: " + test);
			compare = test;
			bestSwap = 1;
		}
		if ((test = checkPossibility2(i, j, k)) < compare) {
			compare = test;
			bestSwap = 2;
		}
		if ((test = checkPossibility3(i, j, k)) < compare) {
			compare = test;
			bestSwap = 3;
		}
		if ((test = checkPossibility4(i, j, k)) < compare) {
			compare = test;
			bestSwap = 4;
		}
		
		return bestSwap;
	}
	
	// calculate the cumulative distance between two locations on the trip
	protected int findCompare(int start, int end) {
		return test_route[end][1] - test_route[start][1];
	}
	
	protected int checkPossibility1(int i, int j, int k) {
		int distance = 0;
		int loc1, loc2;
		// i to j distance
		loc1 = test_route[i][0]; loc2 = test_route[j][0];
		distance += getDistance(loc1, loc2);
		// j through i + 1 distance
		distance += findCompare(i + 1, j);
		// i + 1 to k distance
		loc1 = test_route[i + 1][0]; loc2 = test_route[k][0];
		distance += getDistance(loc1, loc2);
		// k through j + 1 distance
		distance += findCompare(j + 1, k);
		// j + 1 to k + 1 distance
		loc1 = test_route[j + 1][0]; loc2 = test_route[k + 1][0];
		distance += getDistance(loc1, loc2);
		return distance;
	}
	
	protected int checkPossibility2(int i, int j, int k) {
		int distance = 0;
		int loc1, loc2;
		// i to j + 1 distance
		loc1 = test_route[i][0]; loc2 = test_route[j + 1][0];
		distance += getDistance(loc1, loc2);
		// j + 1 through k distance
		distance += findCompare(j + 1, k);
		// k to i + 1 distance
		loc1 = test_route[k][0]; loc2 = test_route[i + 1][0];
		distance += getDistance(loc1, loc2);
		// i + 1 through j distance
		distance += findCompare(i + 1, j);
		// j to k + 1 distance
		loc1 = test_route[j][0]; loc2 = test_route[k + 1][0];
		distance += getDistance(loc1, loc2);
		return distance;
	}
	
	protected int checkPossibility3(int i, int j, int k) {
		int distance = 0;
		int loc1, loc2;
		// i to j + 1 distance
		loc1 = test_route[i][0]; loc2 = test_route[j + 1][0];
		distance += getDistance(loc1, loc2);
		// j + 1 through k distance
		distance += findCompare(j + 1, k);
		// k to j distance
		loc1 = test_route[k][0]; loc2 = test_route[j][0];
		distance += getDistance(loc1, loc2);
		// j through i + 1 distance
		distance += findCompare(i + 1, j);
		// i + 1 to k + 1 distance
		loc1 = test_route[i + 1][0]; loc2 = test_route[k + 1][0];
		distance += getDistance(loc1, loc2);
		return distance;
	}
	
	protected int checkPossibility4(int i, int j, int k) {
		int distance = 0;
		int loc1, loc2;
		// i to k distance
		loc1 = test_route[i][0]; loc2 = test_route[k][0];
		distance += getDistance(loc1, loc2);
		// k through j + 1 distance
		distance += findCompare(j + 1, k);
		// j + 1 to i + 1 distance
		loc1 = test_route[j + 1][0]; loc2 = test_route[i + 1][0];
		distance += getDistance(loc1, loc2);
		// i + 1 to j distance
		distance += findCompare(i + 1, j);
		// j to k distance
		loc1 = test_route[j][0]; loc2 = test_route[k + 1][0];
		distance += getDistance(loc1, loc2);
		return distance;
	}
	
	// rearrange test_route to follow the swap routine
	protected void do3OptSwap(int i, int j, int k, int swapType) {
		System.out.println("swap: " + swapType + " i " + i + " j " + j + " k " + k);
		// counter to keep track of where in array to write
		int counter = i;
		// copy test_route to opt_route so there is a backup
		copyRoute(test_route, opt_route);
		if (swapType == 1) {
			copyElements(i + 1, ++counter, (j - (i + 1)) + 1, true);
			counter += (j - (i + 1)) + 1;
			copyElements(j + 1, counter, (k - (j + 1)) + 1, true);
			counter += (k - (j)) + 1;
			copyElements(k + 1, counter, test_route.length - k - 1, false);
		}
		if (swapType == 2) {
			copyElements(j + 1, ++counter, (k - (j + 1)) + 1, false);
			counter += (k - (j + 1)) + 1;
			copyElements(i + 1, counter, (j - (i + 1)) + 1, false);
			counter += (j - (i + 1)) + 1;
			copyElements(k + 1, counter, test_route.length - k - 1, false);
		}
		if (swapType == 3) {
			copyElements(j + 1, ++counter, (k - (j + 1)) + 1, false);
			counter += (k - (j + 1)) + 1;
			copyElements(i + 1, counter, (j - (i + 1)) + 1, true);
			counter += (j - (i + 1)) + 1;
			copyElements(k + 1, counter, test_route.length - k - 1, false);
		}
		if (swapType == 4) {
			
			copyElements(j + 1, ++counter, (k - (j + 1)) + 1, true);
			counter += (k - (j + 1)) + 1;
			copyElements(i + 1, counter, (j - (i + 1)) + 1, false);
			counter = (j - (i + 1)) + 1;
			copyElements(k + 1, counter, test_route.length - k - 1, false);
		}
		
	}
	
	// copy elements of opt_route[sourceIndex] through opt_route [sourceIndex + length] to test_route[destIndex] through test_route[destIndex + length]
	// if reverse is true, the elements will be copied from sourceIndex + length to sourceIndex, reversing their order in test_route
	protected void copyElements(int sourceIndex, int destIndex, int length, boolean reverse) {
		System.out.println("src: " + sourceIndex + " dest: " + destIndex + " len: " + length + " route: " + test_route.length);
		int offset = 0;
		if (reverse) {
			offset = length - 1;
		}
		for (int i = 0; i < length; i++) {
			test_route[destIndex + i][0] = opt_route[sourceIndex + i + offset][0];
			if (reverse) {
				offset -= 2;
			}
		}
	}
	
	// calculateDistance - private function
	// # calculate the distrance matrix
	private void calculateDistanceTable(){
		for(int i = 0; i < locList.getsize(); i++)
			for(int j = i; j < locList.getsize(); j++)
				dis_matrix[i][j] = dis_matrix[j][i] = distanceCalculator(locList.get(i), locList.get(j));
	}
	
	protected int getDistance(int loc1, int loc2) {
		return dis_matrix[loc1][loc2];
	}

	// distanceCalculator - private function
	// # calculate the distance between two locations
	// Prerequirements -- assume that the latitude and longitude has no chars
	private int distanceCalculator(Location l1, Location l2){
		int R;
		if(KM){
			R = 6371; // Radius of the earth in kilometers
		}
		else{
			R = 3959; // Radius of the earth in miles
		}

		Double latDistance = Math.toRadians(l1.getLatitude() - l2.getLatitude());
	    Double lonDistance = Math.toRadians(l1.getLongitude() - l2.getLongitude());

	    Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	            + Math.cos(Math.toRadians(l1.getLatitude())) * Math.cos(Math.toRadians(l2.getLatitude()))
	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	    Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    int distance = (int)Math.round(R * c);


	    return distance;
	}
}
