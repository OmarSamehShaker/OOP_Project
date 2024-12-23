package com.example.myjavafx;

        import java.util.Scanner;
        public class Customer extends User implements Authentication {
            private static final long serialVersionUID = 3767774409717585518L;
            private String Address;
            private String []interests;
            private double balance;
            private Gender gender;
            private Cart cart;
            Database database = new Database() ;

            public Customer(){}
        public Customer(String userName,String password, String dateOfBirth,String Address,String []interests, double balance,Gender gender , Cart cart){
            super(userName,password,dateOfBirth);
            this.Address = Address;
            this.interests = interests;
            this.balance = balance;
            this.gender = gender;
            this.cart = cart;
        }



            public enum Gender{
            MALE,FEMALE;
            }
            public void setAddress(String Address){
                this.Address = Address;
            }
            public void setinterests(String []interests){
                this.interests = interests;
            }
            public void setBalance(double balance){
                this.balance = balance;
            }
            public void setGender(Gender gender){
                this.gender = gender;
            }
            public String getAddress(){
                return Address;
            }
            public String []getinterests(){
                return interests;
            }
            public double getBalance(){
                return balance;
            }
            public Gender getGender(){
                return gender;
            }
            public Cart getCart(){
                return cart;
            }

            @Override
            public void displayMenu() {
                System.out.println("Customer Menu");
                System.out.println("1. View Profile");
                System.out.println("2. View Products");
                System.out.println("3. View Balance");
                System.out.println("4. Manage Cart");
                System.out.println("5. Update Profile");
                System.out.println("6. CheckOut");
                System.out.println("7. Delete Account");
                System.out.println("8. Exit");
            }
            @Override
            public void displayProfile() {
                System.out.println("Customer Profile: " + getUserName());
                System.out.println("Address: " + getAddress());
                System.out.println("Balance: "+balance+"$");
                System.out.println("Interests: "+String.join(", ",interests));
                System.out.println("Gender: "+getGender());

            }
        
        
        @Override
            public void login(String username,String password,Database database){
                if(database.checkUser(username,password)){
                    System.out.println("Login Success");
                    displayProfile();
                }
            }
        
       
        
            @Override
            public boolean deleteAccount(Database database) {
                return database.removeUser(getUserName());
            }
            @Override
            public void updateProfile(String field, String newValue,Database database) {
            switch (field) {
                case "username" -> setUserName(newValue);
                case "address" -> setAddress(newValue);
                case "date of birth" -> setDateOfBirth(newValue);
                case "interests" -> interests = newValue.split(", ");
                default -> System.out.println("INVALID FIELD!!");
            }
            User ExistingCustomer = database.getUserByUsername(getUserName());
            if(ExistingCustomer!=null&&ExistingCustomer instanceof Customer){
                Customer customer = (Customer) ExistingCustomer;
                customer.setUserName(getUserName());
                customer.setAddress(getAddress());
                customer.setDateOfBirth(getDateOfBirth());
                customer.setinterests(getinterests());
                database.saveUsers();
                System.out.println("Profile Updated...");
            }
            else{
                System.out.println("Error Updating Profile...[Customer Not Found In Database]");
            }
            
            }
            @Override
            public void logout() {
                System.out.println("Customer: "+getUserName()+" Has Logged Out");
            }

            @Override
public void signup(Scanner scanner, Database database) {
    System.out.println("Enter your username: ");
    String username = scanner.nextLine().trim();
    System.out.println("Enter your password: ");
    String password = scanner.nextLine().trim();
    System.out.println("Enter your address: ");
    String address = scanner.nextLine().trim();
    System.out.println("Enter your date of birth: ");
    String dob = scanner.nextLine().trim();
    System.out.println("Enter your interests (Comma separated): ");
    String input = scanner.nextLine().trim();
    String[] interests = input.split(", ");
    System.out.println("Enter Your Balance: ");
    double balance = scanner.nextDouble();
    scanner.nextLine();  

    Gender gender = null;
    while (gender == null) {
        System.out.println("Choose Gender (male/female): ");
        String genderChoice = scanner.nextLine().trim().toLowerCase();
        if (genderChoice.equals("male")) {
            gender = Gender.MALE;
        } else if (genderChoice.equals("female")) {
            gender = Gender.FEMALE;
        } else {
            System.out.println("Invalid choice. Please enter 'male' or 'female'.");
        }
    }

    Cart newCart = new Cart();
    Customer newCustomer = new Customer(username, password, dob, address, interests, balance, gender, newCart);
    database.addUser(newCustomer);
    System.out.println("Account created successfully.");
}


            public boolean ChechOutPayment(Scanner scanner) {
                // Check if the cart is empty
                if (cart.isEmpty()) {
                    System.out.println("Your cart is empty! Please add items to your cart before checking out.");
                    return false;
                }

                double Total = cart.getTotalPrice();
                System.out.println("Your Total is: " + Total);
                System.out.println("Your Balance is: " + balance);

                if (Total > balance) {
                    System.out.println("Insufficient Balance");
                    return false;
                }

                System.out.println("Choose Payment Method");
                System.out.println("1. Credit Card");
                System.out.println("2. PayPal");
                System.out.println("3. Balance");

                int choice = scanner.nextInt();
                switch (choice) {
                    case 1 -> {
                        System.out.println("Enter your Credit Card Number");
                        String creditCard = scanner.next().trim();
                        System.out.println("Enter your Credit Card Expiration Date");
                        String expirationDate = scanner.next().trim();
                        System.out.println("Enter your Credit Card CVV");
                        String cvv = scanner.next().trim();
                        if (validateCreditCard(creditCard, expirationDate, cvv)) {
                            System.out.println("Payment successful using Credit Card!");
                        } else {
                            System.out.println("Invalid credit card details. Payment failed.");
                            return false;
                        }
                    }
                    case 2 -> {
                        System.out.println("Enter your PayPal Email");
                        String paypalEmail = scanner.next().trim();
                        if (validatePayPal(paypalEmail)) {
                            System.out.println("Payment successful using PayPal!");
                        } else {
                            System.out.println("Invalid PayPal email. Payment failed.");
                            return false;
                        }
                    }
                    case 3 -> {
                        if (balance >= Total) {
                            balance -= Total;
                            System.out.println("Payment successful using Balance. The Remaining Balance is: $" + balance);
                        } else {
                            System.out.println("Insufficient Balance for Payment.");
                            return false;
                        }
                    }
                    default -> {
                        System.out.println("Invalid payment method.");
                        return false;
                    }
                }

                // Reset the cart after successful checkout
                cart = new Cart();
                return true;
            }


            private boolean validatePayPal(String email) {
                String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
                return email.matches(emailRegex) && email.endsWith("@paypal.com");
            }

            private boolean validateCreditCard(String cardNumber, String expiryDate, String cvv) {
                return cardNumber.length() == 16 && cvv.length() == 3 && expiryDate.matches("\\d{2}/\\d{2}");
            }
           

        
    
}


