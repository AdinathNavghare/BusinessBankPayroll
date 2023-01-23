<%@page import="com.sun.java.swing.plaf.windows.resources.windows"%>
<%@page import="payroll.DAO.onHoldHandler"%>
<%@page import="payroll.Model.EmployeeBean"%>
<%@page import="payroll.Model.onHoldBean"%>
<%@page import="payroll.DAO.EmployeeHandler"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy DTS3 </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />

<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<script type="text/javascript">

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

<script>
	function TakeCustId() {
		var EMPNO = document.getElementById("EMPNO").value;
		var hreason = document.getElementById("hreason").value;
		var date = document.getElementById("date").value;
		
		if(date ==""){
			alert("Please Enter Date");
			document.getElementById("date").focus();
			return false;
		}
		
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
		
		if(hreason ==""){
			alert("Please Enter reason for Hold");
			document.getElementById("hreason").focus();
			return false;
		}
		
	
	}
	
	function releaseEmployee(empno, name){
	
		var r=  confirm("Are you sure to Release "+name+" ");
	
		if(r==true){
			var p = prompt("Type Yes to Release for selected Employee."); 
			if(p=="yes" || p=="Yes" || p=="YES"){	
				return true;  // false for testing purpose either set true
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}
	
		window.location.href = "OnholdServlet?action=releaseEmployee&empno="+empno;	
	}
	
	function CheckFlag(){
		var f = parseInt(document.getElementById("flag").value);
		if (f == 1) {
			alert("Saved Successfully");
		}
		if (f == 2) {
			alert("Error in Saving Details !");
		}
		if (f == 3) {
			alert("Release Successfully");
		}
		if (f == 4) {
			alert("Error in Release Details !");
		}	
	
	}
	
	
</script>
<script>
	jQuery(function() {
          $("#EMPNO").autocomplete("onHoldSearchList.jsp");
	});
</script>
 <%
String pageName = "OnHold.jsp";
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

<%

	try
	{

		String action ="firsttime";
		String flag = "";
		
	 	action = request.getParameter("action")==null?"firsttime":request.getParameter("action");
	 	
		onHoldHandler  hHandler = new onHoldHandler();
		ArrayList<onHoldBean> onHoldList = new ArrayList<onHoldBean>();
		
		if(action.equalsIgnoreCase("firsttime")){
			
			flag = request.getParameter("flag")==null?"0":request.getParameter("flag");
			onHoldList = hHandler.getEmployeeHoldList();
			System.out.println("@@@@@@@"+onHoldList.size());
		}
	
%>

</head>
<body onload="CheckFlag()" > 
<%@include file="mainHeader.jsp"%>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp"%>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:scroll; max-height:80%; ">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Employee on Hold</h1>
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
			<div align="center">
				<form  action="OnholdServlet?action=onHold" method="Post" onSubmit="return TakeCustId()">
					<table id="customers">
						<tr>
							<th><b> Select Date </b></th>
							<td align="left">
								<input name="date" id="date" readonly="readonly" class="date-picker" placeholder="Click here for Calender"/>
							</td>
							<th><b> Enter Employee Name or Emp-Id </b></th>
							<td align="center">
								<input class="form-control" type="text" name="EMPNO" size="41" id="EMPNO"
								 		title="Enter Employee Name" placeholder="Enter Employee Name"  />
							</td>
						</tr>
						
						<tr>
							<th>Hold Reason</th>
							<td>
								<input class="form-control" type="text" name="hreason" size="41" id="hreason"
								       placeholder="Please Enter Hold Reason"  />
							</td>
							<td colspan="2" align="center"> <input type="submit" class="myButton" value="Put On Hold" /></td>
						</tr>
					</table>
				</form>
			</div> <br/><br/>
			<div align="center">
				<table id="customers">
					<tr>
						<th colspan="6"><b>-: Employee List On Hold :- </b></th>
					</tr>
					<tr>
						<th width="70">SRNO</th>
						<th width="100" >EMPCODE</th>
						<th width="250">EMP NAME</th>
						<th width="100">MONTH</th>
						<th width="150"> REASON</th>
						<th width="150"></th>
					</tr>
				
					<% 
						int count = 1;
						for(onHoldBean hBean :onHoldList){	
					%>
					<tr class="alt">
						<td align="center"><%=count%> </td>
						<td align="center"><%= hBean.getEmpcode()%> </td>
						<td align="center"><%= hBean.getName() %></td>
						<td align="center"><%= hBean.getSalmonth()%> </td>
						<td align="center"><%= hBean.getHreason() %> </td>
						<td align="center">
							<input type="button" class="myButton" value="Release" 
							       onclick="releaseEmployee('<%=hBean.getEmpno()%>', '<%= hBean.getName() %>')"/>
						</td>
					</tr>
						
					<% 	count++;
						}
					%>
				</table>
				<input type="hidden" name="flag" id="flag" value="<%=flag%>">
			</div>
<% }catch(Exception e){e.printStackTrace();}%>
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