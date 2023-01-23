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
<script type="text/javascript" src="js/datetimepicker.js"></script>

<script src="js/MONTHPICK/jquery.js"></script>
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
String empcd;
String trndt;
String action = request.getParameter("action")==null?"":request.getParameter("action");
System.out.println("action is"+action);
TranHandler trnh= new TranHandler();
EmpOffHandler empOffHldr = new EmpOffHandler();
EmployeeHandler emphdlr = new EmployeeHandler();

int trn=0;
int keys=0;
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
		// For Allowances and expenses
		String trncd1=request.getParameter("key");
		trn=Integer.parseInt(trncd1);
		select=request.getParameter("selected");
		session.setAttribute("trncd", trn);
		session.setAttribute("selectvalue", select);
		
		prjCode = request.getParameter("PrjCode");
	    session.setAttribute("prjCode", prjCode);
	    projEmpNolist = empOffHldr.getEmpList(prjCode);
	    session.setAttribute("projEmpNolist", projEmpNolist);
	    
	    for(TranBean tbn : projEmpNolist){
	    	TranBean trbn = new TranBean();
	    	trbn = trnh.getTranByEmpno1(tbn.getEMPNO(), trn);
	    	projEmpNmlist.add(trbn);
	    } 
	    session.setAttribute("projEmpNmlist", projEmpNmlist);
	    
	    tranlist = trnh.getTranDetail(trn);
	    session.setAttribute("list", tranlist);
	    CodeMasterBean temp1 = new CodeMasterBean();
	}
}
catch(Exception e)
{
	e.printStackTrace();
	System.out.println("First Time Loading");
}
%>
<script type="text/javascript">

function check(){
	
	 if( document.getElementById("trncd").selectedIndex==0)
	{
		alert("Please Select Report!!!");	
		document.getElementById("trncd").focus;
		return false;
	}
	   var fromDate=document.getElementById("frmdate").value;
	   var toDate=document.getElementById("todate").value;
	   fromDate = fromDate.replace(/-/g,"/");
	   toDate = toDate.replace(/-/g,"/");
	   if(fromDate == "" || toDate == ""){
			 alert("please select From Date and To Date.!");
			 return false;
	    }
	 	var d1 = new Date(fromDate);	 	
	 	var d2 =new  Date(toDate);
	 	if (d1.getTime() > d2.getTime())
	 	{
		   alert("Invalid Date Range!\n FromDate can't be greater than TODate!");
		   document.getElementById("todate").focus();
		   return false;
	  } 
}
</script>
</head>
<body style="overflow: hidden;">
	<%@include file="mainHeader.jsp"%>
	<!--  start nav-outer-repeat................................................................................................. START -->
	<%@include file="subHeader.jsp"%>

	<!-- start content-outer ........................................................................................................................START -->
	<div id="content-outer" style="overflow-y: scroll; max-height: 78%;">
		<!-- start content -->
		<div id="content">

			<!--  start page-heading -->
			<div id="page-heading">
				<h1>Code Wise Yearly Salary Report</h1>
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
						<div id="content-table-inner">
							<div id="table-content">
								<center>
			<form name="salrpt" action="ReportServlet" method ="post" onSubmit="return check()" >
			<table border="1" id="customers" align="center">
			<tr>
				<th>Code Wise Yearly Salary Report</th>
			<tr>
			<tr class="alt">
				<td  align="center">
				
			<table align="center">
				<tr class="alt" height="30" align="center">
                	<input type="hidden" id="action" name="action" value="codeWiseSalaryReort"></input>
                	<input type="hidden" id="pttrncd" name="pttrncd" />
					<!-- <td>Select Date</td>
					<td align="left">
					<input name="date" id="date" readonly="readonly" class="date-picker" placeholder="Click here for Calender"/>
					</td> -->
				</tr>
				
				 <tr class="alt" id="leftempdate">
					        <td><font size="3">From Date </font></td>
		     				<td><input name="frmdate" id="frmdate" type="text"  readonly="readonly" size="18" style="width: 163px; height: 21px; font-size: 15px;">&nbsp;<img
							src="images/cal.gif" align="middle"
							style="vertical-align: middle; cursor: pointer;"
							onClick="javascript:NewCssCal('frmdate', 'ddmmmyyyy')" /></td>
							
						  </tr>
						   <tr class="alt" id="toodate">
							<td><font size="3">To Date </font></td>
		     				<td><input name="todate" id="todate" type="text"  readonly="readonly" size="18" style="width: 163px; height: 21px; font-size: 15px;">&nbsp;<img
							src="images/cal.gif" align="middle"
							style="vertical-align: middle; cursor: pointer;"
							onClick="javascript:NewCssCal('todate', 'ddmmmyyyy')" />
							</td>
							</tr>
				
				<tr class="alt" height="30" align="center">	
					<td align="center">Select Report For  
															</td>
															<td>
															<select style="width:300px"
																name="trncd" id="trncd" onChange="getTranDetails()">
																	<option value="select" selected>Select</option>
																	<%
																		for (CodeMasterBean temp1 : TRNCODE) {

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
				</tr>
				
				<!-- <tr  class="alt" height="30" align="center">
				<td colspan="2">
				<input type="submit" class="myButton" id="paytran" style="float: left; margin-left: 25%;" value="Before Finalize" onclick="getCode('paytran')" />
				<input type="submit" class="myButton" id ="paytran_stage"  style="float:right; margin-right: 1%; vertical-align: top;"  value="After Finalize" onclick="getCode('paytran_stage')" />
				<input type="hidden" id="tname" name="tname" value="">
				<input type="hidden" id="fname" name="fname" value="">
				</td>
				</tr> -->
				<tr class="alt">
				<td colspan="4" align="center">
				<input type="submit" value="Get Report" class="myButton" /></td>
				</tr>
				</table>

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