<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<body>
	<div class="artifacts">
		<div class="panel-heading">
   			<h1 class="panel-title">
   				<i class="glyphicon glyphicon-list"></i> Last requirements 
   			</h1>
		</div>
				
		<table class="table table-striped">
			<thead>
				<tr>
					<th>Title</th>
					<th>Author</th>
					<th>Creation Date</th>
					<th>Show</th>
					<th>Edit</th>
					<th>Delete</th>
				</tr>
			</thead>
			<tbody>
		  		<c:forEach items="${requirements}" var="requirement" >
			  		<tr>
			  			<td>${requirement.title}</td>
			  			<td>
			  				${requirement.author.login}
			  			</td>
			  			<td>
			  				 <fmt:formatDate value="${requirement.dateOfCreation.time}" pattern="dd/MM/yyyy"/>
			  			</td>
			  			<td>
				  			<a href="show/${requirement.id}/${requirement.encodedTitle}" title="Show"  
				  				data-toggle="modal" data-target="#modal-show">
					        	<span class="glyphicon glyphicon-eye-open"></span>
					        </a>
			  			</td>
				        	<!-- contains modal to show requirement -->
		  					<%@ include file="show.jspf" %>
			  			<td>
			  				<a href="edit/${requirement.id}" title="Edit"
			  				data-toggle="modal" data-target="#modal-edit-${requirement.id}">
			  					<span class="glyphicon glyphicon-pencil"></span>
			  				</a>
			  			</td>
			  				<%@ include file="edit.jspf" %>
			  			<td>
			  				<a href="#" id="${requirement.id}" class="delete-req" title="Delete">
			  					<span class="glyphicon glyphicon-remove"></span>
			  				</a>
			  			</td>		  				
			  		</tr>
		  		</c:forEach>
		  	</tbody>
		 </table>
		 <div>
 		 	<a onclick="previous()">
		 		<i class="glyphicon glyphicon-backward"></i>
		 	</a>
		 	<a onclick="next()">
		 		<i class="glyphicon glyphicon-forward"></i>
		 	</a>
		 </div>
	</div>
	<script src="<c:url value='/js/requirement.js'/>"/></script>
</body>
</html>