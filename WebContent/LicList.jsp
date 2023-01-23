<%@page import="java.util.ArrayList"%>
<%@page import="payroll.Core.ReportDAO"%>
<%@ page contentType="text/html; charset=iso-8859-1" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Lic list</title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />


<script type="text/javascript" src="js/datetimepicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>

<script type="text/javascript">



</script>

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
function validate()
{
	
	if(document.getElementById("date").value=="")
	{
		alert("Please Select Date...!!!");
		document.getElementById("date").focus;
		return false;
	}
	else if(document.getElementById("licstatus").SelectedIndex==""||document.getElementById("licstatus").SelectedIndex==0)
	{
		alert("Please Select Status...!!!");
		document.getElementById("licstatus").focus;
		return false;
	}
	else
	{
		return true;
	}
	
}
</script>

 </head>
 <%
 
 String pageName = "LicList.jsp";
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
 
<body style="overflow: hidden;"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>LIC List</h1>
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
			<!-- <h2>Profession Tax Statement</h2> -->
			<form name="professionTax" action="ReportServlet" method="post" onsubmit="return  validate()">
<table border="1" id="customers">
	<tr>
		<th>LIC LIST</th>
		<tr>
			<tr class="alt">
				<td height="120" align="center">
				<table>
					<tr class="alt">
					
						<td>Select Date</td>
						<input type="hidden" id="action" name="action" value="liclist"></input>
						<td bgcolor="#FFFFFF">
						<input name="date" id="date" readonly="readonly" class="date-picker" placeholder="Click here for Calender"/>
						</td>
					</tr>
					
					<tr class="alt">
					
						<td>Select Status</td>
						<td bgcolor="#FFFFFF">
						<select id="licstatus" name="licstatus">
						<option value="0" selected="selected">SELECT</option>
						<option value="active">ACTIVE</option>
						<option value="nonactive">NON ACTIVE</option>
						 </select>
						</td>
					</tr>
					 <tr>
					 <td colspan="2">&nbsp;</td>
					 </tr>
					<tr>
						<td colspan="2" align="center">
							<input type="submit" name="before" class="myButton"
							value="Get Report" title='Click here to get ESIC Report'/>
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