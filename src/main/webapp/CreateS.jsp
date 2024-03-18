<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ajouter un prooduit</title>
    <link href="css/index-styles.css" rel="stylesheet" />
</head>
<body>
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
	<div class="container masthead ">
    <h2>Ajouter un stock</h2>
    <form action="CreateStock" method="post"  class="row g-3 needs-validation d-block" novalidate>
		   <div class="col-md-4">
		    <label for="validationCustom01" class="form-label">IdProduit :</label>
		    <input type="text" class="form-control" id="validationCustom01"  name="idP" required>
		    <div class="valid-feedback">
		      Looks good!
		    </div>
		  </div>
		  <div class="col-md-4">
		    <label for="validationCustom01" class="form-label">Quantite :</label>
		    <input type="text" class="form-control" id="validationCustom01"  name="quantite" required>
		    <div class="valid-feedback">
		      Looks good!
		    </div>
		  </div>
        
        <div class="col-12">
        	<button type="submit" class="btn btn-primary">Ajouter</button>
        </div>
    </form>
    </div>
    
    
    <!-- Copyright Section-->
	<div class="copyright py-4 text-center text-white">
		<div class="container">
			<small>Copyright &copy; My Website 2024</small>
		</div>
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
