<%@page import="payroll.Model.OtherDetailBean"%>
<%@page import="payroll.Model.ExtraFieldBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="payroll.DAO.ExtraFieldHandler"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy DTS3 </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<script type="text/javascript" src="js/datetimepicker.js"></script>
<!--[if IE]>
<link rel="stylesheet" media="all" type="text/css" href="css/pro_dropline_ie.css" />
<![endif]-->
<%
String action=request.getParameter("action")!=null?request.getParameter("action"):"addemp";
%>
<script type="text/javascript">
		
		
		function validation()
		{
			var inp = document.getElementsByTagName('input');
			for(var i in inp)
			{
			if(inp[i].type == "text")
			{
			  
				if( document.getElementById(inp[i].id).value=="")
				{
					alert("Please Insert All Data");
					
					return false;
				}
			}
			}
			
		}	

</script>
</head>
<body style="overflow: hidden;"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer"  style="overflow-y:scroll; max-height:80%; ">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<% if(action.equalsIgnoreCase("addemp"))
	{
			
			%>
	<div id="content"  ><!--  start page-heading -->
		<div id="step-holder">
		<div class="step-light-right">&nbsp;</div>
		<div class="step-no-off">1</div>
		<div class="step-light-left"><a href="employee.jsp" >Employee Detail</a></div>
		<div class="step-light-right">&nbsp;</div>
		<div class="step-no-off">2</div>
		<div class="step-light-left"><a href="empQual.jsp">  Qualification </a></div>
		<div class="step-light-right">&nbsp;</div>
		<div class="step-no-off">3</div>
		<div class="step-light-left"><a href="empAddress.jsp"> Address </a></div>
		<div class="step-light-right">&nbsp;</div>
		<div class="step-no-off">4</div>
		<div class="step-light-left"><a href="empFamily.jsp">Family </a></div>
        <div class="step-light-right">&nbsp;</div>
        <div class="step-no-off">5</div>
		<div class="step-light-left"><a href="empExper.jsp">Experience </a></div>
		<div class="step-light-right">&nbsp;</div>
		<div class="step-no-off">6</div>
		<div class="step-light-left"><a href="officialInfo.jsp">Official Info</a></div>
		<div class="step-light-right">&nbsp;</div>
	
		<div class="step-no-off">7</div>
		<div class="step-light-left"><a href="awardInfo.jsp">Award</a></div>
		<div class="step-light-right">&nbsp;</div>
		
		<div class="step-no-off">8</div>
		<div class="step-light-left"><a href="Attachment.jsp">Attachment</a></div>
		<div class="step-light-right">&nbsp;</div>
		
		<div class="step-no">9</div>
		<div class="step-dark-left"><a href="otherDetail.jsp">Other Detail</a></div>
		<div class="step-dark-round">&nbsp;</div>
		<div class="clear"></div>
	</div>
	<%
	}
else if(action.equalsIgnoreCase("showemp"))
	{
//In else for Existing Employee Menus.........
	%>
<div id="content"  ><!--  start page-heading -->
			<div id="step-holder">
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">1</div>
			<div class="step-light-left"><a href="EmployeeServlet?action=employee" >Employee Detail</a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">2</div>
			<div class="step-light-left"><a href="EmployeeServlet?action=empQual">  Qualification </a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">3</div>
			<div class="step-light-left"><a href="EmployeeServlet?action=address"> Address </a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">4</div>
			<div class="step-light-left"><a href="EmployeeServlet?action=family">Family </a></div>
            <div class="step-light-right">&nbsp;</div>
            <div class="step-no-off">5</div>
			<div class="step-light-left"><a href="EmployeeServlet?action=experience">Experience </a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">6</div>
			<div class="step-light-left"><a href="EmployeeServlet?action=officialInfo">Official Info</a></div>
			<div class="step-light-right">&nbsp;</div>
			
			<div class="step-no-off">7</div>
			<div class="step-light-left"><a href="EmployeeServlet?action=awardInfo">Award</a></div>
			<div class="step-light-right">&nbsp;</div>
			
			<div class="step-no-off">8</div>
			<div class="step-light-left"><a href="Attachment.jsp?action=showemp">Attachment</a></div>
			<div class="step-light-right">&nbsp;</div>
			
			<div class="step-no">9</div>
			<div class="step-dark-left"><a href="otherDetail.jsp?action=showemp">Other Detail</a></div>
			<div class="step-dark-round"></div>
			<div class="clear"></div>
		</div>
<% 
	} 
%>
	</div>
	<div id="page-heading">
<h1>Other Details</h1>
</div>
	<!-- end page-heading -->
<%

%>
	<table border="0" width="100%"  cellpadding="0" cellspacing="0" id="content-table">
	<tr>
		<th rowspan="3" class="sized"><img src="images/shared/side_shadowleft.jpg" width="20" height="300" alt="" /></th>
		<th class="topleft"></th>
		<td id="tbl-border-top">&nbsp;</td>
		<th class="topright"></th>
		<th rowspan="3" class="sized"><img src="images/shared/side_shadowright.jpg" width="20" height="300" alt="" /></th>
	</tr>
	<tr>
		<td id="tbl-border-left"></td>
		<td align="center">
		<!--  start content-table-inner ...................................................................... START -->
		<div id="content-table-inner">
		
			<!--  start table-content  -->
			<div id="table-content">
			<%
			if(action.equalsIgnoreCase("addemp"))
			{
			ExtraFieldHandler exh = new ExtraFieldHandler();
			
			ArrayList<ExtraFieldBean> exlist = exh.getFieldNumber();
			int i=0;
			%>
			<form action="ExtraFieldServlet?action=saveOtherDetail" method="post" >
			<table width="600" border="1" id="customers" align="left" >
			<tr class="alt">
				<td colspan="2" align="center">Employee Code :- <%=session.getAttribute("empcode")==null?"":session.getAttribute("empcode") %>
					<input type="hidden" id="empno" name="empno" value="<%=session.getAttribute("empno")==null?"":session.getAttribute("empno") %>">
					&nbsp;&nbsp;Name :-<%=session.getAttribute("empname")==null?"":session.getAttribute("empname") %> 
				</td>
			</tr>
			<tr><td><table>
			<%
			
			for(ExtraFieldBean exbean : exlist)
			{
			i++;
			%>
			  <tr class="alt">
			    <td width="50" align="center"><%=i%></td>
			    <td width="300" align="left"><%=exbean.getFieldName() %></td>
			   
			    <td width="250">
			    <%
			    if(exbean.getType()==1)
			    {
			    %>
			    <input class="form-control" type="text" size="30" title="<%=exbean.getFieldDesc() %>" name="<%=exbean.getColumnName()%>" id="<%=exbean.getColumnName() %>" onBlur="TextCheck(this.id)">
			    <%} 
			    if(exbean.getType()==2)
			    {
			    %>
			    <input class="form-control" type="text" title="<%=exbean.getFieldDesc() %>" name="<%=exbean.getColumnName()%>" id="<%=exbean.getColumnName() %>" onblur="intValidation(this.id)">
			    <%} 
			    if(exbean.getType()==3)
			    {
			    %>
			    <input class="form-control" type="text" title="<%=exbean.getFieldDesc() %>" name="<%=exbean.getColumnName()%>" id="<%=exbean.getColumnName() %>" readonly="readonly" onblur="dateValidation(this.id)" >
			    <img
				src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
				onClick="javascript:NewCssCal('<%=exbean.getColumnName()%>', 'ddmmmyyyy')" />
			    
			    <%} %>
			    
			    </td>
			  </tr>
  			<%} %>
  <tr class="alt"><td colspan="4" align="center"><input class="form-control" type="submit"  class="myButton" value="Save">&nbsp;&nbsp;&nbsp;<input type="reset"  class="myButton"  value="Cancel"></td></tr>
</table></td></tr></table>
</form>
<%} 
			
			if(action.equalsIgnoreCase("showemp"))
			{
			ExtraFieldHandler exh = new ExtraFieldHandler();
			ArrayList<ExtraFieldBean> exlist = exh.getFieldNumber();
			
			String empno = session.getAttribute("empno").toString();
			OtherDetailBean otbean = exh.getOtherDetail(empno);
			int i=0;
			%>
			<form action="ExtraFieldServlet?action=updateOtherDetail" method="post">
			<table width="600" border="1" id="customers" align="left">
			
			<tr class="alt"><td colspan="2" align="center"><h4>Employee Code :- <%=session.getAttribute("empcode")==null?"":session.getAttribute("empcode") %>
					<input type="hidden" id="empno" name="empno" value="<%=session.getAttribute("empno") %>">
					&nbsp;&nbsp;&nbsp;&nbsp;Name :-<%=session.getAttribute("empname")==null?"":session.getAttribute("empname") %> 
					</h4></td>
				</tr>
			<%
			if(exlist.size()!=0)
			{
				
				%>
				
			<tr><td><table>
				<% 
			for(ExtraFieldBean exbean : exlist)
			{
			i++;
			%>
			  <tr class="alt">
			    <td width="50"><%=i%></td>
			    <td width="300" align="left"><%=exbean.getFieldName() %></td>
			   
			    <td width="250">
			    <%
			    if(exbean.getType()==1)
			    {
			    %>
			    <input class="form-control" type="text" title="<%=exbean.getFieldDesc() %>" name="<%=exbean.getColumnName()%>" id="<%=exbean.getColumnName() %>" value="<%=otbean.getField(exbean.getFieldNo())==null?"":otbean.getField(exbean.getFieldNo())%>">
			    <%} 
			    if(exbean.getType()==2)
			    {
			    %>
			    <input class="form-control" type="text" title="<%=exbean.getFieldDesc() %>" name="<%=exbean.getColumnName()%>" id="<%=exbean.getColumnName() %>" value="<%=otbean.getField(exbean.getFieldNo())==null?"":otbean.getField(exbean.getFieldNo())%>">
			    <%} 
			    if(exbean.getType()==3)
			    {
			    %>
			    <input class="form-control" type="text" title="<%=exbean.getFieldDesc() %>" name="<%=exbean.getColumnName()%>" id="<%=exbean.getColumnName() %>" readonly="readonly" value="<%=otbean.getField(exbean.getFieldNo())==null?"":otbean.getField(exbean.getFieldNo())%>">
			    <img
				src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
				onClick="javascript:NewCssCal('<%=exbean.getColumnName()%>', 'ddmmmyyyy')" />
			    
			    <%} %>
			    
			    </td>
			  </tr>
  			<%} %>
  <tr class="alt">
  <td colspan="4" align="center"><input type="submit"  class="myButton"  value="Update" >&nbsp;&nbsp;&nbsp;<input type="reset"  class="myButton"  value="Cancel"></td></tr>
			<%}
  %>
</table></td></tr>
</table>
</form>
<%} %>
			
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