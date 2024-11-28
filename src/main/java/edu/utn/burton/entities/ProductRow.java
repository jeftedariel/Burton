/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.burton.entities;

import edu.utn.burton.Burton;
import io.github.palexdev.mfxcore.utils.fx.SwingFXUtils;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Map;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javax.imageio.ImageIO;

/**
 *
 * @author alexledezma
 */
public class ProductRow {
    
    private HBox itemRow;
    
    private Label productInfo;
    
    private ImageView productImageView;
    

    public ProductRow(Map<String, Object> item) {
        this.itemRow = new HBox(20);
        this.itemRow.setAlignment(Pos.CENTER_LEFT);


        int product = (int) item.get("order_item_id");
        int unitPrice = (int) item.get("subtotal");
        String productImage = (String) item.get("images");

        this.productImageView = createImageView(productImage);
        this.productInfo = new Label(product + " | $" + unitPrice);
        
        this.itemRow.getChildren().addAll(productImageView, productInfo);
    }

    public HBox getItemRow() {
        return itemRow;
    }

    private ImageView createImageView(String imageUrl) {
        ImageView imageView = new ImageView();

            try {
                BufferedImage image = ImageIO.read(new URL(imageUrl));
                imageView.setImage(SwingFXUtils.toFXImage(image, null));
            } catch (Exception e) {
                imageView.setImage(new Image(Burton.class.getResource("/assets/unknown.png").toString())); 
            }

        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        imageView.setPreserveRatio(true);
        return imageView;
    }
}
