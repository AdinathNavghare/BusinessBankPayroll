<%@page import="payroll.Core.ReportDAO"%>
<%@page import="payroll.DAO.EmployeeHandler"%>
<%@page import="payroll.DAO.EmpOffHandler"%>
<%@page import="payroll.Model.EmpOffBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="payroll.Model.Lookup"%>
<%@page import="payroll.DAO.LookupHandler"%>
<%@ page contentType="text/html; charset=iso-8859-1" language="java"
import="java.sql.*" errorPage="error.jsp"  isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Bonus Report</title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />
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

<script>
	jQuery(function() {
		$("#EMPNO").autocomplete("list.jsp");
	});
</script>

<script type="text/javascript">

<%  
String date=ReportDAO.getSysDate().substring(7,11);
int dateInInt=Integer.parseInt(date);
int year=0,nextYear=0;
%>

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
		/* var fromDate = document.getElementById("date").value;
	  // 	var toDate=document.getElementById("date1").value;
	   	
		if(document.getElementById("date").value=="")
		{
		alert("Please Select Date");
		document.getElementById("date").focus();
		return false;
		}
	
		if(document.getElementById("type").value=="0")
		{
			alert("Please Select Report Type");
			document.getElementById("type").focus();
			return false;
		} */
		
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
		var type = document.getElementById("type").value;
		 if(type == "0"){
			 alert("Please select Report Type");
			 return false;
		 }
		if(type == "Tentative"){
		 if(document.getElementById("date").value=="0"){
			 alert("Please select Financial Year");
			 return false;
		 } 	
		 if(document.getElementById("percent").value==""){
			 alert("Please select Percentage %");
			 return false;
		 } 	
		}
		if(type == "Details"){
			if(document.getElementById("bonusfrom").value=="0"){
				alert("Please select Report Year");
				document.getElementById("bonusfrom").focus();
				return false;
			}
		}
		if(type == "EX_GRACIA"){
			if(document.getElementById("bonusfrom").value=="0"){
				alert("Please select Report Year");
				document.getElementById("bonusfrom").focus();
				return false;
			}
			if(document.getElementById("month").value=="0"){
				alert("Please select Report Month");
				document.getElementById("month").focus();
				return false;
			}
			if(document.getElementById("YrMarch").value=="0"){
				alert("Please select Percentage");
				document.getElementById("YrMarch").focus();
				return false;
			}
		}
	}
	function changeYear(){
	 	if(document.getElementById("date").value=="")
	 	{
	 	alert("Please select year");
	 	return false;
	 	}
	 	var date=document.getElementById("date").value;
	 	document.getElementById("toYear").value=parseInt(date)+1;	
	 	var type = document.getElementById("type").value;
	 	 if(type == "Details"){
	 		var date=document.getElementById("bonusfrom").value;
		 	document.getElementById("bonusTo").value=parseInt(date)+1;	
	 	}
	 	if(type == "EX_GRACIA"){
	 		var date=document.getElementById("bonusfrom").value;
		 	document.getElementById("bonusTo").value=parseInt(date)+1;	
	 	}
	 }
	function selectReportType(){
		var type = document.getElementById("type").value;
		if(type == "Summary"){
			 document.getElementById('YrMarch').style.display='none';
			 document.getElementById('YrApril').style.display='none';
			 document.getElementById('branches').style.display='none';
			 document.getElementById('desi').style.display='none';
			 document.getElementById('bonusdate').style.display='none';
			 document.getElementById('bonusmonth').style.display='none';
		}
		else if(type == "Details"){
			 document.getElementById('YrMarch').style.display='none';
			 document.getElementById('YrApril').style.display='none';
			 document.getElementById('branches').style='table-row';
			 document.getElementById('bonusdate').style='table-row';
			 document.getElementById('bonusmonth').style.display='none';
		}
		else if(type == "EX_GRACIA"){
			 document.getElementById('YrMarch').style.display='table-row';
			 document.getElementById('YrApril').style.display='none';
			 document.getElementById('branches').style='table-row';
			 document.getElementById('bonusdate').style='table-row';
			 document.getElementById('bonusmonth').style='table-row';
		}
		else if(type == "0"){
			 document.getElementById('YrMarch').style.display='none';
			 document.getElementById('YrApril').style.display='none';
			 document.getElementById('branches').style.display='none';
			 document.getElementById('desi').style.display='none';
			 document.getElementById('bonusdate').style.display='none';
			 document.getElementById('bonusmonth').style.display='none';
		}
		else if(type == "Tentative"){
			 document.getElementById('YrMarch').style.display='table-row';
			 document.getElementById('YrApril').style.display='table-row';
			 document.getElementById('branches').style.display='none';
			 document.getElementById('desi').style.display='none';
			 document.getElementById('bonusdate').style.display='none';
			 document.getElementById('bonusmonth').style.display='none';
		}
	}
	function selectbranch()
	{
		var type =document.getElementById("branchtyp").value;
		if(type =="1")
		{
	     document.getElementById('branc').style.display='none';
		 document.getElementById('desi').style.display='none';
		}
		else if(type =="2")
		{
	    document.getElementById('desi').style.display='none';
		document.getElementById('branc').style='table-row';
			
		}
		/* else if(type =="0")
			{
			document.getElementById('branc').style.display='none';
			document.getElementById('desi').style.display='none';
			} */
		 else if(type =="3")
		{
		document.getElementById('desi').style='table-row';	
		document.getElementById('branc').style.display='none';
		} 
	}
	   function inputLimiter(e, allow) {
			var AllowableCharacters = '';
			if (allow == 'Numbers') {
				AllowableCharacters = '1234567890.';
			}
			var k;
			k = document.all ? parseInt(e.keyCode) : parseInt(e.which);
			if (k != 13 && k != 8 && k != 0) {
				if ((e.ctrlKey == false) && (e.altKey == false)) {
					return (AllowableCharacters.indexOf(String.fromCharCode(k)) != -1);
				} else {
					return true;
				}
			} else {
				return true;
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
		<h1>Bonus Report</h1>
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
			
			<form name="emplistForm1" action="ReportServlet" method="post"  onSubmit="return validation()">
			<table border="1" id="customers" align="center" >
			<tr>
				<th>BONUS REPORT</th>
			<tr>
			<tr class="alt">
				<td  align="center">
				<input type="hidden" id="action" name="action" value="bonus"></input>
				<table align="center">
					<!-- <tr class="alt" height="30" align="center"> 
						<td>Select Date</td>
						<td align="left">
							<input name="date" id="date" readonly="readonly" class="date-picker" placeholder="Click here for Calender"/>
						</td>
							
					</tr> -->
					<tr>
						<td colspan=2 align="left">Report Type</td>						
						<td  align="left">
							<select id="type" name="type" onchange="selectReportType()">
						 		<option value="0">Select</option> 
								<option value="Summary">Summary</option>
						 	    <option value="Details">Details</option> 
						 	    <option value="EX_GRACIA">Ex_Gracia</option>
								<option value="Tentative">Tentative Report</option> 
								
							</select>
			         </td>	
					</tr>
					<tr id="YrApril" style="display:none">
					<td>Financial Year : April-</td>
					<td>	<select id="date" onchange="changeYear()" name="date">		
						<option value="0">Select</option>	
						<option value="<%=dateInInt-1%>" ><%=dateInInt-1%></option>
						<option value="<%=dateInInt%>"><%=dateInInt%></option>
						<option value="<%=dateInInt+1%>"><%=dateInInt+1%></option>
					</select>
					</td>
					<td style="width:80px; text-align:center;">To March-</td>
					<td><input type="text" name="toYear" id="toYear" style="width: 40px;" readonly="readonly" />
					</td>
					</tr>
					<tr id="YrMarch" style="display:none">
					<td>Enter Percentage % </td>
					<td><input type="text" id="percent"  name ="percent" maxlength="5" size="5" onkeypress="return inputLimiter(event,'Numbers')"/>
					</td>
					</tr>
					<tr class="alt" height="30" id="branches" style="display:none">
					<td colspan=2 align="left">Select Branch</td>
					<td>	
					<select id="branchtyp" name="branchtyp"  onchange="selectbranch()"> 
				<!-- 	<option value="0" selected="selected">SELECT</option> -->
					<option value="1">ALL</option>
					<option value="2">Branch_Wise</option>
					<option value="3">Designation_Wise</option>
					</select>
					</td></tr>
					<tr class="alt" id="branc" style="display:none">
					<td colspan="2" align="left">Branch Wise </td>
		     		<td>
					<select name="Branch" id="Branch" style="width:140px">  
		      	<!-- 	<option value="Select">Select</option>   -->
		    		<%
					EmpOffHandler eh = new EmpOffHandler();
					ArrayList<EmpOffBean> ebn = new ArrayList<EmpOffBean>();
					ebn = eh.getprojectCode();
					for(EmpOffBean eopbn : ebn)
					{
					%>
					<option value="<%=eopbn.getPrj_srno()%>"><%=eopbn.getSite_name()%></option>
					<%		}
					%>
		     		</select>
		     		</td>
		     		</tr> 
		     		 <tr  class="alt" id="desi"  style="display:none">
		    		<td colspan="2" align="left">Designation Wise </td>
		     		<td>
					<select name="dewise" id="dewise" style="width:140px">  
		      	<!-- 	<option value="ALL">All</option>   -->
		    											
		    		<%
		    		LookupHandler lkh=new LookupHandler();
		    		ArrayList<Lookup> lkp_list=lkh.getSubLKP_DESC("DESIG");
				    for(Lookup lkp_bean:lkp_list)
					{
					%>
					<option value='<%=lkp_bean.getLKP_SRNO()%>'><%=lkp_bean.getLKP_DESC()%></option>
					<%	  
					 }
					 %>		</select>
		     		</td>
		     		</tr>
		     		<tr id="bonusdate" style="display:none">
					<td>Enter Year : -</td>
					<td>	
					  <select id="bonusfrom" name="bonusfrom" onchange="changeYear()" style="width: 77px;">		
						<option value="0">Select</option>	
						<option value="<%=dateInInt-1%>" ><%=dateInInt-1%></option>
						<option value="<%=dateInInt%>"><%=dateInInt%></option>
						<option value="<%=dateInInt+1%>"><%=dateInInt+1%></option>
					</select> 
					<!-- <input type="text" name="bonusfrom" id="bonusfrom" style="width: 40px;" onchange="changeYear()"/> -->
					</td>
					<td style="width:90px; text-align:left;">To Year-
					<input type="text" name="bonusTo" id="bonusTo" style="width: 60px;" readonly="readonly" />
					</td>
					</tr>
					<tr id="bonusmonth" style="display:none">
						<td  align="left">Select Month</td>						
						<td  align="left">
							<select id="month" name="month" style="width: 77px;">
						 		<option value="0">Select</option> 
								<option value="janamt:JAN">JAN</option>
						 	    <option value="febamt:FEB">FEB</option> 
						 	    <option value="maramt:MAR">MAR</option>
								<option value="apramt:APR">APR</option> 
								<option value="mayamt:MAY">MAY</option> 
								<option value="junamt:JUN">JUN</option> 
								<option value="julamt:JUL">JUL</option> 
								<option value="augamt:AUG">AUG</option> 
								<option value="sepamt:SEP">SEP</option> 
								<option value="octamt:OCT">OCT</option> 
								<option value="novamt:NOV">NOV</option> 
								<option value="decamt:DEC">DEC</option> 
								</select>
			         </td>	
					</tr>
					<tr>						
						<td align="center" colspan="4">
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
		<!-- <div id="process"  style="z-index: 1000;background-color:white;height: 100%;width: 100%;top: 0px;left: 0px;position: fixed;opacity:0.8;" hidden=true; >
			<div align="center" style="padding-top: 20%;">
				<img alt="" src="images/process.gif">
			</div>
		</div> -->
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