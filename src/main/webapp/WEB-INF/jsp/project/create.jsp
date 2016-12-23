<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="/WEB-INF/layouts/errors.jsp"%>

<h4><i class="glyphicon glyphicon-plus"></i> <fmt:message key="create_project" /></h4>
<div>
	<form action="<c:url value="/project/create" />" method="POST">
		<input name="project.title" type="text" class="form-control" 
			placeholder="<fmt:message key="project.title"/>" value="${project.title}">
		
		<select class="form-control" name="project.metodology">
			<c:forEach items="${metodologies}" var="metodology">

				<!-- When validation back to here, get value choosed previously -->			
				<c:choose>
					<c:when test="${project.metodology ne null }">
						<option value="${project.metodology}" selected="selected">${project.metodology}</option>
					</c:when>
					
					<c:otherwise>
						<c:if test="${metodology == 'Generic'}">
							<option value="${metodology}" selected="selected">${metodology}</option>
						</c:if>
						
						<c:if test="${metodology != 'Generic'}">
							<option value="${metodology}">${metodology}</option>
						</c:if>
					</c:otherwise>
				</c:choose>

			</c:forEach>
		</select>
		
		<input name="project.description" type="text" class="form-control"
			placeholder="<fmt:message key="project.description"/>" value="${project.description}">
		
		<input type="submit" class="btn btn-success btn-group-justified form-control"
			value="<fmt:message key="add"/>" />
	</form>
</div>