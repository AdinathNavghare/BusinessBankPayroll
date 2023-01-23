<%@page import="java.util.ArrayList"%>
<%@page import="payroll.Core.ReportDAO"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Yearly Earning/Deduction Report</title>
<link rel="stylesheet" href="css/screen.css" type="text/css"
	media="screen" title="default" />
	<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />
 <script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>



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
  <script type="text/javascript">
  
  	function validate(){
  		
  		var dt=document.getElementById("date").value;
  		
  		//emptype value
  		
  		var aa=document.getElementById("emptype");
  		var bb=aa.options[aa.selectedIndex].value;
  		
  		//report type value
  		var rr=document.getElementById("rportttype");
  		var rrr=rr.options[rr.selectedIndex].value;
  		
  		
  		//format type value
  		var a=document.getElementById("type");
  		var b=a.options[a.selectedIndex].value;
  		
  		
  		if(dt=="")
  			{
  			alert("Please Select Date !!!");
  			return false;
  			}
  		else if(bb=="")
  			{
  			alert("Please Select Employee Type !!!");
  			return false;
  			}
  		else if(rrr=="")
			{
			alert("Please Select Report Type !!!");
			return false;
			}
  		else if(b=="")
  			{
  			alert("Please Select Report Format !!!");
  			return false;
  			}
  		else
  			{
  			document.getElementById("employeetype").value=bb;
  			document.getElementById("rpttype").value=rrr;
  			document.getElementById("reporttype").value=b;
  			return true;
  			}
  		
  	}
  </script>

</head>
<body style="overflow: hidden;"> 
<%-- <%
String pageName = "YearlyEarningDeduction.jsp";
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


<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:scroll; max-height:80%; ">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Yearly Earning/Deduction Report </h1>
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

			<form name="YearlyEarDedReportForm" action="ReportServlet" method="post" onSubmit="return validate()">
			<table border="1" id="customers">
				<tr><th> Yearly Earning/Deduction</th>
				</tr>
				
				
				
				<tr class="alt">			
				<td height="120" align="center">
				<input type="hidden" id="action" name="action" value="YearlyEarDedReport"></input>
				<input type="hidden" id="employeetype" name="employeetype" value=""></input>
				<input type="hidden" id="rpttype" name="rpttype" value=""></input>
				<input type="hidden" id="reporttype" name="reporttype" value=""></input>
				<table>
				
				<tr class="alt">

						<td>Select Date</td>
						<td bgcolor="#FFFFFF">
						<input name="date" id="date" readonly="readonly" class="date-picker" placeholder="Click here for Calender"/>
						</td>

				</tr> 
				<tr class="alt">
					<td>Employee Type</td>
						<td>
							<select id="emptype" style="width:100px";>
						    <option value="active">Active</option> 
							<option value="nonactive">Non Active</option>
							<option value="all">All</option>
					 		</select>
					 	</td>
					</tr>
					
					
				<tr class="alt">
					<td>Report Type</td>
						<td>
							<select id="rportttype" style="width:100px";>
						    <option value="Earning">Yearly Earning</option> 
							<option value="Deduction">Yearly Deduction</option>
						<!-- 	<option value="all">All</option> -->
					 		</select>
					 	</td>
					</tr>	
					
					<!-- <tr><td colspan="2"><input type="radio" name="reporttype" value="textfile" checked="checked">PDF File -->
					<tr class="alt">
					<td>Select Type</td>
						<td>
							<select id="type" style="width:100px";>
							 <!-- <option value="textfile">PDF</option>  -->
							<option value="excelfile">EXCEL</option>
							<!-- <option value="ecs">ECS</option> -->
					 		</select>
					 	</td>
					</tr>
						
					<tr>
						
						
						<td colspan="2" align="center">
							<input type="submit" name="before" class="myButton"
							value="Get Report" />
							<!-- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="submit" name="after" class="myButton"
							value="After Finalize" /> --></td>
						
						
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