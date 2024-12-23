package com.example.myjavafx;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import java.util.List;  

public class ViewCustomersController {

    @FXML
    private TextArea customerInfoTextArea;  

    private Database database=new Database();
    
    @FXML
    public void handleViewCustomers() {
        customerInfoTextArea.clear();
        displayCustomerInfo(database);
    }
    
    private void displayCustomerInfo(Database database) {
        boolean foundCustomer = false;

        List<User> users = database.getUsers();
        StringBuilder customerInfo = new StringBuilder(); 

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

        
        customerInfoTextArea.setText(customerInfo.toString());
    }
}
