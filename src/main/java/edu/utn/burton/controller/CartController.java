/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.burton.controller;

import edu.utn.burton.entities.Cart;
import edu.utn.burton.entities.Message;
import edu.utn.burton.entities.Product;
import edu.utn.burton.entities.ProductCart;
import edu.utn.burton.entities.UserSession;
import edu.utn.burton.entities.ordersDAO;
import javafx.scene.control.Alert;

/**
 *
 * @author Justin Rodriguez Gonzalez
 */
public class CartController {

    private ordersDAO odDAO;

    public CartController() {
        odDAO = new ordersDAO();
    }

    public void getProduct(Product currentProduct, int count) {

        if (currentProduct != null && count != 0) {

            ProductCart productUser = new ProductCart(currentProduct.id(), currentProduct.title(), Cart.getInstance().getCantidadd(currentProduct.id(), count), currentProduct.price(), currentProduct.images().get(0)); // igual qui lo de abajo para que agregue segun lo que el usuario quiera
            Cart.getInstance().addProduct(productUser, count);
            odDAO.getOrCreateActiveCart(UserSession.getInstance().getId());
            odDAO.addProductsToCart(UserSession.getInstance().getId(), Cart.getProducts());
            System.out.println(Cart.getInstance().getProducts().toString());
            Alerts.show(new Message("Exito", "Se agregado a tu carrito de compras un " +  currentProduct.title()), Alert.AlertType.INFORMATION);        
        }
    }

  public void deleteProducto(ProductCart currentProduct, int count) {
    if (!isEmptySpinner(count)) {
        if (currentProduct.getQuantity() <= count) {
          
            odDAO.removeProduct(currentProduct.getProductId(),UserSession.getInstance().getId());
            Cart.getInstance().deleteProducts(currentProduct, count);
            CartMenuController.getInstance().loadProducts();
            
        } else {      
          
            Cart.getInstance().deleteProducts(currentProduct, count);
            odDAO.addProductsToCart(UserSession.getInstance().getId(), Cart.getProducts());  
            CartMenuController.getInstance().loadProducts();
        }
    }
}

    public void addProduct(ProductCart currentProduct, int count) {

        if (!isEmptySpinner(count)) {
            CartMenuController.getInstance().loadProducts();
            ProductCart productUser = new ProductCart(currentProduct.getProductId(), currentProduct.getNameProduct(), Cart.getInstance().getCantidadd(currentProduct.getProductId(), count), currentProduct.getUnitePrice(), currentProduct.getImagePrincipal()); // igual qui lo de abajo para que agregue segun lo que el usuario quiera
            Cart.getInstance().addProduct(productUser, count);
            System.out.println(Cart.getInstance().getProducts().toString());
            odDAO.addProductsToCart(UserSession.getInstance().getId(), Cart.getProducts());
            CartMenuController.getInstance().loadProducts();
        }
    }

    public boolean isEmptySpinner(int count) {
        return (count == 0);

    }
    
}
