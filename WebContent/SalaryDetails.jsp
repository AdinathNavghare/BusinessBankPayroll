<%@page import="payroll.DAO.EmpOffHandler"%>
<%@page import="payroll.Model.EmpOffBean"%>
<%@page import="payroll.Model.TranBean"%>
<%@page import="payroll.DAO.TranHandler"%>
<%@page import="payroll.Model.EmployeeBean"%>
<%@page import="payroll.DAO.EmployeeHandler"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy DTS3</title>
<link rel="stylesheet" href="css/screen.css" type="text/css"
	media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />

<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<style type="text/css">

.ac_results {
	padding: 0px;
	border: 1px solid #cacaca;
	background-color: #f5f6fa;
	overflow: hidden;
	width: 23% !important;
}

</style>
<script type="text/javascript">

<%
String action = request.getParameter("action")==null?"":request.getParameter("action");
ArrayList<TranBean> result = new ArrayList<TranBean>();
EmpOffHandler ofh = new EmpOffHandler();
String proj = null;
String date = null;
int range = 0;
int count = 1;
String dt[] = null;
if(action.equalsIgnoreCase("details")) {
	date = request.getParameter("date");
	range = Integer.parseInt(request.getParameter("rng"));
	proj = request.getParameter("proj");
	if(proj.equalsIgnoreCase("all")){
		result = TranHandler.getReleaseSal(date,proj,range);	
	}else{
		String[] prno = proj.split(":");
		result = TranHandler.getReleaseSal(date,prno[3],range);
	}
	dt = date.split("-");
}

%>
var xmlhttp;
var url="";
if(window.XMLHttpRequest)
{
	xmlhttp=new XMLHttpRequest;
}
else //if(window.ActivXObject)
{   
	xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
}

function getDetails()
{
	var date=document.getElementById('date').value;
	var proj=document.getElementById('projlist').value;
	var range=document.getElementById('salrange').value;
	if(date==null || date==""){
		alert("Please select Month !");
		return false;
	}else if(proj==null || proj==""){
		alert("Please select BRANCH !");
		return false;
	}else {
		proj=proj.replace(/ & /g," and ");
		window.location.href = "?action=details&proj="+proj+"&date="+date+"&rng="+range;	
	}
}
function change(empno)
{
	var date=document.getElementById('date').value;
	var proj=document.getElementById('projlist').value;
	var range=document.getElementById('salrange').value;
	proj=proj.replace(/ & /g," and ");
	//window.showModalDialog("salaryChange.jsp?eno="+empno+"&date="+date,null,"dialogWidth:860px; dialogHeight:420px; scroll:off; status:off;dialogLeft:350px;dialogTop:200px; ");
	//window.location.href="SalaryDetails.jsp?action=details&proj="+proj+"&date="+date+"&rng="+range;
	  document.getElementById("myModal").style.display = 'Block';
	  $("#myModal").load("salaryChange.jsp?eno="+empno+"&date="+date+"&action=details&proj="+proj+"&rng="+range);
	  $("#myModal").fadeTo('slow', 0.9);
	  parent_disable();
	}
function validate() {
	
	var checkboxes = document.getElementsByName('chk');
	var vals = "";
	for (var i=0, n=checkboxes.length;i<n;i++) {
	  if (checkboxes[i].checked) 
	  {
	  vals += ","+checkboxes[i].value;
	  }
	}
	
	var cont=(vals.match(/,/g)).length;
	if(vals==""||vals==null) {
		alert("No Employee(s) Selected !");
		return false;
	} else {
		var chk = confirm("Are you sure to Release ?");
		if(chk==true) {	
			var chk = confirm("Are you sure to Release "+cont+" Employees ?");
			if(chk==true) {	
				return true;	
			} else {
				return false;
			}	
		} else {
			return false;
		}
	}
}

var checkflag = "false";
function check(field) {
  if (checkflag == "false") {
    for (var i = 0; i < field.length; i++) {
      field[i].checked = true;
    }
    checkflag = "true";
    return "Uncheck All";
  } else {
    for (var i = 0; i < field.length; i++) {
      field[i].checked = false;
    }
    checkflag = "false";
    return "Check All";
  }
}
function checkRelease(){
	var f = parseInt(document.getElementById("flag").value);

	
	if (f == 1) {
		alert("Salary of employee/s released successfully");
	
	}
	if(f==2){
		alert("Some problem in releasing salary");
		
		
	}
	closediv();
}
function parent_disable() 
{
	jQuery(function() {
		  $("input[type=Submit]").attr("disabled", "disabled");
		  $("input[type=button]").attr("disabled", true);
		  });

	$('.nav-outer').fadeTo("slow", 0.5).css('pointer-events', 'none');
	document.getElementById("myModal").focus();
}

function closediv()
{
	document.getElementById("myModal").innerHTML= "";
	document.getElementById("myModal").style.display = "none";
	jQuery(function(){
		  $("input[type=Submit]").removeAttr("disabled");
		  $("input[type=button]").attr("disabled", false);
	});

	$('.nav-outer').css('pointer-events', 'auto');
}
</script>
<script type="text/javascript">
jQuery(function() {
	$("#projlist").autocomplete("projlist.jsp");
});

/* jQuery(function() {
	$("input:text").blur(function(){
		if($(this).val()=="" || $(this).val()==" ")
		{
			 
			if($(this).attr('id')!="projlist")
				{					
			alert("Please enter Some value !");
			$(this.value="0.0");
				}
				
		}
		
	});
}); */
</script>
<%
 String pageName = "SalaryDetails.jsp";
try
{
	ArrayList<String> urls = (ArrayList<String>)session.getAttribute("urls");
	if(!urls.contains(pageName))
	{
		response.sendRedirect("NotAvailable.jsp");
	}
}
catch(Exception e)
{
	//response.sendRedirect("login.jsp?action=0");
} 

%>

<%

	SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
	//String dt = format.format(date);
	
	SimpleDateFormat fromformat = new SimpleDateFormat("yyyy-MM-dd");
	fromformat.setLenient(false);%>
</head>
<body style="overflow: hidden;" onload="checkRelease()" onunload="closediv()">
	<%@include file="mainHeader.jsp"%>
	<!--  start nav-outer-repeat................................................................................................. START -->
	<%@include file="subHeader.jsp"%>

	<!-- start content-outer ........................................................................................................................START -->
	<div id="content-outer" style="overflow-y: scroll; max-height: 80%;">
		<!-- start content -->
		<div id="content">

			<!--  start page-heading -->
			<div id="page-heading">
				<h1>Release Salary Details</h1>
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
							<div id="table-content" align="center">

								 <form action="TransactionServlet?action=release" method="post" onsubmit="return validate()">
									<table class="releasetbl" id="customers" border="1">
										<tr class="alt"><th colspan="8">RELEASE SALARIES</th></tr>
										<tr class="alt">
										<td align="right"><b>SELECT DATE :</b></td>
										<% if (action.equalsIgnoreCase("details")) {%>
											<td><input name="date" id="date" type="text" value="<%=date %>" readonly="readonly" size="10" onfocus="getDetails()">&nbsp;<img
												src="images/cal.gif" align="absmiddle"
												style="vertical-align: middle; cursor: pointer;"
												onClick="javascript:NewCssCal('date', 'ddmmmyyyy')" /></td>
										<% } else { %>
											<td><input name="date" id="date" type="text" value="" readonly="readonly" size="10" onfocus="getDetails()">&nbsp;<img
												src="images/cal.gif" align="absmiddle"
												style="vertical-align: middle; cursor: pointer;"
												onClick="javascript:NewCssCal('date', 'ddmmmyyyy')" /></td>
										<% } %>
										<td align="right"><b>SALARY RANGE :</b></td>
										<td><select name="salrange" id="salrange" onchange="getDetails()">
										<%int rngVal = request.getParameter("rng")==null?0:Integer.parseInt(request.getParameter("rng")); 
											%>
												<option value="0" <%=(rngVal==0?"selected='selected'":"") %>> All</option>
												<option value="10" <%=(rngVal==10?"selected='selected'":"") %>> 0 - 10,000</option>
												<option value="20" <%=(rngVal==20?"selected='selected'":"") %>> 10,000 - 20,000</option>
												<option value="30" <%=(rngVal==30?"selected='selected'":"") %>> 20,000 - 30,000</option>
												<option value="40" <%=(rngVal==40?"selected='selected'":"") %>> 30,000 - 40,000</option>
												<option value="50" <%=(rngVal==50?"selected='selected'":"") %>> 40,000 - 50,000</option>
												<option value="51" <%=(rngVal==51?"selected='selected'":"") %>> > 50,000</option>
													
											</select>
										</td>
										<td><b>BRANCH :</b></td>
										<td class="branchtd" colspan="3">
											<input type="text" id="projlist" name="projlist" onClick="this.select();" placeholder="Enter All for all employee(s)" value="<%=request.getParameter("proj")==null?"":request.getParameter("proj").replaceAll(" and ", " & ") %>" title="Enter % for all BRANCH list">
											<input type="button"  class="myButton"  value="Submit" onclick="getDetails()"/>
								     	</td>
										
										</tr>
										<tr class="alt">
											<th width="90">SRNO</th>
											<th width="100">EMP CODE</th>
											<th width="300">EMPLOYEE NAME</th>
											<th width="100">TOTAL INCOME</th>
											<th width="80">TOTAL DED</th>
											<th width="110">NET SALARY</th>
											<th width="110">RELEASE</th>
											<th width="110">EDIT</th>
										</tr>
										
										<% if(action.equalsIgnoreCase("details")) {
											if (!result.isEmpty()) { %>
										<tr> <td colspan="8">
										<div style="height: 360px; width:1100px; overflow-y: scroll;">
											<table>
										<%	
												for (TranBean tb : result) {
											%>
											
												<tr class="alt" id="row">
													<td width="85" align="left"><%=count++%></td>
													<td width="105" align="left"><%=EmployeeHandler.getEmpcode(tb.getEMPNO())%></td>
													<td width="300" align="left"><%=tb.getEMPNAME() %></td>
													<td width="100" align="right"><%=tb.getINP_AMT() %></td>
													<td width="90" align="right"><%=tb.getCAL_AMT() %></td>
													<td width="110" align="right"><%=tb.getNET_AMT() %></td>
													<td width="105" align="center"><input type="checkbox" name="chk" value="<%=tb.getEMPNO()%>"/></td>
													<td width="80" align="center"><input type="button" name="edit" id="edit" value="Edit" onclick="change(<%=tb.getEMPNO()%>)"/></td>
		
												</tr>
											
										<% } %>
										 	</table></div>
										 	<br/>
										<center><input type="submit" value="Release" name="release" />&nbsp;&nbsp;&nbsp;
												<input type="button" value="Check All" onclick="this.value=check(this.form.chk)"/>&nbsp;&nbsp;&nbsp;
												<input type="reset" value="Clear All"/></center>
										</td></tr>
										<%	} else { %>
										<tr class="alt"><td align="center" colspan="8"></td></tr>	
										<tr class="alt">
											<td align="center" colspan="8" ><h3 style="color:red;">No Records Found. </h3> </td>
										</tr>	
										<% }
											}%>	
									</table>
									 
									</form>

							</div>
							<!--  end table-content  -->

							<div class="clear"></div>

						</div> <!--  end content-table-inner ............................................END  -->
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
</html>