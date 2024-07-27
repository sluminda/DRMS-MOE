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

public class LetterType {

    private static final Logger logger = LoggerFactory.getLogger(LetterType.class);

    @FXML
    private TableView<MasterTable> letterTypeTable;

    @FXML
    private TableColumn<MasterTable, Integer> idColumn;

    @FXML
    private TableColumn<MasterTable, String> typeColumn;

    @FXML
    private TextField idField;

    @FXML
    private TextArea typeField;

    private final ObservableList<MasterTable> masterTableData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        letterTypeTable.setItems(masterTableData);
        loadLetterTypeData();

        // Add selection listener to TableView
        letterTypeTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                idField.setText(String.valueOf(newValue.getId()));
                typeField.setText(newValue.getType());
            }
        });
    }

    private void loadLetterTypeData() {
        // Clear previous data
        masterTableData.clear();

        String query = "SELECT * FROM lettertypes";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                MasterTable masterTable = new MasterTable();
                masterTable.setId(rs.getInt("id"));
                masterTable.setType(rs.getString("type"));

                masterTableData.add(masterTable);
            }
        } catch (Exception e) {
            logger.error("Failed to load letter type data", e);
        }
    }

    @FXML
    private void handleAdd() {
        String id = idField.getText();
        String type = typeField.getText();

        if (id.isEmpty() || type.isEmpty()) {
            AlertUtil.showAlertError("Validation Error", "ID or Type cannot be empty");
            return;
        }

        String sql = "INSERT INTO lettertypes (id, type) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(id));
            pstmt.setString(2, type);
            pstmt.executeUpdate();
            loadLetterTypeData();
            AlertUtil.showAlertSuccess("Success", "Letter type data added successfully");
        } catch (Exception e) {
            logger.error("Failed to add letter type data", e);
            AlertUtil.showAlertError("Error", "Failed to add letter type data");
        }
    }

    @FXML
    private void handleUpdate() {
        String id = idField.getText();
        String type = typeField.getText();

        if (id.isEmpty() || type.isEmpty()) {
            AlertUtil.showAlertError("Validation Error", "ID or Type cannot be empty");
            return;
        }

        String sql = "UPDATE lettertypes SET type = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, type);
            pstmt.setInt(2, Integer.parseInt(id));
            pstmt.executeUpdate();
            loadLetterTypeData();
            AlertUtil.showAlertSuccess("Success", "Letter type data updated successfully");
        } catch (Exception e) {
            logger.error("Failed to update letter type data", e);
            AlertUtil.showAlertError("Error", "Failed to update letter type data");
        }
    }

    @FXML
    private void handleDelete() {
        String id = idField.getText();

        if (id.isEmpty()) {
            AlertUtil.showAlertError("Validation Error", "ID cannot be empty");
            return;
        }

        String sql = "DELETE FROM lettertypes WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(id));
            pstmt.executeUpdate();
            loadLetterTypeData();
            AlertUtil.showAlertSuccess("Success", "Letter type data deleted successfully");
        } catch (Exception e) {
            logger.error("Failed to delete letter type data", e);
            AlertUtil.showAlertError("Error", "Failed to delete letter type data");
        }
    }
}
