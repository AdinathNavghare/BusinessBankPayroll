<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="payroll.Core.ReportDAO"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Advance Reoprt </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<script type="text/javascript">

var xmlhttp;
var url="";
if(window.XMLHttpRequest)
	{
	xmlhttp = new XMLHttpRequest;
}
else
{
	xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
}

	function ViewReport1()
	{
		var typ = document.getElementById("Desgn").value;
		var month = document.getElementById("month").value;
		var year = document.getElementById("year").value;
		var date="01-"+month+"-"+year;
		if(month==0)
			{
			alert("please Select Month for Report");
			return false;
			}
		if(year==0)
		{
		alert("please Select year for Report");
		return false;
		}
		
		//var date = document.getElementById("date").value;
		if(typ==0){
			alert("please Select Report Type");
			return false;
		}
		
		document.getElementById("viewPdf").hidden=true;
		document.getElementById("process").hidden=false;
		var format;
		
		
		var btn = document.getElementById("button").value;
		var frmt = document.getElementById("Excel").checked;
		if(frmt){
			format="xls";
		}else {
			format="pdf";
		}
		url="AdvanceServlet?action=newAdvance&date="+date+"&btn="+btn+"&typ="+typ+"&frmt="+format;
		
		xmlhttp.onreadystatechange=function()
		{
			if (xmlhttp.readyState==4 && xmlhttp.status==200)
			{
				var response=xmlhttp.responseText;
	        	document.getElementById("viewPdf").innerHTML=response;
	        	document.getElementById("process").hidden=true;
	        	document.getElementById("viewPdf").hidden=false;
			}
		};
		xmlhttp.open("GET", url, true);
		xmlhttp.send();
	}
	

	
</script>
</head>
<body style="overflow:hidden;"> 
 <%
	String pageName = "PayReg.jsp";
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


<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:auto; max-height:83%; ">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Advance Report </h1>
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
			<!-- <a href="tranMaintainences.jsp" align="left"><b>Edit Salary Details</b></a> -->
			<center>
		<table id="customers">
		<br/><br/><br/><br/><br/>
					<tr class="alt">

						<th colspan="2">Advance Report</th>
					<%-- 	<td bgcolor="#FFFFFF"><input name="date" size="20" id="date"
							type="text" value="<%=ReportDAO.getSysDate() %>" readonly="readonly"
							onFocus="if(value=='dd-mmm-yyyy') {value=''}"
							onBlur="if(value=='') {value='dd-mmm-yyyy';}">&nbsp;<img
							src="images/cal.gif" align="middle"
							style="vertical-align: middle; cursor: pointer;"
							onClick="javascript:NewCssCal('date', 'ddmmmyyyy')" /></td> --%>
		</tr>
		<tr class="alt">
		<td>For Month :</td>
		<td>
			<select name='month' id='month'>
									<option value="0" selected >Select</option>
									<option value='Jan'> January </option>
									<option value='Feb'> February </option>
									<option value='Mar'> March </option>
									<option value='Apr'> April </option>
									<option value='May'> May </option>
									<option value='Jun'> June</option>
									<option value='Jul'> July</option>
									<option value='Aug'> August</option>
									<option value='Sep'> September</option>
									<option value='Oct'> October </option>
									<option value='Nov'> November</option>
									<option value='Dec'> December </option>								
									</select>
					</td>	
					</tr>			
						<tr class="alt">
						
								<td>And Year :</td>			
							
						<td>			<select name="year" id="year"   style="width: 85px">
									<option value="0" selected >Select    </option>
									<%
									String[] yyyy=ReportDAO.getSysDate().split("-");
									int yr=Integer.parseInt(yyyy[2])-1;
									for (int yy=yr;yy<=yr+2;++yy)
									{
									%>
									<option value='<%=yy%>'><%=yy%></option>
									<%}%>
									</select>
									</td>
									</tr>
			
						<!-- <td style="display:none;">Report Type </td> -->
						<td style="display:none;">
							<select name="Desgn" id="Desgn">  
      					  	
      					  	<option value="ID" >Advance Report</option>
      					  	
     			      		</select>
     			      	</td>	
						<td style="display:none;"><input type="radio" name="format" id="Excel" value="Excel" style="display:none">&nbsp;&nbsp;&nbsp;
							<input type="radio" name="format" id="Pdf" value="Pdf" checked>&nbsp;&nbsp;Pdf					
						</td>	
						<tr class="alt">
						<td align="center" colspan="2"><input type="submit"	value="Submit" onclick="ViewReport1()"/>
											<input type="hidden" id="button" value="before"/></td>
							</tr>									
					</tr>
				
		</table>
</center>
<br>
<div id="viewPdf" style="display: none"  >
 </div>
			   <div id="process"  style="z-index: 1000;background-color:white;height: 100%;width: 100%;top: 0px;left: 0px;position: fixed;opacity:0.8;" hidden=true; >
				<div align="center" style="padding-top: 20%;">
				<img alt="" src="images/process.gif">
				</div>
			</div>
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