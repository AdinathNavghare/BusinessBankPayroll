<%@page import="java.text.SimpleDateFormat"%>
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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy DTS3 </title>
<link rel="stylesheet" href="css/screen1.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<link rel="stylesheet" href="css/profilt.css">
<!-- <script src="js/sortTable.js"></script>
<script src="js/sort.js"></script> -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>
$(document).ready(function(){
	debugger;
  $("#myInput").on("keyup", function() {
    var value = $(this).val().toLowerCase();
    $("#myTable tr").filter(function() {
      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
  });
});
</script>

<script type="text/javascript">

function detect(Tid){
	

	/* alert("hinnnnnnnnnnn....."+Tid);*/
	
	 try
		{
		
		var xmlhttp=new XMLHttpRequest();
	 	var url="";
	 	var response="";
	 	url="AddTaskServlet?action=Delete&id="+Tid;
	 	
	 	let s=confirm("do you want to delete this record?");/*+ window.location.replace("ProjectViewAll.jsp")  */
		//alert(s);
		 
		 if(s)
			 {
			 	xmlhttp.onreadystatechange=function()
				{
					if(xmlhttp.readyState==4)
					{	
					response = xmlhttp.responseText;
					
					 if(response>0 )
						{
						 alert("Record Deleted Successfully!!!"+ window.location.replace("ViewTask.jsp"))
						 return false;
						}
					 else
					 {
							alert("Record Does not Deleted........Some Error Occure");	
					 }
					}
			};
			xmlhttp.open("POST", url, true);
			xmlhttp.send();
			 }
			}
			catch(e)
			{
				
			}
}

 //js for hide one table

 $(function() {

	  $(".tab").click(function(e) {
	    e.preventDefault();
	    var content = $(this).html();
	    $('#report').replaceWith('<div class="tab">' + content + '</div>');
	  });

	});
</script> 

<style>
 .tabcontent {
  display: none;
  /* padding: 6px 12px;
  border: 1px solid #ccc;
  border-top: none; */
} 
</style>
<%

Date date = new Date();
	SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
	String dt = format.format(date);
	
	SimpleDateFormat fromformat = new SimpleDateFormat("yyyy-MM-dd");
	fromformat.setLenient(false);%>
</head>
<body style="overflow:auto; "> 
<%@include file="mainHeader.jsp" %>
	 <!--  start nav-outer-repeat................................................................................................. START -->
	<%@include file="subHeader.jsp" %>
	<%-- <%@ include file="ProjectFilterSubHeader.jsp" %> --%>
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" >
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Employee</h1>
	</div>
	<!-- end page-heading -->

	<table border="0" width="100%" cellpadding="0" cellspacing="0" id="content-table">
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
			<div id="table-content" align="center">
		
			<form  action="#" method="Post">
		
	
	
	<table>
					<tr class="alt">
						<th>
	<div class="navbar navbar-default navbar-fixed-top" role="navigation" >
    <div class="container" style="height:40%;">
        
        <div class="collapse navbar-collapse"style="	float:left;">
            <ul class="nav navbar-nav nobull" style="font-size: 13px;">
                <li class="active"><a href="#" class="dropdown-toggle nobull" data-toggle="dropdown"style="background-color: #1F5FA7;">Filter</a>
                    <ul class="dropdown-menu multi-level nobull"style="background-color: #1F5FA7;">
                    			<li><a href="ViewTask.jsp" class="dropdown-toggle" data-toggle="dropdown" style="background-color: #1F5FA7;">View All Task<b class="caret"></b></a>
		                        <li><a href="#" class="dropdown-toggle nobull" data-toggle="dropdown">Task Status</a>
		                        	<ul class="dropdown-menu nobull" style="background-color: #1F5FA7;">
		                        		
		                                 <div class="tab" style="background-color:#1F5FA7;"><li><a href="#" class="tablinks" onclick="openCity(event, 'open')">Open</a></li></div>
		                                 <div class="tab" style="background-color:#1F5FA7;"><li><a href="#" class="tablinks"  onclick="openCity(event, 'done')">done</a></li></div>
				                         <div class="tab" style="background-color:#1F5FA7;"><li><a href="#" class="tablinks" onclick="openCity(event, 'Completed')">Completed</a></li></div>
		                                 <div class="tab" style="background-color:#1F5FA7;"><li><a href="#" class="tablinks" onclick="openCity(event, 'Paid')">Paid</a></li></div>
		                                 <div class="tab" style="background-color:#1F5FA7;"><li><a href="#" class="tablinks" onclick="openCity(event, 'Suspended')">Suspended</a></li></div>
		                                 <div class="tab" style="background-color:#1F5FA7;"><li><a href="#" class="tablinks"  onclick="openCity(event, 'Lost')">Lost</a></li></div>
				                        <div class="tab" style="background-color:#1F5FA7;"><li><a href="#" class="tablinks" onclick="openCity(event, 'Re-Opened')">Re-Opened</a></li></div> 
				                         <div class="tab" style="background-color:#1F5FA7;"><li><a href="#" class="tablinks" onclick="openCity(event,'Waiting-Assessmante')">WtAsesment</a></li></div>
		                             </ul>
		                        </li>
		                        <li><a href="#" class="dropdown-toggle nobull" data-toggle="dropdown">Priority</a>
		                        	<ul class="dropdown-menu" style="background-color: #1F5FA7;">
		                                <div class="tab" style="background-color:#1F5FA7;"><li><a href="#" class="tablinks" onclick="openCity(event, 'unknown')">Unknown</a></li></div>
				                       <div class="tab" style="background-color:#1F5FA7;"><li><a href="#" class="tablinks" onclick="openCity(event, 'low')">Low</a></li></div> 
				                       <div class="tab" style="background-color:#1F5FA7;"><li><a href="#" class="tablinks" onclick="openCity(event, 'medium')">Medium</a></li></div> 
				                       <div class="tab" style="background-color:#1F5FA7;"><li><a href="#" class="tablinks" onclick="openCity(event, 'high')">High</a></li></div> 
				                        <div class="tab" style="background-color:#1F5FA7;"><li><a href="#"class="tablinks" onclick="openCity(event, 'urgent')">Urgent</a></li></div> 
		                                
		                             </ul></li>
		                        
		                       <!--  <li><a href="#" class="dropdown-toggle nobull" data-toggle="dropdown">Sub Task</a>
		                        	<ul class="dropdown-menu" style="background-color: #1F5FA7;">
		                             <div class="tab" style="background-color:#1F5FA7;"><li><a href="#" class="tablinks" onclick="openCity(event, 'change')">Change</a></li></div>
	                                <div class="tab" style="background-color:#1F5FA7;"><li><a href="#" class="tablinks"  onclick="openCity(event, 'plugin')">Plugin</a></li></div> 
			                         <div class="tab" style="background-color:#1F5FA7;"><li><a href="#" class="tablinks" onclick="openCity(event, 'task')">Task</a></li></div>
	                                 <div class="tab" style="background-color:#1F5FA7;"><li><a href="#" class="tablinks" onclick="openCity(event, 'idea')">Idea</a></li></div>
	                                 <div class="tab" style="background-color:#1F5FA7;"><li><a href="#" class="tablinks" onclick="openCity(event, 'bug')">Bug</a></li></div>
	                                <div class="tab" style="background-color:#1F5FA7;"><li><a href="#" class="tablinks"  onclick="openCity(event, 'quote')">Quote</a></li></div> 
			                         <div class="tab" style="background-color:#1F5FA7;"><li><a href="#" class="tablinks" onclick="openCity(event, 'isseu')">Isseu</a></li></div>
		                             </ul>
		                        </li> -->
		                       
                   
                 <li>
                    <a href="#" class="dropdown-toggle nobull" data-toggle="dropdown"style="background-color: #1F5FA7;">Type<b class="caret"></b></a>
                    <ul class="dropdown-menu multi-level nobull" style="background-color: #1F5FA7;">
                               <div class="tab" style="background-color:#1F5FA7;"><li><a href="#" class="tablinks" onclick="openCity(event, 'Change Priority Rate(Hourly rate($ 25.00))')">Change Priority Rate(Hourly rate($ 25.00))</a></li></div> 
		                        <div class="tab" style="background-color:#1F5FA7;"><li><a href="#" class="tablinks"  onclick="openCity(event, 'Defects(Hourly rate($ 0.00))')">Defects(Hourly rate($ 0.00))</a></li></div>
		                        <div class="tab" style="background-color:#1F5FA7;"><li><a href="#" class="tablinks" onclick="openCity(event, 'Changes(Hourly rate($ 15.00))')">Changes(Hourly rate($ 15.00))</a></li></div>		                                
		                
                    </ul>
                </li>
                
               
              </ul>
                </li>      
                
               <!--  <li>
                    <a href="AddTask.jsp" class="dropdown-toggle" data-toggle="dropdown" style="background-color: #1F5FA7;">AddTask<b class="caret"></b></a>
                     
                </li> -->
                <li>                	
                </li>    
                	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;            
            </ul>
            <ul class="nav navbar-nav nobull ">
																<li class="active" >
																
																	<input id="myInput" class="nobull" type="text" placeholder="Search.." style="width: 100%;padding:5px 5px 5px 5px; text-align: center;" >
																&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															</li>
																<li class="active">
																
															<a href="AddTask.jsp" style=" float:right;right:-14%;  color: #1F5FA7;">Add New Task</a>
																
																	
															</li>
																</ul>
          
        </div><!--/.nav-collapse -->
        </div></div>
							
						</th>
					
				</table>
</form>

<br><br><br>

<br><br>
<div align="center" id="report">
<form>
<table id="customers" class="sortable" style="text-align: center;">
 <tbody style="text-align: center;">
<tr>
        <th width="90" bgcolor="1F5FA7">Action</th>
                   	<th width="40px">Task Id</th >
                   	<th width="100px">Project</th>
		        	<th width="70px">Priority</th>
		        	<th width="100px">Task Name</th>
		        	<!-- <th>Sub Task</th>  -->
		        	<th width="100px">Status</th>
		       	    <th width="130px">Type</th>  	
	       			<th width="70px">Est. Time</th>
	       			<th width="100px">Start Date</th>
		     		<th width="100px">Due Date</th>
		     		<!-- <th>AssignTo</th>  -->
</tr>
</tbody>
</table>
		<div style="overflow-y: scroll; height: 230px;">
		<table id="customers">

    <%
		  ArrayList<AddTaskBean> result = new ArrayList<AddTaskBean>();
		  AddTaskDao adao = new AddTaskDao();
			result=adao.getTaskDetail();
	  
			  for(AddTaskBean rs : result)
			  {
   %>           <tbody id="myTable">
				   <tr class="alt">
					<td width="92px">
					
				   <a href="#" class="btn btn-default btn-xs purple" onclick="detect(<%=rs.getTASK_ID() %>)"><i class="fa fa-trash-o"></i></a> 
					<a href="#" class="btn btn-default btn-xs purple" onclick="window.document.location.href='EditTask.jsp?Tid=<%=rs.getTASK_ID()%>&name=<%=rs.getPROJECT()%>&Pid=<%=rs.getPROJECT_ID()%>'"><i class="fa fa-edit"></i></a></td>
					 
					 <td width="44px"><%=rs.getTASK_ID()%></td> 
				    <td width="84px"><%=rs.getPROJECT()%></td> 
					<td width="70px"><%=rs.getPRIORITY() %></td>     
					<td width="100px"><%=rs.getNAME() %></td>
					 <td width="100px"><%=rs.getSTATUS() %></td>    
			        <td width="130px"><%=rs.getTYPE() %></td>
			        <td width="70px"><%=rs.getESTIMATED_TIME() %></td>
			        <td width="100px"><%=rs.getSTART_DATE() %></td> 
			        <td width="100px"><%=rs.getDUE_DATE() %></td>   
			       
					</tr>
	   </tbody>

												<%
													}
												%>
											</table>
										</div>
									</form>
								</div>

<!-- --------------------------start Status open---------------->

<div class="tabcontent" id="open" align="center">
<form>
   <table id="customers" class="sortable" style="text-align: center;">
<tbody style="text-align: center;">
<tr>
        <th width="90" bgcolor="1F5FA7">Action</th>
                   	<th width="40px">Task Id</th >
                   	<th width="100px">Project</th>
		        	<th width="70px">Priority</th>
		        	<th width="100px">Task Name</th>
		        	<!-- <th>Sub Task</th>  -->
		        	<th width="100px">Status</th>
		       	    <th width="130px">Type</th>  	
	       			<th width="70px">Est. Time</th>
	       			<th width="100px">Start Date</th>
		     		<th width="100px">Due Date</th>
</tr>
</tbody>
</table>
<div style="overflow-y: scroll; height: 230px;">
<table id="customers" class="sortable" style="text-align: center;">
  <%
  ArrayList<AddTaskBean> result1 = new ArrayList<AddTaskBean>();
  AddTaskDao adao1 = new AddTaskDao();
	result1=adao1.getTStatusOpen();
	  for(AddTaskBean rss : result1)
	  {
 %>
 <tbody id="myTable">
					<tr class="alt">
					<td width="92px">
					<a href="#" class="btn btn-default btn-xs purple" onclick="detect(<%=rss.getTASK_ID() %>)"><i class="fa fa-trash-o"></i></a> 
					<a href="#" class="btn btn-default btn-xs purple" onclick="window.document.location.href='EditTask.jsp?Tid=<%=rss.getTASK_ID()%>'"><i class="fa fa-edit"></i></a></td>
					 
					 <td width="43px"><%=rss.getTASK_ID()%></td> 
				    <td width="84px"><%=rss.getPROJECT()%></td> 
					<td width="70px"><%=rss.getPRIORITY() %></td>     
					<td width="100px"><%=rss.getNAME() %></td>
					 <td width="100px"><%=rss.getSTATUS() %></td>    
			        <td width="130px"><%=rss.getTYPE() %></td>
			        <td width="70px"><%=rss.getESTIMATED_TIME() %></td>
			        <td width="100px"><%=rss.getSTART_DATE() %></td> 
			        <td width="100px"><%=rss.getDUE_DATE() %></td>   
					</tr>
					</tbody>
	   <%} %>
</table>			
</div>
</form>
</div>

<!-- ------------------------End status open --------------->
<!-- --------------------status Waiting-Assessmante Status Table -------------------------------- -->

 <div align="center" class="tabcontent" id="Waiting-Assessmante">
<form>
   <table id="customers" class="sortable" style="text-align: center;">
<tbody style="text-align: center;">
<tr>
        <th width="90" bgcolor="1F5FA7">Action</th>
                   	<th width="40px">Task Id</th >
                   	<th width="100px">Project</th>
		        	<th width="70px">Priority</th>
		        	<th width="100px">Task Name</th>
		        	<!-- <th>Sub Task</th>  -->
		        	<th width="100px">Status</th>
		       	    <th width="130px">Type</th>  	
	       			<th width="70px">Est. Time</th>
	       			<th width="100px">Start Date</th>
		     		<th width="100px">Due Date</th>
</tr>
</tbody>
</table>
<div style="overflow-y: scroll; height: 230px;">
<table id="customers" class="sortable" style="text-align: center;">
	     
     <%
		  ArrayList<AddTaskBean> result2 = new ArrayList<AddTaskBean>();
		  AddTaskDao adao2 = new AddTaskDao();
			result2=adao2.getTStatusWait();
			  for(AddTaskBean rs2 : result2)
	  {
 %>
  <tbody id="myTable">
   			<tr class="alt">
					<td width="92px">
					<a href="#" class="btn btn-default btn-xs purple" onclick="detect(<%=rs2.getTASK_ID() %>)"><i class="fa fa-trash-o"></i></a> 
					<a href="#" class="btn btn-default btn-xs purple" onclick="window.document.location.href='EditTask.jsp?Tid=<%=rs2.getTASK_ID()%>'"><i class="fa fa-edit"></i></a></td>
					 
					<td width="44px"><%=rs2.getTASK_ID()%></td> 
				    <td width="84px"><%=rs2.getPROJECT()%></td> 
					<td width="70px"><%=rs2.getPRIORITY() %></td>     
					<td width="100px"><%=rs2.getNAME() %></td>
					<td width="100px"><%=rs2.getSTATUS() %></td>    
			        <td width="130px"><%=rs2.getTYPE() %></td>
			        <td width="70px"><%=rs2.getESTIMATED_TIME() %></td>
			        <td width="100px"><%=rs2.getSTART_DATE() %></td> 
			        <td width="100px"><%=rs2.getDUE_DATE() %></td>   
					</tr>
					</tbody>
   	<% 	} %>
	
</table>			
</div>
</form>
</div>
<!-- --------------------end status Waiting-Assessmante Status Table -------------------------------- -->
 <!-- ---------------------status Re-Opened -------------------------------------------------------------->
 
 
   <div class="tabcontent" id="Re-Opened" align="center">
  <form>
   <table id="customers" class="sortable" style="text-align: center;">
<tbody style="text-align: center;">
<tr>
        <th width="90" bgcolor="1F5FA7">Action</th>
                   	<th width="40px">Task Id</th >
                   	<th width="100px">Project</th>
		        	<th width="70px">Priority</th>
		        	<th width="100px">Task Name</th>
		        	<!-- <th>Sub Task</th>  -->
		        	<th width="100px">Status</th>
		       	    <th width="130px">Type</th>  	
	       			<th width="70px">Est. Time</th>
	       			<th width="100px">Start Date</th>
		     		<th width="100px">Due Date</th>
</tr>
</tbody>
</table>
<div style="overflow-y: scroll; height: 230px;">
<table id="customers" class="sortable" style="text-align: center;">
      

  <% 
			  ArrayList<AddTaskBean> result3 = new ArrayList<AddTaskBean>();
			  AddTaskDao adao3 = new AddTaskDao();
				result3=adao3.getTStatusReopen();
				  for(AddTaskBean rs3 : result3)
			{
	
   %> <tbody id="myTable">
   			 <tr class="alt">
					<td width="92px">
					<a href="#" class="btn btn-default btn-xs purple" onclick="detect(<%=rs3.getTASK_ID() %>)"><i class="fa fa-trash-o"></i></a> 
					<a href="#" class="btn btn-default btn-xs purple" onclick="window.document.location.href='EditTask.jsp?Tid=<%=rs3.getTASK_ID()%>'"><i class="fa fa-edit"></i></a></td>
					
					<td width="44px"><%=rs3.getTASK_ID() %></td>   
					<td width="84px"><%=rs3.getPROJECT()%></td> 
					<td width="70px"><%=rs3.getPRIORITY() %></td>     
					<td width="100px"><%=rs3.getNAME() %></td>
					<td width="100px"><%=rs3.getSTATUS() %></td>    
			        <td width="130px"><%=rs3.getTYPE() %></td>
			        <td width="70px"><%=rs3.getESTIMATED_TIME() %></td>
			        <td width="100px"><%=rs3.getSTART_DATE() %></td> 
			        <td width="100px"><%=rs3.getDUE_DATE() %></td>  
			        
					</tr>
  </tbody>
   	<% 	} %>
	
</table>			
</div>
</form>
</div>
 
 <!-- ---------------------End status Re-Opened -------------------------------------------------------------->
 
  <!-- ---------------------status done -------------------------------------------------------------->
 

   <div class="tabcontent" id="done" align="center">
 <form>
   <table id="customers" class="sortable" style="text-align: center;">
<tbody style="text-align: center;">
<tr>
        <th width="90" bgcolor="1F5FA7">Action</th>
                   	<th width="40px">Task Id</th >
                   	<th width="100px">Project</th>
		        	<th width="70px">Priority</th>
		        	<th width="100px">Task Name</th>
		        	<!-- <th>Sub Task</th>  -->
		        	<th width="100px">Status</th>
		       	    <th width="130px">Type</th>  	
	       			<th width="70px">Est. Time</th>
	       			<th width="100px">Start Date</th>
		     		<th width="100px">Due Date</th>
</tr>
</tbody>
</table>
<div style="overflow-y: scroll; height: 230px;">
<table id="customers" class="sortable" style="text-align: center;">
	
  <%
			  ArrayList<AddTaskBean> result4 = new ArrayList<AddTaskBean>();
			  AddTaskDao adao4 = new AddTaskDao();
				result4=adao4.getTStatusDone();
				  for(AddTaskBean rs4 : result4)
			{
   %>
    <tbody id="myTable">
   			 <tr class="alt">
					<td width="92px">
					<a href="#" class="btn btn-default btn-xs purple" onclick="detect(<%=rs4.getTASK_ID() %>)"><i class="fa fa-trash-o"></i></a> 
					<a href="#" class="btn btn-default btn-xs purple" onclick="window.document.location.href='EditTask.jsp?Tid=<%=rs4.getTASK_ID()%>'"><i class="fa fa-edit"></i></a></td>
					
					<td width="44px"><%=rs4.getTASK_ID() %></td>   
					<td width="84px"><%=rs4.getPROJECT()%></td> 
					<td width="70px"><%=rs4.getPRIORITY() %></td>     
					<td width="100px"><%=rs4.getNAME() %></td>
					<td width="100px"><%=rs4.getSTATUS() %></td>    
			        <td width="130px"><%=rs4.getTYPE() %></td>
			        <td width="70px"><%=rs4.getESTIMATED_TIME() %></td>
			        <td width="100px"><%=rs4.getSTART_DATE() %></td> 
			        <td width="100px"><%=rs4.getDUE_DATE() %></td>  
					</tr>
				</tbody>
   	<% 	} %>
	
</table>			
</div>
</form>
</div>
 
 <!-- ---------------------End status Done -------------------------------------------------------------->
 <!-- ---------------------status Completed -------------------------------------------------------------->
 
   <div class="tabcontent" id="Completed" align="center">
 <form>
   <table id="customers" class="sortable" style="text-align: center;">
<tbody style="text-align: center;">
<tr>
        <th width="90" bgcolor="1F5FA7">Action</th>
                   	<th width="40px">Task Id</th >
                   	<th width="100px">Project</th>
		        	<th width="70px">Priority</th>
		        	<th width="100px">Task Name</th>
		        	<!-- <th>Sub Task</th>  -->
		        	<th width="100px">Status</th>
		       	    <th width="130px">Type</th>  	
	       			<th width="70px">Est. Time</th>
	       			<th width="100px">Start Date</th>
		     		<th width="100px">Due Date</th>
</tr>
</tbody>
</table>
<div style="overflow-y: scroll; height: 230px;">
<table id="customers" class="sortable" style="text-align: center;">
	 
  <%
    
			  ArrayList<AddTaskBean> result5 = new ArrayList<AddTaskBean>();
			  AddTaskDao adao5 = new AddTaskDao();
				result5=adao5.getTStatusCompleted();
				  for(AddTaskBean rs5 : result5)
			
			   		{
   %>
    <tbody id="myTable">
   			 <tr class="alt">
					<td width="92px">
					<a href="#" class="btn btn-default btn-xs purple" onclick="detect(<%=rs5.getTASK_ID() %>)"><i class="fa fa-trash-o"></i></a> 
					<a href="#" class="btn btn-default btn-xs purple" onclick="window.document.location.href='EditTask.jsp?Tid=<%=rs5.getTASK_ID()%>'"><i class="fa fa-edit"></i></a></td>
					
					<td width="44px"><%=rs5.getTASK_ID() %></td>   
					<td width="84px"><%=rs5.getPROJECT()%></td> 
					<td width="70px"><%=rs5.getPRIORITY() %></td>     
					<td width="100px"><%=rs5.getNAME() %></td>
					<td width="100px"><%=rs5.getSTATUS() %></td>    
			        <td width="130px"><%=rs5.getTYPE() %></td>
			        <td width="70px"><%=rs5.getESTIMATED_TIME() %></td>
			        <td width="100px"><%=rs5.getSTART_DATE() %></td> 
			        <td width="100px"><%=rs5.getDUE_DATE() %></td>  
					</tr>
   	</tbody>
   	<% 	} %>
	
</table>			
</div>
</form>
</div>
 
 <!-- ---------------------End status Completed -------------------------------------------------------------->
 <!-- ---------------------status Paid -------------------------------------------------------------->
 
   <div class="tabcontent" id="Paid" align="center">
  <form>
   <table id="customers" class="sortable" style="text-align: center;">
<tbody style="text-align: center;">
<tr>
        <th width="90" bgcolor="1F5FA7">Action</th>
                   	<th width="40px">Task Id</th >
                   	<th width="100px">Project</th>
		        	<th width="70px">Priority</th>
		        	<th width="100px">Task Name</th>
		        	<!-- <th>Sub Task</th>  -->
		        	<th width="100px">Status</th>
		       	    <th width="130px">Type</th>  	
	       			<th width="70px">Est. Time</th>
	       			<th width="100px">Start Date</th>
		     		<th width="100px">Due Date</th>
</tr>
</tbody>
</table>
<div style="overflow-y: scroll; height: 230px;">
<table id="customers" class="sortable" style="text-align: center;">
  <%
    
			  ArrayList<AddTaskBean> result6 = new ArrayList<AddTaskBean>();
			  AddTaskDao adao6 = new AddTaskDao();
				result6=adao6.getTStatusPaid();
				  for(AddTaskBean rs6 : result6)
				  {
   %>
    <tbody id="myTable">
   			 <tr class="alt">
					<td width="92px">
					<a href="#" class="btn btn-default btn-xs purple" onclick="detect(<%=rs6.getTASK_ID() %>)"><i class="fa fa-trash-o"></i></a> 
					<a href="#" class="btn btn-default btn-xs purple" onclick="window.document.location.href='EditTask.jsp?Tid=<%=rs6.getTASK_ID()%>'"><i class="fa fa-edit"></i></a></td>
					
					<td width="44px"><%=rs6.getTASK_ID() %></td>   
					<td width="84px"><%=rs6.getPROJECT()%></td> 
					<td width="70px"><%=rs6.getPRIORITY() %></td>     
					<td width="100px"><%=rs6.getNAME() %></td>
					<td width="100px"><%=rs6.getSTATUS() %></td>    
			        <td width="130px"><%=rs6.getTYPE() %></td>
			        <td width="70px"><%=rs6.getESTIMATED_TIME() %></td>
		            <td width="100px"><%=rs6.getSTART_DATE() %></td>
			        <td width="100px"><%=rs6.getDUE_DATE() %></td>   
			         	    
			  </tr>
  </tbody>
   	<% 	} %>
	
</table>			
</div>
</form>
</div>
 
 <!-- ---------------------End status paid -------------------------------------------------------------->
 <!-- --------------------status Suspended -------------------------------------------------------------->
 

   <div class="tabcontent" id="Suspended" align="center">
    <form>
   <table id="customers" class="sortable" style="text-align: center;">
<tbody style="text-align: center;">
<tr>
        <th width="90" bgcolor="1F5FA7">Action</th>
                   	<th width="40px">Task Id</th >
                   	<th width="100px">Project</th>
		        	<th width="70px">Priority</th>
		        	<th width="100px">Task Name</th>
		        	<!-- <th>Sub Task</th>  -->
		        	<th width="100px">Status</th>
		       	    <th width="130px">Type</th>  	
	       			<th width="70px">Est. Time</th>
	       			<th width="100px">Start Date</th>
		     		<th width="100px">Due Date</th>
</tr>
</tbody>
</table>
<div style="overflow-y: scroll; height: 230px;">
<table id="customers" class="sortable" style="text-align: center;">
	
  <%
    
			  ArrayList<AddTaskBean> result7 = new ArrayList<AddTaskBean>();
			  AddTaskDao adao7 = new AddTaskDao();
				result7=adao7.getTStatusSuspended();
				  for(AddTaskBean rs7 : result7)
				  {
   %>
    <tbody id="myTable">
   			 <tr class="alt">
					<td width="92px">
					<a href="#" class="btn btn-default btn-xs purple" onclick="detect(<%=rs7.getTASK_ID() %>)"><i class="fa fa-trash-o"></i></a> 
					<a href="#" class="btn btn-default btn-xs purple" onclick="window.document.location.href='EditTask.jsp?Tid=<%=rs7.getTASK_ID()%>'"><i class="fa fa-edit"></i></a></td>
					
					<td width="44px"><%=rs7.getTASK_ID() %></td>   
					<td width="84px"><%=rs7.getPROJECT()%></td> 
					<td width="70px"><%=rs7.getPRIORITY() %></td>     
					<td width="100px"><%=rs7.getNAME() %></td>
					<td width="100px"><%=rs7.getSTATUS() %></td>    
			        <td width="130px"><%=rs7.getTYPE() %></td>
			        <td width="70px"><%=rs7.getESTIMATED_TIME() %></td>
		            <td width="100px"><%=rs7.getSTART_DATE() %></td>
			        <td width="100px"><%=rs7.getDUE_DATE() %></td>      
			        
					</tr>
   </tbody>
   	<% 	} %>
	
</table>			
</div>
</form>
</div>
 
 <!-- ---------------------End status Suspended-------------------------------------------------------------->
 
  <!-- ---------------------status Lost -------------------------------------------------------------->

   <div class="tabcontent" id="Lost" align="center">
   <form>
   <table id="customers" class="sortable" style="text-align: center;">
<tbody style="text-align: center;">
<tr>
        <th width="90" bgcolor="1F5FA7">Action</th>
                   	<th width="40px">Task Id</th >
                   	<th width="100px">Project</th>
		        	<th width="70px">Priority</th>
		        	<th width="100px">Task Name</th>
		        	<!-- <th>Sub Task</th>  -->
		        	<th width="100px">Status</th>
		       	    <th width="130px">Type</th>  	
	       			<th width="70px">Est. Time</th>
	       			<th width="100px">Start Date</th>
		     		<th width="100px">Due Date</th>
</tr>
</tbody>
</table>
<div style="overflow-y: scroll; height: 230px;">
<table id="customers" class="sortable" style="text-align: center;">
	
  <%
			  ArrayList<AddTaskBean> result8 = new ArrayList<AddTaskBean>();
			  AddTaskDao adao8 = new AddTaskDao();
				result8=adao8.getTStatusLost();
				  for(AddTaskBean rs8 : result8)
				  {
   %>
    <tbody id="myTable">
   			  <tr class="alt">
					<td width="92px">
					<a href="#" class="btn btn-default btn-xs purple" onclick="detect(<%=rs8.getTASK_ID() %>)"><i class="fa fa-trash-o"></i></a> 
					<a href="#" class="btn btn-default btn-xs purple" onclick="window.document.location.href='EditTask.jsp?Tid=<%=rs8.getTASK_ID()%>'"><i class="fa fa-edit"></i></a></td>
					
					<td width="44px"><%=rs8.getTASK_ID() %></td>   
					<td width="84px"><%=rs8.getPROJECT()%></td> 
					<td width="70px"><%=rs8.getPRIORITY() %></td>     
					<td width="100px"><%=rs8.getNAME() %></td>
					<td width="100px"><%=rs8.getSTATUS() %></td>    
			        <td width="130px"><%=rs8.getTYPE() %></td>
			        <td width="70px"><%=rs8.getESTIMATED_TIME() %></td>
		            <td width="100px"><%=rs8.getSTART_DATE() %></td>
			        <td width="100px"><%=rs8.getDUE_DATE() %></td> 
			        
			  </tr>
   	</tbody>
   	<% 	} %>
	
</table>			
</div>
</form>
</div>
 <!-- ---------------------status End Lost-------------------------------------------------------------->

<!-- ---------------------priority unknown -------------------------------------------------------------->
 

   <div class="tabcontent" id="unknown" style="margin-bottom: 20%;" align="center">
   <form>
   <table id="customers" class="sortable" style="text-align: center;">
<tbody style="text-align: center;">
<tr>
        <th width="90" bgcolor="1F5FA7">Action</th>
                   	<th width="40px">Task Id</th >
                   	<th width="100px">Project</th>
		        	<th width="70px">Priority</th>
		        	<th width="100px">Task Name</th>
		        	<!-- <th>Sub Task</th>  -->
		        	<th width="100px">Status</th>
		       	    <th width="130px">Type</th>  	
	       			<th width="70px">Est. Time</th>
	       			<th width="100px">Start Date</th>
		     		<th width="100px">Due Date</th>
</tr>
</tbody>
</table>
<div style="overflow-y: scroll; height: 230px;">
<table id="customers" class="sortable" style="text-align: center;">
  <%
				  ArrayList<AddTaskBean> result9 = new ArrayList<AddTaskBean>();
				  AddTaskDao adao9 = new AddTaskDao();
					result9=adao9.getPriorityUnknown();
					  for(AddTaskBean rs9 : result9)
					  {
   %>
    <tbody id="myTable">
   		<tr class="alt">
					<td width="92px">
					<a href="#" class="btn btn-default btn-xs purple" onclick="detect(<%=rs9.getTASK_ID() %>)"><i class="fa fa-trash-o"></i></a> 
					<a href="#" class="btn btn-default btn-xs purple" onclick="window.document.location.href='EditTask.jsp?Tid=<%=rs9.getTASK_ID()%>'"><i class="fa fa-edit"></i></a></td>
					
					<td width="100px"><%=rs9.getTASK_ID() %></td>   
					<td width="100px"><%=rs9.getPROJECT()%></td> 
					<td width="100px"><%=rs9.getPRIORITY() %></td>     
					<td width="100px"><%=rs9.getNAME() %></td>
					<td width="100px"><%=rs9.getSTATUS() %></td>    
			        <td width="130px"><%=rs9.getTYPE() %></td>
			        <td width="70px"><%=rs9.getESTIMATED_TIME() %></td>
		            <td width="100px"><%=rs9.getSTART_DATE() %></td>
			        <td width="100px"><%=rs9.getDUE_DATE() %></td> 
					</tr>
	</tbody>
   	<% 	} %>
	
</table>			
</div>
</form>
</div>
 <!-- ---------------------End priority unknown-------------------------------------------------------------->
 
  <!-- ---------------------priority Low -------------------------------------------------------------->
 
 
  <div class="tabcontent" id="low" style="margin-bottom: 20%;" align="center">
 <form>
   <table id="customers" class="sortable" style="text-align: center;">
<tbody style="text-align: center;">
<tr>
        <th width="90" bgcolor="1F5FA7">Action</th>
                   	<th width="40px">Task Id</th >
                   	<th width="100px">Project</th>
		        	<th width="70px">Priority</th>
		        	<th width="100px">Task Name</th>
		        	<!-- <th>Sub Task</th>  -->
		        	<th width="100px">Status</th>
		       	    <th width="130px">Type</th>  	
	       			<th width="70px">Est. Time</th>
	       			<th width="100px">Start Date</th>
		     		<th width="100px">Due Date</th>
</tr>
</tbody>
</table>
<div style="overflow-y: scroll; height: 230px;">
<table id="customers" class="sortable" style="text-align: center;">
  <%
    
				 ArrayList<AddTaskBean> result10 =new ArrayList<AddTaskBean>();
			    AddTaskDao ad =new AddTaskDao();
			    result10=ad.getPriorityLow();
			    for(AddTaskBean rs10 : result10)
			    {
   %>
    <tbody id="myTable">
   			 <tr class="alt">
					<td width="92px">
					<a href="#" class="btn btn-default btn-xs purple" onclick="detect(<%=rs10.getTASK_ID() %>)"><i class="fa fa-trash-o"></i></a> 
					<a href="#" class="btn btn-default btn-xs purple" onclick="window.document.location.href='EditTask.jsp?Tid=<%=rs10.getTASK_ID()%>'"><i class="fa fa-edit"></i></a></td>
					
					<td width="44px"><%=rs10.getTASK_ID() %></td>   
					<td width="84px"><%=rs10.getPROJECT()%></td> 
					<td width="70px"><%=rs10.getPRIORITY() %></td>     
					<td width="100px"><%=rs10.getNAME() %></td>
					<td width="100px"><%=rs10.getSTATUS() %></td>    
			        <td width="130px"><%=rs10.getTYPE() %></td>
			        <td width="70px"><%=rs10.getESTIMATED_TIME() %></td>
		            <td width="100px"><%=rs10.getSTART_DATE() %></td>
			        <td width="100px"><%=rs10.getDUE_DATE() %></td> 
					</tr>	 
   </tbody>
   	<% 	} %>
	
</table>			
</div>
</form>
</div>
 
 <!-- ---------------------End priority Low-------------------------------------------------------------->
 
 <!-- ---------------------priority Medium -------------------------------------------------------------->

   <div class="tabcontent" id="medium" style="margin-bottom: 20%;" align="center">
  <form>
   <table id="customers" class="sortable" style="text-align: center;">
<tbody style="text-align: center;">
<tr>
        <th width="90" bgcolor="1F5FA7">Action</th>
                   	<th width="40px">Task Id</th >
                   	<th width="100px">Project</th>
		        	<th width="70px">Priority</th>
		        	<th width="100px">Task Name</th>
		        	<!-- <th>Sub Task</th>  -->
		        	<th width="100px">Status</th>
		       	    <th width="130px">Type</th>  	
	       			<th width="70px">Est. Time</th>
	       			<th width="100px">Start Date</th>
		     		<th width="100px">Due Date</th>
</tr>
</tbody>
</table>
<div style="overflow-y: scroll; height: 230px;">
<table id="customers" class="sortable" style="text-align: center;">
	
  <%
    
			  ArrayList<AddTaskBean> result11 = new ArrayList<AddTaskBean>();
			  AddTaskDao adao11 = new AddTaskDao();
				result11=adao11.getPriorityMedium();
				  for(AddTaskBean rs11 : result11)
				  {
			 
	   %>
	    <tbody id="myTable">
   			  <tr class="alt">
					<td width="92px">
					<a href="#" class="btn btn-default btn-xs purple" onclick="detect(<%=rs11.getTASK_ID() %>)"><i class="fa fa-trash-o"></i></a> 
					<a href="#" class="btn btn-default btn-xs purple" onclick="window.document.location.href='EditTask.jsp?Tid=<%=rs11.getTASK_ID()%>'"><i class="fa fa-edit"></i></a></td>
					
					<td width="44px"><%=rs11.getTASK_ID() %></td>   
					<td width="84px"><%=rs11.getPROJECT()%></td> 
					<td width="70px"><%=rs11.getPRIORITY() %></td>     
					<td width="100px"><%=rs11.getNAME() %></td>
					<td width="100px"><%=rs11.getSTATUS() %></td>    
			        <td width="130px"><%=rs11.getTYPE() %></td>
			        <td width="70px"><%=rs11.getESTIMATED_TIME() %></td>
		            <td width="100px"><%=rs11.getSTART_DATE() %></td>
			        <td width="100px"><%=rs11.getDUE_DATE() %></td> 
					</tr>
   	</tbody>
   	<% 	} %>
	
</table>			
</div>
</form>
</div>
 
 <!-- ---------------------End priority Medum-------------------------------------------------------------->
 
  <!-- ---------------------priority High -------------------------------------------------------------->

				<div class="tabcontent" id="high" style="margin-bottom: 20%;" align="center">
					<form>
   <table id="customers" class="sortable" style="text-align: center;">
<tbody style="text-align: center;">
<tr>
        <th width="90" bgcolor="1F5FA7">Action</th>
                   	<th width="40px">Task Id</th >
                   	<th width="100px">Project</th>
		        	<th width="70px">Priority</th>
		        	<th width="100px">Task Name</th>
		        	<!-- <th>Sub Task</th>  -->
		        	<th width="100px">Status</th>
		       	    <th width="130px">Type</th>  	
	       			<th width="70px">Est. Time</th>
	       			<th width="100px">Start Date</th>
		     		<th width="100px">Due Date</th>
</tr>
</tbody>
</table>
<div style="overflow-y: scroll; height: 230px;">
<table id="customers" class="sortable" style="text-align: center;">
						<%
    
	 ArrayList<AddTaskBean> result12 = new ArrayList<AddTaskBean>();
	  AddTaskDao adao12 = new AddTaskDao();
		result12=adao12.getPriorityHigh();
		  for(AddTaskBean rs12 : result12)
		  {
   %>
    <tbody id="myTable">
						<tr class="alt">
							<td width="92px"><a href="#"
								class="btn btn-default btn-xs purple"
								onclick="detect(<%=rs12.getTASK_ID() %>)"><i
									class="fa fa-trash-o"></i></a> <a href="#"
								class="btn btn-default btn-xs purple"
								onclick="window.document.location.href='EditTask.jsp?Tid=<%=rs12.getTASK_ID()%>'"><i
									class="fa fa-edit"></i></a></td>

							<td width="44px"><%=rs12.getTASK_ID() %></td>
							<td width="84px"><%=rs12.getPROJECT()%></td>
							<td width="70px"><%=rs12.getPRIORITY() %></td>
							<td width="100px"><%=rs12.getNAME() %></td>
							<td width="100px"><%=rs12.getSTATUS() %></td>
							<td width="130px"><%=rs12.getTYPE() %></td>
							<td width="70px"><%=rs12.getESTIMATED_TIME() %></td>
							<td width="100px"><%=rs12.getSTART_DATE() %></td>
							<td width="100px"><%=rs12.getDUE_DATE() %></td>
						</tr>
						</tbody>
   	<% 	} %>
	
</table>			
</div>
</form>
</div>
<!-- ---------------------End priority High-------------------------------------------------------------->
  <!-- ---------------------priority Urgent -------------------------------------------------------------->

   <div class="tabcontent" id="urgent" style="margin-bottom: 20%;" width="100px" align="center">
  <form>
   <table id="customers" class="sortable" style="text-align: center;">
<tbody style="text-align: center;">
<tr>
        <th width="90" bgcolor="1F5FA7">Action</th>
                   	<th width="40px">Task Id</th >
                   	<th width="100px">Project</th>
		        	<th width="70px">Priority</th>
		        	<th width="100px">Task Name</th>
		        	<!-- <th>Sub Task</th>  -->
		        	<th width="100px">Status</th>
		       	    <th width="130px">Type</th>  	
	       			<th width="70px">Est. Time</th>
	       			<th width="100px">Start Date</th>
		     		<th width="100px">Due Date</th>
</tr>
</tbody>
</table>
<div style="overflow-y: scroll; height: 230px;">
<table id="customers" class="sortable" style="text-align: center;">

  <%
    
		  ArrayList<AddTaskBean> result13 = new ArrayList<AddTaskBean>();
		  AddTaskDao adao13 = new AddTaskDao();
			result13=adao13.getPriorityUrgent();
			  for(AddTaskBean rs13 : result13)
			  {
   %>
    <tbody id="myTable">
   			 <tr class="alt">
					<td width="92px">
					<a href="#" class="btn btn-default btn-xs purple" onclick="detect(<%=rs13.getTASK_ID() %>)"><i class="fa fa-trash-o"></i></a> 
					<a href="#" class="btn btn-default btn-xs purple" onclick="window.document.location.href='EditTask.jsp?Tid=<%=rs13.getTASK_ID()%>'"><i class="fa fa-edit"></i></a></td>
					
					<td width="44px"><%=rs13.getTASK_ID() %></td>   
					<td width="84px"><%=rs13.getPROJECT()%></td> 
					<td width="70px"><%=rs13.getPRIORITY() %></td>     
					<td width="100px"><%=rs13.getNAME() %></td>
					<td width="100px"><%=rs13.getSTATUS() %></td>    
			        <td width="130px"><%=rs13.getTYPE() %></td>
			        <td width="70px"><%=rs13.getESTIMATED_TIME() %></td>
		            <td width="100px"><%=rs13.getSTART_DATE() %></td>
			        <td width="100px"><%=rs13.getDUE_DATE() %></td> 
					</tr>	 
   </tbody>
   	<% 	} %>
	
</table>			
</div>
</form>
</div>
 
 <!-- ---------------------End priority Urgent-------------------------------------------------------------->

   <!-- ---------------------Type Change Priority Rate(Hourly rate($ 25.00)) -------------------------------------------------------------->
 
   <div class="tabcontent" id="Change Priority Rate(Hourly rate($ 25.00))" style="margin-bottom: 20%;" align="center">
    <form>
   <table id="customers" class="sortable" style="text-align: center;">
<tbody style="text-align: center;">
<tr>
        <th width="90" bgcolor="1F5FA7">Action</th>
                   	<th width="40px">Task Id</th >
                   	<th width="100px">Project</th>
		        	<th width="70px">Priority</th>
		        	<th width="100px">Task Name</th>
		        	<!-- <th>Sub Task</th>  -->
		        	<th width="100px">Status</th>
		       	    <th width="130px">Type</th>  	
	       			<th width="70px">Est. Time</th>
	       			<th width="100px">Start Date</th>
		     		<th width="100px">Due Date</th>
</tr>
</tbody>
</table>
<div style="overflow-y: scroll; height: 230px;">
<table id="customers" class="sortable" style="text-align: center;">
  <%
			  ArrayList<AddTaskBean> result14 = new ArrayList<AddTaskBean>();
			  AddTaskDao adao14 = new AddTaskDao();
				result14=adao14.getTypeCh25();
				  for(AddTaskBean rs14 : result14)
				  {
   %>
    <tbody id="myTable">
   			 <tr class="alt">
					<td width="92px">
					<a href="#" class="btn btn-default btn-xs purple" onclick="detect(<%=rs14.getTASK_ID() %>)"><i class="fa fa-trash-o"></i></a> 
					<a href="#" class="btn btn-default btn-xs purple" onclick="window.document.location.href='EditTask.jsp?Tid=<%=rs14.getTASK_ID()%>'"><i class="fa fa-edit"></i></a></td>
					
					<td width="44px"><%=rs14.getTASK_ID() %></td>   
					<td width="84px"><%=rs14.getPROJECT()%></td> 
					<td width="70px"><%=rs14.getPRIORITY() %></td>     
					<td width="100px"><%=rs14.getNAME() %></td>
					<td width="100px"><%=rs14.getSTATUS() %></td>    
			        <td width="130px"><%=rs14.getTYPE() %></td>
			        <td width="70px"><%=rs14.getESTIMATED_TIME() %></td>
		            <td width="100px"><%=rs14.getSTART_DATE() %></td>
			        <td width="100px"><%=rs14.getDUE_DATE() %></td> 
					</tr>
    	</tbody>
   	<% 	} %>
	
</table>			
</div>
</form>
</div>
 
 <!-- ---------------------End Type Change Priority Rate(Hourly rate($ 25.00))-------------------------------------------------------------->
 
 <!-- ---------------------Type Defects(Hourly rate($ 0.00)) -------------------------------------------------------------->
  
   <div class="tabcontent" id="Defects(Hourly rate($ 0.00))" style="margin-bottom: 20%;" align="center">
   <form>
   <table id="customers" class="sortable" style="text-align: center;">
<tbody style="text-align: center;">
<tr>
        <th width="90" bgcolor="1F5FA7">Action</th>
                   	<th width="40px">Task Id</th >
                   	<th width="100px">Project</th>
		        	<th width="70px">Priority</th>
		        	<th width="100px">Task Name</th>
		        	<!-- <th>Sub Task</th>  -->
		        	<th width="100px">Status</th>
		       	    <th width="130px">Type</th>  	
	       			<th width="70px">Est. Time</th>
	       			<th width="100px">Start Date</th>
		     		<th width="100px">Due Date</th>
</tr>
</tbody>
</table>
<div style="overflow-y: scroll; height: 230px;">
<table id="customers" class="sortable" style="text-align: center;">
  <%
			  ArrayList<AddTaskBean> result15 = new ArrayList<AddTaskBean>();
			  AddTaskDao adao15 = new AddTaskDao();
				result15=adao15.getTypeCh0();
				  for(AddTaskBean rs15 : result15)
				  {
   %>
    <tbody id="myTable">
   			 <tr class="alt">
					<td width="92px">
					<a href="#" class="btn btn-default btn-xs purple" onclick="detect(<%=rs15.getTASK_ID() %>)"><i class="fa fa-trash-o"></i></a> 
					<a href="#" class="btn btn-default btn-xs purple" onclick="window.document.location.href='EditTask.jsp?Tid=<%=rs15.getTASK_ID()%>'"><i class="fa fa-edit"></i></a></td>
					
					<td width="44px"><%=rs15.getTASK_ID() %></td>   
					<td width="84px"><%=rs15.getPROJECT()%></td> 
					<td width="70px"><%=rs15.getPRIORITY() %></td>     
					<td width="100px"><%=rs15.getNAME() %></td>
					<td width="100px"><%=rs15.getSTATUS() %></td>    
			        <td width="130px"><%=rs15.getTYPE() %></td>
			        <td width="70px"><%=rs15.getESTIMATED_TIME() %></td>
		            <td width="100px"><%=rs15.getSTART_DATE() %></td>
			        <td width="100px"><%=rs15.getDUE_DATE() %></td> 
					</tr>
   	</tbody>
   	<% 	} %>
	
</table>			
</div>
</form>
</div>
 
 <!-- ---------------------End type Defects(Hourly rate($ 0.00))-------------------------------------------------------------->
 
   <!-- ---------------------Type Changes(Hourly rate($ 15.00)) -------------------------------------------------------------->
 

   <div class="tabcontent" id="Changes(Hourly rate($ 15.00))" style="margin-bottom: 20%;" align="center">
    <form>
   <table id="customers" class="sortable" style="text-align: center;">
<tbody style="text-align: center;">
<tr>
        <th width="90" bgcolor="1F5FA7">Action</th>
                   	<th width="40px">Task Id</th >
                   	<th width="100px">Project</th>
		        	<th width="70px">Priority</th>
		        	<th width="100px">Task Name</th>
		        	<!-- <th>Sub Task</th>  -->
		        	<th width="100px">Status</th>
		       	    <th width="130px">Type</th>  	
	       			<th width="70px">Est. Time</th>
	       			<th width="100px">Start Date</th>
		     		<th width="100px">Due Date</th>
</tr>
</tbody>
</table>
<div style="overflow-y: scroll; height: 230px;">
<table id="customers" class="sortable" style="text-align: center;">

  <%
			  ArrayList<AddTaskBean> result16 = new ArrayList<AddTaskBean>();
			  AddTaskDao adao16 = new AddTaskDao();
				result16=adao16.getTypeCh15();
				  for(AddTaskBean rs16 : result16)
				  {
   %>
    <tbody id="myTable">
   			 <tr class="alt">
					<td width="92px">
					<a href="#" class="btn btn-default btn-xs purple" onclick="detect(<%=rs16.getTASK_ID() %>)"><i class="fa fa-trash-o"></i></a> 
					<a href="#" class="btn btn-default btn-xs purple" onclick="window.document.location.href='EditTask.jsp?Tid=<%=rs16.getTASK_ID()%>'"><i class="fa fa-edit"></i></a></td>
					
					<td width="44px"><%=rs16.getTASK_ID() %></td>   
					<td width="84px"><%=rs16.getPROJECT()%></td> 
					<td width="70px"><%=rs16.getPRIORITY() %></td>     
					<td width="100px"><%=rs16.getNAME() %></td>
					<td width="100px"><%=rs16.getSTATUS() %></td>    
			        <td width="130px"><%=rs16.getTYPE() %></td>
			        <td width="70px"><%=rs16.getESTIMATED_TIME() %></td>
		            <td width="100px"><%=rs16.getSTART_DATE() %></td>
			        <td width="100px"><%=rs16.getDUE_DATE() %></td> 
					</tr>
    </tbody>
   	<% 	} %>
	
</table>			
</div>
</form>
</div>
 <!-- ---------------------End type Changes(Hourly rate($ 15.00))-------------------------------------------------------------->

</div>
			<!--  end table-content  -->
<%-- <%@include file="TaskStatus.jsp"%> 
<%@include file="TaskPriority.jsp"%>
<%@include file="TaskLabel.jsp"%>
<%@include file="TaskType.jsp"%> --%>
	
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
	<div class="clear">&nbsp;</div>

</div>
<!--  end content -->
<div class="clear">&nbsp;</div>
</div>
<!--  end content-outer........................................................END -->

<div class="clear">&nbsp;</div>
    
 <script>
function openCity(evt, cityName) {
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
</html>