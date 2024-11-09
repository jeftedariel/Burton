/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.burton.entities;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Justin Rodriguez Gonzalez
 */
public class Cart {

    private static List<ProductUser> products;
    private static Cart instance;
    
    public static Cart getInstance(){
        if(instance == null){
            return instance = new Cart();
        }
        return instance;
    }
    
    private Cart() {
        products = new ArrayList<>();
    }


    public void addProduct(ProductUser prUs, int cantidad) {
        ProductUser existingProduct = products.stream()
                .filter(p -> p.getProductId() == prUs.getProductId())
                .findFirst()
                .orElse(null);
        
        if (existingProduct != null) {
            
            existingProduct.setQuantity(existingProduct.getQuantity() + cantidad);      
            
        } else {
           
            products.add(prUs);
        }
    }
    

    public List<ProductUser> getProducts() {
        return products;
    }

    public void cleanCart() {
        products.clear();
    }

        public long getCantidadd(long id, long cantidad) {
        return products.stream()
                .filter(p -> p.getProductId() == id)
                .map(ProductUser::getQuantity)
                .findFirst()
                .orElse(cantidad);
    }
 

    @Override
    public String toString() {
        return "Cart{" + "products=" + products + '}';
    }

}
