package web;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import model.Produit;

public class ClientProduitServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private String jdbcURL = "jdbc:mysql://localhost:3306/devoirjee";
    private String jdbcUsername = "root";
    private String jdbcPassword = "";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        RequestDispatcher dispatcher = null;

        // Pagination parameters
        int pageSize = 5;
        int currentPage = (request.getParameter("page") != null) ? Integer.parseInt(request.getParameter("page")) : 1;
        String searchQuery = request.getParameter("search");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);

            // Execute SQL query to fetch products
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT * FROM produit";
            
            // Apply search filter if searchQuery is not null
            if (searchQuery != null && !searchQuery.isEmpty()) {
                sql += " WHERE name LIKE '%" + searchQuery + "%'";
            }

            rs = stmt.executeQuery(sql);

            // Move the cursor to the start of the page
            int start = (currentPage - 1) * pageSize;
            if (start > 0) {
                rs.absolute(start);
            }

            // Retrieve products for the current page
            List<Produit> produits = new ArrayList<>();
            for (int i = 0; i < pageSize && rs.next(); i++) {
                Produit produit = new Produit();
                produit.setId(rs.getInt("id"));
                produit.setName(rs.getString("name"));
                produit.setPrix(rs.getDouble("prix"));
                produits.add(produit);
            }

            // Calculate total pages
            rs.last();
            int totalProducts = rs.getRow();
            int totalPages = (int) Math.ceil((double) totalProducts / pageSize);

            // Set attributes for JSP
            request.setAttribute("produits", produits);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("searchQuery", searchQuery);

            // Forward to JSP
            dispatcher = request.getRequestDispatcher("clientProduit.jsp");
            dispatcher.forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
