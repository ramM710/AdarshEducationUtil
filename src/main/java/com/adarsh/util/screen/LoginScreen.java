package com.adarsh.util.screen;

import com.adarsh.run.MainWindow;
import com.adarsh.util.DataExecutor;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author empc46
 */
public class LoginScreen extends Application {

    private final JdbcTemplate jdbcTemplate;

    private final DataExecutor dataExecutor;

    public LoginScreen(JdbcTemplate jdbcTemplate,
            DataExecutor dataExecutor) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataExecutor = dataExecutor;
    }

    @Override
    public void start(Stage primaryStage) {

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(25, 25, 25, 25));

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

            if (dataExecutor.validateLogin(userName, password)) {
                status.setText("Login successful !!");
                primaryStage.close();

                MainWindow mainWindow = new MainWindow(jdbcTemplate, dataExecutor);
                mainWindow.start(new Stage());
            } else {
                status.setText("Wrong credentials !!");
            }
        });

        //Setting the Grid alignment 
        gridPane.setAlignment(Pos.CENTER);

        //Arranging all the nodes in the grid 
        gridPane.add(userNameLabel, 0, 0);
        gridPane.add(userNameField, 1, 0);
        gridPane.add(passwordLabel, 0, 2);
        gridPane.add(passwordField, 1, 2);
        gridPane.add(loginButton, 1, 3);

        Scene scene = new Scene(gridPane, 400, 400);

        primaryStage.setTitle("Adarsh Education Society");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * // * @param args the command line arguments //
     */
//    public static void main(String[] args) {
//        launch(args);
//    }
}
