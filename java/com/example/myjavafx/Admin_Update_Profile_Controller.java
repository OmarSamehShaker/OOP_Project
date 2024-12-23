package com.example.myjavafx;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import java.util.ResourceBundle;
import java.net.URL;

public class Admin_Update_Profile_Controller {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField dobField;
    @FXML
    private TextField roleField;
    @FXML
    private TextField workingHoursField;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button returnButton;

    private Admin currentAdmin;
    private Database database;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        if (currentAdmin != null) {
            
            usernameField.setText(currentAdmin.getUserName());
            dobField.setText(currentAdmin.getDateOfBirth());
            roleField.setText(currentAdmin.getRole());
            workingHoursField.setText(currentAdmin.getWorkingHours());
        }
    }

    public void setCurrentAdmin(Admin admin, Database database) {
        this.currentAdmin = admin;
        this.database = database;
    }

    @FXML
    private void handleSaveButtonAction(ActionEvent event) {
        String username = usernameField.getText();
        String dob = dobField.getText();
        String role = roleField.getText();
        String workingHours = workingHoursField.getText();

        if (currentAdmin != null) {
            
            currentAdmin.setUserName(username);
            currentAdmin.setDateOfBirth(dob);
            currentAdmin.setRole(role);
            currentAdmin.setWorkingHours(workingHours);
            
            database.saveUsers();
            showAlert(Alert.AlertType.INFORMATION, "Profile Updated", "Admin profile updated successfully.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Unable to update profile. Admin data is missing.");
        }
    }

    @FXML
    private void handleCancelButtonAction(ActionEvent event) {
        if (currentAdmin != null) {
    
            usernameField.setText(currentAdmin.getUserName());
            dobField.setText(currentAdmin.getDateOfBirth());
            roleField.setText(currentAdmin.getRole());
            workingHoursField.setText(currentAdmin.getWorkingHours());
        }
    }

    @FXML
    private void handleReturnButtonAction(ActionEvent event) {
        navigateToPage("AdminHomePage.fxml", "Admin Home", event);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void navigateToPage(String fxmlFile, String pageTitle, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/myjavafx/" + fxmlFile));
            Scene scene = new Scene(loader.load());

            if (fxmlFile.equals("AdminHomePage.fxml")) {
                AdminHomePageController controller = loader.getController();
                controller.initializeAdminData(currentAdmin);
            }

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle(pageTitle);
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Unable to load the page: " + e.getMessage());
        }
    }
}
