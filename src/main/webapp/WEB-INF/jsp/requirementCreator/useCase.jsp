<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="/WEB-INF/layouts/errors.jsp"%>

<h4><i class="glyphicon glyphicon-plus"></i> Use case</h4>
<div class="requirement-box" id="useCase">
	<form action="<c:url value='/requirement/createUseCase'/>" method="POST" class="requirement-form">
		<input name="useCase.projectID" type="hidden" value="${projectID}">
		
		<div class="alert alert-danger form-error" id="title-error" role="alert"></div>
		<input name="useCase.title" class="form-control" placeholder="Title" type="text">
		
		<input name="useCase.description" class="form-control" placeholder="Description" type="text">
		<input name="useCase.fakeActors" class="form-control" placeholder="Separated by ','" type="text">
		<input type="submit" value="Add" class="btn btn-success pull-right submit-create">
	</form>
</div>