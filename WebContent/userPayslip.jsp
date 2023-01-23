<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="javax.servlet.http.HttpSession" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<title>User</title>

<script src="js/jquery/jquery-1.4.2.min.js" type="text/javascript"></script>
<script type="text/javascript" src="js/datetimepicker_banking.js"></script>
<script type="text/javascript" src="js/employeeValidation.js"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>
</head>
<body> 

<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		
	</div>
	<!-- end page-heading -->

	<table border="0" width="100%" cellpadding="0" cellspacing="0" id="content-table">
	<tr>
		<th rowspan="3" class="sized"><img src="../images/shared/side_shadowleft.jpg" width="20" height="300" alt="" /></th>
		<th class="topleft"></th>
		<td id="tbl-border-top">&nbsp;</td>
		<th class="topright"></th>
		<th rowspan="3" class="sized"><img src="../images/shared/side_shadowright.jpg" width="20" height="300" alt="" /></th>
	</tr>
	<tr>
		<td id="tbl-border-left"></td>
		<td>
		<!--  start content-table-inner ...................................................................... START -->
		<div id="content-table-inner">
		
			<!--  start table-content  -->
			<div id="table-content">
			

	          <form name="payslipform" action="ReportServlet">
	         
	          <table id="customer">
	          		<tr>
	          		<td><input type="hidden" id="action" name="action" value="payslip"></input>
	          		<input type="hidden" id="EMPNO" name="EMPNO" value="<%=session.getAttribute("EMPNO") %>"></input>
	          		<input type="hidden" id="EMPNO1" name="EMPNO1" value="<%=session.getAttribute("EMPNO") %>"></input></td>
	          		<td>DATE : </td><td><input name="date" size="20" id="date" type="text" value=" " readonly="readonly" onFocus="if(value=='dd-mmm-yyyy') {value=''}" onBlur="if(value=='') {value='dd-mmm-yyyy';}">&nbsp;
	          		<img src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
				onClick="javascript:NewCssCal('date', 'ddmmmyyyy')" />
	          		</td>
	          		</tr>
	          		<tr>
						<td colspan="5" align="center"><input type="submit"
							value="Go" /></td>
			</tr>
	          </table>
	         <div >
	         
	         
	         </div>
	          </form>
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