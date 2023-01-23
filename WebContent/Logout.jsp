<%@page import="payroll.DAO.UsrHandler"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<script type="text/javascript">
        window.history.forward();
      function noBack()
         {
        	$(function() {
        		 window.history.forward();
        	     return false;
        	});
        	  window.history.forward();
         }
</script>
<body onLoad="noBack();" onpageshow="if (event.persisted) noBack();">
    <%
    	try
    	{
    		
    		UsrHandler UH = new UsrHandler();
    		UH.removeLog(session.getAttribute("UID").toString(), session.getId());
    		session.invalidate();
    		response.sendRedirect("login.jsp?action=1");
    	}
        catch(Exception e)
   		{
        	//session.invalidate();
        	response.sendRedirect("login.jsp?action=0");
   		}
   		
     %>
 
</body>
</html>