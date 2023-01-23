<%@page import="payroll.DAO.AddProjectDAO"%>
<%@page import="payroll.DAO.DemoEmpDAO"%>
<%@page import="payroll.Model.AddProjectBean"%>
<%@page import="payroll.Model.DemoEmpBean"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager" %>
<%@page import="java.sql.PreparedStatement" %>
<%@page import="java.sql.SQLException" %>
<%@page import="java.sql.Statement" %>
<%@page import="java.sql.ResultSet" %>
<%@page import="payroll.DAO.ConnectionManager"%>
<%@page %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%-- <%@page errorPage="error.jsp" isErrorPage="true"%> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="ISO-8859-1">

 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
 -->


<title>&copy DTS3</title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<!--for datePicker -->
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<!-- <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script> -->

  
<style type="text/css">
		/* Style the tab */
		*{
     box-sizing: border-box;
  }
   
  
  .tablecontainer 
  {
	  overflow-y: auto;
	  height: 100px;
	  background: #fff;
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
		
		input[type=submit]:hover {
		  background-color: #45a049;
		}
		#up{
		position:relative;
		margin-bottom:auto;
		left:5px;
		float:left;
		}	
		
		#btn3{
		position:absolute;
		margin-bottom:auto;
		padding: 8px 30px;
		border: none;
		border-radius: 4px;
		left:16%;
		float:left;
		}
		
		.topics tr{height : 30px;}
</style>
<%String[] t=new String[15];
%>
 <script>

function ForTaskList(str)
{
	//var str=document.getElementById("ProName").value;
	debugger;
	var html="";
	var xmlhttp=new XMLHttpRequest();
	 	var url="";
	 	var response2="";
	 	url="AddProjectServlet?action=ForTask&q="+str;
    if (str == "" )
    {
        document.getElementById("tskName").innerHTML = "";
        return;
    } 
    else
    { 
    	xmlhttp.onreadystatechange=function()
		{
    		
			if(xmlhttp.readyState==4)
			{	
			response2 = xmlhttp.responseText.split(",");
			if(html!="")
    		{
				var select = document.getElementById("tskName");
				var length = select.options.length;
				for (i = 0; i < length; i++) {
				  select.options[i] = null;
				}    		}
			 //$('#tskName').empty(); 
			 html='<option >select</option>';
			for(var i=0;i<response2.length;i++)
				{
				
				html +='<option >'+response2[i]+'</option>';
				
				}
            	 document.getElementById("tskName").innerHTML = html;
            	//$('.form-control1').html(html);
       		 }
    	}
    	xmlhttp.open("POST", url, true);
        xmlhttp.send(); 
	}
}


function ForSubList(str1)
{
	//alert(str1);
	var tsk=str1.split(":");
	//alert("trask id:"+tsk[0]);
	debugger;
	var html="";
	var xmlhttp=new XMLHttpRequest();
	 	var url="";
	 	var response="";
	 	url="AddProjectServlet?action=ForSubTask&q="+tsk[0];
	 	
    if (str1 == "" )
    {
        document.getElementById("subTask").innerHTML = "";
        return;
    }
    else
    { 
    	xmlhttp.onreadystatechange=function()
		{
    		
			if(xmlhttp.readyState==4)
			{	
			response = xmlhttp.responseText.split(",");
			if(html!="")
    		{
				var select = document.getElementById("subTask");
				var length = select.options.length;
				for (i = 0; i < length; i++) {
				  select.options[i] = null;
				}    		}
			 html='<option >select</option>';
			for(var i=0;i<response.length;i++)
				{
				
				html +='<option >'+response[i]+'</option>';
				
				}
            	 document.getElementById("subTask").innerHTML = html;
            	//$('.form-control1').html(html);
       		 }
    	}
    	xmlhttp.open("POST", url, true);
        xmlhttp.send(); 
	}
}
var e;
 function AssignEmp(emp)
 {
	
	 //alert(emp);
	 
	  var r=document.getElementById("subTask").value;
	 var sub=r.split(":");
		//alert("sub id:"+sub[0]);
		
		var xmlhttp=new XMLHttpRequest();
	 	var url="";
	 	var response="";
	 	var clicked=true;
	 	url="AddProjectServlet?action=addTeam&q="+sub[0]+"&empid="+emp;
	 	xmlhttp.onreadystatechange=function()
		{
			if(xmlhttp.readyState==4)
			{	
				
			response = xmlhttp.responseText;
			
			 if(response>0 )//+""+ window.location.replace("DemoTab.jsp")
				{
				 	e=document.getElementById("button"+emp).disabled=true;
			   		$('#button'+emp).css('background-color', 'darkred');   
				    alert("Employee: "+emp +"  is assign successfully!!!" );
					return false;
				}else{
					alert("Employee "+emp +" is not assign........Some Error Occure");	
				}
			}
			};
			xmlhttp.open("POST", url, true);
			xmlhttp.send();

	   			
 	}
 var response="";
 function ForDisableEmp(sub)
 {
	 debugger;
	alert(response);
	 var a=response;
	 for(var i=0;i<a.length-1;i++)
		{
			document.getElementById("button"+a[i]+"").disabled = false;
			$('#button'+a[i]).css('background-color', '#1F5FA7');
			 //$( "#myid" ).load( "AddProjectTabs.jsp #myid" );
		}
	
	
	/*  var count=1;
	 subTask.addEventListener('change', function () {
		 $( "#myid" ).load( "AddProjectTabs.jsp #myid" );
	 },false); */
	 
	 
	 var r=document.getElementById("subTask").value;
	 var sub=r.split(":");
	 var xmlhttp=new XMLHttpRequest();
	 var url="";
	 url="AddProjectServlet?action=SubToEmp&q="+sub[0];
	 xmlhttp.onreadystatechange=function()
	 {
	 		
			if(xmlhttp.readyState==4)
			{	
			response = xmlhttp.responseText.split(",");
			alert(response);
			
				// $( "#myid" ).load( "AddProjectTabs.jsp #myid" );
			
			for(var i=0;i<response.length-1;i++)
			{
				document.getElementById("button"+response[i]+"").disabled = true;
				$('#button'+response[i]).css('background-color', 'darkred');
			}
			}
			//count=0;
			
		};
			xmlhttp.open("POST", url, true);
			xmlhttp.send();		
		
 }
 
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
<%String action=request.getParameter("action")!=null?request.getParameter("action"):"AddProjectTabs";
System.out.println("action is "+action);
Connection con=null,con1=null;
PreparedStatement ps,ps1;
ResultSet rs,rs1;
String ProName=null,TaskName=null,SubTask=null;
%>

<script>
  
  $(document).ready(function()
  {
	    $("#txtDate,#txtDate1,#txtDate2,#txtDate3,#txtDate4").datepicker({
	        showOn: 'button',
	        buttonText: 'Show Date',
	        /* buttonImageOnly: true, */
	        buttonImage: 'images/cal.gif',
	        dateFormat: "dd-mm-yy"
	        	
	    });
	});
  
  
  </script>

</head>

<body style="overflow:auto">
	
	<%@include file="mainHeader.jsp" %>
	 <!--  start nav-outer-repeat................................................................................................. START -->
	<%@include file="subHeader.jsp" %>
	<%-- <%@ include file="ProjectFilterSubHeader.jsp" %> --%>
	  
<!-- start content-outer ........................................................................................................................START -->
<div id="page-heading">
		<h1>Assign Task</h1>
	</div>
<div align="center">
	 <!--  <button class="tablinks myButton" onclick="opentab(event, 'Attachment')">Attachments</button> -->
	<!--  <div id="table-content"> -->
			<center>
			 <form action="AddProjectServlet?action=addTeam"  method="post">
				<%-- <input type="hidden" value=<%=str2 %>> --%>
			 	<table id="customers" align="center" Style="border:solid;">
			 	<tr>
			 	<td>Project Name:</td>
			 	<td>
			 	<select id="ProName" name="ProName"   onchange="ForTaskList(this.value)" class="form-control2" style="width:155px;">
			 	    <option  selected="selected">Select</option>
					<%
					  ArrayList<AddProjectBean> resultproject = new ArrayList<AddProjectBean>();
					  AddProjectDAO pdao = new AddProjectDAO();
					  resultproject=pdao.getProjectName();
													          
				 			 for(AddProjectBean probean : resultproject)
							{ %>
								 <option value="<%=probean.getPROID() %>"><%=probean.getPRONAME()%></option>
							<% }
					%>
 		       </select> 
		</td>
		
		<td>Tasks:</td>
	 		<td><select id="tskName" name="tskName" onchange="ForSubList(this.value)" class="form-control1" style="width:155px";>
			<option  selected="selected">Select</option>
		</select></td>
							
		<td>SubTask:</td>
	 		<td><select id="subTask" name="subTask"  onchange="ForDisableEmp(this.value)" class="form-control3"  style="width:155px";>
	 		<option  selected="selected">Select</option>
		</select></td> 
 		
 	</tr>
	</table> 
</form>
</div>
 <!--****************************************************************************************-->	
 <!-----------------------Team tab-------------------------> 
<!--  <div id="Team" class="tabcontent" >
 --> <!-- start content-outer ........................................................................................................................START -->
<div id="content-outer">
<!-- start content -->
	<div id="content">
	<table border="0" width="100%" cellpadding="0" cellspacing="0" id="content-table" style="overflow-y:scroll; height:100px;">
		<tr>
			<th rowspan="3" class="sized"><img src="images/shared/side_shadowleft.jpg" width="20" height="300" alt=""/></th>
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
			 <form>
				<%-- <input type="hidden" value=<%=str2 %>> --%>
			 <table id="customers" align="center" Style="overflow-y:auto;">
 			<%
			ArrayList<DemoEmpBean> desiglist1=new ArrayList<DemoEmpBean>();
		DemoEmpDAO ded1=new DemoEmpDAO();
		desiglist1=ded1.getDistEmpDesignation();
			%>
	
		<form>
			<table>
				<tr><td>	<font size="3"><b>Search Designation </b></font>&nbsp;&nbsp;
				<input id="myInput" class="nobull" type="text"
											placeholder="Search.."
											style=" width: 50%; margin-top: 30px;padding: 5px;">
				</td></tr>
			</table>
		</form>
		<div align="center" style="width:1056px" >
				<%
				ArrayList<DemoEmpBean> emplist1=new ArrayList<DemoEmpBean>();
				DemoEmpDAO ded2=new DemoEmpDAO();
				emplist1=ded2.getAllEmpInfo();
				%>							
				<form action="DemoEmpServlet?action=employee" method="post">
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
						<div style="height: 254px; overflow-y: scroll; width:1062.19" id="myid" >
						<table  width="1062.19"  border="1"  height="45px" bgcolor="#CCCCCC" style="font-size:14px; text-align: center;" id="cutomers">
						<%for(DemoEmpBean debean2:emplist1 ){ %>
						<tbody width="577" id="myTable">						
						<tr class="alt" bgcolor="#FFFFFF" style="font-size: 14px;">
								<td width="190px"  bordercolor="grey"><%=debean2.getEMPID() %></td>
								<td width="260px" ><%=debean2.getEMPNAME()%></td>
								<td width="315px"><%=debean2.getEMPDESIGNATION() %></td>
								<td ><input type="button" onclick="AssignEmp(<%=debean2.getEMPID() %>)" id="button<%=debean2.getEMPID()%>" class="myButton" value="Assign">
								<input type="button" onclick="openForm(<%=debean2.getEMPID() %>)" class="myButton" value="View">
								</td>
								
						</tr></tbody>
						<%}%>
					</table>
					</div>
				</form>
			</center>
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
	</table>
	<div class="clear">&nbsp;</div>

<!--  end content -->
<div class="clear">&nbsp;</div>
</div>
<!--  end content-outer........................................................END -->

<div class="clear">&nbsp;</div>
</div>
</div>



<%@ include file="assign.jsp" %>
</body>
</html>