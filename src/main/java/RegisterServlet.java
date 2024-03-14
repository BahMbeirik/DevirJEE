

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Servlet implementation class Register
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

//	/**
//	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
//	 */
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
//	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String uname = request.getParameter("name");
		String uemail = request.getParameter("email");
		String upass = request.getParameter("pass");
		String umobile = request.getParameter("contact");
		RequestDispatcher  dispatcher = null;
		Connection conn = null;
		
		try {
			 Class.forName("com.mysql.cj.jdbc.Driver");
				//creer la connection 
				String url = "jdbc:mysql://localhost:3306/devoirjee?useSSL=false";
			    conn = DriverManager.getConnection(url, "root", "");
				//cree un etat de connection un utilisent class statement
				PreparedStatement pst = conn.prepareStatement("insert into users(username,password,uemail,umobile ) values (?,?,?,?)");
				
				pst.setString(1,uname);
				pst.setString(2,upass);
				pst.setString(3, uemail);
				pst.setString(4, umobile);
				
				int n = pst.executeUpdate();
				dispatcher = request.getRequestDispatcher("registration.jsp");
				if(n >0) {
					request.setAttribute("status", "success");
					
				}else {
					request.setAttribute("status", "failed");
				}
				dispatcher.forward(request, response);
				
				
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
