<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
	
	<title>Urutau - Home page</title>

    <!-- Bootstrap Core CSS -->
    <link href="<c:url value='/css/bootstrap.min.css'/>" rel="stylesheet">
    
    <!-- Stylesheet -->
    <link href="<c:url value='/css/stylesheet.css'/>" rel="stylesheet">
    <link href="<c:url value='/css/index.css'/>" rel="stylesheet">
    
    <!-- jQuery -->
    <script src="js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>
</head>

<body>
<section class="initial">
	<div class="container">
		<div class="row">
			<div class="col-xs-12 col-md-9">
	            	<h1><fmt:message key="urutau.welcome"/></h1>
	            	<p>
	            		<small><fmt:message key="urutau.description"/></small>
	            	</p>
            </div>
			<div class="col-xs-6 col-md-3">
				<c:forEach var="error" items="${errors}">
	            	<c:if test="${error.category eq 'login'}">
		            	<!-- Show only login errors -->
		            	<div class="alert alert-danger" role="alert">
		            			${error.message}
		            	</div>
	            	</c:if>
				</c:forEach>
			                 
	           <form action="${linkTo[UserController].authenticate}" class="form-signin" method="POST">
					<input name="login" class="form-control" placeholder="<fmt:message key='user.login'/>" required autofocus>
					<input name="password" type="password" class="form-control" placeholder="<fmt:message key='user.password'/>" required>
					<button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key='sign_up'/></button>
				</form>
     		</div>
        </div>
    </div>
</section>
<section id="register" class="register home-section">
	 <div class="container">
		<div class="row">
			<div class="col-xs-7 col-md-4 col-md-offset-1">
			 	
			 	<c:forEach var="error" items="${errors}">
	            	<c:if test="${error.category eq 'register'}">
		            	<!-- Show only login errors -->
		            	<div class="alert alert-danger" role="alert">
		            			${error.message}
		            	</div>
	            	</c:if>
				</c:forEach>
	            
				<h2><fmt:message key='sign_up'/></h2>
				<form action="register" class="form-signin" method="POST">		
					<input name="user.email" type="email" class="form-control" 
						placeholder="<fmt:message key='user.email'/>" 
						value="${user.email}" required>
						
					<input name="user.name" class="form-control" 
						placeholder="<fmt:message key='user.name'/>" 
						value="${user.name}" required>
						
					<input name="user.lastName" class="form-control" 
						placeholder="<fmt:message key='user.lastname'/>" 
						value="${user.lastName}" required>
						
					<input name="user.login" class="form-control" 
						placeholder="<fmt:message key='user.login'/>"
						value="${user.login}" required>
						
					<input name="user.password" type="password" class="form-control" 
						placeholder="<fmt:message key='user.password'/>" 
						value="${user.password}" required>
						
					<input name="user.passwordVerify" type="password" class="form-control" 
						placeholder="<fmt:message key='user.password_verify'/>" required>
					
					<input class="btn btn-lg btn-success btn-block" type="submit" value="<fmt:message key='sign_in'/>">
					
					<p class="info">
						The registration rules depend  of the enterprise policy. <br/>
						More information: <a href="#contact" class="page-scroll">Contact administrator</a>.
					</p>
					
				</form>
			</div>
			<div class="col-xs-6 col-md-4 col-md-offset-2">
				<div class="thumbnail">
					<h1 class="motive">
						<i class="glyphicon glyphicon-cog"></i> Highly configurable
					</h1>
					<div class="caption">
						Urutau provides how many settings you need
					</div>
				</div>
				<div class="thumbnail">
					<h1 class="motive">
						<i class="glyphicon glyphicon-edit"></i> Easy to contribute 
					</h1>
					<div class="caption">
						We build it, to facility your contribute
					</div>
				</div>
				<div class="thumbnail">
					<h1 class="motive">
						<i class=" glyphicon glyphicon-book"></i> Helpful to manage requirements 
					</h1>
					<div class="caption">
						Now you will have control!
					</div>
				</div>
			</div>
		</div>
	</div>
</section>

<%@ include file="/WEB-INF/layouts/footer.jsp"%>
    
</body>

</html>
