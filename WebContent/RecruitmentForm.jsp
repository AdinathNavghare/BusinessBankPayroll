<%@page import="java.util.*"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="payroll.DAO.ConnectionManager"%>
<%@page import="payroll.DAO.RecruitmentDAO"%>
<%@page import="payroll.Model.RecruitmentBean"%>
<%@page import=" java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="ISO-8859-1">
<title>&copy DTS3</title>
<link rel="stylesheet" href="css/screen.css" type="text/css"media="screen" title="default" />
<link rel="stylesheet"href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<!--for datepicker  -->
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<style type="text/css">
.form-popup {
	
	display: none;
	position: absolute;
	font-size:10px;
	bottom: 1%;
	right: 12.5%;
	float: right;
	border: 3px solid #f1f1f1;
	z-index: 9;
	width:75%; /* Full width */
  	height: 37%; /* Full height */
  	overflow: hidden; /* Enable scroll if needed */
	background-color: rgb(0,0,0); /* Fallback color */ 
  	background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
}
.form-popup1 {
	
	display: none;
	position: absolute;
	font-size:10px;
	 bottom: 1%; 
	 right: 9.7%;
	float: right;
	border: 3px solid #f1f1f1;
	z-index: 9;
	width:80%; /* Full width */
  	height: 40%; /* Full height */
  	overflow: hidden; /* Enable scroll if needed */
	background-color: rgb(0,0,0); /* Fallback color */ 
  	background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
}
</style>
<script> 
  $(document).ready(function() {
	    $("#RDate").datepicker({
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
  
  function check(ckType)
  {
	  var ckName = document.getElementsByName(ckType.name);
	    var checked = document.getElementById(ckType.id);

	    if (checked.checked) {
	      for(var i=0; i < ckName.length; i++){

	          if(!ckName[i].checked){
	              ckName[i].disabled = true;
	          }else{
	              ckName[i].disabled = false;
	          }
	      } 
	    }
	    else {
	      for(var i=0; i < ckName.length; i++){
	        ckName[i].disabled = false;
	      } 
	    }    
  }
  
  $(document).mouseup(function (e){

		var container = $("#myForm,#myForm1");

		if (!container.is(e.target) && container.has(e.target).length === 0){

			container.fadeOut();
			
		}
	}); 
  
  function detect(id){
	  debugger;
		 try
		 {
			var xmlhttp=new XMLHttpRequest();
		 	var url="";
		 	var response="";
		 	url="RecruitmentServlet?action=Delete&Rid="+id;
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
							 alert("Record Deleted Successfully!!!"+ window.location.replace("RecruitmentForm.jsp"))
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
  
  </script>
  
</head>
<body >
	<%@include file="mainHeader.jsp"%>
	<!--  start nav-outer-repeat................................................................................................. START -->
	<%@include file="subHeader.jsp" %>
	<%-- <%@ include file="ProjectFilterSubHeader.jsp"%>
 --%>

	<!-- start content-outer ........................................................................................................................START -->
	<div id="content-outer" style="overflow-y:auto;height:80%;">
		<!-- start content -->
		<div id="content" >
			<!--  start page-heading -->
			<div id="page-heading" >
				<h2 >Recruitment Form</h2>
			</div>
			<!-- end page-heading -->

			<table border="0" width="100%" cellpadding="0" cellspacing="0" >
	<tr>
		<th rowspan="3" class="sized"><img
			src="images/shared/side_shadowleft.jpg" width="20" height="200"
			alt="" /></th>
		<th class="topleft"></th>
		<td id="tbl-border-top">&nbsp;</td>
		<th class="topright"></th>
		<th rowspan="3" class="sized"><img
			src="images/shared/side_shadowright.jpg" width="20" height="200"
			alt="" /></th>
	</tr>
	<tr>
		<td id="tbl-border-left"></td>
			<td>
						<!--  start content-table-inner ...................................................................... START -->
						<div id="content-table-inner">

							<!--  start table-content  -->
							<div id="table-content">
								<center>
									<form action="RecruitmentServlet?action=RecruitInfo" method="post" onsubmit="return TaskValidation()">
										<table id="customers" align="center">
											<tr class="alt" style="border:solid;border-color:black; ">
													<th colspan="10">Recruitment Form</th>
											</tr>
											<tr  class="alt" style="width: 12px;height:10px;" class="alt">
													<td width="20" colspan="8" style="text-align: right;">
														Date <font  color="red">*</font>:&nbsp;&nbsp; <input type='text' class='datepick' id='RDate'name="RDate" readonly="readonly">
													</td>												
											</tr>
											<tr class="alt">
													<td width="195px">Project Manager Name<font  color="red">*</font> :</td>
													<td><input type="text" id="ProManName" name="ProManName"></td>
													<td width="175px">Job Location<font  color="red">*</font>:</td>
													<td colspan="4"><input type="text" id="JobLoc"name="JobLoc"></td>
											</tr>							
											
											<tr class="alt"><td colspan="10"></td></tr>
											<tr class="alt" style="border:solid;border-color:black; ">
												<th colspan="10">Basic Information About Requirements</th> 
											</tr>
											
											<tr class="alt"><td colspan="10"></td></tr>
											<tr class="alt">
												<td width="195px">Designation<font  color="red">*</font>:</td>
												<td ><input type="text" id="Desig"name="Desig"></td>
												
												<td width="140px">Department<font  color="red">*</font> :</td>
												<td colspan="8"><input type="text" id="Dept"name="Dept"></td>
											</tr>
											
											<tr class="alt">
												<td width="40">No.Of Requirement :</td>
												<td><input type="text" id="ReqNo" name="ReqNo"></td>
													
												<td width="">Type Of Requirement<font  color="red">*</font> :</td>
												<td colspan="8"><select id="ReqType"name="ReqType">
												<option>Fresher</option>
												<option>Experience</option>
												</select>
												</td>
												
											</tr>
											<tr class="alt">
												<td width="40">Education<font  color="red">*</font> :</td>
												<td ><input type="text" id="Education"name="Education"></td>
												
												<td width="">Type of employee :</td>
												<td colspan="8" ><input type="text" id="EmpType"name="EmpType"></td>
														
											</tr>
											<tr class="alt">
												<td width="">Salary range in CTC :</td>
												<td colspan="8">From : <input type="text" style="width:50px;" id="SalFrom" name="SalFrom"> K &nbsp;&nbsp; To : <input type="text" style="width:50px;" id="SalTo" name="SalTo"> K </td>
											</tr>
											
											<tr class="alt">
											<td width="">Bond Requirement:</td>
												<td ><input type="checkbox" id="BondYes"  onclick="check(this)" value="Yes" name="BondYesNo"> Yes &nbsp;&nbsp;
														<input type="checkbox" id="BondNo" onclick="check(this)" value="No" name="BondYesNo"> No											
												</td>
												<td width="">Orignal Documents require :</td>
												<td colspan="8"><input type="checkbox" id="DocYes" onclick="check(this)" value="Yes" name="DocYesNo"> Yes &nbsp;&nbsp;
														<input type="checkbox" id="DocNo" value="No"  onclick="check(this)" name="DocYesNo"> No</td>
											</tr>
											<tr class="alt" style="width: 12px;height:10px;">
												<td width="">Min. Experience :</td>
												<td colspan="8"><input type="text" id="MinExp"name="MinExp"></td>
				  							 </tr>
										
											<tr class="alt" style="width: 12px;height:10px;" class="alt">
												<td width="20">Probation Period<font  color="red">*</font> :</td>
												<td><input type="text" id="ProbPeriod" name="ProbPeriod">Month</td> 
												<td width="">Bond Tenure :</td>
												<td colspan="8"><input type="text" id="BondTenure"name="BondTenure"></td>
				  							 </tr>
										
											<tr ><td colspan="10"></td></tr>
										
											<tr class="alt" style="border:solid;border-color:black; ">
												<th colspan="5">Skills & Knowledge Required </th>
											</tr>
										
											 <tr class="alt" style="width: 12px;height:10px;">							 					  
				      							  <td colspan="5">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<textarea   name="SkillArea" id="SkillArea" cols="90" rows="3" tabindex="101" data-wz-state="256" data-min-length="" placeholder="Skills & Knowledge Required.."></textarea></td>
			  							     </tr>
			  							     <tr class="alt" style="border:solid;border-color:black; ">
												<th colspan="5" >Special  Requirement </th>
											 </tr>
			  							      <tr class="alt" style="width: 12px;height:10px;">
				      							  <td colspan="5" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<textarea   name="SpecialReqArea"  id="SpecialReqArea" cols="90" rows="3" tabindex="101" data-wz-state="256" data-min-length="" placeholder="Special  Requirement.."></textarea></td>
			  							     </tr>
			  							     		   
			  							    <tr class="alt">
												<td colspan="7" align="center">
													<input type="submit" id="btn1"value="Save" class="myButton" style="border:solid;border-color:darkblue; "> &nbsp;&nbsp; &nbsp;
													<input type="reset" id="btn2" value="Close" style="border:solid;border-color:darkblue; "onclick="window.document.location.href='RecruitmentForm.jsp'" class="myButton">&nbsp;&nbsp; &nbsp;
													<input type="button" id="btn3" value="Recruitment List" class="myButton" onclick="RecruitList()" style="border:solid;border-color:darkblue; ">
												</td>
											</tr>
																
										</table>
									</form>
								</center>
							</div>
							<!--  end table-content  -->
							<div class="clear"></div>
						</div> <!--  end content-table-inner ............................................END  -->
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
		
	<!--New Div For RecruitmentList  -->
	
	<div class="form-popup" id="myForm" align="center">

		<form action="#" class="form-container">
			<table id="customers" align="center" width="90%" class="tablecontainer">
			<tbody>
				<!--style="font-size: 12;"  -->
				<tr ><td colspan="10" align="center" style="color:white;">Recruitment List</td></tr>
				<tr style="border:solid;   border-color: black;">
					<th width="75" bgcolor="1F5FA7">Action</th>
					<th width="75">Id</th>
					<th width="95">Date</th>
					<th width="165">Project Manager Name</th>
					<th width="155">Education</th>
					<th width="195">Designation</th>
					<th width="95">Requirement Type</th>
					<th width="180">Salary CTC Range</th>
					
				</tr>
				</tbody>
			</table>
			<div style="overflow-y: scroll; height: 120px;width:860px;" >
				<table id="customers"  align="center" class="tablecontainer">
				<tbody style="background-color: white;">
					<tr class="alt">
						<%
							ArrayList<RecruitmentBean> result = new ArrayList<RecruitmentBean>();
							RecruitmentDAO rdao = new RecruitmentDAO();
							result = rdao.getRecruitList();
							String s;
							for (RecruitmentBean rs : result) {
						%>
					
					<tr>
						<td width="98" align="center">
						<a	class="btn btn-default btn-xs purple"
						onclick="detect(<%=rs.getRecId()%>)"><i class="fa fa-trash-o"></i></a><!-- onclick="window.document.location.href='EditRecruit.jsp?rid=<%=rs.getRecId()%>'" -->
							<a href="#" class="btn btn-default btn-xs purple" onclick="window.document.location.href='EditRecruit.jsp?rid=<%=rs.getRecId()%>'" ><i class="fa fa-edit"></i></a></td>
								<!--onclick="window.document.location.href='EditProject.jsp?Pid='"  onclick="RecruitEdit(<%=rs.getRecId()%>)"-->
						</td>
						<td width="79" align="center"><%=rs.getRecId()%></td>
						<td width="95" align="center"><%=rs.getRecruitDate()%></td>
						<td width="185" align="center"><%=rs.getManagerName()%></td>
						<td width="155" align="center"><%=rs.getEducation()%></td>
						<td width="200" align="center"><%=rs.getDesig()%></td>
						<td width="95" align="center"><%=rs.getReqType()%></td>
						<td width="180" align="center"><%=rs.getSalCTCFrom()%>K To <%=rs.getSalCTCTo()%>K</td>
					
					</tr>
					<%
						}
					%>
					</tbody>
				</table>
			</div>
		</form>
	</div>
		
<!--End div of Recruitment  -->		


<!-- RecruitEdit **************************************************************************************************************-->	

 <div class="form-popup1" id="myForm1" align="center"  style="overflow-y:scroll; height:100%;">
	<%-- <jsp:include page="EditRecruit.jsp?rid=7"></jsp:include> --%>
	
	<%-- <%@ include file="EditRecruit.jsp?rid=7" %> --%>
	
	<!--start content
		<div id="content" >
			<table border="0" width="100%" cellpadding="0" cellspacing="0">
			<td>
						 
						<div id="content-table-inner">

							  
							<div id="table-content">
								<center> 
									<form action="RecruitmentServlet?action=RecruitInfo" method="post" onsubmit="return TaskValidation()">
										<table id="customers" align="center">
											<tr class="alt" style="border:solid;border-color:black; ">
													<th colspan="10" >Recruitment Form</th>
											</tr>
											<tr>
												<td></td>
											</tr>
											<tr  class="alt" style="width: 12px;height:10px;" class="alt">
													<td width="20" colspan="8" style="text-align: right;">
														Date :&nbsp;&nbsp; <input type='text' class='datepick' id='RDate'name="RDate" readonly="readonly">
													</td>												
											</tr>
											<tr class="alt">
													<td width="195px">Project Manager Name :</td>
													<td><input type="text" id="ProManName" name="ProManName"></td>
													<td width="175px">Job Location :</td>
													<td colspan="4"><input type="text" id="JobLoc"name="JobLoc"></td>
											</tr>							
											
											<tr class="alt"><td colspan="10"></td></tr>
											<tr class="alt" style="border:solid;border-color:black; ">
												<th colspan="10">Basic Information About Requirements</th> 
											</tr>
											
											<tr class="alt"><td colspan="10"></td></tr>
											<tr class="alt">
												<td width="195px">Designation :</td>
												<td ><input type="text" id="Desig"name="Desig"></td>
												
												<td width="140px">Department :</td>
												<td colspan="8"><input type="text" id="Dept"name="Dept"></td>
											</tr>
											
											<tr class="alt">
												<td width="40">No.Of Requirement :</td>
												<td><input type="text" id="ReqNo" name="ReqNo"></td>
													
												<td width="">Type Of Requirement :</td>
												<td colspan="8"><select id="ReqType"name="ReqType">
												<option>Fresher</option>
												<option>Experience</option>
												</select>
												</td>
												
											</tr>
											<tr class="alt">
												<td width="40">Education :</td>
												<td ><input type="text" id="Education"name="Education"></td>
												
												<td width="">Type of employee :</td>
												<td colspan="8" ><input type="text" id="EmpType"name="EmpType"></td>
														
											</tr>
											<tr class="alt">
												<td width="">Salary range in CTC :</td>
												<td colspan="8">From : <input type="text" style="width:50px;" id="SalFrom" name="SalFrom"> K &nbsp;&nbsp; To : <input type="text" style="width:50px;" id="SalTo" name="SalTo"> K </td>
											</tr>
											
											<tr class="alt">
											<td width="">Bond Requirement:</td>
												<td ><input type="checkbox" id="BondYes"  onclick="check(this)" value="Yes" name="BondYesNo"> Yes &nbsp;&nbsp;
														<input type="checkbox" id="BondNo" onclick="check(this)" value="No" name="BondYesNo"> No											
												</td>
												<td width="">Orignal Documents require :</td>
												<td colspan="8"><input type="checkbox" id="DocYes" onclick="check(this)" value="Yes" name="DocYesNo"> Yes &nbsp;&nbsp;
														<input type="checkbox" id="DocNo" value="No"  onclick="check(this)" name="DocYesNo"> No</td>
											</tr>
											<tr class="alt" style="width: 12px;height:10px;">
												<td width="">Min. Experience :</td>
												<td colspan="8"><input type="text" id="MinExp"name="MinExp"></td>
				  							 </tr>
										
											<tr class="alt" style="width: 12px;height:10px;" class="alt">
												<td width="20">Probation Period :</td>
												<td><input type="text" id="ProbPeriod" name="ProbPeriod">Month</td> 
												<td width="">Bond Tenure :</td>
												<td colspan="8"><input type="text" id="BondTenure"name="BondTenure"></td>
				  							 </tr>
										
											<tr ><td colspan="10"></td></tr>
										
											<tr class="alt" style="border:solid;border-color:black; ">
												<th colspan="5">Skills & Knowledge Required </th>
											</tr>
										
											 <tr class="alt" style="width: 12px;height:10px;">							 					  
				      							  <td colspan="5">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<textarea   name="SkillArea" id="SkillArea" cols="90" rows="3" tabindex="101" data-wz-state="256" data-min-length="" placeholder="Skills & Knowledge Required.."></textarea></td>
			  							     </tr>
			  							     <tr class="alt" style="border:solid;border-color:black; ">
												<th colspan="5" >Special  Requirement </th>
											 </tr>
			  							      <tr class="alt" style="width: 12px;height:10px;">
				      							  <td colspan="5" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<textarea   name="SpecialReqArea"  id="SpecialReqArea" cols="90" rows="3" tabindex="101" data-wz-state="256" data-min-length="" placeholder="Special  Requirement.."></textarea></td>
			  							     </tr>
			  							     		   
			  							    <tr class="alt">
												<td colspan="7" align="center">
													<input type="submit" id="btn1"value="Save" class="myButton" style="border:solid;border-color:darkblue; "> &nbsp;&nbsp; &nbsp;
													<input type="reset" id="btn2" value="Close" onclick="closeEdit()" style="border:solid;border-color:darkblue; "onclick="window.document.location.href='RecruitmentForm.jsp'"class="myButton">&nbsp;&nbsp; &nbsp;
													<input type="button" id="btn3" value="Recruitment List" class="myButton" onclick="RecruitList()" style="border:solid;border-color:darkblue; ">
												</td>
											</tr>
																
										</table>
									</form>
								 </center>
							</div>
						</div>
					</td>
				</table>-->
			</div> 
	

<!-- End RecruitEdit ****************************************************************************************************-->						
						
<script>
function RecruitList() {
  document.getElementById("myForm").style.display = "block";
}
function RecruitEdit(id)
{
	//window.location.replace("EditRecruit.jsp?RecId=" +RecId);
	var myWindow = window.open("http://localhost:8089/NAMCOPayroll/EditRecruit.jsp?rid="+id, "_blank", "width=800,height=500");
  	document.getElementById("myForm").style.display = "none";	
  	document.getElementById("myForm1").style.display = "block";
  
}
function closeEdit()
{
	document.getElementById("myForm1").style.display = "none";

}
function myFunction(id) {
    var myWindow = window.open("http://localhost:8089/NAMCOPayroll/EditRecruit.jsp?rid="+id, "_blank", "width=800,height=500");
}
</script>
<%-- <%int id=Integer.parseInt(request.getParameter("RecId"));
System.out.print(id+"sssss");
%>	 --%>
</body>
</html>