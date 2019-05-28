<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Change Password</title>

	<script
			src="${pageContext.request.contextPath}/js/custom/form_validations.js"></script>

	<script>
		function submitData(action, method)
		{
			var student = document.getElementById('user');
			student.action = action;
			student.method = method;
			student.submit();
		}
	</script>
	<style>
		.error {
			color: #ff0000;
			font-style: italic;
			font-weight: bold;
			font-size: 10px;
		}

		.align-left {
			text-align: left !important;
		}
	</style>
</head>
<body>

	<section id="subintro">
		<div class="jumbotron subhead" id="overview">
			<div class="container">
				<div class="row">
					<div class="span12">
						<div class="centered">
							<h3>
								<spring:message code="user.changePassword"/>
							</h3>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>

	<section id="maincontent">
		<div class="container">

			<form:form action="/changePassword" id="user" method="${method}"
					   modelAttribute="userDto" cssClass="form-horizontal">
			<form:hidden path="userId" name="userId" id="userId" />

			<div class="row ">
				<div class="span12">
					<div class="err-message" style="text-align:center"> ${message}</div>
					<div class="err-message" id="wait" ></div>
				</div>

				<div class="span6 offset4">
					<div class="centered">

						<div class="control-group">

							<label class="control-label align-left" for="password">
								<spring:message code="user.password"/>
							</label>
							<div class="controls">
								<form:password path="password" name="password"
										id="password" maxlength="20"
											cssClass="span3" />
								<span class="help-block">
									<form:errors path="password" cssClass="error" />
								</span>
							</div>
						</div>

						<div class="control-group">

							<label class="control-label align-left" for="password">
								<spring:message code="user.newPassword"/>
							</label>
							<div class="controls">
								<input type="text" name="newPassword" maxlength="20"
									   class="span3" />
								<%--<form:password path="newPassword" id="newPassword" maxlength="20"
											   cssClass="span3" />--%>
								<span class="help-block">
									<%--<form:errors path="newPassword" cssClass="error" />--%>
								</span>
							</div>
						</div>

					</div>
				</div>

				<div class="span12">
					<div align="center" style="width: 100%;">
						<input type="button" class="btn btn-primary" value="${buttonValue }"
							   onclick="submitData('${action}', '${method}')" />
					</div>
				</div>

			</div>

			</form:form>
		</div>

	</section>

</body>
</html>