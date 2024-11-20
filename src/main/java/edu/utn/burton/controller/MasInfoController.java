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
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * @author Justin Rodriguez Gonzalez
 */
public class MasInfoController implements Initializable {

 
    @FXML  
    private VBox hboxMasInfo;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    public static void showPopup(Stage stage, Product p) {

        try {

            FXMLLoader loader = new FXMLLoader(Burton.class.getResource("/fxml/MasInfo.fxml"));
            Parent root = loader.load();

            MasInfoController controller = loader.getController();
            controller.getItem(p);
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Burton.class.getResource("/styles/masinfo.css").toExternalForm());

            Stage popupStage = new Stage();
            popupStage.setScene(scene);
            popupStage.initStyle(StageStyle.UNDECORATED);
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

    public void getItem(Product currentProduct) {
    hboxMasInfo.getChildren().clear(); 
    for (String image : currentProduct.images()) {
        hboxMasInfo.getStylesheets().add(Burton.class.getResource("/styles/product_cell.css").toExternalForm());

        ImageView imageView = new ImageView();
        imageView.setImage(new Image(image));
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);
        imageView.setPreserveRatio(true);
        imageView.getStyleClass().add("image-view"); 

       
     
        hboxMasInfo.getChildren().add(imageView);
    }
}

}
