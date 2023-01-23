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
UploadFileHandler ufh = new UploadFileHandler();
ArrayList<UploadFilesBean> files = new ArrayList<UploadFilesBean>();
files = ufh.showAllFiles();

String select=new String();
LeaveMasterHandler obj=new LeaveMasterHandler();
String empName;
String action = request.getParameter("action")==null?"":request.getParameter("action");
System.out.println("action is"+action);
EmpOffHandler empOffHldr = new EmpOffHandler();
EmployeeHandler emphdlr = new EmployeeHandler();

LookupHandler lkh=new LookupHandler();
ArrayList<Lookup> emplist=new ArrayList<Lookup>(); 
emplist=lkh.getSubLKP_DESC("ET");	// ET is Code for Employee Type

int empno1=0;
int empno=0;
int flag=-1;
String prjCode = "";
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
		prjCode = request.getParameter("PrjCode");
	    session.setAttribute("prjCode", prjCode);
	    files = ufh.showAllFiles_Project(Integer.parseInt(prjCode));
	    
	}
	
}
catch(Exception e)
{
	e.printStackTrace();
	System.out.println("First Time Loading");
}
%>



<script type="text/javascript">
	function getTranDetails() {
		var prjCode = document.getElementById("prjCode").value;
		//alert(selected);
		window.location.href = "ShowAllSiteFiles.jsp?action=getdetails&PrjCode="+prjCode;
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

</script>
<%
	String pageName = "ShowAllSiteFiles.jsp";
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
				<h1>Uploaded Files</h1>
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

									<form action="" method="post">

										<table id="customers" width="800" align="center">
											<tr>
												<th>Uploaded Files</th>
											</tr>
											<tr>
												<td width="800" align="center">
												<table style="width: 100% !important" >
														<tr>
															<td  align="center">BRANCH Code : <select 
																name="prjCode" id="prjCode" onChange="getTranDetails()">
																	<option value="select" selected>Select</option>
																	<%
    																ArrayList<EmpOffBean> list= new ArrayList<EmpOffBean>();
																	EmpOffHandler ofh = new EmpOffHandler();
    						 										list=ofh.getprojectCode();
    						 										int p;
    						 										if(prjCode==""||prjCode==null){
    						 											p=0;
    						 										}else{
    						 										 p= Integer.parseInt(prjCode);
    						 										}
    						 										for(EmpOffBean lkb :list)
    						 				 						{    						 											
    						 											if (p == lkb.getPrj_srno())  {
    						 				 						%>
    						 				      						<option value="<%=lkb.getPrj_srno()%>" title="<%=lkb.getSite_name()%>" selected><%=lkb.getPrj_name()%></option>  
    						 				     					<%
    						 				     					} else {
    						 				     					%>
    						 				     						<option value="<%=lkb.getPrj_srno()%>"title="<%=lkb.getSite_name()%>"><%=lkb.getPrj_name()%></option>
    						 				     					<%}
    						 				 						}	%>
															</select>
															</td>
															 </tr> 
													</table>
													</td>
											</tr>
											<tr>
												<td>
													<table width="900">
														<tr>
															<th width="93">Sr No.</th>
															<th width="310">File Name</th>
															<th width="200">Date </th>
															<th width="200">Uploaded By </th>
															<th width="175">Download</th>													
														</tr>
													</table>
												</td>
											</tr>
											<tr>
												<td>
													<div id="scrolling"	style="height: 425px; overflow-y: scroll; max-width: 1000px; background-color: #FFFFFF;"
														align="center">
														<table width="900">
															<%
																int index=0;													
																for (UploadFilesBean ufb : files) {
																	empName = obj.getempName(ufb.getEMPNO());																	
															%>
															<tr align="center">
																<td width="100"><%=ufb.getSRNO()%></td>
																<td width="300" align="left" ><%=ufb.getFILENAME()%></td>														
																<td width="200"><%=ufb.getUPDDT()%></td>
																<td width="200"><%=empName%></td>
																<td width="165"><a href="download?act=adminsite&f=<%=ufb.getSRNO()%>&p=<%=ufb.getPRJ_SRNO()%>">
																<img src="images/dwnld-bun.png" height="27" width="110" /></a></td>															
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