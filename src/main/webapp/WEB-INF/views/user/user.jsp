<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
	<meta charset="ISO-8859-1">
	<title>User</title>

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
								<spring:message code="user.operations"/>
							</h3>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>

	<section id="maincontent">
		<div class="container">
			<form:form action="/admin/user" id="user" method="${method}"
					   modelAttribute="userDto" enctype="multipart/form-data"
					   cssClass="form-horizontal">
			<form:hidden path="userId" name="userId" id="userId" />

			<div class="row ">
				<div class="span12">
					<div class="err-message" style="text-align:center"> ${message}</div>
					<div class="err-message" id="wait" ></div>
				</div>

				<div class="span6 offset4">
					<div class="centered">

						<div class="control-group">
							<label class="control-label align-left" for="username">
								<spring:message code="user.username"/>
							</label>

							<div class="controls">

								<form:input path="username" name="username" id="username"
											cssClass="span3" maxlength="25"
											onkeyup="charOnly(this)"
								/>
								<span class="help-inline">
									<form:errors path="username" cssClass="error" />
								</span>

							</div>
						</div>

						<div class="control-group">

							<label class="control-label align-left" for="password">
								<spring:message code="user.password"/>
							</label>
							<div class="controls">
								<form:password path="password" name="password"
										id="password" maxlength="20"
											cssClass="span3" />
								<span class="help-inline">
									<form:errors path="password" cssClass="error" />
								</span>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label align-left" for="roles">
								<spring:message code="user.roles"/>
							</label>

							<div class="controls">

								<%--${selectedRoles}--%>

									<%--<form:select path="roles" name="roles" id="roles" multiple="true"
												 cssClass="span3" cssStyle="height: 100px">
										<form:options items="${roles}" selected="false" />
									</form:select>--%>

								<form:select path="roles" name="roles" id="roles" multiple="true"
										 cssClass="span3" cssStyle="height: 100px">
									<c:forEach items="${roles}" var="role">
										<option value="${role.key}"
											<c:forEach items="${selectedRoleIds}" var="selectedRoleId">
												<c:if test="${role.key eq selectedRoleId}">selected="true"</c:if>
											</c:forEach>
											> ${role.value}
										</option>
									</c:forEach>
								</form:select>

								<%--
									<% System.out.println(pageContext.findAttribute("role")); %>--%>

								<span class="help-inline">
									<form:errors path="roles" cssClass="error" />
								</span>
							</div>
						</div>

						<div class="control-group">

							<label class="control-label align-left" for="disabled">
								<spring:message code="user.disabled"/>
							</label>
							<div class="controls">
								<i class="fa fa-user" aria-hidden="true"></i>

								<form:radiobutton path="disabled" value="false"
												  id="disabled"/> Yes
								<form:radiobutton path="disabled" value="true"
												  id="disabled"/> No

								<form:errors path="disabled" cssClass="error" />

							</div>
						</div>


						<div class="control-group">
							<label class="control-label align-left" for="email">
								<spring:message code="user.email"/>
							</label>

							<div class="controls">

								<%--<div class="input-prepend"> <span class="add-on"><i class="icon-envelope"></i></span>--%>
								<form:input path="email" name="email" id="email"
											maxlength="50" cssClass="span3" />
								<span class="help-inline">
									<form:errors path="email" cssClass="error" />
								</span>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label align-left" >
								<spring:message code="user.userDesc"/>
							</label>
							<div class="controls">

								<form:textarea path="userDesc" name="userDesc" id="userDesc"
											   cssClass="span3" rows="3"
											   cssStyle="height: 100px"/>
								<span class="help-inline">
								<form:errors path="userDesc" cssClass="error" />
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