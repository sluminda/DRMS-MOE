package com.discipline.drms.master_tables.daily_letters;

import com.discipline.drms.utils.sql.DatabaseConnection;
import com.discipline.drms.utils.sql.MasterTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
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

private final ObservableList<MasterTable> masterTableData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));

        loadLetterTypeData();
    }

    private void loadLetterTypeData() {
        // Clear previous data
        masterTableData.clear();

        try (Connection conn = DatabaseConnection.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM lettertypes");

            while (rs.next()) {
                MasterTable masterTable = new MasterTable();
                masterTable.setId(rs.getInt("id"));
                masterTable.setType(rs.getString("Type"));

                masterTableData.add(masterTable);
            }

            letterTypeTable.setItems(masterTableData);

            rs.close();
            stmt.close();
        } catch (Exception e) {
            logger.error("Failed to load employee data", e);
        }
    }
}
