package edu.csu2017sp314.DTR14.tripco.Model;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
	private final String types = "select distinct type from airports;";
	private final String conts = "select name from continents;";
	private final String counts = "select name from countries;";
	private final String Wid = "WHERE airports.id = ";
	Model mod;
	
	
	public Query(){
        
        try	{ // connect to the database 
            Class.forName(driver); 
            Connection conn = DriverManager.getConnection(theURL, user, pass);	

			try { // create a statement
				Statement st = conn.createStatement();
				
				try { // submit a query
					String query = "SELECT * FROM continents LIMIT 10";
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
	
	//Grab airports for specific region
	public Query(String region){
       // mod = model;
        ArrayList<String> ret = new ArrayList<String>();
        try	{ // connect to the database 
            Class.forName(driver); 
            Connection conn = DriverManager.getConnection(theURL, user, pass);
			try { // create a statement
				Statement st = conn.createStatement();
				
				try { //Make list of regions
					String query = "SELECT code FROM regions WHERE name = '"+region+"';";
					ResultSet rs = st.executeQuery(query);
					String code = "";
					try { //Grab iso_country
						rs.next();
						code = rs.getString(1);
					} finally { rs.close(); }
					System.out.println(code);
					query = "SELECT name, id FROM airports WHERE iso_region = '"+code+"';";
					rs = st.executeQuery(query);
					
					try { //Grab countries
						while(rs.next()){
							ret.add(rs.getString(1));
						}
					} finally { rs.close(); }
					System.out.println(ret.toString());
				} finally { st.close(); }
			} finally { conn.close(); }
		} catch (Exception e) {
			System.err.printf("Exception: ");
			System.err.println(e.getMessage());
		}
        ret.trimToSize();
        //mod.sendToView((String)ret.toArray())
	}
	
	//Grab countries for specific continent
	public Query(String continent, Model model){
        mod = model;
        ArrayList<String> ret = new ArrayList<String>();
        try	{ // connect to the database 
            Class.forName(driver); 
            Connection conn = DriverManager.getConnection(theURL, user, pass);
			try { // create a statement
				Statement st = conn.createStatement();
				
				try { //Make list of regions
					String query = "SELECT id FROM continents WHERE name = '"+continent+"';";
					ResultSet rs = st.executeQuery(query);
					String code = "";
					try { //Grab iso_country
						rs.next();
						code = rs.getString(1);
					} finally { rs.close(); }
					System.out.println(code);
					query = "SELECT name FROM countries WHERE continent = '"+code+"';";
					rs = st.executeQuery(query);
					
					try { //Grab countries
						while(rs.next()){
							ret.add(rs.getString(1));
						}
					} finally { rs.close(); }
					System.out.println(ret.toString());
				} finally { st.close(); }
			} finally { conn.close(); }
		} catch (Exception e) {
			System.err.printf("Exception: ");
			System.err.println(e.getMessage());
		}
        ret.trimToSize();
        //mod.sendToView((String)ret.toArray())
	}
	
	
	
	//Grab regions for specific country
	public Query(Model model, String country){
        mod = model;
        ArrayList<String> ret = new ArrayList<String>();
        try	{ // connect to the database 
            Class.forName(driver); 
            Connection conn = DriverManager.getConnection(theURL, user, pass);	

			try { // create a statement
				Statement st = conn.createStatement();
				
				try { //Make list of regions
					String query = "SELECT code FROM countries WHERE name = '"+country+"';";
					ResultSet rs = st.executeQuery(query);
					String code = "";
					try { //Grab iso_country
						rs.next();
						code = rs.getString(1);
					} finally { rs.close(); }
					//System.out.println(code);
					query = "SELECT name FROM regions WHERE iso_country = '"+code+"';";
					rs = st.executeQuery(query);
					
					try { //Grab regions
						while(rs.next()){
							ret.add(rs.getString(1));
						}
					} finally { rs.close(); }
					//System.out.println(ret.toString());
				} finally { st.close(); }
			} finally { conn.close(); }
		} catch (Exception e) {
			System.err.printf("Exception: ");
			System.err.println(e.getMessage());
		}
        ret.trimToSize();
        //mod.sendToView((String)ret.toArray())
	}
	
	
	//Order of itinerary query result set
	//airport id, airport name, lat, long, elevation, municipality, regions, country, continent, 
	//air link, region link, country link
	
	//Itinerary constructor
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
						String query = itin+Wid+"'"+ids[i]+"';";
						ResultSet rs = st.executeQuery(query);
						
						try { // iterate through the query results and print
							String add="";
							int j=0;
							while (rs.next())
							{
								if(j==0)
									add+=rs.getString(j+1);
								else
									add+=rs.getString(j+1);
								j++;
							}
							ret[i] = add;
						} finally { rs.close(); }
					}
					
				} finally { st.close(); }
			} finally { conn.close(); }
		} catch (Exception e) {
			System.err.printf("Exception: ");
			System.err.println(e.getMessage());
		}
        //mod.sendToView
	}
	
	private void sendMsg(String[] cont, String code){
		Msg m = new Msg(cont, code);
		//mod.sendToView(m);
	}
	
	
	
	public static void main(String[] args){
		Query q = new Query("Rhode Island");
	}
	
	
}