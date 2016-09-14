<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<h4><i class="glyphicon glyphicon-pencil"></i><fmt:message key="edit"/> User Story</h4>
<%@ include file="/WEB-INF/layouts/errors.jsp"%>
<%@ include file="/WEB-INF/layouts/success.jsp"%>
<div>
	<form action="<c:url value="/requirementEditor/useCase"/>" method="POST">
		<input name="useCase.id" type="hidden" value="${useCase.id}">
		<input name="useCase.title" class="form-control" placeholder="Title" type="text" value="${useCase.title}">
		<input name="useCase.description" class="form-control" placeholder="Description" type="text" value="${useCase.description}">
		<input name="useCase.fakeActors" class="form-control" placeholder="Separated by ','" type="text" value="${useCase.description}">
		<button type="submit" class="btn btn-success pull-right" name="_method" value="PUT">
			<fmt:message key="save"/>
		</button>
	</form>
</div>