<%@page import="payroll.DAO.RoleDAO"%>
<%@page import="payroll.DAO.EmployeeHandler"%>
<%@page import="payroll.Model.EmployeeBean"%>
<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="payroll.Model.AdvanceBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="payroll.DAO.AdvanceHandler"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sanction Advance</title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<script type="text/javascript">
jQuery(function() {
	$("#EMPNO").autocomplete("list.jsp");
});  
</script>

<script>
jQuery(function() {
	$("#EMPNO1").autocomplete("list.jsp");
});
</script>
<!-- <style type="text/css"> 
.ac_results {
	padding: 0px;
	border: 1px solid #84a10b;
	background-color: #84a10b;
	overflow: hidden;
}

.ac_results ul {
	width: 100%;
	list-style-position: outside;
	list-style: none;
	padding: 0;
	margin: 0;
}

.ac_results li {
	margin: 0px;
	padding: 2px 5px;
	cursor: default;
	display: block;
	color: #fff;
	font-family: verdana;
	cursor: pointer;
	font-size: 12px;
	line-height: 16px;
	overflow: hidden;
}

.ac_loading {
	background: white url('../images/indicator.gif') right center no-repeat;
}

.ac_odd {
	background-color: #84a10b;
	color: #ffffff;
}

.ac_over {
	background-color: #5a6b13;
	color: #ffffff;
}

.input_text {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	border: 1px solid #84a10b;
	padding: 2px;
	width: 150px;
	color: #000;
	background: white url(../images/search.png) no-repeat 3px 2px;
	padding-left: 17px;
}

#accordion {
	list-style: none;
	padding: 0 0 0 0;
	width: 170px;
}

#accordion li {
	display: block;
	background-color: #2c6da0;
	font-weight: bold;
	margin: 1px;
	cursor: pointer;
	padding: 35 35 35 7px;
	list-style: circle;
	-moz-border-radius: 10px;
	-webkit-border-radius: 10px;
	border-radius: 10px;
	height: 30px;
	text-decoration: none;
}

#accordion li:hover {
	background-color: #74ACFF;
}

#accordion ul {
	list-style: none;
	padding: 0 0 0 0;
	display: none;
}

#accordion ul li {
	font-weight: normal;
	cursor: auto;
	padding: 0 0 0 7px;
}

#accordion a {
	text-decoration: none;
	color: #FFFFFF;
}

#accordion a:hover {
	text-decoration: underline;
	text-shadow: #FF9927;
}

#accordion ul li.submenu {
	background-color: #66CCFF;
}

#accordion ul li.submenu:hover {
	background-color: #74ACFF;
}
</style> -->
<script language="javascript">


function inputLimiter(e,allow) {
	  var AllowableCharacters = '';
	  if (allow == 'Numbers'){AllowableCharacters='1234567890';}
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

function validate() {
    
	var type = document.getElementById("type").value;

	var fromDate = document.searchappl.frmdate.value;

	var toDate = document.searchappl.todate.value;

	fromDate = fromDate.replace(/-/g, "/");
	toDate = toDate.replace(/-/g, "/");
	var str1=document.searchappl.EMPNO.value;
	//var str2=document.searchappl.EMPNO1.value;

	var num1 =str1.split(":");

	//var num2 =str2.split(":");

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
		alert("Invalid Date Range!\n\n From Date can't be greater than TO Date!");
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
	
	if(type==0){
		alert("please Select request type");
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
		
 
		function Sanction(action,empno,appno,amt,id,eno)
		{
	 		 var amount= (document.getElementById(id).value);			

	 		 
			 if(amount=="")
				
			amount=parseInt(amt);
			 
        
	 		 var flag = confirm("Are you Sure to Sanction "+amount+"..? or You Want to enter new sanction amount..?");	 
			if(flag)
			{
				
		 		if(amount > parseInt(amt))
				{
				alert("Sanction amount should not be greater than Requested Amount");
				}
				
					 
		 		else{
					 	
					 									
				url="AdvanceServlet?action="+action+"&empno="+empno+"&appNo="+appno+"&amt="+amount+"&ENO="+eno;
				
				xmlhttp.onreadystatechange=function()
				{
					if(xmlhttp.readyState==4)
					{
						var response = xmlhttp.responseText;
						if(response=="true")
						{
							alert("Advance Sanctioned Successfully.");
							window.location.href="sanctionAdvance.jsp";
						}
						else
						{
							alert("failed, try Again!");
						};
					};
				};
				 }
				xmlhttp.open("POST", url, true);
				xmlhttp.send();
			};
		}
		
		 function Cancel(action,empno,appno,amt,eno)
			{
			var flag = confirm("Are you Sure to cancel this "+amt+"......?");
			if(flag)
			{
				url="AdvanceServlet?action="+action+"&empno="+empno+"&appNo="+appno+"&ENO="+eno;
				
				xmlhttp.onreadystatechange=function()
				{
					if(xmlhttp.readyState==4)
					{
						var response = xmlhttp.responseText;
						if(response=="true")
						{
							
							alert("Advance cancelled Successfully.");
							window.location.href="sanctionAdvance.jsp";
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
		 function admin(){
			 alert("You dont have the Authority to sanction..!");
		 }
</script>
</head>

<%

RoleDAO obj1=new RoleDAO();
String roleId=obj1.getrole(Integer.parseInt(session.getAttribute("UID").toString()));
AdvanceHandler advanceHandler = new AdvanceHandler();
AdvanceBean advanceBean = new AdvanceBean();
LookupHandler lookuph= new LookupHandler();
ArrayList<AdvanceBean> getSearchList = new ArrayList<AdvanceBean>();
AdvanceBean advanceBeanSearchFilter = new AdvanceBean();
String action = request.getParameter("action")==null?"NA":request.getParameter("action");
String error = request.getParameter("error")==null?"":request.getParameter("error");
int eno = (Integer)session.getAttribute("EMPNO");
if(action.equalsIgnoreCase("NA")){
	advanceBean.setRequestStatus("PENDING");
session.setAttribute("advanceSearchFilter", advanceBean);
	getSearchList = (ArrayList<AdvanceBean>) advanceHandler.getAdvanceAppList(advanceBean,"ALL");
}else{
	getSearchList = (ArrayList<AdvanceBean>) request.getAttribute("customSearchList");
	advanceBeanSearchFilter = (AdvanceBean)session.getAttribute("advanceSearchFilter");
}
%>
<body>
<%@include file="mainHeader.jsp"%>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp"%>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" >
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Advance Details</h1>
	</div>
	<!-- end page-heading -->

	<table border="0" width="105%" cellpadding="0" cellspacing="0" id="content-table">
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
			<div id="table-content" align="center" >
			
								<form name="searchappl" action="AdvanceServlet?action=customSearch"
										method="post" onSubmit="return validate()">
										<table id="customers" style="margin-top:10px;float: none;">
											<tr>
												<th colspan="4">Search Advance Application</th>
											</tr>
											<tr class="alt">
												<td>FROM DATE&nbsp; <font color="red"><b>*</b></font></td>
												<td><input name="frmdate" size="20" id="frmdate"
													type="text" onBlur="if(value=='')" readonly="readonly"> &nbsp;<img
													src="images/cal.gif" align="middle" value=<%=advanceBeanSearchFilter.getRequestDate() %>
													style="vertical-align: middle; cursor: pointer;"
													onClick="javascript:NewCssCal('frmdate', 'ddmmmyyyy')" />
												</td>
												<td>TO DATE&nbsp; <font color="red"><b>*</b></font></td>
												<td><input name="todate" size="20" id="todate"
													type="text" onBlur="if(value=='')" readonly="readonly">&nbsp;<img
													src="images/cal.gif" align="middle" value=<%=advanceBeanSearchFilter.getRequestDate()%>
													style="vertical-align: middle; cursor: pointer;"
													onClick="javascript:NewCssCal('todate', 'ddmmmyyyy')" /></td>
											</tr>
								<tr class="alt">
						 	<td>SELECT EMPLOYEE  NAME OR ID &nbsp; <font color="red"><b>*</b></font>	</td>
							<td ><input type="text" name="EMPNO" size="40" id="EMPNO" onclick="showHide()" title="Enter Employee Name" ></td>
							<td>TYPE&nbsp; <font color="red"><b>*</b></font></td>
										<td colspan="3"><select name="type" id="type">
														<option value="0">Select</option>
														<option value="All">All</option>
														<option value="sanction">sanction</option>
														<option value="pending">pending</option>
														<option value="cancel">cancel</option>
												</select></td>
												</tr>
												 <tr class="alt"><td style="text-align: center;" colspan="4">
												 &nbsp;&nbsp;&nbsp;&nbsp; <input type="submit"	value="Search" /></td>
												
											
	</tr> 			
											<%-- <tr class="alt">
												<td>FROM EMPNO&nbsp; <font color="red"><b>*</b></font>	</td>
												<td><input type="text" name="EMPNO" id="EMPNO" value="<%=advanceBeanSearchFilter.getEmpNo() %>"
													 title="Enter Employee No"></td>
												<td>TO EMPNO&nbsp; <font color="red"><b>*</b></font></td>
												<td><input type="text" name="EMPNO1" id="EMPNO1" value="<%=advanceBeanSearchFilter.getEmpNo2() %>"
													 title="Enter Employee No"></td>
											</tr>
											<tr class="alt">
												<td>DETAILS&nbsp; <font color="red"><b>*</b></font></td>
												<td colspan="3"><select name="type">
														<option value="Default">Select</option>
														<option value="All">All</option>
														<option value="sanction">sanction</option>
														<option value="pending">pending</option>
														<option value="cancel">cancel</option>
												</select> &nbsp;&nbsp;&nbsp;&nbsp; <input type="submit"	value="Search" /></td>
												
											</tr> --%>
										</table>
									</form>
								
								
								<br/>
								<h3 style="color: red;"><%=error %></h3>
								<h3>Advance Applications</h3>
										
										
										
										<%if(getSearchList.size()<=8)
										{
										%>
										<div align="left" class="imptable" style="overflow:hidden; width: 100%;">
										<table  style="margin-right: 0px">
											<tr align="center" bgcolor="#2f747e">
													
														<td width="75"  style="color: white;">EMP CODE</td>
														<td width="200" style="color: white;">EMP NAME</td>
														<td width="100" style="color: white;">REQUEST DATE</td>
														<td width="100" style="color: white;">FOR THE MONTH</td>
														<td width="120" style="color: white;">REQUESTED AMOUNT</td>
														<!-- <td width="130" style="color: white;"> SALARY PAYABLE</td> -->
														<td width="120" style="color: white;">SANCTION AMOUNT</td>
														<td width="90"  style="color: white;">SANCTION DATE </td>
      												    <td width="130" style="color: white;">POSTING DATE</td> 
													
														<td width="155" style="color: white;">SANCTION / CANCEL</td>
														<td width="75"  style="color: white;">VOUCHER NO.</td>											</tr>
										</table>
										<% }else
										{%>
										<div align="left" class="imptable" style="overflow:hidden; width: 100%;">
										<table  style="margin-right: 0px">
											<tr align="center" bgcolor="#2f747e">
													
														<td width="75"  style="color: white;">EMP CODE</td>
														<td width="200" style="color: white;">EMP NAME</td>
														<td width="100" style="color: white;">REQUEST DATE</td>
														<td width="100" style="color: white;">FOR THE MONTH</td>
														<td width="120" style="color: white;">REQUESTED AMOUNT</td>
													<!-- 	<td width="125" style="color: white;"> SALARY PAYABLE</td> -->
														<td width="120" style="color: white;">SANCTION AMOUNT</td>
														<td width="90"  style="color: white;">SANCTION DATE </td>
      												    <td width="130" style="color: white;">POSTING DATE</td> 
													
														<td width="155" style="color: white;">SANCTION / CANCEL</td>
															
														<td width="75"  style="color: white;">VOUCHER NO.</td>
														</tr>
										</table>
										
										<%} %>	</div>
										
											<%		EmployeeHandler emph = new EmployeeHandler();
													EmployeeBean ebean = new EmployeeBean();
													int i=0;
															if (getSearchList.size() != 0) {
															%>				
																<div align="left"  class="imptable" style="overflow:auto; height:300px; width: 100%;">
																<table >
																
																<%	for (AdvanceBean sancb : getSearchList) {
																		
																		ebean = emph.getEmployeeInformation(Integer.toString(sancb.getEmpNo()));
														

															    		String mon=sancb.getForMonth() ;
															    		String monthyear="";
															    		String year=mon.substring(0,4);
															    		String month=mon.substring(5,7);
															    		if(month.equals("01"))
															    			month="Jan";
															    		else if(month.equals("02"))
															    			month="Feb";
															    		else if(month.equals("03"))
															    			month="Mar";
															    		else if(month.equals("04"))
															    			month="Apr";
															    		else if(month.equals("05"))
															    			month="May";
															    		else if(month.equals("06"))
															    			month="Jun";
															    		else if(month.equals("07"))
															    			month="Jul";
															    		else if(month.equals("08"))
															    			month="Aug";
															    		else if(month.equals("09"))
															    			month="Sep";
															    		else if(month.equals("10"))
															    			month="Oct";
															    		else if(month.equals("11"))
															    			month="Nov";
															    		else
															         		month="Dec";
															    		
															    	monthyear=month+"-"+year;
															    
														%>
											<tr align="center">
											
														<td width="75"><%=ebean.getEMPCODE()%></td>
															<td width="200" ><%=lookuph.getLKP_Desc("SALUTE", ebean.getSALUTE())+" "+ ebean.getFNAME()+" "+ebean.getLNAME() %></td>
															<td width="100"><%=sancb.getRequestDate()%></td>
															<td width ="100"><%=monthyear%></td>
															<td width="120"><%=sancb.getAdvanceAmtRequested() %></td>
															
											<%-- 		<td width="130" > <%=sancb.getPayable() %></td> --%>
																
																<%
															if(sancb.getRequestStatus().equalsIgnoreCase("PENDING")){ 
															%>
																<td width="120" ><input type="text"   size="12" id="<%=++i%>"  placeholder="Enter Amount" style="text-align: right; background-color:#7fe5ff;" name="sanctionAmount" onkeypress="return inputLimiter(event,'Numbers')" /></td>
															<%}
															else if(sancb.getRequestStatus().equalsIgnoreCase("CANCEL")){%>
															
															<td width="120"> <font color="red"> <%=sancb.getSanctionAmt()%> </font></td>
															
															<%}
															else {%>
															
															<td width="120"> <%=sancb.getSanctionAmt()%></td>
															
															<%}%>
															
															
															<%
															if(sancb.getRequestStatus().equalsIgnoreCase("PENDING")){ 
															%>
																<td width="90">YET TO SANCTION</td>
															<%}
															else{%>
															
															<td width="90"><%=sancb.getSanctionDate()%></td>
															
															<%}%>
														
															<td width="130"></td>
															
														
															<td width="155">
															<%
															if(sancb.getRequestStatus().equalsIgnoreCase("PENDING")){ 
																if(roleId.equals("1")){
																		%>
															   		<input   type="button" value="Sanction" onclick="Sanction('sanctionAdvanceApp','<%=sancb.getEmpNo()%>','<%=sancb.getApplNo()%>','<%=sancb.getAdvanceAmtRequested() %>','<%=i%>','<%=eno %>')"/>&nbsp;
														   		   	<input  type="button" value="Cancel" onclick="Cancel('cancelAdvanceApp','<%=sancb.getEmpNo()%>','<%=sancb.getApplNo()%>','<%=sancb.getAdvanceAmtRequested() %>','<%=eno%>')"/>
														  		 <%}else
																	{
																	%>
																	<input type="button" value="Sanction" onclick="admin()">
																	<input type="button" value="Cancel" onclick="admin()">
																	<%}}
															else{
																%>
																		<input style="height: 30px; border: none; text-align: center ;"  type="text" readonly="readonly" value= "<%= sancb.getRequestStatus() %>">
													    				</td>
													    	<%}%>
													    	
																<td width=75><%=sancb.getVoucher_No() %></td>
																</tr>
												
																<%}%>
															
															</table>
														</div>
															<%}else { %>
															<div align="left"  class="imptable" style="overflow:auto; height:300px; width: 100%;">
															<table style="width: 100%;">											
													<tr><td  align="center"  style="width: 100%;"><font color="red">No Applications To Display </font></td></tr>
											
												</table>
														</div>
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