<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Student Attendance</title>

	<script>

		function submitData(action, formId) {
			var student = document.getElementById(formId);
			student.action = action;
			student.method = "post";
			student.submit();
		}

		function submit(action, method) {
			var form = document.getElementById('studentAttendanceForm');
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
								<spring:message code="student.attendance"/>
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
								   cssClass="form-inline"
								   id="studentListForm"
								   modelAttribute="studentAttendanceDto">

							<c:if test="${showTab eq 'edit'}">
								<table class="table " id="studentList" >
									<thead>
										<th scope="row">Batch</th>
										<th scope="row">Branch</th>
										<th scope="row">Year</th>
										<th scope="row">Semester</th>
										<th scope="row">Roll No</th>
										<th scope="row">Name</th>
										<th scope="row">No. of Days</th>
										<th scope="row">Days Present</th>
										<th scope="row">Action</th>
									</thead>
									<tbody>
										<tr>
											<td>
												<form:hidden path="studentAttendanceId"
															 name="studentAttendanceId" />

													${studentAttendanceDto.batch }
												<form:hidden path="batchId"
															 name="batchId" />

												<form:hidden path="batch" name="batch" />
											</td>
											<td>${studentAttendanceDto.branch }
												<form:hidden path="branchId"
															 name="branchId" />
												<form:hidden path="branch"
															 name="branch" />
											</td>
											<td>${studentAttendanceDto.year }
												<form:hidden path="yearId"
															 name="yearId" />
												<form:hidden path="year" name="year" />
											</td>
											<td>${studentAttendanceDto.semester }
												<form:hidden path="semesterId"
															 name="semesterId"  />
												<form:hidden path="semester"
															 name="semester"  />
											</td>
											<td>${studentAttendanceDto.rollNo }
												<form:hidden path="rollNo"
															 name="rollNo" />
											</td>
											<td>${studentAttendanceDto.name }
												<form:hidden path="studentId"
															 name="studentId"  />
												<form:hidden path="name" name="name"  />
											</td>
											<td style="text-align: center">
												<form:input path="noOfDays"
															name="noOfDays"
															id="noOfDays"
															cssStyle="text-align: right"
															maxlength="3" cssClass="span1"
															onkeyup="intOnly(this)"/>
											</td>
											<td style="text-align: center">
												<form:input path="daysPresent"
															name="daysPresent"
															id="daysPresent"
															cssStyle="text-align: right"
															maxlength="3" cssClass="span1"
															onkeyup="intOnly(this)"/>
											</td>
											<td>
												<button type="submit"
														class="btn btn-primary" onclick="submitData('${Role}/student/studentAttendance${operation}','studentListForm')">
													${buttonValue}
												</button>
											</td>
										</tr>
									</tbody>
								</table>
							</c:if>

							<c:if test="${showTab eq 'list'}">

								<label class="control-label align-left" for="batchId">
									<spring:message code="student.batch"/>
								</label>

								<form:select path="batchId" name="batchId" id="batchId"
											 multiple="false" cssClass="span2">
									<form:option value="0" label="Select" />
									<%--<form:options items="${batches}" />--%>

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
												<c:if
														test="${year.key eq
														selectedYearId}">selected="true"</c:if>
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
											<c:if
													test="${batch.key eq selectedSemesterId}"> selected="true"</c:if>
										> ${batch.value}
										</option>
									</c:forEach>
								</form:select>

								<span style="padding: 10px"></span>

								<button type="submit" class="btn btn-primary"
										onclick="submitData('${Role}/student/studentAttendance${operation}','studentListForm')">
									Get Data
								</button>
							</c:if>

						</form:form>
					</div>
				</div>
			</div>

			<c:if test="${not empty message}">
				<div class="err-message" style="text-align:center"> ${message}</div>
			</c:if>

			<c:if test="${not empty attendanceForm}">

				<form:form action="${attendanceAction}" method="${method}"
						   cssClass="form-inline"
						   id="studentAttendanceForm"
						   modelAttribute="attendanceForm">

					<div class="row ">
						<div class="span12">
							<div class="centered">

								<form:hidden path="batchId" name="batchId" />
								<form:hidden path="branchId" name="branchId" />
								<form:hidden path="yearId" name="yearId" />
								<form:hidden path="semesterId" name="semesterId" />


								<c:if test="${not empty attendanceForm.attendanceDtos}">
									<table class="table " id="studentList" >
										<thead>
											<th>S. No.</th>
											<th scope="row">Batch</th>
											<th scope="row">Branch</th>
											<th scope="row">Year</th>
											<th scope="row">Semester</th>
											<th scope="row">Roll No</th>
											<th scope="row">Name</th>
											<th scope="row">No. of Days</th>
											<th scope="row">Days Present</th>
											<th scope="row">Action</th>
										</thead>
										<tbody>
											<c:forEach items="${attendanceForm.attendanceDtos}" var="attendanceDto" varStatus="row">
												<tr>
													<td>${row.index+1}
														<form:hidden
																path="attendanceDtos[${row.index}].studentAttendanceId"
																name="attendanceDtos[${row.index}].studentAttendanceId" />
													</td>
													<td>${attendanceDto.batch }
														<form:hidden
														path="attendanceDtos[${row.index}].batchId"
														name="attendanceDtos[${row.index}].batchId" />
													</td>
													<td>${attendanceDto.branch }
														<form:hidden
																path="attendanceDtos[${row.index}].branchId"
																name="attendanceDtos[${row.index}].branchId" />
													</td>
													<td>${attendanceDto.year }
														<form:hidden
																path="attendanceDtos[${row.index}].yearId"
																name="attendanceDtos[${row.index}].yearId" />
													</td>
													<td>${attendanceDto.semester }
														<form:hidden
																path="attendanceDtos[${row.index}].semesterId"
																name="attendanceDtos[${row.index}].semesterId"  />
													</td>
													<td>${attendanceDto.rollNo }
														<form:hidden
																path="attendanceDtos[${row.index}].rollNo"
																name="attendanceDtos[${row.index}].rollNo" />
													</td>
													<td>${attendanceDto.name }
														<form:hidden
																path="attendanceDtos[${row.index}].studentId"
																name="attendanceDtos[${row.index}].studentId"  />
													</td>
													<td style="text-align: center">
														<c:choose>
															<c:when
																	test="${attendanceDto.noOfDays != 0}">
																${attendanceDto.noOfDays }

																<form:hidden
																		path="attendanceDtos[${row.index}].noOfDays"
																		name="attendanceDtos[${row.index}].noOfDays"  />
															</c:when>
															<c:when
																	test="${attendanceDto.noOfDays == 0}">
																<form:input
																		path="attendanceDtos[${row.index}].noOfDays"
																		name="attendanceDtos[${row.index}].noOfDays"
																		id="noOfDays" cssStyle="text-align: right"
																		maxlength="3" cssClass="span1"
																		onkeyup="intOnly(this)"/>

															</c:when>
														</c:choose>
													</td>
													<td style="text-align: center">
														<c:choose>
															<c:when
																	test="${attendanceDto.daysPresent != 0}">
																${attendanceDto.daysPresent }

																<form:hidden
																		path="attendanceDtos[${row.index}].daysPresent"
																		name="attendanceDtos[${row.index}].daysPresent"  />
															</c:when>
															<c:when
																	test="${attendanceDto.daysPresent == 0}">
																<form:input path="attendanceDtos[${row.index}].daysPresent"
																			name="attendanceDtos[${row.index}].daysPresent"
																			id="daysPresent" cssStyle="text-align: right"
																			maxlength="3" cssClass="span1"
																			onkeyup="intOnly(this)"/>

															</c:when>
														</c:choose>
													</td>
													<td>
														<c:if
																test="${attendanceDto.studentAttendanceId gt 0}">

															<a href="JavaScript:void(0);"
															   style="color: mediumblue"
															   onclick="submit('${Role}/student/studentAttendance/edit/${attendanceDto.studentAttendanceId}/update','Get')">Edit</a>
															</a>
															<span style="padding: 10px"></span>

															<a href="JavaScript:void(0);" style="color: red"
															   onclick="submit('${Role}/student/studentAttendance/edit/${attendanceDto.studentAttendanceId}/delete','Get')">Delete</a>

															</a>
														</c:if>
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>

									<button type="submit" class="btn btn-primary"
											onclick="submitData('${Role}/student/studentAttendance/add','studentAttendanceForm')">
										Post Attendance
									</button>
								</c:if>
							</div>
						</div>
					</div>
				</form:form>
			</c:if>

		</div>
	</section>
</body>
</html>