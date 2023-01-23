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

<style>
   
 
 .tabcontent {
  display: none;
  /* padding: 6px 12px;
  border: 1px solid #ccc;
  border-top: none; */
} 
 </style>
<!-- --------------------Table Content-------------------------------- -->
<!-- --------------------Open Status Table -------------------------------- -->
 <div>     
 <div> 
   <div class="tabcontent" id="open">
   <table id="customers">

<tr>
        <th width="70" bgcolor="1F5FA7">Action</th>
                    <th>Group</th>
		        	<th >Task Id</th >
		        	<th>Priority</th>
		        	<th>Name</th>
		        	<th>Status</th>
		       	    <th>Type</th>  	
	       			<th>Est. Time</th>
		     		<th>Due Date</th>
		     		<th>Label</th>  	
	       			<th>Project</th>  	
	       			<th>AssignTo</th> 
</tr>
<tr class="alt">
   
      

  <%
      Connection conn=null;
    
	 conn = ConnectionManager.getConnection();

	 ResultSet rs1;
	 Statement stmt1 = conn.createStatement();
	
	 
   	try
   	{
   	    
   		 String str1="select * from Task where CONDITION='Active' and TASK_STATUS='Open';";
   		
   	     rs1=stmt1.executeQuery(str1);
   	  while(rs1.next())
 		{
 %>
					<tr class="alt">
					<td align="center">
					<a href="#" class="btn btn-default btn-xs purple" onclick="detect(<%=rs1.getInt("TASK_ID") %>)"><i class="fa fa-trash-o"></i></a> 
					<a href="#" class="btn btn-default btn-xs purple" onclick="window.document.location.href='EditTask.jsp?Tid=<%=rs1.getInt("TASK_ID")%>'"><i class="fa fa-edit"></i></a></td></td>
					
					<td align="center"><%=rs1.getInt("TASK_ID") %></td>  
					<td align="center"><a href="display.jsp?name=<%=rs1.getString("PROJECT")%>&Pid=<%=rs1.getInt("ProjectId")%>" ><%=rs1.getString("PROJECT")%> </a></td> 	                         
					<td align="center"><%=rs1.getString("TASK_PRIORITY") %></td>     
					<td align="center"><%=rs1.getString("name") %></td>
					<td align="center"><%=rs1.getString("LABEL") %></td> 
				    <td align="center"><%=rs1.getString("TASK_STATUS") %></td>    
			        <td align="center"><%=rs1.getString("TASK_TYPE") %></td>
			        <td align="center"><%=rs1.getString("ESTIMATED_TIME") %></td>
			        <td align="center"><%=rs1.getString("TASK_START_DATE") %></td>
			        <td align="center"><%=rs1.getString("DUE_DATE") %></td>   
			        
			          
			        <%-- <td align="center"><%=rs1.getString("ASSIGNED_TO") %></td> --%>
					</tr>
	   	<% 	} %>
   	  
     		
<%
   		 rs1.close();
  
   	}
   	catch(Exception e)
   	{
   		e.printStackTrace();
   	}
  
 %>
</tr>
	
</table>
			
</div>
</div>
 <!-- --------------------End status Open Status Table -------------------------------- -->
 
 
  <!-- --------------------status Waiting-Assessmante Status Table -------------------------------- -->
 
 <div>     
 <div> 
   <div class="tabcontent" id="Waiting-Assessmante">
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
    
	 conn = ConnectionManager.getConnection();

	 ResultSet rs2;
	 Statement stmt2 = conn.createStatement();
	 
   	try
   	{
   	    
   		  String str2="select * from Task where CONDITION='Active' and TASK_STATUS='Waiting-Assessmante';";
   		
   	     rs2=stmt2.executeQuery(str2);
   	    
   	     while(rs2.next())
   		{
   %>
   			<tr class="alt">
					<td align="center">
					<a href="#" class="btn btn-default btn-xs purple" onclick="detect(<%=rs2.getInt("TASK_ID") %>)"><i class="fa fa-trash-o"></i></a> 
					<a href="#" class="btn btn-default btn-xs purple" onclick="window.document.location.href='EditTask.jsp?Tid=<%=rs2.getInt("TASK_ID")%>'"><i class="fa fa-edit"></i></a></td></td>
					
					<td align="center"><%=rs2.getInt("TASK_ID") %></td>
 					<td align="center"><a href="display.jsp?name=<%=rs2.getString("PROJECT")%>&Pid=<%=rs2.getInt("ProjectId")%>" ><%=rs2.getString("PROJECT")%> </a></td>                         
					<td align="center"><%=rs2.getString("TASK_PRIORITY") %></td>     
					<td align="center"><%=rs2.getString("name") %></td>
					<td align="center"><%=rs2.getString("LABEL") %></td>  
				    <td align="center"><%=rs2.getString("TASK_STATUS") %></td>    
			        <td align="center"><%=rs2.getString("TASK_TYPE") %></td>
			        <td align="center"><%=rs2.getString("ESTIMATED_TIME") %></td>
			        <td align="center"><%=rs2.getString("TASK_START_DATE") %></td>
			        <td align="center"><%=rs2.getString("DUE_DATE") %></td>   
			        
			      <%--  	    
			        <td align="center"><%=rs2.getString("ASSIGNED_TO") %></td> --%>
					</tr>
   	<% 	} %>
<%
   		 rs2.close();
     
   	}
   	catch(Exception e)
   	{
   		e.printStackTrace();
   	}
 %>
</tr>	
</table>			
</div>
 
  <!-- --------------------End status Waiting-Assessmante Status Table -------------------------------- -->
 <!-- ---------------------status Re-Opened -------------------------------------------------------------->
 
 <div>     
 <div> 
   <div class="tabcontent" id="Re-Opened">
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
    
	 conn = ConnectionManager.getConnection();

	 ResultSet rs3;
	 Statement stmt3 = conn.createStatement();
	 
   	try
   	{
   	    
   		  String str3="select * from Task where CONDITION='Active' and TASK_STATUS='Re-Opened';";
   		
   	     rs3=stmt3.executeQuery(str3);
   	    
   	     while(rs3.next())
   		{
   %>
   			 <tr class="alt">
					<td align="center">
					<a href="#" class="btn btn-default btn-xs purple" onclick="detect(<%=rs3.getInt("TASK_ID") %>)"><i class="fa fa-trash-o"></i></a> 
					<a href="#" class="btn btn-default btn-xs purple" onclick="window.document.location.href='EditTask.jsp?Tid=<%=rs3.getInt("TASK_ID")%>'"><i class="fa fa-edit"></i></a></td></td>
					
					<td align="center"><%=rs3.getInt("TASK_ID") %></td>   
					<td align="center"><a href="display.jsp?name=<%=rs3.getString("PROJECT")%>&Pid=<%=rs3.getInt("ProjectId")%>" ><%=rs3.getString("PROJECT")%> </a></td>                       
					<td align="center"><%=rs3.getString("TASK_PRIORITY") %></td>     
					<td align="center"><%=rs3.getString("name") %></td>
					 <td align="center"><%=rs3.getString("LABEL") %></td>
				    <td align="center"><%=rs3.getString("TASK_STATUS") %></td>    
			        <td align="center"><%=rs3.getString("TASK_TYPE") %></td>
			        <td align="center"><%=rs3.getString("ESTIMATED_TIME") %></td>
			        <td align="center"><%=rs3.getString("TASK_START_DATE") %></td>
			        <td align="center"><%=rs3.getString("DUE_DATE") %></td>   
			         
			        	    
			        <%-- <td align="center"><%=rs3.getString("ASSIGNED_TO") %></td> --%>
					</tr>
   	<% 	} %>
<%
   		 rs3.close();
		
	     
      
   	}
   	catch(Exception e)
   	{
   		e.printStackTrace();
   	}
  
 %>
</tr>
</table>		
</div>
 
 <!-- ---------------------End status Re-Opened -------------------------------------------------------------->
 
  <!-- ---------------------status done -------------------------------------------------------------->
 
 <div>     
 <div> 
   <div class="tabcontent" id="done">
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
		     		<!-- <th>AssignTo</th>  -->	
	           </tr>
	<tr class="alt"> 
  <%
    
	 conn = ConnectionManager.getConnection();

	 ResultSet rs4;
	 Statement stmt4 = conn.createStatement();
	 
   	try
   	{
   	    
   		  String str4="select * from Task where CONDITION='Active' and TASK_STATUS='done';";
   		
   	     rs4=stmt4.executeQuery(str4);
   	    
   	     while(rs4.next())
   		{
   %>
   			 <tr class="alt">
					<td align="center">
					<a href="#" class="btn btn-default btn-xs purple" onclick="detect(<%=rs4.getInt("TASK_ID") %>)"><i class="fa fa-trash-o"></i></a> 
					<a href="#" class="btn btn-default btn-xs purple" onclick="window.document.location.href='EditTask.jsp?Tid=<%=rs4.getInt("TASK_ID")%>'"><i class="fa fa-edit"></i></a></td></td>
					
					<td align="center"><%=rs4.getInt("TASK_ID") %></td> 
					<td align="center"><a href="display.jsp?name=<%=rs4.getString("PROJECT")%>&Pid=<%=rs4.getInt("ProjectId")%>" ><%=rs4.getString("PROJECT")%> </a></td>                    
					<td align="center"><%=rs4.getString("TASK_PRIORITY") %></td>     
					<td align="center"><%=rs4.getString("name") %></td>
					<td align="center"><%=rs4.getString("LABEL") %></td>
				    <td align="center"><%=rs4.getString("TASK_STATUS") %></td>    
			        <td align="center"><%=rs4.getString("TASK_TYPE") %></td>
			        <td align="center"><%=rs4.getString("ESTIMATED_TIME") %></td>
			        <td align="center"><%=rs4.getString("TASK_START_DATE") %></td>
			        <td align="center"><%=rs4.getString("DUE_DATE") %></td>   
			          
			         	    
			       <%--  <td align="center"><%=rs4.getString("ASSIGNED_TO") %></td> --%>
					</tr>
   	<% 	} %>
   	  
   <%
 rs4.close();	
   	}
   	catch(Exception e)
   	{
   		e.printStackTrace();
   	}
  
 %>
 </tr>
 </table>
 </div>
 
 <!-- ---------------------End status Done -------------------------------------------------------------->
 <!-- ---------------------status Completed -------------------------------------------------------------->
 
 <div>     
 <div> 
   <div class="tabcontent" id="Completed">
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
		     		<!-- <th>AssignTo</th>  -->	
	           </tr>
	<tr class="alt"> 
  <%
    
	 conn = ConnectionManager.getConnection();

	 ResultSet rs5;
	 Statement stmt5 = conn.createStatement();
	 
   	try
   	{
   	    
   		  String str5="select * from Task where CONDITION='Active' and TASK_STATUS='Completed';";
   		
   	     rs5=stmt5.executeQuery(str5);
   	    
   	     while(rs5.next())
   		{
   %>
   			 <tr class="alt">
					<td align="center">
					<a href="#" class="btn btn-default btn-xs purple" onclick="detect(<%=rs5.getInt("TASK_ID") %>)"><i class="fa fa-trash-o"></i></a> 
					<a href="#" class="btn btn-default btn-xs purple" onclick="window.document.location.href='EditTask.jsp?Tid=<%=rs5.getInt("TASK_ID")%>'"><i class="fa fa-edit"></i></a></td></td>
					
					<td align="center"><%=rs5.getInt("TASK_ID") %></td>   
					<td align="center"><a href="display.jsp?name=<%=rs5.getString("PROJECT")%>&Pid=<%=rs5.getInt("ProjectId")%>" ><%=rs5.getString("PROJECT")%> </a></td>                    
					<td align="center"><%=rs5.getString("TASK_PRIORITY") %></td>     
					<td align="center"><%=rs5.getString("name") %></td>
					<td align="center"><%=rs5.getString("LABEL") %></td>
				    <td align="center"><%=rs5.getString("TASK_STATUS") %></td>    
			        <td align="center"><%=rs5.getString("TASK_TYPE") %></td>
			        <td align="center"><%=rs5.getString("ESTIMATED_TIME") %></td>
			        <td align="center"><%=rs5.getString("TASK_START_DATE") %></td>
			        <td align="center"><%=rs5.getString("DUE_DATE") %></td>   
			          
			        	    
			       <%--  <td align="center"><%=rs5.getString("ASSIGNED_TO") %></td> --%>
					</tr>
   	<% 	} %>
<%
   		 rs5.close();
		
	     
      
   	}
   	catch(Exception e)
   	{
   		e.printStackTrace();
   	}
  
 %>
 </tr>
 </table>
 </div>
 
 <!-- ---------------------End status Completed -------------------------------------------------------------->
 <!-- ---------------------status Paid -------------------------------------------------------------->
 
 <div>     
 <div> 
   <div class="tabcontent" id="Paid">
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
		     		<!-- <th>AssignTo</th>  -->
	           </tr>
	<tr class="alt"> 
  <%
    
	 conn = ConnectionManager.getConnection();

	 ResultSet rs6;
	 Statement stmt6 = conn.createStatement();
	 
   	try
   	{
   	    
   		  String str6="select * from Task where CONDITION='Active' and TASK_STATUS='Paid';";
   		
   	     rs6=stmt6.executeQuery(str6);
   	    
   	     while(rs6.next())
   		{
   %>
   			 <tr class="alt">
					<td align="center">
					<a href="#" class="btn btn-default btn-xs purple" onclick="detect(<%=rs6.getInt("TASK_ID") %>)"><i class="fa fa-trash-o"></i></a> 
					<a href="#" class="btn btn-default btn-xs purple" onclick="window.document.location.href='EditTask.jsp?Tid=<%=rs6.getInt("TASK_ID")%>'"><i class="fa fa-edit"></i></a></td></td>
					
					<td align="center"><%=rs6.getInt("TASK_ID") %></td>  
					<td align="center"><a href="display.jsp?name=<%=rs6.getString("PROJECT")%>&Pid=<%=rs6.getInt("ProjectId")%>" ><%=rs6.getString("PROJECT")%> </a></td>                  
					<td align="center"><%=rs6.getString("TASK_PRIORITY") %></td>     
					<td align="center"><%=rs6.getString("name") %></td>
					 <td align="center"><%=rs6.getString("LABEL") %></td>
				    <td align="center"><%=rs6.getString("TASK_STATUS") %></td>    
			        <td align="center"><%=rs6.getString("TASK_TYPE") %></td>
			        <td align="center"><%=rs6.getString("ESTIMATED_TIME") %></td>
			        <td align="center"><%=rs6.getString("TASK_START_DATE") %></td>
			        <td align="center"><%=rs6.getString("DUE_DATE") %></td>   
			         
			         	    
			        <%-- <td align="center"><%=rs6.getString("ASSIGNED_TO") %></td> --%>
					</tr>
   	<% 	} %>
<%
   		 rs6.close();
	
   	}
   	catch(Exception e)
   	{
   		e.printStackTrace();
   	}
  
 %>
 </tr>
 </table>
 </div>
 
 <!-- ---------------------End status paid -------------------------------------------------------------->
 <!-- --------------------status Suspended -------------------------------------------------------------->
 
 <div>     
 <div> 
   <div class="tabcontent" id="Suspended">
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
		     		<!-- <th>AssignTo</th>  -->	
	           </tr>
	<tr class="alt">
  <%
    
	 conn = ConnectionManager.getConnection();

	 ResultSet rs7;
	 Statement stmt7 = conn.createStatement();
	 
   	try
   	{
   	    
   		  String str7="select * from Task where CONDITION='Active' and TASK_STATUS='Suspended';";
   		
   	     rs7=stmt7.executeQuery(str7);
   	    
   	     while(rs7.next())
   		{
   %>
   			 <tr class="alt">
					<td align="center">
					<a href="#" class="btn btn-default btn-xs purple" onclick="detect(<%=rs7.getInt("TASK_ID") %>)"><i class="fa fa-trash-o"></i></a> 
					<a href="#" class="btn btn-default btn-xs purple" onclick="window.document.location.href='EditTask.jsp?Tid=<%=rs7.getInt("TASK_ID")%>'"><i class="fa fa-edit"></i></a></td></td>
					
					<td align="center"><%=rs7.getInt("TASK_ID") %></td>
		 			<td align="center"><a href="display.jsp?name=<%=rs7.getString("PROJECT")%>&Pid=<%=rs7.getInt("ProjectId")%>" ><%=rs7.getString("PROJECT")%> </a></td>                            
					<td align="center"><%=rs7.getString("TASK_PRIORITY") %></td>     
					<td align="center"><%=rs7.getString("name") %></td>
					<td align="center"><%=rs7.getString("LABEL") %></td>
				    <td align="center"><%=rs7.getString("TASK_STATUS") %></td>    
			        <td align="center"><%=rs7.getString("TASK_TYPE") %></td>
			        <td align="center"><%=rs7.getString("ESTIMATED_TIME") %></td>
			        <td align="center"><%=rs7.getString("TASK_START_DATE") %></td>
			        <td align="center"><%=rs7.getString("DUE_DATE") %></td>   
			          
			        
			        <%-- <td align="center"><%=rs7.getString("ASSIGNED_TO") %></td> --%>
					</tr>
   	<% 	} %>
   	  
     		</tbody>  
   		</table>
 	</div>
</div> 
<%
   		 rs7.close();
		
	     
      
   	}
   	catch(Exception e)
   	{
   		e.printStackTrace();
   	}
  
 %>
 </div>
 
 <!-- ---------------------End status Suspended-------------------------------------------------------------->
 
  <!-- ---------------------status Lost -------------------------------------------------------------->
 
     
 <div> 
   <div class="tabcontent" id="Lost">
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
		     		<!-- <th>AssignTo</th>  --> 	
	           </tr>
	<tr class="alt">
  <%
    
	 conn = ConnectionManager.getConnection();

	 ResultSet rs8;
	 Statement stmt8 = conn.createStatement();
	 
   	try
   	{
   	    
   		  String str8="select * from Task where CONDITION='Active' and TASK_STATUS='Lost';";
   		
   	     rs8=stmt8.executeQuery(str8);
   	    
   	     while(rs8.next())
   		{
   %>
   			  <tr class="alt">
					<td align="center">
					<a href="#" class="btn btn-default btn-xs purple" onclick="detect(<%=rs8.getInt("TASK_ID") %>)"><i class="fa fa-trash-o"></i></a> 
					<a href="#" class="btn btn-default btn-xs purple" onclick="window.document.location.href='EditTask.jsp?Tid=<%=rs8.getInt("TASK_ID")%>'"><i class="fa fa-edit"></i></a></td></td>
					
					<td align="center"><%=rs8.getInt("TASK_ID") %></td>  
					<td align="center"><a href="display.jsp?name=<%=rs8.getString("PROJECT")%>&Pid=<%=rs8.getInt("ProjectId")%>" ><%=rs8.getString("PROJECT")%> </a></td>                        
					<td align="center"><%=rs8.getString("TASK_PRIORITY") %></td>     
					<td align="center"><%=rs8.getString("name") %></td>
				    <td align="center"><%=rs8.getString("LABEL") %></td>  
			   	    <td align="center"><%=rs8.getString("TASK_STATUS") %></td>    
			        <td align="center"><%=rs8.getString("TASK_TYPE") %></td>
			        <td align="center"><%=rs8.getString("ESTIMATED_TIME") %></td>
			        <td align="center"><%=rs8.getString("TASK_START_DATE") %></td>
			        <td align="center"><%=rs8.getString("DUE_DATE") %></td>   
			       	    
			        <%-- <td align="center"><%=rs8.getString("ASSIGNED_TO") %></td> --%>
					</tr>
   	<% 	} %> 		
<%
   		 rs8.close();
		
	     conn.close();
      
   	}
   	catch(Exception e)
   	{
   		e.printStackTrace();
   	}
  
 %>
 </tr>
 </table>
 </div>

 
 <!-- ---------------------status End Lost-------------------------------------------------------------->