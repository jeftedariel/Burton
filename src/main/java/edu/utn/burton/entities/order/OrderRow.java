/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.burton.entities.order;

import edu.utn.burton.entities.products.ProductClient;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 *
 * @author alexledezma
 */
public class OrderRow {
    
    private HBox orderRow;
    
    private Label orderLabel;
    
    @FXML
    private MFXButton RegresarOrdenes;

    public OrderRow(ProductClient order, EventHandler<MouseEvent> onClick) {
        this.orderRow = new HBox(20);
        this.orderRow.getStyleClass().add("order-row");

        Label documentIcon = new Label("\uD83D\uDCCB"); // Unicode para documento
        documentIcon.getStyleClass().add("document-icon");

        this.orderLabel = new Label(
            "Orden #" + order.getId() + 
            " | Fecha: " + order.getDate() + 
            " | Monto: $" + order.getTotalAmount() 
        );
        this.orderLabel.getStyleClass().add("order-label");

        this.orderRow.getChildren().addAll(documentIcon, orderLabel);
        this.orderRow.setOnMouseClicked(onClick);
    }

    public HBox getOrderRow() {
        return orderRow;
    }
    
    public Label getOrderLabel() {
        return orderLabel;
    }
    
}

