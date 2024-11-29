/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.burton.dao;

import edu.utn.burton.database.DBAdapterFactory;
import edu.utn.burton.database.IDBAdapter;
import edu.utn.burton.entities.ProductCart;
import edu.utn.burton.entities.ProductClient;
import edu.utn.burton.entities.UserSession;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Justin Rodriguez Gonzalez
 */
public class OrderDAO {

    public static int getOrCreateActiveCart(int idUser) {

        int cartId = -1;
        IDBAdapter adapter = DBAdapterFactory.getAdapter();
        try {
            PreparedStatement psE = adapter.getConnection().prepareStatement("SET FOREIGN_KEY_CHECKS = 0;");
            psE.execute();

            CallableStatement ps = adapter.getConnection().prepareCall("{CALL get_or_create_active_cart(?)}");
            ps.setInt(1, idUser);
            ResultSet rs = ps.executeQuery();

            PreparedStatement psA = adapter.getConnection().prepareStatement("SET FOREIGN_KEY_CHECKS = 1;");
            psA.execute();

            if (rs.next()) {
                cartId = rs.getInt("cart_id");
            }
        } catch (SQLException e) {

            System.out.println("Error al ejecutar el procedimiento: " + e.getMessage());
        } finally {
            adapter.disconnect();
        }

        return cartId;
    }

    public static void completeCart(int usuarioId) {

        IDBAdapter adapter = DBAdapterFactory.getAdapter();

        try {
            int cartId = getActiveCartId(UserSession.getInstance().getId());

            CallableStatement stmt = adapter.getConnection().prepareCall("{CALL complete_cart(?)}");
            stmt.setInt(1, cartId);
            stmt.executeUpdate();
        } catch (SQLException e) {

            System.out.println("Error al ejecutar el procedimiento: " + e.getMessage());
        } finally {
            adapter.disconnect();
        }
    }

    public static int getActiveCartId(int idUser) throws SQLException {
        int cartId = -1;
        IDBAdapter adapter = DBAdapterFactory.getAdapter();

        try {

            CallableStatement stmt = adapter.getConnection().prepareCall("{CALL get_active_cart_by_user(?)}");
            stmt.setInt(1, idUser);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                cartId = rs.getInt("cart_id");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el carrito activo: " + e.getMessage());
        } finally {
            adapter.disconnect();
        }

        return cartId;
    }

    public static ObservableList<ProductClient> getOrdersByUser(int usuarioId) {

        ObservableList<ProductClient> orderDetailsList = FXCollections.observableArrayList();
        IDBAdapter adapter = DBAdapterFactory.getAdapter();

        try {
            CallableStatement stmt = adapter.getConnection().prepareCall("{CALL get_orders_by_user(?)}");
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                Date orderDate = rs.getDate("order_date");
                double totalAmount = rs.getDouble("total_amount");

                orderDetailsList.add(new ProductClient(orderId, orderDate, totalAmount));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener las órdenes: " + e.getMessage());
        } finally {
            adapter.disconnect();
        }

        return orderDetailsList;
    }

    public static ObservableList<ProductClient> getOrdersByAdmin() {

        ObservableList<ProductClient> orderDetailsList = FXCollections.observableArrayList();
        IDBAdapter adapter = DBAdapterFactory.getAdapter();

        try {
            CallableStatement stmt = adapter.getConnection().prepareCall("{CALL get_orders_by_Admin}");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                Date orderDate = rs.getDate("order_date");
                double totalAmount = rs.getDouble("total_amount");

                orderDetailsList.add(new ProductClient(orderId, orderDate, totalAmount));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener las órdenes: " + e.getMessage());
        } finally {
            adapter.disconnect();
        }
        return orderDetailsList;
    }

    //Se utiliza como clave del map un String, ya que representa el nombre de las columnas, y un objeto para obtener cualquier tipo de dato
    public static ObservableList<Map<String, Object>> loadOrderItemsByOrderId(int orderId) {

        ObservableList<Map<String, Object>> orderItemsList = FXCollections.observableArrayList();
        String procedureCall = "{CALL get_order_items_by_order_id(?)}";

        try (Connection conn = DBAdapterFactory.getAdapter().getConnection(); CallableStatement stmt = conn.prepareCall(procedureCall)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("order_item_id", rs.getInt("order_item_id"));
                row.put("order_id", rs.getInt("order_id"));
                row.put("product_id", rs.getInt("product_id"));
                row.put("quantity", rs.getInt("quantity"));
                row.put("subtotal", rs.getInt("subtotal"));
                row.put("title", rs.getString("title"));

                String images = rs.getString("images");
                if (images != null && !images.isEmpty()) {
                    String[] imageUrls = images.split(","); // Separar las URLs por coma
                    row.put("images", imageUrls[0].trim()); // Agregar solo el primer URL
                } else {
                    row.put("images", null); // Manejar casos en los que 'images' sea null o vacío
                }

                orderItemsList.add(row);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener los productos de la orden: " + e.getMessage());
        }

        return orderItemsList;
    }

    public static int createActiveCart(int idUser) {
        int cartId = -1;
        IDBAdapter adapter = DBAdapterFactory.getAdapter();

        try {
            CallableStatement stmt = adapter.getConnection().prepareCall("{CALL create_active_cart(?)}");
            stmt.setInt(1, idUser);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                cartId = rs.getInt("cart_id");
            }
        } catch (SQLException e) {
            System.out.println("Error al crear el carrito activo: " + e.getMessage());
        } finally {
            adapter.disconnect();
        }

        return cartId;
    }

    public static void removeProduct(int idItem) {

        IDBAdapter adapter = DBAdapterFactory.getAdapter();
        String consulta = "DELETE FROM cart_items WHERE product_id = ? AND cart_id = ?;";

        try {
            int cart = getActiveCartId(UserSession.getInstance().getId());
            PreparedStatement psF = adapter.getConnection().prepareStatement("SET foreign_key_checks = 1;");
            psF.execute();
            PreparedStatement ps = adapter.getConnection().prepareStatement(consulta);
            ps.setInt(1, idItem);
            ps.setInt(2, cart);
            ps.executeUpdate();
            System.out.println("eliminado");

        } catch (SQLException e) {
            System.out.println("Error al crear el carrito activo: " + e.getMessage());
        } finally {
            adapter.disconnect();
        }

    }

    public static void cancelCart() {

        IDBAdapter adapter = DBAdapterFactory.getAdapter();
        String consulta = "DELETE FROM carts WHERE cart_id = ?";

        try {
            int cart = getActiveCartId(UserSession.getInstance().getId());
            PreparedStatement ps = adapter.getConnection().prepareStatement(consulta);
            ps.setInt(1, cart);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al crear el carrito activo: " + e.getMessage());
        } finally {
            adapter.disconnect();
        }

    }

    public static void addProductsToCart(ObservableList<ProductCart> items) {
        IDBAdapter adapter = DBAdapterFactory.getAdapter();

        try {

            PreparedStatement psE = adapter.getConnection().prepareStatement("SET FOREIGN_KEY_CHECKS = 0;");
            psE.execute();

            int cart = getActiveCartId(UserSession.getInstance().getId());

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
            System.out.println("Error al agregar productos al carrito: " + e.getMessage());
        } finally {
            adapter.disconnect();
        }
    }

    public static void addProducItemsAndComplete(ProductClient order, ObservableList<ProductCart> order_item, int usuarioId) {

        IDBAdapter adapter = DBAdapterFactory.getAdapter();
        String consult1 = "INSERT INTO orders (user_id, total_amount, status, payment_method) VALUES (?, ?, ?, ?)";
        String consult2 = "INSERT INTO order_items (order_id, product_id, quantity, subtotal) VALUES (?, ?, ?, ?)";

        try {
            // Preparar el statement para insertar en `orders` con `RETURN_GENERATED_KEYS`
            PreparedStatement psE = adapter.getConnection().prepareStatement(consult1, Statement.RETURN_GENERATED_KEYS);
            psE.setInt(1, UserSession.getInstance().getId());
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
                stmt.setDouble(4, item.getSubtotal());

                // Ejecutar la inserción para el producto actual
                stmt.executeUpdate();
            }

            // Cerrar el statement de `order_items`
            stmt.close();

        } catch (SQLException e) {
            System.out.println("Error al agregar productos al carrito: " + e.getMessage());
        } finally {
            adapter.disconnect();
        }
    }

    public static ObservableList<ProductCart> getProductSave() {

        IDBAdapter adapter = DBAdapterFactory.getAdapter();
        ObservableList<ProductCart> products = FXCollections.observableArrayList();

        try {

            int cart = getActiveCartId(UserSession.getInstance().getId());

            int cartId = OrderDAO.getActiveCartId(UserSession.getInstance().getId());
            if (cartId == -1) {

                cartId = OrderDAO.getOrCreateActiveCart(UserSession.getInstance().getId());

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
