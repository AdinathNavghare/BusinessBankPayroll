<%@page import="payroll.Core.ReportDAO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
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
<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script src="js/DeleteRow.js"></script>
<%
int flag=request.getParameter("flag")==null?0:Integer.parseInt(request.getParameter("flag"));
ArrayList<HolidayMasterBean> showholiday= new ArrayList<HolidayMasterBean>();
LookupHandler lh = new LookupHandler();
HolidayMasterHandler hmh = new HolidayMasterHandler();
EmpOffBean ebn = new EmpOffBean();
String action = "";
String fromDate="";
String toDate="";
String branch="";
try{
	action = request.getParameter("action");
	int length = action.length();
	fromDate=request.getParameter("frmdate");
	toDate = request.getParameter("todate");
	branch = request.getParameter("branch");
	showholiday = hmh.getHoldList(fromDate,toDate,branch);
}
catch(Exception e){
	Date date = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
	String dt = sdf.format(date);
	fromDate=ReportDAO.BOM(dt);
	toDate = ReportDAO.EOM(dt);
	branch = "All";
	showholiday = hmh.getHoldList(ReportDAO.BOM(dt), ReportDAO.EOM(dt), "All");
}

%>


<script type="text/javascript">



function validate()
{
   var fromDate=document.holidaymastform.frmdate.value;
   var toDate=document.holidaymastform.todate.value;
   fromDate = fromDate.replace(/-/g,"/");
	toDate = toDate.replace(/-/g,"/");
   
	if(document.holidaymastform.frmdate.value=="")
	{
		 alert("please enter the fromdate");
    	 document.holidaymastform.frmdate.focus();
     	return false;
	}	
    
   if( document.holidaymastform.todate.value=="")
    {
		  alert("please enter the todate");
	      document.holidaymastform.todate.focus();
	      return false;
	}
   
   	var d1 = new Date(fromDate);
 	var d2 =new  Date(toDate);
 	
 	if (d1.getTime() > d2.getTime())
      {
	   alert("Invalid Date Range!\n fromdate Date cannot greater than TODate!");
	   document.holidaymastform.todate.focus();
	   return false;
	   }
 	
}


function deleteHoliday( key){
	
	var conf = confirm(" Delete Holiday ?");
	if(conf==true)
	{	
		//window.location.href="HolidayMasterServlet?action=del&key="+key;
		
		url="HolidayMasterServlet?action=del&key="+key;
		xmlhttp.onreadystatechange=callback;
		xmlhttp.open("GET", url, true);
		xmlhttp.send();
	
	}
	
}
function callback()
{
	if(xmlhttp.readyState==4)
	{
    	var response=xmlhttp.responseText;
    	if(response=="true")
    	{
    		alert("Record Deleted");
			window.location.href="ShowHolidayMaster.jsp?action=show";
			//alert("Record Deleted");
			//window.location.href="ShowHolidayMaster.jsp?action=show&frmdate="+ReportDAO.BOM(dt)+"&todate="+ReportDAO.BOM(dt)+"&branch=All";
        }
    	else
    	{
    		alert("Error occured while deleting Record");
        }        	
	}
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
<body style="overflow: hidden;"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer">
<!-- start content -->
<div id="content" >

	<!--  start page-heading -->
	<div id="page-heading">
		<h1> Show Holiday Records </h1>
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
			<div id="table-content" style="overflow-y:scroll; height:500px;" >
			<center>
			 
			
			
			<form  name="showholidayform" action="ShowHolidayMaster.jsp?action=show" method="post" onSubmit="return validate()">
				<table id="customers" align="center" width="600">
				
				<tr><th colspan="5"> <b>Show Holiday Records</b></th></tr>
				<tr class="alt"> 
				
					<td>Fromdate</td>
					<td><input name="frmdate" value="<%=fromDate %>" size="20" id="frmdate" type="text" onBlur="if(value=='')" readonly="readonly"><img src="images/cal.gif" align="middle" style="vertical-align:middle; cursor:pointer;" onClick="javascript:NewCssCal('frmdate', 'ddmmmyyyy')" /></td>
					<td>Todate</td>
					<td><input name="todate" value="<%=toDate %>" size="20" id="todate" type="text" onBlur="if(value=='')" readonly="readonly"><img src="images/cal.gif" align="middle" style="vertical-align:middle; cursor:pointer;" onClick="javascript:NewCssCal('todate', 'ddmmmyyyy')" /></td></tr>
					<tr class="alt">
					<%-- <td width="130" align="center" colspan="5">Branch&nbsp;&nbsp;&nbsp;&nbsp;					
				 <select name="branch" id="branch" >
				 	<%
				 		if(branch.equalsIgnoreCase("All"))
				 		{
				 	%>
				 			<option value="All" selected="selected">All</option>
					<%
				 		} 
				 		else
				 		{	
					%>
							<option value="All" >All</option>
					 <%
				 		}
						ArrayList<BranchBean> result=new ArrayList<BranchBean>();
					    BranchDAO bdao = new BranchDAO();
					   	result = bdao.getBranchDetails();
				    	for(BranchBean brbean1 : result)
				    	{
				     
				     		if(branch.equalsIgnoreCase(brbean1.getBRNAME()))
				 			{
				     %> 	
				     			<option value="<%=brbean1.getBRNAME()%>" selected="selected"><%=brbean1.getBRNAME()%></option>  
				     <%
				 			}
				     		else
				     		{
				     %>
				     			<option value="<%=brbean1.getBRNAME()%>"><%=brbean1.getBRNAME()%></option>
				     <%			
				     		}
						}
				     %>
				</select></td>	 --%>
						<td width="130" align="center" colspan="5">Branch / Project Name&nbsp;&nbsp;&nbsp;&nbsp;					
				 <select name="branch" id="branch" >
				 	<%
				 		if(branch.equalsIgnoreCase("All"))
				 		{
				 	%>
				 			<option value="All" selected="selected">All</option>
					<%
				 		} 
				 		else
				 		{	
					%>
							<option value="All" >All</option>
							
					 <%
				 		}
						ArrayList<EmpOffBean> list=new ArrayList<EmpOffBean>();
						EmpOffHandler ofh = new EmpOffHandler();
						list=ofh.getprojectCode();
				    	for(EmpOffBean lkb :list)
				    	{
				     
				     		if(branch.equalsIgnoreCase(lkb.getPrj_name()))
				 			{
				     %> 	
				     			<option value="<%=lkb.getPrj_name()%>" selected="selected"><%=lkb.getPrj_name()%></option>  
				     <%
				 			}
				     		else
				     		{
				     %>
				     			<option value="<%=lkb.getPrj_name()%>"><%=lkb.getPrj_name()%></option>
				     <%			
				     		}
						}
				     %> 
				</select></td>				
				</tr>	
					<tr class="alt"><td  colspan="10" align="center">
					<input  type="submit" value="Go" class="myButton"> &nbsp; &nbsp; <input  type="reset" value="Clear" class="myButton">	
						
				</table>
		
			</form>
			<br>	
					<br>	
			<table id="customers" align="center" width="600">
			<tr >
				
				<th width="170">Holiday Name</th>
				   <th width="85">From Date</th>			
					<th width="85">To Date</th>
					<th width="85">Branch</th>
					<th width="70">Repeat Holiday</th>
					<th width="70">Optional Holiday</th>
					<th width="80">Holiday Type</th>
					<th width="65">Half Day</th>
					<th width="45">Attend</th>
					<th width="195">Message Text</th>
					<th width="50">SMS</th>
					<th width="50">Delete</th>
					</tr>
						
				<%
				
				if(showholiday.size()!=0)
					{
					
					
					for(HolidayMasterBean hb : showholiday)
					{
						int sub=hb.getHolino();
					//	System.out.println("valueeeee"+sub);
						
					%>
							<%-- <tr align="center"><td width="170"><%=hb.getHoldname() %></td> --%>
							
							<tr align="center">
							
							<td><%=hb.getHoldname()%></td>
							<td width="85"><%=hb.getFromdate() %></td>
							<td width="85"><%=hb.getTodate() %></td>
							<td width="85"><%= hb.getBranch() %></td>
							<%-- <td><%=lh.getLKP_Desc("BANK", empoffbean.getBANK_NAME())%></td> --%>
							<td width="70"><%=hb.getRepeathold() %></td>
							<td width="70"><%=hb.getOptionalhold() %></td>							
							<td width="80"><%=hb.getType() %></td>
							<td width="65"><%=hb.getDay() %></td>
							<td width="45"><%=hb.getAttend() %></td>
							<td width="195"><%=hb.getText() %></td>
							<td width="50"><%=hb.getSms() %></td>
							<td width="50">
							<input type="button" value="Delete" onclick="deleteHoliday('<%=sub%>')" class="myButton"/></td>
						<%-- <input type="button" value="Delete"onclick="deleteHoliday('delete','holiday','ShowHolidayMaster.jsp?action=show &hdname=<%=hb.getHoldname()%>')"/></td> --%>
			
			</tr>
			
					<%
					}
					} 
						%>
			
				<input type="hidden" id="flag" name="flag" value="<%=flag %>">
			
				</table>
		
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