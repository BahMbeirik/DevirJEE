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
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private String jdbcURL = "jdbc:mysql://localhost:3306/devoirjee";
	private String jdbcUsername = "root";
	private String jdbcPassword = "";
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        RequestDispatcher  dispatcher = null;
       
        
        try {
        	// Récupérer la connexion à partir de la source de données configurée
        	Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            
            // Exécuter la requête SQL pour récupérer tous les produits
            stmt = conn.createStatement();
            String sql = "select * from produit";
            rs = stmt.executeQuery(sql);
            // Traitement des résultats
            List<Produit> produits = new ArrayList<>();
            while (rs.next()) {
            	Produit produit = new Produit();
            	produit.setId(rs.getInt("id"));
            	produit.setName(rs.getString("name"));
                produit.setPrix(rs.getString("prix"));
                produits.add(produit);
            }
            
            // Stocker les résultats dans l'objet request pour l'affichage dans la vue
            request.setAttribute("produits", produits);
            
            
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
}
