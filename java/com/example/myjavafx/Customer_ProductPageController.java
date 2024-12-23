package com.example.myjavafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;

public class Customer_ProductPageController {

    @FXML
    private GridPane grid; // Reference to the GridPane in the FXML

    private Database database;  // Database object to manage products and users
    private Cart cart;  // Cart object to manage user cart functionality

    public void initialize() {
        database = new Database();  // Initialize database
        cart = new Cart();  // Initialize the cart

        // Load products from the database
        database.loadProducts();

        // Add products to the grid
        int numColumns = 3; // Number of columns
        for (int i = 0; i < database.getProducts().size(); i++) {
            Products product = database.getProducts().get(i);

            Label nameLabel = new Label(product.getName());
            Label categoryLabel = new Label("Category: " + product.getCategory());
            Label priceLabel = new Label("Price: $" + product.getPrice());
            Button detailsButton = new Button("Details");
            Button addToCartButton = new Button("Add to Cart");

            // Add an action handler for the "Add to Cart" button
            addToCartButton.setOnAction(e -> {
                try {
                    cart.addProductToCart(product);
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Item added to cart!");
                } catch (AllExceptionsMade.CartFullException ex) {
                    showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
                }
            });

            // Add an action handler for the "Details" button
            detailsButton.setOnAction(e -> {
                showAlert(Alert.AlertType.INFORMATION, "Product Details",
                        "Name: " + product.getName() +
                                "\nCategory: " + product.getCategory() +
                                "\nPrice: $" + product.getPrice());
            });

            // Create a VBox for each product
            VBox productBox = new VBox(10);
            productBox.getChildren().addAll(nameLabel, categoryLabel, priceLabel, detailsButton, addToCartButton);
            productBox.setAlignment(Pos.CENTER);
            productBox.setStyle("-fx-border-color: gray; -fx-border-width: 1; -fx-padding: 10;");

            // Allow productBox to grow with the grid cell
            GridPane.setHgrow(productBox, Priority.ALWAYS);
            GridPane.setVgrow(productBox, Priority.ALWAYS);

            // Add productBox to the grid
            grid.add(productBox, i % numColumns, i / numColumns); // Arrange in columns and rows
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
