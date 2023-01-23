<%@page import="payroll.DAO.*"%>
<%@page import="payroll.Model.*"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy DTS3 </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<script type="text/javascript" src="js/datetimepicker.js"></script>
<%
String action = request.getParameter("action")==null?"":request.getParameter("action");
String emptype = request.getParameter("emptype")==null?"":request.getParameter("emptype");
ArrayList<ShiftBean> callist=null;
if(action.equalsIgnoreCase("mastlist")||action.equalsIgnoreCase("calmastupdate"))
{
	callist =  (ArrayList<ShiftBean>)session.getAttribute("calmastlist");

}
%>
<script type="text/javascript">
 function validateform()
{
	if(document.calmast.EmpType1.value=="Default")
    {   alert("please select EmpType");
        document.calmast.EmpType1.focus();
        return false;
     }
	  if(document.calmast.day1.value=="Default")
	  {
	  alert("please select day");
	   document.calmast.day1.focus();
	   return false;
	  }
	   if(document.calmast.daydate1.value=="")
	  {
	  alert("please select Daydate");
	   document.calmast.daydate1.focus();
	   return false;
	  }
	   
	   if(document.calmast.holiday1.value=="Default")
		  {
		  alert("please select holiday");
		   document.calmast.holiday1.focus();
		   return false;
		  }
	   if(document.calmast.daytype1.value=="Default")
		  {
		  alert("please select daytype");
		   document.calmast.daytype1.focus();
		   return false;
		  }
	 if(document.calmast.desc1.value=="")
		  {
		  alert("please enter desc");
		   document.calmast.desc1.focus();
		   return false;
		  }
	location.r

}

function validate()
{


   if(document.calmastform.EmpType.value=="Default")
        {   alert("please select EmpType");
            document.calmastform.EmpType.focus();
            return false;
         }
		  if(document.calmastform.day.value=="Default")
		  {
		  alert("please select day");
		   document.calmastform.day.focus();
		   return false;
		  }
		   if(document.calmastform.daydate2.value=="")
		  {
		  alert("please select Daydate");
		   document.calmastform.daydate2.focus();
		   return false;
		  }
		   
		   if(document.calmastform.holiday.value=="Default")
			  {
			  alert("please select holiday");
			   document.calmastform.holiday.focus();
			   return false;
			  }
		   if(document.calmastform.daytype.value=="Default")
			  {
			  alert("please select daytype");
			   document.calmastform.daytype.focus();
			   return false;
			  }
		 if(document.calmastform.desc.value=="")
			  {
			  alert("please enter desc");
			   document.calmastform.desc.focus();
			   return false;
			  }
		    
 
}


function Showhide()
	{
		document.getElementById("update").hidden=true;
	}
</script>
<style type="text/css">
.row:hover {
	background-color: #CCCCCC;
	cursor: pointer;
}

.style1 {
	color: #FF0000
}
</style>


</head>
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
		<h1> Calendar Master </h1>
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
			 
			
			
			<form  name="calmastform" action="ShiftServlet?action=addcalmast" method="post" onSubmit="return validate()">
				<table id="customers" align="center" width="600">
				
				<tr><th colspan="4"> Calendar Master</th></tr>
				<%
				    LookupHandler lkph=new  LookupHandler(); 
				
				%>
				
				 
				<tr class="alt"> 
					<td width="155">Employee Type</td>
					<td width="87">
					<select name="EmpType" id="EmpType" style="width:220px;">
						 <option value="Default" selected="selected">Select</option>  
						<%
  							ArrayList<Lookup> result=new ArrayList<Lookup>();
    						LookupHandler lkhp= new LookupHandler();
    						result=lkhp.getSubLKP_DESC("ET");
 							for(Lookup lkbean : result){
 								%>
 									<option value="<%=lkbean.getLKP_SRNO()%>" ><%=lkbean.getLKP_DESC()%></option>  
 									<%
 									}
 							%>
					</select></td>
					<td width="67">Day</td>
					<td width="167">
					<select name="day" id="day">
						 <option value="Default" selected="selected">Select</option>  
						<option value="Sunday">Sunday</option>
						<option value="Monday">Monday</option>
						<option value="Monday">Tuesday</option>
						<option value="Monday">Wednesday</option>
						<option value="Monday">Thursday</option>
						<option value="Monday">Friday</option>
						<option value="Monday">Saturday</option>
					</select>					</td>
				</tr>
				<tr class="alt">
					<td>Day Date</tsd>
					<td><input name="daydate" readonly="readonly" size="20" id="daydate" type="text" onBlur="if(value=='')"> &nbsp;<img src="images/cal.gif" align="absmiddle" style="vertical-align:middle; cursor:pointer;" onClick="javascript:NewCssCal('daydate', 'ddmmmyyyy')" />					</td>
					<td>Holiday Yes/No</td><td><select name="holiday" id="holiday">
						 <option value="Default" selected="selected">Select</option>  
						<option value="Y">Yes</option>
						<option value="N">No</option>
					</select></td>
				</tr>
				<tr class="alt"><td> Day Type</td> <td><select name="daytype" id="daytype">
						 <option value="Default" selected="selected">Select</option>  
						<option value="H">holiday</option>
						<option value="WO">weekly off</option>
					</select> </td> <td>Desc</td><td><input type="text" name="desc"> </td></tr>
				
				
				<tr class="alt" align="center"><td colspan="4"><input name="submit" type="submit" value="Save">				  &nbsp; &nbsp;
				  <input type="reset" value="Reset"></td></tr>
				</table>
			
			</form>
			<br>
			
			
			<table width="600" id="customers">
				<tr>
				
				<th width="170">Employee Type</th>
				   <th width="46">Day</th>			
					<th width="64">Day Type</th>
					
					<th width="64">Day Date</th>
					<th width="50">Holiday</th>
					<th width="73">Desc</th>
					<th width="62">Edit</th>
				</tr>
			<%
			
			
			if(action.equalsIgnoreCase("mastlist")||action.equalsIgnoreCase("calmastupdate")  )
			{
				
				String desc="";
				String desc1="";
				LookupHandler lookup=new LookupHandler();  
			  if(!(callist == null))
			  {
				for(ShiftBean sbean1 : callist)
				{
					desc1=Integer.toString( sbean1.getEmptype());
					
					Lookup lkp=lookup.getLookup("ET-"+desc1);
					
			%>
			
			 <tr class="row"> <td width="204"><%=lkp.getLKP_DESC()%></td>
			 <td width="60"><%=sbean1.getDay() %></td>
			 <td width="50"><%=sbean1.getDaytype() %></td>
			 <td width="66"><%=sbean1.getDaydate() %></td>
			 <td width="50"><%=sbean1.getHoliday()%></td>
			 <td width="66"><%=sbean1.getDesc() %></td> 
			 <td><input type="button" value="Edit" onClick="window.location='calmast.jsp?action=calmastupdate&emptype=<%=sbean1.getEmptype()%>'"/></td>
				</tr>
			
			<%}
				
			  }
		}
		%>
		
				<tr class="alt"><th colspan="7"></th></tr>
				
			</table>
			
			<br>
			
			
			<%
			if(action.equalsIgnoreCase("calmastupdate"))
			{
              
				
				for(ShiftBean sbean1 : callist)
				{
				
				if(emptype.equalsIgnoreCase(Integer.toString(sbean1.getEmptype())))
				{
				
			%>
			<form name="calmast" action="ShiftServlet?action=updatecalmast" method="post" onSubmit="return validateform()">
					<div  id="update">
				
				<table id="customers" align="center" width="600">
				
				<tr><th colspan="4">Edit Calendar Master</th></tr>
				<%
				    LookupHandler lkph3=new  LookupHandler(); 
				
				%>
				
				 
				<tr class="alt"> 
					<td width="75">Employee Type</td>
					<td width="187">
					<select name="EmpType1" id="EmpType1"  style="width:220px;">
						
						<%
  							ArrayList<Lookup> result3=new ArrayList<Lookup>();
    						LookupHandler lkhp3= new LookupHandler();
    						result3=lkhp3.getSubLKP_DESC("ET");
 							for(Lookup lkbean : result3)
 							{
 								if(emptype.equals(Integer.toString(lkbean.getLKP_SRNO())))
 								{
 								
 									%>
 									<option value="<%=lkbean.getLKP_SRNO()%>" selected="selected"><%=lkbean.getLKP_DESC()%></option>  
 									<%
 								  }	
 								%>
									<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
									<%
 								}
 							%>
					</select>
				</td>
					<td width="118">Day</td>
					<td width="200">
					
					<%
					String Sunday = "notselected";
					String Monday = "notselected";
					String Tuesday = "notselected";
					String Wednesday = "notselected";
					String Thursday = "notselected";
					String Friday = "notselected";
					String Saturday = "notselected";
					
					if(sbean1.getDay().contains("Sunday")){
						Sunday = "selected";
					}else if(sbean1.getDay().contains("Monday")){
						Monday = "selected";
					}else if(sbean1.getDay().contains("Tuesday")){
						Tuesday = "selected";
					}else if(sbean1.getDay().contains("Wednesday")){
						Wednesday = "selected";
					}else if(sbean1.getDay().contains("Thursday")){
						Thursday = "selected";
					}else if(sbean1.getDay().contains("Friday")){
						Friday = "selected";
					}else if(sbean1.getDay().contains("Saturday")){
						Saturday = "selected";
					}
					%>
					
					<select name="day1" id="day1">
						<option value="Default" selected="selected">Select</option>
						<option value="Sunday"<%=Sunday %>>Sunday</option>
						<option value="Monday"<%=Monday%>>Monday</option>
						<option value="Tuesday"<%=Tuesday%>>Tuesday</option>
						<option value="Wednesday"<%=Wednesday%>>Wednesday</option>
						<option value="Thursday"<%=Thursday%>>Thursday</option>
						<option value="Friday"<%=Friday%>>Friday</option>
						<option value="Saturday"<%=Saturday%>>Saturday</option>
					</select>					</td>
				</tr>
				<tr class="alt">
					<td>Day Date</td>
					<td>
					<input name="daydate1"  size="20" id="daydate1" type="text" value="<%=sbean1.getDaydate() %>" onBlur="if(value=='')">&nbsp;<img src="images/cal.gif" align="absmiddle" style="vertical-align:middle; cursor:pointer;" onClick="javascript:NewCssCal('daydate1', 'ddmmmyyyy')" />					</td>
					<td>Holiday Yes/No</td>
					<td>
					 <%
				    String Yes = "notselected";
					String No = "notselected";
					if(sbean1.getHoliday().contains("Y")){
						Yes = "selected";
					}
					if(sbean1.getHoliday().contains("N")){
						No = "selected";
					}
				   %>
				   
					<select name="holiday1" id="holiday1">
						<option value="Default" selected="selected">Select</option>
						<option value="Y" <%=Yes%>>Yes</option>
						<option value="N" <%=No%>>No</option>
					</select></td>
				</tr>
				<tr class="alt"><td> Day Type</td> <td>
				   <%
				    String weeklyoff = "notselected";
					String Holiday = "notselected";
					if(sbean1.getDaytype().contains("WO")){
						weeklyoff = "selected";
					}
					if(sbean1.getDaytype().contains("H")){
						Holiday = "selected";
					}
				   %>
				   
				   <select name="daytype1" id="daytype1">
						<option value="Default" selected="selected">Select</option>
						<option value="WO" <%=weeklyoff %>>weeklyoff</option>
						<option value="H"<%=Holiday %>>Holiday</option>
					</select> </td> <td>Desc</td><td><input type="text" name="desc1" id="desc1" value="<%=sbean1.getDesc()%>"> </td></tr>
				
				
				<tr class="alt" align="center"><td colspan="4"><input name="submit" type="submit" value="Save">&nbsp; &nbsp;
				  <input type="button" value="Cancel" onClick="Showhide()"></td></tr>
				</table>
			</div>
			</form>
			<%} 
			
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