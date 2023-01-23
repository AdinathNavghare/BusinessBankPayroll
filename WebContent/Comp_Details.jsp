<%@page import="java.util.ArrayList"%>
<%@page import="payroll.Model.CompBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@page errorPage="error.jsp" isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy DTS3</title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<script type="text/javascript" src="js/datetimepicker.js"></script>
  
<script type="text/javascript">

function validate()
{
	
	    var name=document.forms["compdetails"]["cname"].value;
	    if(name==""){
	 	   alert("Enter the company name.!");
	 	   document.forms["compdetails"]["cname"].focus();
	 	   return false;
	    }
	    
	    var cadd=document.forms["compdetails"]["cadd"].value;
	    if(cadd==""){
	 	   alert("Enter the company address.!");
	 	   document.forms["compdetails"]["cadd"].focus();
	 	   return false;
	    }  
	    
	    city=document.forms["compdetails"]["city"].value;
	    if(city ==""){
	 	   alert("Enter the city.!");
	 	   document.forms["compdetails"]["city"].focus();
	 	   return false;
	    }
	    if(!isNaN(city)){
       		alert("Please enter correct city name.!");
       		document.forms["compdetails"]["city"].focus();
       		return false;
       	} 
	    
	    
	    
	    var phno = document.forms["compdetails"]["phoneno"].value;
	    
	    if(phno!="" ){
	    if(!phno.match(/^\d+/)){
       		alert("Please enter numeric phoneno.! ex- 1234567890 or +91-123456789");
       		document.forms["compdetails"]["phoneno"].focus();
       		return false;
       	} 
	    } 
	    /* if(phno==null ||phno==""){
			alert("Please enter phone no.!");
		    document.forms["compdetails"]["phoneno"].focus();
		    return false;	
		}
	    var phno = document.forms["compdetails"]["phoneno"].value;
	    if(!phno.match(/^\d+/))
	       {
	       alert("Please enter numeric characters for phone no.!");
	       document.forms["compdetails"]["phoneno"].focus();
	       return false;
	       }  */
	    
	    var pin = document.forms["compdetails"]["pincode"].value;
	    if(pin=="" || pin.length !=6){
			alert("Please enter 6 digit pin code.!");
		    document.forms["compdetails"]["pincode"].focus();
		    return false;	
		} 
	    if(!pin.match(/^\d+/)){
       		alert("Please enter numeric characters for pincode.!");
       		document.forms["compdetails"]["pincode"].focus();
       		return false;
       	} 
	    
	    var fromdate=document.compdetails.frmdate.value;
	    if(fromdate == "" || fromdate == null){
	 	   alert("please select the Date.!");
	 	  document.compdetails.frmdate.focus();
	 	   return false;
	    }
	    var domain=document.forms["compdetails"]["domain"].value;
	    if(domain=="" || domain == null){
	 	   alert("Enter the Domain Name.!");
	 	   document.forms["compdetails"]["domain"].focus();
	 	   return false;
	    }
	    var ccode = document.forms["compdetails"]["ccode"].value;
	    if(ccode=="" ||ccode==null){
			alert("Please enter company code.!");
		    document.forms["compdetails"]["ccode"].focus();
		    return false;	
		} 
	    if(!ccode.match(/^\d+/)){
       		alert("Please enter numeric characters for company code.!");
       		document.forms["compdetails"]["ccode"].focus();
       		return false;
       	}
	    var emailID = document.compdetails.email.value;
   		atpos = emailID.indexOf("@");
   		dotpos = emailID.lastIndexOf(".");
   		if(emailID == ""){
   			return true;
   		}
   		else if (atpos < 1 || ( dotpos - atpos < 2 )){
       		alert("Please enter correct email ID");
       		document.compdetails.email.focus() ;
       		return false;
        }   
   		
   		
   		var offday = document.getElementByName("offday").checked; 
	    if(offday == false){
	 	   alert("select week off day.!");
	 	   document.forms["compdetails"]["offday"].focus();
	 	   return false;
	    }  
	    
	    
}

function checksat(objName, chkBox) {
	

	
	document.getElementById(objName).style.display = (chkBox.checked) ? "none":"";
	KeepCount();
}


function KeepCount() {

	var NewCount = 0;
	
	if (document.compdetails.sun.checked)
	{NewCount = NewCount + 1;}

	if (document.compdetails.mon.checked)
	{NewCount = NewCount + 1;}

	if (document.compdetails.tue.checked)
	{NewCount = NewCount + 1;}

	if (document.compdetails.wed.checked)
	{NewCount = NewCount + 1;}

	if (document.compdetails.thu.checked)
	{NewCount = NewCount + 1;}
		
	if (document.compdetails.fri.checked)
	{NewCount = NewCount + 1;}

	if (document.compdetails.sat.checked)
	{
	NewCount = NewCount + 1;}
	 

	
	if (NewCount == 3)
	{
	alert('Pick Just Two Please');
	
	document.compdetails; 
	return false;
	}
	document.forms["compdetails"]["offday"].focus(); return false;
} 
</script> 
<%
	String pageName = "Comp_Details.jsp";
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
 
<body style="overflow:hidden"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1> Company Details</h1>
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
			
			<a href="showCompDetails.jsp"><input type="button" value="Click" class="myButton"> Here to see the Company Details..</a><br><br>
			
			<form action="CompanyServlet?action=add" method="post" name="compdetails" onSubmit="return validate()">

			<table id="customers" align="center">
			<tr class="alt">
				  <th colspan="4">Company Details</th>
			</tr>

			<tr class="alt"><td style="min-width:170px">Company Name :<font color="red"> *</font></td>
							<td colspan="3"><input type="text" name="cname" id="cname" onBlur="TextCheck(this.id)" size="45" title="enter company name"></td>
			</tr>
	
			<tr class="alt"><td>Company Address :<font color="red"> *</font></td>
							<td colspan="3"><textarea rows="2" cols="38" name="cadd" id="cadd"></textarea></td>
			</tr>

			<tr class="alt"><td>City :<font color="red"> *</font></td>
							<td><input type="text" name="city" id="city" ></td>
							<td style="min-width:160px">Country :<font color="red"> *</font></td>
							<td> <select name="select"> 
		
  								<option value="Australia">Australia</option>
  								<option value="Africa">Africa</option>
  								<option value="America">America</option>
  								<option value="Bangladesh">Bangladesh</option>
  								<option value="China">China</option>
  								<option value="India" selected>India</option>
  								<option value="New Zealand">New Zealand</option>
  								<option value="Pakistan">Pakistan</option>
  								<option value="Sri Lanka">Sri Lanka</option>
  			
							</select>
							</td>
			</tr>
		
			<tr class="alt"><td align="justify">Contact No :</td>
							<td><input type="text" name="phoneno" id="phoneno" ></td>
							<td>Pin Code :<font color="red"> *</font></td>
							<td><input type="text" name="pincode" id="pincode"></td>
			</tr>

			<tr class="alt"><td>Email Address :</td>
							<td><input type="text" name="email"></td>
							<td>Website :</td>
							<td><input type="text" name="website" id="website"></td>
			</tr>

			<tr class="alt"><td>From Date :<font color="red"> *</font></td>
							<td><input type='text' name="frmdate" size="15" id="frmdate" onBlur="if(value=='')" readonly="readonly">&nbsp;
								<img src="images/cal.gif" align="middle" style="vertical-align:middle; cursor:pointer;" 
										onClick="javascript:NewCssCal('frmdate', 'ddmmmyyyy')" ></td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
			</tr>

			<tr class="alt"><td>PF No :</td><td><input type="text" name="pfno" id="pfno"></td>
							<td>ESIC No :</td><td><input type="text" name="esicno" id="esicno"></td>
			</tr>

			<tr class="alt"><td>TAN No :</td><td><input type="text" name="tanno" id="tanno"></td>
							<td>PAN No :</td><td><input type="text" name="panno" id="panno"></td>
			</tr>

			<tr class="alt"><td>Domain Name :<font color="red"> *</font></td><td><input type="text" name="domain" id="domain"></td>
							<td>Company Code :<font color="red"> *</font></td><td><input type="text" name="ccode" id="ccode"></td>
			</tr>

			
			<tr class="alt"><td>Select Week Off Day :<br /><span style="font-size:75%">(At max 2)</span></td>
							<td colspan="3"><input type="checkbox" name="offday" id="sun" value="sun" onClick="return KeepCount()"/>&nbsp;&nbsp;Sunday
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" name="offday" id="mon" value="mon" onClick="return KeepCount()" />&nbsp;&nbsp;Monday
											&nbsp;&nbsp;<input type="checkbox" name="offday" id="tue" value="tue" onClick="return KeepCount()" />&nbsp;&nbsp;Tuesday
											&nbsp;&nbsp;<input type="checkbox" name="offday" id="wed" value="wed" onClick="return KeepCount()"/>&nbsp;&nbsp;Wednesday<br>
											<input type="checkbox" name="offday" id="thu" value="thu" onClick="return KeepCount()" />&nbsp;&nbsp;Thursday
											&nbsp;&nbsp;<input type="checkbox" name="offday" id="fri" value="fri" onClick="return KeepCount()" />&nbsp;&nbsp;Friday
											&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" name="offday" id="sat"value="sat" onclick="checksat('rowSubOrders',this);">&nbsp;&nbsp;Saturday

							</td>
			</tr>
			<tr class="alt" id="rowSubOrders" > <td>Alternate Saturday ?</td>
												<td colspan="3"><input type="checkbox" name="altsatday" id="one" value="1" />&nbsp;1 &nbsp;&nbsp;
												<input type="checkbox" name="altsatday" id="two" value="2" />&nbsp;2 &nbsp;&nbsp;
												<input type="checkbox" name="altsatday" id="three" value="3" />&nbsp;3 &nbsp;&nbsp;
												<input type="checkbox" name="altsatday" id="four" value="4" />&nbsp;4 &nbsp;&nbsp;
												<input type="checkbox" name="altsatday" id="five" value="5" />&nbsp;5<br></td>
			</tr>

			<tr class="alt"><td>&nbsp;</td><td colspan="3"><input type="submit" value="Save" name="Save" class="myButton">&nbsp;&nbsp;
												<input type="reset" value="cancel" class="myButton">&nbsp;&nbsp;
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
    

</body>
</html>


