<%@page import="payroll.DAO.ConnectionManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@page import="java.util.*"%>
<%@page import="payroll.DAO.*"%>
<%@page import="payroll.Model.*"%>

 <% 
	try{      
		 String s[]=null;
		 String desc1 ="";
		 String desc ="";
		 String srno="";
		  
			Connection con = null;
			 con = ConnectionManager.getConnection();
		Statement st=con.createStatement(); 
		ResultSet rs = st.executeQuery("select * from lookup where LKP_CODE='ET'");
		
	    	List li = new ArrayList();
	    
			while(rs.next()) 
 			{ 	
			desc1=rs.getString("LKP_DISC");
				StringTokenizer stm = new StringTokenizer(desc1,".");
			    while(stm.hasMoreTokens())
			    {
			    	
			    	desc=stm.nextToken();
			    }
				
				
				
				srno=rs.getString("LKP_SRNO");
				 
 			    li.add(desc+":"+srno);
 			}  
			
			System.out.println("size is"+li.size());
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
					if(str[j].toUpperCase().contains(query.toUpperCase()) || substr.contains(query.toUpperCase()))
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