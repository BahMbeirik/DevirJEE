package web;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Produit;
import model.Stock;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet implementation class ReadStock
 */
public class ReadStock extends HttpServlet {
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
            String sql = "select * from stock";
            rs = stmt.executeQuery(sql);
            // Traitement des résultats
            List<Stock> stocks = new ArrayList<>();
            while (rs.next()) {
            	Stock stock = new Stock();
            	stock.setId(rs.getInt("id"));
            	stock.setIdP(rs.getString("idP"));
            	stock.setQuantite(rs.getString("quantite"));
            	stocks.add(stock);
            }
            
            // Stocker les résultats dans l'objet request pour l'affichage dans la vue
            request.setAttribute("stocks", stocks);
            
            
            // Rediriger vers la page JSP pour afficher les résultats
            dispatcher = request.getRequestDispatcher("ReadS.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            // Gérer les exceptions (journalisation, affichage d'un message d'erreur, etc.)
            e.printStackTrace();
            // Rediriger ou afficher un message d'erreur
            response.sendRedirect("Error.jsp");
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
