<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="payroll.Model.EmployeeBean"%>
<%@page import="payroll.Core.ReportDAO"%>
<%@page import="payroll.DAO.EmployeeHandler"%>
<%@page import="java.util.ArrayList"%>
<%@page import="payroll.DAO.EmpAttendanceHandler"%>
<%@page import="payroll.DAO.LeaveMasterHandler"%>
<%@page import="payroll.Model.Attend_bean"%>
<%@page import="payroll.Core.Calculate"%>
<%@page import="payroll.DAO.RoleDAO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy; DTS3</title>
<link rel="stylesheet" href="css/screen.css" type="text/css"
	media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />
<link rel="stylesheet" href="css/datePicker.css" type="text/css"
	media="screen" title="default" />	
<script type="text/javascript" src="js/jquery.datePicker.js"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/MONTHPICK/jquery.js"></script>
    <script type="text/javascript" src="js/MONTHPICK/jquery-ui.min.js"></script>
    <link rel="stylesheet" type="text/css" media="screen" href="css/MONTHPICK/jquery-ui.css">
<script src="js/jquery/jquery.autocomplete.js"></script>
<title>Late Marking</title>
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




</head>
<%
RoleDAO obj1=new RoleDAO();
String roleId=obj1.getrole(Integer.parseInt(session.getAttribute("UID").toString()));

String date=request.getParameter("date")==null?ReportDAO.getSysDate():request.getParameter("date");
float days=Calculate.getDays(date); 
/* String status=request.getParameter("status");
String event=request.getParameter("event"); */
/* int empNo=Integer.parseInt(request.getParameter("empno")); */
/* System.out.println("date1"+date);
System.out.println("status"+status);
System.out.println("event"+event);
System.out.println("empNo"+empNo); */
String empName;
Attend_bean attend_bean=new Attend_bean();
LeaveMasterHandler leaveMasterHandler=new LeaveMasterHandler();
EmpAttendanceHandler empAttend =new EmpAttendanceHandler();
EmployeeBean employeeBean = new EmployeeBean();
LookupHandler lookupHandler=new  LookupHandler();
EmployeeHandler employeeHandler = new EmployeeHandler();
int empNo = request.getParameter("EMPNO")==null?Integer.parseInt(session.getAttribute("EMPNO").toString()):Integer.parseInt(request.getParameter("EMPNO"));
employeeBean = employeeHandler.getEmployeeInformation(Integer.toString(empNo));		


%>
<script>

jQuery(function() {
    $("#EMPNO").autocomplete("list.jsp");
});
</script>
<script>
function getEmployee()
{
	var e = document.getElementById('EMPNO').value;
	var date = document.getElementById('date').value;
	var res=e.split(":");
	if(e=="")
		{
		alert("Please Enter Employee...!");
		}
	else
		{
		window.location.href = "LateMark.jsp?EMPNO="+res[2]+"&d="+date;
		}
}

function addNew()
{
	var e = document.getElementById("EMPNO").value;
	var date2 = document.getElementById("date").value.trim();
	var res=e.split(":");
	if(date2=="")
	{
		alert("Please Select Month...!");
		return false;
	}
	
	
	if(e=="")
		{
		alert("Please Select Employee...!");
		return false;
		}
	//alert(res[2]);
		window.location.href = "LatePresentSeat.jsp?EMPNO="+res[2]+"&date=01-"+date2+"&prj=";
	}



</script>





<body onload="focus()" > 
<%@include file="mainHeader.jsp"%>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp"%>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:scroll; max-height:80%; ">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Late Mark</h1>
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
	<table id="customers">
							<tr>
								<th colspan="2">Select Employee</th>
							</tr>
					
						<tr>
							<td >Select Month : </td>
							<td>
							 <!-- <input name="date"  id="date" readonly="readonly" class="date-picker" placeholder="Click here for Calender"/> -->					 
							 <input name="date"  id="date" readonly="readonly" value="<%=ReportDAO.getServerDate().substring(3,11)%>"/>
							</td>
				
						</tr>
						<tr>
							<td>Enter Employee Name/Id</td>
							<td>
								<input type="text" name="EMPNO" size="40" id="EMPNO" onclick="showHide()" placeholder="Enter Employee Name" />
							</td>
						</tr>
							
							<tr>
							<td align="center" colspan="2">
								 <input type="button" name="Submit" value="Submit" class="myButton" onclick="addNew()">
							</td>
						</tr>
	</table>
			
</div>

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
</html>