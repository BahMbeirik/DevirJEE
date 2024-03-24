<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des Users</title>
    <link href="css/index-styles.css" rel="stylesheet" />
</head>
<body id="page-top">
	<!-- Navigation-->
	<jsp:include page="Navbar.jsp"></jsp:include>
	<div class="container masthead text-center">
    <h2 class="mb-5">Liste des utilisateurs</h2>
    
    <table class="table">
        <tr>
            <th>ID</th>
            <th>Nom</th>
            <th>Email</th>
            <!--<th>Password</th>-->
            <th>Mobile</th>
            <th>Role</th>
            <th>Action</th>
        </tr>
        <c:forEach var="user" items="${users}">
            <tr>
                <td><c:out value="${user.id}" /></td>
                <td><c:out value="${user.username}" /></td>
                <td><c:out value="${user.uemail}" /></td>
                <!--<td><c:out value="${user.password}" /></td>-->
                <td><c:out value="${user.umobile}" /></td>
                <td>
                	<c:choose>
                		
		                <c:when test="${user.role == 1}" >
		                    <span class="text-success fw-bold">Admin</span>
		                </c:when>
		                <c:otherwise>
		                    User
		                </c:otherwise>
		            </c:choose>
                </td>
                <td class="d-flex justify-content-center">
	                <form action="UpdateUser" method="post">
	                	<input type="hidden" name="userId" value="${user.id}" /> <!-- Add a hidden input field to store the user ID -->
				        <input type="submit" value="Change Role" class="btn btn-primary btn-sm">
				    </form>
	               
	                &nbsp;&nbsp;&nbsp;&nbsp;
	                <a href="DeleteUser?id=<c:out value='${user.id}' />"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="red" class="bi bi-trash3" viewBox="0 0 16 16">
	  														<path d="M6.5 1h3a.5.5 0 0 1 .5.5v1H6v-1a.5.5 0 0 1 .5-.5M11 2.5v-1A1.5 1.5 0 0 0 9.5 0h-3A1.5 1.5 0 0 0 5 1.5v1H1.5a.5.5 0 0 0 0 1h.538l.853 10.66A2 2 0 0 0 4.885 16h6.23a2 2 0 0 0 1.994-1.84l.853-10.66h.538a.5.5 0 0 0 0-1zm1.958 1-.846 10.58a1 1 0 0 1-.997.92h-6.23a1 1 0 0 1-.997-.92L3.042 3.5zm-7.487 1a.5.5 0 0 1 .528.47l.5 8.5a.5.5 0 0 1-.998.06L5 5.03a.5.5 0 0 1 .47-.53Zm5.058 0a.5.5 0 0 1 .47.53l-.5 8.5a.5.5 0 1 1-.998-.06l.5-8.5a.5.5 0 0 1 .528-.47M8 4.5a.5.5 0 0 1 .5.5v8.5a.5.5 0 0 1-1 0V5a.5.5 0 0 1 .5-.5"/>
															</svg></a>
				</td>
            </tr>
        </c:forEach>
        
    </table>
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
