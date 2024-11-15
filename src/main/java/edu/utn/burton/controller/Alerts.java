/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.burton.controller;

import edu.utn.burton.entities.Message;
import java.util.function.Consumer;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author jefte
 */
public class Alerts {

    public static void show(Message message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(message.title());
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/assets/icon.png"));

        alert.setContentText(message.description());
        alert.setHeaderText(null);
        alert.showAndWait();

    }
    public static void showConfirmation(Message message, Consumer<ButtonType> onResponse) {
   
        Alert alert = new Alert(AlertType.CONFIRMATION);

       
        alert.setTitle(message.title());
        alert.setContentText(message.description());

    
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/assets/icon.png"));
        alert.setHeaderText(null);
      
        ButtonType aceptarButton = ButtonType.APPLY;
        ButtonType noButton = ButtonType.NO;  
        
       
        alert.getButtonTypes().setAll(aceptarButton, noButton);  // Solo "Aceptar" y "No"

        alert.showAndWait().ifPresent(response -> {
           
            onResponse.accept(response);
        });
    }
}


