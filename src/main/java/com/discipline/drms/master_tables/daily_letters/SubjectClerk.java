package com.discipline.drms.master_tables.daily_letters;

import com.discipline.drms.utils.AlertUtil;
import com.discipline.drms.utils.sql.DatabaseConnection;
import com.discipline.drms.utils.sql.MasterTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class SubjectClerk {

    private static final Logger logger = LoggerFactory.getLogger(SubjectClerk.class);

    @FXML
    private TableView<MasterTable> subjectClerkTable;

    @FXML
    private TableColumn<MasterTable, Integer> idColumn;

    @FXML
    private TableColumn<MasterTable, String> nameColumn;

    @FXML
    private TextField idField;

    @FXML
    private TextArea nameField;

    private final ObservableList<MasterTable> masterTableData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        subjectClerkTable.setItems(masterTableData);
        loadSubjectClerkData();

        // Add selection listener to TableView
        subjectClerkTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                idField.setText(String.valueOf(newValue.getId()));
                nameField.setText(newValue.getName());
            }
        });
    }

    private void loadSubjectClerkData() {
        // Clear previous data
        masterTableData.clear();

        String query = "SELECT * FROM subjectclerks";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                MasterTable masterTable = new MasterTable();
                masterTable.setId(rs.getInt("id"));
                masterTable.setName(rs.getString("name"));

                masterTableData.add(masterTable);
            }
        } catch (Exception e) {
            logger.error("Failed to load subject clerk data", e);
        }
    }

    @FXML
    private void handleAdd() {
        String id = idField.getText();
        String name = nameField.getText();

        if (id.isEmpty() || name.isEmpty()) {
            AlertUtil.showAlertError("Validation Error", "ID or Name cannot be empty");
            return;
        }

        String sql = "INSERT INTO subjectclerks (id, name) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(id));
            pstmt.setString(2, name);
            pstmt.executeUpdate();
            loadSubjectClerkData();
            AlertUtil.showAlertSuccess("Success", "Subject clerk data added successfully");
        } catch (Exception e) {
            logger.error("Failed to add subject clerk data", e);
            AlertUtil.showAlertError("Error", "Failed to add subject clerk data");
        }
    }

    @FXML
    private void handleUpdate() {
        String id = idField.getText();
        String name = nameField.getText();

        if (id.isEmpty() || name.isEmpty()) {
            AlertUtil.showAlertError("Validation Error", "ID or Name cannot be empty");
            return;
        }

        String sql = "UPDATE subjectclerks SET name = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, Integer.parseInt(id));
            pstmt.executeUpdate();
            loadSubjectClerkData();
            AlertUtil.showAlertSuccess("Success", "Subject clerk data updated successfully");
        } catch (Exception e) {
            logger.error("Failed to update subject clerk data", e);
            AlertUtil.showAlertError("Error", "Failed to update subject clerk data");
        }
    }

    @FXML
    private void handleDelete() {
        String id = idField.getText();

        if (id.isEmpty()) {
            AlertUtil.showAlertError("Validation Error", "ID cannot be empty");
            return;
        }

        String sql = "DELETE FROM subjectclerks WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(id));
            pstmt.executeUpdate();
            loadSubjectClerkData();
            AlertUtil.showAlertSuccess("Success", "Subject clerk data deleted successfully");
        } catch (Exception e) {
            logger.error("Failed to delete subject clerk data", e);
            AlertUtil.showAlertError("Error", "Failed to delete subject clerk data");
        }
    }
}
