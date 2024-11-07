/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package edu.utn.burton.entities;

/**
 *
 * @author jefte
 */
public record Product(int id, String title, double price, String description, String[] images, Category category) {

}
