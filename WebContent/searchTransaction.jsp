<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="payroll.Model.Lookup"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
	<%@page import="java.util.ArrayList"%>
<%@page import="java.util.ListIterator"%>
<%@page import="payroll.DAO.LeaveMasterHandler"%>
<%@page import="payroll.Model.LeaveMasterBean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy DTS3 </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<!--[if IE]>
<link rel="stylesheet" media="all" type="text/css" href="css/pro_dropline_ie.css" />
<![endif]-->

<link rel="stylesheet" href="css/screen.css" type="text/css"
	media="screen" title="default" />
	<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />		
	
		
<script type="text/javascript" src="js/jquery.datePicker.js"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>

		
 
<script language="javascript">


function validate()
{

	
	  
   var fromDate=document.searchform.frmdate.value;
   var toDate=document.searchform.todate.value;
     fromDate = fromDate.replace(/-/g,"/");
	 toDate = toDate.replace(/-/g,"/");
   
	 var EMPNO = document.getElementById("EMPNO").value;
     
		if (document.getElementById("EMPNO").value == "") {
			alert("Please Insert Employee Name");
			document.getElementById("EMPNO").focus();
			return false;
		}
		var atpos=EMPNO.indexOf(":");
		if (atpos<1)
		  {
		  alert("Please Select Correct Employee Name");
		  return false;
		  }
   
   if(document.searchform.frmdate.value=="")
	{
	       alert("please select the Fromdate");
	      document.searchform.frmdate.focus();
	      return false;  
	   
	   
	}
    if( document.searchform.todate.value=="")
   {
	      alert("please select the todate");
	      document.searchform.todate.focus();
	      return false;
	   }
  
 	var d1 = new Date(fromDate);
  	
  	var d2 =new  Date(toDate);
  	
  if (d1.getTime() > d2.getTime()) 
   {
	   alert("Invalid Date Range!\n fromdate Date can't be greater than TODate!");
	   document.searchform.todate.focus();
	   return false;
	   }
 
}
</script>
 
<style type="text/css">
.row:hover{
		background-color:#CCCCCC;
		cursor:pointer;
}

.style1 {color: #FF0000}
</style>

  
  <script>
	jQuery(function() {
          $("#EMPNO").autocomplete("list.jsp");
	});
</script>

<style type="text/css">
.ac_results {
	padding: 0px;
	border: 1px solid #84a10b;
	background-color: #84a10b;
	overflow: hidden;
	
}

.ac_results ul {
	width: 100%;
	list-style-position: outside;
	list-style: none;
	padding: 0;
	margin: 0;
}

.ac_results li {
	margin: 0px;
	padding: 2px 5px;
	cursor: default;
	display: block;
	color: #fff;
	font-family: verdana;
	cursor: pointer;
	font-size: 12px;
	line-height: 16px;
	overflow: hidden;
}

.ac_loading {
	background: white url('../images/indicator.gif') right center no-repeat;
}

.ac_odd {
	background-color: #84a10b;
	color: #ffffff;
}

.ac_over {
	background-color: #5a6b13;
	color: #ffffff;
}

.input_text {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	border: 1px solid #84a10b;
	padding: 2px;
	width: 150px;
	color: #000;
	background: white url(../images/search.png) no-repeat 3px 2px;
	padding-left: 17px;
}

#accordion {
	list-style: none;
	padding: 0 0 0 0;
	width: 170px;
}

#accordion li {
	display: block;
	background-color: #2c6da0;
	font-weight: bold;
	margin: 1px;
	cursor: pointer;
	padding: 35 35 35 7px;
	list-style: circle;
	-moz-border-radius: 10px;
	-webkit-border-radius: 10px;
	border-radius: 10px;
	height:30px;
	text-decoration: none;

	
}
#accordion li:hover
{

background-color:#74ACFF;
}

#accordion ul {
	list-style: none;
	padding: 0 0 0 0;
	display: none;
}

#accordion ul li {
	font-weight: normal;
	cursor: auto;
	padding: 0 0 0 7px;
	
}

#accordion a {
	text-decoration: none;
	color: #FFFFFF;
	
}

#accordion a:hover {
	text-decoration: underline;
	text-shadow: #FF9927;
	 
}
#accordion ul li.submenu
{
background-color: #66CCFF;
}
#accordion ul li.submenu:hover
{
background-color: #74ACFF;
}

</style>


 
<style type="text/css">
.row:hover{
		background-color:#CCCCCC;
		cursor:pointer;
}

.style1 {color: #FF0000}
</style>

</head>
<body style="overflow: hidden;"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 <% 
  String action=request.getParameter("action")==null?"":request.getParameter("action");
ArrayList<LeaveMasterBean> searchtran=null;
LookupHandler lh=new  LookupHandler(); 
 if(action.equals("transaction"))
 {
   searchtran =(ArrayList<LeaveMasterBean>)session.getAttribute("searchlist");
  
  String empno1= (String)request.getAttribute("rempno");
 }
  %>
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:scroll; max-height:80%; ">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<div id="content"  ><!--  start page-heading -->
			<div id="step-holder">
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no">1</div>
			<div class="step-dark-left"><a href="searchTransaction.jsp?action=search" >Search Leave </a></div>
			<div class="step-dark-right">&nbsp;</div>
			<div class="step-no-off">2</div>
			<div class="step-light-left"><a href="showLeaveDetails.jsp?action=show">Leave Apply</a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no">3</div>
			<div class="step-light-left"><a href="leaveCancel.jsp?action=first">Leave Cancel</a></div>
			<div class="step-dark-right">&nbsp;</div>
			
			<div class="step-no">4</div>
			<div class="step-light-left"><a href="sanction.jsp?action=first">Sanction leave</a></div>
			<div class="step-light-round">&nbsp;</div>
			<div class="clear"></div>
		
		</div>
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
            <% if(action.equals("search"))
    { 
    
    %>		
  <!-- This form display first navigation -->
  <!-- this form code is repeated for displaying blank form -->
  
 
		 <h3>Employee Search  Leave Transaction</h3>
			 <table border="1" align="center" bordercolor="#2f747e"><tr align="center"><td >
			<form  name="searchform" action="LeaveMasterServlet?action=search" method="Post" onSubmit="  return validate()">
			  <table border="1" id="customers" style="margin-top:10px;">
			  <tr><th>Search Transaction</th>
	                </tr>
                <tr class="alt">
                  <td width="678" height="29" align="center">Employee Name or Number
				  <input type="text" name="EMPNO"  id="EMPNO" onClick="showHide()" title="Enter Employee Name or Number"></td>
                </tr>
                <tr class="alt">
                  <td height="44">
				     <table> <tr class="alt">  <td>Fromdate</td> 
					 
<td bgcolor="#FFFFFF">
	<input name="frmdate" size="20" id="frmdate" type="text" onBlur="if(value=='')" readonly="readonly">&nbsp;<img src="images/cal.gif" align="absmiddle" style="vertical-align:middle; cursor:pointer;" onClick="javascript:NewCssCal('frmdate', 'ddmmmyyyy')" />
	
</td>
<td>Todate</td> <td bgcolor="#FFFFFF">
	<input name="todate"  size="20" id="todate" type="text" onBlur="if(value=='')" readonly="readonly">&nbsp;<img src="images/cal.gif" align="absmiddle" style="vertical-align:middle; cursor:pointer;" onClick="javascript:NewCssCal('todate', 'ddmmmyyyy')" />
	
</td><td><select name="lcode" id="lcode" style="width: 40px">
		<option selected="selected" value="0">All</option>
			<%
  			  ArrayList<Lookup> result3=new ArrayList<Lookup>();
    		  LookupHandler lkhp3= new LookupHandler();
    	      result3=lkhp3.getSubLKP_DESC("LEAVE");
 				for(Lookup lkbean : result3)
 					{
     				%>
      			<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
     		 	<%}%>
	</select></td> <td><input type="submit" value="Search"/></td> </tr>   </table>
				      
				  </td>
                </tr>
              </table>
			</form>	
			 
			<br>
 <center>
<table id="customers" width="845" align="center">
	<tr><td>
	     <table border="1" id="customers">
	              <tr class="alt" style="font-size:2">
	                <th width="70" height="20" >LeaveCD</th>
	             <th width="60">EmpNO</th>           			
	               <th width="65">TransDate</th>
	             <th width="81" align="right">TransType</th>
	               <th width="75">Appl No</th>
	                 <th width="80">TeleNo</th>
	                 <th width="75">Appl Date</th>
	                      <th width="75">From Date</th>
	                     <th width="75">To Date</th>
	                     <th width="75">Days</th>
	                    <th width="75">Purpose</th>
	                    <th width="66">SancAth</th>
	                 <th width="55">Status</th>
	             </tr>
	      </table>
		  
	</td></tr>
    <tr><td >
	 <div style="height:145px; overflow-y: scroll; width:auto" id="div1" >
	
	
	 <table  id="customers" align="center">
	
		<tr align="center"><td colspan="11" align="center">Currently no selected Transaction</td></tr>
		
	 </table>
	 </div>
	 </td></tr>
	 <tr bgcolor="#2f747e"><td>&nbsp;</td></tr>
	 </table>
	 </td></tr></table>
	 </center>

	<%} %>
			
	<% if(action.equals("transaction"))
    { 
    
    %>		 
			 <h3>Employee Search  Leave Transaction</h3>
			 <table border="1" align="center" bordercolor="#2f747e"><tr align="center"><td >
			<form  name="searchform" action="LeaveMasterServlet?action=search" method="Post" onSubmit="return validate()">
			 <table width="682" cellpadding="0" cellspacing="0" id="customers"align="center" style="margin-top:10px;">
	       <tr>
		  <th width="680">Search Transaction</th>
		   
        </tr>
   </table>
			  <table border="1" id="customers">
                <tr class="alt">
                  <td width="678" height="29"> Employee NO
				  <input type="text" name="EMPNO" id="EMPNO" size="30" title="Enter Customer Name" value="<%=request.getAttribute("empno")%>"  /></td>
                </tr>
                <tr class="alt">
                  <td height="44">
				     <table> <tr class="alt">  <td>Fromdate</td> <td bgcolor="#FFFFFF"><input name="frmdate" id="frmdate" type="text" value="<%=request.getAttribute("frmdate")%>" onBlur="if(value=='')" readonly="readonly" >&nbsp;<img src="images/cal.gif" align="absmiddle" style="vertical-align:middle; cursor:pointer;" onClick="javascript:NewCssCal('frmdate', 'ddmmmyyyy')" />
	
</td>
				     <td>Todate</td> <td bgcolor="#FFFFFF">
	<input name="todate" id="todate" type="text" value="<%=request.getAttribute("todate")%>" onBlur="if(value=='') " readonly="readonly" >&nbsp;<img src="images/cal.gif" align="absmiddle" style="vertical-align:middle; cursor:pointer;" onClick="javascript:NewCssCal('todate', 'ddmmmyyyy')" />
	
</td>
<td>
	<select name="lcode" id="lcode" style="width: 40px">
		<option selected="selected" value="0">All</option>
			<%
  			  ArrayList<Lookup> result3=new ArrayList<Lookup>();
    		  LookupHandler lkhp3= new LookupHandler();
    	      result3=lkhp3.getSubLKP_DESC("LEAVE");
 				for(Lookup lkbean : result3)
 					{
     				%>
      			<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
     		 	<%}%>
	</select>
</td>
 <td><input type="submit" value="Search"/></td> </tr>   </table>
				      
				  </td>
                </tr>
              </table>
			</form>
			 
			<br>
			
			
<!-- This Table show the leave Transaction -->
<center>
<table id="customers" width="845">
	<tr><td>
	     <table border="1" id="customers">
	              <tr class="alt" style="font-size:2">
	                <th width="70" height="20" >LeaveCD</th>
	             <th width="60">EmpNO</th>           			
	               <th width="65">TransDate</th>
	             <th width="81" align="right">TransType</th>
	               <th width="75">Appl No</th>
	                 <th width="80">TeleNo</th>
	                 <th width="75">Appl Date</th>
	                      <th width="75">From Date</th>
	                     <th width="75">To Date</th>
	                     <th width="75">Days</th>
	                     <th width="75">Purpose</th>
	                    <th width="66">SancAth</th>
	                 <th width="55">Status</th>
	             </tr>
	      </table>
		  
	</td></tr>
    <tr><td >
	 <div style="height:200px; overflow-y: scroll; width:auto" id="div1" >
	
	
	 <table  id="customers" align="center">
	 <% 
 
	 if(searchtran.size()!=0)
     {
	 
	   for(LeaveMasterBean leave:searchtran)
		{
		
		%>
		
	<tr  class="row"  bgcolor="#FFFFFF" >
	    <td width="65" ><%=lh.getLKP_Desc("LEAVE",leave.getLEAVECD()) %> </td>
	    <td width="65" > <%=leave.getEMPNO() %></td>
	    <td width="70" ><%=leave.getTRNDATE() %> </td>
	    <td width="70" ><%=leave.getTRNTYPE() %> </td>
	    <td width="80" ><%=leave.getAPPLNO() %> </td>
        <td width="75" ><%=leave.getLTELNO() %> </td>
	    <td width="75" ><%=leave.getAPPLDT() %> </td>
	    <td width="75" ><%=leave.getFRMDT() %> </td>
	    <td width="80" ><%=leave.getTODT() %> </td>
	    <td width="80" ><%=leave.getNODAYS() %> </td>
	    <td width="80" ><%=lh.getLKP_Desc("LPURP",leave.getLEAVEPURP()) %> </td>
	    <td width="70" ><%=leave.getSANCAUTH() %> </td>
	    <td width="40" ><%=leave.getSTATUS() %> </td>
	  
	</tr>
	 
		<%
		}
     }
	 else
	 {
		%>
		<tr align="center"><td colspan="11" align="center">Their Is No Any Record Found</td></tr>
		<%
		}
	
	 
	%>
		
	 </table>
	 
	
	 </div>
	
	 </td>
	 </tr>
	<tr bgcolor="#2f747e"><td>&nbsp;</td></tr>
	</table>
	</td></tr></table>
	</center>	
	 

	<%} %>
			
		 
			
			<div>
			 
			</center>
			</div>
			
	 
		</div>
			<!--  end table-content  -->
	
			<div class="clear"></div>
		 
		  
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