/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.burton.database;

import edu.utn.burton.handlers.ConfigHandler;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author jefte
 */
public class MySQLAdapter implements IDBAdapter{
     Connection connection = null;
    
    static {
        try {
            new com.mysql.cj.jdbc.Driver();
        } catch (Exception e) {

        }
    }
    
    @Override
    public Connection getConnection() {
        try {
            this.connection = DriverManager.getConnection(this.getConnectionString(), ConfigHandler.getInstance().getDatabaseConfig().username(), ConfigHandler.getInstance().getDatabaseConfig().password());
            System.out.println("Connection class =>" + this.connection.getClass().getCanonicalName());

        } catch(Exception e){
            e.printStackTrace();
           
        } finally{
            return this.connection;
        }
    }
    
    @Override
    public void disconnect(){
        if(connection != null){
            try{
                connection.close();
                System.out.println("Connection Closed");
            } catch(Exception e){
                
                e.printStackTrace();
            }
        }
    }

    private String getConnectionString() {
        return "jdbc:mysql://"+ConfigHandler.getInstance().getDatabaseConfig().host()+":"+ConfigHandler.getInstance().getDatabaseConfig().port()+"/"+ConfigHandler.getInstance().getDatabaseConfig().database();
    }

}
