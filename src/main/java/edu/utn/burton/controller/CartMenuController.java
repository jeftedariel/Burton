package edu.utn.burton.controller;

import edu.utn.burton.Burton;
import edu.utn.burton.entities.Cart;
import edu.utn.burton.entities.Message;
import edu.utn.burton.entities.MessageCell;
import edu.utn.burton.entities.ProductCartCell;
import edu.utn.burton.entities.ProductClient;
import edu.utn.burton.entities.UserSession;
import edu.utn.burton.entities.ordersDAO;
import io.github.palexdev.materialfx.controls.MFXButton;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javax.swing.JOptionPane;

/**
 *
 * @author Justin Rodriguez Gonzalez
 */
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

    @FXML
    private MFXButton btnBuy;

    @FXML
    private MFXButton btnCancelarCompra;

    @FXML
    private ObservableList<HBox> observableProductList;

    @FXML
    private Label lblTotalPago;

    @FXML
    private MFXButton returnBtn;

    @FXML
    private ImageView avatar;

    @FXML
    private Text username;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;
        observableProductList = FXCollections.observableArrayList();
        lblTotalPago.setText("Total: " + ProductClient.getInstance().getTotalAmount());

        loadProducts();
         
        btnBuy.setOnAction(ev -> {
          
            Message message = new Message(
                    "Advertencia",
                    "¿Estás seguro de que deseas realizar la compra?",
                    "Si finalizas la compra, no podras modificar tu orden."
            );
            Alerts.showConfirmation(message, response -> {
                if (response == ButtonType.APPLY) {

                    ordersDAO.addProducItemsAndComplete(ProductClient.getInstance(), Cart.getProducts(), UserSession.getInstance().getId());
                    ordersDAO.completeCart(UserSession.getInstance().getId());
                    Cart.getInstance().cleanCart();
                    CartMenuController.getInstance().loadProducts();
                }

            });
        });

        btnCancelarCompra.setOnAction(ev -> {
            // set mesagge
            Message message = new Message(
                    "Advertencia",
                    "¿Estás seguro de que deseas cancelar la compra?",
                    "Si cancelas la compra, se eliminarán todos los productos de tu carrito."
            );

            Alerts.showConfirmation(message, response -> {
                if (response == ButtonType.APPLY) {

                    ordersDAO.cancelCart();
                    Cart.getInstance().cleanCart();
                    CartMenuController.getInstance().loadProducts();
                }

            });

        });

        returnBtn.setOnAction(ev -> {
            MenuController.initGui((Stage) returnBtn.getScene().getWindow());
        });

        //Sets the User's avatar & Name into GUI
        loadUserInfo();
    }

    public void loadUserInfo() {
        username.setText(UserSession.getInstance().getName());
        try {
            Image img = new Image(UserSession.getInstance().getAvatar());
            avatar.setImage(img);
        } catch (Exception e) {
            System.out.println("There was an error while loading Avatar image: " + e);
        }
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
                lblTotalPago.setText("$ " + ProductClient.getInstance().getTotalAmount());
                observableProductList.add(row);
            }
        } else {

            MessageCell cell = new MessageCell();
            cell.updateItem(new Message("Aviso", "No tienes Productos en tu carrito", "/assets/carroX.png"), false);
            HBox row = new HBox(10);
            row.setAlignment(Pos.CENTER);
            row.getChildren().add(cell.getGraphic());
            lblTotalPago.setText("$ " + ProductClient.getInstance().getTotalAmount());
            observableProductList.add(row);

            btnBuy.setVisible(false);
            lblTotalPago.setVisible(false);
            btnCancelarCompra.setVisible(false);
        }

        cartListView.setItems(observableProductList);
    }
}
