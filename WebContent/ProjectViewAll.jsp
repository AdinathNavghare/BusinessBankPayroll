<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="payroll.DAO.ConnectionManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="javax.mail.SendFailedException"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="payroll.DAO.*"%>
<%@page import="payroll.Model.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy DTS3</title>
<link rel="stylesheet" href="css/screen1.css" type="text/css" media="screen" title="default" />
<!-- <link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" /> -->

<link rel="stylesheet"href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<link rel="stylesheet" href="css/profilt.css">

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<!-- <style type="text/css">
 nav {    
  display: block;
  text-align:left;
  
}
nav:hover
{
	 background-color: #fff;
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
  background: #004de6;
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
</style> -->
<style type="text/css">

/* My Model */

.modal {
  display: none; /* Hidden by default */
 
  z-index: 3;
  padding-top:50px; /* Location of the box */
  left: 30;
  top: 0;
 margin-top:300px;
   border: 1px groove #888;
 width: 100%; /*Full width */
  height:200%; /* Full height */ */
   /* Enable scroll if needed */
  background-color:white; /* Fallback color */
  /* background-color: rgba(255,255,255,0.5); /* Black w/ opacity */ */
}

/* The Modal (background) */
.modal {
  display: none; /* Hidden by default */
  position: absolute; /* Stay in place */
  z-index: 1; /* Sit on top */
  padding-top: 10px; /* Location of the box */
  
  width:70%; /* Full width */
  height: 45%; /* Full height */
  overflow: hidden; /* Enable scroll if needed */
  background-color: rgb(0,0,0); /* Fallback color */ 
  background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
}

/* Modal Content */
/* .modal {
  display: none; 
  position: fixed;
  z-index: 1; 
  padding-top: 1px;
  
  width:60%; 
  height: 70%; 
  overflow: auto;
  background-color: rgb(0,0,0);
  background-color: rgba(0,0,0,0.4);
} */
.modal-content {
  background-color: #1F5FA7;
  /* margin: auto; */
  padding: 10px;
 /*  border: 1px solid white; */
  width: 100%;
  height:100px;
}
/* Table css  */
#customers1 {
	/* font-family: "Trebuchet MS", Arial, Helvetica, sans-serif; */
	border-collapse: collapse;
	border-style: ridge;
	height: 50px;
	width: 100%;
}

#customers1 td, #customers1 th {
	border: 2px solid #ddd;
	padding: 8px;
}

#customers1 tr:nth-child(even) {
	background-color: #f2f2f2;
}

#customers tr:hover {
	background-color: #ddd;
}

#customers td {
	font-size: 14px;
}

#customers th {
	font-family: serif;
	background-color: #1F5FA7;
	color: white;
	font-size: medium;
}
</style>
<script type="text/javascript">

function detect(id){
	 try
	 {
		var xmlhttp=new XMLHttpRequest();
	 	var url="";
	 	var response="";
	 	url="AddProjectServlet?action=Delete1&Pid="+id;
	 	let s=confirm("do you want to delete this record?");/*+ window.location.replace("ProjectViewAll.jsp")  */
		 if(s)
			 {
			 	xmlhttp.onreadystatechange=function()
				{
					if(xmlhttp.readyState==4)
					{	
					response = xmlhttp.responseText;
					
					 if(response>0 )
						{
						 alert("Record Deleted Successfully!!!"+ window.location.replace("ProjectViewAll.jsp"))
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
$(function() {

	  $(".tab").click(function(e) {
	    e.preventDefault();
	    var content = $(this).html();
	    $('#report').replaceWith('<div class="tab">' + content + '</div>');
	  });

	});
	

function getDetailes(ProjectId)
{
	debugger;
	var html='';
	 var xmlhttp=new XMLHttpRequest();
	  var response3="";
	  //alert(ProjectId);
	      xmlhttp.onreadystatechange=function()
	        {
	    	 
	    	  
	    	  if (xmlhttp.readyState==4)
	          {
	        	
	        	response3 = xmlhttp.responseText.split(",");
	        	var n=response3.length;
	        	
	        	
	    		  //alert("response3 :"+response3.proName);
	    			 //alert(response3);
	    	      html += '<tr class="alt" style="background-color: #1F0FA7; color:white; width="50%" position: relative;"><th colspan="10">Project Information</th></tr>'
	    		  html+='<tr><th colspan="10"  ></<th></tr>';
	    		  html +='<tr><th>CODE</th><th>NAME</th><th>STATUS</th><th>TYPE</th><th>PRIORITY</th><th>LIVEURL</th><th>TESTURL</th><th>START DATE</th><th>END DATE</th></tr>';
	    		  html +=' <tr >';
	    		  html +=' <td style="background-color: white;  width="50%"  position: relative;">'+response3[0]+' </td>';
	    		  html +=' <td style="background-color: white;  width="50%"  position: relative;">'+response3[1]+' </td>';
	    		  html +=' <td style="background-color: white;  width="50%"  position: relative;">'+response3[2]+' </td>';
	    		  html +=' <td style="background-color: white;  width="50%"  position: relative;">'+response3[3]+' </td>';
	    		  html +=' <td style="background-color: white;  width="50%"  position: relative;">'+response3[4]+' </td>';
	    		  html +=' <td style="background-color: white;  width="50%"  position: relative;">'+response3[5]+' </td>';
	    		  html +=' <td style="background-color: white;  width="50%"  position: relative;">'+response3[6]+' </td>';
	    		  html +=' <td style="background-color: white;  width="50%"  position: relative;">'+response3[7]+' </td>';
	    		  html +=' <td style="background-color: white;  width="50%"  position: relative;">'+response3[8]+' </td>';
	    		  /* html +=' <td style="background-color: white;  width="50%"  position: relative;">'+response3[9]+' </td>'; */
	    		  //html +=' </tr >';
	    		  html+='<tr><th colspan="10"  ></th></tr>';
	    		  html += '<tr class="alt" style="background-color: #1F5FA7; color:white; width="50%" position: relative;"><th colspan="10">Task Information</th></tr>'
		    
	    		  html +='<tr><th colspan="">CODE</th><th colspan="">NAME</th><th>TYPE</th><th>STATUS</th><th>PRIORITY</th><th>START DATE</th><th>END DATE</th>';
	    		  html +=' <tr>';
	    		  for(var i=10;i<n-1;i++)
	    		  {
	    			  
	    			  if(response3[i]=='*')
	    				  {
	    				  		html +=' </tr>';
	    				  }
	    			  else
	    				  {
	    				  
	    				  
	    				  html +='&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<td style="background-color: white;  width:80%;  position: relative;">'+response3[i]+' </td>';
	    				  }
	    		 
	    		  }

	    		  html +=' </tr>';
	    		  
	    		  
	    		  /* document.getElementsByClassName("data").innerHTML = html;	 */
	    		  $('.form_data').html(html);
	    		  
	          }
	    	  
	    	 
	        }
	      xmlhttp.open("POST","DeleteProjectServlet?action=viewAllProject&projectId="+ProjectId,true);
	      xmlhttp.send(); 
	     
	
	 document.getElementById("myModal").style.display = "block";
	  
	
}
	
$(document).mouseup(function (e){

	var container = $("#myModal");

	if (!container.is(e.target) && container.has(e.target).length === 0){

		container.fadeOut();
		
	}
}); 
</script>

<script type="text/javascript">
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
<style>
.tabcontent {
	display: none;
	/* padding: 6px 12px;
  border: 1px solid #ccc;
  border-top: none; */
}
</style>

</head>
<body style="overflow: auto;">
	<%@include file="mainHeader.jsp"%>
	<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp"%>
 
	<%-- <%@ include file="ProjectFilterSubHeader.jsp"%> --%>
	<!-- start content-outer ........................................................................................................................START -->
	<div id="content-outer">
		<!-- start content -->
		<div id="content">

			<!--  start page-heading -->
			<div id="page-heading">
				<h1>ViewAll</h1>
			</div>
			<!-- end page-heading -->

			<table border="0" width="100%" cellpadding="0" cellspacing="0"
				id="content-table" style="margin-bottom: 20%;">
				<!-- <tr>
					<th rowspan="3" class="sized"><img
						src="images/shared/side_shadowleft.jpg" width="20" height="300"
						alt="" /></th>
					<th class="topleft"></th>
					<td id="tbl-border-top">&nbsp;</td>
					<th class="topright"></th>
					<th rowspan="3" class="sized"><img
						src="images/shared/side_shadowright.jpg" width="20" height="300"
						alt="" /></th>
				</tr> -->
				<tr>
					<td id="tbl-border-left"></td>
					<td>
						<!--  start content-table-inner ...................................................................... START -->
						<div id="content-table-inner">

							<!--  start table-content  -->
							<div id="table-content">
								<center>
									<form >
										<table>
											<tr>
											
												<th>
													<div class="navbar navbar-default navbar-fixed-top" align="left"
														role="navigation">
														<div class="container" style="height: 40%;">
																<ul class="nav navbar-nav nobull  " style="font-size: 13px; " align="left">
																
																	<li class="active">
																		<a href="#" class="dropdown-toggle nobull" data-toggle="dropdown" style="background-color: #1F5FA7;">Filter</a>
																		 <ul class="dropdown-menu multi-level nobull" style="background-color: #1F5FA7;">
																			<li class="nobull">
																			  <a href="ProjectViewAll.jsp" class="dropdown-toggle nobull" data-toggle="dropdown">All Projects</a>
																			    <li class="nobull">
																			    <a href="#" class="dropdown-toggle nobull" data-toggle="dropdown">Project_status</a>
																				 <ul class="dropdown-menu nobull" style="background-color: #1F5FA7;">

																					<div class="tab" style="background-color: #1F5FA7;">
																						<li class="open"><a href="#" class="tablinks" onclick="openTab(event, 'open')">Open</a></li>
																					</div>
																					<div class="tab" style="background-color: #1F5FA7;">
																						<li><a href="#" class="tablinks" onclick="openTab(event, 'closed')">Closed</a></li>
																					</div>
																					<div class="tab" style="background-color: #1F5FA7;">
																						<li><a href="#" class="tablinks" onclick="openTab(event, 'onHold')">On Hold</a></li>
																					</div>
																					<div class="tab" style="background-color: #1F5FA7;">
																						<li><a href="#" class="tablinks" onclick="openTab(event, 'cancelled')">Cancelled</a></li>
																					</div>
																				</ul>
																			</li>
																			<li class="nobull">
																			  <a href="#" class="dropdown-toggle nobull" data-toggle="dropdown">Project_Types</a>
																				<ul class="dropdown-menu" style="background-color: #1F5FA7;">
																					<div class="tab" style="background-color: #1F5FA7;">
																						<li><a href="#" class="tablinks" onclick="openTab(event, 'it')">It</a></li>
																					</div>
																					<div class="tab" style="background-color: #1F5FA7;">
																						<li><a href="#" class="tablinks" onclick="openTab(event, 'support')">Support</a></li>
																					</div>
																					<div class="tab" style="background-color: #1F5FA7;">
																						<li><a href="#" class="tablinks" onclick="openTab(event, 'newSite')">New Site</a></li>
																					</div>
																					<div class="tab" style="background-color: #1F5FA7;">
																						<li><a href="#" class="tablinks" onclick="openTab(event, 'internal')">Internal</a></li>
																					</div>
																				</ul>
																			</li>
																		</ul>
																	</li>
																	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																</ul>
																<ul class="nav navbar-nav nobull ">
																<li class="active" >
																
																	<input id="myInput" class="nobull" type="text" placeholder="Search...." style="width: 100%;padding:5px 5px 5px 5px; " >
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															</li>
															
																	<li class="active">
																<!-- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
															<a href="AddNewProject.jsp" style=" float:right;right:-14%;  color: #1F5FA7;">Add New Project</a>
																<!-- 	<input type="button" name="addproject" value="Add New Project" style=" padding:5px 8px 5px 8px; color:white;background-color: #1F5FA7;height:250%;" onclick="window.document.location.href='AddNewProject.jsp'"> -->
																	
															</li>
																</ul>
															
														</div>
													</div>
												</th>
																						
											</tr>
  </table>
 </form>
</center>							
<br><br>
 <div id="report" align="center">
	<form>
		<table id="customers" class="sortable" style="text-align: center;">
			<tbody style="text-align: center;">
				<tr>
					<th width="100px">Action</th>
					<th width="100px">Project ID</th>
					<th width="100px">Project Name</th>
					<th width="100px">Type</th>
					<th width="100px">Project Priority</th>
					<th width="100px">Status</th>
					<th width="100px">Start Date</th>
					<th width="101px">End Date</th>
					<th width="120px">Information</th>
				</tr>
			 </tbody>
		</table>
		
		<div style="overflow-y: scroll; height: 230px;">
		<table id="customers">
			<%
				ArrayList<AddProjectBean> proList1 = new ArrayList<AddProjectBean>();
				AddProjectDAO apd = new AddProjectDAO();
				proList1 = apd.viewProject();
				for (AddProjectBean apb : proList1) 
				{
			%>
			<tbody id="myTable">
			  <tr>
				<td width="100px">&nbsp; &nbsp; &nbsp; &nbsp;
				 <a class="btn btn-default btn-xs purple" id="tb1" onclick="detect(<%=apb.getPROID()%>)">
				 	<i class="fa fa-trash-o"></i>
				 </a> &nbsp; &nbsp; &nbsp;
				  <a href="EditProject.jsp?Pid=<%=apb.getPROID()%>" class="btn btn-default btn-xs purple">
				  	<i class="fa fa-edit"></i>
				  </a>
				</td>
				<td width="100px"><%=apb.getPROID()%></td>
				<td width="100px"><%=apb.getPRONAME()%></td>
				<td width="100px"><%=apb.getPROTYPE()%></td>
				<td width="100px"><%=apb.getPROSTATUS()%></td>
				<td width="100px"><%=apb.getPROPRIORITY()%></td>
				<td width="100px"><%=apb.getREVIEWDATE()%></td>
				<td width="100px"><%=apb.getDESIGNDATE()%></td>
				<td width="100px"><input type="button" onclick="getDetailes(<%=apb.getPROID()%>)" name="addproject" value="view" class="myButton"></td>
			</tr>
		 </tbody>
			<%
				}
			%>
			</table>
		</div>
	</form>
</div>
<!--***********************************************Open*****************************-->
<div align="center" id="open" class="tabcontent">
  <form>
	<table id="customers" class="sortable" style="text-align: center;">
		<!-- <tbody style="text-align: center;"> -->
			<tr>
				<th width="100px">Action</th>
				<th width="100px">Project ID</th>
				<th width="100px">Project Name</th>
				<th width="100px">Type</th>
				<th width="100px">Project Priority</th>
				<th width="100px">Status</th>
				<th width="100px">Start Date</th>
				<th width="101px">End Date</th>
				<th width="120px">Information</th>
			</tr>
		<!--  </tbody> -->
	</table>
	<div style="overflow-y: scroll; height: 230px;">
	  <table id="customers">
		<%
			ArrayList<AddProjectBean> proList11 = new ArrayList<AddProjectBean>();
			AddProjectDAO apd1 = new AddProjectDAO();
			proList1 = apd1.open();
			for (AddProjectBean apb : proList1) 
			{
		%>
		<tbody id="myTable">
		   <tr>
			 <td width="100px">&nbsp; &nbsp; &nbsp; &nbsp;
			 <a class="btn btn-default btn-xs purple" id="tb1" onclick="detect(<%=apb.getPROID()%>)">
			 <i class="fa fa-trash-o"></i></a> &nbsp; &nbsp; &nbsp;
			 <a href="EditProject.jsp?Pid=<%=apb.getPROID()%>" class="btn btn-default btn-xs purple">
			 <i class="fa fa-edit"></i></a>
			 </td>
			 <td width="100px"><%=apb.getPROID()%></td>
			 <td width="100px"><%=apb.getPRONAME()%></td>
			 <td width="100px"><%=apb.getPROTYPE()%></td>
			 <td width="100px"><%=apb.getPROSTATUS()%></td>
			 <td width="100px"><%=apb.getPROPRIORITY()%></td>
			 <td width="100px"><%=apb.getREVIEWDATE()%></td>
			 <td width="100px"><%=apb.getDESIGNDATE()%></td>
			 <td width="100px">
			 	<input type="button"onclick="getDetailes(<%=apb.getPROID()%>)"name="addproject" value="view" class="myButton nobull">
			 </td>
		   </tr>
		</tbody>
		<%
			}
		%>
	</table>
  </div>
 </form>
</div>
<!--End Open****************************  -->

<!--***********************************************closed*****************************-->
<div align="center" id="closed" class="tabcontent">
  <form>
	<table id="customers" class="sortable" style="text-align: center;">
		<tbody style="text-align: center;">
			<tr>
				<th width="100px">Action</th>
				<th width="100px">Project ID</th>
				<th width="100px">Project Name</th>
				<th width="100px">Type</th>
				<th width="100px">Project Priority</th>
				<th width="100px">Status</th>
				<th width="100px">Start Date</th>
				<th width="101px">End Date</th>
				<th width="120px">Information</th>
			</tr>
		 </tbody>
	</table>
	<div style="overflow-y: scroll; height: 230px;">
		<table id="customers">
			<%
				ArrayList<AddProjectBean> proList2 = new ArrayList<AddProjectBean>();
				AddProjectDAO apd2 = new AddProjectDAO();
				proList2 = apd2.close();
				for (AddProjectBean apb2 : proList2)
				{
			%>
			<tbody id="myTable">
				<tr>
					<td width="100px">&nbsp; &nbsp; &nbsp; &nbsp;
					  <a class="btn btn-default btn-xs purple" id="tb1"onclick="detect(<%=apb2.getPROID()%>)">
					  <i class="fa fa-trash-o"></i></a> &nbsp; &nbsp; &nbsp;
					  <a href="EditProject.jsp?Pid=<%=apb2.getPROID()%>" class="btn btn-default btn-xs purple">
					  <i class="fa fa-edit"></i></a>
				    </td>
					<td width="100px"><%=apb2.getPROID()%></td>
					<td width="100px"><%=apb2.getPRONAME()%></td>
					<td width="100px"><%=apb2.getPROTYPE()%></td>
					<td width="100px"><%=apb2.getPROSTATUS()%></td>
					<td width="100px"><%=apb2.getPROPRIORITY()%></td>
					<td width="100px"><%=apb2.getREVIEWDATE()%></td>
					<td width="100px"><%=apb2.getDESIGNDATE()%></td>
					<td width="100px">
						<input type="button"onclick="getDetailes(<%=apb2.getPROID()%>)"name="addproject" value="view" class="myButton nobull">
					</td>
				</tr>
			</tbody>
			<%
				}
			%>
		</table>
	 </div>
 </form>
</div>
<!--End closed****************************  -->

<!--***********************************************OnHold*****************************-->
<div align="center" id="onHold" class="tabcontent">
<form>
	<table id="customers" class="sortable" style="text-align: center;">
		<tbody style="text-align: center;">
			<tr>
				<th width="100px">Action</th>
				<th width="100px">Project ID</th>
				<th width="100px">Project Name</th>
				<th width="100px">Type</th>
				<th width="100px">Project Priority</th>
				<th width="100px">Status</th>
				<th width="100px">Start Date</th>
				<th width="101px">End Date</th>
				<th width="120px">Information</th>
			</tr>
		 </tbody>
	</table>				
	<div style="overflow-y: scroll; height: 230px;">
	   <table id="customers">
			<%
				ArrayList<AddProjectBean> proList3 = new ArrayList<AddProjectBean>();
				AddProjectDAO apd3 = new AddProjectDAO();
				proList3 = apd3.onhold();
				for (AddProjectBean apb3 : proList3) 
				{
			%>
			<tbody id="myTable">
			  <tr>
				<td width="100px">&nbsp; &nbsp; &nbsp; &nbsp;
					<a class="btn btn-default btn-xs purple" id="tb1" onclick="detect(<%=apb3.getPROID()%>)">
					<i class="fa fa-trash-o"></i></a> &nbsp; &nbsp; &nbsp; 
					<a href="EditProject.jsp?Pid=<%=apb3.getPROID()%>"class="btn btn-default btn-xs purple">
					<i class="fa fa-edit"></i></a>
				</td>
				<td width="100px"><%=apb3.getPROID()%></td>
				<td width="100px"><%=apb3.getPRONAME()%></td>
				<td width="100px"><%=apb3.getPROTYPE()%></td>
				<td width="100px"><%=apb3.getPROSTATUS()%></td>
				<td width="100px"><%=apb3.getPROPRIORITY()%></td>
				<td width="100px"><%=apb3.getREVIEWDATE()%></td>
				<td width="100px"><%=apb3.getDESIGNDATE()%></td>
				<td width="100px">
					<input type="button" onclick="getDetailes(<%=apb3.getPROID()%>)"name="addproject" value="view" class="myButton nobull">
				</td>
				</tr>
		   </tbody>
			<%
				}
			%>
		</table>
	 </div>
  </form>			
</div>
<!--End OnHold****************************  -->
<!--***********************************************cancelled*****************************-->
<div align="center" id="cancelled" class="tabcontent">
<form>
	<table id="customers" class="sortable" style="text-align: center;">
		<tbody style="text-align: center;">
			<tr>
				<th width="100px">Action</th>
				<th width="100px">Project ID</th>
				<th width="100px">Project Name</th>
				<th width="100px">Type</th>
				<th width="100px">Project Priority</th>
				<th width="100px">Status</th>
				<th width="100px">Start Date</th>
				<th width="101px">End Date</th>
				<th width="120px">Information</th>
			</tr>
		 </tbody>
	</table>
	<div style="overflow-y: scroll; height: 230px;">
	   <table id="customers">
			<%
				ArrayList<AddProjectBean> proList4 = new ArrayList<AddProjectBean>();
				AddProjectDAO apd4 = new AddProjectDAO();
				proList4 = apd4.cancel();
				for (AddProjectBean apb4 : proList4)
				{
			%>
			<tbody id="myTable">
				<tr>
					<td width="100px">&nbsp; &nbsp; &nbsp; &nbsp;
					  <a class="btn btn-default btn-xs purple" id="tb1" onclick="detect(<%=apb4.getPROID()%>)">
					  <i class="fa fa-trash-o"></i></a> &nbsp; &nbsp; &nbsp;
					  <a href="EditProject.jsp?Pid=<%=apb4.getPROID()%>" class="btn btn-default btn-xs purple">
					  <i class="fa fa-edit"></i></a>
					</td>
					<td width="100px"><%=apb4.getPROID()%></td>
					<td width="100px"><%=apb4.getPRONAME()%></td>
					<td width="100px"><%=apb4.getPROTYPE()%></td>
					<td width="100px"><%=apb4.getPROSTATUS()%></td>
					<td width="100px"><%=apb4.getPROPRIORITY()%></td>
					<td width="100px"><%=apb4.getREVIEWDATE()%></td>
					<td width="100px"><%=apb4.getDESIGNDATE()%></td>
					<td width="100px">
						<input type="button"onclick="getDetailes(<%=apb4.getPROID()%>)"name="addproject" value="view" class="myButton nobull">
					</td>
				</tr>
			</tbody>
			<%
				}
			%>
		</table>
	</div>
 </form>			
</div>
<!--End Cancelled****************************  -->
<!--***********************************************IT*****************************-->
<div align="center" id="it" class="tabcontent">
<form>
	<table id="customers" class="sortable" style="text-align: center;">
		<tbody style="text-align: center;">
			<tr>
				<th width="100px">Action</th>
				<th width="100px">Project ID</th>
				<th width="100px">Project Name</th>
				<th width="100px">Type</th>
				<th width="100px">Project Priority</th>
				<th width="100px">Status</th>
				<th width="100px">Start Date</th>
				<th width="101px">End Date</th>
				<th width="120px">Information</th>
			</tr>
		 </tbody>
	</table>
	<div style="overflow-y: scroll; height: 230px;">
	   <table id="customers">
			<%
				ArrayList<AddProjectBean> proList5 = new ArrayList<AddProjectBean>();
				AddProjectDAO apd5 = new AddProjectDAO();
				proList5 = apd5.it();
				for (AddProjectBean apb5 : proList5)
				{
			%>
			<tbody id="myTable">
				<tr>
					<td width="100px">&nbsp; &nbsp; &nbsp; &nbsp;
						<a class="btn btn-default btn-xs purple" id="tb1" onclick="detect(<%=apb5.getPROID()%>)">
						<i class="fa fa-trash-o"></i></a> &nbsp; &nbsp; &nbsp; 
						<a href="EditProject.jsp?Pid=<%=apb5.getPROID()%>" class="btn btn-default btn-xs purple">
						<i class="fa fa-edit"></i></a>
					</td>
					<td width="100px"><%=apb5.getPROID()%></td>
					<td width="100px"><%=apb5.getPRONAME()%></td>
					<td width="100px"><%=apb5.getPROTYPE()%></td>
					<td width="100px"><%=apb5.getPROSTATUS()%></td>
					<td width="100px"><%=apb5.getPROPRIORITY()%></td>
					<td width="100px"><%=apb5.getREVIEWDATE()%></td>
					<td width="100px"><%=apb5.getDESIGNDATE()%></td>
					<td width="100px">
						<input type="button" onclick="getDetailes(<%=apb5.getPROID()%>)"name="addproject" value="view" class="myButton nobull">
					</td>
				</tr>
			</tbody>
			<%
				}
			%>
		</table>
	 </div>
  </form>			
</div>
<!--End IT****************************  -->
	
<!--***********************************************support*****************************-->
<div align="center" id="support" class="tabcontent">
<form>
	<table id="customers" class="sortable" style="text-align: center;">
		<tbody style="text-align: center;">
			<tr>
				<th width="100px">Action</th>
				<th width="100px">Project ID</th>
				<th width="100px">Project Name</th>
				<th width="100px">Type</th>
				<th width="100px">Project Priority</th>
				<th width="100px">Status</th>
				<th width="100px">Start Date</th>
				<th width="101px">End Date</th>
				<th width="120px">Information</th>
			</tr>
		 </tbody>
	</table>
	<div style="overflow-y: scroll; height: 230px;">
		<table id="customers">
			<%
				ArrayList<AddProjectBean> proList6 = new ArrayList<AddProjectBean>();
				AddProjectDAO apd6 = new AddProjectDAO();
				proList6 = apd6.support();
				for (AddProjectBean apb6 : proList6) 
				{
			%>
			<tbody id="myTable">
				<tr>
				  <td width="100px">&nbsp; &nbsp; &nbsp; &nbsp;
					<a class="btn btn-default btn-xs purple" id="tb1" onclick="detect(<%=apb6.getPROID()%>)">
					<i class="fa fa-trash-o"></i></a> &nbsp; &nbsp; &nbsp; 
					<a href="EditProject.jsp?Pid=<%=apb6.getPROID()%>"class="btn btn-default btn-xs purple">
					<i class="fa fa-edit"></i></a>
				  </td>
				  <td width="100px"><%=apb6.getPROID()%></td>
				  <td width="100px"><%=apb6.getPRONAME()%></td>
				  <td width="100px"><%=apb6.getPROTYPE()%></td>
				  <td width="100px"><%=apb6.getPROSTATUS()%></td>
				  <td width="100px"><%=apb6.getPROPRIORITY()%></td>
				  <td width="100px"><%=apb6.getREVIEWDATE()%></td>
				  <td width="100px"><%=apb6.getDESIGNDATE()%></td>
				  <td width="100px">
				  	<input type="button" onclick="getDetailes(<%=apb6.getPROID()%>)"name="addproject" value="view" class="myButton nobull">
				   </td>
				</tr>
			</tbody>

			<%
				}
			%>
		</table>
	 </div>
  </form>			
</div>
<!--End support****************************  -->   

<!--***********************************************Internal*****************************-->
<div align="center" id="internal" class="tabcontent">
<form>
	<table id="customers" class="sortable" style="text-align: center;">
		<tbody style="text-align: center;">
			<tr>
				<th width="100px">Action</th>
				<th width="100px">Project ID</th>
				<th width="100px">Project Name</th>
				<th width="100px">Type</th>
				<th width="100px">Project Priority</th>
				<th width="100px">Status</th>
				<th width="100px">Start Date</th>
				<th width="101px">End Date</th>
				<th width="120px">Information</th>
			</tr>
		 </tbody>
	</table>
	<div style="overflow-y: scroll; height: 230px;">
	  <table id="customers">
		<%
			ArrayList<AddProjectBean> proList7 = new ArrayList<AddProjectBean>();
			AddProjectDAO apd7 = new AddProjectDAO();
			proList7 = apd7.internal();
			for (AddProjectBean apb7 : proList7)
			{
		%>
		<tbody id="myTable">
			<tr>
				<td width="100px">&nbsp; &nbsp; &nbsp; &nbsp;
					<a class="btn btn-default btn-xs purple" id="tb1" onclick="detect(<%=apb7.getPROID()%>)">
					<i class="fa fa-trash-o"></i></a> &nbsp; &nbsp; &nbsp; 
					<a href="EditProject.jsp?Pid=<%=apb7.getPROID()%>"class="btn btn-default btn-xs purple">
					<i class="fa fa-edit"></i></a>
				</td>
				<td width="100px"><%=apb7.getPROID()%></td>
				<td width="100px"><%=apb7.getPRONAME()%></td>
				<td width="100px"><%=apb7.getPROTYPE()%></td>
				<td width="100px"><%=apb7.getPROSTATUS()%></td>
				<td width="100px"><%=apb7.getPROPRIORITY()%></td>
				<td width="100px"><%=apb7.getREVIEWDATE()%></td>
				<td width="100px"><%=apb7.getDESIGNDATE()%></td>
				<td width="100px">
					<input type="button" onclick="getDetailes(<%=apb7.getPROID()%>)"name="addproject" value="view" class="myButton nobull">
				</td>
			</tr>
		</tbody>

		<%
			}
		%>
		</table>
	 </div>
  </form>			
</div>
<!--End Internal****************************  -->     

<!--***********************************************NewSite*****************************-->
<div align="center" id="newSite" class="tabcontent">
<form>
	<table id="customers" class="sortable" style="text-align: center;">
		<tbody style="text-align: center;">
			<tr>
				<th width="100px">Action</th>
				<th width="100px">Project ID</th>
				<th width="100px">Project Name</th>
				<th width="100px">Type</th>
				<th width="100px">Project Priority</th>
				<th width="100px">Status</th>
				<th width="100px">Start Date</th>
				<th width="101px">End Date</th>
				<th width="120px">Information</th>
			</tr>
		 </tbody>
	</table>
	<div style="overflow-y: scroll; height: 230px;">
		<table id="customers">
			<%
				ArrayList<AddProjectBean> proList8 = new ArrayList<AddProjectBean>();
				AddProjectDAO apd8 = new AddProjectDAO();
				proList8 = apd8.newsite();
				for (AddProjectBean apb8 : proList8) 
				{
			%>
			<tbody id="myTable">
				<tr>
					<td width="100px">&nbsp; &nbsp; &nbsp; &nbsp;
						<a class="btn btn-default btn-xs purple" id="tb1" onclick="detect(<%=apb8.getPROID()%>)">
						<i class="fa fa-trash-o"></i></a> &nbsp; &nbsp; &nbsp; 
						<a href="EditProject.jsp?Pid=<%=apb8.getPROID()%>" class="btn btn-default btn-xs purple">
						<i class="fa fa-edit"></i></a>
					</td>
					<td width="100px"><%=apb8.getPROID()%></td>
					<td width="100px"><%=apb8.getPRONAME()%></td>
					<td width="100px"><%=apb8.getPROTYPE()%></td>
					<td width="100px"><%=apb8.getPROSTATUS()%></td>
					<td width="100px"><%=apb8.getPROPRIORITY()%></td>
					<td width="100px"><%=apb8.getREVIEWDATE()%></td>
					<td width="100px"><%=apb8.getDESIGNDATE()%></td>
					<td width="100px">
						<input type="button" onclick="getDetailes(<%=apb8.getPROID()%>)"name="addproject" value="view" class="myButton nobull">
					</td>
				</tr>
			</tbody>

			<%
				}
			%>
		</table>
	 </div>
  </form>
 </div>
</div>

<!-- For ViewButton -->													
<div class="modal "  id="myModal" align="center" style="overflow-y: auto;" >
	<div id="modal-content">
		<!-- <span class="close">&times;</span> -->
		
				<table id="customers">								
					<tbody class="form_data"  align="center" >
								
								
					</tbody>
				</table>	
	</div>
</div>
<!-- end ViewButton -->	
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

// Get the modal
var modal = document.getElementById('myModal');

// Get the button that opens the modal
var btn = document.getElementById("myBtn");

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

// When the user clicks the button, open the modal 
btn.onclick = function() {
  modal.style.display = "block";
}

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
  modal.style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
  if (event.target == modal) {
    modal.style.display = "none";
  }
}

	
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
}
</script>

</body>
</html>