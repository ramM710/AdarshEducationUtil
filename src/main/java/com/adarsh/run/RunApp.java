package com.adarsh.run;

import com.adarsh.service.UserService;
import com.adarsh.util.screen.LoginScreen;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author empc46
 */
public class RunApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("com/adarsh/config/adarsh-spring-config.xml");

        JdbcTemplate jdbcTemplate = applicationContext.getBean(JdbcTemplate.class);

        // start with login screen and then process further
        LoginScreen loginScreen = new LoginScreen(jdbcTemplate);
        loginScreen.setUserService(applicationContext.getBean(UserService.class));
        loginScreen.start(new Stage());

//        // initialize main window
//        MainWindow mainWindow = new MainWindow(jdbcTemplate);
//        mainWindow.start(new Stage());
    }

    public static void main(String[] args) {
        launch(args);
    }

}
