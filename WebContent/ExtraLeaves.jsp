<%@page import="payroll.DAO.EmpAttendanceHandler"%>
<%@page import="payroll.Model.LeaveBalBean"%>
<%@page import="payroll.Model.EmployeeBean"%>
<%@page import="payroll.DAO.EmployeeHandler"%>
<%@page import="payroll.Model.LMB"%>
<%@page import="payroll.DAO.LMH"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="payroll.Model.Lookup"%>
<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="java.util.ArrayList"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Leaves  Credit</title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>

<script type="text/javascript">

<% 
EmpAttendanceHandler emp = new EmpAttendanceHandler();
String currentyear = emp.getServerDate();
currentyear = currentyear.substring(7, 11);

//String currentday = emp.getServerDate();
//currentyear = currentday.substring(0,2);


%>
function  noBack()
{
	window.history.forward();
}


setTimeout("noBack()", 0);
window.onunload = function() 
{
    null;
};


function redirect_url(type )
{  
	if(type=="PENDING"){
		window.location = "ExtraLeaves.jsp?sList="+type;
	}else if(type=="SANCTION"){
		window.location = "ExtraLeaves.jsp?sList="+type;
	}else if(type=="CANCEL"){
		window.location = "ExtraLeaves.jsp?sList="+type;
	}else 
		window.location = "ExtraLeaves.jsp?sList="+type;

}
	
	function TakeCustId() {
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
	
	function validate(){
		
		 if(document.leaveForm1.leavecode.value == "Default"){
			alert("Please select Leave Type.");
			document.getElementById("leavecode").focus();	
			return false;	
		} 
		 
		var fromDate=document.leaveForm1.frmdate.value;
		var toDate=document.leaveForm1.todate.value;
		   
		 if(fromDate == ""){
			 alert("please select From Date.!");
			 document.getElementById("frmdate").focus();
			 
			 return false;
		 }
		 if(toDate == ""){
			 alert("please select To Date.!");
			 document.getElementById("todate").focus();
			 
			 return false;
		 }
		   fromDate = fromDate.replace(/-/g,"/");
			toDate = toDate.replace(/-/g,"/");
		
			var d1 = new Date(fromDate);
		 	
		 	var d2 =new  Date(toDate);
		 	
		 	
		 	var SalMonth=document.leaveForm1.salaryMonth.value;
		 	SalMonth = SalMonth.replace(/-/g,"/");
					 	
			var saldate=new Date(SalMonth);
				 	
				 /* 	 alert(saldate);
				 	alert(d1); 
				 	 */
				 if (saldate.getTime() > d1.getTime())
				 {
					   alert("The salary of this month is already finalized. You can't apply for leave!!");
					   document.leaveForm1.todate.focus();
					   return false;
				  }
				 	
		 	
		 if (d1.getTime() > d2.getTime())
		 {
			   alert("Invalid Date Range!\n FromDate can't be greater than TODate!");
			   document.leaveForm1.todate.focus();
			   return false;
		  }
		 
		 var datediff = d2.getTime() - d1.getTime();
		 var radios = document.getElementById('halfday').checked;
		 if(radios == true && datediff != 0){
		 	alert("Date Must Be Same To Apply For Half Day Leave !");
		 	return false;
		 }
		 
		/*  var mob = document.leaveForm1.telephone.value;
		 if(mob == "") {
			    return true;
			}
		 else if(mob.length != 10 ){
			 
			 alert("Mobile No. must be of 10 digit.!");
			   document.leaveForm1.telephone.focus();
			   return false;
		 }	 */
		 
		 //validation to check the balance
		 
	  		 var debit = document.getElementById('debitcheck').checked;
		 
			if(debit == true){
   				 var total_balance=document.getElementById('total_balance').value;
		
			 if(total_balance=="0.0"){		 
				 alert("Your leave balance is zero");
				 return false;
			 }
			 
			  var days=document.leaveForm1.days.value;
				var total_days=document.leaveForm1.total_balance.value;
				
				if(parseFloat(days)>parseFloat(total_days)){
					alert("Number of days you are applying leave for Should not exceed leave balance ");
					document.leaveForm1.days.focus();
					return false;
				}
			 	
			 } 
	
		
	}
	
	function confirmation(){
		var flag=confirm("Are you sure to Remove Leave ?");
		if(flag){
			return true;
		}else{
			return false;
		}
	}
	function invalid(){
		alert("\tSorry you can't Remove Leave !\n\n The transactions has done on this Leave");
		return false;
	}
</script>

<script>
	jQuery(function() {
          $("#EMPNO").autocomplete("list.jsp");
	});
	
	$(document).ready(function() {
	    var f = document.getElementById('leave');
	    setInterval(function() {
	        f.style.display = (f.style.display == 'none' ? '' : 'none');
	    }, 500); 
	    
	});
	
	function setDays(){
		var fromDate=document.getElementById("frmdate").value;
		var toDate=document.getElementById("todate").value;
		//alert(fromDate+"---"+toDate);
		
		var curent_year = "<%=currentyear%>";
		
	/* 	alert(fromDate.substring(7,11));
		alert(toDate.substring(7,11));
		alert(curent_year); */
		
		if(
			((fromDate.substring(3,6)!=toDate.substring(3,6)|| (fromDate.substring(7,11)!=toDate.substring(7,11)))
				&& ((toDate!="") && (fromDate!="") ) )    ) 
				
		{
			alert("You can not select dates of different months");
			document.getElementById("frmdate").value="";
			document.getElementById("todate").value="";
			return false;
		}
		
		
		
		else if(fromDate.substring(0,2)>toDate.substring(0,2) && ((toDate!="") && (fromDate!="") ) ){
				alert("To date shall not be earlier than From Date");
				document.getElementById("frmdate").value="";
				document.getElementById("todate").value="";
				document.getElementById("days").value="";
				return false;
			}
	
		
		// code for cant select next year
	 	
			else if(fromDate.substring(7,11)!=curent_year && toDate.substring(7,11)!=curent_year){
				
				alert("You can not select dates of different years");
				document.getElementById("frmdate").value="";
				document.getElementById("todate").value="";
				return false;
			}  
		
			else if (((toDate!="") && (fromDate!="") ) ) {
				if(fromDate!=toDate && document.getElementById('halfday').checked ){
					alert("From Date and To Date must be same for Half day");
					document.getElementById("frmdate").value="";
					document.getElementById("todate").value="";
					document.getElementById("days").value="";
					return false;
				}
				
				if(fromDate==toDate && document.getElementById('halfday').checked ){
					document.getElementById("days").value=0.5;
				}else if(fromDate==toDate){
				document.getElementById("days").value=1;
				}
				if(fromDate!=toDate){
				var lowerLimit=fromDate.substring(0,2);
				var upperLimit=toDate.substring(0,2);
				document.getElementById("days").value=upperLimit-lowerLimit+1;
				}
				return true;
			}
	}
</script>


<style type="text/css">

.ac_results {
	padding: 0px;
	border: 1px solid #cacaca;
	background-color: #f5f6fa;
	overflow: hidden;
	width: 13% !important;
}

</style>


</head>
<body onunload="noBack()">
<%
String action = request.getParameter("action")==null?"":request.getParameter("action");
String empNumber="";
String Salary_Month="";
String str_disp = "";

EmployeeHandler employeeHandler=new EmployeeHandler();
if(action.equalsIgnoreCase("getData"))
{ 
	empNumber=(session.getAttribute("empno").toString());
	EmployeeBean ebean=employeeHandler.getEmployeeInformation(empNumber);
	str_disp=ebean.getFNAME()+" "+ebean.getLNAME()+":"+ebean.getEMPCODE()+":"+ebean.getEMPNO();
	}

EmployeeHandler emph = new EmployeeHandler();
EmployeeBean ebean = new EmployeeBean();

LookupHandler lkh= new LookupHandler();
ArrayList<LMB> leaveBalList = new ArrayList<LMB>();
ArrayList<LMB> leaveList = new ArrayList<LMB>();
LMH leaveMasterHandler = new LMH();
SimpleDateFormat dateFormat;  %>

<%@include file="mainHeader.jsp"%>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp"%>
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Extra Leaves</h1>
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
			<div id="table-content" align="center" style="overflow-y:auto; max-height:500px; ">
			
			<form  action="LMS?action=getLeave" method="Post" onSubmit="return TakeCustId()" name="form1" id="form1">
	<table border="1">
		<tr >
			<td style="padding: 10px;"><B>Enter Employee Name Or Id</B> <input type="text" name="EMPNO" size="50"
								id="EMPNO" onClick="showHide()" title="Enter Employee Id / Name "
							    value="<%=action.equalsIgnoreCase("getData")?str_disp:""%>"> &nbsp;</td>
			<td style="padding: 10px;">
			<input type="Submit" value="SUBMIT" class="myButton" /> </td>		
		</tr>
</table>
</form>
<br/>

<% if(action.equalsIgnoreCase("getData")){
	
	 int empNo = Integer.parseInt(session.getAttribute("empno").toString());
	    /* String searchList = request.getParameter("sList")==null?"ALL":request.getParameter("sList");
		if(searchList.equalsIgnoreCase("PENDING")){
			leaveList = leaveMasterHandler.leaveDisplay(empNo,"PENDING");
		}else if(searchList.equalsIgnoreCase("SANCTION")){
			leaveList = leaveMasterHandler.leaveDisplay(empNo,"SANCTION");
		}else if(searchList.equalsIgnoreCase("CANCEL")){
			leaveList = leaveMasterHandler.leaveDisplay(empNo,"CANCEL");
		}else{ */
			Salary_Month=EmployeeHandler.getSalaryMonth(empNo);
			
			leaveList = leaveMasterHandler.leaveDisplay(empNo,"ALL");
		
		 ebean = emph.getEmployeeInformation(Integer.toString(empNo)); 
	     leaveBalList = (ArrayList<LMB>)request.getAttribute("leaveBalList");
 %>
 
<h2>Leave Details</h2>
<%-- <table id="customers" style="width: 550px">
  	
  	<tr><th  scope="col">Emp ID </th>
  	<th scope="col">Employee Name</th></tr>
      <tr>
      <td align="center"><%=ebean.getEMPCODE() %></td>
      <td align="center" colspan="3"><%=lkh.getLKP_Desc("SALUTE", ebean.getSALUTE())+" "+ ebean.getFNAME()+" "+ebean.getMNAME()+" "+ebean.getLNAME() %></td>
  
  	</table> --%>
  	
  	<table id="customers" style="width: 80%">
  	  <tr>
  	  <th scope="col">Emp Id</th>
  	  <th scope="col">Employee Name</th>
      <th  scope="col">Balance Date </th>
      <th scope="col">Leave Type </th>
      <th scope="col">Leaves Credited </th>
      <th scope="col">Leaves Used</th>
      <th scope="col">Leave Balance</th>
      <th scope="col">Delete</th>
           
    </tr>

   <% float bal = 0.0f;
   float total_balance=0.0f;
   	  if(!leaveBalList.isEmpty()){
	   	for(LMB leaveBean : leaveBalList){
	   		if(leaveBean.getLEAVECD() != 5){
				bal =bal + leaveBean.getTOTCR();
				total_balance=leaveBean.getBAL()  ;
			}  %> 
	   
	<tr align="center">
		  <td><%=ebean.getEMPCODE() %></td>
		  <td><%=lkh.getLKP_Desc("SALUTE", ebean.getSALUTE())+" "+ ebean.getFNAME()+" "+ebean.getMNAME()+" "+ebean.getLNAME() %></td>
	      <td><%=leaveBean.getBALDT() %></td>
	      <td><%=lkh.getLKP_Desc("LEAVE",leaveBean.getLEAVECD()) %></td>
	      <td><%=leaveBean.getTOTCR() %></td>
	      <td><%=leaveBean.getTOTDR() %></td>
	      <td><%=leaveBean.getBAL() %></td>
	      <td>
	      <% boolean flag = LMH.checkTransactions(empNo, leaveBean.getLEAVECD());
	      	if(flag){ %>
	      	<!-- 	<input type="submit" value="Delete" onclick="return invalid()"> -->
	      <%}
	      	else{ %>
	     <!--  		<form action="LMS?action=DeleteLeave" method="post" onclick="return confirmation()"><input type="submit" value="Delete"/> -->
	      		<input type="hidden" name="empno" id="empno" value="<%=empNo%>">
	      		<input type="hidden" name="leavecd" id="leavecd" value="<%=leaveBean.getLEAVECD()%>"></form>
	      <%} %>
	      </td>
    </tr>
    <% }
	   if(LMH.displayEmpLeaves(empNo) - bal > 0){ %>
	<tr class="row">
		<%-- <td colspan="6" height="18"><font size="2"><div id="leave"><%=LMH.displayEmpLeaves(empNo) - bal %> leaves Remaining</div></font></td> --%>
	</tr>

   <%		}
	   }
       else {   %>
   		  
     <tr><td colspan="6" height="20">No Leaves Credited</td></tr>
     
    <%  } %>
    
</table>
<br/>

<div align="center">
		
		<form  id="form1" name="leaveForm1" action="LMS?action=ExtraLeave" method="post" onSubmit=" return validate()">
		
		<table id="customers"  border="1"  style="width: 80%">
	           <%
	   				 dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
					 Date date = new Date();
					 String currentdate = dateFormat.format(date);  
					%>
		<tr><th align="center" colspan="6" >Leaves  Credit</th></tr>
	     <tr class="alt"  >
	      <td >Date :</td>
	      <td><input name="tdate" id="tdate" type="text"  readonly="readonly" value="<%=currentdate %>" size="20"></td>
	 
	      <td >Leave Type : <font color="white" size="3">*</font></td>
	      <td ><select name="leavecode" id="leavecode" style="width: 140px">
						<option selected="selected" value="Default">Select</option>
						<%
							  ArrayList<Lookup> result3=new ArrayList<Lookup>();
								  LookupHandler lkhp3= new LookupHandler();
							      result3=lkhp3.getSubLKP_DESC("LEAVE");
						for(Lookup lkbean : result3)
						{
							if( lkbean.getLKP_SRNO()!=5 && lkbean.getLKP_SRNO()!=6)
							{ %>
								<option value="<%=lkbean.getLKP_SRNO()%>" ><%=lkbean.getLKP_DESC()%></option>  
								<%								 
								}
						}
							 	%>
					</select>
		  </td>
	     </tr>
	     
	     <tr class="alt" >
	      	<td >From Date  :<font color="white" size="3">*</font></td>
	     	<td><input name="frmdate" id="frmdate" type="text"  onchange="setDays()" readonly="readonly">&nbsp;<img
						src="images/cal.gif" align="middle"
						style="vertical-align: middle; cursor: pointer;"
						onClick="javascript:NewCssCal('frmdate', 'ddmmmyyyy')" /></td>
	    
	      	<td >To Date : <font color="white" size="3">*</font></td>
	     	<td><input name="todate" id="todate" type="text" onchange="setDays()" readonly="readonly">&nbsp;<img
						src="images/cal.gif" align="middle"
						style="vertical-align: middle; cursor: pointer;"
						onClick="javascript:NewCssCal('todate', 'ddmmmyyyy')" /></td>
	    </tr>
	   
	    <tr class="alt" >
	    	<td > Transaction :</td>
	        <td><input type="radio" name="leave" value="D" id="debitcheck" style="display: none;" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
	      		<input type="radio" name="leave" value="C" checked="checked"  />&nbsp;&nbsp;CREDIT
	      	<td >Half Day ? :</td>
	 	    <td>&nbsp;Yes&nbsp;<input type="radio" name="halfday" id="halfday" value="yes"/>
	 	  		&nbsp;No&nbsp;<input type="radio" name="halfday" value="no" checked/></td>
	    </tr>
	    
	     <tr class="alt" >
	      	<td >Leave Reason :</td>
	      	<td><input type="text" name="lreason" size="60"/></td>
	      	<td>Total Days</td>
	 	  <td><input type="text" name="days" id="days" readonly="readonly" /> </td>
	    </tr>
	    <tr class="alt" >
	    	<td height="31" colspan="4" align="center">
	    	<input type="submit" value="Add"/>&nbsp;&nbsp;
	    	<input type="reset" value="Cancel"/></td>
	    	 <input type="hidden" id="total_balance" name="total_balance" value="<%=total_balance %>">
	    	 <input type="hidden" id="salaryMonth" name="salaryMonth" value="<%=Salary_Month %>">
	    </tr>
	   
	  </table>
	  </form>
	  
	</div>
	<br/>
	
<h2>Leave Transactions</h2>
<table  id="customers"  border="1" style="float: none; width: 80%">
	
    <tr>
      <th scope="col">App Date</th>
      <th scope="col">Leave Type </th>
      <th scope="col">From Date </th>
      <th scope="col">To Date </th>
      <th scope="col">Total Days</th>
      <th scope="col">Leave Reason </th>
      <th scope="col">Address </th>
      <th scope="col">Tran_Type </th>
      <th scope="col">Status </th>
      
    </tr>
    <% if(leaveList.size()!=0){
    	for(LMB leaveBean : leaveList){%>
    <tr align="center">
    	<td><%=leaveBean.getTRNDATE() %></td>
    	<td><%=lkh.getLKP_Desc("LEAVE",leaveBean.getLEAVECD())%></td>
    	<td><%=leaveBean.getFRMDT() %></td>
    	<td><%=leaveBean.getTODT() %></td>
    	<td><%=leaveBean.getNODAYS() %></td>
    	<td width="150"><%=leaveBean.getLREASON() %></td>
    	<td width="150"><%=leaveBean.getLADDR() %></td>
    	<% if(leaveBean.getTRNTYPE() == 'C') {%>
    	<td>CREDIT</td>
    	<%}else { %>
    	<td>DEBIT</td>
    	<%} %>
    	<td><%=leaveBean.getSTATUS() %></td>
    	
    </tr>
    <%}
    } else {%>
    <tr><td colspan="9" height="20">No Records Found</td></tr>
    <%} %>
  </table>
  <%} %>
  			
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