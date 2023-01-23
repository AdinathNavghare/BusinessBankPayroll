<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Date"%>
 <%@page import="java.text.SimpleDateFormat"%>
<%@page import="payroll.DAO.LeaveMasterHandler"%>
<%@page import="payroll.DAO.EmpOffHandler"%>
<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="payroll.Model.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy DTS3 </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />



<script type="text/javascript" src="js/datetimepicker.js"></script>


<!--   <script type="text/javascript">
    $(document).ready(function() {
        $.validator.addMethod("todate", function(value, element) {
            var frmdate = $('.frmdate').val();
            return Date.parse(frmdate) <= Date.parse(value) || value == "";
        }, "* End date must be after start date");
        $('#form1').validate();
    });
    
</script>
-->
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
      alert("please Select the transaction date");
      document.leaveForm1.tdate.focus();
      return false;
    }
	if(document.leaveForm1.empno.value=="")
	     {

		   alert("please enter the empno");
		   document.leaveForm1.empno.focus();
		   return false;
	      }
	
	
	 if ( ( leaveForm1.type[0].checked == false ) && ( leaveForm1.type[1].checked == false ) ) 
		{
		
		 alert(" please select  whether Debit Or Credit");
		 document.leaveForm1.type.focus();
	      return false;
		
		
		}
	 if(document.leaveForm1.leavecode.value=="Default")
		{
		 alert(" please select the leavecode");
	      document.leaveForm1.leavecode.focus();
	      return false;
		
		}
	
	 if( document.leaveForm1.frmdate.value=="")
      {

	      alert("please enter fromdate");
	      document.leaveForm1.frmdate.focus();
	      return false;
	    }
	 if( document.leaveForm1.todate.value=="")
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
    if(document.leaveForm1.branchcode.value=="Default")
	{
	 alert(" please select the branchcode");
    document.leaveForm1.branchcode.focus();
    return false;
	
	}
    if(document.leaveForm1.leavepurpose.value=="Default")
	{
	 alert(" please select the leavepurpose");
    document.leaveForm1.leavepurpose.focus();
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
	 if( document.leaveForm1.appdate.value=="dd-mmm-yyyy" ||document.leaveForm1.appdate.value=="" )
    {

	      alert("please enter application Date");
	      document.leaveForm1.appdate.focus();
	      return false;
	    }
	 
	 if(document.leaveForm1.nodays.value=="")
	    { alert("please Enter Number of leaves Days");
		      document.leaveForm1.nodays.focus();
		      return false;
		    }
	 if( document.leaveForm1.addDuringleave.value=="")
		 {
		 alert("please enter addDuringleave");
	      document.leaveForm1.addDuringleave.focus();
	      return false;
		 
		 
		 }
	if(document.leaveForm1.telephone.value=="")
		{
		  alert("please enter telephone no");
	      document.leaveForm1.telephone.focus();
	      return false;
		
		}
	if(isNaN(document.leaveForm1.telephone.value))
	  { 
		   alert("please enter only numeric value");
	       document.leaveForm1.telephone.focus();
	       return false;
		
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
<% 
 String action=request.getParameter("action")==null?"":request.getParameter("action"); 
LookupHandler lh=new  LookupHandler(); 
 ArrayList<LeaveMasterBean> listleave =(ArrayList<LeaveMasterBean>)session.getAttribute("leaveList");
  
  
 
 
  %>


</head>
<body style="overflow: hidden;"> 
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
			<div class="step-no-off">1</div>
			<div class="step-light-left"><a href="searchTransaction.jsp?action=search" >Search Leave </a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no">2</div>
			<div class="step-dark-left"><a href="showLeaveDetails.jsp?action=show">Leave Apply</a></div>
			<div class="step-dark-right">&nbsp;</div>
			<div class="step-no-off">3</div>
			<div class="step-light-left"><a href="leaveCancel.jsp?action=first">Leave Cancel</a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no">4</div>
			<div class="step-light-left"><a href="sanction.jsp?action=first">Sanction leave</a></div>
			<div class="step-light-round">&nbsp;</div>
			<div class="clear"></div>
			<div class="clear"></div>
			
		
		</div>
	</div>
	<!-- end page-heading -->

	<table border="0"  cellpadding="0" cellspacing="0" id="content-table">
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
			    <%if(action.equals("true"))
{
%><p style="color: red; size: 10;">Leave application successfully submitted</p>
<%} %>


<form  id="form1" name="leaveForm1" action="leave?action=addleave" method="post" onSubmit=" return validate()">
<h3>Employee Leave Details</h3>


<table width="704" cellpadding="0" cellspacing="0" bordercolor="#000000"
	id="customers" align="center" height="128" border="0">
<tr>
		<th colspan="2">Leave Details</th>

	</tr>
	<tr class="alt">
	<td width="382">&nbsp; &nbsp;Employee Number&nbsp;&nbsp;    
	  <input type="text" name="empno" id="empno" readonly="readonly"
					value="<%=session.getAttribute("sempno") %>"/></td>
				<td width="322"> &nbsp; &nbsp;Employee Name &nbsp; &nbsp;&nbsp;  &nbsp;  
				  <input type="text" name="empno" id="empno" readonly="readonly"
					value="<%=session.getAttribute("ename") %>"/></td>
				
				
	
	</tr>
	<tr class="alt">
		<td width="382">
		<table id="customers">
			<tr>
				<td colspan="2">Transaction Details</td>
			</tr>
			<tr>
				
				<%
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");

				 Date date = new Date();
				 String currentdate = dateFormat.format(date);  
				%>
				<td>Date</td>
				<td><input name="tdate" id="tdate" type="text" value="<%=currentdate %>" readonly="readonly" ></td>
			</tr>

			<tr>
				<td>Type</td>
				<td><input type="radio" name="type" value="D" checked="checked">Debit <input type="radio" name="type" value="C">Credit</td>
			</tr><tr class="alt">
				
			</tr>
			<tr class="alt">
				
			</tr>
			
			<tr>
				<td>Leave code</td>
				<td>
				<select name="leavecode" id="leavecode" style="width: 140px">
								<option selected="selected" value="Default">Select</option>
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
					<!-- <select name="leavecode" style="width: 140px">
					<option selected="selected" value="Default">Select</option>
					<option value="SL">SL</option>
					<option value="PL"> PL</option>
					<option value="CL">CL</option>
				</select> -->
				</td>
			</tr>
			<tr>
				<td>From Date</td>
				<td><input name="frmdate" id="frmdate" type="text" readonly="readonly">&nbsp;<img
					src="images/cal.gif" align="middle"
					style="vertical-align: middle; cursor: pointer;"
					onClick="javascript:NewCssCal('frmdate', 'ddmmmyyyy')" /></td>
			</tr>

			
			<tr>
				<td>To Date</td>
				<td><input name="todate" id="todate" type="text" readonly="readonly">&nbsp;<img
					src="images/cal.gif" align="middle"
					style="vertical-align: middle; cursor: pointer;"
					onClick="javascript:NewCssCal('todate', 'ddmmmyyyy')" /></td>
			</tr>
			<tr>
				<td>Number of Days</td>
				<td><input type="text" name="nodays" id="nodays" maxlength="4"/></td>
			</tr>
			<tr>
				<td>branch Code</td>
				<td><select name="branchcode" style="width: 140px">
					<option value="Default">Select</option> 
					<%
    						  ArrayList<Lookup> resultbr = new ArrayList<Lookup>();
    						 EmpOffHandler ekhp2= new EmpOffHandler();
    						 resultbr =ekhp2.getGradeBranchList("prj");
 							for(Lookup lkbean : resultbr)
 							{
     						%>
      						<option value="<%=lkbean.getLKP_SRNO()%>" ><%=lkbean.getLKP_DESC()%></option>  
     					 		
     					 	<%	}%>
				</select></td>
			</tr>
			<tr>
				<td>Leave Purpose</td>
				<td><select name="leavepurpose" style="width: 140px">
					<option value="Default">Select</option> 
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

				</select></td>
			</tr>
			<tr>
				<td width="109">leave Reason</td>
				<td width="250"><input type="text" name="lreason" /></td>
			</tr>
			<tr>
				<td width="109">Sanctioned By</td>
				 	
				<td width="250"><select name="sanctionby" style="width: 140px">
					<option value="Default">Select</option> 
					<%
    						 ArrayList<Lookup> sanch =new ArrayList<Lookup>();
    						//LookupHandler lkhp1 = new LookupHandler();
    						sanch = lkhp1.getSubLKP_DESC("SAUTH");
    						for(Lookup lkbean1 : sanch)
 							{
 							
 							%>
      						<option value="<%=lkbean1.getLKP_SRNO()%>"><%=lkbean1.getLKP_DESC()%></option>  
     					 	<%
     					 	}
     					 	%>
				</select></td>
			</tr>
			<tr>
				<td width="109" height="29">Substitute (Yes/NO)</td>
				<td width="250"><select name="subsitute" style="width: 140px">
					<option>Y</option>
					<option>N</option>

				</select></td>
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
				<td width="176"><input type="text" name="appno"  /></td>
			</tr>
			<tr>
				<td>Date</td>
				<td><input name="appdate" id="appdate" type="text"
					onBlur="if(value=='') {value='dd-mmm-yyyy';}" size="20px" readonly="readonly">&nbsp;<img
					src="images/cal.gif" align="absmiddle"
					style="vertical-align: middle; cursor: pointer;"
					onClick="javascript:NewCssCal('appdate', 'ddmmmyyyy')" /></td>
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
					value="Save" /><input type="reset" value="Cancel"  /></td>
			</tr>


		</table>
		</td>


	</tr>

<tr bgcolor="#2f747e"><td colspan="2">&nbsp;</td></tr>
</table>
</form>
</center>
<br>
<center>
<table id="customers" align="center">

	<tr>
		<td>
		<table>

			<tr class="alt" style="font-size: 2">
				<th width="88" height="20">LeaveCD</th>
				<th width="85">EmpNO</th>
				<th width="85">TransDate</th>
				<th width="85" align="right">TransType</th>
				<th width="78">Appl No</th>
				<th width="68">TeleNo</th>
				<th width="69">Appl Date</th>
				<th width="70">From Date</th>
				<th width="70">To Date</th>
				<th width="70">Days</th>
				<th width="75">SancAth</th>
				<th width="73">Status</th>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td>
		<div style="height: 145px; overflow-y: scroll; width: auto" id="div1">
		<table border="1" id="customers">

			<% for(LeaveMasterBean leave:listleave)
		{
		
		%>
			<tr class="row" bgcolor="#FFFFFF">
				<td width="90"><%=lh. getLKP_Desc("LEAVE",leave.getLEAVECD())%></td>
				<td width="80"><%=leave.getEMPNO() %></td>
				<td width="90"><%=leave.getTRNDATE() %></td>
				<td width="80"><%=leave.getTRNTYPE() %></td>
				<td width="80"><%=leave.getAPPLNO() %></td>
				<td width="70"><%=leave.getLTELNO() %></td>
				<td width="70"><%=leave.getAPPLDT() %></td>
				<td width="70"><%=leave.getFRMDT() %></td>
				<td width="70"><%=leave.getTODT() %></td>
				<td width="70"><%=leave.getNODAYS() %></td>
				<td width="70"><%=leave.getSANCAUTH() %></td>
				<td width="70"><%=leave.getSTATUS() %></td>

			</tr>
			<%} %>
		</table>
		</div>

		</td>
	</tr>
	<tr bgcolor="#2f747e">
		<td>&nbsp;</td>
	</tr>
</table>
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
</div>
<!--  end content -->
<div class="clear">&nbsp;</div>
</div>
<!--  end content-outer........................................................END -->

<div class="clear">&nbsp;</div>
    

</body>
</html>