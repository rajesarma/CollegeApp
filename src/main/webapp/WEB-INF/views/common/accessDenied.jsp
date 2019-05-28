<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
		 pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Site</title>
</head>
<%--<body>
	Dear <strong>${user}</strong>, You are not authorized to perform this action.
	Click to back to <a href="/home">Home</a>
	or
	<a href="/logout">Logout</a>
</body>--%>

<body data-spy="scroll" data-target=".bs-docs-sidebar">
<header>
	<!-- Navbar
	================================================== -->
	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
				<!-- logo -->
				<a class="brand logo" href="/home"><img src="/img/logo.png" alt=""
				/></a>
				<!-- end logo -->
				<!-- top menu -->
				<div class="navigation pull-right">
					<nav>
						<ul class="sm sm-blue pull-left">
							<li>
								<a href="/">Login</a>
							</li>
							<li >
								<a href="/error">Error</a>
							</li>
							<li>
								<a href="/logout">Logout</a>
							</li>
						</ul>
					</nav>
				</div>
				<!-- end menu -->
			</div>
		</div>
	</div>
</header>
<!-- Subhead
================================================== -->
<section id="subintro">
	<div class="jumbotron subhead" id="overview">
		<div class="container">
			<div class="row">
				<div class="span12">
					<div class="centered">
						<h3 >403</h3>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<section id="maincontent">
	<div class="container">
		<div class="row">
			<div class="span12">
				<div class="centered">
					<h2 class="error">403</h2>
					<h3>Access Denied</h3>
					<p>
						Dear <strong>${user}</strong>, You are not authorized to perform this action.
						Click to back to <a href="/home">Home</a>
						or
						<a href="/logout">Logout</a>
					</p>
				</div>
			</div>
		</div>
	</div>
</section>
<!-- Footer
================================================== -->




</body>

</html>