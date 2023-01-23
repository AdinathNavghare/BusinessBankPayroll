<%@page import="javax.xml.crypto.Data"%>
<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="payroll.DAO.EmpOffHandler"%>
<%@page import="payroll.Model.Lookup"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.ListIterator"%>
<%@page import="payroll.DAO.LeaveMasterHandler"%>
<%@page import="payroll.Model.LeaveMasterBean"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<script type="text/javascript" src="js/datetimepicker.js"></script>

<title>User</title>
<% 
String empno = session.getAttribute("EMPNO").toString(); 
String action = request.getParameter("action")==null?"":request.getParameter("action");
LeaveMasterHandler lmh = new LeaveMasterHandler();
ArrayList<LeaveMasterBean> leaveballist = lmh.getList(Integer.parseInt(empno));
%>

</head>
<body style="overflow: hidden;" > 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:scroll; max-height:80%; ">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<div id="content"  ><!--  start page-heading -->
			<div id="step-holder">
			<div class="step-dark-right">&nbsp;</div>
			<div class="step-no">1</div>
			<div class="step-dark-left"><a href="empleave.jsp" >Leave </a></div>
			<div class="step-dark-right">&nbsp;</div>
			<div class="step-no-off">2</div>
			<div class="step-light-left"><a href="empLeaveHistory.jsp">Leave Balance</a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">3</div>
			<div class="step-light-left"><a href="empLeaveCancel.jsp">Leave Cancel</a></div>
			<div class="step-light-round">&nbsp;</div>
			<div class="clear"></div>
		
		</div>
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
				<center>	 
	<table   align="center" id="customers">
					<tr class="alt" style="font-size: 2">
					<th width="95">EMPNO</th>
					<th width="100">BALDATE</th>
					<th width="82">LeaveCD</th>
					<th width="90" align="center">BAL</th>
					<th width="89">TOTALCR</th>
					<th width="148">TOTALDR</th>
					</tr>
					<%
	
						for(LeaveMasterBean leaveBal:leaveballist)
							{
							
							%>
								<tr class="row" bgcolor="#FFFFFF" align="center">
									<td width="95"><%=leaveBal.getEMPNO() %></td>
									<td width="100"><%=leaveBal.getBALDT() %></td>
									<td width="80"><%=leaveBal.getLEAVECD() %></td>
									<td width="90"><%=leaveBal.getBAL() %></td>
									<td width="90"><%=leaveBal.getTOTCR() %></td>
									<td width="90"><%=leaveBal.getTOTDR() %></td>
					
								</tr>
								<% 
						 			} 
						   
								 %>
							

	<tr bgcolor="#92b22c">
		<td colspan="6" align="right"><input type="button" value = "Apply Leave" onClick="window.location='empLeaveApply.jsp'"></td>
	</tr>
</table>
					 
			</center>	
		</div> <!--  end table-content  -->
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
</div>
<!--  end content -->
<div class="clear">&nbsp;</div>
</div>
<!--  end content-outer........................................................END -->

<div class="clear">&nbsp;</div>
    

</body>
</html>