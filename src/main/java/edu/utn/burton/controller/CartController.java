/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.burton.controller;

import edu.utn.burton.entities.Cart;
import edu.utn.burton.entities.Product;
import edu.utn.burton.entities.ProductCart;
import javax.swing.JOptionPane;

/**
 *
 * @author Justin Rodriguez Gonzalez
 */
public class CartController {

    public CartController() {

    }

    public void getProduct(Product currentProduct, int count) {
        if (currentProduct != null && count != 0) {
            ProductCart productUser = new ProductCart(currentProduct.id(), currentProduct.title(), Cart.getInstance().getCantidadd(currentProduct.id(), count), currentProduct.price(), currentProduct.images().get(0)); // igual qui lo de abajo para que agregue segun lo que el usuario quiera
            Cart.getInstance().addProduct(productUser, count);
            System.out.println(Cart.getInstance().getProducts().toString());
            JOptionPane.showMessageDialog(null, "Producto Agregado " + currentProduct.title());

        }
    }

    public void deleteProducto(ProductCart currentProduct, int count) {
      if(!isEmptySpinner(count)){
            Cart.getInstance().deleteProducts(currentProduct, count);
            JOptionPane.showMessageDialog(null, "Producto Eliminado " + currentProduct.getNameProduct());
            CartMenuController.getInstance().loadProducts();
      }
    }

    public void addProduct(ProductCart currentProduct, int count) {
      
            if(!isEmptySpinner(count)){
            ProductCart productUser = new ProductCart(currentProduct.getProductId(), currentProduct.getNameProduct(), Cart.getInstance().getCantidadd(currentProduct.getProductId(), count), currentProduct.getUnitePrice(), currentProduct.getImagePrincipal()); // igual qui lo de abajo para que agregue segun lo que el usuario quiera
            Cart.getInstance().addProduct(productUser, count);
            System.out.println(Cart.getInstance().getProducts().toString());
            CartMenuController.getInstance().loadProducts();
        }
    }
    
    public boolean isEmptySpinner(int count){
    return (count == 0);
    }

}
