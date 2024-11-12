/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.burton.entities;

import edu.utn.burton.database.DBAdapterFactory;
import edu.utn.burton.database.IDBAdapter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;

/**
 *
 * @author Justin Rodriguez Gonzalez
 */
public class ordersDAO {

    public static int getOrCreateActiveCart(int userId) {

        int cartId = -1;
        IDBAdapter adapter = DBAdapterFactory.getAdapter();
        try {
            PreparedStatement psE = adapter.getConnection().prepareStatement("SET FOREIGN_KEY_CHECKS = 0;");
            psE.execute();

            CallableStatement ps = adapter.getConnection().prepareCall("{CALL get_or_create_active_cart(?)}");
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            PreparedStatement psA = adapter.getConnection().prepareStatement("SET FOREIGN_KEY_CHECKS = 1;");
            psA.execute();

            if (rs.next()) {
                cartId = rs.getInt("cart_id");
            }
        } catch (SQLException e) {

            System.err.println("Error al ejecutar el procedimiento: " + e.getMessage());
        } finally {
            adapter.disconnect();
        }

        return cartId;
    }

    public static void completeCart(int idPersona) {
        
        IDBAdapter adapter = DBAdapterFactory.getAdapter();
        
        try {
            int cartId = getActiveCartId(idPersona);

            CallableStatement stmt = adapter.getConnection().prepareCall("{CALL complete_cart(?)}");
            stmt.setInt(1, cartId);
            stmt.executeUpdate();
        } catch (SQLException e) {

            System.err.println("Error al ejecutar el procedimiento: " + e.getMessage());
        } finally {
            adapter.disconnect();
        }
    }

    public static int getActiveCartId(int userId) throws SQLException {
        int cartId = -1;
        IDBAdapter adapter = DBAdapterFactory.getAdapter();

        try {

            CallableStatement stmt = adapter.getConnection().prepareCall("{CALL get_active_cart_by_user(?)}");
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                cartId = rs.getInt("cart_id");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el carrito activo: " + e.getMessage());
        } finally {
            adapter.disconnect();
        }

        return cartId;
    }

    public static int createActiveCart(int userId) {
        int cartId = -1;
        IDBAdapter adapter = DBAdapterFactory.getAdapter();

        try {
            CallableStatement stmt = adapter.getConnection().prepareCall("{CALL create_active_cart(?)}");
            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                cartId = rs.getInt("cart_id");
            }
        } catch (SQLException e) {
            System.err.println("Error al crear el carrito activo: " + e.getMessage());
        } finally {
            adapter.disconnect();
        }

        return cartId;
    }

    public static void removeProduct(int idItem, int iduser) {

        IDBAdapter adapter = DBAdapterFactory.getAdapter();
        String consulta = "DELETE FROM cart_items WHERE product_id = ? AND cart_id = ?;";

        try {
            int cart = getActiveCartId(iduser);

            PreparedStatement ps = adapter.getConnection().prepareStatement(consulta);
            ps.setInt(1, idItem);
            ps.setInt(2, cart);
            ps.executeUpdate();
            System.out.println("eliminado");

        } catch (SQLException e) {
            System.err.println("Error al crear el carrito activo: " + e.getMessage());
        } finally {
            adapter.disconnect();
        }

    }

    public void addProductsToCart(int idPersona, ObservableList<ProductCart> items) {
        IDBAdapter adapter = DBAdapterFactory.getAdapter();

        try {

            PreparedStatement psE = adapter.getConnection().prepareStatement("SET FOREIGN_KEY_CHECKS = 0;");
            psE.execute();

            int cart = getActiveCartId(idPersona);

            CallableStatement stmt = adapter.getConnection().prepareCall("{CALL add_product_to_cart(?, ?, ?, ?, ?, ?, ?)}");

            for (ProductCart item : items) {

                stmt.setInt(1, cart);
                stmt.setInt(2, item.getProductId());
                stmt.setLong(3, item.getQuantity());
                stmt.setDouble(4, item.getUnitePrice());
                stmt.setDouble(5, item.getSubtotal());
                stmt.setString(6, item.getNameProduct());
                stmt.setString(7, item.getImagePrincipal());

                int rowsAffected = stmt.executeUpdate();
                System.out.println("Rows affected: " + rowsAffected);
            }

            PreparedStatement psA = adapter.getConnection().prepareStatement("SET FOREIGN_KEY_CHECKS = 1;");
            psA.execute();

        } catch (SQLException e) {
            System.err.println("Error al agregar productos al carrito: " + e.getMessage());
        } finally {
            adapter.disconnect();
        }
    }
    
    public static void addProducItemsAndComplete(ProductClient order, ObservableList<ProductCart> order_item, int idUser) {

    IDBAdapter adapter = DBAdapterFactory.getAdapter();
    String consult1 = "INSERT INTO orders (user_id, total_amount, status, payment_method) VALUES (?, ?, ?, ?)";
    String consult2 = "INSERT INTO order_items (order_id, product_id, quantity, unit_price, subtotal, product_name, product_image) VALUES (?, ?, ?, ?, ?, ?, ?)";

    try {
        // Preparar el statement para insertar en `orders` con `RETURN_GENERATED_KEYS`
        PreparedStatement psE = adapter.getConnection().prepareStatement(consult1, Statement.RETURN_GENERATED_KEYS);
        psE.setInt(1, idUser);
        psE.setDouble(2, order.getTotalAmount());
        psE.setString(3, order.getStatus());
        psE.setString(4, order.getTypePay());

        // Ejecutar la inserción en `orders`
        psE.executeUpdate();

        // Obtener el `order_id` generado
        ResultSet rs = psE.getGeneratedKeys();
        int orderId = 0;
        if (rs.next()) {
            orderId = rs.getInt(1);
        }
        rs.close();
        psE.close();

        // Preparar el statement para insertar en `order_items`
        PreparedStatement stmt = adapter.getConnection().prepareStatement(consult2);

        // Insertar cada producto en `order_items`
        for (ProductCart item : order_item) {
            stmt.setInt(1, orderId);
            stmt.setInt(2, item.getProductId());
            stmt.setLong(3, item.getQuantity());
            stmt.setDouble(4, item.getUnitePrice());
            stmt.setDouble(5, item.getSubtotal());
            stmt.setString(6, item.getNameProduct());
            stmt.setString(7, item.getImagePrincipal());
            
            // Ejecutar la inserción para el producto actual
            stmt.executeUpdate();
        }

        // Cerrar el statement de `order_items`
        stmt.close();

    } catch (SQLException e) {
        System.err.println("Error al agregar productos al carrito: " + e.getMessage());
    } finally {
        adapter.disconnect();
    }
}

    public static ObservableList<ProductCart> getProductSave(int idUser) {

        IDBAdapter adapter = DBAdapterFactory.getAdapter();
        ObservableList<ProductCart> products = FXCollections.observableArrayList();

        try {

            int cart = getActiveCartId(idUser);

            int cartId = ordersDAO.getActiveCartId(idUser);
            if (cartId == -1) {

                cartId = ordersDAO.getOrCreateActiveCart(idUser);

            }

            CallableStatement stmt = adapter.getConnection().prepareCall("{CALL get_products_in_cart(?)}");
            stmt.setLong(1, cart);
            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                int idProduc = result.getInt("product_id");
                long quantity = result.getLong("quantity");
                double unityPrice = result.getDouble("unit_price");
                double subtotal = result.getDouble("subtotal");
                String name = result.getString("product_name");
                String image = result.getString("product_image");

                products.add(new ProductCart(idProduc, name, quantity, unityPrice, subtotal, image));
                System.out.println(products.toString());

            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "Error: " + e);

        } finally {

            adapter.disconnect();

        }
        return products;
    }

}
