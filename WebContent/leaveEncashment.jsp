<%@page import="payroll.Model.EmployeeBean"%>
<%@page import="payroll.Model.LeaveEncashmentBean"%>
<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="payroll.DAO.EmployeeHandler"%>
<%@page import="payroll.DAO.LeaveEncashmentHandler"%>
<%@page import="payroll.Model.ProjectBean"%>
<%@page import="payroll.DAO.ProjectListDAO"%>
<%@page import="payroll.DAO.*"%>
<%@page import="payroll.Model.*"%>
<%@page import="payroll.Model.TranBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="payroll.DAO.EmpAttendanceHandler"%>
<%@page import="payroll.DAO.EmpOffHandler"%>
<%@page import="payroll.Model.EmpOffBean"%>
<%@page import="payroll.DAO.RoleDAO"%>
<%@page import="payroll.Core.ReportDAO"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@page errorPage="error.jsp" isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy Leave Encashment </title>
<link rel="stylesheet" href="css/screen.css" type="text/css"
	media="screen" title="default" />
	<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>


<% 
RoleDAO obj1=new RoleDAO();
String roleId=obj1.getrole(Integer.parseInt(session.getAttribute("UID").toString()));
int eno = (Integer)session.getAttribute("EMPNO");
EmpOffHandler eoffhdlr = new EmpOffHandler();
EmpOffBean eoffbn = new EmpOffBean();
eoffbn = eoffhdlr.getEmpOfficAddInfo(Integer.toString(eno));
int site_id = eoffbn.getPrj_srno();
EmpAttendanceHandler empAttendanceHandler=new EmpAttendanceHandler();
String today=empAttendanceHandler.getServerDate();
int flag=-1;
flag=request.getParameter("flag1")==null?flag:Integer.parseInt(request.getParameter("flag1"));
%>

<script>
	
	jQuery(function() {
		
		$("#EMPNO").autocomplete("list.jsp");
	});
	
	
</script>
<script type="text/javascript">

function printEncash() {
	
	window.location.href = "printEncash.jsp";

	
}


function inputLimiter(e,allow) {
	  var AllowableCharacters = '';
	  if (allow == 'Numbers'){AllowableCharacters='1234567890.';}
	  var k;
	  k=document.all?parseInt(e.keyCode): parseInt(e.which);
	  if (k!=13 && k!=8 && k!=0){
	    if ((e.ctrlKey==false) && (e.altKey==false)) {
	      return (AllowableCharacters.indexOf(String.fromCharCode(k))!=-1);
	    } else {
	      return true;
	    }
	  } else {			  
	    return true;
	  }
	}	



function checkleave() {



	var f = (document.getElementById("flag1").value);

	if (f == 1) {
		alert("Encashment Saved Successfully");
	}
	else if (f == 2) {
		alert("Record is not added.Please apply again");
	}
	else if (f == 3) {
		alert("Added Successfully");		
	}
	

}


function validateForEmployee(){

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
  return false;
  }
}


function validateOnSave()
{
	    var encash=document.forms["leaveform"]["encash"].value;
	    var lsanction=document.forms["leaveform"]["lsanction"].value;

	    var date=document.leaveform.edate.value;
	    if(date == "" || date == null){
	 	  alert("Select The Encashment Date.!");
	 	  document.leaveform.edate.focus();
	 	  return false;
	    }
	    
	    var amt=document.forms["leaveform"]["ENCASHAMT"].value;
	    if(amt ==""){
	 	   alert("Enter The Encashment Amount !");
	 	   document.forms["leaveform"]["ENCASHAMT"].focus();
	 	   return false;
	    }
	    
		// VALIDATING FOR PERFECT FLOAT INPUT
	  	var string=lsanction.toString();
	    var pattern = /^\d+(\.\d{1,2})?$/;
	    var result = string.match(pattern);    
	    if(!result){
	    	alert("invalid number of Leaves entered");	
	    	return false;
	    }
	    
	 	// VALIDATING FOR PERFECT FLOAT INPUT
	    var string1=amt.toString();
	    var pattern1 = /^\d+(\.\d{1,2})?$/;
	    var result1 = string1.match(pattern1);    
	    if(!result1){
	    	alert("invalid Amount for Leave encashed");	
	    	return false;
	    }
	    
	    var p=confirm("Are you sure to sanction leave encashment of Rs. "+amt+" for "+lsanction+" leaves?");
	    if(!p)
	    return false;
}

<%

	String action = request.getParameter("action")==null?"":request.getParameter("action");
	ArrayList<TranBean> projEmpNmlist = new ArrayList<TranBean>();
	ArrayList<TranBean> projEmpNolist = new ArrayList<TranBean>();
	
	String emp_no=request.getParameter("emp_info")==null?"":request.getParameter("emp_info");
	String empno = "0";
	
	LeaveEncashmentHandler empEncash = new  LeaveEncashmentHandler();
	LeaveEncashmentBean leaveEncashmentBean = new LeaveEncashmentBean();
	EmployeeHandler emp = new  EmployeeHandler();
	EmpOffHandler off = new EmpOffHandler();
	EmployeeBean empbean = new EmployeeBean();

%>
</script>
<%--  <%
 
 String pageName = "leaveEncashment.jsp";
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
	 e.printStackTrace();
 	//response.sendRedirect("login.jsp?action=0");
 }

 %>
 --%>
 

</head>
<body onload="checkleave()" style="overflow: hidden;"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:scroll; max-height:80%; ">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1> Add Leave Encashment </h1>
	</div>
	<!-- end page-heading -->
	<div align="center">
		<form action="leaveEncashment.jsp" method="POST">
			<table border="1">
				<tr>		
					<td>Enter Employee Name Or Emp-Id <input type="text" name="EMPNO" size="40"
						id="EMPNO" onClick="showHide()" title="Enter Employee Id / Name"> &nbsp;</td>
					<td valign="top">
						<input type="submit" value="Submit" class="myButton" style="margin-left: 5px;margin-right: 5px;" onClick="return validateForEmployee()" />
						<input type="hidden" name="action" id="action" value="getEmpId">
				    </td>					
				</tr>
				<tr></tr>
			</table>
	    </form>
    </div>
   <br>

	<table border="0" style="height: 250px;overflow: hidden; " width="100%" cellpadding="0" cellspacing="0" id="content-table">
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
		<% 
			if(action.equalsIgnoreCase("getEmpId")){
				empno = request.getParameter("EMPNO")==null?"":request.getParameter("EMPNO");
				String[] employ = empno.split(":");
				empno = employ[2].trim();
				// Old Method made by Hemant
			    empbean = emp.getEmployeeInformation(empno);			   
				//	leaveEncashmentBean = empEncash.getEmployeeInfo(empno); 			
				// New Method made by Harshal
				leaveEncashmentBean = empEncash.getEmployeeInfoNew(empno); 		
		%>
		<center>
			<%	
				empbean = emp.getEmployeeInformation(Integer.toString(empbean.getEMPNO()));	
			%>
			<form name="leaveform" action="LeaveEncashServlet?action=insertEncashDetials"  method="post"  onsubmit="return validateOnSave()" >
		
			<table style="width: 90%" id ="customers">
			
				<tr style="height: 30px;" class ="alt"><th colspan="4"> <font size="4">Leave Encashment</font></th></tr>

				<tr style="height: 30px;" class ="alt">
					<td> <font size="3">Employee Name </font></td>
					<td ><input type="text" name="EMPNO1" style="height: 20px; width: 200px;" id="EMPNO1"  readonly="readonly"
						 value="<%=request.getParameter("EMPNO")%>" ></td>
								
					<td><font size="3">Max Limit :</font> </td>
					<td><input style="height: 20px; width: 200px;" type="text" id="limit" name="limit" readonly="readonly"
						value="<%=leaveEncashmentBean.getMaxLimit()%>" /> </td>
				</tr>
			
				<tr style="height: 30px;" class ="alt">
						<td><font size="3">Leave for Encashment:</font></td>
						<td><input style="height: 20px; width: 200px;" type="text" id="lbal" name="lbal" readonly="readonly"
							value="<%=leaveEncashmentBean.getLeaveBal()%>"/> </td>
			
						<td><font size="3">Leave Encashment Date :</font> 
						<td><input style="height: 20px; width: 200px;"name="edate" size="20" id="edate"
							type="text" onBlur="if(value=='')" readonly="readonly" value="<%= leaveEncashmentBean.getFromDate()%>"></td>
				</tr>
			
				<tr style="height: 30px;" class ="alt">
					<td><font size="3">Monthly Gross :</font></td>
					<td><input style="height: 20px; width: 200px;" type="text" id="gross" name="gross" readonly="readonly"
						value="<%=leaveEncashmentBean.getMonthlyGross()%>"/> </td>						
						<%  
							float encashAmt=0;
							encashAmt = (leaveEncashmentBean.getMonthlyGross()/31) * leaveEncashmentBean.getLeaveBal();							
							System.out.println("encashAmt :"+ encashAmt);
						%>						
					<td><font size="3">Encashment Amount :</font> <font style="margin-right: 10px; " color="red"><b>*</b></font></td>
					<td><input style="height: 20px; width: 200px;" type="text" id="ENCASHAMT" name="ENCASHAMT" onkeypress="return inputLimiter(event,'Numbers')"
						value="<%=Math.round(encashAmt) %>" readonly="readonly"  /></td>					
				</tr>
		
				<tr style="height: 30px;" class ="alt">
						 <td colspan="4" align="center">
						<!-- 	 <input class="myButton" type="button" style="height: 30px; width: 150px;"  value="To Print Slip" id="print" name="print" onclick="printEncash()"/> &nbsp;&nbsp;&nbsp;&nbsp;  -->
						<input class="myButton" type="submit" value ="Save" style="height: 30px; width: 60px;" id="print" name="print"/>&nbsp;&nbsp;&nbsp;&nbsp;
						<input class="myButton" type="reset" value="Cancel" style="height: 30px; width: 60px;" id="print" name="print"/>  </td>
				</tr>
			
			</table>						
		</form>
		</center>
	</div>
	<% }%>
	<br>
	<br>
	<br>		
		<center>
			<a href="printEncash.jsp" style="font-size: 20px;" >Click here to print Encashment Details</a>
		</center>
			<!--  end table-content  -->
		<input type="hidden" name="flag1" id="flag1" value="<%=flag%>">
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