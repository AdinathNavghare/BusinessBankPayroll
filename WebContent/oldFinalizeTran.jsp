<%@page import="java.util.ArrayList"%>
<%@page import="payroll.DAO.TranHandler"%>
<%@page import="com.ibm.icu.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=iso-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>Finalize Transactions &copy DTS3</title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<script type="text/javascript" src="js/datetimepicker.js"></script>
<!--[if IE]>
<link rel="stylesheet" media="all" type="text/css" href="css/pro_dropline_ie.css" />
<![endif]-->

<style type="text/css">
<!--
body,td,th {
	font-family: Times New Roman;
	font-size: 16px;
}
-->
</style>

<script type="text/javascript">
	
	function toggle()
	{
		var flag = document.getElementById("checkbox").value;
		
		if(flag=="true")
		{
			document.getElementById("checkbox").value="false";
			document.getElementById("save").disabled = false;			
		}
		else
		{
			document.getElementById("checkbox").value="true";
			document.getElementById("save").disabled = "disabled";
		}
	}
	
	function Confirm()
	{
		var flag = confirm("Are you Sure to Move the transactions to History Table");
		if(flag)
		{
			return true;	
		}
		else
			return false;
	}


</script>
<%
	String pageName = "FinalizeTran.jsp";
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
	boolean flag = false;
	Date dt = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
	String date = sdf.format(dt);
	TranHandler TH = new TranHandler();
	String[] fin = TH.getFinalizeStatus().split(":");
	flag = Boolean.parseBoolean(fin[1]);
	//	fin[0] Contains the Date and this date is dependant on the flag value
	if(flag)
	{
		// flag = "TRUE" means all transaction from tran are already moved to YTDTRAN table
		// fin[0] = Contains the Date on which the transactions are moved to ytdtran
	}
	else
	{
		// flag = "FALSE" means transactions from tran are not moved to ytdtran  
		// fin[0] = Contains the Date on which Last Payroll Calculation Is done
	}
%>
</head>
<body> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Finalize Transactions </h1>
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
			<div id="table-content"><center>
			<form action="TransactionServlet?action=finalize" method="post" onsubmit="return Confirm()">
			  <table width="670" align="center" style="background-color: #92B22C">
                <tr>
                  <td align="center" bgcolor="#FFFFFF">
	                 <%
	                	if(flag)
	                	{
	                		if(fin[0].equalsIgnoreCase("nothing"))
	                		{
	                %>  
	                   <p>There is no Payroll Calculation Done yet.</p> </b>
	                  
                   <%
	                		}
	                		else
	                		{
	                			 %>  
	      	                   <p>Last Month's Transactions are Successfully Saved to History Table on &nbsp;<b> <%= fin[0]%></b>
	      	                  
	                         <%
	      	                
	                		}
	                	}
	                	else
	                	{
                   %>
                              <p>Latest Payroll Calculation is Done for the Month &nbsp;<b> <%= fin[0].substring(3) %></b>
	                  <br/>
	                  <br/>
	                  <input type="checkbox" name="checkbox" id="checkbox" value="true" onClick="toggle()">
	                    &nbsp; I am agree to Finalize the Transactions, and Post it to History Table.</p> <br/>
	                  <p>Select Date <input name="sancDate" id="sancDate" type="text" value="<%=date %>" readonly="readonly"
	                    	 onFocus="if(value=='dd-mmm-yyyy') {value=''}" onBlur="if(value=='') {value='dd-mmm-yyyy';}" >
	                    	 &nbsp;<img src="images/cal.gif" align="middle" style="vertical-align:middle; cursor:pointer;"
	                    	 onClick="javascript:NewCssCal('sancDate', 'ddmmmyyyy')" /></p> <br/>
	                  <span style="border-radius :15px; background-color:silver;padding-top: 10px; padding-bottom: 10px">
	                  &nbsp;&nbsp;Please Note:- Once the Transactions are moved to history Table, you can't do any changes to it.&nbsp;&nbsp;</span>
	                  <p><br/><input type="submit" id="save" value="Move To History" disabled="disabled"></p>
                   <%
	                	}
                   %>
                  </td>
                </tr>
              </table></form>
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
