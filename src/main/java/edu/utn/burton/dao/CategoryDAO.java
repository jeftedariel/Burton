/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.burton.dao;

import edu.utn.burton.database.DBAdapterFactory;
import edu.utn.burton.database.IDBAdapter;
import edu.utn.burton.entities.Category;
import edu.utn.burton.handlers.APIHandler;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author jefte
 */
public class CategoryDAO {

    private static APIHandler api = new APIHandler();

    public static List<Category> getCategories() {
        try {
            return api.getList(Category.class, "categories", null);
        } catch (Exception e) {
            return null;
        }
    }

    public static List<Category> getDBCategories() {

        List<Category> categories = new ArrayList<>();

        String query = "SELECT * FROM categories";

        IDBAdapter adapter = DBAdapterFactory.getAdapter();
        try {
            CallableStatement stmt = adapter.getConnection().prepareCall(query);
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                categories.add(new Category(rs.getInt("id"), rs.getString("name"), rs.getString("image")));
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener las categorias de la db: " + e.getMessage());
        } finally {
            adapter.disconnect();
        }
        return categories;
    }

    public static void dumpCategories(List<Category> categories) {

        String query = "INSERT into categories(id, name, image) values(?,?,?)";
        IDBAdapter adapter = DBAdapterFactory.getAdapter();
        try {

            PreparedStatement pstmt = adapter.getConnection().prepareStatement(query);
            //adapter.getConnection().setAutoCommit(false);

            for (Category c : categories) {

                pstmt.setInt(1, c.id());
                pstmt.setString(2, c.name());
                pstmt.setString(3, c.image());

                pstmt.addBatch();
            }

            int[] r = pstmt.executeBatch();
            System.out.println(r.length + " New categories has been dumped");

        } catch (SQLException e) {
            System.err.println("Error while dumping categories." + e.getMessage());
        } finally {
            adapter.disconnect();
        }

    }

    public static Set<String> getCategoryNames() {
        try {
            return getCategories().stream()
                    .map(Category::name)
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            return null;
        }
    }
    
    public static Category getCategory(int id){
        Category c = null;
        try {
            c = api.getObject(Category.class, "categories/" + id, null);
        } catch (Exception e) {
            System.out.println("Error while getting category via id");
        }
        
        return c;
    }

    public static int getCategoryIdByName(String categoryName) {
        return getCategories()
                .stream()
                .filter(c -> c.name().equals(categoryName))
                .findFirst()
                .map(Category::id)
                .orElse(-1);
    }

}
