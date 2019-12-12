package com.adarsh.util.screen;

import com.adarsh.model.User;
import com.adarsh.service.UserService;
import com.adarsh.util.MainWindow;
import java.util.List;
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
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 *
 * @author empc46
 */
public class LoginScreen extends Application {

    private final JdbcTemplate jdbcTemplate;

    private final UserService userService;

    private final PlatformTransactionManager transactionManager;

    public LoginScreen(JdbcTemplate jdbcTemplate,
            UserService userService,
            PlatformTransactionManager transactionManager) {
        this.jdbcTemplate = jdbcTemplate;
        this.userService = userService;
        this.transactionManager = transactionManager;
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

            if (validate(userName, password)) {
                status.setText("Login successful !!");
                primaryStage.close();

                MainWindow mainWindow = new MainWindow(jdbcTemplate);
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
    private boolean validate(String userName, String password) {

        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        return transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus ts) {
                List<User> users = userService.getAll();
                if (!users.isEmpty()) {
                    for (User user : users) {
                        if (user.getUserName().equals(userName) && user.getPassword().equals(password)) {
                            return true;
                        }
                    }
                }
                return false;
            }
        });
    }
}
