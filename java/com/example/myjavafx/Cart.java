package com.example.myjavafx;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Cart implements Serializable {
    private static final long serialVersionUID = 2L;
    private List<Products> productsInCart;
    private static final int MAX_CART_SIZE = 10;

    public Cart() {
        this.productsInCart = new ArrayList<>();
    }

    public void addProductToCart(Products product) throws AllExceptionsMade.CartFullException {
        if (productsInCart.size() >= MAX_CART_SIZE) {
            throw new AllExceptionsMade.CartFullException("Cart is full! You cannot add more than " + MAX_CART_SIZE + " products.");
        }
        productsInCart.add(product);
        System.out.println("Product added to cart: " + product.getName());
    }

    public void removeProductFromCart(Products product) throws AllExceptionsMade.ProductNotFoundException {
        if (!productsInCart.contains(product)) {
            throw new AllExceptionsMade.ProductNotFoundException("Product not found in cart.");
        }
        productsInCart.remove(product);
        System.out.println("Product removed from cart: " + product.getName());
    }

    public void viewCart() {
        if (productsInCart.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            System.out.println("Products in your cart:");
            for (Products product : productsInCart) {
                System.out.println(product);
            }
        }
    }

    public double getTotalPrice() {
        double total = 0;
        for (Products product : productsInCart) {
            total += product.getPrice();
        }
        return total;
    }
    public List<Products> getProductsInCart() {
        return productsInCart;
    }



    public boolean isEmpty() {
        return this.productsInCart.isEmpty();
    }

    public Order checkoutCart(String paymentMethod) throws AllExceptionsMade.EmptyOrderException {
        if (productsInCart.isEmpty()) {
            throw new AllExceptionsMade.EmptyOrderException("Cannot checkout an empty cart.");
        }
        List<Products> productsList = new ArrayList<>(productsInCart);
        Order order = new Order(productsList, paymentMethod);
        productsInCart.clear();

        return order;
    }


    public void clearCart() {
        productsInCart.clear();
    }
}