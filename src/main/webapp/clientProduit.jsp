
<%@page import="model.Produit"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des Produits</title>
    <link href="css/index-styles.css" rel="stylesheet" />
</head>
<body id="page-top">

<jsp:include page="NavbarClient.jsp"></jsp:include>

<div class="container row gap-5 d-flex align-items-center justify-content-center" style="margin-top:20%;">

    <!-- Search Bar -->
    <div class="d-flex align-items-center justify-content-center">
	    <form action="ClientProduitServlet" method="GET" class="input-group w-25">
	        <input type="search" class="form-control" placeholder="Search" name="search" value="${searchQuery}">
	        <input type="submit" class="btn btn-outline-primary" data-mdb-ripple-init value="Chercher">
	    </form>
     </div>
    

    <c:forEach var="produit" items="${produits}">
        <div class="col-lg-3">
            <div class="card" style="width: 18rem;">
                <img class="card-img-top" src="..." alt="Card image cap">
                <div class="card-body">
                    <h5 class="card-title"><c:out value="${produit.name}" /></h5>
                    <p class="card-text"><c:out value="${produit.prix.toString()}" /></p>
                    <a href="AddToPanierServlet?id=<c:out value="${produit.id}" />" class="btn btn-primary">Ajouter au panier</a>
                    <a href="OrderNowServlet?quantite=1&id=<c:out value="${produit.id}"/>" class="btn btn-danger">Acheter</a>
                </div>
            </div>
     </div>
     </c:forEach>

    <!-- Pagination -->
    <div class="d-flex align-items-center justify-content-center">
    <ul class="pagination">
        <c:if test="${currentPage > 1}">
            <li class="page-item"><a class="page-link" href="?page=${currentPage - 1}&search=${searchQuery}">Previous</a></li>
        </c:if>
        <li class="page-item disabled"><a class="page-link" href="#">Page ${currentPage} of ${totalPages}</a></li>
        <c:if test="${currentPage < totalPages}">
            <li class="page-item"><a class="page-link" href="?page=${currentPage + 1}&search=${searchQuery}">Next</a></li>
        </c:if>
    </ul>
    </div>
    <br>
    <br>
    
    
</div>
<div class="">
		<jsp:include page="footer.jsp"></jsp:include>
	</div>
<!-- Bootstrap core JS-->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<!-- Core theme JS-->
<script src="js/scripts.js"></script>

<script src="https://cdn.startbootstrap.com/sb-forms-latest.js"></script>
</body>
</html>
