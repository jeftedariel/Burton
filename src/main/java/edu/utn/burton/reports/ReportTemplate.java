/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.burton.reports;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import edu.utn.burton.controller.Alerts;
import edu.utn.burton.entities.Message;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 *
 * @author jefte
 */
public abstract class ReportTemplate {

    String pdfNameFile = "";
    String reportTitleName = "";

    public final void generate() {

        Document document = new Document();
        //This opens the "select a folder" tab, :) better than swing
        DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage stage = new Stage();
        File selectedDirectory = directoryChooser.showDialog(stage);

        if (selectedDirectory != null) {
            try {// Only if user press on Select folder
                
                String path = selectedDirectory.getPath();
                String separator = File.separator;  //             \ on Windows, / on Linux/Mac
                if (!path.endsWith(separator)) {
                    path += separator;  
                }
                
                PdfWriter.getInstance(document, new FileOutputStream(path + pdfNameFile +"_"+LocalDate.now()+ ".pdf"));
                System.out.println(path + pdfNameFile +"_"+LocalDate.now()+ ".pdf");
                document.open();

                // Header info (title & banner)
                addHeader(document, reportTitleName);

                // Then add the Report information
                //This is the part that i need to be override by other reportTypes
                addContent(document);

                document.close();
                Alerts.show(new Message("Exito", "El reporte ha sido generado correctamente!"), AlertType.INFORMATION);
            } catch (DocumentException | IOException de) {
                Alerts.show(new Message("Error", "Error al generar el pdf: \n" + de.getMessage()), AlertType.ERROR);
            }
        }
    }

    private void addHeader(Document document, String titleText) throws DocumentException, IOException {
        String path = getClass().getClassLoader().getResource("assets/burtonBanner.png").getPath();
    
        Image icon = Image.getInstance(path);
        icon.setAlignment(Element.ALIGN_CENTER);
        icon.scalePercent(30);
        Font fontTitle = new Font(Font.TIMES_ROMAN, 18, Font.BOLD);
        Paragraph title = new Paragraph(titleText, fontTitle);
        title.setAlignment(Element.ALIGN_CENTER);

        document.add(icon);
        document.add(title);
        document.add(new Paragraph(" "));
    }

    protected abstract void addContent(Document document) throws DocumentException;
}
