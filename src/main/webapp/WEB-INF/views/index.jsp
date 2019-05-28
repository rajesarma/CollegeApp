<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
		 pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Site</title>

	<style>
		body {
			display: -ms-flexbox;
			display: flex;
			-ms-flex-align: center;
			align-items: center;
			padding-top: 40px;
			padding-bottom: 40px;
			/*background-color: #f5f5f5;*/
		}

		.form-signin {
			width: 100%;
			max-width: 330px;
			padding: 15px;
			margin: auto;
		}
		.form-signin .checkbox {
			font-weight: 400;
		}
		.form-signin .form-control {
			position: relative;
			box-sizing: border-box;
			height: auto;
			padding: 10px;
			font-size: 16px;
		}
		.form-signin .form-control:focus {
			z-index: 2;
		}
		.form-signin input[type="email"] {
			margin-bottom: -1px;
			border-bottom-right-radius: 0;
			border-bottom-left-radius: 0;
		}
		.form-signin input[type="password"] {
			margin-bottom: 10px;
			border-top-left-radius: 0;
			border-top-right-radius: 0;
		}

		.err-message {
			font-size: 14px;
			color: red;
			text-align: center;
			margin-bottom: 10px;
		}

	</style>

</head>
<body onload="document.getElementById('username').focus()">

	<div class="container">

		<div class="row">
			<div class="span2"></div>


			<div class="span6">
				<aside>
				<div class="well" >

				<form:form action="/login" method="post" modelAttribute="user"
						   cssClass="form-horizontal">
					<div class="control-group">
							<div class="card-header">
								<h4 class="my-0 font-weight-normal" style="text-align: center">
									Sign in
								</h4>
							</div>
							<div class="card-body">

								<c:if test="${not empty sessionScope.SPRING_SECURITY_LAST_EXCEPTION}">
									<hr>
									<div class="err-message" style="text-align:center"> <%--alert alert-error--%>
											<%--Reason: ${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}--%>
										${SPRING_SECURITY_LAST_EXCEPTION.message}
									</div>
									<hr>
								</c:if>

								<div class="err-message">
									${message}
								</div>

								<%--<label for="username" class="sr-only">User Name</label>--%>

								<div class="control-group">
									<label class="control-label" for="username">User Name</label>

									<div class="controls">
									<form:input path="username" name="username" id="username" cssClass="form-control"
												placeholder="User Name" />
									</div>
								</div>

								<div class="control-group">
									<label for="password" class="control-label">Password</label>
									<div class="controls">
									<form:password path="password" name="password" id="password" cssClass="form-control"
												   placeholder="Password" />

									</div>
								</div>

								<div class="control-group">
									<div class="controls">
										<button type="submit" class="btn btn-primary">
											Sign in
										</button>
										<br>
										<br>
										<label>
											<input type="checkbox" value="remember-me"> Remember me
										</label>

									</div>
								</div>
								<div class="control-group">
									<div class="controls">
										<a href="#" class="bottom-text-w3ls">Forgot Password?</a>
									</div>
								</div>

						</div>
					</div>
				</form:form>

			</div>
				</aside>
		</div>
		</div>
	</div>
</body>
</html>