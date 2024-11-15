/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.burton.entities;

/**
 *
 * @author Justin Rodriguez Gonzalez
 */
public class ProductClient {

    private static ProductClient instance;
    public int userId;
    private double totalAmount;
    private String typePay, status;

    public ProductClient() {
        this.userId = UserSession.getInstance().getId();
        this.totalAmount = Cart.getInstance().calcularTotal();
        this.typePay = "Credit Card";
        this.status = "Completed";
    }

    public static ProductClient getInstance() {
        if (instance == null) {
            return instance = new ProductClient();
        }
        return instance;
    }

    public String getTypePay() {
        return typePay;
    }

    public void setTypePay(String typePay) {
        this.typePay = typePay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "ProductClient{" + "userId=" + userId + ", totalAmount=" + totalAmount + '}';
    }

}
