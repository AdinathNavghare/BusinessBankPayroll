<%@page import="payroll.Model.Lookup"%>
<%@page import="payroll.DAO.EmployeeHandler"%>
<%@page import="payroll.DAO.TranHandler"%>
<%@page import="payroll.DAO.LeaveMasterHandler"%>
<%@page import="payroll.Model.CodeMasterBean"%>
<%@page import="payroll.DAO.CodeMasterHandler"%>
<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="payroll.Model.TranBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="payroll.Model.EmpOffBean"%>
<%@page import="payroll.Model.EmployeeBean"%>
<%@page import="payroll.DAO.EmpOffHandler"%>
<%@page import="payroll.Core.ReportDAO"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>&copy DTS3 </title>
	<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
	<!--[if IE]>
<link rel="stylesheet" media="all" type="text/css" href="css/pro_dropline_ie.css" />
<![endif]-->
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />

<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>




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

<script type="text/javascript">
	var xmlhttp;
	var url="";
	
	if(window.XMLHttpRequest)
	{
		xmlhttp=new XMLHttpRequest;
	}
	else //if(window.ActivXObject)
	{   
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	
	function TakeCustId() {
		var EMPNO = document.getElementById("EMPNO").value;
         
		if (document.getElementById("EMPNO").value == "") {
			alert("Please Insert Employee Name");
			document.getElementById("EMPNO").focus();
			return false;
		}
		var atpos=EMPNO.indexOf(":");
		if (atpos<1)
		  {
		  alert("Please Select Correct Employee Name");
		  return false;
		  }
		}
	function getEmailId()
	{
		
		if(document.getElementById("EMPNO").value=="")
			{
			alert("Please enter Employee !");
			}
		else
			{
		var empno = document.getElementById("EMPNO").value;
		url="mailServlet?empno="+empno;
		xmlhttp.onreadystatechange=callback;
		xmlhttp.open("POST", url, true);
		xmlhttp.send();
			}
	}
	function callback()
	{
    	if(xmlhttp.readyState==4)
		{
        	var response = xmlhttp.responseText;
        	
        	/* if(response == "")
        	{
        		alert("into if");
        		document.getElementById("email").hidden=false;
        		//document.getElementById("toemailid").value=response;
        		document.getElementById("toemailid").value="";
        		document.getElementById("toemailid").focus();
        		document.getElementById("prjemp").focus();
        		
        	
        	} 
        	 */
        	document.getElementById('toemailid').value=response.charAt(0)=="<"?"":response;
        	document.getElementById('toemailid').focus();
        	document.getElementById("prjemp").value=response;
        	document.getElementById("prjemp").focus();
        	
    	}
	}
	function changeValid()
	{
		
		if((document.getElementById("all").value== "one"))
			{
			
			var result_style = document.getElementById('empnumber').style;
			var result_style1 = document.getElementById('emailid').style;
			var result_style2 = document.getElementById('prjemailid').style;
			result_style.display = 'table-row';
			result_style1.display = 'table-row';
			result_style2.display = 'none';
			}
		else if((document.getElementById("all").value== "prjmail"))
		{
		
		var result_style = document.getElementById('empnumber').style;
		var result_style1 = document.getElementById('emailid').style;
		var result_style2 = document.getElementById('prjemailid').style;
		result_style.display = 'table-row';
		result_style1.display = 'table-row';
		result_style2.display = 'table-row';
		//document.getElementById('prjemp').value ='';
		}
		else
		{
			var result_style = document.getElementById('empnumber').style;
			var result_style1 = document.getElementById('emailid').style;
			var result_style2 = document.getElementById('prjemailid').style;
			result_style.display = 'none';
			result_style1.display = 'none';
			result_style2.display = 'none';
			document.getElementById('EMPNO').value =''; 
			document.getElementById('toemailid').value =''; 
		}
		
		
		}
	function validation()
	{
		
		if(document.getElementById("EMPNO").value=="")
		{
		alert("Please Enter Employee !");
		document.getElementById("EMPNO").value="";
		return false;
		}
		if(document.getElementById('toemailid').value=="")
		{
		alert("Please Enter Email !");
		document.getElementById("toemailid").value="";
		return false;
		}
		
		
		if((document.getElementById("all").value=="one"))
		{

			var EMPNO = document.getElementById("empnumber").value;
	         
			if (document.getElementById("empnumber").value == "")
			{
				alert("Please Insert Employee Name");
				document.getElementById("empnumber").focus();
				return false;
			}
			var atpos=EMPNO.indexOf(":");
			if (atpos<1)
			  {
			  alert("Please Select Correct Employee Name");
			  return false;
			  }
		
			if(document.getElementById('emailid')=="")
			{
			alert("Please Insert Employee Email Id");
			document.getElementById("emailid").focus();
			 return false;
			}
		}
		else if(document.getElementById("all").value== "prjmail")
		{
			if(document.getElementById("prjemp").value=="select"){
				alert("Please select BRANCH !");
				return false;
			}
			if (document.getElementById("EMPNO").value == "")
			{
				alert("Please Insert Employee Name");
				document.getElementById("EMPNO").focus();
				return false;
			}
			var EMPNO = document.getElementById("EMPNO").value;
			var atpos=EMPNO.indexOf(":");
			if (atpos<1)
			  {
			  alert("Please Select Correct Employee Name");
			  return false;
			  }
			if(document.getElementById("toemailid").value==""){
				alert("Please enter email-Id !");
				document.getElementById("toemailid").focus();
			 	return false;
			}
		}
		
		
	}
</script>
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
ArrayList<TranBean> projEmpNolist = new ArrayList<TranBean>();
ArrayList<TranBean> projEmpNmlist = new ArrayList<TranBean>();

TRNCODE=CMH.getNoAutocalCDList();


int trncd=0;
String select=new String();
String selectCode = new String();
LeaveMasterHandler obj=new LeaveMasterHandler();
String empName;
String action = request.getParameter("action")==null?"":request.getParameter("action");
System.out.println("action is"+action);
TranHandler trnh= new TranHandler();
EmpOffHandler empOffHldr = new EmpOffHandler();
EmployeeHandler emphdlr = new EmployeeHandler();

LookupHandler lkh=new LookupHandler();
ArrayList<Lookup> emplist=new ArrayList<Lookup>(); 
emplist=lkh.getSubLKP_DESC("ET");	// ET is Code for Employee Type
int trn=0;
int keys=0;
int empno1=0;
int empno=0;
int flag=-1;
String prjemp = "";
try
{  
	
	
	if(action.equalsIgnoreCase("getdetails"))
	{
		// For Allowances and expenses
		String trncd1=request.getParameter("key");
		trn=Integer.parseInt(trncd1);
		select=request.getParameter("selected");
		session.setAttribute("trncd", trn);
		session.setAttribute("selectvalue", select);
		
		prjemp = request.getParameter("PrjCode");
	    session.setAttribute("prjemp", prjemp);
	    projEmpNolist = empOffHldr.getEmpList(prjemp);
	    session.setAttribute("projEmpNolist", projEmpNolist);
	    
	    for(TranBean tbn : projEmpNolist){
	    	TranBean trbn = new TranBean();
	    	trbn = trnh.getTranByEmpno1(tbn.getEMPNO(), trn);
	    	projEmpNmlist.add(trbn);
	    } 
	    session.setAttribute("projEmpNmlist", projEmpNmlist);
	    
	    tranlist = trnh.getTranDetail(trn);
	    session.setAttribute("list", tranlist);
	    
	}
}
	catch(Exception e)
	{
		e.printStackTrace();
		System.out.println("First Time Loading");
	}
	%>


</head>
<body style="overflow: hidden;"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:scroll; max-height:80%; ">
<!-- start content --><div id="content">
					
						<!--  start page-heading -->
<div id="page-heading">
	<h1>Send Pay Slip By Email  </h1>
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
								<div id="table-content">
								<center>
							<form action="mailServlet" ENCTYPE="multipart/form-data" onsubmit="return validation()">   
					 			 <table id="customers">
								<tr><th colspan="2" align="Center">Send Pay-Slip To Employee</th></tr>
								<tr>
									<td>Select Date</td>
										<td bgcolor="#FFFFFF">
										<input name="date" id="date" readonly="readonly" class="date-picker" placeholder="Click here for Calender"/>

										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										To :
										&nbsp;&nbsp;&nbsp;&nbsp;
										<input name="todate" id="todate" readonly="readonly" class="date-picker" placeholder="Click here for Calender"/>

										
										</td>
								</tr>
								<tr><td>Select Employee</td><td>
								<select name="all" id="all" onchange="changeValid()" >
									<option value="0">Select</option>
									<option value="one">One Employee</option>
									<option value="all">All Employees</option>
									<option value="prjmail">BRANCH Employee</option> 
								</select></td></tr>
								<tr id="prjemailid" style="display:none;">
										<td>Select BRANCH</td>
										<td>
											<!-- <select name="prjemp" id="prjemp" onchange="changeValid()"> -->
											<select name="prjemp" id="prjemp">
											<option value="select">Select</option>
											
												<%
														EmpOffHandler eh = new EmpOffHandler();
														ArrayList<EmpOffBean> ebn = new ArrayList<EmpOffBean>();
														ebn = eh.getprojectCode();
														for(EmpOffBean eopbn : ebn)
														{
												%>
														<option value="<%=eopbn.getPrj_srno()%>"><%=eopbn.getSite_name()%></option>
												<%		}
												%>
											</select></td>	</tr>
											
								
								<tr id="empnumber" style="display:none;">
									<td>Employee Number : </td><td><input type="text" name="EMPNO" id="EMPNO" size="40" onchange="return getEmailId()" onblur=" return getEmailId()" value=""></td>
								</tr>
								<tr id="emailid" style="display:none;">
										<td>Employee Email Id : </td>
										<td><input type="text" size="40" name="toemailid" id="toemailid" readonly="readonly">
											<label id="email" hidden="true">Please Insert Email Id</label></td>
								</tr>
									
										
										
								<tr><td colspan="2" align="center"><input type="submit" class="myButton" value="Send Email"></td></tr>
								<tr><td colspan="2" align="center" ><font color="green"><%=request.getParameter("Status")==null?"":request.getParameter("Status") %> </font></td></tr>
								</table>
							
					
							</form>
							<br>
							<h3><font color="#142F36">After Clicking on Send Mail Button, The Process of Sending mail  will be done at back end, you can move other pages instead of waiting here</font></h3>
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