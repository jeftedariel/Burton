/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package edu.utn.burton.entities;

import edu.utn.burton.dao.CategoryDAO;
import edu.utn.burton.handlers.APIHandler;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author jefte
 */
public record Product(int id, String title, double price, String description, List<String> images, String creationAt, int quantity, Category category) {

    static APIHandler apiHandler = new APIHandler();

    public Product(int id, String title, double price, String description, List<String> images, String creationAt, int category_id) {
        this(id, title, price, description, images, creationAt, 1, CategoryDAO.getCategory(category_id));
    }

    public Product(int id, String title, double price, String description, List<String> images, int category_id) {
        this(id, title, price, description, images, "", 1, CategoryDAO.getCategory(category_id));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return id == product.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
