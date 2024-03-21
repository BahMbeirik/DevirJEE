<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Produit" %>
<%@ page import="model.Panier" %>
<%@ page import="dao.ProduitDAO" %>
<%@ page import="connection.DbCon" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<Panier> panier = (List<Panier>) session.getAttribute("panier-list");
    List<Produit> produitList = new ArrayList<>();
    double total = 0;
    double sum = 0;
    DecimalFormat dcf = new DecimalFormat("#.##");
    ProduitDAO produitDAO = new ProduitDAO(DbCon.getConnection());

    if (panier != null) {
        for (Panier item : panier) {
            Produit product = produitDAO.getProductById(item.getId());
            if (product != null) {
            	produitList.add(product);
                total += product.getPrix() * item.getQuantite(); // Adjust for quantity
                
            }
            
        }
        sum += total;
        
    }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Liste des Produits</title>
<link href="css/index-styles.css" rel="stylesheet" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css"/></head>
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
						class="nav-link py-3 px-0 px-lg-3 rounded"
						href="ClientProduitServlet">Produits</a></li>
					<li class="nav-item mx-0 mx-lg-1"><a
						class="nav-link py-3 px-0 px-lg-3 rounded" href="panier.jsp">Panier</a></li>
					<li class="nav-item mx-0 mx-lg-1"><a
						class="nav-link py-3 px-0 px-lg-3 rounded" href="ordre">Les
							ordres</a></li>
					<li class="nav-item mx-0 mx-lg-1"><a
						class="nav-link py-3 px-0 px-lg-3 rounded" href="login.jsp"><button
								class="btn btn-danger">Logout</button></a></li>

				</ul>
			</div>
		</div>
	</nav>
</body>

<div class="container" style="margin-top: 15%;">
	<div class="d-flex py-3">
		<h3>Prix Totale : <%= sum %></h3>
		<a class="mx-3 btn btn-primary" href="#">Acheter maintenant</a>
	</div>

	<table class="table table-loght">
		<thead>
			<tr>
				<th sco="col">Nom</th>
				<th sco="col">Prix</th>
				<th sco="col">Acheter</th>
				<th sco="col">Annuler</th>
			</tr>
		</thead>
		<tbody>
		<% for (Produit product : produitList) { %>
			<tr>
				<td><%= product.getName() %></td>
				<td><%= dcf.format(product.getPrix()) %></td>
				<td width="35%">
					<form action="" method="post" class="form-inline">
						<input type="hidden" name="id" value="<%= product.getId() %>" class="form-input">
						<div class="form-group d-flex justify-content-between">
							<a class="btn bnt-sm btn-incre fs-6 btn-primary" href="QteIncDec"><i class="fas fa-plus-square"></i></a>
							<input type="number" name="quantity" class="form-control mx-3" value="1">
							<a class="btn btn-sm btn-decre fs-6 btn-danger" href="QteIncDec"><i class="fas fa-minus-square"></i></a>

						</div>

					</form>
				</td>
				<td><a href="" class="btn btn-danger">Suprimer</a></td>
			</tr>
			<% } %>
		</tbody>
	</table>
</div>
</html>