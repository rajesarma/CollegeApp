<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
		 pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Users List</title>

	<%--<link rel="stylesheet" type="text/css"  href="<c:url
	value="${pageContext.request.contextPath}/css/custom/dataTables.jqueryui.min.css" />" />

	<script
			src="${pageContext.request.contextPath}/js/custom/jquery.dataTables.min.js"></script>--%>

	<script>
		$(document).ready(function() {
			$('#usersList').DataTable();
		} );

	</script>

</head>
<body>
	<section id="subintro">
		<div class="jumbotron subhead" id="overview">
			<div class="container">
				<div class="row">
					<div class="span12">
						<div class="centered">
							<h3>
								List
							</h3>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>

	<section id="maincontent">
		<div class="container">
			<div class="row ">
				<div class="span12">
					<div class="centered">

					<form:form action="/admin/usersList" cssClass="form-inline"
							   method="post" modelAttribute="userDto" >

						<label class="control-label align-left" for="roles">
							<spring:message code="user.roles"/>
						</label>

						<form:select path="roles" name="roles" id="roles" multiple="false"
									 cssClass="span2">
							<%--<form:options items="${roles}" />--%>
							<c:forEach items="${roles}" var="role">
								<option value="${role.key}"
										<c:forEach items="${selectedRoleIds}" var="selectedRoleId">
											<c:if test="${role.key eq selectedRoleId}">selected="true"</c:if>
										</c:forEach>
								> ${role.value}
								</option>
							</c:forEach>
						</form:select>

						<span style="padding: 20px"></span>

						<button type="submit" class="btn btn-primary">
							Get Data
						</button>
					</form:form>
					</div>
				</div>
			</div>


			<div class="row ">
				<div class="span12">
					<div class="centered">
						<c:if test="${not empty message}">
							<div class="err-message" style="text-align:center"> ${message}</div>
						</c:if>

						<c:if test="${not empty usersList}">

							<table class="table " id="usersList" >
								<thead>
									<th scope="row">Name</th>
									<th scope="row">e-mail</th>
									<th scope="row">Desc</th>
									<th scope="row">Is Active</th>
									<th scope="row">Action</th>
								</thead>
								<tbody>
									<c:forEach items="${usersList }" var="user" >
										<tr>
											<td>${user.username }</td>
											<td>${user.email }</td>
											<td>${user.userDesc }</td>
											<td>${user.activeStatus }</td>

											<td>
												<a style="color: green; text-decoration: underline;" href="${Role}/user/edit/${user.userId}/update
				"> <spring:message code="edit"/>
												</a>
												<span style="padding: 10px"></span>
												<a style="color: red; text-decoration: underline;"
												   href="${Role}/user/edit/${user.userId}/delete"><spring:message code="delete"/>
												</a>
												<span style="padding: 10px"></span>
													<%--<spring:url value="/article/updateArticle/${article.id }" var="updateURL" />--%>
													<%--<a class="btn btn-primary" href="${updateURL }" role="button" >Update</a>--%>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</c:if>
					</div>
				</div>
			</div>

		</div>
	</section>

</body>
</html>