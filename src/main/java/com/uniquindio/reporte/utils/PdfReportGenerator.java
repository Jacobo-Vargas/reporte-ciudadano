package com.uniquindio.reporte.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

public class PdfReportGenerator {

    public static ByteArrayInputStream generate(String title, List<String> lines) throws DocumentException {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Título
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph titleParagraph = new Paragraph(title, titleFont);
            titleParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(titleParagraph);

            document.add(new Paragraph(" ")); // Espacio

            // Contenido
            Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
            for (String line : lines) {
                Paragraph para = new Paragraph(line, contentFont);
                para.setSpacingAfter(5);
                document.add(para);
            }

            document.close();
        } catch (DocumentException ex) {
            throw ex; // Se lanza hacia fuera para ser manejado donde se use
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
