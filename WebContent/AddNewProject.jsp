<%@page import="java.util.*"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="payroll.DAO.ConnectionManager"%>
<%@page import="payroll.DAO.AddProjectDAO"%>
<%@page import="payroll.Model.AddProjectBean"%>
<%@page import=" java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%-- <%@page errorPage="error.jsp" isErrorPage="true"%> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>&copy DTS3</title>
<link rel="stylesheet" href="css/screen1.css" type="text/css"
	media="screen" title="default" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">


 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<!--for datepicker  -->
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<%
	long millis = System.currentTimeMillis();
	java.sql.Date date = new java.sql.Date(millis);
	System.out.println(date);
%>

<script type="text/javascript">
function detect(id){
	 try
	 {
		var xmlhttp=new XMLHttpRequest();
	 	var url="";
	 	var response="";
	 	url="AddProjectServlet?action=Delete&Pid="+id;
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
						 alert("Record Deleted Successfully!!!"+ window.location.replace("AddNewProject.jsp"))
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
 </script>
<style>
* {
	box-sizing: border-box;
}

.form-popup {
	
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
<%
	String action = request.getParameter("action") != null ? request.getParameter("action") : "AddNewProject";
	System.out.println("action is " + action);
%>
<script>
function TaskValidation(){
	//alert(document.getElementById("pnm").value);

 var Projectnm=document.getElementById("fname").value;

 if(Projectnm=="")		
    {
		alert("Please Enter Project Name");
		//setTimeout("document.getElementById('pnm').focus()", 50);
		return false;

	}


 var sdate=document.getElementById("txtDate").value;
 var edate=document.getElementById("txtDate1").value;
 
   /* date sdt=Date.parse(sdate);
   date edt=Date.parse(edate);  */
 
 if (sdate > edate) 
 {
 	alert("Start date should not be Greater than End date !");
 	document.getElementById("txtDate1").value = "";
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

</script>
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
  </script>
</head>
<body >
	<%@include file="mainHeader.jsp"%>
	<!--  start nav-outer-repeat................................................................................................. START -->
	<%@include file="subHeader.jsp" %>
	<%-- <%@ include file="ProjectFilterSubHeader.jsp"%>
 --%>

	<!-- start content-outer ........................................................................................................................START -->
	<div id="content-outer" style="overflow-y:auto;height:80%;">
		<!-- start content -->
		<div id="content">
			<!--  start page-heading -->
			<div id="page-heading">
				<h3>Project</h3>
			</div>
			<!-- end page-heading -->

			<table border="0" width="100%" cellpadding="0" cellspacing="0"
				id="content-table">
				 <tr>
					<th rowspan="3" class="sized"><img
						src="images/shared/side_shadowleft.jpg" width="20" height="300"
						alt="" /></th>
					<th class="topleft"></th>
					<td id="tbl-border-top">&nbsp;</td>
					<th class="topright"></th>
					<th rowspan="3" class="sized"><img
						src="images/shared/side_shadowright.jpg" width="20" height="300"
						alt="" /></th>
				</tr> 
				<tr>
					<td id="tbl-border-left"></td>
					<td>
						<!--  start content-table-inner ...................................................................... START -->
						<div id="content-table-inner">

							<!--  start table-content  -->
							<div id="table-content">
								<center>
									<form action="AddProjectServlet?action=addProject"
										method="post" ENCTYPE="multipart/form-data" onsubmit="return TaskValidation()">
										<table id="customers" align="center">
											<tr class="alt" style="border:solid;border-color:black; ">
												<th colspan="6" >New Project</th>
											</tr>
											<colgroup>
												<col style="width: 105px" />
												 <col style="width:250px;" />
												<col style="width:90px" />
											  <col style="width:200px;" />  
					            				<col style="width:80px" />    
						        					<col style="width:185px;" /> 
											</colgroup>
											<!--****************************************************************************-->
											<tr class="alt" style="width: 12px;height:10px;">
												<td width="20">Type :</td>
												<td><select name="typeselect" id="ts" style="width: 70%;">
														<option value="It">it</option>
														<option value="Support" selected="selected">Support</option>
														<option value="NewSite">New Site</option>
														<option value="Internal">Internal</option>
												</select></td>
												<!--****************************************************************************-->
												<td width="20">Status :</td>
												<td><select name="status" id="stat" style="width: 85%;">
														<option value="Open" selected="selected">Open</option>
														<option value="OnHold">On Hold</option>
														<option value="Closed">Closed</option>
														<option value="Cancelled">Cancelled</option>
												</select></td>
												<!--****************************************************************************-->
												<td width="20">Priority :</td>
												<td><select name="priority" id="priority" style="width: 85%;">
														<option value="Unknown" selected="selected">Unknown</option>
														<option value="High">High</option>
														<option value="Medium">Medium</option>
														<option value="Low">Low</option>
														<option value="Urgent">Urgent</option>
												</select></td>
											</tr>
											<!--****************************************************************************-->
											<tr class="alt" style="width: 12px;height:12px;">
												<td width="40"><font  color="red">*</font>Name :</td>
												<td colspan="5"><input type="text" id="fname"
													name="firstname" placeholder="Project name..">
												</td>
											</tr>
											<!--****************************************************************************-->
											<tr class="alt" style="width: 12px;height:10px;">
												<td width="20">LiveUrl :</td>
												<td><input type="text" name="LUrl" placeholder="URL:">
												</td>

												<td width="20">TestUrl :</td>
												<td colspan="4"><input type="text" name="TUrl"
													placeholder="URL:"></td>
											</tr>
											<!--****************************************************************************-->
											<tr class="alt" style="width: 12px;height:10px;">
												<td width="20">Start Date:</td>
												<td><input type='text' class='datepick' id='txtDate'
													name="review" readonly="readonly"></td>

												<td width="20">End Date :</td>
												<td colspan="4"><input type='text' class='datepick'
													id='txtDate1' name="design" readonly="readonly"></td>
											</tr>
											<!--****************************************************************************-->
											
											 <tr class="alt" style="width: 12px;height:10px;">
			     		 					  <td >Description :</td>
						 					  <td colspan="6" >
			      		 						<textarea   name="post-text" cols="35" rows="6" tabindex="101" data-wz-state="256" data-min-length="" 
			      								placeholder="Project Description.."></textarea>
			  							    </tr>
											<!--****************************************************************************-->
											<tr class="alt">
												<td>&nbsp;</td>
												<td colspan="5"><input type="submit" id="btn1"
													value="Save" class="myButton"> &nbsp;&nbsp; &nbsp;
													<input type="reset" id="btn2" value="Close"
													onclick="window.document.location.href='ProFilter.jsp'"
													class="myButton"></td>
											</tr>


											<tr class="alt">
												<td colspan="3" align="center"><input type="reset"
													id="btn2" value="AddTask" class="myButton"
													onclick="window.document.location.href='AddTask.jsp'">
												</td>
												<td colspan="4" align="center"><input type="button"
													id="btn2" value="Display Records" class="myButton"
													onclick="openForm()"></td>
											</tr>
											<!--****************************************************************************-->
										</table>
									</form>
								</center>
							</div>
							<!--  end table-content  -->
							<div class="clear"></div>
						</div> <!--  end content-table-inner ............................................END  -->
					</td>
					<!-- <td id="tbl-border-right"></td> -->
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


	<!-- new div -->
	<div class="form-popup" id="myForm" align="center">

		<form action="#" class="form-container">
			<table id="customers" align="center" width="90%" class="tablecontainer">
			<tbody>
				<!--style="font-size: 12;"  -->
				<tr ><td colspan="10" align="center" >All Information</td></tr>
				<tr style="border:solid;   border-color: black;">
					<th width="75" bgcolor="1F5FA7">Action</th>
					<th width="75">Id</th>
					<th width="95">Priority</th>
					<th width="195">Name</th>
					<th width="195">Status</th>
					<th width="95">Type</th>
					<th width="180">Start Date</th>
				</tr>
				</tbody>
			</table>
			<div style="overflow-y: scroll; height: 120px;width:900px;" >
				<table id="customers"  align="center" class="tablecontainer">
					<tr class="alt">
						<%
							ArrayList<AddProjectBean> result = new ArrayList<AddProjectBean>();
							AddProjectDAO adao = new AddProjectDAO();
							result = adao.getProjectDetail();
							for (AddProjectBean rs : result) {
						%>
					
					<tr>
						<td width="98" align="center">
						<a	class="btn btn-default btn-xs purple"
						onclick="detect(<%=rs.getPROID()%>)"><i class="fa fa-trash-o"></i></a>
							<a href="#" class="btn btn-default btn-xs purple"
							onclick="window.document.location.href='EditProject.jsp?Pid=<%=rs.getPROID()%>'"><i
								class="fa fa-edit"></i></a></td>
						</td>
						<td width="79" align="center"><%=rs.getPROID()%></td>
						<td width="95" align="center"><%=rs.getPROPRIORITY()%></td>
						<td width="185" align="center"><%=rs.getPRONAME()%></td>
						<td width="195" align="center"><%=rs.getPROSTATUS()%></td>
						<td width="105" align="center"><%=rs.getPROTYPE()%></td>
						<td width="180" align="center"><span title="1547287092"></span><%=rs.getREVIEWDATE()%></td>
					</tr>
					<%
						}
					%>
				</table>
			</div>
		</form>
	</div>
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