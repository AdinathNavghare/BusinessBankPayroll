<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.CallableStatement"%>
<%@page import="com.mysql.*"%>
<%@page import="java.sql.Connection"%>
<%@page import="payroll.DAO.ConnectionManager"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"    pageEncoding="ISO-8859-1"%>
<!-- 
CREATED by -:ABC XYZ on 10-10-2015

 -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Stored Procedures Demo</title>
</head>
<body>
<%

try{
	 Connection con = ConnectionManager.getConnection();
     CallableStatement stmt = null;
   
   //INSERT INFORMATION through Stored Procedures
   /*  stmt = con.prepareCall("{call insertEmployee(?,?,?,?,?)}");
    stmt.setInt(1, 5);
    stmt.setInt(2, 5);
    stmt.setString(3, "aaa");
    stmt.setString(4, "aaa");
    stmt.setString(5, "aaa");
     
    //register the OUT parameter before calling the stored procedure
   
     
    stmt.executeUpdate();
    System.out.println("Employee Record Save Success::"); 
    //read the OUT parameter now
   
     */
    
   
      //SELECTING INFORMATION through Stored Procedures
     PreparedStatement pstmt = con.prepareStatement("{call selectEmployee(?)}");
    
     pstmt.setInt(1, 2);
     ResultSet rs = pstmt.executeQuery();
     while (rs.next()) {
         System.out.println("USER id \t: "+rs.getString("USERID"));
         System.out.println("NAME \t: "+rs.getString("UNAME"));
         System.out.println("PASS\t: "+rs.getString("UPWD"));
         System.out.println("STATUS\t: "+rs.getString("USTATUS"));
     }
     rs.close();
     pstmt.close();
     
    
    
     
    /*  

     //Update INFORMATION through Stored Procedures
     PreparedStatement pstmt = con.prepareStatement("{call updateEmployee(?,?)}");
    
     pstmt.setInt(1, 1);
     pstmt.setString(2, "VIJAY");
     pstmt.execute();
     System.out.println("UPDATED SUCCESSFULLY....");
     
    
      */
    
}catch(Exception e){
    e.printStackTrace();
}







%>
</body>
</html>