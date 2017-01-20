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
			             <h4>
			             	<a href="<c:url value="project/create"/>" class="btn btn-success btn-group-justified">
				            	<fmt:message key="create_project"/>
				            </a>
	   				     </h4>
   				        </li>            
			        </ul>
			    </div>
			</div>
			
			<div class="col-md-8">
				<%@ include file="/WEB-INF/layouts/errors.jsp"%>
				<%@ include file="/WEB-INF/layouts/success.jsp"%>
			</div>
		</div>			
</div>