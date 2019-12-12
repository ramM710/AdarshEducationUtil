package com.adarsh.util.screen;

import com.adarsh.model.MemberDetails;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import org.springframework.jdbc.core.JdbcTemplate;
import com.adarsh.util.DataExecutor;

public class RegistrationForm {

    private final JdbcTemplate jdbcTemplate;

    private final DataExecutor dataExecutor;

    public RegistrationForm(JdbcTemplate jdbcTemplate,
            DataExecutor dataExecutor) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataExecutor = dataExecutor;
    }

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

        Text mobileNumberLabel = new Text("Mobile Number");
        final TextField mobileNumberField = new TextField();

        Text addressLabel = new Text("Address");
        final TextArea addressField = new TextArea();
        addressField.setPrefWidth(150);
        addressField.setWrapText(true);

        Text referenceLabel = new Text("Reference Person");
        final TextField referenceField = new TextField();

        //Label for register 
        Button buttonRegister = new Button("Register");
        buttonRegister.setOnAction((actionEvent) -> {
            registerButtonActions(nameText.getText(), datePicker, groupGender, mobileNumberField.getText(), addressField.getText(), referenceField.getText());
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

        gridPane.add(mobileNumberLabel, 0, 3);
        gridPane.add(mobileNumberField, 1, 3);

        gridPane.add(addressLabel, 0, 4);
        gridPane.add(addressField, 1, 4);

        gridPane.add(referenceLabel, 0, 5);
        gridPane.add(referenceField, 1, 5);

        gridPane.add(buttonRegister, 2, 8);

        //Styling nodes   
//        buttonRegister.setStyle("-fx-background-color: grey; -fx-textfill: white;");
        nameLabel.setStyle("-fx-font: normal bold 15px 'serif' ");
        dobLabel.setStyle("-fx-font: normal bold 15px 'serif' ");
        genderLabel.setStyle("-fx-font: normal bold 15px 'serif' ");
        mobileNumberLabel.setStyle("-fx-font: normal bold 15px 'serif' ");
        addressLabel.setStyle("-fx-font: normal bold 15px 'serif' ");
        referenceLabel.setStyle("-fx-font: normal bold 15px 'serif' ");

        //Setting the back ground color 
        gridPane.setStyle("-fx-background-color: BEIGE;");

        return gridPane;
    }

    private void registerButtonActions(String name, DatePicker dob, ToggleGroup genderGroup, String mobileNo, String address, String referenceName) {
        try {
            String dateOfBirth = dob.getValue().toString();
            RadioButton genderRadioButton = (RadioButton) genderGroup.getSelectedToggle();

            // @TO-DO: save the new members details
            MemberDetails memberDetails = new MemberDetails(
                    name, dateOfBirth,
                    Integer.parseInt(mobileNo),
                    genderRadioButton.getText(),
                    address, referenceName);

            dataExecutor.addNewMember(memberDetails);

        } catch (Exception e) {
            e.getMessage();
        }
    }
}
