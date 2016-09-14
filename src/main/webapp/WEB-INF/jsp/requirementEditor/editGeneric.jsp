<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<h4><i class="glyphicon glyphicon-pencil"></i><fmt:message key="edit"/> Generic</h4>
<%@ include file="/WEB-INF/layouts/errors.jsp"%>
<%@ include file="/WEB-INF/layouts/success.jsp"%>
<div>
	<form action="<c:url value="/requirementEditor/generic"/>" method="POST">
		<input name="generic.id" type="hidden" value="${generic.id}">
		<input name="generic.title" class="form-control" placeholder="Title" type="text" value="${generic.title}">
		<input name="generic.description" class="form-control" placeholder="Description" type="text" value="${generic.description}">
		<button type="submit" class="btn btn-success pull-right" name="_method" value="PUT">
			<fmt:message key="save"/>
		</button>
	</form>
</div>