<%@page import="javax.xml.crypto.Data"%>
<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="payroll.DAO.EmpOffHandler"%>
<%@page import="payroll.Model.Lookup"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.ArrayList"%>
<%@page import="java.util.ListIterator"%>
<%@page import="payroll.DAO.LeaveMasterHandler"%>
<%@page import="payroll.Model.LeaveMasterBean"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>



   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<script type="text/javascript" src="js/datetimepicker.js"></script>
<title>&copy DTS3</title>
<% 

String empno = session.getAttribute("EMPNO").toString(); 
String action = request.getParameter("action")==null?"":request.getParameter("action");
LeaveMasterHandler lmh = new LeaveMasterHandler();
/*  */

Date dt1 = new Date();
SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
String dt = format.format(dt1);
%>

<script language="javascript">
function validate()
{
  var fromDate=document.leaveForm1.frmdate.value;
  var toDate=document.leaveForm1.todate.value;
  var appldate=document.leaveForm1.appdate.value; 
  
    fromDate = fromDate.replace(/-/g,"/");
	toDate = toDate.replace(/-/g,"/");
	appldate=appldate.replace(/-/g,"/");
	 
	if(document.leaveForm1.tdate.value=="")
    {
      alert("please enter the transaction date");
      document.leaveForm1.tdate.focus();
      return false;
    }
	
	else if ( ( leaveForm1.type[0].checked == false ) && ( leaveForm1.type[1].checked == false ) ) 
		{
		
		 alert(" please select  whether Debit Or Credit");
	     
	      return false;
		
		
		}
	else if(document.leaveForm1.leavecode.value=="Default")
		{
		 alert(" please select the leavecode");
	      document.leaveForm1.leavecode.focus();
	      return false;
		
		}
	
	else if( document.leaveForm1.frmdate.value=="")
      {

	      alert("please enter fromdate");
	      document.leaveForm1.frmdate.focus();
	      return false;
	    }
	else if( document.leaveForm1.todate.value=="")
    {

	      alert("please enter todate");
	      document.leaveForm1.todate.focus();
	      return false;
	    }

    var d1 = new Date(fromDate);
  	
  	var d2 =new  Date(toDate);
  	var d3=new Date(appldate);
  	var msofaDay=1000*60*60*24;
  	var days = Math.floor((d2.getTime()- d1.getTime())/(msofaDay));
 
  	
  if (d1.getTime() > d2.getTime())
  {
		   alert("Invalid Date Range!\n fromdate Date cannot greater than TODate!")
		   document.leaveForm1.todate.focus();
		   return false;
  }
  
  if(d1.getTime() < d3.getTime())
	  {
	  
	   alert(" fromdate can not be Lesss than applicationdate");
	   document.leaveForm1.todate.focus();
	   return false;
	  
	  }
 
  if(days > 180)
  {
	  alert(" leave can not apply more than 180 days");
	   document.leaveForm1.todate.focus();
	   return false;  
	  
  }
  
 if(document.leaveForm1.lreason.value=="")
    {
          alert("please enter leave reason");
	      document.leaveForm1.lreason.focus();
	      return false;
	    }
	else if( document.leaveForm1.appno.value=="")
    {

	      alert("please enter applicationNo");
	      document.leaveForm1.appno.focus();
	      return false;
	    }
	else if( document.leaveForm1.appdate.value=="")
    {

	      alert("please enter application Date");
	      document.leaveForm1.appdate.focus();
	      return false;
	    }
	else if( document.leaveForm1.statement.value=="")
    {

	      alert("please enter statement");
	      document.leaveForm1.statement.focus();
	      return false;
	    }
	
}
</script>
</head>
<body style="overflow:hidden;"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
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
			<div class="step-dark-left"><a href="empleave.jsp" >Leave </a></div>
			<div class="step-dark-right">&nbsp;</div>
			<div class="step-no-off">2</div>
			<div class="step-light-left"><a href="empLeaveHistory.jsp">Leave Balance</a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">3</div>
			<div class="step-light-left"><a href="empLeaveCancel.jsp">Leave Cancel</a></div>
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
		
				<center>	
					<div id="table-content">
			
			<center>
			    <%if(action.equals("true"))
{
%><p style="color: red; size: 10;">Leave application succesfully submited</p>
<%} %>


<form  id="form1" name="leaveForm1" action="leave?action=addleave" method="post" onSubmit=" return validate()">
<h3>Employee Leave Details</h3>


<table width="704" cellpadding="0" cellspacing="0" bordercolor="#000000"
	id="customers" align="center" height="128" border="0">
<tr>
		<th colspan="2">Leave Details <input type="hidden" id="empno" name="empno" value="<%=empno %>" ><input type="hidden" id="user" name="user" value="employee" ></th>

	</tr>
	
	<tr class="alt">
		<td width="382">
		<table id="customers">
			<tr>
				<td colspan="2">Transaction Details</td>
			</tr>
			<tr>
				<td>Date</td>
				<td><input name="tdate" id="tdate" type="text" value="<%=dt %>" /></td>
			</tr>

			
			<tr class="alt">
				
			</tr>
			<tr class="alt">
				
			</tr>
			
			<tr>
				<td>Leave code</td>
				<td><select name="leavecode" style="width: 140px">
					<option>SL</option>
					<option>PL</option>
					<option>CL</option>
				</select></td>
			</tr>
			<tr>
				<td>From Date</td>
				<td><input name="frmdate" id="frmdate" type="text"
				    onBlur="if(value=='') {value='dd-mmm-yyyy';}" readonly="readonly">&nbsp;<img
					src="images/cal.gif" align="absmiddle"
					style="vertical-align: middle; cursor: pointer;"
					onClick="javascript:NewCssCal('frmdate', 'ddmmmyyyy')" /></td>
			</tr>

			<tr>
				<td>To Date</td>
				<td><input name="todate" id="todate" type="text"
				    onBlur="if(value=='') {value='dd-mmm-yyyy';}" readonly="readonly">&nbsp;<img
					src="images/cal.gif" align="absmiddle"
					style="vertical-align: middle; cursor: pointer;"
					onClick="javascript:NewCssCal('todate', 'ddmmmyyyy')" /></td>
			</tr>
			<tr>
				<td>branch Code</td>
				<td><select name="branchcode" id="branchcode" >  
      					 <option value="none">Select</option>  
    						<%
    						
  							  ArrayList<Lookup> resultbr = new ArrayList<Lookup>();
    						 EmpOffHandler ekhp2= new EmpOffHandler();
    						 resultbr =ekhp2.getGradeBranchList("BRANCH");
 							for(Lookup lkbean : resultbr)
 							{
     						%>
      						<option value="<%=lkbean.getLKP_SRNO()%>" ><%=lkbean.getLKP_DESC()%></option>  
     					 		
     					 	<%	}%>
     			 </select></td>
			</tr>
			<tr>
				<td>Leave Purpose</td>
				<td><select name="leavepurpose" id="leavepurpose" >  
      					 <option value="none">Select</option>  
    						<%
    						 ArrayList<Lookup> getresult1 =new ArrayList<Lookup>();
    						LookupHandler lkhp1 = new LookupHandler();
    						getresult1=lkhp1.getSubLKP_DESC("LPURP");
    						for(Lookup lkbean : getresult1)
 							{
 							%>
      						<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
     					 	<%
     					 	}
     					 	%>
     			 </select>
				</td>
			</tr>
			<tr>
				<td width="109">leave Reason</td>
				<td width="250"><input type="text" name="lreason" /></td>
			</tr>
			
		</table>
		</td>
		<td width="322" valign="top">
		<table>
			<tr>
				<td colspan="2">Application Details</td>
			</tr>
			<tr>
				<td width="128">Application NO</td>
				<td width="176"><input type="text" name="appno" /></td>
			</tr>
			<tr>
				<td>Date</td>
				<td><input name="appdate" id="appdate" type="text" value="<%=dt%>"/></td>
			</tr>
			<tr>
				<td height="29">Statement Of leave</td>
				<td><input type="text" name="statement" /></td>
			</tr>
			<tr>
				<td height="29">Address During Leave</td>
				<td><input type="text" name="addDuringleave" /></td>
			</tr>
			<tr>
				<td height="29">Telephone NO</td>
				<td><input type="text" name="telephone" /></td>
			</tr>
			<tr>
				<td height="31" colspan="2" align="center"><input type="submit"
					value="Save" /><input type="reset" value="Cancel" onClick="window.location = 'empleave.jsp'" /></td>
			</tr>


		</table>
		</td>


	</tr>

<tr bgcolor="#92b22c"><td colspan="2">&nbsp;</td></tr>
</table>
</form>
</center>
<br>
<center>
<table id="customers" align="center">

	<tr>
		<td>
		<table border="1" id="customers">
							<tr class="alt" style="font-size: 2">
				     			<th width="68" height="20">LeaveCD</th>
								<th width="62">EmpNo</th>
								<th width="70">TransDate</th>
								<th width="69" align="right">TransType</th>
								<th width="81">Appl No</th>
								<th width="77">TeleNo</th>
								<th width="73">Appl Date</th>
								<th width="75">From Date</th>
								<th width="80">To Date</th>
								<th width="129">SancAth</th>
								<th width="55">Status</th>
							</tr>
						</table>
		</td>
	</tr>
	<tr>
		<td>
		<div style="height: 145px; overflow-y: scroll; width: auto" id="div1">
		<table border="1" id="customers">

			<%
			ArrayList<LeaveMasterBean> leaveballist = lmh.leaveDisplay(Integer.parseInt(empno));
			 for(LeaveMasterBean leave:leaveballist)
		{
			
		%>	<tr class="row" bgcolor="#FFFFFF">
						<td width="65"><%=leave.getLEAVECD() %></td>
						<td width="65"><%=leave.getEMPNO() %></td>
						<td width="70"><%=leave.getTRNDATE() %></td>
						<td width="70"><%=leave.getTRNTYPE() %></td>
						<td width="80"><%=leave.getAPPLNO() %></td>
						<td width="75"><%=leave.getLTELNO() %></td>
						<td width="75"><%=leave.getAPPLDT() %></td>
						<td width="75"><%=leave.getFRMDT() %></td>
						<td width="80"><%=leave.getTODT() %></td>
						<td width="130"><%=leave.getSANCAUTH() %></td>
						<td width="40"><%=leave.getSTATUS() %></td>
					</tr>
			<%} %>
		</table>
		</div>

		</td>
	</tr>
	<tr bgcolor="#92b22c">
		<td>&nbsp;</td>
	</tr>
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
</div>
<!--  end content -->
<div class="clear">&nbsp;</div>
</div>
<!--  end content-outer........................................................END -->

<div class="clear">&nbsp;</div>
    

</body>
</html>