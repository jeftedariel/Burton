/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.burton.dao;

import edu.utn.burton.database.DBAdapterFactory;
import edu.utn.burton.entities.Product;
import edu.utn.burton.handlers.APIHandler;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jefte
 */
public class ProductDAO {
//666
    private static APIHandler api = new APIHandler();

    public static List<Product> getProducts(int offset, int limit, int priceMin, int priceMax, String categoryQuery, String searchByName) {
        String query = "products?offset=" + offset * 10 + "&limit=" + limit + "&price_min=" + (int) priceMin + "&price_max=" + (int) priceMax + categoryQuery + searchByName;

        try {

            return api.getList(Product.class, query, null);
        } catch (Exception e) {
            System.out.println("Error fetching products: " + e);
            return null; // Return empty list if error
        }
    }
    
    //Se utiliza como clave del map un String, ya que representa el nombre de las columnas, y un objeto para obtener cualquier tipo de dato
    public static List<Product> getDBProducts() {

        List<Product> items = new ArrayList<>();

        String query = "SELECT * FROM Products";
        try (Connection conn = DBAdapterFactory.getAdapter().getConnection(); CallableStatement stmt = conn.prepareCall(query)) {

            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                items.add(new Product(rs.getInt("product_id"), rs.getString("title"), rs.getDouble("price"), rs.getString("description"), ProductDAO.unserializeUrl(rs.getString("images")), rs.getInt("category_id")));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener los productos de la orden: " + e.getMessage());
        }
        return items;
    }
    
     public static ArrayList<String> unserializeUrl(String url){
        ArrayList<String> urls =  new ArrayList<>(List.of(url.split(",")));
        return urls;
    }
    
     public static String serializeUrl(ArrayList<String> palabras) {
        StringBuilder sb = new StringBuilder();
        for (String palabra : palabras) {
            if (palabra.equals(palabras.getLast())) {
                sb.append(palabra) ;
            } else {
                sb.append(palabra).append(",");
            }
        }
        return sb.toString();
    }
}
