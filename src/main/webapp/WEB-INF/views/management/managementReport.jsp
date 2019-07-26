<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Management Report</title>

</head>
<body>

	<section id="subintro">
		<div class="jumbotron subhead" id="overview">
			<div class="container">
				<div class="row">
					<div class="span12">
						<div class="centered">
							<h3>
								<spring:message code="management.report"/>
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

						<form:form action="/management/managementReport" method="GET" cssClass="form-inline" >
							<div class="row">
								<ul class="portfolio-area da-thumbs">
									<li class="portfolio-item2" data-id="id-0" data-type="ilustrator">
										<div class="span3">
											<div class="thumbnail">
												<div class="image-wrapp">
													<table class="table table-bordered " id="empQlyWise" >
														<thead>
														<tr>
															<th>Qualification</th>
															<th>#</th>
														</tr>
														</thead>
														<tbody>
														<c:forEach items="${empQlyWise }" var="empQly" varStatus="row">
															<tr>
																<td>${empQly.qly_name }</td>
																<td style="text-align: center">${empQly.emp_count }</td>
															</tr>
														</c:forEach>
														</tbody>
													</table>
												</div>
											</div>
										</div>
									</li>
									<li class="portfolio-item2" data-id="id-0" data-type="brand">
										<div class="span3">
											<div class="thumbnail">
												<div class="image-wrapp">
													<table class="table table-bordered " id="yearWise" >
														<thead>
														<tr>
															<th>Year</th>
															<th>#</th>
														</tr>
														</thead>
														<tbody>
														<c:forEach items="${yearWise }" var="year" varStatus="row">
															<tr>
																<td>${year.year }</td>
																<td style="text-align: center">${year.student_count }</td>
															</tr>
														</c:forEach>
														</tbody>
													</table>
												</div>
											</div>
										</div>
									</li>

									<li class="portfolio-item2" data-id="id-0" data-type="photo">
										<div class="span3">
											<div class="thumbnail">
												<div class="image-wrapp">
													<table class="table table-bordered " id="yearSemWise" >
														<thead>
														<tr>
															<th>Year</th>
															<th>Semester</th>
															<th>#</th>
														</tr>
														</thead>
														<tbody>
														<c:forEach items="${yearSemWise }" var="yearSem" varStatus="row">
															<tr>
																<td>${yearSem.year }</td>
																<td>${yearSem.semester }</td>
																<td style="text-align: center">${yearSem.student_count }</td>
															</tr>
														</c:forEach>
														</tbody>
													</table>
												</div>
											</div>
										</div>
									</li>
									<li class="portfolio-item2" data-id="id-0" data-type="web">
										<div class="span3">
											<div class="thumbnail">
												<div class="image-wrapp">
													<table class="table table-bordered " id="batchWise" >
														<thead>
															<tr>
																<th>Batch</th>
																<th>#</th>
															</tr>
														</thead>
														<tbody>
															<c:forEach items="${batchWise }" var="batch" varStatus="row">
																<tr>
																	<%--<td style="text-align: center">${row.index+1}</td>--%>
																	<td>${batch.batch }</td>
																	<td style="text-align: center">${batch.student_count }</td>
																</tr>
															</c:forEach>
														</tbody>
													</table>
												</div>
											</div>
										</div>
									</li>
									<li class="portfolio-item2" data-id="id-0" data-type="web">
										<div class="span3">
											<div class="thumbnail">
												<div class="image-wrapp">
													<table class="table table-bordered " id="branchWise" >
														<thead>
														<tr>
															<th>Branch</th>
															<th>#</th>
														</tr>
														</thead>
														<tbody>
														<c:forEach items="${branchWise }" var="branch" varStatus="row">
															<tr>
																	<%--<td style="text-align: center">${row.index+1}</td>--%>
																<td>${branch.branch_name }</td>
																<td style="text-align: center">${branch.student_count }</td>
															</tr>
														</c:forEach>
														</tbody>
													</table>
												</div>
											</div>
										</div>
									</li>


									<li class="portfolio-item2" data-id="id-0" data-type="graphic">
										<div class="span3">
											<div class="thumbnail">
												<div class="image-wrapp">
													<table class="table table-bordered " id="batchBranchWise" >
														<thead>
														<tr>
															<th>Batch</th>
															<th>Branch</th>
															<th>#</th>
														</tr>
														</thead>
														<tbody>
														<c:forEach items="${batchBranchWise }" var="batchBranch" varStatus="row">
															<tr>
																<td>${batchBranch.batch }</td>
																<td>${batchBranch.branch }</td>
																<td style="text-align: center">${batchBranch.student_count }</td>
															</tr>
														</c:forEach>
														</tbody>
													</table>
												</div>
											</div>
										</div>
									</li>
									<li class="portfolio-item2" data-id="id-0" data-type="graphic">
										<div class="span3">
											<div class="thumbnail">
												<div class="image-wrapp">
													<table class="table table-bordered " id="batchBranchYearWise" >
														<thead>
														<tr>
															<th>Batch</th>
															<th>Branch</th>
															<th>Year</th>
															<th>#</th>
														</tr>
														</thead>
														<tbody>
														<c:forEach items="${batchBranchYearWise }" var="batchBranchYear" varStatus="row">
															<tr>
																<td>${batchBranchYear.batch }</td>
																<td>${batchBranchYear.branch }</td>
																<td>${batchBranchYear.year }</td>
																<td style="text-align: center">${batchBranchYear.student_count }</td>
															</tr>
														</c:forEach>
														</tbody>
													</table>
												</div>
											</div>
										</div>
									</li>
									<li class="portfolio-item2" data-id="id-0" data-type="photo">
										<div class="span3">
											<div class="thumbnail">
												<div class="image-wrapp">
													<table class="table table-bordered " id="batchBranchYearSemWise" >
														<thead>
														<tr>
															<th>Batch</th>
															<th>Branch</th>
															<th>Year</th>
															<th>Semester</th>
															<th>#</th>
														</tr>
														</thead>
														<tbody>
														<c:forEach items="${batchBranchYearSemWise }" var="batchBranchYearSem" varStatus="row">
															<tr>
																<td>${batchBranchYearSem.batch }</td>
																<td>${batchBranchYearSem.branch }</td>
																<td>${batchBranchYearSem.year }</td>
																<td>${batchBranchYearSem.semester }</td>
																<td style="text-align: center">${batchBranchYearSem.student_count }</td>
															</tr>
														</c:forEach>
														</tbody>
													</table>
												</div>
											</div>
										</div>
									</li>

								</ul>
							</div>

						</form:form>
					</div>
				</div>
			</div>

		</div>
	</section>
</body>
</html>