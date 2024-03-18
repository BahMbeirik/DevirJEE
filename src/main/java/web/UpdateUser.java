package web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Produit;
import model.User;

import java.io.IOException;
import java.sql.SQLException;

import dao.ProduitDAO;
import dao.UserDAO;

/**
 * Servlet implementation class UpdateUser
 */
public class UpdateUser extends HttpServlet {
	private UserDAO productService;

    public void init() throws ServletException {
        // Initialize ProductService instance
        productService = new UserDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve product ID from request parameter
        int id = Integer.parseInt(request.getParameter("id"));

        // Retrieve product by ID
        User usertToUpdate = null;
		try {
			usertToUpdate = productService.getUserById(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // Set productToUpdate as an attribute in request scope
        request.setAttribute("userToUpdate", usertToUpdate);

        // Forward request to updateProduct.jsp for displaying product update form
        request.getRequestDispatcher("/UpdateU.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve updated product information from request parameters
        int id = Integer.parseInt(request.getParameter("id"));
        String updatedName = request.getParameter("name");
        String updatedEmail = request.getParameter("email");

        // Retrieve product by ID
        User userToUpdate = null;
		try {
			userToUpdate = productService.getUserById(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // Update product object with new information
        userToUpdate.setName(updatedName);
        userToUpdate.setEmail(updatedEmail);
        // Update other fields as needed

        // Update product in the database
        try {
			productService.updateUser(userToUpdate);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // Redirect user to a success page
        response.sendRedirect(request.getContextPath() + "/ReadUser");
    }

}
