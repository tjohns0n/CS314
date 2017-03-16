//Selection.java
//Data struct to hold data from input gui


package edu.csu2017sp314.DTR14.tripco.View;

import java.io.File;

public class Selection {
	//Class data variables
	//CSV data file name for selection
	private File dataCSV;
	//Name of select file
	private String selectFile;
	//Name of selection
	private String name;
	//Filename of background svg map
	private String backSVG;
	//Array of subset selections
	private String[] subset;
	//Array of options from input
	private boolean[] options;
	//0=IDs 1=Mileage 2=Names
	//Array of optimizations from input
	private boolean[] opts;
	//0=2opt 1=3opt
	
	//Barebones Constructor
	public Selection(File dataFile, String selectfile){
		dataCSV=dataFile;
		selectFile=selectfile;
		name=selectfile;
		backSVG = "";
		options = new boolean[] {false, false, false};
		opts = new boolean[] {false, false};
		subset = null;
	}
	//Full Constructor
	public Selection(File dataFile, String selectfile, String Name, String backsvg, boolean[] Options, boolean[] Opts, String[] Subset){
		dataCSV=dataFile;
		selectFile=selectfile;
		name=Name;
		backSVG=backsvg;
		if(Options.length==3)
			options=Options;
		else
			options = new boolean[] {false, false, false};
		if(Opts.length==2)
			opts=Opts;
		else
			opts = new boolean[] {false, false};
		subset = Subset;
	}
	
	//Setter methods
	public void setCSV(File filename){
		dataCSV=filename;
	}
	public void setBackSVG(String filename){
		backSVG=filename;
	}
	public void setOpt(boolean[] Opt){
		if(Opt.length==2){
			opts=Opt;
		}
	}
	public void set2Opt(boolean opt){
		opts[0]=opt;
	}
	public void set3Opt(boolean opt){
		opts[1]=opt;
	}
	public void setOptions(boolean[] Options){
		if(Options.length==3){
			options=Options;
		}
	}
	public void setIDOption(boolean id){
		options[0]=id;
	}
	public void setMileageOption(boolean mile){
		options[1]=mile;
	}
	public void setNameOption(boolean name){
		options[2]=name;
	}
	public void setSubset(String[] sub){
		subset=sub;
	}
	//Getter methods
	public File getCSV(){
		return dataCSV;
	}
	public String getCSVName(){
		return dataCSV.getName();
	}
	public String getFilename(){
		return selectFile;
	}
	public String getName(){
		return name;
	}
	public String getBackSVGName(){
		if(backSVG!=null)
			return backSVG;
		else
			return "";
	}
	public boolean[] getOptions(){
		return options;
	}
	public boolean[] getOpts(){
		return opts;
	}
	public String[] getSubset(){
		if(subset!=null)
			return subset;
		else{
			String[] r = new String[0];
			return r;
		}
	}	
}