<%@page import="payroll.Model.LeaveMassBean"%>
<%@page import="payroll.Model.ItStandardBean"%>
<%@page import="payroll.Model.CodeMasterBean"%>
<%@page import="payroll.DAO.CodeMasterHandler"%>
<%@page import="payroll.Core.ReportDAO"%>
<%@page import="payroll.DAO.*"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy DTS3 </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<script src="js/filter.js"></script>
<script type="text/javascript" src="js/jquery.datePicker.js"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<!--[if IE]>
<link rel="stylesheet" media="all" type="text/css" href="css/pro_dropline_ie.css" />
<![endif]-->
<style type="text/css">
<!--
body,tr,td,th {
	font-family: Times New Roman;
    font-size: 13px;
}
-->
</style>
<%
	String action ="";
	try
	{
		action = request.getAttribute("action").toString();
	}
	catch(Exception e)
	{
		
	}
/* LeaveMasterHandler lmh=new LeaveMasterHandler();
LookupHandler lh=new  LookupHandler(); 
ArrayList<LeaveMassBean>  ltype=new ArrayList<LeaveMassBean> ();
ltype=lmh.getleavetypeList("select * from leavemass order by srno,EFFDATE"); */

LeaveMasterHandler lmh=new LeaveMasterHandler();
LookupHandler lh=new  LookupHandler(); 
ArrayList<LeaveMassBean>  ltype=new ArrayList<LeaveMassBean> ();
EmpAttendanceHandler empAttendanceHandler=new EmpAttendanceHandler();
String todaysDate=empAttendanceHandler.getServerDate();
String beginningOfYear=ReportDAO.BOYForJanToDec(todaysDate);
//String sql="select * from leavemass where status='A' and EFFDATE>= ' "+beginningOfYear+" ' order by EFFDATE desc,srno desc";
String sql="select * from leavemass where status='A' and EFFDATE>= ' "+beginningOfYear+" ' order by EFFDATE desc,srno ";

System.out.println(sql);
ltype=lmh.getleavetypeList(sql);

%>

<script type="text/javascript">
	function checkAction()
	{
		var action = document.getElementById("action").value;
		if(action!="")
		{
			alert(action);
		}
	}
	
	function validate()
	{
		var r = document.getElementsByName("type");
		var c = -1;
		for(var i=0; i < r.length; i++)
		{
		   if(r[i].checked) 
		   {
		      c = i; 
		   }
		}
		if (c == -1)
		{
			alert("Please select Leave Type");
			return false;
		}
		if(document.getElementById("crdmonth").value==""){
			alert("Please select Month & Date To Credit");
			return false;
		}
		if(numList!="")
		{
			document.getElementById("emplist").value=numList;
			var flag = confirm("Are you sure to Auto Credit the Leaves to Selected Employee(s)");
			if(flag)
			{
				
				document.getElementById("process").hidden=false;
				return flag;	
			}
			else{
				return false;
			}
		}
		else
		{
			alert("No Employees Selected");
			return false;
		}
	}
	
	function getFilter1(forWhat)
	{
	debugger;
		 if(getFilt(forWhat))
		{
			    //document.getElementById('autocredit1').style.visibility='visible';
			   // document.getElementById('crRow').style.display='block';
			    
		}
		// document.getElementById('crRow').style.display='block';
	}

</script>
</head>
<body style="overflow: hidden;" onload="checkAction()" >
 <input type="hidden" id="action" value="<%=action%>">
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:auto; max-height:82%; ">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1> Auto Leave Credit</h1>
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
			<form action="LeaveMasterServlet?action=autoCredit" method="POST" onsubmit="return validate()">
			<input type="hidden" id="emplist" name="emplist"/>
			<center>
				<table id="customers">
				<tr>
					<td>
						<table id="customers">
				       		<tr class="alt">
					      	  <th width="0">SR NO</th>
							  <th width="0">Effective Date</th>
							  <th width="0">Leave Code</th>
							  <th width="0">frequency</th>
							  <th width="0">Credit Limit</th>
							  <th width="0">Max Cumulative</th>
							  <th width="0">Max Carry Forward</th>
							  <th width="0">Begin Date</th>
							  <th width="65">End Date</th>
							</tr> 
						<%
							 
							if(ltype.size()!=0)
							{
								
							     for(LeaveMassBean bean:ltype)
							     {
							        String key =bean.getEFFDATE()+":"+bean.getSRNO();
							      String FRQ=bean.getFREQUENCY();
							     
							      if(FRQ.equalsIgnoreCase("Y"))
							      {
							    	  FRQ="Yearly";
							      }
							      else if(FRQ.equalsIgnoreCase("M"))
							      {
							    	  FRQ="Monthly";
							      }
							      else if(FRQ.equalsIgnoreCase("H"))
							      {
							    	  FRQ="Half Yearly";
							      }
							      else if(FRQ.equalsIgnoreCase("Q"))
							      {
							    	  FRQ="Quarterly";
							      }
						        
						%>
							      <tr class="row" align="center">
							      	<td width="35"><%=bean.getSRNO() %></td>
								     <td width="87"><%=bean.getEFFDATE() %></td>
								     <td width="68"><%=lh.getLKP_Desc("LEAVE",bean.getLEAVECD()) %></td>
								     <td width="63"><%=FRQ %></td>
								     <td width="73"><%=bean.getCRLIM() %></td>
								     <td width="98"><%=bean.getMAXCUMLIM() %></td>
								     <td width="117"><%=bean.getMAXCF() %></td>
								     <td width="65"><%=bean.getFBEGINDATE() %></td>
								     <td width="0"><%=bean.getFENDDATE()%></td>
								  </tr>
						<%
						      	}
							}
					   		else
							{  
						%>
							 <tr>
							     <td colspan="12"> Their is no any record found</td>
							  </tr>
						<%
							}
						%>
						</table>
					
				</td>
				</tr>
					<tr>
						<td align="center">
							<div style="width:100%;">		
							<div style="float:left; width:60%;">
							<fieldset style="width: 500px;height: 50px;padding-top: 10px;"><legend>Select Leave Type</legend>
							<%
							int count=1;
								for(LeaveMassBean LMB:ltype)
								{
							%>
							<%-- <%=LMB.getSRNO()%>:<%=lh.getLKP_Desc("LEAVE",LMB.getLEAVECD())%> --%>
							 <%=count++%>: <%=lh.getLKP_Desc("LEAVE",LMB.getLEAVECD())%> 
							<input type="radio" value="<%=LMB.getSRNO() %>"  name="type">
							<input type="hidden" value="<%=LMB.getSRNO() %>"  name="type1" id="type1"/>
							<input type="hidden" id="<%=count-1%>" value="<%=LMB.getLEAVECD() %>">
							&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;
							<%
								}
							%>
							</fieldset>
							</div>
							<div style=" width:10%;">
							<fieldset style="width: 200px;height: 50px;padding-top: 10px;"><legend> Select Credit Date </legend>
								<input name="crdmonth"  size="18" id="crdmonth" type="text" readonly="readonly" value="<%=ReportDAO.BOYForJanToDec( ReportDAO.getSysDate()) %>"
								onFocus="if(value=='dd-mmm-yyyy') {value=''}"
								onBlur="if(value=='') {value='';}">&nbsp;
								<!-- <img id="dateimg"
								src="images/cal.gif" align="middle"
								style="vertical-align: middle; cursor: pointer;"
								onClick="javascript:NewCssCal('crdmonth', 'ddmmmyyyy')" /> -->
							</fieldset>
							</div></div>
						</td>
					</tr>	
					<tr>
						<td align="center">
							<div id="displayDiv" style="height: 200px"></div>
							<div id="countEMP">0 Employees Selected</div>
							<input type="button" class="myButton" value="Cancel All" onclick="cancelAll()">
							<!-- <input type="button" value="Select Employees" onclick="getFilter('Leave','')"> -->
							<input type="button" class="myButton" value="Select Employees" onclick="getFilter1('Leave')">
							<!-- <input type="button" value="Add More Employee" onclick="addMoreEmp('all','')"> -->
							<input type="button" class="myButton" value="Add More Employee" onclick="addMoreEmp('Leave','crdmonth')">
						
						</td>
					</tr> 	
					<tr >       
						<td align="center" >
							<input class="myButton" type="submit" value="Go for Auto Credit">
						</td>
					</tr>
				</table>			
			</center>	
			</form>
			</div>
			<div id="process"  style="z-index: 1000;background-color:white;height: 100%;width: 100%;top: 0px;left: 0px;position: fixed;opacity:0.8;" hidden=true; >
				<div align="center" style="padding-top: 20%;">
				
				<h1> Please Wait it takes few minutes....Be Patience</h1>
				
				<img alt="" src="images/process.gif">
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