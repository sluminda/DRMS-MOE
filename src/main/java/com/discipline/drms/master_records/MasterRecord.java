package com.discipline.drms.master_records;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MasterRecord {

    @FXML
    private StackPane masterRecord;

    private static final Logger LOGGER = Logger.getLogger(MasterRecord.class.getName());
    private final Map<String, Pane> fxmlCache = new HashMap<>();
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @FXML
    private void loadInstitute() {
        loadFXML("/com/discipline/drms/interfaces/body/master_records/institute.fxml");
    }

    @FXML
    private void loadDesignation() {
        loadFXML("/com/discipline/drms/interfaces/body/master_records/designation.fxml");
    }

    @FXML
    private void loadSubjectClerk() {
        loadFXML("/com/discipline/drms/interfaces/body/master_records/subject_clerk.fxml");
    }

    @FXML
    private void loadInvOfficer() {
        loadFXML("/com/discipline/drms/interfaces/body/master_records/investigation_officer.fxml");
    }

    @FXML
    private void loadFileLocation() {
        loadFXML("/com/discipline/drms/interfaces/body/master_records/file_location.fxml");
    }

    @FXML
    private void loadCurrentInvStatus() {
        loadFXML("/com/discipline/drms/interfaces/body/master_records/current_investigation_status.fxml");
    }

    private void loadFXML(String fxmlFile) {
        Pane cachedPane = fxmlCache.get(fxmlFile);
        if (cachedPane != null) {
            masterRecord.getChildren().setAll(cachedPane);
            return;
        }

        Task<Pane> loadTask = new Task<>() {
            @Override
            protected Pane call() throws IOException {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
                return loader.load();
            }
        };

        loadTask.setOnSucceeded(event -> {
            Pane pane = loadTask.getValue();
            fxmlCache.put(fxmlFile, pane);
            masterRecord.getChildren().setAll(pane);
        });

        loadTask.setOnFailed(event ->
                LOGGER.log(Level.SEVERE, "Failed to load FXML file: " + fxmlFile, loadTask.getException())
        );

        executorService.submit(loadTask);
    }
}
