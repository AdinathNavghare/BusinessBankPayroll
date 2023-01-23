<%@page import="payroll.Core.Calculate"%>
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
<title>&copy; DTS3</title>
<link rel="stylesheet" href="css/screen.css" type="text/css"
	media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>

<%
	LookupHandler lkph= new LookupHandler();
CodeMasterHandler CMH = new CodeMasterHandler();
ArrayList<CodeMasterBean> TRNCODE=new ArrayList<CodeMasterBean>();   
ArrayList<TranBean> tranlist=new ArrayList<TranBean>();
ArrayList<TranBean>  listbyEMPNO=new  ArrayList<TranBean> ();
TRNCODE=CMH.getNoAutocalCDList();

int eno = (Integer)session.getAttribute("EMPNO");

EmpOffHandler eoffhdlr = new EmpOffHandler();
EmpAddrHandler addhdlr = new EmpAddrHandler();
EmpOffBean eoffbn = new EmpOffBean();
 
eoffbn = eoffhdlr.getEmpOfficAddInfo(Integer.toString(eno));
ArrayList<TranBean> tran = new ArrayList<TranBean>();
ArrayList<EmpAddressBean> addbn = new ArrayList<EmpAddressBean>();
tran = eoffhdlr.getEmpList(Integer.toString(eoffbn.getPrj_srno()));
EmpAddressBean eaddbn = new EmpAddressBean();
int site_id = eoffbn.getPrj_srno();
session.setAttribute("Prj_Srno", site_id);
session.setAttribute("emplist", tran);
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

String date1=trnh.getSalaryDate();
String[] d={"","",""};
	float days=Calculate.getDays(date1);
	d=date1.split("-");
%>



<script type="text/javascript">
	function UploadFile() {
		window.showModalDialog("uploadattchment.jsp?upfile=site",null,"dialogWidth:690px; dialogHeight:130px; scroll:off; status:off;dialogLeft:400px;dialogTop:300px; ");
		//window.location.href="attachment?list=show";
	}
	function ViewFile() {
		window.showModalDialog("ShowFiles.jsp?",null,"dialogWidth:800px; dialogHeight:300px; scroll:auto; status:off;dialogLeft:400px;dialogTop:300px; ");
		//window.location.href="attachment?list=show";
	}
	function getTranDetails() {
		var type = document.getElementById("trncd").value;
		var desc = document.getElementById("trncd");
		var selected = desc.options[desc.selectedIndex].text;
		//alert(selected);
		window.location.href = "empTranDetails.jsp?action=getdetails&key="
				+ type + "&selected=" + selected;
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

	function enableTextBoxes() {
		/* var mobded = document.getElementsByName("mobded");
		for(var i=0; i<mobded.length; i++)
		{
			document.getElementById(mobded[i].id).disabled=false;
		} */
		var lopded = document.getElementsByName("lopded");
		for ( var i = 0; i < lopded.length; i++) {
			document.getElementById(lopded[i].id).disabled = false;
		}
		document.getElementById("tranSave").disabled = false;
	}

	function showdiv() {
		var e = document.getElementById("addN");
		if (e.style.display == 'none') {
			e.style.display = 'block';
		} else if (e.style.display == 'block') {
			e.style.display = 'none';

		}

	}
	function inputLimiter(e,allow) {
		  var AllowableCharacters = '';
		  if (allow == 'Numbers'){AllowableCharacters='.1234567890';}
		  var k;
		  k=document.all?parseInt(e.keyCode): parseInt(e.which);
		  if (k!=13 && k!=8 && k!=0){
		    if ((e.ctrlKey==false) && (e.altKey==false)) {
		      return (AllowableCharacters.indexOf(String.fromCharCode(k))!=-1);
		    } else {
		      return true;
		    }
		  } else {
		    return true;
		  }
		}	
	
	function fn(id)
	{
		var val=document.getElementById(id).value;
		var days=<%=days%>;
		if(val=="")
			{
			document.getElementById(id).style.borderColor="red";
				val=parseFloat(window.prompt("Enter Days between 0 to "+days+" \n\n\t\t Zero(0) otherwise !"));
				
			document.getElementById(id).value=val;
			
			}
		else
		{
			if( val>days)
			{
				document.getElementById(id).style.borderColor="red";
			val=parseFloat(window.prompt("Enter Days between 0 to "+days+" \n\n\t\t Zero(0) otherwise !"));
			}
		}
		while(isNaN(val)||val>days)
			{
			document.getElementById(id).style.borderColor="red";
		val=parseFloat(window.prompt("Enter Days between 0 to "+days+" \n\n\t\t Zero(0) otherwise !"));
			}
		document.getElementById(id).style.borderColor="white";
		document.getElementById(id).value=val;
	}
	
	
	function Validate()
	{
		
		var flag=true;
		var lop = document.getElementsByName("lopded");
		
		for(var i=0; i<lop.length; i++)
		{
			
			 if(document.getElementById(lop[i].id).value=="" ||document.getElementById(lop[i].id).value==null)
				 {
				 
				 alert("Please! Enter some value OR 0");
				 document.getElementById(lop[i].id).style.borderColor="red";
				document.getElementById(lop[i].id).focus();
				flag=false;
				break;
				 
				 }
		}
		return flag;
	}
</script>
<%
	String pageName = "SiteEmpTran.jsp";
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

									<form action="TransactionServlet?action=editSiteTranValues"
										method="post" onsubmit="return Validate()">

										<table id="customers" width="800" align="center">
											<tr>
												<th>Employee Transaction Details</th>
											</tr>

											<tr>
												<td>
													<table>
														<tr>
															<td>BRANCH Name : <%=eoffbn.getPrj_name()==null?"":eoffbn.getPrj_name()%></td>
															<td align="center" >
															<input	type="button" class="myButton" value="Edit" onClick="enableTextBoxes()"
																height="150" width="150" /> <input type="submit"
																id="tranSave"  class="myButton" value="Save" disabled="disabled" /></td>
															<td><h2><%=d.length!=0?d[1]+"-"+d[2]:""%> </h2> </td>
														</tr>
													</table>
												</td>

											</tr>

											<tr>
												<td>

													<table width="900">
														<tr>
															<th width="190">Emp No.</th>
															<th width="326">Emp Name</th>
															<!-- <th width="190">Mobile No </th>
															<th width="175">Mob_Ded_Amt</th> -->
															<th width="187">Absent Days</th>

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
																int index = 0;

																for (TranBean tbean : tran) {
																	empName = obj.getempName(tbean.getEMPNO());
																	String str = tbean.getEMPNO() + ":" + tbean.getTRNCD();
																	//TranBean tbn223 = trnh.getTranByEmpno1(tbean.getEMPNO(), 223);
																	TranBean tbn301 = trnh.getTranByEmpno1(tbean.getEMPNO(), 301);
																	//trncd=tbean.getTRNCD();
															%>

															<tr align="center">
																<td width="175"><%=tbean.getEMPNO()%></td>
																<td width="300" align="left"><%=empName%></td>
																<%-- <td width="250" align="left"><%=addhdlr.getEmpAddress1(tbean.getEMPNO()) %></td> --%>
																<%-- <td width="175"><input type="text" id="mobded<%=++index %>" name="mobded" 
																 value="<%=tbn223.getINP_AMT()==0?0:tbn223.getINP_AMT()%>"  
																disabled="disabled"></td> --%>
																<td width="175">
																<input type="text"	id="lopded<%=++index%>" name="lopded" maxlength="4"
																	value="<%=tbn301.getINP_AMT() == 0 ? "" : tbn301.getINP_AMT()%>"
																	disabled="disabled" onkeypress="return inputLimiter(event,'Numbers')" onblur="fn(this.id)"></td>
															</tr>
															<%
																}
															%>
														</table>

													</div>
												</td>
											</tr>
										</table>
									</form>
									<br>
									<table width="300">
									<tr>
									<td>
									<form action=""
										method="post" onsubmit="return UploadFile()">
										<input type="submit"  class="myButton" value="Upload File" />
									</form>
									</td>
									<td>
									<form action=""
										method="post" onsubmit="return ViewFile()">	
										<input type="submit" class="myButton" value="View Upload Files" />
									</form>
									</td>
									</tr>
									</table>

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
