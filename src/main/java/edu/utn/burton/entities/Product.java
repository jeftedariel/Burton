/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package edu.utn.burton.entities;

import java.util.List;

/**
 *
 * @author jefte
 */
public record Product(int id, String title, double price, String description, List<String> images, String creationAt, int quantity, int category_id) {
    public Product(int id, String title, double price, String description, List<String> images, String creationAt) {
        this(id, title, price, description, images, creationAt, 1, 0);
    }
    
    public Product(int id, String title, double price, String description, List<String> images, int category_id) {
        this(id, title, price, description, images, "", 1, category_id);
    }
    
   
}
