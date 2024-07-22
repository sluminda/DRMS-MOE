package com.discipline.drms.daily_letters;

import com.discipline.drms.utils.SceneCache;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DailyLetters {

    @FXML
    private ImageView toMainPanel;

    public void pathToMainPanel() throws IOException {
        Parent mainPanel = SceneCache.getScene("/com/discipline/drms/interfaces/body/main_panel.fxml");
        setCenterPanel(mainPanel);
    }

    private void setCenterPanel(Parent panel) {
        Stage stage = (Stage) toMainPanel.getScene().getWindow();
        BorderPane root = (BorderPane) stage.getScene().getRoot();
        root.setCenter(panel);
    }
}
