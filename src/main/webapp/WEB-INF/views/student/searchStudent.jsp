<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
		 pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Search Student</title>

	<link rel="stylesheet" type="text/css"  href="<c:url
	value="${pageContext.request.contextPath}/css/custom/form-data.css" />" />

	<link rel="stylesheet" type="text/css"  href="<c:url
	value="${pageContext.request.contextPath}/css/custom/dataTables.jqueryui.min.css" />" />

	<script
			src="${pageContext.request.contextPath}/js/custom/jquery.dataTables.min.js"></script>

	<script>
		$(document).ready(function() {
			$('#studentList').DataTable();
		} );

		function submit(action, method) {
			var form = document.getElementById('studentListForm');
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

					<form:form action="/super/searchStudent"
							   cssClass="form-inline"
							   id="studentListForm"
							   modelAttribute="studentDto">

						<label class="control-label align-left" for="name">
							<spring:message code="student.name"/>
						</label>

						<form:input path="name" name="name" id="name" cssClass="span2"
									onkeyup="charOnly(this)" />

						<span style="padding: 10px"></span>

						<span style="padding: 10px">(OR)</span>

						<span style="padding: 10px"></span>

						<label class="control-label align-left" for="rollNo">
							<spring:message code="student.rollNo"/>
						</label>

						<form:input path="rollNo" name="rollNo" id="rollNo"
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

						<c:if test="${not empty studentList}">

							<table class="table " id="studentList" >
								<thead>
									<th scope="row">Joined Year</th>
									<th scope="row">Academic Year</th>
									<th scope="row">Branch</th>
									<th scope="row">Roll No</th>
									<th scope="row">Name</th>
									<th scope="row">Father Name</th>
									<th scope="row">Photo</th>
									<th scope="row">DOB</th>
									<th scope="row">DOJ</th>
									<th scope="row">Action</th>
								</thead>
								<tbody>
									<c:forEach items="${studentList }" var="student" >
										<tr>
											<td>${student.year }</td>
											<td>${student.batch }</td>
											<td>${student.branch }</td>
											<td>${student.rollNo }</td>
											<td>${student.name }</td>
											<td>${student.fatherName }</td>
											<td style="text-align: center; vertical-align: baseline">
												<c:if test="${not empty student.photoData}">
													<%--<img src="data:image/ext;base64,${student.photoData
													 }" />--%>

													<img src="data:image;base64, ${student.photoData }"
														 style='border: 1px solid black' width='25px'
														 height='30px'>
												</c:if>
											</td>
											<td>${student.dob }</td>
											<td>${student.doj }</td>
											<td>
												<a href="JavaScript:void(0);"
												   style="color: mediumblue"
												   onclick="submit('${Role}/student/edit/${student.studentId}/update','Get')">Edit</a>
												<%--<a style="color: green; text-decoration: underline;" href="${Role}/student/edit/${student.studentId}/update
				"> <spring:message code="edit"/>--%>
												</a>
												<span style="padding: 10px"></span>

												<a href="JavaScript:void(0);" style="color: red"
												   onclick="submit('${Role}/student/edit/${student.studentId}/delete','Get')">Delete</a>

												<%--<a style="color: red; text-decoration: underline;"
												   href="${Role}/student/edit/${student.studentId}/delete
				"><spring:message code="delete"/>--%>
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