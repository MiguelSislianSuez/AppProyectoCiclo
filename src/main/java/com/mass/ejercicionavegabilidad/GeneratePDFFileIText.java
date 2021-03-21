package com.mass.ejercicionavegabilidad;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Kentucky
 */
import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import java.io.*;
import javafx.collections.ObservableList;

public class GeneratePDFFileIText {

    // Fonts definitions (Definición de fuentes).
    private static final Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 26, Font.BOLDITALIC);
    private static final Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);

    private static final Font categoryFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private static final Font subcategoryFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private static final Font blueFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
    private static final Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

    
    public void createPDF(File pdfNewFile, ObservableList<TablaPacientes> pacientesDB) {
        // We create the document and set the file name.        
        // Creamos el documento e indicamos el nombre del fichero.

        try {
            Document document = new Document();
            try {

                PdfWriter.getInstance(document, new FileOutputStream(pdfNewFile));

            } catch (FileNotFoundException fileNotFoundException) {
                System.out.println("No se encontró el fichero para generar el pdf" + fileNotFoundException);
            }
            document.open();
            float[] columnWidths = {5, 10, 10, 10, 10, 5, 10, 10, 8, 20};
            
            PdfPTable table = new PdfPTable(columnWidths);
            table.setSpacingAfter(0);
            table.setSpacingBefore(0);
            table.setTotalWidth(350f);
            table.addCell("Id");
            table.addCell("Nombre");
            table.addCell("Apellido");
            table.addCell("Dni");
            table.addCell("Email");
            table.addCell("Generos");
            table.addCell("Telefono");
            table.addCell("Anio");
            table.addCell("NoSS");
            table.addCell("Datos");

            for (int aw = 0; aw < pacientesDB.size(); aw++) {
                TablaPacientes paciente = pacientesDB.get(aw);

                table.addCell(paciente.getId() + "");
                table.addCell(paciente.getNombre());
                table.addCell(paciente.getApellido());
                table.addCell(paciente.getDni());
                table.addCell(paciente.getEmail());
                table.addCell(paciente.getGeneros());
                table.addCell(paciente.getTelefono());
                table.addCell(paciente.getAnio() + "");
                table.addCell(paciente.getNoSS());
                table.addCell(paciente.getDatos());

            }
            document.add(table);

            document.close();
            System.out.println("¡Se ha generado tu hoja PDF!");
        } catch (DocumentException documentException) {
            System.out.println("Se ha producido un error al generar un documento: " + documentException);
        }
    }
}
