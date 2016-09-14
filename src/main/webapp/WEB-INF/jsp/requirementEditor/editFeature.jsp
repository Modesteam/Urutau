<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<h4><i class="glyphicon glyphicon-pencil"></i><fmt:message key="edit"/> Feature</h4>
<%@ include file="/WEB-INF/layouts/errors.jsp"%>
<%@ include file="/WEB-INF/layouts/success.jsp"%>
<div>
	<form action="<c:url value="/requirementEditor/feature"/>" method="POST">
		<input name="feature.id" type="hidden" value="${feature.id}">
		<input name="feature.title" class="form-control" placeholder="Title" type="text" value="${feature.title}">
		<input name="feature.description" class="form-control" placeholder="Description" type="text" value="${feature.description}">
		<input name="feature.content" class="form-control" placeholder="Content" type="text" value="${feature.content}">
		<button type="submit" class="btn btn-success pull-right" name="_method" value="PUT">
			<fmt:message key="save"/>
		</button>
	</form>
</div>