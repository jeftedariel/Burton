package edu.utn.burton.controller;

import edu.utn.burton.Burton;
import edu.utn.burton.entities.products.Product;
import io.github.palexdev.mfxcore.utils.fx.SwingFXUtils;
import java.awt.image.BufferedImage;
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
import javax.imageio.ImageIO;

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
    
    @FXML
    private Label lblCategory;

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
            popupStage.getIcons().add(new Image(Burton.class.getResourceAsStream("/assets/icon.png")));

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
        try {
            BufferedImage BuffImg = ImageIO.read(new URL(currentProduct.images().getFirst()));
            imageLoad.setImage(SwingFXUtils.toFXImage(BuffImg, null));
        } catch (Exception e) {
            imageLoad.setImage(new Image(Burton.class.getResource("/assets/unknown.png").toString()));
        }
        
        vboxImages.getChildren().add(imageLoad);
        lblTitle.getStyleClass().add("label-title");
        lblCategory.getStyleClass().add("label-title");
        lblPrice.getStyleClass().add("label-price");
        lblDe.getStyleClass().add("label-des");
        lblTitle.setText(currentProduct.title());
        lblPrice.setText("$ " + currentProduct.price());
        lblCategory.setText(currentProduct.category().name());
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
                BufferedImage BuffImg = ImageIO.read(new URL(image));
                imageView.setImage(SwingFXUtils.toFXImage(BuffImg, null));
            } catch (Exception e) {
                imageView.setImage(new Image(Burton.class.getResource("/assets/unknown.png").toString()));
            }

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

        }
        return imageviewReturn;
    }

}
