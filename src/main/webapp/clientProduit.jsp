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
<nav class="navbar navbar-expand-lg bg-secondary text-uppercase fixed-top" id="mainNav">
    <div class="container">
        <a class="navbar-brand" href="clientIndex.jsp">Boutique</a>
        <button class="navbar-toggler text-uppercase font-weight-bold bg-primary text-white rounded"
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

<!-- Copyright Section-->
<div class="copyright py-4 text-center text-white">
    <div class="container">
        <small>Copyright &copy; My Website 2024</small>
    </div>
</div>

<!-- Bootstrap core JS-->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<!-- Core theme JS-->
<script src="js/scripts.js"></script>
<!-- * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *-->
<!-- * *                               SB Forms JS                               * *-->
<!-- * * Activate your form at https://startbootstrap.com/solution/contact-forms * *-->
<!-- * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *-->
<script src="https://cdn.startbootstrap.com/sb-forms-latest.js"></script>
</body>
</html>
