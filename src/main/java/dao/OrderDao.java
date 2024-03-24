package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import model.*;

public class OrderDao {
	
	private Connection con;

	private String query;
    private PreparedStatement pst;
    private ResultSet rs;
    
    

	public OrderDao(Connection con) {
		super();
		this.con = con;
	}
	
	public boolean insertOrder(Order model) {
        boolean result = false;
        try {
            String query = "INSERT INTO orders (p_id, u_id, o_quantite, o_date) VALUES (?, ?, ?, ?)";
            pst = this.con.prepareStatement(query);
            pst.setInt(1, model.getId());
            pst.setInt(2, model.getuId());
            pst.setInt(3, model.getQuantite());
            pst.setString(4, model.getDate());

            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                result = true;
                System.out.println("Order inserted successfully.");
            } else {
                System.out.println("Failed to insert order.");
            }
        } catch (SQLException e) {
            System.out.println("Error inserting order: " + e.getMessage());
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing PreparedStatement: " + e.getMessage());
            }
        }

        return result;
    }

    public List<Order> userOrders(int id) {
        List<Order> list = new ArrayList<>();
        try {
            query = "select * from orders where u_id=? order by orders.o_id desc";
            pst = this.con.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            while (rs.next()) {	
                Order order = new Order();
                ProduitDAO  productDao = new ProduitDAO(this.con);
                int pId = rs.getInt("p_id");
                Produit product = productDao.getProductById(pId);
                order.setOrderId(rs.getInt("o_id"));
                order.setId(pId);
                order.setName(product.getName());
                order.setPrix(product.getPrix()*rs.getInt("o_quantite"));
                order.setQuantite(rs.getInt("o_quantite"));
                order.setDate(rs.getString("o_date"));
                list.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return list;
    }

    public void cancelOrder(int id) {
//        boolean result = false;
        try {
            query = "delete from orders where o_id=?";
            pst = this.con.prepareStatement(query);
            pst.setInt(1, id);
            pst.execute();
//            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.print(e.getMessage());
        }
//        return result;
    }
}