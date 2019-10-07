package com.adarsh.util;

import com.adarsh.util.screen.BorderPaneLeftPanel;
import com.adarsh.util.screen.RegistrationForm;
import com.adarsh.util.screen.NewLoan;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author empc46
 */
public class MainWindow extends Application {

    GridPane centerGridPane;

    @Override
    public void start(Stage primaryStage) {

        centerGridPane = new GridPane();
        centerGridPane.setHgap(10);
        centerGridPane.setVgap(10);

        BorderPane borderPane = new BorderPane();

        // set nodes in top, center, left rigth panels.
        borderPane.setTop(menus(centerGridPane));
        borderPane.setCenter(centerGridPane);
        borderPane.setLeft(insertLeftPanel());

        Scene scene = new Scene(borderPane, 900, 700);

        primaryStage.setTitle("Adarsh Education Society");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private MenuBar menus(GridPane gridPane) {
        // Menu Bar
        MenuBar menuBar = new MenuBar();

        //<editor-fold defaultstate="collapsed" desc="menu">
        // File Menu
        Menu fileMenu = new Menu("File");

        // Edit Menu
        Menu editMenu = new Menu("Edit");
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="menu item">
        // List of options in the file menu
        MenuItem fMNewLoan = new MenuItem("New Loan");
        fileMenu.getItems().addAll(fMNewLoan);

        // List of options in the edit menu
        MenuItem eMAddMember = new MenuItem("Add Member");
        editMenu.getItems().addAll(eMAddMember);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="menu item event">
        //event when clicked on new loan form
        fMNewLoan.setOnAction((actionEvent) -> {
            gridPane.getChildren().clear();
            gridPane.add(newLoanForm(), 5, 3);
        });

        //event when clicked on Add new member
        eMAddMember.setOnAction((actionEvent) -> {
            gridPane.getChildren().clear();
            gridPane.add(addMemberForm(), 5, 3);
        });
        menuBar.getMenus().addAll(fileMenu, editMenu);
        //</editor-fold>

        return menuBar;
    }

    private Pane addMemberForm() {
        RegistrationForm registrationForm = new RegistrationForm();
        return registrationForm.run();
    }

    private Pane newLoanForm() {
        NewLoan newLoan = new NewLoan();
        return newLoan.newLoanForm();
    }

    /**
     * @param args the command line arguments
     */
//    public static void main(String[] args) {
//        launch(args);
//    }

    private Pane insertLeftPanel() {
        BorderPaneLeftPanel leftPane = new BorderPaneLeftPanel();
        leftPane.setCenterGridPane(centerGridPane);
        return leftPane.getPanel();
    }

}
