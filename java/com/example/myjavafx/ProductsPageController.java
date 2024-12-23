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

    @FXML
    private Button returnButton; 
    @FXML
    private StackPane rootPane; 
    @FXML
    private VBox vboxContainer; 
    @FXML
    private GridPane productGrid; 
    @FXML
    private Label customerNameLabel;

    private boolean productsLoaded = false;
    private Customer currentCustomer; 

    public void initializeCustomerData(Customer customer) {
        this.currentCustomer = customer;
        customerNameLabel.setText("Welcome, " + customer.getUserName());
    }

    @FXML
    private void initialize() {
        if (productsLoaded) {
            return;  
        }
        
        productGrid.getChildren().clear(); 

        int row = 0;
        for (Products product : products) {
            VBox productCard = new VBox();
            productCard.setSpacing(10);
            productCard.setStyle("-fx-border-color: gray; -fx-border-radius: 5; -fx-padding: 10;");

            Label nameLabel = new Label("Product: " + product.getName());
            nameLabel.setFont(new Font("Arial", 16));
            nameLabel.setTextFill(Color.BLACK);

            Label priceLabel = new Label("Price: $" + product.getPrice());
            priceLabel.setFont(new Font("Arial", 14));
            priceLabel.setTextFill(Color.BLACK);

            productCard.getChildren().addAll(nameLabel, priceLabel);
            productGrid.add(productCard, 0, row++);
        }
        productsLoaded = true;
    }

    @FXML
    void handleReturn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminHomePage.fxml"));
            Stage stage = (Stage) returnButton.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
