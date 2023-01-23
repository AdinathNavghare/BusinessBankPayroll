<%@page import="payroll.DAO.ConnectionManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@page import="java.util.*"%>

 <% 
	try{      
		 String s[] = null;
		 String fname = "";
		 String EMPNO = "";
		 String EMPCODE = "";
		 String LNAME = "";
		 String STATUS = "";
		 Connection con = null;
		 con = ConnectionManager.getConnection();
		 Statement st = con.createStatement(); 
		 ResultSet rs = st.executeQuery("select E.*  from EMPMAST E  where  E.EMPNO IN (select distinct empno from paytran) AND E.EMPNO NOT IN (SELECT DISTINCT EMPNO FROM onhold WHERE status = 'h')");
		
	     List li = new ArrayList();
	    
			while(rs.next()) 
 			{ 	
				fname = rs.getString("FNAME");
				EMPNO = rs.getString("EMPNO");
				EMPCODE = rs.getString("EMPCODE");
				LNAME = rs.getString("LNAME");
				STATUS = rs.getString("STATUS");
				if(STATUS.equalsIgnoreCase("N")){
					STATUS = "Non-Active";
				}else if(STATUS.equalsIgnoreCase("A")){
					STATUS = "Active";
				}
				 li.add(fname+" "+LNAME +":"+EMPCODE+":"+EMPNO+": ("+STATUS.toUpperCase()+")");
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
					String[] lname = str[j].split(" ");
					int num = str[j].indexOf(":")+1;
					int num1 = str[j].lastIndexOf(":")+1;
					String substr = str[j].substring(num);
					String substr1 = str[j].substring(num1);
					
					
					//System.out.println(str[j]);
					
					
					
					
					if(str[j].toUpperCase().contains(query.toUpperCase())||str[j].toUpperCase().contains(query.toUpperCase()) || substr.contains(query.toUpperCase()) || substr1.contains(query.toUpperCase()) ||
							lname[1].toUpperCase().contains(query.toUpperCase()))
					{
						flag = true;
						out.print(str[j]+"\n");
						if(cnt >= 20)//for list display size
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