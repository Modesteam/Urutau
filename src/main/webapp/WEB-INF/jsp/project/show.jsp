<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!-- Self page scripts -->
<script type="text/javascript">
	/*
	 * The follow script file needs page variable to work.
	 * The above variable project.id is a EL
	 */
	currentProjectID = ${project.id};

	var page = {
		projectID : currentProjectID,
		number : 0
	};

	/*
	 * Render paginate.jsp into div class requirements
	 */
	$(document).ready(function() {
		paginate();
	});
</script>

<script src="<c:url value='/js/requirement.js'/>"></script>

<div class="row">
	<div class="navbar-default sidebar" role="navigation">
		<div class="col-md-3">
			<div class="sidebar-nav navbar-collapse">

				<ul class="nav requirement-create-type" id="side-menu">
					<li><a href="javascript:;" data-toggle="collapse"
							data-target="#types" class="default-option">
							<fmt:message key="create_requirement" />
						</a>
						<ul id="types" class="collapse list-unstyled suboption">
							<c:if test="${project.isGeneric()}">
								<li><a href="<c:url value="/requirement/generic/${project.id}"/>" id="generic">
									Generic Requirement </a></li>
							</c:if>

							<c:if test="${project.isGeneric() || project.isScrum()}">
								<li><a href="<c:url value="/requirement/storie/${project.id}"/>"> 
									User Story </a></li>
							</c:if>

							<c:if test="${project.isGeneric() || project.isScrum()}">
								<li><a href="<c:url value="/requirement/feature/${project.id}"/>">
									Feature </a></li>
							</c:if>

							<c:if test="${project.isGeneric() || project.isScrum()}">
								<li><a href="<c:url value="/requirement/epic/${project.id}"/>">
									Epic </a></li>
							</c:if>

							<c:if test="${project.isGeneric() || project.isUP()}">
								<li><a href="<c:url value="/requirement/useCase/${project.id}"/>">
									Use Case </a></li>
							</c:if>
						</ul>
					</li>

					<li id="link-show-kanban"><a
						href="<c:url value='kanban/${project.id}'/>"
						class="default-option">Kanban</a></li>
					<li><a href="<c:url value="${project.title}/edit"/>"
						class="default-option"> <fmt:message key="settings" />
					</a></li>
					<li><a href="#" class="default-option"> <fmt:message
								key="activity" />
					</a></li>
				</ul>

				<div class="modal fade" id="create-r-modal" tabindex="-1"
					role="dialog">
					<div class="modal-dialog" id="r-form"></div>
				</div>

			</div>
		</div>
	</div>

	<div class="col-md-9">
		<div class="create create-requirement panel panel-default">
			<div class="panel-heading">
				<h2 class="panel-title">
					<i class="glyphicon glyphicon-plus"></i>
					<fmt:message key="create_requirement" />
				</h2>
			</div>

			<br />
			<div class="panel-footer">
				<button id="cancel-create-req" class="btn btn-warning">
					<fmt:message key="cancel" />
				</button>
			</div>
		</div>

		<%@ include file="/WEB-INF/layouts/success.jsp" %>

		<div class="panel-target">
			<div class="panel panel-default">
				<div class="panel-body">
					<c:choose>
						<c:when test="${!project.isEmpty()}">
							<div class="panel-body requirements"></div>
						</c:when>
						<c:otherwise>
							<h2 class="text-center">
								<span class="glyphicon glyphicon-info-sign"></span>
								<fmt:message key="do_not_have_requirements" />
							</h2>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
	</div>
</div>

</body>
</html>