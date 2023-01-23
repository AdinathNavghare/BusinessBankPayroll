<%@page import="java.sql.*"%>
<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="org.apache.commons.collections.iterators.ArrayListIterator"%>
<%@page import="payroll.Model.EmpAddressBean"%>
<%@page import="payroll.Model.Lookup"%>
<%@page import="java.util.*"%>
<%
String count=request.getParameter("count"); 
int recrNumber=Integer.parseInt(count);
 String buffer="<select name='city' id='city' ><option value='-1'>Select</option>";  
 try{

	 ArrayList<Lookup> result=new ArrayList<Lookup>();
     LookupHandler lkhp= new LookupHandler();
     result=lkhp.getSubLKP_DESC2("CITY-"+recrNumber);
     
  for(Lookup lkbean : result)
  {
	  
	  %>
       <option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
      
       <%
  
  }
     
 buffer=buffer+"</select>";  
 response.getWriter().println(buffer); 
 }
 catch(Exception e)
 {
     
 }

%>