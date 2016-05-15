<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="/WEB-INF/layouts/errors.jsp"%>

<h4><i class="glyphicon glyphicon-plus"></i> Storie</h4>
<div class="requirement-box">
	<form action="<c:url value='/requirement/createUserStory'/>" method="POST" class="requirement-form">
		<input name="storie.projectID" type="hidden" value="${projectID}">
		<input type="text" name="storie.title" placeholder="Title" class="form-control">

		<label>Tell the Story:</label>
		<textarea name="storie.history" class="form-control">

		</textarea>

		<input type="text" name="acceptanceCriteria.content" class="form-control" placeholder="Acceptance criteria">
		<button type="submit" class="btn btn-success pull-right submit-create">Add</button>
	</form>
</div>