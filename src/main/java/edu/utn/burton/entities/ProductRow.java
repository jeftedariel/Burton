/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.burton.entities;

import edu.utn.burton.Burton;
import edu.utn.burton.controller.MasInfoController;
import edu.utn.burton.dao.ProductDAO;
import io.github.palexdev.mfxcore.utils.fx.SwingFXUtils;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Collection;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 *
 * @author alexledezma
 */
public class ProductRow {
    
    private HBox itemRow;
    
    private Label productInfo;
    
    private ImageView productImageView;
    

    public ProductRow(Order order) {
        this.itemRow = new HBox(20);
        this.itemRow.setAlignment(Pos.CENTER_LEFT);


        String product = order.title();
        int unitPrice = order.subtotal();
        String productImage = order.image();
        int quantity = order.quantity();

        this.productImageView = createImageView(productImage);
        this.productInfo = new Label(product + " | $" + unitPrice + " |  Cantidad: " + quantity);
        
        this.itemRow.getChildren().addAll(productImageView, productInfo);
        
        
        this.itemRow.setOnMouseClicked(ev -> MasInfoController.showPopup((Stage) itemRow.getScene().getWindow(), ProductDAO.getDBProduct(order.product_id())));
        
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
