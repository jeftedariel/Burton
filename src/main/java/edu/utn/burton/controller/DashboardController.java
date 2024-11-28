/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.utn.burton.controller;

import edu.utn.burton.Burton;
import edu.utn.burton.dao.ReportDataDAO;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyListView;
import java.io.IOException;
import java.net.URL;
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
    private MFXButton prueba;

    @FXML
    public MFXLegacyListView showGraphs;

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

        logout.setOnMouseClicked(ev -> {
            LoginController.logout(store);
        });

        trendingSellsReport.setOnMouseClicked(ev -> {
        });

        

        trendingSellsReport.setOnAction(ev -> {
            draw.drawGraph(rpDAO.topSells(), showGraphs);
        });

        turnoverReport.setOnAction(ev -> {
            draw.drawGraph(rpDAO.lowSells(), showGraphs);
            
        });
        
        cbxCategories.setOnMouseClicked(ev ->{
        draw.drawGraph(rpDAO.topSellsByCategories(devolverValorCombo()), showGraphs); 
        });

        prueba.setOnAction(ev -> {
            ShowGraphPDFController report = new ShowGraphPDFController();
            report.generate();
        });
    }

    public void llenarCombo() {
        if (cbxCategories != null) {

            cbxCategories.setItems(FXCollections.observableArrayList(rpDAO.getCategories().values()));

            cbxCategories.setOnAction(event -> {

                int categoryId = devolverValorCombo();
                System.out.println("ID de la categoría seleccionada: " + categoryId);
            });
        } else {
            System.out.println("Error: ComboBox no está inicializado.");
        }
    }

    public int devolverValorCombo() {

        String selectedCategory = (String) cbxCategories.getValue();

        if (selectedCategory != null) {

            for (Map.Entry<Integer, String> entry : rpDAO.getCategories().entrySet()) {
                if (entry.getValue().equals(selectedCategory)) {

                    return entry.getKey();
                }
            }
        }

        // Si no se encuentra la categoría, devolvemos un valor de error
        System.out.println("Error: No se ha seleccionado una categoría válida.");
        return -1; // Retornamos un valor negativo para indicar un error
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
