/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.burton.entities;

import java.util.Map;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;

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
        this.itemRow.setStyle("-fx-padding: 15px; -fx-background-color: #ffffff; -fx-border-radius: 5px; "
                + "-fx-border-color: #dcdcdc; -fx-border-width: 1px; "
                + "-fx-effect: dropshadow(gaussian, lightgrey, 3, 0, 0, 2);");

        String productName = (String) item.get("product_name");
        double unitPrice = (double) item.get("unit_price");
        String productImage = (String) item.get("product_image");

        this.productImageView = createImageView(productImage);
        this.productInfo = new Label(productName + " | $" + unitPrice);
        this.productInfo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333;");

        this.itemRow.getChildren().addAll(productImageView, productInfo);
    }

    public HBox getItemRow() {
        return itemRow;
    }

    private ImageView createImageView(String imageUrl) {
        ImageView imageView = new ImageView();

        if (imageUrl != null && !imageUrl.isEmpty()) {
            try {
                Image image = new Image(imageUrl, 50, 50, true, true); 
                imageView.setImage(image);
            } catch (Exception e) {
                imageView.setImage(new Image("file:default_image.png", 50, 50, true, true)); 
            }
        } else {
            imageView.setImage(new Image("file:default_image.png", 50, 50, true, true));
        }

        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        imageView.setPreserveRatio(true);
        return imageView;
    }
}
