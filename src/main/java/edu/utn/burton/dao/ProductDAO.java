/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.burton.dao;

import edu.utn.burton.database.DBAdapterFactory;
import edu.utn.burton.database.IDBAdapter;
import edu.utn.burton.entities.Product;
import edu.utn.burton.handlers.APIHandler;
import java.sql.CallableStatement;
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

        String query = "SELECT * FROM products";

        IDBAdapter adapter = DBAdapterFactory.getAdapter();
        try {
            CallableStatement stmt = adapter.getConnection().prepareCall(query);
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                items.add(new Product(rs.getInt("id"), rs.getString("title"), rs.getDouble("price"), rs.getString("description"), ProductDAO.unserializeUrl(rs.getString("images")), rs.getInt("category_id")));
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener los productos de la db: " + e.getMessage());
        } finally {
            adapter.disconnect();
        }
        return items;
    }

    //Se utiliza como clave del map un String, ya que representa el nombre de las columnas, y un objeto para obtener cualquier tipo de dato
    public static Product getDBProduct(int id) {
        String query = "SELECT * FROM products WHERE id = ?"; // Consulta con parámetro
        IDBAdapter adapter = DBAdapterFactory.getAdapter();

        try (Connection conn = adapter.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) { // Usa PreparedStatement

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) { 
                if (rs.next()) {
                    return new Product(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getDouble("price"),
                            rs.getString("description"),
                            ProductDAO.unserializeUrl(rs.getString("images")),
                            rs.getInt("category_id")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el producto: " + e.getMessage());
        } finally {
            adapter.disconnect(); 
        }

        return null;
    }

    public static void dumpProducts(List<Product> products) {

        String query = "INSERT into  products(id, title, price, description, images, category_id) values(?,?,?,?,?,?)";
        IDBAdapter adapter = DBAdapterFactory.getAdapter();
        try {

            PreparedStatement pstmt = adapter.getConnection().prepareStatement(query);
            //adapter.getConnection().setAutoCommit(false);

            for (Product p : products) {

                pstmt.setInt(1, p.id());
                pstmt.setString(2, p.title());
                pstmt.setDouble(3, p.price());
                pstmt.setString(4, p.description());
                pstmt.setString(5, ProductDAO.serializeUrl(p.images()));
                pstmt.setInt(6, p.category().id());

                pstmt.addBatch();
            }

            int[] r = pstmt.executeBatch();
            System.out.println(r.length + " New products has been dumped");

        } catch (SQLException e) {
            System.err.println("Error while dumping products." + e.getMessage());
        } finally {
            adapter.disconnect();
        }

    }

    public static ArrayList<String> unserializeUrl(String url) {
        ArrayList<String> urls = new ArrayList<>(List.of(url.split(",")));
        return urls;
    }

    public static String serializeUrl(List<String> palabras) {
        StringBuilder sb = new StringBuilder();
        for (String palabra : palabras) {
            if (palabra.equals(palabras.getLast())) {
                sb.append(palabra);
            } else {
                sb.append(palabra).append(",");
            }
        }
        return sb.toString();
    }
}
