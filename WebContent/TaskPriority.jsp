 <%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="payroll.DAO.ConnectionManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="javax.mail.SendFailedException"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager" %>
<%@page import="java.sql.PreparedStatement" %>
<%@page import="java.sql.SQLException" %>
<%@page import="java.sql.Statement" %>
<%@page import="java.sql.ResultSet" %>
<%@page import="payroll.DAO.*" %>
<%@page import="payroll.Model.*" %>
 
 
 <!-- ---------------------priority unknown -------------------------------------------------------------->
 
 <div>     
 <div> 
   <div class="tabcontent" id="unknown" style="margin-bottom: 20%;">
   <table id="customers">
		        <tr>
		            <th width="70" bgcolor="1F5FA7">Action</th>
                    <th >Task Id</th >
                   	<th>Project</th>
		        	<th>Priority</th>
		        	<th>Task Name</th>
		        	<th>Sub Task</th> 
		        	<th>Status</th>
		       	    <th>Type</th>  	
	       			<th>Est. Time</th>
	       			<th>Start Date</th>
		     		<th>Due Date</th>	
	           </tr>
	<tr class="alt">
  <%
    Connection con1=null;
	
	 ResultSet rs9;
	
	 
   	try
		   	{
   		       con1 = ConnectionManager.getConnection();
		       Statement stmt9 = con1.createStatement();
   	    
   		  String str9="select * from TASK where CONDITION='Active' and TASK_PRIORITY='unknown';";
   		
   	     rs9=stmt9.executeQuery(str9);
   	    
   	     while(rs9.next())
   		{
   %>
   		<tr class="alt">
					<td align="center">
					<a href="#" class="btn btn-default btn-xs purple" onclick="detect(<%=rs9.getInt("TASK_ID") %>)"><i class="fa fa-trash-o"></i></a> 
					<a href="#" class="btn btn-default btn-xs purple" onclick="window.document.location.href='EditTask.jsp?Tid=<%=rs9.getInt("TASK_ID")%>'"><i class="fa fa-edit"></i></a></td></td>
					
					<td align="center"><%=rs9.getInt("TASK_ID") %></td> 
					<td align="center"><a href="display.jsp?name=<%=rs9.getString("PROJECT")%>&Pid=<%=rs9.getInt("ProjectId")%>" ><%=rs9.getString("PROJECT")%> </a></td>                         
					<td align="center"><%=rs9.getString("TASK_PRIORITY") %></td>     
					<td align="center"><%=rs9.getString("name") %></td>
				    <td align="center"><%=rs9.getString("LABEL") %></td>
				    <td align="center"><%=rs9.getString("TASK_STATUS") %></td>    
			        <td align="center"><%=rs9.getString("TASK_TYPE") %></td>
			        <td align="center"><%=rs9.getString("ESTIMATED_TIME") %></td>
			        <td align="center"><%=rs9.getString("TASK_START_DATE") %></td>
			        <td align="center"><%=rs9.getString("DUE_DATE") %></td>   
			       
			        	    
			        <%-- <td align="center"><%=rs9.getString("ASSIGNED_TO") %></td> --%>
					</tr>
	   	<% 	} %>  		
<%
        rs9.close();
		
	    con1.close();
      
   	}
   	catch(Exception e)
   	{
   		e.printStackTrace();
   	}
  
 %>
 </tr>
 </table>
 </div>
 
 <!-- ---------------------End priority unknown-------------------------------------------------------------->
 
  <!-- ---------------------priority Low -------------------------------------------------------------->
 
 <div>     
 <div> 
   <div class="tabcontent" id="low" style="margin-bottom: 20%;">
  <table id="customers">
		        <tr>
		            <th width="70" bgcolor="1F5FA7">Action</th>
                    <th >Task Id</th >
                   	<th>Project</th>
		        	<th>Priority</th>
		        	<th>Task Name</th>
		        	<th>Sub Task</th> 
		        	<th>Status</th>
		       	    <th>Type</th>  	
	       			<th>Est. Time</th>
	       			<th>Start Date</th>
		     		<th>Due Date</th>	
	           </tr>
	<tr class="alt">
  <%
    
	 con1 = ConnectionManager.getConnection();

	 ResultSet rs10;
	 Statement stmt10 = con1.createStatement();
	 
   	try
   	{
   	    
   		  String str10="select * from Task where CONDITION='Active' and TASK_PRIORITY='Low';";
   		
   	     rs10=stmt10.executeQuery(str10);
   	    
   	     while(rs10.next())
   		{
   %>
   			 <tr class="alt">
					<td align="center">
					<a href="#" class="btn btn-default btn-xs purple" onclick="detect(<%=rs10.getInt("TASK_ID") %>)"><i class="fa fa-trash-o"></i></a> 
					<a href="#" class="btn btn-default btn-xs purple" onclick="window.document.location.href='EditTask.jsp?Tid=<%=rs10.getInt("TASK_ID")%>'"><i class="fa fa-edit"></i></a></td></td>
					
					<td align="center"><%=rs10.getInt("TASK_ID") %></td> 
					<td align="center"><a href="display.jsp?name=<%=rs10.getString("PROJECT")%>&Pid=<%=rs10.getInt("ProjectId")%>" ><%=rs10.getString("PROJECT")%> </a></td>	                             
					<td align="center"><%=rs10.getString("TASK_PRIORITY") %></td>     
					<td align="center"><%=rs10.getString("name") %></td>
					<td align="center"><%=rs10.getString("LABEL") %></td>
				    <td align="center"><%=rs10.getString("TASK_STATUS") %></td>    
			        <td align="center"><%=rs10.getString("TASK_TYPE") %></td>
			        <td align="center"><%=rs10.getString("ESTIMATED_TIME") %></td>
			        <td align="center"><%=rs10.getString("TASK_START_DATE") %></td>
			        <td align="center"><%=rs10.getString("DUE_DATE") %></td>   
			         
			        
			       <%--  <td align="center"><%=rs10.getString("ASSIGNED_TO") %></td> --%>
					</tr>	 
   	<% 	} %>
   	  
     		</tbody>  
   		</table>
 	</div>
</div> 
<%
        rs10.close();
		
	    con1.close();
      
   	}
   	catch(Exception e)
   	{
   		e.printStackTrace();
   	}
  
 %>
 </div>
 
 <!-- ---------------------End priority Low-------------------------------------------------------------->
 
 <!-- ---------------------priority Medium -------------------------------------------------------------->
 
 <div>     
 <div> 
   <div class="tabcontent" id="medium" style="margin-bottom: 20%;">
  <table id="customers">
		        <tr>
		            <th width="70" bgcolor="1F5FA7">Action</th>
                    <th >Task Id</th >
                   	<th>Project</th>
		        	<th>Priority</th>
		        	<th>Task Name</th>
		        	<th>Sub Task</th> 
		        	<th>Status</th>
		       	    <th>Type</th>  	
	       			<th>Est. Time</th>
	       			<th>Start Date</th>
		     		<th>Due Date</th> 	
	           </tr>
	<tr class="alt">
  <%
    
	 con1 = ConnectionManager.getConnection();

	 ResultSet rs11;
	 Statement stmt11 = con1.createStatement();
	 
   	try
   	{
   	    
   		  String str11="select * from TASK where CONDITION='Active' and TASK_PRIORITY='Medium';";
   		
   	     rs11=stmt11.executeQuery(str11);
   	    
   	     while(rs11.next())
   		{
   %>
   			  <tr class="alt">
					<td align="center">
					<a href="#" class="btn btn-default btn-xs purple" onclick="detect(<%=rs11.getInt("TASK_ID") %>)"><i class="fa fa-trash-o"></i></a> 
					<a href="#" class="btn btn-default btn-xs purple" onclick="window.document.location.href='EditTask.jsp?Tid=<%=rs11.getInt("TASK_ID")%>'"><i class="fa fa-edit"></i></a></td></td>
					
					<td align="center"><%=rs11.getInt("TASK_ID") %></td>  
					<td align="center"><a href="display.jsp?name=<%=rs11.getString("PROJECT")%>&Pid=<%=rs11.getInt("ProjectId")%>" ><%=rs11.getString("PROJECT")%> </a></td> 	                            
					<td align="center"><%=rs11.getString("TASK_PRIORITY") %></td>     
					<td align="center"><%=rs11.getString("name") %></td>
					<td align="center"><%=rs11.getString("LABEL") %></td>
				    <td align="center"><%=rs11.getString("TASK_STATUS") %></td>    
			        <td align="center"><%=rs11.getString("TASK_TYPE") %></td>
			        <td align="center"><%=rs11.getString("ESTIMATED_TIME") %></td>
			        <td align="center"><%=rs11.getString("TASK_START_DATE") %></td>
			        <td align="center"><%=rs11.getString("DUE_DATE") %></td>   
			          
			        
			       <%--  <td align="center"><%=rs11.getString("ASSIGNED_TO") %></td> --%>
					</tr>
   	<% 	} %>
<%
        rs11.close();		
	    con1.close();  
   	}
   	catch(Exception e)
   	{
   		e.printStackTrace();
   	}
  
 %>
 </tr>
 </table>
 </div>
 
 <!-- ---------------------End priority Medum-------------------------------------------------------------->
 
  <!-- ---------------------priority High -------------------------------------------------------------->
 
 <div>     
 <div> 
   <div class="tabcontent" id="high" style="margin-bottom: 20%;">
  <table id="customers">
		        <tr>
		            <th width="70" bgcolor="1F5FA7">Action</th>
                    <th >Task Id</th >
                   	<th>Project</th>
		        	<th>Priority</th>
		        	<th>Task Name</th>
		        	<th>Sub Task</th> 
		        	<th>Status</th>
		       	    <th>Type</th>  	
	       			<th>Est. Time</th>
	       			<th>Start Date</th>
		     		<th>Due Date</th> 	
	           </tr>
	<tr class="alt">
  <%
    
	 con1 = ConnectionManager.getConnection();

	 ResultSet rs12;
	 Statement stmt12 = con1.createStatement();
	 
   	try
   	{
   	    
   		  String str12="select * from TASK where CONDITION='Active' and TASK_PRIORITY='High';";
   		
   	     rs12=stmt12.executeQuery(str12);
   	    
   	     while(rs12.next())
   		{
   %>
   			 <tr class="alt">
					<td align="center">
					<a href="#" class="btn btn-default btn-xs purple" onclick="detect(<%=rs12.getInt("TASK_ID") %>)"><i class="fa fa-trash-o"></i></a> 
					<a href="#" class="btn btn-default btn-xs purple" onclick="window.document.location.href='EditTask.jsp?Tid=<%=rs12.getInt("TASK_ID")%>'"><i class="fa fa-edit"></i></a></td></td>
					
					<td align="center"><%=rs12.getInt("TASK_ID") %></td>
					<td align="center"><a href="display.jsp?name=<%=rs12.getString("PROJECT")%>&Pid=<%=rs12.getInt("ProjectId")%>" ><%=rs12.getString("PROJECT")%> </a></td> 	                              
					<td align="center"><%=rs12.getString("TASK_PRIORITY") %></td>     
					<td align="center"><%=rs12.getString("name") %></td>
					 <td align="center"><%=rs12.getString("LABEL") %></td>
				    <td align="center"><%=rs12.getString("TASK_STATUS") %></td>    
			        <td align="center"><%=rs12.getString("TASK_TYPE") %></td>
			        <td align="center"><%=rs12.getString("ESTIMATED_TIME") %></td>
			        <td align="center"><%=rs12.getString("TASK_START_DATE") %></td>
			        <td align="center"><%=rs12.getString("DUE_DATE") %></td>   
			       
			        
			       <%--  <td align="center"><%=rs12.getString("ASSIGNED_TO") %></td> --%>
					</tr> 
   	<% 	} %>
<%
        rs12.close();		
	    con1.close();
      
   	}
   	catch(Exception e)
   	{
   		e.printStackTrace();
   	}
  
 %>
 </tr>
 </table>
 </div>
 
 <!-- ---------------------End priority High-------------------------------------------------------------->
  <!-- ---------------------priority Urgent -------------------------------------------------------------->
 
 <div>     
 <div> 
   <div class="tabcontent" id="urgent" style="margin-bottom: 20%;">
  <table id="customers">
		        <tr>
		            <th width="70" bgcolor="1F5FA7">Action</th>
                    <th >Task Id</th >
                   	<th>Project</th>
		        	<th>Priority</th>
		        	<th>Task Name</th>
		        	<th>Sub Task</th> 
		        	<th>Status</th>
		       	    <th>Type</th>  	
	       			<th>Est. Time</th>
	       			<th>Start Date</th>
		     		<th>Due Date</th> 	
	           </tr>
	<tr class="alt">
  <%
    
	 con1 = ConnectionManager.getConnection();

	 ResultSet rs13;
	 Statement stmt13 = con1.createStatement();
	 
   	try
   	{
   	    
   		  String str13="select * from Task where CONDITION='Active' and TASK_PRIORITY='Urgent';";
   		
   	     rs13=stmt13.executeQuery(str13);
   	    
   	     while(rs13.next())
   		{
   %>
   			 <tr class="alt">
					<td align="center">
					<a href="#" class="btn btn-default btn-xs purple" onclick="detect(<%=rs13.getInt("TASK_ID") %>)"><i class="fa fa-trash-o"></i></a> 
					<a href="#" class="btn btn-default btn-xs purple" onclick="window.document.location.href='EditTask.jsp?Tid=<%=rs13.getInt("TASK_ID")%>'"><i class="fa fa-edit"></i></a></td></td>
					
					<td align="center"><%=rs13.getInt("TASK_ID") %></td> 
					<td align="center"><a href="display.jsp?name=<%=rs13.getString("PROJECT")%>&Pid=<%=rs13.getInt("ProjectId")%>" ><%=rs13.getString("PROJECT")%> </a></td> 	                             
					<td align="center"><%=rs13.getString("TASK_PRIORITY") %></td>     
					<td align="center"><%=rs13.getString("name") %></td>
					<td align="center"><%=rs13.getString("LABEL") %></td>
				    <td align="center"><%=rs13.getString("TASK_STATUS") %></td>    
			        <td align="center"><%=rs13.getString("TASK_TYPE") %></td>
			        <td align="center"><%=rs13.getString("ESTIMATED_TIME") %></td>
			        <td align="center"><%=rs13.getString("TASK_START_DATE") %></td>
			        <td align="center"><%=rs13.getString("DUE_DATE") %></td>   
			        
			        
			        <%-- <td align="center"><%=rs13.getString("ASSIGNED_TO") %></td> --%>
					</tr>	 
   	<% 	} %>
   	  
<%
        rs13.close();	
	    con1.close();
      
   	}
   	catch(Exception e)
   	{
   		e.printStackTrace();
   	}
  
 %>
 </tr>
 </table>
 </div>
 
 <!-- ---------------------End priority Urgent-------------------------------------------------------------->