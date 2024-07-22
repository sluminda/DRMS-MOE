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
    private VBox easternButton;

    @FXML
    private VBox southernButton;

    @FXML
    private VBox centralButton;

    @FXML
    private VBox northCentralButton;

    @FXML
    private VBox northernButton;

    @FXML
    private VBox sabaragamuwaButton;

    @FXML
    private VBox northWesternButton;

    @FXML
    private VBox uvaButton;

    @FXML
    public void initialize() {
        // Use the reusable KeyboardHandler to add a key event handler to the ImageView
        KeyboardHandler.addKeyHandler(toMainPanel, this::handleKeyPressed);

        // Add event handler for westernButton
        westernButton.setOnMouseClicked(this::handleWesternClick);
        easternButton.setOnMouseClicked(this::handleEasternClick);
        southernButton.setOnMouseClicked(this::handleSouthernClick);
        centralButton.setOnMouseClicked(this::handleCentralClick);
        northCentralButton.setOnMouseClicked(this::handleNorthCentralClick);
        northernButton.setOnMouseClicked(this::handleNorthernClick);
        sabaragamuwaButton.setOnMouseClicked(this::handleSabaragamuwaClick);
        northWesternButton.setOnMouseClicked(this::handleNorthWesternClick);
        uvaButton.setOnMouseClicked(this::handleUvaClick);

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

//    Western Area
    public void handleWesternClick(MouseEvent event) {
        try {
            Parent westernPanel = SceneCache.getScene("/com/discipline/drms/interfaces/body/province_tables/western.fxml");
            setCenterPanel(westernPanel);
        } catch (IOException e) {
            logger.error("Failed to load Western panel", e);
        }
    }

    //    Eastern Area
    public void handleEasternClick(MouseEvent event) {
        try {
            Parent easternPanel = SceneCache.getScene("/com/discipline/drms/interfaces/body/province_tables/eastern.fxml");
            setCenterPanel(easternPanel);
        } catch (IOException e) {
            logger.error("Failed to load Western panel", e);
        }
    }

    //    Southern Area
    public void handleSouthernClick(MouseEvent event) {
        try {
            Parent southernPanel = SceneCache.getScene("/com/discipline/drms/interfaces/body/province_tables/southern.fxml");
            setCenterPanel(southernPanel);
        } catch (IOException e) {
            logger.error("Failed to load Western panel", e);
        }
    }

    //    Central Area
    public void handleCentralClick(MouseEvent event) {
        try {
            Parent centralPanel = SceneCache.getScene("/com/discipline/drms/interfaces/body/province_tables/central.fxml");
            setCenterPanel(centralPanel);
        } catch (IOException e) {
            logger.error("Failed to load Western panel", e);
        }
    }

    //    North Central Area
    public void handleNorthCentralClick(MouseEvent event) {
        try {
            Parent northCentralPanel = SceneCache.getScene("/com/discipline/drms/interfaces/body/province_tables/northCentral.fxml");
            setCenterPanel(northCentralPanel);
        } catch (IOException e) {
            logger.error("Failed to load Western panel", e);
        }
    }

    //    Northern Area
    public void handleNorthernClick(MouseEvent event) {
        try {
            Parent northernPanel = SceneCache.getScene("/com/discipline/drms/interfaces/body/province_tables/northern.fxml");
            setCenterPanel(northernPanel);
        } catch (IOException e) {
            logger.error("Failed to load Western panel", e);
        }
    }

    //    Sabaragamuwa Area
    public void handleSabaragamuwaClick(MouseEvent event) {
        try {
            Parent sabaragamuwaPanel = SceneCache.getScene("/com/discipline/drms/interfaces/body/province_tables/sabaragamuwa.fxml");
            setCenterPanel(sabaragamuwaPanel);
        } catch (IOException e) {
            logger.error("Failed to load Western panel", e);
        }
    }

    //    North Western Area
    public void handleNorthWesternClick(MouseEvent event) {
        try {
            Parent northWesternPanel = SceneCache.getScene("/com/discipline/drms/interfaces/body/province_tables/northWestern.fxml");
            setCenterPanel(northWesternPanel);
        } catch (IOException e) {
            logger.error("Failed to load Western panel", e);
        }
    }

    //    Uva Area
    public void handleUvaClick(MouseEvent event) {
        try {
            Parent uvaPanel = SceneCache.getScene("/com/discipline/drms/interfaces/body/province_tables/uva.fxml");
            setCenterPanel(uvaPanel);
        } catch (IOException e) {
            logger.error("Failed to load Western panel", e);
        }
    }
}
