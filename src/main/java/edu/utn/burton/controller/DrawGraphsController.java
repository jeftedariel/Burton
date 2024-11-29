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
        // Asigna los datos para mostrar en el gráfico
        ObservableList<XYChart.Series<String, Number>> data = FXCollections.observableArrayList();
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        // Recorre la lista de productos
        for (Product p : productos) {
            String title = p.title();

            // Si el título es demasiado largo, se recorta y se agrega "..."
            if (title.length() > 27) {
                title = title.substring(0, 26) + "...";
            }

            // Agrega los datos a la serie
            series.getData().add(new XYChart.Data<>(title, p.quantity()));
        }

        // Agrega la serie al conjunto de datos
        data.add(series);

        // Crear el gráfico de barras
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        // Asigna los datos al gráfico
        barChart.setData(data);
        barReturn = barChart;

        // Recorre cada barra en el gráfico
        for (XYChart.Data<String, Number> bar : series.getData()) {
            // Crea un Tooltip para cada barra, mostrando el porcentaje
            Tooltip tooltip = new Tooltip(
                    String.format("%.1f%%", bar.getYValue().doubleValue()
                            / series.getData().stream()
                                    .mapToDouble(d -> d.getYValue().doubleValue())
                                    .sum() * 100)
            );
            Tooltip.install(bar.getNode(), tooltip);

            // Asigna un color aleatorio a cada barra
            bar.getNode().setStyle("-fx-bar-fill: " + generateRandomColor() + ";");
        }

        // Asegúrate de que showGraphs es un contenedor adecuado para agregar el gráfico
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
