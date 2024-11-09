/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.utn.burton.controller;

import edu.utn.burton.Burton;
import edu.utn.burton.entities.Cart;
import edu.utn.burton.entities.Product;
import edu.utn.burton.entities.ProductCell;
import edu.utn.burton.handlers.APIHandler;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyListView;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jefte
 */
public class MenuController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    private Cart cart = new Cart();
    
    @FXML
    private MFXLegacyListView<Product> productListView;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        APIHandler api = new APIHandler(Product.class);
        List<Product> products=null;
        try {
            products = api.obtenerProductos("products");
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        
        //Get the items and put them into the ListView instance.
        productListView.getItems().addAll(products);
        productListView.setCellFactory(param -> new ProductCell(cart));
    }   
    
    public static void initGui() {
        try {
            Parent root = FXMLLoader.load(Burton.class.getResource("/fxml/Menu.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Burton E-Commerce");
            stage.getIcons().add(new Image(Burton.class.getResourceAsStream("/assets/icon.png")));
            
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch(IOException e){
        }
    }
    
}
