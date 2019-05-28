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

	<link rel="stylesheet" type="text/css"  href="<c:url
	value="${pageContext.request.contextPath}/css/custom/form-data.css" />" />

	<link rel="stylesheet" type="text/css"  href="<c:url
	value="${pageContext.request.contextPath}/css/custom/dataTables.jqueryui.min.css" />" />

	<%--<link rel="stylesheet" type="text/css"  href="<c:url
	value="${pageContext.request.contextPath}/css/custom/jquery-ui.css" />" />--%>

	<%--<script src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>--%>
	<script
			src="${pageContext.request.contextPath}/js/custom/jquery.dataTables.min.js"></script>
	<%--<script src="${pageContext.request.contextPath}/js/dataTables.jqueryui.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery-ui.js"></script>--%>

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

					<form:form action="${Role}/student/list" cssClass="form-inline"
							   id="studentListForm"
							   modelAttribute="studentDto">



							<label class="control-label align-left" for="batchId">
								<spring:message code="student.batch"/>
							</label>

							<form:select path="batchId" name="batchId" id="batchId"
										 multiple="false" cssClass="span2">
								<form:option value="0" label="Select" />
								<%--<form:options items="${academicYears}" />--%>

								<c:forEach items="${batches}" var="batch">
									<option value="${batch.key}"
										<c:if test="${batch.key eq selectedBatchId}">selected="true"</c:if>
									> ${batch.value}
									</option>
								</c:forEach>

							</form:select>

							<span style="padding: 10px"></span>

							<label class="control-label align-left" for="branchId">
								<spring:message code="student.branch"/>
							</label>

							<form:select path="branchId" name="branchId" id="branchId"
										 multiple="false" cssClass="span2">
								<form:option value="0" label="Select" />
								<%--<form:options items="${branches}" />--%>

								<c:forEach items="${branches}" var="branch">
									<option value="${branch.key}"
											<c:if test="${branch.key eq selectedBranchId}">selected="true"</c:if>
									> ${branch.value}
									</option>
								</c:forEach>

							</form:select>

							<span style="padding: 10px"></span>

							<label class="control-label align-left" for="joiningYearId">
								<spring:message code="student.joiningYear"/>
							</label>

							<form:select path="joiningYearId" name="joiningYearId" id="joiningYearId"
										 multiple="false" cssClass="span2">
								<form:option value="0" label="Select" />
								<c:forEach items="${years}" var="year">
									<option value="${year.key}"
											<c:if test="${year.key eq selectedYearId}">selected="true"</c:if>
									> ${year.value}
									</option>
								</c:forEach>
							</form:select>

							<span style="padding: 10px"></span>

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

						<c:if test="${not empty studentList}">

							<table class="table " id="studentList" >
								<thead>
									<th scope="row">Batch</th>
									<th scope="row">Branch</th>
									<th scope="row">Joined Year</th>
									<th scope="row">Joined Semester</th>
									<th scope="row">Roll No.</th>
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
											<td>${student.batch }</td>
											<td>${student.branch }</td>
											<td>${student.year }</td>
											<td>${student.semester }</td>
											<td>${student.rollNo }</td>
											<td>${student.name }</td>
											<td>${student.fatherName }</td>
											<td style="text-align: center; vertical-align: baseline">
												<c:if test="${not empty student.photoData}">
													<%--<img src="data:image/ext;base64,${college.photoData
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
												<%--<a style="color: green; text-decoration: underline;" href="${Role}/student/edit/${college.studentId}/update
				"> <spring:message code="edit"/>--%>
												</a>
												<span style="padding: 10px"></span>

												<a href="JavaScript:void(0);" style="color: red"
												   onclick="submit('${Role}/student/edit/${student.studentId}/delete','Get')">Delete</a>

												<%--<a style="color: red; text-decoration: underline;"
												   href="${Role}/student/edit/${college.studentId}/delete
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