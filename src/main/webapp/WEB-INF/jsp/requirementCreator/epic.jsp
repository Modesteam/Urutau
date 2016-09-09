<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="/WEB-INF/layouts/errors.jsp"%>

<h4><i class="glyphicon glyphicon-plus"></i> Epic</h4>
<div class="requirement-box">
	<form action="<c:url value='/requirement/createEpic'/>" method="POST" class="requirement-form">
		<input name="epic.projectID" type="hidden" value="${projectID}">
		<input name="epic.title" class="form-control" placeholder="Title" type="text">
		<input name="epic.description" class="form-control" placeholder="Description" type="text">
		<input name="epic.content" class="form-control" placeholder="Content" type="text">
		<input type="submit" value="Add" class="btn btn-success pull-right submit-create">
	</form>
</div>