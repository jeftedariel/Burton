package edu.utn.burton.controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
import edu.utn.burton.Burton;
import edu.utn.burton.entities.Category;
import edu.utn.burton.entities.Message;
import edu.utn.burton.entities.MessageCell;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.RangeSlider;

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
    
    @FXML
    private RangeSlider rangeSlider;
    
    @FXML
    private Text rangeText;
    
    @FXML
    private TextField productNameTXT;
    
    @FXML
    private MFXButton search;
    
    @FXML
    private MFXButton openCart;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Set the max value for the Price Range
        rangeSlider.setHighValue(500);//Then sets the range Text
        setRange();
        
        loadProducts(false);
        setCbxCategories();

        //Listen if the min picker had been changed
        rangeSlider.lowValueProperty().addListener((observable, oldValue, newValue) -> {
            setRange();
        });
        //Listen if the max picker had been changed
        rangeSlider.highValueProperty().addListener((observable, oldValue, newValue) -> {
            setRange();
        });
        //Until user released mouse click it will do the query
        rangeSlider.setOnMouseReleased(ev -> {
            loadProducts(false);
        });

        // it is just for doin the pag selector
        pagination.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) -> {
            loadProducts(false);
        });
        
        cbxCategories.setOnAction(ev -> {
            loadProducts(false);
        });
        
        clearFilters.setOnAction(ev -> {
            clearFilters();
            loadProducts(false);
        });

        //If user search by name
        search.setOnAction(ev -> {
            loadProducts(true);
        });
        
        openCart.setOnMouseClicked(ev -> {
            CartMenuController.initGui((Stage) openCart.getScene().getWindow());
        });
        
        
    }
    
    public void loadProducts(boolean Search) {
        APIHandler api = new APIHandler(Product.class);
        List<Product> products = null;
        String categoryQuery = "";
        String searchByName = "";
        
        if (cbxCategories.getSelectionModel().getSelectedIndex() != -1) {
            categoryQuery = "&categoryId=" + retrieveCategoryId();
        }
        
        if (Search && productNameTXT != null) {
            searchByName = "&title=" + productNameTXT.getText();
        }
        
        try {
            products = api.obtenerProductos("products?offset=" + pagination.getCurrentPageIndex() * 10 + "&limit=10" + "&price_min=" + (int) rangeSlider.getLowValue() + "&price_max=" + (int) rangeSlider.getHighValue() + categoryQuery + searchByName);
            
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

       

        //Sets the pagination size
        //pagination.setMaxPageIndicatorCount(products.size()%10);
        productListView.getItems().clear();
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER);

        //Set the corresponding size for pagination
        //paginatio
        //Iterates the products to put only 4 in it
        if (products.size() == 0) {
            MessageCell cell = new MessageCell();
            Message msg = new Message("Aviso", "No se encontraron items para mostrar.", "/assets/warning.png");
            cell.updateItem(msg, false);
            
            row.getChildren().add(cell.getGraphic());
            productListView.getItems().add(row);
            row = new HBox(10);
            row.setAlignment(Pos.CENTER);
            
        }
        
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
        //Return all the available categories (with duplicates)
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
        //Set the categories into the Cbx, but without duplicates, bc its just text
        List<String> categoryNames = loadCategories().stream()
                .filter(cn -> !cn.name().isBlank())
                .map(Category::name)
                .distinct()
                .collect(Collectors.toList());
        pagination.setCurrentPageIndex(0);
        cbxCategories.setItems(FXCollections.observableArrayList(categoryNames));
    }
    
    public int retrieveCategoryId() {
        //Get the catg id by reverse, using its name
        //Thats why the cbx dont have duplicates.
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
        rangeSlider.setHighValue(500);
        rangeSlider.setLowValue(1);
    }
    
    public void setRange() {//Simple text feature to show the price range in a fancy and cool way
        rangeText.setText("Rango: $" + (int) rangeSlider.getLowValue() + " - $" + (int) rangeSlider.getHighValue());
        
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
