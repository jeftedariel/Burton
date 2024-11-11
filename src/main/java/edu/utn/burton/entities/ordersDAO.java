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

    public static int getActiveCartId(int userId) throws SQLException {
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
    
    public void removeProduct(int idItem, int iduser) {
        
        DBConnection conn = new DBConnection();
        String consulta = "DELETE FROM cart_items WHERE product_id = ? AND cart_id = ?;";
       
         
        
        try {
            int cart = getActiveCartId(iduser);
            
            
            PreparedStatement ps = conn.getConnection().prepareStatement(consulta);
            ps.setInt(1, idItem);
             ps.setInt(2, cart);
            ps.executeUpdate ();
            System.out.println("eliminado");
         
        } catch (SQLException e) {
            System.err.println("Error al crear el carrito activo: " + e.getMessage());
        } finally {
            conn.disconnect();
        }

      
    }


    public void addProductsToCart(int idPersona, ObservableList<ProductCart> items) {
        DBConnection conn = new DBConnection();

        try {
            
            PreparedStatement psE = conn.getConnection().prepareStatement("SET FOREIGN_KEY_CHECKS = 0;");
            psE.execute();
            
            int cart = getActiveCartId(idPersona);
 
            
            CallableStatement stmt = conn.getConnection().prepareCall("{CALL add_product_to_cart(?, ?, ?, ?, ?, ?, ?)}");

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

          
            PreparedStatement psA = conn.getConnection().prepareStatement("SET FOREIGN_KEY_CHECKS = 1;");
            psA.execute();

        } catch (SQLException e) {
            System.err.println("Error al agregar productos al carrito: " + e.getMessage());
        } finally {
            conn.disconnect();
        }
    }
    
    

    public static ObservableList<ProductCart> getProductSave(int idUser) {

        DBConnection conexion = new DBConnection();
        ObservableList<ProductCart> products = FXCollections.observableArrayList();

        try {

               
            int cart = getActiveCartId(idUser);
             
            int cartId = ordersDAO.getActiveCartId(idUser);
            if (cartId == -1) {
                
                cartId = ordersDAO.getOrCreateActiveCart(idUser); 
                
            }


            CallableStatement stmt = conexion.getConnection().prepareCall("{CALL get_products_in_cart(?)}");
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

            conexion.disconnect();

        }
        return products;
    }
    
    
}
