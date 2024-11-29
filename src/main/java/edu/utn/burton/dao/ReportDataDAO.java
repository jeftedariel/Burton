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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jefte
 */
public class ReportDataDAO {

    // obtener los productos totales 
    public List<Product> getProductSells() {

        IDBAdapter adapter = DBAdapterFactory.getAdapter();

        String query = "SELECT productos.title AS Product, SUM(orders.quantity) AS Quantity FROM  order_items orders INNER JOIN products productos ON orders.product_id = productos.id GROUP BY productos.title;";

        List<Product> products = new ArrayList<>();

        try (Connection conn = DBAdapterFactory.getAdapter().getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                products.add(new Product(0, rs.getString("Product"), 0, "", null, "", rs.getInt("Quantity"), new Category(0, "", "")));
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener los items: " + e.getMessage());
        }

        adapter.disconnect();

        return products;
    }

    // obtiene  lo productos por categoria 
    public List<Product> getProductByCategories(int id) {
        IDBAdapter adapter = DBAdapterFactory.getAdapter();
        String query = "SELECT productos.title AS Product,SUM(orders.quantity) AS Quantity FROM order_items orders INNER JOIN products productos  ON orders.product_id = productos.id INNER JOIN categories cate ON productos.category_id = cate.id INNER JOIN orders ord ON orders.order_id = ord.order_id  WHERE cate.id = ? GROUP BY productos.title;";

        List<Product> products = new ArrayList<>();

        try (Connection conn = adapter.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                products.add(new Product(0, rs.getString("Product"), 0, "", null, "", rs.getInt("Quantity"), new Category(0, "", "")));
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener los items: " + e.getMessage());
        }

        adapter.disconnect();
        return products;
    }

    // obtiene los productos que estan por arriba del promedio por medio de categoria 
    public List<Product> topSellsByCategories(int id) {
        List<Product> products = getProductByCategories(id);

        final int average = getTotalSells(products) / products.size();
        return products.stream().filter(product -> product.quantity() >= average).toList();
    }
    
      public List<Product> lowSellsByCategories(int id) {
        List<Product> products = getProductByCategories(id);

        final int average = getTotalSells(products) / products.size();
        return products.stream().filter(product -> product.quantity() <= average).toList();
    }

    // obtiene los productos que estan por encima del promedio 
    public List<Product> topSells() {
        List<Product> products = getProductSells();

        final int average = getTotalSells(products) / products.size();
        //In base of total sells & items calculates the average
        return products.stream().filter(product -> product.quantity() >= average).toList(); //Return the ones that are higher than the average or equals
    }

    // obtiene los productos que estan por abajo del promedio 
    public List<Product> lowSells() {
        List<Product> products = getProductSells();

        final int average = getTotalSells(products) / products.size();
        // The same that topSells() but just the ones that are lower than the average      
        return products.stream().filter(product -> product.quantity() <= average).toList();
    }

    //obtiene el total de la cantidad de cada uno
    public int getTotalSells(List<Product> productos) {
        int totalSells = 0;
        List<Product> sells = productos;
        //Creates a list of all sold Items and then return the total amount of general sells.
        for (Product product : sells) {
            totalSells += product.quantity();
        }
        return totalSells;
    }

    public Map<Integer, String> getCategories() {

        IDBAdapter adapter = DBAdapterFactory.getAdapter();

        String query = "SELECT DISTINCT c.id, c.name FROM categories c INNER JOIN products p ON c.id = p.category_id WHERE EXISTS (SELECT 1 FROM order_items oi WHERE oi.product_id = p.id);";

        Map<Integer, String> categories = new HashMap<>();

        try (Connection conn = DBAdapterFactory.getAdapter().getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                categories.put(rs.getInt("id"), rs.getString("name"));
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener los items: " + e.getMessage());
        }

        adapter.disconnect();

        return categories;
    }

}
