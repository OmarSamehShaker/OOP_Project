package com.example.myjavafx;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Categories implements Serializable {
    private static final long serialVersionUID = 3L;
    private int id;
    private String name;
    private List<Products> products;
    
    public Categories() {}

    public Categories( String name) {
        this.name = name;
        this.products = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Products> getProducts() {
        return products;
    }

    public void addProduct(Products product) {
        products.add(product);
    }

    
    private ArrayList<Categories> categories = new ArrayList<>();

    public ArrayList<Categories> getCategories() {
        return categories;
    }

    public void addCategory(Categories category) {
        categories.add(category);
    }

    public void deleteCategory(int id, String name) {
        categories.removeIf(category -> category.getId() == id && category.getName().equals(name));
    }

    public Categories readCategory(int id, String name) throws AllExceptionsMade.CategoryNotFound {
        for (Categories category : categories) {
            if (category.getId() == id && category.getName().equals(name)) {
                return category;
            }
        }
        throw new AllExceptionsMade.CategoryNotFound("Category with id " + id + " and name " + name + " not found");
    }

    public void updateCategory(int id, String newName) {
        for (Categories category : categories) {
            if (category.getId() == id) {
                category.setName(newName);
                break;
            }
        }
    }

    public void printCategories() throws NullPointerException {
        for (Categories category : categories) {
            System.out.println(category);
        }
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Category Name: " + name;
    }

  
}
