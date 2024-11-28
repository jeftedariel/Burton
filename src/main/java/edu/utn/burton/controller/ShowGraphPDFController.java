/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.burton.controller;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import edu.utn.burton.reports.ReportTemplate;
import io.github.palexdev.mfxcore.utils.fx.SwingFXUtils;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javafx.scene.chart.PieChart;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;

/**
 *
 * @author Justin Rodriguez Gonzalez
 */
public class ShowGraphPDFController extends ReportTemplate {


    public ShowGraphPDFController() {
       
    }

    @Override
    protected void addContent(Document document) throws DocumentException {
            PieChart pie = DrawGraphsController.pieReturn;

        try {
            // Capturar el gráfico como una imagen
            WritableImage snapshot = pie.snapshot(null, null);

            // Convertir WritableImage a BufferedImage
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(snapshot, null);

            // Crear un archivo temporal para almacenar la imagen
            File tempImageFile = File.createTempFile("chart", ".png");
            ImageIO.write(bufferedImage, "png", tempImageFile);

            // Agregar la imagen al documento PDF
            Image pdfImage = Image.getInstance(tempImageFile.getAbsolutePath());
            pdfImage.scaleToFit(500, 300); // Escalar la imagen si es necesario
            document.add(pdfImage);

            // Eliminar el archivo temporal después de usarlo
            tempImageFile.delete();

        } catch (IOException e) {
            e.printStackTrace();
            throw new DocumentException("Error al generar la imagen del gráfico.");
        }
    }
}
