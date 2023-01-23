<%@page import="payroll.DAO.*"%>
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
<script type="text/javascript" src="js/datetimepicker.js"></script>
<%
String action = request.getParameter("action")==null?"":request.getParameter("action");

	
%>

<script type="text/javascript">
function validate()
{
	
		//if(document.getElementById("hdname").value =="")
		if(document.getElementById("hdname").selectedIndex==0)
		{
			alert("Please Select Holiday Name ");
			document.getElementById("hdname").focus();
			return false;
		}
	
		if(document.getElementById("type").selectedIndex==0)
		{
			alert("Please Select Holiday Type ");
			document.getElementById("type").focus();
			return false;
		}
	
	
   		var fromDate=document.holidaymastform.frmdate.value;
   		var toDate=document.holidaymastform.todate.value;
   		fromDate = fromDate.replace(/-/g,"/");
		toDate = toDate.replace(/-/g,"/");
   
		if(document.holidaymastform.frmdate.value=="")
		{
			 alert("please enter the fromdate");
    		 document.holidaymastform.frmdate.focus();
     		return false;
		}
    
   		if( document.holidaymastform.todate.value=="")
    	{
			  alert("please enter the todate");
	      	  document.holidaymastform.todate.focus();
	      	return false;
		}
   
  	 	var d1 = new Date(fromDate);
 		var d2 =new  Date(toDate);
 	
 		if (d1.getTime() > d2.getTime())
    	{
	   		alert("Invalid Date Range!\n fromdate Date cannot greater than TODate!");
	   		document.holidaymastform.todate.focus();
	   		return false;
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
<body style="overflow:hidden"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1> Holiday Master </h1>
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
			 
			
			
			<form  name="holidaymastform" action="HolidayMasterServlet?action=addholdmast" method="post" onSubmit="return validate()">
				<table id="customers" align="center" width="600">
				
				<tr><th colspan="5"> <b>Holiday Master</b></th></tr>
				<tr class="alt">
				<td colspan="1">Holiday Name&nbsp; <font color="red"><b>*</b></font></td>
					<td><select name="hdname" id="hdname">
					<option value="0">Select.....</option>
					<option value="26th Jan">26th Jan</option>
					<option value="1st May ">1st May </option>
					<option value="15th August">15th August </option>
					<option value="Festival">Festival</option>
					<option value="Week OFF">Week OFF</option>
					<option value="other">Other</option>
					<td colspan="3">
				</tr>
				<tr class="alt"> 	
					<td width="130">Holiday Type&nbsp; <font color="red"><b>*</b></font></td>
					<td><select name="type" id="type">
					<option value="0">Select.....</option>
					<option value="National"> National </option>
					<option value="Weekly Off"> Weekly Off </option>
					<option value="Other"> Other </option>
					<%
						ArrayList<Lookup> holdList = new ArrayList<Lookup>();
						LookupHandler hlHoldList = new LookupHandler();
						holdList = hlHoldList.getSubLKP_DESC("HOLD");
						for(Lookup lbn : holdList)
						{
						%>
						<option value="<%=lbn.getLKP_SRNO()%>"><%=lbn.getLKP_DESC()%></option>
						<%
						}
						%>
										
					<!-- <td width="130">Branch</td> --> 
					<%-- <td width="167">
					<select name="branch" id="branch" >
					<option value="All">All</option>  
			    <%
			    ArrayList<BranchBean> result=new ArrayList<BranchBean>();
			    BranchDAO bdao = new BranchDAO();
			   	result = bdao.getBranchDetails();
			    for(BranchBean brbean : result){
			     %>
				      <option value="<%=brbean.getBRNAME()%>"><%=brbean.getBRNAME()%></option>  
				     <%
					}
				     %>
						
					</select>					</td> --%>
				<td width="150">Branch / Project Name</td>
		  <td width="100">
		  
		   <select name="branch" id="branch" >  
      					 <option value="All">All</option>  
    						<%
    						ArrayList<EmpOffBean> list= new ArrayList<EmpOffBean>();
    						EmpOffHandler ofh = new EmpOffHandler();
    						list=ofh.getprojectCode();
    						for(EmpOffBean lkb :list)
 							{
 							%>
      						<option value="<%=lkb.getPrj_srno()%>"> <%=lkb.getPrj_name()%></option>
      						  
     					 	<%
     					 	}
     					 	%>
     			 </select>
		  
		  
		  
		  </td>	
				</tr>
				<tr class="alt">
					<td>Fromdate&nbsp; <font color="red"><b>*</b></font></td>
					<td><input name="frmdate" size="20" id="frmdate" type="text" onBlur="if(value=='')" readonly="readonly"><img src="images/cal.gif" align="absmiddle" style="vertical-align:middle; cursor:pointer;" onClick="javascript:NewCssCal('frmdate', 'ddmmmyyyy')" /></td>
					<td>Todate&nbsp; <font color="red"><b>*</b></font></td>
					<td><input name="todate"  size="20" id="todate" type="text" onBlur="if(value=='')" readonly="readonly"><img src="images/cal.gif" align="absmiddle" style="vertical-align:middle; cursor:pointer;" onClick="javascript:NewCssCal('todate', 'ddmmmyyyy')" /></td></tr>
				</tr>
				<tr class="alt">
		  <td valign="top">Message Text </td>
		  <td align="left" colspan="3">
		  <textarea name="text" id="text" cols="20" >
		  </textarea>
		  
		  </td>
		</tr>
		
				<tr class="alt">
						<td align="left" colspan="5"> Holiday Category : &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						
							<input type="radio" value="National" name="type" title="National Holiday"> National&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" value="Festival" name="type" title="Festival Holiday"> Festival
							<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						
						
							<input type="checkbox" name="annually" value="Yes" title="Holiday Repeat Annually"> Repeat Annually&nbsp;&nbsp;&nbsp;
							<input type="checkbox" name="optional" value="Yes" title="Holiday Is Optional"> Optional Holiday
							<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						
	
						<input type="checkbox" name="day" value="Yes" title="Office Work Half Day"> Half Day&nbsp;&nbsp;&nbsp;
						<input type="checkbox" name="attend" value="Yes" title="Attendence Is Compulsary"> Present Compulsary&nbsp;&nbsp;&nbsp;
						<input type="checkbox" name="sms" value="Yes" title="You Send SMS"> SMS
					</td>
						
						</tr>
						
					<tr class="alt"><td  colspan="10" align="center">
			<input  type="submit" value="Save" class="myButton"> &nbsp; &nbsp; <input  type="button" value="Clear" onClick="window.document.location.href='HolidayMaster.jsp'" class="myButton"><br>
			
						<!-- <tr><th colspan="5">If You Want To See All Holiday List..Please CLICK HERE........
						<input  type="submit" value="Show" onClick="window.document.location.href='ShowHolidayMaster.jsp'"></th></tr> -->
				</table>
				
		
			</form><br><br>
		 		<center>
	
		<b>If You Want To See All Holiday List..Please CLICK HERE	
		<input  type="submit" value="Show" onClick="window.document.location.href='ShowHolidayMaster.jsp'" class="myButton">
	 </b>
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