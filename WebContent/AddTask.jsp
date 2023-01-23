
<%@page import="payroll.Model.AddTaskBean"%>
<%@page import="java.util.ArrayList"%>

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

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@page errorPage="error.jsp" isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>&copy DTS3</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="css/screen1.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script type="text/javascript" src="js/datetimepicker.js"></script>
  <script type="text/javascript" src="js/date.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
 
<script type="text/javascript" src="js/datetimepicker_banking.js"></script>

  <style type="text/css">
  
 #startdate { position:relative;; height:20px; } 
#duedate { position:relative;; height:20px; } 
#progress { position:relative;; width:140px; }	
.topics tr{height : 35px;}	

/* css for editable dropdown*/

.editableBox {
    width: 75px;
    height: 30px;
}

.taskTextBox {
    width: 54px;
    margin-left: -78px;
    height: 25px;
    border: none;
  }
  </style>
  <style type="text/css">
  *{
		  box-sizing: border-box;
		  }
		   .form-popup 
		   {
		     display: none;
	position: absolute;
	font-size:10px;
	bottom: 1%;
	right: 12.5%;
	float: right;
	border: 3px solid #f1f1f1;
	z-index: 9;
	width:75%; /* Full width */
  	height: 37%; /* Full height */
  	overflow: hidden; /* Enable scroll if needed */
	background-color: rgb(0,0,0); /* Fallback color */ 
  	background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
			  
		  } 
		  
		  .tablecontainer {
  overflow-y: auto;
  height: 100px;
  background: #fff;
}   
	
  </style>
 <script>
$(document).ready(function() {

	    $("#startdate,#duedate").datepicker({
	    	
	        showOn: 'button',
	        buttonText: 'Show Date',
	       /*  buttonImageOnly: true, */
	        buttonImage: 'images/cal.gif',
	        dateFormat: "yy-mm-dd" 
	       /* dateFormate: "dd-mmm-yyyy" */
	        	
	    });
	});  
 
/*  $( function() {
	    
	  $(".datepick").datepicker({
	    onSelect: function(dateText) {
	      $(this).change();
	    }
	  }).on("change", function() {
	     display("Change event"); 
	  });

	  function display(msg) {
	    $("<p>").html(msg).appendTo(document.body);
	  }
	  });  */ 
  
 </script> 
<style type="text/css">
		/* Style the tab */
		* {
		  box-sizing: border-box;
		}
		.tab {
		  overflow: hidden;
		  border: 1px solid #ccc;
		  background-color: #f1f1f1;
		}
		
		/* Style the buttons inside the tab */
		.tab button {
		  background-color: inherit;
		  float: left;
		  border: none;
		  outline: none;
		  cursor: pointer;
		  padding: 14px 16px;
		  transition: 0.3s;
		  font-size: 17px;
		}
		
		/* Change background color of buttons on hover */
		.tab button:hover {
		  background-color: #ddd;
		}
		
		/* Create an active/current tablink class */
		.tab button.active {
		  background-color: #ccc;
		}
		
		/* Style the tab content */
		.tabcontent {
		  display: none;
		  padding: 6px 12px;
		  border: 1px solid #ccc;
		  border-top: none;
		} */
		
		
		.division
		{
		margin-top:5px;
		background-color:grey;
		/* background-color: #DADFE1; */
		width:80%;
		}
		
		/* input[type=submit],input[type=reset] */
		#btn1,#btn2 {
		  background-color: blue;
		  color: white;
		  padding: 12px 20px;
		  border: none;
		  border-radius: 4px;
		  left:80%;
		  position:relative;
		  margin-bottom:auto;
		  cursor: pointer;
		  float:left;
		}	
		#up{
		position:relative;
		margin-bottom:auto;
		left:5px;
		float:left;
		}	
		
		
		
</style>

<style type="text/css">
.tab {
  overflow: hidden;
  border: 1px solid #ccc;
  background-color: #f1f1f1;
}

/* Style the buttons inside the tab */
.tab button {
  background-color: inherit;
  float: left;
  border: none;
  outline: none;
  cursor: pointer;
  padding: 14px 16px;
  transition: 0.3s;
  font-size: 17px;
}

/* Change background color of buttons on hover */
.tab button:hover {
  background-color: #ddd;
}

/* Create an active/current tablink class */
.tab button.active {
  background-color: #ccc;
}

/* Style the tab content */
.tabcontent {
  display: none;
  padding: 6px 12px;
  border: 1px solid #ccc;
  border-top: none;
} */


.division
{
margin-top:5px;
background-color:grey;
/* background-color: #DADFE1; */
width:80%;
}

textarea{
    overflow-y: scroll;
    height: 70px;
    width: 300px;
   resize: none;
}

/*----------for checkbox------- */

.multiselect {
  width: 200px;
}

.selectBox {
  position: relative;
}

.selectBox select {
  width: 100%;
  font-weight: bold;
}

.overSelect {
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
}

#checkboxes {
  display:none;
  border: 1px #dadada solid;
  height: 100px;
 
}

#checkboxes label {
  display: block;
  
  
}

#checkboxes label:hover {
  background-color: #1e90ff;
}

.caret {
    border-top: 4px solid #919da9;
}

/* style for select multi combo*/

.combo-label {margin-bottom:.5em;}
 

</style>
 
 
 
 
 
 
 
 
<script type="text/javascript">


 function showCheckboxes() {
	
	 var select = document.getElementById("selectedval").value;	
	// alert(select);

	  if(select=="1"){
		  document.getElementById("funcadmin").style.display = 'block';
		  document.getElementById("funcclient").style.display = 'none';
		  document.getElementById("funcdeveloper").style.display = 'none';
		  document.getElementById("funcmanager").style.display = 'none';
		  document.getElementById("funcdesigner").style.display = 'none';
		  
         
    
      }else if(select=="2"){
    	  document.getElementById("funcadmin").style.display = 'none';
		  document.getElementById("funcclient").style.display = 'block';
		  document.getElementById("funcdeveloper").style.display = 'none';
		  document.getElementById("funcmanager").style.display = 'none';
		  document.getElementById("funcdesigner").style.display = 'none';
        
        }
      else if(select=="3"){
		  document.getElementById("funcadmin").style.display = 'none';
		  document.getElementById("funcclient").style.display = 'none';
		  document.getElementById("funcdeveloper").style.display = 'block';
		  document.getElementById("funcmanager").style.display = 'none';
		  document.getElementById("funcdesigner").style.display = 'none';
		  
         
    
      }else if(select=="4"){
    	  document.getElementById("funcadmin").style.display = 'none';
		  document.getElementById("funcclient").style.display = 'none';
		  document.getElementById("funcdeveloper").style.display = 'none';
		  document.getElementById("funcmanager").style.display = 'block';
		  document.getElementById("funcdesigner").style.display = 'none';
        
        }
      else if(select=="5"){
    	  document.getElementById("funcadmin").style.display = 'none';
		  document.getElementById("funcclient").style.display = 'none';
		  document.getElementById("funcdeveloper").style.display = 'none';
		  document.getElementById("funcmanager").style.display = 'none';
		  document.getElementById("funcdesigner").style.display = 'block';
        
        }	  
} 
 
 //for display all tasks onchange of project 
 function funcpro(){
	 
	 //var select = document.getElementById("selectedval").value;
	 //alert("hi");
	 var proname = document.getElementById("pnm").value;
	 window.location.replace("AddTask.jsp?pro=" + proname); 
	// alert("NAME IS :"+proname); 
 }
 

 function detect(Tid){

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
 						 alert("Record Deleted Successfully!!!"+ window.location.replace("AddTask.jsp"))
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
 
 function checktask()
	{
		
		var name = document.getElementById("name").value;
		var pnm = document.getElementById("pnm").value;
		
		
		/* if(pnm==null)
			{
			alert("please select Project name first");
			}
		 */
		
		
	try
	{
	
	var xmlhttp=new XMLHttpRequest();
	var url="";
	var response2="";
	
	
	url="AddTaskServlet?action=checktask&name="+name+"&pnm="+pnm;

	xmlhttp.onreadystatechange=function()
	{
		if(xmlhttp.readyState==4)
		{
		
		response2 = xmlhttp.responseText;
		 if(response2>0 )
			{
			 alert("You have already exist this task try again...");
			 
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
 
 function CheckPnm()
 {
	 //alert("hello");
	 var Projectnm=document.getElementById("pnm").value;
	// alert(Projectnm);
	 if(Projectnm==null)
		{
			alert("Please select Project Name");
			//setTimeout("document.getElementById('pnm').focus()", 50);
			return false;

		}
 }
 
/*  function parseMyDateForTsk(s) {
		
		var m = [ 'jan', 'feb', 'mar', 'apr', 'may', 'jun', 'jul', 'aug',
				'sep', 'oct', 'nov', 'dec' ];
		var match = s.match(/(\d+)-([^.]+)-(\d+)/);
		var date = match[1];
		var monthText = match[2];
		var year = match[3];
		var month = m.indexOf(monthText.toLowerCase());
		return new Date(year, month, date);
	} */
 function TaskValidation(){
		//alert(document.getElementById("pnm").value);
	
	 var Projectnm=document.getElementById("pnm").value;
	
	 if(Projectnm=="")		
	    {
			alert("Please select Project Name");
			//setTimeout("document.getElementById('pnm').focus()", 50);
			return false;

		}
	
	
	 var sdate=document.getElementById("startdate").value;
	 var edate=document.getElementById("duedate").value;
	 
	   /* date sdt=Date.parse(sdate);
	   date edt=Date.parse(edate);  */
	 
	 if (sdate > edate) 
	 {
	 	alert("Start date should not be Greater than End date !");
	 	document.getElementById("duedate").value = "";
	 	return false;
	 	/* "window.document.location.href='AddTask.jsp"; */
	 }

 }

	$(document).mouseup(function (e){

		var container = $("#myForm");

		if (!container.is(e.target) && container.has(e.target).length === 0){

			container.fadeOut();
			
		}
	}); 	
	
/*   function remuall(Tid){
     try
 		{
 		
 		var xmlhttp=new XMLHttpRequest();
 	 	var url="";
 	 	var response="";
 	 	url="AddTaskServlet?action=removerecord&id="+Tid;
 	 	
 	 	let s=confirm("do you want to delete this record?");
 	 	//window.location.replace("ProjectViewAll.jsp")  
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
 }  */

 
</script>

<%
String action=request.getParameter("action")!=null?request.getParameter("action"):"AddTask";
System.out.println("action is "+action);


AddTaskBean taskbean = new AddTaskBean();
%>
 </head>
<body >
 
<%@include file="mainHeader.jsp" %>
	 <!--  start nav-outer-repeat................................................................................................. START -->
	<%@include file="subHeader.jsp" %>
	<%-- <%@ include file="ProjectFilterSubHeader.jsp" %> --%>
  
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:auto;height:80%;" >
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h2>Task</h2>
	</div>
	<!-- end page-heading -->

<!-- <div align="center">
<tr class="alt"><td>&nbsp;</td><td colspan="3">
				
				<input type="button" class="myButton" value="Attachment"
					onClick="window.document.location.href='TaskAttach.jsp'">
				</td></tr>
</div> -->


	<table border="0" width="100%" cellpadding="0" cellspacing="0" id="content-table" class="topics">
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
				
			<form action="AddTaskServlet?action=addtask" name="taskform" method="post" onsubmit="return TaskValidation()">
			
			<table id="customers" align="center">
			<tr class="alt" style="border:solid;border-color:black;">
				  <th colspan="4">New Task</th>
			</tr>

                        <colgroup>
						    <!--  <col style="width:15%" />
						     <col style="width:10%" /> 
						     <col style="width:15%" />
						     <col style="width:20%" />  -->
						     <!-- <col style="width: 10%" /> -->
						                        <col style="width: 105px" />
												 <col style="width:190px;" />
												<col style="width:107px" />
											  <col style="width:200px;" />   	
											<!--   <col style="width:80px" />  -->
						     </colgroup>			
		

			<tr class="alt"><td>Project :<font color="red"><b>*</b></font></td>										
          	<td colspan="3"><select id="pnm" name="pnm" style="width:600px; height: 30px";>
								<option value="" selected="selected">SELECT</option>
									<%
											ArrayList<AddProjectBean> resultproject = new ArrayList<AddProjectBean>();
									        AddProjectDAO pdao = new AddProjectDAO();
									        resultproject=pdao.getProjectName();
									        
									      for(AddProjectBean probean : resultproject)
									        { %>
									            <%--  <option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option> --%>
									        	 
									        	 <option value="<%=probean.getPRONAME() %>"><%=probean.getPRONAME()%></option>
									        	
									      <%}%>
								</select> </td>
			</tr><tr class="alt">				
							<td>Task Name :</td>
							<td colspan="3"><input type="text" id="name" name="name" placeholder="Enter Task Name" onBlur="checktask()" style="width:600px; height: 25px ";></td>														
							
			</tr>
		
			<tr class="alt"><td>Priority :</td>
							<td><select id="priority" name="priority" style="width:155px";>
								<option value="null" selected="selected">SELECT</option>
									<option value="Unknown">Unknown</option>
									<option value="Low">Low</option>
									<option value="Medium">Medium</option>
									<option value="High">High</option>
									<option value="Urgent">Urgent</option>
								</select></td>
							
							<td>Status :</td>
						
						<td><select id="status" name="status">
					    <option value="0" selected="selected">SELECT</option>
						<optgroup label="Open">
							<option value="open">Open</option>
							<option value="waiting-assessmante">Waiting-Assessmante</option>
							<option value="re-opened">Re-Opened</option>
						</optgroup>
						<optgroup label="done">
							<option value="done">Done?</option>
						</optgroup>
						<optgroup label="closed">
							<option value="completed">Completed</option>
							<option value="paid">Paid</option>
							<option value="suspended">Suspended</option>
							<option value="lost">Lost</option>
						</optgroup>
					</select>
				    </td>
			</tr>

			<!-- <tr class="alt"><td>Task Name :</td>
							<td>
							<input type="text" id="name" name="name" placeholder="Enter Task Name">
							</td>
							<td>Sub Task :</td>
							<td><input type="text" id="label" name="label" class="form-control" style="width:150px";>
								</td>
			</tr> -->
			
			
			<tr class="alt">
			
			<%-- <td>Assigned To :</td>
			<td>
		    <div class="selectBox">
		     <select id="selectedval" name="selectedval" onchange="showCheckboxes()" style="width:155px";>
		       <option value="0" selected="selected">SELECT</option>
		         <option value="1">admin</option>
		         <option value="2">client</option>
		         <option value="3">developer</option>
		         <option value="4">manager</option>
		         <option value="5">designer</option>
		      </select>
	       </div>
		    
		    
		    <div id="funcadmin" style="display: none;">
		      
		         <%
					 Connection con=null;
					 con = ConnectionManager.getConnection();
					 ResultSet rs;
					 Statement stmt = con.createStatement();
					
					 try
					 {
					 String str="select Name from Employee_PM where Designation='admin'";
							 rs=stmt.executeQuery(str);
						
					 while(rs.next())
					 {
						 %>	
                         
     					 <input type="checkbox" name="tasks[assigned_to][]" value ="<%=rs.getString("Name") %>"><%=rs.getString("Name") %><br>
			     	    
					   <% }  
			    		 rs.close();
						// con.close();
				  }
				 catch(Exception e)
				 {
					 e.printStackTrace();
				 } %>
		    </div>
		    
		      <div id="funcclient" style="display: none;">
		      
		         <%
					 
					 ResultSet rs1;
					 Statement stmt1 = con.createStatement();
					
					 try
					 {
					 String str1="select Name from Employee_PM where Designation='client'";
							 rs1=stmt1.executeQuery(str1);
						
					 while(rs1.next())
					 {
						 %>	
                         
     					 <input type="checkbox" name="tasks[assigned_to][]" value ="<%=rs1.getString("Name") %>"><%=rs1.getString("Name") %><br>
			     	    
					   <% }  
			    		 rs1.close();
						
				  }
				 catch(Exception e)
				 {
					 e.printStackTrace();
				 } %>
		    </div>
		
		    <div id="funcdeveloper" style="display: none;">
		      
		         <%
					 
					 ResultSet rs2;
					 Statement stmt2 = con.createStatement();
					
					 try
					 {
					 String str2="select Name from Employee_PM where Designation='developer'";
							 rs2=stmt2.executeQuery(str2);
						
					 while(rs2.next())
					 {
						 %>	
                         
     					 <input type="checkbox" name="tasks[assigned_to][]" value ="<%=rs2.getString("Name") %>"><%=rs2.getString("Name") %><br>
			     	    
					   <% }  
			    		 rs2.close();
						
				  }
				 catch(Exception e)
				 {
					 e.printStackTrace();
				 } %>
		    </div>
		    
		      <div id="funcmanager" style="display: none;">
		      
		         <%
					 
					 ResultSet rs3;
					 Statement stmt3 = con.createStatement();
					
					 try
					 {
					 String str3="select Name from Employee_PM where Designation='manager'";
							 rs3=stmt3.executeQuery(str3);
						
					 while(rs3.next())
					 {
						 %>	
                         
     					 <input type="checkbox" name="tasks[assigned_to][]" value ="<%=rs3.getString("Name") %>"><%=rs3.getString("Name") %><br>
			     	    
					   <% }  
			    		 rs3.close();
						
				  }
				 catch(Exception e)
				 {
					 e.printStackTrace();
				 } %>
		    </div> 
		    
		      <div id="funcdesigner" style="display: none;">
		      
		         <%
					 
					 ResultSet rs4;
					 Statement stmt4 = con.createStatement();
					
					 try
					 {
					 String str4="select Name from Employee_PM where Designation='designer'";
							 rs4=stmt4.executeQuery(str4);
						
					 while(rs4.next())
					 {
						 %>	
                         
     					 <input type="checkbox" name="tasks[assigned_to][]" value ="<%=rs4.getString("Name") %>"><%=rs4.getString("Name") %><br>
			     	    
					   <% }  
			    		 rs4.close();
						con.close();
				  }
				 catch(Exception e)
				 {
					 e.printStackTrace();
				 } %>
		    </div>   
		</td> --%>
		 
		 <td style="min-width:160px">Type :</td>
								<td><select style="width: 158px" id="typeid" name="type">
							    <option value="null" selected="selected">SELECT</option>
								<option value="ChangePriorityRate(Hourly rate($ 25.00))">Change Priority Rate(Hourly rate($ 25.00))</option>
								<option value="Defects(Hourly rate($ 0.00))">Defects(Hourly rate($ 0.00))</option>
								<option value="Changes(Hourly rate($ 15.00))">Changes(Hourly rate($ 15.00))</option>
							    </select></td>
		 
		      <td>Estimated Time :</td>
			  <td><input type="text" name="estiTime" id="estiTime"></td>
		</tr>
			

            <%-- <tr class="alt">
				  		 <td>Created By :</td>
							<td colspan="3"><select id="created_by" name="created_by" class="form-control" style="width:150px";>
								<option value="null" selected="selected">SELECT</option>
									<%
											 Connection con1=null;
											 con1 = ConnectionManager.getConnection();
											 ResultSet rss;
											 Statement stm = con1.createStatement();
											
											 try
											 {
											 String str1="select Name from Employee_PM";
													 rss=stm.executeQuery(str1);
												
											 while(rss.next())
											 {
												 %>	
						
						     					 <option value="<%=rss.getString("Name") %>"><%=rss.getString("Name") %></option> 
									     	
											   <% }  
									    		 rss.close();
												 con1.close();
										  }
										 catch(Exception e)
										 {
											 e.printStackTrace();
										 } 
							 %>
								</select> </td>
							
							<td>Progress :</td>
							<td><select id="progress" name="progress" style="width:150px";>
							<script>							
								var min = 0, max = 100, select = document
										.getElementById('progress');

								for (var i = min; i <= max; i = i + 5) {
									var opt = document.createElement('option');
									opt.value = i;
									opt.innerHTML = i+'%';
									select.appendChild(opt);
									
								}
							</script>
						</select></td>
			</tr> --%>


			<tr class="alt"><td>Start Date :</td>
			    			<td><input type='text' class='datepick' id='startdate'   name="startdate" placeholder="Enter Start Date" readonly="readonly"></td>
							
							<td>Due Date :</td>
							<td><input type="text" name="duedate" class='datepick' id="duedate" placeholder="Enter End Date" readonly="readonly"/></td>
							
							
			</tr>
			
			  <tr class="alt">
		
			         <td>Description :</td>
					<td colspan="3"><textarea rows="2" cols="38" name="description" id="description"></textarea></td>
			</tr>
			 
			<tr class="alt"><td colspan="4" align="center">
			              <input type="submit" value="Save" name="Save" class="myButton">&nbsp;&nbsp;
						  <!-- <input type="reset" value="cancel" class="myButton">&nbsp;&nbsp; -->
						   <a href="AddTask.jsp"><input onclick="this.parentNode.href=AddTask.jsp;" type="button" value="Cancel" name="cancel" class="myButton"/></a>
			</td></tr>
			<tr class="alt"><td colspan="2" align="center">
						    <a href="AddProjectTabs.jsp"><input onclick="this.parentNode.href=AddProjectTabs.jsp;" type="button" value="AssignTask" name="AssignTask" class="myButton"/></a>
			</td>
			                <td colspan="2"  align="center">
			
			 <input type="button" value="Display Records"  class="myButton" onclick="openForm()">
			</td>
			</tr>
		</table>        
	</form>	
	<!-- </div>	 -->
			<!-- <tr class="alt">
			<td colspan="6"  align="center">
			<button class="open-button myButton" onclick="openForm()">Display Records</button>
			 <input type="button" value="Display Records"  class="myButton" onclick="openForm()">
			</td>
		</tr> -->
			
			</center>
			</div>
			
			<!--  end table-content  -->
	
			<div class="clear"></div>
		 
		</div>
		<!--  end content-table-inner ............................................END  -->
		</td>
		<td id="tbl-border-right"></td> <!-- ----------------To reduce space at bottom of form for up the table *************************------>
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

<!-- ---Start Div for view (button) records -->

<div class="form-popup" id="myForm" align="center">
		<form action="#" class="form-container">
		<!-- <div style="overflow-y:scroll;height:120px;"> -->
		<table id="customers" align="center"  class="tablecontainer" width="90%"> <!--style="font-size: 12;"  -->
		<tbody>
		<tr ><td colspan="10" align="center" >All Information</td></tr>
				<tr style="border:solid;   border-color: black;">
				<th width="40">TaskAction</th >
                   	<th width="40">Task Id</th >
                   	<th width="100">Project</th>
		        	<th width="70">Priority</th>
		        	<th width="100">Task Name</th>
		        	<!-- <th>Sub Task</th>  -->
		        	<th width="100">Status</th>
		       	   <!--  <th width="130">Type</th>  	 -->
	       			<th width="70">Est. Time</th>
	       			<th width="100">Start Date</th>
		     		<th width="100">Due Date</th>
		     		<th width="40">SubTaskAction</th >
</tr>
</tbody>
</table>
<div style="overflow-y: scroll; height: 120px;width:900px;">
<table id="customers"  align="center" class="tablecontainer">
<tr class="alt">
		<%
		
   		 ArrayList<AddTaskBean> result = new ArrayList<AddTaskBean>();
		  AddTaskDao adao = new AddTaskDao();
			result=adao.getTaskDetail();
	  
			  for(AddTaskBean rss : result)
			  {
   %>           
				   <tr>
					<td width="100" align="center">
					
				    <a href="#" class="btn btn-default btn-xs purple" onclick="detect(<%=rss.getTASK_ID() %>)"><i class="fa fa-trash-o"></i></a>
					<a href="#" class="btn btn-default btn-xs purple" onclick="window.document.location.href='UpdateTask.jsp?Tid=<%=rss.getTASK_ID() %>'"><i class="fa fa-edit"></i></a></td>
					 
					 
					 <td width="43"><%=rss.getTASK_ID()%></td> 
				    <td width="84"><%=rss.getPROJECT()%></td> 
					<td width="70"><%=rss.getPRIORITY() %></td>     
					<td width="100"><%=rss.getNAME() %></td>
					 <td width="100"><%=rss.getSTATUS() %></td>    
			        <%-- <td width="130"><%=rss.getTYPE() %></td> --%>
			        <td width="70"><%=rss.getESTIMATED_TIME() %></td>
			        <td width="100"><%=rss.getSTART_DATE() %></td> 
			        <td width="100"><%=rss.getDUE_DATE() %></td>   
										
					
			        <td align="center" width="100">
					<a href="#" class="btn btn-default btn-xs purple" onclick="window.document.location.href='AddSubTask.jsp?Tid=<%=rss.getTASK_ID()%>'"><i class="fa fa-plus"></i></a>
					<a href="#" class="btn btn-default btn-xs purple" onclick="window.document.location.href='EditSubTask.jsp?Tid=<%=rss.getTASK_ID()%>'"><i class="fa fa-edit"></i></a></td>
					</tr>
	  <%
						}
					%>
				</table>
			</div>
		</form>
	</div>

<!-- -------End Div view button----- -->
    
<script>
function openForm() {
  document.getElementById("myForm").style.display = "block";
}

function closeForm() {
  document.getElementById("myForm").style.display = "none";
}
</script>

</body>
</html>


