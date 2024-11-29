/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package edu.utn.burton.entities.message;

/**
 *
 * @author jefte
 */
public record Message(String title, String description, String imgUrl) {

    public Message(String title, String description) {
        this(title, description, ""); 
    }
}
