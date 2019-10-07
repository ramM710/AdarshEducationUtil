package com.adarsh.util.screen;

import com.adarsh.model.User;
import com.adarsh.service.UserService;
import com.adarsh.util.MainWindow;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author empc46
 */
public class LoginScreen {

    private static UserService userService;

    public void setUserService(UserService service) {
        userService = service;
    }

    public Pane start(Stage primaryStage) {

        GridPane gridPane = new GridPane();
        gridPane.setMinSize(500, 500);

        // username label
        Label userNameLabel = new Label("User name");

        // username text field
        TextField userNameField = new TextField();

        // password label
        Label passwordLabel = new Label("Password");

        // password text field
        PasswordField passwordField = new PasswordField();

        // login button
        Button loginButton = new Button("Login");
        loginButton.setAlignment(Pos.CENTER);

        Text status = new Text();

        loginButton.setOnAction((event) -> {
            String userName = userNameField.getText();
            String password = passwordField.getText();

            status.setText("Login successful !!");
            primaryStage.close();

            MainWindow mainWindow = new MainWindow();
            mainWindow.start(new Stage());

//            if (validate(userName, password)) {
//                status.setText("Login successful !!");
//                primaryStage.close();
//
//                MainWindow mainWindow = new MainWindow();
//                mainWindow.start(new Stage());
//
//            } else {
//                status.setText("Wrong credentials !!");
//            }
        });

        //Setting the Grid alignment 
        gridPane.setAlignment(Pos.CENTER);

        //Arranging all the nodes in the grid 
        gridPane.add(userNameLabel, 1, 0);
        gridPane.add(userNameField, 2, 0);
        gridPane.add(passwordLabel, 1, 1);
        gridPane.add(passwordField, 2, 1);
        gridPane.add(loginButton, 1, 2);

        return gridPane;
    }

    /**
     * // * @param args the command line arguments //
     */
//    public static void main(String[] args) {
//        launch(args);
//    }
    private boolean validate(String userName, String password) {
        List<User> users = userService.getAll();

        for (User user : users) {
            if (user.getUserName().equals(userName) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

}
