package web;

import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class DeleteServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String jdbcURL = "jdbc:mysql://localhost:3306/devoirjee?useSSL=false";
	private String jdbcUsername = "root";
	private String jdbcPassword = "";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle GET requests here, e.g., redirect to a different page
//        response.sendRedirect("index.jsp"); // Redirect to index.jsp or any other appropriate page
		String id = request.getParameter("id");
		deleteDataByID(id);
		response.sendRedirect("ReadServlet");
    }
	


    // Method to delete data from the database by ID
    private boolean deleteDataByID(String id) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // Establish database connection
            conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);

            // Prepare SQL statement to delete data
            String sql = "DELETE FROM produit WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);

            // Execute the delete operation
            int rowsAffected = pstmt.executeUpdate();

            // Check if any rows were affected (i.e., data was deleted)
            if (rowsAffected > 0) {
                return true; // Deletion successful
            } else {
                return false; // No data deleted (ID not found or other issue)
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Deletion failed due to SQL exception
        } finally {
            // Close resources
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}