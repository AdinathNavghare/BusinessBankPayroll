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


<!-- ---------------------label change -------------------------------------------------------------->
 
 <div>     
 <div> 
   <div class="tabcontent" id="change">
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
    Connection con2=null;
	
	 ResultSet rs14;
	
	 
   	try
		   	{
   		       con2 = ConnectionManager.getConnection();
		       Statement stmt14 = con2.createStatement();
   	    
   		  String str14="select * from TASK where CONDITION='Active' and LABEL='change';";
   		
   	     rs14=stmt14.executeQuery(str14);
   	    
   	     while(rs14.next())
   		{
   %>
   			  <tr class="alt">
					<td align="center">
					<a href="#" class="btn btn-default btn-xs purple" onclick="detect(<%=rs14.getInt("TASK_ID") %>)"><i class="fa fa-trash-o"></i></a> 
					<a href="#" class="btn btn-default btn-xs purple" onclick="window.document.location.href='EditTask.jsp?Tid=<%=rs14.getInt("TASK_ID")%>'"><i class="fa fa-edit"></i></a></td></td>
					
					<td align="center"><%=rs14.getInt("TASK_ID") %></td> 
					<td align="center"><a href="display.jsp?name=<%=rs14.getString("PROJECT")%>&Pid=<%=rs14.getInt("ProjectId")%>" ><%=rs14.getString("PROJECT")%> </a></td>                         
					<td align="center"><%=rs14.getString("TASK_PRIORITY") %></td>     
					<td align="center"><%=rs14.getString("name") %></td>
					<td align="center"><%=rs14.getString("LABEL") %></td>
				    <td align="center"><%=rs14.getString("TASK_STATUS") %></td>    
			        <td align="center"><%=rs14.getString("TASK_TYPE") %></td>
			        <td align="center"><%=rs14.getString("ESTIMATED_TIME") %></td>
			        <td align="center"><%=rs14.getString("TASK_START_DATE") %></td>
			        <td align="center"><%=rs14.getString("DUE_DATE") %></td>   
			          
			         	    
			        <%-- <td align="center"><%=rs14.getString("ASSIGNED_TO") %></td> --%>
					</tr>
   	<% 	} %>
<%
        rs14.close();		
	    con2.close();     
   	}
   	catch(Exception e)
   	{
   		e.printStackTrace();
   	}
  
 %>
 </tr>
 </table>
 </div>
 
 <!-- ---------------------End Label change-------------------------------------------------------------->
 
 <!-- ---------------------label plugin -------------------------------------------------------------->
 
 <div>     
 <div> 
   <div class="tabcontent" id="plugin">
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
    Connection con3=null;
	
	 ResultSet rs15;
	
	 
   	try
		   	{
   		       con3 = ConnectionManager.getConnection();
		       Statement stmt15 = con3.createStatement();
   	    
   		  String str15="select * from TASK where CONDITION='Active' and LABEL='plugin';";
   		
   	     rs15=stmt15.executeQuery(str15);
   	    
   	     while(rs15.next())
   		{
   %>
   			 <tr class="alt">
					<td align="center">
					<a href="#" class="btn btn-default btn-xs purple" onclick="detect(<%=rs15.getInt("TASK_ID") %>)"><i class="fa fa-trash-o"></i></a> 
					<a href="#" class="btn btn-default btn-xs purple" onclick="window.document.location.href='EditTask.jsp?Tid=<%=rs15.getInt("TASK_ID")%>'"><i class="fa fa-edit"></i></a></td></td>
					
					<td align="center"><%=rs15.getInt("TASK_ID") %></td> 
					<td align="center"><a href="display.jsp?name=<%=rs15.getString("PROJECT")%>&Pid=<%=rs15.getInt("ProjectId")%>" ><%=rs15.getString("PROJECT")%> </a></td>                         
					<td align="center"><%=rs15.getString("TASK_PRIORITY") %></td>     
					<td align="center"><%=rs15.getString("name") %></td>
					 <td align="center"><%=rs15.getString("LABEL") %></td>
				    <td align="center"><%=rs15.getString("TASK_STATUS") %></td>    
			        <td align="center"><%=rs15.getString("TASK_TYPE") %></td>
			        <td align="center"><%=rs15.getString("ESTIMATED_TIME") %></td>
			        <td align="center"><%=rs15.getString("TASK_START_DATE") %></td>
			        <td align="center"><%=rs15.getString("DUE_DATE") %></td>   
			         
			         	    
			        <%-- <td align="center"><%=rs15.getString("ASSIGNED_TO") %></td> --%>
					</tr>		 
   	<% 	} %> 
<%
        rs15.close();
		
	    con3.close();
      
   	}
   	catch(Exception e)
   	{
   		e.printStackTrace();
   	}
  
 %>
 </tr>
 </table>
 </div>
 
 <!-- ---------------------End Label plugin-------------------------------------------------------------->
  <!-- ---------------------label task -------------------------------------------------------------->
 
 <div>     
 <div> 
   <div class="tabcontent" id="task">
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
    Connection con4=null;
	
	 ResultSet rs16;
	
	 
   	try
		   	{
   		       con4 = ConnectionManager.getConnection();
		       Statement stmt16 = con4.createStatement();
   	    
   		  String str16="select * from TASK where CONDITION='Active' and LABEL='task';";
   		
   	     rs16=stmt16.executeQuery(str16);
   	    
   	     while(rs16.next())
   		{
   %>
   			 <tr class="alt">
					<td align="center">
					<a href="#" class="btn btn-default btn-xs purple" onclick="detect(<%=rs16.getInt("TASK_ID") %>)"><i class="fa fa-trash-o"></i></a> 
					<a href="#" class="btn btn-default btn-xs purple" onclick="window.document.location.href='EditTask.jsp?Tid=<%=rs16.getInt("TASK_ID")%>'"><i class="fa fa-edit"></i></a></td></td>
					
					<td align="center"><%=rs16.getInt("TASK_ID") %></td> 
					<td align="center"><a href="display.jsp?name=<%=rs16.getString("PROJECT")%>&Pid=<%=rs16.getInt("ProjectId")%>" ><%=rs16.getString("PROJECT")%> </a></td>                         
					<td align="center"><%=rs16.getString("TASK_PRIORITY") %></td>     
					<td align="center"><%=rs16.getString("name") %></td>
					<td align="center"><%=rs16.getString("LABEL") %></td> 
				    <td align="center"><%=rs16.getString("TASK_STATUS") %></td>    
			        <td align="center"><%=rs16.getString("TASK_TYPE") %></td>
			        <td align="center"><%=rs16.getString("ESTIMATED_TIME") %></td>
			        <td align="center"><%=rs16.getString("TASK_START_DATE") %></td>
			        <td align="center"><%=rs16.getString("DUE_DATE") %></td>   
			         
			         	    
			        <%-- <td align="center"><%=rs16.getString("ASSIGNED_TO") %></td> --%>
					</tr>
   	<% 	} %>
<%
        rs16.close();
		
	    con4.close();
      
   	}
   	catch(Exception e)
   	{
   		e.printStackTrace();
   	}
  
 %>
 </tr>
 </table>
 </div>
 
 <!-- ---------------------End Label task-------------------------------------------------------------->
 
  <!-- ---------------------label idea -------------------------------------------------------------->
 
 <div>     
 <div> 
   <div class="tabcontent" id="idea">
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
    Connection con5=null;
	
	 ResultSet rs17;
	
	 
   	try
		   	{
   		       con5 = ConnectionManager.getConnection();
		       Statement stmt17 = con5.createStatement();
   	    
   		  String str17="select * from TASK where CONDITION='Active' and LABEL='idea';";
   		
   	     rs17=stmt17.executeQuery(str17);
   	    
   	     while(rs17.next())
   		{
   %>
   			<tr class="alt">
					<td align="center">
					<a href="#" class="btn btn-default btn-xs purple" onclick="detect(<%=rs17.getInt("TASK_ID") %>)"><i class="fa fa-trash-o"></i></a> 
					<a href="#" class="btn btn-default btn-xs purple" onclick="window.document.location.href='EditTask.jsp?Tid=<%=rs17.getInt("TASK_ID")%>'"><i class="fa fa-edit"></i></a></td></td>
					
					<td align="center"><%=rs17.getInt("TASK_ID") %></td>
					<td align="center"><a href="display.jsp?name=<%=rs17.getString("PROJECT")%>&Pid=<%=rs17.getInt("ProjectId")%>" ><%=rs17.getString("PROJECT")%> </a></td>                          
					<td align="center"><%=rs17.getString("TASK_PRIORITY") %></td>     
					<td align="center"><%=rs17.getString("name") %></td>
					<td align="center"><%=rs17.getString("LABEL") %></td> 
				    <td align="center"><%=rs17.getString("TASK_STATUS") %></td>    
			        <td align="center"><%=rs17.getString("TASK_TYPE") %></td>
			        <td align="center"><%=rs17.getString("ESTIMATED_TIME") %></td>
			        <td align="center"><%=rs17.getString("TASK_START_DATE") %></td>
			        <td align="center"><%=rs17.getString("DUE_DATE") %></td>   
			         
			         	    
			        <%-- <td align="center"><%=rs17.getString("ASSIGNED_TO") %></td> --%>
					</tr>		 
   	<% 	} %>
<%
        rs17.close();
		
	    con5.close();
      
   	}
   	catch(Exception e)
   	{
   		e.printStackTrace();
   	}
  
 %>
 </tr>
 </table>
 </div>
 
 <!-- ---------------------End Label idea-------------------------------------------------------------->
 <!-- ---------------------label bug -------------------------------------------------------------->
 
 <div>     
 <div> 
   <div class="tabcontent" id="bug">
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
    Connection con6=null;
	
	 ResultSet rs18;
	
	 
   	try
		   	{
   		       con6 = ConnectionManager.getConnection();
		       Statement stmt18 = con6.createStatement();
   	    
   		  String str18="select * from TASK where CONDITION='Active' and LABEL='bug';";
   		
   	     rs18=stmt18.executeQuery(str18);
   	    
   	     while(rs18.next())
   		{
   %>
   			 <tr class="alt">
					<td align="center">
					<a href="#" class="btn btn-default btn-xs purple" onclick="detect(<%=rs18.getInt("TASK_ID") %>)"><i class="fa fa-trash-o"></i></a> 
					<a href="#" class="btn btn-default btn-xs purple" onclick="window.document.location.href='EditTask.jsp?Tid=<%=rs18.getInt("TASK_ID")%>'"><i class="fa fa-edit"></i></a></td></td>
					
					<td align="center"><%=rs18.getInt("TASK_ID") %></td> 
					<td align="center"><a href="display.jsp?name=<%=rs18.getString("PROJECT")%>&Pid=<%=rs18.getInt("ProjectId")%>" ><%=rs18.getString("PROJECT")%> </a></td>                         
					<td align="center"><%=rs18.getString("TASK_PRIORITY") %></td>     
					<td align="center"><%=rs18.getString("name") %></td>
					<td align="center"><%=rs18.getString("LABEL") %></td> 
				    <td align="center"><%=rs18.getString("TASK_STATUS") %></td>    
			        <td align="center"><%=rs18.getString("TASK_TYPE") %></td>
			        <td align="center"><%=rs18.getString("ESTIMATED_TIME") %></td>
			        <td align="center"><%=rs18.getString("TASK_START_DATE") %></td>
			        <td align="center"><%=rs18.getString("DUE_DATE") %></td>   
			         
			         	    
			        <%-- <td align="center"><%=rs18.getString("ASSIGNED_TO") %></td> --%>
					</tr>
   	<% 	} %>
<%
        rs18.close();		
	    con6.close();    
   	}
   	catch(Exception e)
   	{
   		e.printStackTrace();
   	}
  
 %>
 </tr>
</table>
 </div>
 
 <!-- ---------------------End Label bug-------------------------------------------------------------->
 
   <!-- ---------------------label quote -------------------------------------------------------------->
 
 <div>     
 <div> 
   <div class="tabcontent" id="quote">
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
    Connection con7=null;
	 ResultSet rs19;
	
	 
   	try
		   	{
   		       con7 = ConnectionManager.getConnection();
		       Statement stmt19 = con7.createStatement();
   	    
   		  String str19="select * from TASK where CONDITION='Active' and LABEL='quote';";
   		
   	     rs19=stmt19.executeQuery(str19);
   	    
   	     while(rs19.next())
   		{
   %>
   			 <tr class="alt">
					<td align="center">
					<a href="#" class="btn btn-default btn-xs purple" onclick="detect(<%=rs19.getInt("TASK_ID") %>)"><i class="fa fa-trash-o"></i></a> 
					<a href="#" class="btn btn-default btn-xs purple" onclick="window.document.location.href='EditTask.jsp?Tid=<%=rs19.getInt("TASK_ID")%>'"><i class="fa fa-edit"></i></a></td></td>
					
					<td align="center"><%=rs19.getInt("TASK_ID") %></td> 
					<td align="center"><a href="display.jsp?name=<%=rs19.getString("PROJECT")%>&Pid=<%=rs19.getInt("ProjectId")%>" ><%=rs19.getString("PROJECT")%> </a></td> 	                             
					<td align="center"><%=rs19.getString("TASK_PRIORITY") %></td>     
					<td align="center"><%=rs19.getString("name") %></td>
					<td align="center"><%=rs19.getString("LABEL") %></td>
				    <td align="center"><%=rs19.getString("TASK_STATUS") %></td>    
			        <td align="center"><%=rs19.getString("TASK_TYPE") %></td>
			        <td align="center"><%=rs19.getString("ESTIMATED_TIME") %></td>
			        <td align="center"><%=rs19.getString("TASK_START_DATE") %></td>
			        <td align="center"><%=rs19.getString("DUE_DATE") %></td>   
			          
			        
			        <%-- <td align="center"><%=rs19.getString("ASSIGNED_TO") %></td> --%>
					</tr>		 
   	<% 	} %> 
<%
        rs19.close();		
	    con7.close();
   	}
   	catch(Exception e)
   	{
   		e.printStackTrace();
   	}
  
 %>
 </tr>
 </table>
 </div>
 
 <!-- ---------------------End Label quote-------------------------------------------------------------->
 
   <!-- ---------------------label isseu -------------------------------------------------------------->
 
 <div>     
 <div> 
   <div class="tabcontent" id="isseu">
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
    Connection con8=null;
	 ResultSet rs20;
	
   	try
		   	{
   		       con8 = ConnectionManager.getConnection();
		       Statement stmt20 = con8.createStatement();
   	    
   		  String str20="select * from TASK where CONDITION='Active' and LABEL='isseu';";
   		
   	     rs20=stmt20.executeQuery(str20);
   	    
   	     while(rs20.next())
   		{
   %>
   			 <tr class="alt">
					<td align="center">
					<a href="#" class="btn btn-default btn-xs purple" onclick="detect(<%=rs20.getInt("TASK_ID") %>)"><i class="fa fa-trash-o"></i></a> 
					<a href="#" class="btn btn-default btn-xs purple" onclick="window.document.location.href='EditTask.jsp?Tid=<%=rs20.getInt("TASK_ID")%>'"><i class="fa fa-edit"></i></a></td></td>
					
					<td align="center"><%=rs20.getInt("TASK_ID") %></td> 
					<td align="center"><a href="display.jsp?name=<%=rs20.getString("PROJECT")%>&Pid=<%=rs20.getInt("ProjectId")%>" ><%=rs20.getString("PROJECT")%> </a></td> 	                             
					<td align="center"><%=rs20.getString("TASK_PRIORITY") %></td>     
					<td align="center"><%=rs20.getString("name") %></td>
					<td align="center"><%=rs20.getString("LABEL") %></td>
				    <td align="center"><%=rs20.getString("TASK_STATUS") %></td>    
			        <td align="center"><%=rs20.getString("TASK_TYPE") %></td>
			        <td align="center"><%=rs20.getString("ESTIMATED_TIME") %></td>
			        <td align="center"><%=rs20.getString("TASK_START_DATE") %></td>
			        <td align="center"><%=rs20.getString("DUE_DATE") %></td>   
			          
			        
			        <%-- <td align="center"><%=rs19.getString("ASSIGNED_TO") %></td> --%>
					</tr>
   	<% 	} %>
   	  
     		</tbody>  
   		</table>
 	</div>
</div> 
<%
        rs20.close();		
	    con8.close();   
   	}
   	catch(Exception e)
   	{
   		e.printStackTrace();
   	}
  
 %>
 </div>
 
 <!-- ---------------------End Label isseu-------------------------------------------------------------->