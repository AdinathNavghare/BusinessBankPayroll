<%@page import="payroll.DAO.ConnectionManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="java.lang.*"%>
<% int UID=Integer.parseInt(String.valueOf(session.getAttribute("UID"))); %>
 <% 
	try{      
		 String s[] = null;
		 String fname = "";
		 String EMPNO = "";
		 String EMPCODE = "";
		 String LNAME = "";
		 Connection con = null;
		 con = ConnectionManager.getConnection();
		 Statement st = con.createStatement();
		 Statement st1 = con.createStatement();
		 Statement st2 = con.createStatement();
		 ResultSet rs=null;
		 int emproleid=0;
		 int empno=0;
		 int prjsrno=0;
		 int SYSrole=0;
		 int dept=0;
		 System.out.println("listttttttttt111111111....UID.."+UID);
		
		 ResultSet roleset = st.executeQuery("select ROLEID from ROLES where ROLENAME LIKE 'branch users' ");
		 
		 if(roleset.next())
		 {
			 SYSrole = roleset.getInt("ROLEID");
		 }
		 
		 
		 ResultSet rs1 = st1.executeQuery("select ROLEID from USERROLES where USERID="+UID);
		 if(rs1.next())
		 {
			 emproleid = rs1.getInt("ROLEID");
		 }
		 if(emproleid==SYSrole)
		 {
			 ResultSet rs12 = st.executeQuery("select Emp_ID from Users where User_ID="+UID);
			 if(rs12.next())
			 {
			 	 empno	= rs12.getInt("Emp_ID");
			 	 if(empno==0)
			 	 {
			 		 ResultSet rs12u = st1.executeQuery("select Emp_Code from Users where User_ID="+UID);
			 		 if(rs12u.next())
			 		 {
			 			 rs = st2.executeQuery(" select EMPNO,EMPCODE,FNAME,LNAME from EMPMAST where STATUS = 'A' and empno in(select distinct(empno) from emptran e where  e.PRJ_SRNO=(SELECT SUBSTRING('"+rs12u.getString("Emp_Code")+"', (PATINDEX('%[0-9]%', '"+rs12u.getString("Emp_Code")+"')), LEN('"+rs12u.getString("Emp_Code")+"'))) and e.srno= "+
			 												" (select max(srno) from emptran t where t.empno=e.empno) ) ");
			 		 		System.out.println(" select EMPNO,EMPCODE,FNAME,LNAME from EMPMAST where STATUS = 'A' and empno in(select distinct(empno) from emptran e where  e.PRJ_SRNO=(SELECT SUBSTRING('"+rs12u.getString("Emp_Code")+"', (PATINDEX('%[0-9]%', '"+rs12u.getString("Emp_Code")+"')), LEN('"+rs12u.getString("Emp_Code")+"'))) and e.srno= "+
										" (select max(srno) from emptran t where t.empno=e.empno) ) ");
			 		 }
			 	 }
			 	 else
			 	 {
			 		ResultSet rs13 = st.executeQuery("select PRJ_SRNO,DEPT from emptran where srno=(select max(srno) from emptran where empno="+empno+") and empno="+empno);
				 	  if(rs13.next())
				 	  {
					  	prjsrno	= rs13.getInt("PRJ_SRNO");
					  	dept	= rs13.getInt("DEPT");
				 	  }
					  rs = st1.executeQuery("select e.EMPNO,e.EMPCODE,e.FNAME,e.LNAME from EMPMAST e,emptran et " +
				 									"where e.STATUS = 'A' and et.srno=(select max(t.srno) from emptran t where t.empno=et.empno ) and et.prj_srno="+prjsrno+" and e.EMPNO=et.empno and et.DEPT="+dept+" ");
			 	 		System.out.println("select e.EMPNO,e.EMPCODE,e.FNAME,e.LNAME from EMPMAST e,emptran et " +
									"where e.STATUS = 'A' and et.srno=(select max(t.srno) from emptran t where t.empno=et.empno ) and et.prj_srno="+prjsrno+" and e.EMPNO=et.empno and et.DEPT="+dept+" ");
			 	 }
			 }
			  
		 	 
		 }
		 else
		 {
		 	 rs = st2.executeQuery("select EMPNO,EMPCODE,FNAME,LNAME from EMPMAST where STATUS = 'A' ");
		 	 System.out.println("no match for roleid");
		 }
	     List li = new ArrayList();
	    
			while(rs.next()) 
 			{ 	
				fname = rs.getString("FNAME");
				EMPNO = rs.getString("EMPNO");
				EMPCODE = rs.getString("EMPCODE");
				LNAME = rs.getString("LNAME");
 			    li.add(fname+" "+LNAME +":"+EMPCODE+":"+EMPNO);
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
					if(str[j].toUpperCase().contains(query.toUpperCase())||str[j].toUpperCase().contains(query.toUpperCase()) || lname[1].toUpperCase().contains(query.toUpperCase()) || lname[1].toUpperCase().contains(query.toUpperCase()) || substr.contains(query.toUpperCase())||substr.toUpperCase().contains(query.toUpperCase()) || substr1.contains(query.toUpperCase()) ||substr1.toUpperCase().contains(query.toUpperCase()))
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