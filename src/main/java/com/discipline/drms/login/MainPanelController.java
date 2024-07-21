package com.discipline.drms.login;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class MainPanelController {

    @FXML
    private VBox masterTables;

    @FXML
    private VBox reports;

    private String userRole;

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
