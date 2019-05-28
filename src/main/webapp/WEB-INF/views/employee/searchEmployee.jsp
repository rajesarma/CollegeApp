<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
		 pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Search Employee</title>

	<script
			src="${pageContext.request.contextPath}/js/custom/jquery.dataTables.min.js"></script>

	<script>
		$(document).ready(function() {
			$('#employeeList').DataTable();
		} );

		function submit(action, method) {
			var form = document.getElementById('employeeListForm');
			form.action = action;
			form.method = method;
			form.submit();
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
								Search
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

					<form:form action="/super/searchEmployee"
							   cssClass="form-inline"
							   id="employeeListForm"
							   modelAttribute="employeeForm">

						<label class="control-label align-left" for="name">
							<spring:message code="employee.name"/>
						</label>

						<form:input path="name" name="name" id="name" cssClass="span2"
									onkeyup="charOnly(this)" />

						<span style="padding: 10px"></span>

						<span style="padding: 10px">(OR)</span>

						<span style="padding: 10px"></span>

						<label class="control-label align-left" for="empNo">
							<spring:message code="employee.empNo"/>
						</label>

						<form:input path="empNo" name="empNo" id="empNo"
									cssClass="span2"
									onkeypress="javascript:return isAlphaNumeric(event,this.value);"
									 />

						<span style="padding: 10px"></span>

						<button type="submit" class="btn btn-primary">
							Search
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

						<c:if test="${not empty employeeList}">

							<table class="table " id="employeeList" >
								<thead>
									<th scope="row">Joined Academic Year</th>
									<th scope="row">Department</th>
									<th scope="row">Emp No</th>
									<th scope="row">Name</th>
									<th scope="row">Father Name</th>
									<th scope="row">Photo</th>
									<th scope="row">DOB</th>
									<th scope="row">DOJ</th>
									<th scope="row">Action</th>
								</thead>
								<tbody>
									<c:forEach items="${employeeList }" var="employee" >
										<tr>
											<td>${employee.joiningAcademicYear }</td>
											<td>${employee.department }</td>
											<td>${employee.empNo }</td>
											<td>${employee.name }</td>
											<td>${employee.fatherName }</td>
											<td style="text-align: center; vertical-align: baseline">
												<c:if test="${not empty employee.photoData}">
													<img src="data:image;base64, ${employee.photoData }"
														 style='border: 1px solid black' width='25px'
														 height='30px'>
												</c:if>
											</td>
											<td>${employee.dob }</td>
											<td>${employee.doj }</td>
											<td>
												<a href="JavaScript:void(0);"
												   style="color: mediumblue"
												   onclick="submit('${Role}/employee/edit/${employee.employeeId}/update','Get')">Edit</a>
												</a>
												<span style="padding: 10px"></span>
												<a href="JavaScript:void(0);" style="color: red"
												   onclick="submit('${Role}/employee/edit/${employee.employeeId}/delete','Get')">Delete</a>
												</a>
												<span style="padding: 10px"></span>
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