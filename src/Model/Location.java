package Model;

public class Location{

	// args: name 			#location name (label) 
	private String name;
	// args: latitude 		#location latitude
	private String latitude;
	// args: longitude 		#location longitude
	private String longitude;
	// args: altitude 		#location altitude (height)
	// still not used yet
	private String altitude;	
	// args: info 			#location other info
	private String info;

	// Constructor 
	// args: name / args: latitude / args: longitude / args: info
	// #add a location with all needed infomation
	// Enhancement: -- altitude may be added in constructor
	public Location(String name, String latitude, String longitude, String info){
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.info = info;
	}

	// Constructor 
	// args: name / args: latitude / args: longitude
	// #add a location with all basic infomation
	// Enhancement: -- altitude may be added in constructor
	public Location(String name, String latitude, String longitude){
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.info = "";
	}

	// showLoc - Output interface function
	// #show the location itself information in specific format
	public void showLoc(){
		System.out.printf("%25s%15s%15s%20s\n", name, latitude, longitude, info);
	}

	// getName - External interface function
	// #return args:name
	public String getName(){
		return name;
	}

	// getLatitude - External interface function
	// #return args:latitude
	// Improvement: -- add some process to make string to a valid double
	public double getLatitude(){
		return Double.parseDouble(latitude);
	}

	// getLongitude - External interface function
	// #return args:longitude
	// Improvement: -- add some process to make string to a valid double
	public double getLongitude(){
		return  Double.parseDouble(longitude);
	}

	// getInfo - External interface function
	// #return args:info
	public String getInfo(){
		return info;
	}
}