package com.adarsh.run;

import com.adarsh.util.MainWindow;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author empc46
 */
public class RunApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("com/adarsh/config/adarsh-spring-config.xml");

        // initialize main window
        MainWindow mainWindow = new MainWindow();
        mainWindow.start(new Stage());

    }

    public static void main(String[] args) {
        launch(args);
    }

}
