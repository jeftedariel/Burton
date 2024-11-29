/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.burton.entities;

import edu.utn.burton.dao.OrderDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;
import java.util.Map;
import javafx.geometry.Pos;
import javafx.scene.control.Label;

/**
 *
 * @author alexledezma
 */
public class OrderDetailsView {
    
    private ObservableList<HBox> orderDetailsList;

    public OrderDetailsView(int orderId) {
        this.orderDetailsList = FXCollections.observableArrayList();
        loadOrderDetails(orderId);
    }

    private void loadOrderDetails(int orderId) {
        ObservableList<Order> orderItems = OrderDAO.loadOrderItemsByOrderId(orderId);

        if (orderItems != null && !orderItems.isEmpty()) {
            for (Order order : orderItems) {
                ProductRow productRow = new ProductRow(order);
                orderDetailsList.add(productRow.getItemRow());
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
    }

    public ObservableList<HBox> getOrderDetailsList() {
        return orderDetailsList;
    }
    
}
