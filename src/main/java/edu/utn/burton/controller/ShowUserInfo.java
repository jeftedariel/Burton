/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.burton.controller;

import edu.utn.burton.entities.UserSession;
import io.github.palexdev.mfxcore.utils.fx.SwingFXUtils;
import java.awt.image.BufferedImage;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javax.imageio.ImageIO;

/**
 *
 * @author alexledezma
 */
public class ShowUserInfo {
    
    @FXML
    private ImageView avatar;

    @FXML
    private Text username;

    public ShowUserInfo(ImageView avatar, Text username) {
        this.avatar = avatar;
        this.username = username;
    }

    public void loadUserInfo() {
        username.setText(UserSession.getInstance().getName());
        BufferedImage image;
        try {
            image = ImageIO.read(new URL(UserSession.getInstance().getAvatar()));
            avatar.setImage(SwingFXUtils.toFXImage(image, null));
        } catch (Exception e) {
            System.out.println("Hubo un error al intentar cargar la img");
        }
    }
    
}
