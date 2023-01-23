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


   <!-- ---------------------Type Change Priority Rate(Hourly rate($ 25.00)) -------------------------------------------------------------->
 
 <div>     
 <div> 
   <div class="tabcontent" id="Change Priority Rate(Hourly rate($ 25.00))" style="margin-bottom: 20%;">
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
    Connection con9=null;
	 ResultSet rs21;
	
   	try
		   	{
   		       con9 = ConnectionManager.getConnection();
		       Statement stmt21 = con9.createStatement();
   	    
   		  String str21="select * from TASK where CONDITION='Active' and TASK_TYPE='Change Priority Rate(Hourly rate($ 25.00))';";
   		
   	     rs21=stmt21.executeQuery(str21);
   	    
   	     while(rs21.next())
   		{
   %>
   			 <tr class="alt">
					<td align="center">
					<a href="#" class="btn btn-default btn-xs purple" onclick="detect(<%=rs21.getInt("TASK_ID") %>)"><i class="fa fa-trash-o"></i></a> 
					<a href="#" class="btn btn-default btn-xs purple" onclick="window.document.location.href='EditTask.jsp?Tid=<%=rs21.getInt("TASK_ID")%>'"><i class="fa fa-edit"></i></a></td></td>
					
					<td align="center"><%=rs21.getInt("TASK_ID") %></td> 
					<td align="center"><a href="display.jsp?name=<%=rs21.getString("PROJECT")%>&Pid=<%=rs21.getInt("ProjectId")%>" ><%=rs21.getString("PROJECT")%> </a></td> 	                             
					<td align="center"><%=rs21.getString("TASK_PRIORITY") %></td>     
					<td align="center"><%=rs21.getString("name") %></td>
					<td align="center"><%=rs21.getString("LABEL") %></td> 
				    <td align="center"><%=rs21.getString("TASK_STATUS") %></td>    
			        <td align="center"><%=rs21.getString("TASK_TYPE") %></td>
			        <td align="center"><%=rs21.getString("ESTIMATED_TIME") %></td>
			        <td align="center"><%=rs21.getString("DUE_DATE") %></td>   
			         
			        
			        <%-- <td align="center"><%=rs21.getString("ASSIGNED_TO") %></td> --%>
					</tr>
   	<% 	} %> 
<%
        rs21.close();		
	    con9.close();   
   	}
   	catch(Exception e)
   	{
   		e.printStackTrace();
   	}
  
 %>
 </tr>
 </tbody>
 </div>
 
 <!-- ---------------------End Type Change Priority Rate(Hourly rate($ 25.00))-------------------------------------------------------------->
 
 <!-- ---------------------Type Defects(Hourly rate($ 0.00)) -------------------------------------------------------------->
 
 <div>     
 <div> 
   <div class="tabcontent" id="Defects(Hourly rate($ 0.00))" style="margin-bottom: 20%;">
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
    Connection con10=null;
	 ResultSet rs22;
	
   	try
		   	{
   		       con10 = ConnectionManager.getConnection();
		       Statement stmt22 = con10.createStatement();
   	    
   		  String str22="select * from TASK where CONDITION='Active' and TASK_TYPE='Defects(Hourly rate($ 0.00))';";
   		
   	     rs22=stmt22.executeQuery(str22);
   	    
   	     while(rs22.next())
   		{
   %>
   			 <tr class="alt">
					<td align="center">
					<a href="#" class="btn btn-default btn-xs purple" onclick="detect(<%=rs22.getInt("TASK_ID") %>)"><i class="fa fa-trash-o"></i></a> 
					<a href="#" class="btn btn-default btn-xs purple" onclick="window.document.location.href='EditTask.jsp?Tid=<%=rs22.getInt("TASK_ID")%>'"><i class="fa fa-edit"></i></a></td></td>
					
					<td align="center"><%=rs22.getInt("TASK_ID") %></td>  
					<td align="center"><a href="display.jsp?name=<%=rs22.getString("PROJECT")%>&Pid=<%=rs22.getInt("ProjectId")%>" ><%=rs22.getString("PROJECT")%> </a></td>                        
					<td align="center"><%=rs22.getString("TASK_PRIORITY") %></td>     
					<td align="center"><%=rs22.getString("name") %></td>
					<td align="center"><%=rs22.getString("LABEL") %></td>  
				    <td align="center"><%=rs22.getString("TASK_STATUS") %></td>    
			        <td align="center"><%=rs22.getString("TASK_TYPE") %></td>
			        <td align="center"><%=rs22.getString("ESTIMATED_TIME") %></td>
			        <td align="center"><%=rs22.getString("DUE_DATE") %></td>   
			        
			         	    
			       <%--  <td align="center"><%=rs22.getString("ASSIGNED_TO") %></td> --%>
					</tr>
   	<% 	} %>
<%
        rs22.close();		
	    con10.close();   
   	}
   	catch(Exception e)
   	{
   		e.printStackTrace();
   	}
  
 %>
 </tr>
 </tbody>
 </div>
 
 <!-- ---------------------End type Defects(Hourly rate($ 0.00))-------------------------------------------------------------->
 
   <!-- ---------------------Type Changes(Hourly rate($ 15.00)) -------------------------------------------------------------->
 
 <div>     
 <div> 
   <div class="tabcontent" id="Changes(Hourly rate($ 15.00))" style="margin-bottom: 20%;">
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
    Connection con11=null;
	 ResultSet rs23;
	
   	try
		   	{
   		       con11 = ConnectionManager.getConnection();
		       Statement stmt23 = con11.createStatement();
   	    
   		  String str23="select * from TASK where CONDITION='Active' and TASK_TYPE='Changes(Hourly rate($ 15.00))';";
   		
   	     rs23=stmt23.executeQuery(str23);
   	    
   	     while(rs23.next())
   		{
   %>
   			 <tr class="alt">
					<td align="center">
					<a href="#" class="btn btn-default btn-xs purple" onclick="detect(<%=rs23.getInt("TASK_ID") %>)"><i class="fa fa-trash-o"></i></a> 
					<a href="#" class="btn btn-default btn-xs purple" onclick="window.document.location.href='EditTask.jsp?Tid=<%=rs23.getInt("TASK_ID")%>'"><i class="fa fa-edit"></i></a></td></td>
					
					<td align="center"><%=rs23.getInt("TASK_ID") %></td> 
					<td align="center"><a href="display.jsp?name=<%=rs23.getString("PROJECT")%>&Pid=<%=rs23.getInt("ProjectId")%>" ><%=rs23.getString("PROJECT")%> </a></td>                         
					<td align="center"><%=rs23.getString("TASK_PRIORITY") %></td>     
					<td align="center"><%=rs23.getString("name") %></td>
					 <td align="center"><%=rs23.getString("LABEL") %></td>
				    <td align="center"><%=rs23.getString("TASK_STATUS") %></td>    
			        <td align="center"><%=rs23.getString("TASK_TYPE") %></td>
			        <td align="center"><%=rs23.getString("ESTIMATED_TIME") %></td>
			        <td align="center"><%=rs23.getString("DUE_DATE") %></td>   
			         
			        	    
			        <%-- <td align="center"><%=rs23.getString("ASSIGNED_TO") %></td> --%>
					</tr>
   	<% 	} %>
<%
        rs23.close();		
	    con11.close();   
   	}
   	catch(Exception e)
   	{
   		e.printStackTrace();
   	}
  
 %>
 </tr>
 </tbody>
 </div>
 
 <!-- ---------------------End type Changes(Hourly rate($ 15.00))-------------------------------------------------------------->