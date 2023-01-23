<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="payroll.Core.Calculate"%>
<%@page import="payroll.Core.ReportDAO"%>
<%@page import="payroll.DAO.*"%>
<%@page import="payroll.Model.*"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy DTS3</title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css"	media="screen" title="default" />
<script src="js/filter.js"></script>
<style type="text/css">
<!--
body,tr,td,th {
	font-family: Times New Roman;
	font-size: 14px;
}
-->
</style>

<script type="text/javascript" src="js/datetimepicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>


<link rel="stylesheet" href="css/jquery.treeview.css" />


<script src="js/jquery.js" type="text/javascript"></script>

<script src="js/jquery.treeview.js" type="text/javascript"></script>

<script type="text/javascript">
var month="";
	var xmlhttp;
	var url="";
	var date;
	
	if(window.XMLHttpRequest)
	{
		xmlhttp=new XMLHttpRequest;
	}
	else //if(window.ActivXObject)
	{   
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}

function nxtMonth(date)
{
		var resp=confirm("Are You Sure to Start Next Month Payroll Calculation ?\n For  "+date);
		dat= document.getElementById("curdate").value;
		dat1= document.getElementById("nxtdate").value;
		if(resp==true)
		{
			document.getElementById("process").hidden=false;
			url="CoreServlet?action=import&date="+dat+"&next="+dat1;
			xmlhttp.onreadystatechange=function()
			{
				if(xmlhttp.readyState==4)
				{
					var response = xmlhttp.responseText;
					alert(response);
					getremaining();
					document.getElementById("process").hidden=true;
					document.getElementById("viewPdf").hidden=false;
				}
				
			};
			xmlhttp.open("POST", url, true);
			xmlhttp.send();
		}
}

function getremaining()
{
	
	url="CoreServlet?action=notfinalized";
	
	xmlhttp.onreadystatechange=function()
	{
		if(xmlhttp.readyState==4)
		{
			var response1 = xmlhttp.responseText;
			document.getElementById("notfinal").innerHTML = response1;
			$("#atree").treeview({
				collapsed: false,
				animated: "medium",
				control:"#sidetreecontrol1",
				persist: "location"
			});
			if(response1.length!=0)
			document.getElementById("salaryremaining").hidden=false;
			
		}
		
	};
	xmlhttp.open("POST", url, true);
	xmlhttp.send();
	
}



</script>
<%
	String pageName = "nextMonthCal.jsp";
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
	Date dt = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
	String[] date = sdf.format(dt).split("-");
	int year = Integer.parseInt(date[2]);
	TranHandler trnh = new TranHandler();
	
%>
</head>
<body style="overflow: hidden" onload="getremaining()"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y: scroll; max-height: 80%;">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Payroll Calculation For Next Month </h1>
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
			 
				<table><tr><td>
				<table id="customers" width="553" align="center">
				<%
					String date1=trnh.getSalaryDate();
					String[] d={"","",""};
					d=date1.split("-");
					Date dd = sdf.parse(date1);
					Calendar cal = Calendar.getInstance();
					cal.setTime(dd);
					cal.add(Calendar.MONTH, 1);
					String output = sdf.format(cal.getTime());
					String[] d1={"","",""};
					d1=output.split("-");
					
				%>
				   <tr> <th>Payroll Calculation For Next Month</th></tr>
				  	<tr class="alt">
						<td align="left">
						Current Month&nbsp;&nbsp;<input name="curdate" id="curdate" readonly="readonly" size="10" value="<%=d.length!=0?d[1]+"-"+d[2]:""%>" disabled="disabled"/>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						
						Next Month&nbsp;&nbsp;<input name="nxtdate" id="nxtdate" readonly="readonly" value="<%=d1.length!=0?d1[1]+"-"+d1[2]:""%>" size="10" disabled="disabled"/>
						</td>
					
					</tr>
					 <tr class="alt" align="center">
						<td>
							<div id="displayDiv" style="height: 300px"></div>
						</td>
					</tr>
					
					<tr class="alt">
						<td  align="center"> 
							<input type="button" value="Import For Next Month" onclick="nxtMonth('<%=d1.length!=0?d1[1]+"-"+d1[2]:""%>')"/>
						</td></tr>

			  </table>
			  
			</td>
			<td width="10"></td>
			<td ><div id="salaryremaining" style=" border-radius:15px; width: 50; height: 100; " >
		  	<center>
				 <table id="customers" width="100" align="left" >
					<tr > <th>Salary Remaining For Finalize </th></tr>
					<tr >
						<td>
						<div id="sidetreecontrol1"><a href="?#"> Collapse  All</a> | <a href="?#">Expand All</a></div>
						<div style="overflow-y: scroll;max-height: 346px; ">
							<table style=" height: 350px;">
								<tr class="alt" align="left">
									<td width="300"  valign="top"  >
										<div id ="notfinal" ></div> 
									</td>
					</tr></table></div></td></tr>
					
				</table> 
				
				
			
			</center>	
		  </div></td>
			</tr>
			</table>
			 </center>	
			<!-- </form> -->
			
          
		   </div>
		   <center>
		   
		   </center>
		   
			<div id="process"  style="z-index: 1000;background-color:white;height: 100%;width: 100%;top: 0px;left: 0px;position: fixed;opacity:0.8;" hidden=true; >
				<div align="center" style="padding-top: 20%;">
				<img alt="" src="images/process.gif">
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
