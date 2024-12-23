package com.example.myjavafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

public class RemoveProductController {
    Database db = new Database();
    @FXML
    private ComboBox<Products> productComboBox;  // ComboBox for selecting a product to remove

    @FXML
    private Button removeButton;

    @FXML
    private Button return_button;

    // Initialize method to populate the ComboBox with products
    @FXML
    public void initialize() {
        loadProducts();
    }

    // Load products into the ComboBox
    private void loadProducts() {
        List<Products> productsList = db.getProducts();  // Fetch products from database

        // Clear any existing items and add products from the database
        productComboBox.getItems().clear();
        productComboBox.getItems().addAll(productsList);
    }

    // Handle remove product action
    @FXML
    void handleRemoveProduct(ActionEvent event) {
        Products selectedProduct = productComboBox.getValue();

        if (selectedProduct == null) {
            System.out.println("Please select a product to remove.");
            return;
        }

        // Perform the database operation to remove the selected product by ID


        // Remove the product by passing its ID
        boolean removed = db.removeProduct(selectedProduct.getId());  // Pass ID of selected product

        // Provide feedback to the user
        if (removed) {
            System.out.println("Product removed successfully.");
            loadProducts();  // Reload the ComboBox after removal
        } else {
            System.out.println("Failed to remove the product.");
        }
    }

    // Handle return action (navigate back to the Admin Home page)
    @FXML
    void handleReturn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminHomePage.fxml"));
            Stage stage = (Stage) return_button.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
