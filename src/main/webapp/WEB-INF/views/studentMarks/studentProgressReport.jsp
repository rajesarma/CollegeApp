<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Student Add</title>

	<script>

		function submitData(action, formId) {
			var student = document.getElementById(formId);
			student.action = action;
			student.method = "post";
			student.submit();
		}

		function getData(type, batch, branch, year, semester, targetId) {

			var url= "";
			if(type == "getStudents") {
				url = '${Role}/student/studentProgressReport/' + type + '/' +
						$("#"+batch).val() + '/' +$("#"+branch).val()+ '/' +$("#"+year).val()+ '/' +$("#"+semester).val();
			} else if(type == "getSemesters") {
				$('#studentId').empty().append('<option selected="selected" value="test">Select</option>');
				url ='${Role}/student/studentProgressReport/' + type + '/0/0/'+$("#"+year).val()+ '/0';
			}

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
					} else if(type == "getStudents") {

						if(response.studentsExists == "true")
						{
							var students = response.students.replace("{","").replace("}","");
							var studentsArr = students.split(",");
							var msg ="";
							for(var index =0; index < studentsArr.length; index++) {
								msg = msg + "\""+studentsArr[index].replace(/=/g, "\": \"")+"\",";
							}
							var subjectOptions = JSON.parse('{'+msg.substring(0,msg.length-1)+'}');

							$("#"+targetId).empty().append('<option selected="selected" value="0">Select</option>');

							$.each(subjectOptions, function(val, text) {
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
								<spring:message code="student.progressReport"/>
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

						<form:form action="${action}" method="${method}"
								   cssClass="form-inline" id="studentListForm"
								   modelAttribute="studentMarksDto">

								<label class="control-label align-left" for="batchId">
									<spring:message code="student.batch"/>
								</label>

								<form:select path="batchId" name="batchId" id="batchId"
											 multiple="false" cssClass="span1"
											 onChange="getData('getStudents', batchId.id, branchId.id, yearId.id, semesterId.id, studentId.id)">
									<form:option value="0" label="Select" />

									<c:forEach items="${batches}" var="batch">
										<option value="${batch.key}"
												<c:if
														test="${batch.key eq
														selectedBatchId}">selected="true"</c:if>
										> ${batch.value}
										</option>
									</c:forEach>

								</form:select>

								<span style="padding: 10px"></span>

								<label class="control-label align-left" for="branchId">
									<spring:message code="student.branch"/>
								</label>

								<form:select path="branchId" name="branchId" id="branchId"
											 multiple="false" cssClass="span2"
											 onChange="getData('getStudents', batchId.id, branchId.id, yearId.id, semesterId.id, studentId.id)">
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
											 multiple="false" cssClass="span1"
											 onChange="getData('getSemesters', 0, 0, yearId.id, 0, semesterId.id)">
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
											 multiple="false" cssClass="span1"
											 onChange="getData('getStudents', batchId.id, branchId.id, yearId.id, semesterId.id, studentId.id)">
									<form:option value="0" label="Select" />

									<c:forEach items="${semesters}" var="semester">
										<option value="${semester.key}"
											<c:if
													test="${semester.key eq selectedSemesterId}"> selected="true"</c:if>
										> ${semester.value}
										</option>
									</c:forEach>
								</form:select>

								<span style="padding: 10px"></span>

								<label class="control-label align-left" for="branchId">
									<spring:message code="student.name"/>
								</label>

								<form:select path="studentId" name="studentId"
											 id="studentId" cssClass="span2">
									<form:option value="0" label="Select" />

									<c:forEach items="${students}" var="student">
										<option value="${student.key}"
												<c:if test="${student.key eq selectedStudentId}">selected="true"</c:if>
										> ${student.value}
										</option>
									</c:forEach>

								</form:select>

								<span style="padding: 10px"></span>

								<button type="submit" class="btn btn-primary"
										onclick="submitData('${Role}/student/studentProgressReport')">
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

						<c:if test="${not empty studentsMarksList}">

							<table class="table " id="studentList" >
								<thead>
								<tr>
									<th rowspan="2" >S. No.</th>
									<th rowspan="2" >Subject</th>
									<th colspan="2" style="text-align: center">Internal - 1</th>
									<th colspan="2" style="text-align: center">Internal - 2</th>
									<th rowspan="2" style="text-align: center; vertical-align: middle">Internal
										<br />Marks</th>
									<th colspan="2" style="text-align: center">External</th>
									<th colspan="2" style="text-align: center">Total</th>
								</tr>
									<tr>
										<th scope="row">Max Marks</th>
										<th scope="row">Marks</th>
										<th scope="row">Max Marks</th>
										<th scope="row">Marks</th>

										<th scope="row">Max Marks</th>
										<th scope="row">Marks</th>

										<th scope="row">Max Marks</th>
										<th scope="row">Marks</th>
									</tr>
									</thead>
								<tbody>
									<c:forEach items="${studentsMarksList }" var="student" varStatus="row">
										<tr>
											<td style="text-align: center">${row.index+1}</td>
											<td>${student.subject_name }</td>
											<td style="text-align: center">${student.internal1_max }</td>
											<td style="text-align: center">${student.internal1 }</td>
											<td style="text-align: center">${student.internal2_max }</td>
											<td style="text-align: center">${student.internal2 }</td>
											<td style="text-align: center">${student.internal }</td>
											<td style="text-align: center">${student.external_max }</td>
											<td style="text-align: center">${student.external }</td>
											<td style="text-align: center">${student.subject_total_max }</td>
											<td style="text-align: center">${student.subject_total }</td>
										</tr>
									</c:forEach>
								</tbody>
								<tfoot>
								<c:forEach items="${studentsMarksTotals }" var="student" varStatus="row">
									<tr>
										<td style="text-align: center; font-weight: bold" colspan="2">Total</td>
										<td style="text-align: center; font-weight: bold">${student.internal1_max }</td>
										<td style="text-align: center; font-weight: bold">${student.internal1 }</td>
										<td style="text-align: center; font-weight: bold">${student.internal2_max }</td>
										<td style="text-align: center; font-weight: bold">${student.internal2 }</td>
										<td style="text-align: center; font-weight: bold">${student.internal }</td>
										<td style="text-align: center; font-weight: bold">${student.external_max }</td>
										<td style="text-align: center; font-weight: bold">${student.external }</td>
										<td style="text-align: center; font-weight: bold">${student.subject_total_max }</td>
										<td style="text-align: center; font-weight: bold">${student.subject_total }</td>
									</tr>
								</c:forEach>
								</tfoot>
							</table>
						</c:if>
					</div>
				</div>
			</div>
		</div>
	</section>
</body>
</html>