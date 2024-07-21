package com.discipline.drms.login;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    @FXML
    private void handleLogin() {
        String username = user.getText();
        String passwordText = password.getText();

        if (username.isEmpty() || passwordText.isEmpty()) {
            showAlert("Validation Error", "Username or Password cannot be empty.");
            return;
        }

        String url = "jdbc:mysql://localhost:3306/drms";
        String dbUser = "root";
        String dbPassword = "1220";

        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword)) {
            String sql = "SELECT * FROM users WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password_hash");
                String salt = rs.getString("salt");
                String role = rs.getString("role");

                if (PasswordUtil.verifyPassword(passwordText, storedHash, salt) && isValidRole(role)) {
                    loadMainPanel(role);
                } else {
                    showAlert("Login Failed", "Invalid username or password.");
                }
            } else {
                showAlert("Login Failed", "Invalid username or password.");
            }

        } catch (SQLException | IOException e) {
            LOGGER.log(Level.SEVERE, "Login failed", e);
            showAlert("Database Error", "An error occurred while connecting to the database.");
        }
    }

    private boolean isValidRole(String role) {
        return role.equals("Owner") || role.equals("Super Admin") || role.equals("Admin");
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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
