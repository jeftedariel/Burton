package edu.utn.burton.controller;

import edu.utn.burton.Burton;
import edu.utn.burton.entities.Cart;
import edu.utn.burton.entities.ProductCartCell;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyListView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Justin Rodriguez Gonzalez
 */
public class CartMenuController implements Initializable {

    @FXML
    private MFXLegacyListView<HBox> cartListView;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadProducts();
    }
    

    public static void initGui(Stage stage) {
        try {
            Parent root = FXMLLoader.load(Burton.class.getResource("/fxml/CartMenu.fxml"));

            Scene scene = new Scene(root);
            scene.getStylesheets().add(Burton.class.getResource("/styles/cartmenu.css").toExternalForm());
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadProducts() {
        cartListView.getItems().clear(); 
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER);

        if (Cart.getInstance().getProducts().isEmpty()) {
            cartListView.getItems().add(new HBox(){{setAlignment(Pos.CENTER); getChildren().add(new ImageView("/assets/carroVacio.png")); prefWidthProperty().bind(cartListView.widthProperty());}});
        } else {
            for (int i = 0; i < Cart.getInstance().getProducts().size(); i++) {
                ProductCartCell cell = new ProductCartCell();
                cell.updateItem(Cart.getInstance().getProducts().get(i), false);
                row.getChildren().add(cell.getGraphic());

               
                if ((i + 1) % 1 == 0 || i == Cart.getInstance().getProducts().size() - 1) {
                    cartListView.getItems().add(row);
                    row = new HBox(10);
                    row.setAlignment(Pos.CENTER);
                }
            }
        }
    }
}