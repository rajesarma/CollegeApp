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
		var openFile = function(event) {
			var input = event.target;
			var output = document.getElementById('photoData');

			var reader = new FileReader();
			reader.onload = function() {
				output.src = reader.result;
				output.width = 80;
				output.height = 90;
			};
			reader.readAsDataURL(input.files[0]);
		};

		function checkSelection(obj, message) {
			//alert(obj.value)
			var id = obj.id;
			var e = document.getElementById(obj.id);
			var optionSelIndex = e.options[e.selectedIndex].value;
			var optionSelectedText = e.options[e.selectedIndex].text;

			if (optionSelIndex == 0)
			{
				alert("Please select " + message);
				document.getElementById(id).focus();
				document.getElementById(id).value = obj.value;
				return false;
			}
		}

		function submitData(action) {
			var employee = document.getElementById('employee');
			employee.action = action;
			employee.method = "post";
			employee.submit();
		}

		function checkData(type,obj, targetId) {
			if(obj.value.length > 0) {
				var url = '${Role}/employee/' + type + '/' + obj.value;
				var message;
				$.ajax( {
					type: "GET",
					url:url,
					cache: false,
					success: function(response) {
						$("#wait").html("");
						var response = jQuery.parseJSON(response);
						var valueExists = response.valueExists;
						if(valueExists == "true")
						{
							var message = response.message;
							$("#wait").html("<center style='font-size: 14px' ><span id='loading' style='font-size: 14px; color:red'><i class='fa fa-spinner fa-spin' style='font-size:24px'></i> <b>" + message + "</b></span></center>");
							document.getElementById(targetId).value = "";
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
								<spring:message code="employee.add"/>
							</h3>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>

	<section id="maincontent">
		<div class="container">

			<form:form action="${Role}/employee/add" id="employee"
					   modelAttribute="employeeForm" enctype="multipart/form-data"
					   cssClass="form-horizontal">
			<form:hidden path="employeeId" name="employeeId" id="employeeId" />

			<%--<aside>--%>
			<div class="row ">
				<div class="span12">
					<div class="err-message" style="text-align:center"> ${message}</div>
					<div id="wait" class="err-message"></div>
				</div>

				<div class="span6">
					<div class="centered">
								<%--<a style="color: green; text-decoration: underline;" href="${Role}/employee/list">Back to employee Report</a>--%>

						<div class="control-group">
							<label class="control-label align-left" for="name">
								<spring:message code="employee.fullName"/>
							</label>

							<div class="controls">
								<form:input path="name" name="name" id="name"
											cssClass="span3"
											onkeyup="charOnly(this)"
								/>
								<span class="help-block">
									<form:errors path="name" cssClass="error" />
								</span>
							</div>
						</div>

						<div class="control-group">

							<label class="control-label align-left" for="fatherName">
								<spring:message code="employee.fatherName"/>
							</label>
							<div class="controls">
								<form:input path="fatherName" name="fatherName" id="fatherName"
											cssClass="span3" maxlength="50"
											onkeyup="charOnly(this)" />
								<span class="help-block">
									<form:errors path="fatherName" cssClass="error" />
								</span>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label align-left" for="mobile">
								<spring:message code="employee.mobile"/>
							</label>
							<div class="controls">
								<form:input path="mobile" name="mobile" id="mobile"
											cssClass="span3" maxlength="10"
											onkeyup="intOnly(this)"
											onChange="checkData('mobileNo.', this, this.id)"/>
								<span class="help-block">
									<form:errors path="mobile" cssClass="error" />
								</span>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label align-left" for="aadharNo">
								<spring:message code="employee.aadharNo"/>
							</label>
							<div class="controls">
								<form:input path="aadharNo" name="aadharNo"
											id="aadharNo" maxlength="12"
											cssClass="span3" onkeyup="intOnly(this)"
											onChange="checkData('aadharNo.', this, this.id)"/>
								<span class="help-block">
									<form:errors path="aadharNo" cssClass="error" />
								</span>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label align-left" for="empNo">
								<spring:message code="employee.empNo"/>
							</label>
							<div class="controls">
								<form:input path="empNo" name="empNo" id="empNo"
											cssClass="span3" maxlength="10"
											onkeypress="javascript:return isAlphaNumeric(event,this.value);"
											onChange="checkData('empNo.', this, this.id)" />
								<span class="help-block">
									<form:errors path="empNo" cssClass="error" />
								</span>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label align-left" for="email">
								<spring:message code="employee.eMail"/>
							</label>
							<div class="controls">
								<form:input path="email" name="email" id="email"
											maxlength="50"
											cssClass="span3" onblur="checkEmail(this);"
											onChange="checkData('email', this, this.id)"/>
								<span class="help-block">
									<form:errors path="email" cssClass="error" />
								</span>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label align-left" for="dob">
								<spring:message code="employee.dob"/>
							</label>
							<div class="controls">
								<form:input path="dob" name="dob" id="dob" maxlength="10"
											cssClass="span2"
											onkeyup="buildDate(this)" onblur="isValidDate(this);"/>
								<span class="help-inline date-format">
									<spring:message code="dateFormat"/>
								</span>
								<span class="help-block">
									<form:errors path="dob" cssClass="error" />
								</span>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label align-left" for="doj">
								<spring:message code="employee.doj"/>
							</label>
							<div class="controls">
								<form:input path="doj" name="doj" id="doj" maxlength="10"
											cssClass="span2"
											onkeyup="buildDate(this)" onblur="isValidDate(this);"/>
								<span class="help-inline date-format">
									<spring:message code="dateFormat"/>
								</span>
								<span class="help-block">
									<form:errors path="doj" cssClass="error" />
								</span>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label align-left" for="email">
								<spring:message code="employee.photo"/>
							</label>
							<div class="controls">
								<input type="file" name="image" id="image"
									   cssClass="span3" onchange="openFile(event)" />

								<%--<input type="file" name="imageBkp" id="imageBkp"
									   cssClass="span3" onchange="openFile(event)" />--%>

								<form:hidden path="photoData" name="photoData" id="photoData" />

								<div id="applicantPhotoName" ></div>
								<span class="help-block">
									<form:errors path="image" cssClass="error" />
								</span>
								<span class="help-block">
									<c:if test="${empty photoData}">
											<img src="data:image;base64,${photoData }" id="photoData"
												 style='border: 1px solid black; visibility: collapse'
												 width='80px' class="img-polaroid"
												 height='80px'>
									</c:if>
									<c:if test="${not empty photoData}">
										<img src="data:image;base64,${photoData }" id="photoData"
											 style='border: 1px solid black; visibility: visible'
											 width='80px' class="img-polaroid"
											 height='80px'>
									</c:if>
								</span>
							</div>
						</div>

					</div>
				</div>
				<%--First Box--%>

				<%--Second Box--%>
				<div class="span6">
					<div class="centered">
						<%--<a style="color: green; text-decoration: underline;" href="${Role}/employee/list">Back to employee Report</a>--%>

							<div class="control-group">
								<label class="control-label align-left" for="deptId">
									<spring:message code="employee.department"/>
								</label>

								<div class="controls">
									<form:select path="deptId" name="deptId" id="deptId"
												 multiple="false"
												 cssClass="span3"
												 onchange="checkSelection(this, 'Department')">
										<form:option value="0" label="Select" />
										<form:options items="${departments}" />
									</form:select>
									<span class="help-block">
									<form:errors path="deptId" cssClass="error" />
								</span>
								</div>
							</div>

							<div class="control-group">

								<label class="control-label align-left" for="qlyId">
									<spring:message code="employee.qualification"/>
								</label>
								<div class="controls">
									<form:select path="qlyId" name="qlyId"
												 id="qlyId" multiple="false"
												 cssClass="span3"
												 onchange="checkSelection(this, 'Qualification')">
										<form:option value="0" label="Select" />
										<form:options items="${qualifications}"
										/>
									</form:select>
									<span class="help-block">
									<form:errors path="qlyId" cssClass="error" />
								</span>
								</div>
							</div>

							<div class="control-group">

								<label class="control-label align-left" for="joiningAcademicYearId">
									<spring:message code="employee.joiningAcademicYear"/>
								</label>
								<div class="controls">
									<form:select path="joiningAcademicYearId" name="joiningAcademicYearId" id="joiningAcademicYearId"
												 multiple="false"
												 cssClass="span3">
										<form:option value="0" label="Select" />
										<form:options items="${academicYears}" />
									</form:select>
									<span class="help-block">
									<form:errors path="joiningAcademicYearId" cssClass="error" />
								</span>
								</div>
							</div>

							<div class="control-group">

								<label class="control-label align-left" for="joiningSemesterId">
									<spring:message code="employee.joiningSemester"/>
								</label>
								<div class="controls">
									<form:select path="joiningSemesterId" name="joiningSemesterId" id="joiningSemesterId"
												 multiple="false"
												 cssClass="span3">
										<form:option value="0" label="Select" />
										<form:options items="${semesters}" />
									</form:select>
									<span class="help-block">
									<form:errors path="joiningSemesterId" cssClass="error" />
								</span>
								</div>
							</div>

							<div class="control-group">
								<label class="control-label align-left" for="bloodGroupId">
									<spring:message code="employee.bloodGroup"/>
								</label>
								<div class="controls">
									<form:select path="bloodGroupId" name="bloodGroupId" id="bloodGroupId"
												 multiple="false"
												 cssClass="span3"
												 onchange="checkSelection(this, 'Blood Group')">
										<form:option value="0" label="Select" />
										<form:options items="${bloodGroups}" />
									</form:select>
									<span class="help-block">
									<form:errors path="bloodGroupId" cssClass="error" />
								</span>
								</div>
							</div>

							<div class="control-group">
								<label class="control-label align-left" >
									<spring:message code="employee.gender"/>
								</label>
								<div class="controls">

									<label class="radio inline" for="gender">
										<form:radiobutton path="gender" value="MALE"
													  id="gender"/> Male
									</label>
									<label class="radio inline" for="gender">
										<form:radiobutton path="gender" value="FEMALE"
													  id="gender"/> Female
									</label>
									<span class="help-block">
									<form:errors path="gender" cssClass="error" />
								</span>
								</div>
							</div>

							<div class="control-group">
								<label class="control-label align-left" for="salary">
									<spring:message code="employee.salary"/>
								</label>
								<div class="controls">
									<form:input path="salary" name="salary" id="salary"
												onkeyup="intOnly(this)"
												maxlength="6" cssClass="span3"/>
									<span class="help-block">
									<form:errors path="salary" cssClass="error" />
								</span>
								</div>
							</div>

							<div class="control-group">
								<label class="control-label align-left" >
									<spring:message code="employee.address"/>
								</label>
								<div class="controls">

									<form:textarea path="address" name="address" id="address"
												   cssClass="span3" rows="3"
												   cssStyle="height: 100px"/>
									<span class="help-block">
									<form:errors path="address" cssClass="error" />
								</span>
								</div>
							</div>
					</div>
				</div>

				<div class="span12">
					<div align="center" style="width: 100%;">
						<input type="button" class="btn btn-primary" value="${buttonValue }"
							   onclick="submitData('${action}')" />
					</div>
				</div>
			</div>
			</form:form>
		</div>
	</section>
</body>
</html>