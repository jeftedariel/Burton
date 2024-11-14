package edu.utn.burton.entities;

import edu.utn.burton.Burton;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import edu.utn.burton.controller.CartController;

public class ProductCell extends ListCell<Product> {

    //This Class is for the information that would be displayed at the menu
    private ImageView imageView = new ImageView();
    private Label titleLabel = new Label();
    private Label priceLabel = new Label();
    private MFXButton addToCart = new MFXButton();
    private MFXButton info = new MFXButton();

    private Product currentProduct;

    private CartController ctrlCart;

    private ImageView shopingcartIcon = new ImageView();
    private ImageView infoIcon = new ImageView();

    public ProductCell() {
        //Initialize the functionality for the shoping
        ctrlCart = new CartController();

        addToCart.setOnAction(event -> {
            ctrlCart.getProduct(currentProduct,1);
            
        });
       
        // Aply the corresponding css 
        imageView.getStyleClass().add("product-image");
        titleLabel.getStyleClass().add("product-title");
        priceLabel.getStyleClass().add("product-price");
        addToCart.getStyleClass().add("product-button");
        info.getStyleClass().add("product-button");
        shopingcartIcon.getStyleClass().add("icon-image");
        
        //Apply icons for Buttons
        shopingcartIcon.setImage(new Image("/assets/shopingcart.png"));
        addToCart.setGraphic(shopingcartIcon);
        
         //Container props
        VBox content = new VBox(imageView, titleLabel, priceLabel, addToCart, info);
        content.getStyleClass().add("product-cell");
        setGraphic(content);

        // Loads the css file with the Styles
        content.getStylesheets().add(Burton.class.getResource("/styles/product_cell.css").toExternalForm());
    }

    @Override
    public void updateItem(Product product, boolean empty) {
        super.updateItem(product, empty);
        //It will be use to be displayed
        String title = product.title();
        
        //Checks if product title is too long, if its true it will add some ... 
        if (product.title().length() > 27) {
            title = "";
            for (int i = 0; i < 26; i++) {
                title += product.title().charAt(i);
            }
            title += "...";
        }
        
        //Then try to add the info
        if (empty || product == null) {
            setGraphic(null);
            currentProduct = null;
        } else {
            currentProduct = product;
            titleLabel.setText(title);
            priceLabel.setText("$" + product.price());
            addToCart.setText("Agregar al Carrito");
            info.setText("Más Info");
            
            try {
                imageView.setImage(new Image(product.images().get(0)));
            } catch (Exception e) {
                imageView.setImage(new Image(Burton.class.getResource("/assets/unknown.png").toString()));
            }
        }
    }
}
