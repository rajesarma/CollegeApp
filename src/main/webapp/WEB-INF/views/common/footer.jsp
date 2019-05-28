<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
		 pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>

<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Menu</title>

</head>
<body bgcolor="white">

	<div id="loading" class="loading">
		<div class="loading-div">
			<div id="cssload-loader" >
				<ul>
					<li></li><li></li><li></li><li></li><li></li><li></li><li></li><li></li>
				</ul>
			</div>
			<div style="padding-top: 10px">
				<b>Loading... </b>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		buildMenu('menu', eval('${servicesMenu}'));
	</script>

	<script type="text/javascript">
		$(function()
		{
			$('#main-menu').smartmenus({
				mainMenuSubOffsetX: -1,
				mainMenuSubOffsetY: 4,
				subMenusSubOffsetX: 6,
				subMenusSubOffsetY: -6
			});
		});
	</script>


</body>
</html>