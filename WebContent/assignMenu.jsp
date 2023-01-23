<%@page import="java.util.Iterator"%>
<%@page import="payroll.DAO.*"%>
<%@page import="payroll.Model.*"%>
<%@page import="java.util.ArrayList"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>&copy DTS3</title>
<link rel="stylesheet" href="css/screen.css" type="text/css"
	media="screen" title="default" />
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script type="text/javascript">
	var xmlhttp;
	var url = "";

	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest;
	} else //if(window.ActivXObject)
	{
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}

	function loadLists(obj) {
		var index = document.getElementById(obj).selectedIndex;
		if (index != 0) {
			var rid = document.getElementById(obj).value;
			url = "RoleServlet?action=loadLists&rid=" + rid;

			xmlhttp.onreadystatechange = callback;
			xmlhttp.open("GET", url, true);
			xmlhttp.send();
		}
	}

	function callback() {
		if (xmlhttp.readyState == 4) {
			var response = xmlhttp.responseText;
			var result = response.split("$");
			document.getElementById("notAssg").innerHTML = result[0];
			document.getElementById("assg").innerHTML = result[1];
		}
	}

	function move(direction) {
		var src = document.getElementById('notAssign');
		var trg = document.getElementById('assign');
		var tem;

		if (direction) {
			tem = src;
			src = trg;
			trg = tem;
		}

		var selected = []

		for ( var i in src.options) {
			if (src.options[i].selected) {
				trg.options.add(new Option(src.options[i].text,
						src.options[i].value));
				selected.unshift(i);
			}
		}

		for (i in selected)
			src.options.remove(selected[i]);
	}

	function selectAll() {
		var box = document.getElementById('assign');
		if (document.getElementById('role').value == 0) {

			return false;
		}
		var len = box.length;

		for ( var i = 0; i < len; i++) {
			document.getElementById('assign').options[i].selected = true;
		}
		return true;
	}

	function checkVal() {
		var flag = document.getElementById("check").value;
		if (flag == 1) {
			alert("Saved Successfully");
			window.location.href = "assignMenu.jsp";
		}
		if (flag == -1) {
			alert("Error in Saving Please Try Again");
			window.location.href = "assignMenu.jsp";
		}
		loadLists();
	}
</script>
<%
	String pageName = "assignMenu.jsp";
	try
	{
		ArrayList<String> urls = (ArrayList<String>)session.getAttribute("urls");
		if(!urls.contains(pageName))
		{
			response.sendRedirect("NotAvailable.jsp");
		}
	}
	catch(Exception e)
	{
		//response.sendRedirect("login.jsp?action=0");
	}

%>

<%
	RoleDAO RD = new RoleDAO();
	ArrayList<RoleBean> roleList = RD.getAllRole();
	int action = 0;
	try {
		action = Integer.parseInt(request.getParameter("action"));
	} catch (Exception e) {

	}
%>


<style type="text/css">
<!--
body,td,th {
	font-family: Georgia;
}
-->
</style>
</head>
<body onLoad="checkVal()" style="overflow: hidden;">
	<%@include file="mainHeader.jsp"%>
	<!--  start nav-outer-repeat................................................................................................. START -->
	<%@include file="subHeader.jsp"%>

	<!-- start content-outer ........................................................................................................................START -->
	<div id="content-outer" style="overflow-y:scroll; max-height:80%; ">
		<!-- start content -->
		<div id="content">

			<!--  start page-heading -->
			<div id="page-heading">
				<h1>Assign Menu</h1>
			</div>
			<!-- end page-heading -->

			<table border="0" width="100%" cellpadding="0" cellspacing="0"
				id="content-table">
				<tr>
					<th rowspan="3" class="sized"><img
						src="images/shared/side_shadowleft.jpg" width="20" height="300"
						alt="" /></th>
					<th class="topleft"></th>
					<td id="tbl-border-top">&nbsp;</td>
					<th class="topright"></th>
					<th rowspan="3" class="sized"><img
						src="images/shared/side_shadowright.jpg" width="20" height="300"
						alt="" /></th>
				</tr>
				<tr>
					<td id="tbl-border-left"></td>
					<td>
						<!--  start content-table-inner ...................................................................... START -->
						<div id="content-table-inner">

							<!--  start table-content  -->
							<div id="table-content">
								<form action="RoleServlet?action=aasignMenu" method="post"
									onSubmit="return selectAll()">
									<center>

										<table id="customers">
											<tr>
												<th>Select Role</th>
												<th><select id="role" name="role"
													onChange="loadLists('role')">
														<option value="0" selected="selected">Select</option>
														<%
															for (RoleBean role : roleList) {
														%>
														<option value="<%=role.getROLEID()%>"><%=role.getROLENAME()%></option>
														<%
															}
														%>
												</select></th>
											</tr>
										</table>

										<br />

										<table width="400" height="47" border="1" id="customers">
											<tr align="center" valign="middle">
												<td width="175" align="center">Copy From Role</td>
												<td width="50">&nbsp;</td>
												<td><select id="role1" name="role1"
													onChange="loadLists('role1')">
														<option value="0" selected="selected">Select</option>
														<%
															for (RoleBean role : roleList) {
														%>
														<option value="<%=role.getROLEID()%>"><%=role.getROLENAME()%></option>
														<%
															}
														%>
												</select></td>
											</tr>
											<tr align="center" valign="middle">
												<th width="175" align="center">Not Assigned</th>
												<th width="50">&nbsp;</th>
												<th width="175" align="center">Assigned</th>
											</tr>
											<tr>
												<td align="center"><span id="notAssg"></span></td>
												<td align="center" valign="middle"><input type="button"
													name="in" value=">>" onClick="move(0)" class="myButton"><br /> <input
													type="button" name="out" onClick="move(1)" class="myButton" value="<<">
												</td>
												<td align="center"><span id="assg"></span></td>
											</tr>
											<tr>
												<td colspan="3" align="center"><input type="submit"
													name="submit" value="Save" class="myButton"></td>
											</tr>
										</table>
										<p>&nbsp;</p>
									</center>
								</form>
							</div>

							<!--  end table-content  -->

							<div class="clear"></div>

						</div> <!--  end content-table-inner ............................................END  -->
					</td>
					<td id="tbl-border-right"></td>
				</tr>
				<tr>
					<th class="sized bottomleft"></th>
					<td id="tbl-border-bottom">&nbsp;</td>
					<th class="sized bottomright"></th>
				</tr>
			</table>
			<div class="clear">&nbsp;</div>

		</div>
		<!--  end content -->
		<div class="clear">&nbsp;</div>
	</div>
	<!--  end content-outer........................................................END -->

	<div class="clear">&nbsp;</div>

	<input type="hidden" name="check" id="check" value="<%=action%>">
</body>
</html>