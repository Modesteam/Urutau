<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="/WEB-INF/layouts/errors.jsp"%>

<h4><i class="glyphicon glyphicon-plus"></i> Generic</h4>
<div class="requirement-box">
	<form action="<c:url value='/requirement/createGeneric'/>" method="POST" class="requirement-form">
		<input name="generic.projectID" type="hidden" value="${projectID}">
		
		<div class="alert alert-danger form-error" id="title-error" role="alert"></div>
		<input name="generic.title" placeholder="Title" type="text" class="form-control">
		
		<input name="generic.description" placeholder="Description" type="text" class="form-control" > 
		<button type="submit" class="btn btn-success pull-right submit-create">Add</button>
	</form>
</div>