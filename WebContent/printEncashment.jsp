<%@page import="payroll.Model.ProjectBean"%>
<%@page import="payroll.DAO.ProjectListDAO"%>
<%@page import="payroll.DAO.*"%>
<%@page import="payroll.Model.*"%>
<%@page import="payroll.Model.TranBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="payroll.DAO.EmpAttendanceHandler"%>
<%@page import="payroll.DAO.EmpOffHandler"%>
<%@page import="payroll.Model.EmpOffBean"%>
<%@page import="payroll.DAO.RoleDAO"%>
<%@page import="payroll.Core.ReportDAO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy Print Encashment </title>
<link rel="stylesheet" href="css/screen.css" type="text/css"
	media="screen" title="default" />
	<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>



<% 
//RoleDAO obj1=new RoleDAO();
//String roleId=obj1.getrole(Integer.parseInt(session.getAttribute("UID").toString()));
int eno = (Integer)session.getAttribute("EMPNO");
EmpOffHandler eoffhdlr = new EmpOffHandler();
EmpOffBean eoffbn = new EmpOffBean();
//eoffbn = eoffhdlr.getEmpOfficAddInfo(Integer.toString(eno));

EmpAttendanceHandler empAttendanceHandler=new EmpAttendanceHandler();
String today=empAttendanceHandler.getServerDate();

%>

<script>

	jQuery(function() {
		
		$("#EMPNO").autocomplete("list.jsp");
	});
</script>

<script type="text/javascript">

<%int prjCode = 0;
int flag=-1;
String action = request.getParameter("action")==null?"":request.getParameter("action");
%>

function Clickheretoprint()
{ 
		var disp_setting="toolbar=yes,location=no,directories=yes,menubar=yes,"; 
		    disp_setting+="scrollbars=yes,width=1000, height=800, left=200, top=10"; 
		var content_vlue = document.getElementById("print_content").innerHTML; 
		
		var docprint=window.open("","",disp_setting); 
			docprint.document.open(); 
			docprint.document.write('<html><head><title>Inel Power System</title>'); 
			docprint.document.write("<style type=\"text/css\">	body,td,th { font-family: Times New Roman; font-size: 14px;}	</style></head><body onLoad=\"self.print()\" style=\"font-family:Times New Roman; font-size:9px;\" ><center>");          
			docprint.document.write(content_vlue);          
			docprint.document.write('</center></body></html>'); 
			docprint.document.close(); 
			docprint.focus(); 
}
</script>
</head>
<body style="overflow: hidden;"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:scroll; max-height:80%; ">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1> Print Encashment </h1>
	</div>
	<!-- end page-heading -->

   	<div align="center">
	<form action="printEncashment.jsp" method="POST">
	<table border="1">
		<tr>		
			<td>Enter Employee Name Or Emp-Id <input type="text" name="EMPNO" size="40"
				id="EMPNO" onClick="showHide()" title="Enter Employee Id / Name "> &nbsp;</td>
			<td valign="top">
				<input type="submit" value="Submit" class="myButton" style="margin-left: 5px;margin-right: 5px; "/>
				<input type="hidden" name="action" id="action" value="print">
		    </td>
				
		</tr>
		<tr></tr>
	</table>
    </form>
    </div>
   <br>

	<table border="0" style="height: 100px;" width="100%" cellpadding="0" cellspacing="0" id="content-table">
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
			
			
			
			 <% 
		if(action.equalsIgnoreCase("print")){
			
			
			String employee="";
			employee = request.getParameter("EMPNO")==null?"":request.getParameter("EMPNO");

			String[] employ = employee.split(":");
			String empno = employ[2].trim();
			String empname= employ[0].trim();
			String empcode= employ[1].trim();
			//System.out.println("name ==== "+empname);
			
			eoffbn = eoffhdlr.getEmpOfficAddInfo(empno);
			String site_name = eoffbn.getPrj_name();
			
			LeaveEncashmentHandler encashHandler = new LeaveEncashmentHandler();
			LeaveEncashmentBean leaveEncashBean = new LeaveEncashmentBean();
			leaveEncashBean = encashHandler.getEmployeeInfo(empno);
			float monthlygross= leaveEncashBean.getMonthlyGross();
			//float yearlygross= monthlygross*12;		
			
			leaveEncashBean= encashHandler.enCashAmount(Integer.parseInt(empno));
			float encashAmount= leaveEncashBean.getEncashmentAmt();
			String encashDate= leaveEncashBean.getLeaveEncashmentDate();
			float total= monthlygross+encashAmount;
			
			
			
		%>		
		<a href="javascript:Clickheretoprint()">Click here to print</a>
		<center> 
		 <div align="center" id="print_content" style="height: auto;">
			<table style="width: 60%" id ="customers">
			
			<tr style="height: 30px;" class ="alt"><th colspan="4"> <font size="4"> Print Encashment</font></th></tr>
			
		
		    <tr style="height: 30px;" class ="alt">
				<td><font size="3">Employee Name :</font>&nbsp; </td>
				<td><input style="height: 20px; width: 200px; border: none;" type="text" id="EMPNO" name="EMPNO" readonly="readonly" value="<%=empname %>" /> </td>
		</tr>
		
			<tr style="height: 30px;" class ="alt">
				<td><font size="3">Employee Code :</font>&nbsp; </td>
				<td><input style="height: 20px; width: 200px; border: none;" type="text" id="code" name="code" readonly="readonly" value="<%=empcode%>" /> </td>
		</tr>
		
			<tr style="height: 30px;" class ="alt">
				<td><font size="3">Branch Name:</font>&nbsp; </td>
				<td><input style="height: 20px; width: 200px; border: none;"  type="text" id="site" name="site" readonly="readonly" value="<%=site_name%>" /> </td>
		</tr>
		
		
			<tr style="height: 30px;" class ="alt">
		
					<td><font size="3">Leave Encashment Date :</font>&nbsp; </td>
					<td><input style="height: 20px; width: 200px; border: none;" name="edate" size="20" id="edate"
					type="text" value="<%=encashDate %>" readonly="readonly"></td>
			</tr>
			
				<tr style="height: 30px;" class ="alt">
				<td><font size="3">Leave Encashment Amount :</font>&nbsp; </td>
				<td><input style="height: 20px; width: 200px; border: none;" type="text" id="amount" name="amount" value="<%=encashAmount%>" readonly="readonly" /> </td>
		   </tr> 
			
			<tr style="height: 30px;" class ="alt">
				<td><font size="3">Monthly Gross :</font>&nbsp; </td>
				<td><input style="height: 20px; width: 200px;  border: none;" type="text" id="ctc" name="ctc" value="<%=monthlygross%>" readonly="readonly" /> </td>
		    </tr>
		    
		    <tr style="height: 30px;" class ="alt">
				<td><font size="3">Total Amount :</font>&nbsp; </td>
				<td><input style="height: 20px; width: 200px;  border: none;" type="text" id="tot" name="tot" value="<%=total%>" readonly="readonly" /> </td>
		    </tr>
		
		
			
		
			</table>
			</div>
					</center>					
		
			</div>
			
			 <% }%> 
			
			
			<input type="hidden" name="flag1" id="flag1" value="<%=flag%>">
			
		
			</div>
			<!--  end table-content  -->
	
			<div class="clear"></div>
		 
		
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