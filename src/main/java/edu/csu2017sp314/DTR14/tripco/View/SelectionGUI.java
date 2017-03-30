package edu.csu2017sp314.DTR14.tripco.View;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.csu2017sp314.DTR14.tripco.Presenter.XMLReader;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SelectionGUI extends Application{
	private boolean[] options;
	private String[] subSet;
	private String xmlFile;
	private String csvFile;
	private Basic basic;

	private TextArea subsetText;

	public SelectionGUI(){
		this.options = new boolean[5];
		this.subSet = null;
		basic = new Basic();
	}

	private Scene dialogScene(Stage stage){

		GridPane grid = basic.newGridPane();
		//Welcome Text
		String welcomes = "Select Options For Your Trip Planning";
		grid.add(basic.setWelcomeWindow(welcomes), 0, 0, 3, 1);

		grid.add(setSelectBox(), 1, 1);

		grid.add(setSubSetBox(), 2, 1);
		
		grid.add(setLauncher(stage), 1, 2);

		return new Scene(grid, 1000, 600);
		
	}

	private VBox setSelectBox(){
		VBox vbox = basic.newVBox(40);
		vbox.getChildren().addAll(setMarkerBox(), setOptionBox());
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
		GridPane gridpane = basic.newGridPane();
		VBox vbox = basic.newVBox(5);
		vbox.getChildren().addAll(name, id, miles);
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

	private GridPane SetOptions(){
		CheckBox opt2 = basic.newCheckBox("2OPT");
		CheckBox opt3 = basic.newCheckBox("3OPT");
		opt2.setOnAction(e -> {
			options[3] = opt2.isSelected();
		});
		opt3.setOnAction(e -> {
			options[4] = opt3.isSelected();
		});
		GridPane gridpane = basic.newGridPane();
		VBox vbox = basic.newVBox(5);
		vbox.getChildren().addAll(opt2, opt3);
		vbox.setAlignment(Pos.CENTER_LEFT);
		gridpane.add(vbox, 1, 0, 1, 3);
		return gridpane;
	}

	private VBox setSubSetBox(){
		//Subset fourth
		Label label = basic.newLabel("Input Your Subset");
		String hints = "Select a subset of locations from the datafile. Seperate ID values of desired locations with a comma";
		
		Text subText = basic.newText(hints, 12, 200);
		subsetText = new TextArea();
		if(subSet != null){
			subsetText.setText(Arrays.toString(subSet).substring(1, Arrays.toString(subSet).length()-1));
		}
		subsetText.setWrapText(true);
		subsetText.setMaxWidth(250);
		VBox vbox = basic.newVBox(20);
		vbox.getChildren().addAll(label, subText, subsetText);
		return vbox;
	}

	private VBox setLauncher(Stage stage){
		Button btn = basic.newButton("Plan Trip");
		btn.setOnAction(e -> {
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
		});
		VBox vbox = basic.newVBox(20);
		vbox.getChildren().addAll(btn);
		return vbox;
	}

	private void gui_FileReader() throws IOException{
        BufferedReader br = new BufferedReader(new FileReader("GUI_OUTPUT.txt"));
        try {
            csvFile = br.readLine();
            xmlFile = br.readLine();
        } finally {
            br.close();
        }
        subSet = new XMLReader().readSelectFile(xmlFile, new StringBuilder());
    }
    
	private void writeResults() throws Exception{
	    try{
		    PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("GUI_OUTPUT.txt", true)));
		    writer.println(options[0]);
		    writer.println(options[1]);
		    writer.println(options[2]);
		    writer.println(options[3]);
		    writer.println(options[4]);
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
    public void start(Stage stage) throws Exception{
		//Set Title
		gui_FileReader();
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