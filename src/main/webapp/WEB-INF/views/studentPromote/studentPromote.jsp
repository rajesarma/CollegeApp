<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Student Promote</title>

	<script>

		function checkSelection() {
			$('.checkbox').attr("checked", !$('.checkbox').attr('checked'));
		}

		function submitData(action, formId) {
			var student = document.getElementById(formId);
			student.action = action;
			student.method = "post";
			student.submit();
		}

		function edit(action, method) {
			var form = document.getElementById('studentPromoteForm');
			form.action = action;
			form.method = method;
			form.submit();
		}

		function getData(type,obj, targetId) {
			if(obj.value.length > 0) {
				var url = '${Role}/student/' + type + '/' + obj.value;
				var message;
				$.ajax( {
					type: "GET",
					url:url,
					cache: false,
					success: function(response) {
						$("#wait").html("");
						var response = jQuery.parseJSON(response);

						if(type == "getSemesters") {

							if(response.semestersExists == "true")
							{
								var semesters =
										response.semesters.replace("{","").replace("}","").replace(",","");
								var semOptions =
										JSON.parse('{"'+semesters.replace(/ /g, '", "').replace(/=/g, '": "')+ '"}');

								$("#"+targetId).empty().append('<option selected="selected" value="0">Select</option>');

								$.each(semOptions, function(val, text) {
									$("#"+targetId).append($('<option></option>').val(val).html(text));
								});
							}
						}
					}, error: function(response) {
						//alert("No data");
						$("#wait").html("<center style='font-size: 14px' ><span id='loading' style='font-size: 14px; color:red'><i class='fa fa-spinner fa-spin' style='font-size:24px'></i> <b>" + response.error + "</b></span></center>");
					},
					beforeSend: function( event, ui ) {
						$("#wait").html("<center style='font-size: 14px' ><span id='loading' style='font-size: 14px; color:red'><i class='fa fa-spinner fa-spin' style='font-size:24px'></i> <b>Loading... </b></span></center>");
					}
				});
			}
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
								<spring:message code="student.promote"/>
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

						<form:form action="${action}" method="${method}" cssClass="form-inline" id="studentPromoteForm"
								   modelAttribute="studentPromoteForm">
							<c:if test="${showTab eq 'list'}">

								<label class="control-label align-left" for="batchId">
									<spring:message code="student.batch"/>
								</label>

								<form:select path="batchId" name="batchId" id="batchId" multiple="false"
											 cssClass="span2">
									<form:option value="0" label="Select" />
									<%--<form:options items="${batches}" />--%>

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

									<c:forEach items="${branches}" var="branch">
										<option value="${branch.key}"
												<c:if test="${branch.key eq selectedBranchId}">selected="true"</c:if>
										> ${branch.value}
										</option>
									</c:forEach>

								</form:select>

								<span style="padding: 10px"></span>

								<label class="control-label align-left" for="yearId">
									<spring:message code="student.year"/>
								</label>

								<form:select path="yearId" name="yearId" id="yearId"
											 multiple="false" cssClass="span2" onChange="getData('getSemesters', this, semesterId.id)">
									<form:option value="0" label="Select" />
									<c:forEach items="${years}" var="year">
										<option value="${year.key}"
												<c:if test="${year.key eq selectedYearId}">selected="true"</c:if>
										> ${year.value}
										</option>
									</c:forEach>
								</form:select>

								<span style="padding: 10px"></span>

								<label class="control-label align-left" for="semesterId">
									<spring:message code="student.semester"/>
								</label>

								<form:select path="semesterId" name="semesterId"
											 id="semesterId"
											 multiple="false" cssClass="span2">
									<form:option value="0" label="Select" />

									<c:forEach items="${semesters}" var="batch">
										<option value="${batch.key}"
												<c:if test="${batch.key eq selectedSemesterId}"> selected="true"</c:if>
										> ${batch.value}
										</option>
									</c:forEach>
								</form:select>

								<span style="padding: 10px"></span>

								<button type="button" class="btn btn-primary"
										onclick="submitData('${Role}/student/studentPromote${operation}','studentPromoteForm')">
									Get Data
								</button>
							</c:if>

							<c:if test="${not empty message}">
								<br>
								<br>
								<div class="err-message" style="text-align:center"> ${message}</div>
							</c:if>

							<c:if test="${not empty studentPromoteForm.students}">
								<table class="table " id="studentList" >
									<thead>
									<th>S. No.</th>
									<th scope="row">Batch</th>
									<th scope="row">Branch</th>
									<th scope="row">Year</th>
									<th scope="row">Semester</th>
									<th scope="row">Roll No</th>
									<th scope="row">Name</th>
									<th scope="row" style="text-align: center">Select
										<br>
										<input type="checkbox" onclick="checkSelection()">
									</th>
									</thead>
									<tbody>
									<c:forEach items="${studentPromoteForm.students}" var="student" varStatus="row">
										<tr>
											<td>${row.index+1}
												<%--<form:hidden
														path="students[${row.index}].studentId"
														name="students[${row.index}].studentId" />--%>
											</td>
											<td>${student.batch }
												<form:hidden
														path="students[${row.index}].batchId"
														name="students[${row.index}].batchId" />
											</td>
											<td>${student.branch }
												<form:hidden
														path="students[${row.index}].branchId"
														name="students[${row.index}].branchId" />
											</td>
											<td>${student.currentYear }
												<form:hidden
														path="students[${row.index}].currentYearId"
														name="students[${row.index}].currentYearId" />
											</td>
											<td>${student.currentSemester }
												<form:hidden
														path="students[${row.index}].currentSemesterId"
														name="students[${row.index}].currentSemesterId"  />
											</td>
											<td>${student.rollNo }
												<form:hidden
														path="students[${row.index}].rollNo"
														name="students[${row.index}].rollNo" />
											</td>
											<td>${student.name }
												<form:hidden
														path="students[${row.index}].name"
														name="students[${row.index}].name"  />
											</td>
											<td style="text-align: center">
												<form:checkbox path="studentIds" value="${student.studentId}"
															   cssClass="checkbox"/>
											</td>
										</tr>
									</c:forEach>
									</tbody>
								</table>

								<button type="button" class="btn btn-primary"
										onclick="submitData('${Role}/student/studentPromote/add','studentPromoteForm')">
									Promote
								</button>
							</c:if>


						</form:form>
					</div>
				</div>
			</div>


		</div>
	</section>
</body>
</html>