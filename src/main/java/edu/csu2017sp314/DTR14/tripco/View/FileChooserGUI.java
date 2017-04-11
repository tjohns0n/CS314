//InputGUI.java
//Input interface for -g command option
//Depends on javafx

package edu.csu2017sp314.DTR14.tripco.View;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FileChooserGUI extends Application{
	private boolean[] options;
	private String xmlFile;
	private String csvFile;
	private String svgFile;
	private Basic basic;
	
	private TextField newTextField;
	
	//Empty constructor, for javafx Application compliance
    public FileChooserGUI(){
    	this.options = new boolean[5];
    	basic = new Basic();
    }
    
	private Scene fileChooser(Stage stage){
		//Welcome Text
		
		GridPane grid = basic.newGridPane(25, 45, 30);
		String welcomes = "Plan trips across the state of Colorado with TripCo!";
		grid.add(basic.setWelcomeWindow(welcomes) ,0, 0, 3, 1);
		
		//Choose from existing selction file
		//File Chooser for existing selection xml

		//File chooser for data csv file
		grid.add(setCSVFileChooser(), 0, 1);

		grid.add(setSelectBox(), 1, 1);

		grid.add(setBackGroundImage(), 2, 1);
		
		//Grid layout
		//Add launcher button to col 1 row 5 
		grid.add(setLauncher(stage), 1, 2);
		
		return new Scene(grid, 1000, 600);
	}

	private VBox setSelectBox(){
		VBox vbox = basic.newVBox(40);
		vbox.getChildren().addAll(setMarkerBox(), setOptionBox());
		return vbox;
	}

	private GridPane SetOptions(){
		CheckBox opt2 = basic.newCheckBox("2OPT");
		CheckBox opt3 = basic.newCheckBox("3OPT");
		opt2.setOnAction(e -> {
			options[3] = opt2.isSelected();
		});
		opt3.setOnAction(e -> {
			options[4] = opt3.isSelected();
		});
		GridPane gridpane = basic.newGridPane(25, 45, 30);
		VBox vbox = basic.newVBox(5);
		vbox.getChildren().addAll(opt2, opt3);
		vbox.setAlignment(Pos.CENTER_LEFT);
		gridpane.add(vbox, 1, 0, 1, 3);
		return gridpane;
	}

	private VBox setOptionBox(){
		Label label = basic.newLabel("Select Additonal Opmimizations");
		VBox vbox = basic.newVBox(20);
		vbox.getChildren().addAll(label, SetOptions());
		return vbox;
	}

	private VBox setMarkerBox(){
		Label label = basic.newLabel("Select optional Markers");
		VBox vbox = basic.newVBox(10);
		vbox.getChildren().addAll(label,setMarkers());
		return vbox;
	}

	private GridPane setMarkers(){
		CheckBox name = basic.newCheckBox("Name");
		CheckBox id = basic.newCheckBox("ID");
		CheckBox miles = basic.newCheckBox("Mileage");
		id.setOnAction(e -> {
			options[0] = id.isSelected();
		});
		miles.setOnAction(e -> {
			options[1] = miles.isSelected();
		});
		name.setOnAction(e -> {
			options[2] = name.isSelected();
		});
		GridPane gridpane = basic.newGridPane(25, 45, 30);
		VBox vbox = basic.newVBox(5);
		vbox.getChildren().addAll(name, id, miles);
		vbox.setAlignment(Pos.CENTER_LEFT);
		gridpane.add(vbox, 1, 0, 1, 3);
		return gridpane;
	}

	private VBox setNewName(){
		Label label = basic.newLabel("Name Your Name TripCo");
		newTextField = basic.newTextField("NewSelectionName");
		VBox vbox = basic.newVBox(10);
		vbox.getChildren().addAll(label, newTextField, new Text());
		return vbox;
	}

	private VBox setCSVFileChooser(){
		Label label = basic.newLabel("Start from existing DataFile");
		Button btn = basic.newButton("Choose CSV File");
		FileChooser chooser = basic.newFileChooser();
		//Grab data csv, grab name from text field, make select new empty Selection
		Text warnText = new Text();
		btn.setOnAction(e ->{ 
			File in=chooser.showOpenDialog(new Stage());
			if(in != null){
				String name = in.getName();
				if(name.length()>4){
					if(name.substring(name.length()-4, name.length()).equalsIgnoreCase(".csv")){
						csvFile = in.getAbsolutePath();
						basic.newWarnText(warnText, name, false);
					}
					else basic.newWarnText(warnText, "CSV", true);
				}
				
				
			}
		});
		//Grid layout
		VBox vbox = basic.newVBox(40);
		vbox.getChildren().addAll(label, btn, warnText);
		return vbox;
	}

	private VBox setBackGroundImage(){
		//Select Background SVG
		Label label = basic.newLabel("Select Background Image");
		FileChooser chooser = basic.newFileChooser("Choose background");
		Button btn = basic.newButton("Choose BG File");
		Text warnText = new Text();
		btn.setOnAction(e -> {
			File back = chooser.showOpenDialog(new Stage());
			if(back != null){
				String name = back.getName();
				if(name.length() > 4){
					if(name.substring(name.length()-4, name.length()).equalsIgnoreCase(".svg")){
						svgFile = back.getAbsolutePath();
						basic.newWarnText(warnText, name, false);
					} else basic.newWarnText(warnText, "SVG", true);
				}
			}
		});
		VBox vbox = basic.newVBox(40);
		vbox.getChildren().addAll(label, btn, warnText);
		return vbox;
	}

	private VBox setSumbitFile(Stage stage){
		Text warnText = new Text();
		Button btn = basic.newButton("Start Planning Trip!");
		btn.setOnAction(e->{
			String entered = newTextField.getText();
			if(xmlFile == null){
				if(entered.isEmpty()) xmlFile = "temp.xml";
				else xmlFile = entered.concat(".xml");
			}
			try {
				writeResults();
				stage.close();
				new SelectionGUI().start(new Stage());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		VBox vbox = basic.newVBox(10);
		vbox.getChildren().addAll(btn, warnText);
		return vbox;
	}

	private VBox setLauncher(Stage stage){
		VBox vbox = basic.newVBox(10);
		vbox.setPadding(new Insets(40, 0, 0, 0));
		vbox.getChildren().addAll(setNewName(), setSumbitFile(stage), new Text());
		return vbox;
	}

	protected void writeResults() throws Exception{
	    try{
	    	PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("GUI_OUTPUT.txt")));
		    writer.println(csvFile);
		    writer.println(xmlFile);
		    writer.println(svgFile);
		    for(int i = 0; i < options.length; i++)
				writer.println(options[i]);
		    writer.close();
		} catch (IOException e) {
		   // do something
		}       
    }
	
	@Override
	public void start(Stage stage) throws Exception{
		//Set Title
		Scene scene = fileChooser(stage);
		stage.setTitle("Welcome to TripCo");
		stage.setScene(scene);
		stage.setResizable(true);
		stage.show();
	}

	
	public static void main(String[] args){
		Application.launch(args);
	}
	
	
}