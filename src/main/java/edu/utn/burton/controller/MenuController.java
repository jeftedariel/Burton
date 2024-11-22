package edu.utn.burton.controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
import edu.utn.burton.Burton;
import edu.utn.burton.dao.CategoryDAO;
import edu.utn.burton.dao.ProductDAO;
import edu.utn.burton.entities.Cart;
import edu.utn.burton.entities.Message;
import edu.utn.burton.entities.MessageCell;
import edu.utn.burton.entities.Product;
import edu.utn.burton.entities.ProductCart;
import edu.utn.burton.entities.ProductCell;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyListView;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    @FXML
    private MFXButton Historial;

    @FXML
    private ImageView avatar;

    @FXML
    private Text username;

    @FXML
    private MFXButton dashboard;

    @FXML
    private MFXButton logout;

    private ShowUserInfo showUserInfo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //Displays on header the user's data
        showUserInfo = new ShowUserInfo(avatar, username);
        showUserInfo.loadUserInfo();

        //Set the max value for the Price Range
        rangeSlider.setHighValue(500);//Then sets the range Text
        setRange();
        
        loadProducts(false);
        loadCategories();
        setPaginationSize(false);

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
            setPaginationSize(false);
        });

        // it is just for doin the pag selector
        pagination.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) -> {
            loadProducts(false);
        });

        cbxCategories.setOnAction(ev -> {
            loadProducts(false);
            setPaginationSize(false);
        });

        clearFilters.setOnAction(ev -> {
            clearFilters();
            loadProducts(false);
            setPaginationSize(false);
        });

        //If user search by name
        search.setOnAction(ev -> {
            loadProducts(true);
            setPaginationSize(true);
        });

        openCart.setOnMouseClicked(ev -> {
            CartMenuController.initGui((Stage) openCart.getScene().getWindow());
        });

        Historial.setOnMouseClicked(ev -> {
            Stage stage = (Stage) Historial.getScene().getWindow();
            HistorialController.initGui(stage);
        });

        logout.setOnMouseClicked(ev -> {
            LoginController.logout(logout);
        });

        dashboard.setOnMouseClicked(ev -> {
            DashboardController.initGui((Stage) dashboard.getScene().getWindow());
        });

        // It uses a addListener from ObservableLists, to update the CartProducts counter :) its simple but interesting
        setTotalProducts();
        Cart.getInstance().getProducts().addListener((ListChangeListener<ProductCart>) ev -> {
            setTotalProducts();
        });

    }

    public void setTotalProducts() {
        int t = 0;

        for (ProductCart pc : Cart.getInstance().getProducts()) {
            t += pc.getQuantity();
        }
        openCart.setText(String.valueOf(t));
    }
    
    public void setPaginationSize(boolean Search){
        String categoryQuery = "";
        String searchByName = "";

        if (cbxCategories.getSelectionModel().getSelectedIndex() != -1) {
            categoryQuery = "&categoryId=" + CategoryDAO.getCategoryIdByName(cbxCategories.getItems().get(cbxCategories.getSelectionModel().getSelectedIndex()).toString());
        }

        if (Search && productNameTXT != null) {
            searchByName = "&title=" + productNameTXT.getText();
        }
        
        
        
        int products = (ProductDAO.getProducts(0, 0, (int) rangeSlider.getLowValue(), (int) rangeSlider.getHighValue(), categoryQuery, searchByName).size());
        int pag=products/10;
        
        if(pag*10 < products){
            pag ++;
        }
        
        if(pag == 0){
            pagination.setPageCount(1);
            pagination.setCurrentPageIndex(0);
        }
        
        System.out.println("Total:" + products + "Paginas:" + pag);
        
        pagination.setPageCount(pag);
    }

    public void loadProducts(boolean Search) {
        String categoryQuery = "";
        String searchByName = "";

        if (cbxCategories.getSelectionModel().getSelectedIndex() != -1) {
            categoryQuery = "&categoryId=" + CategoryDAO.getCategoryIdByName(cbxCategories.getItems().get(cbxCategories.getSelectionModel().getSelectedIndex()).toString());
        }

        if (Search && productNameTXT != null) {
            searchByName = "&title=" + productNameTXT.getText();
        }
        
        List<Product> products=null;
        try {
             products = ProductDAO.getProducts(pagination.getCurrentPageIndex(), 10, (int) rangeSlider.getLowValue(), (int) rangeSlider.getHighValue(), categoryQuery, searchByName);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        displayProducts(products);
        

    }
    
    public void loadCategories() {
        //Set the categories into the Cbx, but without duplicates, bc its just text
        List<String> categoryNames = CategoryDAO.getCategoryNames().stream().toList();
        pagination.setCurrentPageIndex(0);
        cbxCategories.setItems(FXCollections.observableArrayList(categoryNames));
    }

    public void displayProducts(List<Product> products) {

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

    public void clearFilters() {
        pagination.setCurrentPageIndex(0);
        cbxCategories.getSelectionModel().select(-1);
        rangeSlider.setHighValue(500);
        rangeSlider.setLowValue(1);
    }

    public void setRange() {//Simple text feature to show the price range in a fancy and cool way
        rangeText.setText("Rango: $" + (int) rangeSlider.getLowValue() + " - $" + (int) rangeSlider.getHighValue());
    }

    public static void initGui(Stage stage) {

        try {

            Parent root = FXMLLoader.load(Burton.class.getResource("/fxml/Menu.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Burton E-Commerce");
            stage.setResizable(false);
            stage.getIcons().add(new Image(Burton.class.getResourceAsStream("/assets/icon.png")));
            scene.getStylesheets().add(Burton.class.getResource("/styles/menu.css").toExternalForm());

            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();  // Imprime el error para depuración
            Alerts.show(new Message("Error", "Hubo un problema al cargar la interfaz del menú."), Alert.AlertType.ERROR);
        }
    }

}
