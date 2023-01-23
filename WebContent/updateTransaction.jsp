<%@page import="payroll.DAO.CodeMasterHandler"%>
<%@page import="payroll.Model.TranBean"%>
<%@page import="payroll.DAO.TranHandler"%>
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
<title></title>
<style type="text/css">

body,td,th {
	font-family: Georgia;
	font-size: 14px;
	color:#000000;
}

</style>


<%

int check=0;
	try
	{
		String action = request.getParameter("action");
		if(action.equalsIgnoreCase("close"))
		{
			check=1; //Record Modified
		}
		else if(action.equalsIgnoreCase("keep"))
		{
			check=2;	// Error Record not Modified
		}
	}
	catch(Exception e)
	{
	}
String[] key= request.getParameter("key").split(":");
	int empno = Integer.parseInt(key[0]);
	int trncd = Integer.parseInt(key[1]);
	int srno= Integer.parseInt(key[2]);
	String trndate=key[3];
	
	TranHandler trhnd = new TranHandler();
	TranBean trb = trhnd.getSingleTranInfo(empno, trncd, srno, trndate);
	


%>
<script type="text/javascript">

	function getClose()
	{
		window.close();
	}
	
	function checkVal()
	{
		no=document.getElementById("empno").value;
		var flag = document.getElementById("chek").value;
		if(flag == 1)
		{
			alert("Record Updated Successfully!");
			//window.close();
			window.location.href="TransactionServlet?no="+no;
		}
		else if(flag==2)
		{
			alert("Error in Modification of Record");
		}
		
	}
function Validation()
{
if(document.getElementById("inpamnt").value== "" || document.getElementById("adjamt").value=="" ||
		document.getElementById("netamnt").value==""||document.getElementById("cryfwd").value=="")
	{
	alert("Please Enter All Data");
	return false;
	}
}
function getClose1()
{
	no=document.getElementById("empno").value;
	window.location.href="TransactionServlet?no="+no;
	
}
</script>
</head>
<body onLoad="checkVal()">
<img src='images/Close.png' style='float:right;' title='Remove' onclick="getClose1()"><br>
<br><br><br><br><br><br><br>
<div align="center">
<form id="f1" name="f1" action="TransactionServlet" method="post" onsubmit="return Validation()">
<table width="665" border="0" align="center" id="customers">
  <tr class="alt">
    <td colspan="5" align="center">Update Transaction <input type="hidden" id="action" name="action" value="Update"></td>
  </tr>
  <tr class="alt">
    <td width="139" align="right" valign="middle" >Transaction Code </td>
    <td width="194" bgcolor="#FFFFFF"><input type="text" value="<%=CodeMasterHandler.getCDesc(trb.getTRNCD()) %>" size="30" readonly="readonly"/></td>
    <td width="16" bgcolor="#CCCCCC">&nbsp;</td>
    <td width="129" align="right" bgcolor="#FFFFFF"><%if(trb.getTRNCD()==999) {%>Gross Salary<%}else{ %>Input Amount<%} %> </td>
    <td width="165" bgcolor="#FFFFFF"><input type="text" name="inpamnt" id="inpamnt" value="<%=trb.getINP_AMT()%>" readonly="readonly" > </td>
  </tr>
  <tr class="alt">
   <td align="right" valign="middle" bgcolor="#FFFFFF">Carry Forward</td>
   <%if(trb.getTRNCD()==243){%> 
       <td bgcolor="#FFFFFF" ><input type="text" readonly="readonly" name="cryfwd" id="cryfwd" value=0> </td>
   <%}else{ %>
    <td bgcolor="#FFFFFF" ><input type="text" readonly="readonly" name="cryfwd" id="cryfwd" value="<%=trb.getCF_SW()%>"> </td>
   <%} %> <td bgcolor="#CCCCCC">&nbsp;</td>
    <td align="right" bgcolor="#FFFFFF"><%if(trb.getTRNCD()==999) {%>Deduction<%}else{ %>Cal Amount<%} %>  </td>
    <td bgcolor="#FFFFFF"><input name="calamt" id="calamt" type="text" value="<%=trb.getCAL_AMT() %>"  /></td>
  </tr>
  <tr class="alt">
    <td align="right" valign="middle" >Adjust Amount </td>
    <td bgcolor="#FFFFFF"><input type="text" name="adjamt" id="adjamt" value="<%=trb.getADJ_AMT()%>" ></td>
   <td bgcolor="#CCCCCC">&nbsp;</td>
    <td align="right" bgcolor="#FFFFFF"><%if(trb.getTRNCD()==999) {%>Net Pay<%}else{ %>Net Amount<%} %> </td>
    <td bgcolor="#FFFFFF"><input name="netamnt" id="netamnt" type="text" value="<%=trb.getNET_AMT() %>"  /></td>
  
  </tr>
  
  <tr align="center"  class="alt">
    <td colspan="5" valign="middle">
      <label><input type="submit" name="save" value="Update" class="myButton" />
      </label>
      <label><input type="button" name="clear" value="Cancel" onClick="getClose1()" class="myButton"/>
      </label>
    </td>
    </tr>
</table>
<input type="hidden" value="<%=empno%>" name="empno" id="empno">
<input type="hidden" value="<%=trncd%>" name="trncd" id="trncd">
<input type="hidden" value="<%=srno%>" name="srno" id="srno">
<input type="hidden" value="<%=trndate%>" name="trndate" id="trndate">
<input type="hidden" value="<%=check%>" name="chek" id="chek">
</form></div>
</body>
</html>
