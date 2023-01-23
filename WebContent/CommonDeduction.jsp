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
<title>Common Deduction ©copy DTS3</title>
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

	function inputLimiterForFloat(e,allow) {
		  var AllowableCharacters = '';
		  if (allow == 'Numbers'){AllowableCharacters='1234567890.';}
		  var k;
		  k=document.all?parseInt(e.keyCode): parseInt(e.which);
		  if (k!=13 && k!=8 && k!=0){
		    if ((e.ctrlKey==false) && (e.altKey==false)) {
		      return (AllowableCharacters.indexOf(String.fromCharCode(k))!=-1);
		    } else {
		      return true;
		    }
		  } else {			  
		    return true;
		  }
		}
	
function paycal()
{

	if (document.getElementById("date").value.trim() == "") {
		alert("Please Select The Month ");
		document.getElementById("date").focus();
		return false;
	}
	
	
	
	if (document.getElementById("deduction").value == "0") {
		alert("Please Enter The Amonut ");
		document.getElementById("deduction").focus();
		return false;
	}
	
	if (document.getElementById("amount").value.trim() == " ") {
		alert("Please Enter The Amonut ");
		document.getElementById("amount").focus();
		return false;
	}
	
	if(numList != "")
	{
		
		var date =  "01-"+document.getElementById("date").value;
		var deduction=document.getElementById("deduction").value;
		var amount=document.getElementById("amount").value;
	
		
			document.getElementById("process").hidden=false;
	
			url="CommonDedServlet?action=Add&deduction="+deduction+"&amount="+amount+"&list="+numList+"&date="+date;
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
function getFilterforCommonDed()
{
	var month=document.getElementById("date").value;
	if(month=="")
	{
		alert("Please Select Month First");
		document.getElementById("date").focus();
		return false;
	}
	
	if (document.getElementById("deduction").value == "0") {
		alert("Please Enter The Amonut ");
		document.getElementById("deduction").focus();
		return false;
	}
	
	if (document.getElementById("amount").value.trim() == "") {
		alert("Please Enter The Amonut ");
		document.getElementById("amount").focus();
		return false;
	}
	
	else
	{
		getFilt('deduction');
	}
}
/* function addFilt()
{
	var month=document.getElementById("date").value;
	if(month=="")
	{
		alert("Please Select Month first");
		document.getElementById("date").focus();
		return false;
	}
	if (document.getElementById("deduction").value == "0") {
		alert("Please Enter The Amonut ");
		document.getElementById("deduction").focus();
		return false;
	}
	
	if (document.getElementById("amount").value.trim() == "") {
		alert("Please Enter The Amonut ");
		document.getElementById("amount").focus();
		return false;
	}
	
	else
	{
		addMoreEmp('deduction',month);
	}
} */
</script>
<%-- <%

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

%> --%>


<script src="js/MONTHPICK/jquery.js"></script>
    <script type="text/javascript" src="js/MONTHPICK/jquery-ui.min.js"></script>
    <link rel="stylesheet" type="text/css" media="screen" href="css/MONTHPICK/jquery-ui.css">
    <script type="text/javascript">
    var month="";
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
		<h1>Common Deduction</h1>
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
				
				   <tr> <th>Common Deduction Entries</th></tr>
				  	<tr class="alt" style="width: 50%;" >
					
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					
						For Month :
						
						<input name="date" id="date" readonly="readonly" class="date-picker" placeholder="Click here for Calender"/>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						
						Deduction Type :
							<select name="deduction" id="deduction" style="width: 140px"  >
								 <option value="0">Select....</option>  
    						<%
  							  ArrayList<CodeMasterBean> result=new ArrayList<CodeMasterBean>();
    						CodeMasterHandler CMH= new CodeMasterHandler();
    						result=CMH.getCDMAST("2");
 							for(CodeMasterBean CDB : result)
 							{
     						
     							%>
     							<option value="<%=CDB.getTRNCD()%>" ><%=CDB.getDISC()%></option>
     							
     					<%	}%>
							
							</select>
						
						
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						Enter Amount :
					<input name="amount" type="text" id="amount" maxlength="10" onkeypress="return inputLimiterForFloat(event,'Numbers')"/></td>
								
					
					</tr>
					 <tr class="alt" align="center">
						<td >
							<div id="displayDiv" style="height: 300px;width: 800px;"></div>
							<div id="countEMP">0 Employees Selected</div>
							<input type="button" class="myButton"  value="Cancel All" onclick="cancelAll()">
							<input type="button" class="myButton" value="Select Employees" onclick="getFilterforCommonDed()">
							<!-- <input type="button" class="myButton" value="Add More Employee" onclick="addFilt()"> -->
						</td>
					</tr>
					
					<tr class="alt">
						<td  align="center"> 
							<input type="button" class="myButton" value="Save " onclick="paycal()"/> &nbsp;&nbsp;
							
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
