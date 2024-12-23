package com.example.myjavafx;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Add_Product_Controller {
    Database db = new Database();
    @FXML
    private TextField product_name;
    @FXML
    private TextField product_id;
    @FXML
    private TextField product_price;
    @FXML
    private TextField category;
    @FXML
    private Button addProductButton;
    @FXML
    private Button returnButton;

    // Assuming you have an admin object already passed to this controller
    private Admin currentAdmin;

    // This method should be called when this controller is initialized, passing in the Admin object
    public void setAdmin(Admin admin) {
        this.currentAdmin = admin;
    }

    // Handle add product action
    @FXML
    private void handleAddProduct(ActionEvent event) {
        System.out.println("Add Product button clicked.");

        // Retrieve product details from the text fields
        String name = product_name.getText().trim();
        String idText = product_id.getText().trim();
        String priceText = product_price.getText().trim();
        String categoryText = category.getText().trim();

        // Validate input
        if (name.isEmpty() || idText.isEmpty() || priceText.isEmpty() || categoryText.isEmpty()) {
            System.out.println("Please fill out all fields.");
            return;
        }

        // Validate price input
        double price;
        try {
            price = Double.parseDouble(priceText);
        } catch (NumberFormatException e) {
            System.out.println("Invalid price input.");
            return;
        }

        // Parse the product ID and create the product object
        int productId;
        try {
            productId = Integer.parseInt(idText);
        } catch (NumberFormatException e) {
            System.out.println("Invalid product ID input.");
            return;
        }

        // Create a new product instance
        Products newProduct = new Products(name, price, categoryText, productId);


        // Add the product directly without using a thread
        db.addProduct(newProduct, currentAdmin);

        // Print success message
        System.out.println("Product added successfully.");
    }

    // Handle return action (navigate back to the Admin Home page)
    @FXML
    public void handleReturn(ActionEvent event) {
        try {
            System.out.println("Returning to Admin Home page.");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminHomePage.fxml"));
            Stage stage = (Stage) returnButton.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();  // Print error if loading the Admin Home page fails
        }
    }
}
