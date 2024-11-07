/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.utn.burton.controller;

import edu.utn.burton.Burton;
import edu.utn.burton.entities.Product;
import edu.utn.burton.entities.ProductCell;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
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
    
    
    @FXML
    private ListView<Product> productListView;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //Hardcode list of products, it would be fethed from the API soon!.
        List<Product> products = List.of(
            new Product(1, "Laptop", 799.99, "Laptop de 15 pulgadas", new String[]{"https://m.media-amazon.com/images/I/51dbotJw3XL.jpg"}, null),
            new Product(2, "Smartphone", 499.99, "Smartphone con cámara de 12 MP", new String[]{"https://i.imgur.com/1twoaDy.jpeg"}, null),
            new Product(3, "Tablet", 299.99, "Tablet con pantalla de 10 pulgadas", new String[]{"https://i.imgur.com/1twoaDy.jpeg"}, null)
        );
        //Get the items and put them into the ListView instance.
        productListView.getItems().addAll(products);
        productListView.setCellFactory(param -> new ProductCell());
    }   
    
    public static void initGui() {
        try {
            Parent root = FXMLLoader.load(Burton.class.getResource("/fxml/Menu.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Trackademia");
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch(IOException e){
        }
    }
    
}
