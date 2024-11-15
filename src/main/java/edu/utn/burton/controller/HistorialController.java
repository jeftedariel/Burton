/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.burton.controller;

import edu.utn.burton.Burton;
import edu.utn.burton.entities.Cart;
import edu.utn.burton.entities.ProductCart;
import edu.utn.burton.entities.ProductCartCell;
import edu.utn.burton.entities.ProductClient;
import edu.utn.burton.entities.UserSession;
import edu.utn.burton.entities.ordersDAO;
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
        
        loadOrders();
        
    }
    
    @FXML
    private MFXLegacyListView<HBox> Historial;
    
    @FXML
    private ObservableList<HBox> observableOrderList;
    
    @FXML
    private Label lblTotalPago;
    
    private ordersDAO orderDAO;
    
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

    System.out.println("Iniciando carga de órdenes");
    List<Integer> orderIds = ordersDAO.getOrdersByUser(UserSession.getInstance().getId());
    System.out.print("Órdenes encontradas: " + orderIds);

    if (orderIds != null && !orderIds.isEmpty()) {
        System.out.println("Órdenes encontradas para el usuario: " + orderIds.size());

        for (int orderId : orderIds) {
            HBox orderRow = new HBox(10);
            orderRow.setAlignment(Pos.CENTER_LEFT);

            Label orderLabel = new Label("Orden #" + orderId);
            orderLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
            orderRow.getChildren().add(orderLabel);

            orderRow.setOnMouseClicked(event -> loadOrderDetails(orderId));

            observableOrderList.add(orderRow);
        }
    } else {
        HBox emptyOrderRow = new HBox();
        emptyOrderRow.setAlignment(Pos.CENTER);
        emptyOrderRow.getChildren().add(new ImageView("/assets/ordenX.png") {{
            setFitWidth(600);
            setFitHeight(600);
            setPreserveRatio(true);
        }});
        observableOrderList.add(emptyOrderRow);
    }
      Historial.setItems(observableOrderList);
    }

    public void loadOrderDetails(int orderId) {
        
    ObservableList<HBox> orderDetailsList = FXCollections.observableArrayList();


    // Llamar al método en el OrderDAO para cargar los productos de la orden
    ObservableList<HBox> orderItems = ordersDAO.loadOrderItemsByOrderId(orderId);

    // Verificar si hay productos asociados a la orden
    if (orderItems != null && !orderItems.isEmpty()) {
        System.out.println("Detalles encontrados para la orden #" + orderId);
        
        // Agregar los productos de la orden a la lista de detalles
        orderDetailsList.addAll(orderItems);
    } else {
        HBox emptyRow = new HBox();
        emptyRow.setAlignment(Pos.CENTER);
        emptyRow.getChildren().add(new Label("No hay productos en esta orden"));
        orderDetailsList.add(emptyRow);
    }
      Historial.setItems(orderDetailsList);
    }
    
}
