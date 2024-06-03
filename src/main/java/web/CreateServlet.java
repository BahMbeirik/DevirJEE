package web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
                 maxFileSize = 1024 * 1024 * 10,      // 10MB
                 maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class CreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private String jdbcURL = "jdbc:mysql://localhost:3306/devoirjee?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);

            String sql = "INSERT INTO produit (name, prix, image_path) VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(sql);

            String name = request.getParameter("name");
            String prixStr = request.getParameter("prix");
            double prix = Double.parseDouble(prixStr); // Convert price to double
            Part filePart = request.getPart("image"); // Get the image part
            String fileName = extractFileName(filePart);

            // Determine the path to save the image
            String appPath = request.getServletContext().getRealPath("");
            String savePath = appPath + File.separator + "images";
            File fileSaveDir = new File(savePath);
            if (!fileSaveDir.exists()) {
                fileSaveDir.mkdirs();
            }
            filePart.write(savePath + File.separator + fileName);

            pstmt.setString(1, name);
            pstmt.setDouble(2, prix);
            pstmt.setString(3, "images" + File.separator + fileName);

            pstmt.executeUpdate();

            response.sendRedirect("ReadServlet");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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
