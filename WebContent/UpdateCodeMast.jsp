<%@page import="payroll.DAO.CodeMasterHandler"%>
<%@page import="payroll.Model.CodeMasterBean"%>
<%@page import="payroll.Model.Lookup"%>
<%@page import="java.util.ArrayList"%>
<%@page import="payroll.DAO.LookupHandler"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update Code Master</title>
<%
	int check= -1;
	try
	{
		String action = request.getParameter("action");
		if(action.equalsIgnoreCase("1"))
		{
			check=1; //Record Modified
		}
		else if(action.equalsIgnoreCase("0"))
		{
			check=0;	// Error Record not Modified
		}
	}
	catch(Exception e)
	{
		System.out.println("First Time Loading dialog");
	}	
	CodeMasterHandler CMH = new CodeMasterHandler();
	CodeMasterBean Code = new CodeMasterBean();
 	
 	//int t=0;
	int trncd = 0;
 	try
	{
		trncd = Integer.parseInt(request.getParameter("key"));
		Code = CMH.getCodeDetails(trncd);
	}
	catch(Exception e)
	{
		System.out.println("First Time Loading");
	}
%>

<script type="text/javascript">

function inputLimiter(e,allow) {
	  var AllowableCharacters = '';
	  if (allow == 'Numbers'){AllowableCharacters='.1234567890';}
	  var k;
	  k=document.all?parseInt(e.keyCode): parseInt(e.which);
	  if (k!=13 && k!=8 && k!=0){
	    if ((e.ctrlKey==false) && (e.altKey==false)) {
	      return (AllowableCharacters.indexOf(String.fromCharCode(k))!=-1);
	    } else {
	      return true;
	    }
	  } else {
	    return true;
	  }
	}


function getClose()
	{
		window.close();
	}
	
	function checkVal()
	{
		var flag = document.getElementById("chek").value;
		if(flag == 1)
		{
			alert("Record Updated Successfully!");
			window.close();
		}
		else if(flag==2)
		{
			alert("Error in Modification of Record");
		}
		
	}
	function validation()
	{
		
		if(document.getElementById("trcode").value =="")
		{
			alert("Please Enter Transaction Code ");
			document.getElementById("trcode").focus();
			return false;
		}
		if(document.getElementById("desc").value =="")
		{
			alert("Please Enter Description of Transaction ");
			document.getElementById("desc").focus();
			return false;
		}
		if(document.getElementById("sdesc").value =="")
		{
			alert("Please Enter Description of Transaction ");
			document.getElementById("sdesc").focus();
			return false;
		}
		if(document.getElementById("subsystem").value =="")
		{
			alert("Please Enter Sub System Code ");
			document.getElementById("subsystem").focus();
			return false;
		}
		if(document.getElementById("acno").value =="")
		{
			alert("Please Enter Account Number ");
			document.getElementById("acno").focus();
			return false;
		}
		}
</script>

<style type="text/css">
<!--
.style1 {color: #FF0000}
-->
</style>
</head>
<body onLoad="checkVal()">
<center>
<table id="ccustomers">
<tr valign="top"><td align="center">
<form action="CodeMasterServlet?action=update" method="post" onSubmit="return validation()">
          
    <table width="662" height="181" border="0" bgcolor="#1F5FA7">
	<tr align="left" class="alt">
	  <td height="22" colspan="5" style="color:#fff;"> Update Code Information For <label> <span class="style1">Transaction Code</span> </label>
	    <input type="text" name="trcode" id="trcode" value="<%=Code.getTRNCD()%>" readonly="readonly"></td>
	</tr>
    <tr class="alt">
      <td width="162" height="24" align="right" valign="middle" bgcolor="#F5F6FA">Description</td>
      <td width="86" align="left" valign="middle" bgcolor="#F5F6FA"><input name="desc" type="text" id="desc" value="<%=Code.getDISC()%>" size="30" maxlength="50"></td>
      <td width="44" align="left" valign="top" bgcolor="#F5F6FA">&nbsp;</td>
      <td width="138" align="right" valign="middle" bgcolor="#F5F6FA">Short Description</td>
      <td width="210" align="left" valign="middle" bgcolor="#F5F6FA"><input name="sdesc" id="sdesc" type="text" maxlength="14" value="<%=Code.getSDESC()%>"></td>
    </tr>
    <tr class="alt">
      <td height="24" align="right" valign="middle" bgcolor="#F5F6FA">Print In Slip</td>
      <td align="left" valign="middle" bgcolor="#F5F6FA"><label>
        <select name="select.print" id="select.print">
          <option value="N" selected="selected">Select</option>
          <%
          		if(Code.getPSLIPYN().equalsIgnoreCase("YES"))
          		{
          %>
          		<option value="Y" selected="selected">YES</option>
          		<option value="N">NO</option>
          <%
          		}
          		else
          		{
          %>
          		<option value="N" selected="selected">NO</option>
          		<option value="Y">YES</option>
          <%
          		}
          %>
        </select>
      </label></td>
      <td align="left" valign="top" bgcolor="#F5F6FA">&nbsp;</td>
      <td align="right" valign="middle" bgcolor="#F5F6FA">Gross Y/N</td>
      <td align="left" valign="middle" bgcolor="#F5F6FA"><label>
      <select name="select.autoCalc" id="select.autoCalc">
        <option value="N" selected="selected">Select</option>
          <%
          		if(Code.getGROSS_YN().equalsIgnoreCase("YES"))
          		{
          %>
          		<option value="Y" selected="selected">YES</option>
          		<option value="N">NO</option>
          <%
          		}
          		else
          		{
          %>
          		<option value="N" selected="selected">NO</option>
          		<option value="Y">YES</option>
          <%
          		}
          %>
      </select>
      </label></td>
    </tr>
    <tr class="alt">
      <td height="24" align="right" valign="middle" bgcolor="#F5F6FA">Taxable</td>
      <td align="left" valign="middle" bgcolor="#F5F6FA"><label>
      <select name="select.taxable" id="select.taxable">
        <option value="N" selected="selected">Select</option>
          <%
          		if(Code.getTAXABLE().equalsIgnoreCase("YES"))
          		{
          %>
          		<option value="Y" selected="selected">YES</option>
          		<option value="N">NO</option>
          <%
          		}
          		else
          		{
          %>
          		<option value="N" selected="selected">NO</option>
          		<option value="Y">YES</option>
          <%
          		}
          %>
      </select>
      </label></td>
      <td align="left" valign="top" bgcolor="#F5F6FA">&nbsp;</td>
      <td align="right" valign="middle" bgcolor="#F5F6FA">SubsystemCode </td>
      <td align="left" valign="middle" bgcolor="#F5F6FA">
      <input type="text" name="subsystem" id="subsystem" value="<%=Code.getSUBSYS()%>"></td>
    </tr>
    <tr class="alt">
      <td height="24" align="right" valign="middle" bgcolor="#F5F6FA">HO Autoposting</td>
      <td align="left" valign="middle" bgcolor="#F5F6FA"><label>
      <select name="select.autop" id="select.autop">
        <option value="N" selected="selected">Select</option>
          <%
          		if(Code.getAUTOPOST().equalsIgnoreCase("YES"))
          		{
          %>
          		<option value="Y" selected="selected">YES</option>
          		<option value="N">NO</option>
          <%
          		}
          		else
          		{
          %>
          		<option value="N" selected="selected">NO</option>
          		<option value="Y">YES</option>
          <%
          		}
          %>
      </select>
      </label></td>
      <td align="left" valign="top" bgcolor="#F5F6FA">&nbsp;</td>
      <td align="right" valign="middle" bgcolor="#F5F6FA">A/C No.</td>
      <td align="left" valign="middle" bgcolor="#F5F6FA">
      	<input name="acno" id="acno" type="text" size="15" value="<%=Code.getACNO()%>"></td>
    </tr>
    <tr class="alt">
      <td height="24" align="right" valign="middle" bgcolor="#F5F6FA">Print Sequence </td>
      <td align="left" valign="middle" bgcolor="#F5F6FA">    
    <input class="form-control" name="printsequnce" id="printsequnce" type="text" size="15" value="<%=Code.getPrintsequnce()%>" onkeypress="return inputLimiter(event,'Numbers')" >(E.g.1,2..)</td>
    </td>
      <td align="left" valign="top" bgcolor="#F5F6FA">&nbsp;</td>
      <td align="right" valign="middle" bgcolor="#F5F6FA"> </td>
      <td align="left" valign="middle" bgcolor="#F5F6FA">
      	</td>
    </tr>
    <tr align="center" valign="middle" class="alt">
      <td height="26" colspan="5" bgcolor="#F5F6FA">
          <label>
          <input type="submit" name="Submit2"  class="myButton " value="Save" />
          </label>
          <label>
          <input type="button" name="Submit3" class="myButton " value="Cancel" onClick="getClose()"/>
        </label></td>
    </tr>
  </table>

</form></td>
</tr>
<tr><td align="center" valign="middle" bgcolor="#1F5FA7">&nbsp;</td>
</tr>
</table>

</center>
<input type="hidden" value="<%=check%>" name="chek" id="chek">
</body>
</html>