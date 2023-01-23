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
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<script src="js/sort.js"></script>
<%--<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>all project report</title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<style type="text/css">
 nav {    
  display: block;
  text-align:left;
  
}
nav:hover
{
	 background-color:;
	 border-style:  #004de6;

}
 nav ul {
  margin: 0;
  padding:0;
  list-style-type: none;
  font-size: 10px;
}
.nobull
{
list-style-type: none;
}
.nav a {
  display:block; 
  
 /*  border-style: ridge; */
  
  color:white; 
 /*  text-decoration: none; */
  padding: 0.8em 1.8em;
  text-transform: uppercase;
  font-size:100%;
  letter-spacing: 2px;
 	/* font-family: monospace; */
 font-weight:bolder;
  position: relative;
}
.nav{  
  vertical-align: top; 
  display: inline-block;
  box-shadow: 
    1px -1px -1px 1px #000, 
    -1px 1px -1px 1px #fff, 
    0 0 6px 3px #fff;
  border-radius:6px;
}
.nav li {
  position: relative;
}
.nav > li { 
  float: left; 
  border-bottom: 4px #aaa solid; 
  margin-right: 1px; 
}
.nav > li:first-child { 
  border-radius: 4px 0 0 4px;
} 
.nav > li:first-child > a { 
  border-radius: 4px 0 0 0;
}
.nav > li:last-child { 
  border-radius: 0 0 4px 0; 
  margin-right: 0;
} 
.nav > li:last-child > a { 
  border-radius: 0 4px 0 0;
}
.nav li li a { 
  margin-top: 1px;
  
  .nav li a:first-child:nth-last-child(2):before { 
  content: ""; 
  position: absolute; 
  height: 0; 
  width: 0; 
  border: 5px solid transparent; 
  top: 50% ;
  right:5px;  
 }
}


/* submenu positioning*/
.nav ul {
  position: absolute;
  white-space: nowrap;
  /* border-bottom: 5px solid  orange; */
  z-index: 1;
  left: -99999em;
}
.nav > li:hover > ul {
  left: auto;
  margin-top: 5px;
  min-width: 100%;
}


.nav > li li:hover > ul { 
  left: 100%;
  margin-left: 1px;
  top: -1px;
  
}
.nav > li > a:first-child:nth-last-child(2):before { 
  border-top-color: #aaa; 
}
.nav > li:hover > a:first-child:nth-last-child(2):before {
  border: 5px solid transparent; 
  border-bottom-color: orange; 
  margin-top:-5px
}
.nav li li > a:first-child:nth-last-child(2):before {  
  border-left-color: #aaa; 
  margin-top: -5px
}
.nav li li:hover > a:first-child:nth-last-child(2):before {
  border: 5px solid transparent; 
  border-right-color: orange;
  right: 10px; 
} 
</style>
<script type="text/javascript">

function detect(id){
	alert("hi....."+id);
	var Pid = document.getElementById("id").value;
	 alert(Pid);
	 try
		{
		
		var xmlhttp=new XMLHttpRequest();
	 	var url="";
	 	var response2="";
	 	url="AddProjectServlet?action=Delete&Pid="+Pid;
	 	
	 	
	 	xmlhttp.onreadystatechange=function()
		{
			if(xmlhttp.readyState==4)
			{
			
			response2 = xmlhttp.responseText;
			 if(response2>0 )
				{
				 alert("Record Deleted Succeessfully........");
				
					return false;
				}
			}
			
			};
			xmlhttp.open("POST", url, true);
			xmlhttp.send();
			}
			catch(e)
			{
				
			}
}
$(function() {

	  $(".tab").click(function(e) {
	    e.preventDefault();
	    var content = $(this).html();
	    $('#report').replaceWith('<div class="tab">' + content + '</div>');
	  });

	});
</script>

<!-- <script type="text/javascript">
function report({'#open'})
{
var open=document.getElementById("open").value;
}
</script> -->
</head>
<body > 
<%@include file="mainHeader.jsp"%>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="ProjectFilterSubHeader.jsp"%>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" >
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		
	</div>
	<!-- end page-heading -->

	<table border="0" width="100%" cellpadding="0" cellspacing="0" id="content-table" style="margin-bottom: 20%;">
	<tr>
		<th rowspan="3" class="sized"><img src="images/shared/side_shadowleft.jpg" width="20" height="300" alt="" /></th>
		<th class="topleft"></th>
		<td id="tbl-border-top">&nbsp;</td>
		<th class="topright"></th>
		<th rowspan="3" class="sized"><img src="images/shared/side_shadowright.jpg" width="20" height="300" alt="" /></th>
	</tr>
	<tr>
		<td id="tbl-border-left"></td>
		<td>
		<!--  start content-table-inner ...................................................................... START -->
		<div id="content-table-inner">
		
			<!--  start table-content  -->
			<div id="table-content">
			<center>
			<!-- ******************Report filter****************** -->
			<form action="#">
				<table>
					<tr class="alt">
						<th>
							<div class="navbar navbar-default navbar-fixed-top" role="navigation" ">
    <div class="container" style="height:40%;">
        
        <div class="collapse navbar-collapse"style="margin-left:20%;margin-right: 20%;	margin-top: 05;	float:left;">
            <ul class="nav navbar-nav nobull" style="font-size: 13px;">
                <li class="active"><a href="#" class="dropdown-toggle nobull" data-toggle="dropdown" style="background-color: #1F5FA7;">Project</a>
                    <ul class="dropdown-menu multi-level nobull" style="background-color:  #1F5FA7;">
                    			<li ><a href="AddNewProject.jsp" class="tablinks" >Add New Project</a></li>
                    			<li><a href="#" class="tablinks" onclick="openCity(event, 'viewAll')">View All Project</a></li>
		                        <li class="nobull"><a href="#" class="dropdown-toggle nobull" data-toggle="dropdown">Project_status</a>
		                        	<ul class="dropdown-menu nobull" style="background-color:  #1F5FA7;text-transform: uppercase;color: white;">
		                        		
		                                <li><a href="#" class="tablinks" onclick="openCity(event, 'open')">Open</a></li>
		                                <li><a href="#" class="tablinks"  onclick="openCity(event, 'closed')">Closed</a></li>
		                                <li><a href="#" class="tablinks" onclick="openCity(event, 'onHold')">On Hold</a></li>
		                                <li><a href="#" class="tablinks" onclick="openCity(event, 'cancelled')">Cancelled</a></li>
		                             </ul>
		                        </li>
		                        <li><a href="#" class="dropdown-toggle nobull" data-toggle="dropdown">Project_priority</a>
		                        	<ul class="dropdown-menu" style="background-color:  #1F5FA7;text-transform: uppercase;color: white;">
		                                <li><a href="#" class="tablinks" onclick="openCity(event, 'unknown')">Unknown</a></li>
		                                <li><a href="#" class="tablinks" onclick="openCity(event, 'low')">Low</a></li>
		                                <li><a href="#" class="tablinks" onclick="openCity(event, 'medum')">Medum</a></li>
		                                <li><a href="#" class="tablinks" onclick="openCity(event, 'high')">High</a></li>
		                                <li><a href="#"class="tablinks" onclick="openCity(event, 'urgent')">Urgent</a></li>
		                                
		                             </ul>
		                        
		                        <li><a href="#" class="dropdown-toggle nobull" data-toggle="dropdown">Project_type</a>
		                        	<ul class="dropdown-menu" style="background-color: #1F5FA7;text-transform: uppercase;color:white;">
		                               <li><a href="#" class="tablinks"  onclick="openCity(event, 'it')">IT</a></li>
		                               <li><a href="#" class="tablinks" onclick="openCity(event, 'support')">Support</a></li>
		                               <li><a href="#" class="tablinks" onclick="openCity(event, 'internal')">Internal</a></li>
		                               <li><a href="#" class="tablinks" onclick="openCity(event, 'newSite')">New Site</a></li>
		                             </ul>
		                        </li>
		                       
                    </ul>
                </li>
                
                 
                <li>
                    <a href="#" class="dropdown-toggle nobull" data-toggle="dropdown"style="background-color: #1F5FA7;">Group<b class="caret"></b></a>
                    <ul class="dropdown-menu multi-level nobull" style="background-color:  #1F5FA7;">
                        <li><a href="#" class="tablinks" onclick="openCity(event, 'admin')">Admin</a></li>
                        <li><a href="#"  class="tablinks" onclick="openCity(event, 'developer')">Developer</a></li>
                        <li><a href="#"  class="tablinks" onclick="openCity(event, 'designer')">Designer</a></li>
                        <li><a href="#" class="tablinks" onclick="openCity(event, 'manager')">Manager</a></li>
                        <li class="dropdown-submenu"> <a href="#" class="dropdown-toggle" data-toggle="dropdown">Dropdown</a></li>
                    </ul>
                </li>
                <li>
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"style="background-color: #1F5FA7;">Task<b class="caret"></b></a>
                    <ul class="dropdown-menu nobull" style="background-color:  #1F5FA7;">
                    <li><a href="AddTask.jsp" >Add Task</a></li>
                        <li><a href="#" >Name</a></li>
                        <li><a href="#">Status</a></li>
                         <li><a href="#"> Priority</a></li>
                        <li><a href="#"> Types</a></li>
                        <li><a href="#"> Date</a></li>
                       
                        
                    </ul>
                </li>
            </ul>

            <input id="myInput" type="text" placeholder="Search.."style="float: right; margin-top: 30px;">
        </div><!--/.nav-collapse -->
        </div></div>
							
						</th>
					
					
					</tr>
				</table>
			
			
			</form>
			</center>


<br><br>
			<div align="center" id="report">
			<table id="customers">
			<thead>
			<tr class="alt">
			<th width="100" bgcolor="1F5FA7">Action</th>
			<th width="200">Name</th>
			<th width="200">Display on Dashboard</th>
			<th width="200">Display on Menu</th>		
			</tr></thead>
			<tbody>
			 
			
					  <%
					  
					  Connection con= ConnectionManager.getConnection();
					   	try
					   	{
					   		String sql;
					   		PreparedStatement ps;
					   		ResultSet rs;
					   		/*   if(true)
					   		{
					   			 sql="select ProjectId,PType,Pstatus,ProjectName,PPriority from AddProject";
						   		 ps=con.prepareStatement(sql);
						   		 rs=ps.executeQuery();
					   		}
					   		else
					   		{   */
					   		 sql="select ProjectId,PType,Pstatus,ProjectName,PPriority from AddProject";
					   		 ps=con.prepareStatement(sql);
					   		 rs=ps.executeQuery();
					   		 /*  }  */ 
					   		while(rs.next())
					   		{
					   %>
								<tr class="alt">
										<td align="center">
										<a class="btn btn-default btn-xs purple" onclick="detect(<%=rs.getInt("ProjectId") %>)"><i class="fa fa-trash-o"></i></a> 
										<a href="#" class="btn btn-default btn-xs purple" onclick="window.document.location.href='EditProject.jsp?Pid=<%=rs.getInt("ProjectId") %>'"><i class="fa fa-edit"></i></a></td></td>
										<td align="center"><a href="#"><%=rs.getInt("ProjectId") %></a></td>
										                         
										
										<td align="center">xmanager</td>
										<td align="center"><span title="1547287092"></span>Jan 12, 2019 04:58</td>
								</tr>
				
					 <% 	} %>
			         <%
						   		rs.close();
							    ps.close();
							    con.close();
						   	}
						   	catch(Exception e)
						   	{
						   		e.printStackTrace();
						   	}  
			         %>
			</tbody>
								
							<!-- <td >
							        <ul class="nav" align="left">
							               <li ><a href="#">Order By &#9662;</a>	
							            		<ul>
							               			 <li ><a  href="#">Date Added</a></li>
							                		 <li ><a  href="#">Name</a></li>
							                		<li ><a  href="#"><strong>Status</strong></a></li>
							                		<li ><a  href="#">Type</a></li>
							            		</ul>
							      			 </li>
							             </ul>
					        </td>         -->
			
			
			
			</table>
						<center><input type="button" name="addproject" value="Add Report" class="myButton" onclick="window.document.location.href='AddProjectReport.jsp'"></center>
			</div>

 			</div>
			<div class="clear"></div>
		 
		</div>
		<!--  end content-table-inner ............................................END  -->
		</td>
		<td id="tbl-border-right"></td>
	</tr>
	<tr>
		<th class="sized bottomleft"></th>
		<td id="tbl-border-bottom">&nbsp;</td>
		<th class="sized bottomright"></th>
	</tr>
	</table>
	<div class="clear"></div>

</div>
<!--  end content -->
<div class="clear"></div>
</div>
<!--  end content-outer........................................................END -->

<div class="clear">&nbsp;</div>
<script>
function openTab(evt, cityName) {
  var i, tabcontent, tablinks;
  tabcontent = document.getElementsByClassName("tabcontent");
  for (i = 0; i < tabcontent.length; i++) {
    tabcontent[i].style.display = "none";
  }
  tablinks = document.getElementsByClassName("tablinks");
  for (i = 0; i < tablinks.length; i++) {
    tablinks[i].className = tablinks[i].className.replace(" active", "");
  }
  document.getElementById(cityName).style.display = "block";
  evt.currentTarget.className += " active";
/*   
  if(tablinks.{'#open'})
	  {
	    var open=1;
	  } */
}
</script>    
       
</body>
</html> --%>



<div id="ProjectReport" class="tabcontent">
	<form>
			<table id="customers">
			<thead>
			<tr><h2>All Project Report</h2></tr>
			<tr >
			<th >Action</th>
			<th >Name</th>
			<th >Project Name</th>
			<th >Status</th>		
			</tr></thead>
			<tbody id="myTable">
			 
			
					  <%
					  
					  Connection con41= ConnectionManager.getConnection();
					   	try
					   	{
					   		String sql41;
					   		PreparedStatement ps41;
					   		ResultSet rs41;
					   		/*   if(true)
					   		{
					   			 sql="select ProjectId,PType,Pstatus,ProjectName,PPriority from AddProject";
						   		 ps=con.prepareStatement(sql);
						   		 rs=ps.executeQuery();
					   		}
					   		else
					   		{   */
					   		 sql41="select empName,projectName,projectId,statuss,prioritys,typess from project_report ";
					   		 ps41=con41.prepareStatement(sql41);
					   		 rs41=ps41.executeQuery();
					   		 /*  }  */ 
					   		while(rs41.next())
					   		{
					   %>
								<tr class="alt">
										<td >
										<a class="btn btn-default btn-xs purple" onclick="detect(<%=rs41.getInt("ProjectId") %>)"><i class="fa fa-trash-o"></i></a> 
										<a href="#" class="btn btn-default btn-xs purple" onclick="window.document.location.href='EditProject.jsp?Pid=<%=rs41.getInt("ProjectId") %>'"><i class="fa fa-edit"></i></a>
										</td>
										
										
										<td ><a href="#"><%=rs41.getString("empName") %></a></td>
					    	<td><a href="display.jsp?Pid=<%=rs41.getInt("projectId") %>&name=<%=rs41.getString("ProjectName") %>"><%=rs41.getString("ProjectName") %></a></td>
										<td ><a href="#"><%=rs41.getString("statuss") %></a></td>
								</tr>
				
					 <% 	} %>
			         <%
						   		rs41.close();
							    ps41.close();
							   
						   	}
						   	catch(Exception e)
						   	{
						   		e.printStackTrace();
						   	}  
			         %>
			</tbody>
								
							<!-- <td >
							        <ul class="nav" align="left">
							               <li ><a href="#">Order By &#9662;</a>	
							            		<ul>
							               			 <li ><a  href="#">Date Added</a></li>
							                		 <li ><a  href="#">Name</a></li>
							                		<li ><a  href="#"><strong>Status</strong></a></li>
							                		<li ><a  href="#">Type</a></li>
							            		</ul>
							      			 </li>
							             </ul>
					        </td>         -->
			
			
			
			</table>
						<center><input type="button" name="addproject" value="Add Report" class="myButton" onclick="window.document.location.href='AddProjectReport.jsp'"></center>
						</form>
			</div>
			
			
			<div id="TaskReport" class="tabcontent">
			<form>
			<table id="customers">
			<thead>
			<tr><h2>All Task Report</h2></tr>
			<tr >
			<th >Action</th>
			<th >Name</th>
			<th >Display on Dashboard</th>
			<th >Display on Menu</th>		
			</tr></thead>
			<tbody id="myTable">
			 
			
					  <%
					  

					 
					   	try
					   	{
					   		String sql41;
					   		PreparedStatement ps41;
					   		ResultSet rs41;
					   		/*   if(true)
					   		{
					   			 sql="select ProjectId,PType,Pstatus,ProjectName,PPriority from AddProject";
						   		 ps=con.prepareStatement(sql);
						   		 rs=ps.executeQuery();
					   		}
					   		else
					   		{   */
					   		 sql41="select ProjectId,PType,Pstatus,ProjectName,PPriority from AddProject";
					   		 ps41=con41.prepareStatement(sql41);
					   		 rs41=ps41.executeQuery();
					   		 /*  }  */ 
					   		while(rs41.next())
					   		{
					   %>
								<tr class="alt">
										<td align="center">
										<a class="btn btn-default btn-xs purple" onclick="detect(<%=rs41.getInt("ProjectId") %>)"><i class="fa fa-trash-o"></i></a> 
										<a href="#" class="btn btn-default btn-xs purple" onclick="window.document.location.href='EditTask.jsp?tid=<%=rs41.getInt("ProjectId") %>'"><i class="fa fa-edit"></i></a></td>
										<td align="center"><a href="#"><%=rs41.getInt("ProjectId") %></a></td>
										                         
										
										<td align="center">xmanager</td>
										<td align="center"><span title="1547287092"></span>Jan 12, 2019 04:58</td>
								</tr>
				
					 <% 	} %>
			         <%
						   		rs41.close();
							    ps41.close();
							    
						   	}
						   	catch(Exception e)
						   	{
						   		e.printStackTrace();
						   	}  
			         %>
			</tbody>
								
							<!-- <td >
							        <ul class="nav" align="left">
							               <li ><a href="#">Order By &#9662;</a>	
							            		<ul>
							               			 <li ><a  href="#">Date Added</a></li>
							                		 <li ><a  href="#">Name</a></li>
							                		<li ><a  href="#"><strong>Status</strong></a></li>
							                		<li ><a  href="#">Type</a></li>
							            		</ul>
							      			 </li>
							             </ul>
					        </td>         -->
			
			
			
			</table>
						<input type="button" name="addproject" value="Add Report" class="myButton" onclick="window.document.location.href='AddTaskReport.jsp'">
						</form>
			</div>
			
			
			<!--************All Task div**********************  -->
			
<div  id="ViewAllTask" class="tabcontent">
<form action="">
<table id="customers">
<div><input type="button" name="addproject" value="Add Task" class="myButton"  onclick="window.document.location.href='AddTask.jsp'"></div>
<thead>
<tr>
        <th >Action</th>
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
</thead>
<tbody id="myTable">


		  <%
     
	 
		  String sql41="select * from Task";
	 ResultSet rs41;
	
	
	 PreparedStatement stmt41 = con41.prepareStatement(sql41);
   	try
   	{
   	     
   		
   	     rs41=stmt41.executeQuery();
   	    
   	   while(rs41.next())
   		{
   		   //System.out.println(rs.getInt("TASK_ID"));
   		   // Tid=rs.getInt("TASK_ID");
   %>
				    <tr class="alt">
					<td >
					<a href="#" class="btn btn-default btn-xs purple" onclick="detect(<%=rs41.getInt("TASK_ID") %>)"><i class="fa fa-trash-o"></i></a> 
					<a href="#" class="btn btn-default btn-xs purple" onclick="window.document.location.href='EditTask.jsp?Tid=<%=rs41.getInt("TASK_ID")%>'"><i class="fa fa-edit"></i></a></td></td>
					
					<td ><%=rs41.getInt("TASK_ID") %></td> 
					 <td ><%=rs41.getString("PROJECT") %></td> 	                         
					<td ><%=rs41.getString("TASK_PRIORITY") %></td>     
					<td ><%=rs41.getString("name") %></td>
					<td ><%=rs41.getString("LABEL") %></td>  
			        <td ><%=rs41.getString("TASK_STATUS") %></td>    
			        <td ><%=rs41.getString("TASK_TYPE") %></td>
			        <td ><%=rs41.getString("ESTIMATED_TIME") %></td>
			        <td ><%=rs41.getString("TASK_START_DATE") %></td> 
			        <td ><%=rs41.getString("DUE_DATE") %></td>   
			        <%-- <td align="center"><%=rs.getString("ASSIGNED_TO") %></td> --%>
					</tr>
	   	<% 	} %>
   	  </tbody>
     		 
<%
   		 rs41.close();
		stmt41.close();
   	}
   	catch(Exception e)
   	{
   		e.printStackTrace();
   	}
  
 %>
</tr>
	
</table>
			</form>
</div>
			
<% con41.close();%>	
			<!--***********End Task div  -->