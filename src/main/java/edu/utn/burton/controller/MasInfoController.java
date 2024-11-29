package edu.utn.burton.controller;

import edu.utn.burton.Burton;
import edu.utn.burton.entities.Product;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author Justin Rodriguez Gonzalez
 */
public class MasInfoController implements Initializable {

    @FXML
    private VBox vboxImages;

    @FXML
    private HBox hBoxAllImage;

    @FXML
    private Label lblTitle;

    @FXML
    private Label lblPrice;
    
    @FXML
    private TextArea textAreaDescrip;
    
    @FXML
    private Label lblDe;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public static void showPopup(Stage stage, Product p) {

        try {

            FXMLLoader loader = new FXMLLoader(Burton.class.getResource("/fxml/masInfo.fxml"));
            Parent root = loader.load();

            MasInfoController controller = loader.getController();
            controller.showImagePrincipal(p);
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Burton.class.getResource("/styles/masinfo.css").toExternalForm());

            Stage popupStage = new Stage();
            popupStage.setScene(scene);         
            popupStage.initOwner(stage);
            popupStage.setResizable(false);
            popupStage.centerOnScreen();
            popupStage.setTitle("Más Información");

            stage.setOpacity(0.5);
            popupStage.show();
            popupStage.setOnCloseRequest(event -> stage.setOpacity(1.0));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showImagePrincipal(Product currentProduct) {

        ImageView imageLoad = getItem(currentProduct);

        imageLoad.setFitWidth(340);
        imageLoad.setFitHeight(300);
        imageLoad.setImage(new Image(currentProduct.images().getFirst()));
        vboxImages.getChildren().add(imageLoad);
        lblTitle.getStyleClass().add("label-title");
        lblPrice.getStyleClass().add("label-price");
        lblDe.getStyleClass().add("label-des");
        lblTitle.setText(currentProduct.title());
        lblPrice.setText("$ " + currentProduct.price());
        textAreaDescrip.getStyleClass().add("text-area");
        textAreaDescrip.setText(currentProduct.description());
        textAreaDescrip.setWrapText(true);
        textAreaDescrip.setEditable(false);
    }

    public ImageView getItem(Product currentProduct) {
        hBoxAllImage.getChildren().clear();
        ImageView imageviewReturn = new ImageView();
        for (String image : currentProduct.images()) {

            // aplico los estilos 
            hBoxAllImage.getStylesheets().add(Burton.class.getResource("/styles/masinfo.css").toExternalForm());
            ImageView imageView = new ImageView();
            // aplicar como quiero que se vean las imagenes 
            try {
                imageView.setImage(new Image(image));
                imageView.setFitWidth(100);
                imageView.setFitHeight(110);
                imageView.getStyleClass().add("image-view");
                HBox.setMargin(imageView, new Insets(5, 10, 5, 10));

                hBoxAllImage.getChildren().add(imageView);
                hBoxAllImage.getStyleClass().add(".hbox-container");
                //evento para poder obtener la imagen
                imageView.setOnMouseEntered(ev -> {
                    imageView.getStyleClass().add(".image-view:hover");
                    imageviewReturn.setImage(imageView.getImage());

                });

                imageView.setOnMouseExited(event -> imageView.setStyle(""));

            } catch (Exception e) {

                imageView.setImage(new Image(Burton.class.getResource("/assets/unknown.png").toString()));
            }

        }
        return imageviewReturn;
    }

}
