package com.adarsh.util.screen;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;

/**
 *
 * @author empc46
 */
public class BorderPaneLeftPanel {

    GridPane centerGridPane;

    public void setCenterGridPane(GridPane centerGridPane) {
        this.centerGridPane = centerGridPane;
    }

    public Pane getPanel() {
        GridPane pane = new GridPane();
        pane.setStyle("-fx-background-color: #525252");

        ToggleGroup group = new ToggleGroup();

        ToggleButton homeButton = new ToggleButton("Home");
        homeButton.setTextFill(Paint.valueOf("#000000"));
        homeButton.setStyle("-fx-background-color: #89BD83");
        homeButton.setSelected(true);
        homeButtonEvent(homeButton, null);
        homeButton.setToggleGroup(group);

        ToggleButton summaryButton = new ToggleButton("Summary");
        summaryButton.setTextFill(Paint.valueOf("#000000"));
        summaryButton.setStyle("-fx-background-color: grey");
        summaryButton.setToggleGroup(group);

        homeButton.setOnAction((value) -> {
            homeButtonEvent(homeButton, summaryButton);
        });

        summaryButton.setOnAction((value) -> {
            summaryButtonEvent(summaryButton, homeButton);
        });

        pane.add(homeButton, 0, 2);
        pane.add(summaryButton, 0, 3);

        return pane;
    }

    private void summaryButtonEvent(ToggleButton summaryButton, ToggleButton homeButton) {
        centerGridPane.getChildren().clear();
        if (homeButton != null) {
            homeButton.setTextFill(Paint.valueOf("#FFFFFF"));
            homeButton.setStyle("-fx-background-color: grey");
        }

        PieChart pieChart = new PieChart();
        pieChart.setData(getloanData());
        centerGridPane.add(pieChart, 0, 0);

        summaryButton.setTextFill(Paint.valueOf("#000000"));
        summaryButton.setStyle("-fx-background-color: #89BD83");
    }

    private void homeButtonEvent(ToggleButton homeButton, ToggleButton summaryButton) {
        centerGridPane.getChildren().clear();
        if (summaryButton != null) {
            summaryButton.setTextFill(Paint.valueOf("#FFFFFF"));
            summaryButton.setStyle("-fx-background-color: grey");
        }

        PieChart pieChart = new PieChart();
        pieChart.setData(getAccountStatus());
        centerGridPane.add(pieChart, 0, 0);

        homeButton.setTextFill(Paint.valueOf("#000000"));
        homeButton.setStyle("-fx-background-color: #89BD83");
    }

    private ObservableList<PieChart.Data> getloanData() {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        data.addAll(new PieChart.Data("java", 17.56), new PieChart.Data("javafx", 31.76));
        return data;
    }

    private ObservableList<PieChart.Data> getAccountStatus() {
        ObservableList<PieChart.Data> answer = FXCollections.observableArrayList();
        answer.addAll(new PieChart.Data("java", 17),
                new PieChart.Data("JavaFx", 31),
                new PieChart.Data("Swing", 10),
                new PieChart.Data("IO", 20),
                new PieChart.Data("NIO", 21)
        );
        return answer;
    }

}
