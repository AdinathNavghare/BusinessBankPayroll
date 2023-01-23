<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@page import="payroll.DAO.ConnectionManager"%>
<%@page import="payroll.DAO.EmpAttendanceHandler"%>
<%@page import="payroll.Model.LeaveMassBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Date"%>  
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    	<%@page import="java.util.ArrayList"%>
<%@page import="java.util.ListIterator"%>
<%@page import="payroll.DAO.LeaveMasterHandler"%>
<%@page import="payroll.Model.LeaveMasterBean"%>
<%@page import="payroll.Model.Lookup"%>
<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="payroll.Core.ReportDAO"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy DTS3 </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />

		  
<script type="text/javascript" src="js/jquery.datePicker.js"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<style type="text/css">
<!--
body,tr,td,th {
	font-family: Times New Roman;
	font-size: 13px;
}
-->
</style>
<%-- <%
	int flag=-1;
	try
	{
		flag = Integer.parseInt(request.getParameter("flag"));
	}
	catch(Exception e)
	{
	}
	
	String action=request.getParameter("action")==null?"":request.getParameter("action");
	LeaveMasterHandler lmh=new LeaveMasterHandler();
	ArrayList<LeaveMassBean>  ltype=new ArrayList<LeaveMassBean> ();
	ltype=lmh.getleavetypeList("select * from leavemass order by  EFFDATE desc,srno desc");
%> --%>

<%
	 int flag=-1;
     String flag1="";
     String  today="";
     String  jandate="";
	 String  date_lastyear="";
	try
	{
		 Connection con = ConnectionManager.getConnection();
		 ResultSet rs = null;
		 Statement st = con.createStatement();
		  
          String  query1 ="with a as  ( select top 1 LEAVE_END_DATE  "  +
						" from LEAVE_YEAR where STATUS ='open' order by srno desc) " +
        		        " select top 1 *,CONVERT(date, GETDATE(),105) as today , "  +
        		        " dateadd(d,1,CONVERT(date,(select dateadd(m,1,(select * from a))),105)) as jandate "  +
        		        " from LEAVE_YEAR where STATUS ='open' order by srno desc";
          rs=st.executeQuery(query1);
          while (rs.next()) {
          	date_lastyear = rs.getString("LEAVE_END_DATE");
            today = rs.getString("today");
           	jandate = rs.getString("jandate");
              System.out.println("date_lastyeardate_lastyear:--" + date_lastyear);
              System.out.println("jandate:--" + jandate);
          }
		flag1 = (request.getParameter("flag"));
		flag = Integer.parseInt(request.getParameter("flag"));
	}
	catch(Exception e)
	{
	}
	
	String action=request.getParameter("action")==null?"":request.getParameter("action");
	LeaveMasterHandler lmh=new LeaveMasterHandler();
	ArrayList<LeaveMassBean>  ltype=new ArrayList<LeaveMassBean> ();
	EmpAttendanceHandler empAttendanceHandler=new EmpAttendanceHandler();
	String todaysDate=empAttendanceHandler.getServerDate();
	String beginningOfYear=ReportDAO.BOYForJanToDec(todaysDate);
	String sql="select * from leavemass where status='A' and EFFDATE>= ' "+beginningOfYear+" ' order by EFFDATE desc,srno desc";

	System.out.println(sql);
	ltype=lmh.getleavetypeList(sql);
%>

<script type="text/javascript">


function checkFlag()
{
	var flag = parseInt(document.getElementById("flag").value);
	if(flag == 1)
	{
		alert("Leaves Entered Successfully");
	}
	else if(flag == 2)
	{
		alert("Error in Saving Leaves");
	}
	else if(flag == 3)
	{
		alert("Leaves Updated Successfully");
		
	}
	else if(flag == 4)
	{
		alert("Error in updating leaves ");
	
	}
	else if(flag == 5)
	{
		alert("Selected Leave Type already Exist.!");
	
	}
	else if(flag == 6)
	{
		alert("You can not add leaves for a year other than current year");
	}
	else if(flag == 7)
	{
		alert("You can not update current record's dates to another year ");
	}
}
function validateEdit()
{
	var fromDate=document.editform.lbgndate1.value;
	   var toDate=document.editform.lenddate1.value;
	   fromDate = fromDate.replace(/-/g,"/");
		toDate = toDate.replace(/-/g,"/");
		var creditLimitEdit=document.getElementById("crlimit1").value;
		var minLimitEdit=document.getElementById("minlimit1").value;
		var maxCumulativeLimitEdit=document.getElementById("maxcultv1").value;
		var maxCarryForwardEdit=document.getElementById("maxcf1").value;
		
		if(document.getElementById("leavecode1").value=="0")
		   {
		   alert("Please Select Leave Type");
		   document.getElementById("leavecode1").focus();
		   return false;
		   }
	   if(document.getElementById("Efftdate1").value=="")
		 {
		 alert("Please Select Effective date");
		 document.getElementById("Efftdate1").focus();
	   return false;
		 
		 }
	   if(document.getElementById("ldescrptn1").value=="")
		   {
		   alert("Please Select Leave Description");
		   document.getElementById("ldescrptn1").focus();
		   return false;
		   }
	   if( document.getElementById("lbgndate1").value=="")
	   {
		   	alert("Please enter the Begin date");
		   	document.getElementById("lbgndate1").focus();
		      return false;
		   }
	   if( document.getElementById("lenddate1").value=="")
	   {
		   	alert("Please enter the end date");
		   	document.getElementById("lenddate1").focus();
		      return false;
		   }
	   if(document.getElementById("frequency1").value=="0")
	   {
		   alert("Please Enter data for Frequency ");
		   document.getElementById("frequency1").focus();
		   return false;
	   }
	   if(document.getElementById("holiday1").value=="0")
	   {
	   alert("Please Select Holiday Y/N");
	   document.LeaveReport.holiday.focus();
	   return false;
	   }
	 
	   
	   if(creditLimitEdit=="")
	   {
		   alert("Please Enter Credit Limit ");
		  
		   document.getElementById("crlimit1").focus();
		   return false;
	   }
	   
		  if(0.5>parseFloat(creditLimitEdit)){
		    	alert("Credit Limit should not be less than a half day ");
		    	document.getElementById("crlimit1").focus();
		 	   	return false;
		    }
		    
		    if(parseFloat(creditLimitEdit)%0.5!=0){
		    	alert("Credit limit must be multiple of 0.5 ");
		    	document.getElementById("crlimit1").focus();
		 	   	return false;
		    }
		
		    var string=creditLimitEdit.toString();
		    var pattern = /^\d+(\.\d{1,2})?$/;
		    var result = string.match(pattern);    
		    if(!result){
		    	alert("Invalid number of Leaves entered");	
		    	return false;
		    }
	   
	   
	   
	   if(minLimitEdit=="")
	   {
		   alert("Please Enter Minimum Limit ");
		  
		   document.getElementById("minlimit1").focus();
		   return false;
	   }
	   
	   if(0.5>parseFloat(minLimitEdit)&& minLimitEdit!=0 ){
	    	alert("Minimum Limit should not be less than a half day or should be zero");
	    	document.getElementById("minlimit1").focus();
	 	   	return false;
	    }
	    
	    if(parseFloat(minLimitEdit)%0.5!=0){
	    	alert("Minimum limit must be multiple of 0.5 ");
	    	document.getElementById("minlimit1").focus();
	 	   	return false;
	    }
	
	    var string=minLimitEdit.toString();
	    var pattern = /^\d+(\.\d{1,2})?$/;
	    var result = string.match(pattern);    
	    if(!result){
	    	alert("Invalid number of Leaves entered");	
	    	return false;
	    }
	    
	   if(maxCumulativeLimitEdit=="")
	   {
		   alert("Please Enter Max Cumulative Limit ");
		
		   document.getElementById("maxcultv1").focus();
		   return false;
	   }
	   
	   if(0.5>parseFloat(maxCumulativeLimitEdit)&& maxCumulativeLimitEdit!=0 ){
	    	alert("Maximum Cumulative Limit should not be less than a half day or should be zero ");
	    	document.getElementById("maxcultv1").focus();
	 	   	return false;
	    }
	    
	    if(parseFloat(maxCumulativeLimitEdit)%0.5!=0){
	    	alert("Maximum Cumulative Limit must be multiple of 0.5 ");
	    	document.getElementById("maxcultv1").focus();
	 	   	return false;
	    }
	
	    var string=maxCumulativeLimitEdit.toString();
	    var pattern = /^\d+(\.\d{1,2})?$/;
	    var result = string.match(pattern);    
	    if(!result){
	    	alert("Invalid number of Leaves entered");	
	    	return false;
	    }
	    
	   if(maxCarryForwardEdit=="")
	   {
		   alert("Please Enter Max Carry Forward ");
		   document.getElementById("maxcf1").focus();
		   return false;
	   }
	   
	   if(0.5>parseFloat(maxCarryForwardEdit)&& maxCarryForwardEdit!=0 ){
	    	alert("Maximum Carry Forward should not be less than a half day or should be zero ");
	    	document.getElementById("maxcf1").focus();
	 	   	return false;
	    }
	    
	    if(parseFloat(maxCarryForwardEdit)%0.5!=0){
	    	alert("Maximum Carry Forward must be multiple of 0.5 ");
	    	document.getElementById("maxcf1").focus();
	 	   	return false;
	    }

	    var string=maxCarryForwardEdit.toString();
	    var pattern = /^\d+(\.\d{1,2})?$/;
	    var result = string.match(pattern);    
	    if(!result){
	    	alert("Invalid number of Leaves entered");	
	    	return false;
	    }
	   
	   var d1 = new Date(fromDate);
	 	
	 	var d2 =new  Date(toDate);
	 	
	 if (d1.getTime() > d2.getTime())
	      {
		   alert("Invalid Date Range!\n Begin Date Date cannot greater than End Date!");
		   document.editform.lbgndate.focus();
		   return false;
		   }
	 document.getElementById("leavecode1").disabled = false;
	 
	 var flag = confirm("Are you Sure update Leaves?");
		if(flag)
		{
			return true;
		}
		else{
			return false;
		}
	 
}

function validate()
{
	 
	
	 var fromDate=document.getElementById("lbgndate").value;
	   var toDate=document.getElementById("lenddate").value;
	   fromDate = fromDate.replace(/-/g,"/");
		toDate = toDate.replace(/-/g,"/");
		var creditLimit=document.getElementById("crlimit").value;
		var minLimit=document.getElementById("minlimit").value;
		var maxCumulativeLimit=document.getElementById("maxcultv").value;
		var maxCarryForward=document.getElementById("maxcf").value;
		
	    if(document.getElementById("leavecode").value=="0")
		   {
		   alert("Please Select Leave Type");
		   
		   document.getElementById("leavecode").focus();
		   
		   return false;
		   }
	   if(document.getElementById("Efftdate").value=="")
		 {
		 alert("Please Select Effective date");
		
		 document.getElementById("Efftdate").focus();
	   return false;
		 
		 }
	   if(document.getElementById("ldescrptn").value=="")
		   {
		   alert("Please Select Leave Description");
		  
		   document.getElementById("ldescrptn").focus();
		   return false;
		   }
	   if( document.getElementById("lbgndate").value=="")
	   {
		   	alert("Please enter the Begin date");
		   
		   	document.getElementById("lbgndate").focus();
		      return false;
		   }
	   if( document.getElementById("lenddate").value=="")
	   {
		   	alert("Please enter the end date");
		   	
		   	document.getElementById("lenddate").focus();
		      return false;
		   }
	   if(document.getElementById("frequency").value=="0")
	   {
		   alert("Please Enter data for Frequency ");
		   
		   document.getElementById("frequency").focus();
		   return false;
	   }
	   if(document.getElementById("holiday").value=="0")
	   {
	   alert("Please Select Holiday Y/N");
	 
	   document.LeaveReport.holiday.focus();
	   return false;
	   }
	   if(creditLimit=="")
	   {
		   alert("Please Enter Credit Limit ");
		   document.getElementById("crlimit").focus();
		   return false;
	   }
	   
		  if(0.5>parseFloat(creditLimit)){
		    	alert("Credit Limit should not be less than a half day ");
		    	document.getElementById("crlimit").focus();
		 	   	return false;
		    }
		    
		    if(parseFloat(creditLimit)%0.5!=0){
		    	alert("Credit limit must be multiple of 0.5 ");
		    	document.getElementById("crlimit").focus();
		 	   	return false;
		    }
		
		    var string=creditLimit.toString();
		    var pattern = /^\d+(\.\d{1,2})?$/;
		    var result = string.match(pattern);    
		    if(!result){
		    	alert("Invalid number of Leaves entered");	
		    	return false;
		    }
		   
	   
	   
	   if(minLimit=="")
	   {
		   alert("Please Enter Minimum Limit ");
		  
		   document.getElementById("minlimit").focus();
		   return false;
	   }
	   
	   if(0.5>parseFloat(minLimit)&& minLimit!=0 ){
	    	alert("Minimum Limit should not be less than a half day or should be zero");
	    	document.getElementById("minlimit").focus();
	 	   	return false;
	    }
	    
	    if(parseFloat(minLimit)%0.5!=0){
	    	alert("Minimum limit must be multiple of 0.5 ");
	    	document.getElementById("minlimit").focus();
	 	   	return false;
	    }
	
	    var string=minLimit.toString();
	    var pattern = /^\d+(\.\d{1,2})?$/;
	    var result = string.match(pattern);    
	    if(!result){
	    	alert("Invalid number of Leaves entered");	
	    	return false;
	    }
	   
	    
	   if(maxCumulativeLimit=="")
	   {
		   alert("Please Enter Max Cumulative Limit ");
		
		   document.getElementById("maxcultv").focus();
		   return false;
	   }
	   
	   if(0.5>parseFloat(maxCumulativeLimit)&& maxCumulativeLimit!=0 ){
	    	alert("Maximum Cumulative Limit should not be less than a half day or should be zero ");
	    	document.getElementById("maxcultv").focus();
	 	   	return false;
	    }
	    
	    if(parseFloat(maxCumulativeLimit)%0.5!=0){
	    	alert("Maximum Cumulative Limit must be multiple of 0.5 ");
	    	document.getElementById("maxcultv").focus();
	 	   	return false;
	    }
	
	    var string=maxCumulativeLimit.toString();
	    var pattern = /^\d+(\.\d{1,2})?$/;
	    var result = string.match(pattern);    
	    if(!result){
	    	alert("Invalid number of Leaves entered");	
	    	return false;
	    }
	   
	   
	   if(maxCarryForward=="")
	   {
		   alert("Please Enter Max Carry Forward ");
		   document.getElementById("maxcf").focus();
		   return false;
	   }
	   
	   if(0.5>parseFloat(maxCarryForward)&& maxCarryForward!=0 ){
	    	alert("Maximum Carry Forward should not be less than a half day or should be zero ");
	    	document.getElementById("maxcf").focus();
	 	   	return false;
	    }
	    
	    if(parseFloat(maxCarryForward)%0.5!=0){
	    	alert("Maximum Carry Forward must be multiple of 0.5 ");
	    	document.getElementById("maxcf").focus();
	 	   	return false;
	    }
	    
	    var string=maxCarryForward.toString();
	    var pattern = /^\d+(\.\d{1,2})?$/;
	    var result = string.match(pattern);    
	    if(!result){
	    	alert("Invalid number of Leaves entered");	
	    	return false;
	    }
	   

	   
	   if(document.getElementById("leaveincash").value=="0")
	   {
		   alert("Please Select Leave EnCash Y / N ");
		  
		   document.getElementById("leaveincash").focus();
		   return false;
	   }
	   
	   	
	   var d1 = new Date(fromDate);
	 	
	 	var d2 =new  Date(toDate);
	 	
	 if (d1.getTime() > d2.getTime())
	      {
		   alert("Invalid Date Range!\n Begin Date Date cannot greater than End Date!");
		   document.getElementById("dateimg").src='images/cal.gif';
			document.getElementById("dateimg1").src='images/cal.gif';
		   //document.getElementById("checksubmit").disabled="disabled";
		   document.getElementById("lbgndate").focus();
		  
		   return false;
		   } 
	 	
	 document.getElementById("lbgndate").disabled = false;
		document.getElementById("lenddate").disabled = false;
		document.getElementById("leavecode").disabled = false;
		
		 var flag = confirm("Are you Sure to Enter Leaves?");	 
			if(flag)
			{
				return true;
			}
			else{
				return false;
			}
}

function editLtype(key)
{
	
	window.location.href="leaveTypeDetails.jsp?action=edit&key1="+key;
}
function editHide()
{
	//document.getElementById("editdiv").style.display = "none";
	window.location.href="leaveTypeDetails.jsp";
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
function checkEntry()
{
	var flag=true;
	if(document.getElementById("leavecode").value=="0")
		{
		alert("Please Select Leave Type");
		flag=false;
		}	
	else if( document.getElementById("lbgndate").value=="" || document.getElementById("lenddate").value =="")
		{
		alert("Please Select Both Begin date and End date");
		flag=false;
		}
	
	if(flag)
		{
		
		var lcode = document.getElementById("leavecode").value;
		
		var begindate=document.getElementById("lbgndate").value;
		
		var enddate=document.getElementById("lenddate").value; 
		
		var key = lcode+":"+begindate+":"+enddate;
	
		url="leave?action=checkentry&key="+key;
		xmlhttp.onreadystatechange=function()
		{

			
			if(xmlhttp.readyState==4)
			{
		    	var response=xmlhttp.responseText;
		    	
		    	if(response=="true")
		    	{
		    		alert("Data already exist for selected Leave Type.!");
					document.getElementById("checksubmit").disabled = "disabled";
					document.getElementById("changedata").disabled = "disabled";
		        }
		    	else
		    	{
		    		
		    		document.getElementById("checksubmit").disabled = false;
		    		document.getElementById("changedata").disabled = false;
		    		document.getElementById("leavecode").disabled = true;
		    		document.getElementById("lbgndate").disabled = true;
		    		document.getElementById("dateimg").src='';
		    		document.getElementById("dateimg1").src='';
		    		document.getElementById("lenddate").disabled = true; 

		    		
		    		
		    	}
			}
		};
		
		xmlhttp.open("POST", url, true);
		xmlhttp.send();
		}
}

function ChangeData()
{
	document.getElementById("checksubmit").disabled = "disabled";	
	document.getElementById("changedata").disabled = "disabled";
	document.getElementById("lbgndate").disabled = false;
	document.getElementById("lenddate").disabled = false;
	document.getElementById("leavecode").disabled = false;
	document.getElementById("dateimg").src='images/cal.gif';
	document.getElementById("dateimg1").src='images/cal.gif';
	
	}
	
function inputLimiterForFloat(e,allow) {
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
	
function encashEffect()
{
  	 var type=document.getElementById("leavecode").value;
	if(type==2 ||type==3){
		document.getElementById("leaveincash").value="N";
		  $('#leaveincash').attr('disabled',true);
	
		
	}
	if(type==1){
		document.getElementById("leaveincash").value="Y";
		  $('#leaveincash').attr('disabled',true);
	
		
	}
}
	
</script>
<style type="text/css">
.row:hover {
	background-color: #CCCCCC;
	cursor: pointer;
}

.style1 {
	color: #FF0000
}
</style>
</head>
<body onLoad="checkFlag()" style="overflow: hidden;"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer"  style="overflow-y:auto; max-height:82%; ">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Leave Master</h1>
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
			<div id="table-content" align="center">
			
			<%
			
			
			//EmpAttendanceHandler empAttendanceHandler=new EmpAttendanceHandler();
			String str11=date_lastyear.replace("-","/");
            String str22=today.replace("-","/");
            String str33=jandate.replace("-","/");
			Date d1= new Date(str11);
			Date d2= new Date(str22);
			Date d3= new Date(str33);
			
			if(d2.before(d1))
			{
			
			if(action.equalsIgnoreCase("edit"))
				{
				String[] str1 = request.getParameter("key1").split(":");
				String effdate = str1[0];
				int srno=Integer.parseInt(str1[1]==null?"0":str1[1]);
				for(LeaveMassBean bean:ltype )
				{
					if(bean.getSRNO()==srno )
					{
			  %>
			   <div  id="editdiv">
			   <form name="editform" action="leave" method="post" onSubmit="return validateEdit()">
										
		<table border="1" id="customers">
	
			<tr class="alt">
				<td height="200" align="center">
				<table>
					<tr>
						<th colspan="4" align="center">Edit Leave Details<input type="hidden" name="action" value="EditLeavetype"></th></tr>
					<tr>
					<tr>
						<td>Leave Type<input type="hidden"  name="srno" id="srno" value="<%=bean.getSRNO()%>"/> </td>
						<td>
							<select name="leavecode1" id="leavecode1" style="width: 140px" disabled="disabled" >
								 <option value="0">Select....</option>  
    						<%
  							  ArrayList<Lookup> result=new ArrayList<Lookup>();
    						LookupHandler lkhp1= new LookupHandler();
    						result=lkhp1.getSubLKP_DESC("LEAVE");
 							for(Lookup lkbean : result)
 							{
     						if(lkbean.getLKP_SRNO()== bean.getLEAVECD())
     						{
     							%>
     							<option value="<%=bean.getLEAVECD()%>" selected="selected"><%=lkhp1.getLKP_Desc("LEAVE", bean.getLEAVECD())%></option>
     							<%
     						}
     						else
     						{
     						%>
     							<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
     					 <%
     						}
     						}%>
							
							</select>
						</td>
						<td>Effective Date</td>
						<td>
						<input name="Efftdate1"  size="20" id="Efftdate1" type="text" readonly="readonly" value="<%=bean.getEFFDATE() %>"
							onFocus="if(value=='dd-mmm-yyyy') {value=''}"
							onBlur="if(value=='') {value='';}">&nbsp;<img
							src="images/cal.gif" align="middle"
							style="vertical-align: middle; cursor: pointer;"
							onClick="javascript:NewCssCal('Efftdate1', 'ddmmmyyyy')" />
						</td>
						
					</tr>
					<tr><td>Leave Description</td >
						<td colspan="3"><input name="ldescrptn1" type="text" id="ldescrptn1" value="<%=bean.getLEAVEDES() %>" size="50" maxlength="40"/></td>
					</tr>
					<tr>
						<td>Leave Begin Date</td>
						<td>
						<input name="lbgndate1"  size="20" id="lbgndate1" type="text" readonly="readonly" value="<%=bean.getFBEGINDATE() %>"
							onFocus="if(value=='dd-mmm-yyyy') {value=''}"
							onBlur="if(value=='') {value='';}">&nbsp;<img
							src="images/cal.gif" align="middle"
							style="vertical-align: middle; cursor: pointer;"
							onClick="javascript:NewCssCal('lbgndate1', 'ddmmmyyyy')" />
						</td>
						<td>Leave End Date</td>
						<td><input name="lenddate1"  size="20" id="lenddate1" readonly="readonly" type="text" value="<%=bean.getFENDDATE() %>"
							onFocus="if(value=='dd-mmm-yyyy') {value=''}"
							onBlur="if(value=='') {value='';}">&nbsp;<img
							src="images/cal.gif" align="middle"
							style="vertical-align: middle; cursor: pointer;"
							onClick="javascript:NewCssCal('lenddate1', 'ddmmmyyyy')" /></td>
					</tr>
					<tr>
						<td>Frequency</td>
						<td>
						<%
						String y = "notselected";
						String h = "notselected";
						String m = "notselected";
						String q = "notselected";
						if(bean.getFREQUENCY().contains("Y")){
							y = "selected";
						}else if(bean.getFREQUENCY().contains("H")){
							h = "selected";
						}else if(bean.getFREQUENCY().contains("M")){
							m = "selected";
						}else if(bean.getFREQUENCY().contains("Q")){
							q = "selected";
						}
						
						%><select name="frequency1" id="frequency1">
								<option value="">Select Frequency</option>
								<option value="M" <%=m %>>Monthly </option>
								<option value="Q" <%=q %>>Quarterly</option>
								<option value="H" <%=h %> >Half Yearly</option>
								<option value="Y" <%=y %> >Yearly</option>
								
								</select>
									
						
						
						</td>
						<td>Consider Holiday</td>
						<td><select name="holiday1" id="holiday1" style="width: 140px">
							<option value="0" >Select..</option>
            			<%
						if((bean.getCONS_HOLIDAYS()).equalsIgnoreCase("Y"))
							{
						%>
           					 <option value="Y" selected="selected" >Yes</option>
            				<option value="N">Non Active</option>
           				 <%
							}
						else if((bean.getCONS_HOLIDAYS()).equalsIgnoreCase("N"))
							{
						%>
            				<option value="Y" >YES</option>
            				<option value="N" selected="selected">No</option>
            			<%
           					 }
						else{
					
						%>
							 <option value="Y" >Yes</option>
           					 <option value="N">No</option>
						<%
						}
            			%></select></td>
					</tr>
					<tr>
						<td>Credit Limit</td>
						<td><input type="text"  name="crlimit1" id="crlimit1" value="<%=bean.getCRLIM()%>" 
						onkeypress="return inputLimiterForFloat(event,'Numbers')" maxlength="4"/></td>
						<td>Minimum Limit</td>
						<td><input  name="minlimit1" type="text" id="minlimit1" value="<%=bean.getMINLIM()%>" maxlength="4" 
						onkeypress="return inputLimiterForFloat(event,'Numbers')" maxlength="4"/></td>
					</tr>
					<tr>
						<td>Maximum Cumulative limit</td>
						<td><input type="text"  name="maxcultv1" id="maxcultv1" value="<%=bean.getMAXCUMLIM()%>"
						onkeypress="return inputLimiterForFloat(event,'Numbers')" maxlength="4"/></td>
						<td>Maximum Carry Forward </td>
						<td><input  name="maxcf1" type="text" id="maxcf1" value="<%=bean.getMAXCF()%>" maxlength="4"
						onkeypress="return inputLimiterForFloat(event,'Numbers')" maxlength="4"/></td>
					</tr>
					<tr>
						<td>Leave Encash</td>
						<td><select name="leaveincash1" id="leaveincash1" style="width: 140px">
								
								
								<%
						if((bean.getLEAVEINCASH()).equalsIgnoreCase("Y"))
							{
						%>
           					 <option value="Y" selected="selected"  >Yes</option>
           
           				 <%
							}
						else if((bean.getLEAVEINCASH()).equalsIgnoreCase("N"))
							{
						%>
            	
            				<option value="N" selected="selected">No</option>
            			<%
           					 }
						else{
					
						%>
							 <option value="Y" >Yes</option>
           					 <option value="N">No</option>
						<%
						}
						%>		
							</select></td>
						<td>Consider Weekly Off</td>
						<td><select name="weekoff1" id="weekoff1" style="width: 140px">
								<option  value="0">Select</option>
								
								<%
						if((bean.getWEEKOFF()).equalsIgnoreCase("Y"))
							{
						%>
           					 <option value="Y" selected="selected" >Yes</option>
            				<option value="N">No</option>
           				 <%
							}
						else if((bean.getWEEKOFF()).equalsIgnoreCase("N"))
							{
						%>
            				<option value="Y" >YES</option>
            				<option value="N" selected="selected">No</option>
            			<%
           					 }
						else{
					
						%>
							 <option value="Y" >Yes</option>
           					 <option value="N">No</option>
						<%
						}
						%>		
							</select></td>
							
					
					</tr>
					
					<tr>
						<td colspan="4" align="center"><input type="submit"value="Update" /><input type="reset"value="Cancel" onclick="editHide()"/></td>
					</tr>
				</table>

			  </td>
			</tr>
			</table>
			<input type="hidden" name="flag" id="flag" value="<%=flag%>">
			</form>
	  		</div>
			  <%
			                 }
						}
					}
			else{
				%>
			
			
			
			
			<div id="addleave">
			
			<form name="LeaveReport" action="leave?action=InsertLeavetype" method="post" onSubmit="return validate()" >
			<input type="hidden" name="action" value="leavemass">
		<table border="1" id="customers">
			<tr class="alt">
				<td height="200" align="center">
				<table>
					<tr>
						<th colspan="4">Leave Master</th>
					</tr>
					<tr>
						<td>Leave Type&nbsp; <font color="red"><b>*</b></font></td>
						<td>
							<select name="leavecode" id="leavecode" style="width: 140px" onchange="encashEffect()">
								<option selected="selected" value="0">Select</option>
								<%
  							  ArrayList<Lookup> result3=new ArrayList<Lookup>();
    						  LookupHandler lkhp3= new LookupHandler();
    					      result3=lkhp3.getSubLKP_DESC("LEAVE");
 							for(Lookup lkbean : result3)
 							{
     						%>
      							<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
     					 	<%}%>
							</select>
						</td>
						<td>Effective Date</td>
						<td>
						<input name="Efftdate"  size="20" id="Efftdate" type="text" readonly="readonly" value="<%=ReportDAO.getSysDate() %>"
							onFocus="if(value=='dd-mmm-yyyy') {value=''}"
							onBlur="if(value=='') {value='';}">&nbsp;<img 
							src="images/cal.gif" align="middle"
							style="vertical-align: middle; cursor: pointer;"
							onClick="javascript:NewCssCal('Efftdate', 'ddmmmyyyy')" />
						</td>
						
					</tr>
					<tr><td>Leave Description</td >
						<td colspan="3"><input name="ldescrptn" type="text" id="ldescrptn" size="50" maxlength="40"/></td>
					</tr>
					<tr>
						<td>Leave Begin Date&nbsp; <font color="red"><b>*</b></font></td>
						<td>
						<input name="lbgndate"  size="20" id="lbgndate" type="text" readonly="readonly" value=""
							onFocus="if(value=='dd-mmm-yyyy') {value=''}"
							onBlur="if(value=='') {value='';}">&nbsp;<img id="dateimg"
							src="images/cal.gif" align="middle"
							style="vertical-align: middle; cursor: pointer;"
							onClick="javascript:NewCssCal('lbgndate', 'ddmmmyyyy')" />
						</td>
						<td>Leave End Date&nbsp; <font color="red"><b>*</b></font></td>
						<td><input name="lenddate"  size="20" id="lenddate" readonly="readonly" type="text" value=""
							onFocus="if(value=='dd-mmm-yyyy') {value=''}"
							onBlur="if(value=='') {value='';}">&nbsp;<img id="dateimg1"
							src="images/cal.gif" align="middle" style="vertical-align: middle; cursor: pointer;"
							onClick="javascript:NewCssCal('lenddate', 'ddmmmyyyy')" /></td>
					</tr>
					<tr>
						<td>Frequncy</td>
						<td>
						<select name="frequency" id="frequency" style="width: 140px">
												    <option value="0" selected="selected">select</option>
												     <option value="M">Monthly</option>
												     <option value="Q">Quarterly</option>
												     <option value="H">Half Yearly</option>
												     <option value="Y">Yearly</option>
												</select>
						</td>
						<td>Consider Holiday</td>
						<td><select name="holiday" id="holiday" style="width: 140px">
								<option selected="selected" value="0">Select</option>
								<option value="Y">Yes</option>
								<option value="N"> NO</option>
								
							</select></td>
					</tr>
					<tr>
						<td>Credit Limit</td>
						<td><input  name="crlimit" type="text" id="crlimit" maxlength="4" 
						onkeypress="return inputLimiterForFloat(event,'Numbers')"/></td>
						<td>Minimum Limit</td>
						<td><input type="text"  name="minlimit" id="minlimit"
						onkeypress="return inputLimiterForFloat(event,'Numbers')" maxlength="4"/></td>
					</tr>
					<tr>
						<td>Maximum Cumulative limit</td>
						<td><input  name="maxcultv" type="text" id="maxcultv" maxlength="4"
						onkeypress="return inputLimiterForFloat(event,'Numbers')"/></td>
						<td>Maximum Carry Forward </td>
						<td><input type="text"  name="maxcf" id="maxcf" maxlength="4"
						onkeypress="return inputLimiterForFloat(event,'Numbers')"/></td>
					</tr>
					<tr>
						<td>Leave Encash</td>
						<td><select name="leaveincash" id="leaveincash" style="width: 140px">
								<option selected="selected" value="0">Select</option>
								<option value="Y">Yes</option>
								<option value="N"> NO</option>
								
							</select></td>
							<td>Consider Weekly Off</td>
						<td><select name="weekoff" id="weekoff" style="width: 140px">
								<option selected="selected" value="0">Select</option>
								<option value="Y">Yes</option>
								<option value="N"> NO</option>
								
							</select></td>
					</tr>
					<tr>
						<td colspan="4" align="center"><!-- <input type="button"  id="check" value="Check" onclick="checkEntry()" /> --> 
						<input type="submit"id="checksubmit"  value="Submit"  />
						<input type="button"  id="changedata" value="ChangeData" onclick="ChangeData()" disabled="disabled" />
						<input type="reset" value="Cancel" onclick="ChangeData()" /></td>
					</tr>
				</table>

			  </td>
			</tr>
</table>
<input type="hidden" name="flag" id="flag" value="<%=flag%>">
</form>	</div><%} }%>
			
			               <br>
			
			
			      <table id="customers">
				       <tr class="alt">
					      <td>
						      <table>
							  <tr> 
							   <th width="50">SR NO</th>
							  <th width="87">Effective Date</th>
							  <th width="50">Leave Code</th>
							  <th width="50">frequency</th>
							  <th>Credit Limit</th>
							  <th width="60">Max Cumulative</th>
							  <th width="60">Max Carry Forward</th>
							  <th width="60">Minimum Limit</th>
							  <th>Begin Date</th>
							  <th width="60">End Date</th>
							  <th width="52">Consider Holidays</th>
							  <th width="84">Leave Encash</th>
							  <th width="50">Consider WeekOff</th>
							  <th>Leave Description</th>
							   <th width="51">Edit</th>
							  </tr> 
							  </table>
						  </td>
					   </tr>
				     <tr>
					    <td>
						 <div style="height:90px; overflow-y: scroll; width:1200px;" id="div1" >
						  
						   <table>
						    <%
						    int count=1; 
						if(ltype.size()!=0)
						{
							
							LookupHandler lh=new  LookupHandler(); 
						     for(LeaveMassBean bean:ltype)
						     {
						        String key =bean.getEFFDATE()+":"+bean.getSRNO();
						      String FRQ=bean.getFREQUENCY();
						     
						      if(FRQ.equalsIgnoreCase("Y"))
						      {
						    	  FRQ="Yearly";
						      }
						      else if(FRQ.equalsIgnoreCase("M"))
						      {
						    	  FRQ="Monthly";
						      }
						      else if(FRQ.equalsIgnoreCase("H"))
						      {
						    	  FRQ="Half Yearly";
						      }
						      else if(FRQ.equalsIgnoreCase("Q"))
						      {
						    	  FRQ="Quarterly";
						      }
						        
						        %>
						      <tr class="row" align="center">
						      <%String beginDate=bean.getFBEGINDATE().substring(7,11);
						      System.out.println("begin date is"+beginDate);
						      String yearOfServerDate=empAttendanceHandler.getServerDate().substring(7,11);
								System.out.println("TODAY'S DATE"+yearOfServerDate);
						      %>
						      	<td width="50"><%=count++ %></td>
							     <td width="87"><%=bean.getEFFDATE() %></td>
							     <td width="50"><%=lh.getLKP_Desc("LEAVE",bean.getLEAVECD()) %></td>
							     <td width="63"><%=FRQ %></td>
							     <td width="76"><%=bean.getCRLIM() %></td>
							     <td width="75"><%=bean.getMAXCUMLIM() %></td>
							     <td width="60"><%=bean.getMAXCF() %></td>
							     <td width="60"><%=bean.getMINLIM() %></td>
							     <td width="65"><%=bean.getFBEGINDATE() %></td>
							     <td width="0"><%=bean.getFENDDATE()%></td>
							     <td width="52"><%=bean.getCONS_HOLIDAYS() %></td>
							     <td width="84"><%=bean.getLEAVEINCASH() %></td>
							     <td width="50"><%=bean.getWEEKOFF()%></td>
							     <td width="90"><%=bean.getLEAVEDES() %></td>
							     <%if(beginDate.equals(yearOfServerDate)){ %>
							     <td width="51"><input type="button" class="myButton" value="Edit" onClick="editLtype('<%=key%>')"/></td>
							     <% }else{
							     %>
							     <td width="51"></td>
							     <% }%>
							  </tr>
							  <%
						      }
						}
					   else
						{  
								%>
							 <tr>
							     <td colspan="12"> No Records Found</td>
							  </tr>
							<%} %>
								
						   </table>
						   </div>
						</td>
					 
					 </tr>
				  <tr><th>&nbsp;</th></tr>
				  </table>
				  <br>
			
			
			<!-- <div style="width: 300px;height: 100px;margin-left:25%;margin-top: 5px;float:left;border-radius:15px;border-width : 1px;border-style:solid;">	
				<form action="Leave_Auto.jsp" method="post">
					<h2>Leave Auto Credit</h2> 
					<br/>
					<input type="submit" value="Credit Leaves">
					
				</form>
			</div>
			<div style="width: 300px;height: 100px;margin-left:10px;margin-top: 5px;float:left;border-radius:15px;border-width : 1px;border-style:solid;">	
				<form action="Leave_End_Year.jsp" method="post">
					<h2>Leave Year Ending</h2> 
					<br/>
					<input type="submit" value="End Leave Year">
					
				</form>
			</div> -->
			
			
			<input type="hidden" name="flag" id="flag" value="<%=flag%>">
			   <input type="hidden" name="flag1" id="flag1" value="<%=flag1%>">
			
			
				<table align="center">
				<tr>
				<td>
				<center>
					<div style="width: 300px;height: 100px;margin-left:-5%;margin-top: 5px;float:left;border-radius:15px;border-width : 1px;border-style:solid;">
				<form action="LMS?action=openyear" method="post">
					<h2>Leave Auto Credit</h2> 
					<br/>
					<input type="submit" class="myButton"  value="Credit Leaves">
					
				</form>
			
				</div>
				</center>
					</td>
					
					<%
					if(d2.after(d1)&&(d2.before(d3)))
					{ %>
				<td>
				<center>
					<div style="width: 300px;height: 100px;margin-left:39%;margin-top: 5px;float:left;border-radius:15px;border-width : 1px;border-style:solid;">
				 <form action="LMS?action=endyear" method="post">
					<h2>Leave Year End</h2> 
					<br/>
					<input type="submit" class="myButton"  value="End Leave Year">
					
				</form>
				
					</div>
					</center>
					</td>
					<%} %>
				</tr>
				</table>
			
			</div>
			<!--  end table-content  -->
	
			<div class="clear"></div>
		 
		</div>
		<!--  end content-table-inner ............................................END  -->
		</td>
		<td id="tbl-border-right"></td>
	</tr>
	
	</table>
	
</div>
<!--  end content -->

</div>
<!--  end content-outer........................................................END -->

</body>
</html>