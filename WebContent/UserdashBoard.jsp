<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="javax.servlet.http.HttpSession" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<title>User</title>
<script type="text/javascript">
function checkFlag()
{
	var flag = parseInt(document.getElementById("action").value);
	alert(flag);
	if(flag ==1)
	{
		alert("Check In Successfully");
	}
	else if(flag == 0)
	{
		alert("Error in Record Saving");
	}
	
}

</script>

</head>
<body onload="checkFlag()"> 
<%
HttpSession session2 = request.getSession();
session2.setAttribute("empno","6002");

%>
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>About Payroll.... </h1>
	</div>
	<!-- end page-heading -->

	<table border="0" width="100%" cellpadding="0" cellspacing="0" id="content-table">
	<tr>
		<th rowspan="3" class="sized"><img src="images/shared/side_shadowleft.jpg" width="20" height="300" alt="" /></th>
		<th class="topleft"></th>
		<td id="tbl-border-top">&nbsp;</td>
		<th class="topright"></th>
		<th rowspan="3" class="sized"><img src="images/shared/side_shadowright.jpg" width="20" height="300" alt="" /></th>
	</tr>
	<tr>
		<td id="tbl-border-left"></td>
		<td>
		<!--  start content-table-inner ...................................................................... START -->
		<div id="content-table-inner">
		
			<!--  start table-content  -->
			<div id="table-content">
	<table>
	 <tr>
		<td>
			<table width="244" height="146" style="background-color:#F5F5F5; -moz-border-radius: 15px; -webkit-border-radius: 15px;
		      border-radius: 15px;
		      behavior: url(border-radius.htc); ">
			    <tr  align="center">
			   	  <td valign="top" ><br>
			      <marquee hspace="center" height="200px" direction="up" onMouseOver="stop()" onMouseOut="start()" scrolldelay="100" >
					<ul style="font-size:14px ;"  >
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					</ul>
				</marquee>
			   </td>
			</tr>
		 </table>
		</td>
		<td width="100px"></td>
		<td>
			<table width="244" height="146" style="background-color:#F5F5F5; -moz-border-radius: 15px; -webkit-border-radius: 15px;
				border-radius: 15px;
				behavior: url(border-radius.htc); ">
			<tr  align="center">
			<td valign="top" ><br>
				<marquee hspace="center" height="200px" direction="up" onMouseOver="stop()" onMouseOut="start()" scrolldelay="100" >
					<ul style="font-size:14px ;"  >
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					</ul>
				</marquee>
			</td>
			</tr>
			</table>
			</td>
			<td width="100"></td>
			<td>
			<table width="244" height="146" style="background-color:#F5F5F5; -moz-border-radius: 15px; -webkit-border-radius: 15px;
				border-radius: 15px;
				behavior: url(border-radius.htc); ">
			<tr  align="center">
			<td valign="top" ><br>
				<marquee hspace="center" height="200px" direction="up" onMouseOver="stop()" onMouseOut="start()" scrolldelay="100" >
					<ul style="font-size:14px ;"  >
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					</ul>
				</marquee>
			</td>
			</tr>
			</table>
			
			</td>
			</tr>
			<tr>
			<td>
			<td height="50px">
			</td>
			
			</td>
			</tr>
			 <tr>
		<td>
			<table width="244" height="146" style="background-color:#F5F5F5; -moz-border-radius: 15px; -webkit-border-radius: 15px;
		      border-radius: 15px;
		      behavior: url(border-radius.htc); ">
			    <tr  align="center">
			   	  <td valign="top" ><br>
			      <marquee hspace="center" height="200px" direction="up" onMouseOver="stop()" onMouseOut="start()" scrolldelay="100" >
					<ul style="font-size:14px ;"  >
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					</ul>
				</marquee>
			   </td>
			</tr>
		 </table>
		</td>
		<td width="100px"></td>
		<td>
			<table width="244" height="146" style="background-color:#F5F5F5; -moz-border-radius: 15px; -webkit-border-radius: 15px;
				border-radius: 15px;
				behavior: url(border-radius.htc); ">
			<tr  align="center">
			<td valign="top" ><br>
				<marquee hspace="center" height="200px" direction="up" onMouseOver="stop()" onMouseOut="start()" scrolldelay="100" >
					<ul style="font-size:14px ;"  >
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					</ul>
				</marquee>
			</td>
			</tr>
			</table>
			</td>
			<td width="100"></td>
			<td>
			<table width="244" height="146" style="background-color:#F5F5F5; -moz-border-radius: 15px; -webkit-border-radius: 15px;
				border-radius: 15px;
				behavior: url(border-radius.htc); ">
			<tr  align="center">
			<td valign="top" ><br>
				<marquee hspace="center" height="200px" direction="up" onMouseOver="stop()" onMouseOut="start()" scrolldelay="100" >
					<ul style="font-size:14px ;"  >
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					<li><a href=""> About Leave </a></li>
					</ul>
				</marquee>
			</td>
			</tr>
			</table>
			
			</td>
			</tr>
			</table>
			
			</div>
			<!--  end table-content  -->
	
			<div class="clear"></div>
		 
		</div>
		<!--  end content-table-inner ............................................END  -->
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
    

</body>
</html>