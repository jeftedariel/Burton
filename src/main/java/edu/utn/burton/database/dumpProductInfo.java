/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.burton.database;

import edu.utn.burton.dao.ProductDAO;
import edu.utn.burton.entities.Product;
import edu.utn.burton.handlers.APIHandler;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author jefte
 */
public class dumpProductInfo {

    public static void dumpData() {
        APIHandler apiHandler = new APIHandler();
        List<Product> apiProducts = new ArrayList<>();
        try {
            apiProducts = apiHandler.getList(Product.class, "products", null);
        } catch (Exception e) {
            System.out.println("Error while loading api products:" + e);
        }

        List<Product> dbProducts = ProductDAO.getDBProducts();

        
        Set<Product> missingProducts = new HashSet<Product>();
        
        missingProducts.addAll(dbProducts);
        missingProducts.addAll(apiProducts);
        
        missingProducts.removeAll(dbProducts);
        
    }

}
