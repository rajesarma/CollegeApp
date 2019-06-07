<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Student Marks</title>

	<script>

		function submitData(action, formId) {
			var student = document.getElementById(formId);
			student.action = action;
			student.method = "post";
			student.submit();
		}

		function submit(action, method) {
			var form = document.getElementById('studentMarksForm');
			form.action = action;
			form.method = method;
			form.submit();
		}

		function resetData(semesterId, branchId, subjectId, examTypeId) {
			$("#"+semesterId).val('0');
			$("#"+branchId).val('0');
			$("#"+subjectId).val('0');
			$("#"+examTypeId).val('0');
		}

		function getData(type, obj1, obj2, targetId) {

			var value1 = $("#"+obj1).val();
			var value2 = 0 ;
			if(obj2 != 0) {
				value2 = $("#" + obj2).val();
			}

			if(value1 > 0) {

				var url = '${Role}/student/studentMarks/' + type + '/' + value1 + '/' +value2;
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
									$("#"+targetId).append($('<option></option>').val(val.trim()).html(text));
								});
							}
						} else if(type == "getSubjects") {

							$("#examTypeId").val('0');

							if(response.subjectsExists == "true")
							{
								var subjects = response.subjects.replace("{","").replace("}","");
								var subjectsArr = subjects.split(",");
								var msg ="";
								for(var index =0; index < subjectsArr.length; index++) {
									msg = msg + "\""+subjectsArr[index].replace(/=/g, "\": \"")+"\",";
								}
								var subjectOptions = JSON.parse('{'+msg.substring(0,msg.length-1)+'}');

								$("#"+targetId).empty().append('<option selected="selected" value="0">Select</option>');

								$.each(subjectOptions, function(val, text) {
									$("#"+targetId).append($('<option></option>').val(val.trim()).html(text));
								});
							}
						} else if(type == "getExamTypes") {

							if(response.examTypesExists == "true")
							{
								var examTypes = response.examTypes.replace("{","").replace("}","");
								var examTypesArr = examTypes.split(",");
								var msg ="";
								for(var index =0; index < examTypesArr.length; index++) {
									msg = msg + "\""+examTypesArr[index].replace(/=/g, "\": \"")+"\",";
								}
								var examTypesOptions = JSON.parse('{'+msg.substring(0,msg.length-1)+'}');

								$("#"+targetId).empty().append('<option selected="selected" value="0">Select</option>');

								$.each(examTypesOptions, function(val, text) {
									$("#"+targetId).append($('<option></option>').val(val.trim()).html(text));
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
								<spring:message code="student.marks"/>
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
								   modelAttribute="studentMarksDto">

							<c:if test="${showTab eq 'edit'}">
								<table class="table " id="studentList" >
									<thead>
										<th scope="row">Batch</th>
										<th scope="row">Branch</th>
										<th scope="row">Year</th>
										<th scope="row">Semester</th>
										<th scope="row">Subject</th>
										<th scope="row">Exam Type</th>
										<th scope="row">Roll No</th>
										<th scope="row">Name</th>
										<th scope="row">No. of Days</th>
										<th scope="row">Days Present</th>
										<th scope="row">Action</th>
									</thead>
									<tbody>
										<tr>
											<td>
												<form:hidden path="studentMarksId"
															 name="studentMarksId" />

													${studentMarksDto.batch }
												<form:hidden path="batchId"
															 name="batchId" />

												<form:hidden path="batch" name="batch" />
											</td>
											<td>${studentMarksDto.branch }
												<form:hidden path="branchId"
															 name="branchId" />
												<form:hidden path="branch"
															 name="branch" />
											</td>
											<td>${studentMarksDto.year }
												<form:hidden path="yearId"
															 name="yearId" />
												<form:hidden path="year" name="year" />
											</td>
											<td>${studentMarksDto.semester }
												<form:hidden path="semesterId"
															 name="semesterId"  />
												<form:hidden path="semester"
															 name="semester"  />
											</td>

											<td>${studentMarksDto.subject }
												<form:hidden path="subjectId"
															 name="subjectId"  />
												<form:hidden path="subjectId" name="subjectId"  />
											</td>

											<td>${studentMarksDto.examType }
												<form:hidden path="examTypeId"
															 name="examTypeId"  />
												<form:hidden path="examTypeId" name="examTypeId"  />
											</td>

											<td>${studentMarksDto.rollNo }
												<form:hidden path="rollNo"
															 name="rollNo" />
											</td>

											<td>${studentMarksDto.name }
												<form:hidden path="studentId"
															 name="studentId"  />
												<form:hidden path="name" name="name"  />
											</td>

											<td style="text-align: center">
												<form:input path="maxMarks"
															name="maxMarks"
															id="maxMarks"
															cssStyle="text-align: right"
															maxlength="3" cssClass="span1"
															onkeyup="intOnly(this)"/>
											</td>
											<td style="text-align: center">
												<form:input path="marks"
															name="marks"
															id="marks"
															cssStyle="text-align: right"
															maxlength="3" cssClass="span1"
															onkeyup="intOnly(this)"/>
											</td>
											<td>
												<button type="submit"
														class="btn btn-primary" onclick="submitData('${Role}/student/studentMarks${operation}','studentListForm')">
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
											 multiple="false" cssClass="span1"
											 onChange="resetData(semesterId.id, branchId.id, subjectId.id, examTypeId.id)">
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

								<label class="control-label align-left" for="yearId">
									<spring:message code="student.year"/>
								</label>

								<form:select path="yearId" name="yearId" id="yearId"
											 multiple="false" cssClass="span1"
											 onChange="getData('getSemesters', this.id, 0, semesterId.id)">
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
											 multiple="false" cssClass="span1"
											 onChange="getData('getSubjects', branchId.id, semesterId.id, subjectId.id)">
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
									<spring:message code="student.branch"/>
								</label>

								<form:select path="branchId" name="branchId" id="branchId"
											 multiple="false" cssClass="span2"
											 onChange="getData('getSubjects', branchId.id, semesterId.id, subjectId.id)">
									<form:option value="0" label="Select" />

									<c:forEach items="${branches}" var="branch">
										<option value="${branch.key}"
												<c:if test="${branch.key eq selectedBranchId}">selected="true"</c:if>
										> ${branch.value}
										</option>
									</c:forEach>

								</form:select>


								<span style="padding: 10px"></span>

								<label class="control-label align-left" for="subjectId">
									<spring:message code="exam.subject"/>
								</label>

								<form:select path="subjectId" name="subjectId" id="subjectId"
											 multiple="false" cssClass="span2"
											 onChange="getData('getExamTypes', this.id, 0, examTypeId.id)">
									<form:option value="0" label="Select" />
									<c:forEach items="${subjects}" var="subject">
										<option value="${subject.key}"
												<c:if
														test="${subject.key eq
														selectedSubjectId}">selected="true"</c:if>
										> ${subject.value}
										</option>
									</c:forEach>

								</form:select>

								<label class="control-label align-left" for="examTypeId">
									<spring:message code="exam.examType"/>
								</label>

								<form:select path="examTypeId" name="examTypeId" id="examTypeId"
											 multiple="false" cssClass="span2" >
									<form:option value="0" label="Select" />
									<%--<form:options items="${examTypes}" />--%>

									<c:forEach items="${examTypes}" var="examType">
										<option value="${examType.key}"
												<c:if
														test="${examType.key eq
														selectedExamTypeId}">selected="true"</c:if>
										> ${examType.value}
										</option>
									</c:forEach>

								</form:select>

								<span style="padding: 10px"></span>

								<button type="submit" class="btn btn-primary"
										onclick="submitData('${Role}/student/studentMarks${operation}','studentListForm')">
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

			<c:if test="${not empty marksForm}">

				<form:form action="${marksAction}" method="${method}"
						   cssClass="form-inline"
						   id="studentMarksForm"
						   modelAttribute="marksForm">

					<div class="row ">
						<div class="span12">
							<div class="centered">

								<form:hidden path="batchId" name="batchId" />
								<form:hidden path="branchId" name="branchId" />
								<form:hidden path="yearId" name="yearId" />
								<form:hidden path="semesterId" name="semesterId" />
								<form:hidden path="subjectId" name="subjectId" />
								<form:hidden path="examTypeId" name="examTypeId" />

								<c:if test="${not empty marksForm.marksDtos}">
									<table class="table " id="studentList" >
										<thead>
											<th>S. No.</th>
											<th scope="row">Batch</th>
											<th scope="row">Branch</th>
											<th scope="row">Year</th>
											<th scope="row">Semester</th>
											<th scope="row">Subject</th>
											<th scope="row">Exam Type</th>
											<th scope="row">Roll No</th>
											<th scope="row">Name</th>
											<th scope="row">Max Marks</th>
											<th scope="row">Marks</th>
											<th scope="row">Action</th>
										</thead>
										<tbody>

											<c:forEach items="${marksForm.marksDtos}" var="marksDto" varStatus="row">
												<tr>
													<td>${row.index+1}
														<form:hidden
																path="marksDtos[${row.index}].studentMarksId"
																name="marksDtos[${row.index}].studentMarksId" />
													</td>
													<td>${marksDto.batch }
														<form:hidden
														path="marksDtos[${row.index}].batchId"
														name="marksDtos[${row.index}].batchId" />
													</td>
													<td>${marksDto.branch }
														<form:hidden
																path="marksDtos[${row.index}].branchId"
																name="marksDtos[${row.index}].branchId" />
													</td>
													<td>${marksDto.year }
														<form:hidden
																path="marksDtos[${row.index}].yearId"
																name="marksDtos[${row.index}].yearId" />
													</td>
													<td>${marksDto.semester }
														<form:hidden
																path="marksDtos[${row.index}].semesterId"
																name="marksDtos[${row.index}].semesterId"  />
													</td>
													<td>${marksDto.subject}
														<form:hidden
																path="marksDtos[${row.index}].subjectId"
																name="marksDtos[${row.index}].subjectId"  />
													</td>
													<td>${marksDto.examType }
														<form:hidden
																path="marksDtos[${row.index}].examTypeId"
																name="marksDtos[${row.index}].examTypeId"  />
													</td>
													<td>${marksDto.rollNo }
														<form:hidden
																path="marksDtos[${row.index}].rollNo"
																name="marksDtos[${row.index}].rollNo" />
													</td>
													<td>${marksDto.name }
														<form:hidden
																path="marksDtos[${row.index}].studentId"
																name="marksDtos[${row.index}].studentId"  />
													</td>
													<td style="text-align: center">
														<c:choose>
															<c:when
																	test="${marksDto.maxMarks != 0}">
																${marksDto.maxMarks }

																<form:hidden
																		path="marksDtos[${row.index}].maxMarks"
																		name="marksDtos[${row.index}].maxMarks"  />
															</c:when>
															<c:when
																	test="${marksDto.maxMarks == 0}">
																<form:input path="marksDtos[${row.index}].maxMarks"
																			name="marksDtos[${row.index}].maxMarks"
																			id="maxMarks" cssStyle="text-align: right"
																			maxlength="3" cssClass="span1"
																			onkeyup="intOnly(this)"/>
															</c:when>
														</c:choose>
													</td>
													<td style="text-align: center">
														<c:choose>
															<c:when
																	test="${marksDto.marks != 0}">
																${marksDto.marks }

																<form:hidden
																		path="marksDtos[${row.index}].marks"
																		name="marksDtos[${row.index}].marks"  />
															</c:when>
															<c:when
																	test="${marksDto.marks == 0}">
																<form:input
																		path="marksDtos[${row.index}].marks"
																		name="marksDtos[${row.index}].marks"
																		id="marks" cssStyle="text-align: right"
																		maxlength="3" cssClass="span1"
																		onkeyup="intOnly(this)"/>

															</c:when>
														</c:choose>
													</td>
													<td>
														<c:if
																test="${marksDto.studentMarksId gt 0}">

															<a href="JavaScript:void(0);"
															   style="color: mediumblue"
															   onclick="submit('${Role}/student/studentMarks/edit/${marksDto.studentMarksId}/update','Get')">Edit</a>
															</a>
															<span style="padding: 10px"></span>

															<a href="JavaScript:void(0);" style="color: red"
															   onclick="submit('${Role}/student/studentMarks/edit/${marksDto.studentMarksId}/delete','Get')">Delete</a>

															</a>
														</c:if>
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>

									<button type="submit" class="btn btn-primary"
											onclick="submitData('${Role}/student/studentMarks/add','studentMarksForm')">
										Post Marks
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