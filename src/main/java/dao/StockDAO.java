package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import model.Stock;

public class StockDAO {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/devoirjee";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    // SQL queries
    private static final String SELECT_STOCK_BY_ID = "SELECT id, idP, quantite FROM stock WHERE id = ?";
    private static final String UPDATE_STOCK = "UPDATE stock SET idP = ?, quantite = ? WHERE id = ?";

    // Retrieve product by ID from the database
    public Stock getStockById(int id) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(SELECT_STOCK_BY_ID)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Stock(rs.getInt("id"), 
                    		rs.getString("idP"),
                            rs.getString("quantite"));
                }
            }
        }
        return null; // Product not found
    }

    // Update product in the database
    public void updateStock(Stock updatedStock) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(UPDATE_STOCK)) {
            stmt.setString(1, updatedStock.getIdP());
            stmt.setString(2, updatedStock.getQuantite());
            stmt.setInt(3, updatedStock.getId());
            stmt.executeUpdate();
        }
    }

    // Validate product update (simple validation for demonstration)
    public boolean validateStockUpdate(Stock produit) {
        return produit != null && produit.getIdP() != null && !produit.getIdP().isEmpty();
    }

}
