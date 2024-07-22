package com.discipline.drms.province_panel;

import com.discipline.drms.utils.SceneCache;
import com.discipline.drms.utils.KeyboardHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

public class ProvincesPanel {

    private static final Logger logger = LoggerFactory.getLogger(ProvincesPanel.class);

    @FXML
    private ImageView toMainPanel;

    @FXML
    private VBox westernButton;

    @FXML
    public void initialize() {
        // Use the reusable KeyboardHandler to add a key event handler to the ImageView
        KeyboardHandler.addKeyHandler(toMainPanel, this::handleKeyPressed);

        // Add event handler for westernButton
        westernButton.setOnMouseClicked(this::handleWesternClick);
    }

    private void handleKeyPressed(KeyEvent event) {
        if (Objects.requireNonNull(event.getCode()) == KeyCode.BACK_SPACE) {
            try {
                pathToMainPanel();
            } catch (IOException e) {
                logger.error("Failed to navigate to main panel", e);
            }
        }
    }

    public void pathToMainPanel() throws IOException {
        Parent mainPanel = SceneCache.getScene("/com/discipline/drms/interfaces/body/main_panel.fxml");
        setCenterPanel(mainPanel);
    }

    private void setCenterPanel(Parent panel) {
        Stage stage = (Stage) toMainPanel.getScene().getWindow();
        BorderPane root = (BorderPane) stage.getScene().getRoot();
        root.setCenter(panel);
    }

    public void handleWesternClick(MouseEvent event) {
        try {
            Parent westernPanel = SceneCache.getScene("/com/discipline/drms/interfaces/body/province_tables/western.fxml");
            setCenterPanel(westernPanel);
        } catch (IOException e) {
            logger.error("Failed to load Western panel", e);
        }
    }
}
