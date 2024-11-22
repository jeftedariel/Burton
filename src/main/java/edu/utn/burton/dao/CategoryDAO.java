/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.burton.dao;

import edu.utn.burton.entities.Category;
import edu.utn.burton.handlers.APIHandler;
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
    
    public static Set<String> getCategoryNames(){
        try{
            return getCategories().stream()
                    .map(Category::name)
                    .collect(Collectors.toSet());
        } catch(Exception e){
            return null;
        }
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
