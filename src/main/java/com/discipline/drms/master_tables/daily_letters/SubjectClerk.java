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

public class SubjectClerk {

    private static final Logger logger = LoggerFactory.getLogger(SubjectClerk.class);

    @FXML
    private TableView<MasterTable> subjectClerkTable;

    @FXML
    private TableColumn<MasterTable, Integer> idColumn;

    @FXML
    private TableColumn<MasterTable, String> nameColumn;

    private final ObservableList<MasterTable> masterTableData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        loadSubjectClerkData();
    }

    private void loadSubjectClerkData() {
        // Clear previous data
        masterTableData.clear();

        try (Connection conn = DatabaseConnection.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM subjectclerks");

            while (rs.next()) {
                MasterTable masterTable = new MasterTable();
                masterTable.setId(rs.getInt("id"));
                masterTable.setName(rs.getString("name"));

                masterTableData.add(masterTable);
            }

            subjectClerkTable.setItems(masterTableData);

            rs.close();
            stmt.close();
        } catch (Exception e) {
            logger.error("Failed to load employee data", e);
        }
    }
}
