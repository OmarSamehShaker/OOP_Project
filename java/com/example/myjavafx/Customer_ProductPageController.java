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
    private GridPane grid; 

    private Database database;
    private Cart cart;  

    public void initialize() {
        database = new Database();  
        cart = new Cart();  
        
        database.loadProducts();
        int numColumns = 3; 
        
        for (int i = 0; i < database.getProducts().size(); i++) {
            Products product = database.getProducts().get(i);

            Label nameLabel = new Label(product.getName());
            Label categoryLabel = new Label("Category: " + product.getCategory());
            Label priceLabel = new Label("Price: $" + product.getPrice());
            Button detailsButton = new Button("Details");
            Button addToCartButton = new Button("Add to Cart");

            addToCartButton.setOnAction(e -> {
                try {
                    cart.addProductToCart(product);
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Item added to cart!");
                } catch (AllExceptionsMade.CartFullException ex) {
                    showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
                }
            });
            
            detailsButton.setOnAction(e -> {
                showAlert(Alert.AlertType.INFORMATION, "Product Details",
                        "Name: " + product.getName() +
                                "\nCategory: " + product.getCategory() +
                                "\nPrice: $" + product.getPrice());
            });

            VBox productBox = new VBox(10);
            productBox.getChildren().addAll(nameLabel, categoryLabel, priceLabel, detailsButton, addToCartButton);
            productBox.setAlignment(Pos.CENTER);
            productBox.setStyle("-fx-border-color: gray; -fx-border-width: 1; -fx-padding: 10;");

            GridPane.setHgrow(productBox, Priority.ALWAYS);
            GridPane.setVgrow(productBox, Priority.ALWAYS);
            
            grid.add(productBox, i % numColumns, i / numColumns);
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
