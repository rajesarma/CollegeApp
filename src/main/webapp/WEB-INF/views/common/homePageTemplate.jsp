<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
		 pageEncoding="ISO-8859-1" %>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Site</title>

	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="StudentApp">
	<meta name="author" content="Lakshmi Rajeswara Sarma">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="expires" content="0" />
	<meta http-equiv="AUTOCOMPLETE" content="OFF" />
	<meta http-equiv="keywords" content="Site"/>
	<META HTTP-EQUIV="Refresh" CONTENT="2699;URL=/logout">

	<link rel="stylesheet" type="text/css"  href="<c:url
	value="${pageContext.request.contextPath}/css/custom/index.css" />" />
	<%--rel="stylesheet"--%> <%--type="text/html"--%>

	<link rel="stylesheet" type="text/css"  href="<c:url
	value="${pageContext.request.contextPath}/css/menu.css" />"/>

	<link rel="icon" href="<c:url value="${pageContext.request.contextPath}/images/my_logo.jpg" />" type="image/x-icon" />
	<link rel="shortcut icon" href="<c:url value="${pageContext.request.contextPath}/images/my_logo.jpg" />"
		  type="image/x-icon" />

	<%--<script src="<c:url value="${pageContext.request.contextPath}/js/custom/md5.js" />"></script>--%>

	<%--<link rel="stylesheet" href="<c:url value="${pageContext.request.contextPath}/webjars/bootstrap/4.1.1/css/bootstrap.css" />">
	<script src="<c:url value="${pageContext.request.contextPath}/webjars/jquery/3.3.1-1/jquery.min.js" />"></script>
	<script src="<c:url value="${pageContext.request.contextPath}/webjars/bootstrap/4.1.1/js/bootstrap.min.js" />"></script>--%>


	<link rel="stylesheet" href="<c:url value="${pageContext.request.contextPath}/css/bootstrap.css" />">
	<link href="<c:url value="${pageContext.request.contextPath}/css/bootstrap-responsive.css" />" rel="stylesheet">
	<link href="<c:url value="${pageContext.request.contextPath}/css/docs.css" />" rel="stylesheet">
	<link href="<c:url value="${pageContext.request.contextPath}/js/google-code-prettify/prettify.css" />" rel="stylesheet">
	<link href="<c:url value="${pageContext.request.contextPath}/css/style.css" />" rel="stylesheet">

	<link href="<c:url value="${pageContext.request.contextPath}/color/default.css" />" rel="stylesheet">

	<%--<script src="<c:url value="${pageContext.request.contextPath}/webjars/jquery/3.3.1-1/jquery.min.js" />"></script>
	<script src="<c:url value="${pageContext.request.contextPath}/webjars/bootstrap/4.1.1/js/bootstrap.min.js" />"></script>--%>

	<script
			src="<c:url value="${pageContext.request.contextPath}/js/jquery.min.js" />"></script>
	<script src="<c:url value="${pageContext.request.contextPath}/js/bootstrap.js" />"></script>

	<link href="<c:url value="${pageContext.request.contextPath}/css/style.css" />" rel="stylesheet">


</head>
<body data-spy="scroll" data-target=".bs-docs-sidebar">

		<tiles:insertAttribute name="body" />

</body>
</html>