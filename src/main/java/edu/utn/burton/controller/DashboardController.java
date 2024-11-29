/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.utn.burton.controller;

import edu.utn.burton.reports.GraphReport;
import edu.utn.burton.Burton;
import edu.utn.burton.dao.OrderDAO;
import edu.utn.burton.dao.ReportDataDAO;
import edu.utn.burton.entities.OrderDetailsView;
import edu.utn.burton.entities.OrderRow;
import edu.utn.burton.entities.ProductClient;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyListView;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
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

    private static String[] pdfReportNames = {"", ""};

    @FXML
    private MFXButton store;

    @FXML
    private Circle avatar;

    @FXML
    private Text username;

    @FXML
    private MFXButton logout;

    @FXML
    private MFXButton retunOrders;

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

    @FXML
    private MFXLegacyListView<HBox> HistorialAdmin;

    @FXML
    private ObservableList<HBox> observableOrderList;

    public DashboardController() {
        draw = new DrawGraphsController();
        rpDAO = new ReportDataDAO();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        observableOrderList = FXCollections.observableArrayList();
        
        loadOrders();
        
        llenarCombo();
        
        retunOrders.setVisible(false);
        
        user = new ShowUserInfo(this.avatar, this.username);
        user.loadUserInfo();

        store.setOnMouseClicked(ev -> {
            MenuController.initGui((Stage) store.getScene().getWindow());
        });
        
         retunOrders.setOnAction(ev -> {
            loadOrders();
            retunOrders.setVisible(false);
        });

        cleanFilters.setOnAction(ev -> {
            clearFilters();
        });

        logout.setOnMouseClicked(ev -> {
            LoginController.logout(store);
        });

        trendingSellsReport.setOnAction(ev -> {
            pdfReportNames[0] = "ReporteTopVentas";
            pdfReportNames[1] = "Top Ventas";
            if (cbxCategories.getSelectionModel().getSelectedIndex() != -1) {
                int categoryId = devolverValorCombo();
                draw.drawGraph(rpDAO.topSellsByCategories(categoryId), showGraphs);
            } else {
                draw.drawGraph(rpDAO.topSells(), showGraphs);
            }
        });

        turnoverReport.setOnAction(ev -> {
            pdfReportNames[0] = "ReporteMenosVendidos";
            pdfReportNames[1] = "Menos Vendidos";
            if (cbxCategories.getSelectionModel().getSelectedIndex() != -1) {
                int categoryId = devolverValorCombo();
                draw.drawGraph(rpDAO.lowSellsByCategories(categoryId), showGraphs);
            } else {
                draw.drawGraph(rpDAO.lowSells(), showGraphs);
            }
        });

        generatePDF.setOnAction(ev -> {
            GraphReport report = new GraphReport(pdfReportNames[0], pdfReportNames[1]);
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

    public void loadOrders() {
        observableOrderList.clear();
        List<ProductClient> orderDetailsList = OrderDAO.getOrdersByAdmin();

        if (orderDetailsList != null && !orderDetailsList.isEmpty()) {
            for (ProductClient order : orderDetailsList) {
                OrderRow orderRow = new OrderRow(order, event -> loadOrderDetails(order.getId()));
                observableOrderList.add(orderRow.getOrderRow());
            }
        } else {
            HBox emptyOrderRow = new HBox();
            emptyOrderRow.getStyleClass().add("empty-order-row");
            Label emptyLabel = new Label("No hay órdenes");
            emptyLabel.getStyleClass().add("empty-label");
            emptyOrderRow.getChildren().add(emptyLabel);
            observableOrderList.add(emptyOrderRow);
        }
        HistorialAdmin.setItems(observableOrderList);
    }

    public void loadOrderDetails(int orderId) {
        retunOrders.setVisible(true);
        OrderDetailsView detailsView = new OrderDetailsView(orderId);
        HistorialAdmin.setItems(detailsView.getOrderDetailsList());
    }

    public static void initGui(Stage stage) {
        try {

            FXMLLoader loader = new FXMLLoader(Burton.class.getResource("/fxml/Dashboard.fxml"));
            Parent root = loader.load();
            DashboardController controller = loader.getController();
            controller.llenarCombo();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Burton.class.getResource("/styles/menu.css").toExternalForm());
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
