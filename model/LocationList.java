
import java.util.ArrayList;

public class LocationList{

	private 		ArrayList <Location> 	locList;
	private final 	String 					cvsSplitChar = ",";

	public LocationList(){
		locList = new ArrayList <Location>();
	}

	public Location get(int i){
		return locList.get(i);
	}

	public int getsize(){
		return locList.size();
	}

	//show the location list
	public void showLocList(){
		for (int i = 0; i < 75; i++)
			System.out.print("-");
		System.out.printf("\n%25s%15s%15s%20s\n",
				 "name", "latitude", "longitude", "info");
		for(int i = 0; i < locList.size(); i++){
			locList.get(i).showLoc();
		}
		for (int i = 0; i < 75; i++)
			System.out.print("-");
		System.out.println();
	}

	// External interface function
	// to get the Location ArrayList
	public ArrayList <Location> getLocList(){
		return locList;
	}

	// use the first line as a template to extract valid info
	public void lineHandler(String line, String[] title){
		String name 	= "";
		String latitude = "";
		String longitude= "";
		String info 	= "";

		String parts[] = line.split(cvsSplitChar);
		for(int i = 0; i < parts.length; i++){

			if(title[i].toUpperCase().equals("NAME")) 
				name = parts[i];
			else if (title[i].toUpperCase().equals("LATITUDE"))
				latitude = parts[i];
			else if (title[i].toUpperCase().equals("LONGITUDE"))
				longitude = parts[i];
			else if (title[i].toUpperCase().equals("INFO"))
				info = parts[i];
		}

		Location loc = new Location(name, latitude, longitude, info);
		if(checkValid(loc) == true)
			locList.add(loc);
	}

	// to check if the location has previous added
	private boolean checkValid(Location loc){
		boolean flag = true;
		for(int i = 0; i < locList.size(); i++){
			if (locList.get(i).getName().equals(loc.getName()))
				if (locList.get(i).getLatitude() == loc.getLatitude())
					if (locList.get(i).getLongitude() == loc.getLongitude())
						flag = false;
		}
		return flag;
	}

}