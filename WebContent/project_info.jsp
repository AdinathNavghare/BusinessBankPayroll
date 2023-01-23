<%@page import="payroll.DAO.ConnectionManager"%>
<%@page import="payroll.Model.EmpOffBean"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%>
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
String EMPNO=request.getParameter("empno");
String date=request.getParameter("eff_date");
Connection con=null;
ResultSet rs=null;
Statement st = null;
EmpOffBean empaddofficinfo = new EmpOffBean();
try
{
	
	String prj="";
	con = ConnectionManager.getConnection();
	st = con.createStatement();
	rs=st.executeQuery("select * from emptran where empno = '"+EMPNO+"' and EFFDATE <='"+date+"'");
//	rs=st.executeQuery("select * from emptran where empno = '"+EMPNO+"'");
	
	while(rs.next())
	{
		String Prj_Name = null;
		String Prj_Code = null;
		con = ConnectionManager.getConnectionTech();
		Statement stmt = con.createStatement();
	//	ResultSet rs1 = stmt.executeQuery("SELECT Site_Name, Project_Code from Project_Sites where Project_Code='"+rs.getString("PRJ_CODE")+"'");
		ResultSet rs1 = stmt.executeQuery("SELECT REPLACE(Site_Name,':','-') as Site_Name, Project_Code from Project_Sites where Site_isdeleted = 0 and Site_ID='"+rs.getString("PRJ_SRNO")+"'");
		while(rs1.next()){
			Prj_Name = rs1.getString(1);
			Prj_Code = rs1.getString(2);
		}
		rs1.close();
		stmt.close();
	
	
	/*while(rs.next())
	{*/
		empaddofficinfo.setPrj_code(Prj_Code);
		empaddofficinfo.setPrj_name(Prj_Name);
	
	
		prj=""+Prj_Name+":"+Prj_Code+":"+rs.getString("PRJ_SRNO");
	}
	%>
	<input style="border:none;" type="text" id="prj_no" name="prj_no" size="100" value="<%=prj%>"  readonly="readonly">
	<%
		
		
	
	con.close();
}
catch(Exception e)
{
	e.printStackTrace();
}




%>
</body>
</html>