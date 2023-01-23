<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="payroll.DAO.LeaveMasterHandler"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="payroll.Model.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.ListIterator"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sanction</title>
<link rel="stylesheet" href="css/screen.css" type="text/css"
	media="screen" title="defaulrt" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />


<script type="text/javascript" src="js/datetimepicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<script>
	jQuery(function() {
		$("#EMPNO").autocomplete("list.jsp");
	});
</script>

<script>
	jQuery(function() {
		$("#EMPNO1").autocomplete("list.jsp");
	});
</script>
<%
	LeaveMasterHandler obj_SubL=new LeaveMasterHandler();
    String empName="";	
    LeaveMasterBean obj=new LeaveMasterBean();
   
    String action = request.getParameter("action") == null ? "": request.getParameter("action");
	ArrayList<LeaveMasterBean> showsanclist =null;
	ArrayList<LeaveMasterBean> showsanedlist =null;
	LookupHandler lh=new  LookupHandler(); 
	if (action.equals("searchpending"))
	{
		showsanclist = (ArrayList<LeaveMasterBean>) session.getAttribute("sanctionsearch");
		//showsanedlist = (ArrayList<LeaveMasterBean>) session.getAttribute("sanctioned");
	}
	
	if(action.equals("searchsanction"))
	{
		showsanedlist=(ArrayList<LeaveMasterBean>) session.getAttribute("sanctionsearch");
		
	}
	if(action.equals("searchAll"))
	{
		
		showsanedlist=(ArrayList<LeaveMasterBean>) session.getAttribute("sanctionsearch");
		
	}
	
	
	
	/*if (action.equals("sanclist"))
	{
		showsanclist = (ArrayList<LeaveMasterBean>) session.getAttribute("sancleavelist");
		//showsanedlist = (ArrayList<LeaveMasterBean>) session.getAttribute("sanctioned");
	}*/
%>


<script language="javascript">
	function validate() {

		var fromDate = document.searchappl.frmdate.value;
		var toDate = document.searchappl.todate.value;

		fromDate = fromDate.replace(/-/g, "/");
		toDate = toDate.replace(/-/g, "/");

		var str1=document.searchappl.EMPNO.value;
		var str2=document.searchappl.EMPNO1.value;
		var num1 =str1.split(":");
		var num2 =str2.split(":");
		var sno1="";
		var sno2="";
		for (var i = 0; i < num1.length; i++)
		{
			
		 	sno1=num1[i];
			 
		}
		
		for (var j = 0; j < num2.length; j++)
		{
			
			sno2=num2[j];
		}
		
		 
		if (document.searchappl.frmdate.value == "") {
			alert("please select fromdate");
			document.searchappl.frmdate.focus();
			return false;

		}

		if (document.searchappl.todate.value == "") {
			alert("please enter the todate");
			document.searchappl.todate.focus();
			return false;
		}

		var d1 = new Date(fromDate);

		var d2 = new Date(toDate);

		if (d1.getTime() > d2.getTime()) {
			alert("Invalid Date Range! fromdate Date cannot greater than TODate!");
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

		var EMPNO1 = document.getElementById("EMPNO1").value;
        
		if (document.getElementById("EMPNO1").value == "") {
			alert("Please Insert Employee Name");
			document.getElementById("EMPNO1").focus();
			return false;
		}
		var atpos=EMPNO1.indexOf(":");
		if (atpos<1)
		  {
		  alert("Please Select Correct Employee Name");
		  return false;
		  }
		
		if( sno1 > sno2)
	   {
			alert("please enter valid emp no");
			document.searchappl.EMPNO.focus();
			return false;
			
			}
		if (document.searchappl.type.value == "Default") {
			alert("please select search leave type");
			document.searchappl.type.focus();
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
<style type="text/css">
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
</style>

</head>
<body style="overflow: hidden;">
	<%@include file="mainHeader.jsp"%>
	<!--  start nav-outer-repeat................................................................................................. START -->
	<%@include file="subHeader.jsp"%>

	<!-- start content-outer ........................................................................................................................START -->
	<div id="content-outer" style="overflow-y:scroll; max-height:80%; ">
		<!-- start content -->
		<div id="content" >

			<div id="page-heading">

				<div id="content">
					<!--  start page-heading -->
					<div id="step-holder">
						<div class="step-light-right">&nbsp;</div>
						<div class="step-no-off">1</div>
						<div class="step-light-left">
							<a href="searchTransaction.jsp?action=search">Search Leave </a>
						</div>
						<div class="step-light-right">&nbsp;</div>
						<div class="step-no-off">2</div>
						<div class="step-light-left">
							<a href="showLeaveDetails.jsp?action=show">Leave Apply</a>
						</div>
						<div class="step-light-right">&nbsp;</div>
						<div class="step-no">3</div>
						<div class="step-light-left">
							<a href="leaveCancel.jsp?action=first">Leave Cancel</a>
						</div>
						<div class="step-dark-right">&nbsp;</div>

						<div class="step-no">4</div>
						<div class="step-dark-left">
							<a href="sanction.jsp?action=first">Sanction leave</a>
						</div>
						<div class="step-light-round">&nbsp;</div>

						<div class="clear"></div>

					</div>
				</div>
				<!-- end page-heading -->

				<table border="0" width="100%" cellpadding="0" cellspacing="0" id="content-table">
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
								<div id="table-content" align="center">

									<h3>leave Applications for sanction</h3>


                                   <table border="1" align="center" bordercolor="#2f747e"><tr align="center"><td >
									 
									<%
										if (action.equalsIgnoreCase("first")) {
									%>
									<form name="searchappl" action="leave?action=searchappln"
										method="post" onSubmit="return validate()">
										<table id="customers" style="margin-top:10px;">
											<tr>
												<th colspan="4">Search Application</th>
											</tr>
											<tr class="alt">
												<td>Fromdate</td>
												<td><input name="frmdate" size="20" id="frmdate"
													type="text" onBlur="if(value=='')" readonly="readonly"> &nbsp;<img
													src="images/cal.gif" align="absmiddle"
													style="vertical-align: middle; cursor: pointer;"
													onClick="javascript:NewCssCal('frmdate', 'ddmmmyyyy')" />
												</td>
												<td>Todate</td>
												<td><input name="todate" size="20" id="todate"
													type="text" onBlur="if(value=='')" readonly="readonly">&nbsp;<img
													src="images/cal.gif" align="absmiddle"
													style="vertical-align: middle; cursor: pointer;"
													onClick="javascript:NewCssCal('todate', 'ddmmmyyyy')" /></td>
											</tr>

											<tr class="alt">
												<td>From Empno</td>
												<td><input type="text" name="EMPNO" id="EMPNO"
													onClick="showHide()" title="Enter Employee No"></td>
												<td>To Empno</td>
												<td><input type="text" name="EMPNO1" id="EMPNO1"
													onClick="showHide()" title="Enter Employee No"></td>
											</tr>
											<tr class="alt">
												<td>Select</td>
												<td><select name="type">
														<option value="Default">Select</option>
														<option value="All">All</option>
														<option value="sanction">sanction</option>
														<option value="pending">pending</option>
												</select> &nbsp;&nbsp;&nbsp;&nbsp; <input type="submit"
													value="Search" /></td>
												<td colspan="2"></td>
											</tr>
										</table>
									</form>
									<%
										}
									%>
									
									<br>

									<%
										if (action.equalsIgnoreCase("searchpending")
												|| action.equalsIgnoreCase("searchsanction")
												|| action.equalsIgnoreCase("searchAll")) {
									%>
									  
									<form name="searchappl" action="leave?action=searchappln"
										method="post" onSubmit="return validate()">
										<table id="customers" style="margin-top:10px;">
											<tr>
												<th colspan="4">search Application</th>
											</tr>
											<tr class="alt">
												<td>Fromdate</td>
												<td><input name="frmdate" size="20" id="frmdate"
													type="text" value="<%=request.getAttribute("fromdate")%>" readonly="readonly">
													&nbsp;<img src="images/cal.gif" align="absmiddle"
													style="vertical-align: middle; cursor: pointer;"
													onClick="javascript:NewCssCal('frmdate', 'ddmmmyyyy')" />
												</td>
												<td>Todate</td>
												<td><input name="todate" size="20" id="todate"
													type="text" value="<%=request.getAttribute("todate")%>" readonly="readonly">&nbsp;<img
													src="images/cal.gif" align="absmiddle"
													style="vertical-align: middle; cursor: pointer;"
													onClick="javascript:NewCssCal('todate', 'ddmmmyyyy')" /></td>
											</tr>

											<tr class="alt">
												<td>FROM EMPNO</td>
												<td><input type="text" name="EMPNO" id="EMPNO"
													onClick="showHide()" title="Enter Employee No"
													value="<%=request.getAttribute("fromemp")%>"></td>
												<td>TO EMPNO</td>
												<td><input type="text" name="EMPNO1" id="EMPNO1"
													onClick="showHide()" title="Enter Employee No"
													value="<%=request.getAttribute("toemp")%>"></td>
											</tr>
											<tr class="alt">
												<td>SELECT</td>
												<td><select name="type">
														<option value="Default">Select</option>
														<option value="All">All</option>
														<option value="sanction">sanction</option>
														<option value="pending">pending</option>
												</select> &nbsp;&nbsp;&nbsp;&nbsp; <input type="submit"
													value="Search" /></td>
												<td colspan="2"></td>
											</tr>
										</table>
									</form>
									<br>


									<%
										}
									%>


									<%
										if (action.equals("searchpending"))

										{
									%>






									<form name="sanctionform" action="LeaveMasterServlet">

										<table id="customers">
											<tr>
												<td width="800"><table>
														<tr>
															<th width="60">&nbsp;</th>
															<th width="65">EMPNO</th>
															<th width="90">EMPNAME</th>
															<th width="60">APPLNO</th>
															<th width="90">TRANDATE</th>
															<th width="80">LEAVECD</th>
															<th width="85">FRMDATE</th>
															<th width="80">TODATE</th>
															<th width="80">DAYS</th>
															<th width="80">SANCTIONBY</th>


														</tr>
													</table></td>
											</tr>


											<tr>
												<td width="800">
													<div style="height: 200px; overflow-y: scroll; width: auto"
														id="div1">
														<table width="800" border="1">

															<%
																if (showsanclist.size() != 0) {

																		for (LeaveMasterBean sancb : showsanclist) {
																			empName = obj_SubL.getempName(sancb.getEMPNO());
															%>

															<tr class="row">
                                                                <input type="hidden"
																	name="action" value="sanction" />
																<td width="60"><input type="checkbox"
																	value="<%=sancb.getAPPLNO()+":"+sancb.getEMPNO()%>"
																	name="chkAppNo"></td>
																<td width="60"><%=sancb.getEMPNO()%></td>
																<td width="96"><%=empName%></td>
																<td width="60"><%=sancb.getAPPLNO()%></td>
																<td width="85"><%=sancb.getTRNDATE()%></td>
																<td width="80"><%=lh.getLKP_Desc("LEAVE",sancb.getLEAVECD())%></td>
																<td width="80"><%=sancb.getFRMDT()%></td>
																<td width="80"><%=sancb.getTODT()%></td>
																<td width="80"><%=sancb.getNODAYS()%></td>
																<td width="70"><%=sancb.getSANCAUTH()%></td>
															</tr>
															<%
																}
																	}

																	else {
															%>
															<tr>
																<td colspan="7" align="center">Currently their no
																	Any Leave application  
																</td>
															</tr>

															<%
																}
															%>
														</table>
													</div>
												</td>

											</tr>

											<tr bgcolor="#2f747e">
												<td><input type="submit" value="sanction" /></td>
											</tr>
                                            <input type="hidden" name="fromdate" value="<%=request.getAttribute("fromdate")%>">
                                        <input type="hidden" name="todate" value="<%=request.getAttribute("todate")%>">
                                        <input type="hidden" name="fromemp" value="<%=request.getAttribute("fromemp")%>">
                                        <input type="hidden" name="toemp" value="<%=request.getAttribute("toemp")%>">
										</table>
                                        
									</form>
									
									<%
										}
									%>


									<%
										if (action.equals("searchsanction"))

										{
									%>


									<table id="customers">
										<tr>
											<td width="800">
											<table>
													<tr>

														<th width="65">EMPNO</th>
														<th width="90">EMPNAME</th>
														<th width="80">APPLNO</th>
														<th width="90">TRANDATE</th>
														<th width="90">LEAVECD</th>
														<th width="85">FRMDATE</th>
														<th width="95">TODATE</th>
														<th width="95">DAYS</th>
														<th width="80">SANCTIONBY</th>


													</tr>
												</table>
											</td>
										</tr>


										<tr>
											<td width="800">
												<div style="height: 200px; overflow-y: scroll; width: auto"
													id="div1">
													<table width="800" border="1">

														<%
															if (showsanedlist.size() != 0) {

																	for (LeaveMasterBean sancb : showsanedlist) {
																		empName = obj_SubL.getempName(sancb.getEMPNO());
														%>

														<tr class="row">


															<td width="60"><%=sancb.getEMPNO()%></td>
															<td width="80"><%=empName%></td>
															<td width="60"><%=sancb.getAPPLNO()%></td>
															<td width="85"><%=sancb.getTRNDATE()%></td>
															<td width="80"><%=lh.getLKP_Desc("LEAVE",sancb.getLEAVECD())%></td>
															<td width="80"><%=sancb.getFRMDT()%></td>
															<td width="80"><%=sancb.getTODT()%></td>
															<td width="80"><%=sancb.getNODAYS()%></td>
															<td width="70"><%=sancb.getSANCAUTH()%></td>
														</tr>
														<%
															}
																}

																else {
														%>
														<tr>
															<td colspan="7" align="center">Currently their no
																Any Leave application</td>
														</tr>

														<%
															}
														%>
													</table>
												</div>
											</td>

										</tr>

										<tr bgcolor="#2f747e">
											<td>&nbsp;</td>
										</tr>

									</table>

									<%
										}
									%>



									<%
										if (action.equals("searchAll")) {
											ArrayList<LeaveMasterBean> showsanedlist2 = (ArrayList<LeaveMasterBean>) session
													.getAttribute("sanctionsearch");
									%>



									<table id="customers">
										<tr>
											<td width="800">
											<table>
													<tr>
														<th width="65">&nbsp;</th>
														<th width="65">EMPNO</th>
														<th width="90">EMPNAME</th>
														<th width="60">APPLNO</th>
														<th width="90">TRANDATE</th>
														<th width="80">LEAVECD</th>
														<th width="85">FRMDATE</th>
														<th width="80">TODATE</th>
														<th width="80">DAYS</th>
														<th width="80">SANCTIONBY</th>


													</tr>
												</table>
											</td>
										</tr>


										<tr>
											<td width="800">
												<div style="height: 200px; overflow-y: scroll; width: auto"
													id="div1">
													<table width="800" border="1">

														<%
															if (showsanedlist2.size() != 0) {

																	for (LeaveMasterBean sancb : showsanedlist2) {
																		empName = obj_SubL.getempName(sancb.getEMPNO());
														%>

														<%
															int status = sancb.getSTATUS();

																		if (status == 41) {
														%>

														<tr class="row" bgcolor="#00CC99">
															<td>Sanctioned</td>
															<%
																} else if (status == 1 || status == 2) {
															%>
														
														<tr class="row" bgcolor="#99FF99">
															<td>Pending</td>
															<%
																} else if (status == 98) {
															%>
														
														<tr class="row" bgcolor="#CC99CC">
															<td>Canceled</td>
															<%
																}
															%>
															<td width="60"><%=sancb.getEMPNO()%></td>
															<td width="96"><%=empName%></td>
															<td width="60"><%=sancb.getAPPLNO()%></td>
															<td width="85"><%=sancb.getTRNDATE()%></td>
															<td width="80"><%=sancb.getLEAVECD()%></td>
															<td width="80"><%=sancb.getFRMDT()%></td>
															<td width="80"><%=sancb.getTODT()%></td>
															<td width="80"><%=sancb.getNODAYS()	%></td>
															<td width="70"><%=sancb.getSANCAUTH()%></td>
														</tr>
														<%
															}
																}

																else {
														%>
														<tr>
															<td colspan="7" align="center">Currently their no
																Any Leave application</td>
														</tr>

														<%
															}
														%>
													</table>
												</div>
											</td>

										</tr>

										<tr bgcolor="#2f747e">
											<td>&nbsp;</td>
										</tr>

									</table>
                          
 
									<%
										}
									%>
								 </td></tr></table>
				
			</div>
			<!--  end table-content  -->

			<div class="clear"></div>
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
</div>
</body>
</html>