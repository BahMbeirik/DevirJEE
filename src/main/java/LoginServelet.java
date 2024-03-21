
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Servlet implementation class LoginServelet
 */
@WebServlet("/LoginServelet")
public class LoginServelet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
//    /**
//     * @see HttpServlet#HttpServlet()
//     */
//    public LoginServelet() {
//        super();
//        // TODO Auto-generated constructor stub
//    }
//
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
					if(rc.getInt("role") == 0) {
						session.setAttribute("name", rc.getString("username"));
						dispatcher = request.getRequestDispatcher("clientIndex.jsp");
					}
					else{
						session.setAttribute("name", rc.getString("username"));
						dispatcher = request.getRequestDispatcher("index.jsp");
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