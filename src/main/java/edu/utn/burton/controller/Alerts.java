/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.burton.controller;

import edu.utn.burton.Burton;
import edu.utn.burton.entities.Message;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 *
 * @author jefte
 */
public class Alerts {
    public static void show(Message message){
        Alert alert = new Alert( Alert.AlertType.WARNING);
        //Sets the title and desc content
        alert.setTitle(message.title());
        alert.setContentText(message.description());
        alert.setHeaderText(null);
        //Set the image for the Alert
        ImageView imageView = new ImageView();
        imageView.setStyle("-fx-fit-width: 80; -fx-fit-height: 80;");
        
        imageView.setImage(new Image(Burton.class.getResource(message.imgUrl()).toString()));
        alert.setGraphic(imageView);
        
        alert.showAndWait();
    }
}
