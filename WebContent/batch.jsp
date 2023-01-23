<%@page import="payroll.DAO.ReleaseBatchHandler"%>
<%@page import="java.sql.*"%>
<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="org.apache.commons.collections.iterators.ArrayListIterator"%>
<%@page import="payroll.Model.EmpAddressBean"%>
<%@page import="payroll.Model.Lookup"%>
<%@page import="java.util.*"%>
<%
String date=request.getParameter("date"); 
date="01-"+date;
 String buffer="<select name='batch' id='batch' ><option value='-1'>Select</option>";  
 try{

	 ArrayList<Integer> releaseBatchList=new ArrayList<Integer>();
     ReleaseBatchHandler releaseBatchHandler = new ReleaseBatchHandler();
     releaseBatchList=releaseBatchHandler.getReleaseBatchList(date);
     System.out.print("size on batch.jsp page   :"+releaseBatchList.size());
     int index=0;
  for(int  batchNumber : releaseBatchList)
  {
	  
	  %>
       <option value="<%=index%>"><%=batchNumber%></option>  
     
       <%
       index++;
  }
     
 buffer=buffer+"</select>";  
 response.getWriter().println(buffer); 
 }
 catch(Exception e)
 {
     
 }

%>