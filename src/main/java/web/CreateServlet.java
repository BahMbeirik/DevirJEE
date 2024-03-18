package web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * Servlet implementation class ProduitServlet
 */
public class CreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String jdbcURL = "jdbc:mysql://localhost:3306/devoirjee?useSSL=false";
	private String jdbcUsername = "root";
	private String jdbcPassword = "";
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            // Récupérer la connexion à partir de la source de données configurée
        	Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            
            // Préparer la requête SQL
            String sql = "INSERT INTO produit (name, prix) VALUES (?, ?)";
            pstmt = conn.prepareStatement(sql);
            
            // Récupérer les paramètres depuis la requête HTTP
            String name = request.getParameter("name");
            String prix = request.getParameter("prix");
            
            // Définir les valeurs des paramètres
            pstmt.setString(1, name);
            pstmt.setString(2, prix);
            
            // Exécuter la requête
            pstmt.executeUpdate();
            
            // Rediriger ou afficher un message de succès
            response.sendRedirect("ReadServlet");
        } catch (Exception e) {
            // Gérer les exceptions (journalisation, affichage d'un message d'erreur, etc.)
            e.printStackTrace();
            // Rediriger ou afficher un message d'erreur
            response.sendRedirect("error.jsp");
        } finally {
            // Fermer la connexion et les ressources
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}