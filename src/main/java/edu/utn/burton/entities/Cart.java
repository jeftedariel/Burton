/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.burton.entities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Justin Rodriguez Gonzalez
 */
public class Cart {

    private static ObservableList<ProductCart> products;
    private static Cart instance;

    public static Cart getInstance() {
        if (instance == null) {
            return instance = new Cart();
        }
        return instance;
    }

    private Cart() {
        products = FXCollections.observableArrayList();
    }

    public void addProduct(ProductCart prUs, int cantidad) {
        ProductCart existingProduct = products.stream()
                .filter(p -> p.getProductId() == prUs.getProductId())
                .findFirst()
                .orElse(null);

        if (existingProduct != null) {

            existingProduct.setQuantity(existingProduct.getQuantity() + cantidad);

        } else {

            products.add(prUs);
        }
    }

    public void deleteProducts(ProductCart prUs, int cantidad) {

        ProductCart existingProduct = products.stream()
                .filter(p -> p.getProductId() == prUs.getProductId())
                .findFirst()
                .orElse(null);

        if (existingProduct != null) {

            long nuevaCantidad = existingProduct.getQuantity() - cantidad;

            if (nuevaCantidad > 0) {

                existingProduct.setQuantity(nuevaCantidad);
            } else {
                products.remove(existingProduct);
            }
        }
    }

    public static ObservableList<ProductCart> getProducts() {
        return products;
    }

    public void cleanCart() {
        products.clear();
    }

    public long getCantidadd(long id, long cantidad) {
        return products.stream()
                .filter(p -> p.getProductId() == id)
                .map(ProductCart::getQuantity)
                .findFirst()
                .orElse(cantidad);
    }

    @Override
    public String toString() {
        return "Cart{" + "products=" + products + '}';
    }

}
