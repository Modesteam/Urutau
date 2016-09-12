<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="row">
	<div class="navbar-default sidebar" role="navigation">
		<div class="col-md-4">
        	<div class="sidebar-nav">
	        	<ul class="nav">                         
		            	<li id="projects">
		                	<div class="panel panel-default">
		                		<div class="panel-heading">
			                		<h4><fmt:message key="projects"/></h4>
			                	</div>
				                	<ul class="list-group text-left">
				                 		<c:forEach items="${projects}" var="project">
						                    <li class="list-unstyled list-projects list-group-item">
						                    	<a href="${project.id}-${project.title}">
						                    		<i class="glyphicon glyphicon-book"></i> ${project.title}
						                    	</a>
						                    </li>
					                    </c:forEach>                                                       
				                	</ul>
		                	</div>
			             </li>
			             <li>
			             	<button class="btn btn-success btn-group-justified" data-toggle="modal" data-target="#create-project-modal">
				            	<h4><fmt:message key="create_project"/></h4>
	   				        </button>
   				        </li>            
			        </ul>
			    </div>
			</div>
			
			<div class="col-md-8">
				<%@ include file="/WEB-INF/layouts/errors.jsp"%>
				<c:if test="${index_panel ne null}">
					<div class="alert alert-success" role="alert">
						${index_panel}
						<button type="button" class="close" data-dismiss="alert" aria-label="Close">
				          <span aria-hidden="true">&times;</span>
				        </button>
					</div>
				</c:if>
			</div>
		</div>

		<div class="modal fade"  tabindex="-1" id="create-project-modal" role="dialog">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h4>
							<i class="glyphicon glyphicon-plus"></i> <fmt:message key="create_project"/>
						</h4>
					</div>
					<div class="modal-body">
						<form action="project/create" method="POST">
							<input name="project.title" type="text" class="form-control" 
								placeholder="<fmt:message key="project.title"/>">
							<br/>
							<select class="form-control" name="project.metodology">
								<c:forEach items="${metodologies}" var="metodology">
									<c:if test="${metodology == 'Generic'}">
										<option value="${metodology}" selected="selected">${metodology}</option>
									</c:if>
									<c:if test="${metodology != 'Generic'}">
										<option value="${metodology}">${metodology}</option>
									</c:if>
								</c:forEach>
							</select>
							<br/>
							<input name="project.description" type="text" class="form-control" 
								placeholder="<fmt:message key="project.description"/>">
							<br/> 
							<input type="submit" 
								class="btn btn-success btn-group-justified submit-modal" 
								value="<fmt:message key="add"/>"/>					
						</form>
					</div>
				</div>
			</div>
		</div>
			
</div>