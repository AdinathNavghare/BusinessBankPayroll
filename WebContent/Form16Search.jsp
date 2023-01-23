<%@page import="payroll.DAO.TranHandler"%>
<%@page import="payroll.Core.ReportDAO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="payroll.Model.Form16Bean"%>
<%@page import="payroll.DAO.Form16Handler"%>
<%@page import="payroll.DAO.datefun"%>
<%@page import="payroll.DAO.CodeMasterHandler"%>
<%@page import="payroll.Model.TranBean"%>
<%@page import="payroll.Model.TransactionBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="payroll.DAO.EmpOffHandler"%>
<%@page import="payroll.Model.Lookup"%>
<%@page import="payroll.Model.EmployeeBean"%>
<%@page import="java.util.*"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy DTS3 </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />
<script src="js/jquery/jquery-1.4.1.min.js" type="text/javascript"></script>
<!--  date picker script -->
<link rel="stylesheet" href="css/datePicker.css" type="text/css" />
<script src="js/jquery/date.js" type="text/javascript"></script>
<script src="js/jquery/jquery.datePicker.js" type="text/javascript"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>

<script type="text/javascript">
function TakeCustId() {
	var EMPNO = document.getElementById("EMPNO").value;
	var atpos=EMPNO.indexOf(":");
	var date = document.getElementById("date").value;
	if (document.getElementById("EMPNO").value == "") {
		alert("Please Insert Employee Name");
		document.getElementById("EMPNO").focus();
		return false;
	}
	
	else if (atpos<1)
	  {
	  alert("Please Select Correct Employee Name");
	  return false;
	  }
	
	
	else if(date==""){
		alert("please select the Date !");
		document.getElementById("date").focus();
		return false;
	}
	else
		{
		return true;
		}
	}
/* function TextCheck(id)
{
	var check = document.getElementById(id).value;
	fcs = document.getElementById(id);
	var matches = check.match(/\d+/g);
	if (matches == null) {
	    alert("Please Insert Numbers Only ");
	    //document.getElementById(id).focus();
	    setTimeout("fcs.focus()", 50);
	    return false;
	}
} */
function check(btn){
	var chk = confirm('\t\t\t Are You Sure To Finalize ?\n After finalisation, you wont be able to change data for current financial year.');
	if(chk){
		var b=prompt("Type YES for Finalizing records");	
	 		if(b == "yes" || b == "Yes" || b == "YES"){
					
		document.form3.hdnbt.value=btn;
		document.form3.submit();
		return true;
	}
	 		}else{
		return false;
	}
}

function checkalert()
{
	var msg1=document.getElementById("msgg").value;
	//alert(msg1);
	if(msg1=="0")
		{
		alert("Record Finalized Successfully!!!");
		}
	else if(msg1=="1")
		{
		alert("Record Saved Successfully!!!");
		}
	
	}

function inputLimiter(e,allow) {
	  var AllowableCharacters = '';
	  if (allow == 'Numbers'){AllowableCharacters='1234567890.-';}
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

//VALIDATING FOR PERFECT FLOAT INPUT
function validateOnSave() {
var string1=amt.toString();
var pattern1 = /^\d+(\.\d{1,2})?$/;
var result1 = string1.match(pattern1);    
if(!result1){
	alert("Invalid Amount");	
	return false;
}

/* if(0.5>parseFloat(lsanction)){
	alert("Leave Encashment Sanction should not be less than a half day ");
	document.forms["leaveform"]["lsanction"].focus();
	   	return false;
}

if(parseFloat(lsanction)%0.5!=0){
	alert("Leave Encashment Sanction must be multiple of 0.5 ");
	document.forms["leaveform"]["lsanction"].focus();
	   	return false;
} */

}

function alertOnSave(){
	var p=confirm("Are you sure to save the changes?");
	if(p){
		
		var a=document.getElementById("falb").value;
		var b=document.getElementById("furent").value;
		var c=document.getElementById("ftaxlocal").value;
		var d=document.getElementById("fihousingloan").value;
		
		document.getElementById("fhousepr").value=(parseInt(a)+parseInt(b)+parseInt(c)+parseInt(d));
		document.getElementById("process").hidden=false;
		return true;
	}
	else
		
		return false;
}

function modifyRec(empno,trncd,amt)
{

  window.showModalDialog("addNewBill.jsp?empno="+empno+"&trncd="+trncd+"&amt="+amt,null,"dialogWidth:890px; dialogHeight:400px; scroll:off; status:off;dialogLeft:400px;dialogTop:300px; ");
  window.location.href="Form16Search.jsp?action=getdata";
}
function changeYear(){
	if(document.getElementById("date").value=="")
	{
	alert("Please select year");
	return false;
	}
	var date=document.getElementById("date").value;
	document.getElementById("toYear").value=parseInt(date)+1;	
}

function getdata()
{
	
	var flag=TakeCustId();
	if(flag){
	var empid=document.getElementById("EMPNO").value;
	var year=document.getElementById("date").value;
	//alert(empid+"..........."+year);
	 window.location.href=("Form16Search.jsp?action=getdata&empno1="+empid+"&year="+year);
	}
}


</script>
<script>
	jQuery(function() {
          $("#EMPNO").autocomplete("list.jsp");
	});
</script>
<style type="text/css">
.row:hover {
	background-color: #CCCCCC;
	cursor: pointer;
}
.style1 {
	color: #FF0000;
}
</style>
<%

try{
String EMPNO="";
String action = "";
String year="";
int year1=0;
EmpOffHandler emp = new EmpOffHandler();
TranHandler trn = new TranHandler();
TransactionBean trbn = new TransactionBean();
Map<Integer,Form16Bean> fbn1 = new HashMap<Integer, Form16Bean>(); 
Form16Handler fnh = new Form16Handler();
action = request.getParameter("action")== null?"":request.getParameter("action");
System.out.println("action is..................."+action);
if(action.equalsIgnoreCase("getdata"))
{
	EMPNO =(String)request.getParameter("empno1")==null?"":request.getParameter("empno1").toString();
	System.out.println("empno..................."+EMPNO);
	year=request.getParameter("year").toString();
	session.setAttribute("year",year);
	String EMPNO1[]=EMPNO.split(":");
	EMPNO=EMPNO1[2];
	System.out.println("after spit empno..................."+EMPNO);
	 year1=Integer.parseInt(year);

//EMPNO =(String)session.getAttribute("empno1")==null?"":session.getAttribute("empno1").toString();

ArrayList<TranBean> trlist = new ArrayList<TranBean>();
 trbn=emp.getInfoEmpTran(EMPNO);
 trlist=trn.getTranInfo(EMPNO,"tran");// tran :-fire query to tran table
 /* session.setAttribute("trbn",trbn); 
 session.setAttribute("empno1",EMPNO); 
 session.setAttribute("trlist",trlist); */


 fbn1=fnh.getForm16Value(EMPNO,year);
 }
/*  session.setAttribute("fbn", fbn1); */

String msg = request.getParameter("msg")== null?"":request.getParameter("msg");
System.out.println("msg is..."+msg);
String error = request.getParameter("error")==null?"":request.getParameter("error");
 LookupHandler lh = new LookupHandler();
EmpOffHandler ofh = new EmpOffHandler();
String date=ReportDAO.getSysDate().substring(7,11);
int dateInInt=Integer.parseInt(date);

%>
</head>
<body style ="overflow:hidden;" onload="checkalert();"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:scroll; max-height:82%; ">
<!-- start content -->
<div id="content">
	<font size="5" color="black"><b>Form16 Data Entry</b></font><br><br><br>
	<%-- <div id="step-holder">
			<!-- <div class="step-light-right">&nbsp;</div> -->
			<div class="step-no">1</div>
			<%if(EMPNO.equals("") || EMPNO.equals(null)){ %>
			<!-- <div class="step-light-left"><a href="salaryStructure.jsp">ITR Wages</a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">2</div> -->
			<div class="step-dark-left"><a href="Form16Search.jsp" > Form 16</a></div>
			<!-- <div class="step-dark-right">&nbsp;</div>
			<div class="step-no-off">3</div>
			<div class="step-light-left"><a href="taxCompute.jsp">Tax Computation</a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">4</div>
			<div class="step-light-left"><a href="setParameter.jsp">Setup Parameters</a></div>
			<div class="step-light-round">&nbsp;</div>
            <div class="clear"></div> -->
			<%} else { %>
			<!-- <div class="step-light-left"><a href="salaryStructure.jsp?action=salstruct">ITR Wages</a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">2</div> -->
			<div class="step-dark-left"><a href="Form16Search.jsp?action=getdata" > Form 16</a></div>
			<!-- <div class="step-dark-right">&nbsp;</div>
			<div class="step-no-off">3</div>
			<div class="step-light-left"><a href="taxCompute.jsp">Tax Computation</a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">4</div>
			<div class="step-light-left"><a href="setParameter.jsp">Setup Parameters</a></div>
			<div class="step-light-round">&nbsp;</div>
            <div class="clear"></div> -->
            <%} %>
	</div> --%>
	
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
		<table border="1" align="center">
					<tr >
						
							<td><b>Enter Employee Name or Emp-Id </b>
						<input type="text" name="EMPNO"  placeholder="Enter Employee Name"
						style="margin-left: 15px;"
						 size="40" id="EMPNO" title="Enter Employee Name"   ></td>
						<%-- <%=emp1==""?ename:emp1%> --%>
						<td style="width:150px; text-align:center;"><b>Financial Year : April-</b></td>
					<td>
					<select id="date" onchange="changeYear()" name="date">
						
									
<%
if(action.equalsIgnoreCase("getdata"))
{
		
	if(year1==dateInInt-1){
		%>
						<option value="">Select</option>	
						<option value="<%=dateInInt-1%>" selected><%=dateInInt-1%></option>
						<option value="<%=dateInInt%>"><%=dateInInt%></option>
						<option value="<%=dateInInt+1%>"><%=dateInInt+1%></option>
<% }else if(year1==dateInInt){
	%>
	<option value="">Select</option>	
	<option value="<%=dateInInt-1%>" ><%=dateInInt-1%></option>
	<option value="<%=dateInInt%>" selected><%=dateInInt%></option>
	<option value="<%=dateInInt+1%>"><%=dateInInt+1%></option>
<% } else { %>
	<option value="">Select</option>	
	<option value="<%=dateInInt-1%>" ><%=dateInInt-1%></option>
	<option value="<%=dateInInt%>" ><%=dateInInt%></option>
	<option value="<%=dateInInt+1%>" selected><%=dateInInt+1%></option>	


<%}
} else {%>

						<option value="">Select</option>	
						<option value="<%=dateInInt-1%>"><%=dateInInt-1%></option>
						<option value="<%=dateInInt%>"><%=dateInInt%></option>
						<option value="<%=dateInInt+1%>"><%=dateInInt+1%></option>
			<% } %>			
</select>
</td>
									<td style="width:80px; text-align:center;"><b>To March-</b></td>
									<td>
									<%
if(action.equalsIgnoreCase("getdata"))
{
%>	
									<input type="text" name="toYear" id="toYear" 
							style="width: 40px;" readonly="readonly" value="<%=year1+1%>" />
<% } else {   %>	
							<input type="text" name="toYear" id="toYear" 
									style="width: 40px;" readonly="readonly" />
					<% }%>		
					</td>				
					<!-- 	<td><input name="date" id="date" style="margin-left: 25px;"
						 readonly="readonly" class="date-picker" placeholder="Click here for Calender"/>
						</td> -->
						<td valign="top"> <input type="submit" class="myButton" style="margin-left:50px;"
						  value="Submit" onclick="getdata()" ></td>
						
	</tr>
	<tr></tr>
</table></center>
		
		<%if(action.equalsIgnoreCase("getdata"))
		 				{
		 				 
		 %>
<!-- <form action="Form16Servlet?action=trnlist" method="Post" onSubmit="return TakeCustId()" name="form1" id="form1">
<table>
	<tr>
		<td>Enter Employee Name Or Emp-Id &nbsp;&nbsp;&nbsp;<input type="text" name="EMPNO" size="30" id="EMPNO" onClick="showHide()" title="Enter Employee Id / Name "> &nbsp;
		</td>
		<td valign="top"><input type="Submit" value="SUBMIT"  /></td>
	</tr>
</table>
</form> -->
<%
if(fbn1.size()>0){ 
	SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
	String dateInString = ReportDAO.getSysDate();
String dateString = fbn1.get(565).getUPDDT();
Date date1 = null;
Date date2 = null;
Date date3 = null;
Calendar cal = Calendar.getInstance();
try {

	date1 = formatter.parse(dateInString);
	//System.out.println(date);
	date2 = formatter.parse(dateString);
	//System.out.println(date2);
	cal.setTime(date2);
	cal.add(Calendar.YEAR, 1);
	date3 = cal.getTime();
	//System.out.println(cal.getTime());

} catch (Exception e) {
	e.printStackTrace();
}
if(date1.compareTo(date3)>0){
	
}else{
if(fbn1.get(558).getCF_SW().equalsIgnoreCase("F")){
	%>
	<h3 style="color: red;" >You Have Been Already Finalize Record For The Current Financial Year </h3>
<% }}}%>
<form action="Form16Servlet?action=insert" method="post" name="form3" id="form3" onSubmit="return alertOnSave()" >
						


<input type="hidden" id="msgg" value="<%=msg %>" />
	<font size="3" style="font-weight: bold;"><br><br><br><br>Financial Year :<%=request.getParameter("year")%>-
            <%=Integer.parseInt(request.getParameter("year").toString())+1%> </font>
	<%-- <h3 style="color: red;"><%=msg %></h3> --%>
	
	<table><tr> <td>
	<table id="customers" align="center">
	<tr class="alt"><th colspan="4" width="560px">Form 16 Calculations Data Entry Form 
	</th></tr>
		<tr class="alt">
			 <td>Emp No.</td><td><input type="text" size="10" name="empno1" id="empno1"  value="<%=trbn.getEmpno()%>" readonly="readonly"></td>
			 <td>Emp Name</td><td><input type="text" size="40" readonly="readonly" value="<%=trbn.getEmpname()%>"></td>
		</tr>
	</table>
	<div id="table-content" style="height: 408px; width:560px; overflow-y: scroll;">
		<table id="customers">
		<!-- <tr class ="alt">
			<td colspan="2">1]&nbsp;&nbsp;&nbsp;&nbsp;     Gross Annual Income/Salary (include all allowances & Perks)</td>
			<td><input type="text" size="10" name="fgais" id="fgais"></td>
			<td><input type="text" size="10" name="fgais1" id="fgais1" value=""></td>
		</tr> -->
		<%if(fbn1.size()>0){ %>
		<tr class="alt">
			<td colspan="2">1]&nbsp;&nbsp;&nbsp;&nbsp;     Less: Allowances exempt u/s 10 & 17( for Service Period )</td>
			<td></td><%-- <td><input type="text" size="10" name="flaeus" id="flaeus" value="<%=fbn1.get(562).getINP_AMT() %>"></td> --%>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;	i)&nbsp;&nbsp;&nbsp;&nbsp;HRA exemption u/s 10(13A)</td>
			<td></td><%-- <td><input type="text" size="10" name="fheus" id="fheus" value="<%=fbn1.get(563).getINP_AMT() %>"></td> --%>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select City of Residence</td>
			<td><select name="fscr" id="fscr">
					<%if(fbn1.get(564).getINP_AMT()==1){ %>
					<option value="1">Metro</option>
					<option value="2">Non Metro</option>
					<%}else{ %>
					<option value="2">Non Metro</option>
					<option value="1">Metro</option>
					<%} %>
				</select>
			</td>
		</tr>
		<%-- <tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Basic Salary</td>
			<td><input type="text" size="10" name="fbasic" id="fbasic" value="<%=fbn1.get(101).getINP_AMT() %>"></td>
		</tr> --%>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp; ii)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Rent Paid</td>
			<td><input type="text" size="10" name="frp" id="frp" value="<%=fbn1.get(565).getINP_AMT() %>"
			onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<%-- <tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;H.R.A. Recieved</td>
			<td><input type="text" size="10" name="fhrar" id="fhrar" value="<%=fbn1.get(103).getINP_AMT() %>"></td>
		</tr> --%>
		<%-- <tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp; ii)&nbsp;&nbsp;&nbsp;&nbsp;Transport Allownces</td>
			<td><input type="text" size="10" name="fta" id="fta" value="<%=fbn1.get(521).getINP_AMT() %>"></td>
		</tr> --%> 
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp; iii)&nbsp;&nbsp;&nbsp;&nbsp;Children Education Allownces</td>
			<td><input type="text" size="10" name="fcea" id="fcea" value="<%=fbn1.get(522).getINP_AMT() %>"
			onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp; iv)&nbsp;&nbsp;&nbsp;&nbsp;Children Hostel Allownces</td>
			<td><input type="text" size="10" name="fcha" id="fcha" value="<%=fbn1.get(523).getINP_AMT() %>"
			onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp; v)&nbsp;&nbsp;&nbsp;&nbsp;LTA(allowed as per rule)</td>
			<td><input type="text" size="10" name="flta" id="flta" value="<%=fbn1.get(525).getINP_AMT() %>"
			onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp; vi)&nbsp;&nbsp;&nbsp;&nbsp;<span style="vertical-align:middle;" >Medical Bills Reimbursement</span>
			<input type="button" style="margin-right: 10px; float:right; "   value="Insert Bill" onClick="modifyRec('<%=EMPNO%>','524','<%=fbn1.get(524).getINP_AMT()%>')"> </td>
			<td><input type="text" size="10" name="fmbr" id="fmbr" value="<%=fbn1.get(524).getINP_AMT() %>"
			onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"> 
			</td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp; vii)&nbsp;&nbsp;&nbsp;&nbsp;<span style="vertical-align:middle;" >Telephone Bills Reimbursement</span>
			<input type="button" style="margin-right: 10px; float:right;" value="Insert Bill" onClick="modifyRec('<%=EMPNO%>','587','<%=fbn1.get(587).getINP_AMT()%>')" > </td>
			<td><input type="text" size="10" name="tbr" id="tbr" value="<%=fbn1.get(587).getINP_AMT()%>"
			onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp; viii)&nbsp;&nbsp;&nbsp;&nbsp;<span style="vertical-align:middle;" >Sodexo Coupons Reimbursement</span>
			<input type="button" style="margin-right: 10px; float:right;" value="Insert Bill" onClick="modifyRec('<%=EMPNO%>','588','<%=fbn1.get(588).getINP_AMT()%>')"> </td>
			<td><input type="text" size="10" name="scr" id="scr" value="<%=fbn1.get(588).getINP_AMT()%>"
			onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		
		<%-- <tr class="alt">
			<td colspan="2">3]&nbsp;&nbsp;&nbsp;&nbsp;     Deduction u/s 16</td>
			<td><input type="text" size="10" name="fdus16" id="fdus16" value="<%=fbn1.get(566).getINP_AMT() %>"></td> 	
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp; i)&nbsp;&nbsp;&nbsp;&nbsp;Professionsal Tax / tax on Employment (u/s 16(ii))</td>
			<td><input type="text" size="10" name="fpt" id="fpt" value="<%=fbn1.get(202).getINP_AMT() %>"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp; ii)&nbsp;&nbsp;&nbsp;&nbsp;Entertainment Allowance ( for Govt Servent only) (u/s 16(iii))</td>
			<td><input type="text" size="10" name="fea" id="fea" value="<%=fbn1.get(565).getINP_AMT() %>"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">4]&nbsp;&nbsp;&nbsp;&nbsp;Income under the head salaries</td>
			<td><input type="text" size="10" name="fiuhs" id="fiuhs" value="<%=fbn1.get(565).getINP_AMT() %>"></td> 	
		</tr> --%> 
		<tr class="alt">
			<td colspan="2">2]&nbsp;&nbsp;&nbsp;&nbsp;Interest received from saving A/c (Bank/Post Post Office)</td>
			<!-- <td><input type="text" size="10" name="firsac" id="firsac"></td> -->
			<td><input type="text" size="10" name="firsac1" id="firsac1" value="<%=fbn1.get(526).getINP_AMT() %>"
			onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td> 	
		</tr>
		<tr class="alt">
			<td colspan="2">3]&nbsp;&nbsp;&nbsp;&nbsp;Add: Any other income from other sources</td>
			<td></td><%-- <td><input type="text" size="10" name="fios" id="fios" value="<%=fbn1.get(581).getINP_AMT() %>"></td> --%>
		</tr>	
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp; i)&nbsp;&nbsp;&nbsp;&nbsp;Interest from - Fixed Deposit(FD)</td>
			<td><input type="text" size="10" name="fifd" id="fifd" value="<%=fbn1.get(527).getINP_AMT() %>"
			onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp; ii)&nbsp;&nbsp;&nbsp;&nbsp;Interest from - Recuring Deposit(RD)</td>
			<td><input type="text" size="10" name="fird" id="fird" value="<%=fbn1.get(528).getINP_AMT() %>"
			onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp; iii)&nbsp;&nbsp;&nbsp;&nbsp;Interest on N.S.C.(Accrued/ Recd )</td>
			<td><input type="text" size="10" name="finsc" id="finsc" value="<%=fbn1.get(529).getINP_AMT() %>"
			onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp; iv)&nbsp;&nbsp;&nbsp;&nbsp;Interest from Post Office M.I.S (6 yrs.)</td>
			<td><input type="text" size="10" name="fipost" id="fipost" value="<%=fbn1.get(530).getINP_AMT() %>"
			onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp; v)&nbsp;&nbsp;&nbsp;&nbsp;Interest on Saving Bonds</td>
			<td><input type="text" size="10" name="fisb" id="fisb" value="<%=fbn1.get(531).getINP_AMT() %>"
			onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp; vi)&nbsp;&nbsp;&nbsp;&nbsp;Other Sources</td>
			<td><input type="text" size="10" name="fos" id="fos" value="<%=fbn1.get(569).getINP_AMT() %>"
			onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		
		<tr class="alt">
			<td colspan="2">4]&nbsp;&nbsp;&nbsp;&nbsp;Income from house property (Total of following heads in negative) </td>
			<td><input type="text" size="10" name="fhousepr" id="fhousepr" 
			value="<%=(fbn1.get(532).getINP_AMT()+fbn1.get(533).getINP_AMT()+fbn1.get(534).getINP_AMT()+fbn1.get(535).getINP_AMT())%>"
			onkeypress="return inputLimiter(event,'Numbers')" readonly="readonly" maxlength="10"></td>
		</tr>	
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp; i)&nbsp;&nbsp;&nbsp;&nbsp;Accommodation Status</td>
			<td><select name="facomdation" id="facomdation">
					<%
				float acomodation = fbn1.get(571).getINP_AMT();
				if(acomodation == 1){%>
					<option value="1">Self Occupied</option>
					<option value="2">Left Out</option>
					<option value="3">Both</option>
			<%	}else if(acomodation == 2){ %>
					<option value="2">Left Out</option>
					<option value="3">Both</option>
					<option value="1">Self Occupied</option>
			<%	}else{ %>
					<option value="3">Both</option>
					<option value="1">Self Occupied</option>
					<option value="2">Left Out</option>
			<%	} %>
				</select>
			</td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp; ii)&nbsp;&nbsp;&nbsp;&nbsp;Annual Letable Value</td>
			<td><input type="text" size="10" name="falb" id="falb" value="<%=fbn1.get(532).getINP_AMT() %>"
			onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp; iii)&nbsp;&nbsp;&nbsp;&nbsp;Unrealised Rent</td>
			<td><input type="text" size="10" name="furent" id="furent" value="<%=fbn1.get(533).getINP_AMT() %>"
			onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp; iv)&nbsp;&nbsp;&nbsp;&nbsp;Taxes Paid to local Authority</td>
			<td><input type="text" size="10" name="ftaxlocal" id="ftaxlocal" value="<%=fbn1.get(534).getINP_AMT() %>"
			onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp; v)&nbsp;&nbsp;&nbsp;&nbsp;Interest on Housing Loan</td>
			<td><input type="text" size="10" name="fihousingloan" id="fihousingloan" value="<%=fbn1.get(535).getINP_AMT() %>"
			onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<%-- <tr class="alt">
			<td colspan="2">8]&nbsp;&nbsp;&nbsp;&nbsp;Gross Total Income</td>
			<td><input type="text" size="10" name="fgrossti" id="fgrossti" value="<%=fbn1.get(572).getINP_AMT() %>"></td> 	
		</tr> --%>
		<tr class="alt">
			<td colspan="2">5]&nbsp;&nbsp;&nbsp;&nbsp;Less: Deduction under chapter VI A</td>
			<td></td><%-- <td><input type="text" size="10" name="flesschapter" id="flesschapter" value="<%=fbn1.get(573).getINP_AMT() %>"></td> --%>	
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;80C - Public Provident Fund</td>
			<td><input type="text" size="10" name="fpfvpf" id="fpfvpf" value="<%=fbn1.get(501).getINP_AMT() %>"
			onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;80C - Life Insurance premiums</td>
			<td><input type="text" size="10" name="flicp" id="flicp" value="<%=fbn1.get(537).getINP_AMT() %>"
			onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;80C - N.S.C (Investment + accrued Int first five year)</td>
			<td><input type="text" size="10" name="fnsci" id="fnsci" value="<%=fbn1.get(538).getINP_AMT() %>"
			onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;80C - ULIP</td>
			<td><input type="text" size="10" name="fnsulip" id="fnsulip" value="<%=fbn1.get(511).getINP_AMT() %>"
			onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;80C - 80C Deposit</td>
			<td><input type="text" size="10" name="fnsdep" id="fnsdep" value="<%=fbn1.get(512).getINP_AMT() %>"
			onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;80C - Sukanya Samruddhi Scheme</td>
			<td><input type="text" size="10" name="fnssads" id="fnssads" value="<%=fbn1.get(513).getINP_AMT() %>"
			onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;80C - Home. Loan (Principal Repayment)</td>
			<td><input type="text" size="10" name="fhomeloan" id="fhomeloan" value="<%=fbn1.get(539).getINP_AMT() %>"
			onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;80C - Tuition  fees  for 2 children</td>
			<td><input type="text" size="10" name="ftutionfees" id="ftutionfees" value="<%=fbn1.get(540).getINP_AMT() %>"
			onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;80C - E.L.S.S(Mutual Fund)</td>
			<td><input type="text" size="10" name="fmutualfund" id="fmutualfund" value="<%=fbn1.get(541).getINP_AMT() %>"
			onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
		<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;80C - FD (5 Years and above)</td>
			<td><input type="text" size="10" name="ffd" id="ffd" value="<%=fbn1.get(542).getINP_AMT() %>"
			onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;80C - Tax Savings Infrastructure Bonds</td>
			<td><input type="text" size="10" name="ftaxsaving" id="ftaxsaving" value="<%=fbn1.get(543).getINP_AMT() %>"
			onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;80C - Other Investments</td>
			<td><input type="text" size="10" name="fotehrinvest" id="fotehrinvest" value="<%=fbn1.get(544).getINP_AMT() %>"
			onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;80CCC -Pension  Plan</td>
			<td><input type="text" size="10" name="fpensionplan" id="fpensionplan" value="<%=fbn1.get(545).getINP_AMT() %>"
			onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;80CCD1 - New Pension Secheme (Employee Contribution)</td>
			<td><input type="text" size="10" name="fpensionschemeemp" id="fpensionschemeemp" value="<%=fbn1.get(546).getINP_AMT() %>"
			onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;80CCD2 -New Pension Scheme- Employer Contribution</td>
			<td><input type="text" size="10" name="fpensionschemeel" id="fpensionschemeel" value="<%=fbn1.get(547).getINP_AMT() %>"
			onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;80CCG -Rajiv Gandhi Equity Scheme</td>
			<td><input type="text" size="10" name="frges" id="frges" value="<%=fbn1.get(548).getINP_AMT() %>"
			onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;80D - Medical Insurance premiums+Health Check</td>
			<td><input type="text" size="10" name="fmiphelath" id="fmiphelath" value="<%=fbn1.get(549).getINP_AMT() %>"
			onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;80D - Medical Insurance premiums (for Parents)</td>
			<td><input type="text" size="10" name="fmipparents" id="fmipparents" value="<%=fbn1.get(550).getINP_AMT() %>"
			onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;80G Donation to Approved fund(as per applicability 50%/100%)</td>
			<td><input type="text" size="10" name="fdonation" id="fdonation" value="<%=fbn1.get(551).getINP_AMT() %>"
			onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Section 80DD</td>
			<td><input type="text" size="10" name="f80dd" id="f80dd" value="<%=fbn1.get(552).getINP_AMT() %>"
			onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Section 80DDB</td>
			<td><input type="text" size="10" name="f80ddb" id="f80ddb" value="<%=fbn1.get(553).getINP_AMT() %>"
			onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Section 80E</td>
			<td><input type="text" size="10" name="f80e" id="f80e" value="<%=fbn1.get(554).getINP_AMT() %>"
			onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Section 80GG</td>
			<td><input type="text" size="10" name="f80gg" id="f80gg" value="<%=fbn1.get(555).getINP_AMT() %>"
			onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Section 80GGA</td>
			<td><input type="text" size="10" name="f80gga" id="f80gga" value="<%=fbn1.get(556).getINP_AMT() %>"
			onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Section 80GGC</td>
			<td><input type="text" size="10" name="f80ggc" id="f80ggc" value="<%=fbn1.get(557).getINP_AMT() %>"
			onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Section 80U</td>
			<td><input type="text" size="10" name="f80u" id="f80ggu" value="<%=fbn1.get(558).getINP_AMT() %>"
			onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Section 80TTA- Interest received in Saving Account</td>
			<td><input type="text" size="10" name="f80tta" id="f80tta" value="<%=fbn1.get(559).getINP_AMT() %>"
			onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">6]&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Standard Deduction</td>
			<td><input type="text" size="10" name="stdded" id="stdded" value="<%=fbn1.get(500).getINP_AMT() %>"
			onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<%-- <tr class="alt">
			<td colspan="2">10]&nbsp;&nbsp;&nbsp;&nbsp;Total Income</td>
			<td></td><td><input type="text" size="10" name="ftotalincome" id="ftotalincome" value="<%=fbn1.get(574).getINP_AMT() %>"></td> 	
		</tr>
		<tr class="alt">
			<td colspan="2">11]&nbsp;&nbsp;&nbsp;&nbsp;Total Taxable Income (Round off to nearest 10 rupees)</td>
			<td></td><td><input type="text" size="10" name="ftaxableincome" id="ftaxableincome" value="<%=fbn1.get(560).getINP_AMT() %>"></td> 	
		</tr>
		<tr class="alt">
			<td colspan="2">12]&nbsp;&nbsp;&nbsp;&nbsp;Tax on Total Income</td>
			<td></td><td><input type="text" size="10" name="ftaxtotalincome" id="ftaxtotalincome" value="<%=fbn1.get(575).getINP_AMT() %>"></td> 	
		</tr>
		<tr class="alt">
			<td colspan="2">13]&nbsp;&nbsp;&nbsp;&nbsp;Rebate U/S - 87A</td>
			<td></td><td><input type="text" size="10" name="frebateus" id="frebateus" value="<%=fbn1.get(576).getINP_AMT() %>"></td> 	
		</tr>
		<tr class="alt">
			<td colspan="2">14]&nbsp;&nbsp;&nbsp;&nbsp;Total Tax after rebate</td>
			<td></td><td><input type="text" size="10" name="fafterrebate" id="fafterrebate" value="<%=fbn1.get(577).getINP_AMT() %>"></td> 	
		</tr>
		<tr class="alt">
			<td colspan="2">15]&nbsp;&nbsp;&nbsp;&nbsp;Add - Education Cess @ 3%</td>
			<td></td><td><input type="text" size="10" name="feducess" id="feducess" value="<%=fbn1.get(578).getINP_AMT() %>"></td> 	
		</tr>
		<tr class="alt">
			<td colspan="2">16]&nbsp;&nbsp;&nbsp;&nbsp;Total Tax Payable</td>
			<td></td><td><input type="text" size="10" name="ftaxpayble" id="ftaxpayble" value="<%=fbn1.get(579).getINP_AMT() %>"></td> 	
		</tr>
		<tr class="alt">
			<td colspan="2">17]&nbsp;&nbsp;&nbsp;&nbsp;Tax Deduction at Source</td>
			<td></td><td><input type="text" size="10" name="ftds" id="ftds" value="<%=fbn1.get(228).getINP_AMT() %>"></td> 	
		</tr>
		<tr class="alt">
			<td colspan="2">18]&nbsp;&nbsp;&nbsp;&nbsp;Balance Tax Payable / Refundable</td>
			<td></td><td><input type="text" size="10" name="fbalance" id="fbalance" value="<%=fbn1.get(580).getINP_AMT() %>"></td> 	
		</tr> --%>
	<%} else { %>
		<tr class="alt">
			<td colspan="2">1]&nbsp;&nbsp;&nbsp;&nbsp;     Less: Allowances exempt u/s 10 & 17( for Service Period )</td>
			 <td></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;	i)&nbsp;&nbsp;&nbsp;&nbsp;HRA exemption u/s 10(13A)</td>
			<td></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select City of Residence</td>
			<td><select name="fscr" id="fscr">
					<option value="1">Metro</option>
					<option value="2">Non Metro</option>
				</select>
			</td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp; ii)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Rent Paid</td>
			<td><input type="text" size="10" name="frp" id="frp" value="<%= 0%>" onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp; iii)&nbsp;&nbsp;&nbsp;&nbsp;Children Education Allownces</td>
			<td><input type="text" size="10" name="fcea" id="fcea" value="<%= 0 %>"onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp; iv)&nbsp;&nbsp;&nbsp;&nbsp;Children Hostel Allownces</td>
			<td><input type="text" size="10" name="fcha" id="fcha" value="<%= 0 %>"onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp; v)&nbsp;&nbsp;&nbsp;&nbsp;LTA(allowed as per rule)</td>
			<td><input type="text" size="10" name="flta" id="flta" value="<%=0%>"onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp; vi)&nbsp;&nbsp;&nbsp;&nbsp;<span  style="vertical-align:middle;">Medical Bills Reimbursement</span>			
			<input type="button" style="margin-right: 10px; float:right; "   value="Insert Bill" onClick="modifyRec('<%=EMPNO%>','524','0')">		
			</td>
			<td>
			<input type="text" size="10" name="fmbr" id="fmbr" value="<%=0%>"onkeypress="return inputLimiter(event,'Numbers')" maxlength="10">
			</td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp; vii)&nbsp;&nbsp;&nbsp;&nbsp;<span style="vertical-align:middle;" >Telephone Bills Reimbursement</span>
			<input type="button" style="margin-right: 10px; float:right;" value="Insert Bill" onClick="modifyRec('<%=EMPNO%>','587','0')" >
		 </td>
			<td>
			<input type="text" size="10" name="tbr" id="tbr" value="<%=0%>" onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp; viii)&nbsp;&nbsp;&nbsp;&nbsp;<span style="vertical-align:middle;" >Sodexo Coupons Reimbursement</span>
			<input type="button" style="margin-right: 10px; float:right;" value="Insert Bill" onClick="modifyRec('<%=EMPNO%>','588','0')"> 
			</td>
			<td><input type="text" size="10" name="scr" id="scr" value="<%=0%>" onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		
		<tr class="alt">
			<td colspan="2">2]&nbsp;&nbsp;&nbsp;&nbsp;Interst received from saving A/c (Bank/Post Post Office)</td>
			<td><input type="text" size="10" name="firsac1" id="firsac1" value="<%=0 %>"onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td> 	
		</tr>
		<tr class="alt">
			<td colspan="2">3]&nbsp;&nbsp;&nbsp;&nbsp;Add: Any other income from other sources</td>
			<td></td> 	
		</tr>	
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp; i)&nbsp;&nbsp;&nbsp;&nbsp;Interest from - Fixed Deposit(FD)</td>
			<td><input type="text" size="10" name="fifd" id="fifd" value="<%=0%>"onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp; ii)&nbsp;&nbsp;&nbsp;&nbsp;Interest from - Recuring Deposit(RD)</td>
			<td><input type="text" size="10" name="fird" id="fird" value="<%=0%>"onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp; iii)&nbsp;&nbsp;&nbsp;&nbsp;Interest on N.S.C.(Accrued/ Recd )</td>
			<td><input type="text" size="10" name="finsc" id="finsc" value="<%=0%>"onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp; iv)&nbsp;&nbsp;&nbsp;&nbsp;Interest from Post Office M.I.S (6 yrs.)</td>
			<td><input type="text" size="10" name="fipost" id="fipost" value="<%=0%>"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp; v)&nbsp;&nbsp;&nbsp;&nbsp;Interest on Saving Bonds</td>
			<td><input type="text" size="10" name="fisb" id="fisb" value="<%=0%>"onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp; vi)&nbsp;&nbsp;&nbsp;&nbsp;Other Sources</td>
			<td><input type="text" size="10" name="fos" id="fos" value="<%=0%>"onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">4]&nbsp;&nbsp;&nbsp;&nbsp;Income from house property (Total of following heads in negative)</td>
			<td><input type="text" size="10" name="fhousepr" id="fhousepr" value="<%=0%>"onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td> 	
		</tr>	
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp; i)&nbsp;&nbsp;&nbsp;&nbsp;Accommodation Status</td>
			<td><select name="facomdation" id="facomdation">
					<option value="1">Self Occupied</option>
					<option value="2">Left Out</option>
					<option value="3">Both</option>
				</select>
			</td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp; ii)&nbsp;&nbsp;&nbsp;&nbsp;Annual Letable Value</td>
			<td><input type="text" size="10" name="falb" id="falb" value="<%=0%>"onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp; iii)&nbsp;&nbsp;&nbsp;&nbsp;Unrealised Rent</td>
			<td><input type="text" size="10" name="furent" id="furent" value="<%=0%>"onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp; iv)&nbsp;&nbsp;&nbsp;&nbsp;Taxes Paid to local Authority</td>
			<td><input type="text" size="10" name="ftaxlocal" id="ftaxlocal" value="<%=0%>"onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp; v)&nbsp;&nbsp;&nbsp;&nbsp;Interest on Housing Loan</td>
			<td><input type="text" size="10" name="fihousingloan" id="fihousingloan" value="<%=0%>"onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">5]&nbsp;&nbsp;&nbsp;&nbsp;Less: Deduction under chapter VI A</td> 	
			<td></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;80C - Public Provident Fund</td>
			<td><input type="text" size="10" name="fpfvpf" id="fpfvpf" value="<%=0%>"onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;80C - Life Insurance premiums</td>
			<td><input type="text" size="10" name="flicp" id="flicp" value="<%=0%>"onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;80C - N.S.C (Investment + accrued Int first five year)</td>
			<td><input type="text" size="10" name="fnsci" id="fnsci" value="<%=0%>"onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;80C - ULIP</td>
			<td><input type="text" size="10" name="fnsulip" id="fnsulip" value="<%=0%>"onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;80C - 80C Deposit</td>
			<td><input type="text" size="10" name="fnsdep" id="fnsdep" value="<%=0%>"onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;80C - Sukanya Samruddhi Scheme</td>
			<td><input type="text" size="10" name="fnssads" id="fnssads" value="<%=0%>"onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;80C - Home. Loan (Principal Repayment)</td>
			<td><input type="text" size="10" name="fhomeloan" id="fhomeloan" value="<%=0%>"onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;80C - Tuition  fees  for 2 children</td>
			<td><input type="text" size="10" name="ftutionfees" id="ftutionfees" value="<%=0%>"onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;80C - E.L.S.S(Mutual Fund)</td>
			<td><input type="text" size="10" name="fmutualfund" id="fmutualfund" value="<%=0%>"onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;80C - FD (5 Years and above)</td>
			<td><input type="text" size="10" name="ffd" id="ffd" value="<%=0%>"onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;80C - Tax Savings Infrastructure Bonds</td>
			<td><input type="text" size="10" name="ftaxsaving" id="ftaxsaving" value="<%=0%>"onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;80C - Other Investments</td>
			<td><input type="text" size="10" name="fotehrinvest" id="fotehrinvest" value="<%=0%>"onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;80CCC -Pension  Plan</td>
			<td><input type="text" size="10" name="fpensionplan" id="fpensionplan" value="<%=0%>"onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;80CCD1 - New Pension Secheme (Employee Contribution)</td>
			<td><input type="text" size="10" name="fpensionschemeemp" id="fpensionschemeemp" value="<%=0%>"onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;80CCD2 -New Pension Scheme- Employer Contribution</td>
			<td><input type="text" size="10" name="fpensionschemeel" id="fpensionschemeel" value="<%=0%>"onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;80CCG -Rajiv Gandhi Equity Scheme</td>
			<td><input type="text" size="10" name="frges" id="frges" value="<%=0%>"onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;80D - Medical Insurance premiums+Health Check</td>
			<td><input type="text" size="10" name="fmiphelath" id="fmiphelath" value="<%=0%>"onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;80D - Medical Insurance premiums (for Parents)</td>
			<td><input type="text" size="10" name="fmipparents" id="fmipparents" value="<%=0%>"onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;80G Donation to Approved fund(as per applicability 50%/100%)</td>
			<td><input type="text" size="10" name="fdonation" id="fdonation" value="<%=0%>"onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Section 80DD</td>
			<td><input type="text" size="10" name="f80dd" id="f80dd" value="<%=0%>"onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Section 80DDB</td>
			<td><input type="text" size="10" name="f80ddb" id="f80ddb" value="<%=0%>"onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Section 80E</td>
			<td><input type="text" size="10" name="f80e" id="f80e" value="<%=0%>"onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Section 80GG</td>
			<td><input type="text" size="10" name="f80gg" id="f80gg" value="<%=0%>"onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Section 80GGA</td>
			<td><input type="text" size="10" name="f80gga" id="f80gga" value="<%=0%>"onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Section 80GGC</td>
			<td><input type="text" size="10" name="f80ggc" id="f80ggc" value="<%=0%>"onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Section 80U</td>
			<td><input type="text" size="10" name="f80u" id="f80ggu" value="<%=0%>"onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Section 80TTA- Interest received in Saving Account</td>
			<td><input type="text" size="10" name="f80tta" id="f80tta" value="<%=0 %>"></td>
		</tr>
		<tr class="alt">
			<td colspan="2">6]&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Standard Deduction</td>
			<td><input type="text" size="10" name="stdded" id="stdded" value="<%=0%>"
			onkeypress="return inputLimiter(event,'Numbers')" maxlength="10"></td>
		</tr>
		<%} %>
	</table>
	</div>
	<table id="customers">
		<tr class="alt">
			<th width="560px"><!-- <td align="center" width="650px"> -->
			<%if(fbn1.size()>0){
			if(!fbn1.get(558).getCF_SW().equalsIgnoreCase("F")){ %>
		<input type="Submit" value="Update">
		<input type="hidden" name="hdnbt" />
		<input type="button" name="bt" value="Finalize" onclick="check(this.value)" />
		<input type="reset" value="Clear">
		<%}}else{ 
		if(fbn1.size()>0){ %>
		<input type="Submit" value="Update">
		<%}else{ %>
		<input  type="Submit" value="Save">
		<%} %>
		
		<input type="hidden" name="hdnbt" />
		
		
		<input type="button" name="bt" value="Finalize" onclick="check(this.value)" />
		
		<input type="reset" value="Clear">
		<%} %>
		<!-- 	</td> --></th>
		</tr><input type="hidden" id="year1" value="<%=year %>"/>
	</table></td>
	<td width="20"></td>
	<td><table id="customers" align="right" width="550" >
<tr> <th colspan="4" width="530px"> TAX RULES & OTHER USEFUL INFORMATION</th></tr>
<tr class="alt"> <td>
<div style="height: 470px; width:580px; overflow-y: scroll;">
<table  id="customers">
		<%if(year.equalsIgnoreCase("2016")){ %>
		<tr class="alt"><td>1. &nbsp;&nbsp;HRA exemption = minimum of (40% (50% for metros) of Basic+DA or HRA or rent paid - 10% &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;of Basic+DA)</td></tr>	
		<tr class="alt"><td>2. &nbsp;&nbsp;Transport allowance is exempt upto  1600/- per month provided the person is in India &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;during the month. For people having permanent physical disability, the exemption is &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3,200/- per month</td></tr>
		<tr class="alt"><td>3. &nbsp;&nbsp;Medical bills are exempt for self and dependent family, upto 25,000/- per annum</td></tr>
		<tr class="alt"><td>4. &nbsp;&nbsp;"LTA is exempt to the tune of ecomony class airfare for the family to any destination in &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;India, by the shortest route.LTA can be claimed twice in a block of 4 calendar years. The &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;current block is from Jan 2014 to Dec 2017"</td></tr>
	     <tr class="alt"><td>5. &nbsp;&nbsp;Uniform allowance is exempt to the extent of bills produced for purchase of uniforms</td></tr>
	    <tr class="alt"><td>6. &nbsp;&nbsp;Gratuity (max. 10 lac) , VRS (max. 5 lacs) and some such amounts are exempt upto certain &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;limits. If you get any such payment, please find out the exact limit for you from a tax  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;consultant and enter in cell N53</td></tr>
	   	<tr class="alt"><td>7. &nbsp;&nbsp;Children's Education allowance is exempt upto 100/- per child per month plus 300/- per &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;child per month for hostel expenses (max of 2 children only)</td></tr>
	   	<tr class="alt"><td>8. &nbsp;&nbsp;There is an exemption for interest on housing loan. If the loan was taken before Apr 1, &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1999 exemption is limited to 30,000/- per year. If the loan was taken after Apr 1, 1999 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;exemption is limited to 2,50,000/- per year if the house is self-occupied; there is no limit &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;if the house is rented out, but the rent (less 30% of rent as std. deduction and municipal &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; taxes) needs to be declared as income</td></tr>
	   	<tr class="alt"><td>9. &nbsp;&nbsp;If you have rented out your house, enter the total rent income/loss from the house &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(after deducting property tax and 30% of rent as standard maintenance expenses) in cell &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;N61</td></tr>
	 	<tr class="alt"><td>10. &nbsp;Medical Insurance (such as Mediclaim) premium is exempt upto 25,000/- per year for self, &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;spouse & dependent children. Within this limit 5,000/- could be used for preventive &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;health check expenses. An additional 15,000/- is exempt towards premium for parents &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(even if they are not dependent). If the parent(s) are above 65 years of age, an extra &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5,000/- can be claimed</td></tr>
	 	<tr class="alt"><td>11. &nbsp;Deduction in respect of medical treatment of handicapped dependents is limited to &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;50,000/- per year if the disability is less than 80% and 1,00,000/- per year if the disability &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;is more than 80%</td></tr>
	 	<tr class="alt"><td>12. &nbsp;Deduction in respect of medical treatment for specified ailments or diseases for the &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;assesse or dependent can be claimed upto 40,000/- per year. If the person being treated &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;is a senior citizen,the exemption can go up to 60,000/-</td></tr>
	 	<tr class="alt"><td>13. &nbsp;Interest repayment on education loan (taken for higher education from a university &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;for self, spouse & children) is tax exempt from the 1st year of repayment up to a &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;maximum of 8 year There is no exemption for Principal payment	</td></tr>
	 	<tr class="alt"><td>14. &nbsp;Donations to certain charities are tax exempt to the tune of  50% of donation.Please  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;enter the amount donated in cell  N70 </td></tr>
	 	<tr class="alt"><td>15. &nbsp;If you do not get HRA, but have rented a house, an exemption is available. This will be &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;calculated as minimum of (25% of total income or rent paid - 10% of total income or &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;24,000/- per year)</td></tr>
	 	<tr class="alt"><td>16. &nbsp;Donations for certain scientific research and rural development are exempt, as well as &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;donations to some charities under section 35AC or section 80GGA. Please enter the &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;actual amount exempt in cell N74</td></tr>
	 	<tr class="alt"><td>17. &nbsp;Interest from Savings bank account is exempt up to 10,000/- per year.</td></tr>
	 	<tr class="alt"><td>18. &nbsp;If you have a permanent physical disability (including blindness), you can take an &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;exemption of up to 75,000/- per year</td></tr>
	 	<tr class="alt"><td>19. &nbsp;Investments up to 1.5 lac in PF, VFP, PPF, Insurance Premium, Housing loan principal &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;repayment, Stamp duty/registration charges for purchase of new home, NSC, ELSS, long &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;term bank Fixed Deposit, Post Office Term Deposit, New Pension Scheme, etc. are &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;deductible from the taxable income under sec 80C. There is no limit on individual &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;items, so all 1.5 lac can be invested in NSC, for example</td></tr>
	 	<tr class="alt"><td>20. &nbsp;As per clarification from IT department, all perquisites such as rent-free &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;accommodation, company provided car, free or concessional education facilities, &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;employee stock option plan, free club membership, company provided credit card, gift &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;vouchers, meal coupons, hotel stay beyond 15 days, are fully taxable. This tax calculator &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;calculates the tax incidence for accommodation and car. If you receive any other &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;perquisite, please include the value of such perquisite in cell S21</td></tr>
	 	<tr class="alt"><td>21. &nbsp;For the current year, Govt. prescribed rate of interest for PF is 8.75%. If the employer &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;pays interest higher than this, the differential interest earned is treated as perquisites</td></tr>
	 	<tr class="alt"><td>22. &nbsp;Rajiv Gandhi Equity Savings Scheme exemption is available for investment in stock markets &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(direct equity or Mutual Funds). Avaialble only for those with gross income less than 12 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;lacs and only for first time investors in stock market. Exemption available at 50% of &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;investment subject to maximum of 50,000/- invested. Investments are locked-in for &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;three years</td></tr>
	 	<tr class="alt"><td>23. &nbsp;Residents of Sikkim are exempt from Income Tax</td></tr>
	 	<% }else{%>
	 	<tr class="alt" ><td>1. &nbsp;&nbsp;HRA exemption = minimum of (40% (50% for metros) of Basic+DA or HRA or rent paid - 10% &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;of Basic+DA)</td></tr>	
		<tr class="alt"><td>2. &nbsp;&nbsp;Transport allowance is exempt upto  800/- per month provided the person is in India &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;during the month. For people having permanent physical disability, the exemption is &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1,600/- per month</td></tr>
		<tr class="alt"><td>3. &nbsp;&nbsp;Medical bills are exempt for self and dependent family, upto 15,000/- per annum</td></tr>
		<tr class="alt"><td>4. &nbsp;&nbsp;"LTA is exempt to the tune of ecomony class airfare for the family to any destination in &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;India, by the shortest route.LTA can be claimed twice in a block of 4 calendar years. The &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;current block is from Jan 2014 to Dec 2017"</td></tr>
	    <tr class="alt"><td>5. &nbsp;&nbsp;Uniform allowance is exempt to the extent of bills produced for purchase of uniforms</td></tr>
	    <tr class="alt"><td>6. &nbsp;&nbsp;Gratuity (max. 10 lac) , VRS (max. 5 lacs) and some such amounts are exempt upto certain &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;limits. If you get any such payment, please find out the exact limit for you from a tax  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;consultant and enter in cell N53</td></tr>
	   	<tr class="alt"><td>7. &nbsp;&nbsp;Children's Education allowance is exempt upto 100/- per child per month plus 300/- per &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;child per month for hostel expenses (max of 2 children only)</td></tr>
	   	<tr class="alt"><td>8. &nbsp;&nbsp;There is an exemption for interest on housing loan. If the loan was taken before Apr 1, &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1999 exemption is limited to 30,000/- per year. If the loan was taken after Apr 1, 1999 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;exemption is limited to 2,00,000/- per year if the house is self-occupied; there is no limit &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;if the house is rented out, but the rent (less 30% of rent as std. deduction and municipal &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; taxes) needs to be declared as income</td></tr>
	   	<tr class="alt"><td>9. &nbsp;&nbsp;If you have rented out your house, enter the total rent income/loss from the house &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(after deducting property tax and 30% of rent as standard maintenance expenses) in cell &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;N61</td></tr>
	 	<tr class="alt"><td>10. &nbsp;Medical Insurance (such as Mediclaim) premium is exempt upto 15,000/- per year for self, &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;spouse & dependent children. Within this limit 5,000/- could be used for preventive &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;health check expenses. An additional 15,000/- is exempt towards premium for parents &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(even if they are not dependent). If the parent(s) are above 65 years of age, an extra &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5,000/- can be claimed</td></tr>
	 	<tr class="alt"><td>11. &nbsp;Deduction in respect of medical treatment of handicapped dependents is limited to &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;50,000/- per year if the disability is less than 80% and 1,00,000/- per year if the disability &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;is more than 80%</td></tr>
	 	<tr class="alt"><td>12. &nbsp;Deduction in respect of medical treatment for specified ailments or diseases for the &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;assesse or dependent can be claimed upto 40,000/- per year. If the person being treated &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;is a senior citizen,the exemption can go up to 60,000/-</td></tr>
	 	<tr class="alt"><td>13. &nbsp;Interest repayment on education loan (taken for higher education from a university &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;for self, spouse & children) is tax exempt from the 1st year of repayment up to a &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;maximum of 8 year There is no exemption for Principal payment	</td></tr>
	 	<tr class="alt"><td>14. &nbsp;Donations to certain charities are tax exempt to the tune of  50% of donation.Please  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;enter the amount donated in cell  N70 </td></tr>
	 	<tr class="alt"><td>15. &nbsp;If you do not get HRA, but have rented a house, an exemption is available. This will be &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;calculated as minimum of (25% of total income or rent paid - 10% of total income or &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;24,000/- per year)</td></tr>
	 	<tr class="alt"><td>16. &nbsp;Donations for certain scientific research and rural development are exempt, as well as &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;donations to some charities under section 35AC or section 80GGA. Please enter the &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;actual amount exempt in cell N74</td></tr>
	 	<tr class="alt"><td>17. &nbsp;Interest from Savings bank account is exempt up to 10,000/- per year.</td></tr>
	 	<tr class="alt"><td>18. &nbsp;If you have a permanent physical disability (including blindness), you can take an &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;exemption of up to 75,000/- per year</td></tr>
	 	<tr class="alt"><td>19. &nbsp;Investments up to 1.5 lac in PF, VFP, PPF, Insurance Premium, Housing loan principal &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;repayment, Stamp duty/registration charges for purchase of new home, NSC, ELSS, long &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;term bank Fixed Deposit, Post Office Term Deposit, New Pension Scheme, etc. are &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;deductible from the taxable income under sec 80C. There is no limit on individual &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;items, so all 1.5 lac can be invested in NSC, for example</td></tr>
	 	<tr class="alt"><td>20. &nbsp;As per clarification from IT department, all perquisites such as rent-free &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;accommodation, company provided car, free or concessional education facilities, &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;employee stock option plan, free club membership, company provided credit card, gift &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;vouchers, meal coupons, hotel stay beyond 15 days, are fully taxable. This tax calculator &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;calculates the tax incidence for accommodation and car. If you receive any other &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;perquisite, please include the value of such perquisite in cell S21</td></tr>
	 	<tr class="alt"><td>21. &nbsp;For the current year, Govt. prescribed rate of interest for PF is 8.75%. If the employer &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;pays interest higher than this, the differential interest earned is treated as perquisites</td></tr>
	 	<tr class="alt"><td>22. &nbsp;Rajiv Gandhi Equity Savings Scheme exemption is available for investment in stock markets &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(direct equity or Mutual Funds). Avaialble only for those with gross income less than 12 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;lacs and only for first time investors in stock market. Exemption available at 50% of &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;investment subject to maximum of 50,000/- invested. Investments are locked-in for &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;three years</td></tr>
	 	<tr class="alt"><td>23. &nbsp;Residents of Sikkim are exempt from Income Tax</td></tr>
	 	<% }%>
		<tr class="alt" align="left">
		</tr></table></div></td></tr>
	</table></td></tr></table>
</form>
	
<% } }catch(Exception e){
	e.printStackTrace();

	%>
	<script type="text/javascript">

	window.location.href="Form16Search.jsp";

	</script>
	<%}
%>
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
	
<%-- 	<input type="hidden" name="flag" id="flag" value="<%=flag%>">	 --%>
	<div id="process"  style="z-index: 1000;background-color:white;height: 100%;width: 100%;top: 0px;left: 0px;position: fixed;opacity:0.8;" hidden=true; >
				<div align="center" style="padding-top: 20%;">
				
				<h1>Processing For Form 16 Entries</h1>
				
				<img alt="" src="images/process.gif">
				</div>
	</div>
	<div class="clear">&nbsp;</div>

</div>
<!--  end content -->
<div class="clear">&nbsp;</div>
</div>
<!--  end content-outer........................................................END -->

<div class="clear">&nbsp;</div>
              

</body>
</html>
	