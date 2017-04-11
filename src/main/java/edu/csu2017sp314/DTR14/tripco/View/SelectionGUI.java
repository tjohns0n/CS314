package edu.csu2017sp314.DTR14.tripco.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import edu.csu2017sp314.DTR14.tripco.Presenter.Message;
import edu.csu2017sp314.DTR14.tripco.Presenter.XMLReader;
import javafx.application.Application;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
	private static String xmlFile;
	private static String csvFile;

	private Basic basic;

	private TextField searchField = new TextField();
	private TextArea subsetText = new TextArea();
	private TextArea viewText = new TextArea();
	private Text warnText = new Text();
	private Text searchText = new Text();
	private ComboBox[] myComboBoxes = new ComboBox[5];
	private Message ms;
	private String chosen;
	
	private final static String[] labels = new String[]{"Type", "Continent", "Country", "Region", "Individual"};
	private ArrayList<Subset> subsets = new ArrayList<Subset>();
	private static View view = new View();

	public SelectionGUI(){
		this.subSet = new String[0];
		basic = new Basic();
		initiate();
	}
	
	private void initiate(){
		ms = view.setMsg(new Message(new String[0], "M-DB-INIT"));
	}
	
	public static void setXmlFile(String xmlFile) {
		SelectionGUI.xmlFile = xmlFile;
	}
	
	public static void setCsvFile(String csvFile) {
		SelectionGUI.csvFile = csvFile;
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
		for(int i = 0; i < 5; i++) myComboBoxes[i] = basic.newComboBox(labels[i]);
		for(int i = 0; i < 5; i++)
			vbox.getChildren().add(setDropDownBox(i, labels[i], ms.content[i].split(",")));
		vbox.getChildren().add(setSearchBox("Search"));
		vbox.getChildren().add(searchText);
		vbox.setPadding(new Insets(-20, 20, 0, 0));
		return vbox;
	}
	
	private HBox setDropDownBox(int index, String title, String[] content){
		Label label = basic.newLabel(title);
		HBox hbox = basic.newHBox(20);
		hbox.setAlignment(Pos.CENTER_RIGHT);
		updateDropDownBox(myComboBoxes[index], content);     
		myComboBoxes[index].setOnAction((Event ev) -> {
		    chosen =  myComboBoxes[index].getSelectionModel().getSelectedItem().toString();   
		    if(!chosen.isEmpty()){
		    	if(index == 0) return;
		    	Message msg = null;
		    	if(index == 3) {
		    		String type =  myComboBoxes[0].getSelectionModel().getSelectedItem().toString();  
		    		if (!type.isEmpty()) msg = sendMsg(labels[index] + "-" + chosen + "," + labels[0] + "-" + type);
		    		else msg = sendMsg(labels[index] + "-" + chosen);
		    	}
		    	if(index == 4){
	    			addToSubsets(chosen);
		    		viewText.setText(viewSubsets());
		    		return;
		    	}
		    	else  msg = sendMsg(labels[index] + "-" + chosen);
		    	System.out.println("fff : " + msg.content[index+1]);
		    	updateDropDownBox(myComboBoxes[index + 1], msg.content[index+1].split(","));
		    }
		});
		hbox.getChildren().addAll(label, myComboBoxes[index]);
		return hbox;
	}
	
	private void updateDropDownBox(ComboBox comboBox, String[] content){
		comboBox.getItems().clear();
		for(int i = 0; i < content.length; i++)
			comboBox.getItems().add(content[i]);
	}
	
	//content should be the constraints set on drop downs
	//EX: type-heliport
	//EX: region-Texas,type-large_aiport
	//EX: search-dallas
	private Message sendMsg(String content){
		System.out.println(content);
		int index = content.indexOf("-");
		String title = content.substring(0, index);
		String code = "";
		String[] cont = new String[1];
		cont[0] = content;
		switch(title){
			case "Type":{
				code = "M-DB-RN2IL";
				break;
			}
			case "Continent":{
				code = "M-DB-CT2CY";
				break;
			}
			case "Country":{
				code = "M-DB-CY2RN";
				break;
			}
			case "Region":{
				code = "M-DB-RN2IL";
				break;
			}
			case "Search":{
				code = "M-DB-SRCH";
				cont[0] = content.substring(index+1, content.length());
				break;
			}
			case "Plan":{
				code = "M-DB-TRIP";
				cont[0] = content.substring(index+1, content.length());
				break;
			}
		}
		Message m = new Message(cont,code);
		return view.setMsg(m);
	}

	private HBox setSearchBox(String name){
		HBox hbox = basic.newHBox(10);
		Button btn = basic.newButton(name);
		//searchField.setText("Try Search Here");
		btn.setOnAction(e -> {
			String searches = searchField.getText();
			if(searches != null && !searches.isEmpty() && !searches.equals("Try Search Here")){
				Message msg = sendMsg("Search-" + searches);
				addToSubsets(msg);
	    		viewText.setText(viewSubsets());
			}
			else setWarnText(searchText, "Error in Searching");
		});
		hbox.getChildren().addAll(btn, searchField);
		hbox.setAlignment(Pos.BASELINE_RIGHT);
		return hbox;
	}
	
	private String viewSubsets(){
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < subsets.size(); i++){
			sb.append(subsets.get(i).getId());	
			sb.append("\t");
			sb.append(subsets.get(i).getName());
			sb.append("\n");
		}
		return sb.toString();
	}

	private VBox setViewBox(){
		VBox vbox = basic.newVBox(10);
		Label label = basic.newLabel("View Your Subset");
		viewText.setWrapText(true);
		viewText.setMaxWidth(300);
		viewText.setMinHeight(250);
		vbox.getChildren().addAll(label, viewText, setViewAction());
		return vbox;
	}
	
	private HBox setViewAction(){
		HBox hbox = basic.newHBox(20);
		Button btn1 = basic.newButton("Add");
		btn1.setOnAction(e -> {
			String[] newSubSet = getSubSet();
			subSet = concatStrings(subSet, newSubSet);
			subsetText.setText(
				Arrays.toString(subSet).substring(1, Arrays.toString(subSet).length()-1));
		});
		Button btn2 = basic.newButton("Update");
		btn2.setOnAction(e -> {
			updateSubsets(viewText.getText());
		});
		Button btn3 = basic.newButton("Clear");
		btn3.setOnAction(e -> {
			subsets.clear();
			viewText.clear();
		});
		hbox.getChildren().addAll(btn1, btn2, btn3);
		return hbox;
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
			subSet = new String[0];
			setWarnText(warnText, "Cleared Successfully");
		});
		Button btn2 = basic.newButton("Save");
		btn2.setOnAction(e -> {
			String text = subsetText.getText();
			if(!text.isEmpty()){
				subSet = text.split(",");
				for(int i = 0; i < subSet.length; i++) subSet[i].trim();
				System.out.print("..." + xmlFile);
				new SelectionWriter(subSet, "usr"+xmlFile, "").writeXML();
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
			System.out.println(subSet.length);
			if(subSet.length == 1){
				String warns = "No Selection Chosen";
				basic.newWarnText(warnText, warns, true);
			}
			else{
				sendMsg("Plan-" + subsetText.getText());
				stage.close();
			}
		});
		VBox vbox = basic.newVBox(20);
		vbox.getChildren().addAll(btn, warnText);
		return vbox;
	}
    
    private String[] concatStrings(String[] first, String[] second) {
    	List<String> secondList = new ArrayList<String>(Arrays.asList(second));
	    List<String> both = new ArrayList<String>();
	    Collections.addAll(both, first);
	    for(int i = 0; i < secondList.size(); i++){
	    	if(!both.contains(secondList.get(i)))
	    		both.add(secondList.get(i));
	    }
	    return both.toArray(new String[both.size()]);
	}

	private void updateSubsets(String data){
		System.out.println("update = " +data);
		for(int i = 0; i < subsets.size(); i++){
			if(!data.contains(subsets.get(i).getId()))
				subsets.remove(i);
		}
	}

	private void addToSubsets(Message msg){
		String ids[] = msg.content[5].split(",");
		String names[] = msg.content[4].split(",");
		for(int i = 0; i < names.length; i++){
			Subset newsub = new Subset(ids[i], names[i]);
			if(checkValid(newsub)) subsets.add(newsub);
		}
	}
	
	private boolean checkValid(Subset newsub){
		for(int i = 0; i < subsets.size(); i++){
			if(subsets.get(i).getId().equals(newsub.getId()))
				return false;
		}
		return true;
	}
	private void addToSubsets(String string){
		String parts[] = string.split(":");
		Subset newsub = new Subset(parts[0], parts[1]);
		if(!subsets.contains(newsub)) subsets.add(newsub);
	}
	
	private String[] getSubSet() {
		ArrayList<String> al = new ArrayList<String>();
	    for(int i = 0; i < subsets.size(); i++){
	    	al.add(subsets.get(i).getId());
	    }
	    return al.toArray(new String[0]);
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