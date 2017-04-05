<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

Result is ${members.size()}

<br/>

<c:forEach var="member" items="${members}">
	<li>${member.name}</li>
</c:forEach>

Page ${page.number}