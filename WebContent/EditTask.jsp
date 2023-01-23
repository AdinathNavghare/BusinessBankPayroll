<%@page import="payroll.DAO.AddTaskDao"%>
<%@page import="payroll.Model.AddTaskBean"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager" %>
<%@page import="java.sql.PreparedStatement" %>
<%@page import="java.sql.SQLException" %>
<%@page import="java.sql.Statement" %>
<%@page import="java.sql.ResultSet" %>

<%@page import="payroll.DAO.ConnectionManager"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy DTS3</title>

<link rel="stylesheet" href="css/screen1.css" type="text/css" media="screen" title="default" />
<script type="text/javascript" src="js/datetimepicker.js"></script>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  
 <style>

#startdate { position:relative;; height:20px; } 
#duedate { position:relative;; height:20px; } 
#progress { position:relative;; width:140px; }	
.topics tr{height : 30px;}	

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
 <script>
 $(document).ready(function() {

	    $("#startdate,#duedate").datepicker({
	    	
	        showOn: 'button',
	        buttonText: 'Show Date',
	       /*  buttonImageOnly: true, */
	        buttonImage: 'images/cal.gif',
	        dateFormat: "yy-mm-dd"
	        	
	    });
	});  
 
 /* function parseMyDateForTsk(s) {
		
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
	 
	 var sdate=document.getElementById("startdate").value;
	 var edate=document.getElementById("duedate").value;
	 
	 sdate=parseMyDateForTsk(sdate);
	 edate=parseMyDateForTsk(edate);
	 
	 if (sdate > edate) 
	 {
	 	alert("Start date should not be Greater than End date !");
	 	document.getElementById("duedate").value = "";
	 	return false;
	 }

}
 
/*  $(document).ready(function(){
	   
	    $(".editableBox").change(function(){         
	        $(".taskTextBox").val($(".editableBox option:selected").html());
	    });
	}); */
 
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
 


/* css for subtask to make selected option is editable  */

.dropdown {
    position: relative;
    width: 200px;
}
.dropdown select
{
    width: 100%;
}
.dropdown > * {
    box-sizing: border-box;
    height: 1.5em;
}
.dropdown select {
}
.dropdown input {
    position: absolute;
    width: calc(100% - 20px);
}


</style>

<!-- <script type="text/javascript">

 $(function(){
	 $("#checkboxSelectCombo").igCombo({
         width: 300,
         dataSource: colors,
         textKey: "Name",
         valueKey: "Name",
         multiSelection: {
             enabled: true,
             showCheckboxes: true
         },
         dropDownOrientation: "bottom"
     });
 }
</script> 
 -->
<%

int tid=Integer.parseInt(request.getParameter("Tid"));
int pid=Integer.parseInt(request.getParameter("Pid"));
System.out.println("ProjectId : "+pid); 
String pname=request.getParameter("name");
String project=null,type=null,name=null,status=null,priority=null,label=null,discrib=null,created=null,assign=null;
String estitime=null,taskstartdt=null,duedt=null,subtask=null;
int prog=0;

String t[]=null;
String []a=new String[500];
%>
<%
		
   		 ArrayList<AddTaskBean> result = new ArrayList<AddTaskBean>();
		  AddTaskDao adao = new AddTaskDao();
			result=adao.getTaskEditDetail(tid);
	  
			  for(AddTaskBean rs1 : result)
			  {	
				  //project= rs1.getPROJECT_NAME();
					type=rs1.getTYPE();
					name=rs1.getNAME();
					status=rs1.getSTATUS();
					priority=rs1.getPRIORITY();
					
					discrib=rs1.getDISCRIPTION();
					//created=rs.getString("CREATED_BY");
					//assign=rs.getString("ASSIGNED_TO");
					
				     estitime= rs1.getESTIMATED_TIME();
					 taskstartdt=rs1.getSTART_DATE();
					 duedt=rs1.getDUE_DATE();
				     
     }
%>
<%

ArrayList<AddTaskBean> result1 = new ArrayList<AddTaskBean>();
AddTaskDao adaAddTaskDao = new AddTaskDao();
	result=adao.getSubTaskEditDetail(tid);

	  for(AddTaskBean rs2 : result)
	  {	
		 
			//type=rs1.getTYPE();
			 subtask= rs2.getSUBTASK();
	  }
%>
</head>
<body style="overflow: hidden">
<%@include file="mainHeader.jsp" %>
	 <!--  start nav-outer-repeat................................................................................................. START -->
	<%-- <%@include file="subHeader.jsp" %> --%>
	
	
 
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
		<!-- ...... start content-table-inner ...................................................................... START --> 
			
	<div id="content-table-inner">
		<!--  start table-content  -->
		
		  <div id="table-content" align="center"> 
			
	<form action="AddTaskServlet?action=ShowTask" method="post" onsubmit="return TaskValidation()">
		 <input type="hidden" id="task" name="task" value="<%=tid%>">
		 <input type="hidden" id="proid" name="proid" value="<%=pid%>"> 
		  <input type="hidden" id="pronm" name="pronm" value="<%=pname%>">
		<%--  <input type="hidden" id="subtsk" name="subtsk" value="<%=Sub_ID>"> --%>
		<table id="customers" align="center">
			<tr class="alt" style="border:solid;border-color:black;">
				  <th colspan="4">Edit Task</th>
			</tr>
			
			       <colgroup>
						     <col style="width:20%" />
						     <col style="width:35%" /> 
						     <col style="width:20%" />
						     <col style="width:35%" /> 
						     <!-- <col style="width: 10%" /> -->
						      
 				  </colgroup>
			
	
			<%

Connection con1=null;
con1 = ConnectionManager.getConnection();
ResultSet rs1,rs2;
Statement stmt1 = con1.createStatement();
Statement stmt2 = con1.createStatement();


try
{
String str1="select * from Task where TASK_ID="+tid;
		 rs1=stmt1.executeQuery(str1);
		 
	 while(rs1.next()){
			
	project= rs1.getString("PROJECT_NAME");
	type=rs1.getString("TASK_TYPE");
	name=rs1.getString("TASK_NAME");
	status=rs1.getString("TASK_STATUS");
	priority=rs1.getString("TASK_PRIORITY");
	
	discrib=rs1.getString("DISCRIPTION");
	//created=rs.getString("CREATED_BY");
	//assign=rs.getString("ASSIGNED_TO");
	
     estitime= rs1.getString("ESTIMATED_TIME");
	 taskstartdt=rs1.getString("TASK_START_DATE");
	 duedt=rs1.getString("DUE_DATE");
     //prog=Integer.parseInt(rs.getString("PROGRESS"));

     }
				
		 rs1.close();
		 stmt1.close();
		 
		 String str2="select * from SubTask where TASK_ID="+tid;
		 rs2=stmt2.executeQuery(str2);
		 
	 while(rs2.next())
	 {			
	       subtask= rs2.getString("SubTName");
	 } 
	 rs1.close();
	 stmt1.close();
	 con1.close();
  
}
 catch(Exception e)
 {
	 e.printStackTrace();
 }

%>
			
			<tr class="alt"><%-- <td>Project Name :</td>
				<td><input type="text" id="pnm" name="pnm" value="<%=project%>" class="form control"></td> --%>
			   

               <td style="min-width:160px">Task Name :</td>
				<td colspan="3"><input type="text" id="name" name="name" value="<%=name%>" class="form control"></td>
			   
			</tr>

           <tr class="alt"><td>Type :</td>
				<td>
					<select id="typeid" name="type" value="<%=type%>" class="form-control" style="width:155px";>
					    <option value="<%=type%>" selected="selected"><%=type%></option>
						<option value="Change Priority Rate(Hourly rate($ 25.00))">Change Priority Rate(Hourly rate($ 25.00))</option>
						<option value="Defects(Hourly rate($ 0.00))">Defects(Hourly rate($ 0.00))</option>
						<option value="Changes(Hourly rate($ 15.00))">Changes(Hourly rate($ 15.00))</option>
					</select>
				</td>
          
            <td style="min-width:160px"> Status :</td>
				<td>
                   <select id="status" name="status" value="<%=status%>">
					<option value="<%=status%>" selected="selected"><%=status%></option>
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
				
          <tr class="alt"><td>Priority :</td>
				<td>
				      <select id="priority" name="priority" value="<%=priority%>" class="form-control" style="width:155px";>
					<option value="<%=priority%>" selected="selected"><%=priority%></option>
						<option value="Unknown">Unknown</option>
						<option value="Low">Low</option>
						<option value="Medium">Medium</option>
						<option value="High">High</option>
						<option value="Urgent">Urgent</option>
					</select>
				</td>
             <td>Estimated Time :</td>
					<td colspan="3">
						<input type="text" id="estiTime" name="estiTime" value="<%=estitime%>" class="form control">
				   </td>
				   
          
		</tr>
		
                
                <tr class="alt"><td>Start Date :</td>
					<td><input name="startdate" type="text" id="startdate" value=<%=taskstartdt %>
							readonly="readonly" maxlength="10" />
	                </td>
	                 <!-- <input name="startdate" type="text"  readonly="readonly" id="startdate" value=""  maxlength="10" />&nbsp;
								<img src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
								onClick="javascript:NewCssCal('startdate', 'ddmmmyyyy')" /> -->
                
                <td>Due Date :</td>
					<td>
						<input name="duedate" type="text" id="duedate" value="<%=duedt %>"
							readonly="readonly" maxlength="10" />
					</td>
					<!-- <input type='text' name="duedate" size="15" id="duedate" onBlur="if(value=='')" readonly="readonly">&nbsp;
								<img src="images/cal.gif" align="middle" style="vertical-align:middle; cursor:pointer;" 
										onClick="javascript:NewCssCal('duedate', 'ddmmmyyyy')" ></td> -->
									 
                </tr>
				<tr class="alt">
				    <td>Description :</td>
					<td colspan="3"><textarea rows="2" cols="38" type="text" name="description" id="description" value="<%=discrib%>"><%=discrib%></textarea></td>
				</tr>
				
				<tr class="alt"><td>&nbsp;</td><td colspan="3">
			              <input type="submit" value="Update" name="Save" class="myButton">&nbsp;&nbsp;
						  <!-- <a href="AddTask.jsp" input type="button" value="cancel">&nbsp;&nbsp; -->
						  <a href="AddTask.jsp"><input onclick="this.parentNode.href=AddTask.jsp;" type="button" value="Cancel" name="cancel" class="myButton"/></a>
			</td></tr>
	    
		  </table>
 </form>

<!-- <div></div>

</div>	
 end table-content 
			<div class="clear"></div>
		</div>
		 end content-table-inner ............................................END 
		</td>
		<td id="tbl-border-right"></td>
	</tr>
	<tr>
		<th class="sized bottomleft"></th>
		<td id="tbl-border-bottom">&nbsp;</td>
		<th class="sized bottomright"></th>
	</tr>-->
	</table>
	<div class="clear">&nbsp;</div>


<!--  end content -->
<div class="clear">&nbsp;</div>

 <!-- end content-outer........................................................END -->

<div class="clear">&nbsp;</div>
 

<script>
		function opentab(evt, tabName) {
		  var i, tabcontent, tablinks;
		  tabcontent = document.getElementsByClassName("tabcontent");
		  for (i = 0; i < tabcontent.length; i++) {
		    tabcontent[i].style.display = "none";
		  }
		  tablinks = document.getElementsByClassName("tablinks");
		  for (i = 0; i < tablinks.length; i++) {
		    tablinks[i].className = tablinks[i].className.replace(" active", "");
		  }
		  document.getElementById(tabName).style.display = "block";
		  evt.currentTarget.className += " active";
		}
</script>
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