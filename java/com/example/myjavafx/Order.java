package com.example.myjavafx;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Order implements Serializable {
    private static final long serialVersionUID = 11L;
    private static int orderCount = 0;
    private int orderId;
    private Date orderDate;
    private List<Products> orderItems;
    private String paymentMethod;
    private String status;

    public Order(List<Products> orderItems, String paymentMethod) throws AllExceptionsMade.EmptyOrderException {
        if (orderItems == null || orderItems.isEmpty()) {
            throw new AllExceptionsMade.EmptyOrderException("Order must contain at least one product.");
        }
        this.orderId = ++orderCount;
        this.orderDate = new Date();
        this.orderItems = orderItems;
        this.paymentMethod = paymentMethod;
        this.status = "Pending";
    }

    public void createOrder() {
        System.out.println("Order " + orderId + " created on " + orderDate);
        for (Products orderItem : orderItems) {
            if (orderItem != null) {
                System.out.println(orderItem);
            }
        }
    }

    public boolean processPayment(Customer customer, Scanner scanner) throws AllExceptionsMade.PaymentException {
        double totalAmount = getTotalPrice();

        System.out.println("Your Total is: $" + totalAmount);
        System.out.println("Your balance is: $" + customer.getBalance());

        System.out.println("Choose Payment Method");
        System.out.println("1. Credit Card");
        System.out.println("2. PayPal");
        System.out.println("3. Balance");

        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1: {
                System.out.println("Enter your Credit Card Number");
                String creditCard = scanner.nextLine().trim();
                System.out.println("Enter your Credit Card Expiration Date");
                String expirationDate = scanner.nextLine().trim();
                System.out.println("Enter your Credit Card CVV");
                String cvv = scanner.nextLine().trim();
                if (validateCreditCard(creditCard, expirationDate, cvv)) {
                    System.out.println("Payment successful using Credit Card!");
                    customer.setBalance(customer.getBalance() - totalAmount);
                    this.paymentMethod = "Credit Card";
                    this.status = "Paid";
                    printReceipt();
                } else {
                    System.out.println("Invalid credit card details. Payment failed.");
                }
                break;
            }
            case 2: {
                System.out.println("Enter your PayPal Email");
                String paypalEmail = scanner.nextLine().trim();
                if (validatePayPal(paypalEmail)) {
                    System.out.println("Payment successful using PayPal!");
                    customer.setBalance(customer.getBalance() - totalAmount);
                    this.paymentMethod = "PayPal";
                    this.status = "Paid";
                    printReceipt();
                } else {
                    System.out.println("Invalid PayPal email. Payment failed.");
                }
                break;
            }
            case 3: {
                if (customer.getBalance() >= totalAmount) {
                    customer.setBalance(customer.getBalance() - totalAmount);
                    System.out.println("Payment successful using Balance. Remaining balance: $" + customer.getBalance());
                    this.paymentMethod = "Balance";
                    this.status = "Paid";
                    printReceipt();
                } else {
                    System.out.println("Insufficient Balance for Payment.");
                }
                break;
            }
            default: {
                System.out.println("Invalid payment method.");
            }
        }

        if (this.status.equals("Paid")) {
            System.out.println("Order " + orderId + " has been successfully placed!");
            printReceipt();
        } else {
            System.out.println("Order " + orderId + " could not be placed due to payment issues.");
        }
        return false;
    }

    private boolean validatePayPal(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return email.matches(emailRegex) && email.endsWith("@paypal.com");
    }

    private boolean validateCreditCard(String cardNumber, String expiryDate, String cvv) {
        return cardNumber.length() == 16 && cvv.length() == 3 && expiryDate.matches("\\d{2}/\\d{2}");
    }

    public double getTotalPrice() {
        double total = 0;
        for (Products product : orderItems) {
            total += product.getPrice();
        }
        return total;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getStatus() {
        return status;
    }

    public void viewOrder() {
        System.out.println("Order ID: " + orderId);
        System.out.println("Order Date: " + orderDate);
        for (Products product : orderItems) {
            if (product != null) {
                System.out.println(product);
            }
        }
        System.out.println("Payment Method: " + paymentMethod);
        System.out.println("Order Status: " + status);
    }

    public void printReceipt() {
        System.out.println("\n--- Receipt ---");
        System.out.println("Order ID: " + orderId);
        System.out.println("Order Date: " + orderDate);
        System.out.println("Payment Method: " + paymentMethod);
        System.out.println("Status: " + status);

        System.out.println("\nItems Purchased:");
        for (Products product : orderItems) {
            if (product != null) {
                System.out.println(product.getName() + " - $" + product.getPrice());
            }
        }
        System.out.println("\nTotal: $" + getTotalPrice());
        System.out.println("Thank you for your purchase!");
    }
}