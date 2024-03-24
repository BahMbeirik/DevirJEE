package web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class UpdateUser extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Connect to the database
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/devoirjee", "root", "");
            
            // Fetch the current role value from the database for the provided user ID
            int userId = Integer.parseInt(request.getParameter("userId")); // Assuming you pass the user ID from the form
            PreparedStatement fetchStatement = conn.prepareStatement("SELECT role FROM users WHERE id = ?");
            fetchStatement.setInt(1, userId);
            ResultSet resultSet = fetchStatement.executeQuery();
            int currentRole = 0;
            if (resultSet.next()) {
                currentRole = resultSet.getInt("role");
            }
            resultSet.close();
            
            // Toggle the role value
            int newRole = (currentRole == 0) ? 1 : 0;
            
            // Update the role value in the database for the provided user ID
            PreparedStatement updateStatement = conn.prepareStatement("UPDATE users SET role = ? WHERE id = ?");
            updateStatement.setInt(1, newRole);
            updateStatement.setInt(2, userId);
            updateStatement.executeUpdate();
            
            conn.close();
            response.sendRedirect("ReadUser"); // Redirect to a success page
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("Error.jsp"); // Redirect to an error page
        }
    }
}
