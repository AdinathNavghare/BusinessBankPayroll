<%@page import="java.util.ArrayList"%>
<%@page import="payroll.Core.ReportDAO"%>
<%@ page contentType="text/html; charset=iso-8859-1" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>NON-CONTRIBUTE SERVICE</title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />


<script type="text/javascript" src="js/datetimepicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>

<script type="text/javascript">

jQuery(function() {
	$("#EMPNO").autocomplete("searchList.jsp");
});


</script>

<script>
	/* jQuery(function() {
          $("#EMPNO").autocomplete("list.jsp");
	}); */
	function validation()
	{
		//var a=document.getElementById("type").value;
		//alert(a);
  		//var b=a.options[a.selectedIndex].value;
  		//document.getElementById("reporttype").value=b;
  		
  		
  		
		var flag = true;
		if(document.getElementById("EMPNO").value=="")
		{
			alert("Please Select Employee");
			document.getElementById("EMPNO").focus();
			flag = false;
		}
		if(document.getElementById("date").value=="")
		{
			alert("Please Select Date");
			document.getElementById("date").focus();
			flag = false;
		}
		
		/* if(document.getElementById("table").value=="0")
		{
			alert("Please Select Report Status");
			document.getElementById("table").focus();
			flag = false;
		} */
		
		if(flag == true)
		{
			ViewReport();
		}
		
		
	}
	
	var xmlhttp;
	var url ="";
	if(window.XMLHttpRequest)
	{
		xmlhttp = new XMLHttpRequest;
	}
	else
	{
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	
	function ViewReport() 
	{
		 
		document.getElementById("process").hidden=false;
		var date = "01-"+document.getElementById("date").value;
		var EMPNO = document.getElementById("EMPNO").value;
		console.log(date+"   "+EMPNO);
		//var report = document.getElementById("type").value;
		url="ReportServlet?action=nonContributeServ&empno="+EMPNO+"&date="+date;
		
		xmlhttp.onreadystatechange=function()
		{
			if (xmlhttp.readyState==4 && xmlhttp.status==200)
			{
				var response=xmlhttp.responseText;
	        	document.getElementById("viewPdf").innerHTML=response;
	        	document.getElementById("viewPdf").hidden=false;
	        	document.getElementById("process").hidden=true;
			}
		};
		xmlhttp.open("GET", url, true);
		xmlhttp.send();
	}
	
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


 </head>
 <%
 
 String pageName = "esicList.jsp";
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
		<h1>NON-CONTRIBUTE SERVICE</h1>
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
									<form name="NonContServ" action="ReportServlet" method="post" onsubmit="return  validate()">
						<table border="1" id="customers">
							<tr>
								<th>NON-CONTRIBUTORY SERVICES</th>
								<tr>
									<tr class="alt">
										<td height="120" align="center">
										<table>
										
										  <tr class="alt">
												<td colspan="2" align="center">Select Employee Number :  <input type="text" name="EMPNO" id="EMPNO" onClick="showHide()" title="Enter Employee No" size="30"></td>
												<input type="hidden" name="action" id="action" value="NonContributeServ"  title="action">
												<!-- <td colspan="4" align="left"></td> -->
										   </tr>
											<tr class="alt">
											
												<td  align="center">Select Date :  <input name="date" id="date" readonly="readonly" class="date-picker"  placeholder="Click here for Calender"/></td>
												
											</tr>
											 <tr>
											 <td colspan="2">&nbsp;</td>
											 </tr>
											 <tr class="alt">
													 <td colspan="4" align="center">
												 	 <input type="button" value="Get Report" class="myButton" onclick="validation()" /></td>
												  </tr>
										</table>
						
									  </td>
									</tr>
						</table>
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