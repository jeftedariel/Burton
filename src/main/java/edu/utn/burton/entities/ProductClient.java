/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.burton.entities;
import java.time.LocalDate;

/**
 *
 * @author Justin Rodriguez Gonzalez
 */

public class ProductClient {
    private static ProductClient instance;
    private int userId;
    private double totalAmount;
    private LocalDate date;
    private String status, typePay;

    public ProductClient() {
        this.userId = 3;
        this.totalAmount = Cart.getInstance().calcularTotal();
        this.date = LocalDate.now();
        this.status = "En proceso";
        this.typePay = "Tarjeta";
    }
    
      public static ProductClient getInstance() {
        if (instance == null) {
            return instance = new ProductClient();
        }
        return instance;
    }
    
    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ProductClient{" + "userId=" + userId + ", totalAmount=" + totalAmount + ", date=" + date + ", status=" + status + '}';
    } 
    
}
