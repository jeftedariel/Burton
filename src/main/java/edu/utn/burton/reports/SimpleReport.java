/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.burton.reports;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;

/**
 *
 * @author jefte
 */
public class SimpleReport extends ReportTemplate{
    public SimpleReport(){
        //It is the name of the report File
        this.pdfNameFile="SimpleReport";
        this.reportTitleName= "Simple Example Report";
    }
    
    @Override
    protected void addContent(Document document) throws DocumentException {
        Paragraph p = new Paragraph("ope ope ope");
        
        document.add(p);
    }
    
}
