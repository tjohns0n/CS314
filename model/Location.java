

public class Location{
	private String name;
	private String latitude;
	private String longitude;
	private String altitude;	//still not used
	private String info;

	public Location(String name, String latitude, String longitude, String info){
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.info = info;
	}

	public Location(String name, String latitude, String longitude){
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.info = "";
	}

	public void showLoc(){
		System.out.printf("%25s%15s%15s%20s\n", name, latitude, longitude, info);
	}

	public String getName(){
		return name;
	}

	public double getLatitude(){
		return Double.parseDouble(latitude);
	}

	public double getLongitude(){
		return  Double.parseDouble(longitude);
	}

	public String getInfo(){
		return info;
	}
}