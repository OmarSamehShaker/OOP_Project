package com.example.myjavafx;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import java.util.List;  // Import the List interface

public class ViewCustomersController {

    @FXML
    private TextArea customerInfoTextArea;  // Reference to the TextArea in FXML

    private Database database=new Database();



    // Handle the button click to display customer information
    @FXML
    public void handleViewCustomers() {
        // Clear previous content in TextArea

        customerInfoTextArea.clear();

        // Display customer info
        displayCustomerInfo(database);
    }

    // Display customer info in the TextArea
    private void displayCustomerInfo(Database database) {
        boolean foundCustomer = false;

        // Get users from the database
        List<User> users = database.getUsers();
        StringBuilder customerInfo = new StringBuilder(); // Use StringBuilder for efficient string concatenation

        for (User user : users) {
            if (user instanceof Customer) {
                Customer customer = (Customer) user;
                customerInfo.append("Customer Name: ").append(customer.getUserName()).append("\n");
                customerInfo.append("Customer Address: ").append(customer.getAddress()).append("\n");
                customerInfo.append("Customer Balance: ").append(customer.getBalance()).append("\n");
                customerInfo.append("Customer Gender: ").append(customer.getGender()).append("\n");

                // Safely handle interests
                String[] interests = customer.getinterests();
                if (interests == null || interests.length == 0) {
                    customerInfo.append("Customer Interests: No interests listed.\n");
                } else {
                    customerInfo.append("Customer Interests: ").append(String.join(", ", interests)).append("\n");
                }

                customerInfo.append("\n");
                foundCustomer = true;
            }
        }

        if (!foundCustomer) {
            customerInfo.append("No Customers Found\n");
        }

        // Set the customer information to the TextArea
        customerInfoTextArea.setText(customerInfo.toString());
    }
}
