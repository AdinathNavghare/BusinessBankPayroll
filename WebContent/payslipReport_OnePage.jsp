<%@page import="payroll.Core.Calculate"%>
<%@page import="payroll.Core.ReportDAO"%>
<%@page import="payroll.DAO.*"%>
<%@page import="payroll.Model.*"%>
<%@page import="java.util.ArrayList"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Payslip Reports ©copy DTS3</title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css"	media="screen" title="default" />
<script src="js/filter.js"></script>
<style type="text/css">
<!--
body,tr,td,th {
	font-family: Times New Roman;
	font-size: 16px;
}
-->
</style>

<script type="text/javascript" src="js/datetimepicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>

<script type="text/javascript">
var month="";
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

function paycal()
{
 	if (document.getElementById("type").value == "0") {
		alert("Please Select Report Type Type");
		document.getElementById("type").focus();
		return false;
	}
	 
	if(numList != "")
	{
		
		var date =  "01-"+document.getElementById("date").value;
		var type = document.getElementById("type").value;
		
			document.getElementById("process").hidden=false;
				if(type=="txt")
					url="ReportServlet?action=txtpayslip&list="+numList+"&date="+date;
				else if(type=="pdf")
					url="ReportServlet?action=pdfpayslip&type=OnePage&list="+numList+"&date="+date;	
			xmlhttp.onreadystatechange=function()
			{
				if(xmlhttp.readyState==4)
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
		alert(response);
		document.getElementById("process").style.display="block";
	}
}
/* function getFilt()
{
	var month=document.getElementById("date").value;
	if(month=="")
	{
		alert("Please Select date first");
	}
	else
	{
	getFilter('toPaySlip',month);
	}
}
function addFilt()
{
	var month=document.getElementById("date").value;
	if(month=="")
	{
		alert("Please Select date first");
	}
	else
	{
		addMoreEmp('toPaySlip',month);
	}
} */
</script>
<%

String pageName = "payslipReport.jsp";
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


<script src="js/MONTHPICK/jquery.js"></script>
    <script type="text/javascript" src="js/MONTHPICK/jquery-ui.min.js"></script>
    <link rel="stylesheet" type="text/css" media="screen" href="css/MONTHPICK/jquery-ui.css">
    <script type="text/javascript">
    $(function() {
        $('.date-picker').datepicker( {
            changeMonth: true,
            changeYear: true,
            showButtonPanel: true,
            dateFormat: 'M-yy',
            onClose: function(dateText, inst) { 
                month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
                var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
                //$(this).datepicker('setDate', new Date(year, month, 1));
                month=tomonth(month);
            	document.getElementById("date").value=month+"-"+year;
                $(':focus').blur(); 
                month=document.getElementById("date").value;
               	
             	
            }/* ,
            beforeShow : function(input, inst) {
                var datestr;
                if ((datestr = $(this).val()).length > 0) {
                    year = datestr.substring(datestr.length-4, datestr.length);
                    month = jQuery.inArray(datestr.substring(0, datestr.length-5), $(this).datepicker('option', 'monthNamesShort'));
                    $(this).datepicker('option', 'defaultDate', new Date(year, month, 1));
                    $(this).datepicker('setDate', new Date(year, month, 1));
                    month=document.getElementById("date").value;
                   
                    
                }
            } */
        });
        
    });
    </script>
    <style>
    .ui-datepicker-calendar {
        display: none;
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
		<h1>Payslip Report</h1>
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
				<table id="customers" width="900" align="center">
				
				   <tr> <th>Payslip Report</th></tr>
				  	<tr class="alt" >
						<td align="center"><!-- Select  Format :<select name="type" id="type"  >
											<option value="0">Select </option>
											<option value="old" selected>Old</option>
											<option value="new">New</option>
										</select>  --> 
										Select  Format :<select name="type" id="type"  >
											<option value="0">Select </option>
											<option value="txt" selected>TXT</option>
											<option value="pdf">PDF</option>
										</select>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
						Date :
						
						<input name="date" id="date" readonly="readonly" class="date-picker" placeholder="Click here for Calender"/>
						
						</td>
					
					</tr>
					 <tr class="alt" align="center">
						<td >
							<div id="displayDiv" style="height: 300px;width: 800px;"></div>
							<div id="countEMP">0 Employees Selected</div>
							<input type="button" class="myButton"  value="Cancel All" onclick="cancelAll()">
							<input type="button" class="myButton" value="Select Employees" onclick="getFilt('toPaySlip')">
							<!-- <input type="button" class="myButton" value="Add More Employee" onclick="addFilt()"> -->
						</td>
					</tr>
					
					<tr class="alt">
						<td  align="center"> 
							<input type="button" class="myButton" value="Generate Payslip " onclick="paycal()"/> &nbsp;&nbsp;
							
						</td></tr>

			  </table>
				
			<!-- </form> -->
			
          
		   </div>
		   <div id="viewPdf"  hidden="true" align="center">
			   </div>
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
