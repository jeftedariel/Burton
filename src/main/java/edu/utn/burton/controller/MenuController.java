/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.utn.burton.controller;

import edu.utn.burton.Burton;
import edu.utn.burton.entities.Category;
import edu.utn.burton.entities.Product;
import edu.utn.burton.entities.ProductCell;
import edu.utn.burton.handlers.APIHandler;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyListView;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jefte
 */
public class MenuController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private MFXLegacyListView<HBox> productListView;

    @FXML
    private Pagination pagination;

    @FXML
    private ComboBox cbxCategories;

    @FXML
    private MFXButton clearFilters;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        loadProducts();
        setCbxCategories();

        pagination.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) -> {
            loadProducts();
        });

        cbxCategories.setOnAction(ev -> {
            loadProducts();
        });

        clearFilters.setOnAction(ev -> {
            clearFilters();
        });
    }

    public void loadProducts() {
        APIHandler api = new APIHandler(Product.class);
        List<Product> products = null;
        String categoryQuery = "";
        
        if (cbxCategories.getSelectionModel().getSelectedIndex() != -1) {
            categoryQuery = "&categoryId=" + retrieveCategoryId();
        }

        try {
            products = api.obtenerProductos("products?offset=" + pagination.getCurrentPageIndex() * 10 + "&limit=10" + categoryQuery);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        productListView.getItems().clear();
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER);
        
        //Set the corresponding size for pagination
        //paginatio
        
        //Iterates the products to put only 4 in it
        for (int i = 0; i < products.size(); i++) {
            ProductCell cell = new ProductCell();
            cell.updateItem(products.get(i), false);
            row.getChildren().add(cell.getGraphic());

            // If it go up to 4, it creates a new line & a new Hbox
            if ((i + 1) % 4 == 0 || i == products.size() - 1) {
                productListView.getItems().add(row);
                row = new HBox(10);
                row.setAlignment(Pos.CENTER);
            }
        }
    }

    public List<Category> loadCategories() {
        APIHandler api = new APIHandler(Product.class);
        List<Category> categories = null;
        try {
            categories = api.getCategories("categories");
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return categories;
    }

    public void setCbxCategories() {
        List<String> categoryNames = loadCategories().stream()
                .filter(cn -> !cn.name().isBlank())
                .map(Category::name)
                .distinct()
                .collect(Collectors.toList());
        pagination.setCurrentPageIndex(0);
        cbxCategories.setItems(FXCollections.observableArrayList(categoryNames));
    }

    public int retrieveCategoryId() {
        return loadCategories()
                .stream()
                .filter(n -> n.name().equals(cbxCategories.getItems().get(cbxCategories.getSelectionModel().getSelectedIndex()).toString()))
                .distinct()
                .toList()
                .get(0).id();
    }

    public void clearFilters() {
        pagination.setCurrentPageIndex(0);
        cbxCategories.getSelectionModel().select(-1);
    }

    public static void initGui() {

        try {
            Parent root = FXMLLoader.load(Burton.class.getResource("/fxml/Menu.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Burton E-Commerce");
            stage.setResizable(false);
            stage.getIcons().add(new Image(Burton.class.getResourceAsStream("/assets/icon.png")));
            scene.getStylesheets().add(Burton.class.getResource("/styles/menu.css").toExternalForm());
            
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
        }
    }

}
