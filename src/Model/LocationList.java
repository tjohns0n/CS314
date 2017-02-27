package Model;

import java.util.ArrayList;
import java.util.Arrays;

public class LocationList{

	// args: locList 		
	// # an array list store a list of locations
	private ArrayList <Location> locList;
	
	// args: cvs splitchar 	
	// # a char that used for spliting information in cvs format
	private final String cvsSplitRegex = ",";

	// Constructor 
	// # build a new arraylist reference
	protected LocationList(){
		locList = new ArrayList <Location>();
	}

	// get - External interface function
	// # return args:(class)location
	protected Location get(int i){
		return locList.get(i);
	}

	// getsize - External interface function
	// # return args:list.size()
	protected int getsize(){
		return locList.size();
	}

	// showLocList - Output interface function
	// # show a list of locations information in specific format
	protected void showLocList(){
		for (int i = 0; i < 75; i++)
			System.out.print("-");
		System.out.printf("\n%25s%25s%25s%25s%25s\n",
				 "name", "latitude", "longitude", "extras", "template");
		for(int i = 0; i < locList.size(); i++){
			locList.get(i).showLoc();
		}
		for (int i = 0; i < 75; i++)
			System.out.print("-");
		System.out.println();
	}

	// lineHandler
	// args: line / args: title
	// # accept a string line, which contains all information
	// # accept a string array, which is the template to correspond the information
	// # auto-added the location information read from the line to location list
	// Enhancement: -- the title information may have more want to store in location
	protected void lineHandler(String line, String[] title){
		String name 	= "";
		String latitude = "";
		String longitude= "";
		String extras 	= "";
		String template = "";
		String valid 	= "";
		boolean flag = false;
		int j = 0;
		String parts[] = line.split(cvsSplitRegex);
		for(int i = 0; i < parts.length; i++){
			parts[i] = parts[i].trim();
			title[j] = title[j].trim();
			valid += parts[i];
			if(parts[i].indexOf("\"") == 0 && flag == false){
				flag = true; valid += ", "; continue;
			} if(parts[i].indexOf("\"") == parts[i].length()-1 && flag == true){
				flag = false;
			} if(flag == true){
				valid += ", "; continue; 
			}
			if(title[j].toUpperCase().equals("NAME")) 
				name = valid;
			else if (title[j].toUpperCase().equals("LATITUDE"))
				latitude = valid;
			else if (title[j].toUpperCase().equals("LONGITUDE"))
				longitude = valid;
			else{
				if(template != "") template += ",";
				if(extras != "") extras += ",";
				template += title[j];
				extras += valid;
			}
			valid = "";
			j++;
		}

		Location loc = new Location(name, latitude, longitude, extras, template);
		if(checkValid(loc) == true)
			locList.add(loc);
	}

	// checkValid - private function
	// args: location
	// # to check if location has already stored in location list
	// # accomplished by comparing key value
	// Enhancement: -- may ues object.contain();
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
