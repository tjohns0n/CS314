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
	// # location other info
	private String info;

	// args: info 			
	// # location other info template
	private String template;

	// Constructor 
	// args: name / args: latitude / args: longitude / args: info
	// # add a location with all needed infomation
	// Enhancement: -- altitude may be added in constructor
	protected Location(String name, String latitude, String longitude, String info, String template){
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.info = info;
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
		this.info = "";
		this.template = "";
	}

	// showLoc - Output interface function
	// # show the location itself information in specific format
	protected void showLoc(){
		System.out.printf("%25s\n%25s\n%25s\n%25s\n%25s\n", name, latitude, longitude, info, template);
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
		return Double.parseDouble(latitude);
	}

	// getLongitude - External interface function
	// # return args:longitude
	// Improvement: -- add some process to make string to a valid double
	protected double getLongitude(){
		return  Double.parseDouble(longitude);
	}

	// getInfo - External interface function
	// # return args:info
	protected String getInfo(){
		return info;
	}

	// getInfo - External interface function
	// # return args:template
	protected String getTemplate(){
		return template;
	}
}