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

    
    private Admin currentAdmin;

    public void setAdmin(Admin admin) {
        this.currentAdmin = admin;
    }

    @FXML
    private void handleAddProduct(ActionEvent event) {
        System.out.println("Add Product button clicked.");
        String name = product_name.getText().trim();
        String idText = product_id.getText().trim();
        String priceText = product_price.getText().trim();
        String categoryText = category.getText().trim();

        if (name.isEmpty() || idText.isEmpty() || priceText.isEmpty() || categoryText.isEmpty()) {
            System.out.println("Please fill out all fields.");
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceText);
        } catch (NumberFormatException e) {
            System.out.println("Invalid price input.");
            return;
        }
        
        int productId;
        try {
            productId = Integer.parseInt(idText);
        } catch (NumberFormatException e) {
            System.out.println("Invalid product ID input.");
            return;
        }

        Products newProduct = new Products(name, price, categoryText, productId);
        db.addProduct(newProduct, currentAdmin);
        System.out.println("Product added successfully.");
    }


    @FXML
    public void handleReturn(ActionEvent event) {
        try {
            System.out.println("Returning to Admin Home page.");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminHomePage.fxml"));
            Stage stage = (Stage) returnButton.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();  
        }
    }
}
