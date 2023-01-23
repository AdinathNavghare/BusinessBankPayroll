<%@page import="java.awt.Desktop.Action"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="payroll.Model.Retirement_ext_leave_credit"%>
<%@page import="java.util.ArrayList"%>
<%@page import="payroll.Model.*"%>
<%@page import="payroll.DAO.*"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy DTS3</title>
<link rel="stylesheet" href="css/screen.css" type="text/css"
	media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<script>
	jQuery(function() {
		$("#EMPNO").autocomplete("list.jsp");
	});
</script>

<%
	ArrayList<Retirement_ext_leave_credit> projEmpNolist = new ArrayList<Retirement_ext_leave_credit>();
	EmpOffHandler empOffHldr = new EmpOffHandler();
	projEmpNolist = empOffHldr.getEmployeeList();
	String empno ="";
	String retirementDate ="";
%>



<script type="text/javascript">
function creditLeave(EMPNO,retirementDate,leaveDays,credited,key)
{
	var No_of_leave = document.getElementById("creleaveday_"+key).value;
	if(No_of_leave =="" || No_of_leave =="0.00" || No_of_leave =="0"){
		alert("Please enter number of leave(CL)...You want to credit");
		return false;
	}
	
	try
	{
	var xmlhttp=new XMLHttpRequest();
 	var url="";
 	var response="";
 	
 	url="LeaveMasterServlet?action=retireExtLeave&empno="+EMPNO+"&noofLeave="+No_of_leave+"&retirementDate="+retirementDate+"&credited="+credited;

	xmlhttp.onreadystatechange=function()
	{
		if(xmlhttp.readyState==4)
		{
		
		response = xmlhttp.responseText;
		 if(response== 1 )
			{
			 alert("Retirement Leave (CL) Credited Successfully..!");
			window.location.href = "Retirement_ext_leave_credit.jsp";
			}
		 if(response!= 1 )
			{
			 alert("Retirement Leave Not Credited...");
				return false;
			}
		}
		
		};
 
	xmlhttp.open("POST", url, true);
	xmlhttp.send();
	}
	catch(e)
	{
		
	}
}
</script>

</head>
<body  style="overflow: hidden;">
	<%@include file="mainHeader.jsp"%>
	<!--  start nav-outer-repeat................................................................................................. START -->
	<%@include file="subHeader.jsp"%>

	<!-- start content-outer ........................................................................................................................START -->
	<div id="content-outer" style="overflow-y: scroll; max-height: 78%;">
		<!-- start content -->
		<div id="content">

			<!--  start page-heading -->
			<div id="page-heading">
				<h1>Retirement Extension Leave Credit</h1>
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
							<div id="table-content">
								<center>


										<table id="customers" width="800" align="center">
											<tr>
												<th>Retirement Extension Leave Credit</th>
											</tr>

											<tr>
												<td>

													<table width="900">
														<tr>
															<th width="135">Employee Code</th>
															<th width="320">Employee Name</th>
															<th width="155">No of Leave(CL) </th>
															<th width="165">Until Date</th>
															<th width="125">Action</th>
														</tr>
													</table>

												</td>


											</tr>
											<tr>
													<td><font size="5">
											<%
													if(projEmpNolist.size()<= 0 )
													{
													%>
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														There is No Employee...!
													<%
														}
														else{
		
													%>
													</font></td></tr>
											<tr>
												<td>
												
												
													<div id="scrolling"	style="height: 380px; overflow-y: scroll; max-width: 100%; background-color: #FFFFFF;"
														align="center">
														<table width="900">


															<%
																int i=0;
																for (Retirement_ext_leave_credit tbean : projEmpNolist) {
																	empno = tbean.getEmpno();
																	retirementDate = tbean.getRetirementDate();
																	 i++;
															%>
																	

															<tr align="center">
																<td width="135"><%=tbean.getEmpcode()%></td>
																<td width="320" align="left"><%=tbean.getName()%></td>
																<td width="155"><input type="text" id="creleaveday_<%=i%>" name="creleaveday" value="<%=tbean.getLeaveDays()%>"></td>
																<td width="165"><%=tbean.getBalDate()%></td>
																<%-- <input type="hidden" id="empNo"  name="empNo" value="<%=empno%>">
																<input type="hidden" id="retDT"  name="retDT" value="<%=retirementDate%>"> --%>
																<td width="125"><input type="button" id="save" name="save" value="Credit" onClick="creditLeave('<%=tbean.getEmpno()%>','<%=tbean.getRetirementDate()%>','<%=tbean.getLeaveDays()%>','<%=tbean.getCredited()%>','<%=i %>')"/>
																</td>
																
															</tr>
															<%
																}
															
															%>
															
														</table>

													</div>
														<%		
														}
		
														%>
												</td>


											</tr>
								 
										</table>

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


</body>
</html>