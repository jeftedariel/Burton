/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package edu.utn.burton.entities.order;

/**
 *
 * @author jefte
 */
public record Order(int id, int order_item_id ,int product_id, int quantity, int subtotal, String title, String image) {

}
