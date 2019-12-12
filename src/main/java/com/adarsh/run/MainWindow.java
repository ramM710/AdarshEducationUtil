package com.adarsh.run;

import com.adarsh.util.DataExecutor;
import com.adarsh.util.screen.BorderPaneLeftPanel;
import com.adarsh.util.screen.RegistrationForm;
import com.adarsh.util.screen.NewLoan;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author Ram M
 */
public class MainWindow extends Application {

    GridPane centerGridPane;

    private final JdbcTemplate jdbcTemplate;

    private final DataExecutor dataExecutor;

    public MainWindow(JdbcTemplate jdbcTemplate,
            DataExecutor dataExecutor) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataExecutor = dataExecutor;
    }

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

        // Repost Menu
        Menu reportMenu = new Menu("Report");
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="menu item">
        // List of options in the file menu
        MenuItem fMNewLoan = new MenuItem("New Loan");
        fileMenu.getItems().addAll(fMNewLoan);

        // List of options in the edit menu
        MenuItem eMAddMember = new MenuItem("Add Member");
        editMenu.getItems().addAll(eMAddMember);

        // Items of report menu
        MenuItem monthlyReport = new MenuItem("Monthly Report");
        reportMenu.getItems().addAll(monthlyReport);
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
        RegistrationForm registrationForm = new RegistrationForm(jdbcTemplate, dataExecutor);
        return registrationForm.run();
    }

    private Pane newLoanForm() {
        NewLoan newLoan = new NewLoan(jdbcTemplate);
        return newLoan.newLoanForm();
    }

    private Pane insertLeftPanel() {
        BorderPaneLeftPanel leftPane = new BorderPaneLeftPanel();
        leftPane.setCenterGridPane(centerGridPane);
        return leftPane.getPanel();
    }

    private List<String> fetchMenuBars() {
        return jdbcTemplate.query("select menu_name from MenuBarHeader where active =1", new ResultSetExtractor<List<String>>() {
            @Override
            public List<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<String> lists = new ArrayList<>();
                while (rs.next()) {
                    lists.add(rs.getString("menu_name"));
                }
                return lists;
            }
        });
    }

}
