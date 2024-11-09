/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.burton.controller;

import edu.utn.burton.entities.Cart;
import edu.utn.burton.entities.Product;
import edu.utn.burton.entities.ProductUser;
import javax.swing.JOptionPane;

/**
 *
 * @author Justin Rodriguez Gonzalez
 */
public class CartController {

    public CartController() {

    }
    
    public void getProduct(Product currentProduct, Cart cart){
    if (currentProduct != null) {
          
                ProductUser productUser = new ProductUser(currentProduct.id(),cart.getCantidadd(currentProduct.id(), 3),currentProduct.price(), currentProduct.images().get(0)); // igual qui lo de abajo para que agregue segun lo que el usuario quiera
                cart.addProduct(productUser,3);// agregar de donde se saca la cantidad de stock a agregar
                System.out.println(cart.toString());
                JOptionPane.showMessageDialog(null, "Producto Agregado " + currentProduct.title());
                System.out.println("Carrito ahora tiene " + cart.getProducts().size() + " productos.");
            }
    }

}
