package com.example.myjavafx;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class AdminLoginController {
    private Database database = new Database();

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    /**
     * Handles the admin login process.
     */
    @FXML
    private void handleLogin() {
        String enteredUsername = username.getText().trim();
        String enteredPassword = password.getText().trim();

        // Validate input fields
        if (enteredUsername.isEmpty() || enteredPassword.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Input Error", "Please fill in all fields.");
            return;
        }

        // Authenticate admin credentials
        Admin admin = (Admin) database.getUserByUsername(enteredUsername);
        if (admin != null && admin.getPassword().equals(enteredPassword)) {
            // Admin login successful
            System.out.println("Admin login successful!");
            AdminSession.setCurrentAdmin(admin); // Set the logged-in admin as the current admin
            navigateToAdminHomePage(admin);
        } else {
            // Admin login failed
            showAlert(Alert.AlertType.ERROR, "Login Error", "Invalid admin credentials.");
        }
    }

    /**
     * Navigates to the Admin Home Page and passes the logged-in admin's details.
     */
    private void navigateToAdminHomePage(Admin admin) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminHomePage.fxml"));
            Scene scene = new Scene(loader.load());

            // Pass the admin object to the AdminHomeController
            AdminHomePageController adminHomeController = loader.getController();
            adminHomeController.initializeAdminData(admin);

            Stage stage = (Stage) username.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Admin Home Page");
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Unable to load the Admin Home Page.");
        }
    }

    /**
     * Shows an alert dialog with the specified type, title, and content.
     */
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
