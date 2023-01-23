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
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />

<link rel="stylesheet"href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
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
.form-popup1 {
	
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
		 	url="ExprInterviewServlet?action=DeletePersonal&Exprid="+id;
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
							 alert("Record Deleted Successfully!!!"+ window.location.replace("ExprPersonalDetails.jsp"))
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
  
  function hideF()
  {
	  debugger;
	  document.getElementById("addPersonal").style.display = "none";
  }
  function openForm3() {
		debugger;
		var q=document.getElementById("pname").value;
		var r=document.getElementById("txtDate").value;
		
		var s=document.getElementById("adrs1").value;
		var t=document.getElementById("adrs2").value;
		var u=document.getElementById("mail").value;
		var v=document.getElementById("mobile").value;
		var w=document.getElementById("marriage").value;
		var x=document.getElementById("passport").value;
		var y=document.getElementById("txtDate5").value;
		var z=document.getElementById("txtDate1").value;
		var o=document.getElementById("hob").value;
		
		
		 try
			{
			
			var xmlhttp=new XMLHttpRequest();
		 	var url="";
		 	var response="";                   
		 	url="ExprInterviewServlet?action=AddInfo&pname="+q+"&dob="+r+"&adrs1="+s+"&adrs2="+t+"&mail="+u+"&mobile="+v+"&marriage="+w+"&passport="+x+"&Pvalid="+y+"&crrDate="+z+"&hob="+o;
		 	  
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
				 
				document.getElementById("myForm").style.display = "block";		
		  
	}

  </script>


<%
	String action = request.getParameter("action") != null ? request.getParameter("action") : "ExprPersonalDetails";
	System.out.println("action is " + action);
%>

</head>
<body style="overflow-y:auto;"> 
<%@include file="mainHeader.jsp"%>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp"%>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer"   >
<!-- start content -->
<div id="content" style="background-color: lightgray; " >

	<!--  start page-heading -->
	<div id="content" align="center"  ><!--  start page-heading -->
			<div id="step-holder" align="center">
			
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no"><a href="FresherPersonalDetails.jsp" style="color: white;">1</a></div>
			<div class="step-dark-left"><a href="FresherPersonalDetails.jsp" >Personal Detail</a></div>
			<div class="step-dark-right">&nbsp;</div>
			
			<div class="step-no-off"><a href="FreshersFamilyDetails.jsp" >2</a></div>
			<div class="step-light-left"><a href="FreshersFamilyDetails.jsp">Family Details</a></div>
			<div class="step-light-right">&nbsp;</div>
			
			<div class="step-no-off"><a href="FresherEducation.jsp" >3</a></div>
			<div class="step-light-left"><a href="FresherEducation.jsp"> Qualification</a></div>
			<div class="step-light-right">&nbsp;</div>
			
			<div class="step-no-off"><a href="FresherReference.jsp" >4</a></div>
			<div class="step-light-left"><a href="FresherReference.jsp">References</a></div>
            <!-- <div class="step-light-right">&nbsp;</div>
            
			<div class="step-no-off"><a href="FreshersAppraisalLetter.jsp" >5</a></div>
			<div class="step-light-left"><a href="FreshersAppraisalLetter.jsp">Interview Appraisal Report</a></div> -->
			<div class="step-light-right">&nbsp;</div>
		
		</div>	
		
		
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
						<th style="width:850px;">Personal Details</th>
					</tr>
				</table>
   		

<%
if(action.equalsIgnoreCase("EditPersonal"))
{
	int exprid = Integer.parseInt(request.getParameter("exprid"));
	String hidediv = request.getParameter("hidediv")!=null?request.getParameter("hidediv"):"";
	ArrayList<ExprInterviewBean1> result1 = new ArrayList<ExprInterviewBean1>();
	ExprInterviewDao rdao1 = new ExprInterviewDao();
	result1=rdao1.EditPersonal(exprid);
	for (ExprInterviewBean1 rs : result1) {
%>		

     <form action="ExprInterviewServlet?action=UpdatePersonal" method="Post">
     <input type="hidden" name="exprid" id="exprid" value="<%=exprid%>">
		<div>
			<table width="861" border="1" bordercolor="#000000" id="customers">
			<tr class="alt">
				<td colspan="8" style="text-align: right;">Current Date : <input type="text" id="txtDate1" value="<%=rs.getCrrDate() %>" name="crrDate" class='datepick' readonly="readonly" style="position:relative; "></td>
					
			</tr>
					<tr class="alt">
					 <td style="text-align: center;">Full Name (Surname First)</td>
					  <td colspan="3"><input type="text" name="pname" value="<%=rs.getPName()%>" id="pname"></td>
					</tr>
					
					<tr class="alt">
					 <td style="text-align: center;">Date of Birth</td>
					  <td><input type="text" id="txtDate" name="dob" value="<%=rs.getDOB() %>" class='datepick' readonly="readonly" style="position:relative; "></td>
					 <td style="text-align: center;">Email ID</td>
					  <td colspan="5"><input type="text" value="<%=rs.getEmail() %>" name="mail" id="mail"></td>
					
					</tr>
					<tr   class="alt">
					<td style="text-align: center;">Marital Status</td>
					  <td ><select id="marriage" name="marriage" value="<%=rs.getMaritalStatus() %>" style="width:165px; ">
					  		
					  		<option>Married</option>
					  		<option>Unmarried</option>
					  		</select>
					  </td>
					 <td style="text-align: center;">Hand phone No.</td>                                              
					  <td colspan="4"><input type="text" id="mobile" value="<%=rs.getMobileNo() %>" name="mobile"></td>
					</tr>
					
					<tr    class="alt">
					 <td style="text-align: center;">Passport No. </td>
					  <td><input type="text" id="passport" value="<%=rs.getPassportNo() %>" name="passport"></td>
					   <td style="text-align: center;">Passport validity </td>
					  <td colspan="4"><input type="text"  value="<%=rs.getPassportValid()%>" id="txtDate5" name="passValid" class='datepick' readonly="readonly" style="position:relative; "></td>
					</tr>
				
					<tr   class="alt" >
					 <td style="text-align: center;">Present Mailing Address with Tel. No.</td>
					 <td ><textarea id="adrs1" name="adrs1"  cols="25" rows="2" value="<%=rs.getAddress1() %>"  placeholder="Fill Address....." ></textarea></td>					
					 <td style="text-align: center;">Permanent Address with Tel No.</td>
					<td colspan="5"><textarea id="adrs2" cols="25" rows="2"  value="<%=rs.getAddress2() %>" name="adrs2" placeholder="Fill Address....."></textarea></td>
					
					</tr>
					
					<tr  class="alt">
					<td style="text-align: center;">Hobbies/Extra Curricular Activities</td>
					<td colspan="5"><textarea id="hob" value="<%=rs.getHobbies() %>" cols="55" rows="2"  name="hob"></textarea></td>
					</tr>
					
					<tr class="alt"><td colspan="4" align="center"><input type="submit" class="myButton"  value="Update"   /> 
				&nbsp; &nbsp;
			<input type="reset" class="myButton"  value="Cancel"/></td></tr>                        
				<tbody class="form_data3" style="background-color: white;">
			  </tbody>
					
		  </table>	
		  
		</div>
		</form>

<%}
	if(hidediv.equalsIgnoreCase("yes"))
	{
	%>
	<div id="addPersonal"  hidden="true" style="width: 100%;">
	<%}
	else{
	%>
	<div id="addPersonal" style="width: 100%;"   >
	<%}}%>


	
   <div id="addPersonal">
     <form action="#" method="Post">
     
		<div id="div1">
			<table width="861" border="1" bordercolor="#000000" id="customers">
			<tr class="alt">
			<input type="hidden" value="F" name="fresher" id="fresher">	
			<!--  readonly="readonly" -->
				<td colspan="8" style="text-align: right;">Current Date : <input type="text" id="txtDate1" name="crrDate" class='datepick' style="position:relative; "></td>
					
			</tr>
					<tr class="alt">
					 <td style="text-align: center;">Full Name (Surname First)</td>
					  <td colspan="3"><input type="text" name="pname" id="pname"></td>
					</tr>
					
					<tr class="alt">
					 <td style="text-align: center;">Date of Birth</td><!-- readonly="readonly" -->
					  <td><input type="text" id="txtDate" name="dob" class='datepick'  style="position:relative; "></td>
					 <td style="text-align: center;">Email ID</td>
					  <td colspan="5"><input type="text" name="mail" id="mail"></td>
					
					</tr>
					<tr   class="alt">
					<td style="text-align: center;">Marital Status</td>
					  <td ><select id="marriage" name="marriage" style="width:165px; ">
					  		<option>Select...</option>
					  		<option>Married</option>
					  		<option>Unmarried</option>
					  		</select>
					  </td>
					 <td style="text-align: center;">Hand phone No.</td>                                              
					  <td colspan="4"><input type="text" id="mobile" name="mobile"></td>
					</tr>
					
					<tr    class="alt">
					 <td style="text-align: center;">Passport No. </td>
					  <td><input type="text" id="passport" name="passport"></td>
					   <td style="text-align: center;">Passport validity </td>
					  <td colspan="4"><input type="text"  id="txtDate5" name="passValid" class='datepick' readonly="readonly" style="position:relative; "></td>
					</tr>
				
					<tr   class="alt" >
					 <td style="text-align: center;">Present Mailing Address with Tel. No.</td>
					 <td ><textarea id="adrs1" name="adrs1"  cols="25" rows="2"  placeholder="Fill Address....." ></textarea></td>					
					 <td style="text-align: center;">Permanent Address with Tel No.</td>
					<td colspan="5"><textarea id="adrs2" cols="25" rows="2"   name="adrs2" placeholder="Fill Address....."></textarea></td>
					
					</tr>
					
					
					
					<tr  class="alt">
					<td style="text-align: center;">Hobbies/Extra Curricular Activities</td>
					<td colspan="5"><textarea id="hob"  cols="55" rows="2"  name="hob"></textarea></td>
					
					</tr>
					
					<tr class="alt"><td colspan="4" align="center"><input type="button" class="myButton"  value="Submit"  onclick="openForm3()" /> 
				&nbsp; &nbsp;
			<input type="reset" class="myButton"  value="Cancel"/></td></tr>                        
				<tbody class="form_data3" style="background-color: white;">
			  </tbody>
					
		  </table>	
		  
		</div>
		<br><br>
<%-- <% if(action.equalsIgnoreCase("AddInfo"))
{	
%> --%>

		<div class="form-popup" id="myForm" align="center">
		<table width="861" border="1" bordercolor="#000000" id="customers">
		<tr class="alt">
		<th></th>
		<th>Applicant Id</th>
		<th>Name</th>
		<th>DOB</th>
		<th>EmailId</th>
		<th>Phone No</th>
		</tr>
		<tr>
        <%
			
			ArrayList<ExprInterviewBean1> result = new ArrayList<ExprInterviewBean1>();
			ExprInterviewDao rdao = new ExprInterviewDao();
			result=rdao.PersonalRec();
			for (ExprInterviewBean1 rs1 : result) {
		%>
		<tr>
						<td width="98" align="center">
						<a	class="btn btn-default btn-xs purple"
						onclick="detect(<%=rs1.getExprid()%>)"><i class="fa fa-trash-o"></i></a><!-- onclick="window.document.location.href='EditRecruit.jsp?rid=<%=rs1.getExprid()%>'" -->
							<a href="#" class="btn btn-default btn-xs purple" onclick="window.document.location.href='ExprPersonalDetails.jsp?action=EditPersonal&hidediv=yes&exprid=<%=rs1.getExprid()%>'" ><i class="fa fa-edit"></i></a></td>
								<!--onclick="window.document.location.href='EditProject.jsp?Pid='"  onclick="RecruitEdit(<%=rs1.getExprid()%>)"-->
						</td>
						<td width="79" align="center"><%=rs1.getExprid()%></td>
						<td width="79" align="center"><%=rs1.getPName()%></td>
						<td width="79" align="center"><%=rs1.getDOB()%></td>
						<td width="79" align="center"><%=rs1.getEmail()%></td>
						<td width="79" align="center"><%=rs1.getMobileNo()%></td>
					
		
		</tr>
		<%
			}
//}
		%>
		</table>
		
		
		
		</div>
		
		</form>
	</div>
		
		
		</div>
			<!--  end table-content  -->
	
			<!-- <div class="clear"></div> -->
		 
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
	<!-- <div class="clear">&nbsp;</div> -->

</div>
<!--  end content -->
<!-- <div class="clear">&nbsp;</div> -->
</div>
<!--  end content-outer........................................................END -->
</div>
<!-- <div class="clear">&nbsp;</div> -->
		

</body>
</html>