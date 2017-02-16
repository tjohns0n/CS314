
// ATTENTION: this class is still in process

package Model;

public class ShortestRouteCalculator{

	private LocationList locList;
	private	int[] final_route;
	private int[][] dis_matrix;
	private int startIndex;
	private int final_dis;

	public ShortestRouteCalculator(LocationList locList, int startIndex){
		this.locList = locList;
		this.startIndex = startIndex;
		this.dis_matrix = new int[locList.getsize()][locList.getsize()];
		final_route = new int[locList.getsize() + 1];
	}

	public void initiate(){
		calculateDistance();
		for(int i = 0; i < locList.getsize(); i++){
			for(int j = 0; j < locList.getsize(); j++)
				System.out.printf("%10d", dis_matrix[i][j]);
			System.out.println();
		}

		final_dis = calculator();
		showResult();
	}

	public void showResult(){
		for(int i = 0; i < final_route.length; i++)
			System.out.print(final_route[i] + (i < final_route.length-1 ? " -> " : ""));
		System.out.println();
		for(int i = 0; i < final_route.length; i++)
			System.out.print(locList.get(final_route[i]).getName() + (i < final_route.length-1 ? " -> " : ""));
		System.out.println("\n" + final_dis);
	}

	private int calculator(){ 
		//boolean vis, check if city was visited
		boolean[] vis = new boolean[locList.getsize()];
		for(int i = 0; i < locList.getsize(); i++) vis[i] = false;
		int final_dis = 0;
		//initiation stuff


		int i = startIndex;
		vis[startIndex] = true;
		final_route[0] = startIndex;
		int cnt = 1;
		for(int step = 0; step < locList.getsize() - 1; step++){
			int mins = Integer.MAX_VALUE;
			for(int j = 0; j < locList.getsize(); j++){
				if(vis[j] == true) continue;
				if(mins > dis_matrix[i][j]){
					mins = dis_matrix[i][j];
					final_route[cnt] = j;
				}
			}
			vis[final_route[cnt]] = true;
			final_dis += dis_matrix[i][final_route[cnt]];
			i = final_route[cnt++];
		}
		final_route[cnt] = startIndex;
		final_dis += dis_matrix[final_route[cnt-1]][startIndex];
		return final_dis;
	}

	private void calculateDistance(){
		System.out.println("locList.size" + locList.getsize() );
		for(int i = 0; i < locList.getsize(); i++)
			for(int j = i; j < locList.getsize(); j++)
				dis_matrix[i][j] = dis_matrix[j][i] = distanceCalculator(locList.get(i), locList.get(j));
	}

	// prerequirements: assume that the latitude and longitude has no chars
	private int distanceCalculator(Location l1, Location l2){

		final int R = 6371; // Radius of the earth

		Double latDistance = Math.toRadians(l1.getLatitude() - l2.getLatitude());
	    Double lonDistance = Math.toRadians(l1.getLongitude() - l2.getLongitude());

	    Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	            + Math.cos(Math.toRadians(l1.getLatitude())) * Math.cos(Math.toRadians(l2.getLatitude()))
	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	    Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    double distance = R * c * 1000; // convert to meters

	    return (int)distance;
	}
}