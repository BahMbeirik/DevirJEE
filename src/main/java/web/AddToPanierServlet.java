package web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Panier;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class AddToPanierServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		
		try(PrintWriter out = response.getWriter()){
			ArrayList<Panier> panierList = new ArrayList<>();
			
			int id = Integer.parseInt(request.getParameter("id"));
			Panier pn = new Panier();
			pn.setId(id);
			pn.setQuantite(1);
			
			HttpSession session = request.getSession();
			ArrayList<Panier> panier_list = (ArrayList<Panier>) session.getAttribute("panier-list");
			
			if (panier_list == null) {
                panierList.add(pn);
                session.setAttribute("panier-list", panierList);
                response.sendRedirect("ClientProduitServlet");
            } else {
            	panierList = panier_list;

                boolean exist = false;
                for (Panier c:panier_list) {
                    if (c.getId() == id) {
                        exist = true;
                        out.println("<h3 style='color:crimson; text-align: center'>Item Already in Cart. <a href='panier.jsp'>GO to Cart Page</a></h3>");
                    }
                }

                if (!exist) {
                	panierList.add(pn);
                    response.sendRedirect("ClientProduitServlet");
                }
            }
        
		}
	}

}
