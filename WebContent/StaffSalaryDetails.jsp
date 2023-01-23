<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="payroll.DAO.CodeMasterHandler"%>
<%@page import="payroll.Model.CodeMasterBean"%>
<%@page import="payroll.Model.DeductBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="payroll.Model.extra_duty_paymentBean"%>
<%@page import="payroll.DAO.ShiftHandler"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Salary Less Than 25% Report</title>
	<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />

<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />

<!--  date picker script -->
<link rel="stylesheet" href="css/datePicker.css" type="text/css" />
<script src="js/jquery/date.js" type="text/javascript"></script>
<script src="js/jquery/jquery.datePicker.js" type="text/javascript"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<script type="text/javascript" src="js/MONTHPICK/jquery-ui.min.js"></script>
    <link rel="stylesheet" type="text/css" media="screen" href="css/MONTHPICK/jquery-ui.css">
    <script type="text/javascript" src="js/date.js"></script>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />



<script type="text/javascript" src="js/datetimepicker.js"></script>
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

<script>
	jQuery(function() {
		
		$("#EMPNO").autocomplete("list.jsp");
	});
</script>
<script type="text/javascript">
function check(){
	//alert("in else");
	if(document.getElementById("EMPNO").value=="")
	{
		alert("Please Select Employee...!!!");	
		document.getElementById("EMPNO").focus;
		return false;
	}
	
	else if(document.getElementById("frmdate").value=="")
	{
		alert("Please Select Month...!!!");	
		document.getElementById("frmdate").focus;
		return false;
	}
	else 
	{
		//alert("in single emplyee");
		//alert(emp);
		var emp=document.getElementById("EMPNO").value;
		if(emp.includes(":"))
			{
			//alert("include..:");
				var empsplit=emp.split(":");
				
				if(empsplit.length<3)
					{
					alert("Please Select Correct Employee...!!!");
					document.getElementById("EMPNO").value=="";
					document.getElementById("EMPNO").focus;
					return false;
					}
				else
					{
						if(empsplit[2]=="")
							{
							alert("Please Select Correct Employee...!!!");
							document.getElementById("EMPNO").value=="";
							document.getElementById("EMPNO").focus;
							return false;
							}
						else if(/^[a-z]+$/i.test(empsplit[2]))
							{
							alert("Please Select Correct Employee...!!!");
							document.getElementById("EMPNO").value=="";
							document.getElementById("EMPNO").focus;
							return false;
							}
						else
							{
							document.getElementById("getempno").value=empsplit[2];
							return true;
							}
					
					}
			
			}
		else
			{
			alert("Please Select Correct Employee...!!!");
			document.getElementById("EMPNO").value=="";
			document.getElementById("EMPNO").focus;
			return false;
			}
	
	}
	
	
	
}

	
</script>
<%-- <%
	String pageName = "NillReport.jsp";
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
		<h1>Staff Salary Details Report</h1>
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
			
			<form name="staffsalrpt" action="ReportServlet" method ="post"  >
			<table border="1" id="customers" align="center">
			<tr>
				<th>Staff Salary Details</th>
			<tr>
			<tr class="alt">
				<td  align="center">
				
			<table align="center">
			 <tr class="alt" height="30" align="center">
                	
					<td>Select Employee</td>
					 <td align="left">
					<input name="EMPNO" id="EMPNO"  placeholder=" Select Employee "/>
					<input type="hidden" id="getempno" name="getempno" />
					</td> 
				</tr> 
				
				<tr class="alt" height="30" align="center">
                	<input type="hidden" id="action" name="action" value="staffsalaryreport"></input>
                	
					<td>Select Month</td>
					 <td align="left">
					<input name="frmdate" id="frmdate" readonly="readonly" class="date-picker" placeholder="Click here for Calender"/>
					</td> 
				</tr>
				
				<!-- <tr class="alt" height="30" align="center" >
					<td>To Date</td>
					 <td align="left">
					<input name="todate" id="todate" readonly="readonly" class="date-picker" placeholder="Click here for Calender"/>
					</td> 
					
					
					
				</tr>	
				
				<tr id="leave" class="alt" height="30" align="center">	
					<td align="center">Leave Type:
					</td>
					<td><select style="width:250px" name="leavetype" id="leavetype" >
						<option value="0" selected>Select</option>
						<option value="1" >ALL</option>
						<option value="2" >SANCTION</option>
						<option value="3" >PENDING</option>
						<option value="4" >CANCEL</option>
						<option value="5" >NEGATIVE</option>
						<option value="6" >L-MARK</option>
					
						</select>
					</td>
				</tr> -->
				
				<tr  class="alt" height="30" align="center">
				<td colspan="2">
				<input type="submit" class="myButton"   value="GET REPORT" onclick="return check()"/>
						     	
					<input type="hidden" id="tname" name="tname" value="">
						<input type="hidden" id="fname" name="fname" value="">
					<!-- </tr>
				</table> -->
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