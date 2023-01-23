<%@page import="java.sql.Date"%>
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


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@page errorPage="error.jsp" isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy DTS3</title>
<link rel="stylesheet" href="css/screen1.css" type="text/css" media="screen" title="default" />

<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

  
  <style type="text/css">
  
  #customers1 {
  font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
  border-collapse: collapse;
  border-style:ridge;
  
  width: 70%;
}

#customers1 td, #customers1 th {
  border: 2px solid #ddd;
  padding: 8px;
}

#customers1 tr:nth-child(even){background-color: #f2f2f2;}

#customers1 tr:hover {background-color: #ddd;}
#customers1 td{
font-family:serif;
  background-color:  #1F5FA7;
  color: white;
  font-size: medium;
}
#customers1 th {
  
  font-family:serif;
  background-color:  #1F5FA7;
  color: white;
  font-size: medium;
}
  /*----------for checkbox------- */

.multiselect {
  width: 150px;
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
  display: none;
  border: 1px #dadada solid;
}

#checkboxes label {
  display: block;
}

#checkboxes label:hover {
  background-color: #1e90ff;
} 

.topics tr{height : 30px;}

  </style>

<style>

#startdate { position:relative;; height:20px; } 
#duedate { position:relative;; height:20px; } 
#progress { position:relative;; width:140px; }		
.topics tr{height : 30px;}

</style>
<script> 
  $(document).ready(function() {
	    $("#fromdate,#todate").datepicker({
	        showOn: 'button',
	        buttonText: 'Show Date',
	        /* buttonImageOnly: true, */
	        buttonImage: 'images/cal.gif',
	        dateFormat: "yy-mm-dd" 	
	    });
	});
  </script>


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
function drop()
{
	
	var select=document.getElementById("pnm").value;
	
	 var xmlhttp=new XMLHttpRequest();
	 	var url="";
	 	var response2="";
	 	var TotDays="";
	 	url="DeleteProjectServlet?action=getProjectReportData&select="+select;
	 
		 xmlhttp.onreadystatechange=function()
		{
			if(xmlhttp.readyState==4)
			{
			response2 = xmlhttp.responseText;
			if(response2!=null)
				{
				
				alert("response :"+response2);
				
				}
		  	
			//alert("response :"+response2);
			
			 }
			};
		xmlhttp.open("POST", url, true);
		xmlhttp.send(); 
	
	}
</script> 

 </head>
<body style="overflow:hidden">
 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
  
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<!-- <div id="page-heading">
		<h1>New Task</h1>
	</div> -->
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
			
			
	<form action="AddReportServlet?action=generatereport" method="post">
			
			<table id="customers" align="center">
			<tr class="alt">
				  <th colspan="7">New Project Report</th>
			</tr>

                        <colgroup>
						     <col style="width:25%" />
						     <col style="width:25%" /> 
						     <col style="width:20%" />
						     <col style="width:20%" /> 
						     <col style="width: 20%" />
						      
 						 </colgroup>			
		<tr class="alt" colspan="7"></tr>
			<tr class=" alt">
					<th>From Date</th>
					<th>To Date</th>
					<th>Type</th>
					<th>Sub Type</th>
					<th>File Type</th>
					</tr>
					<tr>					
							<td style="font-size: medium;width: 20px;">
							
							<input type="text" class='datepick' id="fromdate"  name="fromdate" placeholder="Enter Date" readonly="readonly"/>&nbsp;
							</td>
								<td style="font-size: medium;">
										<input name="todate" class='datepick' type="text" id="todate" placeholder="Enter Date" readonly="readonly"/>
										</td>
							<td style="font-size: medium;">
								<select style="width: 138px;" id="typeId" name="type" onchange="gettypes(this.value)" class="form-control">
									    <option  selected="selected">Select Type</option>
										<option value="Pstatus">Status</option>
										<option value="PPriority">Priority</option>
										<option value="all">All</option>
							    </select></td>
							    <td style="font-size: medium;">
								<select style="width: 138px" id="subtypeid" name="subtype" class="form-control selectval">
								    <option value="null" selected="selected">Select Type</option>
									<!-- using database to select value -->
							    </select></td>
							    <td style="font-size: medium;">
								<select style="width: 138px" id="typeid" name="filetype" class="form-control">
								    
									<option value="xls">EXCEL</option>
									<option value="pdf">PDF</option>
									<option value="txt">TEXT</option>
							    	</select>
							    </td>
				</tr>
			</table>        
			<input type="submit" value="Save" name="Save" class="myButton">&nbsp;&nbsp;
						  <input type="reset" value="cancel" class="myButton">&nbsp;&nbsp;
			</form>
			
			
			
			</center>
			</div>
			
			<!--  end table-content  -->
	
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
 <script type="text/javascript">
 function gettypes(value)
 {
	 var html="";
	
	 alert(value);
	 try
	 {
		var xmlhttp=new XMLHttpRequest();
	 	var url="";
	 	var response="";
	 	url="AddReportServlet?action=gettypes&types="+value;
	 	
	 	
		
			 	xmlhttp.onreadystatechange=function()
				{
					if(xmlhttp.readyState==4)
					{	
					response = xmlhttp.responseText.split(",");
					
					for(var i=0;i<response.length;i++)
						{
							html+="<option value='"+response[i]+"'>"+response[i]+"</option>"
						}
					
					 $('.selectval').html(html);
					}
				};
				xmlhttp.open("POST", url, true);
				xmlhttp.send();
			
		}
		catch(e)
		{
			
		}
	 
 }
 
 </script>   

</body>
</html>

<%-- <tr class="alt"><td>Project :</td>										
          	<td><select id="pnm" name="pnm" class="form-control" style="width:150px";>
								<option value="null" selected="selected">Select Project</option>
									<%
											 Connection con1=null;
											 con1 = ConnectionManager.getConnection();
											 ResultSet rss;
											 Statement stm = con1.createStatement();
											
											 try
											 {
											 String str1="select ProjectName from AddProject";
													 rss=stm.executeQuery(str1);
												
											 while(rss.next())
											 {
												 %>	
						
						     					 <option value="<%=rss.getString("ProjectName") %>"><%=rss.getString("ProjectName") %></option> 
									     	
											   <% }  
									    		 rss.close();
									    		 stm.close();
												 
										  }
										 catch(Exception e)
										 {
											 e.printStackTrace();
										 } 
					            	%>
								</select> </td>
							
											
							
			</tr>
		
			<tr class="alt">
							
							
							<td>Status :</td>
							<td>
						    <!-- <select id="status" name="status">
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
				</select> -->
							<select id="status" name="status">
							<option  selected="selected">Select Status</option>
							<option  >Open</option>
							<option  >On Hold</option>
							<option  >Cancelled</option>
							<option  >Closed</option>
							<option><%=ps %></option>
							</select>
							</td></tr><tr class="alt">
							<td style="min-width:160px">Type :</td>
								<td><select style="width: 138px" id="typeid" name="type" class="form-control">
							    <option value="null" selected="selected">Select Type</option>
								<option value="ChangePriorityRate(Hourly rate($ 25.00))">Change Rate</option>
								<option value="Defects(Hourly rate($ 0.00))">Defects</option>
								<option value="Changes(Hourly rate($ 15.00))">Changes</option>
							    </select></td>
			</tr>

			<tr class="alt"><td>Priority :</td>
							<td><select id="priority" name="priority" class="form-control" style="width:155px";>
								<option value="null" selected="selected">Select Priority</option>
									<option value="Unknown">Unknown</option>
									<option value="Low">Low</option>
									<option value="Medium">Medium</option>
									<option value="High">High</option>
									<option value="Urgent">Urgent</option>
								</select></td>
							<!-- <td>Sub Task :</td>
							<td><select id="label" name="label" class="form-control" style="width:150px";>
								<option value="null" selected="selected">SELECT</option>
									<option value="Change">Change</option>
									<option value="PlugIn">PlugIn</option>
									<option value="Task">Task</option>
									<option value="Idea">Idea</option>
									<option value="Bug">Bug</option>
									<option value="Quote">Quote</option>
									<option value="Issue">Issue</option>
								</select></td> -->
			</tr>
			
			
			
			

            

			<!-- <tr class="alt">
							<td>Start Date :</td>
							<td>
								<input type="text" class='datepick' id="startdate"  name="startdate" placeholder="Enter Date" readonly="readonly"/>&nbsp;
							</td>
								<td>Due Date :</td>
							<td><input name="duedate" class='datepick' type="text" id="duedate" placeholder="Enter Date" readonly="readonly"/></td>
				</tr>
							 -->
							
							
							
							<td>Due Date :</td>
							<td><input name="duedate" class='datepick' type="text" id="duedate" placeholder="Enter Date" readonly="readonly"/>
							<!-- <input type='text' name="duedate" size="15" id="duedate" onBlur="if(value=='')" readonly="readonly">&nbsp;
								<img src="images/cal.gif" align="middle" style="vertical-align:middle; cursor:pointer;" 
										onClick="javascript:NewCssCal('duedate', 'ddmmmyyyy')" ></td> -->
							
			</tr>
			
			  <tr class="alt"><td>Description :</td>
							<td colspan="3"><textarea rows="2" cols="38" name="description" id="description"></textarea></td>
			</tr> --%>
