<%@page import="java.text.SimpleDateFormat"%>
<%@page import="payroll.DAO.CompanyHandler"%>
<%@page import="payroll.Model.CompBean"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.sql.SQLException"%>

<%@page import="java.util.Iterator"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import = "java.util.ArrayList" %>
<%@page isELIgnored="false" %>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy DTS3</title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<script src="js/jquery/jquery-1.4.1.min.js" type="text/javascript"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script type="text/javascript">
function Showhide()	{
		document.getElementById("update").hidden=true;
	}
	
function validate()
{
		var phno = document.forms["editform"]["phoneno"].value;
		var pfno = document.forms["editform"]["pfno"].value;
		var esicno = document.forms["editform"]["esicno"].value;
		var tanno = document.forms["editform"]["tanno"].value;
		var panno = document.forms["editform"]["panno"].value;
		var ccode = document.forms["editform"]["ccode"].value;
	
		     
 		
	    var name=document.forms["editform"]["cname"].value;
	    if(name==""){
	 	   alert("Enter the company name.!");
	 	   document.forms["editform"]["cname"].focus();
	 	   return false;
	    }
	    
	    var cadd=document.forms["editform"]["cadd"].value;
	    if(cadd==""){
	 	   alert("Enter the company address.!");
	 	   document.forms["editform"]["cadd"].focus();
	 	   return false;
	    }  
	    
	    var city=document.forms["editform"]["city"].value;
	    if(city==""){
	 	   alert("Enter the city.!");
	 	   document.forms["editform"]["city"].focus();
	 	   return false;
	    } 
	    
	    if(!isNaN(city)){
       		alert("Please enter correct city name.!");
       	 document.forms["editform"]["city"].focus();
       		return false;
       	} 
	    /* if(phno==null && phno<10){
			alert("Please enter phone no.!");
		    document.forms["editform"]["phoneno"].focus();
		    return false;	
		}
	   /* if(!phno.match(/^\d+/) )
	       {
	       alert("Please enter numeric characters for phone no.!");
	       document.forms["editform"]["phoneno"].focus();
	       return false;
	       }  */
	    
	    var pin = document.forms["editform"]["pincode"].value;
	    if(pin=="" || pin.length !=6){
			alert("Please enter 6 digit pin code.!");
		    document.forms["editform"]["pincode"].focus();
		    return false;	
		} 
	    if(!pin.match(/^\d+/)){
       		alert("Please enter numeric characters for pincode.!");
       		document.forms["editform"]["pincode"].focus();
       		return false;
       	} 
	    
	    var emailID = document.editform.email.value;
   		atpos = emailID.indexOf("@");
   		dotpos = emailID.lastIndexOf(".");
   		if(emailID == ""){
   			return true;
   		}
  		if (atpos < 1 || ( dotpos - atpos < 2 )){
       		alert("Please enter correct email ID");
       		document.editform.email.focus() ;
       		return false;
        } 
  		
  		var website = document.forms["editform"]["website"].value;
	    if(website==""){
	 	   alert("Enter the website address.!");
	 	   document.forms["editform"]["website"].focus();
	 	   return false;
	    }  
	    
	    var fromdate=document.forms["editform"]["frmdate"].value;
	    if(fromdate==""){
	 	   alert("please select the Date.!");
	 	   document.forms["editform"]["frmdate"].focus();
	 	   return false;
	    }  
	       
  	  /* if(pfno==null ||pfno==""){
			alert("Please enter PF No.!");
		    document.forms["editform"]["pfno"].focus();
		    return false;	
		} 
	    if(!pfno.match(/^\d+/)){
     		alert("Please enter numeric characters for PF No.!");
     		document.forms["editform"]["pfno"].focus();
     		return false;
     	}
	    
	    if(esicno==null ||esicno==""){
			alert("Please enter ESIC No.!");
		    document.forms["editform"]["esicno"].focus();
		    return false;	
		} 
	    if(!esicno.match(/^\d+/)){
       		alert("Please enter numeric characters for ESIC No.!");
       		document.forms["editform"]["esicno"].focus();
       		return false;
       	}
	    
	    if(tanno==null ||tanno==""){
			alert("Please enter TAN No.!");
		    document.forms["editform"]["tanno"].focus();
		    return false;	
		} 
	    if(!tanno.match(/^\d+/)){
       		alert("Please enter numeric characters for TAN No.!");
       		document.forms["editform"]["tanno"].focus();
       		return false;
       	}
	    
	    if(panno==null ||panno==""){
			alert("Please enter PAN No.!");
		    document.forms["editform"]["panno"].focus();
		    return false;	
		} 
	    if(!panno.match(/^\d+/)){
       		alert("Please enter numeric characters for PAN No.!");
       		document.forms["editform"]["panno"].focus();
       		return false;
       	} */
	    
	    var domain=document.forms["compdetails"]["domain"].value;
	    if(domain==""){
	 	   alert("Enter the Domain Name.!");
	 	   document.forms["editform"]["domain"].focus();
	 	   return false;
	    } 
	    
	    if(ccode==null ||ccode==""){
			alert("Please enter company code.!");
		    document.forms["editform"]["ccode"].focus();
		    return false;	
		} 
	    if(!ccode.match(/^\d+/)){
       		alert("Please enter numeric characters for company code.!");
       		document.forms["editform"]["ccode"].focus();
       		return false;
       	}   
	      
   return (true);
   
}
function checksat(objName, chkBox) {
	
document.getElementById(objName).style.display = (chkBox.checked) ? "none":"";
	KeepCount();
}

function KeepCount() {

	var NewCount = 0;

	if (document.editform.Sunday.checked)
	{NewCount = NewCount + 1;}

	if (document.editform.mon.checked)
	{NewCount = NewCount + 1;}

	if (document.editform.tue.checked)
	{NewCount = NewCount + 1;}

	if (document.editform.wed.checked)
	{NewCount = NewCount + 1;}

	if (document.editform.thu.checked)
	{NewCount = NewCount + 1;}
		
	if (document.editform.fri.checked)
	{NewCount = NewCount + 1;}

	if (document.editform.sat.checked)
	{NewCount = NewCount + 1;}

	
	if (NewCount == 3)
	{
	alert('Pick Just Two Please');
	
	document.editform; 
	return false;
	}
	document.forms["editform"]["offday"].focus(); return false;
} 

</script> 
<%
	String action = request.getParameter("action")==null?"":request.getParameter("action");
	int ccode = request.getParameter("ccode")==null?0:Integer.parseInt(request.getParameter("ccode"));
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat output = new SimpleDateFormat("dd-MMM-yyyy");

	ArrayList<CompBean> al = new ArrayList<CompBean>();
	CompanyHandler cdao = new CompanyHandler();
	al = cdao.getDetails();

%>
</head>

<body style="overflow: hidden;"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1> Company Details  </h1>
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
			<div id="table-content"  style="overflow-y:auto; max-height:520px; ">
			<center>
			<form>
			<a href="Comp_Details.jsp"><h3 align="left"><u>Back</u></h3></a>
			<table id="customers" align="center">

				<tr class="alt">
					<th width="30">Name</th>
					<th width="150">Address</th>
					<th width="20">city</th>
					<th width="20">Country</th>
					<th width="35">PhoneNo</th>
					<th width="15">PinCode</th>
					
					<th width="20">PF No</th>
					<th width="20">ESIC No</th>
					<th width="15">TAN No</th>
					<th width="15">PAN No</th>
					
					<th width="15">CompCode</th>
					<th width="20">Edit</th>
				</tr>
	
			<%  
			
				for(CompBean bn : al){
			%>
	
				<tr class="alt" align="center">
					<td width="100"><%=bn.getCname()%></td>
					<td><%=bn.getCadd()%></td>
					<td><%=bn.getCity()%></td>
					<td><%=bn.getCountry()%></td>
					<td><%=bn.getPhno()%></td>
					<td><%=bn.getPincode()%></td>

					<td><%=bn.getPfno()%></td>
					<td><%=bn.getEsicno()%></td>
					<td><%=bn.getTanno()%></td>
					<td><%=bn.getPanno()%></td>
										
					<td><%=bn.getCompcode()%></td>					
		
					<td><input type="button" value="Edit" onclick="window.location='showCompDetails.jsp?action=edit&ccode=<%=bn.getCompcode() %>'" /></td>		
					
				</tr> 
			<%}
			if(al.isEmpty()) {	
			%>
				<tr height="30"> <td colspan="12">No Details Available To Display.</td></tr>
			<%} %>
			</table>

			</form><br>
			

			<%
			if(action.equalsIgnoreCase("edit")){
	
			for(CompBean b1 : al){
				
				if(ccode == b1.getCompcode()){
					
			%>
			
			<form action="CompanyServlet?action=update" name="editform" method="post" onsubmit="return validate()">
			<div id="update" >
			<table id="customers" align="center">
				<tr class="alt">
				  <th colspan="4">Update Company Details</th>
				</tr>
			
			<tr class="alt"><td style="min-width:170px">Company Name :<font color="red"> *</font></td>	
				<td colspan="3"><input type="text" name="cname" id="cname" size="45" title="enter company name" value="<%=b1.getCname()%>"></td>
			</tr>

			<tr class="alt"><td>Company Address :<font color="red"> *</font></td>
				<td colspan="3"><textarea rows="2" cols="38" name="cadd" id="cadd"><%=b1.getCadd()%></textarea></td></tr>

			<tr class="alt"><td>City :<font color="red"> *</font></td><td><input type="text" name="city" id="city" value="<%=b1.getCity()%>"></td>
				<td style="min-width:160px">Country :<font color="red"> *</font></td>
			<td >
	
			<%
					String Australia = "notselected";
					String Africa = "notselected";
					String America = "notselected";
					String Bangladesh = "notselected";
					String China = "notselected";
					String India = "notselected";
					String NewZealand = "notselected";
					String Pakistan = "notselected";
					String SriLanka = "notselected";
					
					if(b1.getCountry().contains("Australia")){
						Australia = "selected";
					}else if(b1.getCountry().contains("Africa")){
						Africa = "selected";
					}else if(b1.getCountry().contains("America")){
						America = "selected";
					}else if(b1.getCountry().contains("Bangladesh")){
						Bangladesh = "selected";
					}else if(b1.getCountry().contains("China")){
						China = "selected";
					}else if(b1.getCountry().contains("India")){
						India = "selected";
					}else if(b1.getCountry().contains("NewZealand")){
						NewZealand = "selected";
					}else if(b1.getCountry().contains("Pakistan")){
						Pakistan = "selected";
					}else if(b1.getCountry().contains("SriLanka")){
						SriLanka = "selected";
					}
			%>
		
			<select name="select"> 
		
  				<option value="Australia"<%=Australia %>>Australia</option>
  				<option value="Africa"<%=Africa %>>Africa</option>
  				<option value="America"<%=America %>>America</option>
  				<option value="Bangladesh"<%=Bangladesh %>>Bangladesh</option>
  				<option value="China"<%=China %>>China</option>
  				<option value="India"<%=India %>>India</option>
  				<option value="New Zealand"<%=NewZealand %>>New Zealand</option>
  				<option value="Pakistan"<%=Pakistan %>>Pakistan</option>
  				<option value="Sri Lanka"<%=SriLanka %>>Sri Lanka</option>
  			
			</select></td></tr>
		
			<tr class="alt"><td>Contact No :</td><td><input type="text" name="phoneno" id="phoneno" value="<%=b1.getPhno()%>"></td>
							<td>Pin Code :<font color="red"> *</font></td><td><input type="text" name="pincode" id="pincode" value="<%=b1.getPincode()%>"></td>
			</tr>

			<tr class="alt"><td>Email Address :</td><td><input type="text" name="email" value="<%=b1.getEmail()%>"></td>
							<td>Website :</td><td><input type="text" name="website" id="website" value="<%=b1.getWebsite()%>"></td>
			</tr>

			<tr class="alt"><td class="alt">From Date :<font color="red"> *</font></td><td><input type='text' name="frmdate" id="frmdate"size="15" value="<%=output.format(sdf.parse(b1.getFrmdate())) %>"onBlur="if(value=='')" readonly="readonly">&nbsp;
						<img src="images/cal.gif" align="middle" style="vertical-align:middle; cursor:pointer;" 
						onClick="javascript:NewCssCal('frmdate', 'ddmmmyyyy')" ></td>
						<td colspan="2">&nbsp;</td>
			</tr>

			<tr class="alt"><td>PF No :</td><td><input type="text" name="pfno" id="pfno" value="<%=b1.getPfno()%>"></td>
							<td>ESIC No :</td><td><input type="text" name="esicno" id="esicno" value="<%=b1.getEsicno()%>"></td>
			</tr>

			<tr class="alt"><td>TAN No :</td><td><input type="text" name="tanno" id="tanno" value="<%=b1.getTanno()%>"></td>
							<td>PAN No :</td><td><input type="text" name="panno" id="panno" value="<%=b1.getPanno()%>"></td>
			</tr>

			<tr class="alt"><td>Domain Name :<font color="red"> *</font></td><td><input type="text" name="domain" id="domain" value="<%=b1.getDomainname()%>"></td>
							<td>Company Code :<font color="red"> *</font></td><td><input type="text" name="ccode" id="ccode" readonly="readonly" value="<%=b1.getCompcode()%>"></td>
			</tr>

			<tr class="alt"><td>Select Week Off Day :<br><span style="font-size:75%">(At max 2)</span></td>
							<td colspan="3">
							<%								
								String Sunday = "notchecked";
								String Monday = "notchecked";
								String Tuesday = "notchecked";
								String Wednesday = "notchecked";
								String Thursday = "notchecked";
								String Friday = "notchecked";
								String Saturday = "notchecked";
					
					
								if(b1.getWoffday().contains("sun"))
									Sunday = "checked";
								if(b1.getWoffday().contains("mon"))
									Monday = "checked";
								if(b1.getWoffday().contains("tue"))
									Tuesday = "checked";
								if(b1.getWoffday().contains("wed"))
									Wednesday = "checked";
								if(b1.getWoffday().contains("thu"))
									Thursday = "checked";
								if(b1.getWoffday().contains("fri"))
									Friday = "checked";
								if(b1.getWoffday().contains("sat"))
									Saturday = "checked";
			
								
							%>
								<input type="checkbox" name="offday" id="Sunday" value="sun" <%=Sunday%> onclick="return KeepCount()" />&nbsp;&nbsp;Sunday &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="checkbox" name="offday" id="mon" value="mon" <%=Monday %> onClick="return KeepCount()"/>&nbsp;&nbsp;Monday &nbsp;&nbsp;
								<input type="checkbox" name="offday" id="tue" value="tue"<%=Tuesday %> onClick="return KeepCount()" />&nbsp;&nbsp;Tuesday &nbsp;&nbsp;
								<input type="checkbox" name="offday" id="wed" value="wed"<%=Wednesday %> onClick="return KeepCount()" />&nbsp;&nbsp;Wednesday<br> 
								<input type="checkbox" name="offday" id="thu" value="thu"<%=Thursday %> onClick="return KeepCount()" />&nbsp;&nbsp;Thursday &nbsp;&nbsp;
								<input type="checkbox" name="offday" id="fri" value="fri"<%=Friday %> onClick="return KeepCount()" />&nbsp;&nbsp;Friday &nbsp;&nbsp;&nbsp;&nbsp;
								<input type="checkbox" name="offday" id="sat" value="sat"<%=Saturday %> onclick="checksat('rowSubOrders',this)"; />&nbsp;&nbsp;Saturday<br>
							</td>
			</tr>
			
			<tr class="alt" id="rowSubOrders"><td>Alternate Saturday :</td>
							<td colspan="3">
							<%								
								String first = "notchecked";
								String second = "notchecked";
								String third = "notchecked";
								String fourth = "notchecked";
								String fifth="notchecked";
								
								if(b1.getAltsat().contains("1"))
									first = "checked";
								if(b1.getAltsat().contains("2"))
									second = "checked";
								if(b1.getAltsat().contains("3"))
									third = "checked";
								if(b1.getAltsat().contains("4"))
									fourth = "checked";
								if(b1.getAltsat().contains("5"))
									fifth = "checked";
							
							%>
											<input type="checkbox" name="altsatday" id="one" value="1" <%=first %>/>&nbsp;1&nbsp;&nbsp;
											<input type="checkbox" name="altsatday" id="two" value="2" <%=second %>/>&nbsp;2&nbsp;&nbsp;
											<input type="checkbox" name="altsatday" id="three" value="3" <%=third %>/>&nbsp;3&nbsp;&nbsp;
											<input type="checkbox" name="altsatday" id="four" value="4" <%=fourth %>/>&nbsp;4
												<input type="checkbox" name="altsatday" id="five" value="5"<%=fifth %> />&nbsp;5<br>											
							</td>
			</tr>
								
			<tr class="alt"><td>&nbsp;</td><td colspan="3"><input type="submit" value="Save" name="save">&nbsp;&nbsp;

				<input type="button" value="Cancel" onclick="Showhide()"></td></tr>
			</table>
		
			
			</div>
			</form>		
			<%		}
				}
			}
			%>	
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