<%@page import="java.sql.Date"%>
<%@page import="payroll.Model.AddTaskBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.time.YearMonth"%>
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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy DTS3</title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />

 <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.5/css/bootstrap.min.css">
 <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-multiselect/0.9.15/css/bootstrap-multiselect.css" />
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script> 
 <script src="https://code.jquery.com/jquery-1.10.2.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.css">
  
<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js"></script> 

<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<!-- <script src="https://code.jquery.com/jquery-1.12.4.js"></script> -->
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>


  
  <style type="text/css">
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

	    $("#startdate,#duedate").datepicker({
	    	
	        showOn: 'button',
	        buttonText: 'Show Date',
	       /*  buttonImageOnly: true, */
	        buttonImage: 'images/cal.gif',
	        dateFormat: "dd-mm-yy"
	        	
	    });
	});  
 </script>
<%
int id=Integer.parseInt(request.getParameter("id"));
int empId=Integer.parseInt(request.getParameter("empID"));
String startDate=request.getParameter("startDate");
String endDate=request.getParameter("endDate");

ProjectTimesheetHandler timeSheetHandler=new ProjectTimesheetHandler();

%>


</head>
<body>
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
	 -->
	<!-- end page-heading -->




	
<input type="hidden" name="startDate" id="startDate" value="<%=startDate%>"/>
 <input type="hidden" name="endDate" id="endDate" value="<%=endDate%>"/>
 <input type="hidden" name="empId" id="empId" value="<%=empId%>"/>		
	<div class="tbl_user_data">
	 <table class="table dataTable no-footer customer" border="1"   id="customer" role="grid" aria-describedby="corporateLeadDataTable_info" style="width: 1478px;">
	 
	 <tfoot align="right">
		<tr>
		<th ></th>
		<th ></th>
		<th></th>
		<th></th>
		<th></th>
		<th></th>
		<th></th>
		<th></th>
		<th></th>
		<th></th>
		<th></th>
		</tr>
	</tfoot>
 	</table>
			
			
	</div>		
	
	<div class="clear">&nbsp;</div>

</div>
<!--  end content -->
<div class="clear">&nbsp;</div>
</div>
<!--  end content-outer........................................................END -->

<div class="clear">&nbsp;</div>


</body>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-multiselect/0.9.15/js/bootstrap-multiselect.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ProjectTimesheet.js"></script>
<script type="text/javascript">

/*$(document).ready(function() {
	
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
*/



/*function addRow(){
	$("#customers").append('<tr   id="myTableRow"> <td><button type="button" onclick="addRow()">Add</button> &nbsp&nbsp <button type="button" onclick="removeRow()">Remove</button></td> <td><select id="ProjectName" name="ProjectName" class="form-control ProjectNames" style="width:150px";> <option value="null" selected="selected">SELECT</option> </select> </td> <td><select id="TaskName" name="TaskName" class="form-control TaskNames" style="width:150px";> <option value="getTaskNames" selected="selected">SELECT</option> </select> </td> <td><input type="text" size="10" name="monday" id="monday"></td> <td><input type="text" size="10" name="tue" name="tue" id="tue"></td> <td><input type="text" size="10" name="wed" id="wed"></td> <td><input type="text" size="10" name="thu" id="thu"></td> <td><input type="text" size="10" name="fri" id="fri"></td> <td><input type="text" size="10" name="sat" id="sat"></td> <td><input type="text" size="10"name="sun" id="sun"></td> </tr>');
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
} */



	

</script>

</html>