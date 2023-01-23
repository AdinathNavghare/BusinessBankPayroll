<%@page import="java.util.ArrayList"%>
<%@page import="payroll.Core.ReportDAO"%>
<%@page import="payroll.DAO.EmpAttendanceHandler"%>
<%@page import="payroll.Model.UploadAttendanceBean"%>
<%@ page contentType="text/html; charset=iso-8859-1" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Upload Attendance</title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />


<script type="text/javascript" src="js/datetimepicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>

<script type="text/javascript">

var xmlhttp;
var url="";
if(window.XMLHttpRequest)
	{
	xmlhttp = new XMLHttpRequest;
}
else
{
	xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
}



function checkfile(id) {
	 var validExts = new Array(".xlsx", ".xls");
    var fileExt = id.value;
    fileExt = fileExt.substring(fileExt.lastIndexOf('.'));
    if (validExts.indexOf(fileExt) < 0) {
      alert("Invalid file selected, valid files are of " +
               validExts.toString() + " types.");
      document.getElementById("file").value=null;
      return false;
    }
    else return true; 
}

function validate()
{
	alert("Hello Validate");
	//document.getElementById('process1').hidden=false;
	
	var fromDate=document.getElementById("date").value;
	alert("DATE : "+fromDate);
   	var toDate=document.getElementById("date").value;
   	var appn=0000000; // for apply leave as flagg

	try
	{
	
		var xmlhttp=new XMLHttpRequest();
		var url="";
		var response2="";
	
		//url="LMS?action=betDate&empno="+empno+"&frmdate="+fromDate+"&todate="+toDate;
	 	url="UploadAttendanceServlet?action=attendanceDate&date="+fromDate+"&date="+toDate+"&appNo="+appn;
	
		xmlhttp.onreadystatechange=function()
		{
			if(xmlhttp.readyState==4)
			{
				response2 = xmlhttp.responseText;
			 	if(response2>0 )
				{
				 	alert("You have already taken attendance for this month...");
				 	alert("Can not Generate excel...."+window.location.replace("UploadAttendance.jsp"));
		
					 fromDate=document.getElementById("date").value="";
					 toDate=document.getElementById("date").value="";
					 return false;
				}
			 	else
			 	{
					checkURL();
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


function checkURL()
{
	alert("Hello checkURL()");
	
	document.getElementById('process1').hidden=false;
	
	var fromDate=document.getElementById("date").value;
	var toDate=document.getElementById("date").value;
   	var appn=0000000; 
	try
	{
		var xmlhttp=new XMLHttpRequest();
		var url="";
		var response2="";
	
		url="UploadAttendanceServlet?action=TakeAttendance&date="+fromDate;
		xmlhttp.onreadystatechange=function()
		{
			if(xmlhttp.readyState==4)
			{
				response2 = xmlhttp.responseText;
				initAll();
			}
		};
		xmlhttp.open("POST", url, true);
		xmlhttp.send();
	}
	catch(e)
	{
		
	}
	
}



function setTRNCD(){
	
	var type	= document.getElementById("selecttrncd");
	var valueT	=type.options[type.selectedIndex].value;
	document.getElementById("TRNCD").value= valueT;
	
}
</script>


 <script type="text/javascript" src="js/MONTHPICK/jquery-ui.min.js"></script>
    <link rel="stylesheet" type="text/css" media="screen" href="css/MONTHPICK/jquery-ui.css">
    <script type="text/javascript">
    $(function() {
        $('.date-picker').datepicker( {
            changeMonth: true,
            changeYear: true,
            showButtonPanel: true,
            dateFormat: 'M-yy',
            onClose: function(dateText, inst) { 
                var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
                var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
                $(this).datepicker('setDate', new Date(year, month, 1));
                $(':focus').blur();
                
            },
            beforeShow : function(input, inst) {
                var datestr;
                if ((datestr = $(this).val()).length > 0) {
                    year = datestr.substring(datestr.length-4, datestr.length);
                    month = jQuery.inArray(datestr.substring(0, datestr.length-5), $(this).datepicker('option', 'monthNamesShort'));
                    $(this).datepicker('option', 'defaultDate', new Date(year, month, 1));
                    $(this).datepicker('setDate', new Date(year, month, 1));
                    
                }
            }
        });
        
    });
    </script>
    <style>
    .ui-datepicker-calendar {
        display: none;
        }
    </style>
    
    <script>
    function checkflag(){
    	debugger;
    	var fg	=	document.getElementById("flag").value;
    	if(fg=="1")
    		alert("Excel uploaded Successfully.");
    	else if(fg=="2")
    		alert("There is some Problem While uploading Excel.");
    	else if(fg=="3")
    		alert("File is Empty or Not Attached File Properly ! Try Again.");
    }
    
   /*  function check()
    {
    	var date	=	document.getElementById("date").value;
    	if(date=="")
   		{
   		alert( " Please select the proper month");
   		return false;
   		}
    	else
    		{
    		try {
    			debugger;
    			var xmlhttp = new XMLHttpRequest();
    			var url = "";
    			var response = "";
    			var dat = document.getElementById('datemonth').value;

    			url = "AttendanceServlet?action=chkAttendDAte&datee=" + dat;
    			let
    			s = confirm("Confirm to Approve Attendance of employee..."
    					+ empcode);/*+ window.location.replace("ProjectViewAll.jsp")  */
    			/*if (s) {
    				xmlhttp.onreadystatechange = function() {
    					if (xmlhttp.readyState == 4) {
    						response = xmlhttp.responseText;

    						if (response == 1) {
    							alert("Attendance Appproved Successfully!!!"+ window.location.replace("EmpAttendanceG.jsp"))
    							return false;
    						} else if(response == 2) {
    							alert("Attendance not complted ...please check it");
    						}else 
    							{
    							alert("Attendance doesn't Approved...please check");
    							}
    					}
    				};
    				xmlhttp.open("POST", url, true);
    				xmlhttp.send();
    			}
    		} catch (e) {

    		}
    		}
    } */
    
    /* function checkfile()
    {
    	debugger;
    	var status	=	document.getElementById("status").value;
    	if(status==0)
   		{
   		alert( "cannot generate file...Please select the currunt month");
   		return false;
   		}
    } */
   
/*        function checkAttendDate()
    {
    	try {
			debugger;
			var xmlhttp = new XMLHttpRequest();
			var url = "";
			var response = "";
			var dat = document.getElementById("date_download").value;
alert("DATE :"+dat);
			url = "UploadAttendanceServlet?action=chkAttendDAte&date="+dat;
			 
			  
				xmlhttp.onreadystatechange = function() {
					if (xmlhttp.readyState == 4) {
						response = xmlhttp.responseText;
						if (response == 1) {
					
							return true;
						} else 
						{
							alert("Can not Generate excel...."+window.location.replace("UploadAttendance.jsp"));
							return false
						}
					}
				}
				xmlhttp.open("POST", url, true);
				
				xmlhttp.send();
			 
		} catch (e) {

		}
    	
    }  
     
 */    
    
	function ViewReport1()
	{
		alert("ViewReport1");
		var typ = document.getElementById("Desgn").value;
		var reporttype = document.getElementById("ReportType").value;
		var employeetype = document.getElementById("emptype").value;
		
		if(typ==0){
			alert("please Select Report Format");
			return false;
		}
		
		if (document.getElementById("date_download").value=="")
			{
			alert("please Select Month");
			return false;
			}
		if(reporttype==0){
			alert("please Select Report Type");
			return false;
		}
		
		document.getElementById("viewPdf").hidden=true;
		document.getElementById("process").hidden=false;
		var format;
		var date ="01-"+document.getElementById("date_download").value;
		//var btn = document.getElementById("button").value;
		var frmt = document.getElementById("Excel").checked;
		
		
		var txt= document.getElementById("txt").checked;
		
		format="xls";
		    // url="ReportServlet?action=attendanceList&date="+date+"&typ="+typ+"&frmt="+format+"&reptype="+reporttype+"&emptype="+employeetype;
		     url="ReportServlet?action=attendanceList;
		alert("URL"+url);
		
		xmlhttp.onreadystatechange=function()
		{
			if (xmlhttp.readyState==4 && xmlhttp.status==200)
			{
				var response=xmlhttp.responseText;
	        	document.getElementById("viewPdf").innerHTML=response;
	        	document.getElementById("process").hidden=true;
	        	document.getElementById("viewPdf").hidden=false;
			}
		};
		xmlhttp.open("GET", url, true);
		xmlhttp.send();
		alert("URL 1"+url);
		}
    </script>





<%-- <%
String flag = request.getParameter("flag")==null?"0":request.getParameter("flag");
String UID = session.getAttribute("UID")==null?"0":session.getAttribute("UID").toString();
String ROLEID = session.getAttribute("ROLEID").toString();
System.out.print("ROLEID==>"+ROLEID);
%> --%>

 </head>
  <%-- <%
 
 String pageName = "UploadAttendance.jsp";
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
 	response.sendRedirect("login.jsp?action=0");
 }

 %>  --%>
 
 <%
 
 String  flag = request.getParameter("flag"); 
 
 %>
 
<body style="overflow: hidden;" onload="checkflag()"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer">
<!-- start content -->
<div id="content" style="overflow-y: scroll; max-height: 700px;">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Attendance System</h1>
	</div>
	<!-- end page-heading -->

	<table border="0" width="100%" cellpadding="0" cellspacing="0" id="content-table">
	<tr>
		<th rowspan="3" class="sized"><img src="images/shared/side_shadowleft.jpg" width="20" height="300" alt="" /></th>
		<th class="topleft"></th>
		<td id="tbl-border-top">&nbsp;</td>
		<th class="topright"></th>
		<th rowspan="3" class="sized"><img src="images/shared/side_shadowright.jpg" width="20" height="300" alt="" /></th>
	</tr>
	<tr>
		<td id="tbl-border-left"></td>
		<td>
		<!--  start content-table-inner ...................................................................... START -->
		<div id="content-table-inner">
		
			<!--  start table-content  -->
			
			<!-- <div id="process1"  style="z-index: 1000;background-color:white;height: 100%;width: 100%;top: 0px;left: 0px;position: fixed;opacity:0.8; transition-duration: 500000s;" hidden=true;  > -->
			<div id="process1"  style="z-index: 1000;background-color:white;height: 100%;width: 100%;top: 0px;left: 0px;position: fixed;opacity:0.8; " hidden=true;  >
				<div align="center" style="padding-top: 20%; " >
				<!-- <img alt="" src="images/process.gif"> -->
				<img alt="" src="images/DataTransfer.gif">
				</div>
			</div>
			
			<div id="viewPdf"  hidden="true">
 </div>
			
			
			<div id="table-content">
			<center>
			<!-- <h2>Profession Tax Statement</h2> -->
			
<table border="1" id="customers">
	<tr>


	<!-- <td  colspan="2" ALIGN="center"> -->
	<td  colspan="6" ALIGN="center">
<!-- 			<form name="professionTax" action="UploadAttendanceServlet?action=TakeAttendance" method="post" onsubmit="return  validate()" enctype="multipart/form-data"> -->
			<form name="professionTax"    method="post"  enctype="multipart/form-data">
	
<table border="1" id="customers"  >
	<tr >
		<th>Attendance Transfer</th>
		</tr>
		  <tr class="alt">
		  
				<td height="120" align="center">
				<table>
					<tr class="alt">
						<td>Month For Attendance</td>
					 <td colspan="2">
					 <!-- <input type="text" id="date" name="date" readonly="readonly" class="date-picker" placeholder="Click here for Calender"/> -->
					 <input type="text" name="date" id="date" readonly="readonly" class="date-picker" placeholder="Click here for Calender"/>
					 </td>
					</tr>
					
					<tr>
						<td colspan="2" align="center">
							<input type="submit" name="before" class="myButton"	value="Take Attendance"  onclick="validate()" title='Click here to Take Attendance'  />
							</td>	
					</tr>
					<input type="hidden" id="flag" value="<%=flag %>">
				</table>

			  </td>
			</tr>
</table>
</form>
</td>
</tr>

<tr height="50"> </tr>


<tr>

<%-- 	<td  colspan="4" ALIGN="right">
			<form name="professionTax" action="UploadAttendanceServlet?action=uploadAttendance" 
			method="post" onsubmit="return  validate()" enctype="multipart/form-data">
<table border="1" id="customers"  >
	<tr >
		<th>Upload Attendance</th>
		</tr>
		  <tr class="alt">
				<td height="120" align="center">
				<table>
					<tr class="alt">
						<td>Month For Attendance</td>
					 <td colspan="2">
					 <!-- <input type="text" id="date" name="date" readonly="readonly" class="date-picker" placeholder="Click here for Calender"/> -->
					 <input type="text" name="Adate" id="Adate" readonly="readonly" class="date-picker" placeholder="Click here for Calender"/>
					 </td>
					</tr>
					
					 <tr>
					 <td colspan="2">
					 <input type="file" id="file" name="file" onchange="checkfile(this);"></input></td>
					 </tr>
					<tr>
						<td colspan="2" align="center">
							<input type="submit" name="before" class="myButton"	value="Upload" title='Click here to Upload Deduction'   />
							</td>	
					</tr>
					<input type="hidden" id="flag" value="<%=flag %>">
				</table>
			  </td>
			</tr>
</table>
</form>
</td>
 --%>
 
<td colspan="6" align="center">

<!-- <form name="emplistForm"  action="ReportServlet" method="post"  onSubmit="return check()"> -->
<form name="emplistForm">
			<table border="1" id="customers" align="center" >
			<tr>
				<th>Download Employee Attendance List</th>
			<tr>
			<tr class="alt">
				<td align="center">
				<!-- <input type="hidden" id="action" name="action" value="forearndeductionemployeeallcallist"></input> -->
			<!-- 	<input type="hidden" id="action" name="action" value="attendanceList" ></input> -->
				<table align="center">
			
				
<%-- 				<tr class="alt" height="30" align="left" id="selecttype" onchange="enableded()">
				<input type="hidden" name="status" id="status" value="<%=request.getParameter("stat") %>"> 
						<td>Select Type</td>
						<td align="left">
							<select id="type" name="type" >
								<option value="attendancesheet"> Attendance Sheet</option>
							</select>
						</td>
					</tr>
					<tr class="alt" height="30" align="letf" id="selectdate"> 
						<td>Select Attendance Month</td>
						<td align="left">
							<input name="date_download" id="date_download" readonly="readonly" class="date-picker" placeholder="Click here for Calender"/>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<input type="submit" class="myButton" value="Get List" onclick="checkAttendDate()" />
						</td>
					</tr>
 --%>
 
 <tr class="alt" height="30" align="letf" id="selectdate"> 
						<td>Select Attendance Month</td>
						<td align="left">
							<input name="date_download" id="date_download" readonly="readonly" class="date-picker" placeholder="Click here for Calender"/>
						</td>
					</tr>
					<tr class="alt" height="30" align="left" id="selecttype" onchange="enableded()">
				<td>Report Format </td>
						<td>
							<select name="Desgn" id="Desgn">  
      					  	<option value="ID">Income & Deduction</option> 
     			      		</select>
     			      	</td>
					</tr>
					<tr class="alt" height="30">
					      	<td>Report Type </td>
     			      	<td>
     			      	<select name="ReportType" id="ReportType">  
      					  	<option value="0">Select</option>
      					  	<option value="paytran_stage">Finalize</option>
      					  </select>
     			      		</td>
     			</tr>
					<tr class="alt" height="30">
						<td>Emp Type</td>
     			      		<td>
     			      			<select name="emptype" id="emptype" >  
      					 			<option value="0">Select</option>  
			    					 	  <option value="0">ALL</option>
			     				</select>
     			      		</td>
     			      	</tr>
					<tr class="alt" height="30">
					<td><input type="radio" name="format" id="Excel" value="Excel" >&nbsp;&nbsp;Excel&nbsp;
							<input type="radio" name="format" id="Pdf" value="Pdf" checked>&nbsp;&nbsp;Pdf
							<input type="radio" name="format" id="txt" value="txt">&nbsp;&nbsp;Txt					
						</td>	
						</tr>
					<tr>
						<td align="center"><input type="submit"	value="GET REPORT" onclick="ViewReport1()"/>
											</td>
						
					</tr>
 					
					
					
					
					
					
				</table>

			  </td>
			</tr>
		</table>
	</form>	 
</td>
</tr>

<tr height="50"></tr>


<TR>
			
<!-- 			<td colspan="19">
				 &nbsp;
									<h2>P : Present  &nbsp;&nbsp;    A : Absent  &nbsp;&nbsp;   
									WO : Week Off &nbsp;&nbsp;  HD : Half Day &nbsp;&nbsp; HO : Holiday  &nbsp;&nbsp; LT : Late Mark &nbsp;&nbsp;    ROFF : Rotation Off  &nbsp;&nbsp;   
									COFF : Compesite Off  &nbsp;&nbsp;  NJ : New Joining &nbsp;&nbsp;  T : Onsite </h2> 
			</td>
 -->			
			
			<TR ALIGN="center">
			
			<td colspan="8">
				<Div style="background-color: lightgery; overflow-y: scroll; width: 1150px; height: 31em">

										<!-- <form name="saveAttend" action ="ReportServlet?action=saveAttend" method="post"> -->
										<table border="1" id="customers">
									<!--	<TR>
			
			<td colspan="19">
				 &nbsp;
									<h2>P : Present  &nbsp;&nbsp;    A : Absent  &nbsp;&nbsp;   
									WO : Week Off &nbsp;&nbsp;  HD : Half Day &nbsp;&nbsp; HO : Holiday  &nbsp;&nbsp; LT : Late Mark &nbsp;&nbsp;    ROFF : Rotation Off  &nbsp;&nbsp;   
									COFF : Compesite Off  &nbsp;&nbsp;  NJ : New Joining </h2> 

			</td>
			</TR> -->
											<tr class="alt">
										<th colspan="34" align="center">
										All Employee Attendance List 
										</th>
										</tr>
										
											<!-- 
											<tr class="alt">
												<th >SR NO.</th> <th>EMPCODE</th> <th>Employee Name</th> <th>1</th> <th>2</th> <th>3</th> <th>4</th> <th>5</th> 
												<th>6</th> <th>7</th> <th>8</th> <th>9</th> <th>10</th> <th>11</th> <th>12</th> <th>13</th> <th>14</th> <th>15</th> 
											</tr>
											<tr>
												<th></th>
												<th>Month Days</th> <th>Absent Days</th> <th>16</th> <th>17</th> <th>18</th> <th>19</th> <th>20</th> <th>21</th> 
												<th>22</th> <th>23</th> <th>24</th> <th>25</th> <th>26</th> <th>27</th> <th>28</th> <th>29</th> <th>30</th> <th>31</th> 
											</tr>
											 -->
											
											<tr class="alt">
												<th >SR NO.</th> <th>EMPCODE</th> <th>Employee Name</th>
												<th>1</th> <th>2</th> <th>3</th> <th>4</th> <th>5</th> <th>6</th> <th>7</th> <th>8</th> 
												<th>9</th> <th>10</th> <th>11</th> <th>12</th> <th>13</th> <th>14</th> <th>15</th> 
												<th>16</th> <th>17</th> <th>18</th> <th>19</th> <th>20</th> <th>21</th> <th>22</th> <th>23</th> 
												<th>24</th> <th>25</th> <th>26</th> <th>27</th> <th>28</th> <th>29</th> <th>30</th> <th>31</th> 
											</tr>
											
											
											</tr>

											<%
											EmpAttendanceHandler ah = new EmpAttendanceHandler();
											// ah.getEmpAttendance();
											String act = "allAttend";
											ArrayList<UploadAttendanceBean> Emp_bean =  ah.getEmpAttendance1(act);
												int srno = 0;
												String date = " ";
												if (Emp_bean.isEmpty()) {
											%>
											<tr>
												<td colspan="35" align="center" >--No Record
													Found--</td>
											</tr>
											<%
												} else {
													for (UploadAttendanceBean UA : Emp_bean) {
														srno++;
														  date = UA.getDateG();
											%>

											<tr>
												<form name="saveAttend" action="ReportServlet?action=saveAttend" method="post" >
													<%-- onsubmit="validate('<%=UA.getEMPCODE()%>')"> --%>
													

													<tr>
														<input type="hidden" name="NDay" id="NDay" value="<%=date.substring(8)%>">
														<td    >
															<%-- <input type="checkbox" name="<%=UA.getEMPCODE()+"CB"%>" id="<%=UA.getEMPCODE()+"CB"%>"> --%>
															<font size="3"><%=srno%></font>
														</td>

														<%-- <td  >
														<input type="button" class="myButton" name="<%=UA.getEMPCODE() + ":edt"%>" id="<%=UA.getEMPCODE() + ":edt"%>" VALUE="EDIT"onClick="editAttendace('<%=UA.getEMPCODE() + ":edt"%>')">
														</td> --%>

														<td   ><font size="3"><%=UA.getEMPCODE()%></font></td>
														<input type="hidden" name="empno" id="empno"value="<%=UA.getEMPNO()%>">
														<input type="hidden" name="datemonth" id="datemonth" value="<%=date%>">
														<input type="hidden" name="empcode" id="empcode" value="<%=UA.getEMPCODE()%>">

														<td    >
															<font size="3"><%=UA.getName()%></font>
														</td>
														<td   >
														<input type="text" name="<%=UA.getEMPCODE() + "1"%>" id="<%=UA.getEMPCODE() + "1"%>" value="<%=UA.getDays1()%>" style="width: 2.5em; text-transform: uppercase;" disabled></td>
														<td   ><input type="text" name="<%=UA.getEMPCODE() + "2"%>" id="<%=UA.getEMPCODE() + "2"%>" value="<%=UA.getDays2()%>"
															style="width: 2.5em; text-transform: uppercase;" disabled></td>
														<td   ><input type="text" name="<%=UA.getEMPCODE() + "3"%>" id="<%=UA.getEMPCODE() + "3"%>" value="<%=UA.getDays3()%>"
															style="width: 2.5em; text-transform: uppercase;" disabled></td>
														<td   ><input type="text" name="<%=UA.getEMPCODE() + "4"%>" id="<%=UA.getEMPCODE() + "4"%>" value="<%=UA.getDays4()%>"
															style="width: 2.5em; text-transform: uppercase;" disabled></td>
														<td   ><input type="text" name="<%=UA.getEMPCODE() + "5"%>" id="<%=UA.getEMPCODE() + "5"%>" value="<%=UA.getDays5()%>"
															style="width: 2.5em; text-transform: uppercase;" disabled></td>
														<td   ><input type="text" 	name="<%=UA.getEMPCODE() + "6"%>" 	id="<%=UA.getEMPCODE() + "6"%>" value="<%=UA.getDays6()%>"
															style="width: 2.5em; text-transform: uppercase;" disabled></td>
														<td   ><input type="text" name="<%=UA.getEMPCODE() + "7"%>" id="<%=UA.getEMPCODE() + "7"%>" value="<%=UA.getDays7()%>"
															style="width: 2.5em; text-transform: uppercase;" disabled></td>
														<td   ><input type="text" name="<%=UA.getEMPCODE() + "8"%>" id="<%=UA.getEMPCODE() + "8"%>" value="<%=UA.getDays8()%>"
															style="width: 2.5em; text-transform: uppercase;" disabled></td>
														<td   ><input type="text" name="<%=UA.getEMPCODE() + "9"%>" id="<%=UA.getEMPCODE() + "9"%>" value="<%=UA.getDays9()%>"
															style="width: 2.5em; text-transform: uppercase;" disabled></td>
														<td   ><input type="text" name="<%=UA.getEMPCODE() + "10"%>" id="<%=UA.getEMPCODE() + "10"%>" value="<%=UA.getDays10()%>"
															style="width: 2.5em; text-transform: uppercase;" disabled></td>
														<td   ><input type="text" name="<%=UA.getEMPCODE() + "11"%>" id="<%=UA.getEMPCODE() + "11"%>" value="<%=UA.getDays11()%>"
															style="width: 2.5em; text-transform: uppercase;" disabled></td>
														<td   ><input type="text" name="<%=UA.getEMPCODE() + "12"%>" id="<%=UA.getEMPCODE() + "12"%>" value="<%=UA.getDays12()%>"
															style="width: 2.5em; text-transform: uppercase;" disabled></td>
														<td   ><input type="text" name="<%=UA.getEMPCODE() + "13"%>" id="<%=UA.getEMPCODE() + "13"%>" value="<%=UA.getDays13()%>"
															style="width: 2.5em; text-transform: uppercase;" disabled></td>
														<td   ><input type="text" name="<%=UA.getEMPCODE() + "14"%>" id="<%=UA.getEMPCODE() + "14"%>"value="<%=UA.getDays14()%>"
															style="width: 2.5em; text-transform: uppercase;" disabled></td>
														<td   ><input type="text" name="<%=UA.getEMPCODE() + "15"%>" id="<%=UA.getEMPCODE() + "15"%>" value="<%=UA.getDays15()%>"
															style="width: 2.5em; text-transform: uppercase;" disabled></td>
													<%-- <td  ><input type="text"  name="<%=UA.getEMPCODE() + "16"%>" id="<%=UA.getEMPCODE() + "16"%>" value="<%=UA.getDays16()%>"
													style="width: 2.5em; text-transform: uppercase;" disabled></td> --%>
													</tr>
											<tr>
												<td style="border-bottom-color: #1F5FA7;"></td>
<td style="border-bottom-color: #1F5FA7;"><font SIZE="3" ><%=date.substring(8)%></font></td>
												<td  style="border-bottom-color: #1F5FA7;"><font SIZE="3" ><%=EmpAttendanceHandler.getEmpAbsentDay(UA.getEMPCODE(),date) %></font></td>
												
													<%-- <td  >
														<input type="button" name="aprrove" id="aprrove" class="myButton" VALUE="APPROVE" onClick="approveAttendance('<%=UA.getEMPCODE()%>')">
													</td> --%>

												

												
												
												<td  style="border-bottom-color: #1F5FA7;"><input type="text"
													name="<%=UA.getEMPCODE() + "16"%>"
													id="<%=UA.getEMPCODE() + "16"%>" value="<%=UA.getDays16()%>"
													style="width: 2.5em; text-transform: uppercase;" disabled></td>

												<td style="border-bottom-color: #1F5FA7;" ><input type="text"
													name="<%=UA.getEMPCODE() + "17"%>"
													id="<%=UA.getEMPCODE() + "17"%>" value="<%=UA.getDays17()%>"
													style="width: 2.5em; text-transform: uppercase;" disabled></td>
												<td  style="border-bottom-color: #1F5FA7;"><input type="text"
													name="<%=UA.getEMPCODE() + "18"%>"
													id="<%=UA.getEMPCODE() + "18"%>" value="<%=UA.getDays18()%>"
													style="width: 2.5em; text-transform: uppercase;" disabled></td>
												<td style="border-bottom-color: #1F5FA7;"><input type="text"
													name="<%=UA.getEMPCODE() + "19"%>"
													id="<%=UA.getEMPCODE() + "19"%>" value="<%=UA.getDays19()%>"
													style="width: 2.5em; text-transform: uppercase;" disabled></td>
												<td style="border-bottom-color: #1F5FA7;" ><input type="text"
													name="<%=UA.getEMPCODE() + "20"%>"
													id="<%=UA.getEMPCODE() + "20"%>" value="<%=UA.getDays20()%>"
													style="width: 2.5em; text-transform: uppercase;" disabled></td>
												<td style="border-bottom-color: #1F5FA7;" ><input type="text"
													name="<%=UA.getEMPCODE() + "21"%>"
													id="<%=UA.getEMPCODE() + "21"%>" value="<%=UA.getDays21()%>"
													style="width: 2.5em; text-transform: uppercase;" disabled></td>
												<td style="border-bottom-color: #1F5FA7;" ><input type="text"
													name="<%=UA.getEMPCODE() + "22"%>"
													id="<%=UA.getEMPCODE() + "22"%>" value="<%=UA.getDays22()%>"
													style="width: 2.5em; text-transform: uppercase;" disabled></td>
												<td  style="border-bottom-color: #1F5FA7;"><input type="text"
													name="<%=UA.getEMPCODE() + "23"%>"
													id="<%=UA.getEMPCODE() + "23"%>" value="<%=UA.getDays23()%>"
													style="width: 2.5em; text-transform: uppercase;" disabled></td>
												<td  style="border-bottom-color: #1F5FA7;"><input type="text"
													name="<%=UA.getEMPCODE() + "24"%>"
													id="<%=UA.getEMPCODE() + "24"%>" value="<%=UA.getDays24()%>"
													style="width: 2.5em; text-transform: uppercase;" disabled></td>
												<td style="border-bottom-color: #1F5FA7;" ><input type="text"
													name="<%=UA.getEMPCODE() + "25"%>"
													id="<%=UA.getEMPCODE() + "25"%>" value="<%=UA.getDays25()%>"
													style="width: 2.5em; text-transform: uppercase;" disabled></td>
												<td  style="border-bottom-color: #1F5FA7;"><input type="text"
													name="<%=UA.getEMPCODE() + "26"%>"
													id="<%=UA.getEMPCODE() + "26"%>" value="<%=UA.getDays26()%>"
													style="width: 2.5em; text-transform: uppercase;" disabled></td>
												<td  style="border-bottom-color: #1F5FA7;"><input type="text"
													name="<%=UA.getEMPCODE() + "27"%>"
													id="<%=UA.getEMPCODE() + "27"%>" value="<%=UA.getDays27()%>"
													style="width: 2.5em; text-transform: uppercase;" disabled></td>
												<%
													if (date.substring(8).equalsIgnoreCase("28")) {
												%>
												<td  style="border-bottom-color: #1F5FA7;"><input type="text"
													name="<%=UA.getEMPCODE() + "28"%>"
													id="<%=UA.getEMPCODE() + "28"%>" value="<%=UA.getDays28()%>"
													style="width: 2.5em; text-transform: uppercase;" disabled></td>
												<td  style="border-bottom-color: #1F5FA7;"></td>
												<td style="border-bottom-color: #1F5FA7;" ></td>
												<td  style="border-bottom-color: #1F5FA7;"></td>
												<%
													} else if (date.substring(8).equalsIgnoreCase("29")) {
												%>
												<td  style="border-bottom-color: #1F5FA7;"><input type="text"
													name="<%=UA.getEMPCODE() + "28"%>"
													id="<%=UA.getEMPCODE() + "28"%>" value="<%=UA.getDays28()%>"
													style="width: 2.5em; text-transform: uppercase;" disabled></td>
												<td  style="border-bottom-color: #1F5FA7;"><input type="text"
													name="<%=UA.getEMPCODE() + "29"%>"
													id="<%=UA.getEMPCODE() + "29"%>" value="<%=UA.getDays29()%>"
													style="width: 2.5em; text-transform: uppercase;" disabled></td>
												<td  style="border-bottom-color: #1F5FA7;"></td>
												<td  style="border-bottom-color: #1F5FA7;"></td>
												<%
													} else if (date.substring(8).equalsIgnoreCase("30")) {
												%>
												<td  style="border-bottom-color: #1F5FA7;"><input type="text"
													name="<%=UA.getEMPCODE() + "28"%>"
													id="<%=UA.getEMPCODE() + "28"%>" value="<%=UA.getDays28()%>"
													style="width: 2.5em; text-transform: uppercase;" disabled></td>
												<td  style="border-bottom-color: #1F5FA7;"><input type="text"
													name="<%=UA.getEMPCODE() + "29"%>"
													id="<%=UA.getEMPCODE() + "29"%>" value="<%=UA.getDays29()%>"
													style="width: 2.5em; text-transform: uppercase;" disabled></td>
												<td  style="border-bottom-color: #1F5FA7;"><input type="text"
													name="<%=UA.getEMPCODE() + "30"%>"
													id="<%=UA.getEMPCODE() + "30"%>" value="<%=UA.getDays30()%>"
													style="width: 2.5em; text-transform: uppercase;" disabled></td>
												<td style="border-bottom-color: #1F5FA7;" ></td>

												<%
													} else if (date.substring(8).equalsIgnoreCase("31")) {
												%>
												<td  style="border-bottom-color: #1F5FA7;"><input type="text"
													name="<%=UA.getEMPCODE() + "28"%>"
													id="<%=UA.getEMPCODE() + "28"%>" value="<%=UA.getDays28()%>"
													style="width: 2.5em; text-transform: uppercase;" disabled></td>
												<td style="border-bottom-color: #1F5FA7;" ><input type="text"
													name="<%=UA.getEMPCODE() + "29"%>"
													id="<%=UA.getEMPCODE() + "29"%>" value="<%=UA.getDays29()%>"
													style="width: 2.5em; text-transform: uppercase;" disabled></td>
												<td  style="border-bottom-color: #1F5FA7;"><input type="text"
													name="<%=UA.getEMPCODE() + "30"%>"
													id="<%=UA.getEMPCODE() + "30"%>" value="<%=UA.getDays30()%>"
													style="width: 2.5em; text-transform: uppercase;" disabled></td>
												<td style="border-bottom-color: #1F5FA7;" ><input type="text"
													name="<%=UA.getEMPCODE() + "31"%>"
													id="<%=UA.getEMPCODE() + "31"%>" value="<%=UA.getDays31()%>"
													style="width: 2.5em; text-transform: uppercase;" disabled></td>


												<%
													}
												%>

												<%--  <td  >
			<input type="button" name="<%=UA.getEMPCODE()+"save"%>" id="<%=UA.getEMPCODE()+"save"%>" class="myButton" VALUE="SAVE" onClick="saveAttendace('<%=UA.getEMPCODE()+":save"%>')">
			</td> --%>

											</tr>
											</form>
											</tr>

											<%
											
												}
												}
											%>
											<!-- <tr>
			<td colspan="35" align="center" >--No Record Found--</td>
			</tr> -->
										

										</table>
										<!-- </form> -->

									</div>
			</td>
			</tr>
			
			
<tr height="50"></tr>			
			
			
				<TR align="center">
			
			<td colspan="5">
				<Div style="background-color: lightgery; overflow-y: scroll; width: 1150px; height: 31em">

										<!-- <form name="saveAttend" action ="ReportServlet?action=saveAttend" method="post"> -->
										<table border="1" id="customers">
									
									<tr class="alt">
								
								
										<th colspan="34" align="center">
												Employee Yearly Attendance 
										</th>
										</tr>
								
										<tr>	<form  action="EmployeeServlet?action=emp" method="Post" onSubmit="return TakeCustId()">
			
		<th>	<font size="3"><b>Enter Employee Name <br>or Emp-Id </b></font>&nbsp;&nbsp;
			<td colspan="12">			<input class="form-control" type="text" name="EMPNO" size="41" id="EMPNO"  onclick="showHide()"
 placeholder="Enter Employee Name or Emp-Code"  title="Enter Employee Name" >&nbsp;&nbsp;  
  <input type="submit" class="myButton" value="Submit" ></td>
					 
	</th>
</form></tr>
											<tr class="alt"><th>EMPCODE</th><td colspan="12"></td></tr>
											<tr class="alt"><th>Employee Name</th><td colspan="12"></td></tr>
											
											<tr class="alt">
												<th>Month <br><br> Days</th> 
												<th>Jan</th> <th>Feb</th> <th>Mar</th> <th>Apr</th> <th>May</th> <th>Jun</th> <th>Jul</th> <th>Aug</th> 
												<th>Sep</th> <th>Oct</th> <th>Nov</th> <th>Dec</th>  
											</tr>
											
											<tr class="alt"><th><font size="5">01</font></th><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
											<tr class="alt"><th><font size="5">02</font></th><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
											<tr class="alt"><th><font size="5">03</font></th><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
											<tr class="alt"><th><font size="5">04</font></th><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
											<tr class="alt"><th><font size="5">05</font></th><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
											<tr class="alt"><th><font size="5">06</font></th><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
											<tr class="alt"><th><font size="5">07</font></th><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
											<tr class="alt"><th><font size="5">08</font></th><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
											<tr class="alt"><th><font size="5">09</font></th><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
											<tr class="alt"><th><font size="5">10</font></th><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
											<tr class="alt"><th><font size="5">11</font></th><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
											<tr class="alt"><th><font size="5">12</font></th><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
											<tr class="alt"><th><font size="5">13</font></th><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
											<tr class="alt"><th><font size="5">14</font></th><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
											<tr class="alt"><th><font size="5">15</font></th><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
											<tr class="alt"><th><font size="5">16</font></th><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
											<tr class="alt"><th><font size="5">17</font></th><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
											<tr class="alt"><th><font size="5">18</font></th><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
											<tr class="alt"><th><font size="5">19</font></th><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
											<tr class="alt"><th><font size="5">20</font></th><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
											<tr class="alt"><th><font size="5">21</font></th><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
											<tr class="alt"><th><font size="5">22</font></th><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
											<tr class="alt"><th><font size="5">23</font></th><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
											<tr class="alt"><th><font size="5">24</font></th><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
											<tr class="alt"><th><font size="5">25</font></th><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
											<tr class="alt"><th><font size="5">26</font></th><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
											<tr class="alt"><th><font size="5">27</font></th><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
											<tr class="alt"><th><font size="5">28</font></th><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
											<tr class="alt"><th><font size="5">29</font></th><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
											<tr class="alt"><th><font size="5">30</font></th><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
											<tr class="alt"><th><font size="5">31</font></th><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
											
											<%
											EmpAttendanceHandler ahNEW = new EmpAttendanceHandler();
											// ah.getEmpAttendance();
											String actNEW = "allAttend";
											ArrayList<UploadAttendanceBean> Emp_beanNEW =  ah.getEmpAttendance1(act);
												int srnoNEW = 0;
												String dateNEW = " ";
												if (Emp_bean.isEmpty()) {
											%>
											<tr>
												<td colspan="35" align="center" >--No Record
													Found--</td>
											</tr>
											<%
												} else {
													for (UploadAttendanceBean UA : Emp_bean) {
														srno++;
														  date = UA.getDateG();
											%>

											<tr>
												<form name="saveAttend" action="ReportServlet?action=saveAttend" method="post" >
													<%-- onsubmit="validate('<%=UA.getEMPCODE()%>')"> --%>
													

													<tr>
														<input type="hidden" name="NDay" id="NDay" value="<%=date.substring(8)%>">
														<td    >
															<%-- <input type="checkbox" name="<%=UA.getEMPCODE()+"CB"%>" id="<%=UA.getEMPCODE()+"CB"%>"> --%>
															<font size="3"><%=srno%></font>
														</td>

														<%-- <td  >
														<input type="button" class="myButton" name="<%=UA.getEMPCODE() + ":edt"%>" id="<%=UA.getEMPCODE() + ":edt"%>" VALUE="EDIT"onClick="editAttendace('<%=UA.getEMPCODE() + ":edt"%>')">
														</td> --%>

														<td   ><font size="3"><%=UA.getEMPCODE()%></font></td>
														<input type="hidden" name="empno" id="empno"value="<%=UA.getEMPNO()%>">
														<input type="hidden" name="datemonth" id="datemonth" value="<%=date%>">
														<input type="hidden" name="empcode" id="empcode" value="<%=UA.getEMPCODE()%>">

														<td    >
															<font size="3"><%=UA.getName()%></font>
														</td>
														<td   >
														<input type="text" name="<%=UA.getEMPCODE() + "1"%>" id="<%=UA.getEMPCODE() + "1"%>" value="<%=UA.getDays1()%>" style="width: 2.5em; text-transform: uppercase;" disabled></td>
														<td   ><input type="text" name="<%=UA.getEMPCODE() + "2"%>" id="<%=UA.getEMPCODE() + "2"%>" value="<%=UA.getDays2()%>"
															style="width: 2.5em; text-transform: uppercase;" disabled></td>
														<td   ><input type="text" name="<%=UA.getEMPCODE() + "15"%>" id="<%=UA.getEMPCODE() + "15"%>" value="<%=UA.getDays15()%>"
															style="width: 2.5em; text-transform: uppercase;" disabled></td>
													</tr>
											<tr>
												<td style="border-bottom-color: #1F5FA7;"></td>
<td style="border-bottom-color: #1F5FA7;"><font SIZE="3" ><%=date.substring(8)%></font></td>
												<td  style="border-bottom-color: #1F5FA7;"><font SIZE="3" ><%=EmpAttendanceHandler.getEmpAbsentDay(UA.getEMPCODE(),date) %></font></td>
												
													<%-- <td  >
														<input type="button" name="aprrove" id="aprrove" class="myButton" VALUE="APPROVE" onClick="approveAttendance('<%=UA.getEMPCODE()%>')">
													</td> --%>

												<td  style="border-bottom-color: #1F5FA7;"><input type="text" name="<%=UA.getEMPCODE() + "16"%>" 
												id="<%=UA.getEMPCODE() + "16"%>" value="<%=UA.getDays16()%>" style="width: 2.5em; text-transform: uppercase;" disabled></td>

												<%
													if (date.substring(8).equalsIgnoreCase("28")) {
												%>
												<td  style="border-bottom-color: #1F5FA7;"><input type="text" name="<%=UA.getEMPCODE() + "28"%>" 
												id="<%=UA.getEMPCODE() + "28"%>" value="<%=UA.getDays28()%>" style="width: 2.5em; text-transform: uppercase;" disabled></td>
												<td  style="border-bottom-color: #1F5FA7;"></td>
												<td style="border-bottom-color: #1F5FA7;" ></td>
												<td  style="border-bottom-color: #1F5FA7;"></td>
												<%
													} else if (date.substring(8).equalsIgnoreCase("29")) {
												%>
												<td  style="border-bottom-color: #1F5FA7;"><input type="text" name="<%=UA.getEMPCODE() + "28"%>"
													id="<%=UA.getEMPCODE() + "28"%>" value="<%=UA.getDays28()%>" style="width: 2.5em; text-transform: uppercase;" disabled></td>
												<td  style="border-bottom-color: #1F5FA7;"><input type="text" name="<%=UA.getEMPCODE() + "29"%>"
													id="<%=UA.getEMPCODE() + "29"%>" value="<%=UA.getDays29()%>" style="width: 2.5em; text-transform: uppercase;" disabled></td>
												<td  style="border-bottom-color: #1F5FA7;"></td>
												<td  style="border-bottom-color: #1F5FA7;"></td>
												<%
													} else if (date.substring(8).equalsIgnoreCase("30")) {
												%>
												<td  style="border-bottom-color: #1F5FA7;"><input type="text"
													name="<%=UA.getEMPCODE() + "28"%>" id="<%=UA.getEMPCODE() + "28"%>" value="<%=UA.getDays28()%>"
													style="width: 2.5em; text-transform: uppercase;" disabled></td>
												<td  style="border-bottom-color: #1F5FA7;"><input type="text" name="<%=UA.getEMPCODE() + "29"%>"
													id="<%=UA.getEMPCODE() + "29"%>" value="<%=UA.getDays29()%>" style="width: 2.5em; text-transform: uppercase;" disabled></td>
												<td  style="border-bottom-color: #1F5FA7;"><input type="text" name="<%=UA.getEMPCODE() + "30"%>"
													id="<%=UA.getEMPCODE() + "30"%>" value="<%=UA.getDays30()%>" style="width: 2.5em; text-transform: uppercase;" disabled></td>
												<td style="border-bottom-color: #1F5FA7;" ></td>

												<%
													} else if (date.substring(8).equalsIgnoreCase("31")) {
												%>
												<td  style="border-bottom-color: #1F5FA7;"><input type="text" name="<%=UA.getEMPCODE() + "28"%>"
													id="<%=UA.getEMPCODE() + "28"%>" value="<%=UA.getDays28()%>" style="width: 2.5em; text-transform: uppercase;" disabled></td>
												<td style="border-bottom-color: #1F5FA7;" ><input type="text" name="<%=UA.getEMPCODE() + "29"%>"
													id="<%=UA.getEMPCODE() + "29"%>" value="<%=UA.getDays29()%>" style="width: 2.5em; text-transform: uppercase;" disabled></td>
												<td  style="border-bottom-color: #1F5FA7;"><input type="text" name="<%=UA.getEMPCODE() + "30"%>"
													id="<%=UA.getEMPCODE() + "30"%>" value="<%=UA.getDays30()%>" style="width: 2.5em; text-transform: uppercase;" disabled></td>
												<td style="border-bottom-color: #1F5FA7;" ><input type="text" name="<%=UA.getEMPCODE() + "31"%>"
													id="<%=UA.getEMPCODE() + "31"%>" value="<%=UA.getDays31()%>" style="width: 2.5em; text-transform: uppercase;" disabled></td>

												<%
													}
												%>

												<%--  <td  >
			<input type="button" name="<%=UA.getEMPCODE()+"save"%>" id="<%=UA.getEMPCODE()+"save"%>" class="myButton" VALUE="SAVE" onClick="saveAttendace('<%=UA.getEMPCODE()+":save"%>')">
			</td> --%>

											</tr>
											</form>
											</tr>

											<%
												}
												}
											%>
																					</table>
										<!-- </form> -->

									</div>
			</td>
			</tr>
	
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
<%-- 	<tr class="alt" align="left">
	<td colspan ='8'  ><font size ='5'> Attendance Month :  <%=date%></font></td> 
				<!-- <a href="employee.jsp?action=addemp">&nbsp;<b>Add Employee</b></a> -->
				<!-- <td colspan ='4'>
				<a href="FinalizeAttendance.jsp" style="font-size: 1.5em;" align="right" >&nbsp;&nbsp;......Click here Approve Attendance</a> 
			</td> -->
			</tr>
 --%>			
			
</table>



</center>
			
			</div>
			
			<!--  end table-content  -->
	
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
    

</body>
</html>