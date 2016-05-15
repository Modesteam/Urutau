<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="/WEB-INF/layouts/errors.jsp"%>

<h4><i class="glyphicon glyphicon-plus"></i> Feature</h4>
<div class="requirement-box">
	<form action="<c:url value='/requirement/createFeature'/>" method="POST" class="requirement-form">
		<input name="feature.projectID" type="hidden" value="${projectID}">
		<input name="feature.title" class="form-control" placeholder="Title" type="text" value="${feature.title}">
		<input name="feature.description" class="form-control" placeholder="Description" type="text" value="${feature.description}">
		<input name="feature.content" class="form-control" placeholder="Content" type="text" value="${feature.content}">
		<input type="submit" value="Add" class="btn btn-success pull-right submit-create">
	</form>
</div>