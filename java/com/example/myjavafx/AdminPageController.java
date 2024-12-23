package com.example.myjavafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.HashMap;

public class AdminPageController {

    // FXML references
    @FXML private ListView<Product> productList;
    @FXML private TextField nameField;
    @FXML private TextField priceField;

    @FXML private ListView<String> customerList;
    @FXML private TextField customerInfoField;

    @FXML private ListView<Order> orderList;
    @FXML private TextField statusField;

    @FXML private ListView<String> roleList;
    @FXML private TextField roleField;

    // Observable lists for better synchronization with UI
    private final ObservableList<Product> products = FXCollections.observableArrayList();
    private final ObservableList<String> customers = FXCollections.observableArrayList();
    private final ObservableList<Order> orders = FXCollections.observableArrayList();
    private final ObservableList<String> roles = FXCollections.observableArrayList();
    private final HashMap<String, String> customerInfoMap = new HashMap<>();
    private final HashMap<String, String> rolesMap = new HashMap<>();

    @FXML
    public void initialize() {
        // Set observable lists to ListView controls
        productList.setItems(products);
        customerList.setItems(customers);
        orderList.setItems(orders);
        roleList.setItems(roles);

        // Example data (optional, for testing)
        products.add(new Product("Example Product", 10.0));
        customers.add("John Doe");
        customerInfoMap.put("John Doe", "Contact: johndoe@example.com");
        orders.add(new Order("Order #1", "Pending"));
        roles.add("Admin");
        rolesMap.put("Admin", "Full Access");
    }

    // Product Tab Actions
    @FXML
    private void addProduct() {
        String name = nameField.getText();
        String price = priceField.getText();
        try {
            if (!name.isEmpty() && !price.isEmpty()) {
                double priceValue = Double.parseDouble(price);
                Product product = new Product(name, priceValue);
                products.add(product);
                nameField.clear();
                priceField.clear();
            } else {
                showError("Product Error", "Please fill in both product name and price.");
            }
        } catch (NumberFormatException e) {
            showError("Price Error", "Price must be a valid number.");
        }
    }

    @FXML
    private void removeSelectedProduct() {
        Product selected = productList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            products.remove(selected);
        } else {
            showError("Selection Error", "No product selected to remove.");
        }
    }

    // Customer Tab Actions
    @FXML
    private void updateCustomerInfo() {
        String selectedCustomer = customerList.getSelectionModel().getSelectedItem();
        String info = customerInfoField.getText();
        if (selectedCustomer != null && !info.isEmpty()) {
            customerInfoMap.put(selectedCustomer, info);
            customerInfoField.clear();
        } else {
            showError("Update Error", "Select a customer and enter valid info.");
        }
    }

    // Order Tab Actions
    @FXML
    private void updateOrderStatus() {
        Order selectedOrder = orderList.getSelectionModel().getSelectedItem();
        String status = statusField.getText();
        if (selectedOrder != null && !status.isEmpty()) {
            selectedOrder.setStatus(status);
            orderList.refresh(); // Refresh to show updated status
            statusField.clear();
        } else {
            showError("Update Error", "Select an order and enter a valid status.");
        }
    }

    // Role Tab Actions
    @FXML
    private void updateRole() {
        String selectedUser = roleList.getSelectionModel().getSelectedItem();
        String newRole = roleField.getText();
        if (selectedUser != null && !newRole.isEmpty()) {
            rolesMap.put(selectedUser, newRole);
            roleField.clear();
        } else {
            showError("Update Error", "Select a role and enter a valid new role.");
        }
    }

    // Helper Method: Show Error Dialog
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Helper Classes
    static class Product {
        private final String name;
        private final double price;

        public Product(String name, double price) {
            this.name = name;
            this.price = price;
        }

        @Override
        public String toString() {
            return name + " - $" + price;
        }
    }

    static class Order {
        private final String details;
        private String status;

        public Order(String details, String status) {
            this.details = details;
            this.status = status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return details + " - Status: " + status;
        }
    }
}
