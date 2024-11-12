/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.burton.controller;

import edu.utn.burton.entities.Message;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;

/**
 *
 * @author jefte
 */
public class Alerts {

    public static void show(Message message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(message.title());
        alert.setContentText(message.description());
        alert.setHeaderText(null);
        
        alert.showAndWait();
    }
}
