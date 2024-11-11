/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.burton.entities;

/**
 *
 * @author Justin Rodriguez Gonzalez
 */


public class ProductCart {
    
    private int productId;
    private long quantity;
    private double unitePrice, subtotal;
    private String imagePrincipal, nameProduct;

    public ProductCart(int productId, String nameProduct,long quantity, double unitePrice, String imagePrincipal) {
        this.productId = productId;
        this.quantity = quantity;
        this.unitePrice = unitePrice;
        this.subtotal = quantity * unitePrice;
        this.imagePrincipal = imagePrincipal;
        this.nameProduct = nameProduct;
    }
    public ProductCart(int productId, String nameProduct,long quantity, double unitePrice, double subtotal, String imagePrincipal) {
        this.productId = productId;
        this.quantity = quantity;
        this.unitePrice = unitePrice;
        this.subtotal = quantity * unitePrice;
        this.imagePrincipal = imagePrincipal;
        this.nameProduct = nameProduct;
        this.subtotal = subtotal;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }
    
    public void setQuantity(long quantity) {
        this.quantity = quantity;
        recalculateSubtotal();
    }
    
    public void setUnitePrice(double unitePrice) {
        this.unitePrice = unitePrice;
        recalculateSubtotal();
    }
    
    private void recalculateSubtotal() {
        this.subtotal = this.quantity * this.unitePrice;
    }
      
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getImagePrincipal() {
        return imagePrincipal;
    }

    public void setImagePrincipal(String imagePrincipal) {
        this.imagePrincipal = imagePrincipal;
    }

    public long getQuantity() {
        return quantity;
    }

    public double getUnitePrice() {
        return unitePrice;
    }
    
    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public String toString() {
        return "ProductUser{" + "productId=" + productId + ", quantity=" + quantity + ", unitePrice=" + unitePrice + ", subtotal=" + subtotal + ", imagePrincipal=" + imagePrincipal + '}';
    }

    
    
}
