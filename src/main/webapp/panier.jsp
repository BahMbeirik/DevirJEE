<%@page import="model.User"%>
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
                total += product.getPrix() * item.getQuantite();         
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

	<jsp:include page="NavbarClient.jsp"></jsp:include>
	
	<div class="container" style="margin-top: 15%;">

	<div class="d-flex py-3">
		<h3>Prix Totale : <%= sum %> </h3>
		<a class="mx-3 btn btn-primary" href="AcheterToutServlet">Acheter tous</a>
	</div>

	<table class="table table-loght w-75">
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
				<td><%= Double.parseDouble(dcf.format(product.getPrix())) * (panier.stream().filter(item -> item.getId() == product.getId()).findFirst().orElse(new Panier()).getQuantite()) %></td>
				<td>
    <form action="OrderNowServlet" method="post" class="form-inline">
    <div class="row align-items-center w-75">
        <input type="hidden" name="id" value="<%= product.getId() %>" class="form-input">
        
        <div class="col-auto">
            <a class="btn btn-sm btn-decre fs-6 btn-danger" href="QteIncDec?action=dec&id=<%=product.getId() %>">
                <i class="fas fa-minus-square"></i>
            </a>
        </div>
        
        <div class="col">
            <input type="number" name="quantite" class="form-control" value="<%= panier.stream().filter(item -> item.getId() == product.getId()).findFirst().orElse(new Panier()).getQuantite() %>">
        </div>
        
        <div class="col-auto">
            <a class="btn btn-sm btn-incre fs-6 btn-primary" href="QteIncDec?action=inc&id=<%=product.getId() %>">
                <i class="fas fa-plus-square"></i>
            </a>
        </div>
        
        <div class="col-auto">
            <button type="submit" class="btn btn-primary btn-sm">Acheter</button>
        </div>
    </div>
</form>

</td>

				<td><a href="RemoveFromPanierServlet?id=<%=product.getId() %>" class="btn btn-danger">Suprimer</a></td>
			</tr>
			<% } %>
		</tbody>
	</table>
</div>
	
<div class=" bottom-0 end-0 w-100">
	<jsp:include page="footer.jsp"></jsp:include>
</div>
</body>
</html>