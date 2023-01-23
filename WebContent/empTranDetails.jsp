<%@page import="java.awt.Desktop.Action"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="payroll.Model.CodeMasterBean"%>
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
	LookupHandler lkph= new LookupHandler();
CodeMasterHandler CMH = new CodeMasterHandler();
ArrayList<CodeMasterBean> TRNCODE=new ArrayList<CodeMasterBean>();   
ArrayList<TranBean> tranlist=new ArrayList<TranBean>();
ArrayList<TranBean>  listbyEMPNO=new  ArrayList<TranBean> ();
TRNCODE=CMH.getNoAutocalCDList();


int trncd=0;
String select=new String();
LeaveMasterHandler obj=new LeaveMasterHandler();
String empName;
String action = request.getParameter("action")==null?"":request.getParameter("action");
System.out.println("action is"+action);
TranHandler trnh= new TranHandler();
LookupHandler lkh=new LookupHandler();
ArrayList<Lookup> emplist=new ArrayList<Lookup>(); 
emplist=lkh.getSubLKP_DESC("ET");	// ET is Code for Employee Type
int trn=0;
int keys=0;
int empno1=0;
int empno=0;
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
	if(action.equalsIgnoreCase("getdetails"))
	{
		String trncd1=request.getParameter("key");
		trn=Integer.parseInt(trncd1);
		select=request.getParameter("selected");
		session.setAttribute("trncd", trn);
		session.setAttribute("selectvalue", select);
		//ArrayList<TranBean> tranlist=new ArrayList<TranBean>();
	    	
	    	tranlist = trnh.getTranDetail(trn);
	    	session.setAttribute("list", tranlist);
	    	
	   }
	if(action.equalsIgnoreCase("edit"))
    {
		String[] str1=request.getParameter("key1").split(":");
	    empno =Integer.parseInt(str1[0]);
		int trncd1=Integer.parseInt(str1[1]);
		System.out.println("empno is "+empno);
		 
		listbyEMPNO=trnh.getTranByEmpno(empno,trncd1);
		//listbyEMPNO=(ArrayList)session.getAttribute("list");
		
    }
	if(session.getAttribute("trncd")!=null)
	{
		 keys=(Integer)session.getAttribute("trncd");
		 tranlist=trnh.getTranDetail(keys);
	}
	 
}
catch(Exception e)
{
	System.out.println("First Time Loading");
}
%>



<script type="text/javascript">
	function getTranDetails() {
		var type = document.getElementById("trncd").value;
		var desc = document.getElementById("trncd");
		var selected = desc.options[desc.selectedIndex].text;
		//alert(selected);
		window.location.href = "empTranDetails.jsp?action=getdetails&key="+ type + "&selected=" + selected;
	}

	function update(key) {
		window.showModalDialog("UpdateCodeMast.jsp?key=" + key,null,"dialogWidth:700px; dialogHeight:230px; scroll:off; status:off;dialogLeft:400px;dialogTop:300px; ");
		getTranCodes();
	}

	function editTranAmount(str) {
		var flag = confirm("Do you want to Edit this Amount");
		if (flag) {

			window.location.href = "empTranDetails.jsp?action=edit&key1=" + str;
		}

	}

	function checkFlag() {
		var f = parseInt(document.getElementById("flag1").value);

		if (f == 1) {
			alert("Tranaction Saved Successfully");

		}
		if (f == 2) {
			alert("Employee Already Exist");
		}
		if (f == 3) {
			alert("Record updated Successfully");
		}
	}

	function getcancel() {

		document.getElementById("d1").hidden = true;
	}
	
	function enableTextBoxes()
	{
		var boxes = document.getElementsByName("tranValue");
		for(var i=0; i<boxes.length; i++)
		{
			document.getElementById(boxes[i].id).disabled=false;
		}
		document.getElementById("tranSave").disabled=false;
	}
	

	function showdiv() {
		var e = document.getElementById("addN");
		if (e.style.display == 'none') {
			e.style.display = 'block';
		} else if (e.style.display == 'block') {
			e.style.display = 'none';

		}

	}

	/* function validate() {
		var EMPNO = document.getElementById("EMPNO").value;

		if (document.getElementById("EMPNO").value == "") {
			alert("Please Insert Employee Name");
			document.getElementById("EMPNO").focus();
			return false;
		}
		var atpos = EMPNO.indexOf(":");
		if (atpos < 1) {
			alert("Please Select Correct Employee Name");
			return false;
		}

		if (document.getElementById("amount").value == "") {
			alert("Please Enter Amount");
			return false;

		}
		if (isNaN(document.getElementById("amount").value)) {
			alert("Invalid data format.\n\nOnly numbers are allowed.");
			document.tranform.amount.focus();
			return false;
		}
	} */
</script>

</head>
<body onLoad="checkFlag()" style="overflow: hidden;">
	<%@include file="mainHeader.jsp"%>
	<!--  start nav-outer-repeat................................................................................................. START -->
	<%@include file="subHeader.jsp"%>

	<!-- start content-outer ........................................................................................................................START -->
	<div id="content-outer" style="overflow-y: scroll; max-height: 80%;">
		<!-- start content -->
		<div id="content">

			<!--  start page-heading -->
			<div id="page-heading">
				<h1>Employee Transaction</h1>
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

									<form action="TransactionServlet?action=editTranValues" method="post">

										<table id="customers" width="800" align="center">
											<tr>
												<th>Employee Transaction Details</th>
											</tr>

											<tr>
												<td align="center"><table>
														<tr>


															<td align="center">Allowances /Expenses <select
																name="trncd" id="trncd" onChange="getTranDetails()">
																	<option value="select" selected>Select</option>
																	<%
																		for (CodeMasterBean temp1 : TRNCODE) {
																			//System.out.println("TRNCD : "+temp1.getTRNCD()+"\tDISC : "+temp1.getDISC());

																			if (trn == temp1.getTRNCD() || keys == temp1.getTRNCD()) {
																				
																	%>
																	<option value="<%=temp1.getTRNCD()%>" selected><%=temp1.getDISC()%></option>

																	<%
																		} else {
																	%>
																	<option value="<%=temp1.getTRNCD()%>"><%=temp1.getDISC()%></option>
																	<%
																		}

																		}
																	%>
															</select>
															</td>
																<%try
											{
												if (session.getAttribute("trncd") != null) {
											%>
								
												<td align="center" ><input type="button"  class="myButton"
													value="Edit" onClick="enableTextBoxes()" height="150" width="150" />
												<input type="submit" id="tranSave" class="myButton"
													value="Save" disabled="disabled" /></td>
											
											<%
												} else {
											%>
											<!-- <tr>
												<td align="left" bgcolor="#2f747e">&nbsp;</td>
											</tr> -->
											<%
												}
											}
											catch(Exception e)
											{
												%>
												<script type="text/javascript">
													window.location.href="login.jsp?action=2";
												</script>
											<%
											}
											%>
															
														</tr>

													</table></td>
											</tr>

											<tr>
												<td>

													<table width="900">
														<tr>
															<th width="175">Employee Number</th>
															<th width="300">Employee Name</th>
															<th width="250">Amount/No of Days </th>
															<th width="175">TranDate</th>
															
														</tr>
													</table>

												</td>


											</tr>



											<tr>
												<td>
													<div id="scrolling"
														style="height: 425px; overflow-y: scroll; max-width: 1000px; background-color: #FFFFFF;"
														align="center">
														<table width="900">


															<%
																int index=0;
																for (TranBean tbean : tranlist) {
																	empName = obj.getempName(tbean.getEMPNO());
																	String str = tbean.getEMPNO()+":"+tbean.getTRNCD();
																	//trncd=tbean.getTRNCD();
															%>

															<tr align="center">
																<td width="175"><%=tbean.getEMPNO()%></td>
																<td width="300"><%=empName%></td>

																<td width="250"><input type="text" id="tranValue<%=++index %>" name="tranValue" 
																value="<%=tbean.getINP_AMT()==0?tbean.getCAL_AMT():tbean.getINP_AMT()%>" 
																disabled="disabled"></td>
																<td width="175"><%=tbean.getTRNDT()%></td>
																<%-- <td width="85" align="center"><input type="button" value="Edit"
																	onClick="editTranAmount('<%=str%>')" /></td> --%>
															</tr>
															<%
																}
															%>
														</table>

													</div>

												</td>


											</tr>
										<%-- 	<%try
											{
												if (session.getAttribute("trncd") != null) {
											%>
											<tr>
												<td align="center" bgcolor="#929292"><input type="button"
													value="Edit" onClick="enableTextBoxes()" height="150" width="150" />
												<input type="submit" id="tranSave"
													value="Save" disabled="disabled" /></td>
											</tr>
											<%
												} else {
											%>
											<tr>
												<td align="left" bgcolor="#2f747e">&nbsp;</td>
											</tr>
											<%
												}
											}
											catch(Exception e)
											{
												%>
												<script type="text/javascript">
													window.location.href="login.jsp?action=2";
												</script>
											<%
											}
											%> --%>
								 
											 <tr><td align="center">
											 	<div id="addN" style="display: none">
										<form name="tranform"action="TransactionServlet?action=addtran" method="post"<%-- onSubmit="return validate()--%>">

											<input type="hidden" name="trancd"
												value="<%=session.getAttribute("trncd")%>">

											<table id="customers" width="">
												<tr>
													<th colspan="5">Add new Transaction</th>
												</tr>
												<tr>
													<td>Employee NO</td>
													<td><input type="text" name="EMPNO" id="EMPNO"
														onClick="showHide()" title="Enter Employee No"></td>
													<td>Amount</td>
													<td><input type="text" name="amount" id="amount" /></td>
													<td align="center"><input type="submit" value="Save" />
														<input type="button" value="Cancel" onClick="showdiv()" /></td>
												</tr>

											</table>

										</form>
										</div>
										
										<div id="d1">
									
										<form action="TransactionServlet?action=edittran" method="post">
											<%
												if (action.equalsIgnoreCase("edit")) {
													for (TranBean tbean1 : listbyEMPNO) {
														int e = tbean1.getEMPNO();
														if (empno == e) {
											%>
											<table id="customers">
												<tr>
													<th colspan="6">Edit Employee Transaction</th>
												</tr>

												<tr>
													<td width="100">Employee Number</td>
													<td width="80"><%=tbean1.getEMPNO()%></td>
													<td width="80">TransactionCode</td>
													<td width="80"><%=tbean1.getTRNCD()%></td>

												</tr>
												<tr>
													<td width="80">Amount</td>
													<td width="80">
													<input type="text" name="amount" value="<%=tbean1.getINP_AMT()%>"></td>
													<td colspan="2">
													<input type="submit" value="Save" />
													<input type="button" value="Cancel" onClick="getcancel()" />
													</td>
												<tr>
												
									
												
												
											</table>

											<input type="hidden" name="empno" value="<%=tbean1.getEMPNO()%>"> 
											<input type="hidden" name="trncd" value="<%=tbean1.getTRNCD()%>">
											<%
												}
													}
												}
											%>
										</form>
									</div>
										
										
                                      </td></tr>

									 
											
										</table>


									</form>
									<br>

									 
									<input type="hidden" name="flag1" id="flag1" value="<%=flag%>">

									 



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
