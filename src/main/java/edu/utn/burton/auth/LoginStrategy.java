/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package edu.utn.burton.auth;

/**
 *
 * @author jefte
 */
public interface LoginStrategy {
    boolean auth(String email, String password);
    boolean oAuth();    
}
