/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package edu.utn.burton.database;

import java.sql.Connection;

/**
 *
 * @author jefte
 */
public interface IDBAdapter {
    Connection getConnection();
    void disconnect();
}
