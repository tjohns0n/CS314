

public class ShortestRouteCalculator{

	private 	LocationList	loclist;
	private		int[]			final_route;
	private		double 			final_dis;

	public ShortestRouteCalculator(LocationList loclist){
		this.loclist 	= loclist;
		final_route = new int[loclist.getsize() + 1];
		final_dis = Double.MAX_VALUE;
		calculator();
		showResult();
	}

	public void showResult(){
		for(int i = 0; i < final_route.length; i++)
					System.out.print(final_route[i] + " -> ");
				System.out.println();
		for(int i = 0; i < final_route.length; i++)
			System.out.print(loclist.get(final_route[i]).getName() + " -> ");
		System.out.println(final_dis);
	}

	private void calculator(){
		
		boolean[] vis = new boolean[loclist.getsize()];
		for(int i = 0; i < loclist.getsize(); i++)
			vis[i] = false;

		int[] route = new int[loclist.getsize() + 1];
		int[][] mins = new int[loclist.getsize()][loclist.getsize()];
		
		for(int i = 0; i < loclist.getsize(); i++)
			for(int j = 0; j < loclist.getsize(); j++)
				mins[i][j] = Integer.MAX_VALUE;

		vis[0] = true;
		dfs(0, vis, 0, 0, route, mins);
	}

	private void dfs(int index, boolean[] vis, int step, double dis, int[] route, int[][] mins){
		// if(mins[index][step] > dis) 
		// 	mins[index][step] = dis;
		
		// if(dis + mins[index][loclist.getsize()-step] < final_dis)


		route[step] = index;
		if(step == loclist.getsize() - 1){
			//jump out of the loop
			if(final_dis > dis + distanceCalculator(loclist.get(index), loclist.get(0))){
				final_dis = dis + distanceCalculator(loclist.get(index), loclist.get(0));
				route[step+1] = 0;
				final_route = route.clone();	
				// for(int i = 0; i < route.length; i++)
				// 	System.out.print(route[i] + " -> ");
				// System.out.println(dis);
			}
		}
		for(int i = 0; i < loclist.getsize(); i++){
			// if visited, continue;
			if(vis[i] == true) continue;

			double temp_dis = distanceCalculator(loclist.get(index), loclist.get(i));
			// else dfs the next available node
			vis[i] = true;
			dis += temp_dis;

			dfs(i, vis, step+1, dis, route, mins);

			// change to next node
			// reset the vis
			vis[i] = false;
			dis -= temp_dis;
		}
		route[step] = -1;
		return;
	}

	// prerequirements: assume that the latitude and longitude has no chars
	private double distanceCalculator(Location l1, Location l2){

		final int R = 6371; // Radius of the earth

		Double latDistance = Math.toRadians(l1.getLatitude() - l2.getLatitude());
	    Double lonDistance = Math.toRadians(l1.getLongitude() - l2.getLongitude());

	    Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	            + Math.cos(Math.toRadians(l1.getLatitude())) * Math.cos(Math.toRadians(l2.getLatitude()))
	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	    Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    double distance = R * c * 1000; // convert to meters

	    return distance;
	}
}