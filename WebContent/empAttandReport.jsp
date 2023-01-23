<%@ page contentType="text/html; charset=iso-8859-1" language="java"
	import="java.sql.*" errorPage=""%>
<%@page import="payroll.DAO.*"%>
<%@page import="payroll.Model.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy DTS3</title>
<link rel="stylesheet" href="css/screen.css" type="text/css"
	media="screen" title="default" />
	<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />
	<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>

<script src="js/jquery/jquery.autocomplete.js"></script>
<style type="text/css">
.row:hover {
	background-color: #CCCCCC;
	cursor: pointer;
}

.style1 {
	color: #FF0000
}
</style>

<%

	String pageName = "empAttandReport.jsp";
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
	int action =-1;
	
	try
	{
		action = Integer.parseInt(request.getParameter("action"));
		
	}
	catch(Exception e)
	{
		
	}
	int UID = Integer.parseInt(session.getAttribute("UID")==null?"0":session.getAttribute("UID").toString());
	String frmdate;
	String todate;
	frmdate = request.getParameter("fromdate");
	todate = request.getParameter("todate");
	if(frmdate== null && todate==null)
	{
		Date dt1 = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
		String sysDate = format.format(dt1);
		frmdate = sysDate;
		todate = sysDate;
	}
	AttendanceHandler AH = new AttendanceHandler();
	ArrayList<AttendanceBean> attlist = AH.getEmpAttendanceList(UID, frmdate, todate);

%>



<script type="text/javascript">
	function details(url)
	{
		window.location.href = url;
	}

</script>
<style type="text/css">
.row:hover{
		background-color:#CCCCCC;
		cursor:pointer;
}

</style>
</head>
<body style="overflow: hidden;">
 <%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:scroll; max-height:80%; ">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Employees Attendance Report</h1>
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
			
			<form name="Att_Report" action="empAttandReport.jsp" method="post">
                 <table border="1" id="customers">
	       <tr>
		<th colspan="5">Employees  Attendance Report</th>
		<tr>
	<tr class="alt" height="30">
					
						<td>FromDate</td>
						
						<td bgcolor="#FFFFFF"><input name="fromdate" size="20"
							id="fromdate" type="text" value="<%=frmdate%> "
							onFocus="if(value=='dd-mmm-yyyy') {value=''}"
							onBlur="if(value=='') {value='dd-mmm-yyyy';}" readonly="readonly">&nbsp;<img
							src="images/cal.gif" align="absmiddle"
							style="vertical-align: middle; cursor: pointer;"
							onClick="javascript:NewCssCal('fromdate', 'ddmmmyyyy')" /></td>
						<td>Todate</td>
					  <td bgcolor="#FFFFFF"><input name="todate" size="20"
							id="todate" type="text" value="<%=todate%> "
							onFocus="if(value=='dd-mmm-yyyy') {value=''}"
							onBlur="if(value=='') {value='dd-mmm-yyyy';}" readonly="readonly">					    &nbsp;<img
							src="images/cal.gif" align="absmiddle"
							style="vertical-align: middle; cursor: pointer;"
							onClick="javascript:NewCssCal('todate', 'ddmmmyyyy')" /></td>
							<td><input type="submit"class="form-submit" value="Submit" ></td>
					</tr>		
             </table>
      </form>
	  <br>
	 
	  <table id="customers">
	      
		  
		  <tr><td width="574"> 
		        <table>
		                <tr> <th width="33">Sr.NO</th>
	                    <th width="47">UserId</th>
	                    <th width="61">EmpNO</th>
	                    <th width="229">Employee Name</th>
	                    
	                    <th width="72">Total Days</th>
	                    <th width="95">Present Days</th>
	                    
		  
		        </table>
		
<div style="height: 400px; overflow-y: scroll; width:auto" id="div1">
 <table>	
		  
	  <%
	  int i=1;
	   for(AttendanceBean Abean:attlist)
	    {
	    String str = Abean.getUSERID()+":"+frmdate+":"+todate;
	    %>
	  <tr class="row" onclick="details('attendanceReport.jsp?key=<%=str %>')" title="Click here for Detailed Report">
	    <td width="36"><%=i %></td>
	    <td width="47"><%=Abean.getUSERID()%></td>
	    <td width="57"><%=Abean.getEMPNO() %></td>
	    <td width="196"><%=Abean.getEMPNAME() %></td>
	    <td width="63" align="center"><%=Abean.getDIFF() %></td>
	    <td width="64" align="center"><%=Abean.getTOTAL_DAYS()%></td>
	 </tr>
	    <%
	    i++;
	    }
	  %>
	 </table>
	 </div>
		</td></tr> 
	  
	  </table>
</center>
			
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