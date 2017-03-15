<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">

		<!-- Bootstrap min css -->
		<link href="<c:url value='/css/bootstrap.min.css'/>" rel="stylesheet">

		<link href="<c:url value='/css/stylesheet.css'/>" rel="stylesheet">

		<!-- jQuery -->
		<script src="<c:url value='/js/jquery-2.2.2.min.js'/>"></script>

		<!-- Bootstrap Core -->
		<script src="<c:url value='/js/bootstrap.min.js'/>"></script>

		<!-- Interface -->
		<script src="<c:url value='/js/interface.js'/>"></script>

		<title>Urutau</title>
	</head>
	<body>
		<header>
			 <nav class="navbar navbar-default navbar-fixed-top" role="navigation">
			 	<div class="container">
					<!-- Brand and toggle get grouped for better mobile display -->
					<div class="navbar-header">
						<a class="navbar-brand" href="<c:url value='/'/>">Urutau</a>
					</div>
					<!-- Top Menu Items -->
					<ul class="nav navbar-right top-nav">
						<li class="dropdown">
							<c:if test="${userSession.isLogged() == true}">
								<a href="#" id="top-menu-btn" class="dropdown-toggle" data-toggle="dropdown">
									${userSession.userLogged.login}
								</a>
								<ul class="dropdown-menu" id="top-menu-dropdown">
									<li><a href="#"><fmt:message key="see_profile"/></a></li>
									<li><a href="user/settings"><fmt:message key="settings"/></a></li>
									<li class="divider"></li>
									<li><a href="<c:url value='/logout'/>"> <fmt:message key="logout"/></a></li>
								</ul>
							</c:if>
							<c:if test="${empty userSession or not userSession.logged}">
							 	<ul class="nav pull-right" id="top-menu-dropdown">
					          		<li class="dropdown" id="menuLogin">
					            		<a  href="#" id="top-menu-btn" class="dropdown-toggle"data-toggle="dropdown">
					            			<fmt:message key="sign_up"/>
					            		</a>
					            		<ul class="dropdown-menu" id="login-box">
					            			<li>
						              			<form action="<c:url value="/user/authenticate"/>" class="form-group login-form" method="POST"> 
									                <input name="login" type="text" class="form-control" placeholder="Login or email"/>
									                <input name="password" type="password" class="form-control" placeholder="Password">
									                <button type="submit" class="btn btn-primary btn-group-justified">
									                	<fmt:message key="sign_up"/>
									                </button>
												</form>
											</li>
							          		<li class="divider"></li>
							          		<li>
							          			<a href="<c:url value="/"/>">
							          				<fmt:message key="sign_in"/>
							          			</a>
							          		</li>
							            </ul>
						          	</li>
						        </ul>
							</c:if>
						</li>
					</ul>
				</div>
			</nav>
		</header>
		<!-- Get all content above header -->
		<div id="wrap" class="container">