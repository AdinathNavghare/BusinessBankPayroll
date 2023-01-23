<%@page import="java.util.*"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager" %>
<%@page import="java.sql.PreparedStatement" %>
<%@page import="java.sql.SQLException" %>
<%@page import="java.sql.Statement" %>
<%@page import="java.sql.ResultSet" %>
<%@page import="payroll.DAO.ConnectionManager"%>
<%@page import="payroll.DAO.AddProjectDAO"%>
<%@page import="payroll.Model.AddProjectBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%-- <%@page errorPage="error.jsp" isErrorPage="true"%> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="ISO-8859-1">
<title>&copy DTS3</title>
<link rel="stylesheet" href="css/screen1.css" type="text/css" media="screen" title="default" />

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<!--for datePicker -->
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

  
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
		
		#btn3{
		background-color: blue;
		color: white;
		position:absolute;
		margin-bottom:auto;
		padding: 8px 30px;
		border: solid;
		border-color:darkblue;
		border-radius: 4px;
		left:16%;
		float:left;
		}	
		#btn4{
		background-color:lightgray ;
		color: black;
		position:absolute;
		margin-bottom:auto;
		padding: 8px 30px;
		border: solid;
		border-color:darkblue;
		border-radius: 4px;
		left:26%;
		float:left;
		}	
</style>

<script>
  
  $(document).ready(function() {

	    $("#txtDate,#txtDate1,#txtDate2,#txtDate3,#txtDate4").datepicker({
	    	
	    	changeMonth: true, 
	        changeYear: true, 
	        yearRange: "-90:+00",
	    	showOn: 'button',
	        buttonText: 'Show Date',
	        /* buttonImageOnly: true, */
	        buttonImage: 'images/cal.gif',
	        dateFormat: "yy-mm-dd" 
	        	
	    });
	});
  
  /* $( function() {
	    
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
<%
/* //AddProjectBean addbean = new AddProjectBean(); */
String action=request.getParameter("action")!=null?request.getParameter("action"):"EditProject";
int pid=Integer.parseInt(request.getParameter("Pid"));
System.out.print("Edit record for "+pid+ " id");
String project=null,type=null,live=null,status=null,priority=null,test=null,descrip=null,review=null,site=null,design=null,develop=null,uat=null;
String[] a=new String[100];
%>
<% 
ArrayList<AddProjectBean> result = new ArrayList<AddProjectBean>();
AddProjectDAO adao = new AddProjectDAO();
	result=adao.getProjectDetailEdit(pid);
	  for(AddProjectBean rs : result)
	  {
	
			type=rs.getPROTYPE();
			status=rs.getPROSTATUS();
			priority=rs.getPROPRIORITY();
			project= rs.getPRONAME();
			live=rs.getLIVEURL();
			test=rs.getTESTURL();
			review=rs.getREVIEWDATE();
			design=rs.getDESIGNDATE();
			/* develop=rs.getString("DevelopDate");
			site=rs.getString("SiteTestDate");
			uat=rs.getString("UATDate"); */
			descrip=rs.getPRODESCRIPTION();
	  }		
%> 
</head>
<body>
<div style="overflow:auto">
	<%@include file="mainHeader.jsp" %>
	<!--  start nav-outer-repeat................................................................................................. START -->
	<%@include file="subHeader.jsp" %>
	 
	 <%--  <%@ include file="ProjectFilterSubHeader.jsp" %> --%>
<!-- start content-outer ........................................................................................................................START -->
<!--****************************************************************************************-->	
<!-- <div id="General" class="tabcontent  active" data-toggle="tab"> -->	
	<div id="content-outer">
<!-- start content -->
	<div id="content">
	   <table border="0" width="100%" cellpadding="0" cellspacing="0" id="content-table" >
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
	
	<form action="AddProjectServlet?action=updateProject" method="post">
	<input type="hidden" name="add" id="add" value="<%=pid%>">
				<table id="customers" align="center">
							<tr class="alt" style="border:solid;border-color:black;">
				  				<th colspan="6">Edit Project</th>
							</tr>
			 				<colgroup>
												<col style="width: 105px" />
												 <col style="width:250px;" />
												<col style="width:90px" />
											  <col style="width:200px;" />  
					            				<col style="width:80px" />    
						        					<col style="width:185px;" /> 
											</colgroup>
 							 
 							
 							 			
			<tr class="alt" style="width: 12px;height:10px;">
				<td width="20">Type :</td>
				<td>
				<select name="typeselect" style="width: 70%;">
		     	<option value="<%=type %>" selected="selected"><%=type %></option>
					<option value="It">It</option>
					<option value="Support" >Support</option>
					<option value="NewSite">New Site</option>
					<option value="Internal">Internal</option>
				</select></td>
<!--****************************** -->
				<td  width="20">Status :</td>
				<td>
		     	<select name="status" style="width: 85%;">
					<option value="<%=status %>" selected="selected"><%=status %></option>
					<option value="Open" >Open</option>
					<option value="On Hold">On Hold</option>
					<option value="Closed">Closed</option>
					<option value="Cancelled">Cancelled</option>
				</select></td>
<!--****************************** -->    		
			<td  width="20">Priority :</td>										
			<td>
     		<select name="priority" id="priority" style="width: 85%;">
				<option  value="<%=priority %>" selected="selected"><%=priority %></option>
				<option  value="Unknown" >Unknown</option>
				<option  value="High">High</option>
				<option  value="Medium">Medium</option>
				<option  value="Low">Low</option>
				<option  value="Urgent">Urgent</option>
			</select></td>    		
		</tr>
<!--****************************************************************************-->
 			 <tr class="alt">
	     		<td width="20"><font color="red">*</font>Name :</td>
	   		 	<td colspan="5">					      		
		      		<input type="text" id="fname" name="firstname" value="<%= project%>"placeholder="Project name..">
		    	</td>
		    </tr>
		    
<!--****************************************************************************************-->	
		   <tr class="alt">
	     		<td width="20">LiveUrl :</td>
	    		<td>
			      	<input type="text"  value="<%=live%>"name="LUrl" placeholder="URL:">
			    </td>
	     		<td width="20">TestUrl :</td>
	   		    <td colspan="3">
		      		<input type="text" value="<%=test%>" name="TUrl" placeholder="URL:">
		    	</td>
		   </tr> 	
<!--****************************************************************************************-->	
		    <tr class="alt">
	     		 <td width="20">Start Date :</td>
	     		 <td> 	 
					<input name="review" type="text" id="txtDate" value="<%=review%>"readonly="readonly" maxlength="10" /> 	
			     </td>
		         <td width="20">End Date :</td>
				 <td colspan="3">      		 
					<input name="design" type="text" id="txtDate1" value="<%=design %>" readonly="readonly" maxlength="10" /> 				
			    </td>
			</tr>
			
		     <%-- <tr class="alt">	     		
	     		 <td width="20">Development :</td>
	     		 <td>     		 
					<input name="devlop" type="text" id="txtDate2" value="<%=develop%>"readonly="readonly" maxlength="10" /> 
			     </td>			
			     <td width="20">SiteTest :</td>
				  <td colspan="4"> 	 
					<input name="sitetest" type="text" id="txtDate3" value="<%=site%>"readonly="readonly" maxlength="10" /> 				
				  </td>
			</tr>
					
			<tr class="alt">
	     		 <td width="20">UAT :</td>
	     		 <td colspan="5">    		 
					<input name="uat" type="text" id="txtDate4" value="<%=uat%>"readonly="readonly" maxlength="10" /> 		
				</td>
			</tr> --%>
<!--****************************************************************************************-->				
		   <tr class="alt">
     		 <td>Description :</td>
			 <td colspan="5">
		      		<textarea  value="<%=descrip %>" name="post-text" cols="35" rows="6" tabindex="101" data-wz-state="256" data-min-length="" placeholder="Project Description.."><%=descrip %></textarea>
		    	</td>
		    </tr>
		    
		     <tr class="alt"><td>&nbsp;</td><td colspan="5">
				 <input type="submit"  value="Save" class="myButton">  
				 &nbsp;&nbsp; &nbsp;  <input type="reset" value="Close" class="myButton" onclick="window.document.location.href='ProFilter.jsp'">
			</td></tr>
 			 <tr class="alt">
    			<td colspan="6" align="center">
    			<input type="reset" value="AddTask"  class="myButton" onclick="window.document.location.href='AddTask.jsp'">
    			</td></tr>
		</table>        
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
</div>
 </body>
</html>