<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<h4><i class="glyphicon glyphicon-pencil"></i><fmt:message key="edit"/> Epic</h4>
<%@ include file="/WEB-INF/layouts/errors.jsp"%>
<%@ include file="/WEB-INF/layouts/success.jsp"%>
<div>
	<form action="<c:url value="/requirementEditor/epic"/>" method="POST">
		<input name="epic.id" type="hidden" value="${epic.id}">
		<input name="epic.title" class="form-control" placeholder="Title" type="text" value="${epic.title}">
		<input name="epic.description" class="form-control" placeholder="Description" type="text" value="${epic.description}">
		<input name="epic.content" class="form-control" placeholder="Content" type="text" value="${epic.content}">
		<button type="submit" class="btn btn-success pull-right" name="_method" value="PUT">
			<fmt:message key="save"/>
		</button>
	</form>
</div>