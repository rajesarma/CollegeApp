<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
		 pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Role Services List</title>

	<script>
		$(document).ready(function() {
			$('#roleServicesList').DataTable();
		} );

		function getServices(obj) {

			$.each($("#services option:selected"), function() {
				$(this).prop('selected', false);
			});

			$.ajax( {
				type : "POST",
				url : '/super/roleServices/' + obj.value,
				cache : false,
				success : function(response) {
					$("#wait").html("");
					$.each(response, function(i,e){
						$("#services option[value='" + e + "']").prop("selected", true);
					});
				}, error: function(response) {
					$("#wait").html("<center style='font-size: 14px' ><span id='loading' style='font-size: 14px; color:red'><i class='fa fa-spinner fa-spin' style='font-size:24px'></i> <b>" + response.error + "</b></span></center>");
				},
				beforeSend: function( event, ui ) {
					$("#wait").html("<center style='font-size: 14px' ><span id='loading' style='font-size: 14px; color:red'><i class='fa fa-spinner fa-spin' style='font-size:24px'></i> <b>Loading... </b></span></center>");
				}
			});
		}

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
								Services Mapping
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
						<aside>

							<div class="span12">
								<div class="err-message" style="text-align:center"> ${message}</div>
								<div id="wait" class="err-message"></div>
							</div>

							<form:form action="/super/roleServices" cssClass="form-inline"
									   method="post" modelAttribute="role">

								<label class="control-label align-left" for="roleId">
									<spring:message code="role.role"/>
								</label>

								<form:select path="roleId" name="roleId" id="roleId" multiple="false"
											 cssClass="span2" onChange="getServices(this)">	<%----%>
									<%--<form:options items="${roles}" />--%>

									<c:forEach items="${roles}" var="role">
										<option value="${role.key}"
												<c:if test="${role.key eq roleId}">selected="true"</c:if>
										> ${role.value}
										</option>
									</c:forEach>
								</form:select>
								<span style="padding-right: 20px" ></span>
								<label class="control-label align-left" for="roleId">
									<spring:message code="service.services"/>
								</label>

								<form:select path="services" name="services" id="services"
											 multiple="true" cssStyle="height: 200px; width: 200px"
											 cssClass="span2">
									<%--<form:options items="${services}" />--%>
									<c:forEach items="${services}" var="role">
										<option value="${role.key}"
												<c:forEach items="${selectedServiceIds}" var="selectedServiceId">
													<c:if test="${role.key eq selectedServiceId}">selected="true"</c:if>
												</c:forEach>
										> ${role.value}
										</option>
									</c:forEach>

								</form:select>

								<span style="padding: 20px"></span>

								<button type="submit" class="btn btn-primary">
									Map
								</button>
							</form:form>
						</aside>
					</div>
				</div>
			</div>


			<%--<div class="row ">
				<div class="span12">
					<div class="centered">

						<c:if test="${not empty sites}">

							<table class="table " id="usersList" >
								<thead>
								<th scope="row">Location</th>
								<th scope="row">IP1</th>
								<th scope="row">IP2</th>
								</thead>
								<tbody>
									<c:forEach items="${sites }" var="site" >
										<tr>
											<td ><c:out
													value="${site.key}"/></td>
											<c:forEach items="${site.value }"
													   var="location" >
												<td
													<c:choose>
														<c:when test="${location.value eq'UP'}">
														style='background-color:#008000; color: white'
														</c:when>
														<c:when test="${location.value
														eq'DOWN'}">
															style='background-color:red;color: white'
														</c:when>
													</c:choose>
													>
													<c:out value="${location.key}"/>
												</td>
											</c:forEach>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</c:if>
					</div>
				</div>
			</div>--%>

		</div>
	</section>
</body>
</html>