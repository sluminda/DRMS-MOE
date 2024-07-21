package com.discipline.drms.admin_panel;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminPanel {

    @FXML
    private StackPane contentPage;

    private static final Logger LOGGER = Logger.getLogger(AdminPanel.class.getName());
    private final Map<String, Pane> fxmlCache = new HashMap<>();

    @FXML
    private void loadDashboard() {
        loadFXML("/com/discipline/drms/interfaces/body/admin_panel/dashboard.fxml");
    }

    @FXML
    private void loadAddUser() {
        loadFXML("/com/discipline/drms/interfaces/body/admin_panel/add_user.fxml");
    }

    @FXML
    private void loadDeleteUser() {
        loadFXML("/com/discipline/drms/interfaces/body/admin_panel/remove_user.fxml");
    }

    @FXML
    private void loadSettings() {
        loadFXML("/com/discipline/drms/interfaces/body/admin_panel/settings.fxml");
    }

    private void loadFXML(String fxmlFile) {
        Pane cachedPane = fxmlCache.get(fxmlFile);
        if (cachedPane != null) {
            contentPage.getChildren().setAll(cachedPane);
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
            contentPage.getChildren().setAll(pane);
        });

        loadTask.setOnFailed(event ->
                LOGGER.log(Level.SEVERE, "Failed to load FXML file: " + fxmlFile, loadTask.getException())
        );

        new Thread(loadTask).start();
    }
}
