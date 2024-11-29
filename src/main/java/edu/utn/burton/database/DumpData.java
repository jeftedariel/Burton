/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.burton.database;

import edu.utn.burton.dao.CategoryDAO;
import edu.utn.burton.dao.ProductDAO;
import edu.utn.burton.entities.Category;
import edu.utn.burton.entities.products.Product;
import edu.utn.burton.handlers.APIHandler;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jefte
 */
public class DumpData {

    static APIHandler apiHandler = new APIHandler();

    public static void dumpData() {
        categories();
        products();
    }

    private static void categories() {
        List<Category> dbCategories = CategoryDAO.getDBCategories();
        List<Category> apiCategories = new ArrayList<>();
        try {
            apiCategories = apiHandler.getList(Category.class, "categories", null);
        } catch (Exception e) {
            System.out.println("Error while loading api products:" + e);
        }

        apiCategories.removeAll(dbCategories);

        if (apiCategories.size() > 0) {
            CategoryDAO.dumpCategories(apiCategories.stream().toList());
        }
        
    }

    private static void products() {
        List<Product> dbProducts = ProductDAO.getDBProducts();
        List<Product> apiProducts = new ArrayList<>();

        try {
            apiProducts = apiHandler.getList(Product.class, "products", null);
        } catch (Exception e) {
            System.out.println("Error while loading api products:" + e);
        }

        apiProducts.removeAll(dbProducts);

        if (apiProducts.size() > 0) {
            ProductDAO.dumpProducts(apiProducts.stream().toList());
        }
    }

}
