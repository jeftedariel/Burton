/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.burton.entities;

import edu.utn.burton.controller.DBConnection;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.ObservableList;

/**
 *
 * @author Justin Rodriguez Gonzalez
 */
public class ordersDAO {

    public int getOrCreateActiveCart(int userId) {

        int cartId = -1;
        DBConnection conn = new DBConnection();

        try {
            PreparedStatement psE = conn.getConnection().prepareStatement("SET FOREIGN_KEY_CHECKS = 0;");
            psE.execute();

            CallableStatement ps = conn.getConnection().prepareCall("{CALL get_or_create_active_cart(?)}");
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            PreparedStatement psA = conn.getConnection().prepareStatement("SET FOREIGN_KEY_CHECKS = 1;");
            psA.execute();

            if (rs.next()) {
                cartId = rs.getInt("cart_id");
            }
        } catch (SQLException e) {

            System.err.println("Error al ejecutar el procedimiento: " + e.getMessage());
        } finally {
            conn.disconnect();
        }

        return cartId;
    }

    public void completeCart(int cartId) {
        DBConnection conn = new DBConnection();

        try {

            CallableStatement stmt = conn.getConnection().prepareCall("{CALL complete_cart(?)}");
            stmt.setInt(1, cartId);
            stmt.executeUpdate();
        } catch (SQLException e) {

            System.err.println("Error al ejecutar el procedimiento: " + e.getMessage());
        } finally {
            conn.disconnect();
        }
    }

    public int getActiveCartId(int userId) throws SQLException {
        int cartId = -1;
        DBConnection conn = new DBConnection();

        try {

            CallableStatement stmt = conn.getConnection().prepareCall("{CALL get_active_cart_by_user(?)}");
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                cartId = rs.getInt("cart_id");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el carrito activo: " + e.getMessage());
        } finally {
            conn.disconnect();
        }

        return cartId;
    }

    public int createActiveCart(int userId) {
        int cartId = -1;
        DBConnection conn = new DBConnection();

        try {
            CallableStatement stmt = conn.getConnection().prepareCall("{CALL create_active_cart(?)}");
            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                cartId = rs.getInt("cart_id");
            }
        } catch (SQLException e) {
            System.err.println("Error al crear el carrito activo: " + e.getMessage());
        } finally {
            conn.disconnect();
        }

        return cartId;
    }

    public void addProductsToCart(int idPersona, ObservableList<ProductCart> items) {
    DBConnection conn = new DBConnection();

    try {
        // Se desactiva la comprobación de claves foráneas temporalmente
        PreparedStatement psE = conn.getConnection().prepareStatement("SET FOREIGN_KEY_CHECKS = 0;");
        psE.execute();

        // Obtenemos el carrito activo para el usuario
        System.out.println("id persona = " + idPersona);
         
        int cart = getActiveCartId(idPersona);
        System.out.println("carrito de la persona = " + cart);
        CallableStatement stmt = conn.getConnection().prepareCall("{CALL add_product_to_cart(?, ?, ?, ?, ?)}");

        for (ProductCart item : items) {
            System.out.println("cart: " + cart);
            System.out.println("productId: " + item.getProductId());
            System.out.println("quantity: " + item.getQuantity());
            System.out.println("unitPrice: " + item.getUnitePrice());
            System.out.println("subtotal: " + item.getSubtotal());

            stmt.setInt(1, cart);
            stmt.setInt(2, item.getProductId());
            stmt.setLong(3, item.getQuantity());
            stmt.setDouble(4, item.getUnitePrice());
            stmt.setDouble(5, item.getSubtotal());

            int rowsAffected = stmt.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected); // Verifica cuántas filas fueron afectadas
        }

        // Restauramos la comprobación de claves foráneas
        PreparedStatement psA = conn.getConnection().prepareStatement("SET FOREIGN_KEY_CHECKS = 1;");
        psA.execute();

    } catch (SQLException e) {
        System.err.println("Error al agregar productos al carrito: " + e.getMessage());
    } finally {
        conn.disconnect();
    }
}

}
