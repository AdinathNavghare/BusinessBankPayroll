<%@page import="payroll.Model.Lookup"%>
<%@page import="payroll.Model.EmpOffBean"%>
<%@page import="payroll.DAO.RoleDAO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="payroll.DAO.EmpOffHandler"%>
<%@page import="payroll.DAO.LeaveMasterHandler"%>
<%@page import="payroll.DAO.EmployeeHandler"%>
<%@page import="payroll.Model.EmployeeBean"%>
<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="payroll.Model.LMB"%>
<%@page import="java.util.ArrayList"%>
<%@page import="payroll.DAO.LMH"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sanction Leave</title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<script type="text/javascript">

jQuery(function() {
	$("#EMPNO").autocomplete("list.jsp");
}); 
jQuery(function() {
    $("#lreason").autocomplete("selectLeavePurpose.jsp");
});

$(".datecls").click(function () {
//	alert("call me");
});

function NewCssCalnew(para1,type)
{
	if(type==1)
	{
NewCssCal(para1, 'ddmmmyyyy');
document.getElementById("calBorder").setAttribute("class", "calupper");
	}
	if(type==2)
		{
	NewCssCal(para1, 'ddmmmyyyy');
	document.getElementById("calBorder").setAttribute("class", "calower");
		}
	if(type==3)
	{
NewCssCal(para1, 'ddmmmyyyy');
document.getElementById("calBorder").setAttribute("class", "caldown");
	}
}

</script>
<style type="text/css">

.calupper
{
	top:135px !important;
}
.calower
{
	top:430px !important;
}
.caldown
{
	top:450px !important;
}
</style>

<script language="javascript">
//To show edit table div ......
function editLeave(empno,appno){
	//window.location.href = "sanctionLeave.jsp?action=editLeaveApp&empno="+empno+"&appno="+appno+"&hides=submithide";

	$("#myModal").empty();
	document.getElementById("myModal").style.display = 'Block';
	$("#myModal").load("sanctionLeave.jsp?action=editLeaveApp&empno="+empno+"&appno="+appno+"&hides=submithide");
	$("#myModal").fadeTo('slow', 0.9);
	$('.nav-outer').fadeTo("slow", 0.5).css('pointer-events', 'none');
}

function onSelection(){
	
	var fromDate=document.getElementById("frmdate1").value;
	var toDate=document.getElementById("todate1").value;
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

//For Applied Leave Between Date
function chk_bet_date()
{
		var empno = document.getElementById("empno01").value;
		var fromDate=document.getElementById("frmdate1").value;
	   	var toDate=document.getElementById("todate1").value;
	   	var appn=document.getElementById("appNo").value;
	   	
	try
	{
	
	var xmlhttp=new XMLHttpRequest();
 	var url="";
 	var response2="";
	
 	url="LMS?action=betDate&empno="+empno+"&frmdate="+fromDate+"&todate="+toDate+"&appNo="+appn;
	xmlhttp.onreadystatechange=function()
	{
		if(xmlhttp.readyState==4)
		{
		
		response2 = xmlhttp.responseText;
		 if(response2>0 )
			{
			 alert("You have already applied leave for this date...");
			
			 document.getElementById('leavecode1').value="";
			 fromDate=document.getElementById("frmdate1").value="";
			 toDate=document.getElementById("todate1").value="";
			 document.getElementById("todate1").placeholder="";
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


//For 5 Consecutive CL...
function consecutive_leave()
{
	var con_leave = document.getElementById("leavecode1").value;	
	if(con_leave=="3")
{
	var empno = document.getElementById("empno01").value;
	var fromDate=document.getElementById("frmdate1").value;
	   	var toDate=document.getElementById("todate1").value;
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
		if(TotDays > 4 && con_leave=="3" )
		 {
		 	alert("You Can Not Apply 5 Consecutive CL ");
		 	document.getElementById('leavecode1').value= "";
		 	return false;
		 }
		
		 }
		};
	xmlhttp.open("POST", url, true);
	xmlhttp.send();
}
}


function validate1(){
	 var mleave = document.getElementById("leavecode1").value;
	 if(document.getElementById("leavecode1").value == "Default" || document.getElementById("leavecode1").value == ""){
		alert("Please select Leave Type");
		return false;
	 }
	  if(mleave == "3")
		 {
	 	 	consecutive_leave();
		 }
	  

	 
	 if(document.getElementById("lreason").value =="" ){
			alert("Please Select Leave Reason");
			document.getElementById("lreason").focus();
			return false;
	 }
	  // var date = document.getElementById("tdate").value;
	   var fromDate=document.getElementById("frmdate1").value;
	   var todate1=document.getElementById("todate1").value;
	 
	   fromDate = fromDate.replace(/-/g,"/");
	   todate1 = todate1.replace(/-/g,"/");
	  // date = date.replace(/-/g,"/");
		
	 if(fromDate == "" || todate1 == ""){
			 alert("please select From Date and To Date.!");
			 return false;
	 }
	 	var d1 = new Date(fromDate);	 	
	 	var d2 =new  Date(todate1);
	//	var d3 =new  Date(date);
		
	
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
		   alert("Invalid Date Range!\n FromDate can't be greater than todate1!");
		   document.getElementById("todate1").focus();
		   return false;
	  }
	 
	 var datediff = d2.getTime() - d1.getTime();
	 var radios = document.getElementById('halfday').checked;
	 if(radios == true && datediff != 0 && !(isNaN(datediff))){
	 	alert("Date Must Be Same To Apply For Half Day Leave !");
	 	return false;
	 } 
	 
	 var mob =document.getElementById("telephone").value;
	 if(mob == "") {
		   
	 }
	 else if(mob.length != 10 ){
		 alert("Mobile No. must be of 10 digit.!");
		 document.getElementById("telephone").focus();
		   return false;
	 }
	 
	 var balance= document.getElementById("leavecode1");
	   
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

	    
	    var appliedLeaveType=document.getElementById("leavecode1").value;
	    var days=document.getElementById("days").value;
	    
	    if(appliedLeaveType=="PL"){
	 
	    	if(parseFloat(plBalance)==0){
	    		alert("PL balance is zero");
	    		return false;
	    	}
	    	
	    	 if((parseInt(days)<=4)  && (parseFloat(clBalance)>=parseFloat(days))){
	 	    	
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
	 	    	}
	    	/* if((parseInt(days)<=4)  && (parseFloat(clBalance)>=parseFloat(days))){
	    		
	    		alert("You can not apply PL for "+days+" days if you have CL balance");
	    		return false; 
	    	
	    	}	 */	   
	    if(parseFloat(days)>parseFloat(plBalance)){
   		alert("You do not have enough PL balance to apply for "+days+" leaves");
   		return false;
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
	    
	    if(appliedLeaveType=="SL"){
	    	if(parseFloat(slBalance)==0){
	    		alert("SL balance is zero");
	    		return false;
	    	}
	    	if(parseInt(days)<4){
	    		alert("SL must be for minimum of 4 days");
	    		return false;
	    	}
	    	if(parseFloat(days)>parseFloat(slBalance)){
	    		alert("You do not have enough SL balance to apply for "+days+" leaves");
	    		return false;
	    	}
	    }
	    
	    if(appliedLeaveType=="MAT_L"){
	    	if(parseInt(days)>180){
	    		alert("Maternity leaves can be taken for maximum 180 days.");
	    		return false;
	    	}
	    }
	  
	if(document.getElementById("leavecode1").value == "2"){

		 var days=document.getElementById("days").value;
		 if(parseFloat(days)<4)
			 {
			 alert("SL can't be applied for less than three days");
			 document.getElementById("days").focus();
			return false;		
			 } 
		} 
	    
	   
	    
  var check=confirm("Are you sure to Add a Leave?");
  if(!check){
	   return false
  }
	 //return false;
}




// For Maternity Leave
function matLeave()
{
	 var mleave = document.getElementById("leavecode1").value;
	 var empno = document.getElementById("empno01").value;
	 var fromDate=document.getElementById("frmdate1").value;
	 var toDate=document.getElementById("todate1").value;
	 
	var xmlhttp=new XMLHttpRequest();
 	var url="";
 	var response1="";
 	url="LMS?action=matLeave&empno="+empno;
	
	xmlhttp.onreadystatechange=function()
	{
		if(xmlhttp.readyState==4)
		{
		response1 = xmlhttp.responseText;
		 if(response1>0 && mleave == "8")
			{
				alert("You Can not Apply Maternity Leave For Male...!");
				document.getElementById('leavecode1').value="";
			
				return false;
			} 
		  else
			 {
			  if(fromDate!="" && toDate!=""){
				 	 chk_bet_date();
				 }
				
			 	 consecutive_leave();
			 } 
		 }
	};
	xmlhttp.open("POST", url, true);
	xmlhttp.send(); 
	
	 if(mleave == "6")
	{
		document.getElementById('lreason').value="Late Leave Will Deducted As CL,PL,LWP";
	//	document.getElementById('lreason').disabled = true;
	}
	else
		{
			document.getElementById('lreason').disabled = false;
			document.getElementById('lreason').value="";
		} 
	/*  if(mleave == "3")
		 {
	 	 	consecutive_leave();
		 } */
	
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


function setDays(){
// 	alert("vfghfvbjhbfvjhfbvhjb");
 	var mleave = document.getElementById("leavecode1").value;
	var fromDate=document.getElementById("frmdate1").value;
	var toDate=document.getElementById("todate1").value;
	//alert(fromDate+"   "+toDate);
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
				document.getElementById("frmdate1").value="";
				document.getElementById("todate1").value="";
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
				 parseMyDate(fromDate);
				  fromDate=parseMyDate(fromDate);
				  toDate=parseMyDate(toDate);
				//  document.getElementById("days").value=(toDate.getTime()-fromDate.getTime())/(24*60*60*1000)+1;
				var day =  Math.round((toDate.getTime()-fromDate.getTime())/(24*60*60*1000)+1); 
				document.getElementById("days").value= day;
		} 
		 //  var date = new Date();	 
		   var fromDate=document.getElementById("frmdate1").value;
		   var toDate=document.getElementById("todate1").value;
		   fromDate = fromDate.replace(/-/g,"/");
			toDate = toDate.replace(/-/g,"/");
		
			var d1 = new Date(fromDate);	 	
		 	var d2 =new  Date(toDate);
		 	
		 if (d1.getTime() > d2.getTime())
		 {
			   alert("\t\t Invalid Date Range!\n\n FromDate can't be greater than TODate!");
			   document.getElementById("todate1").focus();
			   document.getElementById("days").value="";
			   document.getElementById("todate1").value="";
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
		 
		 if(mleave == "3")
		 {
	 	 	consecutive_leave();
		 }
		 
		/*  var mob = document.leaveForm.telephone.value;
		 if(mob == "" || mob == 0) {
			    return 	true;

			}
			return true; */
		}
}



	function validate() {

		var fromDate = document.searchappl.frmdate.value;
		var toDate = document.searchappl.todate.value;
		fromDate = fromDate.replace(/-/g, "/");
		toDate = toDate.replace(/-/g, "/");
		var str1=document.searchappl.EMPNO.value;
		
		var num1 =str1.split(":");
		var num2 =str2.split(":");
		var sno1="";
		var sno2="";
		for (var i = 0; i < num1.length; i++)
		{
			sno1=num1[i];
		}
		for (var j = 0; j < num2.length; j++)
		{
			sno2=num2[j];
		}
		if (document.searchappl.frmdate.value == "") {
			alert("please select From Date");
			document.searchappl.frmdate.focus();
			return false;
		}
		if (document.searchappl.todate.value == "") {
			alert("please enter the To Date");
			document.searchappl.todate.focus();
			return false;
		}
		var d1 = new Date(fromDate);
	    var d2 = new Date(toDate);

		if (d1.getTime() > d2.getTime()) {
			alert("                       Invalid Date Range!\n\n From Date can't be greater than TO Date!");
			document.searchappl.todate.focus();
			return false;
		}
		var EMPNO = document.getElementById("EMPNO").value;
        if (document.getElementById("EMPNO").value == "") {
			alert("Please Enter Employee Name");
			document.getElementById("EMPNO").focus();
			return false;
		}
		var atpos=EMPNO.indexOf(":");
		if (atpos<1)
		  {
		  alert("Please Select Correct Employee Name");
		  return false;
		  }

		if (document.searchappl.type.value == "Default") {
			alert("Please select Leave Detail.!");
			document.searchappl.type.focus();
			return false;
		}
	}

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
		
		function checkLeaveStatus(action,empno,appno,fname,lname)
		{
			alert("i am in Sanction checkLeaveStatus");
			var xmlhttp=new XMLHttpRequest();
		 	var url="";
		 	var response1="";
		 	url="LMS?action=checkLeaveStatus&appno="+appno+"&empno="+empno;
		 	try
		 	{
			xmlhttp.onreadystatechange=function()
			{
				if(xmlhttp.readyState==4)
				{
				response1 = xmlhttp.responseText;
				alert("check leave status :::"+response1)
				if(response1=="EXIST")
				{
					alert("sanction or cancel leave before this leave")
					return false;
				}else{
					Sanction(action,empno,appno,fname,lname);
				}
				return false;
				}
			};
			xmlhttp.open("GET", url, true);
			xmlhttp.send(); 
			}
		 	catch(e)
		 	{
		 		alert(e);
		 	}  
		}
		
		function Sanction(action,empno,appno,fname,lname)
		{
		  alert("i am in Sanction");
			var xmlhttp=new XMLHttpRequest();
		 	var url="";
		 	var response1="";
		 	url="LMS?action=editsanctionLeave&todate="+empno+"&date="+appno;
		 	try
		 	{
			xmlhttp.onreadystatechange=function()
			{
				if(xmlhttp.readyState==4)
				{
				response1 = xmlhttp.responseText;
				
				   if(response1>0)
					{
						alert("Please Santion Or Cancel Pending Leave First ...!");
					return false;
				} 
				   
				}
			};
			xmlhttp.open("POST", url, true);
			xmlhttp.send(); 
			}
		 	catch(e)
		 	{
		 		alert(e);
		 	}   
	 
		 	
			var flag = confirm("Are you Sure to Sanction leave of "+fname+" "+lname+"?");
			if(flag)
			{
				
				window.location.href="LMS?action="+action+"&empno="+empno+"&appNo="+appno;
				
				
			}
		}
		
		function Cancel(action,empno,appno,fname,lname)
			{
				var flag = confirm("Are you Sure to Cancel leave of "+fname+" "+lname+" ?");
				if(flag)
				{
					url="LMS?action="+action+"&empno="+empno+"&appNo="+appno;
					
					xmlhttp.onreadystatechange=function()
					{
						if(xmlhttp.readyState==4)
						{
							var response = xmlhttp.responseText;
							if(response=="true")
							{
								alert("Leave Cancelled Successfully.");
								//window.location.href="sanctionLeave.jsp";
								window.location.href="sanctionpopup.jsp";
							}
							else
							{
								alert("failed, try Again!");
							}
						}
					};
					xmlhttp.open("POST", url, true);
					xmlhttp.send();
				}
			}
		function checkLoan(){
			var x = document.getElementById("search-emp");
			var y = document.getElementById("search-emp-h");
			var f1 =document.getElementById("submithide").value;
			if(f1=="submithide")
			{
		        x.style.display = 'none';
		        y.style.display = 'none';
			}
			  $(function() {
        		  window.history.forward();
        	     return false;
        	    });
			
			var f =document.getElementById("flag").value;
		
				if (f=="1") {
				alert("Leave Sanctioned  Successfully");
					}
				else if (f =="2") {
							alert("CLs Sanctioned but are converted to PL since there were minimum 5 consecutive CLs");			
					}
				else if(f=="3"){
					alert("Leave Rejected Successfully");
				}
				else if(f=="4"){
					alert("Some problem in leave rejection");
				}
					else if(f=="8"){
						alert("You have already applied leave for this date");
				}
				else if ((f!="1") && (f!="2") && (f=="3") &&(f=="4") && (f!= "100")(f!= "8")){
							//alert(f);
							alert("Not enough balance");
						} 
				
				}
		function ticked(applno,empno,leavecd,days) {
			//alert("hi");
			

			var string="Cancel_"+applno;
		//	$(string).attr('value','Disable');
		//alert(string);
		if(document.getElementById(string).disabled ==true){
		document.getElementById(string).disabled = false;
		document.getElementById(string).className = "myButton";
		}
		else {
		document.getElementById(string).disabled = true;
		document.getElementById(string).className = "";
		}


			//alert(empno+"   "+leavecd+"  "+days);
		}
		
		function cancelLeave(applno,fname,lname,leavecd){
			//alert("Cancel_"+applno);
			var count = $("[type='checkbox']:checked").length;
			if(count>1){
			alert("You have selected more than one records for cancellation");
			$("[type='checkbox']:checked").attr("checked", false);
			$("[type='button']").attr("disabled", true);
			$("[type='button']").attr('class',"");
			return false;
			}
			var a=confirm("Are you Sure to Cancel SANCTIONED "+leavecd+" of "+fname+" "+lname+" ?");
			if(a){
				var check = prompt("Are you really sure to cancel the leave? Then type Yes ");	
				if(check == "yes" || check == "Yes" || check == "YES"){
					window.location.href="LMS?action=cancelLeave&applno="+applno;
				} else {
					alert("Sorry ! Wrong Input ");
					return false;
				}
				
			}
		}

		function getname()
		{
		
			var empNo = document.getElementById("EMPNO").value;
			var res=empNo.split(":");
			var fromdate = document.getElementById("frmdate").value;
			var todate = document.getElementById("todate").value;
			
			fromdate = fromdate.replace(/-/g, "/");
			todate = todate.replace(/-/g, "/");
			if(empNo=="")
				{
					alert("Please Enter Employee...!");
					document.getElementById("EMPNO").focus();
					document.getElementById("EMPNO").focus();
					return false;
				}
			
			
			 if(fromdate =="")
				{
				alert("Please Select From Date...!");
				document.getElementById("frmdate").focus();
				return false;
				}
			 if(todate=="")
				{
				alert("Please Select To Date...!");
				document.getElementById("todate").focus();
				return false;
				}
			 
			 var d1 = new Date(fromdate);
			    var d2 = new Date(todate);

				if (d1.getTime() > d2.getTime()) {
					alert(" Invalid Date Range!\n\n From Date can't be greater than TO Date!");
					document.getElementById("todate").focus().value=="";
					document.getElementById("todate").focus();
					return false;
				}
				
		}
		
		function closediv() {
			//alert("closing");
			
			document.getElementById("myModal").style.display = "none";
			$('.nav-outer').fadeTo("slow", 1).css('pointer-events', 'auto');
			window.location.href="index.jsp";
		}		

</script>
</head>

<%
/////

ArrayList<LMB> leaveBalList = new ArrayList<LMB>();
ArrayList<LMB> leaveList = new ArrayList<LMB>();
LMH leaveMasterHandler = new LMH();
SimpleDateFormat dateFormat;

EmployeeHandler emph = new EmployeeHandler();
EmployeeBean ebean = new EmployeeBean();
EmpOffHandler eoh=new EmpOffHandler();
String Salary_Month="";
int empNo = request.getParameter("empno")==null?Integer.parseInt(session.getAttribute("EMPNO").toString()):Integer.parseInt(request.getParameter("empno"));


Salary_Month=EmployeeHandler.getSalaryMonth(empNo);

//System.out.println("emonoi   "+empNo);

RoleDAO obj1=new RoleDAO();
String roleId=obj1.getrole(Integer.parseInt(session.getAttribute("UID").toString()));


EmpOffBean eob= eoh.getEmpOfficAddInfo(Integer.toString(empNo));
int prjsrno=eob.getPrj_srno();
ebean = emph.getEmployeeInformation(Integer.toString(empNo));

leaveBalList = leaveMasterHandler.getList(empNo);
String searchList = request.getParameter("sList")==null?"ALL":request.getParameter("sList");






//////


LMH leaveHandler = new LMH();
LMB lBean = new LMB();
LookupHandler lookupHandler= new LookupHandler();
ArrayList<LMB> getSearchList = new ArrayList<LMB>();
LMB lBeanSearchFilter = new LMB();
String action = request.getParameter("action")==null?"NA":request.getParameter("action");
String error = request.getParameter("error")==null?"":request.getParameter("error");
ArrayList<LMB> leaveApp =new ArrayList<LMB>();
int empno=0;
int appNo=0;
String submithide="";
if(action.equalsIgnoreCase("NA")){
lBean.setSTATUS("PENDING");
session.setAttribute("leaveSearchFilter", lBean);
	getSearchList = (ArrayList<LMB>) leaveHandler.getLeaveAppList(lBean,"PENDING");
}else{
	
	lBeanSearchFilter = (LMB)session.getAttribute("leaveSearchFilter");
	getSearchList = (ArrayList<LMB>) leaveHandler.getLeaveAppList(lBeanSearchFilter,"customSearch");
}
String flag="100";
flag=request.getParameter("flag")==null?"100":request.getParameter("flag"); 
%>
<body onLoad="checkLoan()">
<img src='images/Close.png' style='float:right;' title='Remove' onclick="closediv()"><br>
	<%-- <div style= "visible: true	;" >
	 <%@include file="mainHeader.jsp" %> 
	<!--  start nav-outer-repeat................................................................................................. START -->
	<%@include file="subHeader.jsp"%>  
	 </div> --%>
<!-- start content-outer ... .....................................................................................................................START -->
<div id="content-outer" >
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Leave Details</h1>
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
			<%if(!action.equalsIgnoreCase("editLeaveApp")){ %>
			<div id="table-content" align="center" style="overflow-y:auto; max-height:220px; ">
			
					 			<form name="searchappl" action="LMS?action=customSearch"
										method="post" onSubmit="return getname()">
										<table id="customers" style="margin-top:1px;float: none;">
											<tr id="search-emp-h">
												<th colspan="10"><font size="3">Search Leave Application Of Perticular Employee For Cancel </font></th>
											</tr>
											
												<!-- <tr class="alt">
												
											</tr> -->
											
											<tr class="alt" id="search-emp">
											<td >ENTER EMPLOYEE&nbsp; <font color="red"><b>*</b></font>	</td>
												<td colspan="4	"><input type="text" name="EMPNO" id="EMPNO" 
													size="40"  placeholder="Enter Employee NAME OR ID"></td>
												<td>FROM DATE&nbsp; <font color="red"><b>*</b></font></td>
												 <td><input name="frmdate" size="20" id="frmdate"
													type="text" onBlur="if(value=='')" readonly="readonly"> &nbsp;<img
													src="images/cal.gif" align="middle" value=<%=lBeanSearchFilter.getFRMDT() %>
													style="vertical-align: middle; cursor: pointer;"
													onClick="javascript:NewCssCalnew('frmdate',1)" />
												</td>
												<td>TO DATE&nbsp; <font color="red"><b>*</b></font></td>
												<td><input name="todate" size="20" id="todate" 
													type="text" onBlur="if(value=='')" readonly="readonly">&nbsp;<img
													src="images/cal.gif" align="middle" value=<%=lBeanSearchFilter.getTODT() %>
													style="vertical-align: middle; cursor: pointer;"
													onClick="javascript:NewCssCalnew('todate', 1)" /></td>
													
													<td colspan="4" align="right">
											<input type="submit"	value="Search"  class="myButton"/>
											</td>
											</tr>

										<!-- onclick="getEmployee()" -->
											<!-- <tr class="alt">
												<td>STATUS&nbsp; <font color="red"><b>*</b></font></td>
												<td colspan="3"><select name="type">
														<option value="Default">Select</option>
														<option value="All">All</option>
														<option value="sanction">sanction</option>
														<option value="pending">pending</option>
												</select> &nbsp;&nbsp;&nbsp;&nbsp; </td>	
											</tr> -->
											<!-- <tr> <td colspan="4" align="right">
											<input type="submit" value="Search" class="myButton"/>
											</td></tr> -->
										</table>
									</form> -
								
								<div align="center">
								<br/>
								<h3 style="color: red;"><%=error %></h3>
								<h3>Leave Applications  <%=lBeanSearchFilter.getEmpCode()==null?"":lBeanSearchFilter.getEmpCode() %></h3>
										<table id="customers" style="float: none;">
											<tr>
					<%
															if(!action.equalsIgnoreCase("customSearch")){ %>
														<th width="100">EMPLOYEE CODE</th>
														<% } else {%>
														<th width="100">CHECK</th>
														<%} %>
														<th >EMP NAME</th>
														<th width="90">APP DATE</th>
														<th width="90">LEAVE TYPE</th>
														<th width="85">FROM DATE</th>
														<th width="95">TO DATE</th>
														<th width="50">DAYS</th>
														<th width="100">REASON</th>
														<!-- <th >PURPOSE</th> -->
														<th width="200">SANCTION / CANCEL</th>
													<% 	 if(action.equalsIgnoreCase("customSearch")){ %>
															<th width="50">EDIT</th>
														<% } %> 
													
											</tr>
											<%		EmployeeHandler emph1 = new EmployeeHandler();
													EmployeeBean ebean1 = new EmployeeBean();
											
															if (getSearchList.size() != 0) {

																	for (LMB sancb : getSearchList) {
																		ebean1 = emph1.getEmployeeInformation(Integer.toString(sancb.getEMPNO()));

														if(sancb.getLEAVECD()!=0 && sancb.getLEAVECD()!=0){
                                                          	//if(sancb.getLEAVECD()!=7 && sancb.getLEAVECD()!=10){
									%>
															

											<tr align="center">
															<td width="100">
															<% if(!action.equalsIgnoreCase("customSearch")){ %>
															<%=ebean1.getEMPCODE() %>
															<%}else { %>
															<input class="empCheckbox" align="left" type="checkbox" id="checked" 
															onclick="ticked('<%=sancb.getAPPLNO()%>','<%=sancb.getEMPNO()%>','<%=sancb.getLEAVECD()%>','<%=sancb.getNODAYS()%>')"
															/>
															<input  align="right" type="button" 
															
															id="Cancel_<%=sancb.getAPPLNO()%>" value="Cancel" disabled="disabled" onclick="cancelLeave('<%=sancb.getAPPLNO()%>','<%=ebean1.getFNAME()%>','<%=ebean1.getLNAME()%>','<%=lookupHandler.getLKP_Desc("LEAVE",sancb.getLEAVECD())%>')" />
															<%} %>
															</td>
															<td ><%=lookupHandler.getLKP_Desc("SALUTE", ebean1.getSALUTE())+" "+ ebean1.getFNAME()+" "+ebean1.getLNAME() %></td>
															<td width="85"><%=sancb.getTRNDATE()%></td>
															<%
															if(sancb.getLREASON().equalsIgnoreCase("Late Leave Will be Deducted as CL,PL,LWP")){%>
																
																	<td width="80"><%="Late_L" %></td>
																<%
															}else{
															%>
															<td width="80"><%=lookupHandler.getLKP_Desc("LEAVE",sancb.getLEAVECD())%></td>
															<%}  %>
															
													<%-- 		<td width="80"><%=lookupHandler.getLKP_Desc("LEAVE",sancb.getLEAVECD())%></td> --%>
															<td width="80"><%=sancb.getFRMDT()%></td>
															<td width="80"><%=sancb.getTODT()%></td>
															 <td width="50"><%=sancb.getNODAYS()%></td>
												<%--			<% if(sancb.getTRNTYPE() == 'C'){ %>
																<td>Credit</td>
																<%}else{ %>
																<td>Debit</td>
																<%} %> --%>
										<%-- 				<td><%=lookupHandler.getLKP_Desc("LPURP",sancb.getLEAVEPURP()).equals("LEAVE PURPOSE")?"":
		lookupHandler.getLKP_Desc("LPURP",sancb.getLEAVEPURP())%></td>	 --%>
		
															<td width="50"><%=sancb.getLREASON()%></td>
														
															<td>
															<%
															if(sancb.getSTATUS().equalsIgnoreCase("PENDING")){ %>
															<%-- <form id="my_form" method="post" action="LMS">
															
																<input type="hidden" name="action" value="sanctionLeaveApp">
	    												 		<input type="hidden" name="appNo" value="<%=sancb.getAPPLNO()%>">
	    														<input type="hidden" name="empno" value="<%=sancb.getEMPNO()%>">
	    														<input style="float: left;" type="button" onclick="document.getElementById('my_form').submit();" value="Sanction">
														    	<!-- <a href="javascript:{}" onclick="document.getElementById('my_form').submit();">Sanction</a> -->
      													   </form> --%>		   
																
														   		<input type="button" class="myButton"  value="Sanction" onclick="Sanction('sanctionLeaveApp','<%=sancb.getEMPNO()%>','<%=sancb.getAPPLNO()%>','<%=ebean1.getFNAME()%>','<%=ebean1.getLNAME()%>')"/>&nbsp;
														   		<input type="button" class="myButton"  value="Demo Sanction" onclick="checkLeaveStatus('sanctionLeaveApp','<%=sancb.getEMPNO()%>','<%=sancb.getAPPLNO()%>','<%=ebean1.getFNAME()%>','<%=ebean1.getLNAME()%>')"/>&nbsp;
														   		<input type="button" class="myButton"  value="Cancel" onclick="Cancel('cancelLeaveApp','<%=sancb.getEMPNO()%>','<%=sancb.getAPPLNO()%>','<%=ebean1.getFNAME()%>','<%=ebean1.getLNAME()%>')"/>
														   		
														   <%-- <form id="cancel_Form" method="post" action="LMS">
															
																<input type="hidden" name="action" value="cancelLeaveApp">
	    														<input type="hidden" name="appNo" value="<%=sancb.getAPPLNO()%>">
	    														<input type="hidden" name="empno" value="<%=sancb.getEMPNO()%>">
	    														<input style=" margin-left:10px; float: left;" type="button" onclick="document.getElementById('cancel_Form').submit();" value="Cancel">
            													<!-- <a href="javascript:{}" onclick="document.getElementById('cancel_Form').submit();">Cancel</a> -->
      													   </form> --%>
															<%
															}else{
															%>
															<%= sancb.getSTATUS() %>
													    	</td>
													    		<%} %>
													    		<% if(action.equalsIgnoreCase("customSearch")){ %>
													    	<td>
													    	    	<a href="javascript:{}" onclick="editLeave('<%=sancb.getEMPNO()%>','<%=sancb.getAPPLNO()%>')">Edit</a>
													    	</td>
															<%} %>
												</tr>
													<%}}}else { %>
												<tr>
														<td colspan="10" align="left"><font size="4">No Applications To Display </font></td>
												</tr>
												<%} %>
										</table>
										  <input type="hidden" name="flag" id="flag" value="<%=flag%>">
			                   </div>  							
			</div><%} %> 
			<%
		if(action.equalsIgnoreCase("editLeaveApp")){
			
			 empno = Integer.parseInt(request.getParameter("empno"));
			 appNo = Integer.parseInt(request.getParameter("appno"));
			 submithide = request.getParameter("hides");
			 leaveApp = leaveMasterHandler.getLeaveApp(empno, appNo);
			
			for(LMB leaveBean : leaveApp){
				
		%>
	<div  align="center" >
			<%-- <h3 style="color: white;"><%=error %></h3> --%>
	<form  id="form" name="leaveForm" action="LMS?action=editleave1" method="post" onSubmit=" return validate1()">
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
		      <td><input name="tdate" id="tdate" type="text"  readonly="readonly" value="<%=leaveBean.getTRNDATE() %>" size="25">&nbsp;
		      <img
							src="images/cal.gif" align="middle" id="img1" class="datecls"
							style="vertical-align: middle; cursor: pointer;"
							onClick="javascript:NewCssCalnew('tdate',2);" /></td>
		    
		      <td>&nbsp;<font size="4">Leave Type</font> <font color="red" size="3">*</font></td>
		      <td>
		    	<select name="leavecode1" id="leavecode1" style="width: 128px" onchange="matLeave()"> 
		  <!--     	<select name="leavecode1" id="leavecode1" style="width: 128px" > -->
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
				 							<option value="<%=lkbean.getLKP_SRNO()%>"  title="<%=leaveMasterHandler.getLeaveBalOfEmployeeCodeWise(empNo,lkbean.getLKP_SRNO())%>" ><%=lkbean.getLKP_DESC()%></option>  
									 	<%}
							}
									 	%>
				</select>
			  </td>
		    </tr>
		    <tr>
		    	<td><font size="4">From Date </font><font color="red" size="3">*</font></td>
		     	<td><input name="frmdate1" id="frmdate1" type="text" value="<%=leaveBean.getFRMDT() %>" readonly="readonly" size="25" onchange="setDays(); matLeave();">&nbsp;
		     	<img
							src="images/cal.gif" align="middle" id="img2"
							style="vertical-align: middle; cursor: pointer;"
							onClick="javascript:NewCssCalnew('frmdate1', 3)" /></td>
		   
		      	<td><font size="4">To Date </font><font color="red" size="3">*</font></td>
		     	<td><input name="todate1" id="todate1" type="text" value="" readonly="readonly" size="25" onchange="setDays(); matLeave();"  placeholder="<%=leaveBean.getTODT() %>" >&nbsp;
		     	<img
							src="images/cal.gif" align="middle" id="img3"
							style="vertical-align: middle; cursor: pointer;"
							onClick="javascript:NewCssCalnew('todate1', 3)" />
				</td>
		   </tr>
		    <tr>
		      	<td><font size="4">Leave Purpose</font> <font color="red" size="3">*</font> </td>
		      	<td colspan="3"><input type="text" id="lreason"  name="lreason" value="<%=lookupHandler.getLKP_Desc("LPURP",leaveBean.getLEAVEPURP()).equals("LEAVE PURPOSE")?"":
		lookupHandler.getLKP_Desc("LPURP",leaveBean.getLEAVEPURP())%>" size="75" placeholder="Enter Leave Purpose"/></td>
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
	 	  		 <input type="text" name="days" id="days"  value="<%=leaveBean.getNODAYS() %>" readonly="readonly"/>
	 	  	</td>
	 	  </tr>
	 
		   <tr>
		    <td height="31" colspan="4" align="center">
		    	<!-- <input type="submit" style="margin-right: 50px"  value="Save" class="myButton" />&nbsp;&nbsp;
		    	<input type="reset" value="Cancel" onclick="window.location.href='leave.jsp'" class="myButton"/>
	    	 <input type="button" style="margin-left: 50px" value="ADD REASON " title="ADD REASON TO LOOKUP" class="myButton" onclick="toLookup()"/> -->
	    	 
	    <!-- 	 <input type="button" style="margin-left: 5px" value="ADD REASON " title="ADD REASON TO LOOKUP" class="myButton" onclick="toLookup()"/> -->
	    	 <input type="submit" style="margin-right: 50px"  value="Save" class="myButton" />&nbsp;&nbsp;
	    	<input type="reset" value="Cancel" onclick="window.location.href='sanctionpopup.jsp'" class="myButton"  /> 
	    	  
		    	<input type="hidden" name="appNo" id="appNo" value="<%=leaveBean.getAPPLNO()%>">
		    	
		    	<input type="hidden" name="empno01" id="empno01" value="<%=leaveBean.getEMPNO()%>">
		    </td>
		  </tr>
	</table>
   
   </form>

		</div>
		<br/>
		<% 		
		}
		  
		}%>
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
	<input type="hidden" name="submithide" id="submithide" value="<%=submithide%>">
    

</body>
</html>