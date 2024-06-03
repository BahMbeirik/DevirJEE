package web;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import model.Produit;

public class ReadServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private String jdbcURL = "jdbc:mysql://localhost:3306/devoirjee";
    private String jdbcUsername = "root";
    private String jdbcPassword = "";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        RequestDispatcher dispatcher = null;       
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);

            int pageSize = 5;
            int currentPage = (request.getParameter("page") != null) ? Integer.parseInt(request.getParameter("page")) : 1;
            String searchQuery = request.getParameter("search");

            PreparedStatement ps = null;
            String sql = "SELECT * FROM produit";
            if (searchQuery != null && !searchQuery.isEmpty()) {
                sql += " WHERE name LIKE ?";
            }
            sql += " LIMIT ? OFFSET ?";
            ps = conn.prepareStatement(sql);
            if (searchQuery != null && !searchQuery.isEmpty()) {
                ps.setString(1, "%" + searchQuery + "%");
                ps.setInt(2, pageSize);
                ps.setInt(3, (currentPage - 1) * pageSize);
            } else {
                ps.setInt(1, pageSize);
                ps.setInt(2, (currentPage - 1) * pageSize);
            }
            rs = ps.executeQuery();

            List<Produit> produits = new ArrayList<>();
            while (rs.next()) {
                Produit produit = new Produit();
                produit.setId(rs.getInt("id"));
                produit.setName(rs.getString("name"));
                produit.setPrix(rs.getDouble("prix"));
                produit.setImagePath(rs.getString("image_path"));
                produits.add(produit);
            }

            int totalProducts = getTotalProducts(conn, searchQuery);
            int totalPages = (int) Math.ceil((double) totalProducts / pageSize);

            request.setAttribute("produits", produits);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("searchQuery", searchQuery);

            dispatcher = request.getRequestDispatcher("Read.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private int getTotalProducts(Connection conn, String searchQuery) throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM produit";
        if (searchQuery != null && !searchQuery.isEmpty()) {
            sql += " WHERE name LIKE ?";
        }
        PreparedStatement ps = conn.prepareStatement(sql);
        if (searchQuery != null && !searchQuery.isEmpty()) {
            ps.setString(1, "%" + searchQuery + "%");
        }
        ResultSet rs = ps.executeQuery();
        rs.next();
        int total = rs.getInt("total");
        rs.close();
        ps.close();
        return total;
    }
}
