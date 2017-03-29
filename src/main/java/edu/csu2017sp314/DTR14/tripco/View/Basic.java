package edu.csu2017sp314.DTR14.tripco.View;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

public class Basic{
	protected GridPane newGridPane(){
		GridPane grid = new GridPane();
		ColumnConstraints column1 = new ColumnConstraints();
	    column1.setPercentWidth(35);
	    ColumnConstraints column2 = new ColumnConstraints();
	    column2.setPercentWidth(30);
	    ColumnConstraints column3 = new ColumnConstraints();
	    column3.setPercentWidth(35);
	    grid.getColumnConstraints().addAll(column1, column2, column3);
	    RowConstraints row1 = new RowConstraints();
	    row1.setPercentHeight(25);
	    RowConstraints row2 = new RowConstraints();
	    row2.setPercentHeight(40);
	    RowConstraints row3 = new RowConstraints();
	    row3.setPercentHeight(35);
	    grid.getRowConstraints().addAll(row1, row2, row3);
		return grid;
	}

	protected HBox setWelcomeWindow(String welcomes){
		Text welcome = new Text();
		welcome.setText(welcomes);
		welcome.setFont(Font.font ("Verdana", 25));
		HBox welBox = new HBox();
		welBox.getChildren().addAll(welcome);
		welBox.setAlignment(Pos.CENTER);
		return welBox;
	}

	protected Text newText(String content, int fonts){
		return newText(content, fonts, 0);
	}

	protected Text newText(String content, int fonts, int warps){
		Text text = new Text(content);
		text.setFont(Font.font ("Serif", fonts));
		text.setWrappingWidth(warps);
		return text;
	}

	protected Label newLabel(String content){
		Label label = new Label(content);
		label.setFont(new Font("Serif", 15));
		label.setAlignment(Pos.CENTER);
		return label;
	}
	
	protected TextField newTextField(String content){
		TextField textField = new TextField();
		textField.setMaxWidth(200);
		textField.setPromptText(content);
		return textField;
	}
	
	protected Button newButton(String name){
		return new Button(name);
	}

	protected FileChooser newFileChooser(){
		return newFileChooser("");
	}

	protected FileChooser newFileChooser(String name){
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(name);
		return fileChooser;
	}

	protected void newWarnText(Text warnText, String warns, boolean warn){
		if(warn == true) newWarnText(warnText, "", warns, warn);
		else newWarnText(warnText, warns, "", warn);
	}

	private void newWarnText(Text warnText, String content, String warning, boolean warn){
		if (warn){
			warnText.setFill(Color.FIREBRICK);
			if (warning.length() == 3) warnText.setText("File Selected NOT " + warning);
			else warnText.setText(warning);
		} else {
			warnText.setFill(Color.BLACK);
			warnText.setText("File Selected : " + content);

		}
	}

	protected HBox newHBox(int space){
		HBox hbox = new HBox();
		hbox.setAlignment(Pos.CENTER);
		hbox.setSpacing(space);
		return hbox;
	}

	protected VBox newVBox(int space){
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(space);
		return vbox;
	}

	protected CheckBox newCheckBox(String checker){
		return new CheckBox(checker);
	}

	protected ChoiceBox<Object> newChoiceBox(){
		ChoiceBox<Object> choiceBox = new ChoiceBox<Object>();
		choiceBox.setMaxSize(50, 100);
		return choiceBox;
	}
}