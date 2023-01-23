<%@page import="payroll.DAO.EmpOffHandler"%>
<%@page import="payroll.Model.EmpOffBean"%>
<%@page import="payroll.Model.extra_duty_paymentBean"%>
<%@page import="payroll.DAO.ShiftHandler"%>
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
HttpSession sess = request.getSession();
String action = request.getParameter("action")==null?"":request.getParameter("action");
ArrayList<extra_duty_paymentBean> result = new ArrayList<extra_duty_paymentBean>();
EmpOffHandler ofh = new EmpOffHandler();
ShiftHandler sh =new ShiftHandler();
String emp = null;
/* String date = null; */
int range = 0;
int count = 1;
/* String dt[] = null; */
if(action.equalsIgnoreCase("details")) {
	/* date = request.getParameter("date"); */
	emp = request.getParameter("emp");
	if(emp.equalsIgnoreCase("all")){
		result =sh.getAllExtraPayment(emp);	
	}else{
		if(emp.contains(":")){
		String empno[] = emp.split(":");
		result = sh.getAllExtraPayment(empno[2]);
		}
		else{
			result =result;
		}
	}
	sess.setAttribute("EMPLIST", result);
	/* dt = date.split("-"); */
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
	//var date=document.getElementById("date").value;
	var emp=document.getElementById("emplist").value;

	/* if(date==null || date==""){
		alert("Please select Date !");
		return false;
	}else */ if(emp==null || emp==""){
		alert("Please select Employee !");
		return false;
	}else {
		
		window.location.href = "?action=details&emp="+emp;	
	}
}
/* function change(empno)
{
	var date=document.getElementById('date').value;
	var proj=document.getElementById('projlist').value;
	var range=document.getElementById('salrange').value;
	proj=proj.replace(/ & /g," and ");
	window.showModalDialog("salaryChange.jsp?eno="+empno+"&date="+date,null,"dialogWidth:860px; dialogHeight:420px; scroll:off; status:off;dialogLeft:350px;dialogTop:200px; ");
	window.location.href="SalaryDetails.jsp?action=details&proj="+proj+"&date="+date+"&rng="+range;

	} */
	
	
/* function validate() {
	var btn = document.getElementById("act").value;
	alert(btn);
	var checkboxes = document.getElementsByName("chk");
	
	var vals = "";
	var cnt=0;
	for (var i=0;i<checkboxes.length;i++) {
		if (checkboxes[i].checked==true) 
	  {
	  vals += checkboxes[i].value+",";
	  cnt++;
	  }
	}
	
	//var cont=(vals.match(/,/g)).length;
	
	if(vals==""||vals==null||vals.length==0||cnt==0) {
		alert("Please Select The Employee  !");
		return false;
	} else {
		
		var chk = confirm("Are you sure to Post Transaction ?");
		if(chk==true) {	
			var chk = confirm("Are you sure to Post Extra Payment For "+cnt+" Employees ?");
			if(chk==true) {	
				try
				{
				
				var xmlhttp=new XMLHttpRequest();
			 	var url="";
			 	var response2="";
			 	
			 	url="ShiftServlet?action=PostExtraPayment&checkempno="+vals;

				xmlhttp.onreadystatechange=function()
				{
					if(xmlhttp.readyState==4)
					{
					
					response2 = xmlhttp.responseText;
					 if(response2=="POST")
						{
						 alert("Record Posted Sucessfully...");
						 window.location.href="extra_duty_payment.jsp";	
						} 
					}
					
				};
			 
				xmlhttp.open("POST", url, true);
				xmlhttp.send();
				}
				catch(e)
				{
					
				}
				//return true;	
			} else {
				return false;
			}	
		} else {
			return false;
		}
	}
} */

var checkflag = "false";
function check(field) {
	alert("chk"+field.length);
	alert(checkflag);
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
</script>
<script type="text/javascript">
jQuery(function() {

	$("#emplist").autocomplete("list.jsp");
	
	$("#check_all").click(function() {
		
	    $("input[name=chk]").attr('checked', true);
	    
	   
	
	});
	
	 $(".myButton").click(function(){ 
		 var val=( $(this).val() ); 
		 if(val=="Post"||val=="Delete"||val=="post"||val=="delete"){
		 var checkboxes = document.getElementsByName("chk");
			/* var date = document.getElementById("date").value; */
			var vals = "";
			var cnt=0;
			for (var i=0;i<checkboxes.length;i++) {
				if (checkboxes[i].checked==true) 
			  {
					if(i==checkboxes.length-1)
						{
							vals += checkboxes[i].value;
						}
					else
						{
			  				vals += checkboxes[i].value+",";
						}
			 	 cnt++;
			  }
			}
			
			//var cont=(vals.match(/,/g)).length;
			
			if(vals==""||vals==null||vals.length==0||cnt==0) {
				alert("Please Select The Employee  !");
				return false;
			} else {
				
				var chk = confirm("Are you sure to "+val+" Transaction ?");
				if(chk==true) {	
					var chk = confirm("Are you sure to "+val+" Extra Payment For "+cnt+" Employees ?");
					if(chk==true) {	
						try
						{
						
						var xmlhttp=new XMLHttpRequest();
					 	var url="";
					 	var response2="";
					 		if(val=="Post")
					 			url="ShiftServlet?action=PostExtraPayment&checkempno="+vals;
					 		else
					 			url="ShiftServlet?action=DeleteExtraPayment&checkempno="+vals;
						xmlhttp.onreadystatechange=function()
						{
							if(xmlhttp.readyState==4)
							{
							
							response2 = xmlhttp.responseText;
							 if(response2=="POST")
								{
								 alert("Record Posted Sucessfully...");
								// window.location.href="extra_duty_payment.jsp";
								 window.location.href="PostExtraPayment.jsp?action=details&emp=all";	

								}
							 else if(response2=="DELETE")
								{
								 alert("Record Deleted Sucessfully...");
								 window.location.href="PostExtraPayment.jsp?action=details&emp=all";	
								}
							}
							
						};
					 
						xmlhttp.open("POST", url, true);
						xmlhttp.send();
						}
						catch(e)
						{
							
						}
						//return true;	
					} else {
						return false;
					}	
				} else {
					return false;
				}
			}
	 }
		 
	 });
	
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
<%-- <%
 String pageName = "PostExtraPayment.jsp";
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

%> --%>

<%

	SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
	//String dt = format.format(date);
	
	SimpleDateFormat fromformat = new SimpleDateFormat("yyyy-MM-dd");
	fromformat.setLenient(false);%>
</head>
<body style="overflow: hidden;">
	<%@include file="mainHeader.jsp"%>
	<!--  start nav-outer-repeat................................................................................................. START -->
	<%@include file="subHeader.jsp"%>

	<!-- start content-outer ........................................................................................................................START -->
	<div id="content-outer" style="overflow-y: scroll; max-height: 80%;">
		<!-- start content -->
		<div id="content">

			<!--  start page-heading -->
			<div id="page-heading">
				<h1>Post Extra Payment Details</h1>
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

								 <form  >
									<table class="posttbl" id="customers" border="1">
										<tr class="alt"><th colspan="10"><font size="3"> Post Extra Payment Details</th></tr>
										<tr class="alt"  >
										<td  colspan="10"  align="center"><b>SELECT EMPLOYEE :</b>
										<input type="text" id="emplist" name="emplist"  placeholder="Enter All for all employee(s)" value="<%=request.getParameter("emp")==null?"":request.getParameter("emp").replaceAll(" and ", " & ") %>" title="Enter ALL for All Emplist">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<input type="button"  class="myButton"  value="Submit" onclick="getDetails()"/>
										</td>
										</tr>
										<tr class="alt">
											<th width="52">SRNO</th>
											<th width="95">EMP CODE</th>
											<th width="266">EMPLOYEE NAME</th>
											<th width="80">FOR DATE</th>
											<th width="100">BASIC</th>
											<th width="80">DA</th>
											<th width="110">VDA</th>
											<th width="110">DAY'S</th>
											<th width="110">TOTAL AMOUNT</th>
											<th width="110">POST/DELETE</th>
										</tr>
										
										<% if(action.equalsIgnoreCase("details")) {
											if (!result.isEmpty()) { %>
										<tr> <td colspan="10">
										<div style="height: 360px; width:100%; overflow-y: scroll;">
											<table>
										<%	
												for (extra_duty_paymentBean tb : result) {
													String fordate[]=tb.getTRNDT().split(",");
											%>
											
												<tr class="alt" id="row">
													<td width="45" align="left"><%=count++%></td>
													<td width="90" align="left"><%=tb.getEMPCODE()%></td>
													<td width="260" align="left"><%=tb.getNAME()%></td>
													<td width="75" align="center"><%for(int i=0;i<fordate.length;i++){ %><%=fordate[i] %><br><%} %></td>
													<td width="100" align="center"><%=tb.getBasic()%></td>
													<td width="75" align="center"><%=tb.getDA()%></td>
													<td width="100" align="center"><%=tb.getVDA()%></td>
													<td width="110" align="center"><%=tb.getTOTALDAYS()%></td>
													<td width="110" align="center"><%=tb.getTotal()%></td>
													<td width="86" align="center"><input type="checkbox" name="chk" value="<%=tb.getEMPNO()%>"/></td>
													<%-- <td width="80" align="center"><input type="button" name="edit" id="edit" value="Edit" onclick="change(<%=tb.getEMPNO()%>)"/></td> --%>
		
												</tr>
											
										<% } %>
										 	</table></div>
										 	<br/><tr align="center">
   												 <td colspan="10" valign="middle">
										<center><input type="button"  class="myButton" value="Post"  id="act" name="post" />&nbsp;&nbsp;&nbsp;
												<input type="button"   class="myButton" name="check_all" id="check_all" value="Check All" />&nbsp;&nbsp;&nbsp;
												<input type="reset"   class="myButton" value="Clear All"/>&nbsp;&nbsp;&nbsp;
												<input type="button"   class="myButton" name="Delete" id="act" value="Delete" /></center>
											
										</td></tr>
										<%	} else { %>
										<tr class="alt"><td align="center" colspan="10"></td></tr>	
										<tr class="alt">
											<td align="center" colspan="10" ><h3 style="color:red;">No Records Found OR Already Record Posted... </h3> </td>
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
