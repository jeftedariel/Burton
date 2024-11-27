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
                List<String> imgUrl = new ArrayList<>();
                imgUrl.add(rs.getString("images"));
                items.add(new Product(rs.getInt("product_id"), rs.getString("title"), rs.getDouble("price"), rs.getString("description"), imgUrl, rs.getInt("category_id")));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener los productos de la orden: " + e.getMessage());
        }
        return items;
    }
}
