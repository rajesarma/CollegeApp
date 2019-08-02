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

	<%--<meta http-equiv="Refresh" content="5"; url="/logout"/>--%>
	<%--<META HTTP-EQUIV="Refresh" CONTENT="1800;URL=/expireSession">--%>

	<%-- For CSRF Tokens--%>
	<meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>

	<link rel="icon" href="<c:url value="${pageContext.request.contextPath}/images/my_logo.jpg" />" type="image/x-icon" />
	<link rel="stylesheet" href="<c:url
		value="${pageContext.request.contextPath}/css/custom/loading.css" />" type="text/css" />
	<link rel="shortcut icon" href="<c:url value="${pageContext.request.contextPath}/images/my_logo.jpg" />"
		  type="image/x-icon" />

	<%--<link rel="stylesheet" type="text/css"  href="<c:url
	value="${pageContext.request.contextPath}/css/custom/index.css" />" />--%>

	<%--<link rel="stylesheet" type="text/css"  href="<c:url
	value="${pageContext.request.contextPath}/css/custom/styles.css" />" />--%>

	<link rel="stylesheet" type="text/css"  href="<c:url
	value="${pageContext.request.contextPath}/css/menu.css" />"/>

	<%--<script src="${pageContext.request.contextPath}/js/custom/jquery-3.3.1.js"></script>--%>

	<%--<link
			href="${pageContext.request.contextPath}/webjars/bootstrap/4.1.1/scss/bootstrap.scss"
		  rel="stylesheet" media="screen"/>--%>

	<%--<link rel="stylesheet" href="<c:url value="${pageContext.request.contextPath}/webjars/bootstrap/4.1.1/css/bootstrap.css" />">--%>

	<%--<link rel="stylesheet" href="<c:url value="${pageContext.request.contextPath}/css/bootstrap.css" />">--%>

	<link rel="stylesheet" href="<c:url
	value="${pageContext.request.contextPath}/webjars/bootstrap/2.3.0/css/bootstrap.css" />">
	<link rel="stylesheet" href="<c:url value="${pageContext.request.contextPath}/css/dashboard.css" />">

	<link rel="stylesheet" href="<c:url value="${pageContext.request.contextPath}/css/highcharts/highcharts.css" />">

	<link href="<c:url value="${pageContext.request.contextPath}/css/bootstrap-responsive.css" />" rel="stylesheet">
	<link href="<c:url value="${pageContext.request.contextPath}/css/docs.css" />" rel="stylesheet">
	<link href="<c:url value="${pageContext.request.contextPath}/js/google-code-prettify/prettify.css" />" rel="stylesheet">
	<link href="<c:url value="${pageContext.request.contextPath}/css/style.css" />" rel="stylesheet">
	<link href="<c:url value="${pageContext.request.contextPath}/color/default.css" />" rel="stylesheet">

	<link href="<c:url value="${pageContext.request.contextPath}/css/sweetalert.min.css" />" rel="stylesheet">
	<link href="<c:url value="${pageContext.request.contextPath}/css/prettyPhoto.css" />" rel="stylesheet">

	<script src="<c:url
	value="${pageContext.request.contextPath}/webjars/jquery/1.8.2/jquery.min.js" />"></script>
	<script src="<c:url
	value="${pageContext.request.contextPath}/webjars/bootstrap/2.3.0/js/bootstrap.min.js" />"></script>

	<%--<script
			src="<c:url value="${pageContext.request.contextPath}/js/jquery.min.js" />"></script>
	<script src="<c:url value="${pageContext.request.contextPath}/js/bootstrap.js" />"></script>--%>

	<script src="<c:url value="${pageContext.request.contextPath}/js/menu.js"/>"></script>

	<script
			src="<c:url value="${pageContext.request.contextPath}/js/form_validations.js" />"></script>

	<script
			src="<c:url value="${pageContext.request.contextPath}/js/date_validations.js"/>"></script>

	<script
			src="<c:url value="${pageContext.request.contextPath}/js/sweetalert.min.js"/>"></script>

	<link rel="stylesheet" type="text/css"  href="<c:url
	value="${pageContext.request.contextPath}/css/custom/dataTables.jqueryui.min.css" />" />

	<script src="<c:url value="${pageContext.request.contextPath}/js/custom/jquery.dataTables.min.js" />"></script>

	<script>

		// For CSRF Tokens
		$(function () {
			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");
			$(document).ajaxSend(function (e, xhr, options) {
				xhr.setRequestHeader(header, token);
			});
		});


		if(window.history.length>0) {
			window.history.forward(4);
		}

		function clickIE4() {
			if (event.button==2) {
				return false;
			}
		}

		function clickOpera() {
			if (event.button==2) {
				return false;
			}
		}

		function clickNS4(e) {
			if (document.layers||document.getElementById&&!document.all) {
				if (e.which==2||e.which==3) {
					return false;
				}
			}
		}

		if (document.layers) {
			document.captureEvents(Event.MOUSEDOWN);
			document.onmousedown=clickNS4;
		} else if (document.all&&!document.getElementById) {
			document.onmousedown=clickIE4;
		} else if (navigator.appName=="Opera") {
			document.captureEvents(Event.MOUSEDOWN);
			document.onmousedown=clickOpera;
		}

		document.oncontextmenu=new Function("return false");

		function disableKey(event) {
			if (!event) event = window.event;
			if (!event) return;

			var keyCode = event.keyCode ? event.keyCode : event.charCode;

			if (event.charCode==116) {
				return true;
			} else if (keyCode == 116) {
				window.status = "F5 key detected! Attempting to disabling default response.";
				window.setTimeout("window.status='';", 2000);

				// Standard DOM (Mozilla):
				if (event.preventDefault) event.preventDefault();

				//IE (exclude Opera with !event.preventDefault):
				if (document.all && window.event && !event.preventDefault) {
					event.cancelBubble = true;
					event.returnValue = false;
					event.keyCode = 0;
				}

				return false;
			}
		}

		function setEventListener(eventListener) {
			if (document.addEventListener) document.addEventListener('keypress', eventListener, true);
			else if (document.attachEvent) document.attachEvent('onkeydown', eventListener);
			else document.onkeydown = eventListener;
		}

		function unsetEventListener(eventListener) {
			if (document.removeEventListener) document.removeEventListener('keypress', eventListener, true);
			else if (document.detachEvent) document.detachEvent('onkeydown', eventListener);
			else document.onkeydown = null;
		}

		setEventListener(disableKey)
		//unsetEventListener(disableKey)
	</script>

	<script type="text/javascript">
		$(function() {
			$("#loading").show().delay(400).fadeOut(600);
		});

		function loading() {
			$("#loading").show();
		}

	</script>

	<style>
		.loading-div {
			top: 50%;
			padding-top: 300px;
			text-align: center;
			font-size: 20px;
		}

		.mt-minus-20 {
			margin-top: -20px;
		}

		.min-ht-50 {
			min-height: 50px;
		}

		.min-ht-500 {
			min-height: 500px;
		}
	</style>

</head>
<body data-spy="scroll" data-target=".bs-docs-sidebar">


	<%--<div class="main-bg">--%>



		<%--<div class="page mt-minus-20">

				<div class="min-ht-50" style="background: #084B8A">  &lt;%&ndash;#1cc7d0&ndash;%&gt;
					<tiles:insertAttribute name="menu" />
				</div>
				<div class="min-ht-500 bg-white" > &lt;%&ndash;style="background: #cacaca"&ndash;%&gt;
					<br />
					<tiles:insertAttribute name="body" />
					<tiles:insertAttribute name="footer" />
				</div>
		</div>--%>


			<%--<tiles:insertAttribute name="menu" />--%>
		<%--<div class="min-ht-500 bg-white" > --%><%--style="background: #cacaca"--%>


			<tiles:insertAttribute name="body" />


			<tiles:insertAttribute name="footer" />
		<%--</div>--%>

	<%--</div>--%>

	<script src="<c:url value="${pageContext.request.contextPath}/js/jquery.easing.js" />"></script>
	<script src="<c:url value="${pageContext.request.contextPath}/js/google-code-prettify/prettify.js" />"></script>
	<script src="<c:url value="${pageContext.request.contextPath}/js/modernizr.js" />"></script>
	<script src="<c:url value="${pageContext.request.contextPath}/js/jquery.elastislide.js" />"></script>
	<script src="<c:url value="${pageContext.request.contextPath}/js/sequence/sequence.jquery-min.js" />"></script>
	<script src="<c:url value="${pageContext.request.contextPath}/js/sequence/setting.js" />"></script>
	<script src="<c:url value="${pageContext.request.contextPath}/js/jquery.prettyPhoto.js" />"></script>
	<script src="<c:url value="${pageContext.request.contextPath}/js/application.js" />"></script>
	<script src="<c:url value="${pageContext.request.contextPath}/js/jquery.flexslider.js" />"></script>
	<script src="<c:url value="${pageContext.request.contextPath}/js/hover/jquery-hover-effect.js" />"></script>
	<script src="<c:url value="${pageContext.request.contextPath}/js/hover/setting.js" />"></script>
	<script src="<c:url value="${pageContext.request.contextPath}/js/custom.js" />"></script>

</body>
</html>