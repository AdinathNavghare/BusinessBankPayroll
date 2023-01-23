<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="payroll.Core.Calculate"%>
<%@page import="payroll.Core.ReportDAO"%>
<%@page import="payroll.DAO.*"%>
<%@page import="payroll.Model.*"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Employee List &copy DTS3</title>
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

/* function paycal()
{
	if(numList != "")
	{
		var resp=confirm("Are You Sure to Calculate Payroll?");
		//var month = document.getElementById("month").value +"-"+document.getElementById("year").value;
		date= document.getElementById("date").value;
		if(resp==true)
		{
			document.getElementById("process").hidden=false;
			url="CoreServlet?action=calc&list="+numList+"&date="+month;
			xmlhttp.onreadystatechange=function()
			{
				if(xmlhttp.readyState==4)
				{
					var response = xmlhttp.responseText;
					alert(response);
					NegativeSalary();
					document.getElementById("process").hidden=true;
					initAll();
				}
				
			};
			xmlhttp.open("POST", url, true);
			xmlhttp.send();
		}
	}
	else
	{
		alert("No Employee is Selected");
	}
}
 */
 function getrpt()
 {
 	if(numList != "")
 	{
 		var resp=confirm("Are You Sure to Calculate Payroll?");
 		//var month = document.getElementById("month").value +"-"+document.getElementById("year").value;
 		date= document.getElementById("date").value;
 		if(resp==true)
 		{
 			document.getElementById("process").hidden=false;
 			url="CoreServlet?action=calc&list="+numList+"&date="+month;
 			xmlhttp.onreadystatechange=function()
 			{
 				if(xmlhttp.readyState==4)
 				{
 					var response = xmlhttp.responseText;
 					alert(response);
 					NegativeSalary();
 					document.getElementById("process").hidden=true;
 					initAll();
 				}
 				
 			};
 			xmlhttp.open("POST", url, true);
 			xmlhttp.send();
 		}
 	}
 	else
 	{
 		alert("No Employee is Selected");
 	}
 }

function callback()
{
	if(xmlhttp.readyState==4)
	{
		var response = xmlhttp.responseText;
		//alert(response);
		//NegativeSalary();
		document.getElementById("process").style.display="block";
	}
}




function NegativeSalary()
{
	var date = "25-"+month;
	url="CoreServlet?action=negtvsal&date="+date;
		
	xmlhttp.onreadystatechange=function()
	{
		if(xmlhttp.readyState==4)
		{
			var response1 = xmlhttp.responseText;
			document.getElementById("nsalary").innerHTML = response1;
			if(response1.length!=0)
			document.getElementById("negativesalary").hidden=false;
			
		}
		
	};
	xmlhttp.open("POST", url, true);
	xmlhttp.send();
	
	
	}

function getTrnsation(empno)
{
	var form = document.createElement("form");
	form.action = "TransactionServlet?action=trnlist&list=negative&EMPNO="+empno;
	form.method = "POST";
	document.body.appendChild(form);
	form.submit();
	
}

function getFilt()
{
	if(month == "")
	{
		alert("Please Set Salary Month");	
	}
	else
	{
		getFilter('toPayCal',month);
	}
}
function addFilt()
{
	if(month == "")
	{
		alert("Please Set Salary Month");	
	}
	else
	{
		addMoreEmp('toPayCal',month);
	}
}

function showEditMonth() 
{
	if(month !="")
	{
		if(showList == "")
		{
			initAll();
			document.getElementById("editDate").style.display="block";
		}
		else
		{	var flag = confirm("Currently Selected Employees will be removed?");
			if(flag)
			{
				initAll();
				document.getElementById("editDate").style.display="block";
			}
		}
	}
	else
	{
		document.getElementById("editDate").style.display="block";
	}
}
function setMonth()
{
	if(document.getElementById("month").value=="NA")
	{
		alert("Not a Valid Month");
	}
	else
	{
		month = document.getElementById("month").value +"-"+document.getElementById("year").value;
		document.getElementById("date").value=month;
		document.getElementById("editDate").style.display="none";
	}
}
function closeEditMonth()
{
	document.getElementById("editDate").style.display="none";
}


function getNegtvSalMonths()
{
	url="CoreServlet?action=negtvsalmnth";
	
	xmlhttp.onreadystatechange=function()
	{
		if(xmlhttp.readyState==4)
		{
			var response1 = xmlhttp.responseText;
			document.getElementById("nsalary").innerHTML = response1;
			$("#atree").treeview({
				collapsed: false,
				animated: "medium",
				control:"#sidetreecontrol1",
				persist: "location"
			});
			if(response1.length!=0)
			document.getElementById("negativesalary").hidden=false;
			
		}
		
	};
	xmlhttp.open("POST", url, true);
	xmlhttp.send();
	
}



</script>
<%
	String pageName = "PayrollCalculation.jsp";
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
	
%>
</head>
<body style="overflow: hidden" onload="getNegtvSalMonths()"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y: scroll; max-height: 80%;">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Employee List </h1>
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
			 
			
			<!-- <form  name="leaveLedgerform" action="CoreServlet"  method="post" onSubmit="return validate()"> -->
				<table><tr><td>
				<table id="customers" width="553" align="center">
				
				   <tr> <th>Employee List</th></tr>
				  	<tr class="alt" align="center">
						<td>
						For The Month of <input name="date" id="date" readonly="readonly" size="10" disabled="disabled"/>
						<input type="button" value="Set Month" onclick="showEditMonth()">
						<div style="background-color: silver; padding-top:10%;
	                   width: 100%;height: 100%;z-index: 100; display: none;margin: 0;top: 17%;left: 0;position: fixed;opacity:0.6; " id="editDate">
						<fieldset style="width: 200px;height: 100px;background-color: #2f747e;">
						<legend style="color: #E8FDF7;">Salary Month</legend>
						<img src="images/Close.png" style="float: right;top: 0;cursor: pointer; " onclick="closeEditMonth()">
						<br/>
		                   	<select id = "month" name ="month">
		                   		<option value="NA">Select</option>
		                   		<option value="Jan">Jan</option>
		                   		<option value="Feb">Feb</option>
		                   		<option value="Mar">Mar</option>
		                   		<option value="Apr">Apr</option>
		                   		<option value="May">May</option>
		                   		<option value="Jun">Jun</option>
		                   		<option value="Jul">Jul</option>
		                   		<option value="Aug">Aug</option>
		                   		<option value="Sep">Sep</option>
		                   		<option value="Oct">Oct</option>
		                   		<option value="Nov">Nov</option>
		                   		<option value="Dec">Dec</option>
	                 		</select>
	                 		-
	                 		<select id = "year" name="year">
	                 		<%
	                 			for(int i= 2010;i<2050;i++)
	                 			{
	                 				if(i == year )
	                 				{
	                 		%>
	                 					<option value="<%=i %>" selected="selected"><%=i %></option>
	                 		<%
	                 				}
	                 				else
	                 				{
	                 		%>
	                 					<option value="<%=i %>"><%=i %></option>
	                 		<%
	                 				}
	                 			}
	                 		%>
	                 		</select><br/><br/>
	                 		<input type="button" value=" &nbsp; &nbsp;OK &nbsp; &nbsp;" onclick="setMonth()">
	                 		</fieldset>
	                 		</div>
						
						</td>
					
					</tr>
					 <tr class="alt" align="center">
						<td>
							<div id="displayDiv" style="height: 300px"></div>
							<div id="countEMP">0 Employees Selected</div>
							<input type="button" value="Cancel All" onclick="cancelAll()">
							<input type="button" value="Select Site" onclick="getFilt()">
							<input type="button" value="Select Designation" onclick="getFilt()">
							<!-- <input type="button" value="Add More Employee" onclick="addFilt()"> -->
						</td>
					</tr>
					
					<tr class="alt">
						<td  align="center"> 
							<input type="button" value="Calculate" onclick="getrpt()"/> &nbsp;&nbsp;
							<input type="button" value="Cancel"/>
						</td></tr>

			  </table>
			  
			</td>
			<td width="10"></td>
			<td ><div id="negativesalary" style=" border-radius:15px; width: 50; height: 100; " >
		  	<center>
				 <table id="customers" width="100" align="left" >
					<tr > <th>Salary in Negative</th></tr>
					<tr >
						<td>
						<div id="sidetreecontrol1"><a href="?#"> Collapse  All</a> | <a href="?#">Expand All</a></div>
						<div style="overflow-y: scroll;">
							<table style=" height: 380px;">
								<tr class="alt" align="left">
									<td width="300"  valign="top"  >
										<div id ="nsalary" ></div> 
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
