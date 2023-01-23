<%@page import="java.util.ArrayList"%>
<%@page import="payroll.Model.EmpOffBean"%>
<%@page import="payroll.DAO.EmpOffHandler"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="payroll.Core.ReportDAO"%>
    
    <%@page import="payroll.DAO.LookupHandler"%>
	<%@page import="payroll.Model.Lookup"%>
    
<%@page errorPage="error.jsp" isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copyPayRegister </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<script type="text/javascript">

var xmlhttp;
var url="";
if(window.XMLHttpRequest)
	{
	xmlhttp = new XMLHttpRequest;
}
else
{
	xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
}

	function ViewReport1()
	{
		var typ = document.getElementById("Desgn").value;
		var reporttype = document.getElementById("ReportType").value;
		var employeetype = document.getElementById("emptype").value;
		var branchcode = document.getElementById("branch").value;
		
		if(typ==0){
			alert("please Select Report Format");
			return false;
		}
		
		if (document.getElementById("date").value=="")
			{
			alert("please Select Month");
			return false;
			}
		if(reporttype==0){
			alert("please Select Report Type");
			return false;
		}
		
		document.getElementById("viewPdf").hidden=true;
		document.getElementById("process").hidden=false;
		var format;
		var date ="01-"+document.getElementById("date").value;
		//var btn = document.getElementById("button").value;
		var frmt = document.getElementById("Excel").checked;
		
		
		var txt= document.getElementById("txt").checked;
		
		if(txt)
		{
			if(document.getElementById("Desgn").value=="G" || document.getElementById("Desgn").value=="I")
				{
				alert("Please Choose the Income And Deduction Type for TXT Format..!!");
				document.getElementById("process").hidden=true;
				document.getElementById("viewPdf").hidden=false;
				return false;
				}
			else{
			format="txt";
			url="ReportServlet?action=newpayreg&date="+date+"&typ="+typ+"&frmt="+format+"&reptype="+reporttype;
			}
		}
		
		else{
		   if(frmt){
		    	format="xls";
		   }else {
			format="pdf";
		  }
		  // url="ReportServlet?action=newpayreg&date="+date+"&typ="+typ+"&frmt="+format+"&reptype="+reporttype;
		  //   url="ReportServlet?action=newpayreg&date="+date+"&typ="+typ+"&frmt="+format+"&reptype="+reporttype+"&emptype="+employeetype;
		   url="ReportServlet?action=newpayregaccount&date="+date+"&typ="+typ+"&frmt="+format+"&reptype="+reporttype+"&emptype="+employeetype+"&branch="+branchcode;
		}
		xmlhttp.onreadystatechange=function()
		{
			if (xmlhttp.readyState==4 && xmlhttp.status==200)
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
	function ViewReport2()
	{
		var typ = document.getElementById("Desgn").value;
		if(typ==0){
			alert("please Select Report Type");
			return false;
		}
		if (document.getElementById("date").value=="")
		{
		alert("please Select Month");
		return false;
		}
		document.getElementById("viewPdf").hidden=true;
		document.getElementById("process").hidden=false;
		
		var format;
		var date = "1-"+document.getElementById("date").value;
		var btn = document.getElementById("button1").value;
		var frmt = document.getElementById("Excel").checked;
		if(frmt){
			format="xls";
		}else {
			format="pdf";
		}
		url="ReportServlet?action=newpayreg&date="+date+"&btn="+btn+"&typ="+typ+"&frmt="+format;
		
		xmlhttp.onreadystatechange=function()
		{
			if (xmlhttp.readyState==4 && xmlhttp.status==200)
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

	function ViewReport3()
	{
		var typ = document.getElementById("Desgn").value;
		if(typ==0){
			alert("please Select Report Type");
			return false;
		}
		if (document.getElementById("date").value=="")
		{
		alert("please Select Month");
		return false;
		}
		document.getElementById("viewPdf").hidden=true;
		document.getElementById("process").hidden=false;
		var format;
		var date ="1-"+ document.getElementById("date").value;
		var btn = document.getElementById("button2").value;
		var frmt = document.getElementById("Excel").checked;
		if(frmt){
			format="xls";
		}else {
			format="pdf";
		}
		url="ReportServlet?action=newpayreg&date="+date+"&btn="+btn+"&typ="+typ+"&frmt="+format;
		
		xmlhttp.onreadystatechange=function()
		{
			if (xmlhttp.readyState==4 && xmlhttp.status==200)
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
<body style="overflow:hidden;"> 
 <%
	String pageName = "PayRegAccountWise.jsp";
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


<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:auto; max-height:83%; ">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Pay Register </h1>
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
			<!-- <a href="tranMaintainences.jsp" align="left"><b>Edit Salary Details</b></a> -->
			<center>
		<table border="1" id="customers">
		
					<tr class="alt">

						<th>Select Date</th>
						<td bgcolor="#FFFFFF">
						
						 <input name="date" id="date" readonly="readonly" class="date-picker" placeholder="Click here for Calender"/>
						
						</td>
						<td>Report Format </td>
						<td>
							<select name="Desgn" id="Desgn">  
      					  	
      					  <!--
      					  <option value="0">Select</option>
      					   	<option value="G">General</option>
      					  	<option value="I">Income Heads</option> -->
      					  	<option value="ID">Income & Deduction</option> 
     			      		</select>
     			      	</td>	
     			      	<td>Report Type </td>
     			      	<td>
     			      	<select name="ReportType" id="ReportType">  
      					  	<option value="0">Select</option>
      					  	<option value="paytran">Pre-Finalize</option>
      					  	<option value="paytran_stage">Finalize</option>
      					  	<option value="ytdtran">Release</option> 
     			      		</select>
     			      		</td>
     			      		
     			      			<td>Branch</td>
     			      		<td>
     			      			<select name="branch" id="branch" >  
      					 			<option value="0">All</option>  
			    						<%
			    						  ArrayList<EmpOffBean> ebn = new ArrayList<EmpOffBean>();
			    						  EmpOffHandler eh = new EmpOffHandler();
			    						  ebn = eh.getprojectCode();
			 								for(EmpOffBean eopbn : ebn)
			 								{%>
			      									<option value="<%=eopbn.getPrj_srno()%>"><%=eopbn.getSite_name()%></option>  
			     					 	  <%}%>
			     				</select>
     			      		</td>
     			      		
     			      		
     			      		<td>Emp Type</td>
     			      		<td>
     			      			<select name="emptype" id="emptype" >  
      					 			<option value="0">Select</option>  
			    						<%
			  								ArrayList<Lookup> result=new ArrayList<Lookup>();
			    							LookupHandler lkhp= new LookupHandler();
			    							result=lkhp.getSubNewLKP_DESC("EMP_CATEGORY");
			 								for(Lookup lkbean : result)
			 								{%>
			      								<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
			     					 	  <%}%>
			     					 	  <option value="4">NON PERMANANT</option>
			     					 	  <option value="5">ALL</option>
			     				</select>
     			      		</td>
     			      		
     			      		
						<td>
							<input type="radio" name="format" id="Excel" value="Excel" >&nbsp;Excel&nbsp;
							<input type="radio" name="format" id="Pdf" value="Pdf" checked>&nbsp;&nbsp;Pdf
							<input type="radio" name="format" id="txt" value="txt">&nbsp;&nbsp;Txt					
						</td>	
						<td align="center"><input type="submit"	value="GET REPORT" onclick="ViewReport1()"/>
											</td>
						</tr>
				
		</table>
</center>
<br>
<div id="viewPdf"  hidden="true">
 </div>
			   <div id="process"  style="z-index: 1000;background-color:white;height: 100%;width: 100%;top: 0px;left: 0px;position: fixed;opacity:0.8;" hidden=true; >
				<div align="center" style="padding-top: 20%;">
				<img alt="" src="images/process.gif">
				</div>
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