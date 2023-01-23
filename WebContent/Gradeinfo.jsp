<%@page import="payroll.DAO.ConnectionManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@page import="java.util.*"%>

  <% 
	try{  
		/*
		created date:14/09/2015
		
		
		*/
		 Connection con = null;
		 String gdcd=request.getParameter("grade");
		 String stage=request.getParameter("stage");
		 //System.out.println("---------------"+gdcd);
		// System.out.println("---------------"+stage);
		 con = ConnectionManager.getConnection();
		 Statement st = con.createStatement(); 
		 ResultSet rs = st.executeQuery("SELECT * FROM GRADE_MASTER WHERE GRADE_CODE="+gdcd+" AND srno="+stage+" AND grade_status=0");
			while(rs.next()) 
 			{ 
				//System.out.println("into loop"+rs.getInt("SRNO"));
 			%>
				
				<input type='hidden' name='bsc1' id='bsc1' value='<%=rs.getInt("BASIC")%>'>
				<input type='hidden' name='d1' id='d1' value='<%=rs.getInt("DA")%>'>
				<input type='hidden' name='h1' id='h1' value='<%=rs.getInt("HRA")%>'>
				
				<input type='hidden' name='v1' id='v1' value='<%=rs.getInt("vda")%>'>
				<input type='hidden' name='m1' id='m1' value='<%=rs.getInt("med_all")%>'>
				<input type='hidden' name='e1' id='e1' value='<%=rs.getInt("edu_all")%>'>
				<input type='hidden' name='c1' id='c1' value='<%=rs.getInt("conv_all")%>'>
				
			<%
			}  
					
 		rs.close(); 
 		st.close(); 
		con.close();

		} 
		catch(Exception e){ 
 			e.printStackTrace(); 
 		}

 %>