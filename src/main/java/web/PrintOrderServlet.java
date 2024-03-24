package web;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class PrintOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve order details from request parameters
        String date = request.getParameter("date");
        String name = request.getParameter("name");
        String quantity = request.getParameter("quantity");
        String price = request.getParameter("price");

        // Set response headers
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"order_receipt.pdf\"");

        // Create a new PDF document
        Document document = new Document();
        try {
            // Initialize PDF writer and output stream
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            // Set up fonts and styles
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.BLACK);
            Font labelFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.DARK_GRAY);
            Font valueFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);

            // Add title
            Paragraph title = new Paragraph("Recus du commande", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            // Add order details
            addKeyValue(document, "Date:", date, labelFont, valueFont);
            addKeyValue(document, "Product Name:", name, labelFont, valueFont);
            addKeyValue(document, "Quantity:", quantity, labelFont, valueFont);
            addKeyValue(document, "Price:", price, labelFont, valueFont);

        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            // Close the document
            document.close();
        }
    }

    private void addKeyValue(Document document, String key, String value, Font keyFont, Font valueFont) throws DocumentException {
        // Add key-value pair to the document
        Paragraph p = new Paragraph();
        Chunk chunkKey = new Chunk(key, keyFont);
        Chunk chunkValue = new Chunk(value, valueFont);
        p.add(chunkKey);
        p.add(new Chunk(" "));
        p.add(chunkValue);
        document.add(p);
        document.add(Chunk.NEWLINE);
    }
}
