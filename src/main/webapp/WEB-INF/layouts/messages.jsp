<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<script type="text/javascript">

/**
 * Fix for empty alerts
 */
$(document).ready(function() {
	var alertMessage = $(".alert-json").val();

	if($("#message").text() == '') {
		$(".alert-json").hide();
	}
});
</script>

<c:if test="${errors ne null}">
	<h3 class="error">
		<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true">
		</span>&nbsp;<fmt:message key="errors_on_submit"/>
	</h3>
	
	<c:forEach var="error" items="${errors}">
		<c:if test="${error.category eq 'error'}">
			<ul class="alert alert-danger" role="alert">
				<li class="error-message">${error.message}</li>
			</ul>
		</c:if>
		
		<c:if test="${error.category eq 'warning'}">
			<ul class="alert alert-warning" role="alert">
				<li class="error-message">${error.message}</li>
			</ul>
		</c:if>
	</c:forEach>
</c:if>

<c:if test="${success ne null}">
	<div class="alert alert-success" role="alert">
		${success}
		<button type="button" class="close" data-dismiss="alert" aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
	</div>
</c:if>

<!--  JSON messages -->
<div class="alert alert-json alert-success" role="alert">
	<span id="message"></span>
	<a href="#" class="close" onclick="closeAlert()" aria-label="close">x</a>
</div>