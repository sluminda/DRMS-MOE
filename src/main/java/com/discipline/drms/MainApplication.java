package com.discipline.drms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainApplication extends Application {
    private static final Logger LOGGER = Logger.getLogger(MainApplication.class.getName());

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/discipline/drms/interfaces/combined/LandingPage.fxml"));
            BorderPane root = loader.load();
            primaryStage.setMinWidth(800);
            primaryStage.setMinHeight(600);

            // Load the login panel and set it as the center of the BorderPane
            FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/com/discipline/drms/interfaces/body/login_panel.fxml"));
            Parent loginPanel = loginLoader.load();
            root.setCenter(loginPanel);

            Scene scene = new Scene(root, 800, 600);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Hello!");
            primaryStage.show();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load application", e);
            showAlert();
        }
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Initialization Error");
        alert.setHeaderText(null);
        alert.setContentText("An error occurred while initializing the application.");
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
