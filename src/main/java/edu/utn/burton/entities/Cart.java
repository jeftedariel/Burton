/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.burton.entities;

import edu.utn.burton.entities.products.ProductClient;
import edu.utn.burton.entities.products.ProductCart;
import edu.utn.burton.dao.OrderDAO;
import java.time.LocalDate;
import javafx.collections.ObservableList;

/**
 *
 * @author Justin Rodriguez Gonzalez
 */
public class Cart {
    private int userID;
    private String status;
    private LocalDate created_at;
    private LocalDate update_at;
    private static ObservableList<ProductCart> products;
    private static Cart instance;

    public static Cart getInstance() {
        if (instance == null) {
            return instance = new Cart();
        }
        return instance;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreated_at(LocalDate created_at) {
        this.created_at = created_at;
    }

    public void setUpdate_at(LocalDate update_at) {
        this.update_at = update_at;
    }

    public static void setProducts(ObservableList<ProductCart> products) {
        Cart.products = products;
    }

    public static void setInstance(Cart instance) {
        Cart.instance = instance;
    }
    
    private Cart() {
        products = OrderDAO.getProductSave();
    }

    public void addProduct(ProductCart prUs, int cantidad) {           
        ProductCart existingProduct = products.stream()
                .filter(p -> p.getProductId() == prUs.getProductId())
                .findFirst()
                .orElse(null);

        if (existingProduct != null) {

            existingProduct.setQuantity(cantidad);

        } else {

            products.add(prUs);
            Cart.getInstance().CartEmpty();

        }
        double newTotal = calcularTotal();
        ProductClient.getInstance().setTotalAmount(newTotal);
    }

    public void deleteProducts(ProductCart prUs, int cantidad) {
        ProductCart existingProduct = products.stream()
                .filter(p -> p.getProductId() == prUs.getProductId())
                .findFirst()
                .orElse(null);

        if (existingProduct != null) {
           // System.out.println(existingProduct.toString());
            long nuevaCantidad = cantidad;
            
           // System.out.println("Nueva cantidad = " + nuevaCantidad);
            
            if (nuevaCantidad > 0) {
                
                existingProduct.setQuantity(nuevaCantidad);
                
            } else {
            
                products.remove(existingProduct);
                Cart.getInstance().CartEmpty();
            }

            double newTotal = calcularTotal();
            ProductClient.getInstance().setTotalAmount(newTotal);

            if (nuevaCantidad <= 0) {
                prUs.setQuantity(0);
            }
        }
    }
    
    public static void setDelete(ProductCart prUs){
       getProducts().remove(prUs); 
    }

    public void CartEmpty() {
        if (products.stream().count() == 0) {

        }
    }

    public static ObservableList<ProductCart> getProducts() {
        return products;
    }

    public void cleanCart() {
        products.clear();
    }

    public double calcularTotal() {
        return products.stream().mapToDouble(p -> p.getSubtotal()).sum();
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
        return "Cart{" + "userID=" + userID + ", status=" + status + ", created_at=" + created_at + ", update_at=" + update_at + '}';
    }

}
