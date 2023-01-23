<%@page import="com.sun.java.swing.plaf.windows.resources.windows"%>
<%@page import="payroll.DAO.GradeHandler"%>
<%@page import="payroll.Model.VdaBean"%>
<%@page import="payroll.DAO.VdaDAO"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="org.jfree.data.time.Week"%>
<%@page import="payroll.DAO.EmpAttendanceHandler"%>
<%@page import="payroll.Model.Lookup"%>
<%@page import="payroll.DAO.LookupHandler"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/screen.css" type="text/css"
	media="screen" title="defaulrt" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />
<script type="text/javascript" src="js/jquery.datePicker.js"></script>

<script type="text/javascript" src="js/datetimepicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<link rel="stylesheet" href="css/jquery.treeview.css" />
<script src="js/jquery.js" type="text/javascript"></script>
<title>Vda Management</title>
<script type="text/javascript" >

function checkVda() {
	try
	{
	var f = parseInt(document.getElementById("flag1").value);

	if (f == 1) {
		alert("DA records Saved Successfully");

	}
	if (f == 2) {
		alert("Error in saving DA");

	}
	}
	catch(e)
	{
		
	}
	
}
function validateAdd() {
	var daApplyDate = document.vda.daApplyDate.value;
	daApplyDate = daApplyDate.replace(/-/g, "/");
	if (document.vda.daApplyDate.value == "") {
		alert("Please select DA Applicable Date");
		document.vda.daApplyDate.focus();
		return false;
	}
	var xmlHttp;

	if (window.XMLHttpRequest) { // Mozilla, Safari, ...
		xmlHttp = new XMLHttpRequest();
	} else if (window.ActiveXObject) { // IE
		try {
			xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			try {
				xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e) {
			}
		}
	}

		var vdaDate = document.vda.vdaDate.value;
		vdaDate = vdaDate.replace(/-/g, "/");
		if (document.vda.vdaDate.value == "") {
			alert("Please select Vda  Date");
			document.vda.vdaDate.focus();
			return false;
		}

		var firstMonthVda = document.vda.firstMonthVda.value;
		firstMonthVda = firstMonthVda.replace(/-/g, "/");
		if (document.vda.firstMonthVda.value == "") {
			alert("Please select first month for DA");
			document.vda.firstMonthVda.focus();
			return false;
		}

	
		//VALIDATING FOR DATES
			<%
			String action = request.getParameter("action") == null ? ""	: request.getParameter("action");
 
 VdaDAO vdaDao = new VdaDAO();
				 ArrayList<VdaBean> vdaList = new ArrayList<VdaBean>();
				 VdaBean vdaBeanForLastRecordDetails=vdaDao.getLastAddedVdaDetails();
				 vdaList = vdaDao.getAllAddedVdaDetails();
				 String vdaEndDate="";
	
				 if(action.equalsIgnoreCase("editVdaDetails")){
					 String batchId= request.getParameter("batchId");
					 String daApplicableDate= request.getParameter("daApplicableDate");
					  %>
					 window.location.href="VDA.jsp?action=editVdaDetails&"+<%=batchId%>+"&daApplicableDate="+daApplicableDate;
					<%	 }	
				 if(vdaList.size()!=0){
		                    vdaEndDate=vdaBeanForLastRecordDetails.getEndDate();
		                   
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
						SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MMM-yyyy"); 
						Date tempDate=new Date();
						tempDate=simpleDateFormat.parse(vdaEndDate); 
		                 
						vdaEndDate= outputDateFormat.format(tempDate);
						  
						vdaEndDate=vdaEndDate.replace("-"," ");
						//  System.out.println("----------------------converted date is"+vdaEndDate);
				
				 %>
		
		      //VDA APPLY DATE
		  
		var daApplyDateFirst=daApplyDate.replace("/"," ");
		var daApplyDateSecond=daApplyDateFirst.replace("/"," ");
		
		var daApplyDateFromFormFinal= new Date(daApplyDateSecond);
	
		  var  vdaEndDateFromJava="<%=vdaEndDate%>";
		  
		  var vdaEndDateFromJavaFinal= new Date(vdaEndDateFromJava);
		  
		  if (vdaEndDateFromJavaFinal.getTime() > daApplyDateFromFormFinal.getTime()) {
		  //alert(vdaEndDateFromJavaFinal);
		alert("Vda Applicable Date should not be earlier than Vda End date of previous batch 1 ");
		alert("Vda End date of previous batch is "+vdaEndDateFromJava);
		return false;
		  }
	
		
		// VDA DATE   
				var vdaDateFirst=vdaDate.replace("/"," ");
		var vdaDateSecond=vdaDateFirst.replace("/"," ");
		
		var vdaDateFromFormFinal= new Date(vdaDateSecond);
		
		  var  vdaEndDateFromJava="<%=vdaEndDate%>";
		  
		  var vdaEndDateFromJavaFinal= new Date(vdaEndDateFromJava);
		  
		  if (vdaEndDateFromJavaFinal.getTime() > vdaDateFromFormFinal.getTime()) {
		  
		alert("DA Date should not be earlier than Vda End date of previous batch 2 ");
		alert("DA End date of previous batch is "+vdaEndDateFromJava);
		return false;
		  }
		
		//FIRST  MONTH VDA
		var firstMonthVdaDateFirst=firstMonthVda.replace("/"," ");
		var firstMonthVdaDateSecond=firstMonthVdaDateFirst.replace("/"," ");
		
		var firstMonthVdaFromFormFinal= new Date(firstMonthVdaDateSecond);
		var  vdaEndDateFromJava="<%=vdaEndDate%>";
		  
		var vdaEndDateFromJavaFinal= new Date(vdaEndDateFromJava);
		  
		if (vdaEndDateFromJavaFinal.getTime() > firstMonthVdaFromFormFinal.getTime()) {
		  
		alert("First Month Vda Date should not be earlier than Vda End date of previous batch 3 ");
		alert("Vda End date of previous batch is "+vdaEndDateFromJava);
		return false;
		  }
	
			<%
			}
			if(!action.equalsIgnoreCase("editVdaDetails")){
				vdaList=vdaDao.checkForFinalizedRecords();
				if(vdaList.size()!=0){ %>
				alert("Since Previous Vda records are not finalised,you are not allowed to save new records");
				return false;
					<%}else{ %>
					
		
		var result = confirm("The details you are saving are going to affect everywhere. Do you want to save the Vda details? ");
		if (result == false){
			return false;
		}
		<%}	}%>
	}
	function inputLimiter(e, allow) {
		var AllowableCharacters = '';
		if (allow == 'Numbers') {
			AllowableCharacters = '1234567890.';
		}
		var k;
		k = document.all ? parseInt(e.keyCode) : parseInt(e.which);
		if (k != 13 && k != 8 && k != 0) {
			if ((e.ctrlKey == false) && (e.altKey == false)) {
				return (AllowableCharacters.indexOf(String.fromCharCode(k)) != -1);
			} else {
				return true;
			}
		} else {
			return true;
		}
	}


<%int flag=-1;
      try
		{
    		flag=Integer.parseInt(request.getParameter("flag1")==null?"0":request.getParameter("flag1"));
		}catch(Exception e){
		e.printStackTrace();	
		
		}%>
	

function calculateVda(batchId,daApplicableDate){
		var xmlHttp;

		if (window.XMLHttpRequest) { // Mozilla, Safari, ...
			xmlHttp = new XMLHttpRequest();
		} else if (window.ActiveXObject) { // IE
			try {
				xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
			} catch (e) {
				try {
					xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
				} catch (e) {
				}
			}
		}
	try
	{
		//alert(daApplicableDate);
		var x=confirm("Are you sure to calculate DA for the Batch Id Number: "+batchId+" ?");
		//alert(x);
		if(x){ 
			document.getElementById("process").hidden=false;
			
		<%	System.out.println("the date is:"+vdaBeanForLastRecordDetails.getDaApplicableDate());
			Boolean result=vdaDao.checkForRecordsInGradeFlag(vdaBeanForLastRecordDetails.getDaApplicableDate());
			System.out.println("result11111"+result);
		if(result){
			System.out.println("result00");%>
			url="VdaServlet?action=calculateVda&batchId="+batchId+"&daApplicableDate="+daApplicableDate;
		
			xmlHttp.onreadystatechange=function()
			{
				if(xmlHttp.readyState==4)
				{
					
					var response = xmlHttp.responseText;
					alert(response);
					document.getElementById("process").hidden=true;
				}
			};
			xmlHttp.open("GET", url, true);
			xmlHttp.send();
			
		<%}else{
		System.out.println("result03");%>
			return false;
			<%}%>
			}
	//	window.location.href="VdaServlet?action=calculateVda&batchId="+batchId+"&daApplicableDate="+daApplicableDate;
		else{
		alert("Grades are not entered yet");
		window.location.href="GradeMaster.jsp";
		}
	}
	
	catch(e)
	{
		
	}
	}
	
	//FOR EDITING VDA DETAILS
</script>

 <script type="text/javascript">
    $(function () {
        $(document).keydown(function (e) {
            return (e.which || e.keyCode) != 116;
        });
    });
    
    function getDatesOfNextMonths() {

		var firstMonthVda = document.getElementById("firstMonthVda").value;
	
		 var months = new Array("Jan", "Feb", "Mar", "Apr", "May", "Jun",
				"Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
		var month = firstMonthVda.substring(3, 6);
		var year = firstMonthVda.substring(7, 11);
		var secondMonthIndex = 0;
		var thirdMonthIndex = 0;
		var secondMonthYear = 0;
		var thirdMonthYear = 0;
		var index = months.indexOf(month);
		
			if (index == 11) {
			
			secondMonthYear = parseInt(year)+1;
			thirdMonthIndex = 1;
			thirdMonthYear = secondMonthYear;
		} else if (index == 10) {
		
			secondMonthIndex = 11;
			secondMonthYear = year;
			thirdMonthYear = parseInt(secondMonthYear)+1;
		} else {
			
			secondMonthIndex=index;
			secondMonthIndex =parseInt(secondMonthIndex)+1;
			secondMonthYear = year;
			thirdMonthIndex = parseInt(secondMonthIndex)+1 ;
			thirdMonthYear = year;
		}
		
		var secondMonthVda = "01-" + months[secondMonthIndex] +"-"+ secondMonthYear;
		var thirdMonthVda = "01-" + months[thirdMonthIndex] +"-"+ thirdMonthYear;
	
		document.getElementById("secondMonthVda").value = secondMonthVda;
		document.getElementById("thirdMonthVda").value = thirdMonthVda;


	}

</script>

 <%
 
 String pageName = "VDA.jsp";
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
	 e.printStackTrace();
 	//response.sendRedirect("login.jsp?action=0");
 }

 %>

</head>
<body onload="checkVda()">

	<%@include file="mainHeader.jsp"%>
	<!--  start nav-outer-repeat................................................................................................. START -->
	<%@include file="subHeader.jsp"%>
	<!-- start content-outer ........................................................................................................................START -->
	<div id="content-outer" style=" max-height: 80%;">
		<!-- start content -->
		<div id="content">
			<div id="page-heading">
				<h1>Add New DA</h1></div>
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
									<%
										int flag1 = -1;
										try {
											flag1 = Integer.parseInt(request.getParameter("flag1") == null ? "0": request.getParameter("flag1"));
										} catch (Exception e) {

											e.printStackTrace();

										}
									
								
										if(!action.equalsIgnoreCase("editVdaDetails")){ %>
								
									<form name="vda" action="VdaServlet?action=AddVda"
										method="post" onsubmit=" return validateAdd()">
									<table width="80%" id="customers">
											<th colspan="4">Add New DA</th>
											<tr>
												<td>DA APPLICABLE DATE : <font color="red"><b>*</b></font></td>
													<td> <input  class="form-control" name="daApplyDate" size="28"
													id="daApplyDate" type="text" onBlur="if(value=='')"
													placeholder="Click on calendar icon" readonly="readonly">
													&nbsp;<img src="images/cal.gif" align="middle"
													style="vertical-align: middle; cursor: pointer;"
													onClick="javascript:NewCssCal('daApplyDate', 'ddmmmyyyy')" />
												</td>
												<td>FIX BASIC : <font color="red"><b>*</b></font></td>
												<td> <input  class="form-control" type="text"
													id="fixBasic" name="fixBasic"   onkeypress="return inputLimiter(event,'Numbers')"
													placeholder="Enter mban dividend" required size="28"></td>
											</tr>


											<tr>
												<td>DA DIVISOR :<font color="red"><b>*</b></font></td>
													<td><input  class="form-control" type="text" id="daDivisor" name="daDivisor"  onkeypress="return inputLimiter(event,'Numbers')"
													placeholder="Enter mban divisor" required size="28">
												</td>
												<td>CONSUMER PRICE INDEX CPI : <font color="red"><b>*</b></font></td>
												
												<td>	<input  class="form-control" type="text" id="CPIValue" name="CPIValue"   onkeypress="return inputLimiter(event,'Numbers')"
													placeholder="Enter CPI Value" required size="28"></td>
											</tr>


											<tr >
												<td>DA DATE : <font color="red"><b>*</b></font></td>
													
													<td><input  class="form-control" name="vdaDate" size="28" id="vdaDate" type="text"
													onBlur="if(value=='')" placeholder="Click on calendar icon" readonly="readonly" 
													> &nbsp;<img
													src="images/cal.gif" align="middle"
													style="vertical-align: middle; cursor: pointer;"
													onClick="javascript:NewCssCal('vdaDate', 'ddmmmyyyy')" />
												</td>
												<td>CONSUMER FOOD PRICE INDEX CFPI : <font color="red"><b>*</b></font></td>
													<td colspan="3"><input  class="form-control" type="text" id="CFPIValue"
													name="CFPIValue"  onkeypress="return inputLimiter(event,'Numbers')"
													placeholder="Enter CFPI Value" required size="28">
												</td>
											</tr>


											<tr class="alt">
												<td><b>FIRST MONTH FOR DA: </b><font color="red"><b>*</b></font></td>
												<td>	 <input  class="form-control" name="firstMonthVda" size="28"
													id="firstMonthVda" type="text" onBlur="if(value=='')"
													placeholder="Click on calendar icon"
													onchange="return getDatesOfNextMonths()"
													> &nbsp;<img
													src="images/cal.gif" align="middle"
													style="vertical-align: middle; cursor: pointer;"
													onClick="javascript:NewCssCal('firstMonthVda', 'ddmmmyyyy')" />
												</td>

												<td><b>INDEX FOR FIRST MONTH: </b> <font color="red"><b>*</b></font></td>
											<td> <input  class="form-control" type="text"
													id="firstMonthIndex" name="firstMonthIndex"  onkeypress="return inputLimiter(event,'Numbers')"
													placeholder="Enter da index for first month" required
													size="28"></td>
											</tr>


											<tr  class="alt">
												<td><b>SECOND MONTH FOR DA: </b> 
													<td> <input  class="form-control" name="secondMonthVda" size="28"
													id="secondMonthVda" type="text" onBlur="if(value=='')"
													placeholder="On Selection Of First Month " readonly="readonly" value=""/>
													&nbsp;

												</td>
												<td><b>INDEX FOR SECOND MONTH:</b> <font color="red"><b>*</b></font></td>
												<td><input  class="form-control" type="text" id="secondMonthIndex" name="secondMonthIndex"  onkeypress="return inputLimiter(event,'Numbers')"
													placeholder="Enter da index for second month" required
													size="28"></td>
											</tr>



											<tr class="alt">
												<td><b>THIRD MONTH FOR DA: </b> 
													<td> <input  class="form-control" name="thirdMonthVda" size="28"
													id="thirdMonthVda" type="text" onBlur="if(value=='')"
													placeholder="On Selection Of First Month " readonly="readonly" value=""/>
													&nbsp;

												</td>
												<td><b>INDEX FOR THIRD MONTH:</b> <font color="red"><b>*</b></font></td>
												<td>	<input  class="form-control" type="text" id="thirdMonthIndex"
													name="thirdMonthIndex"  onkeypress="return inputLimiter(event,'Numbers')"
													placeholder="Enter da index for third month" required
													size="28"></td>
											</tr>

					
											<tr>
												<td colspan="4" align="center"><input  class="myButton" type="submit"
													value="Save"> <input  class="myButton" type="reset" value="Reset"></td>
											</tr>
											<%
												EmpAttendanceHandler empAttendanceHandler = new EmpAttendanceHandler();
												String todaysDate = empAttendanceHandler.getServerDate();
												String loggedEmployee = session.getAttribute("EMPNO").toString();
											%>
											
										</table>
										<input name="todaysDate" id="todaysDate" type="hidden"
												value="<%=todaysDate%>" />
											<input name="loggedEmployee" id="loggedEmployee"
												type="hidden" value="<%=loggedEmployee%>" />
									</form>
									<% }
									else{
										ArrayList<VdaBean> vdaEList=new ArrayList<VdaBean>(); 
							VdaBean vdaBean=new VdaBean();
							String firstMonth="",secondMonth="",thirdMonth="",vdaDate="";
							float firstMonthIndex=0.0f,secondMonthndex=0.0f,thirdMonthIndex=0.0f;
							float fixBasic=0.0f,daDivisor=0.0f,CPIValue=0.0f,vdaPercentage=0.0f;
										int batchId=Integer.parseInt(request.getParameter("batchId"));
										SimpleDateFormat outDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
									
										Date tempDate=new Date();
										SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
						//DECLARATIONS ENDS HERE				
									String daApplicableDate=request.getParameter("daApplicableDate");
									tempDate=simpleDateFormat.parse(daApplicableDate);
									daApplicableDate=outDateFormat.format(tempDate);
									
								
								/* 	System.out.println("the batchId value is "+batchId);
									System.out.println("the daApplicableDate value is "+daApplicableDate); */
									vdaEList=vdaDao.getVdaDetailsForEditing(daApplicableDate);
							//		System.out.println("SIZE OF VDAELIST IS : "+vdaEList.size());
				if(vdaEList.size()!=0){
						
						vdaBean=vdaEList.get(0);
						firstMonth=vdaBean.getMonth();
						tempDate=simpleDateFormat.parse(firstMonth);
						firstMonth=outDateFormat.format(tempDate);
						firstMonthIndex=vdaBean.getIndex();
						vdaDate=vdaBean.getVdaDate();
						tempDate=simpleDateFormat.parse(vdaDate);
						vdaDate=outDateFormat.format(tempDate);
						vdaPercentage=vdaBean.getCFPIValue();
						fixBasic=vdaBean.getFixBasic();
						daDivisor=vdaBean.getDaDivisor();
						CPIValue=vdaBean.getCPIValue();
					
						
						
						
						vdaBean=vdaEList.get(1);
						secondMonth=vdaBean.getMonth();
						tempDate=simpleDateFormat.parse(secondMonth);
						secondMonth=outDateFormat.format(tempDate);
						secondMonthndex=vdaBean.getIndex();
						vdaBean=vdaEList.get(2);
						thirdMonth=vdaBean.getMonth();
						tempDate=simpleDateFormat.parse(thirdMonth);
						thirdMonth=outDateFormat.format(tempDate);
						thirdMonthIndex=vdaBean.getIndex();
										
			//							System.out.println("vdalist   :"+vdaBean.getMonth());
						//DELETING PREVIOUS RECORDS
		    vdaDao.deleteLastSavedVdaRecords(daApplicableDate); 
										
									   // vdaBeanForLastRecordDetails=vdaDao.getLastAddedVdaDetails();
						}
				
										
						//	 vdaBean= vdaDao.getVdaDetailsForEditing(daApplicableDate);
									
									
									%>
										<form name="vda" action="VdaServlet?action=AddVda"
										method="post" onsubmit=" return validateAdd()">
									<table width="80%" id="customers">
											<th colspan="4">Edit DA</th>
											<tr>
												<td>DA APPLICABLE DATE : <font color="red"><b>*</b></font></td>
													<td> <input  class="form-control" name="daApplyDate" size="28" value="<%=daApplicableDate%>"
													id="daApplyDate" type="text" onBlur="if(value=='')"
													placeholder="Click on calendar icon" readonly="readonly">
													&nbsp;<img src="images/cal.gif" align="middle"
													style="vertical-align: middle; cursor: pointer;"
													onClick="javascript:NewCssCal('daApplyDate', 'ddmmmyyyy')" />
												</td>
												<td>FIX BASIC : <font color="red"><b>*</b></font></td>
												<td> <input  class="form-control" type="text" value="<%=fixBasic%>"
													id="fixBasic" name="fixBasic"   onkeypress="return inputLimiter(event,'Numbers')"
													placeholder="Enter mban dividend" required size="28"></td>
											</tr>


											<tr>
												<td>DA DIVISOR :<font color="red"><b>*</b></font></td>
													<td><input  class="form-control" value="<%=daDivisor%>" 
													type="text" id="daDivisor" name="daDivisor"  onkeypress="return inputLimiter(event,'Numbers')"
													placeholder="Enter mban divisor" required size="28">
												</td>
												<td>CONSUMER PRICE INDEX CPI: <font color="red"><b>*</b></font></td>
												
												<td>	<input  class="form-control" value="<%=CPIValue%>"
												 type="text" id="CPIValue" name="CPIValue"   onkeypress="return inputLimiter(event,'Numbers')"
													placeholder="Enter CPI Value" required size="28"></td>
											</tr>


											<tr >
												<td>VDA DATE : <font color="red"><b>*</b></font></td>
													
													<td><input  class="form-control" value="<%=vdaDate%>" name="vdaDate" size="28" id="vdaDate" type="text"
													onBlur="if(value=='')" placeholder="Click on calendar icon" readonly="readonly" 
													> &nbsp;<img
													src="images/cal.gif" align="middle"
													style="vertical-align: middle; cursor: pointer;"
													onClick="javascript:NewCssCal('vdaDate', 'ddmmmyyyy')" />
												</td>
												<td>CONSUMER FOOD PRICE INDEX CFPI : <font color="red"><b>*</b></font></td>
													<td colspan="3"><input  class="form-control"  value="<%=vdaPercentage%>"
													type="text" id="CFPIValue"
													name="CFPIValue"  onkeypress="return inputLimiter(event,'Numbers')"
													placeholder="Enter CFPI Value" required size="28">
												</td>
											</tr>


											<tr class="alt">
												<td><b>FIRST MONTH FOR DA: </b><font color="red"><b>*</b></font></td>
												<td>	 <input  class="form-control" name="firstMonthVda" size="28" value="<%=firstMonth%>"
													id="firstMonthVda" type="text" onBlur="if(value=='')"
													placeholder="Click on calendar icon"
													onchange=" return getDatesOfNextMonths()"
													> &nbsp;<img
													src="images/cal.gif" align="middle"
													style="vertical-align: middle; cursor: pointer;"
													onClick="javascript:NewCssCal('firstMonthVda', 'ddmmmyyyy')" />
												</td>

												<td><b>INDEX FOR FIRST MONTH: </b> <font color="red"><b>*</b></font></td>
											<td> <input  class="form-control" type="text" value="<%=firstMonthIndex %>"
													id="firstMonthIndex" name="firstMonthIndex"  onkeypress="return inputLimiter(event,'Numbers')"
													placeholder="Enter vda index for first month" required
													size="28"></td>
											</tr>


											<tr  class="alt">
												<td><b>SECOND MONTH FOR DA: </b> 
													<td> <input  class="form-control" name="secondMonthVda" size="28" value="<%=secondMonth%>"
													id="secondMonthVda" type="text" onBlur="if(value=='')"
													placeholder="On Selection Of First Month " readonly="readonly" value=""/>
													&nbsp;

												</td>
												<td><b>INDEX FOR SECOND MONTH:</b> <font color="red"><b>*</b></font></td>
												<td><input  class="form-control" type="text" value="<%=secondMonthndex%>"
												id="secondMonthIndex" name="secondMonthIndex"  onkeypress="return inputLimiter(event,'Numbers')"
													placeholder="Enter vda index for second month" required
													size="28"></td>
											</tr>



											<tr class="alt">
												<td><b>THIRD MONTH FOR DA: </b> 
													<td> <input  class="form-control" name="thirdMonthVda" size="28" value="<%=thirdMonth%>"
													id="thirdMonthVda" type="text" onBlur="if(value=='')"
													placeholder="On Selection Of First Month " readonly="readonly" value=""/>
													&nbsp;

												</td>
												<td><b>INDEX FOR THIRD MONTH:</b> <font color="red"><b>*</b></font></td>
												<td>	<input  class="form-control" type="text" id="thirdMonthIndex" value="<%=thirdMonthIndex%>"
													name="thirdMonthIndex"  onkeypress="return inputLimiter(event,'Numbers')"
													placeholder="Enter vda index for third month" required
													size="28"></td>
											</tr>

	


											<tr>
												<td colspan="4" align="center"><input  class="myButton" type="submit"
													value="Save New Again"> <input  class="myButton" type="reset" value="Reset"></td>
											</tr>
											<%
												EmpAttendanceHandler empAttendanceHandler = new EmpAttendanceHandler();
												String todaysDate = empAttendanceHandler.getServerDate();
												String loggedEmployee = session.getAttribute("EMPNO").toString();
											%>
											
										</table>
										<input name="todaysDate" id="todaysDate" type="hidden"
												value="<%=todaysDate%>" />
											<input name="loggedEmployee" id="loggedEmployee"
												type="hidden" value="<%=loggedEmployee%>" />
									</form>
										<%
									} %>
								
								<!-- VDA EDIT-->	
              
						<div>
					<table>
					
					</table>
						
			</div>
						<br><br>
					<div align="center" class="imptable classictbl" style="width: 100%;overflow: hidden;">

							<%	if(vdaDao.checkForFinalizedRecords().size()!=0 && !action.equalsIgnoreCase("editVdaDetails")){%>
							<h3>Edit DA Details Of These Batches</h3>
							<%
							
													vdaList=vdaDao.checkForFinalizedRecords();
													int count=1;
												
												%>
													<table  style="position: static;  max-width: 100%;">
											<tr style="position: static; height: 30px;" align="center"
												bgcolor="#1F5FA7">
                                                <td width="100" style="color: white;">SR. No.</td>
												<td width="150" style="color: white;">BATCH ID</td>

												<td width="200" style="color: white;"> MONTH</td>
												
											    <td width="200" style="color: white;">EDIT</td>
											    <td width="200" style="color: white;">CALCULATE</td>
											</tr>
										</table>
												<% 	for (VdaBean vdaBean : vdaList) { %>
										<table style="position: static; max-width: 100%; " >
											
											<tr align="center" style="position: static; height: 30px;">
									
                                                   <td width="100"><%=count%></td>
												<td width="150" ><%=vdaBean.getBatchId()%></td>
												<%
													int monthNumber = Integer.parseInt(vdaBean.getMonth().substring(5, 7));
													
															String monthString = monthNumber == 1 ? "Jan"	  : monthNumber == 2 ? "Feb"
																			               : monthNumber == 3 ? "Mar"  : monthNumber == 4 ? "Apr"
																						   : monthNumber == 5 ? "May"  : monthNumber == 6 ? "Jun"
																						   : monthNumber == 7 ? "Jul"	  : monthNumber == 8 ? "Aug"
																							: monthNumber == 9 ? "Sep" : monthNumber == 10 ? "Oct"
																							: monthNumber == 11 ? "Nov" : "Dec";
															String yearString = vdaBean.getMonth().substring(0, 4);
															String finalString = monthString + "-" + yearString;
												%>
												<td width="200"><%=finalString%></td>
												<td width="200">
												 <input class="myButton" type="button" style="width: 75;" value="Edit" onclick="window.location='VDA.jsp?action=editVdaDetails&batchId=<%=vdaBean.getBatchId()%>&DaApplicableDate=<%=vdaBean.getDaApplicableDate()%>'">
											</td>
											 <td width="200"  style="color: white;">
												 <input class="myButton" type="button" style="width: 125;" value="Calculate" onclick="calculateVda('<%=vdaBean.getBatchId()%>','<%=vdaBean.getDaApplicableDate()%>')"> 
												  
												   </td> 
											</tr>
												</table>	
											<%
											 count++;}
												
													}
												%>
											</tr>
										</table>
						</div>
					
									<br><br>
							<!-- 		<center>
							 <input id="inp" type="button" style="margin-left: 5px" value="Click here to print VDA chart "  class="myButton" 
							 	 onclick="location.href='VDAChart.jsp';" />
								</center> -->
							<div id="process"  style="z-index: 1000;background-color:white;height: 100%;width: 100%;top: 0px;left: 0px;position: fixed;opacity:0.8;" hidden=true; >
				<div align="center" style="padding-top: 20%;">
				
				<h1> Calculation takes 3 to 4 Min....Be Patience</h1>
				
				<img alt="" src="images/process.gif">
				</div>
			</div>
							
							<input type="hidden" name="flag1" id="flag1" value="<%=flag1%>">
							
							<!--  end table-content  -->

						</div> <!--  end content-table-inner ............................................END  -->
						</div>
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
			<div class="clear"></div>


		</div>

	</div>

</body>
</html>