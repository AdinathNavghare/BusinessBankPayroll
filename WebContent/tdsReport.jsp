<%@page import="java.util.ArrayList"%>
<%@page import="payroll.Core.ReportDAO"%>
<%@ page contentType="text/html; charset=iso-8859-1" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy TDS Applicable Report </title>
<link rel="stylesheet" href="css/screen.css" type="text/css"
	media="screen" title="default" />
	<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>

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

function setYear()
{
	if(document.getElementById("year").value=="")
	{
	alert("Please select year");
	return false;
	}
	var date=document.getElementById("year").value;
	document.getElementById("endyear").value=parseInt(date)+1;	
	
	
}	

	function validation()
	{
		if(document.getElementById("type").value=="0")
			{
			alert("Please Select Report Type");
			document.getElementById("type").focus();
			return false;
			}
		
		
	}
	
	function fn(id)
	{
		document.getElementById(id).value=="";
	}
	
	<% 
	 String date=ReportDAO.getSysDate().substring(7,11);
	 int dateInInt=Integer.parseInt(date);
	 
	%>
</script>

</head>
<body onload="setYear()" style="overflow: hidden;"> 
<%--  <%
	String pageName = "tdsReport.jsp";
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
		<h1>TDS Applicable List </h1>
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
			
			<form name="emplistForm" action="ReportServlet?action=tdslist" method ="post" onsubmit="return validation()">
			<table border="1" id="customers" align="center">
			<tr>
				<th width="400">TDS Applicable List</th>
			<tr>
			<tr class="alt">
				<td height="100" align="center">
				
				<table align="center">
					<tr class="alt" height="30" >
                  <!--    <input type="hidden" id="action" name="action" value="emplist"></input> -->
						<td ><b>Financial Year From : April-</b></td>
						
							<td > <select style="width: 120px" name="year" id="year" onChange="setYear()" >
							
							<option value="">Select</option>	
						<option value="<%=dateInInt+1%>"><%=dateInInt+1%></option>
						<option value="<%=dateInInt%>" selected><%=dateInInt%></option>
						<option value="<%=dateInInt-1%>" ><%=dateInInt-1%></option>
						<option value="<%=dateInInt-2%>" ><%=dateInInt-2%></option>
						<option value="<%=dateInInt-3%>" ><%=dateInInt-3%></option>
						<option value="<%=dateInInt-4%>" ><%=dateInInt-4%></option>
						<option value="<%=dateInInt-5%>" ><%=dateInInt-5%></option>
						
							
							</select>
							</td>
						
						

					</tr>
					
					<tr class="alt" height="30" >
                 
						<td ><b>Financial Year To : March-</b></td>
						
							<td > <input style="width: 120px;" type="text" readonly="readonly" name="endyear" id="endyear" value="">
							
							
							</td>
						
						

					</tr>
				
					
					<tr>
			<td colspan="2" align="center">		<input type="submit" value="submit"/> </td>
						<!-- <td align="center"><input type="submit" name="before" id='before'	value="Before Finalize" onclick="fn('after')"/></td>
						<td align="center"><input type="submit" name="after" id='after'	value="After Finalize" onclick="fn('before')"/></td> -->
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