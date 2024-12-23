package com.example.myjavafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomerHomePageController {

    // UI Components
    @FXML
    private Label welcomeLabel;

    @FXML
    private Button viewProductsButton;

    @FXML
    private Button viewProfileButton;

    @FXML
    private Button logoutButton;

    @FXML
    private VBox contentArea;

    @FXML
    private TextArea outputArea; // To display results in the UI (similar to MainController)

    private Customer currentCustomer;
    private Cart cart = new Cart(); // Cart object for managing the customer's cart
    private Database database = new Database(); // Database for storing customers and products

    // Method to initialize customer-specific data
    public void initializeCustomerData(Customer customer) {
        this.currentCustomer = customer;
        welcomeLabel.setText("Welcome, " + customer.getUserName());
    }

    @FXML
    void handleViewBalance() {
        if (currentCustomer != null) {
            double balance = currentCustomer.getBalance();
            showAlert(Alert.AlertType.INFORMATION, "Balance", "Your balance is: $" + balance);
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "No customer logged in.");
        }
    }

    @FXML
    void handleManageCart() {
        if (cart.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Cart", "Your cart is empty.");
        } else {
            StringBuilder cartDetails = new StringBuilder("Products in your cart:\n");
            for (Products product : cart.getProductsInCart()) {
                cartDetails.append(product.getName()).append(" - $").append(product.getPrice()).append("\n");
            }
            showAlert(Alert.AlertType.INFORMATION, "Cart", cartDetails.toString());
        }
    }

    @FXML
    void HandleViewProfile(ActionEvent event) {
        navigateToPage("Customer_View_Profile.fxml", "View Profile", event);
    }

    @FXML
    void HandleViewProducts(ActionEvent event) {
        navigateToPage("Customer_ProductsPage.fxml", "View Products", event);
    }

    @FXML
    void handleLogout(ActionEvent event) {
        Session.clearSession();
        showAlert(Alert.AlertType.INFORMATION, "Logout Successful", "You have been logged out.");
        navigateToPage("Login.fxml", "Login", event);
    }

    @FXML
    void handleCheckout() {
        try {
            if (cart.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Checkout Error", "Cannot checkout an empty cart.");
                return;
            }

            // Assume paymentMethod is chosen elsewhere (e.g., UI dropdown or input)
            String paymentMethod = "Balance";
            Order order = cart.checkoutCart(paymentMethod);

            if (order != null) {
                showAlert(Alert.AlertType.INFORMATION, "Checkout", "Checkout successful! Order ID: " + order.getOrderId());
            } else {
                showAlert(Alert.AlertType.ERROR, "Checkout Error", "Checkout failed.");
            }
        } catch (AllExceptionsMade.EmptyOrderException e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    /**
     * Helper method to show alerts with the given parameters
     */
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Navigate to another page
     * @param fxmlFile The FXML file for the page
     * @param pageTitle The title of the page
     * @param event The action event that triggered the navigation
     */
    private void navigateToPage(String fxmlFile, String pageTitle, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/myjavafx/" + fxmlFile));
            Scene scene = new Scene(loader.load());

            // If the page is for viewing products, pass the current customer to the ProductsPage controller
            if (fxmlFile.equals("ProductsPage.fxml")) {
                ProductsPageController controller = loader.getController();
                controller.initializeCustomerData(currentCustomer); // Pass current customer to the ProductsPage controller
            }

            // Get the stage from the button that was clicked
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle(pageTitle);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Unable to load the page: " + e.getMessage());
        }
    }
}
