<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<body>
	<div>
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
			  				data-toggle="modal" data-target="#myModal-${requirement.id}">
				        	<span class="glyphicon glyphicon-eye-open"></span>
				        </a>
		  					
		  					<div class="modal fade" id="myModal-${requirement.id}"  tabindex="-1" role="dialog">
							    <div class="modal-dialog">
							    
							      <!-- Modal content-->
							      <div class="modal-content">
							        <div class="modal-header">
							          <h4 class="modal-title">
							          	${requirement.title}
							          	<br/>
								        <small>By ${requirement.author.name} in 
										<fmt:formatDate value="${requirement.dateOfCreation.time}" type="date" dateStyle="short" />
										</small>
							          </h4>
							        </div>
							        <div class="modal-body">
							          <p>${requirement.description}</p>
							        </div>
							        <div class="modal-footer">
							          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
							        </div>
							      </div>
							      
							    </div>
							  </div>

			  			</td>
			  			<td>
			  				<a href="edit/${requirement.id}" title="Edit">
			  					<span class="glyphicon glyphicon-pencil"></span>
			  				</a>
			  			</td>
			  			<td>
			  				<a href="excludeRequirement/${requirement.id}" title="Delete">
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
</body>
</html>