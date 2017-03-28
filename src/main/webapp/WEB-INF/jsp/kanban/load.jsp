<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!--  Special Kanban stylesheet-->
<link href="<c:url value="/css/kanban.css"/>" rel="stylesheet">

<!--  CSS plugin to view horizon -->
<link href="<c:url value="/css/bootstrap-horizon.css"/>" rel="stylesheet">

<!-- Specific JS functions for this kanban -->
<script type="text/javascript" src="<c:url value="/js/kanban.js"/>"></script>

<div class="row">
	<h1>${project.title} Kanban</h1>

	<div class="col-md-9">
		<%@ include file="/WEB-INF/layouts/messages.jsp"%>
	</div>

	<div class="col-md-3">
		<div class="btn-group">
			<button type="button" class="btn btn-primary dropdown-toggle" 
						 data-toggle="dropdown">Add Label <span class="glyphicon glyphicon-plus"></span></button>
			<div class="dropdown-menu" role="menu">
				<div class="form-group form-widget">
					<form action="<c:url value="/kanban/createLayer"/>" method="POST">
						<input type="hidden" name="projectID" value="${project.id}"/>
						<input type="text" name="layer.name" placeholder="Layer name" class="form-control"/>
						<input type="submit" value="Save"  class="form-control btn btn-success"/>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>

<section class="kanban">
	<div class="row row-horizon">
		<c:forEach items="${project.layers}" var="layer">
		<div class="layer col-md-3" ondrop="drop(event, ${layer.layerID})" 
			ondragover="allowDrop(event)" id="div1">
			<h2>${layer.name}</h2>
			<c:forEach items="${project.requirements}" var="requirement">
			<!-- Modal to see requirement -->
			<%@ include file="/WEB-INF/jsp/requirement/show.jspf" %>

			<c:if test="${requirement.layer.layerID == layer.layerID}">
			<div class="requirement ${requirement} panel" id="drag${requirement.id}" draggable="true" ondragstart="drag(event)">
				<div class="requirement-type">${requirement.identifierCode}</div>
				<a href="<c:url value='/show/${requirement.id}/${requirement.encodedTitle}'/>" 
	   					title="Show" data-toggle="modal" data-target="#modal-show-${requirement.id}">
					${requirement.title}
				</a>
			</div>
					</c:if>
			</c:forEach>
		</div>
			</c:forEach>
	</div>
</section>
