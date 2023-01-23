<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="payroll.Core.Calculate"%>
<%@page import="payroll.Core.ReportDAO"%>
<%@page import="payroll.DAO.*"%>
<%@page import="payroll.Model.*"%>
<%@page import="java.util.ArrayList"%>
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


<script type="text/javascript" src="js/datetimepicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<script>
	jQuery(function() {
		$("#EMPNO").autocomplete("list.jsp");
	});
</script>




<script type="text/javascript">



function validate()
{
	debugger;
  var fromDate=document.getElementById("frmdate").value;
   var toDate=document.getElementById("todate").value;
   fromDate = fromDate.replace(/-/g,"/");
	toDate = toDate.replace(/-/g,"/");
   
	var p=parse.Integer(todate)-parse.Integer(fromdate);
	
	if(p>3)
		{
		alert("hhhh"+p);
		alert("Income formate upto 3 years");
		}
   
	var EMPNO = document.getElementById("EMPNO").value;
    
	if (document.getElementById("EMPNO").value == "") {
		alert("Please Insert Employee Name");
		document.getElementById("EMPNO").focus();
		return false;
	}
	var atpos=EMPNO.indexOf(":");
	if (atpos<1)
	  {
	  alert("Please Select Correct Employee Name");
	  document.getElementById("EMPNO").focus();
	  return false;
	  }
	
	
 if(document.getElementById("frmdate").value == "")
	 {
	 alert("please enter the fromdate");
     document.getElementById("frmdate").focus();
     return false;
	 
	 }
    
   if( document.getElementById("todate").value=="")
   {
	   alert("please enter the todate");
	      document.leaveLedgerform.todate.focus();
	      return false;
	   }
   var d1 = new Date(fromDate);
 	
 	var d2 =new  Date(toDate);
 	
 if (d1.getTime() > d2.getTime())
      {
	   alert("Invalid Date Range!\n Fromdate Date can't be greater than TODate!");
	   document.leaveLedgerform.todate.focus();
	   return false;
	   }
 return true;
 
}


</script>



</head>
<body style="overflow: hidden;"> 
<%	String pageName = "SalaryIncomeCertificate.jsp";
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
	}%>
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:scroll; max-height:80%; ">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Salary Certificate Report</h1>
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
			<form  name="leaveLedgerform" action="ReportServlet?action=salarycerti" method="post" onsubmit="return validate()">
				<table id="customers" width="553" align="center">
				
				   <th colspan="5"> Salary Certificate</th>
				   <tr class="alt">
						<td colspan="5" align="center">Select Employee Number :  <input type="text" name="EMPNO" id="EMPNO" onClick="showHide()" title="Enter Employee No" size="30"></td>
						<!-- <td colspan="4" align="left"></td> -->
				   </tr>
				   <tr class="alt">
				   		<!-- <td>From Date</td><td><input name="frmdate" size="20" id="frmdate" type="text" onBlur="if(value=='')" readonly="readonly">&nbsp;<img src="images/cal.gif" align="absmiddle" style="vertical-align:middle; cursor:pointer;" onClick="javascript:NewCssCal('frmdate', 'ddmmmyyyy')" /></td>
				   		<td>To Date</td><td><input name="todate"  size="20" id="todate" type="text" onBlur="if(value=='')" readonly="readonly">&nbsp;<img src="images/cal.gif" align="absmiddle" style="vertical-align:middle; cursor:pointer;" onClick="javascript:NewCssCal('todate', 'ddmmmyyyy')" /></td> -->
				   		
				   		<td style="text-align: center;" colspan="" >From Year</td>
				   		<td style="width:100px;">
				   		
						   		<%
						   		     String datee=ReportDAO.getSysDate() ;
						   			System.out.println(datee);
						   			String sd[]=datee.split("-");
						   			System.out.println(sd[2]);
									Date dt = new Date();
									SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
									String[] date = sdf.format(dt).split("-");
									int year = Integer.parseInt(date[2]);
									int ftdate1=Integer.parseInt(sd[2]);
									
								%>	
							   	
							   <select width="100" id = "frmdate" name="frmdate">
	                 		<%
	                 			for(int i= ftdate1-3;i<= ftdate1;i++)
	                 			{
	                 				if(i == ftdate1 )
	                 				{
	                 		%>
	                 					<option value="<%=i %>" selected="selected"><%=i %></option>
	                 		<%
	                 				}
	                 				else
	                 				{
	                 		%>
	                 					<option value="<%=i %>"><%=i %></option>
	                 		<%
	                 				}
	                 			}
	                 		%>
	                 		</select>
	                 		</td>
	                 		
	                 		<td style="text-align: center;" colspan="2" >To Year</td>
				   		<td>
				   		
						   		<%
									Date dt1 = new Date();
									SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy");
									String[] date1 = sdf1.format(dt).split("-");
									int year1 = Integer.parseInt(date[2]);
									
								%>	
							   	
							   <select width="100" id = "todate" name="todate">
	                 		<%-- <%
	                 			for(int i= 1990;i<=ftdate1;i++)
	                 			{
	                 				if(i == ftdate1 )
	                 				{
	                 		%> --%>
	                 					<option value="<%=ftdate1 %>" selected="selected"><%=ftdate1 %></option>
	                 		<%-- <%
	                 				}
	                 				else
	                 				{
	                 		%>
	                 					<option value="<%=i %>"><%=i %></option>
	                 		<%
	                 				}
	                 			} 
	                 		%>--%>
	                 		</select>
	                 		</td>
				   		
				   		
				   		
				   </tr>
				   <tr class="alt"><td colspan="5" align="center"> <input type="submit" value="Get Report"/> &nbsp;&nbsp;<input type="reset" value="Cancel"/></td></tr>
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