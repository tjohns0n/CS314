package edu.csu2017sp314.DTR14.tripco.View;

public class Subset {
	private String id;
	private String name;
	
	public Subset(String id, String name){
		this.id = id;
		this.name = name;
	}

	public Subset(String content){
		String[] parts = content.split(",");
		this.id = parts[0];
		this.name = parts[1];
	}
	
	public String getId(){
		return id;
	}
	
	public String getName(){
		return name;
	}
}