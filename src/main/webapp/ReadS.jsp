<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des stocks</title>
    <link href="css/index-styles.css" rel="stylesheet" />
</head>
<body id="page-top">
	<!-- Navigation-->
	<nav
		class="navbar navbar-expand-lg bg-secondary text-uppercase fixed-top"
		id="mainNav">
		<div class="container">
			<a class="navbar-brand" href="/ProjetJEE/">Gestion de vente en ligne</a>
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
						class="nav-link py-3 px-0 px-lg-3 rounded" href="ReadServlet">Les Produit</a></li>
					<li class="nav-item mx-0 mx-lg-1"><a
						class="nav-link py-3 px-0 px-lg-3 rounded" href="ReadStock">Les Stocks</a></li>
					<li class="nav-item mx-0 mx-lg-1"><a
						class="nav-link py-3 px-0 px-lg-3 rounded" href="ReadUser">Les utilisateurs</a></li>
					<li class="nav-item mx-0 mx-lg-1"><a
						class="nav-link py-3 px-0 px-lg-3 rounded" href="login.jsp"><button class="btn btn-danger">Logout</button></a></li>
					
				</ul>
			</div>
		</div>
	</nav>
	<div class="container masthead text-center">
	<div class="d-flex align-items-center justify-content-center mb-4">
	<form action="ReadStock" method="get" class="input-group w-25">
            <input type="text" name="search" class="form-control" placeholder="Rechercher un produit" value="${searchQuery}">
            <input type="submit" class="btn btn-outline-primary" data-mdb-ripple-init value="Chercher">
     </form>
     </div>

     
    <h2>Liste des stocks</h2>
    <a href="CreateS.jsp"><button class="btn btn-outline-success btn-sm bm">Ajouter un Stock</button></a>
    <br>
    <table class="table">
        <tr>
            <th>ID</th>
            <th>ID Produit</th>
            <th>Quantite</th>
            <th>Action</th>
        </tr>
        <c:forEach var="stock" items="${stocks}">
            <tr>
                <td><c:out value="${stock.id}" /></td>
                <td><c:out value="${stock.idP}" /></td>
                <td><c:out value="${stock.quantite}" /></td>
                <td><a href="UpdateStock?id=<c:out value="${stock.id}" />"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil-square" viewBox="0 0 16 16">
  												<path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>
  												<path fill-rule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5z"/>
												</svg></a>
								&nbsp;&nbsp;&nbsp;&nbsp; <a
								href="DeleteStock?id=<c:out value='${stock.id}' />"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="red" class="bi bi-trash3" viewBox="0 0 16 16">
  														<path d="M6.5 1h3a.5.5 0 0 1 .5.5v1H6v-1a.5.5 0 0 1 .5-.5M11 2.5v-1A1.5 1.5 0 0 0 9.5 0h-3A1.5 1.5 0 0 0 5 1.5v1H1.5a.5.5 0 0 0 0 1h.538l.853 10.66A2 2 0 0 0 4.885 16h6.23a2 2 0 0 0 1.994-1.84l.853-10.66h.538a.5.5 0 0 0 0-1zm1.958 1-.846 10.58a1 1 0 0 1-.997.92h-6.23a1 1 0 0 1-.997-.92L3.042 3.5zm-7.487 1a.5.5 0 0 1 .528.47l.5 8.5a.5.5 0 0 1-.998.06L5 5.03a.5.5 0 0 1 .47-.53Zm5.058 0a.5.5 0 0 1 .47.53l-.5 8.5a.5.5 0 1 1-.998-.06l.5-8.5a.5.5 0 0 1 .528-.47M8 4.5a.5.5 0 0 1 .5.5v8.5a.5.5 0 0 1-1 0V5a.5.5 0 0 1 .5-.5"/>
														</svg></a>
				</td>
            </tr>
        </c:forEach>
        
    </table>
    <!-- Pagination -->
        <ul class="pagination justify-content-center">
            <li class="page-item <c:if test="${currentPage == 1}">disabled</c:if>">
                <a class="page-link" href="?page=1">Precedent</a>
            </li>
            <c:forEach var="page" begin="1" end="${totalPages}" step="1">
                <li class="page-item <c:if test="${currentPage eq page}">active</c:if>">
                    <a class="page-link" href="?page=${page}">${page}</a>
                </li>
            </c:forEach>
            <li class="page-item <c:if test="${currentPage == totalPages}">disabled</c:if>">
                <a class="page-link" href="?page=${totalPages}">Suivant</a>
            </li>
        </ul>
    </div>
    
    
    <!-- Copyright Section-->
	<div class="copyright py-4 text-center text-white">
		<div class="container">
			<small>Copyright &copy; My Website 2024</small>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
	<!-- Core theme JS-->
	<script src="js/scripts.js"></script>
	<script src="https://cdn.startbootstrap.com/sb-forms-latest.js"></script>
</body>
</html>
