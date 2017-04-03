<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ include file="/WEB-INF/layouts/messages.jsp"%>

<div class="row">	
	<div class="col-md-6 col-md-offset-3">
		<h1>${project.title} Settings</h1>
	</div>

	<div class="col-md-6 col-md-offset-3">
		<div class="panel panel-default">
		 	<div class="panel-heading">
		 		<h3>Basic information</h3>
		 	</div>
		 	<div class="panel-body">
				<form action="<c:url value="/${project.id}/setting"/>" method="POST" class="form-group">
					<label for="title">Title</label>
					<input type="text" name="project.title" value="${project.title}"
						class="form-control" placeholder="Title" id="title">
		
					<label for="desc">Brief description</label>				
					<input type="text" name="project.description" value="${project.description}"
						class="form-control" placeholder="Simple description" id="desc"/>
		
					<div class="checkbox">
						<label>
							<input type="checkbox" name="project.public" id="privacy" 
								<c:if test="${project.isPublic() == true}">checked="checked"</c:if>/>
								Projeto é publico?
						</label>
					</div>
					
					<a href="<c:url value="/${project.id}-${project.title}"/>" class="btn btn-default">Cancel</a>
					<button type="submit" name="_method" value="PUT" class="btn btn-default pull-right">Update</button>
				</form>
			</div>
		</div>
	</div>
</div>

<div class="row">
	<div class="col-md-6 col-md-offset-3 boxed">
		<div class="panel panel-default">
	 		<div class="panel-heading">
				<h3>New members</h3>
			</div>
			<div class="panel-body">
				<label>Assign new member by email</label>
				<form action="<c:url value="/project/addMember"/>" method="POST" class="input-group">
					<input type="hidden" name="projectId" value="${project.id}">
					<input type="text" name="member" class="form-control" id="member" placeholder="Email">
					<span class="input-group-btn">
						<button type="submit" class="btn btn-default" type="button">Add</button>
					</span>
   				</form>
   				<br/>
			    <p class="text-info">* When you add new members, they could see, edit, 
			    put and delete artifacts into project</p>
			</div>
		</div>
	</div>
</div>

<div class="row">
	<div class="col-md-6 col-md-offset-3 boxed">
		<div class="panel panel-danger">
	 		<div class="panel-heading">
				<h3>Critical area</h3>
			</div>
		 	<div class="panel-body">
				<form>
					<label>Assign new owner</label>
					<input type="text" class="form-control" placeholder="Add new owner"/>
					<p class="text-info">* Grants privileges to another member of this project</p>
				</form>
				<br/>
				<div>
					<label>Delete this project</label>
					<br/>
					<div class="row">
						<div class="col-xs-4">
							<form action="<c:url value="/project"/>" method="POST">
								<input name="project.id" value="${project.id}" type="hidden"/>
								<button type="submit" class="btn btn-danger" name="_method" value="DELETE">Delete Project</button>
							</form>
						</div>
						<div class="col-xs-8">
							<p class="text-danger">* Make sure about this, because this operation is irreversible!</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>