<%@page import="payroll.DAO.ConnectionManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@page import="java.util.*"%>

 <% 
	try{  
		
		 Connection con = null;
		 int  gdcd = Integer.parseInt(request.getParameter("grade"));
		 //System.out.println("---------------"+gdcd);
		 con = ConnectionManager.getConnection();
		 Statement st = con.createStatement(); 
		 ResultSet rs = st.executeQuery("SELECT * FROM GRADE_MASTER WHERE GRADE_CODE="+gdcd+" AND grade_status=0 order by grade_code,srno");
		 %>
			<option value='0'>Select</option>
		<%
			
		 while(rs.next()) 
 		{ 
			int srno = rs.getInt("SRNO");
			System.out.println("into loop"+rs.getInt("SRNO"));
			
			if(gdcd == 1 || gdcd == 2 || gdcd ==10){
				if(srno >20){
					break;
				}
			}
			if(gdcd == 3 || gdcd == 4){
				if(srno >31){
					break;
				}
			}
			if(gdcd == 11){
				if(srno >21){
					break;
				}
			}
			if(gdcd == 5 || gdcd == 6 || gdcd == 7 || gdcd == 8 || gdcd == 9 || gdcd == 3 ){
				if(srno >36){
					break;
				}
			}
			
 		%>
				<option value='<%=rs.getInt("SRNO")%>'><%=rs.getInt("SRNO")%></option>
		<%
		}  
					
 		rs.close(); 
 		st.close(); 
		con.close();

		} 
		catch(Exception e){ 
 			e.printStackTrace(); 
 		}

//www.java4s.com
 %>