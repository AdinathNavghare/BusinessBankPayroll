
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="payroll.DAO.*"%>
<%@page import="payroll.Core.ReportDAO"%>
<%@page import="payroll.Model.*"%>
<%@page import="java.util.ArrayList"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy DTS3 </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />
<script src="js/filter.js"></script>

<script type="text/javascript" src="js/datetimepicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>



<script src="js/MONTHPICK/jquery.js"></script>
    <script type="text/javascript" src="js/MONTHPICK/jquery-ui.min.js"></script>
    <link rel="stylesheet" type="text/css" media="screen" href="css/MONTHPICK/jquery-ui.css">
    
    <%
    int i=0;
    String date1=ReportDAO.getSysDate();
    String year[]=date1.split("-");
    String thisyear=year[1]+"-"+year[2];
    %>
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
            //$(this).datepicker("setDate", new Date(year, month, 1));
            month=tomonth(month);
            document.getElementById("date").value=month+"-"+year;
            $(':focus').blur();
            month=document.getElementById("date").value;
            }/* ,
            beforeShow : function(input, inst) {
                var datestr;
                if ((datestr = $(this).val()).length > 0) {
                    year = datestr.substring(datestr.length-4, datestr.length);
                    month = jQuery.inArray(datestr.substring(0, datestr.length-5), $("#date").datepicker("option", "monthNamesShort"));
                    alert(month);
                    $("#date").datepicker("option", "defaultDate", new Date(year, month, 1));
                    $("#date").datepicker("setDate", new Date(year, month, 1));
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





<script>
	jQuery(function() {
		$("#EMPNO").autocomplete("list.jsp");
	});
</script>

<script>
	jQuery(function() {
		$("#EMPNO1").autocomplete("list.jsp");
	});
</script>


<script type="text/javascript">

/* function getFiltForIncometax()
{
	var month=document.getElementById("date").value;
	if(month == "")
	{
		alert("Please Select Date between a year");	
		
	}
	else
	{
		var type;
		//getFilter('toIncometaxreport',month);
		var selection=document.getElementById("sheet");
		type=selection.options[selection.selectedIndex].value;
		if(type==""||type=="0"||type==0)
			{
			alert("Please Select Report Type");
			return false;
			}
		else
			{
			getFilterForIncomeTax('toIncometaxreport',month,type);
			
			}
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
		addMoreEmp('toIncometaxreport',month);
	}
}

 */function paycal()
{
	
		
		document.getElementById("list").value=numList;
		var sheetval=document.getElementById("sheet").value;
		var month=document.getElementById("date").value;
		var u_month=month.split("-");
		var umno=getmonth(month);
		//alert(umno);
		//alert("user selected..."+month);
		var thisyear=document.getElementById("thisyear").value;
		var c_month=thisyear.split("-");
		var cmno=getmonth(thisyear);
		//alert(cmno); 
		var uyear=parseInt(u_month[1]);
		//alert(uyear);
		var cyear=parseInt(c_month[1]);	
		//alert(cyear);
		//alert("current date..."+thisyear);
		if(numList==""){
			alert("please select employee !");
			return false;
		}
		else if(document.getElementById("sheet").value==0)
			{
			alert("please select Report type !");
			return false;
			}
		 else if((document.getElementById("sheet").value=="F16") && (uyear>=cyear && umno>3))
			{
				alert("please select Previous Year for F16 !");
				return false;
			}
	/* 	else if(((sheetval=="PrintTaxSheet")||(sheetval=="yearlyearning")) && ((parseInt(uyear)==(parseInt(cyear)+1) && umno>3)||(((parseInt(uyear)-parseInt(cyear)>=1) && umno<3)||(parseInt(uyear)-parseInt(cyear)>=1))||((parseInt(uyear)==parseInt(cyear) && parseInt(umno)>3)||((parseInt(uyear)-parseInt(cyear)>=1) && umno>=1))))
		{
			alert("please select Date Between Current Year for "+sheetval+" !");
			return false;
		} 
		else if(((sheetval=="PrintTaxSheet")||(sheetval=="yearlyearning")) && ((uyear>(cyear+1) && umno>3)||((parseInt(uyear)-parseInt(cyear)>=1) && umno<=3)))
		{
			alert("please select Date Between Current Year for "+sheetval+" !");
			return false;
		} else if(((sheetval=="PrintTaxSheet")||(sheetval=="yearlyearning")) && (((parseInt(uyear)-parseInt(cyear))<0)&& umno<=3 ))
			{
			alert("please select Date Between Current Year for "+sheetval+" !");
			return false;
			}
	 */	
	 
		/* else if(((sheetval=="PrintTaxSheet")||(sheetval=="yearlyearning")) && ((parseInt(uyear)==(parseInt(cyear)+1) && umno>3)||(((parseInt(uyear)-parseInt(cyear)>=1) && umno<3)||(parseInt(uyear)-parseInt(cyear)>=1))||((parseInt(uyear)==parseInt(cyear) && parseInt(umno)>3)||((parseInt(uyear)-parseInt(cyear)>=1) && umno>=1))))
		{
			alert("please select Date Between Current Year for "+sheetval+" !");
			return false;
		}  */
		else if(((sheetval=="PrintTaxSheet")||(sheetval=="yearlyearning")) && ((parseInt(uyear)==(parseInt(cyear)+1)||(((parseInt(uyear)-parseInt(cyear)>=1) && umno<3)||(parseInt(uyear)-parseInt(cyear)>=1))||((parseInt(uyear)-parseInt(cyear)>=1) && umno>=1))))
		{
			alert("please select Date Between Current Year for "+sheetval+" !");
			return false;
		} 
		else if(((sheetval=="PrintTaxSheet")||(sheetval=="yearlyearning")) && ((uyear>(cyear+1) && umno>3)||((parseInt(uyear)-parseInt(cyear)>=1) && umno<=3)))
		{
			alert("please select Date Between Current Year for "+sheetval+" !");
			return false;
		} else if(((sheetval=="PrintTaxSheet")||(sheetval=="yearlyearning")) && (((parseInt(uyear)-parseInt(cyear))<0)&& umno<=3 ))
			{
			alert("please select Date Between Current Year for "+sheetval+" !");
			return false;
			}
	
		
		
		/* else
			{
			alert("this is ajax");
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
		 
			 
			
			url="ReportServlet?action=Incometaxreport&emplist="+numList;
			xmlhttp.onreadystatechange=function()
			{
				alert("this is ajax.."+xmlhttp.readyState);
			if(xmlhttp.readyState==4)
				{
					var response = xmlhttp.responseText;
					
					initAll();
				}

			};
			
			xmlhttp.open("get",url, true);
			xmlhttp.send();
		  
	} */
		
		
		
		
		function getmonth(date)
		{
			var tempmonth=date.split("-");
			//alert(tempmonth[0]);
			var year=parseInt(tempmonth[1]);
			if(tempmonth[0]=="JAN"||tempmonth[0]=="jan"||tempmonth[0]=="Jan")
				return 1;
			else if(tempmonth[0]=="FEB"||tempmonth[0]=="feb"||tempmonth[0]=="Feb")
				return 2;
			else if(tempmonth[0]=="MAR"||tempmonth[0]=="mar"||tempmonth[0]=="Mar")
				return 3;
			else if(tempmonth[0]=="APR"||tempmonth[0]=="apr"||tempmonth[0]=="Apr")
				return 4;
			else if(tempmonth[0]=="MAY"||tempmonth[0]=="may"||tempmonth[0]=="May")
				return 5;
			else if(tempmonth[0]=="JUN"||tempmonth[0]=="jun"||tempmonth[0]=="Jun")
				return 6;
			else if(tempmonth[0]=="JUL"||tempmonth[0]=="jul"||tempmonth[0]=="Jul")
				return 7;
			else if(tempmonth[0]=="AUG"||tempmonth[0]=="aug"||tempmonth[0]=="Aug")
				return 8;
			else if(tempmonth[0]=="SEP"||tempmonth[0]=="sep"||tempmonth[0]=="Sep")
				return 9;
			else if(tempmonth[0]=="OCT"||tempmonth[0]=="oct"||tempmonth[0]=="Oct")
				return 10;
			else if(tempmonth[0]=="NOV"||tempmonth[0]=="nov"||tempmonth[0]=="Nov")
				return 11;
			else if(tempmonth[0]=="DEC"||tempmonth[0]=="dec"||tempmonth[0]=="Dec")
				return 12;
			
		}
	
}

</script>
<%
	String pageName = "taxSheet.jsp";
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

<%
Date date =new Date();
SimpleDateFormat frmdate = new SimpleDateFormat("dd-MMM-yyyy");
String dt=frmdate.format(date);
%>


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
		<h1>Tax Report </h1>
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
			<form  name="leaveLedgerform" action="ReportServlet"  method="get" onsubmit="return paycal()">
				<table id="customers" width="553" align="center">
				
				   <tr> <th colspan="4">Tax Report</th></tr>
					<tr class="alt">
					
					<tr class="alt"><td>Select Year</td>
					<td>
					
<%-- 					<input name="date" size="20" id="date" value="<%=dt %>" type="text" onBlur="if(value=='')">&nbsp;<img src="images/cal.gif" align="absmiddle" style="vertical-align:middle; cursor:pointer;" onClick="javascript:NewCssCal('date', 'ddmmmyyyy')" />
 --%>					
 						<input name="date" id="date" readonly="readonly" class="date-picker" placeholder="Click here for Calender"/>
 						<%-- <select id="date"  style="width: 100px">
 						<option value =<%=thisyear%> selected="selected"><%=thisyear%></option> 
 						<%for (i=thisyear;i>=2015;i--){ %>
 						<option value ="<%=	i	%>" ><%=	i	%></option>
 						<%} %>
 						</select> --%>
 						</td>
					<td>Report Type</td>
					<input type="hidden" id="list" name="list">
					<input type="hidden" id="thisyear" value=<%=thisyear %>>
					
					<td>
					<select name="sheet" id="sheet">
					     <option value="0" selected="selected">Select</option>
						 <option value="PrintTaxSheet">TaxSheet</option>
						  <option value="F16">Form 16</option>
						  <option value="yearlyearning">Yearly Earning</option>
					</select>
					</td>
					</tr>
					
					<tr class="alt" align="center">
								<td colspan="4">
									<div id="displayDiv" style="height: 300px"></div>
									<div id="countEMP">0 Employees Selected</div>
									<input type="button" value="Cancel All" onclick="cancelAll()">
									<input type="button" value="Select Employees" onclick="getFilt('toIncometaxreport')">
									<!-- <input type="button" value="Add More Employee" onclick="addFilt()"> -->
								</td>
								</tr>
					
					
					
					
					
					
					<tr class="alt">
					<td colspan="4" align="center"> 
					<input type="submit" value="GetReport"/> &nbsp;&nbsp;<input type="reset" value="Cancel"/> <input type="hidden" name="action" value="Incometaxreport"> </td></tr>
			
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