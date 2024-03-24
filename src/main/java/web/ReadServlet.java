package web;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.RequestDispatcher;
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
            // Récupérer la connexion à partir de la source de données configurée
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            
            // Pagination parameters
            int pageSize = 5; // Limiting to 5 items per page
            int currentPage = (request.getParameter("page") != null) ? Integer.parseInt(request.getParameter("page")) : 1;
            String searchQuery = request.getParameter("search"); // Get search query from request
            
            // Exécuter la requête SQL pour récupérer les produits avec pagination
            PreparedStatement ps = null;
            String sql = "SELECT * FROM produit";
            if (searchQuery != null && !searchQuery.isEmpty()) {
                sql += " WHERE name LIKE '%" + searchQuery + "%'";
            }
            sql += " LIMIT ? OFFSET ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, pageSize);
            ps.setInt(2, (currentPage - 1) * pageSize);
            rs = ps.executeQuery();
            
            // Traitement des résultats
            List<Produit> produits = new ArrayList<>();
            while (rs.next()) {
                Produit produit = new Produit();
                produit.setId(rs.getInt("id"));
                produit.setName(rs.getString("name"));
                produit.setPrix(rs.getDouble("prix"));
                produits.add(produit);
            }
            
            // Calculating pagination parameters
            int totalProducts = getTotalProducts(conn, searchQuery);
            int totalPages = (int) Math.ceil((double) totalProducts / pageSize);
            
            // Stocker les résultats dans l'objet request pour l'affichage dans la vue
            request.setAttribute("produits", produits);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("searchQuery", searchQuery);
            
            // Rediriger vers la page JSP pour afficher les résultats
            dispatcher = request.getRequestDispatcher("Read.jsp");
            dispatcher.forward(request, response);
            
        } catch (Exception e) {
            // Gérer les exceptions (journalisation, affichage d'un message d'erreur, etc.)
            e.printStackTrace();
            // Rediriger ou afficher un message d'erreur
            response.sendRedirect("error.jsp");
        } finally {
            // Fermer la connexion et les ressources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    // Method to get total number of products (for pagination)
    private int getTotalProducts(Connection conn, String searchQuery) throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM produit";
        if (searchQuery != null && !searchQuery.isEmpty()) {
            sql += " WHERE name LIKE '%" + searchQuery + "%'";
        }
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        rs.next();
        int total = rs.getInt("total");
        rs.close();
        stmt.close();
        return total;
    }
}
