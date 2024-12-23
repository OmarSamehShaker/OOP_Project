package com.example.myjavafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminHomePageController {
    private Database database = new Database();
    @FXML
    private Button view_profile;
    @FXML
    private Button update_profile;
    @FXML
    private Button view_customers;
    @FXML
    private Button add_product;
    @FXML
    private Button remove_product;
    @FXML
    private Button view_products;
    @FXML
    private Button logout_button;
    @FXML
    private Button exit_button;

    private Admin currentAdmin;

    
    public void initializeAdminData(Admin admin) {
        this.currentAdmin = admin;

        if (currentAdmin != null) {
            System.out.println("Welcome, " + currentAdmin.getUserName());
        }
    }

    
    private void navigateToPage(String fxmlFile, String pageTitle, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/myjavafx/" + fxmlFile));
            Scene scene = new Scene(loader.load());

            if (fxmlFile.equals("Admin_View_Profile.fxml")) {
                Admin_View_Profile_Controller controller = loader.getController();
                controller.initializeProfile(currentAdmin);
            } else if (fxmlFile.equals("Admin_Update_Profile.fxml")) {
                Admin_Update_Profile_Controller controller = loader.getController();
                controller.setCurrentAdmin(currentAdmin, database);
            }
            
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle(pageTitle);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Unable to load the page: " + e.getMessage());
        }
    }

    @FXML
    void handleViewProfile(ActionEvent event) {

        navigateToPage("Admin_View_Profile.fxml", "View Profile", event);
    }

    @FXML
    void handleUpdateProfile(ActionEvent event) {

        navigateToPage("Admin_Update_Profile.fxml", "Update Profile", event);
    }

    @FXML
    void handleViewCustomers(ActionEvent event) {
        navigateToPage("View_Customers.fxml", "View Customers", event);
    }

    @FXML
    void handleAddProduct(ActionEvent event) {
        navigateToPage("Add_Product.fxml", "Add Product", event);
    }

    @FXML
    void handleRemoveProduct(ActionEvent event) {
        navigateToPage("Remove_Product.fxml", "Remove Product", event);
    }

    @FXML
    void handleViewProducts(ActionEvent event) {
        navigateToPage("ProductsPage.fxml", "View Products", event);
    }

    @FXML
    void handleLogout(ActionEvent event) {
        Session.clearSession();
        showAlert(Alert.AlertType.INFORMATION, "Logout Successful", "You have been logged out.");
        navigateToPage("Login.fxml", "Login", event);
    }

    @FXML
    void handleExit(ActionEvent event) {
        showAlert(Alert.AlertType.INFORMATION, "Exiting Application", "The application will now close.");
      System.exit(0);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
