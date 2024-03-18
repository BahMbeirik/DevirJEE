package web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Produit;
import model.Stock;

import java.io.IOException;
import java.sql.SQLException;

import dao.ProduitDAO;
import dao.StockDAO;

/**
 * Servlet implementation class UpdateStock
 */
public class UpdateStock extends HttpServlet {
	private StockDAO productService;

    public void init() throws ServletException {
        // Initialize ProductService instance
        productService = new StockDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve product ID from request parameter
        int id = Integer.parseInt(request.getParameter("id"));

        // Retrieve product by ID
        Stock stockToUpdate = null;
		try {
			stockToUpdate = productService.getStockById(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // Set productToUpdate as an attribute in request scope
        request.setAttribute("stockToUpdate", stockToUpdate);

        // Forward request to updateProduct.jsp for displaying product update form
        request.getRequestDispatcher("/UpdateS.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve updated product information from request parameters
        int id = Integer.parseInt(request.getParameter("id"));
        String updatedIdP = request.getParameter("idP");
        String updatedQuantite = request.getParameter("quantite");

        // Retrieve product by ID
        Stock stockToUpdate = null;
		try {
			stockToUpdate = productService.getStockById(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // Update product object with new information
		stockToUpdate.setIdP(updatedIdP);
		stockToUpdate.setQuantite(updatedQuantite);
        // Update other fields as needed

        // Update product in the database
        try {
			productService.updateStock(stockToUpdate);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // Redirect user to a success page
        response.sendRedirect(request.getContextPath() + "/ReadStock");
    }


}
