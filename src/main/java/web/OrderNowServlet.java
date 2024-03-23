package web;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import connection.DbCon;
import dao.OrderDao;
import model.Order;
import model.Panier;

public class OrderNowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
	private String jdbcURL = "jdbc:mysql://localhost:3306/devoirjee?useSSL=false";
	private String jdbcUsername = "root";
	private String jdbcPassword = "";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doPost(request, response);
	  }
    

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	Connection conn = null;
    	PreparedStatement pstmt = null;
  		try {
          // Récupérer la connexion à partir de la source de données configurée
  			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
          
	          // Préparer la requête SQL
	         String sql = "INSERT INTO orders (p_id, u_id, o_quantite, o_date) VALUES  (?, ?, ?, ?)";
	         pstmt = conn.prepareStatement(sql);
	         
	         SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	         Date date = new Date();
	         String productId = request.getParameter("id");
	         int quantity = Integer.parseInt(request.getParameter("quantite"));
	         if (quantity <= 0) {
	             quantity = 1;
          }
	         
	        HttpSession session = request.getSession();
            Integer userId = (Integer) session.getAttribute("id");
	         
	         pstmt.setString(1, productId);
	         pstmt.setInt(2, userId);
	         pstmt.setInt(3, quantity);
	         pstmt.setString(4, formatter.format(date));
          
	         pstmt.executeUpdate();
	         ArrayList<Panier> panier_list = (ArrayList<Panier>) request.getSession().getAttribute("panier-list");
				if(panier_list != null) {
					for(Panier c:panier_list) {
						if(c.getId() == Integer.parseInt(productId)) {
							panier_list.remove(panier_list.indexOf(c));
							break;
							
						}
					}
				}
	         response.sendRedirect("panier.jsp");
      }catch (Exception e) {
          e.printStackTrace();
//          response.sendRedirect("error.jsp");
          	PrintWriter out = response.getWriter();
          	out.print("error : " + e);
          
          
      } finally {
          try {
              if (pstmt != null) pstmt.close();
              if (conn != null) conn.close();
          } catch (SQLException e) {
              e.printStackTrace();
          }
      }
    }
}
