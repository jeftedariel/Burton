/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.burton.controller;

import edu.utn.burton.Burton;
import edu.utn.burton.entities.OrderDetailsView;
import edu.utn.burton.entities.OrderRow;
import edu.utn.burton.entities.ProductClient;
import edu.utn.burton.entities.UserSession;
import edu.utn.burton.dao.OrderDAO;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyListView;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 *
 * @author alexledezma
 */
public class HistorialController implements Initializable{

    private ShowUserInfo user;
    
    @FXML
    private MFXLegacyListView<HBox> Historial;
    
    @FXML
    private ObservableList<HBox> observableOrderList;
    
    @FXML
    private MFXButton Regresar;
    
    @FXML
    private MFXButton RegresarOrdenes;
    
    @FXML
    private StackPane Stack;
    
    @FXML
    private ImageView View;
    
    private OrderDAO orderDAO;
    
    @FXML
    private ImageView avatar;

    @FXML
    private Text username;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
                
        orderDAO = new OrderDAO();
        
        observableOrderList = FXCollections.observableArrayList(); 
        
 
        user = new ShowUserInfo(this.avatar, this.username);
        user.loadUserInfo();
        
        loadOrders();

        Regresar.setOnAction(ev -> {
            MenuController.initGui((Stage) Regresar.getScene().getWindow());
        });
        
        RegresarOrdenes.setOnAction(ev -> {
            HistorialController.initGui((Stage) RegresarOrdenes.getScene().getWindow());
            RegresarOrdenes.setVisible(false);
        });
        
        RegresarOrdenes.setVisible(false);
        
    }
    
    public static void initGui(Stage stage) {
        try {
          
            FXMLLoader loader = new FXMLLoader(Burton.class.getResource("/fxml/Historial.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Burton.class.getResource("/styles/orders.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void loadOrders() {
    observableOrderList.clear();
    List<ProductClient> orderDetailsList = OrderDAO.getOrdersByUser(UserSession.getInstance().getId());

    if (orderDetailsList != null && !orderDetailsList.isEmpty()) {
        for (ProductClient order : orderDetailsList) {
            OrderRow orderRow = new OrderRow(order, event -> loadOrderDetails(order.getId()));
            observableOrderList.add(orderRow.getOrderRow());
        }
    } else {
        HBox emptyOrderRow = new HBox();
        emptyOrderRow.getStyleClass().add("empty-order-row");
        Label emptyLabel = new Label("No hay órdenes");
        emptyLabel.getStyleClass().add("empty-label");
        emptyOrderRow.getChildren().add(emptyLabel);
        observableOrderList.add(emptyOrderRow);
    }
    Historial.setItems(observableOrderList);
    }

    public void loadOrderDetails(int orderId) {
       OrderDetailsView detailsView = new OrderDetailsView(orderId);
       Historial.setItems(detailsView.getOrderDetailsList());
       RegresarOrdenes.setVisible(true);
    }
        
}
