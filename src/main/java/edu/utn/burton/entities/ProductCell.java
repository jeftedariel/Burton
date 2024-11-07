/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.burton.entities;

import edu.utn.burton.Burton;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 *
 * @author jefte
 */
public class ProductCell extends ListCell<Product> {
        //This Class is for the information that would be displayed at the menu
        private ImageView imageView = new ImageView();
        private Label titleLabel = new Label();
        private Label priceLabel = new Label();
        private MFXButton addToCart = new MFXButton();
        private MFXButton info = new MFXButton();

        public ProductCell() {
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);
            setGraphic(new VBox(imageView, titleLabel, priceLabel, addToCart));
        }

        @Override
        protected void updateItem(Product product, boolean empty) {
            super.updateItem(product, empty);
            if (empty || product == null) {
                setGraphic(null);
            } else {
                titleLabel.setText(product.title());
                priceLabel.setText("$" + product.price());
                addToCart.setText("Agregar al Carrito");
                info.setText("Más Info");
                try{
                    imageView.setImage(new Image(product.images().get(0)));
                } catch (Exception e){
                    imageView.setImage(new Image(Burton.class.getResource("/assets/unknown.png").toString()));
                }
                    // Show the first image into the listView
                setGraphic(new VBox(imageView, titleLabel, priceLabel, addToCart, info));
            }
        }
    }
