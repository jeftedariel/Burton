/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.burton.dao;

import edu.utn.burton.entities.Product;
import edu.utn.burton.handlers.APIHandler;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jefte
 */
public class ProductDAO {

    private static APIHandler api = new APIHandler();

   

    public static List<Product> getProducts(int offset, int limit, int priceMin, int priceMax, String categoryQuery, String searchByName) {
        String query = "products?offset=" + offset * 10 + "&limit="+limit + "&price_min=" + (int) priceMin + "&price_max=" + (int) priceMax + categoryQuery + searchByName;
        
        try {
            
            return api.getList(Product.class, query, null);
        } catch (Exception e) {
            System.out.println("Error fetching products: " + e);
            return null; // Return empty list if error
        }
    }
     public static ArrayList<String> desearilizarString(String url){
        ArrayList<String> urls =  new ArrayList<>(List.of(url.split(",")));
        return urls;
    }
    public static String serealizarImagenes(ArrayList<String> palabras) {
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
