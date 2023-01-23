<%@page import="org.apache.commons.lang.CharUtils"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="payroll.Core.ErrorLog"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.FileWriter"%>
<%@page import="java.io.BufferedWriter"%>
<%@page import="java.io.File"%>
<%@page import="java.sql.PreparedStatement"%>

<%@page import="java.sql.Statement"%>
<%@page import="payroll.DAO.ConnectionManager"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
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
Connection con = null;
Statement st = null;
int EMPCODE=1;
String temp_empcode="";
try
{
	con = ConnectionManager.getConnection();
	st = con.createStatement();
	
ResultSet rs = st.executeQuery("Select isnull((select MAX(EMPCODE) from EMPMAST),'A001')");
if(rs.next())
{
	temp_empcode=rs.getString(1);	
}
String letter="";
char[] chars = temp_empcode.toCharArray();
String	str = temp_empcode.replaceAll("[^\\d.]", "");

int result=0;

char res;
for (char c : chars) {
    if(Character.isLetter(c))
    {
    	letter+=c;
    	 result = (int)c; 
    	
    }	

}




EMPCODE=Integer.parseInt(str.trim());
EMPCODE++;



if(EMPCODE<=9)
{
	letter=letter+"00"+EMPCODE;
}
else if(EMPCODE>9 && EMPCODE<=99)
{
	
	letter=letter+"0"+EMPCODE;
	
}
else if (EMPCODE>999)
{
	EMPCODE=1;
	 
    res=(char) (result+1);
	letter=res+"00"+EMPCODE;
}







}
catch(Exception e)
{	 
	e.printStackTrace();
}
%>
</body>
</html>