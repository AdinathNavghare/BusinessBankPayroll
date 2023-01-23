<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@page import="payroll.Model.EmpExperBean"%>
 <%@page import="java.util.*"%>
 <%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Employee</title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<script src="js/DeleteRow.js"></script>
<script src="js/jquery/jquery-1.4.1.min.js" type="text/javascript"></script>
<script type="text/javascript" src="js/datetimepicker_banking.js"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script type="text/javascript">
function Showhide()
{
	
document.getElementById("empExperEdit").hidden=true;
document.getElementById("addExperience").hidden=false;

	}

function editExpvalidation()
{
	/* 
	if(	document.getElementById("empNo").value==""	||
			document.getElementById("empName").value=="" || 
			document.getElementById("srNo").value=="" || 
			document.getElementById("eOrgNamee").value=="" ||
			document.getElementById("eDesg").value=="" || 
			document.getElementById("eSalary").value=="" )	 
		{
			alert("Please Enter All Data");
			return false;
		
		}

		if( document.getElementById("eFrmDate").value == "")
			{
				alert("Please Ffrom Date");	
				document.getElementById("eFrmDate").focus;
				return false;
			}
		if( document.getElementById("eToDate").value == "")
		{
			alert("Please Ffrom Date");	
			document.getElementById("eToDate").focus;
			return false;
		}
		if( document.getElementById("eEffDate").value == "")
			{
			alert("Please Effective Date");	
			document.getElementById("eEffDate").focus;
			return false;
			}*/
			
		var fromDate=document.getElementById("eFrmDate").value;
		   var toDate=document.getElementById("eToDate").value;
		   fromDate = fromDate.replace(/-/g,"/");
			toDate = toDate.replace(/-/g,"/");
			var d1 = new Date(fromDate);
		  	
		  	var d2 =new  Date(toDate); 
		  	
		  if (d1.getTime() > d2.getTime()) 
		   {
			   alert("Invalid Date Range!\n fromdate Date cannot greater than TODate!");
			   document.getElementById("eToDate").focus();
			   return false;
			   } 
		  
		 
	
}


	 function addExpvalidation()
	{/*
		
			if( document.getElementById("aempNo").value=="" || 
				document.getElementById("aempName").value=="" || 
				document.getElementById("aexOrgName").value=="" || 
				document.getElementById("aexDesg").value=="" ||
				document.getElementById("aexSalary").value== "" 
			 )	
			{
				alert("Please Enter All Data");
				return false;
		
			}
		if(document.getElementById("aexFrmDate").value == "")
			{
				alert("Please Select From Date");	
				document.getElementById("aexFrmDate").focus;
				return false;
			}
		if(document.getElementById("aexToDate").value=="")
			{
				alert("Please Select TO Date");	
				document.getElementById("aexToDate").focus;
				return false;
			}	*/
		
		var fromDate=document.getElementById("aexFrmDate").value;
		   var toDate=document.getElementById("aexToDate").value;
		   fromDate = fromDate.replace(/-/g,"/");
			toDate = toDate.replace(/-/g,"/");
			var d1 = new Date(fromDate);
		  	
		  	var d2 =new  Date(toDate);
		  	
		  if (d1.getTime() > d2.getTime()) 
		   {
			   alert("Invalid Date Range!\n fromdate Date cannot greater than TODate!");
			   document.getElementById("aexToDate").focus();
			   return false;
			   }
	
	} 
		function inputLimiter(e, allow) {
		
			var AllowableCharacters = '';
			if (allow == 'Numbers') {
				AllowableCharacters = '1234567890.';
			}
			var k;
			k = document.all ? parseInt(e.keyCode) : parseInt(e.which);
			if (k != 13 && k != 8 && k != 0) {
				if ((e.ctrlKey == false) && (e.altKey == false)) {
					return (AllowableCharacters.indexOf(String.fromCharCode(k)) != -1);
				} else {
					return true;
				}
			} else {
				return true;
			}
		}



</script>

<% 
String action=request.getParameter("action")!=null?request.getParameter("action"):"addemp"; 


%>
</head>
<body style="overflow: hidden;"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" >
<!-- start content -->
<% if(action.equalsIgnoreCase("addemp"))
{
		%>
	<div id="content"  ><!--  start page-heading -->
			<div id="step-holder">
			
			
			
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">1</div>
			<div class="step-light-left"><a href="employee.jsp" >Employee Detail</a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">2</div>
			<div class="step-light-left"><a href="empQual.jsp">  Qualification </a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">3</div>
			<div class="step-light-left"><a href="empAddress.jsp"> Address </a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">4</div>
			<div class="step-light-left"><a href="empFamily.jsp">Family </a></div>
            <div class="step-light-right">&nbsp;</div>
            <div class="step-no">5</div>
			<div class="step-dark-left"><a href="empExper.jsp">Experience</a></div>
			<div class="step-dark-right">&nbsp;</div>
			<div class="step-no-off">6</div>
			<div class="step-light-left"><a href="officialInfo.jsp">Official Info</a></div>
			<div class="step-light-right">&nbsp;</div>
		
			<div class="step-no-off">7</div>
			<div class="step-light-left"><a href="awardInfo.jsp">Award</a></div>
			<div class="step-light-right">&nbsp;</div>
			
			
			<div class="step-no-off">8</div>
			<div class="step-light-left"><a href="Attachment.jsp">Attachment</a></div>
			<div class="step-light-right">&nbsp;</div>
			
			<div class="step-no-off">9</div>
			<div class="step-light-left"><a href="otherDetail.jsp">Other Detail</a></div>
			<div class="step-light-round">&nbsp;</div>
			<div class="clear"></div>
		</div>
<%
	}
else if(action.equalsIgnoreCase("showemp"))
	{

	%>
<div id="content"  ><!--  start page-heading -->
			<div id="step-holder">
			
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">1</div>
			<div class="step-light-left"><a href="EmployeeServlet?action=employee" >Employee Detail</a></div>
			<div class="step-light-right">&nbsp;</div>
			
			<div class="step-no-off">2</div>
			<div class="step-light-left"><a href="EmployeeServlet?action=empQual">  Qualification </a></div>
			<div class="step-light-right">&nbsp;</div>
			
			<div class="step-no-off">3</div>
			<div class="step-light-left"><a href="EmployeeServlet?action=address"> Address </a></div>
			<div class="step-light-right">&nbsp;</div>
			
			<div class="step-no-off">4</div>
			<div class="step-light-left"><a href="EmployeeServlet?action=Family">Family</a></div>
            <div class="step-light-right">&nbsp;</div>
            
        	 <div class="step-no">5</div>
			<div class="step-dark-left"><a href="EmployeeServlet?action=experience"> Experience </a></div>
			<div class="step-dark-right">&nbsp;</div>
            
           
			
			<div class="step-no-off">6</div>
			<div class="step-light-left"><a href="EmployeeServlet?action=officialInfo">Official Info</a></div>
			<div class="step-light-right">&nbsp;</div>
			
			<div class="step-no-off">7</div>
			<div class="step-light-left"><a href="EmployeeServlet?action=awardInfo">Award</a></div>
			<div class="step-light-right">&nbsp;</div>
			
			<div class="step-no-off">8</div>
			<div class="step-light-left"><a href="Attachment.jsp?action=showemp">Attachment</a></div>
			<div class="step-light-right">&nbsp;</div>
			
			<div class="step-no-off">9</div>
			<div class="step-light-left"><a href="otherDetail.jsp?action=showemp">Other Detail</a></div>
			<div class="step-light-round">&nbsp;</div>
			<div class="clear"></div>
			
			
			
			
		</div>
<% 
	} 
%>
<div id="page-heading">
<h1> Employee Experience Information</h1>
</div>
<!-- end page-heading -->
<%
if(action.equalsIgnoreCase("showemp"))
{
	String srNo = request.getParameter("srno")!=null?request.getParameter("srno"):"0";
	String hidediv = request.getParameter("hidediv")!=null?request.getParameter("hidediv"):"";
	ArrayList<EmpExperBean> empexperList = (ArrayList<EmpExperBean>)session.getAttribute("empexperList");
	Date date = new Date();
	SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
	String dt = format.format(date);
	
	SimpleDateFormat fromformat = new SimpleDateFormat("yyyy-MM-dd");
	fromformat.setLenient(false);
	%>
	<form>
		<table border="0" width="100%" cellpadding="0" cellspacing="0" >
	<tr>
		<th rowspan="3" class="sized"><img
			src="images/shared/side_shadowleft.jpg" width="20" height="200"
			alt="" /></th>
		<th class="topleft"></th>
		<td id="tbl-border-top">&nbsp;</td>
		<th class="topright"></th>
		<th rowspan="3" class="sized"><img
			src="images/shared/side_shadowright.jpg" width="20" height="200"
			alt="" /></th>
	</tr>
	<tr>
		<td id="tbl-border-left"></td>
		<td>
	<form>
	
		<table id="customers" width="100%">
				<tr><td width="15%" style="background-color: #F5F6FA;"  ><font color="black">Employee Code</font></td>
  		  			<td width="20%" ><%=session.getAttribute("empcode")==null?"":session.getAttribute("empcode") %><input type="hidden" name="empNo" id="empNo" readonly="readonly" value="<%=session.getAttribute("empno") %>"></td>
					<td width="15%"  style="background-color: #F5F6FA;"  ><font color="black">Employee Name</font></td>
		  			<td width="50%" ><input type="text" name="empNo" id="empNo" size="40" readonly="readonly" value="<%=session.getAttribute("empname") %>"></td>
		  		</tr>
			</table>
		<table  id="customers" width="100%">
		<tr>
		<th width="5%">SRNO</th>
		<th width="25%">Organization  Name</th>
		<th width="15%">From Date</th>
		<th width="15%">To Date</th>
		<th width="15%">Designation</th>
		<th width="10%">Salary</th>
		<th width="40%"> Edit / Delete</th>
		</tr>
		<%
			if(empexperList.size()!=0)
			{
			for(EmpExperBean empexperbean : empexperList)
			{ 
				//date = fromformat.parse(empexperbean.getTODT());
				%>
				<tr align="center">
				<td width="5%"><%=empexperbean.getSRNO() %></td>
				<td width="25%"><%=empexperbean.getORGNAME() %></td>
				<td width="15%"><%=empexperbean.getFROMDT() %></td>
				<td width="15%"><%=empexperbean.getTODT()%></td>
				<td width="15%"><%=empexperbean.getPOST() %></td>
				<td width="10%"><%=empexperbean.getSALARY() %></td>
				<td width="40%"><input type="button" value="Edit" class="myButton" onClick="window.location='empExper.jsp?action=showemp&hidediv=yes&srno=<%=empexperbean.getSRNO() %>'" />
					&nbsp;<input type="button" class="myButton" value="Delete" onclick="deleteRow('emp','expr','<%=empexperbean.getSRNO() %>','EmployeeServlet?action=experience')"></td>
			</tr>
			

				<% //onClick="window.location='empExper.jsp?action=showemp&srno=<%=empexperbean.getSRNO() >'";
			}
			}else
			{
				%> 
				<tr> <td colspan="7" height="30">There Is No Information</td> </tr>
				<%
			}
	%>
	<tr bgcolor="#1F5FA7"><td align="right" colspan="7"></td></tr>
	</table>
	</form>
	
	
		<form action="EmployeeServlet?action=addExper&exper=addmore" method="post" onSubmit="return addExpvalidation()">
	<%
	if(hidediv.equalsIgnoreCase("yes"))
	{
	%>
	<div id="addExperience"  hidden="true" style="width: 100%;">
	<%}
	else{
	%>
	<div id="addExperience" style="width: 100%;"   >
	<%}%>
	<h2>Add the Experience Information of <%=session.getAttribute("empname") %> </h2>
<table id="customers" width="90%">
				<tr><td width="15%" style="background-color: #F5F6FA;"  ><font color="black">Employee Code</font></td>
  		  			<td width="20%" ><%=session.getAttribute("empcode")==null?"":session.getAttribute("empcode") %><input type="hidden" name="aempNo" id="aempNo" readonly="readonly" value="<%=(session.getAttribute("empno")==null?"":session.getAttribute("empno"))%>"></td>
					<td width="15%"  style="background-color: #F5F6FA;"  ><font color="black">Employee Name</font></td>
		  	
		 <td width="50%" ><input class="form-control"  type="text" name="aempName" id="aempName" size="40"  readonly="readonly" value="<%=(session.getAttribute("empname")==null?"":session.getAttribute("empname")) %>">&nbsp; </td> 		
		  		</tr>
		<tr class="alt">
		<td >Organization Name</td>
		  <td colspan="3" width="30%"><input class="form-control" name="aexOrgName" type="text" id="aexOrgName" size="30" maxlength="30"></td></tr>
	   <tr class="alt">	<td>From Date.</td><td><input class="form-control" type="text" name="aexFrmDate" id="aexFrmDate" readonly="readonly">
	   <img
				src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
				onClick="javascript:NewCssCal('aexFrmDate', 'ddmmmyyyy')" />
	   </td>
			<td>To Date</td><td><input class="form-control" type="text" name="aexToDate" id="aexToDate" readonly="readonly">
			<img
				src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
				onClick="javascript:NewCssCal('aexToDate', 'ddmmmyyyy')" />
			</td></tr>
		<tr class="alt"><td>Designation</td><td><input class="form-control" name="aexDesg" type="text" id="aexDesg" maxlength="30"></td><td>Salary</td>
			<td><input class="form-control" name="aexSalary" type="text" id="aexSalary" maxlength="8" onkeypress="return inputLimiter(event,'Numbers')"></td>
		</tr>
	<tr class="alt"><td colspan="4" align="center"><input type="submit" class="myButton"  value="Submit"  /> 
		&nbsp; &nbsp;
		<input type="reset" class="myButton"  value="Cancel"/></td></tr>
		
				
    </table>
    </div>
<div class="clear">&nbsp;</div>
</form>	
	
	<%

	for(EmpExperBean empexperbean : empexperList)
	{
		if(srNo.equalsIgnoreCase(String.valueOf(empexperbean.getSRNO())))
		{
			
	%>
	<form name="editform" id="editform" action="EmployeeServlet?action=editempExper" method="post" onSubmit="return editExpvalidation()">
	
	<div id="empExperEdit"  >
	
	<h2>Edit The Information of <%=session.getAttribute("empname") %> </h2>
		<table id="customers" width="100%">
		  	<tr><td width="15%" style="background-color: #F5F6FA;"  ><font color="black">Employee Code</font></td>
  		  			<td width="20%" ><%=session.getAttribute("empcode")==null?"":session.getAttribute("empcode") %><input type="hidden" name="empNo" id="empNo" readonly="readonly" value="<%=session.getAttribute("empno")%>"></td>
					<td width="15%"  style="background-color: #F5F6FA;"  ><font color="black">Employee Name</font></td>
		  	
		 <td width="50%" ><input type="text" name="empName" id="empName" size="40"  readonly="readonly" value="<%=session.getAttribute("empname")%>">&nbsp; </td> 		
		  		</tr>
		<tr class="alt"><td>Sr No.</td><td><input type="text" readonly="readonly" name="srNo" id="srNo" value="<%=empexperbean.getSRNO() %>"></td>
		<td >Organization Name</td>
		  <td ><input class="form-control" name="eOrgName" type="text" id="eOrgNamee" value="<%=empexperbean.getORGNAME() %>" maxlength="30"></td></tr>
	   <tr class="alt">	<td>From Date.</td><td><input type="text" name="eFrmDate" id="eFrmDate" value="<%=empexperbean.getFROMDT() %>" readonly="readonly">
	   <img
				src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
				onClick="javascript:NewCssCal('eFrmDate', 'ddmmmyyyy')" />
	   </td>
			<td>To Date</td><td><input class="form-control" type="text" name="eToDate" id="eToDate" value="<%=empexperbean.getTODT() %>" readonly="readonly">
			 <img
				src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
				onClick="javascript:NewCssCal('eToDate', 'ddmmmyyyy')" />
			</td></tr>
		<tr class="alt"><td>Designation</td><td><input class="form-control" name="eDesg" type="text" id="eDesg" value="<%=empexperbean.getPOST() %>" maxlength="30"></td><td>Salary</td>
			<td><input  class="form-control" name="eSalary" type="text" id="eSalary" value="<%=empexperbean.getSALARY()%>" maxlength="8"></td>
		</tr>
		<tr class="alt"><td colspan="4" align="center"><input type="submit" class="myButton"  value="Update" /> &nbsp;&nbsp;&nbsp;<input type="button" value="Cancel"  class="myButton" onClick="Showhide()"/></td></tr>
				
    </table>
    </div>
    <div class="clear">&nbsp;</div>
    </form>
<%
	}
	
	}

}
else if(action.equalsIgnoreCase("addemp"))
{
	ArrayList<EmpExperBean> empexperList2 = (ArrayList<EmpExperBean>)session.getAttribute("empexperList");
	Date date = new Date();
	SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
	String dt = format.format(date);
	
	SimpleDateFormat fromformat = new SimpleDateFormat("yyyy-MM-dd");
	fromformat.setLenient(false);
%>	
 <form action="EmployeeServlet?action=addExper" method="post" onSubmit="return addExpvalidation()">
		<table border="0" width="auto" cellpadding="0" cellspacing="0" >
	<tr>
		<th rowspan="3" class="sized"><img
			src="images/shared/side_shadowleft.jpg" width="30" height="200"
			alt="" /></th>
		<th class="topleft"></th>
		<td id="tbl-border-top">&nbsp;</td>
		<th class="topright"></th>
		<th rowspan="3" class="sized"><img
			src="images/shared/side_shadowright.jpg" width="30" height="200"
			alt="" /></th>
	</tr>
	<tr>
		<td id="tbl-border-left"></td>
		<td>
<form action="EmployeeServlet?action=addExper" method="post" onSubmit="return editExpvalidation()">
<div >
<table width="677" id="customers">
		  <tr class="alt"><td width="134" >Employee Code</td>
	  		<td width="181" ><%=session.getAttribute("empcode")==null?"":session.getAttribute("empcode") %><input type="hidden" name="aempNo" id="aempNo" readonly="readonly" value="<%=(session.getAttribute("empno")==null?"":session.getAttribute("empno")) %>"></td>
			<td width="145" >Employee Name</td>
			<td width="197" ><input type="text" name="aempName" id="aempName" readonly="readonly" value="<%=(session.getAttribute("empname")==null?"":session.getAttribute("empname")) %>"> </td></tr>
		<tr class="alt">
		<td >Organization Name</td>
		  <td colspan="3"><input class="form-control" name="aexOrgName" type="text" id="aexOrgName" maxlength="30"></td></tr>
	   <tr class="alt">	<td>From Date.</td><td><input class="form-control" type="text" name="aexFrmDate" id="aexFrmDate" readonly="readonly">
	   <img
				src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
				onClick="javascript:NewCssCal('aexFrmDate', 'ddmmmyyyy')" />
	   </td>
			<td>To Date</td><td><input class="form-control" type="text" name="aexToDate" id="aexToDate" readonly="readonly">
			<img
				src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
				onClick="javascript:NewCssCal('aexToDate', 'ddmmmyyyy')" />
			</td></tr>
		<tr class="alt"><td>Designation</td><td><input class="form-control" name="aexDesg" type="text" id="aexDesg" maxlength="30"></td><td>Salary</td>
			<td><input name="aexSalary" type="text" id="aexSalary" maxlength="8"  onkeypress="return inputLimiter(event,'Numbers')"></td>
		</tr>
		<tr class="alt">
		<td>Add More Experience</td>
			<td valign="top" colspan="3"><input class="form-control" type="radio" name="moreAdd" id="moreAdd" value="YES" >YES &nbsp;&nbsp;&nbsp;&nbsp;
			<input class="form-control" type="radio" name="moreAdd" id="moreAdd" value="NO"  checked="checked"> NO</td>
		</tr>
	<tr><td colspan="4" align="center"><input type="submit" class="myButton" value="Submit"  /> 
		&nbsp; &nbsp;
		<input type="reset"  class="myButton"  value="Cancel"  onClick="Showhide()"/></td></tr>
		
				
    </table>
    </div>

</form>
<%
if(empexperList2!=null)
{
%>
<br><br>
<form>
<table width="1349" id="customers">
		<tr><th>SRNO</th>
		<th width="325">Organization  Name</th>
		<th width="84">From Date</th>
		<th width="71">To Date</th>
		<th width="249">Position</th>
		<th width="100">Salary</th>
		
		</tr>
		<%
			for(EmpExperBean empexperbean : empexperList2)
			{ 
				//date = fromformat.parse(empexperbean.getTODT());
				%>
				<tr><td><%=empexperbean.getSRNO() %></td>
				<td><%=empexperbean.getORGNAME() %></td>
				<td><%=empexperbean.getFROMDT() %></td>
				<td><%=empexperbean.getTODT()%></td>
				<td><%=empexperbean.getPOST() %></td>
				<td><%=empexperbean.getSALARY() %></td>
				
		
			</tr>
			

				<% //onClick="window.location='empExper.jsp?action=showemp&srno=<%=empexperbean.getSRNO() >'";
			}
	%>
	<tr bgcolor="#2f747e"><td align="right" colspan="8"></td></tr>
	</table>
</form>
<%	
}

}
%>
</td>
	<td id="tbl-border-right"></td>
	</tr>
	<tr>
		<th class="sized bottomleft"></th>
		<td id="tbl-border-bottom">&nbsp;</td>
		<th class="sized bottomright"></th>
	</tr>
	</table>

</form>	

</div>
<!--  end content -->
<div class="clear">&nbsp;</div>
</div>
<!--  end content-outer........................................................END -->

<div class="clear">&nbsp;</div>
    </div>

</body>
</html>