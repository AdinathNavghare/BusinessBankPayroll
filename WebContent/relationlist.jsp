<%@page import="payroll.DAO.ConnectionManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@page import="java.util.*"%>

 <% 
	try{      
		 String s[]=null;
		 String fname ="";
		 String LKP_DISC="";
		 String LKP_SRNO="";
			Connection con = null;
			 con = ConnectionManager.getConnection();
		Statement st=con.createStatement(); 
		ResultSet rs = st.executeQuery("select LKP_SRNO,LKP_DISC from lookup where LKP_CODE='RELN' and LKP_SRNO!=0");
		
	    	List li = new ArrayList();
	    
			while(rs.next()) 
 			{ 	
				
				LKP_DISC=rs.getString("LKP_DISC");
				LKP_SRNO=rs.getString("LKP_SRNO");
				li.add(LKP_DISC);
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
						if(cnt>=5)
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