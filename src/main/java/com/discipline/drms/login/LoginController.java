package com.discipline.drms.login;

import com.discipline.drms.utils.sql.UserDAO;
import com.discipline.drms.utils.AlertUtil;
import com.discipline.drms.utils.User;
import com.discipline.drms.utils.PasswordUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginController {

    @FXML
    private TextField user;

    @FXML
    private PasswordField password;

    @FXML
    private Button login;

    private static final Logger LOGGER = Logger.getLogger(LoginController.class.getName());
    private final UserDAO userDAO = new UserDAO();

    @FXML
    private void handleLogin() {
        String username = user.getText();
        String passwordText = password.getText();

        if (username.isEmpty() || passwordText.isEmpty()) {
            AlertUtil.showAlertError("Validation Error", "Username or Password cannot be empty.");
            return;
        }

        try {
            User user = userDAO.getUserByUsername(username);
            if (user != null && PasswordUtil.verifyPassword(passwordText, user.getPasswordHash(), user.getSalt()) && user.isValidRole()) {
                loadMainPanel(user.getRole());
            } else {
                AlertUtil.showAlertError("Login Failed", "Invalid username or password.");
            }
        } catch (SQLException | IOException e) {
            LOGGER.log(Level.SEVERE, "Login failed", e);
            AlertUtil.showAlertError("Database Error", "An error occurred while connecting to the database.");
        }
    }

    private void loadMainPanel(String role) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/discipline/drms/interfaces/body/main_panel.fxml"));
        Parent mainPanel = loader.load();

        // Get the controller and set the role
        MainPanelController controller = loader.getController();
        controller.setUserRole(role);

        // Get the stage and set the new scene
        Stage stage = (Stage) login.getScene().getWindow();
        BorderPane root = (BorderPane) stage.getScene().getRoot();
        root.setCenter(mainPanel);
    }
}