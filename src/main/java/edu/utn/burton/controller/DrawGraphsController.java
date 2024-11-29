/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.burton.controller;

import edu.utn.burton.entities.products.Product;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

/**
 *
 * @author Justin Rodriguez Gonzalez
 */
public class DrawGraphsController {

    static BarChart barReturn;

    public DrawGraphsController() {

    }

    public void drawGraph(List<Product> productos, BarChart<String, Number> barChart) {
        barReturn = barChart;
        // Asigna los datos para mostrar en el gráfico
        ObservableList<XYChart.Series<String, Number>> data = FXCollections.observableArrayList();
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        for (Product p : productos) {
            String title = p.title();

            // Si el título es demasiado largo, se recorta y se agrega "..."
            if (title.length() > 27) {
                title = title.substring(0, 26) + "...";
            }

            series.getData().add(new XYChart.Data<>(title, p.quantity()));
        }

        barChart.getData().clear();
        barChart.getData().add(series);
        barChart.setLegendVisible(false);
        
             
        for (XYChart.Data<String, Number> bar : series.getData()) {

            Node barNode = bar.getNode();
            if (barNode != null) {
                // Asigna un color aleatorio a cada barra
                barNode.setStyle("-fx-bar-fill: " + generateRandomColor() + ";");
            }
        }
    }

    private String generateRandomColor() {

        double red = Math.random();
        double green = Math.random();
        double blue = Math.random();

        return String.format("#%02X%02X%02X", (int) (red * 255), (int) (green * 255), (int) (blue * 255));
    }

    public static BarChart getBarReturn() {
        return barReturn;
    }

}
