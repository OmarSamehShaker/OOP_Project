package com.example.myjavafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.layout.StackPane;

public class ProductsPageController {

    private Database db = new Database();
    private ArrayList<Products> products = db.getProducts();

    // UI components
    @FXML
    private Button returnButton; // Button to return to Admin home
    @FXML
    private StackPane rootPane; // The root container (StackPane)
    @FXML
    private VBox vboxContainer; // VBox containing the GridPane and Button
    @FXML
    private GridPane productGrid; // GridPane for displaying products
    @FXML
    private Label customerNameLabel; // Label to display customer name

    // Flag to ensure products are only loaded once
    private boolean productsLoaded = false;
    private Customer currentCustomer; // Customer object to hold the current customer

    // Method to initialize customer data
    public void initializeCustomerData(Customer customer) {
        this.currentCustomer = customer;
        customerNameLabel.setText("Welcome, " + customer.getUserName());
    }

    @FXML
    private void initialize() {
        // Prevent products from being loaded multiple times
        if (productsLoaded) {
            return;  // Skip loading products if already loaded
        }

        // Initialize the GridPane
        productGrid.getChildren().clear(); // Clear any previous content

        int row = 0;
        for (Products product : products) {
            // Create a VBox for each product (product card)
            VBox productCard = new VBox();
            productCard.setSpacing(10);
            productCard.setStyle("-fx-border-color: gray; -fx-border-radius: 5; -fx-padding: 10;");

            // Create and style the product name label
            Label nameLabel = new Label("Product: " + product.getName());
            nameLabel.setFont(new Font("Arial", 16));
            nameLabel.setTextFill(Color.BLACK);

            // Create and style the price label
            Label priceLabel = new Label("Price: $" + product.getPrice());
            priceLabel.setFont(new Font("Arial", 14));
            priceLabel.setTextFill(Color.BLACK);

            // Add the labels to the VBox (product card)
            productCard.getChildren().addAll(nameLabel, priceLabel);

            // Add the product card to the GridPane at the next row
            productGrid.add(productCard, 0, row++);
        }

        // Set the flag to true after loading products
        productsLoaded = true;
    }

    @FXML
    void handleReturn(ActionEvent event) {
        try {
            // Navigate back to Admin Home Page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminHomePage.fxml"));
            Stage stage = (Stage) returnButton.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
