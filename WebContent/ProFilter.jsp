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
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<script src="js/sortTable.js"></script>
<script src="js/sort.js"></script>


<style>
.sidenav {
  height: inherit;
  width: 200px;
  position: absolute;
  z-index: 1;
  margin-top:105px;
  top: 0;
  left: 0;
  background-color: #1F5FA7;
  overflow:absolu;
  border-top-style:groove;
  padding-top: 20px;
}

.sidenav a {
  padding: 6px 8px 6px 16px;
  text-decoration: none;
  
  color: white;
  display: block;
}

.sidenav a:hover {
  color: #f1f1f1;
}

.main {
  margin-left: 210px; /* Same as the width of the sidenav */
  margin-top: 80px;
  font-size: 14px; /* Increased text to enable scrolling */
   height: 52%;
  border-bottom: 0.5px solid lightgrey;
   border-top: 0.5px solid lightgrey;
    border-left: 0.5px solid lightgrey;
     border-right: 0.5px solid lightgrey;
 
}

@media screen and (max-height: 450px) {
  .sidenav {padding-top: 15px;}
  .sidenav a {font-size: 18px;}
}
.accordion {
  background-color: #1F5FA7;
  color: white;
  cursor: pointer;
  padding: 8px;
  width: 80%;
  border: none;
  text-align:left;
  outline: none;
  font-size: 20px;
  font-family:Times new Roman;
  font-weight:300;
  
  transition: 0.4s;
}

.active, .accordion:hover {
  background-color:; 
}
.ahref
{

	font-size: 18px;
}

.panel {
     padding: 0 10px;
    display: none;
   color: white;
    text-align: center;
    background-color: royalblue;
    overflow: hidden;
}


/* My Model */

.modal {
  display: none; /* Hidden by default */
 
  z-index: 3;
  padding-top:70px; /* Location of the box */
  left: 30;
  top: 0;
 margin-top:100px;
   border: 1px groove #888;
/*  width: 100%; /*Full width */
 /*  height:200%; */ /* Full height */ 
    /* Enable scroll if needed */
  background-color: rgb(0,0,0); /* Fallback color */
  /* background-color: rgba(255,255,255,0.5); /* Black w/ opacity */ */
}

/* The Modal (background) */
.modal {
  display: none; /* Hidden by default */
  position: absolute; /* Stay in place */
  z-index: 1; /* Sit on top */
  padding-top: 10px; /* Location of the box */
  
  width:auto; /* Full width */
  height: 50%; /* Full height */
  overflow: auto; /* Enable scroll if needed */
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
  border: 1px solid white;
  width: 100%;
  height:100px;
}

/* The Close Button */
/* .close {
  color: #aaaaaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: #000;
  text-decoration: none;
  cursor: pointer;
} */


/*hover of side menu */
#mySidenav a {
  position: absolute;
  left: -80px;
  transition: 0.3s;
  padding: 15px;
  width: 100px;
  text-decoration: none;
  font-size: 20px;
  color: white;
  border-radius: 0 5px 5px 0;
}

#mySidenav a:hover {
  left: 0;
}

#about {
  top: 20px;
  background-color: #4CAF50;
}

#blog {
  top: 80px;
  background-color: #2196F3;
}

#projects {
  top: 140px;
  background-color: #f44336;
}

#contact {
  top: 200px;
  background-color: #555
}

.dive
{
	
	
    max-height: 200%;
    width: 80%;
  }
  
</style>


<link rel="stylesheet" href="css/profilt2.css">
<link rel="stylesheet" href="css/tab.css">

<link rel="stylesheet" href="css/screen1.css" type="text/css" media="screen" title="default" />
</head>
<body>
<%@include file="mainHeader.jsp"%>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp"%>


<div class="sidenav" align="left">
				<a href="#" class="accordion">Project</a>
				
				<div class="panel">
				 <a href="ProjectViewAll.jsp"class="tablinks ahref" onclick="openCity(event,'viewAllProject')">View Project</a><hr>
				 <a href="AddNewProject.jsp" class="tablinks ahref">Add Project</a><hr>
				 
				</div>
				<hr>
				<a href="#" class="accordion">Task</a>
				<div class="panel">
				 <a href="AddTask.jsp" class="tablinks ahref">Add Task</a><hr>
				 <a href="ViewTask.jsp" class="tablinks ahref">ViewAll Task</a>
																								
				</div>
					<hr>
					<a href="#" class="accordion tablinks ahref" onclick="show('viewAllEmployee')" >Group</a>
				 <div class="panel">
			<!-- <a href="#" class="tablinks"onclick="show('viewAllEmployee')">View Employee</a><hr>
				 <a href="#" class="tablinks"onclick="show('admin')">Admin</a><hr>
				 <a href="#" class="tablinks"onclick="show('developer')">Developer</a><hr>
				 <a href="#" class="tablinks"onclick="show('designer')">Designer</a><hr>
				 <a href="#" class="tablinks"onclick="show('manager')">Manager</a>-->
				 
				</div> 
					<hr>
				<a href="#" class="accordion">Report</a>
				<div class="panel">
				 
				 <a href="AddProjectReport.jsp" class="tablinks ahref" onclick="show('ProjectReport#')">Project Report</a><hr>
				 <a href="#" class="tablinks ahref" onclick="show('TaskReport#')">Task Report</a><hr>
				 <a href="Project_TimeSheet.jsp" class="tablinks ahref" onclick="show('timesheet##')">Time Sheet</a><hr>
																							
																							
																							
				</div>
				<hr>
				
				
					  
						  
</div>
	
<div class="main dive" id="main_place">
<div id="timesheet" class="tabcontent">
<form action="AddTaskServlet?action=addtask" name="taskform" method="post">
			
			<table id="customers" align="center">
			<tr >
				  <th >Action</th>
				  <th >Project</th>
				  <th >Task</th>
				  <th >Mon</th>
				  <th >Tue</th>
				  <th >Wed</th>
				  <th >Thu</th>
				  <th >Fri</th>
				  <th >Sat</th>
				  <th >Sun</th>
				  <th >Total</th>
				  
			</tr>

            <tr >	
            <td><button type="button" onclick="addRow()">Add</button> </td>								
          	<td><select id="ProjectName" name="ProjectName" class="form-control ProjectNames" style="width:150px;">
								<option value="getProjectNames" selected="selected">SELECT</option>
									<%-- <%
									
											 try
											 {
												 Connection con1=null;
												 con1 = ConnectionManager.getConnection();
												 //Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
												 //con1 = DriverManager.getConnection(url1+serverName+":"+portNumber+";databaseName=" + databaseName,userName,password);
												 ResultSet rss;
												 Statement stm = con1.createStatement();
											    String str1="select P.ProjectName from AddProject P,Project_Allocation PA where P.ProjectId=PA.Project_Id and PA.Employee_Id=2";  // need to get employee id
													 rss=stm.executeQuery(str1);
												
											 while(rss.next())
											 {
												
												 %>	
												
						     					 <option value="<%=rss.getString("ProjectName") %>"><%=rss.getString("ProjectName") %></option> 
									     	
											   <% }  
									    		 rss.close();
												 con1.close();
										  }
										 catch(Exception e)
										 {
											 e.printStackTrace();
										 } 
					            	%> --%>
								</select> </td>
							
						          	<td><select id="TaskName" name="TaskName"  class="form-control TaskNames" style="width:150px";>
								<option value="getTaskNames" selected="selected">SELECT</option>
									<%-- <%
									
											 try
											 {
												 Connection con1=null;
												 con1 = ConnectionManager.getConnection();
												 //Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
												 //con1 = DriverManager.getConnection(url1+serverName+":"+portNumber+";databaseName=" + databaseName,userName,password);
												 ResultSet rss;
												 Statement stm = con1.createStatement();
											     String str1="select distinct Task_Name from Task";
													 rss=stm.executeQuery(str1);
												
											 while(rss.next())
											 {
												
												 %>	
												
						     					 <option value="<%=rss.getString("Task_Name") %>"><%=rss.getString("Task_Name") %></option> 
									     	
											   <% }  
									    		 rss.close();
												 con1.close();
										  }
										 catch(Exception e)
										 {
											 e.printStackTrace();
										 } 
					            	%> --%>
								</select> </td>								
							  <td><input type="text" size="10" name="monday" id="monday"></td>
							  <td><input type="text" size="10" name="tue" name="tue" id="tue"></td>
							  <td><input type="text" size="10" name="wed" id="wed"></td>
							  <td><input type="text" size="10" name="thu" id="thu"></td>
							  <td><input type="text" size="10" name="fri" id="fri"></td>
							  <td><input type="text" size="10" name="sat" id="sat"></td>
							  <td><input type="text" size="10"name="sun" id="sun"></td>	
							  <%-- <td><select id="TaskName" name="TaskName"  class="form-control TaskNames" style="width:150px";>
								<option value="getTaskNames" selected="selected">SELECT</option>
								<%
										System.out.println(ProjectTimesheetHandler.weeksInCalendar(YearMonth.now())); 
								%>
				
								</select></td> --%>
			</tr>
		
			
			</table>        

			</form>

 </div>
  <div id="ViewAllProject">
								<form>
									
									<input type="button" name="addproject"
										value="Add New Project" class="myButton nobull"
										style="float: right; width: 15%; margin-top: 30px;"
										onclick="window.document.location.href='AddNewProject.jsp'">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<input id="myInput" class="nobull" type="text"
											placeholder="Search.."
											style=" width: 20%; margin-top: 30px;padding: 5px;">
											
									
											<br><br>
									<table id="customers" class="sortable" style="text-align: center;" >
										
										<tbody style="text-align: center;" >
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
										<div  style="overflow-y:scroll;height: 230px;">
										<table id="customers" >
										<%
										
										ArrayList<AddProjectBean> proList1=new ArrayList<AddProjectBean>();
										AddProjectDAO apd=new AddProjectDAO();
										proList1=apd.viewProject();
										
										 /* Connection con = null;
											PreparedStatement pst = null;
											ResultSet rs = null;
											String query = "select * from AddProject where PCONDITION='Active'";
											try {
												con = ConnectionManager.getConnection();
												pst = con.prepareStatement(query);
												rs = pst.executeQuery();  */
												for(AddProjectBean apb:proList1)
												 {
										%>
										<tbody id="myTable">
											<tr class="alt">
										<td width="100px">&nbsp; &nbsp; &nbsp; &nbsp;<a class="btn btn-default btn-xs purple" id="tb1"
													onclick="detect(<%=apb.getPROID()%>)"><i
														class="fa fa-trash-o"></i></a> &nbsp; &nbsp;  &nbsp;
												<a href="EditProject.jsp?Pid=<%=apb.getPROID()%>"
													class="btn btn-default btn-xs purple"><i
														class="fa fa-edit"></i></a></td>
												<%-- 	<td id="proid"><a href="DeleteProjectServlet?action=viewAllProject&ProjectId=<%=rs.getInt("ProjectId") %>"  onclick="openForm(<%=rs.getInt("ProjectId") %>)"><%=rs.getString("ProjectName") %></a></td> --%>
												<td width="100px"><%=apb.getPROID()%></td>
												<td width="100px"><%=apb.getPRONAME()%></td>
												<td width="100px"><%=apb.getPROTYPE()%></td>
												<td width="100px"><%=apb.getPROSTATUS()%></td>
												<td width="100px"><%=apb.getPROPRIORITY()%></td>
												<td width="100px"><%=apb.getREVIEWDATE()%></td>
												<td width="100px"><%=apb.getSITETESTDATE()%></td>
												<td width="100px"><input type="button"
												onclick="getDetailes(<%=apb.getPROID()%>)"
													name="addproject" value="view"  class="myButton nobull">
													</td>
											</tr>
													
										</tbody>

										<%
											}
										%>
									</table>
									</div>
									<div>
									
									
									</div>
					<%-- <%
						rs.close();
							pst.close();
			
						} catch (Exception e) {
							e.printStackTrace();
						}
					%> --%>
					</form>
							</div>
				
<div class="modal "  id="myModal" align="center"  >
					<div id="modal-content">
							    <!-- <span class="close">&times;</span> -->
							<div style="background-color: #1F5FA7 ;color:white;font-size: large;;font-weight: bold;">Project Information</div>
							<table id="customers">
								
								<tbody class="form_data"  align="center" >
								
								
								</tbody>
							</table>
							
					</div>`
		</div>
			
<div id="viewAllEmployee" class="tabcontent"  align="center">
	
	
	
		<%
			ArrayList<DemoEmpBean> desiglist1=new ArrayList<DemoEmpBean>();
		DemoEmpDAO ded1=new DemoEmpDAO();
		desiglist1=ded1.getDistEmpDesignation();
			%>
	<div align="center">
		<form  action="DemoEmpInfo.jsp" method="Post" onSubmit="return TakeCustId()">
			<table>
				<tr><td>
				<!-- <input id="myInput" class="nobull" type="text"
											placeholder="Search.."
											style=" width: 20%; margin-top: 30px;padding: 5px;"> -->
					<font size="3"><b>Select Designation </b></font>&nbsp;&nbsp;
						<!-- <input class="form-control" type="text" name="EMPNO" size="41" id="EMPNO"  onclick="showHide()"
								placeholder="Enter Employee Name or Emp-Code"  title="Enter Employee Name" > -->
								
								
								<select name="desg" class="designation" id="desg" style="width:auto; height:auto; padding:6px; font-size:14px; font-weight:bold; font-style: normal; sans-serif;">
								<%
								for(DemoEmpBean debean1:desiglist1)
								{
								%>
										<option style="font-size:14px;font-style: normal;font-weight:bold; sans-serif;"><%=debean1.getEMPDESIGNATION() %></option>
								<%	
								} 
								%>
								
								</select>
 							       &nbsp;&nbsp;   <input type="submit" class="myButton" value="Submit" >
<!--  							       <input type="button" class="myButton" onclick="selectval()" value="SHOW">
 -->							  &nbsp;&nbsp; 	<a href="employee.jsp?action=addemp">&nbsp;<b>Add Employee</b></a>
 									 &nbsp;&nbsp;		<a href="AddProjectTabs.jsp">&nbsp;<b>Assign Task</b></a>
				</td></tr>
			</table>
		</form></div>
				<div align="center">
				
					
				</div>
						
				<div align="center" style="width: 1056px" >
				
				<%
				ArrayList<DemoEmpBean> emplist1=new ArrayList<DemoEmpBean>();
				DemoEmpDAO ded2=new DemoEmpDAO();
				emplist1=ded2.getAllEmpInfo();
				%>
											
				<form action="#" method="post">
					<table width="1062.19"   id="customers">
						    <tbody>
						    <tr class="alt">
						      <th>EMP NO/ID</th>
						      <th>EMP NAME</th>
						      <th>DESIGNATION</th>
						      <th >GET DETAILS</th>
						    </tr>
						    </tbody>
				    </table>
						<div style="height: 254px; overflow-y: scroll; width:1062.19" >
						<table  width="1062.19"  border="1"  height="45px" bgcolor="#CCCCCC" style="font-size:14px; text-align: center;" id="cutomers">
						<%
						for(DemoEmpBean debean2:emplist1 )
						
						{ 
						%>
						<tbody width="577">						
						<tr  style="font-size: 14px;">
								<td width="234px"  bordercolor="grey"><%=debean2.getEMPID() %></td>
								<td width="242px" ><%=debean2.getEMPNAME()%></td>
								<td width="292px"><%=debean2.getEMPDESIGNATION() %></td>
								<td >
								
								<input type="button" onclick="openForm(<%=debean2.getEMPID() %>)" class="myButton" value="View">
								</td>
								
								
						</tr></tbody>
						<%
							}
						%>
					</table>
					</div>
				</form>
			
			</div>
			
		
	
</div>


				<%-- <%@include file="ViewAllProjectReport.jsp"%> --%>
</div>
<%@include file="assign.jsp" %>
<%-- <%@include file="ProjectPriority.jsp"%>
				<%@include file="ProjectType.jsp"%> --%>
<script type="text/javascript">
function show(param_div) {
	 
    document.getElementById('main_place').innerHTML = document.getElementById(param_div).innerHTML;
  }
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
}

/* var selectVal=$('#desg').val();
			function selectval()
			{
				debugger;
				alert("You have selected the country - "+$('#desg').val() );
			var selectVal=$('#desg').val();
			} */
			</script>	
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
<script>

function getDetailes(ProjectId)
{
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
	    	  html +='<tr><th>Code</th><th> Name</th><th>Status</th><th>Type</th><th>Priority</th><th> LiveUrl</th><th>TestingUrl</th><th>Start Date</th><th>End Date</th><th>Description</th></tr>';
    		  html +=' <tr >';
    		  html +=' <td style="background-color: white;  width="50%" fon position: relative;">'+response3[0]+' </td>';
    		  html +=' <td style="background-color: white;  width="50%" fon position: relative;">'+response3[1]+' </td>';
    		  html +=' <td style="background-color: white;  width="50%" fon position: relative;">'+response3[2]+' </td>';
    		  html +=' <td style="background-color: white;  width="50%" fon position: relative;">'+response3[3]+' </td>';
    		  html +=' <td style="background-color: white;  width="50%" fon position: relative;">'+response3[4]+' </td>';
    		  html +=' <td style="background-color: white;  width="50%" fon position: relative;">'+response3[5]+' </td>';
    		  html +=' <td style="background-color: white;  width="50%" fon position: relative;">'+response3[6]+' </td>';
    		  html +=' <td style="background-color: white;  width="50%" fon position: relative;">'+response3[7]+' </td>';
    		  html +=' <td style="background-color: white;  width="50%" fon position: relative;">'+response3[8]+' </td>';
    		  html +=' <td style="background-color: white;  width="50%" fon position: relative;">'+response3[9]+' </td>';
    		  html += '<tr style="background-color: #1F5FA7; color:white; width="50%" position: relative;"><th colspan="7">Task Information</th></tr>'
    		  html +='<tr><th>Code</th><th>Name</th><th>Type</th><th>Status</th><th>Priority</th><th>Start Date</th><th>Due Date</th>';
    		  html +=' <tr>';
	    		  for(var i=10;i<n;i++)
	    		  {
	    			  if(response3[i]=='*')
	    				  {
	    				  		html +=' </tr><tr>';
	    				  }
	    			  else
	    				  {
	    				  html +=' <td style="background-color: white;  width="50%" fon position: relative;">'+response3[i]+' </td>';
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
   <script>
var acc = document.getElementsByClassName("accordion");
var i;

for (i = 0; i < acc.length; i++) {
  acc[i].addEventListener("click", function() {
    this.classList.toggle("active");
    var panel = this.nextElementSibling;
    if (panel.style.display === "block") {
      panel.style.display = "none";
    } else {
      panel.style.display = "block";
    }
  });
}
</script>
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
	}
</script>
</body>
<script type="text/javascript">

$(document).ready(function() {
	
	// get task names
		$.ajax({
			url : 'ProjectTimesheet',
			data : {
				action : $('#TaskName').val()
			},
			success : function(responseText) {
				var data = JSON.parse(responseText);
				var optionHtml = ' <option value="0" disabled="disabled"  selected>--Select Task--</option>';
				for (i = 0; i < data.length; i++) {
					//alert(data);
					optionHtml += '<option>'
							+ data[i].taskName
							+ '</option>';

				}
				

				$('.TaskNames').append(optionHtml);
				$('.TaskNames').html(optionHtml);
			}
		});
	
		var empID=2; // get from session
		// get Project  names with employee id
		$.ajax({
			url : 'ProjectTimesheet',
			data: 'action='+$('#ProjectName').val()+'&employeeId='+empID,
			success : function(responseText) {
				var data = JSON.parse(responseText);
				var optionHtml = ' <option value="0" disabled="disabled"  selected>--Select Project--</option>';
				for (i = 0; i < data.length; i++) {
					//alert(data);
					optionHtml += '<option>'
							+ data[i].projectName
							+ '</option>';

				}
				

				$('.ProjectNames').append(optionHtml);
				$('.ProjectNames').html(optionHtml);
			}
		});
	
});




function addRow(){
	$("#customers").append('<tr   id="myTableRow"> <td><button type="button" onclick="addRow()" class="myButton">Add</button> &nbsp&nbsp <button type="button" class="myButton" onclick="removeRow()" style="margin: 1px 0;">Remove</button></td> <td><select id="ProjectName" name="ProjectName" class="form-control ProjectNames" style="width:150px";> <option value="null" selected="selected">SELECT</option> </select> </td> <td><select id="TaskName" name="TaskName" class="form-control TaskNames" style="width:150px";> <option value="getTaskNames" selected="selected">SELECT</option> </select> </td> <td><input type="text" size="10" name="monday" id="monday"></td> <td><input type="text" size="10" name="tue" name="tue" id="tue"></td> <td><input type="text" size="10" name="wed" id="wed"></td> <td><input type="text" size="10" name="thu" id="thu"></td> <td><input type="text" size="10" name="fri" id="fri"></td> <td><input type="text" size="10" name="sat" id="sat"></td> <td><input type="text" size="10"name="sun" id="sun"></td> </tr>');
	$.ajax({
		url : 'ProjectTimesheet',
		data : {
			action : 'getTaskNames'
		},
		success : function(responseText) {
			var data = JSON.parse(responseText);
			var optionHtml = ' <option value="0" disabled="disabled"  selected>--Select Task--</option>';
			for (i = 0; i < data.length; i++) {
				//alert(data);
				optionHtml += '<option>'
						+ data[i].taskName
						+ '</option>';

			}
			

			$('.TaskNames').append(optionHtml);
			$('.TaskNames').html(optionHtml);
		}
	});
	
	$.ajax({
		url : 'ProjectTimesheet',
		data: 'action=getProjectNames&employeeId=2',
		success : function(responseText) {
			var data = JSON.parse(responseText);
			var optionHtml = ' <option value="0" disabled="disabled"  selected>--Select Project--</option>';
			for (i = 0; i < data.length; i++) {
				//alert(data);
				optionHtml += '<option>'
						+ data[i].projectName 
						+ '</option>';

			}
			

			$('.ProjectNames').append(optionHtml);
			$('.ProjectNames').html(optionHtml);
		}
	});
}

function removeRow(){
	$("#myTableRow").remove();
}
</script>
</html> 
