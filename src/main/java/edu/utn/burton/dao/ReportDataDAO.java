/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.burton.dao;

import edu.utn.burton.database.DBAdapterFactory;
import edu.utn.burton.database.IDBAdapter;
import edu.utn.burton.entities.Category;
import edu.utn.burton.entities.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jefte
 */
public class ReportDataDAO {

    public List<Product> getProductSells() {

        IDBAdapter adapter = DBAdapterFactory.getAdapter();

        String query = "SELECT product_name AS Product, SUM(quantity) AS Quantity FROM order_items GROUP BY product_name;";

        List<Product> products = new ArrayList<>();

        try (Connection conn = DBAdapterFactory.getAdapter().getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                products.add(new Product(0, rs.getString("Product"), 0, "", null, "", rs.getInt("Quantity"), new Category(0,"","")));
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener los items: " + e.getMessage());
        }

        adapter.disconnect();

        return products;
    }

    public int getTotalSells() {
        int totalSells = 0;
        List<Product> sells = getProductSells();
        //Creates a list of all sold Items and then return the total amount of general sells.
        for (Product product : sells) {
            totalSells += product.quantity();
        }
        return totalSells;
    }
    
    

    public List<Product> topSells() {
        List<Product> products = getProductSells();
       
        final int average = getTotalSells() / products.size();
        //In base of total sells & items calculates the average
        return products.stream().filter(product -> product.quantity() >= average).toList(); //Return the ones that are higher than the average or equals
    }

    public List<Product> lowSells() {
        List<Product> products = getProductSells();

        final int average = getTotalSells() / products.size();
         // The same that topSells() but just the ones that are lower than the average
        return products.stream().filter(product -> product.quantity() < average).toList();
    }

}
