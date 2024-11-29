/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.utn.burton.controller;

import edu.utn.burton.Burton;
import edu.utn.burton.dao.ReportDataDAO;
import edu.utn.burton.entities.Product;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyListView;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;

/**
 * FXML Controller class
 *
 * @author jefte
 */
public class DashboardController implements Initializable {

    /**
     * Initializes the controller class.
     */
    DrawGraphsController draw;
    ReportDataDAO rpDAO;

    private ShowUserInfo user;

    @FXML
    private MFXButton store;

    @FXML
    private Circle avatar;

    @FXML
    private Text username;

    @FXML
    private MFXButton logout;

    @FXML
    private MFXButton trendingSellsReport;

    @FXML
    private MFXButton comparativeReport;

    @FXML
    private MFXButton turnoverReport;

    @FXML
    private ComboBox cbxCategories;

    @FXML
    private MFXButton generatePDF;

    @FXML
    public MFXLegacyListView showGraphs;

    @FXML

    public MFXButton cleanFilters;

    public DashboardController() {
        draw = new DrawGraphsController();
        rpDAO = new ReportDataDAO();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        llenarCombo();
        user = new ShowUserInfo(this.avatar, this.username);
        user.loadUserInfo();

        store.setOnMouseClicked(ev -> {
            MenuController.initGui((Stage) store.getScene().getWindow());
        });

        cleanFilters.setOnAction(ev -> {
            clearFilters();
        });

        logout.setOnMouseClicked(ev -> {
            LoginController.logout(store);
        });

        trendingSellsReport.setOnAction(ev -> {

            if (cbxCategories.getSelectionModel().getSelectedIndex() != -1) {
                int categoryId = devolverValorCombo();
                draw.drawGraph(rpDAO.topSellsByCategories(categoryId), showGraphs);
            } else {
                draw.drawGraph(rpDAO.topSells(), showGraphs);
            }
        });

          turnoverReport.setOnAction(ev -> {
               
            if (cbxCategories.getSelectionModel().getSelectedIndex() != -1) {
                int categoryId = devolverValorCombo();
                draw.drawGraph(rpDAO.lowSellsByCategories(categoryId), showGraphs);
            } else {
                draw.drawGraph(rpDAO.lowSells(), showGraphs);
            }
        });

        generatePDF.setOnAction(ev -> {
            ShowGraphPDFController report = new ShowGraphPDFController();
            report.generate();
        });
    }

    public void llenarCombo() {
        if (cbxCategories != null) {
            cbxCategories.setItems(FXCollections.observableArrayList(rpDAO.getCategories().values()));
        }
    }

    public int devolverValorCombo() {
        String selectedCategory = (String) cbxCategories.getValue();
        if (selectedCategory != null) {
            for (Map.Entry<Integer, String> entry : rpDAO.getCategories().entrySet()) {
                if (entry.getValue().equals(selectedCategory)) {
                    System.out.println("esta es la categoria" + entry.getKey());
                    return entry.getKey();
                }
            }
        }
        return -1;
    }

    public void clearFilters() {
        cbxCategories.getSelectionModel().select(-1);
    }

    public static void initGui(Stage stage) {
        try {

            FXMLLoader loader = new FXMLLoader(Burton.class.getResource("/fxml/Dashboard.fxml"));
            Parent root = loader.load();
            DashboardController controller = loader.getController();
            controller.llenarCombo();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Burton.class.getResource("/styles/dashboard.css").toExternalForm());
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
