package edu.csu2017sp314.DTR14.tripco.Model;

import edu.csu2017sp314.DTR14.tripco.Presenter.Presenter;
import edu.csu2017sp314.DTR14.tripco.Presenter.Msg;

public class Model{

	// args: *.csv 			
	// # csv filename 
	private CSVReader cvsr;
	
	// args: location list 	
	// # have a reference of location list
	// # a reference of location list used for external interface
	private static LocationList locList;

	private ShortestRouteCalculator src;
	private Presenter prez;
	// Constructor 
	// args: csvFileName
	// # build a new location list which store the information from csv file
	// # build a new CSVReader which handle the file and auto add location to the list
	// # calculate a shortestneighborroute
	// Improvement: ShortestRouteCalculator is in process and need improvement
	public Model(String csvFileName){
		locList = new LocationList();
		cvsr 	= new CSVReader(csvFileName, locList);
		prez = null;
	}
	
	public Model(Presenter present){
		prez = present;
		locList = new LocationList();
	}
	public Model(){
		locList = new LocationList();
	}

	public boolean planTrip(boolean run2Opt, boolean run3Opt, String[] selection){
		//cvsr.initiate(selection);
		src = new ShortestRouteCalculator(locList, 0);
		if (run3Opt) {
			src.findBestNearestNeighbor(true, true);
		} else if (run2Opt) {
			src.findBestNearestNeighbor(true, false);
		} else {
			src.findBestNearestNeighbor(false, false);
		}
        return true;
	}

	public String[][] reteriveTrip(){
		Trip trip = new Trip(locList, src.getFinalRoute());
		return trip.createTrip();
	}
	
	public void sendToPresenter(Msg msg){
		if(prez!=null){
			prez.sendMessage(msg);	
		}
	}
	
	public void sendToModel(Msg msg){
		String[] codes = msg.code.split("-");
		//Check code length, better be 3 or return
		if(codes.length<3){
			return;
		}
		//Check destination code, if not for model ignore and return
		if(codes[0].equalsIgnoreCase("M")){
			switch(codes[1]){
				case "DB": {
					sendQuery(msg);
					break;
				}
				default: break;
			}
		}
	}
	
	public Msg sendQuery(Msg msg){
		String[] codes = msg.code.split("-");
		//Switch based on DB code
		Msg ret = null;
		switch(codes[2]){
			//M-DB-INIT
			case "INIT":{
				Query q = new Query(this);
				ret = q.getMsg();
				break;
			}
			//M-DB-CN2CY
			//content[] should have a single String with constraints
			case "CT2CY":{
				Query q = new Query(this, msg.content[0]);
				ret = q.getMsg();
				break;
			}
			//M-DB-CY2RN
			//content[] should have a single String with name of country
			case "CY2RN":{
				Query q = new Query(this, msg.content[0]);
				ret = q.getMsg();
				break;
			}
			//M-DB-RN2IL
			//content[] should have a single String with constraints
			case "RN2IL":{
				Query q = new Query(this, msg.content[0]);
				ret = q.getMsg();
				break;
			}
			case "SRCH":{
				Query q = new Query(msg.content[0], this);
				ret = q.getMsg();
				break;
			}
			//M-DB-TRIP
			//content[] should be array of selected airport ids (ident)
			case "TRIP":{
				Query q = new Query(this, msg.content);
				//Start trip planning
				//ITIN query and send to itin writer
				
				break;
			}
			default: break;
		}
		return ret;
	}
	
	
	//Set Location list from DB data for subset
	public void setLocList(String[] subset){
		String [] titles = "id,name,longitude,lattitude".split(",");
		for(int i=0;i<subset.length;i++){
			locList.lineHandler(subset[i], titles, new String[0]);
		}
	}

	public static void main(String args[]){
		String filename = args[0];
		Model model = new Model(filename);
		model.planTrip(false, false, new String[0]);
		model.reteriveTrip();
	}
}