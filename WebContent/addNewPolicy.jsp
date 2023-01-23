<%@page import="payroll.Model.CodeMasterBean"%>
<%@page import="payroll.DAO.CodeMasterHandler"%>
<%@page import="payroll.Model.*"%>
<%@page import="payroll.DAO.*"%>
<%@page import="java.util.*"%>
 <%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />		

<script type="text/javascript" src="js/datetimepicker1.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>

<title></title>
<style type="text/css">

body,td,th {
	font-family: Georgia;
	font-size: 14px;
	color:#000000;
}

</style>


<script type="text/javascript">

jQuery(function() 
	{
          $("#EMPNO1").autocomplete("list.jsp");
	});

	function getClose()
	{
		window.close();
	}
	
	function checkVal()
	{
		var flag = document.getElementById("chek").value;
		
		if(flag == 1)
		{
			alert("Record inserted Successfully!");
			window.close();
		}
		else if(flag==2)
		{
			alert("Error in  inserting Record");
			window.close();
		}
		
	}
	
	function checkNumeric(id)
	{
		fcs = document.getElementById(id);
		
		if (isNaN(fcs.value))
		{
		    alert("Please Enter Numeric Value Only ");
		    setTimeout("fcs.focus()", 50);
		    return false;
		}
		
	}

	function validate()
	{
  		if (document.getElementById("polyname").value == "") 
		{
			alert("Please Enter Policy Name");
			setTimeout("document.getElementById('polyname').focus()",50);
			return false;
		}
		if (document.getElementById("polytype").value == "") 
		{
			alert("Please Enter Policy Type");
			setTimeout("document.getElementById('polytype').focus()",50);
			return false;
		}
		if(document.getElementById("polydate").value == "")
			{
			alert("Please Select Date of Policy");
			setTimeout("document.getElementById('polydate').focus()",50);
			return false;
			}
		if(document.getElementById("preamnt").value == "")
		{
			alert("Please Enter Premium Amount");
			setTimeout("document.getElementById('preamnt').focus()",50);
			return false;
		}
		if(document.getElementById("prestatus").value == "")
		{
			alert("Please Enter Premium Status");
			setTimeout("document.getElementById('prestatus').focus()",50);
			return false;
		}
		if(document.getElementById("matdate").value == "")
		{
			alert("Please Select Date of Maturity ");
			setTimeout("document.getElementById('matdate').focus()",50);
			return false;
		}
		if(document.getElementById("matamnt").value == "")
		{
			alert("Please Enter Maturity Amount");
			setTimeout("document.getElementById('matamnt').focus()",50);
			return false;
		}
		if(document.getElementById("compname").value == "")
		{
			alert("Please Enter Insurance Company Name");
			setTimeout("document.getElementById('compname').focus()",50);
			return false;
		}if(document.getElementById("dedmonth").value == "0")
		{
			alert("Please Select Deduction Month");
			setTimeout("document.getElementById('dedmonth').focus()",50);
			return false;
		}
		
	}
</script>


<%
SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");

Date date = new Date();
String currentdate = dateFormat.format(date); 

String empno = request.getParameter("no")==null?"":request.getParameter("no");

%>
<%


int check=0;
	try
	{
		String action = request.getParameter("action");
		if(action.equalsIgnoreCase("close"))
		{
			check=1; //Record inserted
		}
		else if(action.equalsIgnoreCase("keep"))
		{
			check=2;	// Error Record not inserted
		}
	}
	catch(Exception e)
	{
		
	}
%>
</head>
<body onLoad=" checkVal()">
<form id="f1" name="f1" action="PolicyMasterServlet?action=addnewPolicy" method="post" onSubmit="return validate()">
<table width="672" border="0" align="center" id="customers">
  <tr class="alt">
    <th colspan="5" align="center">Add New Policy </th>
  </tr>
  <tr class="alt"> <td>Employee Number</td><td colspan="4"><input name="empno" id="empno" type="text" readonly="readonly" value="<%=empno%>"></td></tr>
  <tr class="alt">
    <td width="132"  valign="middle" >Policy Name </td>
    <td width="194" bgcolor="#FFFFFF"><input name="polyname" id="polyname" type="text" value="">
	
	</td>
    <td width="25" bgcolor="#CCCCCC">&nbsp;</td>
    <td width="141"  bgcolor="#FFFFFF">Policy Type</td>
    <td width="158" bgcolor="#FFFFFF"> <input name="polytype" type="text"  id="polytype" value="" maxlength="100"></td>
  
  
  <tr class="alt">
    <td width="132" valign="middle" >Policy Date</td>
    <td width="194" bgcolor="#FFFFFF">
	<input type="text" name="polydate" id="polydate" readonly="readonly">
	<img src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
				onClick="javascript:NewCssCal('polydate', 'ddmmmyyyy')" />
	
	
	</td>
    <td width="25" bgcolor="#CCCCCC">&nbsp;</td>
    <td width="141"  bgcolor="#FFFFFF">Policy Amount </td>
    <td width="158" bgcolor="#FFFFFF"><input name="polyamnt" type="text" id="polyamnt" onBlur="checkNumeric(this.id)" maxlength="10"> </td>
  </tr>
 
  <tr class="alt">
    <td  bgcolor="#FFFFFF">Premium Amount</td>
    <td bgcolor="#FFFFFF"><input name="preamnt" type="text" id="preamnt" onBlur="checkNumeric(this.id)" maxlength="10" > </td><td bgcolor="#CCCCCC">&nbsp;</td>
      <td width="141"  bgcolor="#FFFFFF" >Premium Status</td>
      <td><input type="text" name="prestatus" id="prestatus" ></td>
  </tr>
  <tr class="alt">
    <td  bgcolor="#FFFFFF">Maturity Date</td>
    <td bgcolor="#FFFFFF"><input type="text" name="matdate" id="matdate" readonly="readonly" ><img src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
				onClick="javascript:NewCssCal('matdate', 'ddmmmyyyy')" /> </td><td bgcolor="#CCCCCC">&nbsp;</td>
      <td width="141"  bgcolor="#FFFFFF" >Maturity Amount</td>
      <td><input name="matamnt" type="text" id="matamnt" onBlur="checkNumeric(this.id)" maxlength="10" ></td>
  </tr>
  <tr class="alt"><td>Deduction Month</td>
  <td >
  <select name="dedmonth" id="dedmonth">
  <option value="0">Select Month</option>
  <option value="1">January</option>
  <option value="2">February</option>
  <option value="3">March</option>
  <option value="4">April</option>
  <option value="5">May</option>
  <option value="6">June</option>
  <option value="7">July</option>
  <option value="8">August</option>
  <option value="9">September</option>
  <option value="10">October</option>
  <option value="11">November</option>
  <option value="12">December</option>
  </select>
	</td>
	<td></td>
	<td>Insurance Company</td>
	<td><input type="text" name="compname" id="compname" ></td>
	</tr>
 
  <tr align="center"  class="alt">
    <td colspan="5" valign="middle">
      <label><input type="submit" name="save" value="Save" />
      </label>
      <label><input type="button" name="clear" value="Cancel" onClick="getClose()"/>
      </label>
    </td>
    </tr>
</table>

</form>
<input type="hidden" value="<%=check%>" name="chek" id="chek">
</body>
</html>
