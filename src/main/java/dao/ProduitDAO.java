package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Produit;

public class ProduitDAO {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/devoirjee";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    // SQL queries
    private static final String SELECT_PRODUCT_BY_ID = "SELECT id, name, prix FROM produit WHERE id = ?";
    private static final String UPDATE_PRODUCT = "UPDATE produit SET name = ?, prix = ? WHERE id = ?";

    // Retrieve product by ID from the database
    public Produit getProductById(int id) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(SELECT_PRODUCT_BY_ID)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Produit(rs.getInt("id"), 
                    		rs.getString("name"),
                            rs.getString("prix"));
                }
            }
        }
        return null; // Product not found
    }

    // Update product in the database
    public void updateProduct(Produit updatedProduct) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(UPDATE_PRODUCT)) {
            stmt.setString(1, updatedProduct.getName());
            stmt.setString(2, updatedProduct.getPrix());
            stmt.setInt(3, updatedProduct.getId());
            stmt.executeUpdate();
        }
    }

    // Validate product update (simple validation for demonstration)
    public boolean validateProductUpdate(Produit produit) {
        return produit != null && produit.getName() != null && !produit.getName().isEmpty();
    }
}

