<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="payroll.Model.Lookup"%>
<%@page import="java.util.ArrayList"%>
<%@page import="payroll.Core.ReportDAO"%>
<%@page import="payroll.Model.EmpOffBean"%>
<%@page import="payroll.Model.EmployeeBean"%>
<%@page import="java.util.Date"%>
<%@page import="payroll.DAO.EmpOffHandler"%>
<%@ page contentType="text/html; charset=iso-8859-1" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Yearly Earning Report(Bank as Whole) With Monthly BreakUp</title>

<!--[if IE]>
<link rel="stylesheet" media="all" type="text/css" href="css/pro_dropline_ie.css" />
<![endif]-->
<title>Yearly Earning Report(Bank as Whole) With Monthly BreakUp</title>
<link rel="stylesheet" href="css/screen.css" type="text/css"
	media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />	

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
	function validation()
	{
		var frmdate = document.getElementById("frmdate").value;
		var todate = document.getElementById("todate").value;
		if(document.getElementById("frmdate").value=="")
		{
			alert("Please Select from Date");
			document.getElementById("frmdate").focus();
			return false;
			
		}
		if(document.getElementById("todate").value=="")
		{
			alert("Please Select to Date");
			document.getElementById("todate").focus();
			return false;
			
		}
		frmdate = frmdate.replace(/-/g,"/");
		todate = todate.replace(/-/g,"/");
		var d1 = new Date(frmdate);	 	
	 	var d2 =new  Date(todate);
	 	
	 	if (d1.getTime() > d2.getTime())
		 {
			   alert("Invalid Date Range!\n FromDate can't be greater than TODate!");
			   document.getElementById("todate").focus();
			   return false;
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
 <%
	String pageName = "payRegister.jsp";
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
		response.sendRedirect("login.jsp?action=0");
	}

%>

<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:scroll; max-height:80%; ">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Yearly Earning Report(Bank as Whole) With Monthly BreakUp</h1>
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
			<form action="ReportServlet?action=YearlyEarBreakUp" method="post" name="add" onsubmit="return  validation()">
				<table border="1" id="customers">
					<tr>
						<th colspan="5">Yearly Earning Report(Bank as Whole) With Monthly BreakUp</th>
					</tr>
					 <tr class="alt">
					  
							<td><font size="4">From Date </font><font color="red" size="3">*</font></td>
		     	<td><input name="frmdate" id="frmdate" type="text"  readonly="readonly" size="25" >&nbsp;<img
							src="images/cal.gif" align="middle"
							style="vertical-align: middle; cursor: pointer;"
							onClick="javascript:NewCssCal('frmdate', 'ddmmmyyyy')" /></td>
							
						  </tr>
						   <tr class="alt">
							<td><font size="4">To Date </font><font color="red" size="3">*</font></td>
		     	<td><input name="todate" id="todate" type="text"  readonly="readonly" size="25">&nbsp;<img
							src="images/cal.gif" align="middle"
							style="vertical-align: middle; cursor: pointer;"
							onClick="javascript:NewCssCal('todate', 'ddmmmyyyy')" />
				</td>
							</tr>
					  
					   	
					   <tr class="alt">
						 <td colspan="4" align="center">
					 	 <input type="submit" value="Get Report" class="myButton" /></td>
					 	 
					  </tr>
			 		</table>
			 	
			  		<br/>
			  		</form>
			   <div id="viewPdf"  hidden="true">
			   </div>
			   <div id="process"  style="z-index: 1000;background-color:white;height: 100%;width: 100%;top: 0px;left: 0px;position: fixed;opacity:0.8;" hidden=true; >
				<div align="center" style="padding-top: 20%;">
				<img alt="" src="images/process.gif">
				</div>
			</div>
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