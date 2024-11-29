/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.burton.reports;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import edu.utn.burton.controller.DrawGraphsController;
import io.github.palexdev.mfxcore.utils.fx.SwingFXUtils;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javafx.scene.chart.BarChart;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;
import javax.swing.GroupLayout.Alignment;

/**
 *
 * @author Justin Rodriguez Gonzalez
 */
public class GraphReport extends ReportTemplate {

    public GraphReport(String pdfNameFile, String reportTitleName) {
        this.pdfNameFile = pdfNameFile;
        this.reportTitleName = reportTitleName;
    }

    @Override
    protected void addContent(Document document) throws DocumentException {
        BarChart bar = DrawGraphsController.getBarReturn();

        try {
            // Capturar el gráfico como una imagen
            WritableImage snapshot = bar.snapshot(null, null);

            // Convertir WritableImage a BufferedImage
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(snapshot, null);

            // Crear un archivo temporal para almacenar la imagen
            File tempImageFile = File.createTempFile("chart", ".png");
            ImageIO.write(bufferedImage, "png", tempImageFile);

            // Agregar la imagen al documento PDF
            Image pdfImage = Image.getInstance(tempImageFile.getAbsolutePath());
            pdfImage.scaleToFit(500, 300); // Escalar la imagen si es necesario
            pdfImage.setAlignment(Element.ALIGN_CENTER);
            document.add(pdfImage);

            // Eliminar el archivo temporal después de usarlo
            tempImageFile.delete();

        } catch (IOException e) {
            e.printStackTrace();
            throw new DocumentException("Error al generar la imagen del gráfico.");
        }
    }
}
