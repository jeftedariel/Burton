package edu.utn.burton.entities;

import edu.utn.burton.Burton;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class MessageCell extends ListCell<Message> {

    private ImageView imageView = new ImageView();
    private Label titleLabel = new Label();
    private Label descLabel = new Label();

    public MessageCell() {
        // Aply the corresponding css 
        imageView.getStyleClass().add("product-image");
        titleLabel.getStyleClass().add("product-title");
        descLabel.getStyleClass().add("product-price");
        
        //Container props
        VBox content = new VBox(imageView, titleLabel, descLabel);
        content.getStyleClass().add("product-cell");
        setGraphic(content);

        // Loads the css file with the Styles
        content.getStylesheets().add(Burton.class.getResource("/styles/product_cell.css").toExternalForm());
    }

    @Override
    public void updateItem(Message message, boolean empty) {
        super.updateItem(message, empty);
        //It will be use to be displayed
        String title = message.title();
        
        
        //Then try to add the info
        if (empty || message == null) {
            setGraphic(null);
        } else {

            titleLabel.setText(message.title());
            descLabel.setText(message.description());
            
            try {
                imageView.setImage(new Image(message.imgUrl()));
            } catch (Exception e) {
                imageView.setImage(new Image(Burton.class.getResource("/assets/unknown.png").toString()));
            }
        }
    }
}
