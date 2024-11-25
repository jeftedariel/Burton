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
    
    private static Alert initAlert(Message message,AlertType at){
        Alert alert = new Alert(at);
        
        alert.setTitle(message.title());
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/assets/icon.png"));
        alert.setContentText(message.description());
        alert.setHeaderText(null);
        return alert;
    }
    
    public static void show(Message message, AlertType type) {
        Alert alert = initAlert(message,type);
        alert.showAndWait();
    }
    
    public static void showConfirmation(Message message, Consumer<ButtonType> onResponse) {
   
        Alert alert = initAlert(message,AlertType.CONFIRMATION);
      
        ButtonType aceptarButton = ButtonType.APPLY;
        ButtonType noButton = ButtonType.NO; 
        
        alert.getButtonTypes().setAll(aceptarButton, noButton);  // Solo "Aceptar" y "No"

        alert.showAndWait().ifPresent(response -> {
           
            onResponse.accept(response);
        });
    }
}


