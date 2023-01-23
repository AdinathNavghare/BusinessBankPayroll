<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="payroll.Core.Calculate"%>
<%@page import="payroll.Core.ReportDAO"%>
<%@page import="payroll.DAO.*"%>
<%@page import="payroll.Model.*"%>
<%@page import="java.util.ArrayList"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Post Bonus © copy DTS3</title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css"	media="screen" title="default" />
<script src="js/filter.js"></script>
<style type="text/css">
<!--
body,tr,td,th {
	font-family: Times New Roman;
	font-size: 14px;
}
-->
</style>

<script type="text/javascript" src="js/datetimepicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<link rel="stylesheet" href="css/jquery.treeview.css" />
<script src="js/jquery.js" type="text/javascript"></script>
<script src="js/jquery.treeview.js" type="text/javascript"></script>

 <script type="text/javascript">
    $(function () {
        $(document).keydown(function (e) {
            return (e.which || e.keyCode) != 116;
        });
    });
    
    function inputLimiter(e, allow) {
		var AllowableCharacters = '';
		if (allow == 'Numbers') {
			AllowableCharacters = '1234567890';
		}
		var k;
		k = document.all ? parseInt(e.keyCode) : parseInt(e.which);
		if (k != 13 && k != 8 && k != 0) {
			if ((e.ctrlKey == false) && (e.altKey == false)) {
				return (AllowableCharacters.indexOf(String.fromCharCode(k)) != -1);
			} else {
				return true;
			}
		} else {
			return true;
		}
	}
</script>

<script type="text/javascript">
var month="";
	var xmlhttp;
	var url="";
	var date;
	
	if(window.XMLHttpRequest)
	{
		xmlhttp=new XMLHttpRequest;
	}
	else //if(window.ActivXObject)
	{   
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}

function PostBonus()
{
	if(numList != "")
	{
		var month1 = document.getElementById("toYear").value;
	
	if(month1 == "0" )
	{
		alert("Please Select Salary Month");	
		return false;
	}
	
	var resp=confirm("Are You Sure to Post Bonus?");
		if(resp==true)
		{
			document.getElementById("process").hidden=false;
			url="BonusServlet?action=PostBonus&list="+numList+"&date=30-sep-"+month1;
			xmlhttp.onreadystatechange=function()
			{
				if(xmlhttp.readyState==4)
				{
					var response = xmlhttp.responseText;
					
					alert(response);
					document.getElementById("process").hidden=true;
					initAll();
				}
			};
			xmlhttp.open("POST", url, true);
			xmlhttp.send();
		}
	}
	else
	{
		alert("No Employee is Selected");
	}
}

function callback()
{
	if(xmlhttp.readyState==4)
	{
		var response = xmlhttp.responseText;
		document.getElementById("process").style.display="block";
	}
}

function postBonusFilt()
{
	 
	var dt = document.getElementById("toYear").value;
	 
	
	if(dt=="")
	{ 
		alert("Please Select Salary Month");	
		return false;
	}else{
		 month ="sep-"+dt;
	}
	 
	//addMoreEmp('toPostBonus',"sep-"+month);
	getFilt('toPostBonus');
}
	
</script>
 <script>
    function changeYear(){
    	if(document.getElementById("date").value=="")
    	{
    	alert("Please select year");
    	return false;
    	}
    	var date=document.getElementById("date").value;
    	document.getElementById("toYear").value=parseInt(date)+1;	
    }
    </script>
<% String date=ReportDAO.getSysDate().substring(7,11);
int dateInInt=Integer.parseInt(date);%>

<%
	String pageName = "PostBonus.jsp";
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

</head>
<body  > 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y: scroll; max-height: 80%;">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Bonus  Posting </h1>
	</div>
	<!-- end page-heading -->

	<table border="0" width="100%" cellpadding="0" cellspacing="0" id="content-table">
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
				<table>
					<tr>
						<td>
							<table id="customers" width=553 align="center">		
							   <tr> 
							   	<th colspan="4">Post Bonus</th></tr>
							   <tr class="alt" >
								<td style="width:200px; text-align:right;"><b>For The Financial Year : April-</b></td>
		<td style="width:25%" >	<select id="date" style="margin-right: 30px;" onchange="changeYear()" name="date">		
		
						<option value="" selected>Select</option>	
						<option value="<%=dateInInt-1%>" ><%=dateInInt-1%></option>
						<option value="<%=dateInInt%>"><%=dateInInt%></option>
						<option value="<%=dateInInt+1%>"><%=dateInInt+1%></option>
</select>

	<b>To March-</b></td>
		<td style="margin-left: 30px;">
		<input type="text" name="toYear" id="toYear" 
									style="width: 40px;" readonly="readonly" />
		</td>
							   </tr>
								<tr class="alt" align="center">
									<td colspan="4">
										<div id="displayDiv" style="height: 300px"></div>
										<div id="countEMP">0 Employees Selected</div>
										<input type="button" value="Select Employee" onclick="postBonusFilt()" class="myButton">
										<input type="button" value="Cancel All" onclick="cancelAll()" class="myButton">
									</td>
								</tr>
								
								<tr class="alt">
									<td  align="center" colspan="4">  
										<input type="button" value="Post Bonus"  class="myButton" onclick="PostBonus()"/>
										<input type="button"  class="myButton"  value="Cancel"/>
									</td>
								</tr>
			
						    </table>
					  
						</td>
						<td width="10"></td>		
					</tr>
				</table>
			    <!-- </form> -->
			</center>	    
		    </div>
		    <!--  end table-content  -->
		  
	<div id="process"  style="z-index: 1000;background-color:white;height: 100%;width: 100%;top: 0px;left: 0px;position: fixed;opacity:0.8;" hidden=true; >
		<div align="center" style="padding-top: 20%;">
			<h1> Bonus Posting takes 15 to 30 Min....Be Patience</h1>
			<img alt="" src="images/process.gif">
		</div>
	</div>
			
	
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
</html>
