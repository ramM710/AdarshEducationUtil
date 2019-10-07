package com.adarsh.util.screen;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;

public class RegistrationForm {

    public Pane run() {

        //Label for name 
        Text nameLabel = new Text("Name");

        //Text field for name 
        final TextField nameText = new TextField();

        //Label for date of birth 
        Text dobLabel = new Text("Date of birth");

        //date picker to choose date 
        final DatePicker datePicker = new DatePicker();

        //Label for gender
        Text genderLabel = new Text("Gender");

        //Toggle group of radio buttons       
        final ToggleGroup groupGender = new ToggleGroup();
        RadioButton maleRadio = new RadioButton("male");
        maleRadio.setToggleGroup(groupGender);
        RadioButton femaleRadio = new RadioButton("female");
        femaleRadio.setToggleGroup(groupGender);

        //Label for location 
        Text locationLabel = new Text("location");

        //Choice box for location 
        final ChoiceBox locationchoiceBox = new ChoiceBox();
        locationchoiceBox.getItems().addAll("Hyderabad", "Chennai", "Delhi", "Mumbai", "Vishakhapatnam");

        //Label for register 
        Button buttonRegister = new Button("Register");
        buttonRegister.setOnAction((actionEvent) -> {
            registerButtonActions(nameText.getText(), datePicker, groupGender, locationchoiceBox);
        });

        //Creating a Grid Pane 
        GridPane gridPane = new GridPane();

        //Setting size for the pane 
        gridPane.setMinSize(500, 500);

        //Setting the padding    
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        //Setting the vertical and horizontal gaps between the columns 
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        //Setting the Grid alignment 
        gridPane.setAlignment(Pos.CENTER);

        //Arranging all the nodes in the grid 
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameText, 1, 0);

        gridPane.add(dobLabel, 0, 1);
        gridPane.add(datePicker, 1, 1);

        gridPane.add(genderLabel, 0, 2);
        gridPane.add(maleRadio, 1, 2);
        gridPane.add(femaleRadio, 2, 2);

        gridPane.add(locationLabel, 0, 6);
        gridPane.add(locationchoiceBox, 1, 6);

        gridPane.add(buttonRegister, 2, 8);

        //Styling nodes   
        buttonRegister.setStyle("-fx-background-color: darkslateblue; -fx-textfill: white;");

        nameLabel.setStyle("-fx-font: normal bold 15px 'serif' ");
        dobLabel.setStyle("-fx-font: normal bold 15px 'serif' ");
        genderLabel.setStyle("-fx-font: normal bold 15px 'serif' ");
        locationLabel.setStyle("-fx-font: normal bold 15px 'serif' ");

        //Setting the back ground color 
        gridPane.setStyle("-fx-background-color: BEIGE;");

        return gridPane;
    }

    private void registerButtonActions(String name, DatePicker dob, ToggleGroup genderGroup, ChoiceBox locations) {
        try {
            String dateOfBirth = dob.getValue().toString();

            RadioButton genderRadioButton = (RadioButton) genderGroup.getSelectedToggle();
            String gender = genderRadioButton.getText();

            String location = locations.getValue().toString();

            // @TO-DO: save the new members details            
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
