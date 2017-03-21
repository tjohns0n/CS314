//InputGUI.java
//Input interface for -g command option
//Depends on javafx

package edu.csu2017sp314.DTR14.tripco.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import edu.csu2017sp314.DTR14.tripco.View.View;

public class InputGUI extends Application{
	
	private Scene dialog;
	private Scene chooser;
	private String xmlFile;
	private File csvFile;
	private String svgFile;
	private boolean[] options;
	private String[] SUBSET;
	public View vw;
	
	
	public InputGUI(View VW){
		vw = VW;
		dialog = null;
		//setDialogScene();
		chooser = null;
        options = new boolean[5];
        SUBSET=null;
		/*
		try {
			this.start(sg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
	}
	public InputGUI(){
		vw = null;
		dialog = null;
		//setDialogScene();
		chooser = null;
		options = new boolean[5];
		SUBSET=null;
		/*
		try {
			this.start(sg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
	}
	private void readSelection(File input){
		try {
			Scanner scan = new Scanner(input);
			int count = 1;
			boolean dest = false;
			boolean cont = scan.hasNextLine();
			ArrayList<String> subs = new ArrayList<String>();
			while(cont){
				String temp = scan.nextLine().trim();
				cont = scan.hasNextLine();
				switch(count){
					case 1:{//Check opening XML tag
						if(temp.length()>4){
							if(temp.substring(0, 5).equalsIgnoreCase("<?xml")){
								break;
							}
							else
								return;
						}
						else
							return;
					}
					case 2:{//Check opening selection tag
						if(temp.length()>10){
							if(temp.substring(0, 10).equalsIgnoreCase("<selection")){
								break;
							}
							else
								return;
						}
						else
							return;
					}
					case 3:{//Check title tag
						if(temp.length()>6){
							if(temp.substring(0, 6).equalsIgnoreCase("<title")){
								break;
							}
							else
								return;
						}
						else
							return;
					}
					case 4:{//Check opening filename tag
						if(temp.length()>9){
							if(temp.substring(0, 9).equalsIgnoreCase("<filename")){
								temp = temp.substring(temp.indexOf('>')+1);
								temp = temp.substring(0, temp.indexOf('<'));
								csvFile = new File(temp);
								break;
							}
							else
								return;
						}
						else
							return;
					}
					case 5:{//Check opening destinations tag
						if(temp.length()>13){
							if(temp.substring(0, 13).equalsIgnoreCase("<destinations")){
								dest = true;
								break;
							}
							else
								return;
						}
						else
							return;
					}
					default:{//Handle id and closing destinations/selection tag
						if(dest){
							if(temp.length()>3){
								if(temp.substring(0, 3).equalsIgnoreCase("<id")){
									temp = temp.substring(temp.indexOf('>')+1);
									temp = temp.substring(0, temp.indexOf('<'));
									subs.add(temp.trim());
								}
							}
						}
						if(temp.length()>14){
							if(temp.substring(0, 14).equalsIgnoreCase("</destinations")){
								dest = false;
								break;
							}
						}
						if(temp.length()>11){
							if(temp.substring(0, 11).equalsIgnoreCase("</selection")){
								cont = false;
								break;
							}
						}
					}
				}
				count++;
			}
			scan.close();
			SUBSET = subs.toArray(new String[0]);
			//System.out.println(Arrays.toString(SUBSET));
	        return;
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	private void setChooser(Stage prim){
		//Welcome Text
		Text welcome = new Text("Plan trips across the state of Colorado with TripCo!");
		welcome.setWrappingWidth(300);
		HBox welBox = new HBox();
		welBox.getChildren().addAll(welcome);
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.add(welBox, 0, 0);
		grid.setColumnSpan(welBox, 3);
		//Choose from existing selction file
		
		//File Chooser for existing selection xml
		FileChooser choose1 = new FileChooser();
		choose1.setTitle("Choose existing selection");
		//File chooser launch btn
		Text chooseText = new Text("Start from existing Selection");
		chooseText.setWrappingWidth(150);
		Button btn = new Button("Choose File");
		Text check = new Text();
		check.setWrappingWidth(150);
		btn.setOnAction(e ->{ 
			check.setFill(Color.TRANSPARENT);
			File in=choose1.showOpenDialog(new Stage());
			if(in!=null){
				String name = in.getName();
				if(name.length()>4){
					if(name.substring(name.length()-4, name.length()).equalsIgnoreCase(".xml")) {
                                        // TODO Auto-generated catch block
                                        xmlFile = in.getAbsolutePath();
                                        readSelection(in);
                                        check.setFill(Color.BLACK);
                                        check.setText("File Chosen: "+in.getName());
                                    } else{
						check.setFill(Color.FIREBRICK);
						check.setText("File chosen not an .xml document, please select different file");
					}	
				}
			}
		});
		//Vbox layout
		VBox chooseBox = new VBox();
		chooseBox.getChildren().addAll(chooseText, btn, check);
		grid.add(chooseBox, 0, 1);
		//Vertical separator between two options
		Separator vsep = new Separator();
		vsep.setOrientation(Orientation.VERTICAL);
		//Add vsep to col 2 row 2
		grid.add(vsep, 1, 1);
		//Name new selection file
		
		//File chooser for data csv file
		Text newText = new Text("Start new trip from a data .csv");
		newText.setWrappingWidth(150);
		FileChooser choose2 = new FileChooser();
		choose2.setTitle("Choose data .csv");
		TextField newName = new TextField();
		newName.setPromptText("Type new Selection Name");
		Text newCheck = new Text();
		newCheck.setWrappingWidth(150);
		Button btn2 = new Button("Start from data .csv");
		boolean startNew = false;
		//Grab data csv, grab name from text field, make select new empty Selection
		btn2.setOnAction(e ->{ 
			File in=choose2.showOpenDialog(new Stage());
			if(in!=null){
				String nam = in.getName();
				if(nam.length()>4){
					if(nam.substring(nam.length()-4, nam.length()).equalsIgnoreCase(".csv")){
						String entered = newName.getText();
						String name = xmlFile;
						if(name == null){
							if(entered.isEmpty()){//Generate default name
								name="temp.xml";
							}
							else if(entered.length()>4){
								if(!entered.substring(entered.length()-4, entered.length()).equalsIgnoreCase(".xml"))
									name = entered.concat(".xml");
								else
									name = entered;
							}
							else 
								name = entered.concat(".xml");
							xmlFile = name;
							File file = new File(xmlFile);
                            try {
                            	file.createNewFile();
                            } catch (IOException ex) {
                            	Logger.getLogger(InputGUI.class.getName()).log(Level.SEVERE, null, ex);
                            }
						}
						csvFile = in;
						newCheck.setFill(Color.BLACK);
						newCheck.setText("File Selected: "+nam);
					}
					else{
						newCheck.setFill(Color.FIREBRICK);
						newCheck.setText("File selected not .csv");
					}
				}
				
				
			}
		});
		//Grid layout
		VBox newBox = new VBox();
		newBox.getChildren().addAll(newText, newName, btn2, newCheck);
		grid.add(newBox, 2, 1);
		Separator hsep = new Separator();
		//Add hsep to col 1 row 3
		grid.add(hsep, 0, 2);
		grid.setColumnSpan(hsep, 3);
		//Progression to next step (scene)
		Text warn = new Text();
		warn.setWrappingWidth(150);
		Button btn3 = new Button("Start Planning Trip!");
		btn3.setOnAction(e->{
			if(csvFile == null){
				warn.setFill(Color.FIREBRICK);
				warn.setText("No Selection file or new csv file chosen");
			}
				
			else{
				prim.close();
				//prim = new Stage();
				setDialogScene(prim);
				prim.setScene(dialog);
				prim.setTitle("Select Options");
				prim.show();
			}
		});
		//Grid layout
		//Add launcher button to col 1 row 5 
		grid.add(btn3, 0, 4);
		grid.setColumnSpan(btn3, 3);
		//Add warn to col 1 row 6
		grid.add(warn, 0, 5);
		grid.setColumnSpan(warn, 3);
		chooser = new Scene(grid, 400, 300);
	}
	
	private void setDialogScene(Stage prim){
		GridPane grid = new GridPane();
		//Welcome Text
		Text welText = new Text("Select options for your trip planning");
		
		welText.setWrappingWidth(450);
		HBox welBox = new HBox();
		welBox.getChildren().addAll(welText);
		grid.add(welBox, 0, 0, 7, 1);
		Separator hsep = new Separator();
		grid.add(hsep, 0, 1, 7, 1);
		//Select Background SVG
		Label backL = new Label("Background Image");
		VBox backBox = new VBox();
		Text backText = new Text("Choose a background map for your trip that is in .svg format");
		Text backSlct = new Text();
		backSlct.setWrappingWidth(200);
		backText.setWrappingWidth(200);
		FileChooser choose = new FileChooser();
		choose.setTitle("Choose background");
		Button chooseBTN = new Button("Choose File");
		chooseBTN.setOnAction(e -> {
			File back = choose.showOpenDialog(new Stage());
			if(back!=null){
				String name = back.getName();
				if(name.length()>4){
					if(name.substring(name.length()-4, name.length()).equalsIgnoreCase(".svg"))
						svgFile = back.getAbsolutePath();
						backSlct.setText("background Selected: " + name);
				}
			}
		});
		backBox.getChildren().addAll(backL, backText, chooseBTN, backSlct);
		Separator vsep = new Separator();
		vsep.setOrientation(Orientation.VERTICAL);
		grid.add(backBox, 0, 2);
		grid.add(vsep, 1, 2);
		//Options fourth of window
		Label optn = new Label("Options");
		Text optnText = new Text("Select optional markers to be displayed on map of route");
		optnText.setWrappingWidth(150);
		CheckBox name = new CheckBox("Name");
		CheckBox id = new CheckBox("ID");
		CheckBox miles = new CheckBox("Mileage");
		id.setOnAction(e -> {
			options[0] = id.isSelected();
		});
		miles.setOnAction(e -> {
			options[1] = miles.isSelected();
		});
		name.setOnAction(e -> {
			options[2] = name.isSelected();
		});
		VBox optnBox = new VBox();
		optnBox.setPadding(new Insets(10));
		optnBox.getChildren().addAll(optn, optnText, id, name, miles);
		grid.add(optnBox, 2, 2);
		optnBox.setAlignment(Pos.CENTER_LEFT);
		//Optimization fourth
		Separator vsep0 = new Separator();
		vsep0.setOrientation(Orientation.VERTICAL);
		grid.add(vsep0, 3, 2);
		Label opt = new Label("Optimizations");
		Text optText = new Text("Select additonal opmimizations for a shorter route");
		optText.setWrappingWidth(150);
		CheckBox opt2 = new CheckBox("2OPT");
		CheckBox opt3 = new CheckBox("3OPT");
		opt2.setOnAction(e -> {
			options[3] = opt2.isSelected();
		});
		opt3.setOnAction(e -> {
			options[4] = opt3.isSelected();
		});
		VBox optBox = new VBox();
		optBox.getChildren().addAll(opt, optText, opt2, opt3);
		grid.add(optBox, 4, 2);
		//Subset fourth
		Separator vsep1 = new Separator();
		vsep1.setOrientation(Orientation.VERTICAL);
		grid.add(vsep1, 5, 2);
		Label sub = new Label("Subset");
		Text subText = new Text("Select a subset of locations from the datafile. Seperate ID values of desired locations with a comma");
		subText.setWrappingWidth(150);
		TextField subset = new TextField();
		if(sub!=null){
			subset.setText(Arrays.toString(SUBSET).substring(1, Arrays.toString(SUBSET).length()-1));
		}
		VBox subBox = new VBox();
		subBox.getChildren().addAll(sub, subText, subset);
		grid.add(subBox, 6, 2);
		Button plan = new Button("Plan Trip");
		plan.setOnAction(e -> {
			if(vw!=null)
				try {
					if(subset.getText() != null){
						SelectionWriter sw = new SelectionWriter(subset.getText(), xmlFile, csvFile.getAbsolutePath());
						sw.writeXML(xmlFile);
					}
                    writeResults();
	        } catch (Exception ex) {
	            Logger.getLogger(InputGUI.class.getName()).log(Level.SEVERE, null, ex);
	        }
			prim.close();
		});
		grid.add(plan, 6, 3);
		
		dialog = new Scene(grid);
		
	}
	
	protected void writeResults() throws Exception{
	    try{
		    PrintWriter writer = new PrintWriter("GUI_OUTPUT.txt", "UTF-8");
		    writer.println(csvFile.getAbsolutePath());
		    writer.println(xmlFile);
		    writer.println(svgFile);
		    writer.println(options[0]);
		    writer.println(options[1]);
		    writer.println(options[2]);
		    writer.println(options[3]);
		    writer.println(options[4]);
		    writer.close();
		} catch (IOException e) {
		   // do something
		}       
    }
	
	@Override
	public void start(Stage prim) throws Exception {
		//Set Title
		setChooser(prim);
		prim.setTitle("Welcome to TripCo");
		prim.setScene(chooser);
		prim.sizeToScene();
		prim.setResizable(false);
		prim.show();
	}

	
	public static void main(String[] args){
		InputGUI ig = new InputGUI();
		ig.launch(args);
	}
	
	
}