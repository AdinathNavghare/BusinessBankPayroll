<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="payroll.Model.Lookup"%>
<%@page import="payroll.Model.EmpOffBean"%>
<%@page import="payroll.DAO.EmpOffHandler"%>
<%@page import="java.util.ArrayList"%>
<%@page import="payroll.Core.ReportDAO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy DTS3 </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />

<link rel="stylesheet" href="css/datePicker.css" type="text/css" />

<script src="js/jquery/date.js" type="text/javascript"></script>

<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<script>

jQuery(document).ready((function($) {
      $("#EMPNO").autocomplete("list.jsp");
}));
</script>
<style type="text/css">
.row:hover {
	background-color: #CCCCCC;
	cursor: pointer;
}
.style1 {
	color: #FF0000
}
</style>



<script type="text/javascript">
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


	function validation()
	{
	/* 	var fromDate = document.getElementById("date").value;
	  // 	var toDate=document.getElementById("date1").value;
	   	
		if(document.getElementById("date").value=="")
		{
		alert("Please Select Date");
		document.getElementById("date").focus();
		return false;
		} */
		var type=document.getElementById("type").value;
		if(type=="0")
		{
			alert("Please Select Report Type");
			document.getElementById("type").focus();
			return false;
		}
		else if(type=="Empwise")
		{
			if(document.getElementById("EMPNO").value=="")
				{
					alert("Please Select Employee");
					document.getElementById("EMPNO").focus();
					return false;
				}
		}
		else if(type=="Monthwise")
		{
			if(document.getElementById("date1").value=="")
				{
					alert("Please Select Date");
					document.getElementById("date1").focus();
					return false;
				}
		}
		
	/* 	document.getElementById("process").hidden=false;
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
		xmlhttp.open("GET",true);
		xmlhttp.send();
		 */
		
		
		
	}
	
	
	function getVal(){
		
		var type = document.getElementById("type").value;
		if(type == "Empwise"){
			 document.getElementById("Emp").style.display = 'table-row';
			 document.getElementById("date").style.display = 'none';
		}
		else if(type == "Monthwise"){
			 document.getElementById("date").style.display = 'table-row';
			 document.getElementById("Emp").style.display = 'none';

		}
		else if(type == "0"){
			 document.getElementById("date").style.display = 'none';
			 document.getElementById("Emp").style.display = 'none';

		}

					
		
		
		
	}
</script>

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
                var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
                var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
                $(this).datepicker('setDate', new Date(year, month, 1));
                $(':focus').blur();
                
            },
            beforeShow : function(input, inst) {
                var datestr;
                if ((datestr = $(this).val()).length > 0) {
                    year = datestr.substring(datestr.length-4, datestr.length);
                    month = jQuery.inArray(datestr.substring(0, datestr.length-5), $(this).datepicker('option', 'monthNamesShort'));
                    $(this).datepicker('option', 'defaultDate', new Date(year, month, 1));
                    $(this).datepicker('setDate', new Date(year, month, 1));
                    
                }
            }
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
		<h1>Extra Duty Payment Report</h1>
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
			
			<form name="extraduty" action="ReportServlet" method="post" onSubmit="return validation()">
			<table border="1" id="customers" align="center" >
			<tr>
				<th>Extra Duty Payment Report</th>
			<tr>
			<tr class="alt">
				<td  align="center">
				<input type="hidden" id="action" name="action" value="extradutypaymentreport"></input>
				<table align="center">
					
					<tr>
						<td >Report Type</td>
						<td  align="left">
							<select id="type" name="type" onchange="getVal()">
								<option value="0" selected="selected">Select</option>
								<option value="Empwise">Employee Wise</option>
								<option value="Monthwise">Month Wise</option>
								
							 	<!-- <option value="Gender">Gender wise</option>
							 	<option value="Project">Branch Wise</option> -->	
							
							
								
							</select>
			         </td>	
					</tr>
					
					<tr id="Emp" class="alt" style="display: none;">
						<td>Employee Name / Id &nbsp;&nbsp;</td>
						<td ><input type="text" name="EMPNO" id="EMPNO" size="30"   placeholder="Enter Employee Name or No." />
						
						</td>
					</tr>
					
					
					
						<tr id="date" class="alt" height="30" align="left" style="display: none;"> 
							<td>Select Date</td>
							<td align="left">
								<input name="date1" id="date1" readonly="readonly" class="date-picker" placeholder="Click here for Calender"/>
							</td>
								
						</tr>
					
                            
     			      		                           
     			    	<tr>	
						<td colspan="2" align="center">
							<input type="submit" class="myButton" value="Get Report" />
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
		
		<div id="viewPdf"  hidden="true" align="center"></div>
		<div id="process"  style="z-index: 1000;background-color:white;height: 100%;width: 100%;top: 0px;left: 0px;position: fixed;opacity:0.8;" hidden=true; >
			<div align="center" style="padding-top: 20%;">
				<img alt="" src="images/process.gif">
			</div>
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
