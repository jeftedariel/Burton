/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.burton.controller;

import edu.utn.burton.entities.Cart;
import edu.utn.burton.entities.message.Message;
import edu.utn.burton.entities.products.Product;
import edu.utn.burton.entities.products.ProductCart;
import edu.utn.burton.entities.UserSession;
import javafx.scene.Node;
import edu.utn.burton.dao.OrderDAO;
import javafx.scene.control.Alert;
import javafx.scene.control.Spinner;

/**
 *
 * @author Justin Rodriguez Gonzalez
 */
public class CartController {

    private OrderDAO odDAO;

    public CartController() {
        odDAO = new OrderDAO();
    }

    public void getProduct(Product currentProduct, int count) {

        if (currentProduct != null && count != 0) {
            
            ProductCart productUser = new ProductCart(currentProduct.id(), currentProduct.title(), Cart.getInstance().getCantidadd(currentProduct.id(), count), currentProduct.price(), currentProduct.images().get(0));
            Cart.getInstance().addProduct(productUser, count);
            odDAO.getOrCreateActiveCart(UserSession.getInstance().getId());
            odDAO.addProductsToCart(Cart.getProducts());
            Alerts.show(new Message("Exito", "Se agregado a tu carrito de compras un " +  currentProduct.title()), Alert.AlertType.INFORMATION);
             
        }
    }

    
  public void deleteProducto(ProductCart currentProduct, int count) {
      
   
        if (count == 0) {
            odDAO.removeProduct(currentProduct.getProductId());
            Cart.getInstance().deleteProducts(currentProduct, count);
            CartMenuController.getInstance().loadProducts();
            
        } else {      
          
            Cart.getInstance().deleteProducts(currentProduct, count);
            odDAO.addProductsToCart(Cart.getProducts());  
            CartMenuController.getInstance().loadProducts();
        }
    
}

    public void addProduct(ProductCart currentProduct, int count) {

        if (!isEmptySpinner(count)) {
            CartMenuController.getInstance().loadProducts();
            ProductCart productUser = new ProductCart(currentProduct.getProductId(), currentProduct.getNameProduct(), Cart.getInstance().getCantidadd(currentProduct.getProductId(), count), currentProduct.getUnitePrice(), currentProduct.getImagePrincipal()); // igual qui lo de abajo para que agregue segun lo que el usuario quiera
            Cart.getInstance().addProduct(productUser, count);
            System.out.println(Cart.getInstance().getProducts().toString());
            odDAO.addProductsToCart(Cart.getProducts());
            CartMenuController.getInstance().loadProducts();
        }
    }

    public boolean isEmptySpinner(int count) {
        return (count == 0);
    }
    
    public void deleteButton(ProductCart currentProduct){
        
    odDAO.removeProduct(currentProduct.getProductId());
    Cart.getInstance().setDelete(currentProduct);
    CartMenuController.getInstance().loadProducts();
    
    }
    
    public void functionSpinner(Spinner amount, ProductCart currentProduct){
    
       amount.skinProperty().addListener((obs, oldSkin, newSkin) -> {
        if (newSkin != null) {
            // le doy una referencia a los nodos de cual es la flecha para arriba y la de abajo
            Node incrementButton = amount.lookup(".increment-arrow-button");
            Node decrementButton = amount.lookup(".decrement-arrow-button");
            // el evento obteniendo el boton de incrementar 
            if (incrementButton != null) {
                incrementButton.setOnMouseClicked(e -> {        
                  addProduct(currentProduct, (int) amount.getValue());      
                });
            }
            // el evento obteniendo el boton de decrementar 
            if (decrementButton != null) {
                decrementButton.setOnMouseClicked(e -> {
                    deleteProducto(currentProduct, (int) amount.getValue());
                });
            }
        }
    });
    }
    
}
