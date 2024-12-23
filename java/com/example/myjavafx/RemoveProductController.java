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
    private ComboBox<Products> productComboBox;  

    @FXML
    private Button removeButton;

    @FXML
    private Button return_button;

    @FXML
    public void initialize() {
        loadProducts();
    }

    private void loadProducts() {
        List<Products> productsList = db.getProducts(); 
        productComboBox.getItems().clear();
        productComboBox.getItems().addAll(productsList);
    }

    @FXML
    void handleRemoveProduct(ActionEvent event) {
        Products selectedProduct = productComboBox.getValue();

        if (selectedProduct == null) {
            System.out.println("Please select a product to remove.");
            return;
        }

        boolean removed = db.removeProduct(selectedProduct.getId()); 

        if (removed) {
            System.out.println("Product removed successfully.");
            loadProducts(); 
        } else {
            System.out.println("Failed to remove the product.");
        }
    }

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
