<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script type="text/javascript" src="<c:url value="/js/kanban.js"/>"></script>
<link href="<c:url value="/css/kanban.css"/>" rel="stylesheet">
	
<div class="row">
	<!--
	
	<form action='<c:url value="/kanban/createLayer"/>' method="POST">
		<input type="hidden" name="projectID" value="${project.id}">
		<input type="text" name="layer.name" placeholder="Layer name">
		<input type="submit" value="Create layer">
	</form>
	-->

	<h1>${project.title} Kanban</h1>

	<section class="kanban">
		<br/>
		<%@ include file="/WEB-INF/layouts/errors.jsp"%>
		<div class="alert alert-success" role="alert">
			<a href="#" class="close" onclick="closeAlert()" aria-label="close">x</a>
			<span class="alert-message"></span>
		</div>

		<c:forEach items="${project.layers}" var="layer">
			<div class="layer" ondrop="drop(event, ${layer.layerID})"
				ondragover="allowDrop(event)" id="div1">
				<h2>${layer.name}</h2>
				<c:forEach items="${project.requirements}" var="requirement">
					<!-- Modal to see requirement -->
					<%@ include file="/WEB-INF/jsp/requirement/show.jspf" %>
					
					<c:if test="${requirement.layer.layerID == layer.layerID}">
						<div class="requirement ${requirement}" id="drag${requirement.id}"
						 	draggable="true" ondragstart="drag(event)">
					 		<a href="<c:url value='/show/${requirement.id}/${requirement.encodedTitle}'/>"
					 			title="Show" data-toggle="modal" 
					 			data-target="#modal-show-${requirement.id}">
									${requirement.title}
							</a>
						</div>
					</c:if>
				</c:forEach>
			</div>
		</c:forEach>		
	</section>
</div>
