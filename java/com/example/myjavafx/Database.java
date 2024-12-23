package com.example.myjavafx;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Database implements Serializable{
    private static final long serialVersionUID = 1L;
    private ArrayList<User> users;
    private ArrayList<Products> products;
    private HashMap<String,Categories> CategorySort;

    private static final String USERS_FILE = "users.dat";
    private static final String PRODUCTS_FILE = "Products.dat";

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Database() {
        users = new ArrayList<>();
        products = new ArrayList<>();
        CategorySort = new HashMap<>();
        loadUsers();
        loadProducts();
        //CreateDummyUsers();
        //CreateDummyCategories();
        //CreateDummyProducts();


    }


    @SuppressWarnings("unchecked")
    public void loadProducts() {
        // Check if products is already loaded (not null and not empty)
        if (products != null && !products.isEmpty()) {
            System.out.println("Products are already loaded.");
            return; // Exit the method without loading again
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PRODUCTS_FILE))) {
            products = (ArrayList<Products>) ois.readObject();
            System.out.println("Products Loaded Successfully");
        } catch (FileNotFoundException e) {
            System.out.println("No existing product data found.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading products: " + e.getMessage());
        }
    }

    public boolean removeUser(String username) {
        for (User user : users) {
            if (user.getUserName().equals(username)) {
                users.remove(user);
                saveUsers();
                System.out.println("User '" + username + "' has been deleted from the database.");
                return true;
            }
        }
        System.out.println("User not found.");
        return false;
    }

    public boolean removeProduct(int id) {
        for (Products product : products) {
            if (product.getId()==id) {
                products.remove(product);
                saveProducts();
                System.out.println("Product ID: " + id + "' has been deleted from the database.");
                return true;
            }
        }
        System.out.println("Product not found.");
        return false;
    }

    public void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USERS_FILE))) {
            oos.writeObject(users);
            System.out.println("Users saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USERS_FILE))) {
            users = (ArrayList<User>) ois.readObject();
            System.out.println("Users Loaded successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("No existing user data found.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }


    public void saveProducts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PRODUCTS_FILE))) {
            oos.writeObject(products);
            System.out.println("Products Saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving products: " + e.getMessage());
        }
    }



    public void addUser(User user) {
        users.add(user);
        saveUsers();
    }

    public void addProduct(Products product, Admin admin) {
        if (!(admin instanceof Admin)) {
            System.out.println("ONLY Admins are ALLOWED TO ADD PRODUCTS...");
            return;
        }

        Categories existingCategory = CategorySort.get(product.getCategory());

        if (existingCategory != null) {
            existingCategory.addProduct(product);
            System.out.println("Product added to existing category: " + existingCategory.getName());
        } else {
            Categories newCategory = new Categories(product.getCategory());
            newCategory.addProduct(product);
            CategorySort.put(product.getCategory(), newCategory);
            System.out.println("New category created and product added: " + newCategory.getName());
        }

        products.add(product);
        saveProducts();
        System.out.println("Product added successfully: " + product);
    }


    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Products> getProducts() {
        return products;
    }

    public User getUserByUsername(String username) {
        for (User user : users) {
            if (user != null && user.getUserName() != null && user.getUserName().equals(username)) {
                return user;
            }
        }
        return null;
    }



    public Products getProductById(int id) {
        for (Products product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    public boolean checkUser(String username, String password) {
        User user = getUserByUsername(username);
        if (user != null && username.equals(user.getUserName()) && password.equals(user.getPassword())) {
            return true;
        }
        return false;
    }


    public void CreateDummyUsers(){
        Admin admin1 = new Admin("Zeyad","1234","5-7-2005","SuperAdmin","4");
        Admin admin2 = new Admin("Ahmed","1378","1-3-1978","Admin","5");
        Admin admin3 = new Admin("Omar","1111","31-10-2006","Admin","6");

        //Customer customer1 = new Customer("Messi","Goat10","5-12-1980","Rosario",new String[]{"Football","Avengers"},987000,Customer.Gender.MALE,new Cart());
        //Customer customer2 = new Customer("Chloe","1122","22-10-1999","Manchester",new String[]{"Football","Movies"},12000,Customer.Gender.FEMALE,new Cart());

        users.add(admin1);
        users.add(admin2);
        users.add(admin3);
        //users.add(customer1);
        //users.add(customer2);

    }

    public void CreateDummyCategories(){
        Categories category1 = new Categories("Laptops");
        Categories category2 = new Categories("Mobile Phones");

        CategorySort.put("Laptops", category1);
        CategorySort.put("Mobile Phones", category2);
    }

    public void CreateDummyProducts() {
        Products product1 = new Products("Lenovo", 1200, "Laptops", 13324);
        Products product2 = new Products("HP", 1500, "Laptops", 13325);
        Products product3 = new Products("Dell", 1800, "Laptops", 13326);
        Products product4 = new Products("Iphone 16", 540, "Mobile Phones", 11123);
        Products product5 = new Products("Iphone 15", 340, "Mobile Phones", 12323);
        Products product6 = new Products("Oppo Reno", 440, "Mobile Phones", 21332);
        Products product7 = new Products("Samsung Galaxy S22 Ultra", 720, "Mobile Phones", 21333);

        Categories laptopsCategory = CategorySort.get("Laptops");
        if (laptopsCategory == null) {
            laptopsCategory = new Categories("Laptops");
            CategorySort.put("Laptops", laptopsCategory);
        }

        Categories mobilePhonesCategory = CategorySort.get("Mobile Phones");
        if (mobilePhonesCategory == null) {
            mobilePhonesCategory = new Categories("Mobile Phones");
            CategorySort.put("Mobile Phones", mobilePhonesCategory);
        }

        laptopsCategory.addProduct(product1);
        laptopsCategory.addProduct(product2);
        laptopsCategory.addProduct(product3);

        mobilePhonesCategory.addProduct(product4);
        mobilePhonesCategory.addProduct(product5);
        mobilePhonesCategory.addProduct(product6);
        mobilePhonesCategory.addProduct(product7);

        products.add(product1);
        products.add(product2);
        products.add(product3);
        products.add(product4);
        products.add(product5);
        products.add(product6);
        products.add(product7);


    }


    public void displayProductsByCategories() {
        if (CategorySort.isEmpty()) {
            System.out.println("No categories available.");
            return;
        }

        for (Categories category : CategorySort.values()) {
            System.out.println("Category: " + category.getName());
            for (Products product : category.getProducts()) {
                System.out.println("  - " + product+"  ID: "+product.getId());
            }
        }
    }




}