<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:if test="${errors ne null}">
	<h3 class="error">
		<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true">
		</span>&nbsp;<fmt:message key="errors_on_submit"/>
	</h3>
	<ul class="alert alert-danger" role="alert">
		<c:forEach var="error" items="${errors}">
	    	<li class="error-message">${error.message}</li>
		</c:forEach>
	</ul>
</c:if>