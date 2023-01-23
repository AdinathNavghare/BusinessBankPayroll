<%@page import="java.util.ArrayList"%>
<%@page import="payroll.Core.ReportDAO"%>
<%@ page contentType="text/html; charset=iso-8859-1" language="java"
	import="java.sql.*" errorPage="error.jsp"  isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Ctc Report </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />

<script src="js/filter.js"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>

<script type="text/javascript">

function getFilt()
{
	var month=document.getElementById("date").value;
	if(month == "")
	{
		alert("Please Select Date between a year");	
	}
	else
	{
		getFilter('toCTC_Report',month);
	}
}
function addFilt()
{
	var month=document.getElementById("date").value;
	if(month == "")
	{
		alert("Please Select Date between a year");	
	}
	else
	{
		addMoreEmp('toCTC_Report',month);
	}
}

function paycal()
{
	
		
		document.getElementById("list").value=numList;
		if(numList==""){
			alert("please select employee !");
			return false;
		}
		else
			{
		 var xmlhttp;
		var url="";
		if(window.XMLHttpRequest)
		{
			xmlhttp=new XMLHttpRequest;
		}
		else //if(window.ActivXObject)
		{   
			xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
		}
		 
			 
			
			url="ReportServlet?action=CtcReportNew&emplist="+numList;
			xmlhttp.onreadystatechange=function()
			{
			if(xmlhttp.readyState==4)
				{
					var response = xmlhttp.responseText;
					
					initAll();
				}

			};
			
			xmlhttp.open("post",url, true);
			xmlhttp.send();
		  
	}
	
}

</script>
<script>
	jQuery(function() {
		$("#empNo").autocomplete("list.jsp");
	});
</script>
<script>
	jQuery(function() {
		$("#empNo1").autocomplete("list.jsp");
	});
</script>

 <%-- <%
	String pageName = "CTC_Report.jsp";
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
 --%>




 </head>
<body style="overflow: hidden;"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>CTC Report</h1>
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
			<!-- <h2>Profession Tax Statement</h2> -->
			<form name="form10" action="ReportServlet" method="post" onsubmit="return paycal()">
				<table border="1" id="customers">
					<tr>
						<th> CTC Report</th>
					<tr>
					<tr class="alt">
						<td height="80" align="center">
							<table>
							
								<tr class="alt">
								
									<input type="hidden" id="action" name="action" value="CtcReportNew"></input>
									<input type="hidden" id="list" name="list">
									<input type="hidden"  name="date" id="date" readonly="readonly" value="<%=ReportDAO.getSysDate()%>  ">
									
									 <td align="center">
									
									Select Format : 
									<input id="Excel" type="radio" value="Excel" name="format">Excel 
                                    <input id="Pdf" type="radio" checked=""  value="Pdf" name="format">Pdf</td>
									
									</td>									
								</tr>
					 			
					 			<tr class="alt" align="center">
								<td>
									<div id="displayDiv" style="height: 300px"></div>
									<div id="countEMP">0 Employees Selected</div>
									<input type="button" value="Cancel All" onclick="cancelAll()">
									<input type="button" value="Select Employees" onclick="getFilt()">
									<input type="button" value="Add More Employee" onclick="addFilt()">
								</td>
								</tr>
					 			
					 			
					 			
								<tr>
									<td colspan="4" align="center">
										<input type="submit" value="Submit" />&nbsp;&nbsp;&nbsp;
										<input type="reset" value="Clear" />
									</td>
								</tr>
							</table>

			  			</td>
					</tr>
				</table>
			</form>
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