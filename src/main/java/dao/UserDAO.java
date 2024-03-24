//package dao;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//import model.Produit;
//import model.User;
//
//public class UserDAO {
//	private String jdbcURL = "jdbc:mysql://localhost:3306/devoirjee?useSSL=false";
//	private String jdbcUsername = "root";
//	private String jdbcPassword = "";
//
//
//	private static final String SELECT_USER_BY_ID = "select id,name,email from admins where id =?";
//	private static final String UPDATE_USERS_SQL = "update admins set name = ?,email= ? where id = ?;";
//
//	// Retrieve product by ID from the database
//    public User getUserById(int id) throws SQLException {
//        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
//             PreparedStatement stmt = conn.prepareStatement(SELECT_USER_BY_ID)) {
//            stmt.setInt(1, id);
//            try (ResultSet rs = stmt.executeQuery()) {
//                if (rs.next()) {
//                    return new User(rs.getInt("id"), 
//                    		rs.getString("name"),
//                            rs.getString("email"));
//                }
//            }
//        }
//        return null; // Product not found
//    }
//
//    // Update product in the database
//    public void updateUser(User updatedUser) throws SQLException {
//        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
//             PreparedStatement stmt = conn.prepareStatement(UPDATE_USERS_SQL)) {
//            stmt.setString(1, updatedUser.getName());
//            stmt.setString(2, updatedUser.getEmail());
//            stmt.setInt(3, updatedUser.getId());
//            stmt.executeUpdate();
//        }
//    }
//
//    // Validate product update (simple validation for demonstration)
//    public boolean validateProductUpdate(User user) {
//        return user != null && user.getName() != null && !user.getName().isEmpty();
//    }
//
//}
