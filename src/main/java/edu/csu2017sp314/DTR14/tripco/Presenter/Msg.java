package edu.csu2017sp314.DTR14.tripco.Presenter;

public class Msg {
	
	//Content is an array holding comma separated Strings to be processed based on the code
	public String[] content;
	//Code is an identifying dash separated String
	//Example: M-DB-Cy2Rn
	//The fist section (before the first dash) should be a single character marking the destination object
	//M=Model, V=View, P=Presenter
	//The second section should be the type of action for content processing
	//DB=database query, UP=update, ST=set 
	//The third section should specify what type of content is being processed
	//Cy2Rn = DB query to get list of RegioN for a specific CountrY
	//Rn2Il = DB query to get list of IndividuaL airports from a specific RegioN
	public String code;
	
	public Msg(String[] cont, String Code){
		content = cont;
		code = Code;
	}
	
	
	
}
