package Model;

public class Location{

	// args: name 			
	// # location name (label) 
	private String name;
	
	// args: latitude 		
	// # location latitude
	private String latitude;
	
	// args: longitude 		
	// # location longitude
	private String longitude;
	
	// args: altitude 		
	// # location altitude (height)
	// still not used yet
	private String altitude;
	
	// args: info 			
	// # location other extras
	private String extras;

	// args: info 			
	// # location other info template
	private String template;

	// args: locReplaceRegex 			
	// # replace unrelated info into other char
	private String locReplaceRegex = "[^0-9WESN\\.\\s-]";

	// args: locSplitRegex 			
	// # location split character
	private String locSplitRegex = ",";

	// Constructor 
	// args: name / args: latitude / args: longitude / args: info
	// # add a location with all needed infomation
	// Enhancement: -- altitude may be added in constructor
	protected Location(String name, String latitude, String longitude, String extras, String template){
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.extras = extras;
		this.template = template;
	}

	// Constructor 
	// args: name / args: latitude / args: longitude
	// # add a location with all basic infomation
	// Enhancement: -- altitude may be added in constructor
	protected Location(String name, String latitude, String longitude){
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.extras = "";
		this.template = "";
	}

	// showLoc - Output interface function
	// # show the location itself information in specific format
	protected void showLoc(){
		System.out.printf("%25s\n%25s\n%25s\n%25s\n%25s\n", name, latitude, longitude, extras, template);
	}

	// getName - External interface function
	// # return args:name
	protected String getName(){
		return name;
	}

	// getLatitude - External interface function
	// # return args:latitude
	// Improvement: -- add some process to make string to a valid double
	protected double getLatitude(){
		return converter(latitude);
	}

	// getLongitude - External interface function
	// # return args:longitude
	// Improvement: -- add some process to make string to a valid double
	protected double getLongitude(){
		return converter(longitude);
		//return  Double.parseDouble(longitude);
	}

	// getInfo - External interface function
	// # return args:info
	protected String getExtras(){
		return extras;
	}

	// getInfo - External interface function
	// # return args:template
	protected String getTemplate(){
		return template;
	}

	// converter -- private function
	// args: string
	// # converte Geographic Coordinates into double value
	private Double converter(String string){
		String temp = string.replaceAll(locReplaceRegex, ",");
		String[] parts = temp.split(locSplitRegex);
		double res = 0;
		for(int i = 0; i < parts.length; i++){
			if (parts[i].trim().equals("N") || parts[i].trim().equals("E")) continue;
			if (parts[i].trim().equals("S") || parts[i].trim().equals("W")) {
				res = -res;
				continue;
			}
			if (i == 0) res += Double.parseDouble(parts[i].trim());
			if (i == 1) res += Double.parseDouble(parts[i].trim())/60.0;
			if (i == 2) res += Double.parseDouble(parts[i].trim())/3600.0;
		}
		return res;
	}
}
