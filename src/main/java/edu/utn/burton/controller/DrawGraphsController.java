/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.burton.controller;

import edu.utn.burton.entities.Product;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyListView;
import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

/**
 *
 * @author Justin Rodriguez Gonzalez
 */
public class DrawGraphsController {


    static PieChart pieReturn;

    public DrawGraphsController() {
       
    }

   

    public void drawGraph(List<Product> productos, MFXLegacyListView showGraphs) {
        // asigana los datos que se obtienen para mostarr en el grafico
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        for (Product p : productos) {
            data.add(new PieChart.Data(p.title(), p.quantity()));
        }

        // Crear el gráfico de pastel 
        PieChart pie = new PieChart(data);
        pieReturn = pie;
        pie.setTitle("Gráfica de Pastel en JAVA FX");
        pie.setLabelsVisible(true);

        //recoore cada una de las porciones
        for (PieChart.Data slice : pie.getData()) {
            //se usa para dar el nombre a una propiedad y asigna la etiqueta a cada porcion del grafico 
            slice.nameProperty().bind(
                    //definimos el formato que le damos ahi le puse que sea el porcentage con un solo decimal 
                    //suma todos lo datos y depues los divide enre la cantidad 
                    //multiplico por 100 para que se vea bien el porcentahe
                    Bindings.format("%.1f%%", slice.pieValueProperty().divide(pie.getData().stream()
                            .mapToDouble(PieChart.Data::getPieValue)
                            .sum())
                            .multiply(100))
            );
        }

        // Agregar el gráfico de pastel al MFXLegacyListView
        showGraphs.getItems().clear();
        showGraphs.getItems().add(pie);

    }

    public static PieChart getPieReturn() {
        return pieReturn;
    }
 

}
