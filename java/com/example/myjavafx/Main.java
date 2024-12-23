package com.example.myjavafx;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Database database =  new Database();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            if (!startProgram(scanner)) break;

            String userType = getUserType(scanner);
            handleUserLoginOrSignup(scanner, userType, database);
        }
    }

    private static boolean startProgram(Scanner scanner) {
        System.out.println("Start Program (yes or no):");
        String start = scanner.nextLine().trim().toLowerCase();
        while (!start.equals("no") && !start.equals("yes")) {
            System.out.println("Invalid input. Please enter yes or no");
            start = scanner.nextLine().trim().toLowerCase();
        }
        return start.equals("yes");
    }

    private static String getUserType(Scanner scanner) {
        String choice;
        do {
            System.out.println("Admin or Customer (A for Admin or C for Customer): ");
            choice = scanner.nextLine().trim().toUpperCase();
            if (!choice.equals("A") && !choice.equals("C")) {
                System.out.println("Invalid input. Please enter A or C.");
            }
        } while (!choice.equals("A") && !choice.equals("C"));
        return choice;
    }

    private static void handleUserLoginOrSignup(Scanner scanner, String userType, Database database) {
        System.out.println("Log-In/Sign-Up");
        String action = getAction(scanner);

        if (action.equals("login")) {
            loginUser(scanner, userType, database);
        } else if (action.equals("signup")) {
            signupUser(scanner, userType, database);
        }
    }

    private static String getAction(Scanner scanner) {
        String action;
        do {
            System.out.println("Enter login or signup: ");
            action = scanner.nextLine().trim().toLowerCase();
            if (!action.equals("login") && !action.equals("signup")) {
                System.out.println("Invalid input. Please enter login or signup.");
            }
        } while (!action.equals("login") && !action.equals("signup"));
        return action;
    }

    private static void loginUser(Scanner scanner, String userType, Database database) {
        System.out.println("Enter username: ");
        String username = scanner.nextLine().trim();
        System.out.println("Enter password: ");
        String password = scanner.nextLine().trim();

        if (userType.equals("A")) {
            Admin admin = (Admin) database.getUserByUsername(username);
            if (admin != null) {
                admin.login(username, password, database);
                handleAdminMenu(scanner, admin, database);
            } else {
                System.out.println("Admin not found or invalid credentials.");
            }
        } else if (userType.equals("C")) {
            Customer customer = (Customer) database.getUserByUsername(username);
            if (customer != null && customer.getPassword().equals(password)) {
                System.out.println("Customer login successful!");
                handleCustomerMenu(scanner, customer, database);
            } else {
                System.out.println("Customer not found or invalid credentials.");
            }
        }
    }

    private static void signupUser(Scanner scanner, String userType, Database database) {
        if (userType.equals("A")) {
            Admin admin = new Admin();
            admin.signup(scanner, database);
            database.addUser(admin);
        } else if (userType.equals("C")) {
            Customer customer = new Customer();
            customer.signup(scanner, database);
            database.addUser(customer);
        }
    }

    private static void handleAdminMenu(Scanner scanner, Admin admin, Database database) {
        while (true) {
            if (admin == null) {
                System.out.println("Admin not found.");
                return;
            }
            admin.displayMenu();

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> admin.displayProfile();
                case 2 -> {
                    System.out.println("Enter the field you want to edit (UserName, Role, Working Hours, DOB): ");
                    String field = scanner.nextLine().trim().toLowerCase();
                    System.out.println("Enter the new value: ");
                    String newValue = scanner.nextLine().trim();
                    admin.updateProfile(field, newValue, database);
                }
                case 3 -> admin.displayCustomerInfo(database);
                case 4 -> {
                    // Prompt for product details
                    System.out.println("Enter product name: ");
                    String name = scanner.nextLine().trim();
                    System.out.println("Enter product price: ");
                    double price = scanner.nextDouble();
                    scanner.nextLine(); // Consume the newline character
                    System.out.println("Enter product category: ");
                    String category = scanner.nextLine().trim();

                    // Create a new product object
                    int productId = Products.generateUniqueId();  // You may need to adjust how the product ID is generated
                    Products newProduct = new Products(name, price, category, productId);

                    // Add the product using the addProduct method
                    database.addProduct(newProduct, admin);
                }
                case 5 -> {
                    System.out.println("Enter The Product's ID: ");
                    int id = scanner.nextInt();
                    database.removeProduct(id);
                }
                case 6 -> database.displayProductsByCategories();
                case 7 -> {admin.logout(); return;}
                case 8 -> {System.out.println("...Exiting...");
                    System.exit(0);}
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void handleCustomerMenu(Scanner scanner, Customer customer, Database database) {
        while (true) {
            if (customer == null) {
                System.out.println("Customer not found.");
                return;
            }
            customer.displayMenu();

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> customer.displayProfile();
                case 2 -> database.displayProductsByCategories();
                case 3 -> System.out.println("Your balance: $" + customer.getBalance());
                case 4 -> {
                    Cart cart = customer.getCart();
                    System.out.println("1. Add Product to Cart");
                    System.out.println("2. Remove Product from Cart");
                    System.out.println("3. View Cart");
                    int subChoice = scanner.nextInt();
                    scanner.nextLine();
                    switch (subChoice) {
                        case 1 -> {
                            for (Products product : database.getProducts()) {
                                System.out.println("ID: " + product.getId() + ", Name: " + product.getName() + ", Price: $" + product.getPrice() + ", Category: " + product.getCategory());
                            }
                            System.out.println("Enter Product ID to add:");
                            int productId = scanner.nextInt();
                            scanner.nextLine();
                            Products productToAdd = database.getProductById(productId);
                            if (productToAdd != null) {
                                try {
                                    cart.addProductToCart(productToAdd);
                                } catch (AllExceptionsMade.CartFullException e) {
                                    System.out.println(e.getMessage());
                                }
                            } else {
                                System.out.println("Product not found.");
                            }
                        }
                        case 2 -> {
                            System.out.println("Enter Product ID to remove:");
                            int productId = scanner.nextInt();
                            scanner.nextLine();
                            Products productToRemove = database.getProductById(productId);
                            if (productToRemove != null) {
                                try {
                                    cart.removeProductFromCart(productToRemove);
                                } catch (AllExceptionsMade.ProductNotFoundException e) {
                                    System.out.println(e.getMessage());
                                }
                            } else {
                                System.out.println("Product not found.");
                            }
                        }
                        case 3 -> cart.viewCart();
                        default -> System.out.println("Invalid choice.");
                    }
                }
                case 5 -> {
                    System.out.println("Enter the field you want to edit: ");
                    String field = scanner.nextLine().trim().toLowerCase();
                    System.out.println("Enter the new value: ");
                    String newValue = scanner.nextLine().trim();
                    customer.updateProfile(field, newValue, database);
                }
                case 6 -> customer.ChechOutPayment(scanner);
                case 7 -> {
                    customer.deleteAccount(database);
                    return;
                }
                case 8 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

}