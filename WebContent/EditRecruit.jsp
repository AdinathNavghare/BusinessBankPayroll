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


<%
/* //AddProjectBean addbean = new AddProjectBean(); */
String action=request.getParameter("action")!=null?request.getParameter("action"):"EditProject";
int Rid=Integer.parseInt(request.getParameter("rid"));
//System.out.print("Edit record for "+pid+ " id");
String JobLoc=null,RDate=null,ManagerName=null,desig=null,dept=null,ReqType=null,skill=null,SpecialReq=null,education=null,empType=null,BondReq=null,DocReq=null;
int CTCto=0,CTCFrom=0,ProbPeriod=0,ReqNo=0;
float  MinExp=0,BondTenure=0;
		String[] a=new String[100];
%>
<% 
ArrayList<RecruitmentBean> result = new ArrayList<RecruitmentBean>();
RecruitmentDAO rdao = new RecruitmentDAO();
	result=rdao.RecruitEdit(Rid);
	  for(RecruitmentBean rs : result)
	  {
			RDate=rs.getRecruitDate();
			ManagerName=rs.getManagerName();	
			desig= rs.getDesig();
			dept=rs.getDept();
			ReqNo=rs.getNoOfRequirement();
			ReqType=rs.getReqType();
			education=rs.getEducation(); 
			empType=rs.getEmpType();
			CTCFrom=rs.getSalCTCFrom();
			CTCto=rs.getSalCTCTo(); 
			ProbPeriod=rs.getProbPeriod();
			MinExp=rs.getMinExp();
			BondTenure=rs.getBondTenure();
			BondReq=rs.getBondRequire();
			DocReq=rs.getDocRequire();
			skill=rs.getSkills();
			SpecialReq=rs.getSpecialReq();	
	  }		
	 
%> 
<script type="text/javascript">
function myFunction()
{
	debugger;
	var d=<%= RDate%>;
	<%-- var j=<%= JobLoc%>; --%>
	<%-- var p=<%= ManagerName %>; --%>
	var desig=<%= skill%> ;
	if(d==""||desig=="")
		{
		alert("Plz fill data.....");
		}
}
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
</script>
<style>
.form-popup1 
{	
	/* display: none; */
	position: absolute;
	font-size:10px;
	bottom:1%; 
	right: 16%;
	float: right;
	border: 3px solid #f1f1f1;
	z-index: 9;
	width:65%; /* Full width */
  	height: 55%; /* Full height */
  	overflow: hidden; /* Enable scroll if needed */
	background-color: rgb(0,0,0); /* Fallback color */ 
  	background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
}

</style>
</head>
<body style="overflow: auto">
	<%@include file="mainHeader.jsp"%>
	<!--  start nav-outer-repeat................................................................................................. START -->
	<%@include file="subHeader.jsp" %>
	<%-- <%@ include file="ProjectFilterSubHeader.jsp"%>
 --%>

	<!-- start content-outer ........................................................................................................................START -->
	<div   class="form-popup1" align="center" style=" height:100%;" >
		

			<table border="0" width="100%" cellpadding="0" cellspacing="0">
			<td>
						<!--  start content-table-inner ...................................................................... START -->
						<div id="content-table-inner">

							<!--  start table-content  -->
							<div id="table-content">
								<center>
									<form action="RecruitmentServlet?action=RecruitUpdate" method="post" onsubmit="return TaskValidation()">
										<input type="hidden" name="rid" id="rid" value="<%=Rid%>">
										<table id="customers" align="center">
											<tr class="alt" style="border:solid;border-color:black; ">
													<th colspan="10" >Recruitment Form</th>
											</tr>
											<tr  class="alt" style="width: 12px;height:10px;" class="alt">
													<td width="20" colspan="8" style="text-align: right;">
														Date :&nbsp;&nbsp; <input type='text' value="<%=RDate%>" class='datepick' id='RDate'name="RDate" readonly="readonly">
													</td>												
											</tr>
											<tr class="alt">
													<td width="195px">Project Manager Name :</td>
													<td><input type="text" id="ProManName" value="<%=ManagerName %>" name="ProManName"></td>
													<td width="175px">Job Location :</td>
													<td colspan="4">
													<input type="text" name="JobLoc" list="JobLoc" autocomplete="off"/>
													<datalist id="JobLoc" name="JobLoc">
													<% 
													ArrayList<RecruitmentBean> result1 = new ArrayList<RecruitmentBean>();
													RecruitmentDAO rdao1 = new RecruitmentDAO();
													result1=rdao1.RecruitEditJob(Rid);
													for(RecruitmentBean rs : result1)
													{	  
															JobLoc=rs.getJobLocation1();
													%>
													<option><%= rs.getLocId()%>,<%= rs.getJobLocation1()%></option>											
													<%} %>
													</datalist >
													</td>
											</tr>							
											
											<tr class="alt"><td colspan="10"></td></tr>
											<tr class="alt" style="border:solid;border-color:black; ">
												<th colspan="10">Basic Information About Requirements</th> 
											</tr>
											
											<tr class="alt"><td colspan="10"></td></tr>
											<tr class="alt">
												<td width="195px">Designation :</td>
												<td ><input type="text" value="<%=desig %>" id="Desig"name="Desig"></td>
												
												<td width="140px">Department :</td>
												<td colspan="8"><input type="text" value="<%=dept %>" id="Dept"name="Dept"></td>
											</tr>
											
											<tr class="alt">
												<td width="40">No.Of Requirement :</td>
												<td><input type="text" value="<%=ReqNo %>" id="ReqNo" name="ReqNo"></td>
													
												<td width="">Type Of Requirement :</td>
												<td colspan="8"><select id="ReqType"  name="ReqType">
												<option><%=ReqType %></option>
												<option>Fresher</option>
												<option>Experience</option>
												</select>
												</td>
												
											</tr>
											<tr class="alt">
												<td width="40">Education :</td>
												<td ><input type="text" id="Education" value="<%=education %>" name="Education"></td>
												
												<td width="">Type of employee :</td>
												<td colspan="8" ><input type="text" id="EmpType" value="<%=empType %>" name="EmpType"></td>
														
											</tr>
											<tr class="alt">
												<td width="">Salary range in CTC :</td>
												<td colspan="8">From : <input type="text" style="width:50px;" value="<%=CTCFrom %>" id="SalFrom" name="SalFrom"> K &nbsp;&nbsp; To : <input type="text" value="<%=CTCto %>" style="width:50px;" id="SalTo" name="SalTo"> K </td>
											</tr>
											
											<tr class="alt">
											<td width="">Bond Requirement:</td>
												<td ><input type="checkbox" id="BondYes"  onclick="check(this)" value="Yes" <%= ("Yes".equals(BondReq) ? "checked" : "") %> name="BondYesNo"> Yes &nbsp;&nbsp;
													<input type="checkbox" id="BondNo" onclick="check(this)" value="No" <%= ("No".equals(BondReq) ? "checked" : "") %> name="BondYesNo"> No											
												</td>
												<td width="">Orignal Documents require :</td>
												<td colspan="8"><input type="checkbox" id="DocYes" onclick="check(this)" value="Yes" <%= ("Yes".equals(DocReq) ? "checked" : "") %> name="DocYesNo"> Yes &nbsp;&nbsp;
														<input type="checkbox" id="DocNo" value="No" <%= ("No".equals(DocReq) ? "checked" : "") %>  onclick="check(this)"  name="DocYesNo"> No</td>
											</tr>
											<tr class="alt" style="width: 12px;height:10px;">
												<td width="">Min. Experience :</td>
												<td colspan="8"><input type="text" value="<%=MinExp %>" id="MinExp"name="MinExp"></td>
				  							 </tr>
										
											<tr class="alt" style="width: 12px;height:10px;" class="alt">
												<td width="20">Probation Period :</td>
												<td><input type="text" id="ProbPeriod" value="<%=ProbPeriod %>" name="ProbPeriod">Month</td> 
												<td width="">Bond Tenure :</td>
												<td colspan="8"><input type="text" id="BondTenure" value="<%=BondTenure %>" name="BondTenure"></td>
				  							 </tr>
										
											<tr ><td colspan="10"></td></tr>
										
											<tr class="alt" style="border:solid;border-color:black; ">
												<th colspan="5">Skills & Knowledge Required </th>
											</tr>
										
											 <tr class="alt" style="width: 12px;height:10px;">							 					  
				      							  <td colspan="5">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<textarea  value="<%=skill %>" name="SkillArea"  id="SkillArea" cols="90" rows="3" tabindex="101" data-wz-state="256" data-min-length="" placeholder="Skills & Knowledge Required.."><%=skill %></textarea></td>
			  							     </tr>
			  							     <tr class="alt" style="border:solid;border-color:black; ">
												<th colspan="5" >Special  Requirement </th>
											 </tr>
			  							      <tr class="alt" style="width: 12px;height:10px;">
				      							  <td colspan="5" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<textarea  value="<%=SpecialReq %>"  name="SpecialReqArea" id="SpecialReqArea" cols="90" rows="3" tabindex="101" data-wz-state="256" data-min-length="" placeholder="Special  Requirement.."><%=SpecialReq %></textarea></td>
			  							     </tr>
			  							     		   
			  							    <tr class="alt">
												<td colspan="7" align="center">
													<input type="submit" id="btn1" value="Update" class="myButton" onclick="myFunction();"     style="border:solid;border-color:darkblue; "> &nbsp;&nbsp; &nbsp;
													<input type="reset" id="btn2" value="Close" style="border:solid;border-color:darkblue; "onclick="window.document.location.href='RecruitmentForm.jsp'"class="myButton">&nbsp;&nbsp; &nbsp;
													<!-- <input type="button" id="btn3" value="Recruitment List" class="myButton" onclick="RecruitList()" style="border:solid;border-color:darkblue; "> -->
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
				</tr>
			</table>
			
		</div>
</body>
</html>