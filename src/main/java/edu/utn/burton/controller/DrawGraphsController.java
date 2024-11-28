/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.burton.controller;

import edu.utn.burton.entities.Product;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyListView;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;

/**
 *
 * @author Justin Rodriguez Gonzalez
 */
public class DrawGraphsController {

    static BarChart barReturn;

    public DrawGraphsController() {

    }

    public void drawGraph(List<Product> productos, MFXLegacyListView showGraphs) {
        // asigana los datos que se obtienen para mostarr en el grafico
        ObservableList<XYChart.Series<String, Number>> data = FXCollections.observableArrayList();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Product p : productos) {
            String title = p.title();
            if (p.title().length() > 27) {
                title = "";
                for (int i = 0; i < 26; i++) {
                    title += p.title().charAt(i);
                }
                title += "...";

                series.getData().add(new XYChart.Data<>(p.title(), p.quantity()));
            }
        }
        data.add(series);

        // Crear el gráfico de barras
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        barChart.setData(data);
        barChart.setTitle("Gráfica de Barras en JAVA FX");

        // Recorre cada una de las barras
        for (XYChart.Data<String, Number> bar : series.getData()) {
            // Se usa para dar el nombre a una propiedad y asigna la etiqueta a cada porcion del gráfico
            Tooltip tooltip = new Tooltip(
                    // Formato de porcentaje con un solo decimal
                    String.format("%.1f%%", bar.getYValue().doubleValue()
                            / series.getData().stream()
                                    .mapToDouble(d -> d.getYValue().doubleValue())
                                    .sum() * 100)
            );
            Tooltip.install(bar.getNode(), tooltip);

            // Asignar un color aleatorio a cada barra
            bar.getNode().setStyle("-fx-bar-fill: " + generateRandomColor() + ";");
        }

        // Agregar el gráfico de barras al MFXLegacyListView
        showGraphs.getItems().clear();
        showGraphs.getItems().add(barChart);
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
