
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


@WebServlet("/LoginServelet")
public class LoginServelet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String uemail = request.getParameter("username");
		String upass = request.getParameter("password");
		
		RequestDispatcher  dispatcher = null;
		HttpSession session = request.getSession();
		
		try {
			 Class.forName("com.mysql.cj.jdbc.Driver");
				//creer la connection 
				String url = "jdbc:mysql://localhost:3306/devoirjee";
				Connection conn = DriverManager.getConnection(url, "root", "");
				//cree un etat de connection un utilisent class statement
				PreparedStatement pst = conn.prepareStatement("select * from users where uemail = ? and password = ?");
				
				pst.setString(1, uemail);
				pst.setString(2,upass);
				
				ResultSet rc = pst.executeQuery();
				
				if(rc.next()) {
					request.getSession().setAttribute("id", rc.getInt("id"));
					if(rc.getInt("role") == 0) {
//						session.setAttribute("id", rc.getInt("id"));
						response.sendRedirect("clientIndex.jsp");
//						dispatcher = request.getRequestDispatcher("clientIndex.jsp");
					}
					else{
//						session.setAttribute("id", rc.getInt("id"));
						response.sendRedirect("index.jsp");
//						dispatcher = request.getRequestDispatcher("index.jsp");
					}
				}else {
					request.setAttribute("status", "failed");
					dispatcher = request.getRequestDispatcher("login.jsp");
				}
				dispatcher.forward(request, response);
				
				
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}