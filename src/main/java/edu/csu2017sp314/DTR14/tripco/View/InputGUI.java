//InputGUI.java
//Input interface for -g command option
//Depends on javafx

package edu.csu2017sp314.DTR14.tripco.View;


import java.io.File;
import java.lang.reflect.Array;

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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class InputGUI extends Application{
	
	public Selection select;
	private Scene dialog;
	private Scene chooser;
	private View vw;
	
	
	public InputGUI(View VW){
		vw = VW;
		select = null;
		dialog = null;
		setDialogScene();
		chooser = null;
		Stage sg = new Stage();
		try {
			this.start(sg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public InputGUI(){
		vw = null;
		select = null;
		dialog = null;
		setDialogScene();
		chooser = null;
		Stage sg = new Stage();
		try {
			this.start(sg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void setChooser(){
		
	}
	
	public void setDialogScene(){
		GridPane grid = new GridPane();
		//Options third of window
		Label optn = new Label("Options");
		Text optnText = new Text("Select optional markers to be displayed on map of route");
		optnText.setWrappingWidth(200);
		CheckBox name = new CheckBox("Name");
		CheckBox id = new CheckBox("ID");
		CheckBox miles = new CheckBox("Mileage");
		name.setOnAction(e -> {
			select.setNameOption(name.isSelected());
		});
		id.setOnAction(e -> {
			select.setIDOption(id.isSelected());
		});
		miles.setOnAction(e -> {
			select.setMileageOption(miles.isSelected());
		});
		VBox optnBox = new VBox();
		optnBox.setPadding(new Insets(10));
		optnBox.getChildren().addAll(optn, optnText, id, name, miles);
		grid.add(optnBox, 0, 1);
		//Optimization third
		Separator vsep0 = new Separator();
		vsep0.setOrientation(Orientation.VERTICAL);
		grid.add(vsep0, 1, 1);
		Label opt = new Label("Optimizations");
		Text optText = new Text("Select additonal opmimizations for a shorter route");
		optText.setWrappingWidth(200);
		CheckBox opt2 = new CheckBox("2OPT");
		CheckBox opt3 = new CheckBox("3OPT");
		opt2.setOnAction(e -> {
			select.set2Opt(opt2.isSelected());
		});
		opt3.setOnAction(e -> {
			select.set3Opt(opt3.isSelected());
		});
		VBox optBox = new VBox();
		optBox.getChildren().addAll(opt, optText, opt2, opt3);
		grid.add(optBox, 2, 1);
		//Subset third
		Separator vsep1 = new Separator();
		vsep1.setOrientation(Orientation.VERTICAL);
		grid.add(vsep1, 3, 1);
		Label sub = new Label("Subset");
		Text subText = new Text("Select a subset of locations from the datafile. Seperate ID values of desired locations with a comma");
		subText.setWrappingWidth(200);
		TextField subset = new TextField();
		VBox subBox = new VBox();
		subBox.getChildren().addAll(sub, subText, subset);
		grid.add(subBox, 4, 1);
		Button plan = new Button("Plan Trip");
		plan.setOnAction(e -> {
			select.setIDOption(id.isSelected());
			select.setNameOption(name.isSelected());
			select.setMileageOption(miles.isSelected());
			select.set2Opt(opt2.isSelected());
			select.set3Opt(opt3.isSelected());
			if(subset.getText().isEmpty())
				select.setSubset(new String[0]);
			else
				select.setSubset(subset.getText().split(","));
			System.out.println(select.getOpts()[0]);
			
			vw.Notify();
			
		});
		grid.add(plan, 4, 2);
		
		dialog = new Scene(grid);
	}
	
	
	public Selection readSelectFile(File slct){
		Selection r = new Selection(slct, "");
		
		
		
		return r;
	}
	
	@Override
	public void start(Stage prim) throws Exception {
		//Set Title
		prim.setTitle("Welcome to TripCo");
		//Welcome Text
		Text welcome = new Text("Welcome");
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.add(welcome, 0, 0);
		//Choose from existing selction file
		
		//File Chooser for existing selection xml
		FileChooser choose1 = new FileChooser();
		choose1.setTitle("Choose existing selection");
		//File chooser launch btn
		Text chooseText = new Text("Start from existing Selection");
		chooseText.setWrappingWidth(150);
		Button btn = new Button("Choose File");
		Text check = new Text();
		btn.setOnAction(e ->{ 
			//check.setFill(Color.TRANSPARENT);
			File in=choose1.showOpenDialog(prim);
			String name = in.getName();
			if(!name.substring(name.length()-4, name.length()).equalsIgnoreCase(".xml"))
				select = readSelectFile(in);
			else{
				check.setFill(Color.FIREBRICK);
				check.setText("File chosen not an .xml document, please select different file");
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
		newName.setPromptText("Type new filename");
		Button btn2 = new Button("Start from data .csv");
		//Grab data csv, grab name from text field, make select new empty Selection
		btn2.setOnAction(e ->{ 
			File in=choose2.showOpenDialog(prim);
			String entered = newName.getText();
			if(entered.isEmpty()){//Generate default name
				
			}
			String name;
			if(!entered.substring(entered.length()-4, entered.length()).equalsIgnoreCase(".csv"))
				name = entered.concat(".csv");
			else
				name = entered;
			select = new Selection(in, name);
		});
		//Grid layout
		VBox newBox = new VBox();
		newBox.getChildren().addAll(newText, newName, btn2);
		grid.add(newBox, 2, 1);
		Separator hsep = new Separator();
		//Add hsep to col 1 row 3
		grid.add(hsep, 0, 2);
		grid.setColumnSpan(hsep, 3);
		//Progression to next step (scene)
		Text warn = new Text();
		Button btn3 = new Button("Start Planning Trip!");
		btn3.setOnAction(e->{
			if(select==null){
				warn.setFill(Color.FIREBRICK);
				warn.setText("No Selection file or new csv file chosen");
			}
			else{
				prim.close();
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
		chooser = new Scene(grid, 400, 200);
		prim.setScene(chooser);
		prim.sizeToScene();
		prim.show();
	}

	
	public static void main(String[] args){
		launch(args);
	}
	
	
}