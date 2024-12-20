package edu.utn.burton.controller;

import edu.utn.burton.Burton;
import edu.utn.burton.entities.Cart;
import edu.utn.burton.entities.message.Message;
import edu.utn.burton.entities.message.MessageCell;
import edu.utn.burton.entities.products.ProductCartCell;
import edu.utn.burton.entities.products.ProductClient;
import edu.utn.burton.entities.UserSession;
import edu.utn.burton.dao.OrderDAO;
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
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

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
    private Circle avatar;

    @FXML
    private Text username;
    
    private ShowUserInfo user;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        user = new ShowUserInfo(this.avatar , this.username);
        user.loadUserInfo();
        
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

                    OrderDAO.addProducItemsAndComplete(ProductClient.getInstance(), Cart.getProducts(), UserSession.getInstance().getId());
                    OrderDAO.completeCart(UserSession.getInstance().getId());
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

                    OrderDAO.cancelCart();
                    Cart.getInstance().cleanCart();
                    CartMenuController.getInstance().loadProducts();
                }

            });

        });

        returnBtn.setOnAction(ev -> {
            MenuController.initGui((Stage) returnBtn.getScene().getWindow());
        });
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
            cell.updateItem(new Message("Aviso", "No tienes Productos en tu carrito", Burton.class.getResource("/assets/carroX.png").toString()), false);
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
