<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
	<%@page import="payroll.Model.GradeBean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Grade Management</title>
<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>
<link rel="stylesheet" href="css/screen.css" type="text/css"
	media="screen" title="defaulrt" />
<link rel="stylesheet" type="text/css" href="datepickr.css" />
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>

<style type="text/css">
<!--
body,td,th {
	font-family: Georgia;
	font-size: 14px;
}
-->
</style>
<%
	GradeBean GB = new GradeBean();
	int clr=0;
	try
	{
		String action = request.getParameter("action");
		if(action.equalsIgnoreCase("show"))
		{
			GB = (GradeBean)request.getAttribute("detail");
			clr=1;
		}
		else if(action.equalsIgnoreCase("saved"))
		{
			clr=2;
		}
		else if(action.equalsIgnoreCase("deleted"))
		{
			clr=3;
		}
		else if(action.equalsIgnoreCase("notDel"))
		{
			clr=4;
			GB = (GradeBean)request.getAttribute("detail");
		}
	}
	catch(Exception e)
	{
	}

%>
<script type="text/javascript">

	function getDetails()
	{
		var post = document.getElementById("postcd").value;
		var effdt = document.getElementById("effdt").value;
		if(document.getElementById("postcd").value == "")
		{
			alert("Please Enter Post Code");
			document.getElementById("postcd").focus();
			return false;
		}
		if(document.getElementById("effdt").value == "" || document.getElementById("effdt").value == "DD-MMM-YYYY")
		{
			alert("Please Select Effective Date");
			document.getElementById("effdt").focus();
			return false;
		}
		//alert(post+"  "+effdt);
		window.location.href="GradeServlet?action=details&postcd="+post+"&effdt="+effdt;
	}

	function checkToCLear()
	{
		var flag = document.getElementById("clr").value;
		if(flag==0 || flag==2 || flag==3)  // first time loading
		{		
			$(':input','#form1') .not(':button, :submit, :reset, :hidden') .val('') .removeAttr('checked') .removeAttr('selected');	
			
		}
		if(flag==2)
		{
			alert("Record Saved Successfully");
		}
		if(flag==3)
		{
			alert("Record Deleted Successfully");
		}
		if(flag==4)
		{
			alert("Can not delete the record");
		}
	}

	function clearAll()
	{
		$(':input','#form1') .not(':button, :submit, :reset, :hidden') .val('') .removeAttr('checked') .removeAttr('selected');	
		
	}

	function changeAction()
	{
		document.form1.action="GradeServlet?action=delete";
	}
	function Gradevalidation()
	{
		
		if(document.getElementById("postcd").value == "")
		{
			alert("Please Enter Post Code");
			document.getElementById("postcd").focus();
			return false;
		}
		if(document.getElementById("effdt").value == "" || document.getElementById("effdt").value == "DD-MMM-YYYY")
		{
			alert("Please Select Effective Date");
			document.getElementById("effdt").focus();
			return false;
		}
		if(document.getElementById("desc").value == "" )
		{
			alert("Please Enter Description for Grade");
			document.getElementById("desc").focus();
			return false;
		}
		if(document.getElementById("basic").value == "" )
		{
			alert("Please Enter Basic for Grade");
			document.getElementById("basic").focus();
			return false;
		}
		if(isNaN(document.getElementById("basic").value))
		{
			alert("Please Enter Numeric value for Basic of Grade");
			document.getElementById("basic").focus();
			return false;
		}
		if(document.getElementById("alpha").value == "" )
		{
			alert("Please Enter Alpha Code for Grade");
			document.getElementById("alpha").focus();
			return false;
		}
		
	}
</script>

</head>
<body onLoad="checkToCLear()" style="overflow: hidden;"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:scroll; max-height:80%;"  >
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1> Grade Management </h1>
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

<form action="GradeServlet?action=addnew" id="form1" name="form1" method="post" onSubmit="return Gradevalidation()">
<table width="750" border="1" id="customers">
	<tr><th>Grade Management</th></tr>
	
	<tr><td align="center" valign="middle">
	<table width="588" bgcolor="#999999">
  <tr class="alt">
    <td width="80" height="45" align="right" bgcolor="#CCCCCC">Post Code </td>
    <td width="153" bgcolor="#FFFFFF">
    	<label><input name="postcd" type="text" id="postcd" value="<%=GB.getPOSTCD() %>" maxlength="3">
    	</label>
    </td>
    <td width="103" align="right" bgcolor="#CCCCCC">Effective Date </td>
    <td width="178" bgcolor="#FFFFFF">
      <label><input name="effdt" id="effdt" type="text" size="15" value="<%=GB.getEFFDT() %>" onFocus="if(value=='dd-mmm-yyyy') {value=''}"
           	onBlur="if(value=='') {value='';}" readonly="readonly">&nbsp;<img src="images/cal.gif" align="middle" style="vertical-align:middle;
            cursor:pointer;" onClick="javascript:NewCssCal('effdt', 'ddmmmyyyy')" />
      <input type="button" name="Submit" value="Go" onClick="getDetails()">
    </label></td>
  </tr>
  <tr class="alt">
    <td align="right" bgcolor="#CCCCCC">Description</td>
    <td colspan="3" bgcolor="#FFFFFF">
    	<label><input name="desc" id="desc" value="<%=GB.getDISC() %>" type="text" size="50"></label>
    </td>
    </tr>
  <tr class="alt">
    <td align="right" bgcolor="#CCCCCC">Basic</td>
    <td bgcolor="#FFFFFF"><label>
      <input type="text" value="<%=GB.getBASIC() %>" name="basic" id="basic" >
    </label></td>
    <td align="right" bgcolor="#CCCCCC">Alpha Code </td>
    <td bgcolor="#FFFFFF"><label>
      <input name="alpha" id="alpha" value="<%=GB.getALFACD() %>" type="text" size="15">
    </label></td>
  </tr>
</table>
	</td></tr>
	<tr bgcolor="#1F5FA7" ><td align="center" valign="middle">&nbsp;</td></tr>
	<tr class="alt"><td align="center" valign="middle"><table width="459" border="0" bgcolor="#999999">
      <tr class="alt">
        <td width="249" bgcolor="#CCCCCC">Increments</td>
        <td width="30" align="center">1st</td>
        <td width="30" align="center">2nd</td>
        <td width="30" align="center">3rd</td>
        <td width="30" align="center">4th</td>
        <td width="30" align="center">5th</td>
        <td width="30" align="center">6th</td>
      </tr>
      <tr class="alt">
        <td bgcolor="#CCCCCC">In Rupees </td>
        <td bgcolor="#FFFFFF"><input name="incr1" id="incr1" value="<%=GB.getINCR1() %>" type="text" size="5" maxlength="5"></td>
        <td bgcolor="#FFFFFF"><input name="incr2" id="incr2" value="<%=GB.getINCR2() %>" type="text" size="5" maxlength="5"></td>
        <td bgcolor="#FFFFFF"><input name="incr3" id="incr3" value="<%=GB.getINCR3() %>" type="text" size="5" maxlength="5"></td>
        <td bgcolor="#FFFFFF"><input name="incr4" id="incr4" value="<%=GB.getINCR4() %>" type="text" size="5" maxlength="5"></td>
        <td bgcolor="#FFFFFF"><input name="incr5" id="incr5" value="<%=GB.getINCR5() %>" type="text" size="5" maxlength="5"></td>
        <td bgcolor="#FFFFFF"><input name="incr6" id="incr6" value="<%=GB.getINCR6() %>" type="text" size="5" maxlength="5"></td>
      </tr>
      <tr class="alt">
        <td bgcolor="#CCCCCC">Number Of Years </td>
        <td bgcolor="#FFFFFF"><input name="noy1" id="noy1" value="<%=GB.getNOY1() %>" type="text" size="5" maxlength="2"></td>
        <td bgcolor="#FFFFFF"><input name="noy2" id="noy2" value="<%=GB.getNOY2() %>" type="text" size="5" maxlength="2"></td>
        <td bgcolor="#FFFFFF"><input name="noy3" id="noy3" value="<%=GB.getNOY3() %>" type="text" size="5" maxlength="2"></td>
        <td bgcolor="#FFFFFF"><input name="noy4" id="noy4" value="<%=GB.getNOY4() %>" type="text" size="5" maxlength="2"></td>
        <td bgcolor="#FFFFFF"><input name="noy5" id="noy5" value="<%=GB.getNOY5() %>" type="text" size="5" maxlength="2"></td>
        <td bgcolor="#FFFFFF"><input name="noy6" id="noy6" value="<%=GB.getNOY6() %>" type="text" size="5" maxlength="2"></td>
      </tr>
    </table>
		<br/>
	    <table width="240" border="0" id="customers">
          <tr align="center" bgcolor="#FFFFFF">
            <td width="112">Expenses</td>
            <td width="112">Amount</td>
          </tr>
          <tr>
            <td align="right" bgcolor="#CCCCCC">Medical</td>
            <td align="left" bgcolor="#FFFFFF"><input name="medical" id="medical" value="<%=GB.getMED() %>" type="text" size="15"></td>
          </tr>
          <tr>
            <td align="right" bgcolor="#CCCCCC">Education</td>
            <td align="left" bgcolor="#FFFFFF"><input name="edu" id="edu" value="<%=GB.getEDU() %>" type="text" size="15"></td>
          </tr>
          <tr>
            <td align="right" bgcolor="#CCCCCC">Closing</td>
            <td align="left" bgcolor="#FFFFFF"><input name="closing" id="closing" value="<%=GB.getCLOSING() %>" type="text" size="15"></td>
          </tr>
          <tr>
            <td align="right" bgcolor="#CCCCCC">Clearing</td>
            <td align="left" bgcolor="#FFFFFF"><input name="clearing" id="clearing" value="<%=GB.getCLG() %>" type="text" size="15"></td>
          </tr>
          <tr>
            <td align="right" bgcolor="#CCCCCC">Cash</td>
            <td align="left" bgcolor="#FFFFFF"><input name="cash" id="cash" value="<%=GB.getCASH() %>" type="text" size="15"></td>
          </tr>
          <tr>
            <td align="right" bgcolor="#CCCCCC">LTC</td>
            <td align="left" bgcolor="#FFFFFF"><input name="ltc" id="ltc" value="<%=GB.getLTC() %>" type="text" size="15"></td>
          </tr>
          <tr>
            <td align="right" bgcolor="#CCCCCC">Conveyance</td>
            <td align="left" bgcolor="#FFFFFF"><input name="conv" id="conv" value="<%=GB.getCONV() %>" type="text" size="15"></td>
          </tr>
          <tr>
            <td align="right" bgcolor="#CCCCCC">Field Work </td>
            <td align="left" bgcolor="#FFFFFF"><input name="field" id="field" value="<%=GB.getFLDWRK() %>" type="text" size="15"></td>
          </tr>
          <tr>
            <td align="right" bgcolor="#CCCCCC">Washing</td>
            <td align="left" bgcolor="#FFFFFF"><input name="washing" id="washing" value="<%=GB.getWASHING() %>" type="text" size="15"></td>
          </tr>
          <tr>
            <td align="right" bgcolor="#CCCCCC">EXG</td>
            <td align="left" bgcolor="#FFFFFF"><input name="exg" id="exg" value="<%=GB.getEXG() %>" type="text" size="15"></td>
          </tr>
        </table>
	    <p>
	     <label>
	      <input type="button" name="reset" value="Clear" onClick="clearAll()">
	      </label>
	      <label>
	      <input type="submit" name="save" value="Save">
	      </label>
	      <label>
	      <input type="submit" name="delete" value="Delete" onClick="changeAction()">
	      </label>
	      
	    </p></td>
	</tr>
	<tr><td align="center" valign="middle" bgcolor="#1F5FA7">&nbsp;</td></tr>
</table>
</form>
<input type="hidden" id="clr" value="<%=clr%>">

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