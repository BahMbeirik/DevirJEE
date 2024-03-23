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
	
	<c:forEach var="produit" items="${produits}">
	<div class="col-lg-3">
        <div class="card" style="width: 18rem;">
            <img class="card-img-top" src="..." alt="Card image cap">
            <div class="card-body">
                <h5 class="card-title"><c:out value="${produit.name}" /></h5>
                <p class="card-text"><c:out value="${produit.prix.toString()}" /></p>
                <a href="AddToPanierServlet?id=<c:out value="${produit.id}" />" class="btn btn-primary">Ajouter au panier</a>
                <a href="#" class="btn btn-danger">Acheter</a>
            </div>
            
        </div>
       </div>
    </c:forEach>
	</div>

    <br>
    <br>
    <div class="">
		<jsp:include page="footer.jsp"></jsp:include>
	</div>
    

	<!-- Bootstrap core JS-->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
	<!-- Core theme JS-->
	<script src="js/scripts.js"></script>
	<!-- * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *-->
	<!-- * *                               SB Forms JS                               * *-->
	<!-- * * Activate your form at https://startbootstrap.com/solution/contact-forms * *-->
	<!-- * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *-->
	<script src="https://cdn.startbootstrap.com/sb-forms-latest.js"></script>
</body>
</html>