<%@page import="payroll.Core.ReportDAO"%>
<%@ page contentType="text/html; charset=iso-8859-1" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Yearly Salary Details </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />


<script type="text/javascript" src="js/datetimepicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<script src="js/filter.js"></script>
<script type="text/javascript">

/* function getFilt()
{
	var month=document.getElementById("date").value;
	if(month == "")
	{
		alert("Please Select Date between a year");	
	}
	else
	{
		getFilter('toyearlysaldetails',month);
	}
}
function addFilt()
{
	var month=document.getElementById("date").value;
	if(month == "")
	{
		alert("Please Select Date between a year");	
	}
	else
	{
		addMoreEmp('toyearlysaldetails',month);
	}
}
 */
function paycal()
{
	
	if(numList != "")
	{
		document.getElementById("list").value=numList;
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
		 
			 
			
			url="ReportServlet?action=getSalDetails&emplist="+numList;
			xmlhttp.onreadystatechange=function()
			{
			if(xmlhttp.readyState==4)
				{
					var response = xmlhttp.responseText;
					
					initAll();
				}

			};
			
			xmlhttp.open("post",url, true);
			xmlhttp.send();
		 
	}
	else
	{
		alert("No Employee is Selected");
		return false;
	}
	
}


</script>
<script>
	jQuery(function() {
		$("#empNo").autocomplete("list.jsp");
	});
</script>
<script>
	jQuery(function() {
		$("#empNo1").autocomplete("list.jsp");
	});
</script>


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
<div id="content-outer">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Salary Details</h1>
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
			<form name="form10" action="ReportServlet" method="post" onsubmit="return paycal()">
				<table border="1" id="customers">
					<tr>
						<th>Yearly Salary Report</th>
					<tr>
					<tr class="alt">
						<td height="80" align="center">
							<table>
								<tr class="alt">
									<td align="center">Select Date
									<input name="date" id="date" readonly="readonly" class="date-picker" placeholder="Click here for Calender"/>

									<input type="hidden" id="action" name="action" value="getSalDetails"></input> 
									<input type="hidden" id="list" name="list">
									</td>
								</tr>
								
								<tr class="alt" align="center">
								<td>
									<div id="displayDiv" style="height: 300px"></div>
									<div id="countEMP">0 Employees Selected</div>
									<input type="button" class="myButton" value="Cancel All" onclick="cancelAll()">
									<input type="button" class="myButton" value="Select Employees" onclick="getFilt('toyearlysaldetails')">
									<!-- <input type="button" class="myButton" value="Add More Employee" onclick="addFilt()"> -->
								</td>
								</tr>
					 
								<tr>
									<td colspan="4" align="center">
										<input type="submit" class="myButton" value="Get Report"/>&nbsp;&nbsp;&nbsp;
										<input type="reset" class="myButton" value="Clear" />
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