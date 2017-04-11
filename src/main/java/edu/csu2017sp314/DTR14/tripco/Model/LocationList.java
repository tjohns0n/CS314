 package edu.csu2017sp314.DTR14.tripco.Model;

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
	protected void lineHandler(String line, String[] titles, String[] selection){
		boolean useSelection = true;
		if (selection.length == 0) useSelection = false;
		String[] extracts = new String[6];
		String parts[] = line.split(cvsSplitRegex);
		for(int i = 0; i < parts.length; i++){
			
			String valid = parts[i].trim();
			String title = titles[i].trim();

			// If column on csv corresponds to name, assign this element as name:
			if (title.toUpperCase().equals("NAME")) 
				extracts[0] = valid;
			// If column on csv corresponds to latitude, assign this element as latitude:
			else if (title.toUpperCase().equals("LATITUDE"))
				extracts[1] = valid;
			// If column on csv corresponds to longitude, assign this element as longitude:
			else if (title.toUpperCase().equals("LONGITUDE"))
				extracts[2] = valid;
			// If column does not correspond to name, lat, or long:
			else {
				// Grab name of ID:
				if (title.toUpperCase().equals("ID"))
					extracts[3] = valid;
				// If not first element of template, add a comma:
				if(extracts[4] != null) extracts[4] += ",";
				// If not first element of extras, add a comma:
				if(extracts[5] != null) extracts[5] += ",";
				// Add element name to title and element contents to extras:
				extracts[4] += title;
				extracts[5] += valid;
			}
		}

		Location loc = new Location(extracts);
		
		// Add the location if it's valid AND 
		// the location is either in the selection or a selection is not being used
		if(checkValid(loc) == true && (!useSelection || Arrays.asList(selection).contains(extracts[3]))){
			locList.add(loc);
		}
		
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

	public boolean addLocation(Location loc) {
		if (checkValid(loc)) return locList.add(loc);
		else return false;
	}

}