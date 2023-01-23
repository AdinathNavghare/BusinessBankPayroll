<%@page import="payroll.DAO.RoleDAO"%>
<%@page import="payroll.DAO.EmpOffHandler"%>
<%@page import="payroll.Model.EmpOffBean"%>
<%@page import="payroll.Model.EmployeeBean"%>
<%@page import="payroll.DAO.EmployeeHandler"%>
<%@page import="payroll.Model.LMB"%>
<%@page import="payroll.DAO.LMH"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="payroll.Model.Lookup"%>
<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="java.lang.*"%>
<%@page import="payroll.DAO.ConnectionManager"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Leave Application Form</title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<%
					SimpleDateFormat dateFormat1;
	   				 dateFormat1 = new SimpleDateFormat("dd-MMM-yyyy");
					 Date date1 = new Date();
					 String currentdate1 = dateFormat1.format(date1);  
					%>
<script>

	jQuery(function() {	
			//$("#empno").autocomplete("list.jsp");
			$("#empno").autocomplete("list1.jsp");
	});
	jQuery(function() {
        $("#lreason").autocomplete("selectLeavePurpose.jsp");
	});
/* 	jQuery(function() {
        $("#reasonDetails").autocomplete("selectLeavePurpose.jsp");
	}); */
	
</script>
<script type="text/javascript">

// For Applied Leave Between Date
function chk_bet_date()
{
		var empno = document.getElementById("empno01").value;
		var fromDate=document.getElementById("frmdate").value;
	   	var toDate=document.getElementById("todate").value;
	   	var appn=0000000; // for apply leave as flagg
	//	var leaveType=document.getElementById("").value;
	try
	{
	
	var xmlhttp=new XMLHttpRequest();
 	var url="";
 	var response2="";
 	
/*  	url="LMS?action=betDate&empno="+empno+"&frmdate="+fromDate+"&todate="+toDate;
 */ 	url="LMS?action=betDate&empno="+empno+"&frmdate="+fromDate+"&todate="+toDate+"&appNo="+appn;

	xmlhttp.onreadystatechange=function()
	{
		if(xmlhttp.readyState==4)
		{
		
		response2 = xmlhttp.responseText;
		 if(response2>0 )
			{
			 alert("You have already applied leave for this date...");
			 document.getElementById('leavecode').value="";
			 fromDate=document.getElementById("frmdate").value="";
			 toDate=document.getElementById("todate").value="";
				return false;
			}
		}
		
		};
 
	xmlhttp.open("POST", url, true);
	xmlhttp.send();
	}
	catch(e)
	{
		
	}
}

function getEmployee()
{

	var e = document.getElementById("empno").value;
	var res=e.split(":");
	 
	if(e=="")
		{
			alert("Please Enter Employee...!");
		}
	else
		{
		 var atpos=e.indexOf(":");
		if (atpos<1)
		  {
		  alert("Please Select Correct Employee Name");
		  return false;
		  }
		else
			{
		
			window.location.href = "leave.jsp?empno="+res[2];
			}
		}

}

// To show edit table div ......
function editLeave(empno,appno){
	window.location.href = "leave.jsp?action=editLeaveApp&empno="+empno+"&appno="+appno;
}


// For 5 Consecutive CL...
 function consecutive_leave()
{
	
	var con_leave = document.getElementById("leavecode").value;	
	if(con_leave=="CL" ||con_leave=="3")
{
		
	var empno = document.getElementById("empno01").value;
	var fromDate=document.getElementById("frmdate").value;
	var toDate=document.getElementById("todate").value;
	var days = document.getElementById("days").value;
	   
	   
	var xmlhttp=new XMLHttpRequest();
 	var url="";
 	var response2="";
 	var TotDays="";
 	url="LMS?action=consecutive_CL&empno="+empno+"&frmdate="+fromDate+"&todate="+toDate;
 
	xmlhttp.onreadystatechange=function()
	{
		
		if(xmlhttp.readyState==4)
		{
			
		response2 = xmlhttp.responseText;
	  	 TotDays = (+days + +response2);
		if(TotDays > 4 && (con_leave=="CL" || con_leave=="3") )
		 {
		 	alert("You Can Not Apply 5 Consecutive CL ");
		 	document.getElementById('leavecode').value="";
		 	return false;
		 }
		
		 }
		};
 
	xmlhttp.open("POST", url, true);
	xmlhttp.send();
}
} 

// For Maternity Leave
function matLeave(cnt)
{

	 var mleave = document.getElementById("leavecode").value;
	 var empno = document.getElementById("empno01").value;
	 var fromDate=document.getElementById("frmdate").value;
	 var toDate=document.getElementById("todate").value;
	 
	var xmlhttp=new XMLHttpRequest();
 	var url="";
 	var response1="";
 	url="LMS?action=matLeave&empno="+empno;
	
 	try
 	{
	xmlhttp.onreadystatechange=function()
	{
		if(xmlhttp.readyState==4)
		{
		response1 = xmlhttp.responseText;
		
		 if(response1>0 && mleave == "MAT_L")
			{
				alert("You Can not Apply Maternity Leave For Male...!");
				document.getElementById('leavecode').value="";
			
				return false;
			} 
		 else
			 {
			 if(cnt==2){
			   if(fromDate!="" && toDate!="")
			     {
			 	 chk_bet_date();
			     } 
			           }
			 consecutive_leave();
			 }
		 }
	};
	xmlhttp.open("POST", url, true);
	xmlhttp.send(); 
 	}
 	catch(e)
 	{
 	//	alert(e);
 	}
	
	if(mleave == "LATE_L")
	{
		//alert("Late_L is: "+ mleave);
		document.getElementById('lreason').value="Late Leave Will Deducted As CL,PL,LWP";
	//	document.getElementById('lreason').disabled = true;
	}
	else
		{
			document.getElementById('lreason').disabled = false;
			document.getElementById('lreason').value="";
		}
	
	
}

/* Temprory Selection Comment */
 <%--   function chkappdt(){
	var empno = document.getElementById("empno01").value;
	var tdate = document.getElementById("tdate").value;
	var xmlhttp=new XMLHttpRequest();
 	var url="";
 	var response1="";
 	url="LMS?action=chkappdt&empno="+empno+"&tdate="+tdate;
 	try
 	{
	xmlhttp.onreadystatechange=function()
	{
		if(xmlhttp.readyState==4)
		{
		response1 = xmlhttp.responseText;
		
		  if(response1 == 0)
			{
				alert("you can select only the application date within current payroll month of this employee..!");
				document.getElementById('tdate').value="<%=currentdate1%>";
				document.getElementById('frmdate').value="";
				return false;
			}  
		 
		 }
	};
	xmlhttp.open("POST", url, true);
	xmlhttp.send(); 
 	}
 	catch(e)
 	{
 	//	alert(e);
 	}
	
}
 --%>  // for check date within the payroll month
  function chkaFromToDate(){
		var empno = document.getElementById("empno01").value;
		//var tdate = document.getElementById("tdate").value;
		var fromdate = document.getElementById("frmdate").value;
		var todate = document.getElementById("todate").value;
		var xmlhttp=new XMLHttpRequest();
	 	var url="";
	 	
	 	var response1="";
	 	if(fromdate=="")
	 	{
	 		url="LMS?action=chkappdt&empno="+empno+"&tdate="+todate;
	 		
	 	}else if(todate=="")
	 	{
	 		url="LMS?action=chkappdt&empno="+empno+"&tdate="+fromdate;
	 	}
	 	//url="LMS?action=chkappdt&empno="+empno+"&tdate="+tdate;
	 	debugger;
	 	try
	 	{
		xmlhttp.onreadystatechange=function()
		{
			if(xmlhttp.readyState==4)
			{
			response1 = xmlhttp.responseText;
			
		/* 	  if(response1 == 0)
				{
					alert("you can select only the date within current payroll month of this employee..!");
					document.getElementById('tdate').value="";
					document.getElementById('frmdate').value="";
					return false;
				}  
		 */	 
			 }
		};
		xmlhttp.open("POST", url, true);
		xmlhttp.send(); 
	 	}
	 	catch(e)
	 	{
	 	//	alert(e);
	 	}
		
	}
  
  
function validate(){
	/* var ret=  setTimeout(function () {chk_bet_date(); }, 200);
	alert(ret);
	if(ret)
	{ */
	var frmdt=document.getElementById("frmdate").value;//@niket
	var year=frmdt.split("-");//@niket
	
				if(document.getElementById("leavecode").value == "Default" || document.getElementById("leavecode").value==""){
			
					alert("Please select Leave Type.");
					document.leaveForm1.leavecode.focus();
					return false;		
				 }
				 
				 if(document.getElementById("lreason").value =="" ){
						alert("Please Select Leave Reason");
						document.getElementById("lreason").focus();
						return false;
				 }
				  // var date = document.getElementById("tdate").value;
				   var fromDate=document.getElementById("frmdate").value;
				   var toDate=document.getElementById("todate").value;
				   var slyear=parseInt(year[2])-1;
				  
				 	var chk=slyear+"-12-28";//@niket
				 	
				 	var chk2=year[2]+"-01-03";
				 	
				 	
				   fromDate = fromDate.replace(/-/g,"/");
				   toDate = toDate.replace(/-/g,"/");
				   chk=chk.replace(/-/g,"/");//@niket
				   chk2=chk2.replace(/-/g,"/");//@niket
				  // date = date.replace(/-/g,"/");
					
				 if(fromDate == "" || toDate == ""){
						 alert("please select From Date and To Date.!");
						 return false;
				 }
				 	var d1 = new Date(fromDate);	 	
				 	var d2 =new  Date(toDate);
					var d3 =new  Date(chk);//@niket
					var d4 =new  Date(chk2);//@niket
				
				var SalMonth=document.getElementById("salaryMonth").value;
					 	SalMonth = SalMonth.replace(/-/g,"/");
								 	
						var saldate=new Date(SalMonth);
							 	
							 /* 	 alert(saldate);
							 	alert(d1); 
							 	 */
							/*  if (saldate.getTime() > d1.getTime())
							 {
								   alert("The salary of this month is already finalized. You can't apply for leave!!");
								   
								   return false;
							  } */
				
				 if (d1.getTime() > d2.getTime())
				 {
					   alert("Invalid Date Range!\n FromDate can't be greater than TODate!");
					   document.getElementById("todate").focus();
					   return false;
				  }
				 
				 var datediff = d2.getTime() - d1.getTime();
				 var radios = document.getElementById('halfday').checked;
				 if(radios == true && datediff != 0 && !(isNaN(datediff))){
				 	alert("Date Must Be Same To Apply For Half Day Leave !");
				 	return false;
				 } 
				
				 var balance= document.getElementById("leavecode");
				   
				    var plBalance= balance.options[1].title;
				   
				   
				    var slBalance= balance.options[2].title;
				  
				    var clBalance= balance.options[3].title;
			
				    var coffBalance= balance.options[4].title;
				  
				    //var encashBalance= balance.options[5].title;
				    var lateBalance= balance.options[5].title;
				
				    var lwpBalance= balance.options[6].title;
				    var maternityBalance= balance.options[7].title;
				    
				    var transferBalance= balance.options[8].title;
				    var suspenededBalance= balance.options[9].title;
			
				    
				    var appliedLeaveType=document.getElementById("leavecode").value;
				    var days=document.getElementById("days").value;
				   
				    if(appliedLeaveType=="PL" || appliedLeaveType=="1"){
				    	
				    	if(parseFloat(plBalance)==0){
				    		alert("PL balance is zero");
				    		return false;
				    	}
			           if((parseInt(days)<=4)  && (parseFloat(clBalance)>=parseFloat(days))){
				 	    	      var retire_flag=true;
			        	   var retirementdate1 = document.getElementById("retirementDate").value ;
						 	retirementdate1 = retirementdate1.replace(/-/g,"/");
							var r11 = new Date(retirementdate1);
							if(d1.getTime()>r11.getTime()||d2.getTime()>r11.getTime()){
								alert("From date / To Date Must Be Earlier Than Retirement Date.");
								document.getElementById("frmdate").focus();
								document.getElementById("todate").focus();
								flag= false;	
							}
							if(retire_flag==true){
				 	    		 var r=  confirm("You have CL balance, Are You Sure To Apply For PL.");
				 	    		 if(r==true){
				 						var p = prompt("Type Yes To Allow Otherwise Type NO"); 
				 						 if(p=="yes" || p=="Yes" || p=="YES"){	
				 							 return true; 
				 						 }
				 						 else{
				 							 return false;
				 						 }
				 					 }
				 					 else{
				 							return false;
				 						}
								}else if(retire_flag==false){
										return false;
									}
				 	    	}
			           
				    	/* if((parseInt(days)<=4)  && (parseFloat(clBalance)>=parseFloat(days))){
				    		
				    		alert("You can not apply PL for "+days+" days if you have CL balance");
				    		return false;
				    	
				    	}	 */	   
				        if(parseFloat(days)>parseFloat(plBalance)){
			    		alert("You do not have enough PL balance to apply for "+days+" leaves");
			    		retval= false;
			    	}
				    }
				    
				    if(appliedLeaveType=="CL"){
				    	if(parseFloat(clBalance)==0){
				    		alert("CL balance is zero");
				    		return false;
				    	}
				    	if(parseInt(days)>4){
				    		alert("You can not apply CL for more than FOUR days");
				    		return false;
				    	}
				    	if(parseFloat(days)>parseFloat(clBalance)){
				    		alert("You do not have enough CL balance to apply for "+days+" leaves");
				    		return false;
				    	}
				     
				    }
				   
				    /* Temprory Selection Comment */
				   /*  if(appliedLeaveType=="SL"){
				    	if(parseFloat(slBalance)==0){
				    		alert("SL balance is zero");
				    		return false;
				    	}if(d1.getTime()<d3.getTime()||d2.getTime()>d4.getTime()){//@niket
				    	if(parseInt(days)<4){
				    		alert("SL must be for minimum of 4 days");
				    		return false;
				    	}}//if(parseInt(days)<4){alert("not done...");}
				    	if(parseFloat(days)>parseFloat(slBalance)){
				    		alert("You do not have enough SL balance to apply for "+days+" leaves");
				    		return false;
				    	}
				    }
				    */ 
		
				    
				    if(appliedLeaveType=="MAT_L"){
				    	if(parseInt(days)>180){
				    		alert("Maternity leaves can be taken for maximum 180 days.");
				    		return false;
				    	}
				    }
				    
				   
			
			 	if(document.getElementById("leavecode").value == "2"){
					 var days=document.getElementById("days").value;
					 if(d1.getTime()<d3.getTime()||d2.getTime()>d4.getTime()){//@niket
						 if(parseFloat(days)<4)
						 {
						 alert("SL can't be applied for less than three days");
						 document.getElementById("days").focus();
						 return false;	
						 } 
					} }
						 var retirementdate = document.getElementById("retirementDate").value ;
					 	retirementdate = retirementdate.replace(/-/g,"/");
						var r1 = new Date(retirementdate);
						if(d1.getTime()>r1.getTime()||d2.getTime()>r1.getTime()){
							alert("From date / To Date Must Be Earlier Than Retirement Date.");
							document.getElementById("frmdate").focus();
							document.getElementById("todate").focus();
							return false;	
						}
						
						   var check=confirm("Are you sure to Add a Leave?");
						   if(!check){
							   return false;
						   }
//	}
		
	// return retval;
}

/* function validateForm(){
	
	if(document.getElementById("lreason").value =="" ){
		alert("Please Select Leave Reason");
		document.getElementById("lreason").focus();
		return false;
	}
	
	 return false;
} */


function chk_leave_year() {
	 var fromDate=document.getElementById("frmdate").value;
	 var toDate=document.getElementById("todate").value;
	 
	 var xmlhttp=new XMLHttpRequest();
	 	var url="";
	 	var response2="";
	 	var TotDays="";
	 	url="LMS?action=chkLeaveYear&frmdate="+fromDate+"&todate="+toDate;
	 
		 xmlhttp.onreadystatechange=function()
		{
			if(xmlhttp.readyState==4)
			{
			response2 = xmlhttp.responseText;
		  	
			 if(response2 == 0 )
			 {
			 	alert("You Can Not Apply Leave on this Year !! ");
			 	document.getElementById("frmdate").value="";
			 	document.getElementById("todate").value="";
			 	document.getElementById("days").value="";
			 	return false;
			 } 
			
			 }
			};
		xmlhttp.open("POST", url, true);
		xmlhttp.send(); 
} 







function setDays(){
	
	var fromDate=document.getElementById("frmdate").value;
	var toDate=document.getElementById("todate").value;
	
		/* if(fromDate.substring(0,2)>toDate.substring(0,2) && ((toDate!="") && (fromDate!="") ) ){
			alert("To date shall not be earlier than From Date");
			document.getElementById("frmdate").value="";
			document.getElementById("todate").value="";
			document.getElementById("days").value="";
			return false;

		} */
		 
		if (((toDate!="") && (fromDate!="") ) ) {
			
				if(fromDate!=toDate && document.getElementById('halfday').checked ){
				alert("From Date and To Date must be same for Half day");
				document.getElementById("frmdate").value="";
				document.getElementById("todate").value="";
				document.getElementById("days").value="";
				return false;
			}
			
			if(fromDate==toDate && document.getElementById('halfday').checked ){
				document.getElementById("days").value=0.5;
			}
			
			if(fromDate==toDate){
				document.getElementById("days").value=1;
			}
			 if(fromDate!=toDate){
			
				  fromDate=parseMyDate(fromDate);
				  toDate=parseMyDate(toDate);
				//  alert(fromDate+"--1--"+toDate);
				//  document.getElementById("days").value=(toDate.getTime()-fromDate.getTime())/(24*60*60*1000)+1;
				var day =  Math.round((toDate.getTime()-fromDate.getTime())/(24*60*60*1000)+1); 
				
				document.getElementById("days").value= day;
		} 
		 //  var date = new Date();	 
		   var fromDate=document.getElementById("frmdate").value;
		   var toDate=document.getElementById("todate").value;
		   fromDate = fromDate.replace(/-/g,"/");
			toDate = toDate.replace(/-/g,"/");
		
			var d1 = new Date(fromDate);	 	
		 	var d2 =new  Date(toDate);
		 	
		 if (d1.getTime() > d2.getTime())
		 {
			   alert("\t\t Invalid Date Range!\n\n FromDate can't be greater than TODate!");
			   document.getElementById("todate").focus();
			   document.getElementById("days").value="";
			   document.getElementById("todate").value="";
			   return false;
		  }
		 
		
		 var datediff = d2.getTime() - d1.getTime();
		 var radios = document.getElementById('halfday').checked;
		 if(radios == true && datediff != 0 && !(isNaN(datediff))){
		 	alert("Date Must Be Same To Apply For Half Day Leave !");
		 	return false;
		 }
		 
		 if(fromDate==""){
			 alert("Please select From Date");
			 return false;
		 }
		 
		 if(toDate==""){
			 alert("Please select To Date");
			 return false;
		 }
		 
		 
		/*  var mob = document.leaveForm.telephone.value;
		 if(mob == "" || mob == 0) {
			    return 	true;

			}
			return true; */
		}
		chk_leave_year();
}

function onSelection(){
	
	var fromDate=document.getElementById("frmdate").value;
	var toDate=document.getElementById("todate").value;
	if (((toDate!="") && (fromDate!="") ) ) {
	if(fromDate==toDate){
		if (document.getElementById('halfday').checked){
			document.getElementById("days").value=0.5;
		}else{
			document.getElementById("days").value=1;
		}
	}	
	}
/* 	else {
		if(document.getElementById('halfday').checked)
		
			alert("You can not apply half days for a duration more than a day");
			document.getElementById("frmdate").value="";
			document.getElementById("todate").value="";
			document.getElementById("days").value="";
			return false;
		}
	*/

}

function parseMyDate(s) {
	
	var m = [ 'jan', 'feb', 'mar', 'apr', 'may', 'jun', 'jul', 'aug',
			'sep', 'oct', 'nov', 'dec' ];
	var match = s.match(/(\d+)-([^.]+)-(\d+)/);
	var date = match[1];
	var monthText = match[2];
	var year = match[3];
	var month = m.indexOf(monthText.toLowerCase());
	return new Date(year, month, date);
}

function checkLoan(){
	
	
	var MatLeave = document.getElementById("leavecode").value;
	var flag =document.getElementById("flag").value;
	
	 if (flag=="1") {
		alert("Leave applied Successfully");
		window.location.href="leave.jsp";
	}
	 else if (flag =="0") {
			alert("Please Check Your Leave Balance");			
	}
	  else if(flag=="8"){
			alert("You have already applied leave for this date");
		} 
	 else if(flag=="4"){
			alert("Some problem in current leave process. ");
		}
	  /* else if(flag=="7"){
		 alert("You Can Not applied Maternity leave...!");
	 }  */
	/*  else if(flag=="3"){
		 alert("Either From date or To date is a date of holiday");
		} */
		
	 else if ((flag!="3")&& (flag!="8")&& (flag!="1") && (flag!="0") && (flag!= "100")&& (flag!= "4")){
			//alert(flag);
			alert("Please apply the leave from the date :"+flag+" since there are weekoffs in between");
		} 
}


function inputLimiter(e, allow) {
		var AllowableCharacters = '';
		if (allow == 'Numbers') {
			AllowableCharacters = '1234567890';
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
	
	function toLookup(){
		var flag = confirm("Are you Sure to Add reason to lookup ?");
		if(flag)
		{
			
			
		window.location.href="Lookup.jsp?action=subType&key=LPURP-LEAVE PURPOSE";
	}
	}
</script>




</head>

<% 
 int UID=Integer.parseInt(String.valueOf(session.getAttribute("UID")));
 int emproleid=0;
 int SYSrole=0;
%>
 
 <% 
	try{      
		 
		 Connection con = null;
		 con = ConnectionManager.getConnection();
		 Statement st = con.createStatement();
		 ResultSet rs=null;
		
		 System.out.println("listttttttttt111111111....UID.."+UID);
		
		 ResultSet roleset = st.executeQuery("select ROLEID from ROLES where ROLENAME LIKE 'branch users' ");
		 
		 if(roleset.next())
		 {
			 SYSrole = roleset.getInt("ROLEID");
			 System.out.println("sysroleid..."+SYSrole);
		 }
		 
		 
		 ResultSet rs1 = st.executeQuery("select ROLEID from USERROLES where USERID="+UID);
		 if(rs1.next())
		 {
			 emproleid = rs1.getInt("ROLEID");
			 System.out.println("emproleid..."+emproleid);
		 }
	}
 catch(Exception e)
 {
	 e.printStackTrace(); 
 }
 %>
<%
LookupHandler lookupHandler= new LookupHandler();
ArrayList<LMB> leaveBalList = new ArrayList<LMB>();
ArrayList<LMB> leaveList = new ArrayList<LMB>();
LMH leaveMasterHandler = new LMH();
SimpleDateFormat dateFormat;

EmployeeHandler emph = new EmployeeHandler();
EmployeeBean ebean = new EmployeeBean();
EmpOffHandler eoh=new EmpOffHandler();
String Salary_Month="";
int empNo = request.getParameter("empno")==null?Integer.parseInt(session.getAttribute("EMPNO").toString()):Integer.parseInt(request.getParameter("empno"));

System.out.println("emonoi123   "+empNo);

Salary_Month=EmployeeHandler.getSalaryMonth(empNo);

System.out.println("emonoi   "+empNo);

RoleDAO obj1=new RoleDAO();
String roleId=obj1.getrole(Integer.parseInt(session.getAttribute("UID").toString()));


EmpOffBean eob= eoh.getEmpOfficAddInfo(Integer.toString(empNo));
int prjsrno=eob.getPrj_srno();
ebean = emph.getEmployeeInformation(Integer.toString(empNo));

leaveBalList = leaveMasterHandler.getList(empNo);
String searchList = request.getParameter("sList")==null?"ALL":request.getParameter("sList");
String action = request.getParameter("action")==null?"":request.getParameter("action");




if(searchList.equalsIgnoreCase("PENDING")){
	
	leaveList = leaveMasterHandler.leaveDisplay(empNo,"PENDING");
}else if(searchList.equalsIgnoreCase("SANCTION")){
	leaveList = leaveMasterHandler.leaveDisplay(empNo,"SANCTION");
}else if(searchList.equalsIgnoreCase("CANCEL")){
	leaveList = leaveMasterHandler.leaveDisplay(empNo,"CANCEL");
}else{
	leaveList = leaveMasterHandler.leaveDisplay(empNo,"ALL");
}


LookupHandler lh=new  LookupHandler();
String empNumber="";
String str_disp="";
if(request.getParameter("empno")!=null){
	empNumber=request.getParameter("empno");
	 ebean=emph.getEmployeeInformation(empNumber);
	 str_disp=lh.getLKP_Desc("SALUTE", ebean.getSALUTE())+" "+ ebean.getFNAME()+" "+ebean.getMNAME()+" "+ebean.getLNAME();
	 
}


ArrayList<LMB> leaveApp =new ArrayList<LMB>();
int empno=0;
int appNo=0;
String flag="100";
flag=request.getParameter("flag")==null?"100":request.getParameter("flag"); 
%>


<script  type="text/javascript">

function redirect_url(type )
{  
	if(type=="PENDING"){
		window.location = "leave.jsp?sList="+type+"&empno="+<%=empNo%>;
	}else if(type=="SANCTION"){
		window.location = "leave.jsp?sList="+type+"&empno="+<%=empNo%>;
	}else if(type=="CANCEL"){
		window.location = "leave.jsp?sList="+type+"&empno="+<%=empNo%>;
	}else 
		window.location = "leave.jsp?sList="+type+"&empno="+<%=empNo%>;

}
</script>

<body onLoad= " checkLoan()" >

<%@include file="mainHeader.jsp"%>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp"%>
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer"  style="overflow-y:scroll; max-height:80%; ">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1><strong style="font-size: 20px">Leave Master </strong></h1>
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
			<div id="table-content" align="center" style="overflow-y:auto;max-height:520px; ">
		<table id='customers_one'>
		<tr class="alt">
		<td><strong style="font-size: 20px;"> Enter Employee :</strong> </td>
		<td>

		 <input size='75' type="text" id="empno" name="empno" placeholder="Enter Employee Name" style="height: 30px;font-size:16px">
   <input type="button"  value="SUBMIT" onclick="getEmployee()" class="myButton">

		<%--  <input size='40' type="text" id="emp_no" name="emp_no"
		 value="<%=request.getParameter("empno")==null?"":str_disp%>">
		
   <input type="button"  value="SUBMIT" onclick="getEmployee()">  --%>

		</td>
		</table>	
		
	<br>
  <table id="customers_one" width="529" style="float: none;">
  	<tr><th colspan="6"><font color="white" size="5">LEAVE DETAILS</font></th></tr>
    <tr>
    		
    
      <td><font size="4">Emp ID </font></td>
      <td><font size="4">
      <%=ebean.getEMPCODE()==null?"":ebean.getEMPCODE()%></font></td>
      <td><font size="4">Employee Name </font></td>
      <td colspan="2"> <font size="4">
     <%=(ebean.getFNAME()==null)?"":lh.getLKP_Desc("SALUTE", ebean.getSALUTE())+" "+ ebean.getFNAME()+" "+ebean.getMNAME()+" "+ebean.getLNAME()%></font></td>
    </tr>
    <tr>
      <th width="80" scope="col" ><font size="4"> Balance Date </font></th>
      <th scope="col"><font size="4">Leave Type </font></th>
      <th scope="col"><font size="4">Leaves Credited </font></th>
      <th scope="col"><font size="4">Leaves Taken </font></th>
      <th scope="col"><font size="4">Leaves Cancel </font></th>
      <th scope="col"><font size="4">Leave Balance </font></th>
      
    </tr>
     <% float bal = 0.0f;
     	float cancel_lev_bal = 0.0f;
    	if(leaveBalList.size()!=0){
    	 
    		for(LMB leaveBean : leaveBalList){
    			/* if(leaveBean.getLEAVECD() != 5){
    				bal =bal + leaveBean.getTOTCR();
    				leaveBean.getTOTCR()- leaveBean.getBAL();
    			} */	
    			cancel_lev_bal =leaveBean.getTOTCR()- leaveBean.getBAL();
    			%>
    <tr align="center">
	      <td><font size="3"><%=leaveBean.getBALDT() %></font></td>
	      <td><font size="3"><%=lh.getLKP_Desc("LEAVE",leaveBean.getLEAVECD()) %></font></td>
	      <td><font size="3"><%=leaveBean.getTOTCR() %></font></td>
	      <td><font size="3"><%=leaveBean.getTOTDR() %></font></td>
	      <td><font size="3"><%=leaveBean.getCANCELLEAVE()%></font></td>
	      <td><font size="3"><%=leaveBean.getBAL() %></font></td>
	      
	       <input type="hidden" id="empno01" name="empno01" value="<%=ebean.getEMPNO() %>">
    </tr>  	   
    <% }
	   if(LMH.displayEmpLeaves(empNo) - bal > 0){ %>
	<tr class="row">
		<%-- <td colspan="6" height="18"><font size="2"><div id="leave"><%=LMH.displayEmpLeaves(empNo) - bal %> leaves Remaining</div></font></td> --%>
	</tr>

   <%		}
	   }
       else {    %>
	  	<tr><td colspan="6" height="20"></td></tr>
    <% } %>
  </table>
<br/>

<%
		if(action.equalsIgnoreCase("editLeaveApp")){
			
			 empno = Integer.parseInt(request.getParameter("empno"));
			 appNo = Integer.parseInt(request.getParameter("appno"));
			
			 leaveApp = leaveMasterHandler.getLeaveApp(empno, appNo);
			
			for(LMB leaveBean : leaveApp){
				
		%>
	<div align="center">
			<%-- <h3 style="color: white;"><%=error %></h3> --%>
	<form  id="form" name="leaveForm" action="LMS?action=editleave" method="post" onSubmit="return validate()">
		 <input type="hidden" id="salaryMonth" name="salaryMonth" value="<%=Salary_Month %>">
		<input type="hidden" name='empno' value='<%=empno%>'>
		<input type="hidden" name="prj_srno" id="prj_srno" value="<%=prjsrno%>">
		<%-- <%System.out.println("role id he"+roleId) ;%> --%>
		<input type="hidden" name="role_id" id="role_id" value="<%=roleId%>">
		
	<table id="customers_one"  width="500" border="2" BORDERCOLOR=red>
			<tr>
				<th align="center" colspan="4" height="10">
					<font color="white" size="5">EDIT LEAVE</font>
				</th>
			</tr>
		    <tr>
		      <td>&nbsp;<font size="4">Date</font></td>
		      <td><input name="tdate" id="tdate" type="text"  readonly="readonly" value="<%=leaveBean.getTRNDATE() %>" size="25">&nbsp;<img
							src="images/cal.gif" align="middle"
							style="vertical-align: middle; cursor: pointer;"
							onClick="javascript:NewCssCal('tdate', 'ddmmmyyyy')" /></td>
		    
		      <td>&nbsp;<font size="4">Leave Type</font> <font color="red" size="3">*</font></td>
		      <td>
		      	<select name="leavecode" id="leavecode" style="width: 128px" onchange="matLeave(1)">
						<option selected="selected" value="Default">Select</option>
							<%
							int srno = leaveBean.getLEAVECD();
							String selected = "selected";
							ArrayList<Lookup> result=new ArrayList<Lookup>();
									 
							result=lookupHandler.getSubLKP_DESC("LEAVE");
							for(Lookup lkbean : result)
							{
								if(srno == lkbean.getLKP_SRNO() && lkbean.getLKP_SRNO()!=5 && lkbean.getLKP_SRNO()!=6)
								{ %>
									<option value="<%=lkbean.getLKP_SRNO()%>" title="<%=leaveMasterHandler.getLeaveBalOfEmployeeCodeWise(empNo,lkbean.getLKP_SRNO())%>" <%=selected%>><%=lkbean.getLKP_DESC()%></option>  
									<%								 
									}else {
									%>
				 							<option value="<%=lkbean.getLKP_SRNO()%>" title="<%=leaveMasterHandler.getLeaveBalOfEmployeeCodeWise(empNo,lkbean.getLKP_SRNO())%>" ><%=lkbean.getLKP_DESC()%></option>  
									 	<%}
							}
									 	%>
				</select>
			  </td>
		    </tr>
		    <tr>
		    	<td><font size="4">From Date </font><font color="red" size="3">*</font></td>
		     	<td><input name="frmdate" id="frmdate" type="text" value="<%=leaveBean.getFRMDT() %>" readonly="readonly" size="25" onchange="setDays(); matLeave(1);">&nbsp;<img
							src="images/cal.gif" align="middle"
							style="vertical-align: middle; cursor: pointer;"
							onClick="javascript:NewCssCal('frmdate', 'ddmmmyyyy')" /></td>
		   
		      	<td><font size="4">To Date </font><font color="red" size="3">*</font></td>
		     	<td><input name="todate" id="todate" type="text" value="<%=leaveBean.getTODT() %>" readonly="readonly" size="25" onchange="setDays(); matLeave(1);">&nbsp;<img
							src="images/cal.gif" align="middle"
							style="vertical-align: middle; cursor: pointer;"
							onClick="javascript:NewCssCal('todate', 'ddmmmyyyy')" />
				</td>
		   </tr>                                                                                  
		    <tr>
		      	<td><font size="4">Leave Purpose</font> <font color="red" size="3">*</font> </td>
		      	<td colspan="3"><input type="text" id="lreason"  name="lreason" value="<%=lookupHandler.getLKP_Desc("LPURP",leaveBean.getLEAVEPURP()).equals("LEAVE PURPOSE")?"":
		lookupHandler.getLKP_Desc("LPURP",leaveBean.getLEAVEPURP())%>" size="100" placeholder="Enter Leave Reason"/></td>
		   </tr>
		    <%-- <tr>
		      	<td>Address </td>
		      	<td colspan="3">
		      		<input type="text" name="addDuringleave" value="<%=leaveBean.getLADDR() %>" size="60"/>
		      	</td>
		    </tr> --%>
		    <tr>
		      	<td><font size="4">Mob No.</font></td>
		      	<td><input type="text" name="telephone" value="<%=leaveBean.getLTELNO()%>"  onkeypress="return inputLimiter(event,'Numbers')"/>		      
		      	<td align="right"><font size="4">Half Day ?</font></td>
		      	<% if(leaveBean.getNODAYS() == 0.5f){ %>
	 	  		    <td>&nbsp;<font size="4">Yes</font>&nbsp;
	 	  		    <input type="radio" name="halfday" id="halfday" value="yes" onchange="onSelection()" checked />&nbsp;No&nbsp;
	 	  		    <input type="radio" name="halfday" value="no"  onchange="onSelection()"/></td>

	 	  	  	<%}else{ %>
	 	  	  		<td>&nbsp;<font size="4">Yes</font>&nbsp;
	 	  	  			<input type="radio" name="halfday" id="halfday" value="yes"  onchange="onSelection()"/>&nbsp;<font size="4">No</font>&nbsp;
	 	  	  			<input type="radio" name="halfday" value="no"  onchange="onSelection()" checked/></td>

	 	  	  	<%} %>
	 	  		
		    </tr>

		   <tr>
		   	<td><font size="4">Total Days</font></td>
	 	  	<td colspan="3">
	 	  		 <input type="text" name="days" id="days" value="<%=leaveBean.getNODAYS()%>" readonly="readonly"/>
	 	  	</td>
	 	  </tr>
	 
		   <tr>
		    <td height="31" colspan="4" align="center">
		    	<!-- <input type="submit" style="margin-right: 50px"  value="Save" class="myButton" />&nbsp;&nbsp;
		    	<input type="reset" value="Cancel" onclick="window.location.href='leave.jsp'" class="myButton"/>
	    	 <input type="button" style="margin-left: 50px" value="ADD REASON " title="ADD REASON TO LOOKUP" class="myButton" onclick="toLookup()"/> -->
	    	 
	    <!-- 	 <input type="button" style="margin-left: 5px" value="ADD REASON " title="ADD REASON TO LOOKUP" class="myButton" onclick="toLookup()"/> -->
	    	 <input type="submit" style="margin-right: 50px"  value="Save" class="myButton" />&nbsp;&nbsp;
	    	<input type="reset" value="Cancel" onclick="window.location.href='leave.jsp'" class="myButton"  /> 
	    	  
		    	<input type="hidden" name="appNo" id="appNo" value="<%=leaveBean.getAPPLNO()%>">
		    </td>
		  </tr>
	</table>
   
   </form>

		</div>
		<br/>
		<% 		
		}
		  
		}
		else{ 
	     if(empNo!=0){  %>
	   	<div align="center">
		<%-- <h3 style="color: red;"><%=error %></h3> --%>
			<form  id="form1" name="leaveForm1" action="LMS?action=addleave" method="post" onSubmit="return validate();">
			 <input type="hidden" id="salaryMonth" name="salaryMonth" value="<%=Salary_Month %>">
			 <input type="hidden" id="retirementDate" name="retirementDate" value="<%=ebean.getRETIREMENT_EXT_PERIOD()%>">
		
			<table id="customers_one" width="529"  style="float: none;">
	           <%
	   				 dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
					 Date date = new Date();
					 String currentdate = dateFormat.format(date);  
					%>
					<input type="hidden" name='empno' value='<%=empNo%>'>
					<input type="hidden" name="prj_srno" id="prj_srno" value="<%=prjsrno%>">
					<input type="hidden" name="role_id" id="role_id" value="<%=roleId%>">
		<tr><th align="center" colspan="6" height="16"><h2><font color="white" size="5">APPLY LEAVE</font></h2></th></tr>
	     <tr>
	      <td align="right"><font size="4">Application Date</font></td>
	       <td><input name="tdate" id="tdate" type="text"  readonly="readonly" value="<%=currentdate %>" size="25" class="txtheight" onchange="chkappdt()">&nbsp;<img 
						src="images/cal.gif" align="middle"
						style="vertical-align: middle; cursor: pointer;"
						onClick="javascript:NewCssCal('tdate', 'ddmmmyyyy');"/></td>
	  <td align="right"><font size="4">From Date</font> <font color="red" size="5">*</font></td>
	      <td><input name="frmdate" id="frmdate" type="text" readonly="readonly" size="25" onchange=" chkaFromToDate();setDays(); matLeave(2); chkappdt(); "  class="txtheight">&nbsp;<img 
						src="images/cal.gif" align="middle"
						style="vertical-align: middle; cursor: pointer;"
						onClick="javascript:NewCssCal('frmdate', 'ddmmmyyyy')" /></td>
	     
	  
	     </tr>
	     <tr>
	     
	        <td align="right"><font size="4">Leave Type </font><font color="red" size="5">*</font></td>
	        <td ><select name="leavecode" id="leavecode" style="width: 179px" class="txtheight"  onchange="matLeave(2)">
						<option selected="selected" value="Default">Select</option>
						<%
							  ArrayList<Lookup> result3=new ArrayList<Lookup>();
								  LookupHandler lkhp3= new LookupHandler();
							      result3=lkhp3.getSubLKP_DESC("LEAVE");
									
						for(Lookup lkbean : result3)
						{
				//			if ( (lkbean.getLKP_SRNO()<11)&&(lkbean.getLKP_SRNO()!=5) && ((leaveMasterHandler.getLeaveBalOfEmployeeCodeWise(empNo,lkbean.getLKP_SRNO())>0)|| (lkbean.getLKP_SRNO()==7) || (lkbean.getLKP_SRNO()==10) ) ) {					
							
				//	if ((lkbean.getLKP_SRNO()<11) && (lkbean.getLKP_SRNO()!=5) && ((leaveMasterHandler.getLeaveBalOfEmployeeCodeWise(empNo,lkbean.getLKP_SRNO())>0)|| (lkbean.getLKP_SRNO()==7)) ) {
					
					//if ((lkbean.getLKP_SRNO()<11) && (lkbean.getLKP_SRNO()!=5) ) {
						if ((lkbean.getLKP_SRNO()<20) && (lkbean.getLKP_SRNO()!=5) ) {
								lkbean.setLKP_DESC(lkhp3.getLKP_Desc("LEAVE",lkbean.getLKP_SRNO()));
						%>
			 							<option value="<%=lh.getLKP_Desc("LEAVE",lkbean.getLKP_SRNO())%>" title="<%=leaveMasterHandler.getLeaveBalOfEmployeeCodeWise(empNo,lkbean.getLKP_SRNO())%>"><%=lkbean.getLKP_DESC()%></option>  
								 	<%}
								
								else if((lkbean.getLKP_SRNO()<20)&&(lkbean.getLKP_SRNO()!=5)) {%>
			 							<option value="<%=lh.getLKP_Desc("LEAVE",lkbean.getLKP_SRNO())%>" title="<%=leaveMasterHandler.getLeaveBalOfEmployeeCodeWise(empNo,lkbean.getLKP_SRNO())%>" hidden><%=lkbean.getLKP_DESC()%></option>  
						
						 <%} }%>	
					</select>
		  </td>
	      <td align="right"><font size="4">To Date </font><font color="red" size="5">*</font></td>
	     <td><input name="todate" id="todate" type="text" readonly="readonly" size="25" onchange=" chkaFromToDate(); setDays(); matLeave(2);chkappdt(); " class="txtheight">&nbsp;<img
						src="images/cal.gif" align="middle"
						style="vertical-align: middle; cursor: pointer;"
						onClick="javascript:NewCssCal('todate', 'ddmmmyyyy')" /></td>
	    </tr>
	     <tr>
	      <td align="right"><font size="4">Leave Purpose </font><font color="red" size="5">*</font></td>
	      <td colspan="3">
	      <input type="text"  name="lreason" id="lreason"  size="100" placeholder="Enter Leave Reason" class="txtheight" >
	    </tr>
	    <tr> 
	     <!--  <td align="right">Address </td>
	      <td colspan="3"><input type="text" name="addDuringleave" size="60"/></td>
	    </tr> -->
	    <tr>
	      <td align="right"><font size="4">Mob No.</font> </td>
	      <td><input type="text" name="telephone" id="telephone"  onkeypress="return inputLimiter(event,'Numbers')"  class="txtheight" size="25" /></td>
	 	  <td align="right"><font size="4">Half Day ?</font></td>
	 	  <td>&nbsp;<font size="4">Yes</font>&nbsp;<input type="radio" name="halfday" id="halfday" value="yes"  onchange="onSelection()"/>
	 	  		&nbsp;<font size="4">No</font>&nbsp;<input type="radio" name="halfday" value="no"  onchange="onSelection()" checked/></td>
		</tr>

		<tr><td align="right"><font size="4">Total Days</font></td>
	 	  <td><input type="text" name="days" id="days" readonly="readonly" class="txtheight" size="25"/> </td>
	 	  </tr>
	 	  
	 	  <tr><td align="right"><font size="4">Leave Reason</font></td>
		 <td colspan="3">
		 	<textarea name="reasonDetails" id="reasonDetails"  cols="20" rows="2" size="100"  class="txtheight"  style="width: 712px; height: 49px; font-size: large; " maxlength="199" ></textarea> 
		 </td>
	 </tr> 
	
		  <!-- <tr><td align="right">Total Days</td>
	 	  <td><input type="text" name="days" id="days" readonly="readonly" /> </td>
	 	  </tr> -->
	    <tr >
	    <td height="31" colspan="4" align="center">
	    	
	    	 <input type="button" style="margin-left: 5px" value="ADD REASON " title="ADD REASON TO LOOKUP" class="myButton" onclick="toLookup()"/>
	 <input type="submit" style="margin-left: 490px" value="Apply" class="myButton"  />&nbsp;&nbsp;
	    	<input type="reset" value="Cancel" class="myButton"  /> 
	    	</td></tr>
	  </table>
	  
<%-- 	  
	<% if(leaveBalList.size()!=0){
    	 
    		
    			/* if(leaveBean.getLEAVECD() != 5){
    				bal =bal + leaveBean.getTOTCR();
    			} */	%>
    
    <input type="hidden" id="<%=lh.getLKP_Desc("LEAVE",leaveBean.getLEAVECD())%>" value="<%=leaveBean.getBAL() %>">
    
	      
    <% }
	
	   }%> --%>
	  
	  </form>
	  
		</div>
		<br/>


<%}}
String status="ALL";
status=request.getParameter("sList")==null?"ALL":request.getParameter("sList");

%>



<div align="center">
<%if(emproleid != SYSrole){ %>
<h2><strong style="font-size: 20px">Leave Transactions</strong></h2>
<div>
<table id=customers_three  border="1" style="float: none;">
<tr><th><strong style="font-size: 18px;">Select List</strong></th><td><select name="type"  onchange="redirect_url(this.value)" style="width:100% ">

<option value="SELECT" >SELECT</option>
<option value="ALL" >ALL</option>
<option value="PENDING" >PENDING</option>
<option value="SANCTION" >SANCTION</option>
<option value="CANCEL" >CANCELED</option></select></td></tr>
</table>

</div>
<table  id="customers" width="529" border="1" style="float: none;">

    <tr class="alt">
      <th scope="col"><font size="4">App Date </font></th>
      <th scope="col"><font size="4">Leave Type </font></th>
      <th scope="col"><font size="4">From Date </font></th>
      <th scope="col"><font size="4">To Date </font></th>
      <th scope="col"><font size="4">Total Days </font></th>
      <th scope="col"><font size="4">Leave Reason </font></th>
      <th scope="col"><font size="4">Leave Purpose </font></th>
      <th scope="col"><font size="4">Tran Type </font></th>
      <th scope="col"><font size="4">Status </font></th>
      <th scope="col" width="50"><font size="3">Edit </font></th>
    </tr>
    <%
    if(leaveList.size()!=0){
    	for(LMB leaveBean : leaveList){%>
    <tr align="center">
    	<td><font size="3"><%=leaveBean.getTRNDATE() %></font></td>
    	<%
			if(leaveBean.getLREASON().equalsIgnoreCase("Late Leave Will be Deducted as CL,PL,LWP")){%>
																
			<td><font size="3"><%="Late_L" %></font></td>
			<%
			}else{
			%>
			<td><font size="3"><%=lookupHandler.getLKP_Desc("LEAVE",leaveBean.getLEAVECD())%></font></td>
			<%}  %>
	<%-- 	<td><font size="3"><%=lookupHandler.getLKP_Desc("LEAVE",leaveBean.getLEAVECD())%></font></td> --%>
			
    	
    	
    <%-- 	<td><font size="3"><%=lookupHandler.getLKP_Desc("LEAVE",leaveBean.getLEAVECD())%></font></td> --%>
    	<td><font size="3"><%=leaveBean.getFRMDT() %></font></td>
    	<td><font size="3"><%=leaveBean.getTODT() %></font></td>
    	<td><font size="3"><%=leaveBean.getNODAYS() %></font></td>
    	<td width="150"><font size="3"><%=leaveBean.getLREASON() %></font></td>
    	
    	<%
				if(leaveBean.getLREASON().equalsIgnoreCase("Late Leave Will be Deducted as CL,PL,LWP")){%>
																
				<td width="150"><font size="3"><%="CONSIDERED LATE LEAVE" %></font></td>
				<%
				}else{
				%>
				<td width="150"><font size="3"><%=lookupHandler.getLKP_Desc("LPURP",leaveBean.getLEAVEPURP()).equals("LEAVE PURPOSE")?"":
		lookupHandler.getLKP_Desc("LPURP",leaveBean.getLEAVEPURP())%></font></td>
				<%}  %>
    	
    	<% if(leaveBean.getTRNTYPE() == 'C') {%>
    	<td><font size="3">CREDIT</font></td>
    	<%}else { %>
    	<td><font size="3">DEBIT</font></td>
    	<%} %>
    	<td><font size="3"><%=leaveBean.getSTATUS() %></font></td>
    	<td>
    	<%if(leaveBean.getSTATUS().equalsIgnoreCase("PENDING")){
    		%>
    	
    	<a href="javascript:{}" onclick="editLeave('<%=leaveBean.getEMPNO()%>','<%=leaveBean.getAPPLNO()%>')">Edit</a>
    	<input type="hidden" name="appNo" value="<%=leaveBean.getAPPLNO()%>">
    	<input type="hidden" name="empno" value="<%=leaveBean.getEMPNO()%>">
           
        
        <%} %>
    	</td>
    </tr>
    <%}
    } else {%>
    <tr><td colspan="10" height="20"><font size="4">No Records Found</font></td></tr>
    <%} %>
  </table><%} %>
  <input type="hidden" name="flag" id="flag" value="<%=flag%>">
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
