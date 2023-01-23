<%@page import="payroll.Model.NotificationBean"%>
<%@page import="payroll.Model.Lookup"%>
<%@page import="java.util.ArrayList"%>
<%@page import="payroll.DAO.LookupHandler"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/screen.css" type="text/css"
	media="screen" title="defaulrt" />
<link rel="stylesheet" type="text/css" href="datepickr.css" />
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<title>Notification Management</title>
<%	String pageName = "notification.jsp";
	/* try
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
	} */
%>

<script type="text/javascript">

</script>
<style type="text/css">
.row:hover{
		background-color:#CCCCCC;
		cursor:pointer;
}

.style1 {color: #FF0000}
</style>
</head>
<body style="overflow:hidden"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:scroll; max-height: 90%; width:auto">
<!-- start content -->
<div id="content"  >

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Notification Management</h1>
	</div>
	<!-- end page-heading -->

	<table border="0" width="100%" cellpadding="0" cellspacing="0" id="content-table">
	<tr>
		<th rowspan="3" class="sized"><img src="images/shared/side_shadowleft.jpg" width="20" height="500" alt="" /></th>
		<th class="topleft"></th>
		<td id="tbl-border-top">&nbsp;</td>
		<th class="topright"></th>
		<th rowspan="3" class="sized"><img src="images/shared/side_shadowright.jpg" width="20" height="500" alt="" /></th>
	</tr>
	<tr>
		<td id="tbl-border-left"></td>
		<td>
		<!--  start content-table-inner ...................................................................... START -->
		<div id="content-table-inner">
		
			<!--  start table-content  -->
			<div id="table-content">
			
			<font face="Georgia" size="2">
<center>
<h3>Notification Management</h3>



<table id='customers'>
<tr>

<th> SR.NO</th>
<th> Notification Details</th>
<th> Received From</th>
<th> Arrival Date</th>
<th> ACTION</th>
</tr>

<%
LookupHandler lkp=new LookupHandler();
ArrayList<NotificationBean> list=new ArrayList<NotificationBean>();
list=NotifyHandler.getEMPNotifications(session.getAttribute("UID").toString());
int srno=1;
for(NotificationBean bean:list)
{

%>

<tr class="alt">
<td><%=srno++%></td>
<td> <%=bean.getDisc()%></td>
<td> <%=lkp.getLKP_Desc("ET",Integer.parseInt(bean.getCreated_by()))%> </td>
<td> <%=bean.getCreated_date()%></td>
<td>  </td>
</tr>
<%
}

if(list.size()<=0)
{
%>
<tr class="alt">
<td colspan="5">NO records Found..............</td>
</tr>
<%}
%>

</table>









</center>
</font>
			
			
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