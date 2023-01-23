<%@page import="payroll.Model.RoleBean"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
String action = request.getParameter("action")!=null?request.getParameter("action"):"";

%>
<div>
  <div align="center">CREATE ROLE
  
  <form action="RoleServlet" method="post">
  <table width="254" border="1" bgcolor="#F5F5F5">
  <input type="hidden" value="addRole" name="action">
    <tr>
      <td>Role Name :
      <input type="text" name="rolename"></td>
	  <td>Role Desc :
      <input type="text" name="roledesc"></td>
    </tr>
	  <tr>
	   <td align="center"><input type="submit" value="CREATE"></td>
    </tr>
   
  </table>
  </form>
</div>
<%
if(action.equals("showRole")){
ArrayList<RoleBean> arrList = (ArrayList<RoleBean>)request.getAttribute("roleList"); 


%>
		<div align="center">
			<table width="350" height="79" border="1" bgcolor="#F5F5F5">
				<th>Role Information</th>
				<tr>
					<th>Role ID</th>
					<th>Role Name</th>
					<th>Role Desc</th>
				</tr>
				<%for(RoleBean rBean : arrList){ %>
				<tr>
					<td><%=rBean.getROLEID() %></td>
					<td><%=rBean.getROLENAME() %></td>
					<td><%=rBean.getROLEDESC() %></td>

				</tr>
				<%} %>
		</table>
		</div>
		<%} %>
</div>
</body>
</html>