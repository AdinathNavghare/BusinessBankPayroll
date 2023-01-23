<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="payroll.Model.SearchBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Attendance Details </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<script type="text/javascript">
	function search(){
		var date = document.getElementById("date").value;
		if(date==""||date==""){
			document.getElementById("date").focus;
			return false;
		}
		window.location.href="DbfInsert_DataServlet?action=getdata&date="+date;
		//window.location.href="SearchRecords.jsp";
	}
</script>
<style type="text/css">
.row:hover {
	background-color: #CCCCCC;
	cursor: pointer;
}

</style>
<%
	String action = request.getParameter("action")==null?"":request.getParameter("action");
	ArrayList<SearchBean> list = new ArrayList<SearchBean>();
	Date date = new Date();
	SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
	String dt = format.format(date);
	
	SimpleDateFormat fromformat = new SimpleDateFormat("yyyy-MM-dd");
	fromformat.setLenient(false);%>
</head>
<body style="overflow: hidden;"> 
<%@include file="mainHeader.jsp"%>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp"%>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:auto; max-height:80%; ">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Search Attendance Details</h1>
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
			<div id="table-content" align="center"  style="overflow-y:auto; max-height:480px;">
			
				<table id="customers" width="553" align="center">
				 <tr> <th colspan="4">Search Attendance</th></tr>
					<tr class="alt">
						<td><b>Select Month & Year </b>&nbsp;&nbsp;&nbsp;
						<%if(action.equalsIgnoreCase("show")){%>
						<input name="date" id="date" type="text" readonly="readonly" value="<%=request.getAttribute("date")%>"size="14" onfocus="search()">&nbsp;<img
						src="images/cal.gif" align="absmiddle"
						style="vertical-align: middle; cursor: pointer;"
						onClick="javascript:NewCssCal('date', 'ddmmmyyyy')" />
						<%}else{ %>
						<input name="date" id="date" type="text" readonly="readonly" size="14" onfocus="search()">&nbsp;<img
						src="images/cal.gif" align="absmiddle"
						style="vertical-align: middle; cursor: pointer;"
						onClick="javascript:NewCssCal('date', 'ddmmmyyyy')" />
						<%} %>
						</td>
					</tr>
					
				</table>
				<% 
				if(action.equalsIgnoreCase("show")){
					list = (ArrayList<SearchBean>)request.getAttribute("list");
				%>
				<br/>
				<table border="1" id="customers">
					<tr class="alt">
						<th>ID </th>
						<th>EMP_NAME </th>
						<th>MONTH</th>
						<th>MON_DAYS</th>
						<th>PR_DAYS</th>
						<th>ABS_DAYS</th>
						<th>WO_DAYS</th>
						<th>PAID_DAYS</th>
						<!-- <th>EO_CNT</th>
						<th>EO_HRS</th>
						<th>OT_CNT</th>
						<th>OT_HRS</th> -->
						<th>WRK_HRS</th>
					</tr>
				<% for(SearchBean sb : list){ %>
					<tr class="row" bgcolor="#FFFFFF" align="center">
						<td><%=sb.getID() %></td>
						<td align="left"><%=new LookupHandler().getLKP_Desc("ET", sb.getEmpno()) %></td>
						<td><%=sb.getMonth() %></td>
						<td><%=sb.getMON_DAY() %></td>
						<td><%=sb.getSPR_CNT() %></td>
						<td><%=sb.getABS_CNT() %></td>
						<td><%=sb.getWO_CNT() %></td>
						<td><%=sb.getPAID_DAYS() %></td>
						<%-- <td><%=sb.getEO_CNT() %></td>
						<td><%=sb.getEO_HRS() %></td>
						<td><%=sb.getOT_CNT() %></td>
						<td><%=sb.getOT_HRS() %></td> --%>
						<td align="right"><%=sb.getWRK_HRS() %></td>
					</tr>
				<%} if(list.isEmpty()){ %>
					<tr><td colspan="9">No Records Available for This Month .</td></tr>
				<%} %>
				</table>
			<%} %>
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