package com.discipline.drms.daily_letters;

import com.discipline.drms.utils.SceneCache;
import com.discipline.drms.utils.sql.DatabaseConnection;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DailyLetters {

    @FXML
    private ImageView toMainPanel;

    @FXML
    private TextField letterNo;

    @FXML
    private TextArea senderName;

    @FXML
    private TextField letterFileNo;

    @FXML
    private DatePicker letterDate;

    @FXML
    private ComboBox<String> letterType;

    @FXML
    private TextArea letterSubject;

    @FXML
    private ComboBox<String> subjectClerk;

    @FXML
    private DatePicker receivedDate;

    @FXML
    private TextField fileRelatedToLetter;

    @FXML
    private ComboBox<String> actionTaken;

    @FXML
    private DatePicker dateOfActionTaken;

    @FXML
    private TextArea specialNote;

    @FXML
    private Label formResult;

    @FXML
    private VBox resultBox;

    @FXML
    private Button submitForm;


    @FXML
    private void initialize() {

        populateComboBox("SELECT type FROM LetterTypes", letterType);
        populateComboBox("SELECT name FROM SubjectClerks", subjectClerk);
        populateComboBox("SELECT action FROM ActionTakens", actionTaken);
        setNextLetterId();

        resultBox.setVisible(false);
        resultBox.setManaged(false);

        addValidationListeners();
    }


    private static final Logger logger = LoggerFactory.getLogger(DailyLetters.class);

    private void populateComboBox(String query, ComboBox<String> comboBox) {
        ObservableList<String> data = FXCollections.observableArrayList();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                data.add(resultSet.getString(1));
            }

        } catch (SQLException e) {
            logger.error("Error populating ComboBox with query: {}", query, e);
        }
        comboBox.setItems(data);
    }

    private void setNextLetterId() {
        String query = "SELECT IFNULL(MAX(id), 0) + 1 AS nextId FROM Letters";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                int nextId = resultSet.getInt("nextId");
                letterNo.setText(String.valueOf(nextId));
            }

        } catch (SQLException e) {
            logger.error("Error fetching next letter ID", e);
        }
    }

    @FXML
    private void submitFormClicked() {
        if (!validateForm()) {
            formResult.setText("Please fill all required fields.");
            displayResultBox();
            return;
        }

        String insertQuery = "INSERT INTO Letters (senderName, letterFileNo, letterDate, letterTypeId, letterSubject, subjectClerkId, receivedDate, fileRelatedToLetter, actionTakenId, dateOfActionTaken, specialNote) " +
                "VALUES (?, ?, ?, (SELECT id FROM LetterTypes WHERE type = ?), ?, (SELECT id FROM SubjectClerks WHERE name = ?), ?, ?, (SELECT id FROM ActionTakens WHERE action = ?), ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(insertQuery)) {

            statement.setString(1, senderName.getText());
            statement.setString(2, letterFileNo.getText());
            statement.setDate(3, java.sql.Date.valueOf(letterDate.getValue()));
            statement.setString(4, letterType.getValue());
            statement.setString(5, letterSubject.getText());
            statement.setString(6, subjectClerk.getValue());
            statement.setDate(7, java.sql.Date.valueOf(receivedDate.getValue()));
            statement.setString(8, fileRelatedToLetter.getText());
            statement.setString(9, actionTaken.getValue());
            statement.setDate(10, java.sql.Date.valueOf(dateOfActionTaken.getValue()));
            statement.setString(11, specialNote.getText());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                formResult.setText("Form submitted successfully!");
                clearForm();
                setNextLetterId();
            } else {
                formResult.setText("Form submission failed.");
            }
        } catch (SQLException e) {
            formResult.setText("Error submitting form.");
            logger.error("Error saving form data", e);
        }
        displayResultBox();
    }

    @FXML
    private void resetFormClicked() {
        clearForm();
        formResult.setText("Form reset.");
        displayResultBox();
    }

    private void clearForm() {
        senderName.clear();
        letterFileNo.clear();
        letterDate.setValue(null);
        letterType.getSelectionModel().clearSelection();
        letterSubject.clear();
        subjectClerk.getSelectionModel().clearSelection();
        receivedDate.setValue(null);
        fileRelatedToLetter.clear();
        actionTaken.getSelectionModel().clearSelection();
        dateOfActionTaken.setValue(null);
        specialNote.clear();

        // Re-set prompt text to the combo boxes
        letterType.setPromptText("Select Letter Type");
        subjectClerk.setPromptText("Select Subject Clerk");
        actionTaken.setPromptText("Select Action Taken");

        // Re-validate the form to disable submit button if needed
        validateForm();
    }




    private void displayResultBox() {
        resultBox.setVisible(true);
        resultBox.setManaged(true);
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(() -> Platform.runLater(() -> {
            resultBox.setVisible(false);
            resultBox.setManaged(false);
        }), 5, TimeUnit.SECONDS);
        executorService.shutdown();
    }

    private void addValidationListeners() {
        ChangeListener<Object> validationListener = (observable, oldValue, newValue) -> validateForm();

        senderName.textProperty().addListener(validationListener);
        letterFileNo.textProperty().addListener(validationListener);
        letterDate.valueProperty().addListener(validationListener);
        letterType.valueProperty().addListener(validationListener);
        letterSubject.textProperty().addListener(validationListener);
        subjectClerk.valueProperty().addListener(validationListener);
        receivedDate.valueProperty().addListener(validationListener);
        fileRelatedToLetter.textProperty().addListener(validationListener);
        actionTaken.valueProperty().addListener(validationListener);
        dateOfActionTaken.valueProperty().addListener(validationListener);

        validateForm();
    }

    private boolean validateForm() {
        boolean valid = !senderName.getText().trim().isEmpty() &&
                !letterFileNo.getText().trim().isEmpty() &&
                letterDate.getValue() != null &&
                letterType.getValue() != null &&
                !letterSubject.getText().trim().isEmpty() &&
                subjectClerk.getValue() != null &&
                receivedDate.getValue() != null &&
                !fileRelatedToLetter.getText().trim().isEmpty() &&
                actionTaken.getValue() != null &&
                dateOfActionTaken.getValue() != null;

        submitForm.setDisable(!valid);
        return valid;
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
