/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.burton.controller;

import edu.utn.burton.entities.UserSession;
import java.awt.image.BufferedImage;
import javafx.fxml.FXML;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 *
 * @author alexledezma
 */
public class ShowUserInfo {

    @FXML
    private Circle avatar;

    @FXML
    private Text username;

    public ShowUserInfo(Circle avatar, Text username) {
        this.avatar = avatar;
        this.username = username;
    }

    public void loadUserInfo() {
        username.setText(UserSession.getInstance().getName());
        BufferedImage image;
        try {
            Image im = new Image(UserSession.getInstance().getAvatar(), false);
            avatar.setFill(new ImagePattern(im));
            avatar.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));
        } catch (Exception e) {
            System.out.println("Hubo un error al intentar cargar la img");
        }
    }

}
