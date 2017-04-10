package edu.csu2017sp314.DTR14.tripco.View;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.csu2017sp314.DTR14.tripco.Presenter.Msg;
import edu.csu2017sp314.DTR14.tripco.Presenter.XMLReader;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SelectionGUI extends Application{
	private String[] subSet;
	private String xmlFile;
	private String csvFile;
	private Basic basic;

	private TextField searchField = new TextField();
	private TextArea subsetText = new TextArea();
	private TextArea viewText = new TextArea();
	private Text warnText = new Text();
	private Text searchText = new Text();
	private Msg ms;
	
	private ArrayList<Subset> subsets = new ArrayList<Subset>();
	private static View view = new View();

	public SelectionGUI(){
		this.subSet = new String[0];
		basic = new Basic();
		initiate();
	}
	
	private void initiate(){
		ms = view.setMsg(new Msg(new String[0], "M-DB-INIT"));
		System.out.println(ms.content[0]);
	}

	private Scene dialogScene(Stage stage){

		GridPane grid = basic.newGridPane(20, 65, 15);
		//Welcome Text
		String welcomes = "Select Options For Your Trip Planning";
		grid.add(basic.setWelcomeWindow(welcomes), 0, 0, 3, 1);

		grid.add(setSelectBox(), 0, 1);

		grid.add(setViewBox(), 1, 1);
		
		grid.add(setSubSetBox(), 2, 1);
		
		grid.add(setLauncher(stage), 1, 2);

		return new Scene(grid, 1000, 600);
		
	}

	private VBox setSelectBox(){
		VBox vbox = basic.newVBox(20);
		vbox.getChildren().add(setDropDownBox("Type", ms.content[0].split(",")));
		vbox.getChildren().add(setDropDownBox("Continent", new String[0]));
		vbox.getChildren().add(setDropDownBox("Country", new String[0]));
		vbox.getChildren().add(setDropDownBox("Region", new String[0]));
		vbox.getChildren().add(setSearchBox("Search"));
		vbox.getChildren().add(searchText);
		vbox.setPadding(new Insets(-20, 50, 0, 0));
		return vbox;
	}
	
	private HBox setSearchBox(String name){
		HBox hbox = basic.newHBox(10);
		Button btn = basic.newButton(name);
		searchField.setText("Try Search Here");
		btn.setOnAction(e -> {
			String searches = searchField.getText();
			if(searches != null && !searches.isEmpty() && !searches.equals("Try Search Here")){
				// TODO 
				// Send Search Info
			}
			else setWarnText(searchText, "Error in Searching");
		});
		hbox.getChildren().addAll(btn, searchField);
		hbox.setAlignment(Pos.BASELINE_RIGHT);
		return hbox;
	}

	private VBox setViewBox(){
		VBox vbox = basic.newVBox(10);
		Label label = basic.newLabel("View Your Subset");
		viewText.setWrapText(true);
		viewText.setMaxWidth(300);
		viewText.setMinHeight(250);
		Button btn = basic.newButton("Add Subset");
		btn.setOnAction(e -> {
			String[] newSubSet = getSubSet(viewText.getText());
			subSet = concatStrings(subSet, newSubSet);
			subsetText.setText(
				Arrays.toString(subSet).substring(1, Arrays.toString(subSet).length()-1));
		});
		vbox.getChildren().addAll(label, viewText, btn);
		return vbox;
	}

	private HBox setDropDownBox(String title, String[] content){
		Label label = basic.newLabel(title);
		HBox hbox = basic.newHBox(20);
		hbox.setAlignment(Pos.CENTER_RIGHT);
		hbox.getChildren().addAll(label, basic.newComboBox(content));
		return hbox;
	}
	
	//content should be the constraints set on drop downs
	//EX: type-heliport
	//EX: region-Texas,type-large_aiport
	//EX: search-dallas
	private Msg sendMsg(String content){
		String title = content.split("-")[0];
		String code = "";
		String[] cont = new String[1];
		cont[0] = content;
		switch(title){
			case "type":{
				code = "M-DB-RN2IL";
				break;
			}
			case "continent":{
				code = "M-DB-CT2CY";
				break;
			}
			case "country":{
				code = "M-DB-CY2RN";
				break;
			}
			case "region":{
				code = "M-DB-RN2IL";
				break;
			}
			case "search":{
				code = "M-DB-SRCH";
				cont[0] = content.split("-")[1];
				break;
			}
			case "Plan":{
				code = "M-DB-TRIP";
				
			}
		}
		Msg m = new Msg(cont,code);
		return view.setMsg(m);
	}

	private VBox setSubSetBox(){
		//Subset fourth
		Label label = basic.newLabel("Input Your Subset");
		String hints = "Select a subset of locations from the datafile. Seperate ID values of desired locations with a comma";
		
		Text subText = basic.newText(hints, 12, 200);
		subsetText.setWrapText(true);
		subsetText.setMaxWidth(300);
		subsetText.setMinHeight(180);
		VBox vbox = basic.newVBox(20);
		vbox.getChildren().addAll(label, subText, subsetText, setbuttonSet(), warnText);
		return vbox;
	}

	private HBox setbuttonSet(){
		HBox hbox = basic.newHBox(20);
		Button btn1 = basic.newButton("Clear");
		btn1.setOnAction(e -> {
			subsetText.setText(null);
			setWarnText(warnText, "Cleared Successfully");
		});
		Button btn2 = basic.newButton("Save");
		btn2.setOnAction(e -> {
			String text = subsetText.getText();
			if(!text.isEmpty()){
				subSet = text.split(",");
				for(int i = 0; i < subSet.length; i++) subSet[i].trim();
				new SelectionWriter(subSet, xmlFile, csvFile).writeXML();
				setWarnText(warnText, "Saved Successfully in " + xmlFile);
			}
			else setWarnText(warnText, "Error! No SubSet!");
		});
		hbox.getChildren().addAll(btn1, btn2, setLoader());
		return hbox;
	}

	private Button setLoader(){
		FileChooser chooser = basic.newFileChooser("Choose existing selection");
		Button btn = basic.newButton("Load");
		btn.setOnAction(e ->{ 
			File in = chooser.showOpenDialog(new Stage());
			if(in != null){
				String name = in.getName();
				if(name.length() > 4){
					if(name.substring(name.length()-4, name.length()).equalsIgnoreCase(".xml")) {
                        xmlFile = in.getAbsolutePath();
                        try {
                        	StringBuilder cvsFileName = new StringBuilder();
							subSet = new XMLReader().readSelectFile(in.getAbsolutePath(), cvsFileName);
							csvFile = cvsFileName.toString();
						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
						}
					}
					if(subSet != null)
						subsetText.setText(
							Arrays.toString(subSet).substring(1, Arrays.toString(subSet).length()-1));
				}
				setWarnText(warnText, "Loaded Successfully");
			}
			else{
				setWarnText(warnText, "No File Choosen");
			}
		});
		return btn;
	}

	private void setWarnText(Text text, String content){
		text.setFill(Color.FIREBRICK);
		text.setText(content);
	}

	private VBox setLauncher(Stage stage){
		Button btn = basic.newButton("Plan Trip");
		Text warnText = new Text();
		btn.setOnAction(e -> {
			if(csvFile == null || csvFile.equals("null")){
				String warns = "No Selection or new CSV file chosen";
				basic.newWarnText(warnText, warns, true);
			}
			else{
				try {	
					if(subsetText.getText() != null)
						subSet = toStandardStringArray(subsetText.getText());
					else subSet = new String[0];
					new SelectionWriter(subSet, xmlFile, csvFile).writeXML();
	                writeResults();
		        } catch (Exception ex) {
		            Logger.getLogger(SelectionGUI.class.getName()).log(Level.SEVERE, null, ex);
				}
				stage.close();
			}
		});
		VBox vbox = basic.newVBox(20);
		vbox.getChildren().addAll(btn, warnText);
		return vbox;
	}

    
	private void writeResults() throws Exception{
	    try{
		    PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("GUI_OUTPUT.txt", true)));
		    for(int i = 0; i < subSet.length; i++)
		    	writer.print( (i == 0 ? "" : ",") + subSet[i].trim());
		    writer.close();
		} catch (IOException e) {
		   // do something
		}       
    }

    private String[] toStandardStringArray(String string){
    	return string.split(",");
    }

    private String[] concatStrings(String[] first, String[] second) {
	    List<String> both = new ArrayList<String>(first.length + second.length);
	    Collections.addAll(both, first);
	    Collections.addAll(both, second);
	    return both.toArray(new String[both.size()]);
	}

	private String[] getSubSet(String content) {
	    return new String[0];
	}

    public void start(Stage stage) throws Exception{
		//Set Title
		Scene scene = dialogScene(stage);
		stage.setTitle("Welcome to TripCo");
		stage.setScene(scene);
		stage.setResizable(true);
		stage.show();
	}

	public static void main(String[] args){
		Application.launch(args);
	}
	
}