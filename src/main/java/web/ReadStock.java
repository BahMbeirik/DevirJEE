package web;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import model.Stock;

public class ReadStock extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private String jdbcURL = "jdbc:mysql://localhost:3306/devoirjee";
    private String jdbcUsername = "root";
    private String jdbcPassword = "";
    private int recordsPerPage = 5;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        RequestDispatcher dispatcher = null;

        int currentPage = 1;
        if (request.getParameter("page") != null) {
            currentPage = Integer.parseInt(request.getParameter("page"));
        }

        String searchKeyword = request.getParameter("search");
        String query = "SELECT COUNT(*) FROM stock";
        String filter = "";

        // If a search keyword is provided, modify the query to search for it
        if (searchKeyword != null && !searchKeyword.isEmpty()) {
            filter = " WHERE idP LIKE ? OR quantite LIKE ?";
            query = "SELECT COUNT(*) FROM stock" + filter;
        }

        try {
            conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);

            pstmt = conn.prepareStatement(query);

            // If searching, set parameters for the prepared statement
            if (searchKeyword != null && !searchKeyword.isEmpty()) {
                pstmt.setString(1, "%" + searchKeyword + "%");
                pstmt.setString(2, "%" + searchKeyword + "%");
            }

            rs = pstmt.executeQuery();
            rs.next();
            int totalRecords = rs.getInt(1);

            int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);
            int start = (currentPage - 1) * recordsPerPage;

            String selectSQL = "SELECT * FROM stock";
            if (!filter.isEmpty()) {
                selectSQL += filter;
            }
            selectSQL += " LIMIT ?, ?";

            pstmt = conn.prepareStatement(selectSQL);
            int paramIndex = 1;
            if (searchKeyword != null && !searchKeyword.isEmpty()) {
                pstmt.setString(paramIndex++, "%" + searchKeyword + "%");
                pstmt.setString(paramIndex++, "%" + searchKeyword + "%");
            }
            pstmt.setInt(paramIndex++, start);
            pstmt.setInt(paramIndex, recordsPerPage);

            rs = pstmt.executeQuery();

            List<Stock> stocks = new ArrayList<>();
            while (rs.next()) {
                Stock stock = new Stock();
                stock.setId(rs.getInt("id"));
                stock.setIdP(rs.getString("idP"));
                stock.setQuantite(rs.getString("quantite"));
                stocks.add(stock);
            }

            request.setAttribute("stocks", stocks);
            request.setAttribute("totalRecords", totalRecords);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("searchKeyword", searchKeyword);

            dispatcher = request.getRequestDispatcher("ReadS.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
