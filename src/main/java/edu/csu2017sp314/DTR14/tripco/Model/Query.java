package edu.csu2017sp314.DTR14.tripco.Model;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.sql.ResultSet;
import edu.csu2017sp314.DTR14.tripco.Presenter.Msg;

public class Query {

	private final String driver = "com.mysql.jdbc.Driver";
	private final String theURL = "jdbc:mysql://129.82.45.59:3306/cs314";
	private final String user = "bcgood";
	private final String pass = "830271534";
	private final String itin = "SELECT airports.id,airports.name,latitude,longitude,elevation_ft,"+
						"municipality,regions.name,countries.name,continents.name,airports.wikipedia_link,"+
						"regions.wikipedia_link,countries.wikipedia_link FROM continents INNER JOIN"+
						" countries ON countries.continent = continents.id INNER JOIN"+
						" regions ON regions.iso_country = countries.code INNER JOIN" +
						" airports ON airports.iso_region = regions.code ";
	private final String types = "SELECT distinct type FROM airports;";
	private final String conts = "SELECT name FROM continents;";
	private final String counts = "SELECT name FROM countries;";
	private final String Wid = "WHERE airports.id = ";
	public Model mod;
	private Msg mess;
	
	
	public Query(){
        try	{ // connect to the database 
            Class.forName(driver); 
            Connection conn = DriverManager.getConnection(theURL, user, pass);	
			try { // create a statement
				Statement st = conn.createStatement();
				
				try { // submit a query
					String query = "SELECT * FROM continents LIMIT 10;";
					ResultSet rs = st.executeQuery(query);
					
					try { // iterate through the query results and print
						while (rs.next())
						{
							String id = rs.getString("Id");
							String name = rs.getString("name");
							System.out.printf("%s,%s\n", id, name);
						}
					} finally { rs.close(); }
				} finally { st.close(); }
			} finally { conn.close(); }
		} catch (Exception e) {
			System.err.printf("Exception: ");
			System.err.println(e.getMessage());
		}
	}
	
	//Return iso id for give continent name
	private String continent2id(String cont){
		String ret = "";
		try	{//Connect to DB 
            Class.forName(driver); 
            Connection conn = DriverManager.getConnection(theURL, user, pass);	
			try{
				Statement st = conn.createStatement();
				try {//Grab code from name
					String query = "SELECT id FROM continents where name like "+"'"+cont+"'"+";";
					ResultSet rs = st.executeQuery(query);
					try{//Grab code from rs
						if(rs.next()){
							ret = rs.getString(1);
						}
					} finally { rs.close(); }
				} finally { st.close(); }
			} finally { conn.close(); }
		} catch (Exception e) {
			System.err.printf("Exception: ");
			System.err.println(e.getMessage());
		}
		return ret;
	}
	
	//Returns iso code for given country name
	private String country2code(String count){
		String ret = "";
		try	{//Connect to DB 
            Class.forName(driver); 
            Connection conn = DriverManager.getConnection(theURL, user, pass);	
			try{
				Statement st = conn.createStatement();
				try {//Grab code from name
					String query = "SELECT code FROM countries where name = "+"'"+count+"'"+";";
					ResultSet rs = st.executeQuery(query);
					try{//Grab code from rs
						if(rs.next()){
							ret = rs.getString(1);
						}
					} finally { rs.close(); }
				} finally { st.close(); }
			} finally { conn.close(); }
		} catch (Exception e) {
			System.err.printf("Exception: ");
			System.err.println(e.getMessage());
		}
		return ret;
	}
	
	//Grab code from given region name and country code
	private String region2code(String region, String country){
		String ret = "";
		try	{//Connect to DB 
            Class.forName(driver); 
            Connection conn = DriverManager.getConnection(theURL, user, pass);	
			try{
				Statement st = conn.createStatement();
				try {//Grab code from name
					String query = "SELECT code FROM regions where name = '"+region+"'";
					//Additional condition to query if iso_country code is not blank
					if(country.isEmpty()){
						query+=";";
					}
					else{
						query+=" and iso_country = '"+country+"';";
					}
					ResultSet rs = st.executeQuery(query);
					try{//Grab code from rs
						if(rs.next()){
							ret = rs.getString(1);
						}
					} finally { rs.close(); }
				} finally { st.close(); }
			} finally { conn.close(); }
		} catch (Exception e) {
			System.err.printf("Exception: ");
			System.err.println(e.getMessage());
		}
		return ret;
	}
	
	private String[] continent2countries(String id){
		String[] ret = {"","","","",""};
		try	{//Connect to DB 
            Class.forName(driver); 
            Connection conn = DriverManager.getConnection(theURL, user, pass);	
			try{
				Statement st = conn.createStatement();
				try {//Grab code from name
					String query = "SELECT name FROM countries where continent = '"+id+"';";
					ResultSet rs = st.executeQuery(query);
					try{//Grab code from rs
						while(rs.next()){
							ret[2]+=rs.getString(1)+",";
						}
						ret[2]=ret[2].substring(0, ret[2].length()-1);
					} finally { rs.close(); }
				} finally { st.close(); }
			} finally { conn.close(); }
		} catch (Exception e) {
			System.err.printf("Exception: ");
			System.err.println(e.getMessage());
		}
		return ret;
	}
	
	//Grab region name from given iso_country code
	private String[] country2regions(String count){
		String[] ret = {"","","","",""};
		try	{//Connect to DB 
            Class.forName(driver); 
            Connection conn = DriverManager.getConnection(theURL, user, pass);	
			try{
				Statement st = conn.createStatement();
				try {//Grab code from name
					String query = "SELECT name FROM regions where iso_country = '"+count+"';";
					ResultSet rs = st.executeQuery(query);
					try{//Grab names from rs
						while(rs.next()){
							ret[3]+=rs.getString(1)+",";
						}
						ret[3]=ret[3].substring(0, ret[3].length()-1);
					} finally { rs.close(); }
				} finally { st.close(); }
			} finally { conn.close(); }
		} catch (Exception e) {
			System.err.printf("Exception: ");
			System.err.println(e.getMessage());
		}
		return ret;
	}
	
	private String[] region2airports(String region, String type){
		String[] ret = {"","","","",""};
        try	{ // connect to the database 
            Class.forName(driver); 
            Connection conn = DriverManager.getConnection(theURL, user, pass);
			try { // create a statement
				Statement st = conn.createStatement();
				try { //Make list of regions
					String query = "SELECT name, id FROM airports WHERE iso_region = '"+region+"'";
					//Add type condition to query if not empty
					if(type.isEmpty()){
						query+=";";
					}
					else{
						query+=" and type = '"+type+"';";
					}
					ResultSet rs = st.executeQuery(query);
					try { //Grab countries
						while(rs.next()){
							ret[4]+=rs.getString(1)+"-"+rs.getString(2)+",";
						}
						ret[4] = ret[4].substring(0, ret[4].length()-1);
					} finally { rs.close(); }
					System.out.println(ret.toString());
				} finally { st.close(); }
			} finally { conn.close(); }
		} catch (Exception e) {
			System.err.printf("Exception: ");
			System.err.println(e.getMessage());
		}
		return ret;
	}
	
	//Init query
	//Grab list of types, continents, and countries
	//M-DB-INIT
	public Query(Model model){
        mod = model;
        String[] ret = {"","","","",""};
        try	{ // connect to the database 
            Class.forName(driver); 
            Connection conn = DriverManager.getConnection(theURL, user, pass);	
			try {
				Statement st = conn.createStatement();
				try {//Queries
					ResultSet rs = st.executeQuery(types);
					try{ //Grab types
						String type = "";
						while(rs.next()){
							type+=rs.getString(1)+",";
						}
						type = type.substring(0, type.length()-1);//Trim trailing ','
						ret[0]=type;
					}finally { rs.close(); }
					rs = st.executeQuery(conts);
					try { //Grab continents
						String cont = "";
						while (rs.next()){
							cont+=rs.getString(1)+",";
						}
						cont = cont.substring(0, cont.length()-1);//Trim trailing ','
						ret[1]=cont;
					} finally { rs.close(); }
					rs = st.executeQuery(counts);
					try { //Grab countries
						String count = "";
						while (rs.next()){
							count+=rs.getString(1)+",";
						}
						count = count.substring(0, count.length()-1);//Trim trailing ','
						ret[2]=count;
					} finally { rs.close(); }
				} finally { st.close(); }
			} finally { conn.close(); }
		} catch (Exception e) {
			System.err.printf("Exception: ");
			System.err.println(e.getMessage());
		}
        mess = new Msg(ret, "V-ST-INIT");
        //sendMsg(ret,"V-ST-INIT");
	}
	
	//Order of itinerary query result set
	//airport id, airport name, lat, long, elevation, municipality, regions, country, continent, 
	//air link, region link, country link
	//Itinerary constructor
	//M-DB-ITIN
	public Query(String[] ids, Model model){
		mod = model;
        String[] ret = new String[ids.length];
        try	{ // connect to the database 
            Class.forName(driver); 
            Connection conn = DriverManager.getConnection(theURL, user, pass);	

			try { // create a statement
				Statement st = conn.createStatement();
				
				try { // submit a query
					for(int i=0; i<ids.length; i++){
						String query = itin+Wid+"'"+ids[i]+"';";
						ResultSet rs = st.executeQuery(query);
						
						try { // iterate through the query results and print
							String add="";
							int j=0;
							while (rs.next()){
								add+=rs.getString(j+1)+",";
								j++;
							}
							ret[i] = add.substring(0, add.length()-1);
						} finally { rs.close(); }
					}
				} finally { st.close(); }
			} finally { conn.close(); }
		} catch (Exception e) {
			System.err.printf("Exception: ");
			System.err.println(e.getMessage());
		}
        mess = new Msg(ret, "V-ST-ITIN");
        //sendMsg(ret,"V-ST-ITIN");
	}
	
	//ID query for Model trip planning
	//M-DB-TRIP
	public Query(Model model, String[] ids){
		mod = model;
        String[] ret = new String[ids.length];
        try	{ // connect to the database 
            Class.forName(driver); 
            Connection conn = DriverManager.getConnection(theURL, user, pass);	

			try { // create a statement
				Statement st = conn.createStatement();
				
				try { // submit a query
					for(int i=0; i<ids.length; i++){
						String query = "SELECT id,name,longitude,lattitude,"+"'"+ids[i]+"';";
						ResultSet rs = st.executeQuery(query);
						
						try { // iterate through the query results and print
							String add="";
							int j=0;
							while (rs.next()){
								add+=rs.getString(j+1)+",";
								j++;
							}
							ret[i] = add.substring(0, add.length()-1);
						} finally { rs.close(); }
					}
				} finally { st.close(); }
			} finally { conn.close(); }
		} catch (Exception e) {
			System.err.printf("Exception: ");
			System.err.println(e.getMessage());
		}
        mod.setLocList(ret);
	}
	
	private void sendMsg(String[] cont, String code){
		Msg m = new Msg(cont, code);
		mod.sendToPresenter(m);
	}
	
	
	//Takes comma seperated string of limits already set in GUI
	//EX: type-large_aiports,continent-North America,country-United States,region-Texas
	private String[] parseLimits(String limits){
		String[] ret = {"","","",""};
		String[] limit = limits.split(",");
		for(int i=0; i<limit.length;i++){
			String[] fields = limit[i].split("-");
			switch(fields[0]){
				case "type":{
					ret[0] = fields[1];
					break;
				}
				case "continent":{
					ret[1] = continent2id(fields[1]);
					break;
				}
				case "country":{
					ret[2] = country2code(fields[1]);
					break;
				}
				case "region":{
					ret[3] = region2code(fields[1], ret[2]);
					break;
				}
				default: break;
			}
		}
		return ret;
	}
	
	//Main Constructor
	//Based of constraints give, know which query to execute and message back
	public Query(Model model, String constraints){
		mod=model;
		String[] constraint = parseLimits(constraints);
		//Constraints array, 0=type, 1=continent, 2=country, 3=region
		//Have continent but nothing else, grab countries for that continent
		if(!constraint[1].isEmpty() && constraint[2].isEmpty() && constraint[3].isEmpty()){
			String[] countries = continent2countries(constraint[1]);
			//sendMsg(countries, "V-UP-CY");
			mess = new Msg(countries, "V-UP-CY");
		}
		//Have country but not region, grab all regions for that country
		else if(!constraint[2].isEmpty()&&constraint[3].isEmpty()){
			System.out.println(constraint[2]);
			String[] regions = country2regions(constraint[2]);
			//sendMsg(regions, "V-UP-RN");
			mess = new Msg(regions, "V-UP-RN");
		}
		//Have region, grab all airports for specific region
		else if(!constraint[3].isEmpty()){
			String[] airports = region2airports(constraint[3], constraint[0]);
			//sendMsg(airports, "V-UP-IL");
			mess = new Msg(airports, "V-UP-IL");
		}
	}
	
	//Search Query for given search token
	public Query(String token, Model model){
		mod=model;
		mod = model;
		ArrayList<String> ret = new ArrayList<String>();
        try	{ // connect to the database 
            Class.forName(driver); 
            Connection conn = DriverManager.getConnection(theURL, user, pass);	

			try { // create a statement
				Statement st = conn.createStatement();
				
				try { // submit a query
						String query = "SELECT name,id FROM airports WHERE name like '%"+token+"%' ";
						query+="or municipality like '%"+token+"%' ";
						query+="or id like '%"+token+"%' ";
						query+="or municipality like '%"+token+"%' ";
						query+="or keywords like '%"+token+"%' ";
						query+=";";
						ResultSet rs = st.executeQuery(query);
					try { // iterate through the query results and print
						while (rs.next()){
							ret.add(rs.getString(1)+","+rs.getString(2));
						}
					} finally { rs.close(); }
				} finally { st.close(); }
			} finally { conn.close(); }
		} catch (Exception e) {
			System.err.printf("Exception: ");
			System.err.println(e.getMessage());
		}
        ret.trimToSize();
		String[] r = ret.toArray(new String[ret.size()]);
        mess = new Msg(r,"V-ST-SRCH");
	}
	
	public Msg getMsg(){
		return mess;
	}
	
	
	public static void main(String[] args){
		Query q = new Query(null, "country-United States");
	}
	
	
}