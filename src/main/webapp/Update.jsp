<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Modifier un produit</title>
    <link href="css/index-styles.css" rel="stylesheet" />
</head>
<body>
	<!-- Navigation-->
	<jsp:include page="Navbar.jsp"></jsp:include>
	<div class="container masthead ">
    <h2>Modifier un produit</h2>
    <form action="UpdateServlet" method="post" class="row g-3 needs-validation d-block" novalidate>
   		 <input type="hidden" name="id" value="${productToUpdate.id}">
    	<div class="col-md-4">
		    <label for="validationCustom01" class="form-label">Nom :</label>
		    <input type="text" class="form-control" id="validationCustom01"  name="name" value="${productToUpdate.name}" required>
		    <div class="valid-feedback">
		      Looks good!
		    </div>
		  </div>
		  <div class="col-md-4">
		    <label for="validationCustom01" class="form-label">Prix :</label>
		    <input type="text" class="form-control" id="validationCustom01"  name="prix" value="${productToUpdate.prix}" required>
		    <div class="valid-feedback">
		      Looks good!
		    </div>
		  </div>
		  <div class="col-12">
        	<button type="submit" class="btn btn-primary">Modifier</button>
       	</div>
        
    </form>
    </div>
    
    
    <jsp:include page="footer.jsp"></jsp:include>

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
