package com.example.myjavafx;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class LoginController {
    private Database database = new Database();

    @FXML
    private ComboBox<String> combo_box;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    public void initialize() {
        // Populate ComboBox with options (Admin, Customer)
        combo_box.getItems().addAll("Admin", "Customer");
        combo_box.getSelectionModel().selectFirst(); // Default selection is "Admin"
    }

    @FXML
    private void handleLogin() {
        String enteredUsername = username.getText().trim();
        String enteredPassword = password.getText().trim();
        String selectedUserType = combo_box.getValue();

        // Validate input fields
        if (enteredUsername.isEmpty() || enteredPassword.isEmpty() || selectedUserType == null) {
            showAlert(Alert.AlertType.WARNING, "Input Error", "Please fill in all fields.");
            return;
        }

        // Handle login logic
        if ("Admin".equals(selectedUserType)) {
            handleAdminLogin(enteredUsername, enteredPassword);
        } else if ("Customer".equals(selectedUserType)) {
            handleCustomerLogin(enteredUsername, enteredPassword);
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Error", "Invalid user type selected.");
        }
    }

    private void handleAdminLogin(String username, String password) {
        Admin admin = (Admin) database.getUserByUsername(username); // Fetch admin from database
        if (admin != null && admin.getPassword().equals(password)) {
            // Admin login successful
            System.out.println("Admin login successful!");
            navigateToPage("/com/example/myjavafx/AdminHomePage.fxml", "Admin Home Page", admin);
        } else {
            // Admin login failed
            showAlert(Alert.AlertType.ERROR, "Login Error", "Invalid admin credentials.");
        }
    }

    private void handleCustomerLogin(String username, String password) {
        Customer customer = (Customer)database.getUserByUsername(username); // Fetch customer from database
        if (customer != null && customer.getPassword().equals(password)) {
            // Customer login successful
            System.out.println("Customer login successful!");
            navigateToPage("CustomerHomePage.fxml", "Customer Home Page",customer);
        } else {
            // Customer login failed
            showAlert(Alert.AlertType.ERROR, "Login Error", "Invalid customer credentials.");
        }
    }

    private void navigateToPage(String fxmlFile, String pageTitle, Object user) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            Scene scene = new Scene(fxmlLoader.load());

            // Pass the user object to the appropriate controller
            if (fxmlFile.equals("AdminHomePage.fxml")) {
                AdminHomePageController controller = fxmlLoader.getController();
                controller.initializeAdminData((Admin) user);
            } else if (fxmlFile.equals("CustomerHomePage.fxml")) {
                CustomerHomePageController controller = fxmlLoader.getController();
                controller.initializeCustomerData((Customer) user);
            }

            Stage stage = (Stage) username.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle(pageTitle);
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Unable to load the requested page.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
