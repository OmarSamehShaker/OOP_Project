package com.example.myjavafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Admin_Customer_Screen_Controller {

    @FXML
    private Button adminLoginButton;

    @FXML
    private Button customerLoginButton;

    @FXML
    private Button customerSignupButton;


    private void navigateToPage(String fxmlFile, String pageTitle, Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setTitle(pageTitle);
            stage.show();
        } catch (IOException e) {
            showAlert("Navigation Error", "Unable to load the page: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleAdminLogin(ActionEvent event) {
        Stage stage = (Stage) adminLoginButton.getScene().getWindow();
        navigateToPage("login.fxml", "Admin Login", stage);
    }

    @FXML
    private void handleCustomerLogin(ActionEvent event) {
        Stage stage = (Stage) customerLoginButton.getScene().getWindow();
        navigateToPage("login.fxml", "Customer Login", stage);
    }

    @FXML
    private void handleCustomerSignup(ActionEvent event) {
        Stage stage = (Stage) customerSignupButton.getScene().getWindow();
        navigateToPage("signup.fxml", "Customer Signup", stage);
    }
}
