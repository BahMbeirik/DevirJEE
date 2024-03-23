package web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Panier;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;


public class QteIncDec extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		try(PrintWriter out = response.getWriter()) {
			String action = request.getParameter("action");
			int id = Integer.parseInt(request.getParameter("id"));
			ArrayList<Panier> panier_list = (ArrayList<Panier>) request.getSession().getAttribute("panier-list");
			if(action != null && id>=1) {
				if(action.equals("inc")) {
					for(Panier c:panier_list) {
						if(c.getId() == id) {
							int quantite = c.getQuantite();
							quantite++;
							c.setQuantite(quantite);
							response.sendRedirect("panier.jsp");
						}
					}
				}
				
				if(action.equals("dec")) {
					for(Panier c:panier_list) {
						if(c.getId() == id && c.getQuantite() > 1) {
							int quantite = c.getQuantite();
							quantite--;
							c.setQuantite(quantite);
							break;
						}
					}
					response.sendRedirect("panier.jsp");
				}
			}else {
				response.sendRedirect("panier.jsp");
			}
		}
	}

}
