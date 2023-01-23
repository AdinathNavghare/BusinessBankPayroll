<%@page import="payroll.DAO.ConnectionManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@page import="java.util.*"%>

 <% 
	try{      
		 String s[] = null;
		 String lkpCode = "";
		 int lkpSrno=0;
		 String lkpDisc = "";
	  // System.out.println("hi");
		 Connection con = null;
		 con = ConnectionManager.getConnection();
		 Statement st = con.createStatement(); 
		 ResultSet rs = st.executeQuery("select LKP_SRNO,LKP_DISC from LOOKUP where LKP_CODE='LPURP'");
		
	     List li = new ArrayList();
	    
			while(rs.next()) 
 			{ 	
				lkpSrno = rs.getInt("LKP_SRNO");
				lkpDisc = rs.getString("LKP_DISC");
			
 			    li.add(lkpDisc);
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
				int cnt = 1;
				for(int j=0; j<str.length; j++)
				{	
					/*if(str[j].toUpperCase().startsWith(query.toUpperCase()))
					{
						out.print(str[j]+"\n");
						if(cnt>=5)
							break;
						cnt++;
					}*/
					
					
					
					
				//	System.out.println(str[j]);
					
					
					
					
					 if(str[j].toUpperCase().contains(query.toUpperCase())
							 ||str[j].toUpperCase().contains(query.toUpperCase())
	
							)
					{
						flag = true;
						out.print(str[j]+"\n");
						/* if(cnt >= 20)//for list display size
							break; */
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