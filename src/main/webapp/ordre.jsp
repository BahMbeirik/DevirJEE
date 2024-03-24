<%@page import="java.util.List"%>
<%@page import="model.Order"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="dao.OrderDao"%>
<%@page import="connection.DbCon"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.stream.Collectors"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
// Pagination parameters
int pageSize = 5; // Limiting to 5 items per page
int currentPage = (request.getParameter("page") != null) ? Integer.parseInt(request.getParameter("page")) : 1;
String searchQuery = request.getParameter("search");

DecimalFormat dcf = new DecimalFormat("#.##");
request.setAttribute("dcf", dcf);
session = request.getSession();
Integer auth = (Integer) session.getAttribute("id");

OrderDao orderDao = new OrderDao(DbCon.getConnection());
List<Order> allOrders = orderDao.userOrders(auth);

List<Order> filteredOrders = allOrders;
if (searchQuery != null && !searchQuery.isEmpty()) {
    filteredOrders = allOrders.stream()
            .filter(order -> order.getName().toLowerCase().contains(searchQuery.toLowerCase()))
            .collect(Collectors.toList());
}

int totalOrders = filteredOrders.size();
int totalPages = (int) Math.ceil((double) totalOrders / pageSize);

// Ensure currentPage is within valid range
if (currentPage < 1) {
    currentPage = 1;
} else if (currentPage > totalPages) {
    currentPage = totalPages;
}

// Calculate start and end indices for sublist
int start = Math.max((currentPage - 1) * pageSize, 0); // Ensure start index is non-negative
int end = Math.min(start + pageSize, totalOrders);

// Extract orders for the current page
List<Order> ordersForPage = filteredOrders.subList(start, end);
%>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des Produits</title>
    <link href="css/index-styles.css" rel="stylesheet" />
</head>
<body id="page-top">
	<nav
		class="navbar navbar-expand-lg bg-secondary text-uppercase fixed-top"
		id="mainNav">
		<div class="container">
			<a class="navbar-brand" href="clientIndex.jsp">Boutique</a>
			<button
				class="navbar-toggler text-uppercase font-weight-bold bg-primary text-white rounded"
				type="button" data-bs-toggle="collapse"
				data-bs-target="#navbarResponsive" aria-controls="navbarResponsive"
				aria-expanded="false" aria-label="Toggle navigation">
				Menu <i class="fas fa-bars"></i>
			</button>
			<div class="collapse navbar-collapse" id="navbarResponsive">
				<ul class="navbar-nav ms-auto">
					<li class="nav-item mx-0 mx-lg-1"><a
						class="nav-link py-3 px-0 px-lg-3 rounded" href="ClientProduitServlet">Produits</a></li>
					<li class="nav-item mx-0 mx-lg-1"><a
						class="nav-link py-3 px-0 px-lg-3 rounded" href="panier.jsp">Panier</a></li>
					<li class="nav-item mx-0 mx-lg-1"><a
						class="nav-link py-3 px-0 px-lg-3 rounded" href="ordre.jsp">Les ordres</a></li>
					<li class="nav-item mx-0 mx-lg-1"><a
						class="nav-link py-3 px-0 px-lg-3 rounded" href="login.jsp"><button class="btn btn-danger">Logout</button></a></li>
					
				</ul>
			</div>
		</div>
	</nav>
<body>
	<div class="container" style="margin-top: 15%;">
    
    <div class="d-flex align-items-center justify-content-center mb-4">
	    <form action="ordre.jsp" method="GET" class="input-group w-25">
	        <input name="search" placeholder="Rcherche..." value="<%= (searchQuery != null) ? searchQuery : "" %>">
	        <input type="submit" class="btn btn-outline-primary" data-mdb-ripple-init value="Chercher">
	    </form>
     </div>

     
		<table class="table  table-striped">
			<thead>
				<tr>
					<th scope="col">Date</th>
					<th scope="col">Nom</th>
					<th scope="col">Quantite</th>
					<th scope="col">Prix</th>
					<th scope="col">Cancel</th>
				</tr>
			</thead>
			<tbody>
			
			<% for (Order o : ordersForPage) { %>
                <tr>
                	<form action="PrintOrderServlet" method="POST" target="_blank">
                    <td><input type="hidden" name="date" value="<%= o.getDate() %>"><%= o.getDate() %></td>
                    <td><input type="hidden" name="name" value="<%= o.getName() %>"><%= o.getName() %></td>
                    <td><input type="hidden" name="quantity" value="<%= o.getQuantite() %>"><%= o.getQuantite() %></td>
                    <td><input type="hidden" name="price" value="<%= dcf.format(o.getPrix()) %>"><%= dcf.format(o.getPrix()) %></td>
                    <td>
                    <a class="btn btn-sm btn-danger" href="CancelOrderServlet?id=<%= o.getOrderId() %>">Annuler</a>
                    <button type="submit" class="btn btn-sm btn-primary">Imprimer</button>
                    
                    </td>
                    </form>
                </tr>
            <% } %>
			
			</tbody>
		</table>
		<!-- Pagination -->
		<ul class="pagination">
		    <% if (currentPage > 1) { %>
		        <li class="page-item"><a class="page-link" href="?page=<%= currentPage - 1 %>&search=<%= searchQuery %>">Precedent</a></li>
		    <% } %>
		    <li class="page-item disabled"><a class="page-link" href="#">Page <%= currentPage %> of <%= totalPages %></a></li>
		    <% if (currentPage < totalPages) { %>
		        <li class="page-item"><a class="page-link" href="?page=<%= currentPage + 1 %>&search=<%= searchQuery %>">Suivant</a></li>
		    <% } %>
		</ul>

	</div>
	
		<div class="copyright py-4 text-center text-white">
		<div class="container">
			<small>Copyright &copy; My Website 2024</small>
		</div>
	</div>
</body>
</html>