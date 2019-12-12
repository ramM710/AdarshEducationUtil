package com.adarsh.util.screen;

import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Ram Mishra
 */
public class NewLoan {

    private final JdbcTemplate jdbcTemplate;

    public NewLoan(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Pane newLoanForm() {

        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(10);

        // loan amount label
        Label loanAmoutLabel = new Label("Loan Amount: ");
        loanAmoutLabel.setStyle("-fx-font: normal bold 15px 'serif'");
        pane.add(loanAmoutLabel, 0, 0);

        // loan amount text field
        TextField loanFieldValue = new TextField("1000");
        loanFieldValue.setMaxWidth(80);
        pane.add(loanFieldValue, 1, 0);

        // loan amount value slider
        Slider loanAmountSlider = new Slider(1000, 100000, 1000);
        loanAmountSlider.setMajorTickUnit(loanAmountSlider.getMax() / 2);
        loanAmountSlider.setShowTickLabels(true);
        loanAmountSlider.setShowTickMarks(true);
        loanAmountSlider.setSnapToTicks(true);
        pane.add(loanAmountSlider, 1, 1);

        // duration label
        HBox hBox = new HBox();
        hBox.setSpacing(5);
        Label periodLabel = new Label("Term Length");
        periodLabel.setStyle("-fx-font: normal bold 15px 'serif'");

        // loan tenure duration selection
        ChoiceBox tenureDurationOption = new ChoiceBox();
        tenureDurationOption.setMaxWidth(80);
        tenureDurationOption.setValue("Months");
        tenureDurationOption.getItems().addAll("Months", "Years");

        hBox.getChildren().addAll(periodLabel, tenureDurationOption);
        pane.add(hBox, 0, 2);

        // duration text field
        TextField loanDurationFieldValue = new TextField("1");
        loanDurationFieldValue.setMaxWidth(80);
        pane.add(loanDurationFieldValue, 1, 2);

        // duration selection slider
        Slider durationSlider = new Slider(1, 60, 1);
        durationSlider.setMajorTickUnit(durationSlider.getMax() / 2);
        durationSlider.setShowTickMarks(true);
        durationSlider.setShowTickLabels(true);
        durationSlider.setSnapToTicks(true);
        pane.add(durationSlider, 1, 3);

        tenureDurationOption.setOnAction((value) -> {
            if (!tenureDurationOption.getValue().toString().equalsIgnoreCase("months")) {
                durationSlider.setMin(1);
                durationSlider.setMax(5);
            } else {
                durationSlider.setMin(1);
                durationSlider.setMax(60);
            }
        });

        //<editor-fold defaultstate="collapsed" desc="text field events">
        // loan amount text field 
        loanFieldValue.setOnAction((actionEvent) -> {
            String currentLoanAmount = loanFieldValue.getText();
            loanAmountSlider.setValue(Double.parseDouble(currentLoanAmount));
        });

        // loan duration text field 
        loanDurationFieldValue.setOnAction((actionEvent) -> {
            String currentLoanDuration = loanDurationFieldValue.getText();
            durationSlider.setValue(Double.parseDouble(currentLoanDuration));
        });
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="slider events">
        // loan slider event
        loanAmountSlider.valueProperty().addListener((listener) -> {
            Double currentValue = loanAmountSlider.getValue();
            loanFieldValue.setText(String.valueOf(currentValue.intValue()));
        });

        // loan period slider event
        durationSlider.valueProperty().addListener((listener) -> {
            Double currentValue = durationSlider.getValue();
            loanDurationFieldValue.setText(String.valueOf(currentValue.intValue()));
        });
        //</editor-fold>

        // ROI label
        HBox roiHBox = new HBox();
        roiHBox.setSpacing(5);
        Label roiLabel = new Label("Interest (%)");
        roiLabel.setStyle("-fx-font: normal bold 15px 'serif'");

        // roi duration selection
        ChoiceBox roiDurationOption = new ChoiceBox();
        roiDurationOption.setMaxWidth(80);
        roiDurationOption.setValue("Months");
        roiDurationOption.getItems().addAll("Months", "Years");
        roiHBox.getChildren().addAll(roiLabel, roiDurationOption);
        pane.add(roiHBox, 0, 4);

        roiDurationOption.setOnAction((value) -> {
            String roiDuration = roiDurationOption.getValue().toString();
        });

        // ROI field
        TextField roiField = new TextField("2");
        roiField.setMaxWidth(50);
        pane.add(roiField, 1, 4);

        //Setting the back ground color 
        pane.setStyle("-fx-background-color: BEIGE;");
        pane.setAlignment(Pos.CENTER);
        return pane;
    }
}
