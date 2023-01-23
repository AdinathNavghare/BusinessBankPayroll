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
String status="pending";
date=request.getParameter("date")==null?ReportDAO.getSysDate():request.getParameter("date");
status=request.getParameter("status")==null?status="pending":request.getParameter("status");
LeaveMasterHandler obj=new LeaveMasterHandler();
EmpOffHandler eoffhdlr = new EmpOffHandler();
EmpAttendanceHandler EAH=new EmpAttendanceHandler();
EmpOffBean eoffbn = new EmpOffBean();
eoffbn = eoffhdlr.getEmpOfficAddInfo(Integer.toString(eno));

ArrayList<EmpOffBean> projlist= new ArrayList<EmpOffBean>();
ArrayList<AttendStatusBean> proj_attend_state = new ArrayList<AttendStatusBean>();
projlist=eoffhdlr.getprojectCode();
proj_attend_state =EAH.getAllProjectAttendanceStatus(projlist,status);
session.setAttribute("proj_list", projlist);

int site_id = eoffbn.getPrj_srno();
session.setAttribute("Prj_Srno", site_id);

String select=new String();


float days=Calculate.getDays(date);
String action = request.getParameter("action")==null?"":request.getParameter("action");

RoleDAO obj1=new RoleDAO();
String roleId=obj1.getrole(Integer.parseInt(session.getAttribute("UID").toString()));

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
	
function view_att(prj,dt)
{
	
	window.location.href="EmpPresentSeat.jsp?prj="+prj+"&date="+dt,"_self";
	
}

function approve_att(prj,dt)
{
	var r = confirm("Are you sure to Approval ?");
	if (r == true) {
		window.location.href="EmpAttendServlet?action=approved&prj="+prj+"&date="+dt,"_self";
	} 
	
}	

function reject_att(prj,dt)
{
	var r = confirm("Are you sure to Reject ?");
	if (r == true) {
		window.location.href="EmpAttendServlet?action=reject&prj="+prj+"&date="+dt,"_self";
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
		window.location.href="approveAttendance.jsp?status="+st,"_self";
		}
}
	</script>
<%
	 String pageName = "approveAttendance.jsp";
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
 

%>

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
				<h1>Attendance Approval</h1>
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
								
									<%-- <td>Select Date :</td>
									<td> 
									<input name="date" size="20" id="date" value="<%=date==null?"":date%>" disabled="disabled" type="text" onchange="check()">&nbsp;
									<img src="images/cal.gif" align="middle"
							style="vertical-align: middle; cursor: pointer;"
							onClick="javascript:NewCssCal('date', 'ddmmmyyyy')" />
									</td> --%>
									<td>Select Status :</td>
									<td><select id="state" name="state" onchange="validate()">
											<option value="">Select</option>
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
									%>	<input type="button" id="" value="View" onclick="view_att('<%=lkb.getPrj_srno()%>','<%=asb.getAtt_month()%>')">
										<%if(roleId.equals("1"))
										{%>
										<input type="button" id="" value="Reject" onclick="reject_att('<%=lkb.getPrj_srno()%>','<%=asb.getAtt_month()%>')">
										<input type="button" id="" value="Approved" onclick="approve_att('<%=lkb.getPrj_srno()%>','<%=asb.getAtt_month()%>')">
									<%	
									}}
									else if( asb.getAppr_status().equalsIgnoreCase("")||asb.getAppr_status().equalsIgnoreCase("saved"))
									{
										%>
										
										<%	
									}
									
									else if( asb.getAppr_status().equalsIgnoreCase("approved"))
									{
										count++;
										%>
										<input type="button" id="" value="View" onclick="view_att('<%=lkb.getPrj_srno()%>','<%=asb.getAtt_month()%>')">
										 <font color="green">Approved</font>
										<%	
									}
									else if(asb.getAppr_status().equalsIgnoreCase("rejected"))
									{
										count++;
										%>
										<input type="button" id="" value="View" onclick="view_att('<%=lkb.getPrj_srno()%>','<%=asb.getAtt_month()%>')">
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
												%>	<input type="button" id="" value="View" onclick="view_att('<%=lkb.getPrj_srno()%>','<%=asb.getAtt_month()%>')">
													<%if(roleId.equals("1"))
														{%>
													<input type="button" id="" value="Reject" onclick="reject_att('<%=lkb.getPrj_srno()%>','<%=asb.getAtt_month()%>')">
													<input type="button" id="" value="Approved" onclick="approve_att('<%=lkb.getPrj_srno()%>','<%=asb.getAtt_month()%>')">
												<%	
												}}
												else if( asb.getAppr_status().equalsIgnoreCase("")||asb.getAppr_status().equalsIgnoreCase("saved"))
												{
													%>
													
													<%	
												}
												
												else if( asb.getAppr_status().equalsIgnoreCase("approved"))
												{
													count++;
													%>
													<input type="button" id="" value="View" onclick="view_att('<%=lkb.getPrj_srno()%>','<%=asb.getAtt_month()%>')">
													 <font color="green">Approved</font>
													<%	
												}
												else if(asb.getAppr_status().equalsIgnoreCase("rejected"))
												{
													count++;
													%>
													<input type="button" id="" value="View" onclick="view_att('<%=lkb.getPrj_srno()%>','<%=asb.getAtt_month()%>')">
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
																	<%if(asb.getAppr_status().equalsIgnoreCase("pending"))
																					{
																					count++;
																					%>	<input type="button" id="" value="View" onclick="view_att_2('<%=asb.getAppr_status() %>','<%=asb.getEmpno() %>','<%=asb.getAtt_month() %>','<%=asb.getTranEvent() %>')" >
																					
																	<%}	}
								}
								
								
								if(count<1)
								{
									%>
									
									<tr style="width: 50%;">
									
									<td style="width: 50%;" colspan="5"> Data not found !</td>
									
								</tr>
									<%
								}
								%>
								
								
								
								
								
								
								</table>
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
								