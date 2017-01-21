<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<!-- Bootstrap Core CSS -->
	<link href="<c:url value='/css/bootstrap.min.css'/>" rel="stylesheet">
		
	<!-- Stylesheet -->
	<link href="<c:url value='/css/stylesheet.css'/>" rel="stylesheet">
	
	<!-- jQuery -->
	<script src="js/jquery.js"></script>
	
	<!-- Bootstrap Core JavaScript -->
	<script src="js/bootstrap.min.js"></script>
</head>
<body>
<section class="layout-gradient">
	<div class="container">
		<div class="row">
			<div class="col-md-6 layout-gradient-col text-center">
				<img src="<c:url value='/img/urutau.png'/>">
				<h2 class="text-center"><fmt:message key="urutau.admin.welcome.first"/></h2>
				<br/>
			</div>
			<div class="col-md-6 layout-gradient-col">
				<form action="createFirstAdministrator" method="POST" class="form-group text-center" >
					<input name="administrator.email" type="email" class="form-control"
						placeholder="Email: example@email.com" required>
					<input name="administrator.login" type="text" class="form-control" 
						placeholder="Login: (a..z + 1..9)" required/>
					<input name="administrator.password.userPasswordPassed" type="password" class="form-control"
						placeholder="Password: minimum 6 length" required/>
					<input name="administrator.name" type="text" class="form-control" 
						placeholder="Name: Jhon"/>
					<input name="administrator.lastName" type="text" class="form-control" 
						placeholder="Lastname: Pereira"/>
					<button class="btn btn-primary btn-block" type="submit">Próxima página (1/2)</button>
				</form>
			</div>
		</div>
	</div>
</section>
</body>
</html>