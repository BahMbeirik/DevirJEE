package web;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Stock;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ReadStock extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private String jdbcURL = "jdbc:mysql://localhost:3306/devoirjee";
    private String jdbcUsername = "root";
    private String jdbcPassword = "";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        RequestDispatcher dispatcher = null;

        try {
            // Récupérer la connexion à partir de la source de données configurée
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);

            // Exécuter la requête SQL avec une jointure pour récupérer les stocks avec le nom du produit
            String sql = "SELECT s.id, s.idP, s.quantite, p.name AS productName " +
                         "FROM stock s " +
                         "JOIN produit p ON s.idP = p.id";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            // Traitement des résultats
            List<Stock> stocks = new ArrayList<>();
            while (rs.next()) {
                Stock stock = new Stock();
                stock.setId(rs.getInt("id"));
                stock.setIdP(rs.getString("idP"));
                stock.setQuantite(rs.getString("quantite"));
                stock.setProductName(rs.getString("productName")); // Récupérer le nom du produit

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
