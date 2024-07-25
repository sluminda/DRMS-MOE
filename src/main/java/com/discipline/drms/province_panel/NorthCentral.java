package com.discipline.drms.province_panel;

import com.discipline.drms.utils.KeyboardHandler;
import com.discipline.drms.utils.SceneCache;
import com.discipline.drms.utils.sql.DatabaseConnection;
import com.discipline.drms.utils.sql.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;

public class NorthCentral {

    private static final Logger logger = LoggerFactory.getLogger(NorthCentral.class);

    @FXML
    private ImageView toProvince;

    @FXML
    private TableView<Employee> employeeTable;

    @FXML
    private TableColumn<Employee, Integer> idColumn;

    @FXML
    private TableColumn<Employee, String> firstNameColumn;

    @FXML
    private TableColumn<Employee, String> lastNameColumn;

    @FXML
    private TableColumn<Employee, String> emailColumn;

    private final ObservableList<Employee> employeeData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Use the reusable KeyboardHandler to add a key event handler to the ImageView
        KeyboardHandler.addKeyHandler(toProvince, this::handleKeyPressed);


        // Initialize the table columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Load employee data
        loadEmployeeData();
    }

    private void handleKeyPressed(KeyEvent event) {
        if (Objects.requireNonNull(event.getCode()) == KeyCode.BACK_SPACE) {
            try {
                pathToProvince();
            } catch (IOException e) {
                logger.error("Failed to navigate to main panel", e);
            }
        }
    }

    public void pathToProvince() throws IOException {
        Parent mainPanel = SceneCache.getScene("/com/discipline/drms/interfaces/body/provinces_panel.fxml");
        setCenterPanel(mainPanel);
    }

    private void setCenterPanel(Parent panel) {
        Stage stage = (Stage) toProvince.getScene().getWindow();
        BorderPane root = (BorderPane) stage.getScene().getRoot();
        root.setCenter(panel);
    }

    private void loadEmployeeData() {
        // Clear previous data
        employeeData.clear();

        try (Connection conn = DatabaseConnection.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM employees");

            while (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getInt("id"));
                employee.setFirstName(rs.getString("first_name"));
                employee.setLastName(rs.getString("last_name"));
                employee.setEmail(rs.getString("email"));
                employeeData.add(employee);
            }

            employeeTable.setItems(employeeData);

            rs.close();
            stmt.close();
        } catch (Exception e) {
            logger.error("Failed to load employee data", e);
        }
    }
}
