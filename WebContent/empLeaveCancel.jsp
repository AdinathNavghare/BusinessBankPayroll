<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.ArrayList"%>
<%@page import="java.util.ListIterator"%>
<%@page import="payroll.DAO.LeaveMasterHandler"%>
<%@page import="payroll.Model.LeaveMasterBean"%>
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<script type="text/javascript" src="js/datetimepicker.js"></script>
<title>User</title>
<% 
String empno = session.getAttribute("EMPNO").toString(); //get empno from session
String action = request.getParameter("action") == null?"":request.getParameter("action");
LeaveMasterHandler lmh = new LeaveMasterHandler();
ArrayList<LeaveMasterBean> leavecncellist = lmh.getlast(Integer.parseInt(empno));
%>
<script type="text/javascript">
function modifyRec(key)
	{
	// var no=document.getElementById("empno1").value;
	  window.showModalDialog("empLeaveCnfrm.jsp?key="+key,null,"dialogWidth:690px; dialogHeight:130px; scroll:off; status:off;dialogLeft:400px;dialogTop:300px; ");
	  window.location.href="TransactionServlet?no="+no;
	}
</script>



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
		<div id="content"  ><!--  start page-heading -->
			<div id="step-holder">
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">1</div>
			<div class="step-light-left"><a href="empleave.jsp" >Leave </a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">2</div>
			<div class="step-light-left"><a href="empLeaveHistory.jsp">Leave Balance</a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no">3</div>
			<div class="step-dark-left"><a href="empLeaveCancel.jsp">Leave Cancel</a></div>
			<div class="step-dark-round">&nbsp;</div>
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
	<table border="1" id="customers">
	<tr class="alt" style="font-size: 2">
		<th width="67" height="20">LeaveCD</th>
		<th width="70">EmpNO</th>
		<th width="81">TransDate</th>

		<th width="54">Appl No</th>
		<th width="63">TeleNo</th>

		<th width="57">From Date</th>
		<th width="48">To Date</th>
		<th width="71">SancAth</th>
		<th width="40">Status</th>
		<th width="54">&nbsp;</th>
	</tr>
	<% 
	if(leavecncellist.size()!=0)
	{
	
	for(LeaveMasterBean leave:leavecncellist)
		{
		
		String str = leave.getEMPNO()+":"+leave.getEMPNO()+":"+leave.getLEAVECD()+":"+leave.getAPPLNO();
		%>
	   <tr class="row" bgcolor="#FFFFFF">
		<td width="75"><%=leave.getLEAVECD() %></td>
		<td width="85"><%=leave.getEMPNO() %></td>
		<td width="90"><%=leave.getTRNDATE() %></td>

		<td width="75"><%=leave.getAPPLNO() %></td>
		<td width="75"><%=leave.getLTELNO() %></td>

		<td width="70"><%=leave.getFRMDT() %></td>
		<td width="70"><%=leave.getTODT() %></td>
		<td width="75"><%=leave.getSANCAUTH() %></td>
		<td width="40"><%=leave.getSTATUS()%></td>
		<td width="60" align="center"><input type="button" Value="cancel"
			onClick="window.location='empLeaveCancel.jsp?action=showdetails&empno=<%=leave.getEMPNO()%>&leavecd=<%=leave.getLEAVECD()%>&applno=<%=leave.getAPPLNO()%>'" /></td>
	</tr>
	<% }
	
	}
	else
	{
	%>
	<tr align="center"><td colspan="11" align="center">Their Is No Any Record Found</td></tr>
<% 
  }

 %>
	<tr bgcolor="#92b22c">
		<td colspan="10">&nbsp;</td>
	</tr>

</table>


<%
   if(action.equals("showdetails"))
   {
	   
	   ArrayList<LeaveMasterBean> listcancel=null;
	   String empno1 = request.getParameter("empno")==null?"":request.getParameter("empno");
	   String leavecd=request.getParameter("leavecd")==null?"":request.getParameter("leavecd");
	   String applno=request.getParameter("applno")==null?"":request.getParameter("applno");
	   
	   for(LeaveMasterBean leavebean2 :leavecncellist)
	   {
	    if(applno.equalsIgnoreCase(String.valueOf(leavebean2.getAPPLNO())) && leavecd.equalsIgnoreCase(String.valueOf(leavebean2.getLEAVECD())))
	    {
	  
  %>
  
  <div id="hide2" style="display: block;">
  <form  name="leaveForm " action="leave?action=statusChange" method="post" >
  <table  id="customers" align="center">
	 <tr>
		<th>Leave Transaction</th>
		
    </tr>
	<tr class="alt">
		<td width="421"  align="center">
		         <table id="customers">
				 <tr> <td colspan="2"> Cancel Leave Transaction <input type="hidden" id="user" name="user" value="employee" ></td></tr>
				 <tr class="alt"><td>Date</td><td><input type="text" name="tdate" value="<%=leavebean2.getTRNDATE()%>" readOnly="readonly"/></td></tr>
				
				 
				      <tr class="alt"><td width="109">Employee NO</td>
				      <td width="250"><input type="text" name="empno" value="<%=leavebean2.getEMPNO() %>"  /></td> </tr>
				    
					  <tr><td>Leave code</td><td>
					  <input type="text" name="leavecode" value="<%=leavebean2.getLEAVECD() %>"/>
					  
					  </td></tr>
					   <tr><td>From Date</td><td><input type="text" name="frmdate" value="<%=leavebean2.getFRMDT() %>" /></td></tr>
					   
					    <tr><td>To Date</td><td> <input type="text" name="todate" value="<%=leavebean2.getTODT() %>" /></td></tr>
					    <tr><td>Applno</td><td> <input type="text" name="applno" value="<%=leavebean2.getAPPLNO() %>" /></td></tr>
						 <tr><td width="109">Sanctioned By</td>
			            <td width="250"><input type="text" name="sanctionby" value="<%=leavebean2.getSANCAUTH() %>" /></td></tr>
						 <tr><td width="109">Status</td>
			            <td width="250"><input type="text" name="status" value="98" /></td></tr>
						<tr><td width="109" height="29" ></td>
						<td width="250"> <input type="submit" value="Save"/>  <input type="button" value="Cancel" onClick="showDiv1()"/></td>
						</tr>
		      </table>	   </td>
	  
	   
	   
	</tr>
		
	<tr bgcolor="#92b22c"><td>&nbsp;</td></tr>
</table>


</form>

   </div> 


   <%
	    }
	   }
    }
   %>
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
</div>
<!--  end content -->
<div class="clear">&nbsp;</div>
</div>
<!--  end content-outer........................................................END -->

<div class="clear">&nbsp;</div>
    

</body>
</html>