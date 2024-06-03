package web;

import java.io.*;
import java.sql.*;

import dao.ProduitDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.*;
import model.Produit;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
                 maxFileSize = 1024 * 1024 * 10,      // 10MB
                 maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class UpdateServlet extends HttpServlet {
    private ProduitDAO productService;

    public void init() throws ServletException {
        productService = new ProduitDAO(null);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Produit productToUpdate = null;
        try {
            productToUpdate = productService.getProductById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("productToUpdate", productToUpdate);
        request.getRequestDispatcher("/Update.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String updatedName = request.getParameter("name");
        double updatedPrix = Double.parseDouble(request.getParameter("prix"));

        Produit productToUpdate = null;
        try {
            productToUpdate = productService.getProductById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        productToUpdate.setName(updatedName);
        productToUpdate.setPrix(updatedPrix);

        Part filePart = request.getPart("image");
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = extractFileName(filePart);
            String appPath = request.getServletContext().getRealPath("");
            String savePath = appPath + File.separator + "images";
            File fileSaveDir = new File(savePath);
            if (!fileSaveDir.exists()) {
                fileSaveDir.mkdirs();
            }
            String filePath = savePath + File.separator + fileName;
            filePart.write(filePath);

            String oldImagePath = productToUpdate.getImagePath();
            productToUpdate.setImagePath("images" + File.separator + fileName);

            File oldFile = new File(appPath + File.separator + oldImagePath);
            if (oldFile.exists()) {
                oldFile.delete();
            }
        }

        try {
            productService.updateProduct(productToUpdate);
            productService.updateImagePath(productToUpdate.getId(), productToUpdate.getImagePath());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/ReadServlet");
    }

    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }
}
