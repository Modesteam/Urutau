<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div class="members">
	<div class="panel-heading">
		<h3 class="panel-title">
			<i class="glyphicon glyphicon-list"></i> Members
		</h3>
	</div>
	<table class="table table-striped table-members">
		<tbody>
			<c:forEach var="member" items="${members}">
				<tr>
					<td class="col-md-10">
						<div class="member-info">
							${member.name} ${member.lastName} <br /> <small>${member.login}</small>
						</div>
					</td>
					<td class="col-md-2">
						<form action="<c:url value="/project/removeMember"/>" method="POST">
							<input name="userID" value="${member.userID}" type="hidden" />
							<input name="projectID" value="${projectID}" type="hidden" />
							<button class="btn btn-default" type="submit" name="_method" value="DELETE">
								<i class="glyphicon glyphicon-remove alert-danger"></i>
							</button>
						</form>
					</td>
				</tr>
			</c:forEach>
			<tr class="text-center">
				<td colspan="2">
					<button onclick="previous()" class="btn btn-default">
						<i class="glyphicon glyphicon-backward"></i>
					</button>
					<button onclick="next()" class="btn btn-default">
						<i class="glyphicon glyphicon-forward"></i>
					</button>
				</td>
			</tr>
		</tbody>
	</table>
</div>
