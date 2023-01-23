<%@page import="payroll.Core.ReportDAO"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.awt.Desktop.Action"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="payroll.Model.CodeMasterBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="payroll.Model.*"%>
<%@page import="payroll.DAO.*"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Encashment DTS3</title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<script src="js/MONTHPICK/jquery.js"></script>
<script type="text/javascript" src="js/MONTHPICK/jquery-ui.min.js"></script>
<link rel="stylesheet" type="text/css" media="screen" href="css/MONTHPICK/jquery-ui.css">

<script type="text/javascript">
	jQuery(function() {
		$("#EMPNO").autocomplete("list.jsp");
	});
	
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
	
	
	function paycal()
	{
	   
	   if (document.getElementById("date1").value.trim() == "") {
			alert("Please Select Report Date");
			document.getElementById("date1").focus();
			return false;
		} 
	//   alert(document.getElementById("prjCode1").value);
	   if (document.getElementById("prjCode1").value== "") {
			alert("Please Select Branch");
			document.getElementById("prjCode1").focus();
			return false;
		}
	   if(prjCode1 == "select"){		
			if(prjCode1 == "select" ){
				alert("Please Select BRANCH Code !");
				document.getElementById("prjCode1").focus();
		}
	   }  
	   else
	   {			
			    var date = document.getElementById("date1").value;
			    var prjCode1 = document.getElementById("prjCode1").value;
			//    var prjName = document.getElementById("prjCode1").text;
			    var e = document.getElementById("prjCode1");
			    var selBr = e.options[e.selectedIndex].text;
			    
		//	    alert(selBr);
				document.getElementById("process").hidden=false;
		        date="01-Jan-"+date;
				url="LeaveEncashServlet?action=leaveEncashReportListBranchWies&date="+date+"&prjCode1="+prjCode1+"&selbr="+selBr;
				xmlhttp.onreadystatechange=function()
				{
					if(xmlhttp.readyState==4)
					{
						var response=xmlhttp.responseText;
			        	document.getElementById("viewPdf").innerHTML=response;
			        	document.getElementById("process").hidden=true;
			        	document.getElementById("viewPdf").hidden=false;
					}
					
				};
				xmlhttp.open("GET", url, true);
				xmlhttp.send();	 
				
	   }
	}
	// tentetive report...
	function Leavetentative()
	{
	document.getElementById("process1").hidden=false;
	var xmlhttp=new XMLHttpRequest();
 	var url="";
 	var response2="";
	url="LeaveEncashServlet?action=leaveEncashtentetive";
	xmlhttp.onreadystatechange=function()
	{
		if(xmlhttp.readyState==4)
		{
		
		 response2=xmlhttp.responseText;
		document.getElementById("viewPdf1").innerHTML=response2;
    	document.getElementById("process1").hidden=true;
    	document.getElementById("viewPdf1").hidden=false;
		}
		
		};
 
	xmlhttp.open("GET", url, true);
	xmlhttp.send();
	}
	
			
	
	
</script>


<script type="text/javascript" language="javascript">

function ABC(formname, checktoggle)
{
/*   if(checktoggle)	{
	  document.getElementById("encashBT").disabled = false;
	  document.getElementById("uncheckAll").disabled = false;
  }
  else{
	//  document.getElementById("encashBT").disabled = true;
  } */
  var checkboxes = new Array(); 
  checkboxes = document[formname].getElementsByTagName('input');
  for (var i=0; i<checkboxes.length; i++)  {
    if (checkboxes[i].type == 'checkbox')   {
      checkboxes[i].checked = checktoggle;
    }
  }
}
 function enableButton(){	
	
	if(document.getElementById("checklist").checked)
	{
		document.getElementById("encashBT").disabled=false;
	}else{
	//	document.getElementById("encashBT").disabled=true;
	}
}  

</script>

<%

	int empno=0;
	int days=0;
	float amt=0;
	int flag=-1;
	String prjCode = "";
	String action = null;
	LeaveEncashmentHandler  LEH= new LeaveEncashmentHandler();
	ArrayList<LeaveEncashmentBean> projEmpNolist = new ArrayList<LeaveEncashmentBean>();
	ArrayList<Float> list1 = new ArrayList<Float>();
	String date=ReportDAO.getSysDate().substring(7,11);
	int dateInInt=Integer.parseInt(date);
try
{  
	action = request.getParameter("action")==null?"0":request.getParameter("action");
	flag =  Integer.parseInt(request.getParameter("flag1")==null?"0":request.getParameter("flag1"));
	
	prjCode = request.getParameter("PrjCode");	
	date = request.getParameter("date");	
	int year=0;
	if(action.equalsIgnoreCase("getEncashList"))
	{					
		projEmpNolist = LEH.getEmpList(prjCode,date);	
		year=Integer.parseInt(date.substring(7,11));
	}
		 
%>

<script type="text/javascript">

	function getTranDetails() {		
		
		var prjCode = document.getElementById("prjCode").value;
		var date = document.getElementById("date").value;
		date="01-Jan-"+date;
		if(prjCode == "select" || date ==""){		
			if(prjCode == "select" ){
				alert("Please Select BRANCH Code !");
				document.getElementById("prjCode").focus();
			}
			else if(date ==""){
				alert("Please Select Date !");
				document.getElementById("date").focus();
			}
		}
		else{
			window.location.href = "EncashmentList.jsp?action=getEncashList&PrjCode="+prjCode+"&date="+date;	
		}
	}

	function checkFlag() {
		var f = parseInt(document.getElementById("flag1").value);
		if (f == 1) {
			alert("Encah Details Saved Successfully");
		}
		if (f == 2) {
			alert("Error in Saving Encah Details !");
		}
		if (f == 3) {
			alert("Record updated Successfully");
		}
		if (f == 4) {
			alert("Employee Already Exist");
		}	
	}
	
	

	// Form Validation start from here.........
	function validation(){
	//	alert("Form validation start here..");	
				var result = updateCount();
				 function updateCount(){
					  var count  = $("input[type=checkbox]:checked").size();
					  if(count == 0)
					  {
						  alert("Please Select At Least One CheckBox..");
						  return false;
					  }
					  else{
						  return true;
					  }					 
				 }
				 
				 if(result == true){
					var r=  confirm("Are you sure to Encash Leave.");
				 }
				 if(r==true && result== true){
					var p = prompt("Type Yes to Encash the Leave to selected Employee."); 
					 if(p=="yes" || p=="Yes" || p=="YES"){	
						 return true;  // false for testing purpose either set true
					 }
					 else{
						 return false;
					 }
				 }
				 else{
						return false;
					}		
	
		return true;  // false for testing purpose either set true
	}
	
</script>


</head>
<body onLoad="checkFlag()" style="overflow: hidden;">
<%@include file="mainHeader.jsp"%>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp"%>

<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y: scroll; max-height: 78%;">
	<!-- start content -->
	<div id="content">
	<!--  start page-heading -->
	<div id="page-heading">
		<h1>BranchWise Leave Encashment Details</h1>
	</div>
	<!-- end page-heading -->

	<table border="0" width="100%" cellpadding="0" cellspacing="0" id="content-table">
		<tr>
			<th rowspan="3" class="sized">
			<img  src="images/shared/side_shadowleft.jpg" width="20" height="300" alt="" /></th>
			<th class="topleft"></th>
			<td id="tbl-border-top">&nbsp;</td>
			<th class="topright"></th>
			<th rowspan="3" class="sized">
				<img src="images/shared/side_shadowright.jpg" width="20" height="300" alt="" /></th>
		</tr>
		<tr>
			<td id="tbl-border-left"></td>
			<td>
			<!--  start content-table-inner ...................................................................... START -->
			<div id="content-table-inner">
			<!--  start table-content  -->
			<div id="table-content">
			<center>
			
			
			<div id="">
				<center>
				<table id="customers" width="800" align="center" >
				<tr>
		     		<!-- <td>
		     			<input name="date1" id="date1" type="text"  readonly="readonly">&nbsp;
		     			<img  src="images/cal.gif" align="middle"	style="vertical-align: middle; cursor: pointer;"
		     				  onClick="javascript:NewCssCal('date1', 'ddmmmyyyy')" />
		     		</td> -->
		     		<td> Select Year: 
		     		<select name="date1" id="date1" type="text"  readonly="readonly">&nbsp;
		     		<!-- <font color="white" size="3">*</font> -->
		     		
		     	<option value="" selected>Select</option>	
		     			<option value="<%=dateInInt-1%>" ><%=dateInInt-1%></option>
						<option value="<%=dateInInt%>" ><%=dateInInt%></option>
						<option value="<%=dateInInt+1%>"><%=dateInInt+1%></option>
						<option value="<%=dateInInt+2%>"><%=dateInInt+2%></option>



</select>
</td>
	     
		     			<td  align="center">Branch Code :</td>
								<td>
								 <select style="width:300px" name="prjCode1" id="prjCode1" >
										<option value="select" selected>Select</option>
										<!-- // 1000 for all branch -->
										<option value="1000"  title="" >All</option>
										
									</select>
								</td>
		     		<td>
		     			<input type="submit" class="myButton" id="takeReport" name ="takeReport"  onclick="paycal()" value="Get Report"  />
		     		</td>
		     	</tr>	
				</table>
				</center>	
			</div>
			<br>
			<form  name="form3"  action="LeaveEncashServlet?action=encashEmpList" method="post" onsubmit="return validation()">
				<table id="customers" width="800" align="center" id="quar1" style="display: none">
					<tr>
						<th>Encashment Details</th>
					</tr>

					<tr>
						<td width="800" align="center">
						<table style="width: 100% !important" >
							<tr>
							
				<td >Select Year For Encashment:<font color="white" size="3">*</font></td>
					<td>	 
						<select id="date" name="date">
					     <% 	if(action.equalsIgnoreCase("getEncashList"))
{			%>

	     	
	     			<!-- <input name="date" id="date" type="text"  onchange="getTranDetails()" readonly="readonly" >&nbsp; -->
	<!--      			<input name="date" id="date" readonly="readonly" class="date-picker"
	     			onchange="getTranDetails()" placeholder="Click here for Calender"/> -->

		
		
			
		
	<% if(year==dateInInt){
		%>
						<option value="">Select</option>	
						<option value="<%=dateInInt%>" selected><%=dateInInt%></option>
						<option value="<%=dateInInt+1%>"><%=dateInInt+1%></option>
						<option value="<%=dateInInt+2%>"><%=dateInInt+2%></option>
<% }else if(year==(dateInInt+1)){
	%>
	<option value="">Select</option>	

	<option value="<%=dateInInt%>" ><%=dateInInt%></option>
	<option value="<%=dateInInt+1%>" selected><%=dateInInt+1%></option>
	<option value="<%=dateInInt+2%>"><%=dateInInt+2%></option>
<% } else { %>
	<option value="">Select</option>	
	<option value="<%=dateInInt%>" ><%=dateInInt%></option>
	<option value="<%=dateInInt+1%>" ><%=dateInInt+1%></option>	
	<option value="<%=dateInInt+2%>" selected><%=dateInInt+2%></option>


<%} %>

	     		
	    			<% } else {%>
	    			
	    		
						<option value="" selected>Select</option>	
						<option value="<%=dateInInt%>" ><%=dateInInt%></option>
						<option value="<%=dateInInt+1%>"><%=dateInInt+1%></option>
						<option value="<%=dateInInt+2%>"><%=dateInInt+2%></option>
	

<% }%>
</select>
</td>								<td  align="center">Branch Code :</td>
								<td>
								 <select style="width:300px" name="prjCode" id="prjCode" onChange="getTranDetails()">
										<option value="select" selected>Select</option>
										<!-- // 1000 for all branch -->
										<option value="1000" id="allbranch"  >All</option>
										<%
    										ArrayList<EmpOffBean> list= new ArrayList<EmpOffBean>();
											EmpOffHandler ofh = new EmpOffHandler();
    						 				list=ofh.getprojectCode();
    						 				int p;
    						 				if(prjCode==""||prjCode==null){
    						 					p=0;
    						 				}else{
    						 					p= Integer.parseInt(prjCode);
    						 				}
    						 				for(EmpOffBean lkb :list)
    						 				{    						 											
    						 					if (p == lkb.getPrj_srno())  {
    						 			%>
    						 			<option value="<%=lkb.getPrj_srno()%>" title="<%=lkb.getSite_name()%>"  selected><%=lkb.getPrj_name()%></option>  
    						 			<%
    						 			} else {
    						 			%>
    						 			<option value="<%=lkb.getPrj_srno()%>"title="<%=lkb.getSite_name()%>"><%=lkb.getPrj_name()%></option>
    						 			<%}
    						 			}	
    						 			%>
    						 			
									</select>
								</td>																		
						</tr> 
					</table>
					</td>
				</tr>
				<tr>
					<td>
						<table width="900">
						<tr>
							<th width="70">Employee Code</th>
							<th width="171">Employee Name</th>
							<th width="70">Basic</th>
							<th width="70">D.A.</th>
							<th width="70">V.D.A.</th>												
							<th width="100">Amount</th>
							<th width="90">Days</th>
							<th width="100">Encash Date</th>																
						</tr>
						</table>
					</td>
			   </tr>
			<tr>
				<td>
					<div id="scrolling"	style="height: 300px; overflow-y: scroll; max-width: 100%; background-color: #FFFFFF;
					align="center" >
						<table width="900" >
								
					<%
						SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
						int count =0;
						for(LeaveEncashmentBean lb : projEmpNolist ){
							
							count++;	//Number of employee for Encashment
							
							list1 = LeaveEncashmentHandler.getEncashAmtByMonth(lb.getEmpNo(),lb.getDays(),date);
					%>							
							<tr align="center">
								<td width= "20"> 
								 	<input type="checkbox" class="empCheckbox" id="checklist" value="<%=lb.getEmpNo()%>" 
									       name="checklist" onclick="enableButton()" />  
									 <%-- <input type="checkbox" class="empCheckbox" id="checklist" value="<%=lb.getEmpNo()%>" 
									       name="checklist" />  --%>      
									
								</td>
								<td width="40"><%=lb.getEmpCode()%></td>
								<td width="200"><%= lb.getEname()%></td>
					<% 			
							int i=0;
							//System.out.println("Size of list"+list1.size());
							if(list1.size()>0)
							{
								while( i <= list1.size()){
									
								//System.out.println("I am in While loop jsp"+list1.get(3));
					%>
									<td width="70">
										<input type="text"  id="basic_<%=lb.getEmpNo()%>" name="basic_<%=lb.getEmpNo()%>" size="10"  readonly="readonly" value="<%=list1.get(0)%>">
									</td>			
									<td width="70">
										<input type="text"  id="da_<%=lb.getEmpNo()%>" name="da_<%=lb.getEmpNo()%>" size="10"  readonly="readonly" value="<%=list1.get(1)%>">
									</td>			
									<td width="70" >
										<input type="text"  id="vda_<%=lb.getEmpNo()%>" name="vda_<%=lb.getEmpNo()%>" size="10"  readonly="readonly" value="<%=list1.get(2)%>">
									</td>	
									<td width="100">
										<input type="text"  id="amt_<%=lb.getEmpNo()%>" name="amt_<%=lb.getEmpNo()%>" size="10"  readonly="readonly" value="<%=list1.get(3)%>">
									</td>								
					<%
									i++;
									break;
								//	System.out.println(list1.get(i));	
								}
							}
							else{
					%>	
								<td width="70">0</td>			
								<td width="70">0</td>			
								<td width="70">0</td>	
								<td width="100">0</td>	
					<%
							}
					%>		
										
								<td width="90">
									<input type="text"  id="days_<%=lb.getEmpNo()%>" name="days_<%=lb.getEmpNo()%>" size="10"  readonly="readonly" value="<%=lb.getDays()%>">
								</td>							
				                
				                <td width="100">
				                	<input type="text"  id="date_<%=lb.getEmpNo()%>" name="date_<%=lb.getEmpNo()%>" size="10"  readonly="readonly" value="<%="01-Jan-"+year%>">
				                </td>											
							</tr>
					<%	
						}	
						System.out.println("Number of Employee for Encashment"+ count);
					%>
																																
						</table>
					</div>
				</td>
			</tr>
<% 				
		}
catch(Exception e)
{
	e.printStackTrace();
	System.out.println("First Time Loading");
}
%>		
					<tr>
						<td align="center" bgcolor="#FFFFFF">
							<!-- <input type=button id="checkAll" value="Check All" onClick="this.value=check5(this.form.checklist)"> 
							<input type="submit" id="encashBT" name ="encashBT" disabled="disabled" value="Encash" /> -->
							
							 <input type="button" class="myButton" id="checkAll" value="Check All" onclick="ABC('form3',true)" /> 
							 <!-- <input type="button" class="myButton" id="uncheckAll" value="UnCheck All"  onclick="ABC('form3',false)" disabled="disabled" /> -->  
							 <input type="button" class="myButton" id="uncheckAll" value="UnCheck All"  onclick="ABC('form3',false)" />
							  <input type="submit" class="myButton" id="encashBT" name ="encashBT"  value="Encash"/> 
							   <!--  <input type="submit" class="myButton" id="encashBT" name ="encashBT" disabled="disabled" value="Encash" />  -->
								 
							
							
							
						</td>
					</tr>
						
					<tr>
						<td align="left" bgcolor="#1F5FA7">&nbsp;</td>
					</tr>
			</table>
			</form>

			<br>
			<input type="hidden" name="flag1" id="flag1" value="<%=flag%>">
			</center>
		</div>		
		
		<!--  end table-content  -->
		<div class="clear"></div>

		</div> <!--  end content-table-inner ............................................END  -->
		 <div id="viewPdf1"  hidden="true" align="center"></div>  
		 <div id="process1"  style="z-index: 1000;background-color:white;height: 100%;width: 100%;top: 0px;left: 0px;position: fixed;opacity:0.8;" hidden=true; >
				<div align="center" style="padding-top: 20%;">
					<img alt="" src="images/process.gif">
				</div>
		</div>  
		 <!-- To display pdf on brawser -->
		<div id="viewPdf"  hidden="true" align="center"></div>
		<div id="process"  style="z-index: 1000;background-color:white;height: 100%;width: 100%;top: 0px;left: 0px;position: fixed;opacity:0.8;" hidden=true; >
				<div align="center" style="padding-top: 20%;">
					<img alt="" src="images/process.gif">
				</div>
		</div>
		
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