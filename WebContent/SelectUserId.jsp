<%@page import="payroll.DAO.ConnectionManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@page import="java.util.*"%>

 <% 
	try{      
		 String s[]=null;
		 String fname ="";
		 String EMPNO="";
		 String EMPCODE="";
		 String LNAME="";
		 String userid = null;
		 Connection con = null;
		 Connection conTech = null;
		 con = ConnectionManager.getConnection();
		 conTech = ConnectionManager.getConnectionTech();
		 Statement st=con.createStatement();
		 Statement stTech=conTech.createStatement();
		 ResultSet rsUser = st.executeQuery("select USERID from USERROLES");
		 StringBuilder sb = new StringBuilder();
		 while(rsUser.next()){
			     if(sb.length() > 0){
			         sb.append(',');
			     }
			     sb.append(rsUser.getString(1));
			     userid = sb.toString();
		 }
		 ResultSet rsUsrMast = stTech.executeQuery("SELECT EMP_ID from USERS where USER_ID not in ("+userid+")");
		 while(rsUsrMast.next()){
		     if(sb.length() > 0){
		         sb.append(',');
		     }
		     sb.append(rsUsrMast.getString(1));
		     EMPNO = sb.toString();
		 }
		 ResultSet rs = st.executeQuery("select * from EMPMAST E where E.EMPNO IN ("+EMPNO+")");
		
	    	List li = new ArrayList();
	    
	    	while(rs.next()) 
 			{ 	
				fname=rs.getString("FNAME");
				EMPNO=rs.getString("EMPNO");
				EMPCODE=rs.getString("EMPCODE");
				LNAME=rs.getString("LNAME");
 			    li.add(fname+"  "+LNAME +":"+EMPCODE+":"+EMPNO);
 			}
			
			String[] str = new String[li.size()];			
			Iterator it = li.iterator();
			
			int i = 0;
			while(it.hasNext())
			{
				String p = (String)it.next();	
				str[i] = p;
				i++;
			}
		
			//jQuery related start		
				String query = (String)request.getParameter("q");
				boolean flag = false;
				int cnt=1;
				for(int j=0;j<str.length;j++)
				{
					/*if(str[j].toUpperCase().startsWith(query.toUpperCase()))
					{
						out.print(str[j]+"\n");
						if(cnt>=5)
							break;
						cnt++;
					}*/
					int num = str[j].indexOf(":")+1;
					String substr = str[j].substring(num);
					if(str[j].toUpperCase().startsWith(query.toUpperCase()) || substr.startsWith(query.toUpperCase()))
					{
						flag = true;
						out.print(str[j]+"\n");
						if(cnt>=10)//for list display size
							break;
						cnt++;
					}
				}
				if(flag == false){
					out.print("Not Found Please Re-Enter");
				}
			//jQuery related end	
		
			
 		rs.close(); 
 		st.close(); 
		con.close();

		    } 
		catch(Exception e){ 
 			e.printStackTrace(); 
 		}

//www.java4s.com
 %>