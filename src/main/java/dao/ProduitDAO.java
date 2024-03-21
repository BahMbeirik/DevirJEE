package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Produit;
import model.Panier;

public class ProduitDAO {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/devoirjee";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    
    private Connection con;
    

    // SQL queries
    private static final String SELECT_PRODUCT_BY_ID = "SELECT id, name, prix FROM produit WHERE id = ?";
    private static final String UPDATE_PRODUCT = "UPDATE produit SET name = ?, prix = ? WHERE id = ?";
    
    public ProduitDAO(Connection con) {
		super();
		this.con = con;
	}

    // Retrieve product by ID from the database
    public Produit getProductById(int id) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(SELECT_PRODUCT_BY_ID)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Produit(rs.getInt("id"), 
                    		rs.getString("name"),
                            rs.getDouble("prix"));
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
            stmt.setDouble(2, updatedProduct.getPrix());
            stmt.setInt(3, updatedProduct.getId());
            stmt.executeUpdate();
        }
    }

    // Validate product update (simple validation for demonstration)
    public boolean validateProductUpdate(Produit produit) {
        return produit != null && produit.getName() != null && !produit.getName().isEmpty();
    }
    
    
    public double getTotalCartPrice(ArrayList<Panier> panierList) {
        double sum = 0;
        try {
            if (panierList.size() > 0) {
                for (Panier item : panierList) {
                    String query = "select prix from produit where id=?";
                    PreparedStatement pst = this.con.prepareStatement(query);
                    pst.setInt(1, item.getId());
                    ResultSet rs = pst.executeQuery();
                    while (rs.next()) {
                        sum+=rs.getDouble("prix")*item.getQuantite();
                    }

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return sum;
    }

    
    public List<Panier> getCartProducts(ArrayList<Panier> panierList) {
    	ResultSet rs;
        List<Panier> produits = new ArrayList<>();
        try {
            if (panierList.size() > 0) {
                for (Panier item : panierList) {
                    String query = "select * from produit where id=?";
                    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                           PreparedStatement pst = conn.prepareStatement(query)) {
                    		pst.setInt(1, item.getId());
                    		pst.executeUpdate();
                    		rs = pst.executeQuery();
                       }
                    while (rs.next()) {
                        Panier row = new Panier();
                        row.setId(rs.getInt("id"));
                        row.setName(rs.getString("name"));
                        row.setPrix(rs.getDouble("prix")*item.getQuantite());
                        row.setQuantite(item.getQuantite());
                        produits.add(row);
                    }

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return produits;
    }
    
}

