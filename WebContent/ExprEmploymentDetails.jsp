<%@page import="javax.mail.SendFailedException"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager" %>
<%@page import="java.sql.PreparedStatement" %>
<%@page import="java.sql.SQLException" %>
<%@page import="java.sql.Statement" %>
<%@page import="java.sql.ResultSet" %>
<%@page import="payroll.DAO.*" %>
<%@page import="payroll.Model.*" %>

<%@page import="payroll.Model.Lookup"%>
<%@page import="payroll.DAO.LookupHandler"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="payroll.Model.EmpQualBean"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy DTS3 </title>
<link rel="stylesheet"href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />

<script src="js/jquery/jquery.bind.js" type="text/javascript"></script>
<!--for datepicker  -->
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<style type="text/css">

.form-popup {
	
	display: none;
	position: relative;
	}
.form-popup2 {
	
	display: none;
	position: relative;
	}
</style>

<script> 
  $(document).ready(function() {
	    $("#txtDate,#txtDate1,#txtDate2,#txtDate3,#txtDate4,#txtDate5").datepicker({
	    	changeMonth: true, 
	        changeYear: true, 
	        yearRange: "-90:+00",
	    	showOn: 'button',
	        buttonText: 'Show Date',
	        /* buttonImageOnly: true, */
	        buttonImage: 'images/cal.gif',
	        dateFormat: "yy-mm-dd" 	
	    });
	});
  function detect(id){
	  debugger;
		 try
		 {
			var xmlhttp=new XMLHttpRequest();
		 	var url="";
		 	var response="";
		 	url="ExprInterviewServlet?action=DeleteEmploy&employid="+id;
		 	let s=confirm("do you want to delete this record?");/*+ window.location.replace("ProjectViewAll.jsp")  */
			 if(s)
				 {
				 	xmlhttp.onreadystatechange=function()
					{
						if(xmlhttp.readyState==4)
						{	
						response = xmlhttp.responseText;
						
						 if(response>0 )
							{
							 alert("Record Deleted Successfully!!!"+ window.location.replace("ExprEmploymentDetails.jsp"))
							 return false;
							}
						 else
						 {
								alert("Record Does not Deleted........Some Error Occure");	
						 }
						}
					};
					xmlhttp.open("POST", url, true);
					xmlhttp.send();
				 }
			}
			catch(e)
			{
				
			}
	}

  var html1="";
  function openForm1() {
	  
	  debugger;
	  var q=document.getElementById("OrgName").value;
		var r=document.getElementById("txtDate").value;
		
		var s=document.getElementById("txtDate1").value;
		var t=document.getElementById("StartPos").value;
		var u=document.getElementById("LeavPos").value;
		var v=document.getElementById("LeavReas").value;
		var w=document.getElementById("JobDes").value;
		var x=document.getElementById("SalCTC").value;
		
		
		
		 try
			{
			
			var xmlhttp=new XMLHttpRequest();
		 	var url="";
		 	var response="";                   
		 	url="ExprInterviewServlet?action=AddEmployRec&OrgName="+q+"&From="+r+"&To="+s+"&StartPos="+t+"&LeavPos="+u+"&LeavReas="+v+"&JobDes="+w+"&SalCTC="+x;
		 	  
				 	xmlhttp.onreadystatechange=function()
					{
						if(xmlhttp.readyState==4)
						{	
						response = xmlhttp.responseText;
						
						 if(response=="true" )
							{
							 alert(" Successfully save record!!!")
							$( "#div1" ).load( "ExprPersonalDetails.jsp #div1" );
							 return false;
							}
						 else
						 {
								alert("fail...");	
						 }
						}
				};
				xmlhttp.open("POST", url, true);
				xmlhttp.send();
				 
				}
				catch(e){} 	

  	  document.getElementById("myForm2").style.display = "block";
  	}
</script>
<%
	String action = request.getParameter("action") != null ? request.getParameter("action") : "ExprEmploymentDetails";
	System.out.println("action is " + action);
%>
</head>
<body > 
<%@include file="mainHeader.jsp"%>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp"%>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:auto;height:80%;"  >
<!-- start content -->
<div id="content" ><!-- style="background-color: lightgray; "  -->

	<!--  start page-heading -->
<div id="content" align="center"  ><!--  start page-heading -->
			<div id="step-holder" align="center">
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off"><a href="ExprPersonalDetails.jsp" >1</a></div>
			<div class="step-light-left"><a href="ExprPersonalDetails.jsp" >PersonalDetail</a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no"><a href="ExprEmploymentDetails.jsp"style="color: white;" >2</a></div>
			<div class="step-dark-left"><a href="ExprEmploymentDetails.jsp">EMPLOYMENTDetails</a></div>
			<div class="step-dark-right">&nbsp;</div>
			<div class="step-no-off"><a href="ExprFamilyDetails.jsp" >3</a></div>
			<div class="step-light-left"><a href="ExprFamilyDetails.jsp"> FamilyDetails</a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off"><a href="ExprEducation.jsp" >4</a></div>
			<div class="step-light-left"><a href="ExprEducation.jsp">Qualification</a></div>
            <div class="step-light-right">&nbsp;</div>
			<div class="step-no-off"><a href="ExprSalaryDrawn.jsp" >5</a></div>
			<div class="step-light-left"><a href="ExprSalaryDrawn.jsp">LAST DRAWN SALARY</a></div>
            <div class="step-light-right">&nbsp;</div>
            <div class="step-no-off"><a href="ExprReference.jsp" >6</a></div>
			<div class="step-light-left"><a href="ExprReference.jsp">REFERENCES</a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off"><a href="ExprOther.jsp" >7</a></div>
			<div class="step-light-left"><a href="ExprOther.jsp">OtherDetails</a></div>
			<div class="step-light-right">&nbsp;</div>
			
		
		</div>
		</div>
		<!-- <div>
		<div id="page-heading" align="left">
				<h1 >Employment Form</h1>
			</div>
			</div> -->
		
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
			
				<table id="customers">
					<tr class="alt">
						<th style="width:850px;">EMPLOYMENT RECORD</th>
					</tr>
					<tr>
						<td><h3 style="text-align: center;">(Current Employment should be listed first)</h3></td>
					</tr>
				</table>
				
<%
if(action.equalsIgnoreCase("EditEmployment"))
{
	int employid = Integer.parseInt(request.getParameter("employid"));
	String hidediv = request.getParameter("hidediv")!=null?request.getParameter("hidediv"):"";
	ArrayList<ExprInterviewBean1> result1 = new ArrayList<ExprInterviewBean1>();
	ExprInterviewDao rdao1 = new ExprInterviewDao();
	result1=rdao1.EditEmploy(employid);
	for (ExprInterviewBean1 rs : result1) {
%>		
		<form action="ExprInterviewServlet?action=UpdateEmploy" method="Post">
		<input type="hidden" name="employid" id="employid" value="<%=employid%>">
		<div >
			<table width="861" border="1" bordercolor="#000000" id="customers">
				
				<tr class="alt">
				<td>Applicant Id :</td>
				<td> <input type="text" id="Exprid" value="<%=rs.getExprid() %>" name="Aid"></td>
				
				<td>Applicant Name:</td>
				<td colspan="3"><input type="text" id="AName" name="AName"></td>
				</tr>
				<tr class="alt">
				<td>Organization Name:</td>
				<td colspan="3"><input type="text" value="<%=rs.getOrgName() %>" id="OrgName" name="OrgName"></td>
				</tr>
				<tr class="alt">
				<td>From Date</td>
				<td><input type="text" id="txtDate" value="<%=rs.getFromDate() %>" name="From" class='datepick' readonly="readonly" style="position:relative; "></td>
				<td>To Date</td>
				<td><input type="text" id="txtDate1" name="To" value="<%=rs.getToDate() %>" class='datepick' readonly="readonly" style="position:relative; "></td>
				</tr>
				<tr class="alt">
				<td>Position at the time of joining</td>
				<td ><input type="text" id="StartPos" value="<%=rs.getStartPos() %>" name="StartPos"></td>
				<td>Position at the time of leaving</td>
				<td colspan="3"><input type="text" value="<%=rs.getLeavPos() %>" id="LeavPos" name="LeavPos"></td>
				</tr>
				
				<tr class="alt">
				<td>Reasons for leaving</td>
				<td ><textarea cols="25" rows="2"  value="<%=rs.getLeavReas() %>" id="LeavReas" name="LeavReas"></textarea></td>
				<td>Last drawn salary (CTC)</td>
				<td colspan="3"><input type="text" value="<%=rs.getSalCTC() %>" id="SalCTC" name="SalCTC"></td>
				</tr>
				
				<tr class="alt">
				<td>Job Description & Responsibilities <br>in the current job</td>
				<td colspan="5"><textarea cols="53" rows="2" id="JobDes" value="<%=rs.getJobDes() %>" name="JobDes"></textarea></td>
				</tr>
				
				<!-- <tr class="alt">
				<td>Add More Experience</td>
				<td valign="top" colspan="3"><input class="form-control" type="radio" name="moreAdd" id="moreAdd" value="YES" >YES &nbsp;&nbsp;&nbsp;&nbsp;
				<input class="form-control" type="radio" name="moreAdd" id="moreAdd" value="NO"  checked="checked"> NO</td>
				</tr> -->
				
				<tr class="alt">
				<td colspan="4" align="center"><input type="Submit" class="myButton"  value="Update"  /> 
				&nbsp; &nbsp;
				<input type="reset" class="myButton"  value="Cancel"/>
				</td>
				</tr>
		
			</table>
		</div>	
		</form>
<%}
	if(hidediv.equalsIgnoreCase("yes"))
	{
	%>
	<div id="addEmployment"  hidden="true" style="width: 100%;">
	<%}
	else{
	%>
	<div id="addEmployment" style="width: 100%;"   >
<%}}%>  
<div id="addEmployment">
     <form action="#" method="Post">
		<div id="div1">
			<table width="861" border="1" bordercolor="#000000" id="customers">
				
				<tr class="alt">
				<td>Applicant Id :</td>
				<td> <input type="text" id="Exprid" name="Aid"></td>
				
				<td>Applicant Name:</td>
				<td colspan="3"><input type="text" id="AName" name="AName"></td>
				</tr>
				<tr class="alt">
				<td>Organization Name:</td>
				<td colspan="3"><input type="text" id="OrgName" name="OrgName"></td>
				</tr>
				<tr class="alt">
				<td>From Date</td>
				<td><input type="text" id="txtDate" name="From" class='datepick' readonly="readonly" style="position:relative; "></td>
				<td>To Date</td>
				<td><input type="text" id="txtDate1" name="To" class='datepick' readonly="readonly" style="position:relative; "></td>
				</tr>
				<tr class="alt">
				<td>Position at the time of joining</td>
				<td ><input type="text" id="StartPos" name="StartPos"></td>
				<td>Position at the time of leaving</td>
				<td colspan="3"><input type="text" id="LeavPos" name="LeavPos"></td>
				</tr>
				
				<tr class="alt">
				<td>Reasons for leaving</td>
				<td ><textarea cols="25" rows="2" id="LeavReas" name="LeavReas"></textarea></td>
				<td>Last drawn salary (CTC)</td>
				<td colspan="3"><input type="text" id="SalCTC" name="SalCTC"></td>
				</tr>
				
				<tr class="alt">
				<td>Job Description & Responsibilities <br>in the current job</td>
				<td colspan="5"><textarea cols="53" rows="2" id="JobDes" name="JobDes"></textarea></td>
				</tr>
				
				<tr class="alt">
				<td>Add More Experience</td>
				<td valign="top" colspan="3"><input class="form-control" type="radio" name="moreAdd" id="moreAdd" value="YES" >YES &nbsp;&nbsp;&nbsp;&nbsp;
				<input class="form-control" type="radio" name="moreAdd" id="moreAdd" value="NO"  checked="checked"> NO</td>
				</tr>
				
				<tr class="alt">
				<td colspan="4" align="center"><input type="button" class="myButton"  value="Submit"  onclick="openForm1()" /> 
				&nbsp; &nbsp;
				<input type="reset" class="myButton"  value="Cancel"/>
				</td>
				</tr>
		
			</table>
		</div>
		<br><br>
		<div class="form-popup2" id="myForm2" align="center">
				<table class="myTableER"  width="861" border="1" bordercolor="#000000" id="customers" >
							<tr bordercolor="#CCCCCC" align="center"  class="alt">
							 <th></th>
							 <th>RecId</th>
							 <th>Organization</th>
							 <th>From Date</th>
							 <th>To Date</th>
							 <th>Joining Position</th>
							 <th>leaving Position</th>
							 <th>salary (CTC)</th>
							
							 </tr>
							 
							 <tr>
        <%
			
			ArrayList<ExprInterviewBean1> result = new ArrayList<ExprInterviewBean1>();
			ExprInterviewDao rdao = new ExprInterviewDao();
			result=rdao.EmployRec();
			for (ExprInterviewBean1 rs1 : result) {
		%>
		<tr>
						<td width="98" align="center">
						<a	class="btn btn-default btn-xs purple"
						onclick="detect(<%=rs1.getEmployId()%>)"><i class="fa fa-trash-o"></i></a><!-- onclick="window.document.location.href='EditRecruit.jsp?rid=<%=rs1.getEmployId()%>'" -->
							<a href="#" class="btn btn-default btn-xs purple" onclick="window.document.location.href='ExprEmploymentDetails.jsp?action=EditEmployment&hidediv=yes&employid=<%=rs1.getEmployId()%>'" ><i class="fa fa-edit"></i></a></td>
								<!--onclick="window.document.location.href='EditProject.jsp?Pid='"  onclick="RecruitEdit(<%=rs1.getExprid()%>)"-->
						</td>
						<td width="79" align="center"><%=rs1.getEmployId()%></td>
						<td width="79" align="center"><%=rs1.getOrgName()%></td>
						<td width="79" align="center"><%=rs1.getFromDate()%></td>
						<td width="79" align="center"><%=rs1.getToDate()%></td>
						<td width="79" align="center"><%=rs1.getStartPos()%></td>
						<td width="79" align="center"><%=rs1.getLeavPos()%></td>
						<td width="79" align="center"><%=rs1.getSalCTC()%></td>
					
		
		</tr>
		<%
			}
//}
		%>
	
				</table>
				<!-- <table class="myTableER"  width="861" border="1" bordercolor="#000000" id="customers" >
							 <tbody class="form_data2" style="background-color: white;">
							 
							 </tbody>
				</table> -->
			</div>
		
	</form>
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
</div>
<div class="clear">&nbsp;</div>

</body>
</html>