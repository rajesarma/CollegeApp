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
	<style>
		@media (min-width: 1200px) {
			.container, .navbar-static-top .container, .navbar-fixed-top .container, .navbar-fixed-bottom .container {
				width: 100vw;
			}
		}

		.table th{ text-align: center; }

		@media only screen and (min-width: 480px) and (max-width: 767px) {
			.pp_pic_holder{ left: 50% !important; width: 400px !important;  margin-left: -200px !important; }
			div.pp_default .pp_content_container .pp_right{ padding-right: 21px !important; }
			.pp_content, #pp_full_res img{ width: 100% !important; height: 100% !important; }
			div.pp_default .pp_content_container .pp_details {margin-top: 20px !important; }
		}

		@media only screen and (max-width: 479px) {
			.pp_pic_holder{ left: 50% !important; width: 300px !important;  margin-left: -150px !important; }
			div.pp_default .pp_content_container .pp_right{ padding-right: 21px !important; }
			.pp_content, #pp_full_res img{ width: 100% !important; height: 100% !important; }
			div.pp_default .pp_content_container .pp_details {margin-top: 20px !important; }
		}

		div.ajax {
			width: 550px;
			height: 300px;
			overflow: auto;
		}

	</style>

	<script src="<c:url value="${pageContext.request.contextPath}/js/Highcharts-4.0.4/js/highcharts.js" />"></script>
	<script src="<c:url value="${pageContext.request.contextPath}/js/Highcharts-4.0.4/js/highcharts-3d.js" />"></script>
	<script src="<c:url value="${pageContext.request.contextPath}/js/Highcharts-4.0.4/js/highcharts-more.js" />"></script>

	<%--<script src="<c:url value="${pageContext.request.contextPath}/js/highcharts/highcharts.js" />"></script>
	<script src="<c:url value="${pageContext.request.contextPath}/js/highcharts/highcharts-3d.js" />"></script>
	<script src="<c:url value="${pageContext.request.contextPath}/js/highcharts/data.js" />"></script>
	<script src="<c:url value="${pageContext.request.contextPath}/js/highcharts/highcharts-more.js" />"></script>
	<script src="<c:url value="${pageContext.request.contextPath}/js/highcharts/series-label.js" />"></script>
	<script src="<c:url value="${pageContext.request.contextPath}/js/highcharts/exporting.js" />"></script>
	<script src="<c:url value="${pageContext.request.contextPath}/js/highcharts/export-data.js" />"></script>--%>

</head>
<body>

	<header>
		<div class="navbar navbar-fixed-top" style="border-bottom: 5px solid #94c045">
			<div class="navbar-inner">
				<div class="container">
					<!-- logo -->
					<a class="brand logo" href="/home"><img src="/img/logo.png" alt="" /></a>
					<!-- end logo -->
				</div>
			</div>
		</div>
	</header>

	<div>
		<ul class="breadcrumb notop pull-right">
			<li><a style="font-weight: bold" href="/logout">Logout</a>
				<%--<span class="divider">/</span>--%>
			</li>
		</ul>
	</div>

		<section id="maincontent">
			<div class="container">
				<div class="row">
					<div class="span3">
						<aside>
							<div class="widget">
								<h4>Branch Wise Data</h4>
								<ul class="cat ajax">

									<c:forEach items="${branchWiseCount }" var="student" varStatus="row">
										<li><a href="/management/managementDashboard/branchWiseData/${student.branch_id}?ajax=true" class="font-weight-bold text-uppercase ajax" rel="prettyPhoto[ajax]">${student.branch_name}(${student.count})</a></li>
									</c:forEach>

								</ul>
							</div>
							<span class="widget">
								<h4>Tabular Data</h4>

								<ul class="cat">
									<li><a href="#batchWiseTable" rel="prettyPhoto[inline]">Batch Wise Data</a></li>
									<li><a href="#branchWiseTable" rel="prettyPhoto[inline]">Branch Wise Data</a></li>
									<li><a href="#yearWiseTable" rel="prettyPhoto[inline]">Year Wise Data</a></li>
									<li><a href="#yearSemWiseTable" rel="prettyPhoto[inline]">Year Sem Wise Data</a></li>
									<li><a href="#batchBranchWiseTable" rel="prettyPhoto[inline]">Batch Branch Wise</a></li>
									<li><a href="#batchBranchYearWiseTable" rel="prettyPhoto[inline]">Batch Branch Year Wise</a></li>
									<li><a href="#batchBranchYearSemWiseTable" rel="prettyPhoto[inline]">Batch Branch Year Sem Wise</a></li>
									<li><a href="#empQlyWiseTable" rel="prettyPhoto[inline]">Employee Qualification Wise</a></li>
									<br>
								</ul>
							</span>
							<span class="widget">
								<h4>Recent Joinees</h4>

								<ul class="recent-posts">
									<c:forEach items="${recentStudents }" var="student" varStatus="row">
										<li>${student.student_name }
											<div class="clear">
											</div>
											<span class="date"><i class="icon-calendar"></i>${student.doj}</span>
										</li>
										<br>
									</c:forEach>
								</ul>
							</span>
						</aside>
					</div>

					<div class="span9">
						<div class="container">
							<div class="row">
								<c:forEach items="${dataCount}" var="entry" varStatus="row">
									<div class="col-xl-2 col-md-6 mb-2">
										<div class="card border-left-serenity shadow">
											<div class="card-body">
												<div class="text-xs font-weight-bold text-uppercase
												mb-1">${entry.key.replaceAll("_"," ").replaceAll("\\d", "")}</div>
												<div class="h5 mb-0 font-weight-bold text-gray-800">${entry.value}</div>
											</div>
										</div>
									</div>
								</c:forEach>
							</div>
							<div class="row">
								<div class="col-xl-3 col-md-6 mb-3">
									<div class="card shadow">
										<div class="card-body">
											<div class="text-xs font-weight-bold text-uppercase mb-1 centered">Batch Wise Attendance Percentages</div>
											<c:forEach items="${batchWiseAttendances }" var="batch" varStatus="row">
												<span class="text-xs font-weight-bold">${batch.batch_name}</span>
												<span class="text-xs pull-right font-weight-bold">${batch.att_avg}</span>
													<span class="progress">
													<span class="bar" style="width: ${batch.att_avg}%;"></span>
												</span>
											</c:forEach>

											<%--<div class="progress">
												<div class="bar bar-info" style="width: 35%;"></div>
												<div class="bar bar-success" style="width: 30%;"></div>
												<div class="bar bar-warning" style="width: 15%;"></div>
												<div class="bar bar-danger" style="width: 10%;"></div>
											</div>--%>
										</div>
									</div>

								</div>
							</div>

							<div class="row">
								<div class="row">
									<div class="span4">
										<div class="card shadow">
											<div class="card-body">
												<div id="empWiseChart"></div>
											</div>
										</div>
									</div>
									<div class="span4">
										<div class="card shadow">
											<div class="card-body">
												<div id="batchWiseChart"></div>
											</div>
										</div>
									</div>
									<div class="span4">
										<div class="card shadow">
											<div class="card-body">
												<div id="branchWiseChart"></div>
											</div>
										</div>
									</div>
									<div class="span4">
										<div class="card shadow">
											<div class="card-body">
												<div id="yearWiseChart"></div>
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="span6">
										<div class="card shadow">
											<div class="card-body">
												<div id="batchBranchWiseChart"></div>
											</div>
										</div>
									</div>
									<div class="span6">
										<div class="card shadow">
											<div class="card-body">
												<div id="batchBranchYearWiseChart"></div>
											</div>
										</div>
									</div>
								</div>
								<div class="row">

									<div class="span5">
										<div class="card shadow">
											<div class="card-body">
												<div id="yearSemWiseChart"></div>
											</div>
										</div>
									</div>
									<div class="span6">
										<div class="card shadow">
											<div class="card-body">
												<div id="batchBranchYearSemWiseChart"></div>
											</div>
										</div>
									</div>
								</div>

								<%--Table data--%>
								<div id="empQlyWiseTable" style="display:none;" >
									<h3 class="center">Employee Qualification Wise Data</h3>
									<table class="table table-bordered " >
										<thead>
										<tr>
											<th>Qualification</th>
											<th>Count</th>
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

								<div id="yearWiseTable" style="display:none;">
									<h3 class="center">Year Wise Students Data</h3>
									<table class="table table-bordered " >
										<thead>
										<tr>
											<th>Year</th>
											<th>Count</th>
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

								<div id="yearSemWiseTable" style="display:none;">
									<h3 class="center">Year Semester Wise Students Data</h3>
									<table class="table table-bordered " >
										<thead>
										<tr>
											<th>Year</th>
											<th>Semester</th>
											<th>Count</th>
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

								<div id="batchWiseTable" style="display:none;">
									<h3 class="center">Batch Wise Students Data</h3>
									<table class="table table-bordered " >
										<thead>
										<tr>
											<th>Batch</th>
											<th>Count</th>
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

								<div id="branchWiseTable" style="display:none;">
									<h3 class="center">Branch Wise Students Data</h3>
									<table class="table table-bordered " >
										<thead>
										<tr>
											<th>Branch</th>
											<th>Count</th>
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

								<div id="batchBranchWiseTable" style="display:none;">
									<h3 class="center">Batch Branch Wise Students Data</h3>
									<table class="table table-bordered " >
										<thead>
										<tr>
											<th>Batch</th>
											<th>Branch</th>
											<th>Count</th>
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

								<div id="batchBranchYearWiseTable" style="display:none;">
									<h3 class="center">Batch Branch Year Wise Students Data</h3>
									<table class="table table-bordered " >
										<thead>
										<tr>
											<th>Batch</th>
											<th>Branch</th>
											<th>Year</th>
											<th>Count</th>
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

								<div id="batchBranchYearSemWiseTable" style="display:none;">
									<h3 class="center">Batch Branch Year Sem Wise Students Data</h3>
									<table class="table table-bordered " >
										<thead>
											<tr>
												<th>Batch</th>
												<th>Branch</th>
												<th>Year</th>
												<th>Semester</th>
												<th>Count</th>
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
				</div>
			</div>
	</section>

	<%--</form:form>--%>
	<script>

		/*Highcharts.setOptions({
			// colors: ['#50B432', '#ED561B', '#DDDF00', '#24CBE5', '#64E572', '#FF9655', '#FFF263',      '#6AF9C4']
			// colors: ['#50B432', '#7cb5ec', '#434348', '#f7a35c', '#90ed7d', '#2b908f', '#f45b5b', '#91e8e1','#8085e9', '#f15c80', '#e4d354'],
			colors: ['#6baa01', '#008ee4','#9b59b6', '#ED561B', '#DDDF00', '#24CBE5', '#64E572', '#FF9655', '#FFF263', '#6AF9C4', '#34495e', '#50B432']
		});*/

		var colors = ['#6baa01', '#008ee4','#9b59b6', '#ED561B', '#DDDF00', '#24CBE5', '#64E572', '#FF9655', '#FFF263', '#6AF9C4', '#34495e', '#50B432'];

		// Highcharts.getOptions().colors = Highcharts.map(Highcharts.getOptions().colors, function (color) {
		Highcharts.getOptions().colors = Highcharts.map(colors, function (color) {
			return {
				radialGradient: {
					cx: 0.5,
					cy: 0.3,
					r: 0.7
				},
				stops: [
					[0, color],
					[1, Highcharts.Color(color).brighten(-0.3).get('rgb')] // darken
				]
			};
		});

		$("#empWiseChart").highcharts({
			chart: {
				type: 'column',
				options3d: {
					enabled: true,
					alpha: 15,
					beta: 0,
					depth: 50,
					viewDistance: 25
				}
			},
			title: {
				text: "<a href='#inline_demo2' rel='prettyPhoto[inline]'>Faculty Wise</a>"
			},
			xAxis: {
				categories: [
						<c:forEach items="${empQlyWise }" var="empQly" varStatus="row">
							'${empQly.qly_name }',
						</c:forEach>
						]
			},
			yAxis: {
				title: {
					text: 'Count'
				},
				labels: {
					formatter: function () {
						return this.value ;
					}
				}
			},
			tooltip: {
				crosshairs: true,
				shared: true
			},
			plotOptions: {
				spline: {
					marker: {
						radius: 4,
						lineColor: '#666666',
						lineWidth: 1
					}
				}
			},
			series: [{
				name: 'Qualification',
				marker: {
					symbol: 'square'
				},
				data: [
					<c:forEach items="${empQlyWise }" var="empQly" varStatus="row">
						${empQly.emp_count },
					</c:forEach>
				]
			}]
		});


		// Batch Wise
		$("#batchWiseChart").highcharts({
			chart: {
				type: 'column',
				options3d: {
					enabled: true,
					alpha: 15,
					beta: 0,
					depth: 50,
					viewDistance: 25
				}
			},
			title: {
				text: 'Batch wise Students'
			},
			xAxis: {
				categories: [
					<c:forEach items="${batchWise }" var="batch" varStatus="row">
					'${batch.batch }',
					</c:forEach>
				]
			},
			yAxis: {
				title: {
					text: 'Students Count'
				},
				labels: {
					formatter: function () {
						return this.value ;
					}
				}
			},
			tooltip: {
				crosshairs: true,
				shared: true
			},
			plotOptions: {
				spline: {
					marker: {
						radius: 4,
						lineColor: '#666666',
						lineWidth: 1
					}
				}
			},
			series: [{
				name: 'Batch',
				marker: {
					symbol: 'square'
				},
				data: [
					<c:forEach items="${batchWise }" var="batch" varStatus="row">
					${batch.student_count },
					</c:forEach>
				]
			}]
		});

		// Branch Wise
		$("#branchWiseChart").highcharts({
			chart: {
				type: 'column',
				options3d: {
					enabled: true,
					alpha: 15,
					beta: 0,
					depth: 50,
					viewDistance: 25
				}
			},
			title: {
				text: 'Branch wise Students'
			},
			xAxis: {
				categories: [
					<c:forEach items="${branchWise }" var="branch" varStatus="row">
					'${branch.branch_name }',
					</c:forEach>
				]
			},
			yAxis: {
				title: {
					text: 'Students Count'
				},
				labels: {
					formatter: function () {
						return this.value ;
					}
				}
			},
			tooltip: {
				crosshairs: true,
				shared: true
			},
			plotOptions: {
				spline: {
					marker: {
						radius: 4,
						lineColor: '#666666',
						lineWidth: 1
					}
				}
			},
			series: [{
				name: 'Branch',
				marker: {
					symbol: 'square'
				},
				data: [
					<c:forEach items="${branchWise }" var="branch" varStatus="row">
					${branch.student_count },
					</c:forEach>
				]
			}]
		});

		// Year Wise
		$("#yearWiseChart").highcharts({
			chart: {
				type: 'column',
				options3d: {
					enabled: true,
					alpha: 15,
					beta: 0,
					depth: 50,
					viewDistance: 25
				}
			},
			title: {
				text: 'Year wise Students'
			},
			xAxis: {
				categories: [
					<c:forEach items="${yearWise }" var="year" varStatus="row">
					'${year.year }',
					</c:forEach>
				]
			},
			yAxis: {
				title: {
					text: 'Students Count'
				},
				labels: {
					formatter: function () {
						return this.value ;
					}
				}
			},
			tooltip: {
				crosshairs: true,
				shared: true
			},
			plotOptions: {
				spline: {
					marker: {
						radius: 4,
						lineColor: '#666666',
						lineWidth: 1
					}
				}
			},
			series: [{
				name: 'Year',
				marker: {
					symbol: 'square'
				},
				data: [
					<c:forEach items="${yearWise }" var="year" varStatus="row">
					${year.student_count },
					</c:forEach>
				]
			}]
		});


		// Year Sem Wise
		$("#yearSemWiseChart").highcharts({

			chart: {
				type: 'column',
				/*options3d: {
					enabled: true,
					alpha: 15,
					beta: 0,
					depth: 100,
					viewDistance: 25
				},*/

				margin: 90,
				options3d: {
					enabled: true,
					alpha: 10,
					beta: 20,
					depth: 220,
					viewDistance: 25
				}
			},
			title: {
				text: 'Year Sem wise Students'
			},

			xAxis: {
				categories: [
					<c:forEach items="${yearSemWiseData}" var="entry" varStatus="row">
						<c:if test="${entry.key eq 'yearList'}">
							<c:forEach items="${entry.value}" var="year" varStatus="loop">
								'<c:out value = "${year}"/>' ${!loop.last ? ', ' : ''}
							</c:forEach>
						</c:if>
					</c:forEach>
				]
			},

			yAxis: {
				min: 0,
				title: {
					text: 'Count'
				},
				stackLabels: {
					enabled: true,
					style: {
						fontWeight: 'bold',
					}
				}
			},

			tooltip: {
				headerFormat: '<b>{point.x}</b><br/>',
				pointFormat: '{series.name}: {point.y}<br/>'
			},

			plotOptions: {
				column: {
					depth: 40,
					stacking: undefined,
					grouping: false,
					groupZPadding: 10,
					/*dataLabels: {
						enabled: true
					}*/
				}
			},

			series: [
				{
					name: 'I Sem',
					data: [
						<c:forEach items="${yearSemWiseData}" var="entry" varStatus="outer" >
							<c:if test="${entry.key eq 'sem1List'}">
								<c:forEach items="${entry.value}" var="sem" varStatus="loop">
									<c:out value = "${sem}"/> ${!loop.last ? ', ' : ''}
								</c:forEach>
							</c:if>
						</c:forEach>
					]
				}, {
					name: 'II Sem',
					data: [
						<c:forEach items="${yearSemWiseData}" var="entry" varStatus="outer" >
							<c:if test="${entry.key eq 'sem2List'}">
								<c:forEach items="${entry.value}" var="sem" varStatus="loop">
									<c:out value = "${sem}"/> ${!loop.last ? ', ' : ''}
								</c:forEach>
							</c:if>
						</c:forEach>
					]
				}
			]
		});


		// Batch Branch Wise
		$("#batchBranchWiseChart").highcharts({

			chart: {
				type: 'column',
				/*options3d: {
					enabled: true,
					alpha: 15,
					beta: 0,
					depth: 100,
					viewDistance: 25
				},*/

				margin: 90,
				options3d: {
					enabled: true,
					alpha: 10,
					beta: 20,
					depth: 220,
					viewDistance: 25
				}
			},

			title: {
				text: 'Batch Branch Wise'
			},
			xAxis: {
				categories: [
							<c:forEach items="${batches}" var="batch" varStatus="outer" >
								'<c:out value = "${batch}"/>' ${!loop.last ? ', ' : ''}
							</c:forEach>
							]
			},
			labels: {
				items: [{
					html: 'Batch Wise',
					style: {
						left: '360px',
						top: '-10px',
					}
				}]
			},
			plotOptions: {
				column: {
					depth: 40,
					stacking: undefined,
					grouping: false,
					groupZPadding: 10,
					/*dataLabels: {
						enabled: true
					}*/
				}
			},
			series: [
				<c:forEach items="${batchBranchWiseData}" var="entry" varStatus="outer">
					<c:if test="${entry.key eq 'branchWiseMap'}">
						<c:forEach items="${entry.value}" var="bb" varStatus="inner" >
						{
							type: 'column',
							name: '${bb.key}',
							data: ${bb.value}
							${!inner.last ? ', ' : ''}
						}${!outer.last ? ', ' : ''}
						</c:forEach>
					</c:if>
				</c:forEach>
			{
				type: 'pie',
				name: '',
				data: [
					<c:forEach items="${batchBranchWiseData}" var="entry" varStatus="outer">
						<c:if test="${entry.key eq 'batchWiseMap'}">
							<c:forEach items="${entry.value}" var="bb" varStatus="inner" >
								{
									name: '${bb.key}',
									y:${bb.value}${!inner.last ? ', ' : ''}
								},
							</c:forEach>
						</c:if>
					</c:forEach>
					],
				center: [380, 10],
				size: 50,
				showInLegend: false,
				dataLabels: {
					enabled: false
				}
			}]
		});

		// Batch Branch Year Wise
		$("#batchBranchYearWiseChart").highcharts({

			title: {
				text: 'Batch Branch Year Wise'
			},
			xAxis: {
				categories: [
					<c:forEach items="${batches}" var="batch" varStatus="outer" >
					'<c:out value = "${batch}"/>' ${!loop.last ? ', ' : ''}
					</c:forEach>
				]
			},
			labels: {
				items: [{
					html: 'Batch Wise',
					style: {
						left: '380px',
						top: '-10px',
					}
				}]
			},

			series: [
				<c:forEach items="${batchBranchYearWiseData}" var="entry" varStatus="outer">
					<c:if test="${entry.key eq 'branchWiseMap'}">
						<c:forEach items="${entry.value}" var="bb" varStatus="inner" >
							{
								type: 'column',
								name: '${bb.key}',
								data: ${bb.value}
								${!inner.last ? ', ' : ''}
							}${!outer.last ? ', ' : ''}
						</c:forEach>
					</c:if>
				</c:forEach>
				<c:forEach items="${batchBranchYearWiseData}" var="entry" varStatus="outer">
					<c:if test="${entry.key eq 'yearWiseMap'}">
						<c:forEach items="${entry.value}" var="bb" varStatus="inner" >
							{
								type: 'spline',
								name: '${bb.key}',
								data: ${bb.value}
								${!inner.last ? ', ' : ''}
							},${!outer.last ? ', ' : ''}
						</c:forEach>
					</c:if>
				</c:forEach>
				{
					type: 'pie',
					name: '',
					data: [
						<c:forEach items="${batchBranchYearWiseData}" var="entry" varStatus="outer">
							<c:if test="${entry.key eq 'batchWiseMap'}">
								<c:forEach items="${entry.value}" var="bb" varStatus="inner" >
									{
										name: '${bb.key}',
										y:${bb.value}${!inner.last ? ', ' : ''}
									},
								</c:forEach>
							</c:if>
						</c:forEach>
					],
					center: [400, 10],
					size: 50,
					showInLegend: false,
					dataLabels: {
						enabled: false
					}
				}]
		});

	</script>

	<script type="text/javascript" charset="utf-8">
		$(document).ready(function(){
			$("a[rel^='prettyPhoto']").prettyPhoto({
				animation_speed:'normal',
				theme:'light_rounded',
				// slideshow:3000,
				// autoplay_slideshow: true
			});

			$(".ajax a[rel^='prettyPhoto']").prettyPhoto(
				{
					animation_speed:'normal',
					show_title: true,
					theme:'light_rounded',
					animation_speed: 'fast',
					default_width: 550,
					// default_height: 500,
					// overlay_gallery: true,
					allow_resize: true,
				});
		});
	</script>


</body>


</html>