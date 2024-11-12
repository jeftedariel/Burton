/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package edu.utn.burton.database;

/**
 *
 * @author jefte
 */
public record DatabaseConf(String host, int port, String username, String database, String password, String dbtype) {

}
