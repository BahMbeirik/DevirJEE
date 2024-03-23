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

import model.*;


public class AcheterToutServlet extends HttpServlet {
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
		
		try{

			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		     
	        String sql = "INSERT INTO orders (p_id, u_id, o_quantite, o_date) VALUES  (?, ?, ?, ?)";
	        pstmt = conn.prepareStatement(sql);
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
			HttpSession session = request.getSession();
            Integer userId = (Integer) session.getAttribute("id");
            
			ArrayList<Panier> panier_list = (ArrayList<Panier>) request.getSession().getAttribute("panier-list");
			if(panier_list != null) {
				for(Panier c:panier_list) {
//					Order order = new Order();
//					order.setId(c.getId());
//					order.setuId(auth.getId());
//					order.setQuantite(c.getQuantite());
//					order.setDate(formatter.format(date));
//					OrderDao oDao = new OrderDao(DbCon.getConnection());
//					boolean result = oDao.insertOrder(order);
//					if(!result) break;
			
					pstmt.setInt(1, c.getId());
			        pstmt.setInt(2, userId);
			        pstmt.setInt(3, c.getQuantite());
			        pstmt.setString(4, formatter.format(date));
		         
			        pstmt.executeUpdate();
				}
				panier_list.clear();
				response.sendRedirect("ordre.jsp");
			}else {
				response.sendRedirect("panier.jsp");
			}
		} catch (Exception e) {
	          e.printStackTrace();
	          	PrintWriter out = response.getWriter();
	          	out.print("error : " + e);
	          
	      } finally {
	          try {
	              if (pstmt != null) pstmt.close();
	              if (conn != null) conn.close();
	          } catch (SQLException e) {
	              e.printStackTrace();
	          }
	      }}
	}
