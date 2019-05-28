<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
		 pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Students List</title>

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

						<%--form-actions--%>
					<form:form action="${Role}/employee/list" cssClass="form-inline"
							   id="employeeListForm"
							   modelAttribute="employeeForm">

						<div class="row ">
							<div class="span8">

								<%--<label class="control-label align-left" for="deptId">
									<spring:message code="employee.department"/>
								</label>--%>

								<form:select path="deptId" name="deptId" id="deptId"
											 multiple="false" cssClass="span2">
									<form:option value="0" label="Department" />
									<c:forEach items="${departments}" var="batch">
										<option value="${batch.key}"
											<c:if test="${batch.key eq selectedDeptId}">selected="true"</c:if>
										> ${batch.value}
										</option>
									</c:forEach>
								</form:select>

								<%--<span style="padding: 10px"></span>--%>

								<%--<label class="control-label align-left"
									   for="joiningAcademicYearId">
									<spring:message code="employee.joiningAcademicYear"/>
								</label>--%>

								<form:select path="joiningAcademicYearId" name="joiningAcademicYearId" id="joiningAcademicYearId"
											 multiple="false" cssClass="span3">
									<form:option value="0"
												 label="Joining Academic Year" />
									<c:forEach items="${academicYears}" var="year">
										<option value="${year.key}"
												<c:if
														test="${year.key eq
														selectedJoiningAcademicYearId}">selected="true"</c:if>
										> ${year.value}
										</option>
									</c:forEach>
								</form:select>


								<%--<span style="padding: 10px"></span>--%>

								<%--<label class="control-label align-left" for="joiningSemesterId">
									<spring:message code="employee.joiningSemester"/>
								</label>--%>

								<form:select path="joiningSemesterId" name="joiningSemesterId" id="joiningSemesterId"
											 multiple="false" cssClass="span2">
									<form:option value="0" label="Joining Semester" />
									<c:forEach items="${semesters}" var="semester">
										<option value="${semester.key}"
												<c:if
														test="${semester.key eq
															selectedJoiningSemesterId}">selected="true"</c:if>
										> ${semester.value}
										</option>
									</c:forEach>
								</form:select>

								<br><br>

								<label class="control-label align-left" for="deptId">
									<spring:message code="employee.salary"/>
								</label>

								<form:select path = "conditionString"
											 id="conditionString" items = "${conditions}"
											 cssClass="span2" />

								<form:input path="salary" name="salary" id="salary"
											onkeyup="intOnly(this)"
											maxlength="6" cssClass="input-mini"/>
							</div>
							<div class="span2">
								<button type="submit" class="btn btn-primary">
									Get Data
								</button>
							</div>
						</div>
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
									<th scope="row">Department</th>
									<th scope="row">Joined Academic Year</th>
									<th scope="row">Joined Semester</th>
									<th scope="row">Emp No.</th>
									<th scope="row">Name</th>
									<th scope="row">Father Name</th>
									<th scope="row">Salary</th>
									<th scope="row">Photo</th>
									<th scope="row">DOB</th>
									<th scope="row">DOJ</th>
									<th scope="row">Action</th>
								</thead>
								<tbody>
									<c:forEach items="${employeeList }" var="employee" >
										<tr>
											<td>${employee.department }</td>
											<td>${employee.joiningAcademicYear }</td>
											<td>${employee.joiningSemester }</td>
											<td>${employee.empNo }</td>
											<td>${employee.name }</td>
											<td>${employee.fatherName }</td>
											<td>${employee.salary }</td>
											<td style="text-align: center; vertical-align: baseline">
												<c:if test="${not empty employee.photoData}">
													<%--<img src="data:image/ext;base64,${employee.photoData
													 }" />--%>

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