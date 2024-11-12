/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.burton.database;

import edu.utn.burton.handlers.ConfigHandler;

/**
 *
 * @author jefte
 */
public class DBAdapterFactory {
    private static final String DB_TYPE = "dbadaptertype";
    
    public static IDBAdapter getAdapter(){
        
        try{ //Try to get that db adapter interface
            return (IDBAdapter)Class.forName("edu.utn.burton.database."+ConfigHandler.getInstance().getDatabaseConfig().dbtype()).newInstance();
        } catch (Exception e){
            e.printStackTrace();
            return null;
            
        }
    }
}
