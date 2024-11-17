/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.burton.controller;

import edu.utn.burton.Burton;
import edu.utn.burton.entities.UserSession;
import edu.utn.burton.entities.ordersDAO;
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
import javafx.geometry.Pos;
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
                
        orderDAO = new ordersDAO();
        
        observableOrderList = FXCollections.observableArrayList(); 
        
 
        user = new ShowUserInfo();
        user.avatar = this.avatar;  
        user.username = this.username;  
   
        user.loadUserInfo();
        
        loadOrders();

         Regresar.setOnAction(ev -> {
            MenuController.initGui((Stage) Regresar.getScene().getWindow());
        });
    }
    
    private ShowUserInfo user;
    
    @FXML
    private MFXLegacyListView<HBox> Historial;
    
    @FXML
    private ObservableList<HBox> observableOrderList;
    
    @FXML
    private MFXButton Regresar;
    
    @FXML
    private StackPane Stack;
    
    @FXML
    private ImageView View;
    
    private ordersDAO orderDAO;
    
    @FXML
    private ImageView avatar;

    @FXML
    private Text username;
    
    public static void initGui(Stage stage) {
        try {
          
            FXMLLoader loader = new FXMLLoader(Burton.class.getResource("/fxml/Historial.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Burton.class.getResource("/styles/cartmenu.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void loadOrders() {
    observableOrderList.clear();
    List<Integer> orderIds = ordersDAO.getOrdersByUser(UserSession.getInstance().getId());

    if (orderIds != null && !orderIds.isEmpty()) {
        for (int orderId : orderIds) {
            HBox orderRow = new HBox(20);
            orderRow.setAlignment(Pos.CENTER_LEFT);
            orderRow.setStyle("-fx-padding: 10px; -fx-background-color: #f4f4f4; -fx-border-radius: 5px; -fx-border-color: #dcdcdc; -fx-border-width: 1px;");

            Label documentIcon = new Label("\uD83D\uDCCB"); //Unicode para documento
            documentIcon.setStyle("-fx-font-size: 20px; -fx-text-fill: #3f7cba;");

            //Crear el texto de la orden
            Label orderLabel = new Label("Orden #" + orderId);
            orderLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333;");

            //Se añade el icono y el texto a la fila
            orderRow.getChildren().addAll(documentIcon, orderLabel);

            orderRow.setOnMouseClicked(event -> loadOrderDetails(orderId));

            observableOrderList.add(orderRow);
        }
    } else {
        HBox emptyOrderRow = new HBox();
        emptyOrderRow.setAlignment(Pos.CENTER);
        emptyOrderRow.setStyle("-fx-padding: 10px; -fx-background-color: #f8d7da; -fx-border-radius: 5px; -fx-border-color: #f5c6cb; -fx-border-width: 1px;");
        Label emptyLabel = new Label("No hay órdenes");
        emptyLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #721c24;");
        emptyOrderRow.getChildren().add(emptyLabel);

        observableOrderList.add(emptyOrderRow);
    }
    Historial.setItems(observableOrderList);
    }

    public void loadOrderDetails(int orderId) {
        
    ObservableList<HBox> orderDetailsList = FXCollections.observableArrayList();
    ObservableList<HBox> orderItems = ordersDAO.loadOrderItemsByOrderId(orderId);

    if (orderItems != null && !orderItems.isEmpty()) {
        for (HBox item : orderItems) {
            item.setAlignment(Pos.CENTER_LEFT);
            item.setStyle("-fx-padding: 15px; -fx-background-color: #ffffff; -fx-border-radius: 5px; "
                    + "-fx-border-color: #dcdcdc; -fx-border-width: 1px; "
                    + "-fx-effect: dropshadow(gaussian, lightgrey, 3, 0, 0, 2);");

            Label productIcon = new Label("\uD83D\uDED2"); //Icono de carrito de compras
            productIcon.setStyle("-fx-font-size: 24px; -fx-text-fill: #FF9800;");

            //Se coloca el ícono al principio del HBox
            item.getChildren().add(0, productIcon);

            orderDetailsList.add(item);
        }
    } else {
        HBox emptyRow = new HBox();
        emptyRow.setAlignment(Pos.CENTER);
        emptyRow.setStyle("-fx-padding: 20px; -fx-background-color: #f8d7da; "
                + "-fx-border-radius: 5px; -fx-border-color: #f5c6cb; -fx-border-width: 1px;");
        Label emptyLabel = new Label("No hay productos en esta orden");
        emptyLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #721c24;");
        emptyRow.getChildren().add(emptyLabel);

        orderDetailsList.add(emptyRow);
    }
    Historial.setItems(orderDetailsList);
    }

}
