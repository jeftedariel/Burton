package edu.utn.burton.controller;

import edu.utn.burton.Burton;
import edu.utn.burton.entities.Cart;
import edu.utn.burton.entities.ProductCartCell;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CartMenuController implements Initializable {

    private static CartMenuController instance;

   public static CartMenuController getInstance() {
        if (instance == null) {
            instance = new CartMenuController();  
        }
        return instance;
    }
    
    @FXML
    private MFXLegacyListView<HBox> cartListView;

    private ObservableList<HBox> observableProductList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       instance = this;  
       observableProductList = FXCollections.observableArrayList();
       loadProducts();
    }

    public static void initGui(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(Burton.class.getResource("/fxml/CartMenu.fxml"));
            Parent root = loader.load();
            CartMenuController cartMenuController = loader.getController();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Burton.class.getResource("/styles/cartmenu.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadProducts() {
        observableProductList.clear(); 

        
        if (Cart.getInstance() != null && Cart.getInstance().getProducts() != null && !Cart.getInstance().getProducts().isEmpty()) {
           
            for (int i = 0; i < Cart.getInstance().getProducts().size(); i++) {
                ProductCartCell cell = new ProductCartCell();
                cell.updateItem(Cart.getInstance().getProducts().get(i), false);
                HBox row = new HBox(10);
                row.setAlignment(Pos.CENTER);
                row.getChildren().add(cell.getGraphic());

                
                observableProductList.add(row);
            }
        } else {
            
            HBox emptyCartRow = new HBox();
            emptyCartRow.setAlignment(Pos.CENTER);
            emptyCartRow.getChildren().add(new ImageView("/assets/carroVacio.png") {
                {
                    setFitWidth(180);
                    setFitHeight(180);
                    setPreserveRatio(true);
                }
            });
            observableProductList.add(emptyCartRow);
        }

        
        cartListView.setItems(observableProductList);
    }
}
