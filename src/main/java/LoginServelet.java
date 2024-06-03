
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

import org.mindrot.jbcrypt.BCrypt;


@WebServlet("/LoginServelet")
public class LoginServelet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uemail = request.getParameter("username");
        String upass = request.getParameter("password");

        HttpSession session = request.getSession();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/devoirjee";
            Connection conn = DriverManager.getConnection(url, "root", "");
            PreparedStatement pst = conn.prepareStatement("select * from users where uemail = ?");
            pst.setString(1, uemail);
            ResultSet rc = pst.executeQuery();

            if (rc.next() && BCrypt.checkpw(upass, rc.getString("password"))) {
                session.setAttribute("id", rc.getInt("id"));
                if (rc.getInt("role") == 0) {
                    response.sendRedirect("ClientProduitServlet");
                } else {
                    response.sendRedirect("index.jsp");
                }
            } else {
                request.setAttribute("status", "failed");
                RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
                dispatcher.forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}