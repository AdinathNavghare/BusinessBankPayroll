<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="org.jfree.ui.about.ProjectInfo"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Iterator"%>

<%@page import="java.util.concurrent.ConcurrentHashMap"%>
<%@page import="java.util.Hashtable"%>
<%@page import="java.util.Map"%>
<%@page import="payroll.Core.ReportDAO"%>
<%@page import="payroll.Core.Calculate"%>
<%@page import="java.awt.Desktop.Action"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="payroll.Model.CodeMasterBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="payroll.Model.*"%>
<%@page import="payroll.DAO.*"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy; DTS3</title>
<link rel="stylesheet" href="css/screen.css" type="text/css"
	media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />
<link rel="stylesheet" href="css/datePicker.css" type="text/css"
	media="screen" title="default" />	
<script type="text/javascript" src="js/jquery.datePicker.js"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>

<script src="js/jquery/jquery.autocomplete.js"></script>

<%

EmployeeHandler emph = new EmployeeHandler();
EmployeeBean ebean = new EmployeeBean();
LookupHandler lookuph= new LookupHandler();

LookupHandler lkph= new LookupHandler();
ArrayList<TranBean> tranlist=new ArrayList<TranBean>();
int eno = (Integer)session.getAttribute("EMPNO");
String empName;
String date="";
String status="rejected";
date=request.getParameter("date")==null?ReportDAO.getSysDate():request.getParameter("date");
status=request.getParameter("status")==null?status="rejected":request.getParameter("status");
LeaveMasterHandler obj=new LeaveMasterHandler();
EmpOffHandler eoffhdlr = new EmpOffHandler();
EmpAttendanceHandler EAH=new EmpAttendanceHandler();
EmpOffBean eoffbn = new EmpOffBean();
eoffbn = eoffhdlr.getEmpOfficAddInfo(Integer.toString(eno));

int site_id = eoffbn.getPrj_srno();
String siteName=eoffbn.getPrj_name();
session.setAttribute("Prj_Srno", site_id);


ArrayList<EmpOffBean> projlist= new ArrayList<EmpOffBean>();
ArrayList<AttendStatusBean> proj_attend_state = new ArrayList<AttendStatusBean>();
ArrayList<AttendStatusBean> proj_attend_state_site_admin = new ArrayList<AttendStatusBean>();
projlist=eoffhdlr.getprojectCode();

proj_attend_state =EAH.getAllAttendanceStatus(projlist,status);
proj_attend_state_site_admin =EAH.getAllAttendanceStatus(projlist,status,eno,site_id);
session.setAttribute("proj_list", projlist);



String select=new String();


float days=Calculate.getDays(date);
String action = request.getParameter("action")==null?"":request.getParameter("action");


TranHandler trnh= new TranHandler();
LookupHandler lkh=new LookupHandler();
ArrayList<Lookup> emplist=new ArrayList<Lookup>(); 
emplist=lkh.getSubLKP_DESC("ET");	// ET is Code for Employee Type

int flag=-1;
try
{  
	
	try
	{
	flag=Integer.parseInt(request.getParameter("flag")==null?"":request.getParameter("flag")); 
	}catch(Exception e)
	{
		System.out.println("no flag value"+flag);
	}
	
}
catch(Exception e)
{
	System.out.println("First Time Loading");
}

 
%>
<%  SimpleDateFormat format =new SimpleDateFormat("dd-MMM-yyyy");
String DATE_FORMAT = "yyyy MM dd";

SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
Calendar c1 = Calendar.getInstance(); // today
// String today=format.format(c1.getTime());     /* -----for System date --------*/
	 
String today=EAH.getServerDate();     /* -----for Server date --------*/



RoleDAO obj1=new RoleDAO();
String roleId=obj1.getrole(Integer.parseInt(session.getAttribute("UID").toString()));


%>


<script type="text/javascript">
function checkFlag() {
		var f = parseInt(document.getElementById("flag1").value);

		if (f == 1) {
			alert("Record Approved Successfully");

		}
		else if (f == 2) {
			alert("Record Rejected Successfully");
		}
		else if (f == 100) {
			alert("Salary Calculation Of This Month Has't Started Yet !");
		}
		
		
	}

function act(date)
{
	
	
	var r = confirm("Are you sure to send for Approval ?");
	if (r == true) {
		window.location.href="EmpAttendServlet?action=approval&date="+date;
	} 
	

}
function view_att(status,dt,site)
{
	
	window.location.href="EmpPresentSeat.jsp?site_id="+site+"&status="+status+"&date="+dt,"_self";
	
}
function view_att1(status,dt,srno,name,code)
{
	window.location.href="EmpPresentSeat.jsp?action=getdetails&status="+status+"&prj="+srno+"&proj="+name+":"+code+"&date="+dt,"_self";
	
}

function view_att_2(status,empno,date,event){
	window.location.href="newAttendSheet.jsp?status="+status+"&empno="+empno+"&date="+date+"&event="+event+" ";
}



function addNew()
{
	var $radio = $('input[name=selectSite]:checked');
	var updateDay = $radio.val();
	var id = $radio.attr('id');
	var site=<%=site_id%>;
	
var date=document.getElementById("adddate").value;
var flag=true;
if(date=="")
	{
	alert("Please select Date");
	flag= false;
	}
	
if(flag==true)
{
<%	if(roleId.equals("1"))
{%>
window.location.href="EmpPresentSeat.jsp?date="+date+"&site_id="+site,"_self";
	<%}else{%>
window.location.href="EmpPresentSeat.jsp?date="+date+"&site_id="+id,"_self";
<%}%>
}
}


function validate()
{
	/* var dt=document.getElementById("date").value; */
	var st=document.getElementById("state").value;
	var flag=true;
	/* if(document.getElementById("date").value=="")
		{
		alert("Please select Date");
		flag= false;
		} */
	if(document.getElementById("state").value=="")
	{
	alert("Please select Status");
	flag= false;
	}
	
	if(flag==true)
		{
		window.location.href="attendanceMain.jsp?status="+st,"_self";
		}
}
	</script>
<%-- <%
	 String pageName = "attendanceMain.jsp";
	try
	{
		ArrayList<String> urls = (ArrayList<String>)session.getAttribute("urls");
		if(!urls.contains(pageName))
		{
			response.sendRedirect("NotAvailable.jsp");
		}
	}
	catch(Exception e)
	{
		//response.sendRedirect("login.jsp?action=0");
	}
 

%> --%>

</head>
<body onLoad="checkFlag()">
	<%@include file="mainHeader.jsp"%>
	<!--  start nav-outer-repeat................................................................................................. START -->
	<%@include file="subHeader.jsp"%>

	<!-- start content-outer ........................................................................................................................START -->
	<div id="content-outer">
		<!-- start content -->
		<div id="content">

			<!--  start page-heading -->
			<div id="page-heading">
				<h1>Add Attendance</h1>
			</div>
			<!-- end page-heading -->

			<table border="0" width="100%" cellpadding="0" cellspacing="0"
				id="content-table">
				<tr>
					<th rowspan="3" class="sized"><img
						src="images/shared/side_shadowleft.jpg" width="20" height="300"
						alt="" /></th>
					<th class="topleft"></th>
					<td id="tbl-border-top">&nbsp;</td>
					<th class="topright"></th>
					<th rowspan="3" class="sized"><img
						src="images/shared/side_shadowright.jpg" width="20" height="300"
						alt="" /></th>
				</tr>
				<tr>
					<td id="tbl-border-left"></td>
					<td>
						<!--  start content-table-inner ...................................................................... START -->
						<div id="content-table-inner">

							<!--  start table-content  -->
							<div id="table-content" style="height: 500px; overflow: auto;">
								<center>
								
								<table id="customers">
								<tr>
								
									
									<td>Select Status :</td>
									<td><select id="state" name="state" onchange="validate()">
											<option value="">Select</option>
											<option value="saved" <%=status.equalsIgnoreCase("saved")?"Selected":"" %>>Saved </option>
											<option value="pending" <%=status.equalsIgnoreCase("pending")?"Selected":"" %>>Pending </option>
											<option value="approved" <%=status.equalsIgnoreCase("approved")?"Selected":"" %>>Approved </option>
											<option value="rejected" <%=status.equalsIgnoreCase("rejected")?"Selected":"" %>>Rejected </option>
												<%-- <option value="left" <%=status.equalsIgnoreCase("left")?"Selected":"" %>>Left</option>
													<option value="transfer" <%=status.equalsIgnoreCase("transfer")?"Selected":"" %>>Transfer</option> --%>
											<option value="all" <%=status.equalsIgnoreCase("all")?"Selected":"" %>>All </option>
										</select></td>
										
									
								</tr>
								
								</table>
								<br><br>
								
								<table id="customers">
								<%if(status.equals("rejected") || status.equals("saved") ||status.equals("pending") || status.equals("approved") 
										||status.equals("all") ){
								%>
								<tr>
									
									<th>Site Code</th>
									<th>Site Name</th>
									<th>Month</th>
									<th>Status</th>
									<th>Action</th>
								</tr>
								<%}
						if(status.equals("left") || status.equals("transfer")  ){
							%>
							<tr>
									
									<th>Emp Code</th>
									<th>Employee Name</th>
									<th>Month</th>
									<th>Status</th>
									<!-- <th>Submit Date</th> -->
									<th>Action</th>
								</tr>
							<%
						}	
								int count=0;
								if(roleId.equals("1"))
								{
									for(EmpOffBean lkb :projlist)
									{
								
										for(AttendStatusBean asb :proj_attend_state)
										{
								
											if(asb.getSite_id()== lkb.getPrj_srno() && asb.getAppr_status().equalsIgnoreCase(status))
											{
											
											%>
									<tr>
										
										<td><%=lkb.getPrj_code()%></td>
										<td><%=lkb.getPrj_name() %></td>
										<td><%=asb.getAtt_month() %></td>
										<td><%=asb.getAppr_status() %></td>
										
										<td>
									
										
										<%if(asb.getAppr_status().equalsIgnoreCase("pending"))
										{
											count++;
										%>	<input type="button" id="" value="View" onclick="view_att1('<%=asb.getAppr_status() %>','<%=asb.getAtt_month()%>','<%=lkb.getPrj_srno()%>','<%=lkb.getPrj_name() %>','<%=lkb.getPrj_code()%>')">
											<%	
										}
										else if( asb.getAppr_status().equalsIgnoreCase("")||asb.getAppr_status().equalsIgnoreCase("saved"))
										{
											count++;
											%>
											<input type="button" id="" value="View" onclick="view_att1('<%=asb.getAppr_status() %>','<%=asb.getAtt_month()%>','<%=lkb.getPrj_srno()%>','<%=lkb.getPrj_name() %>','<%=lkb.getPrj_code()%>')">
											
											<%	
										}
										else if( asb.getAppr_status().equalsIgnoreCase("approved"))
										{
											count++;
											%>
											<input type="button" id="" value="View" onclick="view_att1('<%=asb.getAppr_status() %>','<%=asb.getAtt_month()%>','<%=lkb.getPrj_srno()%>','<%=lkb.getPrj_name() %>','<%=lkb.getPrj_code()%>')">
											 <font color="green">Approved</font>
											<%	
										}
										else if(asb.getAppr_status().equalsIgnoreCase("rejected"))
										{
											count++;
											%>
											<input type="button" id="" value="View" onclick="view_att1('<%=asb.getAppr_status() %>','<%=asb.getAtt_month()%>','<%=lkb.getPrj_srno()%>','<%=lkb.getPrj_name() %>','<%=lkb.getPrj_code()%>')">
											 <font color="red">Rejected</font>
											<%	
										}
										%></td>
									</tr>
										
										<%
										
										}
											else if(status.equalsIgnoreCase("all") && asb.getSite_id()== lkb.getPrj_srno())
											{
												
												%>
												<tr>
													
													<td><%=lkb.getPrj_code()%></td>
													<td><%=lkb.getPrj_name() %></td>
													<td><%=asb.getAtt_month() %></td>
													<td><%=asb.getAppr_status() %></td>
													<td>
													<%if(asb.getAppr_status().equalsIgnoreCase("pending"))
														{
														count++;
														%>	<input type="button" id="" value="View" onclick="view_att1('<%=asb.getAppr_status() %>','<%=asb.getAtt_month()%>','<%=lkb.getPrj_srno()%>','<%=lkb.getPrj_name() %>','<%=lkb.getPrj_code()%>')">
											<%	
														}
													else if( asb.getAppr_status().equalsIgnoreCase("")||asb.getAppr_status().equalsIgnoreCase("saved"))
													{
														count++;
														%>
														<input type="button" id="" value="View" onclick="view_att1('<%=asb.getAppr_status() %>','<%=asb.getAtt_month()%>','<%=lkb.getPrj_srno()%>','<%=lkb.getPrj_name() %>','<%=lkb.getPrj_code()%>')">
											
														<%	
													}
													
													else if( asb.getAppr_status().equalsIgnoreCase("approved"))
													{
														count++;
														%>
														<input type="button" id="" value="View" onclick="view_att1('<%=asb.getAppr_status() %>','<%=asb.getAtt_month()%>','<%=lkb.getPrj_srno()%>','<%=lkb.getPrj_name() %>','<%=lkb.getPrj_code()%>')">
											 <font color="green">Approved</font>
														<%	
													}
													else if(asb.getAppr_status().equalsIgnoreCase("rejected"))
													{
														count++;
														%>
														<input type="button" id="" value="View" onclick="view_att1('<%=asb.getAppr_status() %>','<%=asb.getAtt_month()%>','<%=lkb.getPrj_srno()%>','<%=lkb.getPrj_name() %>','<%=lkb.getPrj_code()%>')">
												 
														 <font color="red">Rejected</font>
														<%	
													}
													%></td>
												</tr>
												<%							}
											
										
											
										}
											
										
									}
									
									for(AttendStatusBean asb :proj_attend_state)
									{

										 if(asb.getSite_id()==0 && asb.getTranEvent().equalsIgnoreCase("left") ||asb.getSite_id()==0 && asb.getTranEvent().equalsIgnoreCase("transfer"))
										 {
																					count++;
																					ebean = emph.getEmployeeInformation(Integer.toString(asb.getEmpno()));
																					
																					%>
																				
																			<tr>
																		
																		<td align="center"><%=ebean.getEMPCODE()%></td>
																		<td><%=lookuph.getLKP_Desc("SALUTE", ebean.getSALUTE())+" "+ ebean.getFNAME()+" "+ebean.getLNAME() %></td>
																		<td><%=asb.getAtt_month() %></td>
																	<%-- 	<td><%=asb.getSubmit_date() %></td> --%>
																		<td><%=asb.getAppr_status() %></td>
																		<td>	
																		<%
																						
																						 if(asb.getAppr_status().equalsIgnoreCase("pending")){	
																							 count++;
																					%><input type="button" id="" value="View" onclick="view_att_2('<%=asb.getAppr_status() %>','<%=asb.getEmpno() %>','<%=asb.getAtt_month() %>','<%=asb.getTranEvent() %>')" >
																				
																				
																				<%} else	if(asb.getAppr_status().equalsIgnoreCase("approved")){
																					count++;
																						%>
																						<input type="button" id="" value="View" onclick="view_att_2('<%=asb.getAppr_status() %>','<%=asb.getEmpno() %>','<%=asb.getAtt_month() %>','<%=asb.getTranEvent() %>')" >
																						 <font color="green">Approved</font>
																					<%}
																					else if(asb.getAppr_status().equalsIgnoreCase("rejected")){
																						count++;
																						%>
																					<input type="button" id="" value="View" onclick="view_att_2('<%=asb.getAppr_status() %>','<%=asb.getEmpno() %>','<%=asb.getAtt_month() %>','<%=asb.getTranEvent() %>')" >
																						 <font color="red">Rejected</font>
																				<%}
																						
												}
									}
								}
				
								else
								{
									for(EmpOffBean lkb :projlist)
									{
								
										for(AttendStatusBean asb :proj_attend_state_site_admin)
										{
								
											if(asb.getSite_id()== lkb.getPrj_srno() && asb.getAppr_status().equalsIgnoreCase(status))
											{
											
											%>
									<tr>
										
										<td><%=lkb.getPrj_code()%></td>
										<td><%=lkb.getPrj_name() %></td>
										<td><%=asb.getAtt_month() %></td>
										<td><%=asb.getAppr_status() %></td>
										
										<td>
									
										
										<%if(asb.getAppr_status().equalsIgnoreCase("pending"))
										{
											count++;
										%>	<input type="button" id="" value="View" onclick="view_att('<%=asb.getAppr_status() %>','<%=asb.getAtt_month()%>','<%=asb.getSite_id()%>')">
											<%	
										}
										else if( asb.getAppr_status().equalsIgnoreCase("")||asb.getAppr_status().equalsIgnoreCase("saved"))
										{
											count++;
											%>
											<input type="button" id="" value="View" onclick="view_att('<%=asb.getAppr_status() %>','<%=asb.getAtt_month()%>','<%=asb.getSite_id()%>')">
											
											<%	
										}
										else if( asb.getAppr_status().equalsIgnoreCase("approved"))
										{
											count++;
											%>
											<input type="button" id="" value="View" onclick="view_att('<%=asb.getAppr_status() %>','<%=asb.getAtt_month()%>','<%=asb.getSite_id()%>')">
											 <font color="green">Approved</font>
											<%	
										}
										else if(asb.getAppr_status().equalsIgnoreCase("rejected"))
										{
											count++;
											%>
											<input type="button" id="" value="View" onclick="view_att('<%=asb.getAppr_status() %>','<%=asb.getAtt_month()%>','<%=asb.getSite_id()%>')">
											 <font color="red">Rejected</font>
											<%	
										}
										%></td>
									</tr>
										
										<%
										
										}
											else if(status.equalsIgnoreCase("all") && asb.getSite_id()== lkb.getPrj_srno())
											{
												
												%>
												<tr>
													
													<td><%=lkb.getPrj_code()%></td>
													<td><%=lkb.getPrj_name() %></td>
													<td><%=asb.getAtt_month() %></td>
													<td><%=asb.getAppr_status() %></td>
													<td>
													<%if(asb.getAppr_status().equalsIgnoreCase("pending"))
														{
														count++;
														%>	<input type="button" id="" value="View" onclick="view_att('<%=asb.getAppr_status() %>','<%=asb.getAtt_month()%>','<%=asb.getSite_id()%>')">
											<%	
														}
													else if( asb.getAppr_status().equalsIgnoreCase("")||asb.getAppr_status().equalsIgnoreCase("saved"))
													{
														count++;
														%>
															<input type="button" id="" value="View" onclick="view_att('<%=asb.getAppr_status() %>','<%=asb.getAtt_month()%>','<%=asb.getSite_id()%>')">											
														<%	
													}
													
													else if( asb.getAppr_status().equalsIgnoreCase("approved"))
													{
														count++;
														%>
														<input type="button" id="" value="View" onclick="view_att('<%=asb.getAppr_status() %>','<%=asb.getAtt_month()%>','<%=asb.getSite_id()%>')">
											 <font color="green">Approved</font>
														<%	
													}
													else if(asb.getAppr_status().equalsIgnoreCase("rejected"))
													{
														count++;
														%>
														<input type="button" id="" value="View" onclick="view_att('<%=asb.getAppr_status() %>','<%=asb.getAtt_month()%>','<%=asb.getSite_id()%>')">
												 
														 <font color="red">Rejected</font>
														<%	
													}
													%></td>
												</tr>
												<%							}
											
										
											
										}
											
										
									}
									
									for(AttendStatusBean asb :proj_attend_state)
									{

										 if(asb.getSite_id()==0 && asb.getTranEvent().equalsIgnoreCase("left") ||asb.getSite_id()==0 && asb.getTranEvent().equalsIgnoreCase("transfer"))
										 {
																					count++;
																					ebean = emph.getEmployeeInformation(Integer.toString(asb.getEmpno()));
																					
																					%>
																				
																			<tr>
																		
																		<td align="center"><%=ebean.getEMPCODE()%></td>
																		<td><%=lookuph.getLKP_Desc("SALUTE", ebean.getSALUTE())+" "+ ebean.getFNAME()+" "+ebean.getLNAME() %></td>
																		<td><%=asb.getAtt_month() %></td>
																	<%-- 	<td><%=asb.getSubmit_date() %></td> --%>
																		<td><%=asb.getAppr_status() %></td>
																		<td>	
																		<%
																						
																						 if(asb.getAppr_status().equalsIgnoreCase("pending")){	
																							 count++;
																					%><input type="button" id="" value="View" onclick="view_att_2('<%=asb.getAppr_status() %>','<%=asb.getEmpno() %>','<%=asb.getAtt_month() %>','<%=asb.getTranEvent() %>')" >
																				
																				
																				<%} else	if(asb.getAppr_status().equalsIgnoreCase("approved")){
																					count++;
																						%>
																						<input type="button" id="" value="View" onclick="view_att_2('<%=asb.getAppr_status() %>','<%=asb.getEmpno() %>','<%=asb.getAtt_month() %>','<%=asb.getTranEvent() %>')" >
																						 <font color="green">Approved</font>
																					<%}
																					else if(asb.getAppr_status().equalsIgnoreCase("rejected")){
																						count++;
																						%>
																					<input type="button" id="" value="View" onclick="view_att_2('<%=asb.getAppr_status() %>','<%=asb.getEmpno() %>','<%=asb.getAtt_month() %>','<%=asb.getTranEvent() %>')" >
																						 <font color="red">Rejected</font>
																				<%}
																						
												}
									}
								}
								
								if(count<1 )
								{
									%>
									
									<tr style="width: 50%;">
									
									<td style="width: 50%;" colspan="5"> Data not found !</td>
									
								</tr>
									
									
								<%	}%>
								
						
								</table>
								<div>
								<br><br>
								<table id="customers">
								<tr>
								<th colspan="2">ADD New Attendance Sheet</th>
								</tr>
								
								<tr>
							
								<%if(roleId.equals("1"))
									{%>
									<td>Select Date :</td>
								<td>
								<input name="adddate" size="20" id="adddate" value="<%=today %>" disabled="disabled" type="text">&nbsp;
									<img src="images/cal.gif" align="middle"	style="vertical-align: middle; cursor: pointer;"	onClick="javascript:NewCssCal('adddate', 'ddmmmyyyy')" />
								
								 </td>
								<%}
								else{
									 %><td>Today's Date :  <input name="adddate" size="20" id="adddate" value="<%=today %>" disabled="disabled" type="text">&nbsp;</td></tr>
										<tr>
										
										<!-- <select name="selectSite" size="4" id="selectSite" > -->
									
											
												<%
												ArrayList<EmpOffBean> list= new ArrayList<EmpOffBean>();
												EmpOffHandler ofh = new EmpOffHandler();
					    						list=ofh.getprojectCode();
					    						int counter=1;
					    						int selected=0;
												ArrayList <TranBean>siteList=new ArrayList<TranBean>();
												EmpAttendanceHandler empAttendanceHandler=new EmpAttendanceHandler();
												siteList=empAttendanceHandler.getAssignedSitesList(eno);
		                                       if(siteList.size()!=0){
		                                    	   %>	<td>Select Site : <br>  <br> 
		                                    	    	<input type="radio"   name="selectSite"  id="<%=site_id%>" value="Official Site" checked="checked" size="4" value="<%=site_id%>"/><%=siteName %> 
		                                    	   <br> <br> 	<%
												for(EmpOffBean lkb :list)
		    							{
		    						for(TranBean tranBean:siteList){
		    							if(lkb.getPrj_srno()==Integer.parseInt(	tranBean.getSite_id())){
		    								if(lkb.getPrj_srno()!=site_id){
		    								// System.out.println("siteList contains"+lkb.getPrj_srno());
		    							%>
		    						<input type="radio" name="selectSite" size="4" id="<%=lkb.getPrj_srno()%>" value="<%=lkb.getPrj_name()%>"/><%=lkb.getPrj_name()%><br>
		    				<%-- 	 	<input type="radio"   style="display: none;" name="selectSite"  id="<%=site_id%>" value="Official Site" checked="checked" size="4" value="<%=site_id%>"/>  --%>
		    						<br>	
		    							<%-- <option  title="<%=lkb.getPrj_code()%>" ><%=lkb.getPrj_name()%></option> --%>
		    					<% }	} }}
		    					
		                                    }
		    					else{%> 
		    						
		    				
		    						<input type="radio"   style="display: none;" name="selectSite"  id="<%=site_id%>"  checked="checked" size="4" value="<%=site_id%>"/>
		    						<br>	
		    					<%}%>
		    					
											</select>
											
											</td>
											</tr>
										 <%} %>
										 
										</tr>
										
										<tr>
										<td align="center" colspan="2"><input type="button" value="ADD" onclick="addNew()" /></td>
									
										</tr>
										</table>
										
										</div>
										
										
										</center>
										<input type="hidden" name="flag1" id="flag1" value="<%=flag%>">
										</div>
									<!--  end table-content  -->

									<div class="clear"></div>

								</div> <!--  end content-table-inner ............................................END  -->
							</td>
							<td id="tbl-border-right"></td>
						
						
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
										
