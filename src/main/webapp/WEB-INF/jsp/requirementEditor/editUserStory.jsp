<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<h4><i class="glyphicon glyphicon-pencil"></i><fmt:message key="edit"/> User Story</h4>
<%@ include file="/WEB-INF/layouts/errors.jsp"%>
<%@ include file="/WEB-INF/layouts/success.jsp"%>
<div>
	<form action="<c:url value="/requirementEditor/storie"/>" method="POST">
		<input name="storie.id" type="hidden" value="${storie.id}">
		<input name="storie.title" class="form-control" placeholder="Title" type="text" value="${storie.title}">
		<label>Tell the Story:</label>
		<textarea name="storie.history" class="form-control">${storie.history}</textarea>
		<button type="submit" class="btn btn-success pull-right" name="_method" value="PUT">
			<fmt:message key="save"/>
		</button>
	</form>
</div>