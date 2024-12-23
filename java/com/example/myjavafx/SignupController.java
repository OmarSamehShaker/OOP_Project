package com.example.myjavafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class SignupController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField interestsField;

    @FXML
    private TextField addressField;

    @FXML
    private DatePicker dobPicker;

    @FXML
    private ComboBox<Customer.Gender> genderComboBox;

    @FXML
    private Button sign_up;

    private final Database database = new Database(); // Assume Database is already initialized

    @FXML
    public void initialize() {
        // Populate the gender combo box with enum values
        genderComboBox.getItems().setAll(Customer.Gender.values());
    }

    @FXML
    void handleSignup(ActionEvent event) {
        try {
            // Collect field data
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            String address = addressField.getText().trim();
            String dob = dobPicker.getValue() != null ? dobPicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : "";
            String interestsInput = interestsField.getText().trim();
            String[] interests = interestsInput.isEmpty() ? new String[0] : interestsInput.split(",\\s*");
            Customer.Gender gender = genderComboBox.getValue();

            // Validate all fields
            if (username.isEmpty() || password.isEmpty() || address.isEmpty() || dob.isEmpty() || gender == null) {
                showAlert(AlertType.ERROR, "Validation Error", "All fields are required!");
                return;
            }

            if (password.length() < 6) {
                showAlert(AlertType.ERROR, "Validation Error", "Password must be at least 6 characters long.");
                return;
            }

            // Create a new Cart instance for the customer
            Cart newCart = new Cart();

            // Default balance for a new customer (modify as needed)
            double balance = 0.0;

            // Create and add the new customer to the database
            Customer newCustomer = new Customer(username, password, dob, address, interests, balance, gender, newCart);
            database.addUser(newCustomer);

            // Show success message
            showAlert(AlertType.INFORMATION, "Signup Successful", "User has been added to the database.");

            // Navigate to the next page
            goToNextPage();

        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error", "An error occurred during signup: " + e.getMessage());
        }
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void goToNextPage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SignupController.class.getResource("Login.fxml")); // Replace with your next page's FXML file
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) usernameField.getScene().getWindow(); // Get the current stage
            stage.setScene(scene);
            stage.setTitle("Next Page"); // Update the title as needed
            stage.show();
        } catch (IOException e) {
            showAlert(AlertType.ERROR, "Navigation Error", "Unable to load the next page: " + e.getMessage());
        }
    }
}
