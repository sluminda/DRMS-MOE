package com.discipline.drms.master_tables;

import com.discipline.drms.utils.SceneCache;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MasterTablePanel {

    @FXML
    private ImageView toMainPanel;

    @FXML
    private StackPane masterTablePanel;

    private static final Logger LOGGER = Logger.getLogger(MasterTablePanel.class.getName());
    private final Map<String, Pane> fxmlCache = new HashMap<>();
    private final ExecutorService executorService = Executors.newCachedThreadPool();


    @FXML
    private void loadLetterType() {
        loadFXML("/com/discipline/drms/interfaces/body/master_tables/letter_type.fxml");
    }


    private void loadFXML(String fxmlFile) {
        Pane cachedPane = fxmlCache.get(fxmlFile);
        if (cachedPane != null) {
            masterTablePanel.getChildren().setAll(cachedPane);
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
            masterTablePanel.getChildren().setAll(pane);
        });

        loadTask.setOnFailed(event ->
                LOGGER.log(Level.SEVERE, "Failed to load FXML file: " + fxmlFile, loadTask.getException())
        );

        executorService.submit(loadTask);
    }

    public void pathToMainPanel() throws IOException {
        Parent mainPanel = SceneCache.getScene("/com/discipline/drms/interfaces/body/main_panel.fxml");
        setCenterPanel(mainPanel);

        SceneCache.clearCache();
    }

    private void setCenterPanel(Parent panel) {
        Stage stage = (Stage) toMainPanel.getScene().getWindow();
        BorderPane root = (BorderPane) stage.getScene().getRoot();
        root.setCenter(panel);
    }
}
