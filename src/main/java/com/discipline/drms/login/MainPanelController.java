package com.discipline.drms.login;

import com.discipline.drms.utils.SceneCache;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class MainPanelController {

    @FXML
    private VBox masterTables;

    @FXML
    private VBox reports;

    @FXML
    private VBox tableView;



    private String userRole;


    public void pathToProvinces() throws IOException {
        Parent provincesPanel = SceneCache.getScene("/com/discipline/drms/interfaces/body/provinces_panel.fxml");
        setCenterPanel(provincesPanel);
    }

    private void setCenterPanel(Parent panel) {
        Stage stage = (Stage) tableView.getScene().getWindow();
        BorderPane root = (BorderPane) stage.getScene().getRoot();
        root.setCenter(panel);
    }





    public void setUserRole(String role) {
        this.userRole = role;
        adjustVisibility();
    }

    private void adjustVisibility() {
        if ("Super Admin".equals(userRole)) {
            masterTables.setVisible(false);
            masterTables.setManaged(false);
        } else if ("Admin".equals(userRole)) {
            masterTables.setVisible(false);
            masterTables.setManaged(false);
            reports.setVisible(false);
            reports.setManaged(false);
        }
    }
}
