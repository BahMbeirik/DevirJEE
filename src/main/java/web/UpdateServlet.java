package web;

import java.io.*;
import java.sql.*;

import dao.ProduitDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import model.Produit;

public class UpdateServlet extends HttpServlet {
	/**
	 * 
	 */
	private ProduitDAO productService;

    public void init() throws ServletException {
        // Initialize ProductService instance
        productService = new ProduitDAO(null);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve product ID from request parameter
        int id = Integer.parseInt(request.getParameter("id"));

        // Retrieve product by ID
        Produit productToUpdate = null;
		try {
			productToUpdate = productService.getProductById(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // Set productToUpdate as an attribute in request scope
        request.setAttribute("productToUpdate", productToUpdate);

        // Forward request to updateProduct.jsp for displaying product update form
        request.getRequestDispatcher("/Update.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve updated product information from request parameters
        int id = Integer.parseInt(request.getParameter("id"));
        String updatedName = request.getParameter("name");
        double updatedPrix = Double.parseDouble(request.getParameter("prix"));

        // Retrieve product by ID
        Produit productToUpdate = null;
		try {
			productToUpdate = productService.getProductById(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // Update product object with new information
        productToUpdate.setName(updatedName);
        productToUpdate.setPrix(updatedPrix);
        

        // Update product in the database
        try {
			productService.updateProduct(productToUpdate);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // Redirect user to a success page
        response.sendRedirect(request.getContextPath() + "/ReadServlet");
    }

}
