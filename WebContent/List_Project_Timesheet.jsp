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
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.css">
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js"></script>
<style type="text/css">
   /*----------for checkbox------- */
/*
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

 */

/* modal pop up */
/* The Modal (background) */
.modal {
	display: none; /* Hidden by default */
	position: fixed; /* Stay in place */
	z-index: 1; /* Sit on top */
	padding-top: 100px; /* Location of the box */
	left: 0;
	top: 0;
	width: 100%; /* Full width */
	height: 100%; /* Full height */
	overflow: auto; /* Enable scroll if needed */
	background-color: rgb(0, 0, 0); /* Fallback color */
	background-color: rgba(0, 0, 0, 0.4); /* Black w/ opacity */
}

/* Modal Content */
.modal-content {
	position: relative;
	background-color: #fefefe;
	margin: auto;
	padding: 0;
	border: 1px solid #888;
	width: 80%;
	box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0
		rgba(0, 0, 0, 0.19);
	-webkit-animation-name: animatetop;
	-webkit-animation-duration: 0.4s;
	animation-name: animatetop;
	animation-duration: 0.4s
}

/* Add Animation */
@
-webkit-keyframes animatetop {
	from {top: -300px;
	opacity: 0
}

to {
	top: 0;
	opacity: 1
}

}
@
keyframes animatetop {
	from {top: -300px;
	opacity: 0
}

to {
	top: 0;
	opacity: 1
}

}

/* The Close Button */
.close {
	color: white;
	float: right;
	font-size: 28px;
	font-weight: bold;
}

.close:hover, .close:focus {
	color: #000;
	text-decoration: none;
	cursor: pointer;
}

.modal-header1 {
	padding: 2px 16px;
	background-color: #1f2326;
	color: white;
}

.modal-body {
	padding: 2px 16px;
}

.modal-footer {
	padding: 2px 16px;
	background-color: #1f2326;
	color: white;
}


/* #startdate { position:relative;; height:20px; } 
#duedate { position:relative;; height:20px; } 
#progress { position:relative;; width:140px; }		
.topics tr{height : 30px;}
 */
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

</head>
<body>
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<!-- <div id="page-heading">
		<h1>New Task</h1>
	</div> -->
	<!-- end page-heading -->

<div align="center">
<tr class="alt"><td>&nbsp;</td><td colspan="3">
				
				
           <!-- <a  href="" class="btn btn-success btn-sm" style="position: absolute; right: 150px"><i class="fa fa-fw fa-plus"></i>Upload Excel</a> -->
					<button type="button" onclick="upload();" id="btnSubmit"
						style="width: 182px; font-weight: 700; font-size: 14px; position: absolute; right: 209px"></i>Add Time sheet (Weekly) </button>
				</td></tr>
</div>


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
			
			
			<form action="AddTaskServlet?action=addtask" name="taskform" method="post">
			
			<table id="customers" align="center">

			
			</table>        

			</form>
		<div id="uploadmyModal" class="modal">
		<!-- Modal content -->
		<div class="modal-content" style="width: 500px">
			<div style="height:20px;background-color: #1f2326;">
				<span class="close terminate" onclick="document.getElementById('uploadmyModal').style.display='none'"
					style="margin-right: 12px; color: #00f1c5; font-size: 23px;">x</span>
				 <h2 class="modal-title"
								style="margin-left: 20px;  color: #00f1c5;">Add Time sheet in week</h2>
			</div>
			
			<div class="modal-body">
					<form action="ProjectTimesheet" method="GET"  style="height: 100px; ">
					<input type="hidden" name="action" id="action" value="addSheet"/>
					<input type="hidden" name="empID" id="empID" value="2" />
					
					<select id="MonthNames" name="MonthNames"  class="form-control ProjectNames" style="width:150px;" onchange="getWeekStartDates(this);">
								<option value="abc" selected="selected">SELECT</option>
								<option value="JANUARY" >JANUARY</option>
								<option value="FEBRUARY" >FEBRUARY</option>
								<option value="MARCH" >MARCH</option>
								<option value="APRIL" >APRIL</option>
								<option value="MAY" >MAY</option>
								<option value="JUNE" >JUNE</option>
								<option value="JULY">JULY</option>
								<option value="AUGUST" >AUGUST</option>
								<option value="SEPTEMBER" >SEPTEMBER</option>
								<option value="OCTOBER" >OCTOBER</option>
								<option value="NOVEMBER">NOVEMBER</option>
								<option value="DECEMBER">DECEMBER</option>

					</select>
					
					<select id="weekStartDate" name="weekStartDate" class="form-control weekStartDate" style="width:150px";>
								<option value="getProjectNames" selected="selected">SELECT Date</option>
								
								
					</select>
					
					<input type="submit"  value="Add Sheet" style="height:24px;width:75px;" />
					
					</form>
				</div>
			
		</div>

	</div>
			
			
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
</body>

<script type="text/javascript">
//Get the modal
var uploadmodal = document.getElementById('uploadmyModal');
$(document).ready(function() {
	$("#customers").DataTable(
		{
			
			"bFilter" : false,
			"destroy" : true,
			"bPaginate": false,
			"bInfo": false, 
			"ajax" : {
				"url" : "ProjectTimesheet",
				"type": "GET",
				"contentType" : "application/json; charset=utf-8",
				 "dataType" : "json",
				"data": function (d) {
        	  		return $.extend({}, d,{"action":"listWeekTimesheet","empID":"2"});
	        	  },
	        	  
			},
			"aoColumns" : getColumns(),
			"rowId" : 'id'
		});
});

function getColumns(){
	var colArray = new Array();
	
	var id1 = new Object();
	id1.mDataProp = "SNo", 
	id1.title="Sr NO. ",
	id1.sClass = "alignCenter";
	id1.bSortable = false;
	id1.width="25%";
	id1.mRender = function(obj, data, type, row) {
		return "<span style='white-space:nowrap'>" + type.id + "</span>";
		
	};
	colArray.push(id1);
	
	var startDate = new Object();
	startDate.mDataProp = "startDate", 
	startDate.sClass = "alignCenter";
	startDate.title="Start Date",
	startDate.width="25%";
	startDate.bSortable = false;
	startDate.mRender = function(obj, data, type, row) {
	
		return "<span style='white-space:nowrap'>" + type.startDate + "</span>";
		
		
	};
	colArray.push(startDate);
	
	var EndDate = new Object();
	EndDate.mDataProp = "EndDate", 
	EndDate.title= "End Date", 
	EndDate.sClass = "alignCenter";
	EndDate.width="25%";
	EndDate.bSortable = false;
	EndDate.mRender = function(obj, data, type, row) {
		
		return "<span style='white-space:nowrap'>" +type.EndDate + "</span>";
		
		
	};
	colArray.push(EndDate);
	

	var editLinkCol = new Object();
	editLinkCol.mDataProp = null,
	editLinkCol.title="Action",
	editLinkCol.sClass = "alignCenter";
	editLinkCol.bSortable = false;
	editLinkCol.mRender = function(obj, data, type, row) {
		return '<a href="Project_TimeSheet.jsp?id=' +type.id+ '&empID='+type.EmployeeId+'&startDate='+type.startDate +'&endDate='+type.EndDate+'" </a>Go To';
	};
	colArray.push(editLinkCol);

	return colArray;
}


function upload() {
	uploadmodal.style.display = "block";
}

function getWeekStartDates(elem){
	var e = document.getElementById("MonthNames");
	var monthName = e.options[e.selectedIndex].value;
	
	$.ajax({
		url : 'ProjectTimesheet',
		data: 'action=getWeekStartDates&monthName='+monthName,
		success : function(responseText) {
			var data = JSON.parse(responseText);
			var optionHtml = ' <option value="0" disabled="disabled"  selected>--Select Date--</option>';
			console.log(data);
			$.each(data, function(index, item)
			        {
				      optionHtml += "<option>" + item + "</option>";
			        });
			

			$('.weekStartDate').append(optionHtml);
			$('.weekStartDate').html(optionHtml); 
		}
	});
	
}

</script>
</html>