package web;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Produit;
import model.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet implementation class ReadUser
 */
public class ReadUser extends HttpServlet {
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
            String sql = "select * from users";
            rs = stmt.executeQuery(sql);
            // Traitement des résultats
            List<User> users = new ArrayList<>();
            while (rs.next()) {
            	User user = new User();
            	user.setId(rs.getInt("id"));
            	user.setUsername(rs.getString("username"));
            	user.setPassword(rs.getString("password"));
            	user.setUemail(rs.getString("uemail"));
            	user.setUmobile(rs.getString("umobile"));
            	user.setRole(rs.getString("role"));
                users.add(user);
            }
            
            // Stocker les résultats dans l'objet request pour l'affichage dans la vue
            request.setAttribute("users", users);
            
            
            // Rediriger vers la page JSP pour afficher les résultats
            dispatcher = request.getRequestDispatcher("ReadU.jsp");
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
